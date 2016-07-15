package com.biopama.controller.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.biopama.model.TypeMenace;
import com.biopama.service.Iservice;
import com.biopama.utilitaires.KeyGen;

@Component
public class TypeMenacController {
	@Autowired
	Iservice iservice;
	@Autowired
	KeyGen keyGen;
	
private List<TypeMenace> objetListe = new ArrayList<TypeMenace>();
	
	private TypeMenace slctd = new TypeMenace();
	private TypeMenace slctdTb = new TypeMenace();
	private boolean  creer = true;
	private boolean  modifier = true;
	private boolean  supprimer = true;
	
	@PostConstruct
	public void postConstru() {
		
		chargeData();
		
	}
	
	public void chargeData(){
		getObjetListe().clear();
		List listTND = iservice.getObjects("TypeMenace");
		if(!listTND.isEmpty()){
			for (Iterator it = listTND.iterator(); it.hasNext();) {
				TypeMenace nd = (TypeMenace) it.next();
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
	public void create(){		if(getSlctd().getTmeLibelle().isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Saisir Une Valeur ! ", ""));  
		}else{
			getSlctd().setTmeCod(keyGen.getTypeID("M",1,3,"type_menace","tme_cod"));
			iservice.addObject(getSlctd());
		}
		chargeData();
		vider();
		creer = false;	
		modifier = true;
		supprimer = true;
	}
	
	public void update(){
		if(getSlctd().getTmeLibelle().isEmpty()){
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
		if(getSlctd().getTmeLibelle().isEmpty()){
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
		setSlctd(new TypeMenace());
	}

	public void onRowSelectDoss() {
		setSlctd(getSlctdTb());
		creer = true;	
		modifier = false;
		supprimer = false;
					
	}
	
	public void onRowUnSelect() {
		setSlctd(new TypeMenace());
		creer = true;	
		modifier = false;
		supprimer = false;
					
	}
	
	
	public List<TypeMenace> getObjetListe() {
		return objetListe;
	}

	public void setObjetListe(List<TypeMenace> objetListe) {
		this.objetListe = objetListe;
	}

	public TypeMenace getSlctd() {
		return slctd;
	}
	public void setSlctd(TypeMenace slctd) {
		this.slctd = slctd;
	}
	public TypeMenace getSlctdTb() {
		return slctdTb;
	}
	public void setSlctdTb(TypeMenace slctdTb) {
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


