<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%-- /WebContent/member/deleteForm.jsp--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>회원 탈퇴 비밀번호 입력</title>
<link rel="stylesheet" type="text/css" href="../css/main.css" />
<script type="text/javascript" src="../script.js"></script>
</head>
<body>
<form action="delete.do" method="post">
	<input type="hidden" name="id" value="${param.id}">
	<table class="board_table" style="width:80%;">
		<caption>회원 비밀번호 입력</caption>
		<tr><th>비밀번호</th><td><input type="password" name="pass" /></td></tr>
		<tfoot>
		<tr><td colspan="2"><input type="submit" class="button" value="탈퇴하기" /></td></tr>
		</tfoot>
	</table>
</form>
</body>
</html> 