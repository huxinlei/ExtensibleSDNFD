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
			当前位置
			<b class="tip"></b>人员管理
			<b class="tip"></b>操作界面
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
							<b>人员管理 </b>
						</td>
					</tr>
				</thead>
				<tbody>

					<tr>
						<td>
							用户名
						</td>
						<td>
							<input name="util.s_1" value="${util.s_1 }" type="text" />
						</td>
						<td>
							密码
						</td>
						<td>
							<input name="util.s_2" value="${util.s_2 }" type="text" />
						</td>
					</tr>
					<tr>
						<td>
							姓名
						</td>
						<td>
							<input name="util.s_0" value="${util.s_0 }" type="text" />
						</td>
						<td>
							出生日期
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
							性别
						</td>
						<td>
							<input name="util.s_4" type="radio"
								<c:if test="${util.s_4 == '男' || (util.s_11 != 'user' && util.s_11 != 'admin')}">checked</c:if>
								value="男" />
							男
							<input name="util.s_4" type="radio"
								<c:if test="${util.s_4 == '女'}">checked</c:if> value="女" />
							女
						</td>
						<td>
							联系方式
						</td>
						<td>
							<input name="util.s_5" value="${util.s_5 }" type="text" />
						</td>
					</tr>
					<tr>
						<td>
							紧急联系人
						</td>
						<td>
							<input name="util.s_6" value="${util.s_6 }" type="text" />

						</td>
						<td>
							紧急联系方式
						</td>
						<td>
							<input name="util.s_7" value="${util.s_7 }" type="text" />
						</td>
					</tr>
					<tr>
						<td>
							籍贯
						</td>
						<td>
							<input name="util.s_8" value="${util.s_8 }" type="text" />

						</td>
						<td>
							家庭住址
						</td>
						<td>
							<input name="util.s_9" value="${util.s_9 }" type="text" />
						</td>
					</tr>
					<tr>
						<td>
							爱好
						</td>
						<td>
							<input name="util.s_10" value="${util.s_10 }" type="text" />
						</td>
						<td>
							身份类型
						</td>
						<td>
							<select name="util.s_11">
								<option <c:if test="${util.s_11 == 'teacher'}">selected</c:if>
									value="teacher">
									教师
								</option>
								<option <c:if test="${util.s_11 == 'student'}">selected</c:if>
									value="student">
									学生
								</option>
								<option <c:if test="${util.s_11 == 'admin'}">selected</c:if>
									value="admin">
									管理员
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td width="15%">
							备注
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
									value="保存" />
							</c:if>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input class="btn btn-inverse" type="reset" value="取消" />
						</td>
					</tr>
				</tfoot>
			</table>
		</form>
	</body>
</html>