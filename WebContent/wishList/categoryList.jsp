<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- project/WebContent/wishList/main.jsp --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>위시리스트 메인</title>
<script type="text/javascript">
function delca(num) {
	if(confirm("카테고리를 삭제하시겠습니까?")==true) {
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
	<tr><td colspan="3"><h3>카테고리 목록</h3></td></tr>
	<tr><td colspan="3" style="text-align:left;">
		<a href="itemAllList.do?id=${sessionScope.login}&purchase=2" class="button">물품 전체 보기</a>
		<a class="button" onclick="win_open_small('wishList','categoryAddForm.do')">카테고리 추가</a></td></tr>
	<tr><th width="50%">카테고리명</th><th width="20%">물품 개수</th><th width="30%">변경 / 삭제</th></tr>
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