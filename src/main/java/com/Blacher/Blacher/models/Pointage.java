package com.Blacher.Blacher.models;

import java.time.LocalDate;

import com.Blacher.Blacher.models.Info.Service;
import com.Blacher.Blacher.models.Info.State;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter

public class Pointage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @Enumerated(EnumType.STRING)
    private Info.Service Serv;
    @Enumerated(EnumType.STRING)
    private Info.State etat;
    @Column(name = "date_pointage")
    private LocalDate datePointage;
    @ManyToOne
    @JoinColumn(name = "matr", referencedColumnName = "matr", nullable = false)
    private Employee employee;
    @Column(name = "heures_travaillees")
    private Double heuresTravaillees;
    public Pointage() {
		super();
	}
	public Pointage(Long id, Employee employee, LocalDate date, int mois, int annee, Double ret, Double pres,
			Double retEtAut, Double absNj, Double absCt, Double absLt, Double absAut, Double absPay, Double cgAnn,
			Double maPied, Double jf, Double at, Service serv, State etat, LocalDate datePointage,
			Double heuresTravaillees) {
		super();
		this.id = id;
		this.employee = employee;
		this.date = date;
		this.mois = mois;
		this.annee = annee;
		this.ret = ret;
		this.pres = pres;
		this.retEtAut = retEtAut;
		this.absNj = absNj;
		this.absCt = absCt;
		this.absLt = absLt;
		this.absAut = absAut;
		this.absPay = absPay;
		this.cgAnn = cgAnn;
		this.maPied = maPied;
		this.jf = jf;
		this.at = at;
		Serv = serv;
		this.etat = etat;
		this.datePointage = datePointage;
		this.heuresTravaillees = heuresTravaillees;
	}
    public Double getHeuresTravaillees() {
		return heuresTravaillees;
	}
	public void setHeuresTravaillees(Double heuresTravaillees) {
		this.heuresTravaillees = heuresTravaillees;
	}
	

    public LocalDate getDatePointage() {
		return datePointage;
	}
	public void setDatePointage(LocalDate datePointage) {
		this.datePointage = datePointage;
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
