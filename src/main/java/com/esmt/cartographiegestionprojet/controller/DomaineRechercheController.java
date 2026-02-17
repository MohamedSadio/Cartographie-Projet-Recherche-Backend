package com.esmt.cartographiegestionprojet.controller;

import com.esmt.cartographiegestionprojet.dto.DomaineRechercheDTO;
import com.esmt.cartographiegestionprojet.dto.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.esmt.cartographiegestionprojet.service.DomaineRechercheService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/domaines")
public class DomaineRechercheController {

    @Autowired
    private DomaineRechercheService domaineService;

    /**
     * GET /api/domaines
     * Récupérer tous les domaines de recherche
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<DomaineRechercheDTO>>> getAllDomaines() {
        List<DomaineRechercheDTO> domaines = domaineService.getAllDomaines();
        return ResponseEntity.ok(ApiResponse.success(domaines));
    }

    /**
     * GET /api/domaines/{id}
     * Récupérer un domaine par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DomaineRechercheDTO>> getDomaineById(@PathVariable UUID id) {
        DomaineRechercheDTO domaine = domaineService.getDomaineById(id);
        return ResponseEntity.ok(ApiResponse.success(domaine));
    }

    /**
     * GET /api/domaines/nom/{nom}
     * Récupérer un domaine par nom
     */
    @GetMapping("/nom/{nom}")
    public ResponseEntity<ApiResponse<DomaineRechercheDTO>> getDomaineByNom(@PathVariable String nom) {
        DomaineRechercheDTO domaine = domaineService.getDomaineByNom(nom);
        return ResponseEntity.ok(ApiResponse.success(domaine));
    }

    /**
     * POST /api/domaines
     * Créer un nouveau domaine (ADMIN uniquement)
     */
    @PostMapping
    public ResponseEntity<ApiResponse<DomaineRechercheDTO>> createDomaine(
            @RequestBody DomaineRechercheDTO dto) {
        DomaineRechercheDTO createdDomaine = domaineService.createDomaine(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Domaine créé avec succès", createdDomaine));
    }

    /**
     * PUT /api/domaines/{id}
     * Mettre à jour un domaine (ADMIN uniquement)
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DomaineRechercheDTO>> updateDomaine(
            @PathVariable UUID id,
            @RequestBody DomaineRechercheDTO dto) {
        DomaineRechercheDTO updatedDomaine = domaineService.updateDomaine(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Domaine mis à jour avec succès", updatedDomaine));
    }

    /**
     * DELETE /api/domaines/{id}
     * Supprimer un domaine (ADMIN uniquement)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDomaine(@PathVariable UUID id) {
        domaineService.deleteDomaine(id);
        return ResponseEntity.ok(ApiResponse.success("Domaine supprimé avec succès", null));
    }
}