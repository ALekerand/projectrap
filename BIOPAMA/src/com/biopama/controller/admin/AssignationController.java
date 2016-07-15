package com.biopama.controller.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.biopama.convertor.EntityList;
import com.biopama.model.Assignation;
import com.biopama.model.Fonction;
import com.biopama.model.MotDePasse;
import com.biopama.model.Operateur;
import com.biopama.model.Pays;
import com.biopama.model.TypeFonction;
import com.biopama.security.UserController;
import com.biopama.service.Iservice;
import com.biopama.utilitaires.OperateurRow;

@Component
@Scope(value="session")
public class AssignationController {
	@Autowired
	Iservice iservice;
	@Autowired
	UserController userController;

	private List<Assignation> objetListe = new ArrayList<Assignation>();
	private List<TypeFonction> listTypeFonction = new ArrayList<TypeFonction>();
	private List<Pays> listPays = new ArrayList<Pays>();
	private List<Fonction> foncListe = new ArrayList<Fonction>();
	private List<Operateur> listOperateur = new ArrayList<Operateur>();
	
	
	
	private Assignation slctd = new Assignation();
	private Assignation slctdTb = new Assignation();
	private boolean  creer = false;
	private boolean  modifier = true;
	private boolean  typef = false;
	
	
	
	public AssignationController() {
		Assignation ass = new Assignation();
		Operateur op = new Operateur();
		Fonction f = new Fonction();
		TypeFonction tf = new TypeFonction();
		Pays p = new Pays();
		f.setTypeFonction(tf);
		f.setPays(p);
		
		ass.setOperateur(op);
		ass.setFonction(f);
		ass.setAssCourant("o");
		setSlctd(ass);
		setSlctdTb(ass);
	}

	@PostConstruct
	public void postConstru() {	
		chargeData();
		rafraichir();
	}
	public void rafraichir(){
		chargeAssData(userController.getFonction().getPays().getPayLibCourt());
		
	}
	public void chargeData(){
		chargeOpData();
		chargePayData();
		
	}
	
	public void chargeOpData(){
		int n = 0 ;
		getListOperateur().clear();
		List listTND = iservice.getObjects("Operateur");
		
			for (Iterator it = listTND.iterator(); it.hasNext();) {
				n = n+1;
				Operateur o = (Operateur) it.next();
				getListOperateur().add(o);
	
			}
			//ordonner les Opérateurs
			 Collections.sort(getListOperateur(), new Comparator<Operateur>() {
			        @Override public int compare(Operateur p1, Operateur p2) {
			        	int n = p2.getOpeMatricule().compareTo(p1.getOpeMatricule());
			        	return n; // des  
			        }

			    });
			 
	}
	
	public void chargePayData(){
		
		getListPays().clear();
		List listTND = iservice.getObjects("Pays");
		
			for (Iterator it = listTND.iterator(); it.hasNext();) {
				Pays p = (Pays) it.next();
				
					getListPays().add(p);
				
	
			}
			//ordonner les Fonctions
			 Collections.sort(getListPays(), new Comparator<Pays>() {
			        @Override public int compare(Pays p1, Pays p2) {
			        	int n = p2.getPayLibCourt().compareTo(p1.getPayLibCourt());
			        	return n; // des  
			        }

			    });
			 
	}
	
	public void chargeFcData(String libCourtPays){
		int n = 0 ;
		getFoncListe().clear();
		List listTND = iservice.getObjects("Fonction");
		
			for (Iterator it = listTND.iterator(); it.hasNext();) {
				Fonction f = (Fonction) it.next();
				if(libCourtPays.equalsIgnoreCase(f.getPays().getPayLibCourt())){
				getFoncListe().add(f);
				}
	
			}
			//ordonner les Fonctions
			 Collections.sort(getFoncListe(), new Comparator<Fonction>() {
			        @Override public int compare(Fonction p1, Fonction p2) {
			        	int n = p2.getTypeFonction().getTyfCode().compareTo(p1.getTypeFonction().getTyfCode());
			        	return n; // des  
			        }

			    });
			 
	}
	
	public void chargeAssData(String libCourtPays){
		int n = 0 ;
		getObjetListe().clear();
		List listTND = iservice.getObjects("Assignation");
		
			for (Iterator it = listTND.iterator(); it.hasNext();) {
				
				Assignation a = (Assignation) it.next();
				
				if(libCourtPays.equalsIgnoreCase(a.getFonction().getPays().getPayLibCourt())){
					getObjetListe().add(a);
					
					}
				
			}
			//ordonner les Assignations
			 Collections.sort(getObjetListe(), new Comparator<Assignation>() {
			        @Override public int compare(Assignation p1, Assignation p2) {
			        	int n = p2.getOperateur().getOpeMatricule().compareTo(p1.getOperateur().getOpeMatricule());
			        	return n; // des  
			        }

			    });
			 
	}
	
	public void checkFonction(){
		
		if(getSlctd().getFonction().getPays().getPayLibCourt()!=null){
			chargeFcData(getSlctd().getFonction().getPays().getPayLibCourt());
		}
	}
	
	
	public void validationChamp(){
		if(modifier==false){
			creer = true;
			typef = true;
		}else{
			creer = false;	
			typef = false;
		}
		//supprimer = true;
	}
	
	

	//Méthode CRUD
	@Transactional
	public void create(){
		//Récupération de la fonction
		for (Fonction f : getFoncListe()) {
			if (f.getFonCod().equalsIgnoreCase(getSlctd().getFonction().getFonCod())) {
				getSlctd().setFonction(f);
			}
		}
		
		//Récupération de l'Opérateur
				for (Operateur o : getListOperateur()) {
					if (o.getOpeMatricule().equalsIgnoreCase(getSlctd().getOperateur().getOpeMatricule())) {
						getSlctd().setOperateur(o);
					}
				}
				
				
		/*//Récupération du pays
		for (Pays p : getListPays()) {
			if (p.getPayLibCourt().equalsIgnoreCase(getSlctd().getFonction().getPays().getPayLibCourt())) {
				getSlctd().getFonction().setPays(p);
			}
		}
		*/

		iservice.addObject(getSlctd());
		
			
			chargeAssData(getSlctd().getFonction().getPays().getPayLibCourt());
			vider();
			creer = true;
			modifier = false;
			typef = true;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Assignation enrégistrée ! ", ""));
		
	}
	@Transactional
	public void update(){
	   getSlctd().setOperateur(getSlctd().getOperateur());
	/* //Récupération de type fonction
 		for(TypeFonction tf : entityList.getListTypeFonction()){
 			if(tf.getTyfLibelle().equalsIgnoreCase(getSlctd().getFonction().getTypeFonction().getTyfLibelle())){
 				 getSlctd().getFonction().setTypeFonction(tf);
 			}
 		}*/
		iservice.updateObject(getSlctd().getFonction());
		iservice.updateObject(getSlctd());
		
		chargeAssData(getSlctd().getFonction().getPays().getPayLibCourt());
		vider();
		creer = false;	
		modifier = true;
		typef = false;
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Assignation mise à jour ! ", ""));  
	}

	
	public void viderChamps(){
		vider();
		creer = false;	
		modifier = true;
		typef = false;
	}
	
	public void vider(){
		Assignation ass = new Assignation();
		Operateur op = new Operateur();
		Fonction f = new Fonction();
		TypeFonction tf = new TypeFonction();
		Pays p = new Pays();
		f.setTypeFonction(tf);
		f.setPays(p);
		
		ass.setOperateur(op);
		ass.setFonction(f);
		ass.setAssCourant("o");
		setSlctd(ass);
		setSlctdTb(ass);
	
	}

	public void onRowSelectAss() {
		setSlctd(getSlctdTb());
		creer = true;	
		modifier = false;
		typef = true;
					
	}
	
	public void onRowUnSelect() {
		Assignation ass = new Assignation();
		Operateur op = new Operateur();
		Fonction f = new Fonction();
		TypeFonction tf = new TypeFonction();
		Pays p = new Pays();
		f.setTypeFonction(tf);
		f.setPays(p);
		
		ass.setOperateur(op);
		ass.setFonction(f);
		ass.setAssCourant("o");
		setSlctd(ass);
		creer = false;	
		modifier = true;
		
		
					
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


	public boolean isTypef() {
		return typef;
	}

	public void setTypef(boolean typef) {
		this.typef = typef;
	}

	public List<Fonction> getFoncListe() {
		return foncListe;
	}

	public void setFoncListe(List<Fonction> foncListe) {
		this.foncListe = foncListe;
	}

	public List<Assignation> getObjetListe() {
		return objetListe;
	}

	public void setObjetListe(List<Assignation> objetListe) {
		this.objetListe = objetListe;
	}

	public List<TypeFonction> getListTypeFonction() {
		return listTypeFonction;
	}

	public void setListTypeFonction(List<TypeFonction> listTypeFonction) {
		this.listTypeFonction = listTypeFonction;
	}

	public List<Pays> getListPays() {
		return listPays;
	}

	public void setListPays(List<Pays> listPays) {
		this.listPays = listPays;
	}

	public List<Operateur> getListOperateur() {
		return listOperateur;
	}

	public void setListOperateur(List<Operateur> listOperateur) {
		this.listOperateur = listOperateur;
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

	
}
