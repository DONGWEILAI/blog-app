package com.dylan.blogapp.service;

import com.dylan.blogapp.dto.LoginDTO;
import com.dylan.blogapp.dto.RegisterDTO;

public interface AuthService {
    String login(LoginDTO loginDTO);

    String register(RegisterDTO registerDTO);
}