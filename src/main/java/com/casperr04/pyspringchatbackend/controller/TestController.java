package com.casperr04.pyspringchatbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo/controller")
public class TestController {
    @GetMapping
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @GetMapping("/public")
    public ResponseEntity<String> sayHelloPublic() {
        return  ResponseEntity.ok("");
    }
}
