<%@page import="imu.edu.cn.osgilauncher.OSGiAdmin"%>
<%@page import="net.sf.sojo.interchange.json.JsonSerializer"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String msg = "";
boolean success = true;
try {
	long id = Long.parseLong(request.getParameter("id"));
	String op = request.getParameter("op");
	//System.out.println(id+","+op);
	if (id == 0) {
		if ("startup".equals(op)) OSGiAdmin.startup();
		else if ("shutdown".equals(op)) OSGiAdmin.shutdown();
		else {
			success = false;
			msg = "不支持该操作";
		}
	}
	else if (id == -1) {
		String path = request.getParameter("path");
		if ("install".equals(op)) {
			OSGiAdmin.installBundle(path);
		}
	}
	else {
		if ("uninstall".equals(op)) OSGiAdmin.uninstallBundle(id);
		else if ("start".equals(op)) OSGiAdmin.startBundle(id);
		else if ("stop".equals(op)) OSGiAdmin.stopBundle(id);
		else {
			success = false;
			msg = "不支持该操作";
		}
	}
	
} catch (Exception e) {
	success = false;
	msg = e.getMessage();
}

Map result = new HashMap();
result.put("success", success);
result.put("msg", msg);

JsonSerializer jsonSer = new JsonSerializer();
String jsonStr = jsonSer.serialize(result).toString();

response.addHeader("Content-type", "application/json");
out.println(jsonStr);
%>