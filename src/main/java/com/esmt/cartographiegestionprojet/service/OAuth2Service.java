package com.esmt.cartographiegestionprojet.service;

import com.esmt.cartographiegestionprojet.dto.GoogleUserInfo;
import com.esmt.cartographiegestionprojet.entity.User;
import com.esmt.cartographiegestionprojet.repository.UserRepository;
import com.esmt.cartographiegestionprojet.utils.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class OAuth2Service {

    @Autowired
    private UserRepository userRepository;

    /**
     * Créer ou récupérer un utilisateur depuis Google OAuth
     */
    @Transactional
    public User getOrCreateUserFromGoogle(GoogleUserInfo googleUser) {
        // 1. Chercher si l'utilisateur existe déjà (par email)
        Optional<User> existingUser = userRepository.findByEmail(googleUser.getEmail());

        if (existingUser.isPresent()) {
            // Utilisateur existe déjà, on le retourne
            return existingUser.get();
        }

        // 2. Créer un nouvel utilisateur
        User newUser = new User();
        newUser.setEmail(googleUser.getEmail());

        // Extraire prénom et nom depuis Google
        if (googleUser.getGiven_name() != null) {
            newUser.setPrenom(googleUser.getGiven_name());
        } else {
            newUser.setPrenom(googleUser.getName()); // Fallback
        }

        if (googleUser.getFamily_name() != null) {
            newUser.setNom(googleUser.getFamily_name());
        } else {
            newUser.setNom(""); // Fallback
        }

        // Password = ID Google (l'utilisateur ne pourra pas se connecter avec email/password)
        newUser.setPassword("GOOGLE_OAUTH_" + googleUser.getSub());

        // Rôle par défaut : CANDIDAT
        newUser.setRole(Role.CANDIDAT);

        newUser.setDateCreation(LocalDate.now());
        newUser.setDateModification(LocalDate.now());
        newUser.setActif(true);

        // 3. Sauvegarder et retourner
        return userRepository.save(newUser);
    }
}