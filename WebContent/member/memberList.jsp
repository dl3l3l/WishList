<%@page import="model.MemberDao"%>
<%@page import="java.util.List"%>
<%@page import="model.Member"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<%-- project/WebContent/member/memberList.jsp --%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript" src="http://www.chartjs.org/dist/2.9.3/Chart.min.js"></script>
<meta charset="EUC-KR">
<title>회원 목록 보기</title>
<style type="text/css">
table {
	width: 55%;
}
</style>
</head>
<body>
	<div style="display: flex; padding-top: 20px; justify-content: center;">
		<table class="board_table">
			<caption>회원 목록</caption>
			<tr>
				<th>아이디</th>
				<th>사진</th>
				<th>이름</th>
				<th>&nbsp;</th>
			</tr>
			<%-- list : request.setAttribute("list", new MemberDao().list());로 설정해준 값 --%>
			<c:forEach var="m" items="${list}">
				<tr>
					<td><a href="info.do?id=${m.id}">${m.id}</a></td>
					<td><img src="picture/sm_${m.picture}" width="40" height="45"></td>
					<td>${m.name}</td>
					<td><c:if test="${m.id != 'admin'}">
							<a href="delete.do?id=${m.id}" class="button2" style="font-size:14px">강제탈퇴 </a>
						</c:if></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>
