package com.biopama.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.biopama.dao.IDao;
import com.biopama.model.Operateur;

@Transactional(readOnly = true)
public class Service implements Iservice {

	// DAO is injected...
		IDao Dao;
		
		@Transactional(readOnly = false)
		@Override
		public void addObject(Object object) {
			// TODO Auto-generated method stub
			getDao().addObject(object);
		}
		
		
		@Transactional(readOnly = false)
		@Override
		public void updateObject(Object object) {
			// TODO Auto-generated method stub
			getDao().updateObject(object);
		}
		
		@Transactional(readOnly = false)
		@Override
		public void deleteObject(Object object) {
			// TODO Auto-generated method stub
			getDao().deleteObject(object);
		}

		@Override
		public Object getObjectById(int id, String objet) {
			// TODO Auto-generated method stub
			return getDao().getObjectById(id, objet);
		}

		@Override
		public Object getObjectById(String id, String objet) {
			// TODO Auto-generated method stub
			return getDao().getObjectById(id, objet);
		}

		@Override
		@Transactional
		public Object getObjectById(String id, Object objet) {
			// TODO Auto-generated method stub
			return getDao().getObjectById(id, objet);
			 
		}
		
		@Override
		public List<Object> getObjects(Object object) {
			// TODO Auto-generated method stub
			return getDao().getObjects(object);
		}

		@Override
		@Transactional
		public Operateur getUser(String login){
			return getDao().getUser(login);
		}
		
		public String getCodeTable(String pseudo, int tailleChar, int tailleNumber,
				String tableName, String column){
			return getDao().getCodeTable(pseudo, tailleChar, tailleNumber, tableName, column);
		}
		
		public IDao getDao() {
			return Dao;
		}

		public void setDao(IDao dao) {
			Dao = dao;
		}


		@Override
		public List getObjects(String objet) {
			// TODO Auto-generated method stub
			return getDao().getObjects(objet);
		}

}
