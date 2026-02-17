package com.esmt.cartographiegestionprojet.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    /**
     * 🏭 MÉTHODE 1 : Générer un token JWT
     *
     * Entrées : email, userId, role
     * Sortie : Un token JWT signé
     *
     * Exemple de token généré :
     * "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbWFkb3VAZXNtdC5zbiIsInVzZXJJZCI6IjEyMyIsInJvbGUiOiJDQU5ESURBVCIsImlhdCI6MTcwNzQ5MTYwMCwiZXhwIjoxNzA3NTc4MDAwfQ.signature"
     */
    public String generateToken(String email, UUID userId, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);  // Date d'expiration (maintenant + 24h)

        // Créer la clé de signature à partir de la clé secrète
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        // Construire le token
        return Jwts.builder()
                .setSubject(email)  // Le "sujet" du token = email
                .claim("userId", userId.toString())  // Ajouter userId dans le token
                .claim("role", role)  // Ajouter role dans le token
                .setIssuedAt(now)  // Date de création
                .setExpiration(expiryDate)  // Date d'expiration
                .signWith(key, SignatureAlgorithm.HS512)  // Signer avec la clé
                .compact();  // Convertir en String
    }

    /**
     * 📧 MÉTHODE 2 : Extraire l'email du token
     *
     * Entrée : Token JWT
     * Sortie : Email de l'utilisateur
     */
    public String getEmailFromToken(String token) {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        // Parser le token et extraire les "claims" (données)
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();  // Le "subject" = email
    }

    /**
     * 🆔 MÉTHODE 3 : Extraire l'userId du token
     */
    public UUID getUserIdFromToken(String token) {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String userIdStr = claims.get("userId", String.class);
        return UUID.fromString(userIdStr);
    }

    /**
     * 👤 MÉTHODE 4 : Extraire le role du token
     */
    public String getRoleFromToken(String token) {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("role", String.class);
    }

    /**
     * ✅ MÉTHODE 5 : Valider le token
     *
     * Vérifie que :
     * - Le token n'est pas expiré
     * - La signature est valide
     * - Le format est correct
     */
    public boolean validateToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;  // Token valide
        } catch (MalformedJwtException ex) {
            System.err.println("❌ Token JWT invalide (format incorrect)");
        } catch (ExpiredJwtException ex) {
            System.err.println("❌ Token JWT expiré (dépassé 24h)");
        } catch (UnsupportedJwtException ex) {
            System.err.println("❌ Token JWT non supporté");
        } catch (IllegalArgumentException ex) {
            System.err.println("❌ JWT claims string est vide");
        }
        return false;  // Token invalide
    }
}