<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.coffeeyo.member.model.MemberDAO" %>    
<%@ page import="com.coffeeyo.member.model.Member" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 

<html>
<head>

	<title>회원정보 수정화면</title>
	
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
		// 비밀번호 입력여부 체크
		function checkValue() {
			
		}
	</script>
	
</head>
<body>

		<br><br>
		<b><font size="6" color="gray">회원정보 수정</font></b>
		<br><br><br>
		<!-- 회원정보를 가져와 member 변수에 담는다. -->
		<c:set var="member" value="${requestScope.memberInfo}"/>
		
		<!-- 입력한 값을 전송하기 위해 form 태그를 사용한다 -->
		<!-- 값(파라미터) 전송은 POST 방식 -->
		<form method="post" action="/member/memberUpdateProcAction.yo" 
				name="userInfo" onsubmit="return checkValue()">
				
			<table>
				<tr>
					<td id="title">아이디</td>
					<td id="title">${member.userid}</td>
				</tr>
				<tr>
					<td id="title">비밀번호</td>
					<td>
						<input type="password" name="passwd" maxlength="50" 
							value="${member.passwd}">
					</td>
				</tr>
			</table>	
			<br><br>	
			<table>

				<tr>
					<td id="title">이름</td>
					<td>${member.uname}</td>
				</tr>
					
				<tr>
					<td id="title">성별</td>
					<td>
						<select name="gender">
							<option value="0" <c:if test="${member.gender eq 0}">selected</c:if>>선택</option>
							<option value="1" <c:if test="${member.gender eq 1}">selected</c:if>>남</option>
							<option value="2" <c:if test="${member.gender eq 2}">selected</c:if>>여</option>
						</select>
					
					</td>
				</tr>
				<tr>
					<td id="title">닉네임</td>
					<td>${member.nick}</td>
				</tr>
				<tr>
					<td id="title">생일</td>
					<td>
						<input type="date" name="birthday" value="${member.birthday}"/>
					</td>
				</tr>
					
				<tr>
					<td id="title">휴대전화</td>
					<td>
						<input type="text" name="hp" value="${member.hp}"/>
					</td>
				</tr>
				<tr>
					<td id="title">직업</td>
					<td>
						<select name="job">
							<option value="0" <c:if test="${member.job eq 0}">selected</c:if>>선택</option>
							<option value="1" <c:if test="${member.job eq 1}">selected</c:if>>학생</option>
							<option value="2" <c:if test="${member.job eq 2}">selected</c:if>>회사원</option>
							<option value="3" <c:if test="${member.job eq 3}">selected</c:if>>주부</option>
							<option value="4" <c:if test="${member.job eq 4}">selected</c:if>>기타</option>
						</select>
					
					</td>
				</tr>
			</table>
			<br><br>
			<input type="button" value="취소" onclick="javascript:window.location='/'">
			<input type="submit" value="수정"/>  
		</form>
		
</body>
</html>