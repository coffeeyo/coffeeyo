<%@page import="com.coffeeyo.board.model.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
	<title>Insert title here</title>
	<script type="text/javascript">
		function checkValue(){
			var form = document.forms[0];
			var board_subject = form.subject.value;
			var board_content = form.content.value;
			
			if(!board_subject){
				alert("제목을 입력해주세요.")
				return false;
			}
			else if(!board_content){
				alert("내용을 입력해주세요.")
				return false;
			}
		}
	
	</script>
</head>
<body>
<h1>게시판 글수정</h1>
<br>
	<b><font size="6" color="gray">글쓰기</font></b>
	<br>
	
	<form method="post" action="../admin/boardUpdateAction.yo?pageNum=${pageNum}" name="boardForm" enctype="multipart/form-data"
		onsubmit="return checkValue()">
	<input type="hidden" name="num" value="${bo.bidx}">
	<table width="700" border="3" bordercolor="lightgray" align="center">

		<tr>
			<td id="title">
				제 목
			</td>
			<td>
				<input name="subject" type="text" size="70" maxlength="100" value="${bo.subject}"/>
			</td>		
		</tr>
		<tr>
			<td id="title">
				내 용
			</td>
			<td>
				<textarea name="content" cols="72" rows="20">${bo.comm}</textarea>			
			</td>		
		</tr>
		<c:if test="${bo.image ne null}">
		<tr>
			<td id="title">
				기존 파일
			</td>
			<td>
				<a href="../view/upload/board/${bo.image}"> <img src="../view/upload/board/${bo.image}" border="0" title="${bo.subject}" alt="${bo.subject}" /></a>
			</td>	
		</tr>
		</c:if>
		<tr>
			<td id="title">
				이미지첨부
			</td>
			<td>
				<input type="file" name="image" />
			</td>	
		</tr>
		<tr>
			<td id="title">공지유무</td>
			<td>
				<select name="notiyn">
					<option value="Y" <c:if test="${bo.notiyn eq 'Y'}">selected</c:if>>예</option>
					<option value="N" <c:if test="${bo.notiyn eq 'N'}">selected</c:if>>아니오</option>
				</select>
			</td>
		</tr>

		<tr align="center" valign="middle">
			<td colspan="5">
				<input type="submit" value="수정" >
				<input type="button" value="목록" onclick="location.href='../admin/boardListAction.yo?pageNum=${pageNum}'">			
			</td>
		</tr>
	</table>	
	</form>
</body>
</html>