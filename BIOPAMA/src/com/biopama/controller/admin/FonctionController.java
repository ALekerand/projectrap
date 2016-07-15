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
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.biopama.convertor.EntityList;
import com.biopama.model.Fonction;
import com.biopama.model.Pays;
import com.biopama.model.TypeFonction;
import com.biopama.service.Iservice;
import com.biopama.utilitaires.KeyGen;


@Component
public class FonctionController {
	@Autowired
	Iservice iservice;
	
	@Autowired
	EntityList entityList;
	
	@Autowired
	KeyGen keyGen;
	
private List<Fonction> objetListe = new ArrayList<Fonction>();
	
	private Fonction slctd = new Fonction();
	private Fonction slctdTb = new Fonction();
	private boolean  creer = false;
	private boolean  modifier = true;
	private boolean  supprimer = true;
	private boolean  typef = false;
	
	@PostConstruct
	public void postConstru() {	
		chargeData();
		
	}
	
	public void chargeData(){
		int n = 0 ;
		getObjetListe().clear();
		List listTND = iservice.getObjects("Fonction");
		
			for (Iterator it = listTND.iterator(); it.hasNext();) {
				Fonction f = (Fonction) it.next();
				getObjetListe().add(f);
			}
			//ordonner les Fonction
			 Collections.sort(getObjetListe(), new Comparator<Fonction>() {
			        @Override public int compare(Fonction p1, Fonction p2) {
			        	int n = p2.getFonCod().compareTo(p1.getFonCod());
			        	return n; // des  
			        }

			    });
			 vider();
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
		
		
			//Récupération de type fonction
			for (TypeFonction tf : entityList.getListTypeFonction()) {
				if (tf.getTyfLibelle().equalsIgnoreCase(getSlctd().getTypeFonction().getTyfLibelle())) {
					getSlctd().setTypeFonction(tf);
				}
			}
			
			//Récupération du Pays
			for (Pays p : entityList.getListPays()) {
				if (p.getPayLibCourt().equalsIgnoreCase(getSlctd().getPays().getPayLibCourt())) {
					getSlctd().setPays(p);
				}
			}
			String codeF = keyGen.getFonctionID(getSlctd().getPays().getPayLibCourt(), getSlctd().getTypeFonction().getTyfCode()) ;
			getSlctd().setFonCod(codeF);
			
			System.out.println("Code fonction = "+codeF);

			
			iservice.addObject(getSlctd());
			chargeData();
			vider();
			creer = true;
			modifier = false;
			typef = true;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Fonction enrégistrée ! ", ""));
		
		}  
	
	public void delete(){
		
		
			iservice.deleteObject(getSlctd());
		
		chargeData();
		vider();
		creer = true;	
		modifier = true;
		supprimer = true;
	}
	
	@Transactional
	public void update(){
	
		iservice.updateObject(getSlctd());

		
		chargeData();
		vider();
		creer = false;	
		modifier = true;
		typef = false;
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Fonction mise à jour ! ", ""));  
	}

	
	public void viderChamps(){
		vider();
		creer = false;	
		modifier = true;
		typef = false;
	}
	
	public void vider(){
		Fonction f = new Fonction();
		TypeFonction tf = new TypeFonction();
		Pays p = new Pays();
		f.setTypeFonction(tf);
		f.setPays(p);
		setSlctd(f);
		setSlctdTb(f);
	
	}

	public void onRowSelect() {
		setSlctd(getSlctdTb());
		creer = true;	
		modifier = false;
		typef = true;
					
	}
	
	public void onRowUnSelect() {
		setSlctd(new Fonction());
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

	
	public List<Fonction> getObjetListe() {
		return objetListe;
	}

	public void setObjetListe(List<Fonction> objetListe) {
		this.objetListe = objetListe;
	}

	public Fonction getSlctd() {
		return slctd;
	}

	public void setSlctd(Fonction slctd) {
		this.slctd = slctd;
	}

	public Fonction getSlctdTb() {
		return slctdTb;
	}

	public void setSlctdTb(Fonction slctdTb) {
		this.slctdTb = slctdTb;
	}

	public boolean isSupprimer() {
		return supprimer;
	}

	public void setSupprimer(boolean supprimer) {
		this.supprimer = supprimer;
	}

	
}
