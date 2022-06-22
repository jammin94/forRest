let count = 0;


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
	      var str2 = "<p><input type='file'  id='file_"+(count+1)+"'  name='uploadFiles' style='display:none' onchange='change()'></p>";
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