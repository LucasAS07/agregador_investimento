package br.com.losystem.agregadorinvestimentos.repository;

import br.com.losystem.agregadorinvestimentos.entity.AccountStock;
import br.com.losystem.agregadorinvestimentos.entity.AccountStockId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}
