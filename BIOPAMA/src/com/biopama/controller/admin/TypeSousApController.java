package com.biopama.controller.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.biopama.model.TypeSousAp;
import com.biopama.service.Iservice;
import com.biopama.utilitaires.KeyGen;

@Component
public class TypeSousApController {
	@Autowired
	Iservice iservice;
	@Autowired
	KeyGen keyGen;
	
private List<TypeSousAp> objetListe = new ArrayList<TypeSousAp>();
	
	private TypeSousAp slctd = new TypeSousAp();
	private TypeSousAp slctdTb = new TypeSousAp();
	private boolean  creer = true;
	private boolean  modifier = true;
	private boolean  supprimer = true;
	
	@PostConstruct
	public void postConstru() {
		
		chargeData();
		
	}
	
	public void chargeData(){
		getObjetListe().clear();
		List listTND = iservice.getObjects("TypeSousAp");
		if(!listTND.isEmpty()){
			for (Iterator it = listTND.iterator(); it.hasNext();) {
				TypeSousAp nd = (TypeSousAp) it.next();
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
	public void create(){		if(getSlctd().getLibelleTypSousAp().isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Saisir Une Valeur ! ", ""));  
		}else{
			getSlctd().setCodeTypSousAp(keyGen.getTypeID("SAP",3,15,"type_sous_ap","code_typ_sous_ap"));
			iservice.addObject(getSlctd());
		}
		chargeData();
		vider();
		creer = false;	
		modifier = true;
		supprimer = true;
	}
	
	public void update(){
		if(getSlctd().getLibelleTypSousAp().isEmpty()){
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
		if(getSlctd().getLibelleTypSousAp().isEmpty()){
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
		setSlctd(new TypeSousAp());
	}

	public void onRowSelectDoss() {
		setSlctd(getSlctdTb());
		creer = true;	
		modifier = false;
		supprimer = false;
					
	}
	
	public void onRowUnSelect() {
		setSlctd(new TypeSousAp());
		creer = true;	
		modifier = false;
		supprimer = false;
					
	}
	
	
	public List<TypeSousAp> getObjetListe() {
		return objetListe;
	}

	public void setObjetListe(List<TypeSousAp> objetListe) {
		this.objetListe = objetListe;
	}

	public TypeSousAp getSlctd() {
		return slctd;
	}
	public void setSlctd(TypeSousAp slctd) {
		this.slctd = slctd;
	}
	public TypeSousAp getSlctdTb() {
		return slctdTb;
	}
	public void setSlctdTb(TypeSousAp slctdTb) {
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


