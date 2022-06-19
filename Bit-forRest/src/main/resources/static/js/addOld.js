const slidePage = document.querySelector(".slidepage");
const firstNextBts = document.querySelector(".nextBtn");
const prevBtnSec = document.querySelector(".prev-1");
const submit = document.querySelector(".submit");
const progressText =document.querySelectorAll(".step p");
const progressCheck =document.querySelectorAll(".step .check");
const bullet =document.querySelectorAll(".step .bullet");
let max=7;
let current=1;

firstNextBts.addEventListener("click", function(){
    slidePage.style.marginLeft="-25%";
    bullet[current-1].classList.add("active");
    progressText[current-1].classList.add("active");
    current+=1;
});


prevBtnSec.addEventListener("click", function(){
    slidePage.style.marginLeft="0%";
    bullet[current-2].classList.remove("active");
    progressText[current-2].classList.remove("active");
    current-=1;
});



//////////이미지 image/////////

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
	 	if ($(".img-preview-small").length <10) {
		console.log('test');
		 $("input[type='file']").click();
		  } 
	})


$("input[type='file']").change(function() {
  var input = $("input[type='file']")[0];
  if (input.files && input.files[0]) {
    var reader = new FileReader();
    reader.onload = function(e) {
      $(".img-preview-big img")[0].src = e.target.result;
      $(".img-preview-small img").each(function() {
        $(this).removeClass("img-small-selected"); /*selected는 작은 사진 검은색 테두리 관련된 것 새로운 사진이 올라오면 기존에 click되어 있던 테두리가 없어짐*/
      })
      var newImg = '<div class="img-preview-small">' +
          '<img src="' + e.target.result + '" class="img-small-selected"></p><input type="text" name="count" value="'+count+">" +
          '</div>';  /*새로운 사진에 테두리가 생기고 기존 테두리 없어짐*/
      $("#fileDiv").append(str);
      $(".img-holder").append(newImg);/*image holder div에 새 이미지 추가 */
      var left = $('.img-preview-operate').width();
      $('.img-preview-operate').scrollLeft(left);/*뭔지 모르겠음; */
    }
    reader.readAsDataURL(input.files[0]);/*선택된 파일만 읽겠다?; */
  }

});

// $(".img-preview-small").hover(function() {
// 	 console.log("Deepak");	
// }, function() {
// 	 console.log("Chandwani");	
// })

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



})