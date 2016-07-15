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
import com.biopama.model.CategorieAp;
import com.biopama.model.ReseauApVir;
import com.biopama.security.UserController;
import com.biopama.service.Iservice;
import com.biopama.utilitaires.KeyGen;


@Component
@Scope(value="session")
public class ReseauVirController {
	@Autowired
	Iservice iservice;
	@Autowired
	KeyGen keyGen;
	@Autowired
	UserController userController;

	private List<ReseauApVir> objetListe = new ArrayList<ReseauApVir>();
	private List<CategorieAp> listCatAp = new ArrayList<CategorieAp>();
	private List<AirProtegee> listAirp = new ArrayList<AirProtegee>();
	private String slctdAP;
	private String slctdCat;
	
	
	
	private ReseauApVir slctd = new ReseauApVir();
	private ReseauApVir slctdTb = new ReseauApVir();
	private boolean  creer = false;
	private boolean  modifier = true;
	private String rechCode;
	private String rechNom;
	private String rechType;
	private String rechPays;
	
	
	public ReseauVirController() {
		ReseauApVir rv = new ReseauApVir();
		AirProtegee ap = new AirProtegee();
		CategorieAp c = new CategorieAp();
		rv.setAirProtegee(ap);
		rv.setCategorieAp(c);
		setSlctd(rv);
		setSlctdTb(rv);
	}
	@PostConstruct
	public void postConstru() {	
	
		//chargeFoncConsByPays(userController.getFonction().getPays().getPayLibCourt());
		chargeApByFonc(userController.getFonction().getFonCod());
		chargeCatAp();
		rafraichir();
	}
	public void rafraichir(){
//	getSlctd().getAirProtegee().setPays(userController.getFonction().getPays());
	getObjetListe().clear();
	//chargeAPByPays(userController.getFonction().getPays().getPayLibCourt());

	 
	}
	
	public void chargeAPByPays(String pays){
		getListAirp().clear();
		List listAP = iservice.getObjects("AirProtegee");
		for (Iterator it = listAP.iterator(); it.hasNext();) {
			AirProtegee AP = (AirProtegee) it.next();
			if(pays.equalsIgnoreCase(AP.getPays().getPayLibCourt())){
				getListAirp().add(AP);
				}	
		}
		//Ordre par pays
		Collections.sort(getListAirp(), new Comparator<AirProtegee>() {
	        @Override public int compare(AirProtegee p1, AirProtegee p2) {
	        	int n = (p1.getPays().getPayLibLong()+p1.getAipCod()).compareTo(p2.getPays().getPayLibLong()+p2.getAipCod());
	        	return n; // croi  
	        }

	    });
		
		}
	
	public void chargeALL(String nonAp, String catAp, String pays, String codeAp){
		getListAirp().clear();
		//Controle critère
		List listAP = iservice.getObjects("AirProtegee");
		for (Iterator it = listAP.iterator(); it.hasNext();) {
			AirProtegee AP = (AirProtegee) it.next();
			//Cas 1 code
			if(returnCritere(nonAp, catAp, pays, codeAp).equalsIgnoreCase("1000")){
				//exécution
				if(codeAp.equalsIgnoreCase(AP.getAipCod())){
					getListAirp().add(AP);
					}	
				}	
			//Cas 2 nom
			if(returnCritere(nonAp, catAp, pays, codeAp).equalsIgnoreCase("0100")){
				//exécution
				if(nonAp.equalsIgnoreCase(AP.getAipNomUtilise())){
					getListAirp().add(AP);
					}	
				}
			//Cas 3 catAp
			if(returnCritere(nonAp, catAp, pays, codeAp).equalsIgnoreCase("0010")){
				//exécution
				if(catAp.equalsIgnoreCase(AP.getAipCategNatio())){
					getListAirp().add(AP);
					}	
				}
			//Cas 4 pays
			if(returnCritere(nonAp, catAp, pays, codeAp).equalsIgnoreCase("0001")){
				//exécution
				if(pays.equalsIgnoreCase(AP.getPays().getPayLibCourt())){
					getListAirp().add(AP);
					}	
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
	
	public void chargeCatAp(){
		getListCatAp().clear();
		List listC = iservice.getObjects("CategorieAp");
		for (Iterator it = listC.iterator(); it.hasNext();) {
			CategorieAp c = (CategorieAp) it.next();
				getListCatAp().add(c);	
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
	
	public void checkRvByAP(){
		getObjetListe().clear();
		if(getSlctdAP()!=null){
		for (AirProtegee ap : getListAirp()) {
			if(ap.getAipCod().equalsIgnoreCase(getSlctdAP())){
				getSlctd().setAirProtegee(ap);
				getObjetListe().addAll(ap.getReseauApVirs());
				}	
		}	
		}
		}
	
	public void checkCat(){
	
		if(getSlctdCat()!=null){
		for (CategorieAp c : getListCatAp()) {
			if(c.getCaaCod().equalsIgnoreCase(getSlctdCat())){
				getSlctd().setCategorieAp(c);
				}	
		}	
		}else{
			getSlctd().setCategorieAp(new CategorieAp());
			
		}
		}
	
	

	//Méthode CRUD
	@Transactional
	public void create(){
		Date date = new Date();
		date = Calendar.getInstance().getTime();
		getSlctd().setRavDate(date);
		iservice.addObject(getSlctd());	
		vider();
		chargeApByFonc(userController.getFonction().getFonCod());
			
			creer = true;
			modifier = false;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "AirProtegee Ajoutée au réseau  ! ", ""));
		
	}
	
	//Méthode CRUD
	@Transactional
	public void update(){
		iservice.updateObject(getSlctd());	
		chargeApByFonc(userController.getFonction().getFonCod());
			//vider();
			creer = true;
			modifier = false;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "AirProtegee Modifiée dans le réseau ! ", ""));
		
	}
	
	public void onRowSelect() {
		setSlctd(getSlctdTb());
		creer = true;	
		modifier = false;
					
	}
	
	public void onRowUnSelect() {
		setSlctd(new ReseauApVir());
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
		return "/pages/Production/AirProtegee?faces-redirect=true";
		//return "pretty:affectation";
	}
	
	
	public void vider(){
		ReseauApVir rv = new ReseauApVir();
		AirProtegee ap = new AirProtegee();
		CategorieAp c = new CategorieAp();
		rv.setAirProtegee(ap);
		rv.setCategorieAp(c);
		setSlctd(rv);
		setSlctdTb(rv);
		setRechNom(""); 
		setRechType(""); 
		setRechPays(""); 
		setRechCode("");
		setSlctdAP("");
		
		creer = false;	
		modifier = true;
		//rafraichir();
	
	}


	public List<ReseauApVir> getObjetListe() {
		return objetListe;
	}

	public void setObjetListe(List<ReseauApVir> objetListe) {
		this.objetListe = objetListe;
	}

	public ReseauApVir getSlctd() {
		return slctd;
	}

	public void setSlctd(ReseauApVir slctd) {
		this.slctd = slctd;
	}

	public ReseauApVir getSlctdTb() {
		return slctdTb;
	}

	public void setSlctdTb(ReseauApVir slctdTb) {
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
	public List<CategorieAp> getListCatAp() {
		return listCatAp;
	}
	public void setListCatAp(List<CategorieAp> listCatAp) {
		this.listCatAp = listCatAp;
	}
	public String getSlctdCat() {
		return slctdCat;
	}
	public void setSlctdCat(String slctdCat) {
		this.slctdCat = slctdCat;
	}
	
}
