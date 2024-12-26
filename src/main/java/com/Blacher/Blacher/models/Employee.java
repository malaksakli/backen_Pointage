package com.Blacher.Blacher.models;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="employees")
public class Employee {
	 @Id
//la validité et l'unicité du matricule
    @Column(unique = true, nullable = false)
    private Long matr;

	    public Long getMatr() {
		return matr;
	}

	public void setMatr(Long matr) {
		this.matr = matr;
	}
	 @Column(name = "nom_et_prenom_employee")
		private String nomEtPrenom;
	 @Column(name = "nom_et_prenom_employee_pere")
	    private String nomEtPrenomPere;
	 @Column(name = "n_tel")
	    private String Ntel;
	    private Long cin;
	    @Column(name = "nb_enf")
	    private Long NbEnf;
	    private Long taille;
	    private Long pointure;

	    @JsonFormat(pattern = "yyyy-MM-dd")
	    @Column(name = "date_nais")
	    private Date DateNais;// date de naissance

	    @Enumerated(EnumType.STRING)
	    private Info.Genre sf;
	    
	    @Enumerated(EnumType.STRING)
	    private Info.EtatCivil etat;
	    
	    @Enumerated(EnumType.STRING)
	    private Info.Classe classe;
	    
	    @Column(name = "nom_de_chef")
	    @Enumerated(EnumType.STRING)
	    private Info.NomDeChef NomDeChef;
	    
	    @Column(name = "serv")
	    @Enumerated(EnumType.STRING)
	    private Info.Service Serv;
	    
	    @Column(name = "type_de_contrat")
	    @Enumerated(EnumType.STRING)
	    private Info.TypeDeContrat TypeDeContrat;
	    
	    @Column(name = "mode_pay")
	    @Enumerated(EnumType.STRING)
	    private Info.ModeDePaiement ModePay;
	    
	    @Column(name = "date_recr")
	    @JsonFormat(pattern = "yyyy-MM-dd")
	    private Date DateRecr; // date de recrutement

	    private Long anc; // ancienneté
	    
	    @Column(name = "test")
	    private boolean Test;
	    @Column(name = "domici_irre_salaire")
	    private boolean DomiciIrreSalaire;
	    
	    @Column(name = "fin_contr")
	    @JsonFormat(pattern = "yyyy-MM-dd")
	    private Date FinContr; // date de fin de contrat
	    private String alerte;
	    private String remarque; // remarque
	    private String bank; // banque
	    private Long rib; // RIB
	    @JsonFormat(pattern = "yyyy-MM-dd")
	    @Column(name = "quitte_le")
	    private Date QuitteLe; // date de départ

	   
	    public String getNomEtPrenom() {
	        return nomEtPrenom;
	    }

	    public void setNomEtPrenom(String nomEtPrenom) {
	        this.nomEtPrenom = nomEtPrenom;
	    }

	    public String getNomEtPrenomPere() {
	        return nomEtPrenomPere;
	    }

	    public void setNomEtPrenomPere(String nomEtPrenomPere) {
	        this.nomEtPrenomPere = nomEtPrenomPere;
	    }

	    public String getNtel() {
	        return Ntel;
	    }

	    public void setNtel(String Ntel) {
	        this.Ntel = Ntel;
	    }

	    public Long getCin() {
	        return cin;
	    }

	    public void setCin(Long cin) {
	        this.cin = cin;
	    }

	    public Long getNbEnf() {
	        return NbEnf;
	    }

	    public void setNbEnf(Long NbEnf) {
	        this.NbEnf = NbEnf;
	    }

	    public Long getTaille() {
	        return taille;
	    }

	    public void setTaille(Long taille) {
	        this.taille = taille;
	    }

	    public Long getPointure() {
	        return pointure;
	    }

	    public void setPointure(Long pointure) {
	        this.pointure = pointure;
	    }


	    public Date getDateNais() {
	        return DateNais;
	    }

	    public void setDateNais(Date DateNais) {
	        this.DateNais = DateNais;
	    }


	    public Date getDateRecr() {
	        return DateRecr;
	    }

	    public void setDateRecr(Date DateRecr) {
	        this.DateRecr = DateRecr;
	    }

	    public Long getAnc() {
	        return anc;
	    }

	    public void setAnc(Long anc) {
	        this.anc = anc;
	    }

	    public boolean getTest() {
	        return Test;
	    }

	    public void setTest(boolean Test) {
	        this.Test = Test;
	    }

	    public boolean isDomiciIrreSalaire() {
	        return DomiciIrreSalaire;
	    }

	    public void setDomiciIrreSalaire(boolean DomiciIrreSalaire) {
	        this.DomiciIrreSalaire = DomiciIrreSalaire;
	    }

	    public Date getFinContr() {
	        return FinContr;
	    }

	    public void setFinContr(Date FinContr) {
	        this.FinContr = FinContr;
	    }

	    public String getalerte() {
	        return alerte;
	    }

	    public void setalerte(String alerte) {
	        this.alerte = alerte;
	    }

	    public String getremarque() {
	        return remarque;
	    }

	    public void setremarque(String remarque) {
	        this.remarque = remarque;
	    }

	   

	    public Info.Genre getSf() {
			return sf;
		}

		public void setSf(Info.Genre sf) {
			this.sf = sf;
		}

		public Info.EtatCivil getEtat() {
			return etat;
		}

		public void setEtat(Info.EtatCivil etat) {
			this.etat = etat;
		}

		public Info.Classe getClasse() {
			return classe;
		}

		public void setClasse(Info.Classe classe) {
			this.classe = classe;
		}

		public Info.NomDeChef getNomDeChef() {
			return NomDeChef;
		}

		public void setNomDeChef(Info.NomDeChef NomDeChef) {
			this.NomDeChef = NomDeChef;
		}

		public Info.Service getServ() {
			return Serv;
		}

		public void setServ(Info.Service Serv) {
			this.Serv = Serv;
		}

		public Info.TypeDeContrat getTypeDeContrat() {
			return TypeDeContrat;
		}

		public void setTypeDeContrat(Info.TypeDeContrat TypeDeContrat) {
			this.TypeDeContrat = TypeDeContrat;
		}

		public Info.ModeDePaiement getModePay() {
			return ModePay;
		}

		public void setModePay(Info.ModeDePaiement ModePay) {
			this.ModePay = ModePay;
		}

		public String getBank() {
	        return bank;
	    }

	    public void setBank(String bank) {
	        this.bank = bank;
	    }

	    public Long getRib() {
	        return rib;
	    }

	    public void setRib(Long rib) {
	        this.rib = rib;
	    }

	    public Date getQuitteLe() {
	        return QuitteLe;
	    }

	    public void setQuitteLe(Date QuitteLe) {
	        this.QuitteLe = QuitteLe;
	    }
	    

	  
	   
	   @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL,orphanRemoval = true)
	    private List<Pointage> pointages;

}



