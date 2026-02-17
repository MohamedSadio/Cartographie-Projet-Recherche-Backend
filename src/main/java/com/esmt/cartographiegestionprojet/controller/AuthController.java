package com.esmt.cartographiegestionprojet.controller;

import com.esmt.cartographiegestionprojet.dto.LoginRequest;
import com.esmt.cartographiegestionprojet.dto.LoginResponse;
import com.esmt.cartographiegestionprojet.dto.response.ApiResponse;
import com.esmt.cartographiegestionprojet.entity.User;
import com.esmt.cartographiegestionprojet.security.CustomUserDetails;
import com.esmt.cartographiegestionprojet.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentification", description = "Endpoints pour l'authentification et la gestion des sessions")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();
        String token = tokenProvider.generateToken(user.getEmail(), user.getId(), user.getRole().name());

        return ApiResponse.success(new LoginResponse(token, user.getId(), user.getEmail(), user.getNom(), user.getPrenom(), user.getRole()));
    }
}