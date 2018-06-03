package com.ccit.action;

import java.util.List;
import java.util.Map;

import com.ccit.manager.ObjectManager;
import com.ccit.model.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private User util;
	private String errMsg;
	private String code;
	private ObjectManager objectManager;

	@SuppressWarnings("unchecked")
	public String loginAction() throws Exception {
		Map session = ActionContext.getContext().getSession();
		String random = (String) session.get("code");
		if (random != null && random.equals(code)) {
			session.remove("code");
			boolean flag = objectManager.checkUser(util);
			if (flag) {
				session.put("username", util.getS_1());
				session.put("type", util.getS_11());
				session.put("state", util.getS_12());
				session.put("id", util.getId());
				return SUCCESS;
			} else {
				errMsg = "登录失败！";
				return "fail";
			}
		} else {
			errMsg = "效验码错误！";
			return "fail";
		}

	}

	@SuppressWarnings("unchecked")
	public String registerAction() throws Exception {
		List list = objectManager.getUtil("from User where s_1 = '"
				+ util.getS_1() + "'");
		if (list != null && list.size() > 0) {
			errMsg = "该用户名已使用！";
			return "fail";
		} else {
			objectManager.saveObject(util);
			errMsg = "";
			return SUCCESS;
		}
	}

	@SuppressWarnings("unchecked")
	public String initInfo() throws Exception {
		Map session = ActionContext.getContext().getSession();
		int id = (Integer) session.get("id");
		util = (User) objectManager.getObject(new User(), id);
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String updateInfo() throws Exception {
		objectManager.updateObject(util);
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String updatePwd() throws Exception {
		Map session = ActionContext.getContext().getSession();
		Integer id = (Integer) session.get("id");

		String oldpwd = util.getS_3();
		String pwd = util.getS_4();
		String repwd = util.getS_5();

		if (pwd != null && pwd.equals(repwd)) {
			util = (User) objectManager.getObject(new User(), id);
			if (util.getS_2().equals(oldpwd)) {
				util.setS_2(repwd);
				objectManager.updateObject(util);
			} else {
				errMsg = "旧密码错误";
				return "fail";
			}
			errMsg = "";
			return SUCCESS;
		} else {
			errMsg = "两次密码不一致！";
			return "fail";
		}
	}

	public User getUtil() {
		return util;
	}

	public void setUtil(User util) {
		this.util = util;
	}

	 
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
