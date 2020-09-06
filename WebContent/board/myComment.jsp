<%@page import="model.Board"%>
<%@page import="model.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>�ۼ� ���</title>
<script type="text/javascript">
function listdo(page) {
	f = document.searchf;
	f.pageNum.value=page;
	f.submit();
}

</script>
</head>
<body>
<div style="display:flex; justify-content:center; padding-top:50px;">
<form name="commentf">
	<input type="hidden" name="pageNum" value="1">
</form>
<table class="board_table" style="width:60%;">
<caption>�� ��� ��ȸ</caption>
<tr>
	<th width="60%">��� ����</th>
	<th width="20%">�ش� ��</th>
	<th width="20%">�ۼ���</th>
</tr>
<c:if test="${replycount==0}">
	<tr><td colspan="3"> ��ϵ� ����� �����ϴ�. </td></tr>
</c:if>
<c:if test="${replycount>0}">
<c:forEach var="c" items="${clist}">
<tr>
	<!--��� ����  -->
	<td style="padding-left:8px; text-align:left">${c.content}</td>
	<!--��� �� �Խñ� ����  -->
	<c:set var="postNum" value="${c.postnum}" />
	<% int postnum = (int)pageContext.getAttribute("postNum");
	   Board b = new BoardDao().selectOne(postnum);
	   String subject = b.getSubject();
	   request.setAttribute("subject", subject); %>
	<td><a href="postInfo.do?postnum=${c.postnum}">${subject}</a></td>
	<!--�ۼ���  -->
	<td><fmt:formatDate value="${c.regdate}" pattern="yyyy-MM-dd" /></td> 
</tr>
</c:forEach>
<%-- ������ ó���ϱ� --%>
<tfoot>
	<tr><td colspan="3">
		<c:if test="${pageNum<=1}"><span class="page">����</span></c:if>
		<c:if test="${pageNum>1}"><a class="page" href="javascript:listdo(${pageNum-1})">����</a></c:if>
		<c:forEach var="a" begin="${startpage}" end="${endpage}">
			<c:if test="${a==pageNum}"><span class="now_page">${a}</span></c:if>
			<c:if test="${a!=pageNum}"><a class="page" href="javascript:listdo(${a})">${a}</a></c:if>
		</c:forEach>
		<c:if test="${pageNum>=maxpage}"><span class="page">����</span></c:if>
		<c:if test="${pageNum<maxpage}"><a class="page" href="javascript:listdo(${pageNum+1})">����</a></c:if>
		</td>
	</tr>
</tfoot>
</c:if>
</table>
</div>
</body>
</html>