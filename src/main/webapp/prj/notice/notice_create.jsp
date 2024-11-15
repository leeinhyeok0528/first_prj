<%@ page import="org.json.simple.JSONObject" %>
<%@ page import="java.sql.SQLException" %>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="kr.co.sist.notice.MangeNoticeDAO" %>
<%@ page import="kr.co.sist.notice.NoticeVO" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<%
    request.setCharacterEncoding("UTF-8");
    JSONObject jsonObj = new JSONObject();
    String method = request.getMethod();
    jsonObj.put("result", !"GET".equals(method)); // POST 요청만 처리

    // 폼 데이터 가져오기
    String categoryIdStr = request.getParameter("categoryId"); // categoryId로 수정
    String title = request.getParameter("title");
    String content = request.getParameter("content");
    String adminId = request.getParameter("admin_id"); // admin_id로 수정

    if (adminId != null && !adminId.isEmpty()) {
        // NoticeVO 객체 생성 및 속성 설정
        NoticeVO nVO = new NoticeVO();
        nVO.setAdmin_Id(adminId); // 전달된 admin_id 설정

        // categoryId는 String으로 받으므로, int로 변환 후 setCategoryId 호출
        try {
            int categoryId = Integer.parseInt(categoryIdStr); // String을 int로 변환
            nVO.setCategolyId(categoryId); // 카테고리 ID 설정
        } catch (NumberFormatException e) {
            jsonObj.put("insertStatus", false); // categoryId가 잘못된 경우 실패 처리
            out.print(jsonObj.toJSONString());
            return; // 종료
        }

        nVO.setTitle(title);
        nVO.setContent(content);

        // 데이터베이스에 공지사항 등록
        MangeNoticeDAO mnDAO = MangeNoticeDAO.getInstance();
        try {
            mnDAO.insertNotice(nVO); // 공지사항 등록
            jsonObj.put("insertStatus", true); // 성공 시 true
        } catch (SQLException se) {
            jsonObj.put("insertStatus", false); // 실패 시 false
            se.printStackTrace();
        }
    } else {
        jsonObj.put("insertStatus", false); // admin_id가 없으면 실패 처리
    }

    out.print(jsonObj.toJSONString()); // JSON 응답 출력
%>
