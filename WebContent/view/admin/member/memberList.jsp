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
	<style>
		td, th{padding:5px 10px;}
		.top_title{width:1000px; margin:auto; font-size: 30px; font-weight: bold;}
		.top_tab{border: 1px solid gray; border-collapse:collapse; width:1000px; margin:auto;}
		.firstrow{text-align:center;background-color:#af9885f5;font-weight:bold; border: 1px solid gray;}
		.contentrow{text-align:center;valign:middle;height:30px;border: 1px solid gray;}
		.top_b{text-align:right;width:1000px; margin:auto;margin-top:10px;}
		.lname{font-weight:bold; padding-right:10px; padding-left:20px;}
		.listup{width:1000px; margin:auto;}
	</style>
	<script>
		function memAdd() {
			alert('준비중! 개발 후순위~');
		}
		
		function memModify(id) {;
			$('#userid').val(id);
			$('#nowPage').val('${nowPage}');
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
				$('#memFrm').attr('action', '/admin/memberRestoreAction.yo');
				$('#memFrm').submit();
			}
		}
	</script>
</head>
<body>
	<!-- 회원목록 위 부분-->
	<div class="top_title" >
		<p>회원 목록</p>
	</div>
	<table class="top_tab" border="1">
	<tr>
		<td>
		<div class="lookup">
			<form action="/admin/memberListAction.yo" method="post">
			<span class="lname">검색옵션</span>
			<span class="input">
				<select name="opt">
					<option value="0">성명</option>
					<option value="1">아이디</option>
				</select>
			</span>
			<span class="input"><input type="text" name="condition" size="50px"/></span>
			<span class="btn"><input type="submit"  class="inbtn" name="search" value="조회" /></span>
			</form>
		</div>
		</td>
	</tr>
	</table>
	<table class="top_b">
		<tr>
			<td align="left">[ 전체 회원 수 :	${requestScope.listCount}]</td>
			<td>	
				<%--	
				<c:if test="${sessionScope.ulevel eq 10}">
					<p><input type="button" class="outbtn" id="pd_create" value="회원등록" /></p>
				</c:if>
				--%>
			</td>
		</tr>
	</table>
	
	<!-- 회원 목록 부분 -->
	<form id="memFrm" name="memFrm" action="/admin/memberUpdateFormAction.yo" method="post">
		<input type="hidden" id="userid" name="userid" />
		<input type="hidden" id="nowPage" name="nowPage" />
	</form>
	<table class="listup">	
		<tr class="firstrow">
			<td>아이디</td>
			<td>이름</td>
			<td>성별</td>
			<td>생년월일</td>
			<td>연락처</td>
			<td>가입일</td>
			<td>등급</td>
			<td>상태</td>
			<td>수정/삭제</td>
		</tr>

		<c:forEach var="member" items="${requestScope.memberList}">
			<tr class="contentrow">
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
					<input type="button" id="memModify" value="수정" class="btn btn-success" onclick="memModify('${member.userid}');" />
					<c:if test="${member.ulevel ne '10'}">
						<c:if test="${member.status eq 1}">
							<input type="button" id="memLeave" value="탈퇴" class="btn btn-danger" onclick="memLeave('${member.userid}');"/>
						</c:if>
						<c:if test="${member.status eq 2}">
							<input type="button" id="memLeave" value="복원" class="btn btn-success"  onclick="memRestore('${member.userid}');"/>
						</c:if>
					</c:if>
				 </td>
			</tr>
		</c:forEach>	
	</table>
	
	<br>
	<div class="bbsPageForm">
	<c:if test="${fn:length(requestScope.memberList) ne 0}">
		<c:if test="${PINFO.startPage ne 1}">
			<a href='/admin/memberListAction.yo?nowPage=${PINFO.startPage-1}'>[ 이전 ]</a>
		</c:if>
		
		<c:forEach var="page" begin="${PINFO.startPage}" end="${PINFO.endPage}">
			<c:if test="${PINFO.nowPage eq page}">
				${page}&nbsp;
			</c:if>
			<c:if test="${PINFO.nowPage ne page}">
				<a href='/admin/memberListAction.yo?nowPage=${page}'>${page}&nbsp;</a>
			</c:if>
		</c:forEach>
		
		<c:if test="${PINFO.endPage ne PINFO.totalPage }">
			<a href='/admin/memberListAction.yo?nowPage=${PINFO.endPage+1}'>[다음]</a>
		</c:if>
	</c:if>
	</div>
</body>
</html>