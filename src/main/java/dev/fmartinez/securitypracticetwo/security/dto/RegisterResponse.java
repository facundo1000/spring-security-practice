package dev.fmartinez.securitypracticetwo.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegisterResponse {

    private String username;

    private String password;

    private String email;

    private Boolean enabled;
}
