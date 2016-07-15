package com.biopama.utilitaires;

import java.util.ArrayList;
import java.util.List;

import com.biopama.model.Affectation;
import com.biopama.model.AirProtegee;
import com.biopama.model.Document;
import com.biopama.model.Gouvernance;
import com.biopama.model.Mission;
import com.biopama.model.ReseauAp;

public class AireProtegeeRow {
	private AirProtegee airProtegee = new AirProtegee();
	private List<Affectation> listeAffectation = new ArrayList<Affectation>();
	private List<ReseauAp> listeReseauAp = new ArrayList<ReseauAp>();
	private List<Gouvernance> listeGouvernance = new ArrayList<Gouvernance>();
	private List<Mission> listeMission = new ArrayList<Mission>();
	private List<Document> listeDocument = new ArrayList<Document>();
	
	private int nOrdr =0;

	public AirProtegee getAirProtegee() {
		return airProtegee;
	}

	public void setAirProtegee(AirProtegee airProtegee) {
		this.airProtegee = airProtegee;
	}

	public List<Affectation> getListeAffectation() {
		return listeAffectation;
	}

	public void setListeAffectation(List<Affectation> listeAffectation) {
		this.listeAffectation = listeAffectation;
	}

	public List<ReseauAp> getListeReseauAp() {
		return listeReseauAp;
	}

	public void setListeReseauAp(List<ReseauAp> listeReseauAp) {
		this.listeReseauAp = listeReseauAp;
	}

	public List<Gouvernance> getListeGouvernance() {
		return listeGouvernance;
	}

	public void setListeGouvernance(List<Gouvernance> listeGouvernance) {
		this.listeGouvernance = listeGouvernance;
	}

	public List<Mission> getListeMission() {
		return listeMission;
	}

	public void setListeMission(List<Mission> listeMission) {
		this.listeMission = listeMission;
	}

	public List<Document> getListeDocument() {
		return listeDocument;
	}

	public void setListeDocument(List<Document> listeDocument) {
		this.listeDocument = listeDocument;
	}

	public int getnOrdr() {
		return nOrdr;
	}

	public void setnOrdr(int nOrdr) {
		this.nOrdr = nOrdr;
	}
	
	
}
