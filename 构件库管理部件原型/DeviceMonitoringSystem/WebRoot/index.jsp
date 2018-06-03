<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>网络设备管理系统</title>
		<%@ include file="common.jsp"%>
		<%@ include file="admin/com.jsp"%>
	</head>
	<body>
		<div class="warp">

			<!--头部开始-->
			<div class="top_c">
				<div class="top-nav">
					欢迎您，${username }！&nbsp;&nbsp;
					<a
						href="${pageContext.request.contextPath}/admin/User/updatepwd.jsp"
						target="Conframe">修改密码</a> |
					<a href="${pageContext.request.contextPath}/admin/User/help.jsp"
						target="Conframe">帮助</a> |
					<a href="${pageContext.request.contextPath}/login.jsp">安全退出</a>
				</div>
			</div>
			<!--头部结束-->

			<!--左边菜单开始-->
			<div class="left_c left">
				<h1>
					系统操作菜单
				</h1>
				<div class="acc">

					<div>
						<a class="one">个人资料</a>
						<ul class="kid">
							<li>
								<b class="tip"></b><a target="Conframe"
									href="${pageContext.request.contextPath}/initInfo.action">修改个人信息</a>
							</li>
						</ul>
					</div>
					<c:if test="${type == 'admin'}">
						<div>
							<a class="one">构件管理</a>
							<ul class="kid">
								<li>
									<b class="tip"></b><a target="Conframe"
										href="${pageContext.request.contextPath}/admin/Bundle/saveOrUpdate.jsp">增加</a>
								</li>
								<li>
									<b class="tip"></b><a target="Conframe"
										href="${pageContext.request.contextPath}/Bundle-getAllUtil.action">查询</a>
								</li>
							</ul>
						</div>
					</c:if>
					<div>
						<a class="one">设备管理</a>
						<ul class="kid">
							<li>
								<b class="tip"></b><a target="Conframe"
									href="${pageContext.request.contextPath}/FDevice-getAllUtil.action">查询</a>
							</li>
						</ul>
					</div>
					<div>
						<a class="one">构造件管理</a>
						<ul class="kid">
							<li>
									<b class="tip"></b><a target="Conframe"
										href="${pageContext.request.contextPath}/admin/Constructor/saveOrUpdate.jsp">增加</a>
							</li>
							<li>
								<b class="tip"></b><a target="Conframe"
									href="${pageContext.request.contextPath}/Constructor-getAllUtil.action">查询</a>
							</li>
						</ul>
					</div>
					<div>
						<a class="one">设备构造件绑定管理</a>
						<ul class="kid">
							<li>
									<b class="tip"></b><a target="Conframe"
										href="${pageContext.request.contextPath}/admin/TdcBind/saveOrUpdate.jsp">增加</a>
							</li>						
							<li>
								<b class="tip"></b><a target="Conframe"
									href="${pageContext.request.contextPath}/TdcBind-getAllUtil.action">查询</a>
							</li>
						</ul>
					</div>

					<!--  <div>
						<a class="one">实验室日志管理</a>
						<ul class="kid">

							<li>
								<b class="tip"></b><a target="Conframe"
									href="${pageContext.request.contextPath}/DeviceLog-initUtil.action">增加</a>
							</li>
							<li>
								<b class="tip"></b><a target="Conframe"
									href="${pageContext.request.contextPath}/DeviceLog-getAllUtil.action">查询</a>
							</li>
						</ul>
					</div>-->

					<div id="datepicker"></div>
				</div>

			</div>
			<!--左边菜单结束-->

			<!--右边框架开始-->
			<div class="Conframe">
				<iframe name="Conframe" id="Conframe"></iframe>
			</div>
			<!--右边框架结束-->

			<!--底部开始-->
			<div class="bottom_c">
				Copyright &copy;2014-2017 内蒙古大学计算机学院云计算服务研究所 版权所有
			</div>
			<!--底部结束-->
		</div>
	</body>
</html>