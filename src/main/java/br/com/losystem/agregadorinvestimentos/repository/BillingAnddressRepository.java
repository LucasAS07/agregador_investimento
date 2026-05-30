package br.com.losystem.agregadorinvestimentos.repository;

import br.com.losystem.agregadorinvestimentos.entity.BillingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BillingAnddressRepository extends JpaRepository<BillingAddress, UUID> {
}
