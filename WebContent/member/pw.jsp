<%@page import="model.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%-- project/WebContent/member/pw.jsp --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>��й�ȣã��</title>
<script type="text/javascript">
function closer(){
	self.close();
}
</script>
</head>
<body>
	<table>
		<tr>
			<th>��й�ȣ</th>
			<td>**${sendPw}</td>
		</tr>
		<tr>
			<td colspan="2"><input type="button" value="�ݱ�" onclick="closer()"></td>
		</tr>
	</table>
</body>
</html>