<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%-- /WebContent/member/passwordForm.jsp --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>��й�ȣ ����</title>
<link rel="stylesheet" href="../css/main.css">
<script type="text/javascript">
	function inchk(f) {
		if (f.chgpass.value != f.chgpass2.value) {
			alert("���� ��й�ȣ�� ���� ��й�ȣ Ȯ���� �ٸ��ϴ�.");
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
			<caption>��й�ȣ ����</caption>
			<tr>
				<th>���� ��й�ȣ</th>
				<td><input type="password" name="pass"></td>
			</tr>
			<tr>
				<th>���� ��й�ȣ</th>
				<td><input type="password" name="chgpass"></td>
			</tr>
			<tr>
				<th>���� ��й�ȣ Ȯ��</th>
				<td><input type="password" name="chgpass2"></td>
			</tr>
		<tfoot>
			<tr>
				<td colspan="2"><input type="submit" class="button" value="��й�ȣ ����">
				<input type="button" value="���" onclick="closer()"></td>
			</tr>
		</tfoot>
		</table>
	</form>
</body>
</html>