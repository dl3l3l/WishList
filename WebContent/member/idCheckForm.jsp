<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>아이디 중복 확인</title>
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
	<h3>아이디 중복 확인</h3>
	<input type="text" value="${param.chkid}" name="id">
	<input type="submit" value="확인">
	<input type="button" onclick="idsend('${param.chkid}')" value="닫기">
</form>
</div>
</body>
</html>