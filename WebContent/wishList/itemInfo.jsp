<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<link rel="stylesheet" type="text/css" href="../css/main.css" />
<title>${item.itemname} 조회</title>
<script type="text/javascript">
function delitem(num) {
	if(confirm("해당 물품을 삭제하시겠습니까?")==true) {
		location.href="itemDelete.do?itemnum="+num 
	} else {
		return;
	}
}
</script>
</head>
<body>
<div style="padding:0px 140px;">
	<a href="itemList.do?categorynum=${ca.categorynum}&purchase=2" style=" text-decoration : none;">
	<img src="../icon/arrow.png">
	<font size="3">[ ${ca.categoryname} ] 목록으로</font></a>
</div>
<div id="itemInfo" align="center" style="padding-top:30px;">
	<table class="wish_table" style="width:65%;">
		<caption>물품 조회</caption>
		<tr>
			<th width="20%">카테고리명</th>
			<td width="50%">${ca.categoryname}</td>
		</tr>
		<tr>
			<th>물품명</th>
			<td>${item.itemname}</td>
		</tr>
		<tr>
			<th>가격</th>
			<td>${item.price}</td>
		</tr>
		<tr>
			<th>판매 사이트</th>
			<td><a href="${item.url}" target="_blank" style="word-break:break-all;">${item.url}</a></td>
		</tr>
		<tr>
			<th>메모</th>
			<td style="word-break:break-all;">${item.memo}</td>
		</tr>
		<tr>
			<th>구매 여부</th>
			<td>${item.purchase==1?"O":"X"}</td>
		</tr>
	</table>
</div>
	<div id="itemInfo_button" align="center">
		<a href="itemEditForm.do?itemnum=${item.itemnum}" class="button">수정</a>
		<a onclick="delitem(${item.itemnum})" class="button">삭제</a>
		<a href="../board/writeForm.do?id=${sessionScope.login}&type=20&itemnum=${item.itemnum}"
			class="button">정보 공유</a>
		<c:if test="${item.purchase==1}">
			<a href="../board/writeForm.do?id=${sessionScope.login}&type=10&itemnum=${item.itemnum}"
				class="button">후기</a>
		</c:if>
	</div>
</body>
</html>