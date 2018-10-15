<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>결과 페이지</title>
	<script>
		function goMemberInfo(id) {	
			$('#userid').val(id);
			$('#resFrm').submit();
		}
		
		function goHome() {
			location.href = '/';
		}
	</script>
</head>
<body>
<div id="wrap">
	<br><br>
	<div class="board" style="text-align:center;">
	<form id="resFrm" name="resFrm" action="../member/memberUpdateFormAction.yo" method="post">
		<input type="hidden" id="userid" name="userid" /> 
	</form>
	<c:choose>
		<c:when test="${msg!=null && msg=='0'}">
			<font size='6'>회원정보가 수정되었습니다.</font>
			<script>
				$(function(){
					//setTimeout(goMemberInfo('${id}'),10000);
				});
			</script>
		</c:when>
		<c:when test="${msg!=null && msg=='1'}">
			<font size='6'>${sessionScope.nick} 님 가입을 환영합니다. </font>
			<c:remove var="msg" scope="session"></c:remove>
			<script>
				$(function(){
					//setTimeout(goHome(),10000);
				});
			</script>
		</c:when>
		<c:otherwise>
			<font size='6'>탈퇴되었습니다.</font>
			<script>
				$(function(){
					//setTimeout(goHome(),10000);
				});
		</script>
		</c:otherwise>
		
	</c:choose>

	<br><br>
	<input type="button" value="메인으로" onclick="javascript:window.location='/'"/>
	</div>
</div>
</body>
</html>