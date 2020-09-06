<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>물품 추가</title>
<link rel="stylesheet" type="text/css" href="../css/main.css" />
<script type="text/javascript" src="../script.js"></script>
</head>
<body>
<div align="center" style="padding-top:50px;">
	<form action="itemAdd.do" name="f" method="post">
		<input type="hidden" name="categorynum" value="${ca.categorynum}" >
		<table class="wish_table" style="width:70%;">
			<caption>[ ${ca.categoryname} ] 물품 추가</caption>
			<tr>
				<th width="20%">물품명</th>
				<td width="70%"><input type="text" name="itemname" required></td>
			</tr>
			<tr>
				<th>가격</th>
				<td><input type="number" name="price"></td>
			</tr>
			<tr>
				<th>판매 사이트</th>
				<td><input type="url" name="url"></td>
			</tr>
			<tr>
				<th>메모</th>
				<td><input type="text" name="memo"></td>
			</tr>
		<tfoot>
			<tr>
				<td colspan="2" style="text-align: center;">
					<input type="submit" class="button" value="완료">
					<input type="button" onclick="closer()" value="취소">
				</td>
			</tr>
		</tfoot>
		</table>
	</form>
</div>
</body>
</html>