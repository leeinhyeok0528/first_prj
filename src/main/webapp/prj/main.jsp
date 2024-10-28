
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	info="관리자 페이지 메인 대시보드 화면"
	%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>관리 페이지</title>
<!-- chart.js CDN  -->

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<!-- Bootstrap CDN -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- jQuery CDN -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<style>
body, html {
	margin: 0;
	padding: 0;
	height: 100%;
	font-family: Arial, sans-serif;
	background-color: #f4f4f4;
}

/* 상단 고정 헤더 */
.header {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	background-color: #2D3539;
	color: white;
	padding: 15px;
	display: flex;
	justify-content: space-between;
	z-index: 1000;
}

/* 좌측 고정 사이드바 */
.sidebar {
	position: fixed;
	top: 0;
	left: 0;
	width: 250px;
	height: 100%;
	background-color: #414B5A;
	padding-top: 70px;
	z-index: 999;
	color: white;
	overflow-y: auto;
}

.sidebar h3 {
	padding: 15px;
	text-align: center;
	background-color: #414B5A;
}

/* 아코디언 및 사이드바 링크 */
.sidebar a, .accordion-button {
	color: white;
	padding: 10px 20px;
	text-decoration: none;
	display: block;
	background-color: #414B5A;
	border: 1px solid #354b5e;
	border-radius: 0;
}

.sidebar a:hover, .accordion-button:hover {
	background-color: #354b5e;
}

/* 아코디언 확장/클릭 시 배경색 유지 */
.accordion-button:not(.collapsed), .accordion-body {
	background-color: #414B5A !important;
	color: white;
}

.accordion-body a {
	background-color: #414B5A;
	color: white;
}

.accordion-body a:hover {
	background-color: #354b5e;
	color: white;
}

.accordion-body a:focus {
	color: white;
	outline: none;
}

.accordion-body a:active {
	color: white;
}

/* 메인 콘텐츠 */
.main-content {
	margin-left: 250px;
	padding: 85px 20px;
	background-color: #e9ecef;
	min-height: 100vh;
	display: flex;
	gap: 20px;
	flex-wrap: wrap;
	height: 45%;
}

.contentTop {
	background-color: white;
	width: calc(50% - 10px);
	padding: 20px;
}

.contentBottom {
	background-color: white;
	width: calc(50% - 10px);
	padding: 20px;
	height: 55%
}

.innerBox {
	height: 150px;
	width: 50%;
	float: left;
	text-align: right;
	border-top: 0px solid #EEF0F4;
	border-right: 1px solid #EEF0F4;
	border-bottom: 1px solid #EEF0F4;
	border-left: 1px solid #EEF0F4;
}
</style>
<script>
	// 페이지가 로드되면 실행될 작업
	$(function() {
		dayChart(); // 차트를 생성하는 함수를 호출
		monthlyChart();
	})//ready

	function dayChart() {
		// #myChart라는 id를 가진 <canvas> 요소를 선택하고, 2D 그래픽 컨텍스트를 가져옴
		let ctx = document.querySelector('#dayChart').getContext('2d');

		// Chart 객체 생성: 이 객체가 실제로 차트를 그리며, 다양한 차트 유형과 데이터를 지원함
		new Chart(ctx, {
			type : 'bar', // 차트의 타입을 설정 ('bar': 막대 차트, 'line': 선 차트, 'pie': 파이 차트 등)

			// 차트에 사용할 데이터 설정
			data : {
				labels : [ 'day1', 'day2', 'day3', 'day4', 'day5', 'day6',
						'day7' ], // X축에 표시될 항목 이름(카테고리)
				datasets : [ {
					label : '일일 매출', // 데이터셋에 대한 설명 (차트의 범례에 표시됨)
					data : [ 20, 39, 53, 15, 7, 40, 30 ], // 각 카테고리에 해당하는 매출 데이터 (Y축 값)
					borderWidth : 1
				// 막대의 테두리 두께를 설정
				}, ]
			},

			// 차트의 옵션을 설정하는 부분 (여기서는 축, 스타일 등을 설정)
			options : {
				scales : {
					// Y축의 설정을 정의
					y : {
						beginAtZero : true
					// Y축 값이 0에서 시작하도록 설정 (false일 경우 최소값에서 시작)
					}
				},
				hover : {
					mode : 'index' // hover 시 동작 방식을 설정
				}
			}
		});
	}//dayChart

	function monthlyChart() {
		// #myChart라는 id를 가진 <canvas> 요소를 선택하고, 2D 그래픽 컨텍스트를 가져옴
		let mChart = document.querySelector('#monthlyChart').getContext('2d');

		// Chart 객체 생성: 이 객체가 실제로 차트를 그리며, 다양한 차트 유형과 데이터를 지원함
		new Chart(mChart, {
			type : 'bar', // 차트의 타입을 설정 ('bar': 막대 차트, 'line': 선 차트, 'pie': 파이 차트 등)

			// 차트에 사용할 데이터 설정
			data : {
				labels : [ 'Month1', 'Month2', 'Month3', 'Month4', 'Month5',
						'Month6' ], // X축에 표시될 항목 이름(카테고리)
				datasets : [ {
					label : '월별 매출', // 데이터셋에 대한 설명 (차트의 범례에 표시됨)
					data : [ 10, 19, 13, 15, 12, 13 ], // 각 카테고리에 해당하는 매출 데이터 (Y축 값)
					borderWidth : 1
				// 막대의 테두리 두께를 설정
				}, ]
			},

			// 차트의 옵션을 설정하는 부분 (여기서는 축, 스타일 등을 설정)
			options : {
				scales : {
					// Y축의 설정을 정의
					y : {
						beginAtZero : true
					// Y축 값이 0에서 시작하도록 설정 (false일 경우 최소값에서 시작)
					}
				},
				hover : {
					mode : 'index' // hover 시 동작 방식을 설정
				}
			}
		});
	}//monthlyChart
</script>


</head>

<body>

	<!-- 상단 고정 헤더 -->
	<div class="header">
		<span>스마트스토어 센터</span> <span>로그인 상태</span>
	</div>

	<!-- 좌측 고정 사이드바 -->
	<div class="sidebar" id="sidebar">
		<c:import url="http://localhost/first_prj/prj/sidebar.jsp"></c:import>
	</div>

	<!-- 메인 콘텐츠 영역 -->
	<div class="main-content">
		<!-- 주문/배송영역 -->
		<div class="contentTop">
			<h4 style="border-bottom: 1px solid #EEF0F4">주문/배송</h4>
			<div class="innerBox">
				<img src="http://192.168.10.225/first_prj/prj/images/img1.png"
					style="float: left;">
				<p>신규주문 N건</p>


			</div>
			<div class="innerBox">
				<img src="http://192.168.10.225/first_prj/prj/images/img2.png"
					style="float: left;">
				<p>배송준비 N건</p>
				<p>배송중 N건</p>
				<p>배송완료 N건</p>
			</div>
		</div>

		<!-- 문의/정산영역 -->
		<div class="contentTop">
			<h4 style="border-bottom: 1px solid #EEF0F4">고객요청/구매확정</h4>
			<div class="innerBox">
				<img src="http://192.168.10.225/first_prj/prj/images/img3.png"
					style="float: left;">
				<p>취소요청 N건</p>
				<p>교환요청 N건</p>

			</div>
			<div class="innerBox">
				<img src="http://192.168.10.225/first_prj/prj/images/img4.png"
					style="float: left;">
				<p>구매확정 N건</p>

			</div>
		</div>





		<div class="contentBottom">
			<canvas id="dayChart"></canvas>
			<p style="font-size: 10px; text-align: right">단위:(만원)</p>
		</div>


		<div class="contentBottom">
					<h4 style="border-bottom: 1px solid #EEF0F4">회원현황/신규문의</h4>
			<div class="innerBox">
				<img src="http://192.168.10.225/first_prj/prj/images/img5.png"
					style="float: left;">
				<p>신규주문 N건</p>


			</div>
			<div class="innerBox">
				<img src="http://192.168.10.225/first_prj/prj/images/img6.png"
					style="float: left;">
				<p>배송준비 N건</p>
				<p>배송중 N건</p>
				<p>배송완료 N건</p>
			</div>
		</div>
	</div>

</body>

</html>
