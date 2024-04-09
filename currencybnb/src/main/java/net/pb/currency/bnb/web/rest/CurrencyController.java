package net.pb.currency.bnb.web.rest;

import lombok.RequiredArgsConstructor;
import net.pb.currency.bnb.dto.CurrencyDTO;
import net.pb.currency.bnb.service.CurrencyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bnb")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping("/download-currencies")
    public ResponseEntity<List<CurrencyDTO>> downloadCurrenciesFromBnb() {
        List<CurrencyDTO> currencyDTOList = currencyService.performBnbCurrencySearchAndSendThroughWebSocket();
        if (currencyDTOList != null && !currencyDTOList.isEmpty()) {
            return ResponseEntity.ok(currencyDTOList);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
