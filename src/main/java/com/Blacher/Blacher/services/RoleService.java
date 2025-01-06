package com.Blacher.Blacher.services;

import org.springframework.stereotype.Service;

import com.Blacher.Blacher.Repository.RoleRepository;
import com.Blacher.Blacher.models.Role;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Ajouter un rôle
    public Role addRole(String name) {
        Role role = new Role();
        role.setName(name);
        return roleRepository.save(role);
    }

    // Récupérer tous les rôles
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Récupérer un rôle par son nom
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Rôle introuvable : " + name));
    }
    public Role createRoleIfNotExist(String roleName) {
        return roleRepository.findByName(roleName).orElseGet(() -> {
            Role role = new Role();
            role.setName(roleName);
            return roleRepository.save(role);
        });
    }
    // Supprimer un rôle
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
