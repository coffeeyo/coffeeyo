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
<!-- 글목록 위 부분-->
<!-- 주석 테스트 -->
	<br>
	<div class="topBbs">
		<h1>
			상품 목록 [ 전체 상품 개수 :
			${requestScope.listCount}]
		</h1>
	</div>
	<!-- 게시글 목록 부분 -->
	<br>
	<div class="board">
	<c:if test="${fn:length(requestScope.prodList) > 0}">
	<div class="row">
	  <c:forEach var="prod" items="${requestScope.prodList}" varStatus="status">	
	  <div class="col-md-3">
	    <div class="thumbnail">
	      <!--<a href="/w3images/lights.jpg">-->
	        <c:if test="${prod.image ne null}">
				<img src="../view/upload/product/${prod.image}" title="${prod.pname}" alt="${prod.pname}" style="cursor:pointer;" onclick="goProdDetail('${prod.pidx}','${prod.cidx}')"/>
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
	
	<br>
	<div class="bbsPageForm">
	
	
	<c:if test="${fn:length(requestScope.prodList) > 0}">
		<c:if test="${startPage != 1}">
			<a href='../board/boardListAction.yo?pageNum=${startPage-1}'>[ 이전 ]</a>
		</c:if>
		
		<c:forEach var="pageNum" begin="${startPage}" end="${endPage}">
			<c:if test="${pageNum == spage}">
				${pageNum}&nbsp;
			</c:if>
			<c:if test="${pageNum != spage}">
				<a href='../board/boardListAction.yo?pageNum=${pageNum}'>${pageNum}&nbsp;</a>
			</c:if>
		</c:forEach>
		
		<c:if test="${endPage != maxPage }">
			<a href='..bd?pageNum=${endPage+1 }'>[다음]</a>
		</c:if>
	</c:if>
	
	</div>
	
	<!--  검색 부분 -->
	<br>
	<div class="bbsSearchForm">
		<form>
			<select name="opt">
				<option value="0">상품명</option>
			</select>
			<input type="text" size="20" name="condition"/>&nbsp;
			<select name="cidx">
				<option value="0" selected>분류없음</option>
				<c:if test="${fn:length(requestScope.cateList) > 0}">
					<c:forEach var="cate" items="${requestScope.cateList}">
						<option value="${cate.cidx}">${cate.cname}</option>
					</c:forEach>
				</c:if>
			</select>
			&nbsp;
			<input type="submit" value="검색"/>
		</form>	
	</div>
	<form id="prodFrm" name="prodFrm" method="post">
		<input type="hidden" id="num" name="num" />
		<input type="hidden" id="pageNum" name="pageNum" />
	</form>
	<form id="prodDetailFrm" name="prodDetailFrm" method="post">
		<input type="hidden" id="pid" name="pid" />
		<input type="hidden" id="cid" name="cid" />
	</form>
</body>
</html>