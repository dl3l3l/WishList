<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%-- /WebContent/member/passwordForm.jsp --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>비밀번호 변경</title>
<link rel="stylesheet" href="../css/main.css">
<script type="text/javascript">
	function inchk(f) {
		if (f.chgpass.value != f.chgpass2.value) {
			alert("변경 비밀번호와 변경 비밀번호 확인이 다릅니다.");
			f.chgpass2.value = "";
			f.chgpass2.focus();
			return false;
		}
		return true;
	}
	function closer() {
		self.close();
	}
</script>
</head>
<body>
	<form action="password.do" method="post" name="f" onsubmit="return inchk(this)">
		<table class="board_table" >
			<caption>비밀번호 변경</caption>
			<tr>
				<th>현재 비밀번호</th>
				<td><input type="password" name="pass"></td>
			</tr>
			<tr>
				<th>변경 비밀번호</th>
				<td><input type="password" name="chgpass"></td>
			</tr>
			<tr>
				<th>변경 비밀번호 확인</th>
				<td><input type="password" name="chgpass2"></td>
			</tr>
		<tfoot>
			<tr>
				<td colspan="2"><input type="submit" class="button" value="비밀번호 변경">
				<input type="button" value="취소" onclick="closer()"></td>
			</tr>
		</tfoot>
		</table>
	</form>
</body>
</html>