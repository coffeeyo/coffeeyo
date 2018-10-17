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
	    
	    <script src="/js/jquery.cookie.js"></script> 
	    <script src="/js/common.js"></script>
	    <script>
	    	$(function(){
	    		var cartOpen = $.cookie("cartOpen");
	    		//alert(cartOpen);
	    		<c:if test="${sessionScope.userid ne null}">
	    		if(cartOpen == 'Y') {
	    			$('#btnCartOpen').text('닫기');
	    			$('.cart_r').css('width','400px');
	    		}
	    		else {
	    			$('#btnCartOpen').text('열기');
	    			$('.cart_r').css('width','0px');
	    		}
	    		</c:if>
	    	});
	    </script>
        <decorator:head />
    </head>
    <body>
    <c:set var="conPath" value="${pageContext.request.requestURI}"/>
    <!-- header 스타일 수정(20181011) -->
    <div class="navbar navbar-inverse" style="top:0; width:100%; height:190px; background-color: #77563cf5; border-color: #77563cf5;">
    	<div class="container" style="width:90%">
    		<div class="navbar-header " >
	            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
	              <span class="sr-only">Toggle navigation</span>
	              <span class="icon-bar"></span>
	              <span class="icon-bar"></span>
	              <span class="icon-bar"></span>
	            </button>
	            <a class="navbar-logo" href="/"><img src="/img/coffeeLogo.png" class="img-circle" style="width:150px; height:150px;" alt="coffee YO!! logo" title="coffee YO!! logo" /></a>
	            <a class="navbar-logo" href="/" ><img src="/img/coffeeLogo4.png" alt="coffee YO!! logo2" title="coffee YO!! logo2"/></a>
	            
          	</div>
          	<div class="navbar-collapse collapse" style="float:right;">
          		<!-- https://www.w3schools.com/bootstrap/bootstrap_tabs_pills.asp 참고 -->
	   			<ul class="nav navbar-nav">
	   				<li>
	   					<ul class="nav nav-tabs">
	   					<li class="dropdown">
					      <a class="dropdown-toggle" data-toggle="dropdown" href="#">커피요<span class="caret"></span></a>
					      <ul class="dropdown-menu">
					        <c:if test="${fn:length(requestScope.cateList) > 0}">
								<c:forEach var="cate" items="${requestScope.cateList}">
									<li><a href="javascript:void(0);" onclick="goCateProduct('${cate.cidx}')">${cate.cname}</a></li>
								</c:forEach>
								<form id="cateFrm" name="cateFrm" action="/product/productListAction.yo" method="post">
									<input type="hidden" id="cate"  name="cidx">
								</form>
					      	</c:if>                     
					      </ul>
					    </li>
					    <li><a href="/board/boardBoardList.yo">커뮤니티</a></li>
	   					<c:if test="${sessionScope.userid eq null}">
						  <li <c:if test="${conPath eq '/member/loginFormAction.yo'}">class="active"</c:if>><a href="/member/loginFormAction.yo" >로그인</a></li>
						  <li <c:if test="${conPath eq '/member/joinFormAction.yo'}">class="active"</c:if>><a href="/member/joinFormAction.yo">회원가입</a></li>
						</c:if>
						</ul>
	   				</li>
	              
	              <c:if test="${sessionScope.userid ne null}">
	              	<li>
						    <ul class="nav nav-tabs">
							  <li class="dropdown">
							    <a class="dropdown-toggle" data-toggle="dropdown" href="#">${sessionScope.nick}
							    <span class="caret"></span></a>
							    <ul class="dropdown-menu">
							      <li <c:if test="${conPath eq '/member/memberInfoFormAction.yo' or conPath eq '/member/memberUpdateFormAction.yo'}">class="active"</c:if>><a href="/member/memberInfoFormAction.yo">회원정보</a></li>
							      <li><a href="/order/orderHistoryListAction.yo">주문조회</a></li>
							      <li><a href="#contact">내글보기</a></li>
							      <c:if test="${sessionScope.ulevel eq 10}">
					              	<li><a href="/admin/orderHistoryListAction.yo">사이트관리</a></li>
					              </c:if>
					              <c:if test="${sessionScope.userid ne null}">
								  	<li><a href="/member/logOutAction.yo">로그아웃</a></li>
								  </c:if>
							    </ul>
							  </li>
							  <c:if test="${sessionScope.userid ne null}">
							  	<li><a href="javascript:void(0);" onclick="setWingToggleHistory();">장바구니</a></li>
							  </c:if>
							</ul>
	              	</li>
	              </c:if>
	            </ul>
            </div>
   		</div>
    </div>
    <div id="viewCart" class="cart_r">
    	<div id="itemhead">
	    	<div id="cartBtn">
	    		<c:if test="${sessionScope.userid ne null}">
	    		<button id="btnCartOpen" class="ir wing-toggle btn-warning" onclick="setWingToggleHistory(this);">열기</button>
	    		</c:if>
	    	</div>
	    	<div class="panel panel-default">
	    		<div class="panel-heading"><h3><span class="glyphicon glyphicon-shopping-cart"></span> 장바구니</h3></div>
	    		<div id="panel-body">
		    	<iframe id="cartFrame" src="/popup/cartListAction.yo" allowtransparency="true" style="border:none; width:100%; height: 89vh;" ></iframe>
		    	</div>
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