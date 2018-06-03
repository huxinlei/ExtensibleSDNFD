package com.ccit.action;

import java.util.*;
import com.ccit.model.Constructor;
import com.ccit.manager.*;
import com.opensymphony.xwork2.*;

@SuppressWarnings({ "unchecked", "serial" })
public class ConstructorAction extends ActionSupport {
	private Constructor util;
	private List<Constructor> list;
	private String message;
	private String str;
	private ObjectManager objectManager;

	public String saveOrUpdateObject() throws Exception{
		objectManager.saveOrUpdateObject(util);
		util = null;
		return getAllUtil();
	}

	public String deleteUtil() throws Exception{
		objectManager.deleteObject(util);
		return getAllUtil();
	}

	public String selectUtil() throws Exception{
		util = (Constructor) objectManager.getObject(new Constructor(), util.getId());
		return "saveOrUpdate";
	}

	public String getAllUtil() throws Exception{
		String sql = "";
		if (null != message && message.trim().length() > 0) {
			String _Str = new String(str.getBytes("iso-8859-1"), "utf-8");
			str = _Str;
			sql = "from Constructor where " + message + " like '%" + str + "%'";
		} else {
			sql = "from Constructor";
		}
		message = null;
		list = objectManager.getUtil(sql);
		return "find";
	}

	public Constructor getUtil() {
		return util;
	}

	public void setUtil(Constructor util) {
		this.util = util;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ObjectManager getObjectManager() {
		return objectManager;
	}

	public void setObjectManager(ObjectManager objectManager) {
		this.objectManager = objectManager;
	}

	public List<Constructor> getList() {
		return list;
	}

	public void setList(List<Constructor> list) {
		this.list = list;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

}
