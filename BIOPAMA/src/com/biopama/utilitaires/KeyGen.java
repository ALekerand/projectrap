package com.biopama.utilitaires;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.biopama.service.Iservice;

@Component
public class KeyGen {
	@Autowired
	Iservice iservice;
	
	public String getApID(String paysCode, String TypeAp) {
		String p = paysCode.substring(0, 3);
		String pseudo = p + TypeAp;// 4 il reste 6 sur 10
		String IdAp = iservice.getCodeTable(pseudo, 4, 10,
				"air_protegee", "aip_cod");
		System.out.println("****Id de l' Aire Protegée =" + IdAp+" La Taille: "+IdAp.length());
		return IdAp;
	}
	
	public String getFonctionID(String libCourtPays, String codeTypeFonc) {
		
		String pseudo = libCourtPays.substring(0, 3)+codeTypeFonc;//6 il reste 5
		String IdFonc = iservice.getCodeTable(pseudo, 6, 10,
				"fonction", "fon_cod");
		System.out.println("****Id de la Fonction =" + IdFonc);
		return IdFonc;
	}
	
	public String getDossierID(String imputationId) {
		
		String pseudo = imputationId+"D";//reste 1
		String IdDoss = iservice.getCodeTable(pseudo, 14, 16,
				"T_DOSSIERS", "CODE_DOS");
		System.out.println("****Id du Dossier =" + IdDoss);
		return IdDoss;
	}
	
public String getTypeID(String pseudo, int taillePseudo, int taille, String table, String id) {
		
		String IdDoss = iservice.getCodeTable(pseudo, taillePseudo, taille,
				table, id);
		System.out.println("****Id du Type  =" + IdDoss);
		return IdDoss;
	}
	
}
