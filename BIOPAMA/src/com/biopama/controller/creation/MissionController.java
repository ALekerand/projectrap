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

import com.biopama.model.Mission;
import com.biopama.model.TypeMission;
import com.biopama.model.AirProtegee;
import com.biopama.model.Fonction;
import com.biopama.security.UserController;
import com.biopama.service.Iservice;
import com.biopama.utilitaires.KeyGen;


@Component
@Scope(value="session")
public class MissionController {
	@Autowired
	Iservice iservice;
	@Autowired
	KeyGen keyGen;
	@Autowired
	UserController userController;

	private List<Mission> objetListe = new ArrayList<Mission>();
	private List<AirProtegee> listAirp = new ArrayList<AirProtegee>();
	private List<TypeMission> listType = new ArrayList<TypeMission>();
	private String slctdAP;
	private String slctdType;
	
	
	
	private Mission slctd = new Mission();
	private Mission slctdTb = new Mission();
	private boolean  creer = false;
	private boolean  modifier = true;
	private String rechCode;
	private String rechNom;
	private String rechType;
	private String rechPays;
	
	
	public MissionController(){
		Mission m = new Mission();
		AirProtegee ap = new AirProtegee();
		Fonction f = new Fonction();
		TypeMission tm = new TypeMission();
		m.setAirProtegee(ap);
		m.setFonction(f);
		m.setTypeMission(tm);
		
		setSlctd(m);
		setSlctdTb(m);
	}
	@PostConstruct
	public void postConstru() {	
	
		chargeApByFonc(userController.getFonction().getFonCod());
		chargeType();
		rafraichir();
	}
	public void rafraichir(){
	//getSlctd().getAirProtegee().setPays(userController.getFonction().getPays());
	getObjetListe().clear();
	//chargeAPByPays(userController.getFonction().getPays().getPayLibCourt());
	
	
	//ordonner les AP
	/* Collections.sort(getObjetListe(), new Comparator<Mission>() {
	        @Override public int compare(Mission p1, Mission p2) {
	        	int n = p1.getAirProtegee().getAipCod().compareTo(p2.getAirProtegee().getAipCod());
	        	return n; // croi  
	        }

	    });*/
	 
	}
	
	public void chargeType(){
		getListType().clear();
		List listType = iservice.getObjects("TypeMission");
		for (Iterator it = listType.iterator(); it.hasNext();) {
			TypeMission TP = (TypeMission) it.next();
			getListType().add(TP);
			
		}
		
		//ordonner les 
		 Collections.sort(getListType(), new Comparator<TypeMission>() {
		        @Override public int compare(TypeMission p1, TypeMission p2) {
		        	int n = p1.getTmiLibelle().compareTo(p2.getTmiLibelle());
		        	return n; // croi  
		        }

		    });
		 
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
		//Controle crit�re
		List listAP = iservice.getObjects("AirProtegee");
		for (Iterator it = listAP.iterator(); it.hasNext();) {
			AirProtegee AP = (AirProtegee) it.next();
			//Cas 1 code
			if(returnCritere(nonAp, catAp, pays, codeAp).equalsIgnoreCase("1000")){
				//ex�cution
				if(codeAp.equalsIgnoreCase(AP.getAipCod())){
					getListAirp().add(AP);
					}	
				}	
			//Cas 2 nom
			if(returnCritere(nonAp, catAp, pays, codeAp).equalsIgnoreCase("0100")){
				//ex�cution
				if(nonAp.equalsIgnoreCase(AP.getAipNomUtilise())){
					getListAirp().add(AP);
					}	
				}
			//Cas 3 catAp
			if(returnCritere(nonAp, catAp, pays, codeAp).equalsIgnoreCase("0010")){
				//ex�cution
				if(catAp.equalsIgnoreCase(AP.getAipCategNatio())){
					getListAirp().add(AP);
					}	
				}
			//Cas 4 pays
			if(returnCritere(nonAp, catAp, pays, codeAp).equalsIgnoreCase("0001")){
				//ex�cution
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
	
	public void checkAPP(){
		/*Mission m = new Mission();
		AirProtegee ap = new AirProtegee();
		Fonction f = new Fonction();
		TypeMission tm = new TypeMission();
		m.setAirProtegee(ap);
		m.setFonction(f);
		m.setTypeMission(tm);
		
		setSlctd(m);
		setSlctdTb(m);*/
		
		getObjetListe().clear();
		if(getSlctdAP()!=null){
		for (AirProtegee ap : getListAirp()) {
			if(ap.getAipCod().equalsIgnoreCase(getSlctdAP())){
				getSlctd().setAirProtegee(ap);
				getObjetListe().addAll(ap.getMissions());
				}	
		}	
		}
		}

	public void checkType(){
		if(getSlctdType()!=null){
			for (TypeMission t : getListType()) {
				if(t.getTmiCod().equalsIgnoreCase(getSlctdType())){
					getSlctd().setTypeMission(t);
					}	
			}	
			}
	}
	

	//M�thode CRUD
	@Transactional
	public void create(){
		Date date = new Date();
		date = Calendar.getInstance().getTime();
		getSlctd().setFonction(userController.getFonction());
		iservice.addObject(getSlctd());	
		//checkResByAP(getSlctd().getAirProtegee().getPays().getPayLibCourt());
		chargeApByFonc(userController.getFonction().getFonCod());
			vider();
			creer = true;
			modifier = false;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Mission enr�gistr�e ! ", ""));
		
	}
	
	//M�thode CRUD
	@Transactional
	public void update(){
		iservice.updateObject(getSlctd());	
		//chargeAPByPays(getSlctd().getAirProtegee().getPays().getPayLibCourt());
		//chargeAPAll();
		chargeApByFonc(userController.getFonction().getFonCod());
			vider();
			creer = true;
			modifier = false;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Mission  Modifi�e ! ", ""));
		
	}
	
	public void onRowSelect() {
		setSlctd(getSlctdTb());
		creer = true;	
		modifier = false;
					
	}
	
	public void onRowUnSelect() {
		setSlctd(new Mission());
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
		return "/pages/Production/Mission?faces-redirect=true";
		//return "pretty:affectation";
	}
	
	
	public void vider(){
		Mission m = new Mission();
		AirProtegee ap = new AirProtegee();
		Fonction f = new Fonction();
		TypeMission tm = new TypeMission();
		m.setAirProtegee(ap);
		m.setFonction(f);
		m.setTypeMission(tm);
		
		setSlctd(m);
		setSlctdTb(m);
		setRechNom(""); 
		setRechType(""); 
		setRechPays(""); 
		setRechCode("");
		setSlctdAP("");
		
		creer = false;	
		modifier = true;
		rafraichir();
	
	}


	public List<Mission> getObjetListe() {
		return objetListe;
	}

	public void setObjetListe(List<Mission> objetListe) {
		this.objetListe = objetListe;
	}

	public Mission getSlctd() {
		return slctd;
	}

	public void setSlctd(Mission slctd) {
		this.slctd = slctd;
	}

	public Mission getSlctdTb() {
		return slctdTb;
	}

	public void setSlctdTb(Mission slctdTb) {
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
	public String getSlctdType() {
		return slctdType;
	}
	public void setSlctdType(String slctdType) {
		this.slctdType = slctdType;
	}
	public List<TypeMission> getListType() {
		return listType;
	}
	public void setListType(List<TypeMission> listType) {
		this.listType = listType;
	}
	
	
}
