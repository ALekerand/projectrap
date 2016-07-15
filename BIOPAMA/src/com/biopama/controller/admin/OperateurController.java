package com.biopama.controller.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.biopama.model.MotDePasse;
import com.biopama.model.Operateur;
import com.biopama.service.Iservice;

@Component
public class OperateurController {
	@Autowired
	Iservice iservice;
	
private List<Operateur> objetListe = new ArrayList<Operateur>();

	
	private Operateur slctd = new Operateur();
	private Operateur slctdTb = new Operateur();
	private boolean  creer = true;
	private boolean  modifier = true;
	private boolean  supprimer = true;
	private boolean vueMotpasse1 = true;
	private boolean vueMotpasse2 = false;
	private String motpasse;
	private String motpasse2;
	
	@PostConstruct
	public void postConstru() {	
		chargeData();
		
	}
	
	public void chargeData(){
		getObjetListe().clear();
		List listTND = iservice.getObjects("Operateur");
		if(!listTND.isEmpty()){
			for (Iterator it = listTND.iterator(); it.hasNext();) {
				Operateur nd = (Operateur) it.next();
				getObjetListe().add(nd);
	
			}
		}
	}
	
	
	public void checkMotpasse(){
	if(vueMotpasse1==true){
		vueMotpasse2 = false;
	}else{
		vueMotpasse2 = true;
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
	public void checkPasse(){
		if(getSlctd().getMotDePasses()!=null){
			for(MotDePasse mdp: getSlctd().getMotDePasses()){
				if(mdp.getMdpStatut()==true){
					setMotpasse(mdp.getMdpMotdepasse());	
					setMotpasse2(mdp.getMdpMotdepasse());
				}
				
			}
		}
	}
	
	public void checkLogin(){
		
			for(Operateur lg: objetListe){
				if(lg.getOpeLogin().equalsIgnoreCase(getSlctd().getOpeLogin())){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Login déja utilisé ! ", ""));  
					creer = false;	
				}
				
			}
		
	}
	
	//Méthode CRUD
	public void create(){
		if(getSlctd().getOpeMatricule().isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Saisir Une Valeur ! ", ""));  
		}else{
			iservice.addObject(getSlctd());
			//add Mot de passe
			MotDePasse mdp = new MotDePasse();
			mdp.setOperateur(getSlctd());
			mdp.setMdpMotdepasse(motpasse);
			mdp.setMdpDate(Calendar.getInstance().getTime());
			mdp.setMdpStatut(true);
			iservice.addObject(mdp);
		}
		chargeData();
		vider();
		creer = false;	
		modifier = true;
		supprimer = true;
	}
	
	public void update(){
		if(getSlctd().getOpeMatricule().isEmpty()){
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
		if(getSlctd().getOpeMatricule().isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Selectionner Un élément ! ", ""));  
		}else{
			
			for(MotDePasse mdp: getSlctd().getMotDePasses()){
				iservice.deleteObject(mdp);
				}
			iservice.deleteObject(getSlctd());
			}
		
		chargeData();
		vider();
		creer = true;	
		modifier = true;
		supprimer = true;
	}
	
	public void viderCamps(){
		vider();
		creer = true;	
		modifier = true;
		supprimer = true;
	}
	
	public void vider(){
		setSlctd(new Operateur());
		setMotpasse("");
	}

	public void onRowSelectDoss() {
		setSlctd(getSlctdTb());
		checkPasse();
		creer = true;	
		modifier = false;
		supprimer = false;
					
	}
	
	public void onRowUnSelect() {
		setSlctd(new Operateur());
		creer = true;	
		modifier = false;
		supprimer = false;
		setMotpasse("");
					
	}
	
	
	public List<Operateur> getObjetListe() {
		return objetListe;
	}

	public void setObjetListe(List<Operateur> objetListe) {
		this.objetListe = objetListe;
	}

	public Operateur getSlctd() {
		return slctd;
	}
	public void setSlctd(Operateur slctd) {
		this.slctd = slctd;
	}
	public Operateur getSlctdTb() {
		return slctdTb;
	}
	public void setSlctdTb(Operateur slctdTb) {
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

	public boolean isVueMotpasse1() {
		return vueMotpasse1;
	}

	public void setVueMotpasse1(boolean vueMotpasse1) {
		this.vueMotpasse1 = vueMotpasse1;
	}

	public boolean isVueMotpasse2() {
		return vueMotpasse2;
	}

	public void setVueMotpasse2(boolean vueMotpasse2) {
		this.vueMotpasse2 = vueMotpasse2;
	}

	public String getMotpasse() {
		return motpasse;
	}

	public void setMotpasse(String motpasse) {
		this.motpasse = motpasse;
	}

	public String getMotpasse2() {
		return motpasse2;
	}

	public void setMotpasse2(String motpasse2) {
		this.motpasse2 = motpasse2;
	}
	
	
}


