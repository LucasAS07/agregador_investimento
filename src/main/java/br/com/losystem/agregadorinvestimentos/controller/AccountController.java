package br.com.losystem.agregadorinvestimentos.controller;

import br.com.losystem.agregadorinvestimentos.dto.response.AccountStockResponseDTO;
import br.com.losystem.agregadorinvestimentos.dto.response.AssociationAccountStockDTO;
import br.com.losystem.agregadorinvestimentos.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{accountsId}/stocks")
    public ResponseEntity<Void> associateStock(@PathVariable String accountsId,
                                               @RequestBody AssociationAccountStockDTO dto) {
        accountService.associationAccount(accountsId, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountsId}/stocks")
    public ResponseEntity<List<AccountStockResponseDTO>> listStocks(@PathVariable String accountsId) {
        var accounts = accountService.listAccount(accountsId);
        return ResponseEntity.ok(accounts);
    }
}
