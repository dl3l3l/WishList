<%@page import="model.CommentDao"%>
<%@page import="model.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>작성글</title>
<script type="text/javascript">
	function listdo(page) {
		f = document.pagef;
		f.pageNum.value=page;
		f.submit();
	}
</script>
</head>
<body>
<div style="display:flex; justify-content:center; padding-top:40px;">
<form action="myBoard.do?id=${sessionScope.login}" method="post" name="pagef">
			<input type="hidden" name="pageNum" value="1">
</form>
<table class="board_table">
	<caption>내 게시글 조회</caption>
  <thead>
	<tr><td colspan="5" style="text-align:right">글 개수 : ${boardcount}</td></tr>
	<tr><th width="6%">번호</th>
		<th width="10%">&nbsp;</th>
		<th width="50%">제목</th>
		<th width="17%">작성일</th>
		<th width="11%">조회수</th></tr>
  </thead>
	<c:if test="${boardcount==0}">
	<tr><td colspan="5"> 등록된 게시글이 없습니다. </td></tr>
	</c:if>
<%-- 게시글 조회하기 --%>
	<c:if test="${boardcount>0}">
<c:forEach var="b" items="${list}">
	<tr <c:if test="${b.type==90}">bgcolor=#ff8a8a</c:if>>
		<td>${boardnum}</td><c:set var="boardnum" value="${boardnum-1}" />
		<%--카테고리 리스트에 나타내기 --%>
		<td><c:set var="typeNum" value="${b.type}" />
			<c:set var="postNum" value="${b.postnum}" />
		<% 
		int typenum = (int)pageContext.getAttribute("typeNum");
		String btype = "";
		if(typenum < 30){
			btype=new BoardDao().typeName((typenum/10)*10);
			btype += "-";
		}
		String typename = new BoardDao().typeName(typenum); 

		int postnum = (int)pageContext.getAttribute("postNum");
		int rcount = new CommentDao().commentCount(postnum);
		request.setAttribute("rcount", rcount);
		%>
		<font size="2">[<c:if test="${bt=='전체'}"><%=btype%></c:if><%=typename %>]</font></td>
		<td style="text-align:left">
			<a href="postInfo.do?postnum=${b.postnum}" style="text-decoration:none;">
			&nbsp; ${b.subject}</a>
			<!-- 파일 유무 나타내기 -->
			<c:if test="${!empty b.file}"><img src="../icon/clip.png"></c:if>
			<!-- 댓글 수 나타내기 -->
			<c:if test="${rcount!=0}"><font color="red">[${rcount}]</font></c:if></td>
		<td><fmt:formatDate var="rdate" value="${b.regdate}" pattern="yyyy-MM-dd" />
			<c:if test="${today==rdate}">
				<fmt:formatDate value="${b.regdate}" pattern="HH:mm:ss" />
			</c:if>
			<c:if test="${today!=rdate}">
				<fmt:formatDate value="${b.regdate}" pattern="yyyy-MM-dd HH:mm:ss" />
			</c:if></td>
		<td>${b.readcnt}</td>
	</tr>
</c:forEach>
  <tfoot>
	<%-- 페이지 처리하기 --%>
	<tr><td colspan="5">
		<c:if test="${pageNum<=1}"><span class="page">이전</span></c:if>
		<c:if test="${pageNum>1}"><a class="page" href="javascript:listdo(${pageNum-1})">이전</a></c:if>
		<c:forEach var="a" begin="${startpage}" end="${endpage}">
			<c:if test="${a==pageNum}"><span class="now_page">${a}</span></c:if>
			<c:if test="${a!=pageNum}"><a class="page" href="javascript:listdo(${a})">${a}</a></c:if>
		</c:forEach>
		<c:if test="${pageNum>=maxpage}"><span class="page">다음</span></c:if>
		<c:if test="${pageNum<maxpage}"><a class="page" href="javascript:listdo(${pageNum+1})">다음</a></c:if>
		</td>
	</tr>
  </tfoot>
	</c:if>
</table>
</div>
</body>
</html>