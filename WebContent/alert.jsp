<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%--project/WebContent/alert.jsp --%>
<script>
	if ("${closer}") {
		self.close();
	}
	if("${msg}") {
		alert("${msg}");
	}
	if("${url}"){
		location.href = "${url}";
	}
	if("${reload}"){
		opener.document.location.reload();
	}
	
</script>