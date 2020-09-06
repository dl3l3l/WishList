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
<title>�Խñ� �� ��ȸ</title>
<script type="text/javascript">
	function delpost() {
		if(confirm("�Խñ��� �����Ͻðڽ��ϱ�?")==true) {
			location.href="delete.do?postnum=${b.postnum}"
		} else {
			return;
		}
	}
	
	function delcom(num) {
		if(confirm("����� �����Ͻðڽ��ϱ�?")==true) {
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
<caption style="background-color:white">${bt} �Խ���</caption>
<tr style="background-color:white;"> <!-- ��ư ǥ�� -->
	<td colspan="2" style="text-align:left;">
	<c:if test="${b.id==sessionScope.login}">
	<a href="updateForm.do?postnum=${b.postnum}" class="button">����</a>
	</c:if>
	<c:if test="${b.id == sessionScope.login || sessionScope.login=='admin'}">
	<input type="button" onclick="delpost()" value="����">
	</c:if>
	</td>
	<td style="text-align:right;">
	<a href="boardList.do?type=${btype}" class="button">���</a>
	</td>
</tr>
<tr> <!-- ���� -->
	<td colspan="3"><h4>${b.subject}</h4></td>
</tr>
<tr> <!-- �ۼ��� -->
	<td colspan="1" style="text-align:left;">
	<b>${b.id}</b>
	<img src="${path}/member/picture/sm_${writer.picture}" width="30" height="30" style="border-radius:70px;">
	</td>
	<!-- �ۼ��� -->
	<td colspan="2" style="text-align:right;"><fmt:formatDate value="${b.regdate}" pattern="yyyy.MM.dd HH:mm" /></td>
</tr>
<tr> <!-- ī�װ�, ��ȸ�� -->
	<td style="text-align:left;"><c:if test="${dt!=''}">ī�װ� : ${dt}</c:if></td>
	<td colspan="2" style="text-align:right;">��ȸ�� : ${b.readcnt}</td>
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
<!-- ���â -->
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
		<input style="width:88%;" type="text" name="content" placeholder="����� �Է��ϼ���.">
			<input style="width:10%;" type="submit" value="���">
		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>