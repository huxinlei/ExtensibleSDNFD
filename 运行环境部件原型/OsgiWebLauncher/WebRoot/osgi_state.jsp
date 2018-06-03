<%@page import="imu.edu.cn.osgilauncher.BundleConfig"%>
<%@page import="imu.edu.cn.osgilauncher.OSGiConfig"%>
<%@page import="java.util.ArrayList"%>
<%@page import="imu.edu.cn.osgilauncher.BundleInfo"%>
<%@page import="java.util.List"%>
<%@page import="net.sf.sojo.interchange.json.JsonSerializer"%>
<%@page import="imu.edu.cn.osgilauncher.OSGiAdmin"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
Map map = new HashMap();
map.put("frameworkState", OSGiAdmin.getFrameworkState());
List<BundleInfo> bundles = OSGiAdmin.getBundles();
map.put("bundles", bundles);

List<String> uninstalled = new ArrayList();
OSGiConfig osgiConfig = OSGiAdmin.getOSGiConfig();
if (osgiConfig != null) {
	List<BundleConfig> configs = osgiConfig.getBundleConfigs();
	for (BundleConfig bundleConfig : configs) {
		String locationUrl = bundleConfig.getLocationUrl();
		boolean finded = false;
		for (BundleInfo bundleInfo : bundles) {
			if (bundleInfo.getLocation().equals(locationUrl)) {
				finded = true;
				break;
			}
		}
		if (!finded) uninstalled.add(locationUrl);
	}
}
map.put("uninstalled", uninstalled);

JsonSerializer jsonSer = new JsonSerializer();
String jsonStr = jsonSer.serialize(map).toString();

response.setHeader("Content-type", "application/json");
response.setHeader("Cache-Control","no-cache");
out.println(jsonStr);
%>