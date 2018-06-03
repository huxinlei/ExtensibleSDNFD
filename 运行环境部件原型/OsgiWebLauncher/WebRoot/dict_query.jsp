<%@page import="demo.osgi.dictquery.QueryService"%>
<%@page import="imu.edu.cn.osgilauncher.OSGiAdmin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>dict query</title>
</head>
<body>
<%
String key = request.getParameter("key");
if (key == null) key = "";
%>
<form method="GET">
<input type="text" name="key" value="<%=key%>"/>(可输入 temp、test)&nbsp;&nbsp;<input type="submit" value="查询"/><br/>
</form>
<div><%=key + ":"%></div>
<%
if (!"".equals(key)) {
	QueryService queryService = OSGiAdmin.getService(QueryService.class);
	if (queryService != null) {
		String value = queryService.queryWord(key);
		if (value == null) value="";
		out.println(value);
	}
	else {
		out.println("服务不存在");
	}
}
%>

</body>
</html>