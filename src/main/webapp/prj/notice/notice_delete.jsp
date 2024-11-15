<%@page import="kr.co.sist.notice.MangeNoticeDAO"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.sql.SQLException"%>
<%@page language="java" contentType="application/json; charset=UTF-8"%>

<%
    String idsParam = request.getParameter("ids"); // 체크된 게시물 ID들 (쉼표로 구분)
    if (idsParam != null && !idsParam.isEmpty()) {
        String[] ids = idsParam.split(","); // ID들 배열로 분리
        MangeNoticeDAO mnDAO = MangeNoticeDAO.getInstance();
        boolean success = true;
        
        try {
            // 각 ID에 대해 삭제 처리
            for (String id : ids) {
                int noticeId = Integer.parseInt(id);
                int result = mnDAO.deleteNotice(noticeId);
                if (result == 0) {
                    success = false; // 하나라도 실패하면 전체 실패 처리
                    break;
                }
            }

            if (success) {
                out.print("{\"status\":\"success\"}");
            } else {
                out.print("{\"status\":\"failure\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.print("{\"status\":\"failure\"}");
        }
    } else {
        out.print("{\"status\":\"failure\"}");
    }
%>
