<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>��ǰ �߰�</title>
<link rel="stylesheet" type="text/css" href="../css/main.css" />
<script type="text/javascript" src="../script.js"></script>
</head>
<body>
<div align="center" style="padding-top:50px;">
	<form action="itemAdd.do" name="f" method="post">
		<input type="hidden" name="categorynum" value="${ca.categorynum}" >
		<table class="wish_table" style="width:70%;">
			<caption>[ ${ca.categoryname} ] ��ǰ �߰�</caption>
			<tr>
				<th width="20%">��ǰ��</th>
				<td width="70%"><input type="text" name="itemname" required></td>
			</tr>
			<tr>
				<th>����</th>
				<td><input type="number" name="price"></td>
			</tr>
			<tr>
				<th>�Ǹ� ����Ʈ</th>
				<td><input type="url" name="url"></td>
			</tr>
			<tr>
				<th>�޸�</th>
				<td><input type="text" name="memo"></td>
			</tr>
		<tfoot>
			<tr>
				<td colspan="2" style="text-align: center;">
					<input type="submit" class="button" value="�Ϸ�">
					<input type="button" onclick="closer()" value="���">
				</td>
			</tr>
		</tfoot>
		</table>
	</form>
</div>
</body>
</html>