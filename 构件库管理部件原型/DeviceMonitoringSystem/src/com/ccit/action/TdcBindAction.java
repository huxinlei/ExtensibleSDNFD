package com.ccit.action;

import java.util.*;
import com.ccit.model.TdcBind;
import com.ccit.manager.*;
import com.opensymphony.xwork2.*;

@SuppressWarnings({ "unchecked", "serial" })
public class TdcBindAction extends ActionSupport {
	private TdcBind util;
	private List<TdcBind> list;
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
		util = (TdcBind) objectManager.getObject(new TdcBind(), util.getId());
		return "saveOrUpdate";
	}

	public String getAllUtil() throws Exception{
		String sql = "";
		if (null != message && message.trim().length() > 0) {
			String _Str = new String(str.getBytes("iso-8859-1"), "utf-8");
			str = _Str;
			sql = "from TdcBind where " + message + " like '%" + str + "%'";
		} else {
			sql = "from TdcBind";
		}
		message = null;
		list = objectManager.getUtil(sql);
		return "find";
	}

	public TdcBind getUtil() {
		return util;
	}

	public void setUtil(TdcBind util) {
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

	public List<TdcBind> getList() {
		return list;
	}

	public void setList(List<TdcBind> list) {
		this.list = list;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

}
