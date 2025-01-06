package com.Blacher.Blacher.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.Blacher.Blacher.Repository.RoleRepository;
import com.Blacher.Blacher.Repository.UserRepository;
import com.Blacher.Blacher.models.Role;
import com.Blacher.Blacher.models.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private  RoleService roleService;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, RoleRepository roleRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Register a new user with a fixed role.
     *
     * @param username the username
     * @param password the password (will be encoded)
     * @param roleName the role name (admin or user)
     * @return the saved user
     */
    public User registerUser(String username, String password, String roleName) {
        // Vérifier si l'utilisateur existe déjà
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Nom d'utilisateur déjà pris");
        }

        // Récupérer le rôle existant depuis la base de données
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Le rôle spécifié n'existe pas : " + roleName));

        // Ajouter le rôle à l'utilisateur
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        // Créer l'utilisateur avec le rôle
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(roles);

        // Sauvegarder l'utilisateur
        return userRepository.save(user);
    }


    /**
     * Retrieve all users.
     *
     * @return a list of all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieve a user by ID.
     *
     * @param id the user ID
     * @return the user with the given ID
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }
    public User createAdminIfNotExist(String username, String password) {
        return userRepository.findByUsername(username).orElseGet(() -> {
            User admin = new User();
            admin.setUsername(username);
            admin.setPassword(password);
            admin.getRoles().add(roleService.createRoleIfNotExist("ROLE_ADMIN"));
            return userRepository.save(admin);
        });
    }
    /**
     * Update an existing user's details.
     *
     * @param id the user ID
     * @param updatedUser the updated user details
     * @return the updated user
     */
    public User updateUser(Long id, User updatedUser,String role) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setPassword(updatedUser.getPassword());  // TODO: Encode the password


        Role newRole = roleRepository.findByName(role)
                .orElseThrow(() -> new RuntimeException("Role not found with name: " + role));

        // Replace the user's roles with the new role
        Set<Role> newRoles = new HashSet<>();
        newRoles.add(newRole);
        existingUser.setRoles(newRoles);

        // Roles are not modified here, as they are fixed during registration
        return userRepository.save(existingUser);
    }

    /**
     * Delete a user by ID.
     *
     * @param id the user ID
     */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }
}
