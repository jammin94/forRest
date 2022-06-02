<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Photogram</title>
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
        integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous" />
</head>
<script type="text/javascript">
	function fncAddAnnounce(){
		document.detail.action='../addAnnounce';
		document.detail.submit();
	}
	
	function fncAddFAQ(){
		document.detailForm.action='../addFAQ';
		document.detailForm.submit();
	}
	
	function fncUpdateAnnounce(){
		document.detailForm.action='../updateAnnounce';
		document.detailForm.submit();
	}
	
	function fncUpdateFAQ(){
		document.detailForm.action='../updateFAQ';
		document.detailForm.submit();
	}
	
</script>

<body>
    <div class="container">
 		<a href="/board/getAnnounce/2">GET: getAnnounce</a><br/>
 		<a href="/board/getFAQ/5">GET: getFAQ</a><br/>
 		<a href="/board/deleteAnnounce">GET: deleteAnnounce</a><br/>
 		<a href="/board/deleteFAQ">GET: deleteFAQ</a><br/>
 		<a href="/board/listAnnounce">GET: listAnnounce</a><br/>
 		<a href="/board/listFAQ">GET: listFAQ</a><br/>
 		<a href="/board/updateFixAnnounce/2">GET: updateFixAnnounce</a><br/>
    </div>
    
    <form name="detail" method="post">
     	boardNo
	    <input type="text" name="boardNo"/><br/>
	    boardTitle
	    <input type="text" name="boardTitle"/><br/>
	    boardDetail
	    <input type="text" name="boardDetail"/><br/>
	    category
	    <input type="text" name="category"/><br/>
	    boardFlag
	    <input type="text" name="boardFlag"/><br/>
	    
	    Search
	    <input type="text" name="searchKeyword"/><br/>
	    
	    <div>
		    <input type="radio" id="title"
		     name="searchCondition" value="title">
		    <label for="contactChoice1">Email</label>
		
		    <input type="radio" id="context"
		     name="searchCondition" value="context">
		    <label for="contactChoice2">Phone</label>
		    
		    <input type="radio" id="ASC"
		     name="orderCondition" value="ASC">
		    <label for="contactChoice1">오름차순</label>
		
		    <input type="radio" id="DESC"
		     name="orderCondition" value="DESC">
		    <label for="contactChoice2">내림차순</label>
		  </div>
	    
	    <a href="javascript:fncAddAnnounce();">POST: addAnnounce</a><br/>
	    <a href="javascript:fncAddFAQ();">POST: addFAQ</a><br/>
	    <a href="javascript:fncUpdateAnnounce();">POST: updateAnnounce</a><br/>
	    <a href="javascript:fncUpdateFAQ();">POST: updateFAQ</a><br/>
	</form>  

</body>

</html>