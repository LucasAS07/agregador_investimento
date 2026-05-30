package br.com.losystem.agregadorinvestimentos.dto.request;

public record CreateAccountDTO(
        String description,
        String street,
        Integer number
) {
}
