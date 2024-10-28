<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <h3>    <a href="main.jsp">관리자 명     </a>  </h3>
      <div class="accordion" id="accordionSidebar">
         <div class="accordion-item" >
            <h2 class="accordion-header" id="headingOne">
               <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" 
                  data-bs-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne">상품 관리</button>
            </h2>
            <div id="collapseOne" class="accordion-collapse collapse" aria-labelledby="headingOne"
               data-bs-parent="#accordionSidebar">
               <div class="accordion-body">
                  <a href="#">상품 조회/수정</a>
                  <a href="#">상품 등록</a>
               </div>
            </div>
         </div>

         <div class="accordion-item">
            <h2 class="accordion-header" id="headingTwo">
               <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                  data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">판매 관리</button>
            </h2>
            <div id="collapseTwo" class="accordion-collapse collapse" aria-labelledby="headingTwo"
               data-bs-parent="#accordionSidebar">
               <div class="accordion-body">
                  <a href="#">판매 리스트</a>
               </div>
            </div>
         </div>

         <div class="accordion-item">
            <h2 class="accordion-header" id="headingFour">
               <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                  data-bs-target="#collapseFour" aria-expanded="false" aria-controls="collapseFour">문의 관리</button>
            </h2>
            <div id="collapseFour" class="accordion-collapse collapse" aria-labelledby="headingFour"
               data-bs-parent="#accordionSidebar">
               <div class="accordion-body">
                  <a href="qna.jsp">문의 리스트</a>
               </div>
            </div>
         </div>

         <div class="accordion-item">
            <h2 class="accordion-header" id="headingFive">
               <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                  data-bs-target="#collapseFive" aria-expanded="false" aria-controls="collapseFive">리뷰 관리</button>
            </h2>
            <div id="collapseFive" class="accordion-collapse collapse" aria-labelledby="headingFive"
               data-bs-parent="#accordionSidebar">
               <div class="accordion-body">
                  <a href="review.jsp">리뷰 리스트</a>
               </div>
            </div>
         </div>

         <div class="accordion-item">
            <h2 class="accordion-header" id="headingSix">
               <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                  data-bs-target="#collapseSix" aria-expanded="false" aria-controls="collapseSix">공지사항 관리</button>
            </h2>
            <div id="collapseSix" class="accordion-collapse collapse" aria-labelledby="headingSix"
               data-bs-parent="#accordionSidebar">
               <div class="accordion-body">
                  <a href="#">공지사항 리스트</a>
                  <a href="#">공지사항 등록</a>
               </div>
            </div>
         </div>

         <div class="accordion-item">
            <h2 class="accordion-header" id="headingSeven">
               <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                  data-bs-target="#collapseSeven" aria-expanded="false" aria-controls="collapseSeven">회원 관리</button>
            </h2>
            <div id="collapseSeven" class="accordion-collapse collapse" aria-labelledby="headingSeven"
               data-bs-parent="#accordionSidebar">
               <div class="accordion-body">
                  <a href="#">회원 목록</a>
               </div>
            </div>
         </div>
      </div>