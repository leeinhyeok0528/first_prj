<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
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
}

.content-box {
	background-color: white;
	width: 1500px;
	padding: 20px;
	box-sizing: border-box;
}

/* Adjusted sub-title spacing */
#sub-title {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 15px; /* Add spacing below */
}
</style>


<!-- 상단 고정 헤더 -->
<div class="header">
	<span><a href="http://192.168.10.225/first_prj/prj/admin_main.jsp">스마트스토어 센터</a></span> <span>로그인 상태</span>
</div>

<!-- 좌측 고정 사이드바 -->
<div class="sidebar">
	<h3>${adminData.name}명</h3>
	<div class="accordion" id="accordionSidebar">
		<!-- 상품 관리 -->
		<div class="accordion-item">
			<h2 class="accordion-header" id="headingOne">
				<button class="accordion-button collapsed" type="button"
					data-bs-toggle="collapse" data-bs-target="#collapseOne"
					aria-expanded="false" aria-controls="collapseOne">상품 관리</button>
			</h2>
			<div id="collapseOne" class="accordion-collapse collapse"
				aria-labelledby="headingOne" data-bs-parent="#accordionSidebar">
				<div class="accordion-body">
					<a href="productList.jsp">상품 리스트</a> <a
						href="productRegistration.jsp">상품 등록</a>
				</div>
			</div>
		</div>

		<!-- 판매 관리 -->
		<div class="accordion-item">
			<h2 class="accordion-header" id="headingTwo">
				<button class="accordion-button collapsed" type="button"
					data-bs-toggle="collapse" data-bs-target="#collapseTwo"
					aria-expanded="false" aria-controls="collapseTwo">판매 관리</button>
			</h2>
			<div id="collapseTwo" class="accordion-collapse collapse"
				aria-labelledby="headingTwo" data-bs-parent="#accordionSidebar">
				<div class="accordion-body">
					<a href="salesList.jsp">판매 리스트</a>
				</div>
			</div>
		</div>

		<!-- 문의 관리 -->
		<div class="accordion-item">
			<h2 class="accordion-header" id="headingFour">
				<button class="accordion-button collapsed" type="button"
					data-bs-toggle="collapse" data-bs-target="#collapseFour"
					aria-expanded="false" aria-controls="collapseFour">문의 관리</button>
			</h2>
			<div id="collapseFour" class="accordion-collapse collapse"
				aria-labelledby="headingFour" data-bs-parent="#accordionSidebar">
				<div class="accordion-body">
					<a href="http://192.168.10.225/first_prj/prj/inquiry/admin_inquiry.jsp">문의 리스트</a>
				</div>
			</div>
		</div>

		<!-- 리뷰 관리 -->
		<div class="accordion-item">
			<h2 class="accordion-header" id="headingFive">
				<button class="accordion-button collapsed" type="button"
					data-bs-toggle="collapse" data-bs-target="#collapseFive"
					aria-expanded="false" aria-controls="collapseFive">리뷰 관리</button>
			</h2>
			<div id="collapseFive" class="accordion-collapse collapse"
				aria-labelledby="headingFive" data-bs-parent="#accordionSidebar">
				<div class="accordion-body">
					<a href="http://192.168.10.225/first_prj/prj/review/admin_review.jsp">리뷰 리스트</a>
				</div>
			</div>
		</div>

		<!-- 공지사항 관리 -->
		<div class="accordion-item">
			<h2 class="accordion-header" id="headingSix">
				<button class="accordion-button collapsed" type="button"
					data-bs-toggle="collapse" data-bs-target="#collapseSix"
					aria-expanded="false" aria-controls="collapseSix">공지사항 관리
				</button>
			</h2>
			<div id="collapseSix" class="accordion-collapse collapse"
				aria-labelledby="headingSix" data-bs-parent="#accordionSidebar">
				<div class="accordion-body">
					<a href="http://192.168.10.225/first_prj/prj/notice/notice_list.jsp">공지사항 리스트</a> 
					<a href="http://192.168.10.225/first_prj/prj/notice/notice_add.jsp">공지사항 등록</a>
				</div>
			</div>
		</div>

		<!-- 회원 관리 -->
		<div class="accordion-item">
			<h2 class="accordion-header" id="headingSeven">
				<button class="accordion-button collapsed" type="button"
					data-bs-toggle="collapse" data-bs-target="#collapseSeven"
					aria-expanded="false" aria-controls="collapseSeven">회원 관리</button>
			</h2>
			<div id="collapseSeven" class="accordion-collapse collapse"
				aria-labelledby="headingSeven" data-bs-parent="#accordionSidebar">
				<div class="accordion-body">
					<a href="http://192.168.10.225/first_prj/prj/member/memberList.jsp">회원 목록</a>
				</div>
			</div>
		</div>

	</div>
</div>