package com.biopama.controller.creation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.biopama.model.AirProtegee;
import com.biopama.model.Pays;
import com.biopama.security.UserController;
import com.biopama.service.Iservice;
import com.biopama.utilitaires.KeyGen;


@Component
@Scope(value="session")
public class CreationAPController {
	@Autowired
	Iservice iservice;
	@Autowired
	KeyGen keyGen;
	@Autowired
	UserController userController;
	@Autowired
	AffectationController affectationController;

	private List<AirProtegee> objetListe = new ArrayList<AirProtegee>();

	
	
	
	private AirProtegee slctd = new AirProtegee();
	private AirProtegee slctdTb = new AirProtegee();
	private boolean  creer = false;
	private boolean  modifier = true;
	private String rechCode;
	private String rechNom;
	private String rechType;
	private String rechPays;
	
	
	@PostConstruct
	public void postConstru() {	
		rafraichir();
	}
	public void rafraichir(){
	getSlctd().setPays(userController.getFonction().getPays());
	chargeAPByPays(userController.getFonction().getPays().getPayLibCourt());	
	
	//ordonner les AP
	 Collections.sort(getObjetListe(), new Comparator<AirProtegee>() {
	        @Override public int compare(AirProtegee p1, AirProtegee p2) {
	        	int n = p1.getAipCod().compareTo(p2.getAipCod());
	        	return n; // croi  
	        }

	    });
	 
	}
	
	public void chargeAPByPays(String pays){
		getObjetListe().clear();
		List listAP = iservice.getObjects("AirProtegee");
		for (Iterator it = listAP.iterator(); it.hasNext();) {
			AirProtegee AP = (AirProtegee) it.next();
			if(pays.equalsIgnoreCase(AP.getPays().getPayLibCourt())){
				getObjetListe().add(AP);
				}	
		}	
		
		}
	
	public void chargeALL(String nonAp, String catAp, String pays, String codeAp){
		getObjetListe().clear();
		//Controle critère
		List listAP = iservice.getObjects("AirProtegee");
		for (Iterator it = listAP.iterator(); it.hasNext();) {
			AirProtegee AP = (AirProtegee) it.next();
			//Cas 1 code
			if(returnCritere(nonAp, catAp, pays, codeAp).equalsIgnoreCase("1000")){
				//exécution
				if(codeAp.equalsIgnoreCase(AP.getAipCod())){
					getObjetListe().add(AP);
					}	
				}	
			//Cas 2 nom
			if(returnCritere(nonAp, catAp, pays, codeAp).equalsIgnoreCase("0100")){
				//exécution
				if(nonAp.equalsIgnoreCase(AP.getAipNomUtilise())){
					getObjetListe().add(AP);
					}	
				}
			//Cas 3 catAp
			if(returnCritere(nonAp, catAp, pays, codeAp).equalsIgnoreCase("0010")){
				//exécution
				if(catAp.equalsIgnoreCase(AP.getAipCategNatio())){
					getObjetListe().add(AP);
					}	
				}
			//Cas 4 pays
			if(returnCritere(nonAp, catAp, pays, codeAp).equalsIgnoreCase("0001")){
				//exécution
				if(pays.equalsIgnoreCase(AP.getPays().getPayLibCourt())){
					getObjetListe().add(AP);
					}	
				}
		}	
			
		}
	
	public boolean checkString(String value){
		if(value==null || value=="" || value.equalsIgnoreCase("") || value.equals(null)){
			return false;
			}else{
			return true;
			}	
		}
	
	public String returnCritere(String nonAp, String catAp, String pays, String codeAp){
		String i ="";
		if(checkString(codeAp)==true){i=i+"1";}else{i=i+"0";}//1000	
		if(checkString(nonAp)==true){i=i+"1";}else{i=i+"0";}//0100 ou 1100
		if(checkString(catAp)==true){i=i+"1";}else{i=i+"0";}//0010 ou 0110 ou 1010 ou 1110	
		if(checkString(pays)==true){i=i+"1";}else{i=i+"0";}	//0001 ou 0011 ou 0111 ou 0101 ou 1111 ou 1001	
		
		System.out.println("...............i........ "+i);
			return i;
		}
	
	
	public void chechCode(){
		getSlctd().setAipCod(keyGen.getApID(getSlctd().getPays().getPayLibCourt(), getSlctd().getAipCategNatio()));
	}
	
	//Méthode CRUD
	@Transactional
	public void create(){
		getSlctd().setAipCod(keyGen.getApID(getSlctd().getPays().getPayLibCourt(), getSlctd().getAipCategNatio()));
		getSlctd().setFonction(userController.getFonction());
		Date date = new Date();
		date = Calendar.getInstance().getTime();
		getSlctd().setAipDateCreation(date);
		
		iservice.addObject(getSlctd());	
		chargeAPByPays(getSlctd().getPays().getPayLibCourt());
			vider();
			creer = true;
			modifier = false;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Aire Protégée enrégistrée ! ", ""));
		
	}
	
	//Méthode CRUD
	@Transactional
	public void update(){
		iservice.updateObject(getSlctd());	
		chargeAPByPays(getSlctd().getPays().getPayLibCourt());
			vider();
			creer = true;
			modifier = false;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Aire Protégée Modifiée ! ", ""));
		
	}
	
	public void onRowSelect() {
		setSlctd(getSlctdTb());
		creer = true;	
		modifier = false;
					
	}
	
	public void onRowUnSelect() {
		setSlctd(new AirProtegee());
		creer = false;	
		modifier = true;
		
		
					
	}
	
	public void checkRecherche(){
		chargeALL(getRechNom(), getRechType(), getRechPays(), getRechCode());
		creer = false;	
		modifier = true;
	}
	
	public void cleanRecherche(){
		vider();
		creer = false;	
		modifier = true;
	}
	
	public void viderChamps(){
		vider();
		creer = false;	
		modifier = true;
	}
	
	public String affectation(){
		affectationController.getSlctd().setAirProtegee(getSlctd());
		return "/pages/Production/Affectation?faces-redirect=true";
		//return "pretty:affectation";
	}
	
	public String reseau(){
		return "/pages/Production/Reseau?faces-redirect=true";
	}
	
	public String reseauVir(){
		return "/pages/Production/ReseauVirtuel?faces-redirect=true";
	}
	
	public void vider(){
		AirProtegee ap = new AirProtegee();
		Pays p = new Pays();
		ap.setPays(p);
		setSlctd(ap);
		setSlctdTb(ap);
		setRechNom(""); 
		setRechType(""); 
		setRechPays(""); 
		setRechCode("");
		creer = false;	
		modifier = true;
		rafraichir();
	
	}


	public List<AirProtegee> getObjetListe() {
		return objetListe;
	}

	public void setObjetListe(List<AirProtegee> objetListe) {
		this.objetListe = objetListe;
	}

	public AirProtegee getSlctd() {
		return slctd;
	}

	public void setSlctd(AirProtegee slctd) {
		this.slctd = slctd;
	}

	public AirProtegee getSlctdTb() {
		return slctdTb;
	}

	public void setSlctdTb(AirProtegee slctdTb) {
		this.slctdTb = slctdTb;
	}

	public boolean isCreer() {
		return creer;
	}

	public void setCreer(boolean creer) {
		this.creer = creer;
	}

	public boolean isModifier() {
		return modifier;
	}

	public void setModifier(boolean modifier) {
		this.modifier = modifier;
	}

	public String getRechCode() {
		return rechCode;
	}

	public void setRechCode(String rechCode) {
		this.rechCode = rechCode;
	}

	public String getRechNom() {
		return rechNom;
	}

	public void setRechNom(String rechNom) {
		this.rechNom = rechNom;
	}

	public String getRechType() {
		return rechType;
	}

	public void setRechType(String rechType) {
		this.rechType = rechType;
	}

	public String getRechPays() {
		return rechPays;
	}

	public void setRechPays(String rechPays) {
		this.rechPays = rechPays;
	}
	
}
