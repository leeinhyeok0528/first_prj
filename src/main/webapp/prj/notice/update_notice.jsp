<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="kr.co.sist.notice.MangeNoticeDAO" %>
<%@ page import="kr.co.sist.notice.NoticeVO" %>

<%
    request.setCharacterEncoding("UTF-8");
    response.setContentType("application/json; charset=UTF-8");

    boolean updateStatus = false;
    boolean result = true;

    String noticeIdStr = request.getParameter("noticeId");
    String category = request.getParameter("category");
    String title = request.getParameter("title");
    String content = request.getParameter("content");

    if (noticeIdStr != null && !noticeIdStr.isEmpty()) {
        int noticeId = Integer.parseInt(noticeIdStr);

        // NoticeVO 객체 생성 및 업데이트할 데이터 설정
        NoticeVO nVO = new NoticeVO();
        nVO.setNoticeId(noticeId);
        nVO.setCategolyId(Integer.parseInt(category)); // 카테고리를 ID로 설정했다고 가정
        nVO.setTitle(title);
        nVO.setContent(content);

        // DAO 인스턴스 생성 후 업데이트 수행
        MangeNoticeDAO noticeDAO = MangeNoticeDAO.getInstance();
        int updateCount = noticeDAO.updateOneNotice(nVO);

        // 업데이트 성공 여부 확인
        updateStatus = (updateCount > 0);
    } else {
        result = false;
    }

    // JSON 응답 생성
    out.print("{");
    out.print("\"result\": " + result + ",");
    out.print("\"updateStatus\": " + updateStatus);
    out.print("}");
%>
