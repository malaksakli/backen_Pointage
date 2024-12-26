package com.Blacher.Blacher.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Table(name = "pointage")
@Entity
public class Pointage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // L'entité Employee est associée à "matr", et non à "id".
    @ManyToOne
    @JoinColumn(name = "matr", referencedColumnName = "matr", nullable = false)
    private Employee employee;

    private LocalDate date;
    private int mois;
    private int annee;
  

    private Double ret;
    private Double pres;
    private Double retEtAut;
    private Double absNj;
    private Double absCt;
    private Double absLt;
    private Double absAut;
    private Double absPay;
    private Double cgAnn;
    private Double maPied;
    private Double jf;
    private Double at;
    private Info.Service Serv;
    
    @Column(name = "date_pointage")
    private LocalDate datePointage;
    
    @Column(name = "heures_travaillees")
    private Double heuresTravaillees;
    
    public Double getHeuresTravaillees() {
		return heuresTravaillees;
	}
	public void setHeuresTravaillees(Double heuresTravaillees) {
		this.heuresTravaillees = heuresTravaillees;
	}
	
 
    
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public Double getRet() {
        return ret;
    }
    public void setRet(Double ret) {
        this.ret = ret;
    }
    public Double getRetEtAut() {
        return retEtAut;
    }
    public void setRetEtAut(Double retEtAut) {
        this.retEtAut = retEtAut;
    }
    public Double getAbsNj() {
        return absNj;
    }
    public void setAbsNj(Double absNj) {
        this.absNj = absNj;
    }
    public Double getAbsCt() {
        return absCt;
    }
    public void setAbsCt(Double absCt) {
        this.absCt = absCt;
    }
    public Double getAbsLt() {
        return absLt;
    }
    public void setAbsLt(Double absLt) {
        this.absLt = absLt;
    }
    public Double getAbsAut() {
        return absAut;
    }
    public void setAbsAut(Double absAut) {
        this.absAut = absAut;
    }
    public Double getAbsPay() {
        return absPay;
    }
    public void setAbsPay(Double absPay) {
        this.absPay = absPay;
    }
   
    public Double getCgAnn() {
        return cgAnn;
    }
    public void setCgAnn(Double cgAnn) {
        this.cgAnn = cgAnn;
    }
    public Double getMaPied() {
        return maPied;
    }
    public void setMaPied(Double maPied) {
        this.maPied = maPied;
    }
    public Double getJf() {
        return jf;
    }
    public void setJf(Double jf) {
        this.jf = jf;
    }
    
    public Double getAt() {
        return at;
    }
    public void setAt(Double at) {
        this.at = at;
    }

  
	public Double getPres() {
		return pres;
	}
	public void setPres(Double pres) {
		this.pres = pres;
	}
	  public void calculerHeuresTravaillees() {
	        // Initialisation des valeurs par défaut si null pour éviter les NullPointerExceptions
	        Double heuresAutorisees = at == null ? 0 : at;
	        Double retard = ret == null ? 0 : ret;
	        Double retardAutorise = retEtAut == null ? 0 : retEtAut;
	        Double heuresPresent = pres == null ? 0 : pres;

	        // Calcul des heures travaillées
	        this.heuresTravaillees = heuresPresent - (heuresAutorisees + retard + retardAutorise);

	        // Assurez-vous que le résultat ne soit pas négatif
	        if (this.heuresTravaillees < 0) {
	            this.heuresTravaillees = 0.0;
	        }
	    }
	public Info.Service getServ() {
		return Serv;
	}
	public void setServ(Info.Service serv) {
		Serv = serv;
	}
	public int getMois() {
		return mois;
	}
	public void setMois(int mois) {
		this.mois = mois;
	}
	public int getAnnee() {
		return annee;
	}
	public void setAnnee(int annee) {
		this.annee = annee;
	}
	
}