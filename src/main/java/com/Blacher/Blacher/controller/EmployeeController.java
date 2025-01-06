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

import org.springframework.http.ResponseEntity;
@CrossOrigin(origins = "*")
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
    @GetMapping("/allavecpointage")
    public List<Employee> getAllEmployeesavecpointage() {
        return employeeService.getallavecpointage();
    }
    @GetMapping("/{id}")
    public Employee getbyid(@PathVariable Long id) {
        return employeeService.rechercheparmatr(id);
    }
    @GetMapping("/bynom/{nom}")
    public List<Employee> getbynom(@PathVariable String nom) {
        return employeeService.rechercheparnom(nom);
    }
    // Supprimer un employé par ID
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        employeeService.supprimerEmployee(id);
        return "Employé supprimé avec succès.";
    }

    
    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
        return employeeService.ajoutEmployee(employee);
    }
   

    // Mettre à jour un employé existant
    @PutMapping("/update/{matr}")
    public String update(@PathVariable Long matr, @RequestBody Employee updatedEmployee) {
        employeeService.modifierEmployee(matr, updatedEmployee);
        return "Employé mis à jour avec succès.";
    }
    @GetMapping("/check-matr/{matr}")
    public boolean checkMatricule(@PathVariable Long matr) {
        return employeeService.existsByMatr(matr); // Assurez-vous d'utiliser 'matr'
    }
}
