<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>���̵� �ߺ� Ȯ��</title>
<script type="text/javascript">
function idsend(id) {
	opener.document.memberInfo.id.value = id;
	self.close();
}
</script>
</head>
<body>
<div align="center">
<form action="idCheck.do" method="post" name="id_chk">
	<h3>���̵� �ߺ� Ȯ��</h3>
	<input type="text" value="${param.chkid}" name="id">
	<input type="submit" value="Ȯ��">
	<input type="button" onclick="idsend('${param.chkid}')" value="�ݱ�">
</form>
</div>
</body>
</html>