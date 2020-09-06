<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>�Խ��� �̿� ��Ȳ</title>
</head>
<body>
<!-- �׷��� -->
	<div class="w3 w3-container w3-padding-16" align="center">
		<h4>�Խ��� �̿� ��Ȳ</h4>
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
<!-- ����� �Խñ� ��ȸ -->
<div class="w3 w3-container w3-padding-16" align="center">
	<div id="boardcontainer2" style="width: 80%;">
		<h4>�ۼ��ں� �Խñ� ��ȸ</h4>
	<form action="myBoard.do?id=this.value">
		<label for="id">ID : </label>
		<select name="id" id="id">
			<c:forEach var="w" items="${wlist}">
				<option value="${w}">${w}</option>
			</c:forEach>
		</select>
		<input type="submit" value="��ȸ">
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
				alert("���� ����:" + e.status);
			}
		})
	}
	
	function bargraph() {
		$.ajax("${path}/ajax/graph2.do",{
			success : function(data) {
				barGraphPrint(data);
			},
			error : function(e){
				alert("���� ����:" + e.status);
			}
		})
	}
	
	function piegraph2() {
		$.ajax("${path}/ajax/graph3.do",{
			success : function(data) {
				pieGraphPrint2(data);
			},
			error : function(e){
				alert("���� ����:" + e.status);
			}
		})
	}
	
	// ȸ���� �Խ��� ��ϼ�
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
					text : '�ۼ��ں� �Խñ� ��� �Ǽ�',
					position : 'top',
				}
			}
		}
		var ctx = document.getElementById("canvas1").getContext("2d");
		new Chart(ctx,config);
	}
	
	// Ÿ�Ժ� �Խ��� ��ϼ�
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
				labels : ['�ı�','Ư������','����','��������'],
			},
			options : {
				responsive : true,
				legend : { position : 'bottom' },
				title : {
					display : true,
					text : '�Խ��Ǻ� �Խñ� ��� �Ǽ�',
					position : 'top'
				}
			}
		}
		var ctx = document.getElementById("canvas3").getContext("2d");
		new Chart(ctx,config);
	}
	
	
	// ��¥�� �Խ��� ��ϼ�
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
					label : '�Ǽ�',					
					fill : false,
					data : datas
				},
				{	
					type : 'bar',
					backgroundColor : colors,
					label : '�Ǽ�',	
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
					text : '�ֱ� ��¥�� �Խñ� ��� �Ǽ�',
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