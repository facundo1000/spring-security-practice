package dev.fmartinez.securitypracticetwo.security;

import dev.fmartinez.securitypracticetwo.models.UserAccount;
import dev.fmartinez.securitypracticetwo.repository.RoleRepository;
import dev.fmartinez.securitypracticetwo.repository.UserAccountRepository;
import dev.fmartinez.securitypracticetwo.security.dto.LoginRequest;
import dev.fmartinez.securitypracticetwo.security.dto.RegisterRequest;
import dev.fmartinez.securitypracticetwo.security.dto.RegisterResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl {
    private final UserAccountRepository repo;

    private final RoleRepository roleRepo;

    private final PasswordEncoder encoder;

    public String loginValidate(LoginRequest request) {
        UserAccount user = repo.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException(request.getUsername()));

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        } else {
            log.info("User {} logged in", user);
            return "Logged in";
        }
    }

    public RegisterResponse saveUser(RegisterRequest request) {
        UserAccount user = new UserAccount();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setEnabled(true);
        repo.save(user);
        log.info("User {} saved", user);
        return RegisterResponse.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .enabled(user.getEnabled())
                .build();
    }

    public String roleAssign(Long userId, Long roleId) {
        UserAccount userAccount = repo.findById(userId).orElseThrow();
        Role role = roleRepo.findById(roleId).orElseThrow();
        userAccount.getRole().add(role);
        repo.save(userAccount);
        log.info("Role {} assigned to user {}", role, userAccount);
        return "Role " + role.getName() + " assigned to user " + userAccount.getUsername();
    }

}
