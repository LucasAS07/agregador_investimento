package br.com.losystem.agregadorinvestimentos.service;

import br.com.losystem.agregadorinvestimentos.client.BrapiClient;
import br.com.losystem.agregadorinvestimentos.dto.response.AccountStockResponseDTO;
import br.com.losystem.agregadorinvestimentos.dto.response.AssociationAccountStockDTO;
import br.com.losystem.agregadorinvestimentos.entity.AccountStock;
import br.com.losystem.agregadorinvestimentos.entity.AccountStockId;
import br.com.losystem.agregadorinvestimentos.repository.AccountRepository;
import br.com.losystem.agregadorinvestimentos.repository.AccountStockRepository;
import br.com.losystem.agregadorinvestimentos.repository.StockRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    @Value("#{environment.TOKEN}")
    private String TOKEN;

    private final StockRepository stockRepository;
    private final AccountRepository accountRepository;
    private final AccountStockRepository accountStockRepository;
    private final BrapiClient brapiClient;

    public AccountService(StockRepository stockRepository, AccountRepository accountRepository,
                          AccountStockRepository accountStockRepository, BrapiClient brapiClient) {
        this.stockRepository = stockRepository;
        this.accountRepository = accountRepository;
        this.accountStockRepository = accountStockRepository;
        this.brapiClient = brapiClient;
    }

    public void associationAccount(String accountsId, AssociationAccountStockDTO dto) {

        var stock = stockRepository.findById((dto.stockId())).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        var account = accountRepository.findById(UUID.fromString(accountsId)).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        var id = new AccountStockId(account.getAccountId(),stock.getStockId());
        var entity = new AccountStock(
                id,
                account,
                stock,
                dto.quantity()
        );

        accountStockRepository.save(entity);
    }

    public List<AccountStockResponseDTO> listAccount(String accountsId) {
        var account = accountRepository.findById(UUID.fromString(accountsId)).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        return account.getAccountStocks()
                .stream().map(
                        as -> new AccountStockResponseDTO(
                                as.getStock().getStockId(),
                                as.getQuantity(),
                                getTotal(as.getQuantity(),as.getStock().getStockId())))
                .toList();
    }

    private double getTotal(Integer quantity, String stockId) {
        var response = brapiClient.getQuote(TOKEN, stockId);

        var price = response.results().getFirst().regularMarketPrice();

        return quantity * price;
    }
}
