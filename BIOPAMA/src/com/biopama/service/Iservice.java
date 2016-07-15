package com.biopama.service;

import java.util.List;

import com.biopama.model.Operateur;

public interface Iservice {
	/**
	 * Add Object
	 * 
	 * @param  Object Object
	 */
	public void addObject(Object object);
	
	/**
	 * Update Object
	 * 
	 * @param  Object Object
	 */
	public void updateObject(Object object);
	
	/**
	 * Delete Object
	 * 
	 * @param  Object Object
	 */
	public void deleteObject(Object object);
	
	/**
	 * Get Object
	 * 
	 * @param  int Object Id
	 */
	public Object getObjectById(int id, String objet);
	
	public Object getObjectById(String id, String objet);
	
	public Object getObjectById(String id, Object objet);
	
	/**
	 * Get Object List
	 * 
	 */
	public List<Object> getObjects(Object object);
	
	/**
	 * Get Object List
	 */
	public  List getObjects(String objet);
	
	public Operateur getUser(String login);
	
	public String getCodeTable(String pseudo, int tailleChar, int tailleNumber,
			String tableName, String column);

}
