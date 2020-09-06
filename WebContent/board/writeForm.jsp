<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- project/WebContent/board/writeForm.jsp --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>�Խ��� �۾���</title>
<c:set var="path" value="${pageContext.request.contextPath}" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script type="text/javascript">
function selectType(boardtype, dtype){
	$('#type').empty();
	$('#type').append("<option>ī�װ� ����</option>");
	if(boardtype=='10') { // �ı� �Խ���
		$('#type').append("<option value='11'>����</option>");
		$('#type').append("<option value='12'>����</option>");
		$('#type').append("<option value='13'>��ǰ/�ǰ�</option>");
		$('#type').append("<option value='14'>����ǰ</option>");
		$('#type').append("<option value='15'>�м�</option>");
		$('#type').append("<option value='16'>��Ƽ</option>");
		$('#type').append("<option value='17'>����</option>");
		$('#type').append("<option value='18'>��Ÿ</option>");
		document.getElementById("type").style.display="";
	} else if(boardtype=='20'){
		$('#type').append("<option value='21'>����</option>");
		$('#type').append("<option value='22'>�ؿ�</option>");
		document.getElementById("type").style.display="";
	} else if(boardtype=='30'){
		$('#type').append("<option value='30' selected>����</option>");
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
	<caption><c:if test="${type==10}">�ı� </c:if>
			<c:if test="${type==20}">Ư�� ���� </c:if>
			<c:if test="${type==30}">���� </c:if>
			�Խñ� ����</caption>
	<tr><th>ī�װ�</th><td style="text-align:left">
		<c:choose>
		<c:when test="${type==0}">
			<select name="btype" id="btype" onChange="selectType(this.value)" required>
				<option>�Խ��� ����</option>
				<option value="10">�ı�</option>
				<option value="20">Ư�� ����</option>
				<option value="30">����</option>
			</select>
			<select name="type" id="type" style="display:none;" required></select>
		</c:when>
		<c:otherwise>
			<select name="type" id="type" required>
			<c:if test="${fn:startsWith(type,1)}"> <%--�ı� �Խ����� ��� --%>
				<option>����</option>
				<option value='11' <c:if test="${type==11}">selected</c:if>>����</option>
				<option value='12' <c:if test="${type==12}">selected</c:if>>����</option>
				<option value='13' <c:if test="${type==13}">selected</c:if>>��ǰ/�ǰ�</option>
				<option value='14' <c:if test="${type==14}">selected</c:if>>����ǰ</option>
				<option value='15' <c:if test="${type==15}">selected</c:if>>�м�</option>
				<option value='16' <c:if test="${type==16}">selected</c:if>>��Ƽ</option>
				<option value='17' <c:if test="${type==17}">selected</c:if>>����</option>
				<option value='18' <c:if test="${type==18}">selected</c:if>>��Ÿ</option>
			</c:if>
			<c:if test="${fn:startsWith(type,2)}"> <%--Ư������ �Խ����� ��� --%>
				<option>����</option>
				<option value='21' <c:if test="${type==21}">selected</c:if>>����</option>
				<option value='22' <c:if test="${type==22}">selected</c:if>>�ؿ�</option>
			</c:if>
			<c:if test="${type==30}"> <%--���� �Խ����� ��� --%>
				<option value='30' selected>����</option>
			</c:if>
  	 	  	</select>
		</c:otherwise>
		</c:choose>
		</td>
  	</tr>
	<tr><th>����</th><td><input type="text" name="subject" required></td></tr>
	<tr><th>����</th><td><textarea rows="15" name="content" id="editor" required>
	<c:if test="${!empty item.itemnum}">
	��ǰ�� : ${item.itemname}
	���� : ${item.price}
	����Ʈ �ּ� : ${item.url}
	</c:if>
	</textarea>
	</td></tr>
	<tr><th>÷������</th><td><input type="file" name="file"></td></tr>
<tfoot>
	<tr><td colspan="2"><input type="submit" class="button" value="�Ϸ�">
						<input type="button" onclick="history.go(-1)" value="���"></td></tr>
</tfoot>
	</table>	  
</form>
</div>
</body>
</html>