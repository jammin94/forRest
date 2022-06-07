(function(){
	  // FAQ Template - by CodyHouse.co
	  var FaqTemplate = function(element) {
			this.element = element;
			this.sections = this.element.getElementsByClassName('cd-faq__group');
			console.log(element.getElementsByClassName('cd-faq__group'));
			this.triggers = this.element.getElementsByClassName('cd-faq__trigger');
			this.faqContainer = this.element.getElementsByClassName('cd-faq__items')[0];
			console.log(element.getElementsByClassName('cd-faq__items')[0]);
			this.faqsCategoriesContainer = this.element.getElementsByClassName('cd-faq__categories')[0];
	  	this.faqOverlay = this.element.getElementsByClassName('cd-faq__overlay')[0];
	  	this.faqClose = this.element.getElementsByClassName('cd-faq__close-panel')[0];
	  	this.scrolling = false;
	  	console.log("FaqTemplate 잘 됨");
	  	console.log(this.faqContainer);
	  	console.log(this.element);
	  	console.log(this.element);
	  	
	  	initFaqEvents(this);
	  };

	  function initFaqEvents(faqs) {
	  	// click on a faq category

		
		console.log("faqs 란"+faqs);
		// on desktop -> toggle faq content visibility when clicking on the trigger element
		faqs.faqContainer.addEventListener('click', function(event){			
			var trigger = event.target.closest('.cd-faq__trigger');
			if(!trigger) return;
			event.preventDefault();
			var content = trigger.nextElementSibling,
				parent = trigger.closest('li'),
				bool = Util.hasClass(parent, 'cd-faq__item-visible');

			Util.toggleClass(parent, 'cd-faq__item-visible', !bool);

			//store initial and final height - animate faq content height
			Util.addClass(content, 'cd-faq__content--visible');
			var initHeight = bool ? content.offsetHeight: 0,
				finalHeight = bool ? 0 : content.offsetHeight;
			
			if(window.requestAnimationFrame) {
				Util.setHeight(initHeight, finalHeight, content, 200, function(){
					heighAnimationCb(content, bool);
				});
			} else {
				heighAnimationCb(content, bool);
			}
			console.log("addEventListener 잘 됨");
		});
		
		if(window.requestAnimationFrame) {
			// on scroll -> update selected category
			window.addEventListener('scroll', function(){
				if(getMq(faqs) != 'desktop' || faqs.scrolling) return;
				faqs.scrolling = true;
				window.requestAnimationFrame(updateCategory.bind(faqs)); 
			});
		}
		console.log("init FAQ 잘 됨");
  };//FAQ 에 이벤트 삽입 함수


	   $( ".cd-faq__trigger" ).on("click" , function() {
	  	// click on a faq category

		
		// on desktop -> toggle faq content visibility when clicking on the trigger element
		faqs.faqContainer.addEventListener('click', function(event){			
			var trigger = event.target.closest('.cd-faq__trigger');
			if(!trigger) return;
			event.preventDefault();
			var content = trigger.nextElementSibling,
				parent = trigger.closest('li'),
				bool = Util.hasClass(parent, 'cd-faq__item-visible');

			Util.toggleClass(parent, 'cd-faq__item-visible', !bool);

			//store initial and final height - animate faq content height
			Util.addClass(content, 'cd-faq__content--visible');
			var initHeight = bool ? content.offsetHeight: 0,
				finalHeight = bool ? 0 : content.offsetHeight;
			
			if(window.requestAnimationFrame) {
				Util.setHeight(initHeight, finalHeight, content, 200, function(){
					heighAnimationCb(content, bool);
				});
			} else {
				heighAnimationCb(content, bool);
			}
			console.log("addEventListener 잘 됨");
		});
		
		if(window.requestAnimationFrame) {
			// on scroll -> update selected category
			window.addEventListener('scroll', function(){
				if(getMq(faqs) != 'desktop' || faqs.scrolling) return;
				faqs.scrolling = true;
				window.requestAnimationFrame(updateCategory.bind(faqs)); 
			});
		}
		console.log("init FAQ 잘 됨");
  });//FAQ 에 이벤트 삽입 함수



  function heighAnimationCb(content, bool) {
		content.removeAttribute("style");
		if(bool) Util.removeClass(content, 'cd-faq__content--visible');
		console.log("heighAnimationCb 잘 됨");
  };

  var faqTemplate = document.getElementsByClassName('js-cd-faq'),
  	faqArray = [];
  if(faqTemplate.length > 0) {
		for(var i = 0; i < faqTemplate.length; i++) {
			faqArray.push(new FaqTemplate(faqTemplate[i])); 
		}
	console.log("faqTemplate 잘 됨");	
  };
  
  console.log("init 잘 됨");
})();