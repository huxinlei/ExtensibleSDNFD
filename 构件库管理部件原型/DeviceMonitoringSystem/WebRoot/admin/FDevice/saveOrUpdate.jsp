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
			<b class="tip"></b>设备管理
			<b class="tip"></b>操作界面
		</div>
		<form
			action="${pageContext.request.contextPath}/FDevice-saveOrUpdateObject.action"
			method="post">
			<input name="util.id" value="${util.id }" type="hidden" />
			<table
				class="table table-striped table-bordered table-condensed list">
				<thead>
					<tr>
						<td colspan="4">
							<b>设备管理 </b>
						</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							设备名称
						</td>
						<td>
							<input name="util.name" value="${util.name }" type="text" />
							
						</td>
						<td>
							接入日期
						</td>
						<td>
							<div class="input-append">
								<input name="util.indate" value="${util.indate }" type="text"
									class="span2 datepicker" readonly="readonly" />
								<span class="add-on"><i class="icon-calendar"></i> </span>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							MAC地址
						</td>
						<td>
							<input name="util.mac" value="${util.mac }" type="text" />
							
						</td>
						<td>
							离开日期
						</td>
						<td>
							<div class="input-append">
								<input name="util.outdate" value="${util.outdate }" type="text"
									class="span2 datepicker" readonly="readonly" />
								
								<span class="add-on"><i class="icon-calendar"></i> </span>
							</div>
						</td>
					</tr>
					<tr>
						<td width="15%">
							状态
						</td>
						<td width="500" colspan="3" height="">
							<textarea name="util.status" style="width: 95%" rows="1" cols="5">${util.status }</textarea>
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