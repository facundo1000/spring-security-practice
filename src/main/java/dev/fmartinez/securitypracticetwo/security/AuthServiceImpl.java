package dev.fmartinez.securitypracticetwo.security;

import dev.fmartinez.securitypracticetwo.models.UserAccount;
import dev.fmartinez.securitypracticetwo.repository.RoleRepository;
import dev.fmartinez.securitypracticetwo.repository.UserAccountRepository;
import dev.fmartinez.securitypracticetwo.security.dto.LoginRequest;
import dev.fmartinez.securitypracticetwo.security.dto.LoginResponse;
import dev.fmartinez.securitypracticetwo.security.dto.RegisterRequest;
import dev.fmartinez.securitypracticetwo.security.dto.RegisterResponse;
import dev.fmartinez.securitypracticetwo.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl {
    private final UserAccountRepository repo;

    private final UserDetailsServiceImpl service;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    private final RoleRepository roleRepo;

    public LoginResponse loginValidate(LoginRequest request) {

        Authentication authentication = authenticate(request.username(), request.password());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);

        return LoginResponse.builder()
                .username(request.username())
                .message("Successfully logged in")
                .token(accessToken)
                .status(true)
                .build();
    }

    public RegisterResponse saveUser(RegisterRequest request) {
        UserAccount user = new UserAccount();
        user.setUsername(request.username());
        user.setPassword(encoder.encode(request.password()));
        user.setEmail(request.email());
        user.setEnabled(true);

        Optional<Set<Role>> roleByName = roleRepo.findAllByNameIn(request.rolesNames().rolesNames());


        if (roleByName.isPresent()) {
            user.setRole(roleByName.get());
        }


        UserAccount userCreated = repo.save(user);

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userCreated.getRole().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));


        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), authorities);

        String accessToken = jwtUtils.createToken(authentication);

        context.setAuthentication(authentication);

        log.info("User {} saved", user);
        return RegisterResponse.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .token(accessToken)
                .enabled(user.getEnabled())
                .build();
    }


    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = service.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Username or password is incorrect");
        }

        if (!encoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }


}
