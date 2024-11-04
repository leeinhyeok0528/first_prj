<%@page import="inquiry.SearchVO"%>
<%@page import="inquiry.InquiryVO"%>
<%@page import="inquiry.AdminInquiryDAO"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.SQLException"%>
<%@page import="kr.co.sist.util.BoardUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    AdminInquiryDAO aiDAO = AdminInquiryDAO.getInstance();
    List<InquiryVO> listBoard = null;
    SearchVO sVO = new SearchVO(); // SearchVO 객체 생성

    // 검색 조건 받아오기
    String filter = request.getParameter("filter");
    if (filter != null && !filter.isEmpty()) {
        sVO.setFilter(filter);
    }

    String field = request.getParameter("field"); // 검색 필드
    if (field != null && !field.isEmpty()) {
        sVO.setField(field);
    }

    String keyword = request.getParameter("keyword"); // 검색 키워드
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
  <nav aria-label="Page navigation">
      <ul class="pagination justify-content-center">
          <%
              // BoardUtil의 pagination 메소드 사용
              String paginationHtml = BoardUtil.getInstance().pagination(sVO);
              out.print(paginationHtml);
          %>
      </ul>
  </nav>
</body>
</html>
