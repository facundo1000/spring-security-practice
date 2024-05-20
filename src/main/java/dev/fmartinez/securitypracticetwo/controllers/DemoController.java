package dev.fmartinez.securitypracticetwo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {

    @GetMapping("/demo")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello World");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> adminResponse() {
        return ResponseEntity.ok("Admin Response");
    }

    @GetMapping("/user")
    public ResponseEntity<String> userResponse() {
        return ResponseEntity.ok("User Response");
    }

    @GetMapping("/dev")
    public ResponseEntity<String> developerResponse() {
        return ResponseEntity.ok("Developer Response");
    }
}
