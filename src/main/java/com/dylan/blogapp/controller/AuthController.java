package com.dylan.blogapp.controller;

import com.dylan.blogapp.dto.LoginDTO;
import com.dylan.blogapp.dto.RegisterDTO;
import com.dylan.blogapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;


    @PreAuthorize("hasRole('USER')")
    @GetMapping()
    String index(){
        System.out.println("..............hit");
        return "index";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
        System.out.println("------aaaaa-----");
        String result = authService.login(loginDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> login(@RequestBody RegisterDTO registerDTO){
        System.out.println("------bbbbbb-----");
        String result = authService.register(registerDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}