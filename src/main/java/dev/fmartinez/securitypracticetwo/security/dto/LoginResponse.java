package dev.fmartinez.securitypracticetwo.security.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

@JsonPropertyOrder({"username", "message", "token", "status"})
@Builder
public record LoginResponse(String username, String message, String token, boolean status) {

}
