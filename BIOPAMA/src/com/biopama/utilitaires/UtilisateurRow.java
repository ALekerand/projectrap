package com.biopama.utilitaires;

import com.biopama.model.Fonction;
import com.biopama.model.MotDePasse;
import com.biopama.model.Operateur;

public class UtilisateurRow {
	private Operateur utilisateur = new Operateur();
	private MotDePasse motDePasses = new MotDePasse();
	private Fonction fonctions = new Fonction();
	private String statut;
	private int nOrdr =0;
	
	public Operateur getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Operateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	

	public MotDePasse getMotdepasses() {
		return motDePasses;
	}
	public void setMotdepasses(MotDePasse motDePasses) {
		this.motDePasses = motDePasses;
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
	
	
}
