package com.Blacher.Blacher.controller;

import java.util.HashMap;
import java.util.Map;


import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        try {
            // Authentification
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );

            // Récupérer les détails de l'utilisateur authentifié
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Extraire le rôle principal de l'utilisateur
            String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("user"); // Par défaut

            // Préparer la réponse
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Connexion réussie");
            response.put("username", username);
            response.put("role", role); // Ajout du rôle
            response.put("status", "success");

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(createErrorResponse("Nom d'utilisateur ou mot de passe incorrect"));
        } catch (DisabledException e) {
            return ResponseEntity.status(403).body(createErrorResponse("L'utilisateur est désactivé"));
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

   