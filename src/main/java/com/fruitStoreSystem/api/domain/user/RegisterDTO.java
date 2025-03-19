package com.fruitStoreSystem.api.domain.user;

public record RegisterDTO(String login, String password, UserRoles role) {
}
