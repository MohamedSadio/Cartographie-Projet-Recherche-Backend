package com.esmt.cartographiegestionprojet.repository;

import com.esmt.cartographiegestionprojet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.esmt.cartographiegestionprojet.utils.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Boolean existsByEmail(String email);
    User findByRole(Role role);
    List<User> findAllByRole(Role role);
    Optional<User> findByEmail(String email);
}
