package com.dylan.blogapp.service.impl;

import com.dylan.blogapp.dto.LoginDTO;
import com.dylan.blogapp.dto.RegisterDTO;
import com.dylan.blogapp.entity.Role;
import com.dylan.blogapp.entity.User;
import com.dylan.blogapp.exception.BlogAPIException;
import com.dylan.blogapp.repository.RoleRepository;
import com.dylan.blogapp.repository.UserRepository;
import com.dylan.blogapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(),
                loginDTO.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "login successfully!";
    }

    @Override
    public String register(RegisterDTO registerDTO) {
        if(userRepository.existsByUsername(registerDTO.getUsername())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "username already exists.");
        }
        User user = new User();
        user.setName(registerDTO.getName());
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Set<Role> roleSet = new HashSet<>();
        Role role = roleRepository.findByRoleName("ROLE_USER").get();
        roleSet.add(role);
        user.setRoles(roleSet);

        userRepository.save(user);

        return "register successfully!";
    }
}