package br.com.losystem.agregadorinvestimentos.service;

import br.com.losystem.agregadorinvestimentos.dto.request.CreateStockDTO;
import br.com.losystem.agregadorinvestimentos.entity.Stock;
import br.com.losystem.agregadorinvestimentos.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Stock createStock(CreateStockDTO stockDTO) {
        var stock = new Stock(
                stockDTO.stockId(),
                stockDTO.description()
        );
        return stockRepository.save(stock);
    }
}
