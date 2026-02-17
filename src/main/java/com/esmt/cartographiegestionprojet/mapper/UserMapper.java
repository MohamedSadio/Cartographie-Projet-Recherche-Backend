package com.esmt.cartographiegestionprojet.mapper;

import com.esmt.cartographiegestionprojet.dto.UserCreateDTO;
import com.esmt.cartographiegestionprojet.dto.UserDTO;
import com.esmt.cartographiegestionprojet.entity.User;
import org.springframework.stereotype.Component;
import com.esmt.cartographiegestionprojet.utils.Role;

import java.time.LocalDate;

@Component
public class UserMapper {

    /**
     * Convertir User entity → UserDTO
     */
    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setNom(user.getNom());
        dto.setPrenom(user.getPrenom());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setDateCreation(user.getDateCreation());
        dto.setActif(user.isActif());

        return dto;
    }

    /**
     * Convertir UserCreateDTO → User entity
     */
    public User toEntity(UserCreateDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setNom(dto.getNom());
        user.setPrenom(dto.getPrenom());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole() != null ? dto.getRole() : Role.CANDIDAT);
        user.setDateCreation(LocalDate.now());
        user.setDateModification(LocalDate.now());
        user.setActif(true);

        return user;
    }

    /**
     * Mettre à jour une entité User depuis un DTO
     */
    public void updateEntityFromDTO(User user, UserCreateDTO dto) {
        if (user == null || dto == null) {
            return;
        }

        user.setNom(dto.getNom());
        user.setPrenom(dto.getPrenom());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(dto.getPassword());
        }

        user.setDateModification(LocalDate.now());
    }

    /**
     * Nom complet de l'utilisateur
     */
    public String getFullName(User user) {
        if (user == null) {
            return "";
        }
        return user.getPrenom() + " " + user.getNom();
    }
}