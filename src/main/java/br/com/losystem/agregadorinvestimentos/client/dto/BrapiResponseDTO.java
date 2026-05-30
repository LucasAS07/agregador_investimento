package br.com.losystem.agregadorinvestimentos.client.dto;

import java.util.List;

public record BrapiResponseDTO(List<StockDTO> results) {
}
