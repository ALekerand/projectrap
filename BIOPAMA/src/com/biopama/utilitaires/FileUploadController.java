package com.biopama.utilitaires;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.application.FacesMessage;
//import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//@ManagedBean(name="fileUploadController")
@Component
@Scope(value="session")
public class FileUploadController {
	private static  Logger logger=Logger.getLogger(FileUploadController.class);

	private String destination="c:\\SIGMiC\\upload\\";
	
	private UploadedFile file; 
	private String fileName;
	private String codeImputation;
	
	public UploadedFile getFile() {  
	    return file;  
	}  
	
	public void setFile(UploadedFile file) {  
	    this.file = file;  
	}  

	 public void handleFileUpload(FileUploadEvent event) {
		 file=event.getFile();
		 
	        FacesMessage message = new FacesMessage("Succesful", fileName + " is uploaded.");
	        FacesContext.getCurrentInstance().addMessage(null, message);
	      System.out.print("Test 2 3 4 file.getFileName()"+event.getFile().getFileName()); 
	    }
	
	
	public void upload() { 	
		try {
			 String name = file.getFileName().substring(0,file.getFileName().length()-4)+"-"+getCodeImputation()+".pdf";
			 fileName = name;
			System.out.print("getFileName()"+fileName);
			
		if(copyFile(fileName, file.getInputstream())){
			FacesMessage msg = new FacesMessage("Succès! ", fileName + " a été téléchargé!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		else {
			FacesMessage msg = new FacesMessage("Echec du chargement! ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}		
		} catch (IOException e) {		
		e.printStackTrace();		
		}	
			
		
	}
	public String getNomFile(String nom){
		//String NomFile=null;

		return nom;
	}
	
	public static boolean contains(String str, String searchStr) {
	      if (str == null || searchStr == null) {
	          return false;
	      }
	      return str.indexOf(searchStr) >= 0;
	  }
	
	public boolean copyFile(String fileName, InputStream in) {
	
		try {
			File dir = new File("c:\\SIGMiC\\upload\\");
			dir.mkdirs();	
			
		//FileWriter newJsp = new FileWriter("c:\\upload\\temp\\test.txt");
		OutputStream out = new FileOutputStream(new File(destination + fileName));
		
		
		int read = 0;
		
		byte[] bytes = new byte[1024];
		
		while ((read = in.read(bytes)) != -1) {
		
		out.write(bytes, 0, read);
		
		}
		
		in.close();
		
		out.flush();
		
		out.close();
		
		System.out.println("Un nouveau fichier a été ajouté dans le dossier c:\\upload\\temp\\ !");
		return true;
		
		} catch (IOException e) {
		
		System.out.println(e.getMessage());
		return false;
		
		}
	
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCodeImputation() {
		return codeImputation;
	}

	public void setCodeImputation(String codeImputation) {
		this.codeImputation = codeImputation;
	}

}
