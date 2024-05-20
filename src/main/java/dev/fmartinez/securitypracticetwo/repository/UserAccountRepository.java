package dev.fmartinez.securitypracticetwo.repository;

import dev.fmartinez.securitypracticetwo.models.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByUsername(String username);
}
