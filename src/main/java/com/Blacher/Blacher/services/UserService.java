package com.Blacher.Blacher.services;


import org.springframework.stereotype.Service;

import com.Blacher.Blacher.Repository.RoleRepository;
import com.Blacher.Blacher.Repository.UserRepository;
import com.Blacher.Blacher.models.Role;
import com.Blacher.Blacher.models.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

	private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    // Rôles fixes
    private final Role user;
    private final Role admin;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        // Initialiser les rôles fixes à partir de la base de données (si ce n'est pas déjà fait)
        this.user = roleRepository.findByName("user")
                .orElseThrow(() -> new RuntimeException("Rôle non trouvé : user"));
        this.admin = roleRepository.findByName("admin")
                .orElseThrow(() -> new RuntimeException("Rôle non trouvé : admin"));
    }

     //Ajouter un utilisateur avec un rôle fixe
    public User registerUser(String username, String password, String roleName) {
        // Par défaut, nous assignons ROLE_USER à l'utilisateur
        Set<Role> roles = new HashSet<>();
        if ("admin".equals(roleName)) {
            roles.add(admin);
        } else {
            roles.add(user);
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);  // Ne pas encoder le mot de passe
        user.setRoles(roles);  // Assigner les rôles fixes
        return userRepository.save(user);  // Enregistrer l'utilisateur
    }
   

    // Récupérer tous les utilisateurs
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Récupérer un utilisateur par ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec l'ID : " + id));
    }

    // Modifier un utilisateur
    public User updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setPassword(updatedUser.getPassword());  // Assurez-vous de mettre à jour le mot de passe aussi

        // On ne modifie pas les rôles ici, car ils sont fixés lors de l'ajout
        return userRepository.save(existingUser);
    }

    // Supprimer un utilisateur
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}