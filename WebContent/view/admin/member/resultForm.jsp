<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>결과 페이지</title>
	<script>
		$(function(){
			var lv = '${sessionScope.ulevel}';
			if(lv != 10) {
				alert('운영자만 이용 하실 수 있습니다.');
				location.href = '/';
			}
		});
		function goMemberInfo(id) {	
			$('#userid').val(id);
			$('#resFrm').submit();
		}
		function goMemberList() {
			location.href = '/admin/memberListAction.yo';
		}
	</script>
</head>
<body>
<div id="wrap">
	<br><br>
	<div class="board" style="text-align:center;">
	<form id="resFrm" name="resFrm" action="/admin/memberUpdateFormAction.yo" method="post">
		<input type="hidden" id="userid" name="userid" /> 
		<input type="hidden" id="nowPage" name="${nowPage}" /> 
	</form>
	<c:choose>
		<c:when test="${msg != null && msg == '0'}">
			<font size='6'>회원정보가 수정되었습니다.</font><br/>
			<input type="button" value="회원정보확인" onclick="goMemberInfo('${id}');"/>
			<script>
				$(function(){
					//setTimeout(goMemberInfo('${id}'),3000);
				});
			</script>
		</c:when>
		<c:when test="${msg != null && msg == '1'}">
			<font size='6'>회원가입 처리 완료.</font><br/>
			<input type="button" value="회원목록으로" onclick="goMemberList();"/>
			<script>
				$(function(){
					//setTimeout(goMemberList(),3000);
				});
			</script>
		</c:when>
		<c:when test="${msg == '2'}">
			<font size='6'>탈퇴되었습니다.</font><br/>
			<input type="button" value="회원목록으로" onclick="goMemberList();"/>
			<script>
				$(function(){
					//setTimeout(goMemberList(),3000);
				});
			</script>
		</c:when>
		<c:when test="${msg == '3'}">
			<font size='6'>복원되었습니다.</font><br/>
			<input type="button" value="회원목록으로" onclick="goMemberList();"/>
			<script>
				$(function(){
					setTimeout(goMemberList(),3000);
				});
			</script>
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
	</div>
</div>
</body>
</html>