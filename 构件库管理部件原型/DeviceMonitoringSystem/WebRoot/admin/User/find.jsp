<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
		<%@ include file="../com.jsp"%>
		<script type="text/javascript">
			$(function () {
				$(".datepicker").datepicker();
			})
		</script>
	</head>
	<body>
		<div class="alert alert-info">
			��ǰλ��
			<b class="tip"></b>��Ա����
			<b class="tip"></b>��ѯ����
		</div>
		<form
			action="${pageContext.request.contextPath}/Util_B-getAllUtil.action">
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<td colspan="6" class="auto-style2">
							&nbsp;����д��ѯ����
						</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td align=right>
							&nbsp;&nbsp;
							<select name="message">
								<option value="s_0" />
									����
								</option>
								<option value="s_3" />
									��������
								</option>
								<option value="s_4" />
									�Ա�
								</option>
								<option value="s_5" />
									��ϵ��ʽ
								</option>
								<option value="s_6" />
									������ϵ��
								</option>
							</select>
						</td>
						<td class="detail" colspan="5">
							<input type="text" name="str" id="str" value="${str }" />
							&nbsp;&nbsp;
							<input class="btn btn-inverse" id="find" type="submit" value="��ѯ" />
							&nbsp;&nbsp;
							<input class="btn btn-inverse" type="button" value="���"
								onclick="javascript: document.getElementById('str').value='';" />
							&nbsp;&nbsp;
						</td>
					</tr>
				</tbody>
			</table>
		</form>
		<c:if test="${list != 'null'}">
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr class="tr_detail">
						<td>
							����
						</td>
						<td>
							��������
						</td>
						<td>
							�Ա�
						</td>
						<td>
							��ϵ��ʽ
						</td>
						<td>
							������ϵ��
						</td>
						<td>
							�������
						</td>
						<td>
							����
						</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="tmp" items="${list}">
						<tr>
							<td>
								${tmp.s_0 }
							</td>
							<td>
								${tmp.s_3 }
							</td>
							<td>
								${tmp.s_4 }
							</td>
							<td>
								${tmp.s_5 }
							</td>
							<td>
								${tmp.s_6 }
							</td>
							<td>
								<c:if test="${tmp.s_11 == 'admin'}">
								����Ա
								</c:if>
								<c:if test="${tmp.s_11 == 'teacher'}">
								��ʦ
								</c:if>
								<c:if test="${tmp.s_11 == 'student'}">
								ѧ��
								</c:if>
							</td>
							<td>
								<c:if test="${type == 'admin'}">
									<a target="Conframe"
										href="${pageContext.request.contextPath}/User-selectUtil.action?util.id=${tmp.id }">�޸�
									</a>
									<a target="Conframe"
										href="${pageContext.request.contextPath}/User-deleteUtil.action?util.id=${tmp.id }">ɾ��
									</a>
								</c:if>
								<c:if test="${type != 'admin'}">
									<a target="Conframe"
										href="${pageContext.request.contextPath}/User-selectUtil.action?util.id=${tmp.id }">�鿴
									</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</body>
</html>
