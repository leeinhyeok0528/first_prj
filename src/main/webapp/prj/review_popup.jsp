<%@page import="review.ReviewVO"%>
<%@page import="review.AdminReviewDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"   
    %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>리뷰 관리 팝업</title>

    <!-- Bootstrap CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <!-- jQuery CDN -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>

    <style>
        body,
        html {
            margin: 0;
            padding: 0;
            height: 100%;
            font-family: Arial, sans-serif;
        }

        .header {
            width: 100%;
            background-color: #28a745;
            color: white;
            padding: 15px;
            font-weight: bold;
            text-align: center;
        }

        .content {
            padding: 20px;
            width: 100%;
        }

        /* 테이블을 한 줄로 나란히 정렬하는 CSS */
        .titleTable {
            width: 100%;
            table-layout: fixed;
            border-collapse: collapse;
        }

        th, td {
            text-align: left;
            padding: 10px;
            border: 1px solid #ddd;
            white-space: nowrap;
        }

        /* 리뷰 컨테이너 - Flexbox 사용 */
        .reviewContainer {
            display: flex;
            border: 1px solid #ddd;
            margin-top: 20px;
            padding: 20px;
            min-height: 150px;
            max-height: 300px;
        }

		/* 이미지 영역 (40%) */
		.review-image {
		    flex: 0 0 40%;  /* 40%의 공간을 차지 */
		    margin-right: 20px;
		    max-width: 40%;  /* 최대 너비를 40%로 제한 */
		}
		
		/* 텍스트 영역 (60%) */
		.review-text {
		    flex: 0 0 60%;  /* 60%의 공간을 차지 */
		}
				img {
		    max-width: 100%;  /* 부모 요소의 너비를 넘지 않도록 설정 */
		    max-height: 100%; /* 부모 요소의 높이를 넘지 않도록 설정 */
		    height: auto;     /* 비율을 유지하면서 높이를 자동 조정 */
		    width: auto;      /* 비율을 유지하면서 너비를 자동 조정 */
		    object-fit: contain; /* 이미지가 잘리지 않고 틀에 맞춰줌 */
		}

    </style>
    <script type="text/javascript">
    $(function(){
    	$(function(){
    	
    		
    		$("#delete").click(function(){
    			
    			alert("삭제버튼 등록");
    		})//삭제버튼 이벤트 등록
    		
    		$("#close").click(function(){
    			self.close();
    		})//닫기버튼 이벤트 등록
    		
    		
    	})//ready
    	
    	
    })//ready
    
    
    
    </script>
    
    
</head>

<body>

    <div class="header">
        리뷰 상세보기
    </div>

    <div class="content">
<%
AdminReviewDAO arDAO = AdminReviewDAO.getInstance();
ReviewVO rVO = null;
int reviewId= Integer.parseInt(request.getParameter("reviewId"));

rVO = arDAO.selectOneReview(reviewId);
 
request.setAttribute("rVO", rVO);
request.setAttribute("reviewId", reviewId);
%>




        <table class="titleTable">
            <tr>
                <th>상품명</th>
                <td>예시</td>

                <th>작성자</th>
                <td>예시 제목</td>

                <th>리뷰 등록일</th>
                <td>2024-10-15</td>

            </tr>
        </table>

        <!-- 리뷰가 보이는 공간: 이미지 40%, 텍스트 60% -->
        <div class="reviewContainer">
            <!-- 이미지 영역 -->
            <div class="reviewImage">
                <img src="http://localhost/first_prj/prj/images/img1.png" alt="리뷰 이미지">
            </div>

            <!-- 리뷰내용 영역 -->
            <div class="reviewText">
            </div>
        </div>

        <!-- 버튼 영역 -->
        <div style="text-align: center;">
        <input type="button" class="btn btn-success btn-sm" value="삭제" id="delete" name="delete" >
        <input type="button" class="btn btn-light btn-sm" value="닫기" id="close" name="close">
        </div>
    </div>

</body>

</html>