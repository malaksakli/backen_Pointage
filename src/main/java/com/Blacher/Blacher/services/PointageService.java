package com.Blacher.Blacher.services;

import java.time.LocalDate;
import java.util.*;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;


import com.Blacher.Blacher.Repository.EmployeeRepository;
import com.Blacher.Blacher.models.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Blacher.Blacher.Repository.PointageRepository;
import com.Blacher.Blacher.models.Employee;
import com.Blacher.Blacher.models.Pointage;

@Service
public class PointageService {

    @Autowired
    private PointageRepository pointageRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    public Pointage ajouterPointage(Pointage pointage,Long Matr) {
        Employee employee = employeeRepository.findByMatr(Matr).orElse(null);
        pointage.setEmployee(employee);
    	pointage.calculerHeuresTravaillees();
        return pointageRepository.save(pointage);
    }

    public List<Pointage> getPointagesParDate(LocalDate date) {
        return pointageRepository.findByDate(date);
    }

    public List<Pointage> getPointagesMensuels(Long matr, LocalDate mois, LocalDate annee) {
        return pointageRepository.findByEmployee_MatrAndDateBetween(matr, mois, annee);
    }

    public List<Pointage> saveAll(List<Pointage> pointages) {
        return pointageRepository.saveAll(pointages);
    }
    public List<Pointage> ajouterPointagesPourTous(List<Employee> employees) {
        LocalDate today = LocalDate.now();
        List<Pointage> pointages = employees.stream().map(employee -> {
            Pointage pointage = new Pointage();
            pointage.setEmployee(employee);
//            pointage.setDatePointage(today);
            return pointage;
        }).toList();

        return pointageRepository.saveAll(pointages);
    }
    //dans cas d'absence on peux pas mettre une autre etat 
    public boolean validerUnSeulEtat(Pointage pointage) {
        int count = 0;
        if (pointage.getHeuresTravaillees() != null && pointage.getHeuresTravaillees() > 0) count++;
        if (pointage.getAbsNj() != null && pointage.getAbsNj() > 0) count++;
        if (pointage.getAbsCt() != null && pointage.getAbsCt() > 0) count++;
        if (pointage.getAbsLt() != null && pointage.getAbsLt() > 0) count++;
        if (pointage.getAbsAut() != null && pointage.getAbsAut() > 0) count++;
        if (pointage.getAbsPay() != null && pointage.getAbsPay() > 0) count++;
        if (pointage.getAt() != null && pointage.getAt() > 0) count++;
        return count <= 1;
    }

    // Récupérer les pointages pour un mois et une année
    public List<Pointage> getPointagesMensuels(Long matr, int mois, int annee) {
        LocalDate debutMois = LocalDate.of(annee, mois, 1);
        LocalDate finMois = debutMois.withDayOfMonth(debutMois.lengthOfMonth());
        return pointageRepository.findByEmployee_MatrAndDateBetween(matr, debutMois, finMois);
    }
    public List<Pointage> getPointagesbymatr(Long matr) {
     
        return pointageRepository.findByEmployee_Matr(matr);
    }
    // Calculer le total de chaque état pour le mois et l'année donnés
      public Map<String, Double> getTotalEtatPointageMensuel(Long matr, int mois, int annee) {
        List<Pointage> pointagesMensuels = getPointagesMensuels(matr, mois, annee);

        // Calculer les totaux de chaque état (Présent, Absent, Autorisation, etc.)
        double totalPresent = pointagesMensuels.stream().mapToDouble(p -> p.getPres() == null ? 0 : p.getPres()).sum();
        double totalAbsentNonJustifie = pointagesMensuels.stream().mapToDouble(p -> p.getAbsNj() == null ? 0 : p.getAbsNj()).sum();
        double totalAbsentCourtTerme = pointagesMensuels.stream().mapToDouble(p -> p.getAbsCt() == null ? 0 : p.getAbsCt()).sum();
        double totalAbsentLongTerme = pointagesMensuels.stream().mapToDouble(p -> p.getAbsLt() == null ? 0 : p.getAbsLt()).sum();
        double totalAbsentAut = pointagesMensuels.stream().mapToDouble(p -> p.getAbsAut() == null ? 0 : p.getAbsAut()).sum();
        double totalAbsentPay = pointagesMensuels.stream().mapToDouble(p -> p.getAbsPay() == null ? 0 : p.getAbsPay()).sum();
        double totalAutorisation = pointagesMensuels.stream().mapToDouble(p -> p.getAt() == null ? 0 : p.getAt()).sum();
        double totalRetard = pointagesMensuels.stream().mapToDouble(p -> p.getRet() == null ? 0 : p.getRet()).sum();
        double totalRetardAutorise = pointagesMensuels.stream().mapToDouble(p -> p.getRetEtAut() == null ? 0 : p.getRetEtAut()).sum();
        double totalConge = pointagesMensuels.stream().mapToDouble(p -> p.getCgAnn() == null ? 0 : p.getCgAnn()).sum();
        double totalMiseAPied = pointagesMensuels.stream().mapToDouble(p -> p.getMaPied() == null ? 0 : p.getMaPied()).sum();
        double totalJourFerie = pointagesMensuels.stream().mapToDouble(p -> p.getJf() == null ? 0 : p.getJf()).sum();
          double doubleValue = mois; // Implicit conversion from int to double
        // Retourner un Map avec le total de chaque état
        return Map.ofEntries(
        	    Map.entry("Present", totalPresent),
        	    Map.entry("Absent_Non_Justifie", totalAbsentNonJustifie),
        	    Map.entry("Absent_Court_Terme", totalAbsentCourtTerme),
        	    Map.entry("Absent_Long_Terme", totalAbsentLongTerme),
        	    Map.entry("Absent_AUT", totalAbsentAut),
        	    Map.entry("Absent_PAY", totalAbsentPay),
        	    Map.entry("Autorisation", totalAutorisation),
        	    Map.entry("Retard", totalRetard),
        	    Map.entry("Retard_Autorise", totalRetardAutorise),
        	    Map.entry("Conge", totalConge),
        	    Map.entry("Mise_a_Pied", totalMiseAPied),
        	    Map.entry("Jour_Ferie", totalJourFerie),
                Map.entry("mois", doubleValue)
        	);
    }


    public List<Map<String, Object>> getRecapitulatifParServiceEtMois(int annee, String etat) {
        // Fetch all pointages for the given year
        List<Pointage> pointages = pointageRepository.findByAnnee(annee);

        // Determine the attribute to sum based on the state (etat)
        ToDoubleFunction<Pointage> attributeToSum;
        switch (etat.toLowerCase()) {
            case "present":
                attributeToSum = p -> p.getPres() == null ? 0 : p.getPres();
                break;
            case "retard":
                attributeToSum = p -> p.getRet() == null ? 0 : p.getRet();
                break;
            case "autorisation":
                attributeToSum = p -> p.getAt() == null ? 0 : p.getAt();
                break;
            case "absent_non_justifie":
                attributeToSum = p -> p.getAbsNj() == null ? 0 : p.getAbsNj();
                break;
            case "absent_court_terme":
                attributeToSum = p -> p.getAbsCt() == null ? 0 : p.getAbsCt();
                break;
            case "absent_long_terme":
                attributeToSum = p -> p.getAbsLt() == null ? 0 : p.getAbsLt();
                break;
            case "absent_aut":
                attributeToSum = p -> p.getAbsAut() == null ? 0 : p.getAbsAut();
                break;
            case "absent_pay":
                attributeToSum = p -> p.getAbsPay() == null ? 0 : p.getAbsPay();
                break;
            default:
                throw new IllegalArgumentException("État non reconnu: " + etat);
        }

        // Group and compute totals
        Map<Info.Service, Map<Integer, Double>> groupedData = pointages.stream()
                .collect(Collectors.groupingBy(
                        Pointage::getServ, // Group by service
                        Collectors.groupingBy(
                                Pointage::getMois, // Group by month
                                Collectors.summingDouble(attributeToSum) // Sum the relevant attribute
                        )
                ));

        // Initialize the result with all services and months set to 0
        List<Map<String, Object>> resultat = new ArrayList<>();
        for (Info.Service service : Info.Service.values()) {
            Map<String, Object> ligne = new HashMap<>();
            ligne.put("Service", service.name());
            for (int mois = 1; mois <= 12; mois++) {
                ligne.put(getNomMois(mois), groupedData.getOrDefault(service, new HashMap<>()).getOrDefault(mois, 0.0));
            }
            resultat.add(ligne);
        }


        return resultat;
    }

    // Utility method to convert month numbers to names
    private String getNomMois(int mois) {
        String[] moisNoms = {
                "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
                "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
        };
        return mois >= 1 && mois <= 12 ? moisNoms[mois - 1] : "Inconnu";
    }

    public Pointage getbyid(Long id) {
        return  pointageRepository.findById(id).get();
    }
    public Pointage modifier(Long id,Pointage pointage) {
        Pointage pointage1=  pointageRepository.findById(id).get();
        pointage1.setDatePointage(pointage.getDatePointage());
        pointage1.setDate(pointage.getDate());
        pointage1.setAbsAut(pointage.getAbsAut());
        pointage1.setAbsCt(pointage.getAbsCt());
        pointage1.setAbsLt(pointage.getAbsLt());
        pointage1.setAbsNj(pointage.getAbsNj());
        pointage1.setAbsPay(pointage.getAbsPay());
        pointage1.setAnnee(pointage.getAnnee());
        pointage1.setMois(pointage.getMois());
        pointage1.setAt(pointage.getAt());
        pointage1.setCgAnn(pointage.getCgAnn());
        pointage1.setHeuresTravaillees(pointage.getHeuresTravaillees());
        pointage1.setJf(pointage.getJf());
        pointage1.setMaPied(pointage.getMaPied());
        pointage1.setPres(pointage.getPres());
        pointage1.setRet(pointage.getRet());
        pointage1.setRetEtAut(pointage.getRetEtAut());
        pointage1.setServ(pointage.getServ());
        return pointageRepository.save(pointage1);
    }
        public String modifierPointageParDate(String date, Pointage pointageDetails) {
          // Conversion de la date en LocalDate
          LocalDate datePointage = LocalDate.parse(date);

          // Rechercher le pointage existant par date
          Pointage pointage = pointageRepository.findByDatePointage(datePointage)
                  .orElseThrow(() -> new RuntimeException("Pointage introuvable pour la date : " + datePointage));

          // Mise à jour des détails
          pointage.setPres(pointageDetails.getPres());
          pointage.setAbsNj(pointageDetails.getAbsNj());
          pointage.calculerHeuresTravaillees(); // Recalcul automatique

          // Enregistrer les modifications
          pointageRepository.save(pointage);

          return "Pointage mis à jour avec succès pour la date : " + datePointage;
      }

      // Méthode pour calculer les heures travaillées d'un employé par matricule
      public Double calculerHeuresTravailleesParMatricule(Long matricule) {
          // Chercher le dernier pointage par matricule
          Optional<Pointage> pointageOpt = pointageRepository.findFirstByEmployeeMatrOrderByDateDesc(matricule);

          if (pointageOpt.isPresent()) {
              Pointage pointage = pointageOpt.get();

              // Calculer les heures travaillées
              pointage.calculerHeuresTravaillees();

              // Retourner les heures travaillées
              return pointage.getHeuresTravaillees();
          } else {
              // Aucun pointage trouvé
              throw new RuntimeException("Aucun pointage trouvé pour la matricule : " + matricule);
          }
      }
      //lister tous pointage sans filtre 
      public List<Pointage> getTousLesPointages() {
    	    return pointageRepository.findAll();
    	}
      //affcher pointage par date 
      public List<Pointage> getPointagesPourDate(LocalDate date) {
    	    return pointageRepository.findByDate(date);
    	}
      public List<Pointage> getPointagesPourPeriode(LocalDate dateDebut, LocalDate dateFin) {
    	    return pointageRepository.findByDateBetween(dateDebut, dateFin);
    	}
      public List<Pointage> getPointagesPourEmploye(Long matr) {
    	    return pointageRepository.findByEmployee_Matr(matr);
    	}
      //recap par serv
      public Map<String, Map<String, Double>> getTotalEtatPointageAnneeParService(int annee) {
    	    // Crée une carte pour stocker les résultats
    	    Map<String, Map<String, Double>> resultats = new HashMap<>();

    	    // Pour chaque mois de l'année
    	    for (int mois = 1; mois <= 12; mois++) {
    	        // Récupère les pointages pour le mois et l'année
    	        List<Pointage> pointagesMensuels = getPointagesParMoisAnnee(mois, annee);
    	        
    	        // Récupérer la liste des services uniques
    	        Set<com.Blacher.Blacher.models.Info.Service> services = pointagesMensuels.stream()
    	                .map(Pointage::getServ)  // Récupère le service de chaque pointage
    	                .collect(Collectors.toSet()); // Collecte les services uniques

    	        // Pour chaque service, calculer les totaux
    	        for (com.Blacher.Blacher.models.Info.Service service : services) {
    	            // Filtrer les pointages pour ce service et ce mois
    	            List<Pointage> pointagesService = pointagesMensuels.stream()
    	                    .filter(p -> p.getServ().equals(service))
    	                    .collect(Collectors.toList());

    	            // Calcul des totaux pour ce service et ce mois
    	            double totalPresent = pointagesService.stream().mapToDouble(p -> p.getPres() == null ? 0 : p.getPres()).sum();
    	            double totalAbsentNonJustifie = pointagesService.stream().mapToDouble(p -> p.getAbsNj() == null ? 0 : p.getAbsNj()).sum();
    	            double totalAbsentCourtTerme = pointagesService.stream().mapToDouble(p -> p.getAbsCt() == null ? 0 : p.getAbsCt()).sum();
    	            double totalAbsentLongTerme = pointagesService.stream().mapToDouble(p -> p.getAbsLt() == null ? 0 : p.getAbsLt()).sum();
    	            double totalAbsentAut = pointagesService.stream().mapToDouble(p -> p.getAbsAut() == null ? 0 : p.getAbsAut()).sum();
    	            double totalAbsentPay = pointagesService.stream().mapToDouble(p -> p.getAbsPay() == null ? 0 : p.getAbsPay()).sum();
    	            double totalRetard = pointagesService.stream().mapToDouble(p -> p.getRet() == null ? 0 : p.getRet()).sum();
    	            double totalRetardAutorise = pointagesService.stream().mapToDouble(p -> p.getRetEtAut() == null ? 0 : p.getRetEtAut()).sum();
    	            double totalCongesAnnuel = pointagesService.stream().mapToDouble(p -> p.getCgAnn() == null ? 0 : p.getCgAnn()).sum();
    	            double totalMiseAPied = pointagesService.stream().mapToDouble(p -> p.getMaPied() == null ? 0 : p.getMaPied()).sum();
    	            double totalJourFerie = pointagesService.stream().mapToDouble(p -> p.getJf() == null ? 0 : p.getJf()).sum();
    	            double totalAutorisation = pointagesService.stream().mapToDouble(p -> p.getAt() == null ? 0 : p.getAt()).sum();

    	            // Création de la carte des totaux pour ce service et ce mois
    	            Map<String, Double> totauxMois = new HashMap<>();
    	            totauxMois.put("Présent", totalPresent);
    	            totauxMois.put("Absent Non Justifié", totalAbsentNonJustifie);
    	            totauxMois.put("Absent Court Terme", totalAbsentCourtTerme);
    	            totauxMois.put("Absent Long Terme", totalAbsentLongTerme);
    	            totauxMois.put("Absent AUT", totalAbsentAut);
    	            totauxMois.put("Absent PAY", totalAbsentPay);
    	            totauxMois.put("Retard", totalRetard);
    	            totauxMois.put("Retard Autorisé", totalRetardAutorise);
    	            totauxMois.put("Congé Annuel", totalCongesAnnuel);
    	            totauxMois.put("Mise à Pied", totalMiseAPied);
    	            totauxMois.put("Jour Férié", totalJourFerie);
    	            totauxMois.put("Autorisation", totalAutorisation);

    	            // Ajoute le résultat pour ce service et ce mois dans la carte finale
    	            resultats.put(service + " - Mois " + mois, totauxMois);
    	        }
    	    }

    	    return resultats;
    	}

      public List<Pointage> getPointagesParMoisAnnee(int mois, int annee) {
    	    // Implémentation de la récupération des pointages pour le mois et l'année
    	    return pointageRepository.findByMoisAndAnnee(mois, annee); // Exemple d'appel à un repository
    	}




}
