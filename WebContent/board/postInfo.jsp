<%@page import="model.MemberDao"%>
<%@page import="model.Board"%>
<%@page import="model.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<%--/WebContent/model2/board/info.jsp --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>게시글 상세 조회</title>
<script type="text/javascript">
	function delpost() {
		if(confirm("게시글을 삭제하시겠습니까?")==true) {
			location.href="delete.do?postnum=${b.postnum}"
		} else {
			return;
		}
	}
	
	function delcom(num) {
		if(confirm("댓글을 삭제하시겠습니까?")==true) {
			location.href="commentDelete.do?commentnum=" + num 
		} else {
			return;
		}
	}

</script>
</head>
<body>
<div id="board_section" style="display:flex; justify-content:center;">
<table style="width:70%; background-color: #e3e4e2;">
<caption style="background-color:white">${bt} 게시판</caption>
<tr style="background-color:white;"> <!-- 버튼 표시 -->
	<td colspan="2" style="text-align:left;">
	<c:if test="${b.id==sessionScope.login}">
	<a href="updateForm.do?postnum=${b.postnum}" class="button">수정</a>
	</c:if>
	<c:if test="${b.id == sessionScope.login || sessionScope.login=='admin'}">
	<input type="button" onclick="delpost()" value="삭제">
	</c:if>
	</td>
	<td style="text-align:right;">
	<a href="boardList.do?type=${btype}" class="button">목록</a>
	</td>
</tr>
<tr> <!-- 제목 -->
	<td colspan="3"><h4>${b.subject}</h4></td>
</tr>
<tr> <!-- 작성자 -->
	<td colspan="1" style="text-align:left;">
	<b>${b.id}</b>
	<img src="${path}/member/picture/sm_${writer.picture}" width="30" height="30" style="border-radius:70px;">
	</td>
	<!-- 작성일 -->
	<td colspan="2" style="text-align:right;"><fmt:formatDate value="${b.regdate}" pattern="yyyy.MM.dd HH:mm" /></td>
</tr>
<tr> <!-- 카테고리, 조회수 -->
	<td style="text-align:left;"><c:if test="${dt!=''}">카테고리 : ${dt}</c:if></td>
	<td colspan="2" style="text-align:right;">조회수 : ${b.readcnt}</td>
<tr>
<td colspan="3" style="border:1px solid black; min-height:500px;">
<div style="margin:10px;">
<c:if test="${!empty b.file}"><img src="file/${b.file}" style="max-width:400px; max-height:450px;"><br></c:if>
${b.content}
</div>
</td>
</tr>
<tr>
	<td colspan="3">
		<c:if test="${empty b.file}">&nbsp;</c:if>
		<c:if test="${!empty b.file}"><a href="file/${b.file}">${b.file}</a></c:if>
	</td>
</tr>
</table>
</div>
<hr>
<!-- 댓글창 -->
<form action="reply.do?postnum=${b.postnum}" name="reply" method="post">
<div id="reply_section" style="display:flex; justify-content:center;">
<table style="width:70%; background-color: #e3e4e2;">
<c:forEach var="c" items="${clist}">
<c:set var="cid" value="${c.id}" />
	<tr><% String picture = new MemberDao().getPicture((String)pageContext.getAttribute("cid")); %>
		<td colspan="2" style="width:20%; text-align:left;">${c.id}
		<img src="${path}/member/picture/sm_<%=picture %>" width="25" height="25" style="border-radius:70px;">
		<font size="1.8px"><fmt:formatDate value="${c.regdate}" pattern="yyyy.MM.dd HH:mm" /></font></td>
	</tr>
	<tr>
		<td style="width:1%;">&nbsp;</td>
		<td style="width:90%; text-align:left;">
		${c.content}
		<c:if test="${c.id == sessionScope.login}">
			<a onclick="win_open_small('board','commentEditForm.do?commentnum=${c.commentnum}')" style="text-decoration:none;">
				<img src="../icon/pen16.png">
			</a>
		</c:if>
		<c:if test="${c.id == sessionScope.login || sessionScope.login=='admin'}">
			<a onclick="delcom(${c.commentnum})" style="text-decoration:none;">
				<img src="../icon/x12.png">
			</a>
		</c:if>
		</td>
	</tr>
</c:forEach>
	<tr>
		<td colspan="2">
		<input style="width:88%;" type="text" name="content" placeholder="댓글을 입력하세요.">
			<input style="width:10%;" type="submit" value="등록">
		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>