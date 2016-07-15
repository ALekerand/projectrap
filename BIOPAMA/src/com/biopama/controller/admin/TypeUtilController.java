package com.biopama.controller.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.biopama.model.TypeUtilisation;
import com.biopama.service.Iservice;
import com.biopama.utilitaires.KeyGen;

@Component
public class TypeUtilController {
	@Autowired
	Iservice iservice;
	@Autowired
	KeyGen keyGen;
	
private List<TypeUtilisation> objetListe = new ArrayList<TypeUtilisation>();
	
	private TypeUtilisation slctd = new TypeUtilisation();
	private TypeUtilisation slctdTb = new TypeUtilisation();
	private boolean  creer = true;
	private boolean  modifier = true;
	private boolean  supprimer = true;
	
	@PostConstruct
	public void postConstru() {
		
		chargeData();
		
	}
	
	public void chargeData(){
		getObjetListe().clear();
		List listTND = iservice.getObjects("TypeUtilisation");
		if(!listTND.isEmpty()){
			for (Iterator it = listTND.iterator(); it.hasNext();) {
				TypeUtilisation nd = (TypeUtilisation) it.next();
				getObjetListe().add(nd);
	
			}
		}
	}
	
	public void validationChamp(){
		if(modifier==false){
			creer = true;		
		}else{
			creer = false;	
		}
		supprimer = true;
	}
	//Méthode CRUD
	public void create(){		if(getSlctd().getTutLibelle().isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Saisir Une Valeur ! ", ""));  
		}else{
			getSlctd().setTutCod(keyGen.getTypeID("U",1,3,"type_utilisation","tut_cod"));
			iservice.addObject(getSlctd());
		}
		chargeData();
		vider();
		creer = false;	
		modifier = true;
		supprimer = true;
	}
	
	public void update(){
		if(getSlctd().getTutLibelle().isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Selectionner Un élément ! ", ""));  
		}else{
			iservice.updateObject(getSlctd());
		}
		chargeData();
		vider();
		creer = false;	
		modifier = true;
		supprimer = true;
	}
	public void delete(){
		if(getSlctd().getTutLibelle().isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Selectionner Un élément ! ", ""));  
		}else{
			iservice.deleteObject(getSlctd());
		}
		chargeData();
		vider();
		creer = true;	
		modifier = true;
		supprimer = true;
	}
	
	public void vider(){
		setSlctd(new TypeUtilisation());
	}

	public void onRowSelectDoss() {
		setSlctd(getSlctdTb());
		creer = true;	
		modifier = false;
		supprimer = false;
					
	}
	
	public void onRowUnSelect() {
		setSlctd(new TypeUtilisation());
		creer = true;	
		modifier = false;
		supprimer = false;
					
	}
	
	
	public List<TypeUtilisation> getObjetListe() {
		return objetListe;
	}

	public void setObjetListe(List<TypeUtilisation> objetListe) {
		this.objetListe = objetListe;
	}

	public TypeUtilisation getSlctd() {
		return slctd;
	}
	public void setSlctd(TypeUtilisation slctd) {
		this.slctd = slctd;
	}
	public TypeUtilisation getSlctdTb() {
		return slctdTb;
	}
	public void setSlctdTb(TypeUtilisation slctdTb) {
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

	public boolean isSupprimer() {
		return supprimer;
	}

	public void setSupprimer(boolean supprimer) {
		this.supprimer = supprimer;
	}
	
	
}


