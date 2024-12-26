package com.Blacher.Blacher.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Blacher.Blacher.Repository.EmployeeRepository;
import com.Blacher.Blacher.models.Employee;
@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
    EmployeeRepository repemp;

    // Ajouter un employé
    @Override
    public void ajoutEmployee(Employee e) {
        repemp.save(e);
    }

    // Supprimer un employé par matricule
    @Override
    public void supprimerEmployee(Long matr) {
        repemp.deleteById(matr);
    }

    // Obtenir tous les employés
    @Override
    public List<Employee> getAllEmployee() {
        return repemp.findAll();
    }

    // Rechercher un employé par matricule
    @Override
    public Employee rechercheparmatr(Long matr) {
        return repemp.findById(matr)
            .orElseThrow(() -> new RuntimeException("Employé non trouvé : " + matr));
    }

    // Modifier un employé
    @Override
    public void modifierEmployee(Long matr, Employee updatedEmployee) {
        Employee existingEmployee = repemp.findById(matr)
            .orElseThrow(() -> new RuntimeException("Employé non trouvé : " + matr));

        // Mettre à jour les champs
        existingEmployee.setNomEtPrenom(updatedEmployee.getNomEtPrenom());
        existingEmployee.setNomEtPrenomPere(updatedEmployee.getNomEtPrenomPere());
        existingEmployee.setNtel(updatedEmployee.getNtel());
        existingEmployee.setCin(updatedEmployee.getCin());
        existingEmployee.setNbEnf(updatedEmployee.getNbEnf());
        existingEmployee.setTaille(updatedEmployee.getTaille());
        existingEmployee.setPointure(updatedEmployee.getPointure());
        existingEmployee.setDateNais(updatedEmployee.getDateNais());
        existingEmployee.setSf(updatedEmployee.getSf());
        existingEmployee.setEtat(updatedEmployee.getEtat());
        existingEmployee.setClasse(updatedEmployee.getClasse());
        existingEmployee.setNomDeChef(updatedEmployee.getNomDeChef());
        existingEmployee.setServ(updatedEmployee.getServ());
        existingEmployee.setTypeDeContrat(updatedEmployee.getTypeDeContrat());
        existingEmployee.setModePay(updatedEmployee.getModePay());
        existingEmployee.setDateRecr(updatedEmployee.getDateRecr());
        existingEmployee.setAnc(updatedEmployee.getAnc());
        existingEmployee.setTest(updatedEmployee.getTest());
        existingEmployee.setDomiciIrreSalaire(updatedEmployee.isDomiciIrreSalaire());
        existingEmployee.setFinContr(updatedEmployee.getFinContr());
        existingEmployee.setalerte(updatedEmployee.getalerte());
        existingEmployee.setremarque(updatedEmployee.getremarque());
        existingEmployee.setBank(updatedEmployee.getBank());
        existingEmployee.setRib(updatedEmployee.getRib());
        existingEmployee.setQuitteLe(updatedEmployee.getQuitteLe());

        // Sauvegarder les modifications
        repemp.save(existingEmployee);
    }
  

}