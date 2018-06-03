<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
		<%@ include file="../comlist.jsp"%>

		<script type="text/javascript">
			$(function () {
				$(".datepicker").datepicker();
			})
		</script>
	</head>
	<body>
		<div class="alert alert-info">
			当前位置
			<b class="tip"></b>学院管理
			<b class="tip"></b>查询界面 
			<a href="${pageContext.request.contextPath}/admin/Part/export.jsp"><img
					src="${pageContext.request.contextPath}/common/img/table/xls2.gif" />
			</a>
		</div>
		<ec:table items="list"
			action="${pageContext.request.contextPath}/Part-getAllUtil.action"
			imagePath="${pageContext.request.contextPath}/common/img/table/*.gif"
			title="学院列表 " width="100%" rowsDisplayed="5" var="tmp">
			<ec:exportXls fileName="Part.xls" tooltip="Excel" />
			<ec:row>
				<ec:column property="s_0" title="学院" />
				<ec:column property="s_1" title="添加日期" />
				<ec:column property="s_2" title="备注" />
				<ec:column sortable="false" filterable="false" viewsDenied="xls"
					alias="id2" title="具体操作">
					<c:if test="${type == 'admin'}">
						<a target="Conframe"
							href="${pageContext.request.contextPath}/Part-selectUtil.action?util.id=${tmp.id }">修改
						</a>
						<a target="Conframe"
							href="${pageContext.request.contextPath}/Part-deleteUtil.action?util.id=${tmp.id }">删除
						</a>
					</c:if>
					<c:if test="${type != 'admin'}">
						<a target="Conframe"
							href="${pageContext.request.contextPath}/Part-selectUtil.action?util.id=${tmp.id }">查看
						</a>
					</c:if>
				</ec:column>
			</ec:row>
		</ec:table>
	</body>
</html>
