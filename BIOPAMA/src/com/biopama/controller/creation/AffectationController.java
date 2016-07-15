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

import com.biopama.model.Affectation;
import com.biopama.model.AirProtegee;
import com.biopama.model.Fonction;
import com.biopama.security.UserController;
import com.biopama.service.Iservice;
import com.biopama.utilitaires.KeyGen;


@Component
@Scope(value="session")
public class AffectationController {
	@Autowired
	Iservice iservice;
	@Autowired
	KeyGen keyGen;
	@Autowired
	UserController userController;

	private List<Affectation> objetListe = new ArrayList<Affectation>();
	private List<Fonction> listFonction = new ArrayList<Fonction>();
	private List<AirProtegee> listAirp = new ArrayList<AirProtegee>();
	private String slctdAP;
	
	
	
	private Affectation slctd = new Affectation();
	private Affectation slctdTb = new Affectation();
	private boolean  creer = false;
	private boolean  modifier = true;
	private String rechCode;
	private String rechNom;
	private String rechType;
	private String rechPays;
	
	
	public AffectationController() {
		Affectation af = new Affectation();
		AirProtegee ap = new AirProtegee();
		Fonction f = new Fonction();
		af.setAirProtegee(ap);
		af.setFonction(f);
		setSlctd(af);
		setSlctdTb(af);
	}
	@PostConstruct
	public void postConstru() {	
	
		chargeFoncConsByPays(userController.getFonction().getPays().getPayLibCourt());
		chargeApByFonc(userController.getFonction().getFonCod());
		rafraichir();
	}
	public void rafraichir(){
	getSlctd().getAirProtegee().setPays(userController.getFonction().getPays());
	getObjetListe().clear();
	//chargeAPByPays(userController.getFonction().getPays().getPayLibCourt());
	
	
	//ordonner les AP
	 Collections.sort(getObjetListe(), new Comparator<Affectation>() {
	        @Override public int compare(Affectation p1, Affectation p2) {
	        	int n = p1.getAirProtegee().getAipCod().compareTo(p2.getAirProtegee().getAipCod());
	        	return n; // croi  
	        }

	    });
	 
	}
	
	public void chargeAPByPays(String pays){
		getObjetListe().clear();
		vider();
		List listAP = iservice.getObjects("Affectation");
		for (Iterator it = listAP.iterator(); it.hasNext();) {
			Affectation AP = (Affectation) it.next();
			if(pays.equalsIgnoreCase(AP.getAirProtegee().getPays().getPayLibCourt())){
				getObjetListe().add(AP);
				}	
		}	
		
		}
	
	public void chargeALL(String nonAp, String catAp, String pays, String codeAp){
		getObjetListe().clear();
		
		//Controle critère
		List listAP = iservice.getObjects("Affectation");
		for (Iterator it = listAP.iterator(); it.hasNext();) {
			Affectation AP = (Affectation) it.next();
			//Cas 1 code
			if(returnCritere(nonAp, catAp, pays, codeAp).equalsIgnoreCase("1000")){
				//exécution
				if(codeAp.equalsIgnoreCase(AP.getAirProtegee().getAipCod())){
					getObjetListe().add(AP);
					}	
				}	
			//Cas 2 nom
			if(returnCritere(nonAp, catAp, pays, codeAp).equalsIgnoreCase("0100")){
				//exécution
				if(nonAp.equalsIgnoreCase(AP.getAirProtegee().getAipNomUtilise())){
					getObjetListe().add(AP);
					}	
				}
			//Cas 3 catAp
			if(returnCritere(nonAp, catAp, pays, codeAp).equalsIgnoreCase("0010")){
				//exécution
				if(catAp.equalsIgnoreCase(AP.getAirProtegee().getAipCategNatio())){
					getObjetListe().add(AP);
					}	
				}
			//Cas 4 pays
			if(returnCritere(nonAp, catAp, pays, codeAp).equalsIgnoreCase("0001")){
				//exécution
				if(pays.equalsIgnoreCase(AP.getAirProtegee().getPays().getPayLibCourt())){
					getObjetListe().add(AP);
					}	
				}
		}	
			
		}
	public void chargeFoncConsByPays(String pays){
		getListFonction().clear();
		List listF = iservice.getObjects("Fonction");
		for (Iterator it = listF.iterator(); it.hasNext();) {
			Fonction f = (Fonction) it.next();
			if(pays.equalsIgnoreCase(f.getPays().getPayLibCourt()) && f.getTypeFonction().getTyfCode().equalsIgnoreCase("COV")){
				getListFonction().add(f);
				}	
		}	
		
		}
	public void chargeApByFonc(String fonc){
		getListAirp().clear();
		List listAP = iservice.getObjects("AirProtegee");
		for (Iterator it = listAP.iterator(); it.hasNext();) {
			AirProtegee AP = (AirProtegee) it.next();
			if(fonc.equalsIgnoreCase(AP.getFonction().getFonCod())){
				getListAirp().add(AP);
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
	
	public void checkAfByAP(){
		Affectation af = new Affectation();
		AirProtegee a = new AirProtegee();
		Fonction f = new Fonction();
		af.setAirProtegee(a);
		af.setFonction(f);
		setSlctd(af);
		getObjetListe().clear();
		if(getSlctdAP()!=null){
		for (AirProtegee ap : getListAirp()) {
			if(ap.getAipCod().equalsIgnoreCase(getSlctdAP())){
				getSlctd().setAirProtegee(ap);
				getObjetListe().addAll(ap.getAffectations());
				}	
		}	
		}
		}
	
	public void checkFonc(){
		if(getSlctd().getFonction().getFonCod()!=null){
		for (Fonction f : getListFonction()) {
			if(f.getFonCod().equalsIgnoreCase(getSlctd().getFonction().getFonCod())){
				getSlctd().setFonction(f);
				}	
		}	
		}
		}

	//Méthode CRUD
	@Transactional
	public void create(){
		checkFonc();
		Date date = new Date();
		date = Calendar.getInstance().getTime();
		
		getSlctd().setAffDate(date);
		iservice.addObject(getSlctd());	
		chargeAPByPays(getSlctd().getAirProtegee().getPays().getPayLibCourt());
			vider();
			creer = true;
			modifier = false;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Affectation enrégistrée ! ", ""));
		
	}
	
	//Méthode CRUD
	@Transactional
	public void update(){
		iservice.updateObject(getSlctd());	
		chargeAPByPays(getSlctd().getAirProtegee().getPays().getPayLibCourt());
			vider();
			creer = true;
			modifier = false;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Affectation Modifiée ! ", ""));
		
	}
	
	public void onRowSelect() {
		setSlctd(getSlctdTb());
		creer = true;	
		modifier = false;
					
	}
	
	public void onRowUnSelect() {
		setSlctd(new Affectation());
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
		return "/pages/Production/Affectation?faces-redirect=true";
		//return "pretty:affectation";
	}
	
	
	public void vider(){
		Affectation af = new Affectation();
		AirProtegee ap = new AirProtegee();
		Fonction f = new Fonction();
		af.setAirProtegee(ap);
		af.setFonction(f);
		setSlctd(af);
		setSlctdTb(af);
		setRechNom(""); 
		setRechType(""); 
		setRechPays(""); 
		setRechCode("");
		setSlctdAP("");
		
		creer = false;	
		modifier = true;
		rafraichir();
	
	}


	public List<Affectation> getObjetListe() {
		return objetListe;
	}

	public void setObjetListe(List<Affectation> objetListe) {
		this.objetListe = objetListe;
	}

	public Affectation getSlctd() {
		return slctd;
	}

	public void setSlctd(Affectation slctd) {
		this.slctd = slctd;
	}

	public Affectation getSlctdTb() {
		return slctdTb;
	}

	public void setSlctdTb(Affectation slctdTb) {
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
	public List<Fonction> getListFonction() {
		return listFonction;
	}
	public void setListFonction(List<Fonction> listFonction) {
		this.listFonction = listFonction;
	}
	public List<AirProtegee> getListAirp() {
		return listAirp;
	}
	public void setListAirp(List<AirProtegee> listAirp) {
		this.listAirp = listAirp;
	}
	public String getSlctdAP() {
		return slctdAP;
	}
	public void setSlctdAP(String slctdAP) {
		this.slctdAP = slctdAP;
	}
	
}
