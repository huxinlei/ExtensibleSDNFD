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
			<b class="tip"></b>设备状态管理
			<b class="tip"></b>操作界面
		</div>
		<form
			action="${pageContext.request.contextPath}/DeviceStatus-saveOrUpdateObject.action"
			method="post">
			<input name="util.id" value="${util.id }" type="hidden" />
			<table
				class="table table-striped table-bordered table-condensed list">
				<thead>
					<tr>
						<td colspan="4">
							<b>设备状态管理 </b>
						</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							设备
						</td>
						<td  colspan="3" >
							<select name="util.device.id">
								<c:forEach var="type" items="${listDevice}">
									<option
										<c:if test="${type.id == util.device.id }">
																	selected
																	</c:if>
										value="${type.id }">
										${type.s_0 }
									</option>
								</c:forEach>
							</select>
							
						</td>
					</tr>

					<tr>
						<td>
							当前时间
						</td>
						<td>
							<input name="util.s_0" value="${util.s_0 }" type="text" />
							
						</td>
						<td>
							当前日期
						</td>
						<td>
							<div class="input-append">
								<input name="util.s_1" value="${util.s_1 }" type="text"
									class="span2 datepicker" readonly="readonly" />
								
								<span class="add-on"><i class="icon-calendar"></i> </span>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							水箱1液位(cm)
						</td>
						<td>
							<input name="util.s_2" value="${util.s_2 }" type="text" />
							
						</td>
						<td>
							水箱2液位(cm)
						</td>
						<td>
							<input name="util.s_3" value="${util.s_3 }" type="text" />
						</td>
					</tr>
					<tr>
						<td>
							水箱3液位(cm)
						</td>
						<td>
							<input name="util.s_4" value="${util.s_4 }" type="text" />
							
						</td>
						<td>
							水箱4液位(cm)
						</td>
						<td>
							<input name="util.s_5" value="${util.s_5 }" type="text" />
						</td>
					</tr>
					<tr>
						<td>
							水箱5液位(cm)
						</td>
						<td>
							<input name="util.s_6" value="${util.s_6 }" type="text" />
							
						</td>
						<td>
							水箱6液位(cm)
						</td>
						<td>
							<input name="util.s_7" value="${util.s_7 }" type="text" />
						</td>
					</tr>
					<tr>
						<td>
							水箱5液位(温度)
						</td>
						<td>
							<input name="util.s_8" size=100 class=123 value="${util.s_8 }"
								type="text" />
						</td>
						<td colspan="2"></td>
					</tr>
					<tr>
						<td width="15%">
							备注
						</td>
						<td width="500" colspan="3" height="">
							<textarea name="util.s_9" style="width: 95%" rows="4" cols="5">${util.s_9 }</textarea>
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