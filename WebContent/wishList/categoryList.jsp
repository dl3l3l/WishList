<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- project/WebContent/wishList/main.jsp --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>���ø���Ʈ ����</title>
<script type="text/javascript">
function delca(num) {
	if(confirm("ī�װ��� �����Ͻðڽ��ϱ�?")==true) {
		location.href="categoryRemove.do?categorynum=" + num 
	} else {
		return;
	}
}
</script>
</head>
<body>
<div class="wish_body">
<table class="wish_table" style="width:70%; table-layout:fixed;">
<thead>
	<tr><td colspan="3"><h3>ī�װ� ���</h3></td></tr>
	<tr><td colspan="3" style="text-align:left;">
		<a href="itemAllList.do?id=${sessionScope.login}&purchase=2" class="button">��ǰ ��ü ����</a>
		<a class="button" onclick="win_open_small('wishList','categoryAddForm.do')">ī�װ� �߰�</a></td></tr>
	<tr><th width="50%">ī�װ���</th><th width="20%">��ǰ ����</th><th width="30%">���� / ����</th></tr>
</thead>
<c:forEach var="ca" items="${list}">
	<tr>
		<td><a href="itemList.do?categorynum=${ca.categorynum}&purchase=2">${ca.categoryname}</a></td>
		<td>${ca.id}</td>
		<td><a onclick="win_open_small('wishList','categoryEditForm.do?categorynum=${ca.categorynum}')">
			<img src="../icon/pencil.png"></a>
			&nbsp;
			<a onclick="delca(${ca.categorynum})">
			<img src="../icon/trash.png"></a>
		</td>
	</tr>
</c:forEach>
</table>
</div>
</body>
</html>