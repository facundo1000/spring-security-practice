package dev.fmartinez.securitypracticetwo.repository;

import dev.fmartinez.securitypracticetwo.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Set<Role>> findAllByNameIn(List<String> names);

}
