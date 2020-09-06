<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- project/WebContent/board/writeForm.jsp --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>게시판 글쓰기</title>
<c:set var="path" value="${pageContext.request.contextPath}" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script type="text/javascript">
function selectType(boardtype, dtype){
	$('#type').empty();
	$('#type').append("<option>카테고리 선택</option>");
	if(boardtype=='10') { // 후기 게시판
		$('#type').append("<option value='11'>가전</option>");
		$('#type').append("<option value='12'>가구</option>");
		$('#type').append("<option value='13'>식품/건강</option>");
		$('#type').append("<option value='14'>생필품</option>");
		$('#type').append("<option value='15'>패션</option>");
		$('#type').append("<option value='16'>뷰티</option>");
		$('#type').append("<option value='17'>육아</option>");
		$('#type').append("<option value='18'>기타</option>");
		document.getElementById("type").style.display="";
	} else if(boardtype=='20'){
		$('#type').append("<option value='21'>국내</option>");
		$('#type').append("<option value='22'>해외</option>");
		document.getElementById("type").style.display="";
	} else if(boardtype=='30'){
		$('#type').append("<option value='30' selected>자유</option>");
		document.getElementById("type").style.display="none";
	}
	
	if ($.trim(dtype) != "") {
        $('#btype').val(boardtype);
        $('#type').val(type);
    }
}
</script>
</head>
<body>
<div align="center">
<form action="write.do?id=${sessionScope.login}" onsubmit="selectchk()" method="post" enctype="multipart/form-data" name="post">
<table class="board_table">
	<caption><c:if test="${type==10}">후기 </c:if>
			<c:if test="${type==20}">특가 정보 </c:if>
			<c:if test="${type==30}">자유 </c:if>
			게시글 쓰기</caption>
	<tr><th>카테고리</th><td style="text-align:left">
		<c:choose>
		<c:when test="${type==0}">
			<select name="btype" id="btype" onChange="selectType(this.value)" required>
				<option>게시판 선택</option>
				<option value="10">후기</option>
				<option value="20">특가 정보</option>
				<option value="30">자유</option>
			</select>
			<select name="type" id="type" style="display:none;" required></select>
		</c:when>
		<c:otherwise>
			<select name="type" id="type" required>
			<c:if test="${fn:startsWith(type,1)}"> <%--후기 게시판인 경우 --%>
				<option>선택</option>
				<option value='11' <c:if test="${type==11}">selected</c:if>>가전</option>
				<option value='12' <c:if test="${type==12}">selected</c:if>>가구</option>
				<option value='13' <c:if test="${type==13}">selected</c:if>>식품/건강</option>
				<option value='14' <c:if test="${type==14}">selected</c:if>>생필품</option>
				<option value='15' <c:if test="${type==15}">selected</c:if>>패션</option>
				<option value='16' <c:if test="${type==16}">selected</c:if>>뷰티</option>
				<option value='17' <c:if test="${type==17}">selected</c:if>>육아</option>
				<option value='18' <c:if test="${type==18}">selected</c:if>>기타</option>
			</c:if>
			<c:if test="${fn:startsWith(type,2)}"> <%--특가정보 게시판인 경우 --%>
				<option>선택</option>
				<option value='21' <c:if test="${type==21}">selected</c:if>>국내</option>
				<option value='22' <c:if test="${type==22}">selected</c:if>>해외</option>
			</c:if>
			<c:if test="${type==30}"> <%--자유 게시판인 경우 --%>
				<option value='30' selected>자유</option>
			</c:if>
  	 	  	</select>
		</c:otherwise>
		</c:choose>
		</td>
  	</tr>
	<tr><th>제목</th><td><input type="text" name="subject" required></td></tr>
	<tr><th>내용</th><td><textarea rows="15" name="content" id="editor" required>
	<c:if test="${!empty item.itemnum}">
	물품명 : ${item.itemname}
	가격 : ${item.price}
	사이트 주소 : ${item.url}
	</c:if>
	</textarea>
	</td></tr>
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