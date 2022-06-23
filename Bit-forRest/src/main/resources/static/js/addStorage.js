const slidePage = document.querySelector(".slidepage");
const firstNextBts = document.querySelector(".nextBtn");
const prevBtnSec = document.querySelector(".prev-1");
const nextBtnSec = document.querySelector(".next-1");
const prevBtnThird = document.querySelector(".prev-2");
const nextBtnThird = document.querySelector(".next-2");
const prevBtnFourth = document.querySelector(".prev-3");
const nextBtnFourth = document.querySelector(".next-3"); //약관 관련
const prevBtnFifth = document.querySelector(".prev-4");
const nextBtnFifth = document.querySelector(".next-4");
const prevBtnSixth = document.querySelector(".prev-5");
const nextBtnSixth = document.querySelector(".submit");
const progressText =document.querySelectorAll(".step p");
const progressCheck =document.querySelectorAll(".step .check");
const bullet =document.querySelectorAll(".step .bullet");
let max=7;
let current=1;
let count = 0;

firstNextBts.addEventListener("click", function(){
    slidePage.style.marginLeft="-25%";
    bullet[current-1].classList.add("active");
    progressText[current-1].classList.add("active");
    progressCheck[current-1].classList.add("active");
    current+=1;
});

nextBtnSec.addEventListener("click", function(){
    slidePage.style.marginLeft="-50%";
    bullet[current-1].classList.add("active");
    progressText[current-1].classList.add("active");
    progressCheck[current-1].classList.add("active");
    current+=1;
});

nextBtnThird.addEventListener("click", function(){
    slidePage.style.marginLeft="-75%";
    bullet[current-1].classList.add("active");
    progressText[current-1].classList.add("active");
    progressCheck[current-1].classList.add("active");
    current+=1;
});

nextBtnFourth.addEventListener("click", function(){
    slidePage.style.marginLeft="-100%";
    bullet[current-1].classList.add("active");
    progressText[current-1].classList.add("active");
    progressCheck[current-1].classList.add("active");
    current+=1;
});

nextBtnFifth.addEventListener("click", function(){
    slidePage.style.marginLeft="-125%";
    bullet[current-1].classList.add("active");
    progressText[current-1].classList.add("active");
    progressCheck[current-1].classList.add("active");
    current+=1;
});


prevBtnSec.addEventListener("click", function(){
    slidePage.style.marginLeft="0%";
    bullet[current-2].classList.remove("active");
    progressText[current-2].classList.remove("active");
    progressCheck[current-2].classList.remove("active");
    current-=1;
});

prevBtnThird.addEventListener("click", function(){
    slidePage.style.marginLeft="-25%";
    bullet[current-2].classList.remove("active");
    progressText[current-2].classList.remove("active");
    progressCheck[current-2].classList.remove("active");
    current-=1;
});

prevBtnFourth.addEventListener("click", function(){
    slidePage.style.marginLeft="-50%";
    bullet[current-2].classList.remove("active");
    progressText[current-2].classList.remove("active");
    progressCheck[current-2].classList.remove("active");
    current-=1;
});

prevBtnFifth.addEventListener("click", function(){
    slidePage.style.marginLeft="-75%";
    bullet[current-2].classList.remove("active");
    progressText[current-2].classList.remove("active");
    progressCheck[current-2].classList.remove("active");
    current-=1;
});

prevBtnSixth.addEventListener("click", function(){
    slidePage.style.marginLeft="-100%";
    bullet[current-2].classList.remove("active");
    progressText[current-2].classList.remove("active");
    progressCheck[current-2].classList.remove("active");
    current-=1;
});

 $(function(){
		$(".submit").on("click", function(){
			//console.log("제발"+$('.couponValue:selected').attr('name'));
			if(validationCheck()){
				return;
			};
			
			submitCouponNumber();
			combineAddr();
			combinePhone();
			combineAccount();
			
		
		
			 
			request_pay();
		 })
	 }) 


//////////주소등록/////////////
 function pickup_execDaumPostcode() {
	        new daum.Postcode({
	            oncomplete: function(data) {
	                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

	                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
	                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	                var addr = ''; // 주소 변수
	                var extraAddr = ''; // 참고항목 변수

	                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
	                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
	                    addr = data.roadAddress;
	                } else { // 사용자가 지번 주소를 선택했을 경우(J)
	                    addr = data.jibunAddress;
	                }

	                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
	                if(data.userSelectedType === 'R'){
	                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
	                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
	                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
	                        extraAddr += data.bname;
	                    }
	                    // 건물명이 있고, 공동주택일 경우 추가한다.
	                    if(data.buildingName !== '' && data.apartment === 'Y'){
	                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	                    }
	                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
	                    if(extraAddr !== ''){
	                        extraAddr = ' (' + extraAddr + ')';
	                    }
	                    // 조합된 참고항목을 해당 필드에 넣는다.
	                    document.getElementById("pickup_extraAddress").value = extraAddr;
	                
	                } else {
	                    document.getElementById("pickup_extraAddress").value = '';
	                }

	                // 우편번호와 주소 정보를 해당 필드에 넣는다.
	                document.getElementById('pickup_postcode').value = data.zonecode;
	                document.getElementById("pickup_address").value = addr;
	                // 커서를 상세주소 필드로 이동한다.
	                document.getElementById("pickup_detailAddress").focus();
	            }
	        }).open();
	    }

	 function divy_execDaumPostcode() {
	        new daum.Postcode({
	            oncomplete: function(data) {
	                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

	                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
	                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	                var addr = ''; // 주소 변수
	                var extraAddr = ''; // 참고항목 변수

	                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
	                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
	                    addr = data.roadAddress;
	                } else { // 사용자가 지번 주소를 선택했을 경우(J)
	                    addr = data.jibunAddress;
	                }

	                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
	                if(data.userSelectedType === 'R'){
	                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
	                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
	                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
	                        extraAddr += data.bname;
	                    }
	                    // 건물명이 있고, 공동주택일 경우 추가한다.
	                    if(data.buildingName !== '' && data.apartment === 'Y'){
	                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	                    }
	                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
	                    if(extraAddr !== ''){
	                        extraAddr = ' (' + extraAddr + ')';
	                    }
	                    // 조합된 참고항목을 해당 필드에 넣는다.
	                    document.getElementById("divy_extraAddress").value = extraAddr;
	                
	                } else {
	                    document.getElementById("divy_extraAddress").value = '';
	                }

	                // 우편번호와 주소 정보를 해당 필드에 넣는다.
	                document.getElementById('divy_postcode').value = data.zonecode;
	                document.getElementById("divy_address").value = addr;
	                // 커서를 상세주소 필드로 이동한다.
	                document.getElementById("divy_detailAddress").focus();
	            }
	        }).open();
	    }
	    
	    
 //csv를 넣어 주소를 하나로 합치는 함수
	 
	 function combineAddr(){
		 
		 //디버깅
		 //alert($("input[name='postcode']").val())
		 
		 var pickupAddrValue = "";
		 
		 var pickupAddrValue = $("input[name='pickupPostcode']").val() + "/"  + $("input[name='pickupBaseAddress']").val() + "/" 
			+  $("input[name='pickupDetailAddress']").val() + "/" +  $("input[name='pickupExtraAddress']").val();
		 
		 $('.pickupAddress').val(pickupAddrValue);
		
		//alert(pickupAddrValue);
		 
		 var divyAddrValue = "";
		 
		 var divyAddrValue = $("input[name='divyPostcode']").val() + "/"  + $("input[name='divyBaseAddress']").val() + "/" 
			+  $("input[name='divyDetailAddress']").val() + "/" +  $("input[name='divyExtraAddress']").val();
		 
		 $('.divyAddress').val(divyAddrValue);
		
		// alert(divyAddrValue);
		 
	 }
	 
	 //csv를 넣어 전화번호를 합치는 함수
	 function combinePhone(){
		 
		 //디버깅
		 
		 var receiverPhone = "";
		 
		 var receiverPhone = $("input[name='receiverPhone1']").val() + "/"  + $("input[name='receiverPhone2']").val() + "/" 
			+  $("input[name='receiverPhone3']").val();
		 
		 $('.receiverPhone').val(receiverPhone);
			
		
		
		// alert(receiverPhone); 
	 }
	 
	 //csv를넣어 계좌를 합침
	 function combineAccount(){
		 
		 //디버깅
		 
		 var account = "";
		 
		 var account = $(".accountBank option:selected").val() + "/"  + $("input[name='accountNumber']").val()
		 
		$('.account').val(account);
		// alert(account); 
	 }
	 
	//쿠폰넘버를 넘겨줌
	
	 function submitCouponNumber(){
		//undefined일경우 couponeNo를 0으로넘김
		if($(".couponValue:selected").attr("data-coNo")==undefined){
			  $(".subCouponNo").val(0);
		} else{
		    $(".subCouponNo").val($(".couponValue:selected").attr("data-coNo"));
		}
		
	}
	 
//체크했을때 1 안했을때 0을 보냄
	$('#input_check').on('change', function(){
   	this.value = this.checked ? 1 : 0;
  // alert(this.value);
	}).change();
	


	
	
function request_pay(){
		
		IMP.init("imp88340030");
			
		 IMP.request_pay({
		    	pg : "html5_inicis", 
		        pay_method : 'card',
		        merchant_uid : $("input[name='tranNo']").val(),//주문번호
		        name : $("input[name='prodName']").val(),
		        amount :  $("input[name='resultPrice']").val(),
		    }, function(rsp) {
    	console.log(rsp);
    	$.ajax({

        	type : "POST",
        	url : "/payment/json/verifyIamport?imp_uid=" + rsp.imp_uid 
        	
        }).done(function(data) {
        	
        	console.log(data);
        	
        	if(rsp.paid_amount == data.response.amount){
	        	alert("결제 및 결제검증완료");
	        		$('form').attr('method', 'POST').attr('action', '/storage/addStorage?paymentNo='+rsp.imp_uid).submit()
	        	
        	} else {
        		alert("결제 실패");
        	}
        });
    });
		
		
	}
	
	
	
	
	// 가로 세로 높이 기간 수량에 따른 가격변화를 실시간으로 표시
	$(document).ready(function() {
 
$("#resv_width").on("propertychange change paste input", function() {
 
    var resv_width= fnReplace($("#resv_width").val());
    var resv_length = fnReplace($("#resv_length").val());
    var resv_height = fnReplace($("#resv_height").val());
    var resv_period = fnReplace($("#resv_period").val());
    var resv_quantity = fnReplace($("#resv_quantity").val());
    
    $("#resv_originPrice").val( Math.round(resv_width *  resv_length * resv_height  * resv_period * resv_quantity * 0.04));
    $("#resv_resultPrice").val(Math.round(resv_width *  resv_length * resv_height  * resv_period * resv_quantity * 0.04));
});
 
$("#resv_length").on("propertychange change paste input", function() {
 
    var resv_width= fnReplace($("#resv_width").val());
    var resv_length = fnReplace($("#resv_length").val());
    var resv_height = fnReplace($("#resv_height").val());
    var resv_period = fnReplace($("#resv_period").val());
    var resv_quantity = fnReplace($("#resv_quantity").val());
    
    $("#resv_originPrice").val( Math.round(resv_width *  resv_length * resv_height  * resv_period * resv_quantity * 0.04));
    $("#resv_resultPrice").val(Math.round(resv_width *  resv_length * resv_height  * resv_period * resv_quantity * 0.04));
});
 
$("#resv_height").on("propertychange change paste input", function() {
 
    var resv_width= fnReplace($("#resv_width").val());
    var resv_length = fnReplace($("#resv_length").val());
    var resv_height = fnReplace($("#resv_height").val());	
    var resv_period = fnReplace($("#resv_period").val());
    var resv_quantity = fnReplace($("#resv_quantity").val());
    
    $("#resv_originPrice").val( Math.round(resv_width *  resv_length * resv_height  * resv_period * resv_quantity * 0.04));
    $("#resv_resultPrice").val(Math.round(resv_width *  resv_length * resv_height  * resv_period * resv_quantity * 0.04));
});
 
$("#resv_period").on("propertychange change paste input", function() {
	
    var resv_width= fnReplace($("#resv_width").val());
    var resv_length = fnReplace($("#resv_length").val());
    var resv_height = fnReplace($("#resv_height").val());
    var resv_period = fnReplace($("#resv_period").val());
    var resv_quantity = fnReplace($("#resv_quantity").val());
   
    $("#resv_originPrice").val( Math.round(resv_width *  resv_length * resv_height  * resv_period * resv_quantity * 0.04));
    $("#resv_resultPrice").val(Math.round(resv_width *  resv_length * resv_height  * resv_period * resv_quantity * 0.04));
});
 
$("#resv_quantity").on("propertychange change paste input", function() {
    var resv_width= fnReplace($("#resv_width").val());
    var resv_length = fnReplace($("#resv_length").val());
    var resv_height = fnReplace($("#resv_height").val());
    var resv_period = fnReplace($("#resv_period").val());
    var resv_quantity = fnReplace($("#resv_quantity").val());
    console.log(resv_width *  resv_length * resv_height * resv_quantity * 0.1);
    $("#resv_originPrice").val( Math.round(resv_width *  resv_length * resv_height  * resv_period * resv_quantity * 0.04));
    $("#resv_resultPrice").val(Math.round(resv_width *  resv_length * resv_height  * resv_period * resv_quantity * 0.04));
});
 
});
	
	function fnReplace(val) {
	    var ret = 0;
	    if(typeof val != "undefined" && val != null && val != ""){
	        ret = Number(val.replace(/,/gi,''));
	    }
	    return ret;        
	}
	
	
	//쿠폰선택시 총결제금액만 변경
 $(function(){
    	
  
    	$(".coupon").on("click",function(){
    		
    		if($(".coupon option:selected").val()!=="-- 선택 --"){    			
    			       		    			   
    	    	    var originPrice =    $("#resv_originPrice").val();
    	    	    console.log(originPrice);
    	    		
    	    	   
    	    	    //금액할인 퍼센트할인에 따라 할인금액을 계산
    	    	    if($(".coupon option:selected").val()<1){
    	    	    	
    	    	    	 var discountPrice = Math.round(originPrice * $(".coupon option:selected").val());
						console.log("discountPrice:"+ discountPrice);
    	    	    	
    	    	    } else{
    	    	    	
    	    	    	var discountPrice =  $(".coupon option:selected").val().replace(".0","")
    	    	    	console.log("discountPrice:"+discountPrice);
    	    	    	
    	    	    }
    	    	    
    	    	    	$('#discountPrice').val(discountPrice);
    	    	    	
	    	    	    var resultPrice = originPrice - discountPrice;
	    	    	    console.log(resultPrice);
	    	    	    
    	    	    //계산값 텍스트 대체 , 할인금액 >=계산금액일때 계산불가
    	    	    if(resultPrice<=0){
							 $('#resv_resultPrice').val("양심어디....? ㅡ.ㅡ;");
					} else{
						
    	    	    	$('#resv_resultPrice').val(resultPrice);
						
					}
						
    		} else {
    			
    			 $('#discountPrice').val(0);
    			 $('#resv_resultPrice').val($("#resv_originPrice").val());
    		}
    	 
    	})
    	 
    })
    
	 
//////////이미지 image/////////

$(function(){
	

	var initWidth = $(".img-preview-big")[0].offsetWidth;
	$(".img-preview-big").css("height", initWidth + "px");

	window.onresize = function(event) {
	  var initWidth = $(".img-preview-big")[0].offsetWidth;
	  $(".img-preview-big").css("height", initWidth + "px");
	};

	});

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
	 	if ($(".img-preview-small").length <10) {
		console.log('test');
		console.log(count);
		 $("input[id='file_"+(count)+"']").click();
		 console.log($("input[id='file_"+(count)+"']"));
		  } 
	})
	
	function fn_deleteFile(obj){
		obj.remove();
	}

	function change() {
		console.log(count);
	  var input = $("input[id='file_"+(count)+"']")[0];
	  if (input.files && input.files[0]) {
	    var reader = new FileReader();
	    reader.onload = function(e) {
	      $(".img-preview-big img")[0].src = e.target.result;
	      $(".img-preview-small img").each(function() {
	        $(this).removeClass("img-small-selected"); /*selected는 작은 사진 검은색 테두리 관련된 것 새로운 사진이 올라오면 기존에 click되어 있던 테두리가 없어짐*/
	      })
	      var newImg = '<div class="img-preview-small">' +
	          '<img src="' + e.target.result + '" class="img-small-selected">' +'<input type="hidden" name="count" value="'+(count)+'">'+
	          '</div>';  /*새로운 사진에 테두리가 생기고 기존 테두리 없어짐*/
	      var str2 = "<p><input type='file'  id='file_"+(count+1)+"'  name='uploadFile' style='display:none' onchange='change()'></p>";
	      count++;
	      $("#fileDiv").append(str2);
	      $(".img-holder").append(newImg);/*image holder div에 새 이미지 추가 */
	      var left = $('.img-preview-operate').width();
	      $('.img-preview-operate').scrollLeft(left);/*뭔지 모르겠음; */
	    }
	    reader.readAsDataURL(input.files[0]);/*선택된 파일만 읽겠다?; */
	  }
	}
	
	$(document).on('mouseenter mouseleave', '.img-preview-small img', function(ev) {
	  var mouse_is_inside = ev.type === 'mouseenter';
	  if (mouse_is_inside) {
	    $(this)[0].classList.add("img-small-active");
	  } else {
	    if (!$(this)[0].classList.contains("img-small-selected"));
	    $(this)[0].classList.remove("img-small-active");
	  }
	});/*테두리 관련 옵션 */
	
	
	$(document).on('click', '.img-preview-small img', function(ev) {
	  $(".img-preview-small img").each(function() {
	    $(this).removeClass("img-small-selected");
	  })
	  $(this).addClass("img-small-selected");
	  $(".img-preview-big img")[0].src = $(this)[0].src;
	});
	
	
	$(".img-delete").click(function() {
		var value=  $(".img-small-selected").parent().find("input").val()
		console.log(value);
		fn_deleteFile($("#fileDiv").find("input[id='file_"+(value)+"']"));
		console.log($("#fileDiv").find("input[id='file_"+(value)+"']"))
		
	  $(".img-small-selected")[0].parentElement.remove();/*관련된 element를 지워버리겠다? */
	  if ($(".img-preview-small").length > 0) {
	    $(".img-preview-small img")[0].classList.add("img-small-selected");
	    $(".img-preview-big img")[0].src = $(".img-preview-small img")[0].src;
	    $('.img-preview-operate').scrollLeft(0);
	  } else {
	    $(".img-preview-big img")[0].src = "https://uploader-assets.s3.ap-south-1.amazonaws.com/codepen-default-placeholder.png";
	    $(".img-delete").css("display", "none");
	  }
	})


/////////////////value 유효성검사//////////////////////

function validationCheck() {
	
	//물품관련 정보
	var prodName = $("input[name='prodName']").val();
	var width = $("input[name='width']").val();
	var length = $("input[name='length']").val();
	var height = $("input[name='height']").val();
	var prodDetail = $("input[name='prodDetail']").val();
	var prodQuantity = $("input[name='prodQuantity']").val();
	
	//대여관련정보
	var rentalPrice = $("input[name='rentalPrice']").val();
	var accountNumber = $("input[name='accountNumber']").val();
	
	//구매자관련 정보
	var receiverName = $("input[name='receiverName']").val();
	
	var receiverPhone1 = $("input[name='receiverPhone1']").val();
	var receiverPhone2 = $("input[name='receiverPhone2']").val();
	var receiverPhone3 = $("input[name='receiverPhone3']").val();
	
	
	//주소관련정보
	var pickupPostcode = $("input[name='pickupPostcode']").val();
	var pickupDetailAddress = $("input[name='pickupDetailAddress']").val();
	
	var divyPostcode = $("input[name='divyPostcode']").val();
	var divyDetailAddress = $("input[name='divyDetailAddress']").val();
	
	
	//가격관련 정보
	var resultPrice = $("input[name='resultPrice']").val();
	
	if(prodName == null || prodName.length > 21){
				alert("물품명은 1~20자 사이로 입력하세요");
				return;
			}
			
	if(width == null || width < 1){
				alert("물품크기를 정확하게 입력하세요");
				return;
			}
			
	if(length == null || length < 1){
				alert("물품크기를 정확하게 입력하세요");
				return;
			}
			
	if(height == null || height < 1){
				alert("물품크기를 정확하게 입력하세요");
				return;
			}
			
	if(prodDetail == null || prodDetail.length > 100){
				alert("상세정보는 1~100자 사이로 입력하세요");
				return;
			}
   if(prodQuantity == null ||prodQuantity < 1){
				alert("물품수량을 올바르게 입력하세요");
				return;
			}		

			
  /* if(rentalPrice == null || rentalPrice < 1){
				alert("1~20자 사이로 입력하세요");
				return;
			}		
			
   if(accountNumber == null || prodName.length > 21){
				alert("1~20자 사이로 입력하세요");
				return;
			}			*/
	
   if(receiverName == null || receiverName.length < 1){
				alert("주문자이름을 입력해주세요");
				return;
			}		
				
   if(receiverPhone1 == null || receiverPhone1.length < 1){
				alert("연락처를 입력하세요");
				return;
			}	
			
   if(receiverPhone2 == null || receiverPhone2.length < 1){
				alert("연락처를 입력하세요");
				return;
			}		
			
	if(receiverPhone3 == null || receiverPhone3.length < 1){
				alert("연락처를 입력하세요");
				return;
			}		
			
	if(pickupPostcode == null || pickupPostcode.length < 1){
				alert("주소를 입력해주세요");
				return;
			}	
			
	if(pickupDetailAddress == null || pickupDetailAddress.length < 1){
				alert("주소를 입력해주세요");
				return;
			}				

	if(divyPostcode == null || divyPostcode.length < 1){
				alert("주소를 입력해주세요");
				return;
			}	
			
	if(divyDetailAddress == null || divyDetailAddress.length < 1){
				alert("주소를 입력해주세요");
				return;
			}		
			
	if(resultPrice == null || resultPrice < 1){
				alert("최소결제금액보다 적습니다");
				return;
			}
	
}

