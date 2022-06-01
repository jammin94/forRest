<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
  
   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" >
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" >
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>
	
	
	<!-- Bootstrap Dropdown Hover CSS -->
   <link href="/css/animate.min.css" rel="stylesheet">
   <link href="/css/bootstrap-dropdownhover.min.css" rel="stylesheet">
    <!-- Bootstrap Dropdown Hover JS -->
   <script src="/javascript/bootstrap-dropdownhover.min.js"></script>
   
   
   <!-- jQuery UI toolTip 사용 CSS-->
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <!-- jQuery UI toolTip 사용 JS-->
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<style>
	.underline{
		text-decoration:underline;
	}

	.upProduct{
		color:red;
		text-decoration : underline;
		cursor:pointer;
	}
	
	.ajaxButton{
		z-index:1;
		border: none;
 		border-radius: 50%;
 		cursor:pointer; 	
 		background-color: lightgray;
 		color:white;	
	}
	.ajaxButton:hover{
		background-color: skyblue;
	}
	
	.page-header{
	    padding-top: 30px;	
	}
	
	.form-group .form-control.index{
		width: 110px !important;
	}
	
	.tableheader th{
		text-align:center;
	}
	
	.caption{		
	    position: absolute;
	  	top: 0;
	    left: 0;	  	   
	    transition:all 0.7s;	    	      
    	position: absolute;    	    	   
	    width: 251px;
	    height: 250px;
	    padding: 50px;
	    color:#fff;	    
	    text-align: center;
	    cursor:pointer;
	    background-color: rgb(0 0 0 / 50%); 	
	    opacity:0;
	}
	
	.captionBuyNo{
		position: absolute;
		top: 0;
	    left: 0;
	    transition: all 0.7s;
	    position: absolute;
	    width: 251px;
	    height: 250px;
	    padding: 50px;
	    color: #fff;
	    text-align: center;
	    cursor: none;
	    pointer-events: none;
	    background-color: rgb(54 54 54 / 50%);
	    opacity: 1;
	}
	
	.captionParent{
  		position: relative;
		padding-bottom: 3.5%;
		padding:0;
		width: 255px;
		left: 80px;
	}
	

	@import url('https://fonts.googleapis.com/css?family=Source+Code+Pro');

body{
  padding: 40px;
  background-color: #fff;  
}

.text{
    position: absolute;
    top: 7%;
    left: 50%;
    transform: translate(-50%, 0);
}

.happyText {
  border-right: solid 3px rgba(0,255,0,.75);
  white-space: nowrap;
  overflow: hidden;    
  /*font-family: 'Source Code Pro', monospace;*/  
  font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
  font-size: 28px;
  color: rgb(28 28 28 / 70%);
}

/* Animation */
.happyText {
  animation: animated-text 3s steps(40,end) 500ms 1 normal both,  			 
             animated-cursor 600ms steps(40,end) infinite; 
               
}

/* text animation */

@keyframes animated-text{
  from{width: 0;}
  to{width: 305px;}
}

/* cursor animations */

@keyframes animated-cursor{
  from{border-right-color: rgba(0,255,0,.75);}
  to{border-right-color: transparent;}
}

.searchBtn{
	top:0;
	left:0;
}

	
</style>

</head>

<body>
   <div class="container">
<p>
  대여수익확인페이지
</p>

</div>
</body>

</html>