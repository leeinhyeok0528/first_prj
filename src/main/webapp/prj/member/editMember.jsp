<%@page import="kr.co.sist.user.UserVO"%>
<%@page import="kr.co.sist.user.AdminMemberManageDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" info=""%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
request.setCharacterEncoding("UTF-8");
String userId = request.getParameter("userid");
AdminMemberManageDAO ammDAO = AdminMemberManageDAO.getInstance();
UserVO uVO = ammDAO.selectOneMember(userId);
%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>회원 수정</title>

<!-- 부트스트랩 CDN -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<script type="text/javascript">
$(function() {
    // 수정 버튼 클릭 시
   $(function() {
    // 수정 버튼 클릭
    $("#update").click(function() {
        if (confirm("수정하시겠습니까?")) {
            $.ajax({
                url: "updateMember.jsp", // updateMember.jsp에서 처리
                type: "POST",
                data: {
                    memberId: $("#memberId").val(), // 수정할 회원 ID
                    memberName: $("#memberName").val(),
                    zipcode: $("#zipcode").val(),
                    address: $("#address").val(),
                    joindate: $("#joindate").val(),
                    email: $("#email").val()
                },
                success: function(response) {
                    if (response.trim() === "success") {
                        alert("정상적으로 수정되었습니다.");
                        window.location.href = "memberList.jsp";
                    } else {
                        alert("수정에 실패했습니다. 다시 시도해 주세요.");
                    }
                },
                error: function() {
                    alert("서버 오류가 발생했습니다. 다시 시도해 주세요.");
                }
            });
        }
    });

    // 삭제 버튼 클릭
    $("#delete").click(function() {
        if (confirm("삭제하시겠습니까?")) {
            $.ajax({
                url: "deleteMember.jsp", // deleteMember.jsp에서 처리
                type: "POST",
                data: { memberId: $("#memberId").val() }, // 삭제할 회원 ID
                success: function(response) {
                    if (response.trim() === "success") {
                        alert("회원이 삭제되었습니다.");
                        window.location.href = "memberList.jsp";
                    } else {
                        alert("삭제에 실패했습니다. 다시 시도해 주세요.");
                    }
                },
                error: function() {
                    alert("서버 오류가 발생했습니다. 다시 시도해 주세요.");
                }
            });
        }
    });
});


	    $("#list").click(function() {
	        window.location.href = "memberList.jsp";
	    });
	});
</script>

</head>
<body>

  <!-- 좌측 고정 사이드바 -->
   <div class="sidebar" id="sidebar">
        <c:import url="http://192.168.10.225/first_prj/prj/sidebar.jsp"/>
   </div>


	<!-- 메인 콘텐츠 영역 -->
	<div class="main-content">
		<div class="content-box" id="sub-title">
			<h4>회원관리</h4>
		</div>

		<div class="content-box">
			<h2>회원 수정</h2>
			<table class="table">
				<colgroup>
					<col width="40%">
					<col width="60%">
				</colgroup>
				<tbody>

					<tr>
						<th>회원ID</th>
						<td><input type="text" id="memberId" value="<%= uVO.getUserId() %>" readonly="readonly"></td>
					</tr>
					<tr>
						<th>회원성명</th>
						<td><input type="text" id="memberName" value="<%= uVO.getName() %>" /></td>
					</tr>
					<tr>
						<th>회원주소</th>
						<td>
							<input type="text" id="zipcode" value="<%= uVO.getZipcode() %>" style="width: 80px;" />
							<input type="text" id="address" value="<%= uVO.getAddress1() + " " + uVO.getAddress2() %>" />
						</td>
					</tr>
					<tr>
						<th>가입일자</th>
						<td><input type="text" id="joindate" value="<%= uVO.getJoinDate() %>" readonly="readonly"></td>
					</tr>

					<tr>
						<th>이메일</th>
						<td><input type="text" id="email" value="<%= uVO.getEmail() %>" /></td>
					</tr>

				</tbody>
			</table>

			<div class="btn_group" style="text-align: center">
				<button type="button" id="update" class="btn btn-primary">수정</button>
				<button type="button" id="delete" class="btn btn-danger">삭제</button>
				<button type="button" id="list" class="btn btn-secondary">목록</button>
			</div>

		</div>
	</div>

</body>
</html>
