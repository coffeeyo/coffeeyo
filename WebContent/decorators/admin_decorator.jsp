<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html lang="ko">
    <head>
    	<meta charset="utf-8">
    	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    	<meta name="viewport" content="width=device-width, initial-scale=1">
    	<!-- 위 3개의 메타 태그는 *반드시* head 태그의 처음에 와야합니다; 어떤 다른 콘텐츠들은 반드시 이 태그들 *다음에* 와야 합니다 -->
        <title><decorator:title default="coffee YO" /></title>
        <link href="/css/default.css" rel="stylesheet">
        <!-- 부트스트랩 -->
  		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  		
        <!-- IE8 에서 HTML5 요소와 미디어 쿼리를 위한 HTML5 shim 와 Respond.js -->
	    <!-- WARNING: Respond.js 는 당신이 file:// 을 통해 페이지를 볼 때는 동작하지 않습니다. -->
	    <!--[if lt IE 9]>
	      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	    <![endif]-->
	    <style>
	    	.navbar-inverse .navbar-nav>li>a {
			    color: white;
			}
	    </style>
	    
	    <script src="/js/common.js"></script>
        <decorator:head />
    </head>
    <body>
	<c:set var="conPath" value="${pageContext.request.requestURI}"/>
	<!-- header 스타일 수정(20181010) -->
    <div class="navbar navbar-inverse" style="background-color: #77563cf5; border-color: #77563cf5; width:100%;  height:190px; z-index:999;">
    	<div class="container" style="width:90%">
    		<div class="navbar-header">
	            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
	              <span class="sr-only">Toggle navigation</span>
	              <span class="icon-bar"></span>
	              <span class="icon-bar"></span>
	              <span class="icon-bar"></span>
	            </button>
	            <a class="navbar-logo" href="/"><img src="/img/coffeeLogo.png" class="img-circle" style="width:150px; height:150px;" alt="coffee YO!! logo" title="coffee YO!! logo" /></a>
	            <a class="navbar-logo2" href="/" ><img src="/img/coffeeLogo4.png" alt="coffee YO!! logo2" title="coffee YO!! logo2"/></a>
          	</div>
          	<div class="navbar-collapse collapse" style="float:right; margin-left:0;">
	   			<ul class="nav navbar-nav " >
	              <li <c:if test="${conPath eq '/admin/productListAction.yo' 
	              					or conPath eq '/admin/productAddFrmAction.yo' 
	              					or conPath eq '/admin/productUpdateFrmAction.yo'
	              					or conPath eq '/admin/productUpdateProcAction.y'}">class="active"</c:if>><a href="../admin/productListAction.yo"><span class="glyphicon glyphicon-gift"></span> 상품관리</a></li>
	              <li <c:if test="${conPath eq '/admin/orderManage.yo'}">class="active"</c:if>><a href="../admin/orderHistoryListAction.yo"><span class="glyphicon glyphicon-shopping-cart"></span> 주문관리</a></li>
	              <li <c:if test="${conPath eq '/admin/memberListAction.yo'}">class="active"</c:if>><a href="/admin/memberListAction.yo"><span class="glyphicon glyphicon-user"></span> 회원관리</a></li>
	              <li <c:if test="${conPath eq '/admin/boardBoardList.yo'}">class="active"</c:if>><a href="../admin/boardBoardList.yo"><span class="glyphicon glyphicon-cloud"></span> 커뮤니티관리</a></li>
	              <li><a href="/member/logOutAction.yo"><span class="glyphicon glyphicon-log-out"></span> 로그아웃</a></li>
	            </ul>
            </div>
   		</div>
    </div>
    
    <decorator:body />
    
    <div class="footer">
    	<span class="footer-text">Copyright&copy;</span>
    	<span class="show-on-scroll "><a href="javascript:scrollUp()">Top</a></span>
    </div>
    </body>
</html>