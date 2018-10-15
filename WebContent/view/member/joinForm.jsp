<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
	<title>회원가입 화면</title>

	<style type="text/css">
		.top_title {width:1000px; text-align:left; margin:auto; font-size: 30px; font-weight: bold;}
		td, th{padding:5px 10px; border:1px solid gray;}
		.inner_tr {border:1px solid gray;}
		.inner_tb {margin:auto;width:1000px;}
		.item_col {background-color:#af9885f5; font-weight:bold; text-align:center;}
	</style>

	<script type="text/javascript">
	
		// 회원가입 화면의 입력값들을 검사한다.
		function checkValue()
		{
			var form = document.userInfo;
		
			if(!form.userid.value){
				alert("아이디를 입력하세요.");
				form.userid.focus();
				return false;
			}
			
			if(form.idDuplication.value != "idCheck"){
				alert("아이디 중복체크를 해주세요.");
				form.userid.focus();
				return false;
			}
			
			if(!form.passwd.value){
				alert("비밀번호를 입력하세요.");
				form.passwd.focus();
				return false;
			}
			
			// 비밀번호와 비밀번호 확인에 입력된 값이 동일한지 확인
			if(form.passwd.value != form.passwordcheck.value ){
				alert("비밀번호를 동일하게 입력하세요.");
				form.passwd.value = "";
				form.passwordcheck.value = "";
				form.passwd.focus();
				return false;
			}	
			
			if(!form.uname.value){
				alert("이름을 입력하세요.");
				form.uname.focus();
				return false;
			}
			
			if(!form.nick.value){
				alert("닉네임을 입력하세요.");
				form.nick.focus();
				return false;
			}
			
			if(form.nickDuplication.value != "nickCheck"){
				alert("닉네임 중복체크를 해주세요.");
				form.nick.focus();
				return false;
			}
						
			if(!form.hp.value){
				alert("핸드폰번호를 입력하세요.");
				form.hp.focus();
				return false;
			}
			
			if(isNaN(form.hp.value)){
				alert("전화번호는 - 제외한 숫자만 입력해주세요.");
				form.hp.focus();
				return false;
			}
			
		}
		
		// 취소 버튼 클릭시 첫화면으로 이동
		function goFirstForm() {
			location.href="/";
		}	
		
		// 아이디 중복체크 화면open
		function openIdChk(){
		
			window.name = "parentForm";
			window.open("/view/popup/idCheckForm.jsp",
					"chkForm", "width=500, height=300, resizable = no, scrollbars = no");	
		}

		// 아이디 입력창에 값 입력시 hidden에 idUncheck를 세팅한다.
		// 이렇게 하는 이유는 중복체크 후 다시 아이디 창이 새로운 아이디를 입력했을 때
		// 다시 중복체크를 하도록 한다.
		function inputIdChk(){
			document.userInfo.idDuplication.value ="idUncheck";
		}
		
		// 닉네임 중복체크 화면open
		function openNickChk(){
		
			window.name = "parentForm";
			window.open("/view/popup/nickCheckForm.jsp",
					"chkForm", "width=500, height=300, resizable = no, scrollbars = no");	
		}

		// 닉네임 입력창에 값 입력시 hidden에 nickUncheck를 세팅한다.
		// 이렇게 하는 이유는 중복체크 후 다시 닉네임 창이 새로운 닉네임를 입력했을 때
		// 다시 중복체크를 하도록 한다.
		function inputNickChk(){
			document.userInfo.nickDuplication.value ="nickUncheck";
		}
		
	</script>
	
</head>
<body>
	<div class="top_title" >
		<p>회원가입</p>
	</div>
	<!-- 입력한 값을 전송하기 위해 form 태그를 사용한다 -->
	<!-- 값(파라미터) 전송은 POST 방식, 전송할 페이지는 JoinPro.jsp -->
	<form method="post" action="/member/joinProcAction.yo" 
			name="userInfo" onsubmit="return checkValue()">
		<table class="inner_tb">
			<tr class="inner_tr">
				<td class="item_col">아이디</td>
				<td>
					<input type="text" name="userid" maxlength="50" onkeydown="inputIdChk()">
					<input type="button" value="중복확인" class="btn btn-info"  onclick="openIdChk()">	
					<input type="hidden" name="idDuplication" value="idUncheck" >
				</td>
			</tr>
					
			<tr class="inner_tr">
				<td class="item_col">비밀번호</td>
				<td>
					<input type="password" name="passwd" maxlength="50">
				</td>
			</tr>
			
			<tr class="inner_tr">
				<td class="item_col">비밀번호 확인</td>
				<td>
					<input type="password" name="passwordcheck" maxlength="50">
				</td>
			</tr>
				
			<tr class="inner_tr">
				<td class="item_col">이름</td>
				<td>
					<input type="text" name="uname" maxlength="50">
				</td>
			</tr>
			
			<tr class="inner_tr">
				<td class="item_col">닉네임</td>
				<td>
					<input type="text" name="nick" maxlength="50" onkeydown="inputNickChk()">
					<input type="button" value="중복확인" class="btn btn-info"  onclick="openNickChk()">	
					<input type="hidden" name="nickDuplication" value="nickUncheck" >
				</td>
			</tr>
				
			<tr class="inner_tr">
				<td class="item_col">성별</td>
				<td>
					<input type="radio" name="gender" value="1" checked>남
					<input type="radio" name="gender" value="2" >여
				</td>
			</tr>
				
			<tr class="inner_tr">
				<td class="item_col">휴대전화</td>
				<td>
					<input type="text" name="hp"/>
				</td>
			</tr>
			<tr align="center" valign="middle" >
				<td colspan="2" >
					<input type="button" value="취소"  class="btn btn-default" onclick="goFirstForm()">
					<input type="submit" value="가입" class="btn btn-success" />  
				</td>
			</tr>
		</table>		
	</form>
</body>
</html>