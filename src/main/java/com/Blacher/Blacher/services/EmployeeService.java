package com.Blacher.Blacher.services;

import java.util.List;

import com.Blacher.Blacher.models.Employee;
import org.springframework.http.ResponseEntity;

public interface EmployeeService  {
    ResponseEntity<?> ajoutEmployee(Employee e);
    void supprimerEmployee(Long matr);
    List<Employee> getAllEmployee();
    Employee rechercheparmatr(Long matr);
    List<Employee> rechercheparnom(String nom);
    void modifierEmployee(Long matr, Employee updatedEmployee); 
    public List<Employee> getallavecpointage() ;
    boolean existsByMatr(Long matr);
}
