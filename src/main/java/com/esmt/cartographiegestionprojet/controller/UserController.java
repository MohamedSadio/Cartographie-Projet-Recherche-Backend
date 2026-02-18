package com.esmt.cartographiegestionprojet.controller;

import com.esmt.cartographiegestionprojet.dto.UserCreateDTO;
import com.esmt.cartographiegestionprojet.dto.UserDTO;
import com.esmt.cartographiegestionprojet.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.esmt.cartographiegestionprojet.service.UserService;
import com.esmt.cartographiegestionprojet.utils.Role;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users",description = "Gestion des utilisateurs")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * GET /api/users
     * Récupérer tous les utilisateurs
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    /**
     * GET /api/users/{id}
     * Récupérer un utilisateur par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable UUID id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    /**
     * GET /api/users/email/{email}
     * Récupérer un utilisateur par email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserByEmail(@PathVariable String email) {
        UserDTO user = userService.getUserByEmail(email);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    /**
     * GET /api/users/role/{role}
     * Récupérer les utilisateurs par rôle
     */
    @GetMapping("/role/{role}")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getUsersByRole(@PathVariable Role role) {
        List<UserDTO> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    /**
     * POST /api/users
     * Créer un nouvel utilisateur
     */
    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@RequestBody UserCreateDTO dto) {
        UserDTO createdUser = userService.createUser(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Utilisateur créé avec succès", createdUser));
    }

    /**
     * PUT /api/users/{id}
     * Mettre à jour un utilisateur
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(
            @PathVariable UUID id,
            @RequestBody UserCreateDTO dto) {
        UserDTO updatedUser = userService.updateUser(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Utilisateur mis à jour avec succès", updatedUser));
    }

    /**
     * PUT /api/users/{id}/role
     * Changer le rôle d'un utilisateur (ADMIN uniquement)
     */
    @PutMapping("/{id}/role")
    public ResponseEntity<ApiResponse<UserDTO>> changeUserRole(
            @PathVariable UUID id,
            @RequestParam Role newRole) {
        UserDTO updatedUser = userService.changeUserRole(id, newRole);
        return ResponseEntity.ok(ApiResponse.success("Rôle modifié avec succès", updatedUser));
    }

    /**
     * PUT /api/users/{id}/toggle-status
     * Activer/Désactiver un utilisateur
     */
    @PutMapping("/{id}/toggle-status")
    public ResponseEntity<ApiResponse<UserDTO>> toggleUserStatus(@PathVariable UUID id) {
        UserDTO updatedUser = userService.toggleUserStatus(id);
        return ResponseEntity.ok(ApiResponse.success("Statut modifié avec succès", updatedUser));
    }

    /**
     * DELETE /api/users/{id}
     * Supprimer un utilisateur (soft delete)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable UUID id) {
        userService.softDelete(id);
        return ResponseEntity.ok(ApiResponse.success("Utilisateur supprimé avec succès", null));
    }
}