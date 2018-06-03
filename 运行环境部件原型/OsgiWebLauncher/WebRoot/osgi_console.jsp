<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>OSGi Switch Web Console</title>
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script language="javascript" type="text/javascript">
$(document).ready(function(){
	var stateDict = {};
	stateDict["1"] = "UNINSTALLED";
	stateDict["2"] = "INSTALLED";
	stateDict["4"] = "RESOLVED";
	stateDict["8"] = "STARTING";
	stateDict["16"] = "STOPPING";
	stateDict["32"] = "ACTIVE";
	
	
  var requestState = 0;
  function refreshState() {
	if (requestState != 0) return;
	requestState = 1;
	jQuery.ajax({
	  url : "osgi_state.jsp",
	  type : "GET",
      success : function(dataObj) {
        requestState = 0;
        $("#frameworkState").html(stateDict[dataObj.frameworkState + ""]);
        if (dataObj.frameworkState == 32) {
        	$("#frameworkControlBtn").attr("value", "停止");
        }
        else {
        	$("#frameworkControlBtn").attr("value", "启动");
        }
        var trs = $("#bundlesList").find("tr");
        if (trs.length > 1) {
        	for (var i=1; i<trs.length; i++) {
        		$(trs[i]).remove();
        	}
        }
        trs = $("#uninstalledList").find("tr");
        if (trs.length > 1) {
            for (var i=1; i<trs.length; i++) {
                $(trs[i]).remove();
            }
        }
        		
        if (dataObj.bundles != null && dataObj.bundles.length > 0) {
        	for (var i=0; i<dataObj.bundles.length; i++) {
        		var bundle = dataObj.bundles[i];
        		var row = $("<tr></tr>");
        		var cell = $("<td align='center'>" + bundle.id + "</td>");
        		row.append(cell);
        		cell = $("<td align='center'>" + bundle.symbolicName + "</td>");
        		row.append(cell);
        		cell = $("<td align='center'>" + bundle.versionMajor + "." + bundle.versionMinor
        				+ "." + bundle.versionMicro + "." + bundle.versionQualifier + "</td>");
        		row.append(cell);
        		cell = $("<td align='center'>" + stateDict[bundle.state + ""] + "</td>");
        		row.append(cell);
        		cell = $("<td align='center'>" + bundle.location + "</td>");
        		row.append(cell);
        		cell = $("<td align='center'></td>");
        		if (bundle.location != "System Bundle") {
	        		if (bundle.state == 1) {
	        			var btn = $("<input type='button' value='安装' bundle_id='" + bundle.id + "'/>");
	        			btn.bind("click", function () {
	        				doOperation($(this).attr("bundle_id"), "install");
	        			});
	        			cell.append(btn);
	        		}
	        		else {
	        			var btn = $("<input type='button' value='卸载' bundle_id='" + bundle.id + "'/>");
	        			btn.bind("click", function () {
	        				doOperation($(this).attr("bundle_id"), "uninstall");
	        			});
	        			cell.append(btn);
	        			var span = $("<span>&nbsp;&nbsp;</span>");
	        			cell.append(span);
	        			
	        			if (bundle.state == 32) {
	        				btn = $("<input type='button' value='停止' bundle_id='" + bundle.id + "'/>");
	             			btn.bind("click", function () {
	             				doOperation($(this).attr("bundle_id"), "stop");
	             			});
	             			cell.append(btn);
	        			}
	        			else {
	        				btn = $("<input type='button' value='启动' bundle_id='" + bundle.id + "'/>");
	             			btn.bind("click", function () {
	             				doOperation($(this).attr("bundle_id"), "start");
	             			});
	             			cell.append(btn);
	        			}
	        		}
        		}
        		row.append(cell);
        		$("#bundlesList").append(row);
        	}
        }
        
        if (dataObj.uninstalled != null && dataObj.uninstalled.length > 0) {
        	for (var i=0; i<dataObj.uninstalled.length; i++) {
        		var row = $("<tr></tr>");
        		var cell = $("<td align='left'>" + dataObj.uninstalled[i] + "</td>");
        		row.append(cell);
        		cell = $("<td align='center'></td>");
        		var btn = $("<input type='button' value='安装' bundle_path='" + dataObj.uninstalled[i] + "'/>");
    			btn.bind("click", function () {
    				doOperation(-1, "install", $(this).attr("bundle_path"));
    			});
    			cell.append(btn);
    			row.append(cell);
    			$("#uninstalledList").append(row);
        	}
        }
	  },
	  error : function(XMLHttpRequest, textStatus, errorThrown) {
		requestState = 0;
	  }
	});
  }
  
  $("#refreshBtn").bind("click", function () {
	  refreshState();
  });
  
  $("#frameworkControlBtn").bind("click", function () {
	  if ($(this).attr("value") == "启动") doOperation(0, "startup");
	  else doOperation(0, "shutdown");
  });
  
  function doOperation(id, op, path) {//alert(id+","+op);
	  jQuery.ajax({
		  url : "osgi_op.jsp",
		  type : "GET",
		  data : {id:id, op:op, path:path},
	      success : function(data) {
	    	if (!data.success) $("#statusDiv").html("操作错误:" + data.msg); 
	    	else $("#statusDiv").html("操作成功");
	    	refreshState();
		  },
		  error : function(XMLHttpRequest, textStatus, errorThrown) {
			  $("#statusDiv").html("操作错误:" + textStatus);
		  }
		});
  }
  
  refreshState();
});
</script>
<style>
body { margin:0px; padding:0px; padding-top:0px; padding-bottom:0px; }
.table { border: 1px solid #E7E7E7; font-size:14px;border-collapse:collapse; }
</style>
</head>
<body>
<div align="center" style="font-size:22px;"><b>OSGi交换机管理</b></div>
<table border="1" width="100%" cellpadding="0" cellspacing="0">
  <tr>
    <td width="30%" align="right">框架状态：</td>
    <td width="40%" align="left" id="frameworkState"></td>
    <td width="30%" align="center">
      <input type="button" id="frameworkControlBtn" value="启动">&nbsp;&nbsp;
      <input type="button" id="refreshBtn" value="刷新">
    </td>
  </tr>
  <tr>
    <td colspan="3">
    已装载的Bundles：
    </td>
  </tr>
  <tr>
    <td colspan="3">
      <table id="bundlesList" border="1" width="100%" cellpadding="0" cellspacing="0">
        <tr>
          <td width="5%" align="center">ID</td>
          <td width="20%" align="center">名称</td>
          <td width="20%" align="center">版本</td>
          <td width="10%" align="center">状态</td>
          <td width="35%" align="center">位置</td>
          <td width="10%" align="center">操作</td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td colspan="3">
    未装载的Bundles：
    </td>
  </tr>
  <tr>
    <td colspan="3">
      <table id="uninstalledList" border="1" width="100%" cellpadding="0" cellspacing="0">
        <tr>
          <td width="90%" align="center">位置</td>
          <td width="10%" align="center">操作</td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td colspan="3">
      <div id="statusDiv" style="color:red"></div>
    </td>
  </tr>
</table>
</body>
</html>