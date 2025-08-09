package com.example.keycloak.dto;

public record LogEventDto(
        PessoaDto pessoaDto,
        String operacao,
        String microservico,
        Long idUsuario,
        String nomeUsuario
) {
}
