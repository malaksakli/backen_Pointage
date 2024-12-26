package com.Blacher.Blacher.controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Blacher.Blacher.models.Employee;
import com.Blacher.Blacher.services.EmployeeService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Récupérer tous les employés
    @GetMapping("/all")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployee();
    }

    // Supprimer un employé par ID
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        employeeService.supprimerEmployee(id);
        return "Employé supprimé avec succès.";
    }

    
    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody Employee employee) {
        try {
            employeeService.ajoutEmployee(employee);
            return ResponseEntity.ok("Employé ajouté avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de l'ajout de l'employé.");
        }
    }
   

    // Mettre à jour un employé existant
    @PutMapping("/update/{matr}")
    public String update(@PathVariable Long matr, @RequestBody Employee updatedEmployee) {
        employeeService.modifierEmployee(matr, updatedEmployee);
        return "Employé mis à jour avec succès.";
    }
}