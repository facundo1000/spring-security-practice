package dev.fmartinez.securitypracticetwo.security.dto;

import lombok.Builder;

@Builder

public record RegisterResponse(String username, String password, String token, String email, Boolean enabled) {

}
