package com.biopama.utilitaires;

import java.io.Serializable;

import org.apache.log4j.Logger;



public class AutorityClass implements Serializable{
	private static final long serialVersionUID = 1L;
private boolean menuADM = false;
private boolean menuADA = false;
private boolean menuRAP = false;
private boolean menuPAR = false;
private boolean menuCOV = false;
private boolean menuOBS = false;
public boolean isMenuADM() {
	return menuADM;
}
public void setMenuADM(boolean menuADM) {
	this.menuADM = menuADM;
}
public boolean isMenuADA() {
	return menuADA;
}
public void setMenuADA(boolean menuADA) {
	this.menuADA = menuADA;
}
public boolean isMenuRAP() {
	return menuRAP;
}
public void setMenuRAP(boolean menuRAP) {
	this.menuRAP = menuRAP;
}
public boolean isMenuPAR() {
	return menuPAR;
}
public void setMenuPAR(boolean menuPAR) {
	this.menuPAR = menuPAR;
}
public boolean isMenuCOV() {
	return menuCOV;
}
public void setMenuCOV(boolean menuCOV) {
	this.menuCOV = menuCOV;
}
public boolean isMenuOBS() {
	return menuOBS;
}
public void setMenuOBS(boolean menuOBS) {
	this.menuOBS = menuOBS;
}


}
