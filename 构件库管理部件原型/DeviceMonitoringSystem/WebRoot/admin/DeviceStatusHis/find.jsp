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
			��ǰλ��
			<b class="tip"></b>�豸״̬��ʷ����
			<b class="tip"></b>��ѯ����
		</div>
		<ec:table items="list"
			action="${pageContext.request.contextPath}/DeviceStatusHis-getAllUtil.action"
			imagePath="${pageContext.request.contextPath}/common/img/table/*.gif"
			title="�豸״̬��ʷ�б�" width="200%" rowsDisplayed="5" var="tmp"><ec:exportXls fileName="DeviceStatusHis.xls" tooltip="Excel" /><ec:row>
				<ec:column property="device.s_0" title="�豸" />
				<ec:column property="s_0" title="��ǰʱ��" />
				<ec:column property="s_1" title="��ǰ����" />
				<ec:column property="s_2" title="ˮ��1Һλ(cm)" />
				<ec:column property="s_3" title="ˮ��2Һλ(cm)" />
				<ec:column property="s_4" title="ˮ��3Һλ(cm)" />
				<ec:column property="s_5" title="ˮ��4Һλ(cm)" />
				<ec:column property="s_6" title="ˮ��5Һλ(cm)" />
				<ec:column property="s_7" title="ˮ��6Һλ(cm)" />
				<ec:column property="s_8" title="ˮ��5Һλ(�¶�)" />
				<ec:column sortable="false" filterable="false" viewsDenied="xls"
					alias="id2" title="�������">
						<c:if test="${type == 'admin'}">
							<a target="Conframe"
								href="${pageContext.request.contextPath}/DeviceStatusHis-selectUtil.action?util.id=${tmp.id }">�޸�
							</a>
							<a target="Conframe"
										href="${pageContext.request.contextPath}/DeviceStatusHis-deleteUtil.action?util.id=${tmp.id }">ɾ��
							</a>
						</c:if>
								<c:if test="${type != 'admin'}">
									<a target="Conframe"
										href="${pageContext.request.contextPath}/DeviceStatusHis-selectUtil.action?util.id=${tmp.id }">�鿴
							</a>
						</c:if>
				</ec:column>
				</ec:row>
		</ec:table>
	</body>
</html>
