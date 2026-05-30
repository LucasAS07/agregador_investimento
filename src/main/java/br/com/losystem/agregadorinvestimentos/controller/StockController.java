package br.com.losystem.agregadorinvestimentos.controller;

import br.com.losystem.agregadorinvestimentos.dto.request.CreateStockDTO;
import br.com.losystem.agregadorinvestimentos.entity.Stock;
import br.com.losystem.agregadorinvestimentos.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/v1/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody CreateStockDTO stockDTO) {
        var stockId = stockService.createStock(stockDTO);
        return ResponseEntity.created(URI.create("/v1/stocks/" + stockId.toString())).build();
    }
}
