package com.enigma.tokonyadia.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class TokoController {

    @GetMapping("/sayHello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping(path = "/{userId}")
    public String user(@PathVariable int userId) {
        return "Hello User " + userId ;
        
    }

    @GetMapping(path = "/page")
    public String page(@RequestParam String page) {
        return "welcome to page : " + page ;
        
    }

    @PostMapping
    public ResponseEntity<String> redBody(@RequestBody String name) {
        return ResponseEntity.ok().body("success add :" + name);
    }
}
