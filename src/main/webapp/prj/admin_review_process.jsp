<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" 
info="문의에 대해 답변을 추가/삭제 하는 일"
%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="inquiry.InquiryVO"%>
<%@ page import="inquiry.AdminInquiryDAO"%>
<% request.setCharacterEncoding("UTF-8"); %>

<%
    String action = request.getParameter("submitAction");
    String inquiryIdStr = request.getParameter("inquiryId");

    // 디버깅을 위해 파라미터 값 출력
   System.out.println (action);
    System.out.println(inquiryIdStr);

    AdminInquiryDAO aiDAO = AdminInquiryDAO.getInstance();

    String message;
    boolean success = false;

    if ("delete".equals(action) && inquiryIdStr != null && !inquiryIdStr.isEmpty()) {
        try {
            int inquiryId = Integer.parseInt(inquiryIdStr);
            // InquiryVO 객체 생성 및 inquiryId 설정
            InquiryVO iVO = new InquiryVO();
            iVO.setInquiryId(inquiryId);

            int rowCount = aiDAO.deleteInquiry(iVO);

            if (rowCount > 0) {
                message = "삭제가 완료되었습니다.";
                success = true;
            } else {
                message = "삭제에 실패하였습니다.";
            }
        } catch (NumberFormatException e) {
            message = "유효하지 않은 문의 ID입니다.";
            e.printStackTrace();
        } catch (SQLException e) {
            message = "데이터베이스 오류가 발생하였습니다.";
            e.printStackTrace();
        }
    } else if ("register".equals(action)) {
        try {
            int inquiryId = Integer.parseInt(inquiryIdStr);
            String answer = request.getParameter("answer");

            // InquiryVO 객체 생성 및 필요한 데이터 설정
            InquiryVO iVO = new InquiryVO();
            iVO.setInquiryId(inquiryId);
            iVO.setAdminAd(answer);
            iVO.setStatus("처리완료"); // 상태 업데이트 (필요에 따라 변경)

            int rowCnt = aiDAO.insertHandleInquiry(iVO);

            if (rowCnt > 0) {
                message = "답변이 등록되었습니다.";
                success = true;
            } else {
                message = "답변 등록에 실패하였습니다.";
            }
        } catch (NumberFormatException e) {
            message = "유효하지 않은 문의 ID입니다.";
            e.printStackTrace();
        } catch (SQLException e) {
            message = "데이터베이스 오류가 발생하였습니다.";
            e.printStackTrace();
        }
    } else {
        message = "유효하지 않은 요청입니다.";
    }

%>
<script>
    alert('<%= message %>');
    <% if (success) { %>
        if (window.opener) {
            window.opener.location.reload(); // 부모 창 새로고침
        }
    <% } %>
    window.close(); // 팝업 닫기
</script>
