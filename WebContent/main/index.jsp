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
<title>메인 화면</title>
<style type="text/css">

</style>
</head>
<body>
<!-- 후기 -->
<div class="inform_container" style="display:flex; justify-content:center;">
<table class="board_table" style="width:80%">
<caption>후기 게시판</caption>
<thead>
<tr>
	<td style="text-align:right;" colspan="5"><a href="../board/boardList.do?type=10">더보기</a></td>
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
				<%  // 분류명
					int typenum = (int)pageContext.getAttribute("typeNum");
					String typename = new BoardDao().typeName(typenum); 
					// 댓글 수 
					int postnum = (int)pageContext.getAttribute("postNum");
					int rcount = new CommentDao().commentCount(postnum);
					request.setAttribute("rcount", rcount);%>
				<font size="2">[<%=typename %>]</font>
				${r.subject}</a>
				<!-- 댓글 수 나타내기 -->
				<c:if test="${rcount!=0}"><font color="red">[${rcount}]</font></c:if><br>
				<fmt:formatDate value="${r.regdate}" pattern="yyyy-MM-dd" />
				<font size="2" color="gray">조회:${r.readcnt}</font>
		</td>
	</c:if>
	</c:forEach>
</tr>
</tbody>
</table>
</div>
<hr>
<!-- 특가정보 -->
<div class="inform_container" style="display:flex; justify-content:center;">
<table class="board_table" style="width:40%"> 
<caption>특가정보 게시판</caption>
<thead>
<tr>
	<td style="text-align:right;" colspan="4"><a href="../board/boardList.do?type=20">더보기</a></td>
</tr>
</thead>
<tbody>
<tr>	
	<th width="10%">분류</th>
	<th width="30%">제목</th>
	<th width="23%">작성일</th>
	<th width="8%">조회수</th>
</tr>
<c:forEach var="i" items="${inform}">
<tr>
	<td> <!-- 분류 -->
		<c:set var="typeNum" value="${i.type}" />
		<c:set var="postNum" value="${i.postnum}" />
		<%  // 분류명
		int typenum = (int)pageContext.getAttribute("typeNum");
		String typename = new BoardDao().typeName(typenum); 
			// 댓글 수 
		int postnum = (int)pageContext.getAttribute("postNum");
		int rcount = new CommentDao().commentCount(postnum);
		request.setAttribute("rcount", rcount);%>
		<font size="2">[<%=typename %>]</font></td>
	<td style="text-align:left"> <!-- 제목 -->
		<a href="../board/postInfo.do?postnum=${i.postnum}" style="text-decoration:none;">
		&nbsp; ${i.subject}</a>
		<!-- 파일 유무 나타내기 -->
		<c:if test="${!empty i.file}"><img src="../icon/clip.png"></c:if>
		<!-- 댓글 수 나타내기 -->
		<c:if test="${rcount!=0}"><font color="red">[${rcount}]</font></c:if>
		</td>
	<td> <!-- 작성일 -->
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
<!-- 자유 -->
<table class="board_table" style="width:40%">
<caption>자유 게시판</caption>
<thead>
<tr>
	<td style="text-align:right;" colspan="3"><a href="../board/boardList.do?type=30">더보기</a></td>
</tr>
</thead>
<tbody>
<tr>	
	<th width="30%">제목</th>
	<th width="20%">작성일</th>
	<th width="10%">조회수</th>
</tr>
<c:forEach var="f" items="${free}">
<tr>
	<td style="text-align:left"> <!-- 제목 -->
		<a href="../board/postInfo.do?postnum=${f.postnum}" style="text-decoration:none;">
		&nbsp; ${f.subject}</a>
		<!-- 파일 유무 나타내기 -->
		<c:if test="${!empty f.file}"><img src="../icon/clip.png"></c:if>
		<!-- 댓글 수 나타내기 -->
		<c:set var="postNum" value="${f.postnum}" />
		<% int postnum = (int)pageContext.getAttribute("postNum");
			int rcount = new CommentDao().commentCount(postnum);
			request.setAttribute("rcount", rcount);%>
		<c:if test="${rcount!=0}"><font color="red">[${rcount}]</font></c:if>
		</td>
	<td> <!-- 작성일 -->
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