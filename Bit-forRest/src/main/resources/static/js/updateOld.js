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

submit.addEventListener("click",function(){
	$("form").attr("method" , "POST").attr("action" , "/old/updateOld").submit();	
});


