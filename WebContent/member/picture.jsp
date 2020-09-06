<%@page import="java.awt.Graphics2D"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%-- project/WebContent/member/picture.jsp --%>
<script type="text/javascript">
	img = opener.document.getElementById("pic");
	img.src="picture/${fname}"; // opener 창에 이미지가 보여짐.
	opener.document.memberInfo.picture.value="${fname}"; // hidden태그. 파라미터값 설정
	self.close();
</script>
