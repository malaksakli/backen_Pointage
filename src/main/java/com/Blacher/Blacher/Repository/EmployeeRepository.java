package com.Blacher.Blacher.Repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Blacher.Blacher.models.Employee;

public interface EmployeeRepository extends JpaRepository <Employee, Long>{
	Optional<Employee> findByMatr(Long matr);
	List<Employee> findEmployeesByNomEtPrenom(String nom);
	boolean existsByMatr(Long matr);
	@Query("SELECT e FROM Employee e LEFT JOIN FETCH e.pointages")
    List<Employee> findAllWithPointages();
}
