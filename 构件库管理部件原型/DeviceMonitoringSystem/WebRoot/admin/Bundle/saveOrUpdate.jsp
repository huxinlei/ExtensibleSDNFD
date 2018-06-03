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
			action="${pageContext.request.contextPath}/Bundle-saveOrUpdateObject.action"
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
							构件名称
						</td>
						<td>
							<input name="util.name" value="${util.name }" type="text" />
							
						</td>
						<td>
							上传日期
						</td>
						<td>
							<div class="input-append">
								<input name="util.update" value="${util.update }" type="text"
									class="span2 datepicker" readonly="readonly" />
								<span class="add-on"><i class="icon-calendar"></i> </span>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							版本
						</td>
						<td>
							<input name="util.version" value="${util.version }" type="text" />
							
						</td>
						<td>
							修改日期
						</td>
						<td>
							<div class="input-append">
								<input name="util.modifydate" value="${util.modifydate }" type="text"
									class="span2 datepicker" readonly="readonly" />
								
								<span class="add-on"><i class="icon-calendar"></i> </span>
							</div>
						</td>
					</tr>
					<tr>
						<td width="15%">
							构件大小
						</td>
						<td width="500" colspan="3" height="">
							<textarea name="util.size" style="width: 95%" rows="1" cols="5">${util.size }</textarea>
						</td>
					</tr>
					<tr>
						<td width="15%">
							构件内容
						</td>
						<td width="500" colspan="3" height="">
							<textarea name="util.content" style="width: 95%" rows="5" cols="5">${util.content }</textarea>
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