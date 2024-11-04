<%@page import="inquiry.AdminInquiryDAO"%>
<%@page import="inquiry.InquiryVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>문의 관리 팝업</title>

    <!-- Bootstrap CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <!-- jQuery CDN -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>

       <script type="text/javascript">
    $(function () {

        // 삭제 버튼 이벤트
        $(".delete-button").click(function (event) {
            if (confirm("해당 문의를 삭제하시겠습니까?")) {
                // 폼 제출
                $(this).closest('form').submit();
            }
        });

        // 닫기 버튼 이벤트
        $("#close").click(function () {
            self.close();
        });
        
        
        
        
    });
    </script>
    <style>
        body, html {
            margin: 0;
            padding: 0;
            height: 100%;
            font-family: Arial, sans-serif;
        }
        .header {
            background-color: #28a745;
            color: white;
            padding: 15px;
            font-weight: bold;
            text-align: center;
        }
        .content {
            padding: 20px;
        }
        .titleTable {
            width: 100%;
            table-layout: fixed;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            white-space: nowrap;
        }
        .form-container {
            padding: 20px;
            border: 1px solid #ddd;
            margin-top: 20px;
        }
        .footer {
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>

<body>
    <div class="header">문의 관리</div>
    <div class="content">
        <%
            AdminInquiryDAO aiDAO = AdminInquiryDAO.getInstance();
            InquiryVO iVO = null;

            String inquiryIdStr = request.getParameter("inquiryId");
            if (inquiryIdStr != null && !inquiryIdStr.isEmpty()) {
                try {
                    int inquiryId = Integer.parseInt(inquiryIdStr);
                    iVO = aiDAO.selectOneInquiry(inquiryId);
                    request.setAttribute("iVO", iVO);
                    request.setAttribute("inquiryId", inquiryId);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    out.println("<p>잘못된 형식의 ID입니다. 숫자로 입력해 주세요.</p>");
                }
            } else {
                out.println("<p>유효한 ID가 전달되지 않았습니다.</p>");
            }

            if (iVO == null) {
                out.println("<p>해당 ID로 데이터를 찾을 수 없습니다.</p>");
            }
        %>

        <c:if test="${not empty iVO}">
            <!-- 문의 정보 테이블 -->
            <table class="titleTable">
                <tr>
                    <th>문의 유형</th>
                    <td><c:out value="${iVO.category}"/></td>
                    <th>제목</th>
                    <td><c:out value="${iVO.title}"/></td>
                    <th>등록일</th>
                    <td> <fmt:formatDate value="${iVO.createAt}" pattern="yyyy-dd-MM"/></td>
                    <th>아이디</th>
                    <td><c:out value="${iVO.userId}"/></td>
                </tr>
            </table>
            
            <!-- 문의 내용 -->
            <div class="form-container">
                <p><c:out value="${iVO.content}"/></p>
            </div>

            <!-- 답변 등록 폼 -->
        <div class="form-container">
                <h5>답변</h5>
                <div class="form-group">
                    <c:choose>
                        <c:when test="${not empty iVO.adminAd}">
                            <!-- 답변이 이미 있는 경우 -->
                            <div><c:out value="${iVO.adminAd}"/></div>
                         <form method="post" action="adminAd_process.jsp" name="subitFrm">
							    <input type="hidden" name="inquiryId" value="${inquiryId}">
							    <div class="footer">
							        <button type="submit" class="btn btn-success btn-sm delete-button" name="submitAction" value="delete">삭제</button>
							        <button type="button" class="btn btn-light btn-sm" id="close">닫기</button>
							    </div>  
							</form>

                        </c:when>
                        <c:otherwise>
                            <!-- 답변이 없는 경우 폼 표시 -->
                            <form method="post" action="adminAd_process.jsp" name="submitFrm">
							    <input type="hidden" name="inquiryId" value="${inquiryId}">
							    <textarea id="content" class="form-control" rows="7" placeholder="내용을 입력하세요" name="answer"></textarea>
							    <div class="footer">
							        <button type="submit" class="btn btn-danger btn-sm" id="register" name="submitAction" value="register">등록</button>
							        <button type="submit" class="btn btn-success btn-sm delete-button" name="submitAction" id="delete" value="delete">삭제</button>
							        <button type="button" class="btn btn-light btn-sm" id="close">닫기</button>
							    </div>  
							</form>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </c:if>
    </div>
</body>
</html>


