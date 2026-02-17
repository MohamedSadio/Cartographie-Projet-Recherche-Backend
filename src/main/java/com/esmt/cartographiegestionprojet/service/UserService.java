package com.esmt.cartographiegestionprojet.service;

import com.esmt.cartographiegestionprojet.dto.UserCreateDTO;
import com.esmt.cartographiegestionprojet.dto.UserDTO;
import com.esmt.cartographiegestionprojet.entity.User;
import com.esmt.cartographiegestionprojet.exception.BusinessException;
import com.esmt.cartographiegestionprojet.exception.NotFoundException;
import com.esmt.cartographiegestionprojet.mapper.UserMapper;
import com.esmt.cartographiegestionprojet.repository.UserRepository;
import com.esmt.cartographiegestionprojet.utils.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    /**
     * Créer un nouvel utilisateur
     */
    @Transactional
    public UserDTO createUser(UserCreateDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Un utilisateur avec cet email existe déjà");
        }

        validateUserData(dto);

        User user = userMapper.toEntity(dto);
        user.setPassword(dto.getPassword());

        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    /**
     * Récupérer un utilisateur par ID
     */
    public UserDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utilisateur non trouvé avec l'ID : " + id));
        return userMapper.toDTO(user);
    }

    /**
     * Récupérer tous les utilisateurs
     */
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupérer les utilisateurs par rôle
     */
    public List<UserDTO> getUsersByRole(Role role) {
        return userRepository.findAllByRole(role).stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupérer un utilisateur par email
     */
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Utilisateur non trouvé avec l'email : " + email));
        return userMapper.toDTO(user);
    }

    /**
     * Mettre à jour un utilisateur
     */
    @Transactional
    public UserDTO updateUser(UUID id, UserCreateDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utilisateur non trouvé avec l'ID : " + id));

        // Validation : Email unique (sauf si c'est le même)
        if (!user.getEmail().equals(dto.getEmail()) && userRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Un utilisateur avec cet email existe déjà");
        }

        userMapper.updateEntityFromDTO(user, dto);

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(dto.getPassword());
        }

        User updatedUser = userRepository.save(user);
        return userMapper.toDTO(updatedUser);
    }

    /**
     * Changer le rôle d'un utilisateur (ADMIN uniquement)
     */
    @Transactional
    public UserDTO changeUserRole(UUID userId, Role newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Utilisateur non trouvé"));

        user.setRole(newRole);
        user.setDateModification(LocalDate.now());

        User updatedUser = userRepository.save(user);
        return userMapper.toDTO(updatedUser);
    }

    /**
     * Activer/Désactiver un utilisateur
     */
    @Transactional
    public UserDTO toggleUserStatus(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Utilisateur non trouvé"));

        user.setActif(!user.isActif());
        user.setDateModification(LocalDate.now());

        User updatedUser = userRepository.save(user);
        return userMapper.toDTO(updatedUser);
    }

    /**
     * Supprimer un utilisateur (soft delete)
     */
    @Transactional
    public void softDelete(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Utilisateur non trouvé"));

        user.setActif(false);
        user.setDateModification(LocalDate.now());
        userRepository.save(user);
    }

    /**
     * Validation des données utilisateur
     */
    private void validateUserData(UserCreateDTO dto) {
        if (dto.getNom() == null || dto.getNom().trim().isEmpty()) {
            throw new BusinessException("Le nom est obligatoire");
        }
        if (dto.getPrenom() == null || dto.getPrenom().trim().isEmpty()) {
            throw new BusinessException("Le prénom est obligatoire");
        }
        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new BusinessException("L'email est obligatoire");
        }
        if (dto.getPassword() == null || dto.getPassword().length() < 6) {
            throw new BusinessException("Le mot de passe doit contenir au moins 6 caractères");
        }
    }


    /**
     * Récupérer l'entité User (pour usage interne)
     */
    public User getUserEntityById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utilisateur non trouvé"));
    }
}