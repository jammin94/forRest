<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%-- 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
     --%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
  
   
    <link rel="stylesheet" href="/css/addRentalReview.css">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
        integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous" />  
        
    <link rel="stylesheet" href="/css/style.css">    
    <link rel="stylesheet" type="text/css" href="/css/animate.css" />
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.css" />
	<link rel="stylesheet" type="text/css" href="/css/responsive.css" />

	
	<!-- favicon links -->
	<link rel="shortcut icon" type="image/png" href="/images/header/favicon.png" />    
        
        
        
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script src="addRentalReview.js" defer></script>
	<script>
	 //이미지업로드 부분만 jquery 처리
	$(function(){
		
	  var initWidth = $(".img-preview-big")[0].offsetWidth;
	$(".img-preview-big").css("height", initWidth + "px");

	window.onresize = function(event) {
	  var initWidth = $(".img-preview-big")[0].offsetWidth;
	  $(".img-preview-big").css("height", initWidth + "px");
	};

	$(".img-upload-handler").on('mouseenter mouseleave', '.img-preview-big', function(ev) {
	  var mouse_is_inside = ev.type === 'mouseenter';
	  if ($(".img-preview-small").length > 0) {
	    if (mouse_is_inside) {
	      $(".img-delete").css("display", "flex");
	    } else {
	      $(".img-delete").css("display", "none");
	    }
	  } else {
	    $(".img-delete").css("display", "none");
	  }
	});

	$(".img-add-more").click(function() {
		console.log("click test");
		
	 if ($(".img-preview-small").length <2) {
		 $("input[type='file']").click();
		  } 
	  
	  if ($(".img-preview-small").length >1) {
		   	alert("최대2장까지 추가할 수 있습니다 ");
		  } 
	})

	$("input[type='file']").change(function() {
	  var input = $("input[type='file']")[0];
	  if (input.files && input.files[0]) {
	    var reader = new FileReader();
	    reader.onload = function(e) {
	      $(".img-preview-big img")[0].src = e.target.result;
	      $(".img-preview-small img").each(function() {
	        $(this).removeClass("img-small-selected");
	      })
	      var newImg = '<div class="img-preview-small">' +
	          '<img src="' + e.target.result + '" class="img-small-selected">' +
	          '</div>';
	      $(".img-holder").append(newImg);
	      var left = $('.img-preview-operate').width();
	      $('.img-preview-operate').scrollLeft(left);
	    }
	    reader.readAsDataURL(input.files[0]);
	  }
	});

	// $(".img-preview-small").hover(function() {
//	 	 console.log("Deepak");	
	// }, function() {
//	 	 console.log("Chandwani");	
	// })

	$(document).on('mouseenter mouseleave', '.img-preview-small img', function(ev) {
	  var mouse_is_inside = ev.type === 'mouseenter';
	  if (mouse_is_inside) {
	    $(this)[0].classList.add("img-small-active");
	  } else {
	    if (!$(this)[0].classList.contains("img-small-selected"));
	    $(this)[0].classList.remove("img-small-active");
	  }
	});

	$(document).on('click', '.img-preview-small img', function(ev) {
	  $(".img-preview-small img").each(function() {
	    $(this).removeClass("img-small-selected");
	  })
	  $(this).addClass("img-small-selected");
	  $(".img-preview-big img")[0].src = $(this)[0].src;
	});

	$(".img-delete").click(function() {
	  $(".img-small-selected")[0].parentElement.remove();
	  if ($(".img-preview-small").length > 0) {
	    $(".img-preview-small img")[0].classList.add("img-small-selected");
	    $(".img-preview-big img")[0].src = $(".img-preview-small img")[0].src;
	    $('.img-preview-operate').scrollLeft(0);
	  } else {
	    $(".img-preview-big img")[0].src = "https://uploader-assets.s3.ap-south-1.amazonaws.com/codepen-default-placeholder.png";
	    $(".img-delete").css("display", "none");
	  }
	})
	})
	  
	</script>
</head>



<body>

<!-- 툴바 include -->
<jsp:include page="/WEB-INF/views/main/toolbar.jsp" />

   <div class="container">
<p>
  리뷰쓰기 
</p>
<form id="review-form" action="/rentalReview/addRentalReview" method="post">
  <h2>상품이미지 / 간략상세정보 </h2>
  <div id="rating">
 

<div class="main-wrapper">
  <div class="img-upload-plugin">
                    <div class="img-upload-handler">
                      <div class="img-preview-big">
                        <img src="https://uploader-assets.s3.ap-south-1.amazonaws.com/codepen-default-placeholder.png">
                        <div class="img-delete">
                          <img src="https://uploader-assets.s3.ap-south-1.amazonaws.com/codepen-delete-icon.png">
                        </div>
                      </div>
                    </div>
                    <div class="img-preview-operate">
                      <div class="img-holder" style="display: inline-block; padding: 2px;"></div>
                      <button type="button" class="img-add-more">
                        <img src="https://uploader-assets.s3.ap-south-1.amazonaws.com/codepen-add-more-btn.png">
                      </button>
                    </div>
                    <input type="file" name="img-upload-input" style="display:none">
                  </div>
</div>

  </div>
  <span id="starsInfo" class="help-block">
    상품에 대한 별점을 남겨주세요 !
  </span>
    <div class="star-rating">
  <input type="radio" id="5-stars" name="reviewScore" value="5" />
  <label for="5-stars" class="star">&#9733;</label>
  <input type="radio" id="4-stars" name="reviewScore" value="4" />
  <label for="4-stars" class="star">&#9733;</label>
  <input type="radio" id="3-stars" name="reviewScore" value="3" />
  <label for="3-stars" class="star">&#9733;</label>
  <input type="radio" id="2-stars" name="reviewScore" value="2" />
  <label for="2-stars" class="star">&#9733;</label>
  <input type="radio" id="1-star" name="reviewScore" value="1" />
  <label for="1-star" class="star">&#9733;</label>
</div>
  <div class="form-group">
   <!--  <label class="control-label" for="review">Your Review:</label> -->
    <textarea class="form-control" rows="10" placeholder="Your Reivew" name="reviewDetail" id="review"></textarea>
    <span id="reviewInfo" class="help-block pull-right">
    
    </span>
  </div>
  <button>등록하기 </button>
  </form>
<div id="review-container">
</div>
</div>
</body>

</html>