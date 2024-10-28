<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">

<head>
   <meta charset="UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <title>문의관리 페이지</title>

<!-- bootstrap CDN 시작 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<!-- jQuery CDN 시작 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<style type="text/css">

</style>

   <style>
/* 기본 설정 */
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
   color: white;
   overflow-y: auto;
}

.sidebar a, .accordion-button {
   color: white;
   padding: 10px 20px;
   text-decoration: none;
   display: block;
   background-color: #414B5A;
}

.sidebar a:hover, .accordion-button:hover {
   background-color: #354b5e;
}

/* 아코디언 확장/클릭 시 배경색 유지 */
.accordion-button:not(.collapsed), .accordion-body {
   background-color: #414B5A !important;
   color: white;
}

/* 메인 콘텐츠 */
.main-content {
   margin-left: 250px;
   padding: 85px 20px;
   background-color: #e9ecef;
   min-height: 100vh;
}

/* 공통 폼 스타일 */
.form {
   background-color: white;
   padding: 20px;
   border-radius: 5px;
   box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
   margin-bottom: 20px;
}

.form-group {
   display: flex;
   align-items: center;
   margin: 20px 0 15px 0;
   border-bottom: 1px solid #EEF0F4;
   
}

.form-group label {
   width: 100px;
   font-weight: bold;
   margin-right: 10px;
}

.form-group input, .form-group select {
   margin-right: 15px;
   width: auto;
}


.date {
   width: 250px;
}

/* 버튼 공통 스타일 */
.btn {
   border-radius: 5px;
   cursor: pointer;
   margin-right: 10px;
}

/* 테이블 스타일 */
.table-hover tbody tr:hover {
   background-color: #f5f5f5;
}


   </style>
   
 <script type="text/javascript">
 $(document).ready(function () {
	    initializeDateFields();
	    setupRadioButtons();
	    setupSearchButton();
	    $("tbody tr").click(function () {
	        showPopUp();
	        
	    });
	    
	});//ready
	


	// 페이지 로드시 날짜 기본 설정
	function initializeDateFields() {
	    const today = new Date();
	    const formattedToday = formatDate(today);
	    $('#startDate').val(formattedToday);
	    $('#endDate').val(formattedToday);
	    $('#btnradio1').prop('checked', true); // 오늘을 기본 선택으로 설정
	}

	// 날짜 포맷팅 함수 (yyyy-mm-dd 형식으로 변환)
	function formatDate(date) {
	    const year = date.getFullYear();
	    const month = String(date.getMonth() + 1).padStart(2, '0');
	    const day = String(date.getDate()).padStart(2, '0');
	    return year + '-' + month + '-' + day;
	}

	// 라디오 버튼 설정 함수
	function setupRadioButtons() {
	    $('input[name="date"]').on('change', function () {
	        const today = new Date();
	        let startDate = new Date(today);
	        switch (this.id) {
	            case 'btnradio1':
	                // 오늘
	                startDate = today;
	                break;
	            case 'btnradio2':
	                // 3일 전
	                startDate.setDate(today.getDate() - 3);
	                break;
	            case 'btnradio3':
	                // 1주일 전
	                startDate.setDate(today.getDate() - 7);
	                break;
	            case 'btnradio4':
	                // 1달 전
	                startDate.setMonth(today.getMonth() - 1);
	                break;
	        }
	        setDateRange(startDate, today);
	    });
	}

	// 날짜 범위 설정 함수
	function setDateRange(startDate, endDate) {
	    $('#startDate').val(formatDate(startDate));
	    $('#endDate').val(formatDate(endDate));
	}

	// 검색 유형,날짜설정 체크 
	function setupSearchButton() {
	    $('#searchBtn').on('click', function () {
	    	let sel = $("#filter").val();
	        const startDate = $('#startDate').val();
	        const endDate = $('#endDate').val();
	        alert('설정된 날짜 범위: ' + startDate + ' ~ ' + endDate+ "설정된 검색유형은"+ sel);
	    });
	}

	//상세보기 팝업창 함수
function showPopUp() {
var left=window.screenX+300;
var top= window.screenY+200;
window.open("qna_popup.jsp","qna_popup","width=700,height=700, left="+left+",top="+top)


}//

    </script>

</head>

<body>

   <!-- 상단 고정 헤더 -->
   <div class="header">
      <span>스마트스토어 센터</span>
      <span>로그인 상태</span>
   </div>

  <!-- 좌측 고정 사이드바 -->
   <div class="sidebar">
   <c:import url="http://localhost/first_prj/prj/sidebar.jsp"></c:import>
   </div>

   <!-- 메인 콘텐츠 영역 -->
   <div class="main-content">
      <!-- 헤더 섹션 -->
      <div class="form">
         <h4>문의 관리</h4>
      </div>

      <!-- 새로운 검색 필터 폼 -->
      <div class="form">
      
         <form>
           
            <div class="form-group" >
               <label for="filter">유형</label>
               <select id="filter" name="filter" class="form-select">
                  <option value="all">전체</option>
                  <option value="item">상품문의</option>
                  <option value="refund">구매취소</option>
               </select>
            </div>

              <div class="form-group"  >
               <label for="date-range" >문의일</label>
              <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
                      <input type="radio" class="btn-check" name="date" id="btnradio1" autocomplete="off">
                      <label class="btn btn-outline-secondary  btn-sm" for="btnradio1"  style="margin-right: 0px">오늘</label>
                    
                      <input type="radio" class="btn-check" name="date" id="btnradio2" autocomplete="off">
                      <label class="btn btn-outline-secondary  btn-sm" for="btnradio2"  style="margin-right: 0px">3일</label>
                    
                      <input type="radio" class="btn-check" name="date" id="btnradio3" autocomplete="off">
                      <label class="btn btn-outline-secondary  btn-sm" for="btnradio3"  style="margin-right: 0px">1주일</label>
                      
                      <input type="radio" class="btn-check" name="date" id="btnradio4" autocomplete="off">
                      <label class="btn btn-outline-secondary btn-sm" for="btnradio4">1달</label>
            </div>
               <input type="date" id="startDate" class="form-control date">~
               <input type="date" id="endDate" class="form-control date">
            </div>


            <div class="form-group" style="justify-content: center;margin-top:50px;">
               <input type="button"  id="searchBtn"  class="btn btn-success"   value="검색" >
               <input type="reset" class="btn btn-light" value="옵션 초기화">
            </div>
         </form>
      </div>
      
      <!-- 문의검색 결과 출력 div -->
 <div class="form" >
     <h5  class="form-group" style="border-bottom: 1px solid  #EEF0F4">검색결과 N건</h5>
   <table  class="table table-striped table-hover">

      <thead style="font-size: 20px;"> 
      <tr>
      <td>접수일</td>
      <td>처리상태</td>
      <td>문의유형</td>
      <td>주문번호</td>
      <td>문의제목</td>
      <td>상품번호</td>
      <td>상품명</td>
      </tr>
      </thead>
      <tbody>
            <tr>
      <td>접수일/예시</td>
      <td>처리상태/예시</td>
      <td>문의유형/예시</td>
      <td>주문번호/예시</td>
      <td>문의제목/예시</td>
      <td>상품번호/예시</td>
      <td>상품명/예시</td>
      </tr>
            <tr>
      <td>접수일/예시</td>
      <td>처리상태/예시</td>
      <td>문의유형/예시</td>
      <td>주문번호/예시</td>
      <td>문의제목/예시</td>
      <td>상품번호/예시</td>
      <td>상품명/예시</td>
      </tr>
            <tr>
      <td>접수일/예시</td>
      <td>처리상태/예시</td>
      <td>문의유형/예시</td>
      <td>주문번호/예시</td>
      <td>문의제목/예시</td>
      <td>상품번호/예시</td>
      <td>상품명/예시</td>
      </tr>
            <tr>
      <td>접수일/예시</td>
      <td>처리상태/예시</td>
      <td>문의유형/예시</td>
      <td>주문번호/예시</td>
      <td>문의제목/예시</td>
      <td>상품번호/예시</td>
      <td>상품명/예시</td>
      </tr>
      
      </tbody>
   </table>
    
<p style="color: E9ECEF">클릭시 문의 상세보기 페이지가 나타납니다.</p>
</div>

   </div>

</body>

</html>