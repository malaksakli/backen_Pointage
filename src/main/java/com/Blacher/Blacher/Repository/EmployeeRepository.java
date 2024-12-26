package com.Blacher.Blacher.Repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Blacher.Blacher.models.Employee;

public interface EmployeeRepository extends JpaRepository <Employee, Long>{
	Optional<Employee> findByMatr(Long matr);
}
