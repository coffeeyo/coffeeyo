<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<br><br>
<b><font size="6" color="gray">로그인</font></b>
<br><br><br>

<form name="login" action="/member/loginProcAction.yo" class="navbar-form navbar-left">
    <div class="form-group">
      <input type="text" name="id" placeholder="userid" class="form-control">
    </div>
    <div class="form-group">
      <input type="password" name="pass" placeholder="password" class="form-control">
    </div>
    <button type="submit" class="btn btn-success">로그인</button>
</form>

</body>
</html>