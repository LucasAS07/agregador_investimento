package br.com.losystem.agregadorinvestimentos.service;

import br.com.losystem.agregadorinvestimentos.dto.CreateUserDTO;
import br.com.losystem.agregadorinvestimentos.dto.UpdateUserDTO;
import br.com.losystem.agregadorinvestimentos.entity.User;
import br.com.losystem.agregadorinvestimentos.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        var userExists = userRepository.findById(userId);
        if (userExists.isPresent()) {
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

}
