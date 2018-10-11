<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%-- 게시판 상세보기 --%>
<table border="1" align="center" width="600">
	<tr>
		<th>분류명</th>
		<td>${DATA.cname }</td>
		<th>상품명</th>
		<td>${DATA.pname }</td>
	</tr>
	<tr>
		<th>작성자</th>
		<td>${DATA.nick }</td>
		<th>작성일</th>
		<td>${DATA.createdt }</td>
	</tr>
	<tr>
		<th>조회수</th>
		<td>${DATA.readcnt }</td>
		<th>추천수</th>
		<td>${DATA.likecnt }</td>
	</tr>
	<tr>
		<th>제목</th>
		<td colspan="3">${DATA.subject }</td>
	</tr>
	<tr>
		<th>내용</th>
		<td colspan="3">
		${DATA.comm }
		<c:if test="${sessionScope.userid eq DATA.userid}">
			<br/>
			<input type="button" class="mBtn" value="수정">
			<input type="button" class="dBtn" value="삭제">
		</c:if>	
		</td>
	</tr>
</table>
<%-- 댓글작성테이블 --%>
<form id="wrfrm" name="wrfrm" action="../board/commentWrite.yo" method="post">
	<input type="hidden" id="oriNo" name="oriNo" value="${oriNo}">
	<input type="hidden" id="nowPage" name="nowPage" value="${nowPage}">
	<table width="600" border="1" align="center">
		<tr>
			<td><textarea name="comm" id="comm" placeholder="댓글 작성란"></textarea></td>
			<td colspan="2" align="right">
				<input type="button" id="wrBtn" value="글등록" >
			</td>
		</tr>
	</table>
</form>
</body>
</html>