<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.coffeeyo.product.model.ProductDao"%>
<%@page import="com.coffeeyo.product.model.Product"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html lang="ko">
<head>
<link href="https://fonts.googleapis.com/css?family=Barrio"	rel="stylesheet">
<meta charset="UTF-8">
<script>
</script>
</head>
<body> 
	<div class="board">
	<!-- MD추천 목록 부분 -->
	<c:if test="${fn:length(requestScope.recommList) > 0}">
		<div id="myCarousel" class="carousel slide" data-ride="carousel">
		  <!-- Indicators -->
		  <ol class="carousel-indicators">
		  	<c:forEach var="recomm" items="${requestScope.recommList}" varStatus="status">
		   	 <li data-target="#myCarousel" data-slide-to="${status.index}"  <c:if test="${status.index eq 0}">class="active"</c:if>></li>
		    </c:forEach>
		  </ol>
		
		  <!-- Wrapper for slides -->
		  <div class="carousel-inner">
		  	<c:forEach var="recomm" items="${requestScope.recommList}" varStatus="status">
		    <div <c:if test="${status.index eq 0}">class="item active"</c:if> <c:if test="${status.index ne 0}">class="item"</c:if>>
		      <img src="../view/upload/product/${recomm.image}" title="${recomm.pname}" alt="${recomm.pname}" style="cursor:pointer;" onclick="goProdDetail('${recomm.pidx}','${recomm.cidx}')"/>
		    </div>
			</c:forEach>
		  </div>
		
		  <!-- Left and right controls -->
		  <a class="left carousel-control" href="#myCarousel" data-slide="prev">
		    <span class="glyphicon glyphicon-chevron-left"></span>
		    <span class="sr-only">Previous</span>
		  </a>
		  <a class="right carousel-control" href="#myCarousel" data-slide="next">
		    <span class="glyphicon glyphicon-chevron-right"></span>
		    <span class="sr-only">Next</span>
		  </a>
		</div>
	</c:if>
	<br>
		
		<c:if test="${fn:length(requestScope.prodList1) > 0}">
		<h3>분류1</h3>
		<div class="row">
		  <c:forEach var="prod" items="${requestScope.prodList1}" varStatus="status">	
		  <div class="col-md-3">
		    <div class="thumbnail">
		      <!--<a href="/w3images/lights.jpg">-->
		        <c:if test="${prod.image ne null}">
					<img src="../view/upload/product/${prod.image}" border="0" title="${po.pname}" alt="${po.pname}"  style="width:100%;cursor:pointer;"  onclick="goProdDetail('${prod.pidx}','${prod.cidx}')" />
				</c:if>
		        <div class="caption">
		          <p>[${prod.cateName}] ${prod.pname}</p>
		          <p>별점: 0 점</p>
		          <p>가격: ${prod.price} 원</p>
		        </div>
		      <!--</a>-->
		    </div>
		  </div>
		  </c:forEach>
		</div>
		</c:if>
		
		
		<c:if test="${fn:length(requestScope.prodList2) > 0}">
		<h3>분류2</h3>
		<div class="row">
		  <c:forEach var="prod" items="${requestScope.prodList2}" varStatus="status">	
		  <div class="col-md-3">
		    <div class="thumbnail">
		      <!--<a href="/w3images/lights.jpg">-->
		        <c:if test="${prod.image ne null}">
					<img src="../view/upload/product/${prod.image}" border="0" title="${po.pname}" alt="${po.pname}"  style="width:100%;cursor:pointer;"  onclick="goProdDetail('${prod.pidx}','${prod.cidx}')" />
				</c:if>
		        <div class="caption">
		          <p>[${prod.cateName}] ${prod.pname}</p>
		          <p>별점: 0 점</p>
		          <p>가격: ${prod.price} 원</p>
		        </div>
		      <!--</a>-->
		    </div>
		  </div>
		  </c:forEach>
		</div>
		</c:if>
		
		
		<c:if test="${fn:length(requestScope.prodList3) > 0}">
		<h3>분류3</h3>
		<div class="row">
		  <c:forEach var="prod" items="${requestScope.prodList3}" varStatus="status">	
		  <div class="col-md-3">
		    <div class="thumbnail">
		      <!--<a href="/w3images/lights.jpg">-->
		        <c:if test="${prod.image ne null}">
					<img src="../view/upload/product/${prod.image}" border="0" title="${po.pname}" alt="${po.pname}"  style="width:100%;cursor:pointer;"  onclick="goProdDetail('${prod.pidx}','${prod.cidx}')" />
				</c:if>
		        <div class="caption">
		          <p>[${prod.cateName}] ${prod.pname}</p>
		          <p>별점: 0 점</p>
		          <p>가격: ${prod.price} 원</p>
		        </div>
		      <!--</a>-->
		    </div>
		  </div>
		  </c:forEach>
		</div>
		</c:if>
	</div>
	<form id="prodDetailFrm" name="prodDetailFrm" method="post">
		<input type="hidden" id="pid" name="pid" />
		<input type="hidden" id="cid" name="cid" />
	</form>
	
</body>
</html>