<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="kr.co.sist.user.AdminMemberManageDAO"%>
<%@page import="kr.co.sist.user.UserVO"%>
<%@page import="org.json.simple.JSONObject"%>
<%
    String userId = request.getParameter("memberId");
    String memberName = request.getParameter("memberName");
    String zipcode = request.getParameter("zipcode");
    String address = request.getParameter("address");
    String joindate = request.getParameter("joindate");
    String email = request.getParameter("email");

    AdminMemberManageDAO dao = AdminMemberManageDAO.getInstance();
    JSONObject jsonResponse = new JSONObject();

    try {
        UserVO user = dao.selectOneMember(userId);
        if (user != null) {
            user.setName(memberName);
            user.setPhone(zipcode);  // 예시로 가정한 수정
            user.setAddress1(address);
            user.setAddress2(address); // 예시로 가정한 수정
            user.setJoinDate(joindate);
            user.setEmail(email);

            int result = dao.updateMember(user);
            if (result > 0) {
                jsonResponse.put("status", "success");
            } else {
                jsonResponse.put("status", "failure");
            }
        } else {
            jsonResponse.put("status", "failure");
        }
    } catch (SQLException e) {
        jsonResponse.put("status", "failure");
        e.printStackTrace();
    }
    out.print(jsonResponse.toJSONString());
%>
