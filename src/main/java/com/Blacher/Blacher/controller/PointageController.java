package com.Blacher.Blacher.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Blacher.Blacher.Repository.EmployeeRepository;
import com.Blacher.Blacher.models.Employee;
import com.Blacher.Blacher.models.Pointage;
import com.Blacher.Blacher.services.PointageService;

@RestController
@RequestMapping("/api/pointages")
public class PointageController {

    @Autowired
    private PointageService pointageService;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Ajouter un pointage pour un employé
    @PostMapping("/ajouter")
    public ResponseEntity<String> ajouterPointage(@RequestBody Pointage pointage) {
        try {
            if (!pointageService.validerUnSeulEtat(pointage)) {
                return ResponseEntity.badRequest()
                        .body("Erreur : Un seul état d'absence ou de présence est autorisé.");
            }
            Pointage savedPointage = pointageService.ajouterPointage(pointage);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Pointage ajouté avec succès pour l'employé : " + savedPointage.getEmployee().getNomEtPrenom());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'ajout du pointage : " + e.getMessage());
        }
    }


    // Récupérer les pointages par date
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Pointage>> getPointagesParDate(@PathVariable String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date);
            List<Pointage> pointages = pointageService.getPointagesParDate(parsedDate);
            return ResponseEntity.ok(pointages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Récupérer le total des états de pointage pour un mois et une année
    @GetMapping("/mensuel/{matr}/{mois}/{annee}")
    public ResponseEntity<Map<String, Double>> getTotalEtatPointageMensuel(
            @PathVariable Long matr,
            @PathVariable int mois,
            @PathVariable int annee) {
        try {
            Map<String, Double> totals = pointageService.getTotalEtatPointageMensuel(matr, mois, annee);
            return ResponseEntity.ok(totals);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Ajouter un pointage pour tous les employés
    @PostMapping("/ajouter_pointage_employés")
    public ResponseEntity<List<Pointage>> ajouterPointagesPourTousLesEmployes() {
        try {
            // Récupérer tous les employés
            List<Employee> employees = employeeRepository.findAll();
            if (employees.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            // Créer un pointage pour chaque employé
            LocalDate today = LocalDate.now();
            List<Pointage> pointages = employees.stream().map(employee -> {
                Pointage pointage = new Pointage();
                pointage.setEmployee(employee);
                pointage.setDate(today);
                pointage.setRet(0.0); // Valeurs par défaut
                pointage.setRetEtAut(0.0);
                pointage.setAbsNj(0.0);
                pointage.setAbsCt(0.0);
                pointage.setAbsLt(0.0);
                pointage.setAbsAut(0.0);
                pointage.setAbsPay(0.0);
                pointage.setAt(0.0);
                pointage.setCgAnn(0.0);
                pointage.setMaPied(0.0);
                pointage.setJf(0.0);
                pointage.setPres(0.0);
                return pointage;
            }).toList();

            // Sauvegarder tous les pointages
            List<Pointage> savedPointages = pointageService.saveAll(pointages);
            return ResponseEntity.ok(savedPointages);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    @PutMapping("/modifierParDate/{date}")
    public ResponseEntity<String> modifierPointageParDate(@PathVariable String date, @RequestBody Pointage pointageDetails) {
        try {
            // Appel au service pour modifier le pointage
            String message = pointageService.modifierPointageParDate(date, pointageDetails);
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pointage non trouvé pour la date donnée.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur inattendue : " + e.getMessage());
        }
    }

 // Endpoint pour récupérer les heures travaillées d'un employé
    @GetMapping("/heures-travaillees/{matr}")
    public ResponseEntity<Double> getHeuresTravaillees(@PathVariable Long matr) {
        try {
            Double heuresTravaillees = pointageService.calculerHeuresTravailleesParMatricule(matr);
            return ResponseEntity.ok(heuresTravaillees);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    //lister tous les pointage sans filtre 
    @GetMapping("/liste")
    public ResponseEntity<List<Pointage>> getTousLesPointages() {
        try {
            List<Pointage> pointages = pointageService.getTousLesPointages();
            return ResponseEntity.ok(pointages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //affcher pointage par date 
    @GetMapping("/par-date/{date}")
    public ResponseEntity<List<Pointage>> getPointagesPourDate(@PathVariable String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date);
            List<Pointage> pointages = pointageService.getPointagesPourDate(parsedDate);
            return ResponseEntity.ok(pointages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    //récupérer tous les pointages entre deux dates.
    @GetMapping("/par-periode/{dateDebut}/{dateFin}")
    public ResponseEntity<List<Pointage>> getPointagesPourPeriode(
            @PathVariable String dateDebut, 
            @PathVariable String dateFin) {
        try {
            LocalDate startDate = LocalDate.parse(dateDebut);
            LocalDate endDate = LocalDate.parse(dateFin);
            List<Pointage> pointages = pointageService.getPointagesPourPeriode(startDate, endDate);
            return ResponseEntity.ok(pointages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    //récupérer tous les pointages d'un employé en fonction de son matricule
    @GetMapping("/par-employe/{matr}")
    public ResponseEntity<List<Pointage>> getPointagesPourEmploye(@PathVariable Long matr) {
        try {
            List<Pointage> pointages = pointageService.getPointagesPourEmploye(matr);
            return ResponseEntity.ok(pointages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




    
}