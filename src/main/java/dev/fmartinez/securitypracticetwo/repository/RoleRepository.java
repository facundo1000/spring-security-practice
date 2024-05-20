package dev.fmartinez.securitypracticetwo.repository;

import dev.fmartinez.securitypracticetwo.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
