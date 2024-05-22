package dev.fmartinez.securitypracticetwo.controllers;

import dev.fmartinez.securitypracticetwo.security.*;
import dev.fmartinez.securitypracticetwo.security.dto.LoginRequest;
import dev.fmartinez.securitypracticetwo.security.dto.LoginResponse;
import dev.fmartinez.securitypracticetwo.security.dto.RegisterRequest;
import dev.fmartinez.securitypracticetwo.security.dto.RegisterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl service;

    @GetMapping("/hello")
    public ResponseEntity<String> helloAuth(){
        return new ResponseEntity<>("Esta es una ruta publica", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return new ResponseEntity<>(service.loginValidate(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerResponse(@RequestBody RegisterRequest registerRequest) {
        return new ResponseEntity<>(service.saveUser(registerRequest),HttpStatus.OK);
    }


}
