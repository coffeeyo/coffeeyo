<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
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
		//댓글 삭제하기
	
	})

</script>
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
		</td>
	</tr>
	<tr>
		<td>첨부파일</td>
		<td colspan="3">
			<c:if test="${DATA.image ne null}">
				<a href="../view/upload/board/${DATA.image}"> <img src="../view/upload/board/${DATA.image}" border="0" /></a>
			</c:if>
			<c:if test="${DATA.image eq null}">
				첨부파일 없음
			</c:if>
		</td>
	</tr>
	<tr>
		<td colspan="4" align="center">
			<c:if test="${sessionScope.userid eq DATA.userid}">
				<input type="button" id="mBtn" value="수정">
				<input type="button" id="dBtn" value="삭제">
			</c:if>	
		</td>
	</tr>
</table>
<table border="1" align="center" width="600">
	<tr align="center">
		<td><input type="button" id="bBtn" value="목록보기" onclick="location.href='/board/boardBoardList.yo?oriNo=${oriNo}&nowPage=${nowPage }'"></td>
	</tr>
</table>
<%-- 댓글작성테이블 --%>
<c:if test="${not empty sessionScope.userid}">
	<form id="wrfrm" name="wrfrm" action="../board/commentWrite.yo" method="post">
		<input type="hidden" id="oriNo" name="oriNo" value="${oriNo}">
		<input type="hidden" id="nowPage" name="nowPage" value="${nowPage}">
		<table width="600" border="1" align="center">
			<tr>
				<td><textarea name="comm" id="comm" placeholder="댓글 작성란"></textarea></td>
				<td align="center">
					<input type="button" id="wrBtn" value="글등록" >
				</td>
			</tr>
		</table>
	</form>
</c:if>
<%-- 댓글이 존재한다면 반복해서 뿌려주기 --%>
<%-- 댓글이 없는 경우 --%>
<c:if test="${empty COMM }">
	<table border="1" width="600" align="center">
		<tr>
			<td align="center" style="color:red;">댓글을 작성해주세요</td>
		</tr>
	</table>
</c:if>
<%-- 댓글이 존재하는 경우.. 댓글수만큼 반복출력 --%>
<c:if test="${not empty COMM}">
	<c:forEach var="temp" items="${COMM}">
		<table id="${temp.bcidx }" border="1" width="600" align="center">
			<tr>
				<th>닉네임</th>
				<td>${temp.nick }</td>
			</tr>
			<tr>
				<td colspan="2">${temp.comm }</td>
			</tr>
			<tr>
				<td colspan="2" align="right">${temp.createdt }
					<c:if test="${temp.userid eq sessionScope.userid}">
						<input type="button" class="mCBtn" value="수정">
						<input type="button" class="dCBtn" value="삭제">
					</c:if>
				</td>
			</tr>
		</table>
		<%-- 댓글수정을 위한 임시폼 --%>
		<form id="${temp.bcidx}frm" method="post" action="../board/commentUpdate.yo" style="display:none;">
			<input type="hidden" name="reNo" value="${temp.bcidx }"/>
			<input type="hidden" name="oriNo" value="${temp.bidx }"/>
			<input type="hidden" name="nowPage" value="${nowPage }"/>
			<table border="1" width="600" align="center">
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
					<td colspan="2" align="right">${temp.createdt }</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input type="button" class="sCBtn" value="저장">
						<input type="button" class="bCBtn" value="취소">
					</td>
				</tr>
			</table>
		</form>
	</c:forEach>
</c:if>
<!--   

<%-- 댓글을 삭제하기 위한 임시폼을 만들어 놓는다 --%>
<form id="tempReFrm" action="../REBoard/ReplyDelete.re" method="post" > 
	<input type="hidden" name="reNo"  id="tempReNo"/>
	<input type="hidden" name="pw"  id="tempRePw"/>
	<input type="hidden" name="oriNo" id="tempReOriNo"/>
	<input type="hidden" name="nowPage"  id="tempReNowPage"/>
</form>
-->

</body>
</html>