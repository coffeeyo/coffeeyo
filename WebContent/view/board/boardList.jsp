<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BoardList.jsp</title>
<style>
	table {
		border-collapse:collapse;
	}
	th {
		border-bottom:1px solid #7C5D44;
		background-color:#7C5D44;
		font-weight:lighter;
		color:white;
		text-align:center;
	}
	td {
		text-align:center;
	}
	.style1 tr {
		border-bottom:1px solid #EFE4B0;
	}
	.style1 tr:hover { 
		background-color: #EFE4B0; 
	} 
	h3 {
		color:#7C5D44;
	}
	a {
		color:black;
	}
	a:hover {
		font-weight:bold;
		color:#7C5D44;
	}
	.button {
    width:80px;
    background-color: #EFE4B0;
    border: none;
    color:black;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 13px;
    margin: 4px;
    cursor: pointer;
    border-radius:5px;
	}
	.button:hover {
	    background-color: #7C5D44;
	    color:white;
	    font-weight:bold;
	}
</style>
<script>
	function WriteForm(){
		//할일
		//글쓰기 폼을 요청
		//자바스크립트에서의 문서이동
		//location객체의 href속성이용
		location.href="../board/boardWriteForm.yo"
	}
	function AllList(){
		location.href="../board/boardBoardList.yo";
	}
	function MyList(){
		location.href="../board/boardBoardList.yo?opt=4&condition=${sessionScope.userid}";
	}
</script>
</head>
<body>
<table align="center" width="70%">
	<tr>
		<td>
			<h3 align="left">게시판 목록</h3><h5 align="left">게시물 개수 : ${COUNT}</h5>
		</td>
	</tr>
</table>
<%-- 검색부분 --%>
<form action="../board/boardBoardList.yo" method="post">
	<table border="1" align="center" width="70%"  height="50px">
		<tr>
			<td>
				<select id="opt" name="opt">
					<option value="0">제목</option>
					<option value="1">내용</option>
					<option value="2">제목+내용</option>
					<option value="3">글쓴이</option>
					<!-- 내글보기는 <option value="4">의 기능을 가짐-->
				</select>
				&nbsp;
				<input type="text" size="20"  id="condition"  name="condition"/>
				&nbsp;
				<input type="submit" value="검색" class="button"/>
				&nbsp;
				<input type="button" value="전체보기" onclick="AllList()" class="button"/>
				<c:if test="${not empty sessionScope.userid}">
				&nbsp;
				<input type="button" value="내글보기" onclick="MyList()" class="button"/>
				</c:if>
			</td>
		</tr>
	</table>
</form>	
<br>
<%-- 1. 목록을 출력 --%>
<%-- 목록보기에서 필요한 다른 기능(글쓰기)을 처리하도록 한다 --%>
	<table align="center" width="70%">
		<tr>
			<td style="text-align:right;">
				<input type="button" id="wbtn" value="글쓰기" onclick="WriteForm()" class="button"/>
			</td>
		</tr>
	</table>
<%-- 4번 방식으로 넘어온 모델을 꺼내보자 --%>
	<table align="center" width="70%" class="style1">
		<tr height="30px">
			<th width="10%">글번호</th>
			<th width="25%">상품명</th>
			<th width="15%">닉네임</th>
			<th width="25%">제목</th>
			<th width="5%">조회수</th>
			<th width="5%">추천수</th>
			<th width="15%">작성일</th>
		</tr> 
		<c:forEach var="data" items="${LIST}">
			<tr height="30px">
				<c:if test="${data.notiyn eq 'Y' }">
					<td><b>공지사항</b></td>
				</c:if>
				<c:if test="${data.notiyn eq 'N' }">
					<td>${data.bidx}</td>
				</c:if>
				<td>${data.pname}</td>
				<td>${data.nick}</td>
				<td><a href="../board/boardBoardDetail.yo?oriNo=${data.bidx}&nowPage=${PINFO.nowPage}">${data.getShortSubject()}</a></td>
				<td>${data.readcnt}</td>
				<td>${data.likecnt}</td>
				<td>${data.createdt}</td>
			</tr>
		</c:forEach>
	</table>
<br>
<%-- 2. 페이지 이동 기능을 추가  [이전][1][2][3][다음]--%>
	<table align="center" width="70%">	
		
		<tr>
			<td>
			<%-- 이전링크만들기 --%>
			<c:if test="${PINFO.startPage eq 1}">
				[이전]
			</c:if>
			<c:if test="${PINFO.startPage ne 1}">
				<%-- 링크는 목록보기를 요청 + 원하는 페이지를 알려주면 된다 --%>
				<a href="../board/boardBoardList.yo?nowPage=${PINFO.startPage-1}">[이전]</a>		
			</c:if>
			
			<%-- [1][2][3]만들기 --%>
			<c:forEach var="page" begin="${PINFO.startPage}"  end="${PINFO.endPage }">
				<a href="../board/boardBoardList.yo?nowPage=${page}">[${page}]</a>
			</c:forEach>
	
			<%-- 다음링크만들기 --%>
			<c:if test="${PINFO.endPage eq PINFO.totalPage}">
				[다음]
			</c:if>
			<c:if test="${PINFO.endPage ne PINFO.totalPage}">
				<a href="../board/boardBoardList.yo?nowPage=${PINFO.endPage+1}">[다음]</a>
			</c:if>
			</td>
		</tr>
	</table>
</body>
</html>