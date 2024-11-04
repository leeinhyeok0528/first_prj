<%@page import="kr.co.sist.util.BoardUtil"%>
<%@page import="inquiry.SearchVO"%>
<%@page import="inquiry.AdminInquiryDAO"%>
<%@page import="inquiry.InquiryVO"%>
<%@page import="java.util.List"%>
<%@ page import="java.sql.SQLException" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>
<%@page import="inquiry.AdminInquiryDAO"%>
<%@page import="inquiry.InquiryVO"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.SQLException"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

 
<!DOCTYPE html>
<html lang="ko">

<head>
   <meta charset="UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <title>문의관리 페이지</title>

   <!-- Bootstrap CDN 시작 -->
   <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
   <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
   <!-- jQuery CDN 시작 -->
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>

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

 a{ color: #000000;text-decoration: none;font-size:16px  }
 a:hover{ color: #858585; text-decoration: underline; font-size:16px  }
   </style>
   
   
   <script type="text/javascript">
      $(document).ready(function () {
         initializeDateFields();
         setupRadioButtons();
         setupSearchButton();
         setupPaginationLinks(); // 페이지네이션 링크 이벤트 설정
      });
      
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
                  startDate = today;
                  break;
               case 'btnradio2':
                  startDate.setDate(today.getDate() - 3);
                  break;
               case 'btnradio3':
                  startDate.setDate(today.getDate() - 7);
                  break;
               case 'btnradio4':
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

      // 검색 버튼 클릭 시 AJAX로 검색 조건 전송 및 결과 표시
      function setupSearchButton() {
          $('#searchBtn').on('click', function () {
              // 검색 시 currentPage를 1로 설정
              $('#currentPage').val(1);
              performSearch();
          });
      }

      // 페이지네이션 링크 이벤트 설정
      function setupPaginationLinks() {
          // 동적으로 생성된 링크를 처리하기 위해 이벤트 위임 사용
          $('#searchResults').on('click', '.page-link', function (e) {
              e.preventDefault();
              const page = $(this).data('page');
              if (page) {
                  $('#currentPage').val(page);
                  performSearch();
              }
          });
      }

      // 실제 검색 수행 함수
      function performSearch() {
          const filter = $("#filter").val();
          const startDate = $('#startDate').val();
          const endDate = $('#endDate').val();
          const currentPage = $('#currentPage').val();

          $.ajax({
              url: "inquiry_search.jsp",
              type: "GET",
              data: {
                  filter: filter,
                  startDate: startDate,
                  endDate: endDate,
                  currentPage: currentPage
              },
              success: function(response) {
                  $('#searchResults').html(response);
              },
              error: function(xhr, status, error) {
                  console.error("Error:", status, error);
              }
          });
      }

      // 상세보기 팝업창 함수
      function openPopup(inquiryId) {
         var left = window.screenX + 300;
         var top = window.screenY + 200;
         var width = 700;
         var height = 700;
         window.open("qna_popup.jsp?inquiryId=" + inquiryId, "qna_popup", "width=" + width + ",height=" + height + ",left=" + left + ",top=" + top);
      }

      // Reset 버튼 기능=> reset버튼 클릭시 옵션이 초기화되고 다시 모든 문의를 표시함.
      $('input[type="reset"]').on('click', function () {
          // 필터 초기화
          $('#filter').val('all');
          initializeDateFields();

          // currentPage를 1로 설정
          $('#currentPage').val(1);

          // 검색 수행
          performSearch();
      });
   </script>
</head>


<body>

<!-- 서버 측 코드 시작 -->
<%
     AdminInquiryDAO aiDAO = AdminInquiryDAO.getInstance();
     List<InquiryVO> listBoard = null;
     SearchVO sVO = new SearchVO(); // SearchVO 객체 생성

     // 검색 조건 받아오기
     String filter = request.getParameter("filter");
     if (filter != null && !filter.isEmpty()) {
         sVO.setFilter(filter);
     }

     String field = request.getParameter("field"); // 추가: 검색 필드
     if (field != null && !field.isEmpty()) {
         sVO.setField(field);
     }

     String keyword = request.getParameter("keyword"); // 추가: 검색 키워드
     if (keyword != null && !keyword.isEmpty()) {
         sVO.setKeyword(keyword);
     }

     String startDate = request.getParameter("startDate");
     if (startDate != null && !startDate.isEmpty()) {
         sVO.setStartDate(startDate);
     }

     String endDate = request.getParameter("endDate");
     if (endDate != null && !endDate.isEmpty()) {
         sVO.setEndDate(endDate);
     }

     // 현재 페이지 번호 받아오기
     String paramPage = request.getParameter("currentPage");
     int currentPage = 1;
     if (paramPage != null && !paramPage.isEmpty()) {
         try {
             currentPage = Integer.parseInt(paramPage);
         } catch (NumberFormatException nfe) {
             currentPage = 1;
         }
     }

     int pageScale = 10; // 한 페이지당 보여줄 게시물 수

     // 총 게시물 수 가져오기
     int totalCount = 0;
     try {
         totalCount = aiDAO.getTotalInquiryCount(sVO); // 총 게시물 수 가져오는 메서드 (필터 적용)
     } catch (SQLException se) {
         se.printStackTrace();
     }

     int totalPage = (int) Math.ceil((double) totalCount / pageScale); // 총 페이지 수

     int startNum = (currentPage - 1) * pageScale + 1; // 시작 번호
     int endNum = currentPage * pageScale; // 끝 번호

     sVO.setCurrentPage(currentPage);
     sVO.setStartNum(startNum);
     sVO.setEndNum(endNum);
     sVO.setTotalPage(totalPage);
     sVO.setTotalCount(totalCount);
     sVO.setUrl("qna.jsp"); // 페이지네이션 링크의 기본 URL 설정

     // 문의 목록 가져오기
     try {
         listBoard = aiDAO.getInquiries(sVO); // 페이지네이션 적용된 문의 목록 가져오기
     } catch (SQLException se) {
         se.printStackTrace();
     }

     request.setAttribute("listBoard", listBoard);
     request.setAttribute("sVO", sVO);
%>

   <!-- 상단 고정 헤더 -->
   <div class="header">
      <span>스마트스토어 센터</span>
      <span>로그인 상태</span>
   </div>

   <!-- 좌측 고정 사이드바 -->
   <div class="sidebar">
      <c:import url="/prj/sidebar.jsp"></c:import>
   </div>

   <!-- 메인 콘텐츠 영역 -->
   <div class="main-content">
      <!-- 헤더 섹션 -->
      <div class="form">
         <h4>문의 관리</h4>
      </div>

      <!-- 검색 필터 폼 -->
      <div class="form">
         <form>
            <!-- 현재 페이지를 관리하기 위한 숨겨진 필드 -->
            <input type="hidden" id="currentPage" name="currentPage" value="1">
            <input type="hidden" id="field" name="field" value="${sVO.field}">
            <input type="hidden" id="keyword" name="keyword" value="${sVO.keyword}">

            <div class="form-group">
               <label for="filter">유형</label>
               <select id="filter" name="filter" class="form-select">
                  <option value="all" <c:if test="${sVO.filter == 'all'}">selected</c:if>>전체</option>
                  <option value="item" <c:if test="${sVO.filter == 'item'}">selected</c:if>>상품문의</option>
                  <option value="refund" <c:if test="${sVO.filter == 'refund'}">selected</c:if>>구매취소</option>
               </select>
            </div>

            <div class="form-group">
               <label for="date-range">문의일</label>
               <div class="btn-group" role="group">
                  <input type="radio" class="btn-check" name="date" id="btnradio1" autocomplete="off" <c:if test="${sVO.startDate != null && sVO.startDate.equals(fn:substringAfter(fn:substringBefore(sVO.startDate, ' '), '-'))}">checked</c:if>>
                  <label class="btn btn-outline-secondary btn-sm" for="btnradio1" style="margin-right: 0px">오늘</label>

                  <input type="radio" class="btn-check" name="date" id="btnradio2" autocomplete="off" <c:if test="${fn:length(sVO.startDate) > 0 && !sVO.startDate.equals(fn:substringAfter(fn:substringBefore(sVO.startDate, ' '), '-'))}">checked</c:if>>
                  <label class="btn btn-outline-secondary btn-sm" for="btnradio2" style="margin-right: 0px">3일</label>

                  <input type="radio" class="btn-check" name="date" id="btnradio3" autocomplete="off">
                  <label class="btn btn-outline-secondary btn-sm" for="btnradio3" style="margin-right: 0px">1주일</label>

                  <input type="radio" class="btn-check" name="date" id="btnradio4" autocomplete="off">
                  <label class="btn btn-outline-secondary btn-sm" for="btnradio4" style="margin-right: 0px">1달</label>
               </div>
               <input type="date" id="startDate" name="startDate" class="form-control date" value="<c:out value='${sVO.startDate}'/>"> ~
               <input type="date" id="endDate" name="endDate" class="form-control date" value="<c:out value='${sVO.endDate}'/>">
            </div>

            <div class="form-group" style="justify-content: center; margin-top: 50px;">
               <input type="button" id="searchBtn" class="btn btn-success" value="검색">
               <input type="reset" class="btn btn-light" value="옵션 초기화">
            </div>
         </form>
      </div>

     <!-- 검색 결과 표시 영역 -->
    <div id="searchResults">

        <div class="form">
            <h5 class="form-group" style="border-bottom: 1px solid #EEF0F4">
               전체문의 <c:out value="${sVO.totalCount}"/>건
            </h5>
            <table class="table table-striped table-hover">
                <thead style="font-size: 20px;">
                    <tr>
                    	<td>번호</td>
                        <td>문의유형</td>
                        <td>문의제목</td>
                        <td>처리상태</td>
                        <td>등록일</td>
                        <td>작성자</td>
                    </tr>
                </thead>
                <tbody>
       <% int articleNo = sVO.getTotalCount() - (sVO.getCurrentPage() - 1) * pageScale; %>
    <c:forEach var="iVO" items="${listBoard}">
        <tr>
            <td><%= articleNo %></td>
            <% articleNo--; %>
            <td><c:out value="${iVO.category}"/></td>
            <td><a href="#" onclick="openPopup('${iVO.inquiryId}')">
                <c:out value="${iVO.title}"/></a></td>
            <td><c:out value="${iVO.status}"/></td>
            <td><fmt:formatDate value="${iVO.createAt}" pattern="yyyy-MM-dd HH:mm"/></td>
            <td><c:out value="${iVO.userId}"/></td>
        </tr>
    </c:forEach>
              </tbody>
          </table>
      </div>

          <!-- 페이지네이션 -->
              <ul class="pagination center">
                  <%
                      // BoardUtil의 pagination 메소드 사용
                      String paginationHtml = BoardUtil.getInstance().pagination(sVO);
                      out.print(paginationHtml);
                  %>
              </ul>
      </div>
   </div>
</body>
</html>
