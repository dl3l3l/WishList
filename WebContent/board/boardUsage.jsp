<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>게시판 이용 현황</title>
</head>
<body>
<!-- 그래프 -->
	<div class="w3 w3-container w3-padding-16" align="center">
		<h4>게시판 이용 현황</h4>
		<div class="w3-half">
			<div class="w3-container w3-padding-16">
				<div id="piecontainer" style="width: 80%;">
					<canvas id="canvas1" style="width: 100%;"></canvas>
				</div>
			</div>
		</div>
		<div class="w3-half">
			<div class="w3-container w3-padding-16">
				<div id="barcontainer" style="width: 80%;">
					<canvas id="canvas2" style="width: 100%;"></canvas>
				</div>
			</div>
		</div>
		<div class="w3-quarter">&nbsp;</div>
		 <div class="w3-half">
			<div class="w3-container w3-padding-16">
				<div id="barcontainer" style="width: 80%;">
					<canvas id="canvas3" style="width: 100%;"></canvas>
				</div>
			</div>
		</div>
	<div class="w3-quarter"></div>
	</div>
<hr>
<!-- 멤버별 게시글 조회 -->
<div class="w3 w3-container w3-padding-16" align="center">
	<div id="boardcontainer2" style="width: 80%;">
		<h4>작성자별 게시글 조회</h4>
	<form action="myBoard.do?id=this.value">
		<label for="id">ID : </label>
		<select name="id" id="id">
			<c:forEach var="w" items="${wlist}">
				<option value="${w}">${w}</option>
			</c:forEach>
		</select>
		<input type="submit" value="조회">
	</form>
	</div>
</div>
<script>
	var randomColorFactor = function() {
		return Math.round(Math.random()*255);
	}
	var randomColor = function(opa) {
		return "rgba(" + randomColorFactor() + ","
					   + randomColorFactor() + ","
					   + randomColorFactor() + ","
					   + (opa || '.3') + ")";
	}
	
	$(function(){
		piegraph();
		bargraph();
		piegraph2();
	})
					
	function piegraph() {
		$.ajax("${path}/ajax/graph.do",{
			success : function(data) {
				pieGraphPrint(data);
			},
			error : function(e){
				alert("서버 오류:" + e.status);
			}
		})
	}
	
	function bargraph() {
		$.ajax("${path}/ajax/graph2.do",{
			success : function(data) {
				barGraphPrint(data);
			},
			error : function(e){
				alert("서버 오류:" + e.status);
			}
		})
	}
	
	function piegraph2() {
		$.ajax("${path}/ajax/graph3.do",{
			success : function(data) {
				pieGraphPrint2(data);
			},
			error : function(e){
				alert("서버 오류:" + e.status);
			}
		})
	}
	
	// 회원별 게시판 등록수
	function pieGraphPrint(data){
		console.log(data)
		var rows = JSON.parse(data);
		var ids = []
		var datas = []
		var colors = []
		$.each(rows,function(index,item){
			ids[index] = item.id;
			datas[index] = item.cnt;
			colors[index] = randomColor(0.7);
		})
		var config = {
			type : 'pie',
			data : {
				datasets : [{
					data : datas,
					backgroundColor : colors
				}],
				labels : ids
			},
			options : {
				responsive : true,
				legend : { position : 'bottom' },
				title : {
					display : true,
					text : '작성자별 게시글 등록 건수',
					position : 'top',
				}
			}
		}
		var ctx = document.getElementById("canvas1").getContext("2d");
		new Chart(ctx,config);
	}
	
	// 타입별 게시판 등록수
	function pieGraphPrint2(data){
		console.log(data)
		var rows = JSON.parse(data);
		var types = []
		var datas = []
		var colors = []
		$.each(rows,function(index,item){
			types[index] = item.type;
			datas[index] = item.cnt;
			colors[index] = randomColor(0.7);
		})
		var config = {
			type : 'pie',
			data : {
				datasets : [{
					data : datas,
					backgroundColor : colors
				}],
				labels : ['후기','특가정보','자유','공지사항'],
			},
			options : {
				responsive : true,
				legend : { position : 'bottom' },
				title : {
					display : true,
					text : '게시판별 게시글 등록 건수',
					position : 'top'
				}
			}
		}
		var ctx = document.getElementById("canvas3").getContext("2d");
		new Chart(ctx,config);
	}
	
	
	// 날짜별 게시판 등록수
	function barGraphPrint(data){
		console.log(data)
		var rows = JSON.parse(data);
		var dates = [] // labels
		var datas = []
		var colors = []
		
		$.each(rows,function(index,item){
			dates[index] = item.date;
			datas[index] = item.cnt;
			colors[index] = randomColor(0.7);
		})
		
		var chartData = {
			labels : dates,
			datasets : [
				{	
					type : 'line',
					borderWidth : 2,
					borderColor : colors,
					label : '건수',					
					fill : false,
					data : datas
				},
				{	
					type : 'bar',
					backgroundColor : colors,
					label : '건수',	
					data : datas,
					borderColor : colors
				}
			]
		}
		var config = {
			type : 'bar',
			data : chartData,
			options : {
				responsive : true,
				legend : { display : false },
				title : {
					display : true,
					text : '최근 날짜별 게시글 등록 건수',
					position : 'top'
				},
				scales : {
					xAxes : [{
						display : true,
						stacked : true
					}],
					yAxes : [{
						display : true,
						stacked : true
					}]
				}	
			}
		}
		var ctx = document.getElementById("canvas2").getContext("2d");
		new Chart(ctx,config);
	}
</script>
</body>
</html>