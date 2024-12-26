package com.Blacher.Blacher.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Blacher.Blacher.models.Pointage;

public interface PointageRepository extends JpaRepository<Pointage, Long> {
    // Trouver les pointages par date
    List<Pointage> findByDate(LocalDate date);
    // Trouver les pointages pour un employé entre deux dates
    public List<Pointage> findByEmployee_MatrAndDateBetween(Long matr, LocalDate startDate, LocalDate endDate);
    //recherche un pointage parune date 
    Optional<Pointage> findByDatePointage(LocalDate datePointage);
    
    Optional<Pointage> findFirstByEmployeeMatrOrderByDateDesc(Long matr);
    
    List<Pointage> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    List<Pointage> findByEmployee_Matr(Long matr);
    
    List<Pointage> findByMoisAndAnnee(int mois, int annee);

}