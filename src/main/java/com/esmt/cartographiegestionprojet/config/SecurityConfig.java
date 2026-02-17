package com.esmt.cartographiegestionprojet.config;

import com.esmt.cartographiegestionprojet.security.CustomUserDetailsService;
import com.esmt.cartographiegestionprojet.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter, CustomUserDetailsService userDetailsService) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        // ════════════════════════════════════════════════
                        // ENDPOINTS PUBLICS
                        // ════════════════════════════════════════════════
                        .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
                        .requestMatchers("/api/health").permitAll()

                        // Google OAuth
                        .requestMatchers("/api/auth/google/**").permitAll()
                        .requestMatchers("/oauth2/**").permitAll()
                        .requestMatchers("/login/oauth2/**").permitAll()

                        // ════════════════════════════════════════════════
                        // ENDPOINTS ADMIN UNIQUEMENT
                        // ════════════════════════════════════════════════
                        .requestMatchers("/api/users/**").hasRole("ADMIN")

                        // Domaines : Lecture publique, création/modification ADMIN
                        .requestMatchers("/api/domaines").permitAll()  // GET domaines (public)
                        .requestMatchers("/api/domaines/{id}").permitAll()  // GET domaine par ID
                        .requestMatchers("/api/domaines/nom/**").permitAll()  // GET par nom
                        .requestMatchers("/api/domaines/**").hasRole("ADMIN")  // POST/PUT/DELETE

                        // ════════════════════════════════════════════════
                        // ENDPOINTS GESTIONNAIRE + ADMIN
                        // ════════════════════════════════════════════════
                        .requestMatchers("/api/statistiques/**").hasAnyRole("GESTIONNAIRE", "ADMIN")

                        // ════════════════════════════════════════════════
                        // ENDPOINTS PARTICIPATIONS
                        // ════════════════════════════════════════════════

                        // GET : Tous les utilisateurs authentifiés peuvent consulter
                        .requestMatchers("/api/participations/projet/**").authenticated()
                        .requestMatchers("/api/participations/user/**").authenticated()

                        // POST/DELETE : Seulement GESTIONNAIRE et ADMIN
                        .requestMatchers("/api/participations").hasAnyRole("GESTIONNAIRE", "ADMIN")
                        .requestMatchers("/api/participations/**").hasAnyRole("GESTIONNAIRE", "ADMIN")

                        // ════════════════════════════════════════════════
                        // ENDPOINTS PROJETS : Tous authentifiés
                        // ════════════════════════════════════════════════
                        .requestMatchers("/api/projets/**").authenticated()

                        // Tout le reste nécessite authentification
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/api/auth/google/callback", true)
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:8080",
                "http://localhost:3000",
                "http://localhost:4200",
                "http://localhost:5173"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    @SuppressWarnings("deprecation")
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}