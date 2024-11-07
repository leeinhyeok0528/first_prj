<%@page import="inquiry.InquiryUtil"%>
<%@page import="inquiry.InquirySearchVO"%>
<%@page import="inquiry.AdminInquiryDAO"%>
<%@page import="inquiry.InquiryVO"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.SQLException"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>

<jsp:useBean id="sVO" class="inquiry.InquirySearchVO"  scope="page"/>
<jsp:setProperty property="*" name="sVO"/>

<!DOCTYPE html>
<html>

<head>
    <!-- 기존 헤더 내용 유지 -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>문의관리 페이지</title>

    <!-- Bootstrap CDN 시작 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <!-- jQuery CDN 시작 -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>

    <style>
        /* 기존 스타일 유지 */
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

        a { 
            color: #000000;
            text-decoration: none;
            font-size: 16px;
        }

        a:hover { 
            color: #858585; 
            text-decoration: underline; 
            font-size: 16px;
        }
    </style>

    <script type="text/javascript">
        $(document).ready(function () {
            initializeDateFields();
            setupRadioButtons();
            setupSearchButton();
        });

        // 페이지 로드시 날짜 기본 설정
        function initializeDateFields() {
            const today = new Date();
            const formattedToday = formatDate(today);
            $('#startDate').val(formattedToday);
            $('#endDate').val(formattedToday);
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
            $('input[name="date"]').change(function () {
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

        // 검색 버튼 클릭 시 폼 제출
        function setupSearchButton() {
            $('#searchBtn').click(function () {
                // 폼 제출 전에 유효성 검사 추가
                const startDate = $('#startDate').val();
                const endDate = $('#endDate').val();

                if (startDate && endDate && startDate > endDate) {
                    alert("시작일은 종료일보다 늦을 수 없습니다.");
                    return;
                }

                // 폼 제출
                $('#frm').submit();
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
    </script>
</head>


<body>

<!-- 서버 측 코드 시작 -->
<%
    // 게시판 리스트 구현

    // 1. 총 레코드 수 구하기
    int totalCount = 0; // 총 레코드 수

    AdminInquiryDAO aiDAO = AdminInquiryDAO.getInstance();
    String filter = sVO.getFilter();
    String startDate = sVO.getStartDate();
    String endDate = sVO.getEndDate();
    try {
        // 총 문의 수 조회
        totalCount = aiDAO.selectTotalCount(sVO);
    } catch (SQLException se) {
        se.printStackTrace();
    }

    // 2. 한 화면에 보여줄 레코드의 수
    int pageScale = 10;

    // 3. 총 페이지 수
    int totalPage = (int) Math.ceil((double) totalCount / pageScale);

    // 4. 검색의 시작번호를 구하기 (pagination의 번호) [1][2][3]
    String paramPage = request.getParameter("currentPage");

    int currentPage = 1;
    if (paramPage != null) {
        try {
            currentPage = Integer.parseInt(paramPage);
        } catch (NumberFormatException nfe) {
        } // end catch
    } // end if

    int startNum = currentPage * pageScale - pageScale + 1; // 시작번호
    // 5. 끝번호 
    int endNum = startNum + pageScale - 1; // 끝 번호

    sVO.setCurrentPage(currentPage);
    sVO.setStartNum(startNum);
    sVO.setEndNum(endNum);
    sVO.setTotalPage(totalPage);
    sVO.setTotalCount(totalCount);

    // Debug: sVO의 필드 출력 (디버깅용)
    System.out.println("Filter: " + filter);
    System.out.println("Start Date: " + startDate);
    System.out.println("End Date: " + endDate);
    System.out.println("Current Page: " + currentPage);

    List<InquiryVO> listBoard = null;
    try {
        // 필터와 날짜 범위에 따라 문의사항 조회
        listBoard = aiDAO.selectInquiriesByFilter(filter, startDate, endDate);

        // 페이징 처리
        // 전체 조회된 문의 수를 기준으로 페이징 계산
        totalCount = listBoard.size();
        totalPage = (int) Math.ceil((double) totalCount / pageScale);
        sVO.setTotalPage(totalPage);
        sVO.setTotalCount(totalCount);

        // 페이징을 위한 시작 번호 설정
        int displayStartNum = currentPage * pageScale - pageScale + 1;
        if (displayStartNum < 1) {
            displayStartNum = 1;
        }

        // 페이징 리스트를 잘라내기 (현재 페이지에 맞는 리스트만 선택)
        int fromIndex = Math.min(startNum - 1, listBoard.size());
        int toIndex = Math.min(endNum, listBoard.size());
        List<InquiryVO> pagedList = listBoard.subList(fromIndex, toIndex);

        // 제목 길이 제한
        String tempSubject = "";
        for (InquiryVO tempVO : pagedList) {
            tempSubject = tempVO.getTitle();
            if (tempSubject.length() > 30) {
                tempVO.setTitle(tempSubject.substring(0, 29) + "...");
            }
        } // end for

        // 페이지네이션을 위한 리스트 설정
        pageContext.setAttribute("listBoard", pagedList);

    } catch (SQLException se) {
        se.printStackTrace();
    } // end catch

    pageContext.setAttribute("totalCount", totalCount);
    pageContext.setAttribute("pageScale", pageScale);
    pageContext.setAttribute("totalPage", totalPage);
    pageContext.setAttribute("currentPage", currentPage);
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
            <form method="GET" action="qna.jsp" name="frm" id="frm" >
                <!-- 현재 페이지를 관리하기 위한 숨겨진 필드 -->
                <input type="hidden" id="currentPage" name="currentPage" value="1">

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
                    <!-- 오늘 -->
        <input type="radio" class="btn-check" name="date" id="btnradio1" value="today" autocomplete="off"
            <c:if test="${param.date == 'today' || empty param.date}">checked</c:if>>
        <label class="btn btn-outline-secondary btn-sm" for="btnradio1" style="margin-right: 0px">오늘</label>

        <!-- 지난 3일 -->
        <input type="radio" class="btn-check" name="date" id="btnradio2" value="last3days" autocomplete="off"
            <c:if test="${param.date == 'last3days'}">checked</c:if>
        >
        <label class="btn btn-outline-secondary btn-sm" for="btnradio2" style="margin-right: 0px">3일</label>

        <!-- 지난 1주일 -->
        <input type="radio" class="btn-check" name="date" id="btnradio3" value="lastweek" autocomplete="off"
            <c:if test="${param.date == 'lastweek'}">checked</c:if>
        >
        <label class="btn btn-outline-secondary btn-sm" for="btnradio3" style="margin-right: 0px">1주일</label>

        <!-- 지난 1달 -->
        <input type="radio" class="btn-check" name="date" id="btnradio4" value="lastmonth" autocomplete="off"
            <c:if test="${param.date == 'lastmonth'}">checked</c:if>
        >
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
                    검색결과 :   <c:out value="${sVO.totalCount}"/> 건
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
                        <%
                            // 현재 페이지에 맞는 리스트를 이미 pagedList로 가져왔으므로, listBoard를 pagedList로 설정
                            List<InquiryVO> displayList = (List<InquiryVO>) pageContext.getAttribute("listBoard");
                            int articleNo = sVO.getStartNum();
                        %>
                        <c:forEach var="iVO" items="${listBoard}">
                            <tr>
                                <td><%= articleNo %></td>
                                <% articleNo++; %>
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
            <% sVO.setUrl("qna.jsp"); %>
            <%= new InquiryUtil().pagination(sVO) %>
        </div>
    </div>
</body>
</html>
