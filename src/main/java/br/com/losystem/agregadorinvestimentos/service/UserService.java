package br.com.losystem.agregadorinvestimentos.service;

import br.com.losystem.agregadorinvestimentos.dto.response.AccountResponseDTO;
import br.com.losystem.agregadorinvestimentos.dto.request.CreateAccountDTO;
import br.com.losystem.agregadorinvestimentos.dto.request.CreateUserDTO;
import br.com.losystem.agregadorinvestimentos.dto.UpdateUserDTO;
import br.com.losystem.agregadorinvestimentos.entity.Account;
import br.com.losystem.agregadorinvestimentos.entity.BillingAddress;
import br.com.losystem.agregadorinvestimentos.entity.User;
import br.com.losystem.agregadorinvestimentos.repository.AccountRepository;
import br.com.losystem.agregadorinvestimentos.repository.BillingAnddressRepository;
import br.com.losystem.agregadorinvestimentos.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.isNull;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final BillingAnddressRepository billingAnddressRepository;

    public UserService(UserRepository userRepository, AccountRepository accountRepository,
                       BillingAnddressRepository billingAnddressRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.billingAnddressRepository = billingAnddressRepository;
    }

    public UUID createUser(CreateUserDTO userDTO) {
        var entity = new User(null,
                userDTO.username(),
                userDTO.email(),
                userDTO.password(),
                Instant.now(),
                null);
        var user = userRepository.save(entity);
        return user.getUserID();
    }

    public Optional<User> getUserById(String userId) {

        var user = userRepository.findById(UUID.fromString(userId));
        return user;
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public void deleteByid(String id) {
        var userId = UUID.fromString(id);
        var userExists = userRepository.existsById(userId);
        if (userExists) {
            userRepository.deleteById(userId);
        }
    }

    public void updateUser(String userId, UpdateUserDTO dto) {
        var id = UUID.fromString(userId);
         var userExists = userRepository.findById(id);
         if (userExists.isPresent()) {
             var user = userExists.get();
             if (dto.username() != null) {
                 user.setUsername(dto.username());
             }

             if (dto.password() != null) {
                 user.setPassword(dto.password());
             }

             userRepository.save(user);
         }
    }

    public void createAccount(String userId, CreateAccountDTO accountDTO) {

        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (isNull(user.getAccounts())) {
            user.setAccounts(new ArrayList<>());
        }

        var account = new Account(
                accountDTO.description(),
                user,
                null,
                new ArrayList<>()
        );

        var accountCreated = accountRepository.save(account);

        var billingAddrens = new BillingAddress(
            accountCreated.getAccountId(),
                accountCreated,
                accountDTO.street(),
                accountDTO.number()
        );

        billingAnddressRepository.save(billingAddrens);
    }

    public List<AccountResponseDTO> listAccount(String userId) {
        var user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return user.getAccounts()
                .stream().map(ac -> new AccountResponseDTO(ac.getAccountId().toString(),ac.getDescription()))
                .toList();
    }
}
