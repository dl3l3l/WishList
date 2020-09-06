<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%--project/WebContent/member/idForm.jsp --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>아이디 찾기</title>
</head>
<body>
<form action="id.do" method="post" name="f">
<h3>아이디찾기</h3>
<table>
	<tr><th>이름</th><td><input type="text" name="name"></td></tr>
	<tr><th>이메일</th><td><input type="text" name="email"></td></tr>
	<tr><td colspan=2><input type="submit" class="button" value="아이디찾기"></td></tr>
</table>
</form>
</body>
</html>