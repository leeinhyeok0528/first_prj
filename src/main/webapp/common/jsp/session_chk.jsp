<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   
<c:if  test="${empty userData }"> <!-- 세션이 사라졌다면-->
<c:redirect  url="http://localhost/jsp_prj/index.jsp"/>
</c:if>
<%
String remoteIp = request.getRemoteAddr(); 
%>