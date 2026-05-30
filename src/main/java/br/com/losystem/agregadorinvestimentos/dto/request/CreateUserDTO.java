package br.com.losystem.agregadorinvestimentos.dto.request;

public record CreateUserDTO(
        String username,
        String email,
        String password
) {
}
