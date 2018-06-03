package com.ccit.manager;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ccit.model.User;
import com.ccit.util.HibernateUtils;

@SuppressWarnings("unchecked")
public class ObjectManagerImpl implements ObjectManager {

	public void saveObject(Object object) {
		Session session = null;
		try {
			session = HibernateUtils.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.save(object);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}
	
	public void saveOrUpdateObject(Object object) {
		Session session = null;
		try {
			session = HibernateUtils.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.saveOrUpdate(object);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	public void updateObject(Object object) {
		Session session = null;
		try {
			session = HibernateUtils.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.update(object);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	public void deleteObject(Object object) {
		Session session = null;
		try {
			session = HibernateUtils.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.delete(object);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
	}

	public List getObjectList(Object object) {
		Session session = null;
		List list = null;
		try {
			session = HibernateUtils.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Query query = session.createQuery("from "
					+ object.getClass().getName());
			list = query.list();
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
		}
	}

	public Object getObject(Object object, int i) {
		Session session = HibernateUtils.getSession();
		session.beginTransaction();
		object = session.load(object.getClass(), i);
		session.getTransaction().commit();
		return object;
	}

	public List getUtil(String hql) {
		Session session = null;
		List list = null;
		try {
			session = HibernateUtils.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Query query = session.createQuery(hql);
			list = query.list();
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
		}
	}

	public boolean checkUser(User user) {
		Session session = null;
		List list = null;
		try {
			session = HibernateUtils.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Query query = session
					.createQuery("from User where s_1 = ? and s_2 = ? and s_11 = ?");
			query.setString(0, user.getS_1());
			query.setString(1, user.getS_2());
			query.setString(2, user.getS_11());
			list = query.list();
			session.getTransaction().commit();
			if (null != list && list.size() > 0) {
				User tmp = (User) list.get(0);
				user.setId(tmp.getId());
				user.setS_1(tmp.getS_1());
				user.setS_11(tmp.getS_11());
				user.setS_12(tmp.getS_12());
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean checkUsername(User user) {
		Session session = null;
		List list = null;
		try {
			session = HibernateUtils.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Query query = session.createQuery("from User where s_1 = ?");
			query.setString(0, user.getS_1());
			list = query.list();
			session.getTransaction().commit();
			if (null != list && list.size() > 0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
 

}
