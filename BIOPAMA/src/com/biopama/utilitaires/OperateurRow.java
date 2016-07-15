package com.biopama.utilitaires;

import java.util.ArrayList;
import java.util.List;

import com.biopama.model.Assignation;
import com.biopama.model.Fonction;
import com.biopama.model.MotDePasse;
import com.biopama.model.Operateur;

public class OperateurRow {
	private Operateur operateur = new Operateur();
	private MotDePasse motdepasse = new MotDePasse();
	private Fonction fonctions = new Fonction();
	private Assignation assignation = new Assignation();
	private List<MotDePasse> mdpListe = new ArrayList<MotDePasse>();
	private List<Assignation> assListe = new ArrayList<Assignation>();
	private String statut;
	private int nOrdr =0;
	
	
	public Operateur getOperateur() {
		return operateur;
	}
	public void setOperateur(Operateur operateur) {
		this.operateur = operateur;
	}
	public MotDePasse getMotdepasse() {
		return motdepasse;
	}
	public void setMotdepasse(MotDePasse motdepasse) {
		this.motdepasse = motdepasse;
	}
	public Assignation getAssignation() {
		return assignation;
	}
	public void setAssignation(Assignation assignation) {
		this.assignation = assignation;
	}
	public Fonction getFonctions() {
		return fonctions;
	}
	public void setFonctions(Fonction fonctions) {
		this.fonctions = fonctions;
	}
	public int getnOrdr() {
		return nOrdr;
	}
	public void setnOrdr(int nOrdr) {
		this.nOrdr = nOrdr;
	}
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	public List<MotDePasse> getMdpListe() {
		return mdpListe;
	}
	public void setMdpListe(List<MotDePasse> mdpListe) {
		this.mdpListe = mdpListe;
	}
	public List<Assignation> getAssListe() {
		return assListe;
	}
	public void setAssListe(List<Assignation> assListe) {
		this.assListe = assListe;
	}
	
	
}
