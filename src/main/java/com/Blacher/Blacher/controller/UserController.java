package com.Blacher.Blacher.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Blacher.Blacher.models.User;
import com.Blacher.Blacher.services.UserService;


@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired

    private  UserService userService;



    // Ajouter un utilisateur avec un rôle fixe
   @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User user, @RequestParam String roleName) {
      try {
            User savedUser = userService.registerUser(user.getUsername(), user.getPassword(), roleName);
            return ResponseEntity.ok("Utilisateur ajouté avec succès : " + savedUser.getUsername());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }
 


    // Afficher tous les utilisateurs
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    // Afficher un utilisateur spécifique
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    // Modifier un utilisateur
    @PutMapping("/update/{id}/{role}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser,@PathVariable String role) {
        try {
            User updated = userService.updateUser(id, updatedUser, role);
            return ResponseEntity.ok("Utilisateur mis à jour : " + updated.getUsername());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    // Supprimer un utilisateur
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("Utilisateur supprimé avec succès.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }
}
