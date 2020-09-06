<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<link rel="stylesheet" type="text/css" href="../css/main.css" />
<title>${item.itemname} ��ȸ</title>
<script type="text/javascript">
function delitem(num) {
	if(confirm("�ش� ��ǰ�� �����Ͻðڽ��ϱ�?")==true) {
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
	<font size="3">[ ${ca.categoryname} ] �������</font></a>
</div>
<div id="itemInfo" align="center" style="padding-top:30px;">
	<table class="wish_table" style="width:65%;">
		<caption>��ǰ ��ȸ</caption>
		<tr>
			<th width="20%">ī�װ���</th>
			<td width="50%">${ca.categoryname}</td>
		</tr>
		<tr>
			<th>��ǰ��</th>
			<td>${item.itemname}</td>
		</tr>
		<tr>
			<th>����</th>
			<td>${item.price}</td>
		</tr>
		<tr>
			<th>�Ǹ� ����Ʈ</th>
			<td><a href="${item.url}" target="_blank" style="word-break:break-all;">${item.url}</a></td>
		</tr>
		<tr>
			<th>�޸�</th>
			<td style="word-break:break-all;">${item.memo}</td>
		</tr>
		<tr>
			<th>���� ����</th>
			<td>${item.purchase==1?"O":"X"}</td>
		</tr>
	</table>
</div>
	<div id="itemInfo_button" align="center">
		<a href="itemEditForm.do?itemnum=${item.itemnum}" class="button">����</a>
		<a onclick="delitem(${item.itemnum})" class="button">����</a>
		<a href="../board/writeForm.do?id=${sessionScope.login}&type=20&itemnum=${item.itemnum}"
			class="button">���� ����</a>
		<c:if test="${item.purchase==1}">
			<a href="../board/writeForm.do?id=${sessionScope.login}&type=10&itemnum=${item.itemnum}"
				class="button">�ı�</a>
		</c:if>
	</div>
</body>
</html>