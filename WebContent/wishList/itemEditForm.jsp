<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>${item.itemname} 정보 수정</title>
</head>
<body>
<div align="center" style="padding-top:50px;">
	<form action="itemEdit.do" name="f" method="post">
		<input type="hidden" name="itemnum" value="${item.itemnum}">
		<table class="wish_table" style="width:70%">
			<caption>물품 정보 수정</caption>
			<tr>
				<th width="20%">카테고리명</th>
				<td width="70%"><input type="text" name="categoryname" value="${ca.categoryname}" readonly></td>
			</tr>
			<tr>
				<th>물품명</th>
				<td><input type="text" name="itemname" value="${item.itemname}" required></td>
			</tr>
			<tr>
				<th>가격</th>
				<td><input type="number" name="price" value="${item.price}"></td>
			</tr>
			<tr>
				<th>판매 사이트</th>
				<td><input type="url" name="url" value="${item.url}"></td>
			</tr>
			<tr>
				<th>메모</th>
				<td><input type="text" name="memo" value="${item.memo}"></td>
			</tr>
			<tr>
				<th>구매 여부</th>
				<td><input type="radio" name="purchase" value=1 <c:if test="${item.purchase==1}">checked</c:if>>완료 
					<input type="radio" name="purchase" value=0 <c:if test="${item.purchase==0}">checked</c:if>>미완료</td>
			</tr>
		<tfoot>
			<tr>
				<td colspan="2"><input type="submit" class="button" value="수정">
								<a href="itemInfo.do?itemnum=${item.itemnum}" class="button">취소</a>
				</td>
			</tr>
		</tfoot>
		</table>
	</form>
</div>
</body>
</html>