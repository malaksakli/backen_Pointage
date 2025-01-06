package com.Blacher.Blacher.Configuration;

import com.Blacher.Blacher.services.RoleService;
import com.Blacher.Blacher.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer implements CommandLineRunner {
    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public RoleInitializer(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        roleService.createRoleIfNotExist("ROLE_ADMIN");
        roleService.createRoleIfNotExist("ROLE_USER");

        // Créer l'utilisateur administrateur
        userService.createAdminIfNotExist("admin", "password123"); // Changez le mot de passe pour un usage réel.
    }
}
