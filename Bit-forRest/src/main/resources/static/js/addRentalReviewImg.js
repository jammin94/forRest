 //이미지업로드
  
//$ = jquery; 
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
      console.log('asdfasdf');
    } else {
      $(".img-delete").css("display", "none");
      console.log('111212121');
    }
  } else {
    $(".img-delete").css("display", "none");
  }
});


$(".img-add-more").click(function() {
	console.log("click test");
  $("input[type='file']").click();
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
  