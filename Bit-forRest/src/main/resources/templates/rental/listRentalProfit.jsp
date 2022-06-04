<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    
<!DOCTYPE html>
<html lang="en">

<head>
  
	<!-- 참조 : http://getbootstrap.com/css/   참조 -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
	<!--  ///////////////////////// Bootstrap, jQuery CDN ////////////////////////// -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" >
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" >
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>
	
	
	<!-- Bootstrap Dropdown Hover CSS -->
   <link href="/css/bootstrap-dropdownhover.min.css" rel="stylesheet">
    <!-- Bootstrap Dropdown Hover JS -->
   <script src="/javascript/bootstrap-dropdownhover.min.js"></script>
   
   
   <!-- jQuery UI toolTip 사용 CSS-->
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  
   <link rel="stylesheet" href="/css/style.css">    
    <link rel="stylesheet" type="text/css" href="/css/animate.css" />
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="/css/responsive.css" />
 
  
  <!-- jQuery UI toolTip 사용 JS-->
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

</head>

<body>
<!-- 툴바 include -->
<jsp:include page="/WEB-INF/views/main/toolbar.jsp" />


<!--  화면구성 div Start /////////////////////////////////////-->
<div class="container list">
	<div class="page-header text-info">
		       <h3>대여수익확인</h3>
		</div>
		
				 <!-- table 위쪽 검색 Start /////////////////////////////////////-->
	    <div class="row">
	    
		    <div class="col-md-6 text-left">
		    	<p class="text-primary">
		    		전체  ${resultPage.totalCount } 건수, 현재 ${resultPage.currentPage}  페이지 
		    	</p>
		    </div>
	    	
		</div>
		<!-- table 위쪽 검색 Start /////////////////////////////////////-->
		
	 <form class="form-inline" name="detailForm">
	 <input type="hidden" id="currentPage" name="currentPage" value=""/>
		 <!--  table Start /////////////////////////////////////-->
      <table class="table table-hover table-striped">
	       <thead>
	          <tr class="tableHeader">
	            <th align="center">No</th>
	            <th align="center" >회원ID</th>
	            <th align="center">회원명</th>	      
	            <th align="center">전화번호</th>
	            <th align="center">요청사항</th>
	            <th align="center">배송현황</th>				
	          </tr>
	        </thead>
	        
	        <tbody>
	        
	     <c:set var="i" value="0" /> 
	        
	 <c:forEach var="rental" items="${list}">
		<c:set var="i" value="${ i+1 }" />
		<tr class="ct_list_pop">
		
			<td align="center">
			<span>${ i }</span>
			</td>
		
			
			<td align="center">
				<span>렌탈 유저 아이디</span>
			</td>
			
			<td align="center">렌탈 대여자 이름 ${rental.tranNo}</td>
			
			
			<td align="center">렌탈 대여자 휴대폰</td>		
		
			<td align="center">렌탈 대여자 요청사항</td>
			
			
			
			 <%--  <c:choose>
		         <c:when test="${sessionScope.user.userId == 'admin'}">
		              <c:choose>
							<c:when test="${purchase.tranCode.equals('100')}">					
								<td align="center">
								구매완료
								<a href="/purchase/updateTranCode?prodNo=${product.prodNo}&tranCode=${product.proTranCode}&currentPage=${resultPage.currentPage}">배송하기</a>
								</td>
							</c:when>
							<c:when test="${purchase.tranCode.equals('200')}">
								<td align="center">
								배송중
								<a href="/purchase/updateTranCode?prodNo=${product.prodNo }&tranCode=${product.proTranCode}&currentPage=${resultPage.currentPage}">배송완료</a>
								</td>
								
							</c:when>
							<c:otherwise>
								<td align="center">배송완료</td>
							</c:otherwise>
					</c:choose>
		         </c:when>
		        
		         <c:otherwise>
		          <c:choose>
							<c:when test="${purchase.tranCode.equals('100')}">					
								<td align="center">
								구매완료							
								</td>
							</c:when>
							<c:when test="${purchase.tranCode.equals('200')}">
								<td align="center">
								배송중								
								</td>
								
							</c:when>
							<c:otherwise>
								<td align="center">배송완료</td>
							</c:otherwise>
					</c:choose>
		         </c:otherwise>
	    	  </c:choose>
 --%>
			
		</tr>
		
	</c:forEach>	
	       
	        </tbody>

      </table>
		<!--  table End /////////////////////////////////////-->
	</form>
</div>
<!--  화면구성 div End /////////////////////////////////////-->



</body>

</html>