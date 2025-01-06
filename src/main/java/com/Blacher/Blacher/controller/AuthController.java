package com.Blacher.Blacher.controller;

import java.util.HashMap;
import java.util.Map;


import com.Blacher.Blacher.Repository.UserRepository;
import com.Blacher.Blacher.models.Request.LoginRequest;
import com.Blacher.Blacher.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Injecter le PasswordEncoder

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {

        try {
            // Vérification de l'utilisateur
            User user = userRepository.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("Nom d'utilisateur incorrect"));

            // Vérification du mot de passe (comparaison avec le hachage)
            if (!loginRequest.getPassword().equals(user.getPassword())) {
                return ResponseEntity.badRequest().body(createErrorResponse("Mot de passe incorrect"));
            }

            // Préparer la réponse
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Connexion réussie");
            response.put("username", user.getUsername());
            response.put("role", user.getRoles());
            response.put("status", "success");

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(createErrorResponse("Erreur d'authentification"));
        }
    }

    private Map<String, String> createErrorResponse(String message) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", message);
        errorResponse.put("status", "error");
        return errorResponse;
    }
}

