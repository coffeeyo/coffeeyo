<%@page import="com.coffeeyo.board.model.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
	
	//댓글 등록
	function writeCmt()
	{
		var board = $('#comment_board').val();
		var id = $('#comment_id').val();
		var content = $('#comment_content').val();
		
		if(!content)
		{
			alert("내용을 입력하세요.");
			return false;
		}
		else
		{	
			var param="comment_board="+board+"&comment_id="+id+"&comment_content="+content;
	
			$.ajax({
				url: '/board/commentWriteAction.yo',
				type: 'POST',
				data: param,
				dataType: 'JSON',
				success: function(data) {
					var resultText = data.check;
					
					if(resultText == 1){ 
						document.location.reload(); // 상세보기 창 새로고침
					}
				},
				error:function(){
					//
				}
			});
		}
	}
	
	
	// 댓글 삭제창
	function cmDeleteOpen(comment_num){
		var msg = confirm("댓글을 삭제합니다.");	
		if(msg == true){ // 확인을 누를경우
			deleteCmt(comment_num);
		}
		else{
			return false; // 삭제취소
		}
	}
	
	// 댓글 삭제
	function deleteCmt(comment_num)
	{
		var param="comment_num="+comment_num;
		
		$.ajax({
			url: '/board/commentDeleteAction.yo',
			type: 'POST',
			data: param,
			dataType: 'JSON',
			success: function(data) {
				var resultText = data.check;
				
				if(resultText == 1){ 
					document.location.reload(); // 상세보기 창 새로고침
				}
			},
			error:function(){
				//
			}
		});
	}
	
	// 댓글 수정창
	function cmUpdateOpen(comment_num){
		window.name = "parentForm";
		window.open("/popup/commentUpdateFormAction.yo?num="+comment_num,
					"updateForm", "width=570, height=350, resizable = no, scrollbars = no");
	}
	
	function addLinke() {
		var board = $('#comment_board').val();
				
		var param="num="+board;
		
		$.ajax({
			url: '/board/boardAddLikeAction.yo',
			type: 'POST',
			data: param,
			dataType: 'JSON',
			success: function(data) {
				var resultText = data.check;
				
				if(resultText == 1){ 
					document.location.reload(); // 상세보기 창 새로고침
				}
			},
			error:function(){
				//
			}
		});
	}
	
	function loginFail() {
		alert('로그인 후 투표 가능');
	}
</script>
</head>
<body>
<%
	// 줄바꿈 
	pageContext.setAttribute("br", "<br/>");
	pageContext.setAttribute("cn", "\n");
%>
<div id="wrap">
	<br><br>
	<div class="board">
		
	<h1>글내용보기</h1>
	<table border="1" class="bbs">
		<tr>
			<td>글제목</td>
			<td>${bo.subject}</td>
			<td>조회수</td>
			<td>${bo.readcnt}</td>
		</tr>
		<tr>
			<td>글쓴이</td>
			<td>${bo.nickName}</td>
			<td>작성일</td>
			<td>${bo.createdt}</td>
		</tr>
		<tr>
			<td>분류</td>
			<td>${cateInfo.cname}</td>
			<td>상품</td>
			<td>${prodInfo.pname}</td>
		</tr>
		
		<tr>
			<td>첨부파일</td>
			<td colspan="3">
				<c:if test="${bo.image ne null}">
					<a href="../view/upload/board/${bo.image}"> <img src="../view/upload/board/${bo.image}" border="0" title="${bo.subject}" alt="${bo.subject}" /></a>
				</c:if>
				<c:if test="${bo.image eq null}">
					첨부파일 없음
				</c:if>
			</td>
		</tr>
		<tr>
			<td>글내용</td>
			<td colspan="3">${bo.comm}</td>
		</tr>
		<tr>
			<td>(${bo.likecnt})
			<c:if test="${sessionScope.userid eq null}" ><c:set var="linkLink"  value="loginFail()"></c:set></c:if>
			<c:if test="${sessionScope.userid ne null}" ><c:set var="linkLink"  value="addLinke()"></c:set></c:if>
			<input type="button" value="좋아요" onclick="${linkLink}" /></td>
			<td colspan="4" align="right">
				<c:if test="${bo.userid == sessionScope.userid or sessionScope.ulevel > 9}">
					<input type="button" value="글수정"
					onclick="location.href='../board/boardUpdateFrmAction.yo?num=${num}&pageNum=${pageNum}'">
					<input type="button" value="글삭제"
					onclick="location.href = '../board/boardDeleteAction.yo?num=${num}&pageNum=${pageNum}'">
				</c:if>
				<input type="button" value="글목록"
				onclick="location.href = '../board/boardListAction.yo?pageNum=${pageNum}'"></td>
		</tr>
	</table>
	</div>
	
	<p/>
	
	<!-- 댓글 부분 -->
	<div class="comment">
		<table border="1" bordercolor="lightgray" class="bbscomm">
	<!-- 댓글 목록 -->	
	<c:if test="${requestScope.commentList != null}">
		<c:forEach var="comment" items="${requestScope.commentList}">
			<tr>
				<!-- 아이디, 작성날짜 -->
				<td width="150">
					<div>					
						${comment.nickName}<br>
						<font size="2" color="lightgray">${comment.createdt}</font>
					</div>
				</td>
				<!-- 본문내용 -->
				<td width="550">
					<div class="text_wrapper">
						${fn:replace(comment.comm, cn, br)}
					</div>
				</td>
				<!-- 버튼 -->
				<td width="100">
					<div  id="btn">
					<c:if test="${comment.userid == sessionScope.userid}">
						<a href="#" onclick="cmUpdateOpen(${comment.bcidx})">[수정]</a><br>	
						<a href="#" onclick="cmDeleteOpen(${comment.bcidx})">[삭제]</a>
					</c:if>		
					</div>
				</td>
			</tr>
			
		</c:forEach>
	</c:if>
			
			<!-- 로그인 했을 경우만 댓글 작성가능 -->
			<c:if test="${sessionScope.userid !=null}">
			<tr bgcolor="#F5F5F5">
			<form id="writeCommentForm">
				<input type="hidden" id="comment_board" name="comment_board" value="${num}">
				<input type="hidden" id="comment_id" name="comment_id" value="${sessionScope.userid}">
				<!-- 아이디-->
				<td width="150">
					<div>
						${sessionScope.nick}
					</div>
				</td>
				<!-- 본문 작성-->
				<td width="550">
					<div>
						<textarea id="comment_content" name="comment_content" rows="4" cols="70" ></textarea>
					</div>
				</td>
				<!-- 댓글 등록 버튼 -->
				<td width="100">
					<div id="btn">
						<p><a href="#" onclick="writeCmt()">[댓글등록]</a></p>	
					</div>
				</td>
			</form>
			</tr>
			</c:if>
		</table>
	</div>
	
</div>	

</body>
</html>