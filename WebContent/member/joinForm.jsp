<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%--project/WebContent/member/joinForm.jsp --%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<meta charset="EUC-KR">
<title>ȸ������</title>
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
          $('#chkpass').html('��й�ȣ ��ġ���� ����');
          $('#chkpass').attr('color', '#F08080');
        } else{
          $('#chkpass').html('��й�ȣ ��ġ��');
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
		<caption>ȸ������</caption>
			<tr>
				<td style="text-align:center">
					<img src="" width="140" height="140" style="border-radius:70px;" id="pic" ><br>
					<font size="2.5"><a href="javascript:win_upload()">�������</a></font></td>
			</tr>
			<tr>
				<td><label for="id">���̵�</label>
					<a onclick="win_open_small('member','idCheckForm.do')" class="button2">�ߺ� Ȯ��</a>
					<input type="text" name="id" id="id" placeholder="ID" value="${chkid}" required></td>
			</tr>
			<tr>
				<td><label for="pass">��й�ȣ</label>
					<input type="password" id="pass" name="pass" placeholder="PASSWORD" required></td>
			</tr>
			<tr>
				<td><label for="pass">��й�ȣ Ȯ��</label>
					<input type="password" id="pass2" name="pass2" placeholder="PASSWORD CHECK" required>
					<font id="chkpass" size="2"></font>
				</td>
			</tr>
			<tr>
				<td><label for="pass">�̸�</label>
					<input type="text" name="name" placeholder="NAME" required></td>
			</tr>
			<tr>
				<td><label for="pass">�̸���</label>
					<input type="email" name="email" placeholder="EMAIL" required></td>
			</tr>
			<tr>
				<td style="text-align: center;">
				<input type="submit" class="button" value="ȸ������"></td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>