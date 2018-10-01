<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.coffeeyo.board.model.BoardDAO"%>
<%@page import="com.coffeeyo.board.model.Board"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Barrio"
	rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
	function goWrite() {
		location.href = '/board/boardWriteFrmAction.yo';
	}
</script>
</head>
<body>
	<!-- 글목록 위 부분(20181001-페이징은 다시 구현해야 함)-->
	<br>
	<div class="topBbs">
		<h1>
			게시판 글목록 [ 전체글의 개수 :
			${requestScope.listCount}]
		</h1>
		<c:if test="${sessionScope.userid != null}">
		<div class="bbs_link">
			<input type="button" value="글쓰기" onclick="goWrite()"/>
		</div>
		</c:if>
	</div>
	<!-- 게시글 목록 부분 -->
	<br>
	<div class="board">
	<table class="bbs">
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>날짜</th>
			<th>조회수/좋아요</th>
		</tr>
		<c:if test="${fn:length(requestScope.noticeList) > 0}">
		<c:forEach var="notice" items="${requestScope.noticeList}">
		<tr>
			<td>공지</td>
			<td>
				 <a href="/board/boardDetailAction.yo?num=${notice.bidx}&pageNum=${spage}">${notice.subject}</a>
			</td>
			<td>${notice.nickName}</td>
			<td>${notice.createdt}</td>
			<td>${notice.readcnt}  /  ${notice.likecnt}</td>
		</tr>
		</c:forEach>
		</c:if>
		<c:if test="${fn:length(requestScope.boardList) > 0}">
		<c:forEach var="board" items="${requestScope.boardList}">
		<tr>
			<td>${board.bidx}</td>
			<td>
				 <a href="/board/boardDetailAction.yo?num=${board.bidx}&pageNum=${spage}">${board.subject}</a>
			</td>
			<td>${board.nickName}</td>
			<td>${board.createdt}</td>
			<td>${board.readcnt}  /  ${board.likecnt}</td>
		</tr>
		</c:forEach>
		</c:if>
	</table>
	</div>
	
	<br>
	<div class="bbsPageForm">
	
	
	<c:if test="${fn:length(requestScope.boardList) > 0}">
		<c:if test="${startPage != 1}">
			<a href='/board/boardListAction.yo?pageNum=${startPage-1}'>[ 이전 ]</a>
		</c:if>
		
		<c:forEach var="pageNum" begin="${startPage}" end="${endPage}">
			<c:if test="${pageNum == spage}">
				${pageNum}&nbsp;
			</c:if>
			<c:if test="${pageNum != spage}">
				<a href='/board/boardListAction.yo?pageNum=${pageNum}'>${pageNum}&nbsp;</a>
			</c:if>
		</c:forEach>
		
		<c:if test="${endPage != maxPage }">
			<a href='/board/boardListAction.yo?pageNum=${endPage+1 }'>[다음]</a>
		</c:if>
	</c:if>
	
	</div>
	
	<!--  검색 부분 -->
	<br>
	<div class="bbsSearchForm">
		<form>
			<select name="opt">
				<option value="0">제목</option>
				<option value="1">내용</option>
				<option value="2">제목+내용</option>
				<option value="3">글쓴이</option>
			</select>
			<input type="text" size="20" name="condition"/>&nbsp;
			<input type="submit" value="검색"/>
		</form>	
	</div>

</body>
</html>