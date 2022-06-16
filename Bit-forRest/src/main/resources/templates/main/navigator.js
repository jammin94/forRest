

const ulTag = document.querySelector("ul");

let totalPages = 20; // 전체 페이지 수

function element(totalPages, page){
    let liTag='';
    let activeLi;
    let beforePages = page - 1;
    let afterPages = page +1;
    if(page>1){ //1페이지 이상이면 이전 버튼 생성
        liTag +='<a href="javascript:fncGetList('+(page-1)+');"><li class="btn prev" onclick="element(totalPages,'+(page-1)+')"><span><i class="fa-solid fa-angle-left"></i>Prev</span></li></a>';
    }
    if(page>2){
        liTag += '<a href="javascript:fncGetList('+1+');"><li class="numb" onclick="element(totalPages, 1)"><span>1</span></li></a>';
        if(page>3){
            liTag += '<li class="dots"><span>...</span></li>';
        }
    }

    if(page==totalPages){
        beforePage=beforePages-2;
    }else if(page==totalPages-1){
        beforePage=beforePages-1;
    }

    if(page==1){
        afterPages=afterPages+2;
    }else if(page==2){
        afterPages=afterPages+1;
    }

    for(let pageLength =beforePages ; pageLength <= afterPages; pageLength++){
        if(pageLength >totalPages){
            continue;
        }
        if(pageLength==0){
            pageLength =pageLength+1
        }
        if(page==pageLength){
            activeLi = "active";
        }else{
            activeLi ="";
        }
        liTag += '<a href="javascript:fncGetList('+(pageLength)+');"><li class="numb '+activeLi+'" onclick="element(totalPages, '+pageLength+')"><span>'+pageLength+'</span></li></a>'
        
    }
    if(page<totalPages-1){
        if(page<totalPages-2){
            liTag += '<li class="dots"><span>...</span></li>';
        }
        liTag += '<a href="javascript:fncGetList('+(totalPages)+');"><li class="numb" onclick="element(totalPages, '+totalPages+')"><span>'+totalPages+'</span></li></a>';
    }


    if(page<totalPages-1){ //1페이지 이상이면 이전 버튼 생성
        liTag +='<a href="javascript:fncGetList('+(page+1)+');"><li class="btn next" onclick="element(totalPages,'+(page+1)+')"><span>Next<i class="fa-solid fa-angle-right"></i></span></li></a>';
    }
    ulTag.innerHTML =liTag;
}
element(totalPages,5); //calling back form 우리 페이지 5== 현재페이지