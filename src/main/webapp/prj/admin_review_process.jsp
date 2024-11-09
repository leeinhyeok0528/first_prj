<%@page import="review.AdminReviewDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" 
info="문의에 대해 답변을 추가/삭제 하는 일"
%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="inquiry.InquiryVO"%>
<%@ page import="inquiry.AdminInquiryDAO"%>
<% request.setCharacterEncoding("UTF-8"); %>

<%
    String action = request.getParameter("submitAction");
    int reviewId = Integer.parseInt(request.getParameter("reviewId")); 

    // 디버깅을 위해 파라미터 값 출력
   System.out.println (action);
   System.out.println (reviewId);

	AdminReviewDAO arDAO = AdminReviewDAO.getInstance();

    String message;
	
    
    boolean successFlag = false;
    if(action == null){
	    
    	
    }//end if
    
    
    
    
    
    if (action != null  ) {
        try {
			
				
        	iVO.setInquiryId(inquiryId);

            int rowCount = aiDAO.deleteInquiry(iVO);

            if (rowCount > 0) {
                message = "삭제가 완료되었습니다.";
                successFlag = true;
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
    }//end if

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
