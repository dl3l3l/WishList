<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- project/WebContent/main/noticeForm.jsp --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>공지사항 작성</title>
</head>
<body>
<div align="center">
<form action="noticeWrite.do?id=${sessionScope.login}" method="post" enctype="multipart/form-data" name="post">
<table class="board_table">
	<caption>공지사항 작성</caption>
	<tr><th>제목</th><td><input type="text" name="subject"></td></tr>
	<tr><th>내용</th><td><textarea rows="15" name="content"></textarea></td></tr>
	<tr><th>첨부파일</th><td><input type="file" name="file"></td></tr>
  <tfoot>
	<tr><td colspan="2"><input type="submit" class="button" value="완료">
						<input type="button" onclick="history.go(-1)" value="취소"></td></tr>
  </tfoot>
	</table>	  
</form>
</div>
</body>
</html>