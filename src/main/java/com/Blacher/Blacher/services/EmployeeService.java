package com.Blacher.Blacher.services;

import java.util.List;

import com.Blacher.Blacher.models.Employee;

public interface EmployeeService  {
    void ajoutEmployee(Employee e);
    void supprimerEmployee(Long matr);
    List<Employee> getAllEmployee();
    Employee rechercheparmatr(Long matr);
    void modifierEmployee(Long matr, Employee updatedEmployee);    
}
