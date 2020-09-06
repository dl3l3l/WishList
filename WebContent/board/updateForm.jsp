<%@page import="model.Board"%>
<%@page import="model.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- project/WebContent/board/updateForm.jsp --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>�Խù� �����ϱ�</title>
<script>
	function file_delete() {
		document.post.file2.value="";
		file_desc.style.display="none";
	}
</script>
</head>
<body>
<div align="center">
<form action="update.do?id=${sessionScope.login}&postnum=${b.postnum}" method="post" enctype="multipart/form-data" name="post">
<input type="hidden" name="file2" value="${b.file}">
<table class="board_table">
<caption>�Խù� ����</caption>
<tr><th>ī�װ�</th>
	<td style="text-align:left">
  	   <select name="type" id="type">
			<c:if test="${btype==10}"> <%--�ı� �Խ����� ��� --%>
				<option>����</option>
				<option value='11' <c:if test="${b.type==11}">selected</c:if>>����</option>
				<option value='12' <c:if test="${b.type==12}">selected</c:if>>����</option>
				<option value='13' <c:if test="${b.type==13}">selected</c:if>>��ǰ/�ǰ�</option>
				<option value='14' <c:if test="${b.type==14}">selected</c:if>>����ǰ</option>
				<option value='15' <c:if test="${b.type==15}">selected</c:if>>�м�</option>
				<option value='16' <c:if test="${b.type==16}">selected</c:if>>��Ƽ</option>
				<option value='17' <c:if test="${b.type==17}">selected</c:if>>����</option>
				<option value='18' <c:if test="${b.type==18}">selected</c:if>>��Ÿ</option>
			</c:if>
			<c:if test="${btype==20}"> <%--Ư������ �Խ����� ��� --%>
				<option>����</option> 
				<option value='21' <c:if test="${b.type==21}">selected</c:if>>����</option>
				<option value='22' <c:if test="${b.type==22}">selected</c:if>>�ؿ�</option>
			</c:if>
			<c:if test="${btype==30}"> <%--���� �Խ����� ��� --%>
				<option value='30' selected>����</option>
			</c:if>
			<c:if test="${b.type==90}"> <%--�������� �Խ����� ��� --%>
				<option value='90' selected>��������</option>
			</c:if>
  	 	  	</select>
  	   </td></tr>
<tr><th>����</th>
	<td><input type="text" name="subject" value="${b.subject}"></td>
</tr>
<tr><th>����</th>
	<td><textarea rows="15" name="content" id="content1">${b.content}</textarea></td>
</tr>
<tr><th>÷������</th>
	<td style="text-align:left">
	<c:if test="${!empty b.file}">
	<div id="file_desc">${b.file}<a href="javascript:file_delete()">[÷������ ����]</a></div>
	</c:if><input type="file" name="file">	
	</td>
</tr>
<tfoot>
<tr><td colspan="2">
	<input type="submit" class="button" value="�Ϸ�">
	<input type="button" onclick="history.go(-1)" value="���"></td>
</tr>
</tfoot></table></form>
</div>
</body>
</html>