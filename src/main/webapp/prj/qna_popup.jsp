<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"   
    %>
<!DOCTYPE html>
<html>

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
$(function(){
	$("#answer").click(function(){
		save();

	})//등록버튼 이벤트 등록
	
	$("#delete").click(function(){
		
		alert("삭제버튼 등록");
	})//삭제버튼 이벤트 등록
	
	$("#close").click(function(){
		self.close();
	})//닫기버튼 이벤트 등록
	
	
})//ready
	function save(){
		let answer =$("#content").val();
		alert(answer);
	
	}//sve

</script>


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
            white-space: nowrap; /* 데이터를 한 줄로 유지 */
        }

     

        .form-container {
            padding: 20px;
            border: 1px solid #ddd;
            margin-top: 20px;
        }

        .footer {
            text-align: center;
            margin-top: 20px;
            margin-right: 10px;
        }

      
    </style>
</head>

<body>

    <div class="header">
        문의 관리
    </div>

    <div class="content">
        <!-- 한 줄로 데이터를 표시 -->
        <table class="titleTable">
            <tr>
                <th>문의 유형</th>
                <td>예시</td>

                <th>제목</th>
                <td>예시 제목</td>

                <th>등록일</th>
                <td>2024-10-15</td>

                <th>아이디</th>
                <td>star</td>
            </tr>
        </table>
		<div class="form-container">
			문의사항이 보이는 공간입니다 
		</div>


        <!-- 답변 등록 폼 -->
        
        <form action="">
        <div class="form-container">
            <h5>답변 등록</h5>

            <div class="form-group">
                <label for="content">내용</label>
                <textarea id="content" class="form-control" rows="7" placeholder="내용을 입력하세요"></textarea>
            </div>

            <div class="footer">
                <input type="button" class="btn btn-danger btn-sm" value="등록" id="answer" name="answer">
                <input type="button" class="btn btn-success btn-sm" value="삭제" id="delete" name="delete">
                <input type="button" class="btn btn-light btn-sm" value="닫기" id="close" name="close">
            </div>
        </div>
        </form>
    </div>

</body>

</html>