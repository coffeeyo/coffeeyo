<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.coffeeyo.member.model.Member" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<html>
<head>
	<title>현재 유저정보 출력화면</title>
	
	<style type="text/css">
		table{
			width:90%;
			margin-left:auto; 
			margin-right:auto;
			border:3px solid skyblue;
		}
		
		td{
			border:1px solid skyblue
		}
		
		#title{
			background-color:skyblue
		}
	</style>
	
	<script type="text/javascript">
	
		function changeForm(val){
			if(val == "-1"){
				location.href="/";
			}else if(val == "0"){
				location.href="/member/memberUpdateFormAction.do";
			}else if(val == "1"){
				if(confirm('탈퇴하시겠씁니까?')) {
					location.href="/member/memberLeaveAction.yo";
				}
			}
		}
		
	</script>
	
</head>
<body>
		<br><br>
		<b><font size="6" color="gray">내 정보</font></b>
		<br><br><br>
		
		<!-- 회원정보를 가져와 member 변수에 담는다. -->
		<c:set var="member" value="${requestScope.memberInfo}"/>
		
		<!-- 가져온 회원정보를 출력한다. -->
		<table>
			<tr>
				<td id="title">아이디</td>
				<td>${member.userid}</td>
			</tr>
						
			<tr>
				<td id="title">비밀번호</td>
				<td>${member.passwd}</td>
			</tr>
					
			<tr>
				<td id="title">이름</td>
				<td>${member.uname}</td>
			</tr>
			
			<tr>
				<td id="title">성별</td>
				<td>
					<select name="gender" disabled>
						<option value="0" <c:if test="${member.gender eq 0}">selected</c:if>>선택</option>
						<option value="1" <c:if test="${member.gender eq 1}">selected</c:if>>남</option>
						<option value="2" <c:if test="${member.gender eq 2}">selected</c:if>>여</option>
					</select>
				
				</td>
			</tr>
					
			<tr>
				<td id="title">생일</td>
				<td>
					${member.birthday}
				</td>
			</tr>
				
			<tr>
				<td id="title">휴대전화</td>
				<td>${member.hp}</td>
			</tr>
			
			<tr>
				<td id="title">직업</td>
				<td>
					<select name="job" disabled>
						<option value="0" <c:if test="${member.job eq 0}">selected</c:if>>선택</option>
						<option value="1" <c:if test="${member.job eq 1}">selected</c:if>>학생</option>
						<option value="2" <c:if test="${member.job eq 2}">selected</c:if>>회사원</option>
						<option value="3" <c:if test="${member.job eq 3}">selected</c:if>>주부</option>
						<option value="4" <c:if test="${member.job eq 4}">selected</c:if>>기타</option>
					</select>
				
				</td>
			</tr>
		</table>
		
		<br>
		<input type="button" value="뒤로" onclick="changeForm(-1)">
		<input type="button" value="회원정보 변경" onclick="changeForm(0)">
		<input type="button" value="회원탈퇴" onclick="changeForm(1)">
</body>
</html>