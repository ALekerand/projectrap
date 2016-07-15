package com.biopama.controller.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.biopama.model.TypeGouvernace;
import com.biopama.service.Iservice;
import com.biopama.utilitaires.KeyGen;

@Component
public class TypeGouvController {
	@Autowired
	Iservice iservice;
	@Autowired
	KeyGen keyGen;
	
private List<TypeGouvernace> objetListe = new ArrayList<TypeGouvernace>();
	
	private TypeGouvernace slctd = new TypeGouvernace();
	private TypeGouvernace slctdTb = new TypeGouvernace();
	private boolean  creer = true;
	private boolean  modifier = true;
	private boolean  supprimer = true;
	
	@PostConstruct
	public void postConstru() {
		
		chargeData();
		
	}
	
	public void chargeData(){
		getObjetListe().clear();
		List listTND = iservice.getObjects("TypeGouvernace");
		if(!listTND.isEmpty()){
			for (Iterator it = listTND.iterator(); it.hasNext();) {
				TypeGouvernace nd = (TypeGouvernace) it.next();
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
	public void create(){		if(getSlctd().getTgvLebelle().isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Saisir Une Valeur ! ", ""));  
		}else{
			getSlctd().setTgvCod(keyGen.getTypeID("TG",2,5,"type_gouvernace","tgv_cod"));
			iservice.addObject(getSlctd());
		}
		chargeData();
		vider();
		creer = false;	
		modifier = true;
		supprimer = true;
	}
	
	public void update(){
		if(getSlctd().getTgvLebelle().isEmpty()){
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
		if(getSlctd().getTgvLebelle().isEmpty()){
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
		setSlctd(new TypeGouvernace());
	}

	public void onRowSelectDoss() {
		setSlctd(getSlctdTb());
		creer = true;	
		modifier = false;
		supprimer = false;
					
	}
	
	public void onRowUnSelect() {
		setSlctd(new TypeGouvernace());
		creer = true;	
		modifier = false;
		supprimer = false;
					
	}
	
	
	public List<TypeGouvernace> getObjetListe() {
		return objetListe;
	}

	public void setObjetListe(List<TypeGouvernace> objetListe) {
		this.objetListe = objetListe;
	}

	public TypeGouvernace getSlctd() {
		return slctd;
	}
	public void setSlctd(TypeGouvernace slctd) {
		this.slctd = slctd;
	}
	public TypeGouvernace getSlctdTb() {
		return slctdTb;
	}
	public void setSlctdTb(TypeGouvernace slctdTb) {
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


