package dev.fmartinez.securitypracticetwo.security.dto;

import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public record RolesNames(@Size(max = 3, message = "The user cannot have more than 3 roles") List<String> rolesNames) {
}
