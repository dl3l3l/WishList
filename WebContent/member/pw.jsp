<%@page import="model.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%-- project/WebContent/member/pw.jsp --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>비밀번호찾기</title>
<script type="text/javascript">
function closer(){
	self.close();
}
</script>
</head>
<body>
	<table>
		<tr>
			<th>비밀번호</th>
			<td>**${sendPw}</td>
		</tr>
		<tr>
			<td colspan="2"><input type="button" value="닫기" onclick="closer()"></td>
		</tr>
	</table>
</body>
</html>