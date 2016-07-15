package com.biopama.controller.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.biopama.model.TypeDocument;
import com.biopama.service.Iservice;
import com.biopama.utilitaires.KeyGen;

@Component
public class TypeDocController {
	@Autowired
	Iservice iservice;
	@Autowired
	KeyGen keyGen;
	
private List<TypeDocument> objetListe = new ArrayList<TypeDocument>();
	
	private TypeDocument slctd = new TypeDocument();
	private TypeDocument slctdTb = new TypeDocument();
	private boolean  creer = true;
	private boolean  modifier = true;
	private boolean  supprimer = true;
	
	@PostConstruct
	public void postConstru() {
		
		chargeData();
		
	}
	
	public void chargeData(){
		getObjetListe().clear();
		List listTND = iservice.getObjects("TypeDocument");
		if(!listTND.isEmpty()){
			for (Iterator it = listTND.iterator(); it.hasNext();) {
				TypeDocument nd = (TypeDocument) it.next();
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
	public void create(){		if(getSlctd().getTdcLibelle().isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Saisir Une Valeur ! ", ""));  
		}else{
			getSlctd().setTdcCod(keyGen.getTypeID("TD",2,5,"type_document","tdc_cod"));
			iservice.addObject(getSlctd());
		}
		chargeData();
		vider();
		creer = false;	
		modifier = true;
		supprimer = true;
	}
	
	public void update(){
		if(getSlctd().getTdcLibelle().isEmpty()){
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
		if(getSlctd().getTdcLibelle().isEmpty()){
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
		setSlctd(new TypeDocument());
	}

	public void onRowSelectDoss() {
		setSlctd(getSlctdTb());
		creer = true;	
		modifier = false;
		supprimer = false;
					
	}
	
	public void onRowUnSelect() {
		setSlctd(new TypeDocument());
		creer = true;	
		modifier = false;
		supprimer = false;
					
	}
	
	
	public List<TypeDocument> getObjetListe() {
		return objetListe;
	}

	public void setObjetListe(List<TypeDocument> objetListe) {
		this.objetListe = objetListe;
	}

	public TypeDocument getSlctd() {
		return slctd;
	}
	public void setSlctd(TypeDocument slctd) {
		this.slctd = slctd;
	}
	public TypeDocument getSlctdTb() {
		return slctdTb;
	}
	public void setSlctdTb(TypeDocument slctdTb) {
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


