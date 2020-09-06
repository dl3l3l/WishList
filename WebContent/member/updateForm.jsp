<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- /WebContent/member/updateForm.jsp --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>ȸ�� ���� ����</title>
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
			<caption>ȸ�� ���� ����</caption>
			<tr>
				<td style="text-align:center">
					<img src="picture/${mem.picture}" width="140" height="140" style="border-radius:70px;" id="pic"><br>
					<font size="2.5"><a href="javascript:win_upload()">��������</a></font></td>
			</tr>
			<tr>
				<td><label for="id">���̵�</label>
					<input type="text" name="id" readonly value="${mem.id}"></td>
			</tr>
			<tr>
				<td><label for="pass">��й�ȣ</label>
					<input type="password" name="pass" required></td>
			</tr>
			<tr>
				<td><label for="pass">�̸�</label>
					<input type="text" name="name" value="${mem.name}"></td>
			</tr>
			<tr>
				<td><label for="pass">�̸���</label>
					<input type="email" name="email" value="${mem.email}"></td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<input type="submit" class="button" value="ȸ������"> 
				<c:if test="${sessionScope.login != 'admin' || mem.id != 'admin' }">
					<input type="button" value="��й�ȣ����" onclick="win_passchg()">
				</c:if>
					<input type="button" value="���" onclick="history.go(-1)"></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>