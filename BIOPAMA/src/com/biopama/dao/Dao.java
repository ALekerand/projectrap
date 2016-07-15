package com.biopama.dao;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.biopama.model.Operateur;

public class Dao implements IDao {

	private SessionFactory sessionFactory;

	@Override
	public void addObject(Object object) {
		// TODO Auto-generated method stub
		getSessionFactory().getCurrentSession().save(object);

	}

	@Override
	public void updateObject(Object object) {
		// TODO Auto-generated method stub
		getSessionFactory().getCurrentSession().update(object);
	}

	@Override
	public void deleteObject(Object Object) {
		// TODO Auto-generated method stub
		getSessionFactory().getCurrentSession().delete(Object);
	}

	@Override
	public Object getObjectById(int id, String objet) {
		// TODO Auto-generated method stub
		String query= "from" +" "+ objet + " "+ " where id =?";
		  List liste = getSessionFactory().getCurrentSession().createQuery(query).setParameter(0,id).list();
		  if (liste.size()==0){
		   return null;}
		return liste.get(0);
	}

	@Override
	public Object getObjectById(String id, String objet) {
		// TODO Auto-generated method stub
		String query= "from" +" "+ objet + " "+ " where id =?";
		  List liste = getSessionFactory().getCurrentSession().createQuery(query).setParameter(0,id).list();
		  if (liste.size()==0){
		   return null;}
		return liste.get(0);
	}

	@Override
	@Transactional
	public Object getObjectById(String id, Object objet) {
		
		Session session= getSessionFactory().getCurrentSession();
		Criteria crit = session.createCriteria((Class) objet);
		crit.add(Restrictions.eq("id", id));
		return (Object) crit.uniqueResult();
	}
	
	@Override
	public List<Object> getObjects(Object object) {
		// TODO Auto-generated method stub
		List list = getSessionFactory().getCurrentSession().createQuery("from"+" "+object).list();
			// TODO Auto-generated method stub
			return list ;
	}
	
	@Override
	public List getObjects(String objet) {
		// TODO Auto-generated method stub
		String query = "from"+" "+objet;
		List list = getSessionFactory().getCurrentSession().createQuery(query).list();
		return list;
	}
	
	/**
	 * @author User
	 *
	 */

	@Override
	@Transactional
	public String getCodeTable(String pseudo, int tailleChar, int size,
			String tableName, String column) {
		// Methode cr�ation d'un id code alphanumrique chronologique d'une
				// ligne de table ds la BD
				// SELECT MAX(to_number( SUBSTR(PAR_MATRICULE, 2, 10))) FROM T_PARTICIPANTS;

				String query = "SELECT MAX(CAST(SUBSTR(" + column + ", "
						+ (tailleChar + 1) + ", " + (size)
						+ ") AS INT)) FROM " + tableName + " WHERE "+column+" LIKE '"+pseudo+"%'";
	
		/*String query = "SELECT MAX(CAST(SUBSTRING(" + column + " FROM "
				+ (tailleChar + 1) + " FOR " + (size)
				+ ") AS int)) FROM " + tableName + " WHERE "+column+" LIKE '"+pseudo+"%'";
				*/
				try {
					Integer	bv = (Integer) getSessionFactory().getCurrentSession()
							.createSQLQuery(query).uniqueResult();
					
					String tC = String.valueOf(size-tailleChar);
					if (bv == null) {
						//int numOrdT = i+1;
						String numOrd= String.format("%0"+tC+"d", 1);
						System.out.println("///////Verification requette V null");
						System.out.println("///////Verification requette V null et pseudo = "+pseudo);
						String s = pseudo + numOrd;
						System.out.println("///////Verification requette V null et pseudo + numrd = "+s);
						return s;
					} else {
						//Integer v = bv.intValue();
						bv = bv +1;
						String numOrd= String.format("%0"+tC+"d", bv);
						System.out.println("///////Verification requette V non null");
						String s = pseudo + numOrd;
						return s;
					}
				} catch (HibernateException e) {
					e.printStackTrace();
					return "blag aaa";
				}
				// sess.close();

	}
	
	
	@Transactional
	public Operateur getUser(String login){
		/*String query = "SELECT * FROM " + "T_OPERATEUR" + " a  WHERE a." + key + " ='"
				+ id + "' ";
		TOperateur A = (TOperateur)getSessionFactory().getCurrentSession()
				.createSQLQuery(query).addEntity(TOperateur.class).uniqueResult();
		return A;*/
		Session session= getSessionFactory().getCurrentSession();
		Criteria crit = session.createCriteria(Operateur.class);
		crit.add(Restrictions.eq("opeLogin", login));
		System.out.println("Login de connexion TOperateur : Login "+((Operateur) crit.uniqueResult()).getOpeLogin()+" Nom "+((Operateur) crit.uniqueResult()).getOpeNom());
		return (Operateur) crit.uniqueResult();	
	}



	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
}
