/*
function starsReducer(state, action) {
    switch (action.type) {
      case 'HOVER_STAR': {
        return {
          starsHover: action.value,
          starsSet: state.starsSet
        }
      }
      case 'CLICK_STAR': {
        return {
          starsHover: state.starsHover,
          starsSet: action.value
        }
      }
        break;
      default:
        return state
    }
  }

  var StarContainer = document.getElementById('rating');
  var StarComponents = StarContainer.children;

  var state = {
    starsHover: 0,
    starsSet: 4
  }

  function render(value) {
    for(var i = 0; i < StarComponents.length; i++) {
      StarComponents[i].style.fill = i < value ? '#f39c12' : '#808080'
    }
  }

  for (var i=0; i < StarComponents.length; i++) {
    StarComponents[i].addEventListener('mouseenter', function() {
      state = starsReducer(state, {
        type: 'HOVER_STAR',
        value: this.id
      })
      render(state.starsHover);
    })

    StarComponents[i].addEventListener('click', function() {
      state = starsReducer(state, {
        type: 'CLICK_STAR',
        value: this.id
      })
      render(state.starsHover);
    })
  }

  StarContainer.addEventListener('mouseleave', function() {
    render(state.starsSet);
  })

  var review = document.getElementById('review');
  var remaining = document.getElementById('remaining');
  review.addEventListener('input', function(e) {
    review.value = (e.target.value.slice(0,999));
    remaining.innerHTML = (999-e.target.value.length);
  })

  var form = document.getElementById("review-form")

  form.addEventListener('submit', function(e) {
    e.preventDefault();
    let post = {
      stars: state.starsSet,
      review: form['review'].value,
      name: form['name'].value,
      city: form['city'].value,
      email: form['email'].value
    }

    console.log(post)
  })

  document.getElementById('submit').addEventListener('click', function(e) {
    e.preventDefault();
    document.getElementById('submitForm').click();
  })

  var reviews = {
    reviews: [
      {
        stars: 3,
        name: 'bob',
        city: 'Noosk',
        review: '1 Thompson Greenspon is so grateful to have worked with CPASiteSolutions on our'
      },{
        stars: 4,
        name: 'bobbo',
        city: 'WinNoosk',
        review: '2 Thompson Greenspon is so grateful to have worked with CPASiteSolutions on our'
      },{
        stars: 2,
        name: 'bobster',
        city: 'NooSKI',
        review: '3 Thompson Greenspon is so grateful to have worked with CPASiteSolutions on our'
      },
    ]
  }

  function ReviewStarContainer(stars) {
    var div = document.createElement('div');
    div.className = "stars-container";
    for (var i = 0; i < 5; i++) {
      var svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
      svg.setAttribute('viewBox',"0 12.705 512 486.59");
      svg.setAttribute('x',"0px");
      svg.setAttribute('y',"0px");
      svg.setAttribute('xml:space',"preserve");
      svg.setAttribute('class',"star");
      var svgNS = svg.namespaceURI;
      var star = document.createElementNS(svgNS,'polygon');
      star.setAttribute('points', '256.814,12.705 317.205,198.566 512.631,198.566 354.529,313.435 414.918,499.295 256.814,384.427 98.713,499.295 159.102,313.435 1,198.566 196.426,198.566');
      star.setAttribute('fill', i < stars ? '#f39c12' : '#808080');
      svg.appendChild(star);
      div.appendChild(svg);
    }
    return div;
  }

  function ReviewContentContainer(name, city, review) {

    var reviewee = document.createElement('div');
    reviewee.className = "reviewee footer";
    reviewee.innerHTML  = '- ' + name + ', ' + city

    var comment = document.createElement('p');
    comment.innerHTML = review;

    var div = document.createElement('div');
    div.className = "review-content";
    div.appendChild(comment);
    div.appendChild(reviewee);

    return div;
  }

  function ReviewsContainer(review) {
    var div = document.createElement('blockquote');
    div.className = "review";
    div.appendChild(ReviewStarContainer(review.stars));
    div.appendChild(ReviewContentContainer(review.name,review.city,review.review));
    return div;
  }

  for(var i = 0; i < reviews.reviews.length; i++) {
    document.getElementById('review-container').appendChild(ReviewsContainer(reviews.reviews[i]))
  }
  
   */
 $(function(){
		
	  var initWidth = $(".img-preview-big")[0].offsetWidth;
	$(".img-preview-big").css("height", initWidth + "px");

	window.onresize = function(event) {
	  var initWidth = $(".img-preview-big")[0].offsetWidth;
	  $(".img-preview-big").css("height", initWidth + "px");
	};
	/*
	$("#rating").on('mouseenter mouseleave', '.img-preview-big', function(ev) {
	  var mouse_is_inside = ev.type === 'mouseenter';
	  if ($(".img-preview-small").length > 0) {
	    if (mouse_is_inside) {
		console.log('asdfadsf');
	      $(".img-delete").css("display", "flex");	
	    } else {
	      $(".img-delete").css("display", "none");
	    }
	  } else {
	    $(".img-delete").css("display", "none");
	  }
	});
  */

	$("#rating").hover(function(ev) {
	  var mouse_is_inside = ev.type === 'mouseenter';
	  if ($(".img-preview-small").length > 0) {
	    if (mouse_is_inside) {
		console.log('asdfadsf');
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
	 if ($(".img-preview-small").length <1) {
		console.log('test');
		 $("input[type='file']").click();
		  } 
	  
	  if ($(".img-preview-small").length >1) {
		   	alert("1장만 업로드 할 수 있습니다. ");
		  } 
	})
	/* */
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
	
	$('button').on('click',function(){
		console.log("ddd");
	})
	
	  const button = document.querySelector("button");

	  button.addEventListener("click",function(){
	  	console.log("click test");
	  })
	
	})