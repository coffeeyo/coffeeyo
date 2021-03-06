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
	    
	    <script src="/js/common.js"></script>
        <decorator:head />
    </head>
    <body>
    <c:set var="conPath" value="${pageContext.request.requestURI}"/>
    
    <div class="navbar navbar-inverse" style="position	: fixed; top:0; width:100%; z-index:999;">
    	<div class="container">
    		<div class="navbar-header " >
	            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
	              <span class="sr-only">Toggle navigation</span>
	              <span class="icon-bar"></span>
	              <span class="icon-bar"></span>
	              <span class="icon-bar"></span>
	            </button>
	            <a class="navbar-brand" href="/">coffee YO!! logo</a>
          	</div>
          	<div class="navbar-collapse collapse">
	   			<ul class="nav navbar-nav">
	   			  <c:if test="${sessionScope.userid eq null}">
	   			  	<li <c:if test="${conPath eq '/member/loginFormAction.yo'}">class="active"</c:if>><a href="/member/loginFormAction.yo">로그인</a></li>
	              	<li <c:if test="${conPath eq '/member/joinFormAction.yo'}">class="active"</c:if>><a href="/member/joinFormAction.yo">회원가입</a></li>
	              </c:if>
	              <li <c:if test="${conPath eq '/product/productListAction.yo' or conPath eq '/product/productDetailAction.yo'}">class="active"</c:if>><a href="/product/productListAction.yo">커피</a></li>
	              <c:if test="${sessionScope.userid ne null}">
	              	<li><a href="#contact">주문조회</a></li>
	              	<li <c:if test="${conPath eq '/member/memberInfoFormAction.yo' or conPath eq '/member/memberUpdateFormAction.yo'}">class="active"</c:if>><a href="/member/memberInfoFormAction.yo">회원정보</a></li>
	              </c:if>
	              <li <c:if test="${conPath eq '/board/boardListAction.yo' or conPath eq '/board/boardWriteFrmAction.yo' or conPath eq '/board/boardDetailAction.yo'}">class="active"</c:if>><a href="/board/boardListAction.yo">커뮤니티</a></li>
	              <c:if test="${sessionScope.ulevel eq 10}">
	              	<li><a href="/admin/memberListAction.yo">관리자</a></li>
	              </c:if>
	              <c:if test="${sessionScope.userid ne null}">
	              	<li><a href="/member/logOutAction.yo">로그아웃</a></li>
	              </c:if>
	            </ul>
	            <%--
                <c:if test="${sessionScope.userid eq null}">
		        <form name="login" action="/member/loginProcAction.yo" class="navbar-form navbar-right">
		            <div class="form-group">
		              <input type="text" name="id" placeholder="userid" class="form-control">
		            </div>
		            <div class="form-group">
		              <input type="password" name="pass" placeholder="password" class="form-control">
		            </div>
		            <button type="submit" class="btn btn-success">로그인</button>
		        </form>
		        </c:if>
		        --%>
		        
            </div>
   		</div>
   		<ul>
        	<li>대메뉴1</li>
        	<li>대메뉴2</li>
        </ul>
    </div>
    
    <decorator:body />
    
    <div class="footer">
    	<span class="footer-text">Copyright&copy;</span>
    	<span class="show-on-scroll "><a href="javascript:scrollUp()">Top</a></span>
    </div>
    </body>
</html>