<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.coffeeyo.member.model.MemberDAO" %>    
<%@ page import="com.coffeeyo.member.model.Member" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
	<title>회원정보 수정화면</title>
	
	<style type="text/css">
		.top_title {width:1000px; text-align:left; margin:auto; font-size: 30px; font-weight: bold;}
		td, th{padding:5px 10px; border:1px solid gray;}
		.inner_tr {border:1px solid gray;}
		.inner_tb {margin:auto;width:1000px;}
		.item_col {background:lightgray; font-weight:bold; text-align:center;}
	</style>
	
	<script type="text/javascript">
		// 비밀번호 입력여부 체크
		$(function(){
			$('#btnUpdate').click(function(){
				var pwd = $('#passwd').val();
				var pwdChk = $('#passwdChk').val();
				if(pwd != null) {
					if(pwd != pwdChk) {
						alert('비밀번호 확인 결과 일치하지 않습니다.');
						$('#passwd').val('');
						$('#passwdChk').val('');
						$('#passwd').focus();
						return;
					}
				}
				$('#updateFrm').submit();
			});
		});
	</script>
</head>
<body>
	<div class="top_title" >
		<p>회원정보 수정</p>
	</div>		
	<!-- 회원정보를 가져와 member 변수에 담는다. -->
	<c:set var="member" value="${requestScope.memberInfo}"/>
	
	<!-- 입력한 값을 전송하기 위해 form 태그를 사용한다 -->
	<!-- 값(파라미터) 전송은 POST 방식 -->
	<form method="post" action="../member/memberUpdateProcAction.yo" 
			id="updateFrm" name="updateFrm">
			
		<table border="1" class="inner_tb">
			<tr>
				<td id="item_col">아이디</td>
				<td>${member.userid}</td>
			</tr>
			<tr>
				<td id="item_col">비밀번호</td>
				<td>
					<input type="password"  id="passwd" name="passwd" maxlength="50" />
				</td>
			</tr>
			<tr>
				<td id="item_col">비밀번호 확인</td>
				<td>
					<input type="password" id="passwdChk"  name="passwdChk" maxlength="50" />
				</td>
			</tr>
			<tr>
				<td id="item_col">성명</td>
				<td>${member.uname}</td>
			</tr>
			<tr>
				<td id="item_col">닉네임</td>
				<td>${member.nick}</td>
			</tr>
			<tr>
				<td id="item_col">핸드폰번호</td>
				<td>
					<input type="text" name="hp" value="${member.hp}"/>
				</td>
			</tr>
			<tr>
				<td id="item_col">성별</td>
				<td>
				<input type="radio" name="gender" value="1" <c:if test="${member.gender eq 1}">checked</c:if> />남자&nbsp;&nbsp;
				<input type="radio" name="gender" value="2" <c:if test="${member.gender eq 2}">checked</c:if> />여자
				</td>
			</tr>
			<tr>
				<td id="item_col">생년월일</td>
				<td>
					<input type="date" name="birthday" value="${member.birthday}"/>
				</td>
			</tr>
			<tr>
				<td id="item_col">직업</td>
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
			<tr align="center" valign="middle" >
				<td colspan="2" >
					<input type="button" value="취소" class="btn btn-default" onclick="javascript:window.location='/'">
					<input type="button" id="btnUpdate" value="수정" class="btn btn-success" />  
				</td>
			</tr>
		</table>
	</form>
</body>
</html>