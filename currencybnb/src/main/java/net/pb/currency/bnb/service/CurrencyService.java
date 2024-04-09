package net.pb.currency.bnb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.pb.currency.bnb.dto.CurrencyDTO;
import net.pb.currency.bnb.dto.CurrencyWrapperDTO;
import net.pb.currency.bnb.mapper.CurrencyMapper;
import net.pb.currency.bnb.repository.CurrencyRepository;
import net.pb.currency.bnb.utils.JAXBUtils;
import net.pb.currency.bnb.utils.MyWebSocketHandler;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CurrencyService {


    private final WebClient webClient;

    private final CurrencyMapper mapper;

    private final CurrencyRepository repository;

    private final MyWebSocketHandler webSocketHandler;

    public List<CurrencyDTO> performBnbCurrencySearch() {
        try {
            Mono<String> xmlFromBnbMono = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("downloadOper", "true")
                            .queryParam("group1", "first")
                            .queryParam("firstDays", "01")
                            .queryParam("firstMonths", "04")
                            .queryParam("firstYear", "2024")
                            .queryParam("search", "true")
                            .queryParam("showChart", "false")
                            .queryParam("showChartButton", "false")
                            .queryParam("type", "xml")
                            .build())
                    .accept(MediaType.APPLICATION_XML)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, clientResponse -> Mono.error(new RuntimeException("Failed to retrieve XML from BNB")))
                    .bodyToMono(String.class);

            String xmlFromBnb = xmlFromBnbMono.block(); // Blocking call to retrieve XML, consider avoiding it

            if (xmlFromBnb != null) {
                Optional<CurrencyWrapperDTO> unmarshalledCurrencyWrapper = JAXBUtils.unmarshalFromXml(xmlFromBnb, CurrencyWrapperDTO.class);
                return unmarshalledCurrencyWrapper
                        .map(CurrencyWrapperDTO::getCurrencyDTOList)
                        .orElseThrow(() -> new NoSuchElementException("No currency list found"))
                        .stream()
                        .filter(currencyDTO -> currencyDTO.getGoldEquivalent() != 0)
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching currency data from BNB", e);
        }
        return Collections.emptyList();
    }

    public List<CurrencyDTO> performBnbCurrencySearchAndSendThroughWebSocket() {
        List<CurrencyDTO> currencyDTOList = performBnbCurrencySearch();
        if (currencyDTOList != null && !currencyDTOList.isEmpty()) {
            try {
                String jsonString = new ObjectMapper().writeValueAsString(currencyDTOList);
                log.info("Currency data from BNB as jsonString: " + jsonString);
                webSocketHandler.sendMessageToAll(jsonString);
            } catch (JsonProcessingException e) {
                log.error("Error processing the JSON at performBnbCurrencySearchAndSendThroughWebSocket", e);
            }
        }
        return currencyDTOList != null ? new ArrayList<>(currencyDTOList) : new ArrayList<>();
    }
}
