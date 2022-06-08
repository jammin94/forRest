
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
		 $("input[type='file']").click();
	});


/* base64로 이미지 저장시 필요함. 근데 우리는 다른데..? */
	$("input[type='file']").change(function() {
	  var input = $("input[type='file']")[0].files;
	  console.log(input);
	   if(input){
	  	for(var num=0; num<input.length; num++){
	    var reader = new FileReader();
	    reader.onload = function(e) {
			console.log("e.target.result : "+e.target.result);
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
	      reader.readAsDataURL(input[num]);
	     }
	    }
	});
	/*
	$("input[type='file']").change(function() {
	  var input = $("input[type='file']")[0].files;
	  console.log(input);
	   if(input){
	  	for(var num=0; num<input.length; num++){
		console.log("현재 num = "+num);
		console.log(input[num].name);
	    var reader = new FileReader();
	    reader.onload = function(e) {
			console.log("e.target.result : "+e.target.result);
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
	      reader.readAsDataURL(input[num]);
	     }
	    }
	});
	*/

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