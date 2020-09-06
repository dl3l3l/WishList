<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- project/WebContent/member/myPage.jsp --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>My Page</title>
<style type="text/css">
.mybutton {
  background-color: #4d4d4d;
  border-radius: 8px;
  border: none;
  color: white;
  padding: 17px;
  text-align: center;
  text-decoration : none;
  font-size: 18px;
  margin: 10px 5px;
  min-width : 140px;
  cursor: pointer;
}
.mybutton img {
  margin: 5px;
}
</style>
</head>
<body>
<div style="display:flex; justify-content:center; padding-top:150px;">
	<a href="info.do?id=${sessionScope.login}" class="mybutton">
		<img src="../icon/mypage.png"><br>내 정보</a>&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="../board/myBoard.do?id=${sessionScope.login}" class="mybutton"> 
		<img src="../icon/myboard.png"><br>작성글</a>&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="../board/myComment.do?id=${sessionScope.login}" class="mybutton"> 
		<img src="../icon/mycomment.png"><br>작성 댓글</a>&nbsp;&nbsp;&nbsp;&nbsp;
</div>
<c:if test="${sessionScope.login=='admin'}">
<hr>
<div style="display:flex; justify-content:center;">
	<a href="memberList.do" class="mybutton">
		<img src="../icon/member.png"><br>회원 목록</a>
	<a href="../board/boardUsage.do" class="mybutton">
		<img src="../icon/chart.png"><br><font size="3">게시판 이용 현황</font></a>
</div>
</c:if>

</body>
</html>