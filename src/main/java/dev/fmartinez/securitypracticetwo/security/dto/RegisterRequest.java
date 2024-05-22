package dev.fmartinez.securitypracticetwo.security.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RegisterRequest(@NotBlank String username,
                              @NotBlank String password,
                              @NotBlank @Email(regexp = "[a-zA-Z]+[0-9]+@[a-zA-Z]+.?[a-z]+") String email,
                              @Valid RolesNames rolesNames) {
}
