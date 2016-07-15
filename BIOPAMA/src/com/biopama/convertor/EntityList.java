package com.biopama.convertor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.biopama.model.Pays;
import com.biopama.model.TypeFonction;
import com.biopama.service.Iservice;


@Component
public class EntityList {
	@Autowired
	Iservice iservice;

	//les listes des listBoxes
	
	private List<TypeFonction> listTypeFonction = new ArrayList<TypeFonction>();
	private List<Pays> listPays = new ArrayList<Pays>();
	

	
	@PostConstruct
	public void postConstru() {
		List listTP = getIservice().getObjects("TypeFonction");
		for (Iterator it = listTP.iterator(); it.hasNext();) {
			TypeFonction TP = (TypeFonction) it.next();
			getListTypeFonction().add(TP);
		}
			//ordonner les TTypeParticipants
			 Collections.sort(getListTypeFonction(), new Comparator<TypeFonction>() {
			        @Override public int compare(TypeFonction p1, TypeFonction p2) {
			        	int n = p1.getTyfLibelle().compareTo(p2.getTyfLibelle());
			        	return n; // Ascending  
			        }

			    });
		
	
		List listNM = getIservice().getObjects("Pays");
		for (Iterator it = listNM.iterator(); it.hasNext();) {
			Pays NM = (Pays) it.next();
			getListPays().add(NM);
		}	
		//ordonner les Pays
		 Collections.sort(getListPays(), new Comparator<Pays>() {
		        @Override public int compare(Pays p1, Pays p2) {
		        	int n = p2.getPayLibLong().compareTo(p1.getPayLibLong());
		        	return n; // des  
		        }

		    });
		 
		
		
	}
	
	public void chargePays(){
		getListPays().clear();
		List listNM = getIservice().getObjects("Pays");
		for (Iterator it = listNM.iterator(); it.hasNext();) {
			Pays NM = (Pays) it.next();
			getListPays().add(NM);
		}	
		//ordonner les Pays
		 Collections.sort(getListPays(), new Comparator<Pays>() {
		        @Override public int compare(Pays p1, Pays p2) {
		        	int n = p2.getPayLibLong().compareTo(p1.getPayLibLong());
		        	return n; // des  
		        }

		    });
		 	
	}
	
	

	
	
	public List<Pays> getListPays() {
		return listPays;
	}

	public void setListPays(List<Pays> listPays) {
		this.listPays = listPays;
	}

	public List<TypeFonction> getListTypeFonction() {
		return listTypeFonction;
	}

	public void setListTypeFonction(List<TypeFonction> listTypeFonction) {
		this.listTypeFonction = listTypeFonction;
	}

	public Iservice getIservice() {
		return iservice;
	}

	public void setIservice(Iservice iservice) {
		this.iservice = iservice;
	}

}
