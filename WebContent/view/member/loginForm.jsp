<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Barrio"
	rel="stylesheet">
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	$(function(){
		$('#joinBtn').click(function(){
			location.href = '/member/joinFormAction.yo';
		});
	});
</script>
</head>
<body>
<div class="loginFrm">
	<b><font size="6" color="gray">로그인</font></b>
	<br><br><br>
	
	<form name="login" method="post" action="/member/loginProcAction.yo" class="navbar-form navbar-left">
	    <div class="form-group">
	      <input type="text" name="id" placeholder="userid" class="form-control">
	    </div>
	    <div class="form-group">
	      <input type="password" name="pass" placeholder="password" class="form-control">
	    </div>
	    <button type="submit" class="btn btn-success">로그인</button>
	    <button type="button" id="joinBtn" class="btn btn-success">회원가입</button>
	</form>
</div>
</body>
</html>