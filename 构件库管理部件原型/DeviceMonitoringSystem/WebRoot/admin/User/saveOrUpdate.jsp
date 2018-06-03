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
				$('.modal').show();
				$(".datepicker").datepicker();
				$('#list').hide();
				$('#find').click(function () {
					$('#list').show();
				})
			})
		</script>
	</head>
	<body>
		<div class="alert alert-info">
			��ǰλ��
			<b class="tip"></b>��Ա����
			<b class="tip"></b>��������
		</div>
		<form
			action="${pageContext.request.contextPath}/User-saveOrUpdateObject.action"
			method="post">
			<input name="util.id" value="${util.id }" type="hidden" />
			<table
				class="table table-striped table-bordered table-condensed list">
				<thead>
					<tr>
						<td colspan="4">
							<b>��Ա���� </b>
						</td>
					</tr>
				</thead>
				<tbody>

					<tr>
						<td>
							�û���
						</td>
						<td>
							<input name="util.s_1" value="${util.s_1 }" type="text" />
						</td>
						<td>
							����
						</td>
						<td>
							<input name="util.s_2" value="${util.s_2 }" type="text" />
						</td>
					</tr>
					<tr>
						<td>
							����
						</td>
						<td>
							<input name="util.s_0" value="${util.s_0 }" type="text" />
						</td>
						<td>
							��������
						</td>
						<td>
							<div class="input-append">
								<input name="util.s_3" value="${util.s_3 }" type="text"
									class="span2 datepicker" readonly="readonly" />
								<span class="add-on"><i class="icon-calendar"></i> </span>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							�Ա�
						</td>
						<td>
							<input name="util.s_4" type="radio"
								<c:if test="${util.s_4 == '��' || (util.s_11 != 'user' && util.s_11 != 'admin')}">checked</c:if>
								value="��" />
							��
							<input name="util.s_4" type="radio"
								<c:if test="${util.s_4 == 'Ů'}">checked</c:if> value="Ů" />
							Ů
						</td>
						<td>
							��ϵ��ʽ
						</td>
						<td>
							<input name="util.s_5" value="${util.s_5 }" type="text" />
						</td>
					</tr>
					<tr>
						<td>
							������ϵ��
						</td>
						<td>
							<input name="util.s_6" value="${util.s_6 }" type="text" />

						</td>
						<td>
							������ϵ��ʽ
						</td>
						<td>
							<input name="util.s_7" value="${util.s_7 }" type="text" />
						</td>
					</tr>
					<tr>
						<td>
							����
						</td>
						<td>
							<input name="util.s_8" value="${util.s_8 }" type="text" />

						</td>
						<td>
							��ͥסַ
						</td>
						<td>
							<input name="util.s_9" value="${util.s_9 }" type="text" />
						</td>
					</tr>
					<tr>
						<td>
							����
						</td>
						<td>
							<input name="util.s_10" value="${util.s_10 }" type="text" />
						</td>
						<td>
							�������
						</td>
						<td>
							<select name="util.s_11">
								<option <c:if test="${util.s_11 == 'teacher'}">selected</c:if>
									value="teacher">
									��ʦ
								</option>
								<option <c:if test="${util.s_11 == 'student'}">selected</c:if>
									value="student">
									ѧ��
								</option>
								<option <c:if test="${util.s_11 == 'admin'}">selected</c:if>
									value="admin">
									����Ա
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td width="15%">
							��ע
						</td>
						<td width="500" colspan="3" height="">
							<textarea name="util.s_15" style="width: 95%" rows="4" cols="5">${util.s_15 }</textarea>
						</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td>
							&nbsp;
						</td>
						<td colspan="3">
							<c:if test="${type == 'admin'}">
								<input class="btn btn-inverse" id="find" type="submit"
									value="����" />
							</c:if>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input class="btn btn-inverse" type="reset" value="ȡ��" />
						</td>
					</tr>
				</tfoot>
			</table>
		</form>
	</body>
</html>