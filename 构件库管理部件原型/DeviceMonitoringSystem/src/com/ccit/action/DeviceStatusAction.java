package com.ccit.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.ccit.manager.ObjectManager;
import com.ccit.model.Device;
import com.ccit.model.DeviceStatus;
import com.ccit.model.DeviceStatusHis;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings( { "unchecked", "serial" })
public class DeviceStatusAction extends ActionSupport {
	private DeviceStatus util;
	private List<DeviceStatus> list;
	private List<Device> listDevice;
	private String message;
	private String str;
	private ObjectManager objectManager;

	public String initUtil() throws Exception {
		listDevice = objectManager.getUtil("from Device");
		util = null;
		return "saveOrUpdate";
	}

	public String showDeviceStatus() throws Exception {
		List<DeviceStatus> listDeviceStatus = objectManager
				.getUtil("from DeviceStatus");
		DeviceStatusHis dsh = null;
		if (null != listDeviceStatus && listDeviceStatus.size() > 0) {
			for(DeviceStatus ds:listDeviceStatus){
				dsh = new DeviceStatusHis();
				dsh.setDevice(ds.getDevice());
				dsh.setS_0(ds.getS_0());
				dsh.setS_1(ds.getS_1());
				dsh.setS_2(ds.getS_2());
				dsh.setS_3(ds.getS_3());
				dsh.setS_4(ds.getS_4());
				dsh.setS_5(ds.getS_5());
				dsh.setS_6(ds.getS_6());
				dsh.setS_7(ds.getS_7());
				dsh.setS_8(ds.getS_8());
				objectManager.saveOrUpdateObject(dsh);
				objectManager.deleteObject(ds);
			}
		}
		Device device = (Device) objectManager.getObject(new Device(), util
				.getId());
		DeviceStatus ds = new DeviceStatus();
		Random r = new Random();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		sdf = new SimpleDateFormat("HH:mm");
		String time = sdf.format(new Date());

		ds.setDevice(device);
		ds.setS_0(time);
		ds.setS_1(date);
		ds.setS_2("" + r.nextInt(30));
		ds.setS_3("" + r.nextInt(30));
		ds.setS_4("" + r.nextInt(30));
		ds.setS_5("" + r.nextInt(30));
		ds.setS_6("" + r.nextInt(30));
		ds.setS_7("" + r.nextInt(30));
		ds.setS_8("" + r.nextInt(50));
		objectManager.saveOrUpdateObject(ds);
		return getAllUtil();
	}

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
		listDevice = objectManager.getUtil("from Device");
		util = (DeviceStatus) objectManager.getObject(new DeviceStatus(), util
				.getId());
		return "saveOrUpdate";
	}

	public String getAllUtil() throws Exception {
		// Map session = ActionContext.getContext().getSession();
		// String type = (String) session.get("type");
		// int id = (Integer) session.get("id");
		String sql = "";
		if (null != message && message.trim().length() > 0) {
			String _Str = new String(str.getBytes("iso-8859-1"), "utf-8");
			str = _Str;
			sql = "from DeviceStatus where " + message + " like '%" + str
					+ "%'";
		} else {
			sql = "from DeviceStatus";
			// if (!"admin".equals(type)) {
			// sql = sql + " where user.id = " + id;
			// }
		}
		message = null;
		list = objectManager.getUtil(sql);
		return "find";
	}

	public DeviceStatus getUtil() {
		return util;
	}

	public void setUtil(DeviceStatus util) {
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

	public List<DeviceStatus> getList() {
		return list;
	}

	public void setList(List<DeviceStatus> list) {
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
