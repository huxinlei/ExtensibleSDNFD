package com.ccit.action;

import java.util.*;
import com.ccit.model.Device;
import com.ccit.model.DeviceStatusHis;
import com.ccit.manager.ObjectManager;
import com.opensymphony.xwork2.*;

@SuppressWarnings({ "unchecked", "serial" })
public class DeviceStatusHisAction extends ActionSupport {
	private DeviceStatusHis util;
	private List<DeviceStatusHis> list;
	private List<Device> listDevice;
	private String message;
	private String str;
	private ObjectManager objectManager;

	public String initUtil() throws Exception{
		listDevice = objectManager.getUtil("from Device");
		util = null;
		return "saveOrUpdate";
	}

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
		listDevice = objectManager.getUtil("from Device");
		util = (DeviceStatusHis) objectManager.getObject(new DeviceStatusHis(), util.getId());
		return "saveOrUpdate";
	}

	public String getAllUtil() throws Exception{
		//Map session = ActionContext.getContext().getSession();
		//String type = (String) session.get("type");
		//int id = (Integer) session.get("id");
		String sql = "";
		if (null != message && message.trim().length() > 0) {
			String _Str = new String(str.getBytes("iso-8859-1"), "utf-8");
			str = _Str;
			sql = "from DeviceStatusHis where " + message + " like '%" + str + "%'";
		} else {
			sql = "from DeviceStatusHis";
			//if (!"admin".equals(type)) {
			//	sql = sql + " where user.id = " + id;
			//}
		}
		message = null;
		list = objectManager.getUtil(sql);
		return "find";
	}

	public DeviceStatusHis getUtil() {
		return util;
	}

	public void setUtil(DeviceStatusHis util) {
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

	public List<DeviceStatusHis> getList() {
		return list;
	}

	public void setList(List<DeviceStatusHis> list) {
		this.list = list;
	}

	public List<Device> getListDevice() {
		return listDevice;
	}

	public void setListDevice(List<Device> listDevice) {
		this.listDevice = listDevice;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

}
