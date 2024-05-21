package dev.fmartinez.securitypracticetwo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {

    @GetMapping("/unsecured")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("<h1>Hello this URL doesn't work with oauth2</h1>");
    }

    @GetMapping("/secured")
    public ResponseEntity<String> adminResponse() {
        return ResponseEntity.ok("<h1>Hello this URL does work with oauth2<h1>");
    }


}
