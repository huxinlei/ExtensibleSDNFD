package com.ccit.manager;

import java.util.List;

import com.ccit.model.User;

@SuppressWarnings("unchecked")
public interface ObjectManager {

	public void saveObject(Object object);
	
	public void saveOrUpdateObject(Object object);

	public List getObjectList(Object object);

	public Object getObject(Object object, int i);

	public void updateObject(Object object);

	public void deleteObject(Object object);

	public List getUtil(String hql);

	public boolean checkUser(User user);

	public boolean checkUsername(User user);

}
