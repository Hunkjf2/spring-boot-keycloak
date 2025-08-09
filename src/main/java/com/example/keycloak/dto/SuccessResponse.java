package com.example.keycloak.dto;

public record SuccessResponse(
        int status,
        String message
) {
    public static SuccessResponse ok(String message){
        return new SuccessResponse(200, message);
    }
}
