<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- project/WebContent/main/noticeForm.jsp --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>�������� �ۼ�</title>
</head>
<body>
<div align="center">
<form action="noticeWrite.do?id=${sessionScope.login}" method="post" enctype="multipart/form-data" name="post">
<table class="board_table">
	<caption>�������� �ۼ�</caption>
	<tr><th>����</th><td><input type="text" name="subject"></td></tr>
	<tr><th>����</th><td><textarea rows="15" name="content"></textarea></td></tr>
	<tr><th>÷������</th><td><input type="file" name="file"></td></tr>
  <tfoot>
	<tr><td colspan="2"><input type="submit" class="button" value="�Ϸ�">
						<input type="button" onclick="history.go(-1)" value="���"></td></tr>
  </tfoot>
	</table>	  
</form>
</div>
</body>
</html>