<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>ī�װ��� ����</title>
<link rel="stylesheet" href="../css/main.css">
<script>
function closer() {
	self.close();
}
</script>
</head>
<body>
<form action="categoryEdit.do" name="f" method="post">
		<input type="hidden" name="categorynum" value="${ca.categorynum}">
		<table class="wish_table">
			<tr>
				<th>ī�װ���</th>
				<td><input type="text" name="categoryname" value="${ca.categoryname}" required></td>
			</tr>
		  <tfoot>
			<tr>
				<td colspan="2" style="text-align: center;">
				<input type="submit" class="button" value="����">
				<input type="button" value="���" onclick="closer()">
				</td>
			</tr>
		  </tfoot>
		</table>
	</form>
</body>
</html>