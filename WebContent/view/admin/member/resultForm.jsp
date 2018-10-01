<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<html>
<head>
	<title>결과 페이지</title>
	<script>
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
	<br><br><br>
	<form id="resFrm" name="resFrm" action="/admin/memberUpdateFormAction.yo" method="post">
		<input type="hidden" id="userid" name="userid" /> 
	</form>
	<c:set var="msg" value="${sessionScope.msg}" scope="session" />
	<c:set var="id" value="${sessionScope.id}" scope="session" />
	<c:choose>
		<c:when test="${msg!=null && msg=='0'}">
			<font size='6'>회원정보가 수정되었습니다.</font><br/>
			<input type="button" value="회원정보확인" onclick="goMemberInfo('${id}');"/>
			<script>
				$(function(){
					setTimeout(goMemberInfo('${id}'),3000);
				});
			</script>
		</c:when>
		<c:when test="${msg!=null && msg=='1'}">
			<font size='6'>회원가입 처리 완료.</font><br/>
			<input type="button" value="회원목록으로" onclick="goMemberList();"/>
			<script>
				$(function(){
					setTimeout(goMemberList(),3000);
				});
			</script>
		</c:when>
		<c:when test="${msg=='2'}">
			<font size='6'>탈퇴되었습니다.</font><br/>
			<input type="button" value="회원목록으로" onclick="goMemberList();"/>
			<script>
				$(function(){
					setTimeout(goMemberList(),3000);
				});
			</script>
		</c:when>
		<c:when test="${msg=='3'}">
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
	
</body>
</html>