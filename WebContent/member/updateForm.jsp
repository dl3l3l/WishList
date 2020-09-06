<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- /WebContent/member/updateForm.jsp --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>회원 정보 수정</title>
<style type="text/css">
	td { text-align:left; }
	table { margin:5px; width:30%; 
			background-color: #D3D3D3; }
</style>
<script type="text/javascript">
	function win_passchg() {
		var op = "width=500, height=250, left=50, top=150";
		open("passwordForm.do", "", op);
	}
</script>
</head>
<body>
<div align="center">
	<form action="update.do" name="memberInfo" method="post">
		<input type="hidden" name="picture" value="${mem.picture}">
		<table>
			<caption>회원 정보 수정</caption>
			<tr>
				<td style="text-align:center">
					<img src="picture/${mem.picture}" width="140" height="140" style="border-radius:70px;" id="pic"><br>
					<font size="2.5"><a href="javascript:win_upload()">사진수정</a></font></td>
			</tr>
			<tr>
				<td><label for="id">아이디</label>
					<input type="text" name="id" readonly value="${mem.id}"></td>
			</tr>
			<tr>
				<td><label for="pass">비밀번호</label>
					<input type="password" name="pass" required></td>
			</tr>
			<tr>
				<td><label for="pass">이름</label>
					<input type="text" name="name" value="${mem.name}"></td>
			</tr>
			<tr>
				<td><label for="pass">이메일</label>
					<input type="email" name="email" value="${mem.email}"></td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<input type="submit" class="button" value="회원수정"> 
				<c:if test="${sessionScope.login != 'admin' || mem.id != 'admin' }">
					<input type="button" value="비밀번호수정" onclick="win_passchg()">
				</c:if>
					<input type="button" value="취소" onclick="history.go(-1)"></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>