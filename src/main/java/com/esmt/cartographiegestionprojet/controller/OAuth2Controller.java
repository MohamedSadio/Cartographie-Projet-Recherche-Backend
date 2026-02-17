package com.esmt.cartographiegestionprojet.controller;

import com.esmt.cartographiegestionprojet.dto.GoogleUserInfo;
import com.esmt.cartographiegestionprojet.dto.LoginResponse;
import com.esmt.cartographiegestionprojet.dto.response.ApiResponse;
import com.esmt.cartographiegestionprojet.entity.User;
import com.esmt.cartographiegestionprojet.security.JwtTokenProvider;
import com.esmt.cartographiegestionprojet.service.OAuth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth/google")
@CrossOrigin(origins = "*")
public class OAuth2Controller {

    @Autowired
    private OAuth2Service oauth2Service;

    @Autowired
    private JwtTokenProvider tokenProvider;

    /**
     * Callback Google OAuth
     * Appelé automatiquement après l'authentification Google
     */
    @GetMapping("/callback")
    public ResponseEntity<ApiResponse<LoginResponse>> googleCallback(
            OAuth2AuthenticationToken authentication) {

        // 1. Extraire les informations de l'utilisateur Google
        Map<String, Object> attributes = authentication.getPrincipal().getAttributes();

        GoogleUserInfo googleUser = new GoogleUserInfo();
        googleUser.setSub((String) attributes.get("sub"));
        googleUser.setEmail((String) attributes.get("email"));
        googleUser.setName((String) attributes.get("name"));
        googleUser.setGiven_name((String) attributes.get("given_name"));
        googleUser.setFamily_name((String) attributes.get("family_name"));
        googleUser.setPicture((String) attributes.get("picture"));
        googleUser.setEmail_verified((Boolean) attributes.get("email_verified"));

        // 2. Créer ou récupérer l'utilisateur dans notre DB
        User user = oauth2Service.getOrCreateUserFromGoogle(googleUser);

        // 3. Générer notre JWT (le même que pour login classique)
        String jwt = tokenProvider.generateToken(
                user.getEmail(),
                user.getId(),
                user.getRole().name()
        );

        // 4. Créer la réponse
        LoginResponse response = new LoginResponse(
                jwt,
                user.getId(),
                user.getEmail(),
                user.getNom(),
                user.getPrenom(),
                user.getRole()
        );

        return ResponseEntity.ok(ApiResponse.success("Authentification Google réussie", response));
    }

    /**
     * Endpoint pour initier le login Google depuis le frontend
     */
    @GetMapping("/login")
    public ResponseEntity<ApiResponse<String>> initiateGoogleLogin() {
        String googleLoginUrl = "http://localhost:8081/oauth2/authorization/google";
        return ResponseEntity.ok(ApiResponse.success("Redirect to Google", googleLoginUrl));
    }
}