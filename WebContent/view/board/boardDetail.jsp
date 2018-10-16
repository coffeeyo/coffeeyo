<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	h3 {
		color:#7C5D44;
	}
	table {
		border-collapse:collapse;
	}

	.style1 tr, .style1 td {
		border-bottom:2px solid #d2bdad;
	}

	.button:hover, .btn2:hover {
		background-color: #f6f2ef;
		font-weight:bold;
	    color:black;
	}
	.button {
	    width:80px;
	    background-color: #d2bdad;
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
	.btn2 {
	    width:50px;
	    background-color: #d2bdad;
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
	textarea {
		width:100%;
	}
	.brdImage {
		height:500px;
		padding:10px; 
	}
	.style2 {
	}
	.style2 tr {
		border-bottom:1px solid #d2bdad;
		background-color:white;
		color:black;
	}
	
</style>
<script>
	$(function(){
		
		//원글 수정하기
		$("#mBtn").click(function(){
			$(location).attr("href","../board/boardUpdateForm.yo?oriNo=${oriNo}&nowPage=${nowPage}");
		})
		//원글 삭제하기 
		$("#dBtn").click(function(){
			if(confirm("정말로 삭제하시겠습니까?")){
				$(location).attr("href","../board/boardBoardDelete.yo?oriNo=${oriNo}&nowPage=${nowPage}");				
			}
		})
		//댓글 등록하기
		$("#wrBtn").click(function(){
			$("#wrfrm").submit();
		})
		
		//댓글수정버튼을 클릭하면 댓글수정폼으로 변경되도록 하고싶다
		$(".mCBtn").click(function(){
			var button=$(this);
			var table=button.parents("table");
			var id=table.attr("id");
			//테이블을 감추자
			$("#"+id).hide();
			//수정폼을 보여주자
			$("#"+id+"frm").show();
		})
		//댓글 수정등록하기
		$(".sCBtn").click(function(){
			var button=$(this);
			var form=button.parents("form");
			$(form).submit();
		})
		//댓글 수정취소하기
		$(".bCBtn").click(function(){
			var button=$(this);
			var table=button.parents("table");
			var id=table.attr("id");
			$(location).attr("href","../board/boardBoardDetail.yo?reNo="+id+"&oriNo=${oriNo}&nowPage=${nowPage}");
		})
		//댓글 삭제하기
		$(".dCBtn").click(function(){
			if(confirm("정말로 삭제하시겠습니까?")){
				var button=$(this);
				var table=button.parents("table");
				var id=table.attr("id");
				$(location).attr("href","../board/commentDelete.yo?reNo="+id+"&oriNo=${oriNo}&nowPage=${nowPage}");
			}
		})	
	})
</script>
</head>
<body>
<%-- 게시판 상세보기 --%>
<table align="center" width="70%">
	<tr>
		<td>
			<h3 align="left">게시판 상세보기</h3>
		</td>
	</tr>
</table>
<table align="center" width="70%" class="style1">
	<tr height="30px">
		<td colspan="2"><b>${DATA.subject }</b></td>
	</tr>
	<tr height="30px">
		<td align="left">작성자:${DATA.nick } | 분류:${DATA.cname } | 상품명:${DATA.pname }</td>
		<td align="right"> ${DATA.createdt } 조회${DATA.readcnt } 추천${DATA.likecnt }</td>
	</tr>
	<tr height="30px">
		<td colspan="2" align="center">
		${DATA.comm }
		<br>
		<c:if test="${DATA.image ne null}">
		<a href="../view/upload/board/${DATA.image}"> <img src="../view/upload/board/${DATA.image}"  class="brdImage"/></a>
		</c:if>
		</td>
	</tr>
	<c:if test="${sessionScope.userid eq DATA.userid}">
	<tr>
		<td colspan="4" align="center">
				<input type="button" id="mBtn" value="수정" class="button" >
				<input type="button" id="dBtn" value="삭제" class="button">
		</td>
	</tr>
	</c:if>	
</table>
<%-- 좋아요체크를 위한 form --%>
<c:if test="${not empty sessionScope.userid}">
	<form id="LCheckfrm" action="../board/boardAddLike.yo" method="post">
		<input type="hidden" name="oriNo" value="${oriNo}">
		<input type="hidden" name="nowPage" value="${nowPage}">
		<input type="hidden" name="userid" value="${sessionScope.userid}">
		
		<table align="center" width="70%">
			<tr align="center">
				<td><input type="submit" id="LBtn" value="추천/취소" class="button"></td>
			</tr>
		</table>
	</form>
</c:if>
<br>
<%-- 댓글작성테이블 --%>
<c:if test="${not empty sessionScope.userid}">
	<form id="wrfrm" name="wrfrm" action="../board/commentWrite.yo" method="post">
		<input type="hidden" id="oriNo" name="oriNo" value="${oriNo}">
		<input type="hidden" id="nowPage" name="nowPage" value="${nowPage}">
		<table align="center" width="70%">
			<tr>
				<td><textarea name="comm" id="comm" placeholder="댓글 작성란"></textarea></td>
				<td width="10%" align="center"><input type="button" id="wrBtn" value="글등록"  class="button"></td>
			</tr>
		</table>
	</form>
</c:if>
<br>
<%-- 댓글이 존재한다면 반복해서 뿌려주기 --%>
<%-- 댓글이 없는 경우 --%>
<c:if test="${empty COMM }">
	<table width="70%" align="center">
		<tr>
			<td align="center"><b>"첫번째 댓글을 작성해주세요"</b></td>
		</tr>
	</table>
</c:if>
<%-- 댓글이 존재하는 경우.. 댓글수만큼 반복출력 --%>
<c:if test="${not empty COMM}">
	<c:forEach var="temp" items="${COMM}">
		<table id="${temp.bcidx }"  width="70%" align="center" class="style2">
			<tr>
				<td width="20%"><b>${temp.nick }</b></td>
				<td width="50%">${temp.comm }</td>
				<td width="30%" align="right">
				${temp.createdt }
				<c:if test="${temp.userid eq sessionScope.userid}">
					<input type="button" class="mCBtn btn2" value="수정">
					<input type="button" class="dCBtn btn2" value="삭제" > 
				</c:if>
				</td>
			</tr>
		</table>
		<%-- 댓글수정을 위한 임시폼 --%>
		<form id="${temp.bcidx}frm" method="post" action="../board/commentUpdate.yo" style="display:none;">
			<input type="hidden" name="reNo" value="${temp.bcidx }"/>
			<input type="hidden" name="oriNo" value="${temp.bidx }"/>
			<input type="hidden" name="nowPage" value="${nowPage }"/>
			<table width="70%" align="center" class="style2">
				<tr>
					<th>닉네임</th>
					<td>${temp.nick }</td>
				</tr>
				<tr>
					<td colspan="2">
						<textarea name="comm" id="mComm" placeholder="댓글 수정란">${temp.getCommNbr()}</textarea>
					</td>
				</tr>
				<tr>				
					<td colspan="2" align="right">${temp.createdt }
						<input type="button" class="sCBtn btn2" value="저장">
						<input type="button" class="bCBtn btn2" value="취소">
					</td>
			</table>
		</form>
	</c:forEach>
</c:if>
<br>
<table align="center" width="70%">
	<tr align="center">
		<td><input type="button" id="bBtn" value="목록보기"  class="button" onclick="location.href='/board/boardBoardList.yo?oriNo=${oriNo}&nowPage=${nowPage }'"></td>
	</tr>
</table>
</body>
</html>