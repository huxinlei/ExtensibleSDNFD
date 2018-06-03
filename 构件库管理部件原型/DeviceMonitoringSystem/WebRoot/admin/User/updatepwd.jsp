<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
			<b class="tip"></b>修改密码
		</div>
		<form
			action="${pageContext.request.contextPath}/updatePwd.action"
			method="post">
			<input name="util.id" value="${util.id }" type="hidden" />
			<table
				class="table table-striped table-bordered table-condensed list">
				<thead>
					<tr>
						<td colspan="4">
							<b><spring:message code="user_14" /> </b>
						</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							原密码
						</td>
						<td>
							<input name="util.s_3"   type="text" />
							
						</td>
						 
					</tr>
					<tr>
						<td>
							新密码
							
						</td>
						<td>
							<input name="util.s_4"  type="text" />
							
						</td>
						 
					</tr>
					<tr>
						<td>
							确认新密码
							
						</td>
						<td>
							<input name="util.s_5"  type="text" />
							 &nbsp;&nbsp;<font color=red><s:property value="message" /> </font>
						</td>
						 
					</tr>

				</tbody>
				<tfoot>
					<tr>
						<td>
							&nbsp;
						</td>
						<td colspan="3">
							<input class="btn btn-inverse" id="find" type="submit" value="保存" />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input class="btn btn-inverse" type="reset" value="取消" />
						</td>
					</tr>
				</tfoot>
			</table>
		</form>
	</body>
</html>