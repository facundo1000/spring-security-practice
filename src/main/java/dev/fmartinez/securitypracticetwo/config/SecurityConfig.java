package dev.fmartinez.securitypracticetwo.config;

import dev.fmartinez.securitypracticetwo.config.filter.JwtTokenValidator;
import dev.fmartinez.securitypracticetwo.security.UserDetailsServiceImpl;
import dev.fmartinez.securitypracticetwo.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils utils;

    @Bean
    public WebSecurityCustomizer customizer() {
        return webSecurity -> webSecurity.ignoring().requestMatchers(PathRequest.toH2Console());
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(https -> {
                    https.requestMatchers("/auth/**").permitAll();
                    https.requestMatchers(HttpMethod.GET, "/api/user").hasAnyRole("USER", "ADMIN");
                    https.requestMatchers(HttpMethod.GET, "/api/admin").hasRole("ADMIN");
                    https.requestMatchers("/api/dev").hasRole("DEVELOPER");
                    https.anyRequest().authenticated();
                })
                //se necesita ejectuar este filtro antes de que se ejecute el filtro de autenticacion
                .addFilterBefore(new JwtTokenValidator(utils), UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl service) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(service);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

}
