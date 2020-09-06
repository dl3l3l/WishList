<%@page import="model.MemberDao"%>
<%@page import="model.Member"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%--/WebContent/layout/layout.jsp --%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<title><decorator:title /></title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript" src="http://www.chartjs.org/dist/2.9.3/Chart.min.js"></script>
<link rel="stylesheet" type="text/css" href="${path}/css/main.css" />
<script type="text/javascript" src="${path}/script.js"></script>
<style>
.w3-sidebar a {
	font-family: "Roboto", sans-serif;
}

body, h1, h2, h3, h4, h5, h6, .w3-wide {
	font-family: "Montserrat", sans-serif;
}

.w3-theme-l5, .w3-container.w3-large {
	
}
.w3-bar.w3-top {
	padding-top:15px;
	padding-bottom:15px;
	background-color: #DCDCDC;
} 
.w3-sidebar {
  z-index: 3;
  width: 250px;
  top: 80px;
  bottom: 0;
  height: inherit;
  box-shadow: 2px 5px rgba(0,0,0,0.16), 2px 2px rgba(0,0,0,0.12);
}

.myFooter {
	background-color: #DCDCDC;
}
</style>
<decorator:head />
<body>
	<!-- Navbar -->
	<div class="w3-top">
		<div class="w3-bar w3-theme w3-top w3-xlarge" >
		<span style="float: left; padding-left:50px">
					<i onclick="w3_close()" class="fa fa-remove w3-hide-large w3-button w3-display-topright"></i>
							<a href="${path}/main/index.do" class="home"><b>WishList</b>
							<img src="${path}/icon/shopping.png"></a>
		</span>
			<div class="w3-center w3-bold" style="margin-left: 250px;">
				<!-- 로고 -->
				<%
					String title = "";
					String uri = request.getRequestURI();
					if (uri.contains("main")) {
						title = "Main";
					} else if (uri.contains("member") || uri.contains("my")) {
						title = "My Page";
					} else if (uri.contains("wishList")) {
						title = "WishList";
					} else if (uri.contains("board")) {
						title = "Board";
					}
					request.setAttribute("title", title);
				%>
				<font size="5px" style="font-weight:bold;">${title}</font>
			<span style="float: right; font-size: 19px;">
				<a href="${path}/board/boardList.do?type=0" class="button">전체글보기</a> 
				<a href="${path}/board/boardList.do?type=90" class="button">공지사항</a>
			</span>
			</div>
		</div>
	</div>

	<!-- Sidebar -->
	<nav class="w3-sidebar w3-bar-block w3-collapse w3-large w3-theme-l5 w3-center w3-padding-16" id="mySidebar">
		<!-- 로그인 -->
	  <div id="login_section" style="padding-top:20px;">
		<c:if test="${empty sessionScope.login}">
			<form action="${path}/member/login.do" method="post" name="login">
				<input type="text" name="id" placeholder="아이디" class="login" required style="width:70%;"><br>
				<input type="password" name="pass" placeholder="비밀번호" class="login" required style="width:70%;"><br>
				<input type="submit" class="button" value="로그인">
				<a href="${path}/member/joinForm.do" class="button">회원가입</a><br>
			</form>
			<a class="find" onclick="win_open_find('member','idForm')">아이디찾기</a> / 
			<a class="find" onclick="win_open_find('member','pwForm')">비밀번호찾기</a>
		</c:if>
		<c:if test="${!empty sessionScope.login}">
			<% String picture = new MemberDao().getPicture((String) request.getSession().getAttribute("login")); %>
			<img src="${path}/member/picture/<%=picture %>" width="140" height="140" style="border-radius:70px;">
				<br> ${sessionScope.login}님 <br>
			<a href="${path}/member/myPage.do?id=${sessionScope.login}" class="button">마이페이지</a>
			<a href="${path}/member/logout.do" class="button">로그아웃</a>
		</c:if>
	  </div>
		<hr>
		<!-- 메뉴 -->
		<div class="w3-padding-10 w3-large" style="font-weight: bold;">
			<a href="${path}/wishList/categoryList.do?id=${sessionScope.login}"
				class="w3-bar-item w3-button">위시리스트</a> <a onclick="myAccFunc()"
				href="javascript:void(0)" class="w3-button w3-block w3-center-align"
				id="myBtn"> 게시판 <i class="fa fa-caret-down"></i>
			</a>
			<div id="demoAcc"
				class="w3-bar-block w3-hide w3-padding-medium w3-medium">
				<a href="${path}/board/boardList.do?type=10"
					class="w3-bar-item w3-button">후기</a> <a
					href="${path}/board/boardList.do?type=20"
					class="w3-bar-item w3-button">특가 정보</a> <a
					href="${path}/board/boardList.do?type=30"
					class="w3-bar-item w3-button">자유</a>
			</div>
		</div>
	</nav>
	<!-- Top menu on small screens -->
	<header class="w3-bar w3-top w3-hide-large w3-black w3-xlarge">
		<div class="w3-bar-item w3-padding-24 w3-wide">
			<a href="${path}/main/index.do" class="home">WishList</a>
		</div>
		<a href="javascript:void(0)"
			class="w3-bar-item w3-button w3-padding-24 w3-right"
			onclick="w3_open()"><i class="fa fa-bars"></i></a>
	</header>
	<!-- Overlay effect when opening sidebar on small screens -->
	<div class="w3-overlay w3-hide-large" onclick="w3_close()"
		style="cursor: pointer" title="close side menu" id="myOverlay"></div>


	<!-- !PAGE CONTENT! -->
	<div class="w3-main" style="margin-left: 250px">
		<!-- Push down content on small screens -->
		<div class="w3-hide-large" style="margin-top: 75px"></div>
		<div class="w3-row w3-padding-64"></div>
		<div class="w3-row">
			<decorator:body />
		</div>
	</div>
	<script>
		// Accordion 
		function myAccFunc() {
			var x = document.getElementById("demoAcc");
			if (x.className.indexOf("w3-show") == -1) {
				x.className += " w3-show";
			} else {
				x.className = x.className.replace(" w3-show", "");
			}
		}
		// 메뉴 - 게시판
		document.getElementById("myBtn").click();

		// Open and close sidebar
		function w3_open() {
			document.getElementById("mySidebar").style.display = "block";
			document.getElementById("myOverlay").style.display = "block";
		}

		function w3_close() {
			document.getElementById("mySidebar").style.display = "none";
			document.getElementById("myOverlay").style.display = "none";
		}
	</script>
</body>
</html>
