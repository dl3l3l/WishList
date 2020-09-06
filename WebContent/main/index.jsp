<%@page import="model.CommentDao"%>
<%@page import="model.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>���� ȭ��</title>
<style type="text/css">

</style>
</head>
<body>
<!-- �ı� -->
<div class="inform_container" style="display:flex; justify-content:center;">
<table class="board_table" style="width:80%">
<caption>�ı� �Խ���</caption>
<thead>
<tr>
	<td style="text-align:right;" colspan="5"><a href="../board/boardList.do?type=10">������</a></td>
</tr>
</thead>
<tbody>
<tr>
	<c:forEach var="r" items="${review}">
	<c:if test="${!empty r.file}">
		<td><a href="../board/postInfo.do?postnum=${r.postnum}" style="text-decoration:none;">
			<img src="${path}/board/file/${r.file}" width="145" height="160" style="margin:5px;"><br>
				<c:set var="typeNum" value="${r.type}" />
				<c:set var="postNum" value="${r.postnum}" />
				<%  // �з���
					int typenum = (int)pageContext.getAttribute("typeNum");
					String typename = new BoardDao().typeName(typenum); 
					// ��� �� 
					int postnum = (int)pageContext.getAttribute("postNum");
					int rcount = new CommentDao().commentCount(postnum);
					request.setAttribute("rcount", rcount);%>
				<font size="2">[<%=typename %>]</font>
				${r.subject}</a>
				<!-- ��� �� ��Ÿ���� -->
				<c:if test="${rcount!=0}"><font color="red">[${rcount}]</font></c:if><br>
				<fmt:formatDate value="${r.regdate}" pattern="yyyy-MM-dd" />
				<font size="2" color="gray">��ȸ:${r.readcnt}</font>
		</td>
	</c:if>
	</c:forEach>
</tr>
</tbody>
</table>
</div>
<hr>
<!-- Ư������ -->
<div class="inform_container" style="display:flex; justify-content:center;">
<table class="board_table" style="width:40%"> 
<caption>Ư������ �Խ���</caption>
<thead>
<tr>
	<td style="text-align:right;" colspan="4"><a href="../board/boardList.do?type=20">������</a></td>
</tr>
</thead>
<tbody>
<tr>	
	<th width="10%">�з�</th>
	<th width="30%">����</th>
	<th width="23%">�ۼ���</th>
	<th width="8%">��ȸ��</th>
</tr>
<c:forEach var="i" items="${inform}">
<tr>
	<td> <!-- �з� -->
		<c:set var="typeNum" value="${i.type}" />
		<c:set var="postNum" value="${i.postnum}" />
		<%  // �з���
		int typenum = (int)pageContext.getAttribute("typeNum");
		String typename = new BoardDao().typeName(typenum); 
			// ��� �� 
		int postnum = (int)pageContext.getAttribute("postNum");
		int rcount = new CommentDao().commentCount(postnum);
		request.setAttribute("rcount", rcount);%>
		<font size="2">[<%=typename %>]</font></td>
	<td style="text-align:left"> <!-- ���� -->
		<a href="../board/postInfo.do?postnum=${i.postnum}" style="text-decoration:none;">
		&nbsp; ${i.subject}</a>
		<!-- ���� ���� ��Ÿ���� -->
		<c:if test="${!empty i.file}"><img src="../icon/clip.png"></c:if>
		<!-- ��� �� ��Ÿ���� -->
		<c:if test="${rcount!=0}"><font color="red">[${rcount}]</font></c:if>
		</td>
	<td> <!-- �ۼ��� -->
		<fmt:formatDate var="rdate" value="${i.regdate}" pattern="yyyy-MM-dd" />
		<c:if test="${today==rdate}">
			<fmt:formatDate value="${i.regdate}" pattern="HH:mm:ss" />
		</c:if>
		<c:if test="${today!=rdate}">
			<fmt:formatDate value="${i.regdate}" pattern="yyyy-MM-dd HH:mm:ss" />
		</c:if></td>
	<td>${i.readcnt}</td>
</tr>
</c:forEach>
</tbody>
</table> &nbsp;&nbsp;&nbsp;
<!-- ���� -->
<table class="board_table" style="width:40%">
<caption>���� �Խ���</caption>
<thead>
<tr>
	<td style="text-align:right;" colspan="3"><a href="../board/boardList.do?type=30">������</a></td>
</tr>
</thead>
<tbody>
<tr>	
	<th width="30%">����</th>
	<th width="20%">�ۼ���</th>
	<th width="10%">��ȸ��</th>
</tr>
<c:forEach var="f" items="${free}">
<tr>
	<td style="text-align:left"> <!-- ���� -->
		<a href="../board/postInfo.do?postnum=${f.postnum}" style="text-decoration:none;">
		&nbsp; ${f.subject}</a>
		<!-- ���� ���� ��Ÿ���� -->
		<c:if test="${!empty f.file}"><img src="../icon/clip.png"></c:if>
		<!-- ��� �� ��Ÿ���� -->
		<c:set var="postNum" value="${f.postnum}" />
		<% int postnum = (int)pageContext.getAttribute("postNum");
			int rcount = new CommentDao().commentCount(postnum);
			request.setAttribute("rcount", rcount);%>
		<c:if test="${rcount!=0}"><font color="red">[${rcount}]</font></c:if>
		</td>
	<td> <!-- �ۼ��� -->
		<fmt:formatDate var="rdate" value="${f.regdate}" pattern="yyyy-MM-dd" />
		<c:if test="${today==rdate}">
			<fmt:formatDate value="${f.regdate}" pattern="HH:mm:ss" />
		</c:if>
		<c:if test="${today!=rdate}">
			<fmt:formatDate value="${f.regdate}" pattern="yyyy-MM-dd HH:mm:ss" />
		</c:if></td>
	<td>${f.readcnt}</td>
</tr>
</c:forEach>
</tbody>
</table>
</div>
</body>
</html>