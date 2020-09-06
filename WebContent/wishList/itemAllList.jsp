<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>��ǰ ��ü ����</title>
</head>
<body>
<%--���� ���� --%>
<c:set var="priceSum" value="0" />
<c:set var="buySum" value="0" />
<c:set var="count" value="0" />
<c:set var="buycount" value="0" />
<%--��ǰ ���� ��� --%>
<div class="wish_body">
<table class="wish_table" style="width:70%;">
<caption>
	<font size="5px">��ǰ ��ü ���</font>
</caption>
<thead>
<tr><td  style="text-align:left;">
		<a href="categoryList.do?id=${sessionScope.login}" class="button" >ī�װ� ���</a>	
	</td>
	<td colspan="3" style="text-align:right;">
	<form action="itemAllList.do?id=${sessionScope.login}" name="purchasef" method="post">
  		<label>����:</label>
  		<select name="purchase" id="purchase">
   			 <option value="2">��ü</option>
   			 <option value="1">�Ϸ�</option>
   			 <option value="0">�̿Ϸ�</option>
  		</select>
  		<input type="submit" value="��ȸ">
	</form></td></tr>
</thead>
	<tr><th>ī�װ���</th><th>��ǰ��</th><th>����</th><th>���ſ���</th></tr>
<c:forEach var="i" items="${itemlist}">
	<tr <c:if test="${i.purchase==1}">bgcolor=#AFDFE4</c:if>>
		<td>${i.memo}</td>
		<td><a href="itemInfo.do?itemnum=${i.itemnum}">${i.itemname}</a></td>
		<td>${i.price}</td>
		<td>${i.purchase==1?"O":"X"}</td>
	</tr>
<%--��ǰ ���� �հ� ���ϱ� --%>
<c:set var="priceSum" value="${priceSum+i.price}" />
<c:if test="${i.purchase==1}">
<c:set var="buySum" value="${buySum+i.price}" />
</c:if>
<%--��ǰ ���� ���ϱ� --%>
<c:set var="count" value="${count+1}" />
<c:if test="${i.purchase==1}">
<c:set var="buycount" value="${buycount+1}" />
</c:if>
</c:forEach>
<tr><th></th>
<th></th>
<th> �հ� :  <c:out value="${buySum}" /> / <c:out value="${priceSum}" /></th>
<th> �� ���� : <c:out value="${buycount}"/> / <c:out value="${count}"/></th></tr>
</table>
</div>
</body>
</html>