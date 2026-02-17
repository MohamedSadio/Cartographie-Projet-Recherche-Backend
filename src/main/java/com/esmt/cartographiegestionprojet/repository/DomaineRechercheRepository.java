package com.esmt.cartographiegestionprojet.repository;

import com.esmt.cartographiegestionprojet.entity.DomaineRecherche;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DomaineRechercheRepository extends JpaRepository<DomaineRecherche, UUID> {
    Boolean existsByNomDomaine(String nomDomaine);
    Optional<DomaineRecherche> findByNomDomaine(String nomDomaine);
}
