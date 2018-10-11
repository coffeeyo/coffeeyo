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
		table{
			width:100%;
			margin-left:auto; 
			margin-right:auto;
			border:3px solid black;
		}
		
		td{
			border:1px solid black
		}
		.board  h1 {
			text-align: center;
		}
		#updBtn {
			float: right;
		}
		
		#title{
			
		}
	</style>
	
	<script type="text/javascript">
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
			
			$('#btnList').click(function(){
				var url = '/admin/memberListAction.yo?nowPage=${nowPage}'
				$(location).attr('href', url);
			});
		});
	</script>
	
</head>
<body>
<div id="wrap">
	<div class="board" >
		<h1>회원정보 수정</h1>
		<!-- 회원정보를 가져와 member 변수에 담는다. -->
		<c:set var="member" value="${requestScope.memberInfo}"/>
		
		<!-- 입력한 값을 전송하기 위해 form 태그를 사용한다 -->
		<!-- 값(파라미터) 전송은 POST 방식 -->
		<form method="post" action="/admin/memberUpdateProcAction.yo" 
				id="updateFrm" name="updateFrm" >
		<input type="hidden" name="userid" value="${member.userid}"/>
		<input type="hidden" name="nowPage" value="${nowPage}"/>
			<table>
				<tr>
					<td id="title">아이디</td>
					<td id="title">${member.userid}</td>
				</tr>
				<tr>
					<td id="title">비밀번호</td>
					<td>
						<input type="password" id="passwd"  name="passwd" maxlength="50" />
					</td>
				</tr>
				<tr>
					<td id="title">비밀번호 확인</td>
					<td>
						<input type="password" id="passwdChk"  name="passwdChk" maxlength="50" />
					</td>
				</tr>

				<tr>
					<td id="title">이름</td>
					<td>${member.uname}</td>
				</tr>
				
				<tr>
					<td id="title">닉네임</td>
					<td>${member.nick}</td>
				</tr>
				<tr>
					<td id="title">핸드폰번호</td>
					<td>
						<input type="text" name="hp" value="${member.hp}"/>
					</td>
				</tr>
				<tr>
					<td id="title">성별</td>
					<td>
					<input type="radio" name="gender" value="1" <c:if test="${member.gender eq 1}">checked</c:if> />남자&nbsp;&nbsp;
					<input type="radio" name="gender" value="2" <c:if test="${member.gender eq 2}">checked</c:if> />여자
					</td>
				</tr>
				
				<tr>
					<td id="title">생년월일</td>
					<td>
						<input type="date" name="birthday" value="${member.birthday}"/>
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
				<tr>
					<td id="title">등급</td>
					<td>
						<select name="ulevel">
							<c:if test="${member.ulevel ne 10}">
							<option value="1" <c:if test="${member.ulevel eq 1}">selected</c:if>>일반</option>
							</c:if>
							<option value="10" <c:if test="${member.ulevel eq 10}">selected</c:if>>운영자</option>
						</select>
					</td>
				</tr>
				<tr>
					<td id="title">상태</td>
					<td>
						<select name="status">
							<option value="1" <c:if test="${member.status eq 1}">selected</c:if>>정상</option>
							<option value="2" <c:if test="${member.status eq 2}">selected</c:if>>탈퇴</option>
						</select>
					</td>
				</tr>
			</table>
			<br><br>
			<div id="updBtn">
			<input type="button" id="btnUpdate" value="저장"/>  
			<input type="button" id="btnList" value="목록" />
			</div>
		</form>
		</div>
</div>
</body>
</html>