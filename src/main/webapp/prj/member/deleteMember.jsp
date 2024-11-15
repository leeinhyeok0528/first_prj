<%@page import="java.sql.SQLException"%>
<%@page import="org.json.simple.JSONObject"%>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="kr.co.sist.user.UserVO" %>
<%@page import="kr.co.sist.user.AdminMemberManageDAO"%>
<%
    String userId = request.getParameter("memberId");

    AdminMemberManageDAO dao = AdminMemberManageDAO.getInstance();
    JSONObject jsonResponse = new JSONObject();

    try {
        int result = dao.deleteMember(userId);
        if (result > 0) {
            jsonResponse.put("status", "success");
        } else {
            jsonResponse.put("status", "failure");
        }
    } catch (SQLException e) {
        jsonResponse.put("status", "failure");
        e.printStackTrace();
    }
    out.print(jsonResponse.toJSONString());
%>
