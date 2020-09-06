<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>카테고리 추가</title>
<link rel="stylesheet" href="../css/main.css">
<script type="text/javascript">
function reclose() {
	self.close();
	opener.document.location.reload();
}
</script>
</head>
<body>
	<form action="categoryAdd.do" name="f" method="post">
		<table class="wish_table">
			<tr>
				<th>카테고리명</th>
				<td><input type="text" name="categoryname" required></td>
			</tr>
		<tfoot>
			<tr>
				<td colspan="2" style="text-align: center;">
					<input type="submit" class="button" value="추가">
					<input type="button" value="닫기" onclick="reclose()">
				</td>
			</tr>
		</tfoot>
		</table>
	</form>
</body>
</html>