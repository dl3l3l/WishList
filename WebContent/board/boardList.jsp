<%@page import="model.CommentDao"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@page import="model.BoardDao"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%-- project/WebContent/board/list.jsp --%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<meta charset="EUC-KR">
<title>${bt} 게시판 조회</title>
<script type="text/javascript">
	function listdo(page) {
		f = document.searchf;
		f.pageNum.value=page;
		f.submit();
	}
	function typeList(type){
		location.href = "boardList.do?type="+type;
	}
</script>
</head>
<body>
<div style="display:flex; justify-content:center;">
<table class="board_table" style="width:85%;">
  <thead>
	<tr><td colspan="5"><h3>${bt} 게시판</h3></td></tr>
		<!-- 검색 기능  -->
	<tr><td colspan="5">
		<form action="boardList.do?type=${boardtype}" method="post" name="searchf">
			<input type="hidden" name="pageNum" value="1">
			<select name="column">
				<option>선택</option>
				<option value="subject">제목</option>
				<option value="content">내용</option>
				<option value="subject,content">제목+내용</option>
				<option value="id">작성자</option>
			</select>
		<script>document.searchf.column.value = "${param.column}";</script>
			<input type="text" name="find" value="${param.find}" style="width:50%;">
			<input type="submit" value="검색">
		</form></td></tr>
		<!-- 카테고리별 조회 -->
	<tr><td colspan="2" style="text-align:left;">
  		<c:if test="${boardtype!=0 && boardtype<30}"> <!-- 전체게시판, 공지사항게시판, 자유 게시판은 나타내지 않음  -->
  			<label>카테고리:</label>
  			<select name="type" id="type" onChange="typeList(this.value)">
  				<option>선택</option>
   			<c:if test="${fn:startsWith(boardtype,1)}">
   			 	<option value="11" <c:if test="${boardtype==11}">selected</c:if>>가전</option>
   				<option value="12" <c:if test="${boardtype==12}">selected</c:if>>가구</option>
   			 	<option value="13" <c:if test="${boardtype==13}">selected</c:if>>식품/건강</option>
   			 	<option value="14" <c:if test="${boardtype==14}">selected</c:if>>생필품</option>
   			 	<option value="15" <c:if test="${boardtype==15}">selected</c:if>>패션</option>
   			 	<option value="16" <c:if test="${boardtype==16}">selected</c:if>>뷰티</option>
   			 	<option value="17" <c:if test="${boardtype==17}">selected</c:if>>육아</option>
   			 	<option value="18" <c:if test="${boardtype==18}">selected</c:if>>기타</option>
   			</c:if>
   			<c:if test="${fn:startsWith(boardtype,2)}">
   			 	<option value="21" <c:if test="${boardtype==21}">selected</c:if>>국내</option>
   			 	<option value="22" <c:if test="${boardtype==22}">selected</c:if>>해외</option>
   			</c:if>
  		</select></c:if></td>
		<!-- 게시글 개수 -->
		<td colspan="4" style="text-align:right;">글 개수 : ${boardcount}</td></tr>
	<tr><th width="6%">번호</th>
		<th width="10%">&nbsp;</th>
		<th width="42%">제목</th>
		<th width="15%">작성자</th>
		<th width="20%">작성일</th>
		<th width="7%">조회수</th></tr>
	<c:if test="${boardcount==0}">
	<tr><td colspan="5"> 등록된 게시글이 없습니다. </td></tr>
	</c:if>
 </thead>
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
		<td style="text-align:left"> <!-- 제목 -->
		<a href="postInfo.do?postnum=${b.postnum}" style="text-decoration:none;">
		&nbsp; ${b.subject}</a>
		<!-- 파일 유무 나타내기 -->
		<c:if test="${!empty b.file}"><img src="../icon/clip.png"></c:if>
		<!-- 댓글 수 나타내기 -->
		<c:if test="${rcount!=0}"><font color="red">[${rcount}]</font></c:if>
		</td>
		<td>${b.id}</td> <!-- 작성자 -->
		<td><!-- 작성일 -->
			<fmt:formatDate var="rdate" value="${b.regdate}" pattern="yyyy-MM-dd" />
			<c:if test="${today==rdate}">
				<fmt:formatDate value="${b.regdate}" pattern="HH:mm:ss" />
			</c:if>
			<c:if test="${today!=rdate}">
				<fmt:formatDate value="${b.regdate}" pattern="yyyy-MM-dd HH:mm:ss" />
			</c:if>
		</td>
		<td>${b.readcnt}</td> <!-- 조회수 -->
	</tr>
</c:forEach>
  <tfoot>
	<%-- 페이지 처리하기 --%>
	<tr><td colspan="6">
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
	</c:if>
	<tr><td colspan="6" style="text-align:right">
			<c:if test="${boardtype!=90}">
				<a href="writeForm.do?id=${sessionScope.login}&type=${boardtype}" class="button">글쓰기</a>
			</c:if>
			<c:if test="${boardtype==90 && sessionScope.login == 'admin'}">
				<a href="noticeForm.do" class="button">공지사항 작성</a>
			</c:if>
		</td>
	</tr>
  <tfoot>
</table>
</div>
</body>
</html>