<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.coffeeyo.member.model.Member" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html lang="ko">
<head>
	<link href="https://fonts.googleapis.com/css?family=Barrio"	rel="stylesheet">
	<meta charset="UTF-8">
	<script>
		function memAdd() {
			alert('준비중! 개발 후순위~');
		}
		
		function memModify(id) {;
			$('#userid').val(id);
			$('#memFrm').attr('action', '/admin/memberUpdateFormAction.yo');
			$('#memFrm').submit();
		}
		
		function memLeave(id) {
			if(confirm('탈퇴처리 하겠습니까?')){
				$('#userid').val(id);
				$('#memFrm').attr('action', '/admin/memberLeaveAction.yo');
				$('#memFrm').submit();
			}
		} 
		
		function memRestore(id) {
			if(confirm('복원처리 하겠습니까?')){
				$('#userid').val(id);
				$('#memFrm').attr('action', '/admin/memberRestoreAction.do');
				$('#memFrm').submit();
			}
		}
	</script>
</head>
<body>
	<!-- 회원목록 위 부분-->
	<br>
	<div class="topBbs">
		<h1>
			회원 목록 [ 전체 회원수 : 
			${requestScope.listCount}]
			
		</h1>
		<c:if test="${sessionScope.ulevel eq 10}">
		<div class="bbs_link">
			<input type="button" value="회원등록" onclick="memAdd()"/>
		</div>
		</c:if>
	</div>

	<!-- 회원 목록 부분 -->
	<br>
	<form id="memFrm" name="memFrm" action="/admin/memberUpdateFormAction.yo" method="post">
		<input type="hidden" id="userid" name="userid" />
	</form>
	<div class="board">
	<table class="bbs">	
		<tr>
			<th>아이디</th>
			<th>이름</th>
			<th>성별</th>
			<th>생년월일</th>
			<th>연락처</th>
			<th>가입일</th>
			<th>등급</th>
			<th>상태</th>
			<th>수정/삭제</th>
		</tr>

		<c:forEach var="member" items="${requestScope.memberList}">
			<tr>
				<td>${member.userid}</td>
				<td>${member.uname}</td>
				<td>
					<c:if test="${member.gender eq 1 or member.gender eq 3}">남자</c:if>
					<c:if test="${member.gender eq 2 or member.gender eq 4}">운영자</c:if>
					<c:if test="${member.gender eq 0}"></c:if>
				</td>
				<td>${member.birthday}</td>
				<td>${member.hp}</td>
				<td>${member.createdt}</td>
				<td>
					<c:if test="${member.ulevel lt 10}">일반</c:if>
					<c:if test="${member.ulevel gt 9}">운영자</c:if>
				</td>
				<td>
					<c:if test="${member.status eq 1}">정상</c:if>
					<c:if test="${member.status eq 2}">탈퇴</c:if>
				</td>
				<td>
					<input type="button" id="memModify" value="수정" onclick="memModify('${member.userid}');" />
					<c:if test="${member.ulevel ne '10'}">
						<c:if test="${member.status eq 1}">
							<input type="button" id="memLeave" value="탈퇴" onclick="memLeave('${member.userid}');"/>
						</c:if>
						<c:if test="${member.status eq 2}">
							<input type="button" id="memLeave" value="복원" onclick="memRestore('${member.userid}');"/>
						</c:if>
					</c:if>
				 </td>
			</tr>
		</c:forEach>	
	</table>
	</div>
	
	<br>
	<div class="bbsPageForm">
	<c:if test="${fn:length(requestScope.memberList) > 0}">
		<c:if test="${startPage != 1}">
			<a href='/admin/memberListAction.yo?pageNum=${startPage-1}'>[ 이전 ]</a>
		</c:if>
		
		<c:forEach var="pageNum" begin="${startPage}" end="${endPage}">
			<c:if test="${pageNum == spage}">
				${pageNum}&nbsp;
			</c:if>
			<c:if test="${pageNum != spage}">
				<a href='/admin/memberListAction.yo?pageNum=${pageNum}'>${pageNum}&nbsp;</a>
			</c:if>
		</c:forEach>
		
		<c:if test="${endPage != maxPage }">
			<a href='/admin/memberListAction.yo?pageNum=${endPage+1 }'>[다음]</a>
		</c:if>
	</c:if>
	</div>
	<!--  검색 부분 -->
	<br>
	<div class="bbsSearchForm">
		<form>
			<select name="opt">
				<option value="0">성명</option>
				<option value="1">아이디</option>
			</select>
			<input type="text" size="20" name="condition"/>&nbsp;
			&nbsp;
			<input type="submit" value="검색"/>
		</form>	
	</div>
</body>
</html>