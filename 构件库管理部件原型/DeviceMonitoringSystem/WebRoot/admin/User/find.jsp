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
			当前位置
			<b class="tip"></b>人员管理
			<b class="tip"></b>查询界面
		</div>
		<form
			action="${pageContext.request.contextPath}/Util_B-getAllUtil.action">
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<td colspan="6" class="auto-style2">
							&nbsp;请填写查询条件
						</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td align=right>
							&nbsp;&nbsp;
							<select name="message">
								<option value="s_0" />
									姓名
								</option>
								<option value="s_3" />
									出生日期
								</option>
								<option value="s_4" />
									性别
								</option>
								<option value="s_5" />
									联系方式
								</option>
								<option value="s_6" />
									紧急联系人
								</option>
							</select>
						</td>
						<td class="detail" colspan="5">
							<input type="text" name="str" id="str" value="${str }" />
							&nbsp;&nbsp;
							<input class="btn btn-inverse" id="find" type="submit" value="查询" />
							&nbsp;&nbsp;
							<input class="btn btn-inverse" type="button" value="清空"
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
							姓名
						</td>
						<td>
							出生日期
						</td>
						<td>
							性别
						</td>
						<td>
							联系方式
						</td>
						<td>
							紧急联系人
						</td>
						<td>
							身份类型
						</td>
						<td>
							操作
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
								管理员
								</c:if>
								<c:if test="${tmp.s_11 == 'teacher'}">
								教师
								</c:if>
								<c:if test="${tmp.s_11 == 'student'}">
								学生
								</c:if>
							</td>
							<td>
								<c:if test="${type == 'admin'}">
									<a target="Conframe"
										href="${pageContext.request.contextPath}/User-selectUtil.action?util.id=${tmp.id }">修改
									</a>
									<a target="Conframe"
										href="${pageContext.request.contextPath}/User-deleteUtil.action?util.id=${tmp.id }">删除
									</a>
								</c:if>
								<c:if test="${type != 'admin'}">
									<a target="Conframe"
										href="${pageContext.request.contextPath}/User-selectUtil.action?util.id=${tmp.id }">查看
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
