<%@page import="model.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%-- project/WebContent/member/id.jsp --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>idã��</title>
<script type="text/javascript">
	function idsend(id) {
		opener.document.login.id.value = id;
		self.close();
	}
</script>
</head>
<body>
	<table>
		<tr>
			<th>���̵�</th>
			<td>${sendId}**</td>
		</tr>
		<tr>
			<td colspan="2"><input type="button" value="���̵�����"
				onclick="idsend('${sendId}')"></td>
		</tr>
	</table>
</body>
</html>
