<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>�����豸����ϵͳ</title>
		<%@ include file="common.jsp"%>
		<%@ include file="admin/com.jsp"%>
	</head>
	<body>
		<div class="warp">

			<!--ͷ����ʼ-->
			<div class="top_c">
				<div class="top-nav">
					��ӭ����${username }��&nbsp;&nbsp;
					<a
						href="${pageContext.request.contextPath}/admin/User/updatepwd.jsp"
						target="Conframe">�޸�����</a> |
					<a href="${pageContext.request.contextPath}/admin/User/help.jsp"
						target="Conframe">����</a> |
					<a href="${pageContext.request.contextPath}/login.jsp">��ȫ�˳�</a>
				</div>
			</div>
			<!--ͷ������-->

			<!--��߲˵���ʼ-->
			<div class="left_c left">
				<h1>
					ϵͳ�����˵�
				</h1>
				<div class="acc">

					<div>
						<a class="one">��������</a>
						<ul class="kid">
							<li>
								<b class="tip"></b><a target="Conframe"
									href="${pageContext.request.contextPath}/initInfo.action">�޸ĸ�����Ϣ</a>
							</li>
						</ul>
					</div>
					<c:if test="${type == 'admin'}">
						<div>
							<a class="one">��������</a>
							<ul class="kid">
								<li>
									<b class="tip"></b><a target="Conframe"
										href="${pageContext.request.contextPath}/admin/Bundle/saveOrUpdate.jsp">����</a>
								</li>
								<li>
									<b class="tip"></b><a target="Conframe"
										href="${pageContext.request.contextPath}/Bundle-getAllUtil.action">��ѯ</a>
								</li>
							</ul>
						</div>
					</c:if>
					<div>
						<a class="one">�豸����</a>
						<ul class="kid">
							<li>
								<b class="tip"></b><a target="Conframe"
									href="${pageContext.request.contextPath}/FDevice-getAllUtil.action">��ѯ</a>
							</li>
						</ul>
					</div>
					<div>
						<a class="one">���������</a>
						<ul class="kid">
							<li>
									<b class="tip"></b><a target="Conframe"
										href="${pageContext.request.contextPath}/admin/Constructor/saveOrUpdate.jsp">����</a>
							</li>
							<li>
								<b class="tip"></b><a target="Conframe"
									href="${pageContext.request.contextPath}/Constructor-getAllUtil.action">��ѯ</a>
							</li>
						</ul>
					</div>
					<div>
						<a class="one">�豸������󶨹���</a>
						<ul class="kid">
							<li>
									<b class="tip"></b><a target="Conframe"
										href="${pageContext.request.contextPath}/admin/TdcBind/saveOrUpdate.jsp">����</a>
							</li>						
							<li>
								<b class="tip"></b><a target="Conframe"
									href="${pageContext.request.contextPath}/TdcBind-getAllUtil.action">��ѯ</a>
							</li>
						</ul>
					</div>

					<!--  <div>
						<a class="one">ʵ������־����</a>
						<ul class="kid">

							<li>
								<b class="tip"></b><a target="Conframe"
									href="${pageContext.request.contextPath}/DeviceLog-initUtil.action">����</a>
							</li>
							<li>
								<b class="tip"></b><a target="Conframe"
									href="${pageContext.request.contextPath}/DeviceLog-getAllUtil.action">��ѯ</a>
							</li>
						</ul>
					</div>-->

					<div id="datepicker"></div>
				</div>

			</div>
			<!--��߲˵�����-->

			<!--�ұ߿�ܿ�ʼ-->
			<div class="Conframe">
				<iframe name="Conframe" id="Conframe"></iframe>
			</div>
			<!--�ұ߿�ܽ���-->

			<!--�ײ���ʼ-->
			<div class="bottom_c">
				Copyright &copy;2014-2017 ���ɹŴ�ѧ�����ѧԺ�Ƽ�������о��� ��Ȩ����
			</div>
			<!--�ײ�����-->
		</div>
	</body>
</html>