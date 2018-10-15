<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.coffeeyo.product.model.ProductDao"%>
<%@page import="com.coffeeyo.product.model.Product"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>상품목록조회</title>

<style>
td, th{padding:5px 10px;}
.top_title{width:1000px; margin:auto; font-size: 30px; font-weight: bold;}
.top_tab{border: 1px solid gray; border-collapse:collapse; width:1000px; margin:auto;}
.firstrow{text-align:center;background-color:lightgray;font-weight:bold;border: 1px solid gray;}
.contentrow{text-align:center;valign:middle;height:30px;border: 1px solid gray;}
.top_b{text-align:right;width:1000px; margin:auto;margin-top:10px;}
.lname{font-weight:bold; padding-right:10px; padding-left:20px;}
.listup{width:1000px; margin:auto;}
#pname {font-weight:bold;text-align:left;
    width: 400px;
    text-overflow: ellipsis;
    -o-text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
    word-wrap: normal !important;
    display: block;}

</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
function prodUpdate(pno, spage) {
	$("#num").val(pno);
	$("#pageNum").val(spage);
	
	$("#prodFrm").attr("action", "../admin/productUpdateFrmAction.yo");
	$("#prodFrm").submit();
}


$(function(){
	$("#pd_create").click(function(){
		$(location).attr("href","../admin/productAddFrmAction.yo")
	});

});
</script>
</head>
<body>
<div class="top_title" >
		<p>상품 목록</p>
	</div>
<table class="top_tab" border="1">
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
		<td align="left">[ 전체 상품 개수 :	${requestScope.listCount}]</td>
		<td>		
			<c:if test="${sessionScope.ulevel eq 10}">
				<p><input type="button" class="outbtn" id="pd_create" value="신규등록" /></p>
			</c:if>
		</td>
	</tr>
</table>
<table class="listup" >
	<tr class="firstrow">
		<td>상품번호</td>
		<td>상품분류</td>
		<td>상품명</td>
		<td>가격</td>
		<td>등록일</td>
		<td>노출여부</td>
		<td>수정</td>
	</tr>
	<c:if test="${fn:length(requestScope.prodList) > 0}">
		<c:forEach items="${requestScope.prodList}" var = "pdlist">	
		<tr class="contentrow">
			<td>${pdlist.pidx}</td>
			<td>${pdlist.cateName}</td>
			<td id="pname">
				<c:if test="${pdlist.image ne null}">
					<img src="../view/upload/product/${pdlist.image}" border="0" title="${po.pname}" alt="${po.pname}" width="120px" height="120px" />
				</c:if>&nbsp;&nbsp;&nbsp;
				<a href="../admin/productUpdateFrmAction.yo?num=${pdlist.pidx}&pageNum=${spage}">${pdlist.pname}</a>
			</td>
			<td>${pdlist.price}&nbsp;원</td>
			<td>${pdlist.createdt}</td>
			<td>
				<c:if test="${pdlist.status eq 1}">노출</c:if>
				<c:if test="${pdlist.status eq 2}">미노출</c:if>
			</td>
			<td><input type="button" class="inbtn" name="productUpdateBtn" id="rBtn" value="상품수정" onclick="prodUpdate('${pdlist.pidx}','${spage}')"/></td>
		</tr>
		</c:forEach>
	</c:if>
</table>
<%-- 2. 페이지 이동 기능을 추가--%>
<br/>
	<table style="width:1000px;margin:auto;" >
		<tr>
			<td align="center">
				<c:if test="${fn:length(requestScope.prodList) > 0}">
					<c:if test="${startPage != 1}">
						<a href='../admin/productListAction.yo?pageNum=${startPage-1}'>[이전 ]</a>
					</c:if>

					<c:forEach var="pageNum" begin="${startPage}" end="${endPage}">
						<c:if test="${pageNum == spage}">${pageNum}&nbsp;</c:if>
						<c:if test="${pageNum != spage}">
							<a href='../admin/productListAction.yo?pageNum=${pageNum}'>${pageNum}&nbsp;</a>
						</c:if>
					</c:forEach>

					<c:if test="${endPage != maxPage }">
						<a href='../admin/productListAction.yo?pageNum=${endPage+1 }'>[다음]</a>
					</c:if>
				</c:if>
			</td>
		</tr>
	</table>  
<form id="prodFrm" name="prodFrm" method="post">
		<input type="hidden" id="num" name="num" />
		<input type="hidden" id="pageNum" name="pageNum" />
</form>
</body>
</html>