package com.ccit.action;

import java.util.List;

import com.ccit.manager.ObjectManager;
import com.ccit.model.User;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("unchecked")
public class UserAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private User util;
	private List<User> list;
	private String message;
	private String str;
	private ObjectManager objectManager;

	public String saveOrUpdateObject() throws Exception {
		objectManager.saveOrUpdateObject(util);
		util = null;
		return getAllUtil();
	}

	public String deleteUtil() throws Exception {
		objectManager.deleteObject(util);
		return getAllUtil();
	}

	public String selectUtil() throws Exception {
		util = (User) objectManager.getObject(new User(), util.getId());
		return "saveOrUpdate";
	}

	public String getAllUtil() throws Exception {
		String sql = "";
		if (null != message && message.trim().length() > 0) {
			String _Str = new String(str.getBytes("iso-8859-1"), "utf-8");
			str = _Str;
			sql = "from User where " + message + " like '%" + str + "%'";
		} else {
			sql = "from User";
		}
		message = null;
		list = objectManager.getUtil(sql);
		return "find";
	}

	public User getUtil() {
		return util;
	}

	public void setUtil(User util) {
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

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public List<User> getList() {
		return list;
	}

	public void setList(List<User> list) {
		this.list = list;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

}
