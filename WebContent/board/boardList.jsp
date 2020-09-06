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
<title>${bt} �Խ��� ��ȸ</title>
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
	<tr><td colspan="5"><h3>${bt} �Խ���</h3></td></tr>
		<!-- �˻� ���  -->
	<tr><td colspan="5">
		<form action="boardList.do?type=${boardtype}" method="post" name="searchf">
			<input type="hidden" name="pageNum" value="1">
			<select name="column">
				<option>����</option>
				<option value="subject">����</option>
				<option value="content">����</option>
				<option value="subject,content">����+����</option>
				<option value="id">�ۼ���</option>
			</select>
		<script>document.searchf.column.value = "${param.column}";</script>
			<input type="text" name="find" value="${param.find}" style="width:50%;">
			<input type="submit" value="�˻�">
		</form></td></tr>
		<!-- ī�װ��� ��ȸ -->
	<tr><td colspan="2" style="text-align:left;">
  		<c:if test="${boardtype!=0 && boardtype<30}"> <!-- ��ü�Խ���, �������װԽ���, ���� �Խ����� ��Ÿ���� ����  -->
  			<label>ī�װ�:</label>
  			<select name="type" id="type" onChange="typeList(this.value)">
  				<option>����</option>
   			<c:if test="${fn:startsWith(boardtype,1)}">
   			 	<option value="11" <c:if test="${boardtype==11}">selected</c:if>>����</option>
   				<option value="12" <c:if test="${boardtype==12}">selected</c:if>>����</option>
   			 	<option value="13" <c:if test="${boardtype==13}">selected</c:if>>��ǰ/�ǰ�</option>
   			 	<option value="14" <c:if test="${boardtype==14}">selected</c:if>>����ǰ</option>
   			 	<option value="15" <c:if test="${boardtype==15}">selected</c:if>>�м�</option>
   			 	<option value="16" <c:if test="${boardtype==16}">selected</c:if>>��Ƽ</option>
   			 	<option value="17" <c:if test="${boardtype==17}">selected</c:if>>����</option>
   			 	<option value="18" <c:if test="${boardtype==18}">selected</c:if>>��Ÿ</option>
   			</c:if>
   			<c:if test="${fn:startsWith(boardtype,2)}">
   			 	<option value="21" <c:if test="${boardtype==21}">selected</c:if>>����</option>
   			 	<option value="22" <c:if test="${boardtype==22}">selected</c:if>>�ؿ�</option>
   			</c:if>
  		</select></c:if></td>
		<!-- �Խñ� ���� -->
		<td colspan="4" style="text-align:right;">�� ���� : ${boardcount}</td></tr>
	<tr><th width="6%">��ȣ</th>
		<th width="10%">&nbsp;</th>
		<th width="42%">����</th>
		<th width="15%">�ۼ���</th>
		<th width="20%">�ۼ���</th>
		<th width="7%">��ȸ��</th></tr>
	<c:if test="${boardcount==0}">
	<tr><td colspan="5"> ��ϵ� �Խñ��� �����ϴ�. </td></tr>
	</c:if>
 </thead>
<%-- �Խñ� ��ȸ�ϱ� --%>
	<c:if test="${boardcount>0}">
<c:forEach var="b" items="${list}">
	<tr <c:if test="${b.type==90}">bgcolor=#ff8a8a</c:if>>
		<td>${boardnum}</td><c:set var="boardnum" value="${boardnum-1}" />
		<%--ī�װ� ����Ʈ�� ��Ÿ���� --%>
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
		<font size="2">[<c:if test="${bt=='��ü'}"><%=btype%></c:if><%=typename %>]</font></td>
		<td style="text-align:left"> <!-- ���� -->
		<a href="postInfo.do?postnum=${b.postnum}" style="text-decoration:none;">
		&nbsp; ${b.subject}</a>
		<!-- ���� ���� ��Ÿ���� -->
		<c:if test="${!empty b.file}"><img src="../icon/clip.png"></c:if>
		<!-- ��� �� ��Ÿ���� -->
		<c:if test="${rcount!=0}"><font color="red">[${rcount}]</font></c:if>
		</td>
		<td>${b.id}</td> <!-- �ۼ��� -->
		<td><!-- �ۼ��� -->
			<fmt:formatDate var="rdate" value="${b.regdate}" pattern="yyyy-MM-dd" />
			<c:if test="${today==rdate}">
				<fmt:formatDate value="${b.regdate}" pattern="HH:mm:ss" />
			</c:if>
			<c:if test="${today!=rdate}">
				<fmt:formatDate value="${b.regdate}" pattern="yyyy-MM-dd HH:mm:ss" />
			</c:if>
		</td>
		<td>${b.readcnt}</td> <!-- ��ȸ�� -->
	</tr>
</c:forEach>
  <tfoot>
	<%-- ������ ó���ϱ� --%>
	<tr><td colspan="6">
		<c:if test="${pageNum<=1}"><span class="page">����</span></c:if>
		<c:if test="${pageNum>1}"><a class="page" href="javascript:listdo(${pageNum-1})">����</a></c:if>
		<c:forEach var="a" begin="${startpage}" end="${endpage}">
			<c:if test="${a==pageNum}"><span class="now_page">${a}</span></c:if>
			<c:if test="${a!=pageNum}"><a class="page" href="javascript:listdo(${a})">${a}</a></c:if>
		</c:forEach>
		<c:if test="${pageNum>=maxpage}"><span class="page">����</span></c:if>
		<c:if test="${pageNum<maxpage}"><a class="page" href="javascript:listdo(${pageNum+1})">����</a></c:if>
		</td>
	</tr>
	</c:if>
	<tr><td colspan="6" style="text-align:right">
			<c:if test="${boardtype!=90}">
				<a href="writeForm.do?id=${sessionScope.login}&type=${boardtype}" class="button">�۾���</a>
			</c:if>
			<c:if test="${boardtype==90 && sessionScope.login == 'admin'}">
				<a href="noticeForm.do" class="button">�������� �ۼ�</a>
			</c:if>
		</td>
	</tr>
  <tfoot>
</table>
</div>
</body>
</html>