<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" info="" %>
         <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
         
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>공지사항 등록 페이지</title>
  <link rel="stylesheet" href="admin.css">
  <jsp:useBean id="nVO" class="kr.co.sist.notice.NoticeVO"/>
  <jsp:useBean id="ncVO" class="kr.co.sist.notice.NoticeCategoryVO"/>
  <jsp:setProperty property="*" name="nVO"/>
  <jsp:setProperty property="*" name="ncVO"/>
  

  <!-- Bootstrap CDN -->
  <link rel="stylesheet" href="http://192.168.10.225/first_prj/prj/main_Sidbar.css">
  
  <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet">
  <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>

  <style>
    
  
      .notice_title {
          height: 10%;
          border: 1px solid #333;
          font-weight: bold;
          font-size: 30pt;
          border-radius: 20px;
          text-align: center;
      }

      .notice_list {
          height: 80%;
          margin-top: 20px;
          padding-top: 20px;
          border: 1px solid #333;
          border-radius: 20px;
      }

      .notice-form {
          width: 100%;
          border-collapse: separate;
          border-spacing: 0 15px;
      }

      .notice-form .label {
          width: 150px;
          text-align: left;
          padding-right: 20px;
          vertical-align: top;
      }

      .notice-form .required {
          color: red;
          margin-right: 5px;
      }

      .notice-form input,
      .notice-form select,
      .notice-form textarea {
          width: 100%;
      }

      .button-group {
          text-align: center;
      }

      .button-group .btn {
          width: 150px;
          height: 50px;
      }
  </style>
</head>
<body>
    <!-- 헤더와 사이드바 임포트-->
        <c:import url="/prj/sidebar.jsp"/>


<!-- 메인 콘텐츠 영역 -->
<div class="main-content">
  <div class="content-box">
    <div class="notice_title">공지사항 등록</div>
    <div class="notice_list">
      <table class="table notice-form">
        <tr>
          <td class="label"><span class="required">*</span>분류</td>
          <td><select name="category" id="category" style="width: 20%">
            <option value="N/A">선택하세요</option>
            <option value="delivery">배송</option>
            <option value="order">주문</option>
          </select></td>
        </tr>
        <tr>
          <td class="label"><span class="required">*</span>제목</td>
          <td><input type="text" id="title"></td>
        </tr>
        <tr>
          <td class="label"><span class="required">*</span>공지사항 상세</td>
          <td><textarea id="content" rows="10" placeholder="공지사항 내용을 작성해주세요" style="resize:none;"></textarea></td>
        </tr>
      </table>
      <div class="button-group">
        <button class="btn btn-success" id="addBtn">등록</button>
        <button class="btn btn-warning" id="cancelBtn">취소</button>
      </div>
    </div>
  </div>
  <script>
      $(function () {
          $('#addBtn').click(() => {
        	  
        	  var param={ title: $('#title').val(),
        	            content: $('#content').val(),
        	            categolyId: $('#categolyId').val()};
        	  
        	  $.ajax({
        		    url: "notice_create.jsp",
        		    type: "post",
        		    data: param,
        		    dataType: "json",
        		    error: function(xhr) {
        		        console.log(xhr.status);
        		        alert("공지사항이 정상적으로 등록되지 못하였습니다");
        		    },
        		    success: function(jsonObj) {
        		        if (jsonObj.result) {
        		            if (!jsonObj.loginStatus) {
        		                alert("로그인 정보가 존재하지 않습니다");
        		                return;
        		            }
        		            
        		            var msg = "공지사항등록에 실패하였습니다";
        		            if (jsonObj.insertStatus) {
        		                msg = "공지사항등록에 성공하였습니다";
        		                alert(msg);
        		                // 등록에 성공한 후 notice_list.jsp로 이동
        		                location.href = "notice_list.jsp";
        		            } else {
        		                alert(msg);
        		            }
        		        } else {
        		            alert("요청이 실패했습니다. 서버 오류가 발생했습니다.");
        		        }
        		    }
        		});

          $('#cancelBtn').click(()=>{
              location.href='notice_list.jsp'
          })
      });
      })
  </script>
</div>

</body>
</html>