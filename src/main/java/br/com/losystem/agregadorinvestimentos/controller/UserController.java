package br.com.losystem.agregadorinvestimentos.controller;

import br.com.losystem.agregadorinvestimentos.dto.CreateUserDTO;
import br.com.losystem.agregadorinvestimentos.dto.UpdateUserDTO;
import br.com.losystem.agregadorinvestimentos.entity.User;
import br.com.losystem.agregadorinvestimentos.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDTO userDTO) {
        var userId = userService.createUser(userDTO);
        return ResponseEntity.created(URI.create("/v1/users/" + userId.toString())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id){
        var user = userService.getUserById(id);
        if (user.isPresent()){
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        var users =userService.listUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        userService.deleteByid(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable String userId, @RequestBody UpdateUserDTO dto) {
        userService.updateUser(userId,dto);
        return ResponseEntity.noContent().build();
    }

}
