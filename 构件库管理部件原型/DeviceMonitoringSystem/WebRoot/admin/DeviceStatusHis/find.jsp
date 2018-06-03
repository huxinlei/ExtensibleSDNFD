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
			<b class="tip"></b>设备状态历史管理
			<b class="tip"></b>查询界面
		</div>
		<ec:table items="list"
			action="${pageContext.request.contextPath}/DeviceStatusHis-getAllUtil.action"
			imagePath="${pageContext.request.contextPath}/common/img/table/*.gif"
			title="设备状态历史列表" width="200%" rowsDisplayed="5" var="tmp"><ec:exportXls fileName="DeviceStatusHis.xls" tooltip="Excel" /><ec:row>
				<ec:column property="device.s_0" title="设备" />
				<ec:column property="s_0" title="当前时间" />
				<ec:column property="s_1" title="当前日期" />
				<ec:column property="s_2" title="水箱1液位(cm)" />
				<ec:column property="s_3" title="水箱2液位(cm)" />
				<ec:column property="s_4" title="水箱3液位(cm)" />
				<ec:column property="s_5" title="水箱4液位(cm)" />
				<ec:column property="s_6" title="水箱5液位(cm)" />
				<ec:column property="s_7" title="水箱6液位(cm)" />
				<ec:column property="s_8" title="水箱5液位(温度)" />
				<ec:column sortable="false" filterable="false" viewsDenied="xls"
					alias="id2" title="具体操作">
						<c:if test="${type == 'admin'}">
							<a target="Conframe"
								href="${pageContext.request.contextPath}/DeviceStatusHis-selectUtil.action?util.id=${tmp.id }">修改
							</a>
							<a target="Conframe"
										href="${pageContext.request.contextPath}/DeviceStatusHis-deleteUtil.action?util.id=${tmp.id }">删除
							</a>
						</c:if>
								<c:if test="${type != 'admin'}">
									<a target="Conframe"
										href="${pageContext.request.contextPath}/DeviceStatusHis-selectUtil.action?util.id=${tmp.id }">查看
							</a>
						</c:if>
				</ec:column>
				</ec:row>
		</ec:table>
	</body>
</html>
