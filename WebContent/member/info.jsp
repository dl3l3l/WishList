<%@page import="model.Member"%>
<%@page import="model.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<%-- project/WebContent/member/info.jsp --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<link rel="stylesheet" href="../css/main.css" />
<title>회원 정보 보기</title>
<style type="text/css">
	table { margin:5px; width:30%; }
</style>
</head>
<body>
<div align="center" style="padding-top:50px;">
	<table class="board_table">
		<caption>회원 정보 보기</caption>
	<thead>
		<tr>
			<td colspan="2">
				<img src="picture/${mem.picture}" width="140" height="140" style="border-radius:70px;"></td>
		</tr>
	</thead>
		<tr>
			<th>아이디</th>
			<td>${mem.id}</td>
		</tr>
		<tr>
			<th>이름</th>
			<td>${mem.name}</td>
		</tr>
		<tr>
			<th>이메일</th>
			<td>${mem.email}</td>
		</tr>
	</table>
	<c:if test="${param.id == sessionScope.login}">
		<a href="updateForm.do?id=${mem.id}" class="button">수정</a>
	</c:if> 
	<c:if test="${param.id != 'admin' && sessionScope.login != 'admin'}">
		<a onclick="win_open_small('member','deleteForm.do?id=${mem.id}')" class="button">탈퇴</a>
	</c:if>
</div>
</body>
</html>
