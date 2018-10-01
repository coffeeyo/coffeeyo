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
	function prodUpdate(pno, spage) {
		$('#num').val(pno);
		$('#pageNum').val(spage);
		
		$('#prodFrm').attr('action', '../admin/productUpdateFrmAction.yo');
		$('#prodFrm').submit();
	}
	
	function prodAdd() {
		location.href = '../admin/productAddFrmAction.yo';
	}
</script>
</head>
<body>
<!-- 글목록 위 부분-->
	<br>
	<div class="topBbs">
		<h1>
			상품 목록 [ 전체 상품 개수 :
			${requestScope.listCount}]
		</h1>
		<c:if test="${sessionScope.ulevel eq 10}">
		<div class="bbs_link">
			<input type="button" value="상품등록" onclick="prodAdd()"/>
		</div>
		</c:if>
	</div>
	<!-- 게시글 목록 부분 -->
	<br>
	<div class="board">
	<table class="bbs">
		<tr>
			<th>번호</th>
			<th>분류</th>
			<th>이미지</th>
			<th>상품명</th>
			<th>금액</th>
			<th>제조소요시간</th>
			<th>MD추천</th>
			<th>노출</th>
			<th>등록일</th>
			<th>수정</th>
		</tr>
		<c:if test="${fn:length(requestScope.prodList) > 0}">
		<c:forEach var="prod" items="${requestScope.prodList}">
		<tr>
			<td>${prod.pidx}</td>
			<td>${prod.cateName}</td>
			<td>
			<c:if test="${prod.image ne null}">
				<img src="../view/upload/product/${prod.image}" border="0" title="${po.pname}" alt="${po.pname}" />
			</c:if>
			</td>
			<td>
				 ${prod.pname}
			</td>
			<td>${prod.price}</td>
			<td>${prod.maketm}분</td>
			<td>
				<c:if test="${prod.recomm eq 1}">추천함</c:if>
				<c:if test="${prod.recomm eq 0}">-</c:if>
			</td>
			<td>
				<c:if test="${prod.status eq 1}">노출</c:if>
				<c:if test="${prod.status eq 2}">미노출</c:if>
			</td>
			<td>${prod.createdt}</td>
			<td>
				<input type="button" value="수정" onclick="prodUpdate('${prod.pidx}','${spage}')">
			</td>
		</tr>
		</c:forEach>
		</c:if>
	</table>
	</div>
	
	<br>
	<div class="bbsPageForm">
	
	
	<c:if test="${fn:length(requestScope.prodList) > 0}">
		<c:if test="${startPage != 1}">
			<a href='../admin/productListAction.yo?pageNum=${startPage-1}'>[ 이전 ]</a>
		</c:if>
		
		<c:forEach var="pageNum" begin="${startPage}" end="${endPage}">
			<c:if test="${pageNum == spage}">
				${pageNum}&nbsp;
			</c:if>
			<c:if test="${pageNum != spage}">
				<a href='../admin/productListAction.yo?pageNum=${pageNum}'>${pageNum}&nbsp;</a>
			</c:if>
		</c:forEach>
		
		<c:if test="${endPage != maxPage }">
			<a href='../admin/productListAction.yo?pageNum=${endPage+1 }'>[다음]</a>
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
</body>
</html>