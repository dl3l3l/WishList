<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%--project/WebContent/member/joinForm.jsp --%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<meta charset="EUC-KR">
<title>회원가입</title>
<style type="text/css">
	td { text-align:left; }
	table { margin:5px; width:30%; 
			background-color: #D3D3D3; }
</style>
<script type="text/javascript">
$(function(){
    $('#pass').keyup(function(){
      $('#chkpass').html('');
    });
    $('#pass2').keyup(function(){
        if($('#pass').val() != $('#pass2').val()){
          $('#chkpass').html('비밀번호 일치하지 않음');
          $('#chkpass').attr('color', '#F08080');
        } else{
          $('#chkpass').html('비밀번호 일치함');
          $('#chkpass').attr('color', '#199894b3');
        }
    });
});

</script>
</head>
<body>
<div align="center">
	<form action="join.do" name="memberInfo" method="post" onsubmit="return inchk(this)">
		<input type="hidden" name="picture" value="">
		<table>
		<caption>회원가입</caption>
			<tr>
				<td style="text-align:center">
					<img src="" width="140" height="140" style="border-radius:70px;" id="pic" ><br>
					<font size="2.5"><a href="javascript:win_upload()">사진등록</a></font></td>
			</tr>
			<tr>
				<td><label for="id">아이디</label>
					<a onclick="win_open_small('member','idCheckForm.do')" class="button2">중복 확인</a>
					<input type="text" name="id" id="id" placeholder="ID" value="${chkid}" required></td>
			</tr>
			<tr>
				<td><label for="pass">비밀번호</label>
					<input type="password" id="pass" name="pass" placeholder="PASSWORD" required></td>
			</tr>
			<tr>
				<td><label for="pass">비밀번호 확인</label>
					<input type="password" id="pass2" name="pass2" placeholder="PASSWORD CHECK" required>
					<font id="chkpass" size="2"></font>
				</td>
			</tr>
			<tr>
				<td><label for="pass">이름</label>
					<input type="text" name="name" placeholder="NAME" required></td>
			</tr>
			<tr>
				<td><label for="pass">이메일</label>
					<input type="email" name="email" placeholder="EMAIL" required></td>
			</tr>
			<tr>
				<td style="text-align: center;">
				<input type="submit" class="button" value="회원가입"></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>