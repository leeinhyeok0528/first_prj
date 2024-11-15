<%@page import="kr.co.sist.user.AdminMemberVO"%>
<%@page import="kr.co.sist.notice.NoticeVO"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.SQLException"%>
<%@page import="kr.co.sist.notice.MangeNoticeDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" info="" %>
         <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
         
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>공지사항 리스트 페이지</title>
  <link rel="stylesheet" href="http://192.168.10.225/first_prj/prj/admin.css">
<link rel="stylesheet" href="http://192.168.10.225/first_prj/prj/main_Sidbar.css">

  <!-- Bootstrap CDN -->
  <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet">
  <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>

  <style>
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
  
      .notice_title {
          height: 10%;
          border: 1px solid #333;
          font-weight: bold;
          font-size: 30pt;
          border-radius: 20px;
          text-align: center;
      }

      .notice_list {
          height: 70%;
          margin-top: 20px;
          padding-top: 20px;
          border: 1px solid #333;
          border-radius: 20px;
      }

      .button-group {
          margin-top: 20px;
          margin-right: 20px;
          text-align: right;
      }
  </style>

    <script>
    $(document).ready(function () {
        // 전체 선택 체크박스 클릭 시 모든 체크박스 선택/해제
        $('#selectAll').change(function () {
            $('.selectItem').prop('checked', $(this).prop('checked'));
        });

        // 개별 항목 체크박스 클릭 시 전체 선택 여부 결정
        $('.selectItem').change(function () {
            $('#selectAll').prop('checked', $('.selectItem:checked').length === $('.selectItem').length);
        });

        // 삭제 버튼 클릭 시
        $('#deleteBtn').click(function () {
            const selectedRows = $('.selectItem:checked'); // 선택된 항목
            if (selectedRows.length === 0) {
                alert('삭제할 항목을 선택해주세요');
                return;
            }

            const selectedIds = [];
            selectedRows.each(function () {
                selectedIds.push($(this).val()); // 선택된 항목의 ID 값 수집
            });

            if (confirm('선택한 ' + selectedRows.length + '개의 게시물을 삭제하시겠습니까?')) {
                $.ajax({
                    url: 'notice_delete.jsp', // 삭제 요청을 처리하는 JSP 페이지
                    type: 'POST',
                    data: { ids: selectedIds.join(',') }, // 선택된 ID들 전송
                    success: function (response) {
                        if (response.status === 'success') {
                            selectedRows.each(function () {
                                $(this).closest('tr').remove(); // 삭제된 항목 화면에서 제거
                            });
                            alert('선택한 게시물이 삭제되었습니다.');
                        } else {
                            alert('삭제에 실패했습니다. 다시 시도해주세요.');
                        }

                        // 전체 선택 체크박스 해제
                        $('#selectAll').prop('checked', false);
                    },
                    error: function () {
                        alert('서버 요청 중 오류가 발생했습니다.');
                    }
                });
            }
        });
    });
</script>

</head>
<body>

    <!-- 헤더와 사이드바 임포트-->
        <c:import url="/prj/sidebar.jsp"/>


<!-- 메인 콘텐츠 영역 -->
<div class="main-content">
  <div class="content-box">
    <div class="notice_title">공지사항 리스트</div>

    <div class="notice_list">
      	<jsp:useBean id="nVO" class="kr.co.sist.notice.NoticeVO" scope="page"/>
        <jsp:useBean id="amVO" class="kr.co.sist.user.AdminMemberVO" scope="page"/>
    <% 
        MangeNoticeDAO mnDAO = MangeNoticeDAO.getInstance();
        int totalCount = 0;
        try {
            totalCount = mnDAO.selectTotalCount(amVO);
        } catch(SQLException se) {
            se.printStackTrace();
        }
        request.setAttribute("totalCount", totalCount);
    %>
      <span style="text-align: left; margin-left: 10px">공지사항 목록(총 ${totalCount}개)</span>
        <%
            List<NoticeVO> listnotice = null;
            try {
                listnotice = mnDAO.selectAllNotice();
            } catch(SQLException se) {
                se.printStackTrace();
            }
        %>
      <table class="table table-hover" style="text-align: center">
        <thead>
        <tr>
          <th><input type="checkbox" id="selectAll"></th>
          <th>번호</th>
          <th>카테고리</th>
          <th>제목</th>
          <th>작성자</th>
          <th>작성 일시</th>
        </tr>
        </thead>
        <tbody class="table-group-divider">
       <%
    if (listnotice != null && !listnotice.isEmpty()) {
        for (NoticeVO tempVO : listnotice) {
    %>
    <tr>
        <td><input type="checkbox" class="selectItem" value="<%= tempVO.getInquiry_id() %>"></td>
        <td><%= tempVO.getInquiry_id() %></td>
       <%--  <td><%= tempVO.getCategoryId() %></td> --%>
        <td><a href="notice_update.jsp?admin_id=<%= tempVO.getAdmin_Id() %>"><%= tempVO.getTitle() %></a></td>
        <td><%= tempVO.getAdmin_Id() %></td>
        <td><%= tempVO.getCreateAt() %></td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="6">등록된 공지사항이 없습니다.</td>
    </tr>
    <%
    }
    %>
        </tbody>
      </table>
    </div>
    <div>
      <div class="button-group">
        <button class="btn btn-success" id="createBtn" onclick="location.href='notice_add.jsp'">공지사항 등록</button>
        <button class="btn btn-danger" id="deleteBtn">선택 삭제</button>
      </div>
      <div class="page-nation" style="text-align: center">
        <!-- 페이지네이션 구현 필요 시 여기에 추가 -->
      </div>
    </div>
  </div>

</div>

</body>
</html>
