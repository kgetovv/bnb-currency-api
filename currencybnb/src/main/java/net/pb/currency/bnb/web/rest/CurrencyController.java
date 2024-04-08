package net.pb.currency.bnb.web.rest;

import net.pb.currency.bnb.dto.CurrencyDTO;
import net.pb.currency.bnb.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bnb")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/download-currencies")
    public ResponseEntity<List<CurrencyDTO>> downloadCurrenciesFromBnb() {

        return ResponseEntity.ok(currencyService.performBnbCurrencySearch());
    }
}
