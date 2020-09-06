<%@page import="model.Board"%>
<%@page import="model.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- project/WebContent/board/updateForm.jsp --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>게시물 수정하기</title>
<script>
	function file_delete() {
		document.post.file2.value="";
		file_desc.style.display="none";
	}
</script>
</head>
<body>
<div align="center">
<form action="update.do?id=${sessionScope.login}&postnum=${b.postnum}" method="post" enctype="multipart/form-data" name="post">
<input type="hidden" name="file2" value="${b.file}">
<table class="board_table">
<caption>게시물 수정</caption>
<tr><th>카테고리</th>
	<td style="text-align:left">
  	   <select name="type" id="type">
			<c:if test="${btype==10}"> <%--후기 게시판인 경우 --%>
				<option>선택</option>
				<option value='11' <c:if test="${b.type==11}">selected</c:if>>가전</option>
				<option value='12' <c:if test="${b.type==12}">selected</c:if>>가구</option>
				<option value='13' <c:if test="${b.type==13}">selected</c:if>>식품/건강</option>
				<option value='14' <c:if test="${b.type==14}">selected</c:if>>생필품</option>
				<option value='15' <c:if test="${b.type==15}">selected</c:if>>패션</option>
				<option value='16' <c:if test="${b.type==16}">selected</c:if>>뷰티</option>
				<option value='17' <c:if test="${b.type==17}">selected</c:if>>육아</option>
				<option value='18' <c:if test="${b.type==18}">selected</c:if>>기타</option>
			</c:if>
			<c:if test="${btype==20}"> <%--특가정보 게시판인 경우 --%>
				<option>선택</option> 
				<option value='21' <c:if test="${b.type==21}">selected</c:if>>국내</option>
				<option value='22' <c:if test="${b.type==22}">selected</c:if>>해외</option>
			</c:if>
			<c:if test="${btype==30}"> <%--자유 게시판인 경우 --%>
				<option value='30' selected>자유</option>
			</c:if>
			<c:if test="${b.type==90}"> <%--공지사항 게시판인 경우 --%>
				<option value='90' selected>공지사항</option>
			</c:if>
  	 	  	</select>
  	   </td></tr>
<tr><th>제목</th>
	<td><input type="text" name="subject" value="${b.subject}"></td>
</tr>
<tr><th>내용</th>
	<td><textarea rows="15" name="content" id="content1">${b.content}</textarea></td>
</tr>
<tr><th>첨부파일</th>
	<td style="text-align:left">
	<c:if test="${!empty b.file}">
	<div id="file_desc">${b.file}<a href="javascript:file_delete()">[첨부파일 삭제]</a></div>
	</c:if><input type="file" name="file">	
	</td>
</tr>
<tfoot>
<tr><td colspan="2">
	<input type="submit" class="button" value="완료">
	<input type="button" onclick="history.go(-1)" value="취소"></td>
</tr>
</tfoot></table></form>
</div>
</body>
</html>