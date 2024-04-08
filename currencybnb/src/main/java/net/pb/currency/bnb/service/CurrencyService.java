package net.pb.currency.bnb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.pb.currency.bnb.dto.CurrencyDTO;
import net.pb.currency.bnb.dto.CurrencyWrapperDTO;
import net.pb.currency.bnb.mapper.CurrencyMapper;
import net.pb.currency.bnb.repository.CurrencyRepository;
import net.pb.currency.bnb.utils.JAXBUtils;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CurrencyService {


    private final WebClient webClient;

    private final CurrencyMapper mapper;

    private final CurrencyRepository repository;

//    private final WebSocketHandler webSocketHandler;

    public List<CurrencyDTO> performBnbCurrencySearch() {

        try {
            String xmlFromBnb = webClient.get()
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
                    .onStatus(HttpStatusCode::isError, clientResponse -> Mono.error(Exception::new))
                    .bodyToMono(String.class)
                    .block();

            if (xmlFromBnb != null) {
                Optional<CurrencyWrapperDTO> unmarshalledCurrencyWrapper = JAXBUtils.unmarshalFromXml(xmlFromBnb, CurrencyWrapperDTO.class);

                return unmarshalledCurrencyWrapper
                        .map(CurrencyWrapperDTO::getCurrencyDTOList)
                        .orElseThrow(() -> new NoSuchElementException("No currency list found"))
                        .stream()
                        .filter(currencyDTO -> !currencyDTO.getGoldEquivalent().equals(0))
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
