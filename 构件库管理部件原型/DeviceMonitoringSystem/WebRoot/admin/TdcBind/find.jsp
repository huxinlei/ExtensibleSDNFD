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
			<b class="tip"></b>设备管理
			<b class="tip"></b>查询界面
		</div>
		<ec:table items="list"
			action="${pageContext.request.contextPath}/TdcBind-getAllUtil.action"
			imagePath="${pageContext.request.contextPath}/common/img/table/*.gif"
			title="设备列表 " width="100%" rowsDisplayed="5" var="tmp">
			<ec:exportXls fileName="Device.xls" tooltip="Excel" />
			<ec:row>
				<ec:column property="d_id" title="设备编号" />
				<ec:column property="c_id" title="构造件编号" />
				<ec:column property="d_c_binddate" title="绑定时间" />
				<ec:column property="d_c_modifydate" title="修改绑定时间" />
				<ec:column sortable="false" filterable="false" viewsDenied="xls"
					alias="id2" title="具体操作">
						<c:if test="${type == 'admin'}">
							<a target="Conframe"
								href="${pageContext.request.contextPath}/TdcBind-selectUtil.action?util.id=${tmp.id }">修改
							</a>
							<a target="Conframe"
										href="${pageContext.request.contextPath}/TdcBind-deleteUtil.action?util.id=${tmp.id }">删除
							</a>
						</c:if>
								<c:if test="${type != 'admin'}">
									<a target="Conframe"
										href="${pageContext.request.contextPath}/TdcBind-selectUtil.action?util.id=${tmp.id }">查看
							</a>
						</c:if>
				</ec:column>
			</ec:row>
		</ec:table>
	</body>
</html>
