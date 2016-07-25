package com.biopama.security;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.biopama.model.Assignation;
import com.biopama.model.Fonction;
import com.biopama.model.MotDePasse;
import com.biopama.model.Operateur;
import com.biopama.utilitaires.AutorityClass;

@Component
@Scope(value="session")
public class UserController implements Serializable{
	Logger logger = Logger.getLogger(UserController.class);

	private static final long serialVersionUID = 1L;
	
	private List<Assignation> listeAss = new ArrayList<Assignation>();
	private Operateur operateur = new Operateur();
	private MotDePasse motDePasse = new MotDePasse();
	private Fonction fonction = new Fonction();
	private Date dateCon ;
	private Assignation slctd = new Assignation();
	private Assignation slctdTb = new Assignation();
	private AutorityClass autority = new AutorityClass();
	private String name;
	private String password;
	private boolean  creer = true;
	
	@Autowired
	UserService userService;
	
	@PostConstruct
	public void postConstru() throws IOException {
		
		creer = true;
		setOperateur(userService.getOperateurs());
		getListeAss().addAll(userService.getListeAss());
		setAutority(userService.getAutoritys());
		//setOperateur(userService.getOperateurs());
		setDateCon(userService.getDateCons());
		logger.info("Une connexion de l'Opérateur: "+userService.getOperateurs().getOpeMatricule());
	}
	
	public String doAction(){
		if(!creer){
			setFonction(getSlctd().getFonction());
			userService.cleanAutority();
			userService.getAutorisation(getSlctd().getFonction().getTypeFonction().getTyfCode());
			setAutority(userService.getAutoritys());
		return"/pages/index?faces-redirect=true";
		}else{
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Sélectionner une fonction dans la liste ! ", ""));
		
			return"/pages/accueil?faces-redirect=true";
		}		
	}
	
	public void onRowSelect() {
		setSlctd(getSlctdTb());
		creer = false;	
					
	}
	
	public String logOut(){
		creer = true;
		return "/logout.jsp?faces-redirect=true";
	}

	public Operateur getOperateur() {
		return operateur;
	}

	public void setOperateur(Operateur operateur) {
		this.operateur = operateur;
	}
	public MotDePasse getMotdepasse() {
		return motDePasse;
	}
	public void setMotdepasse(MotDePasse motDePasse) {
		this.motDePasse = motDePasse;
	}
	public Fonction getFonction() {
		return fonction;
	}
	public void setFonction(Fonction fonction) {
		this.fonction = fonction;
	}
	public Date getDateCon() {
		return dateCon;
	}
	public void setDateCon(Date dateCon) {
		this.dateCon = dateCon;
	}
	public AutorityClass getAutority() {
		return autority;
	}
	public void setAutority(AutorityClass autority) {
		this.autority = autority;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public List<Assignation> getListeAss() {
		return listeAss;
	}


	public void setListeAss(List<Assignation> listeAss) {
		this.listeAss = listeAss;
	}

	public Assignation getSlctd() {
		return slctd;
	}

	public void setSlctd(Assignation slctd) {
		this.slctd = slctd;
	}

	public Assignation getSlctdTb() {
		return slctdTb;
	}

	public void setSlctdTb(Assignation slctdTb) {
		this.slctdTb = slctdTb;
	}

	public boolean isCreer() {
		return creer;
	}

	public void setCreer(boolean creer) {
		this.creer = creer;
	}
	
	
}
