<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>��� ����</title>
<link rel="stylesheet" type="text/css" href="../css/main.css" />
<script type="text/javascript">
function closer() {
	self.close();
}
</script>
</head>
<body>
<form action="commentEdit.do?commentnum=${c.commentnum}" name="comment_content" method="post" >
<textarea rows="5" name="content">${c.content}</textarea><br>
<input type="submit" class="button" value="����">
<input type="button" value="���" onclick="closer()" >
</form>
</body>
</html>