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
<title>상품 상세보기</title>
<style>
.top_title{width:1000px; margin-left:auto; margin-right:auto; font-size: 30px; font-weight: bold;}
.pdcount{width:1000px; margin-left:auto; margin-right:auto; font-size: 12px; font-weight: bold;}
td, th{padding:5px 10px;}
.top_tab{border: 1px solid gray; border-collapse:collapse; width:1000px; margin-left:auto; margin-right:auto;}
.firstrow{text-align:center;background-color:lightgray;font-weight:bold;border: 1px solid gray;}
.contentrow{text-align:center;valign:middle;height:30px;border: 1px solid gray;}
.top_b{text-align:right;width:1000px; margin:auto;margin-top:10px;}
.lname{font-weight:bold; padding-right:10px; padding-left:20px;}
#listbig{width:1000px; margin-left:auto; margin-right:auto;height:100vh;margin-bottom:700px}
#pname {font-weight:bold; width: 200px; text-overflow: ellipsis;
    -o-text-overflow: ellipsis; overflow: hidden;
    white-space: nowrap; word-wrap: normal !important; display: block;
    color:green;
    font-size:16px;}
.result{font-weight:bold}
ul{list-style:none;padding:0px; margin:0px;}
li{float:left;padding:10px;}
.incontent{text-align:left; }
</style>
<script>


</script>
</head>
<body>
<div class="top_title" >
		<p class="incontent">
		<c:forEach end="0" items="${requestScope.prodList}" var="pdlist">
			${po.cateName}</c:forEach> 상품 전체보기</p>
</div>
<%--<table class="top_tab" border="1">
<tr>
	<td>
	<div class="lookup">
		<span class="lname">상품분류</span>
		<span class="input">
			<select name="category">
				<option>-----선택하세요-----</option>
				<option>프라프치노</option>
				<option>에스프레소</option>
				<option>콜드브루</option>
			</select>
		</span>
		<span class="lname">상품명</span>
		<span class="input"><input type="text" name="pname" value="상품명을 입력하세요" size="50px"/></span>
		<span class="btn"><input type="button"  class="inbtn" name="search" value="조회" /></span>
	</div>
	</td>
</tr>
</table>	

<table class="top_b">
	<tr>
		<td class="incontent" >[전체 상품 개수: ${requestScope.listCount}]</td>
		<td>		
			<select class="select" name="cidx" required>
					<option value="newod">최신순</option>
					<option value="popod">인기순</option>
					<option value="priceod">가격순</option>
			</select>
		</td>
	</tr>
</table>
 --%>
	<div id="listbig">
		<ul>
			<c:if test="${fn:length(requestScope.prodList) > 0}"> 
				<c:forEach items="${requestScope.prodList}" var="pdlist">
					<li>
						<div class="result">
							<c:if test="${pdlist.image ne null}">
								<a href="../product/productDetailAction.yo?pid=${pdlist.pidx}&cid=${pdlist.cidx}&pageNum=${spage}">
								<img src="../view/upload/product/${pdlist.image}" border="0" title="${po.pname}" alt="${po.pname}" width="230px" height="230px" />
								</a>
							</c:if>
						</div>
							<div><span class="result" id="pname">${pdlist.pname}</span></div>
							<div>가격:<span class="result">${pdlist.price}&nbsp;원</span></div>
							<div>별점:<span class="result">
										<c:if test="${pdlist.pcPointAvg >= 0 && pdlist.pcPointAvg < 0.5}">
								        	<img src="../img/별점0.png" width="200px" height="40px">
								        </c:if>
								        <c:if test="${pdlist.pcPointAvg >= 0.5 && pdlist.pcPointAvg < 1}">
								        	<img src="../img/별점0.5.png" width="200px" height="40px">
								        </c:if>
								        <c:if test="${pdlist.pcPointAvg >= 1 && pdlist.pcPointAvg < 1.5}">
								        	<img src="../img/별점1.png" width="200px" height="40px">
								        </c:if>
								        <c:if test="${pdlist.pcPointAvg >= 1.5 && pdlist.pcPointAvg < 2}">
								        	<img src="../img/별점1.5.png" width="200px" height="40px">
								        </c:if>
								        <c:if test="${pdlist.pcPointAvg >= 2 && pdlist.pcPointAvg < 2.5}">
								        	<img src="../img/별점2.png" width="200px" height="40px">
								        </c:if>
								        <c:if test="${pdlist.pcPointAvg >= 2.5 && pdlist.pcPointAvg < 3}">
								        	<img src="../img/별점2.5.png" width="200px" height="40px">
								        </c:if>
								        <c:if test="${pdlist.pcPointAvg >= 3 && pdlist.pcPointAvg < 3.5}">
								        	<img src="../img/별점3.png" width="200px" height="40px">
								        </c:if>
								        <c:if test="${pdlist.pcPointAvg>=3.5 && pdlist.pcPointAvg<4}">
								        	<img src="../img/별점3.5.png" width="200px" height="40px">
								        </c:if>
								        <c:if test="${pdlist.pcPointAvg >= 4 && pdlist.pcPointAvg < 4.5}">
								        	<img src="../img/별점4.png" width="200px" height="40px">
								        </c:if>
								        <c:if test="${pdlist.pcPointAvg >= 4.5 && pdlist.pcPointAvg < 5}">
								        	<img src="../img/별점4.5.png" width="200px" height="40px">
								        </c:if>
								        <c:if test="${pdlist.pcPointAvg == 5}">
								      		<img src="../img/별점5.png" width="200px" height="40px">
								        </c:if>
										<fmt:formatNumber value="${pdlist.pcPointAvg}" pattern="0.0"/>/5
								        </span>
						</div>
					</li>
				</c:forEach>
			</c:if>
		</ul>
		<p>
	</div>


</body>
</html>