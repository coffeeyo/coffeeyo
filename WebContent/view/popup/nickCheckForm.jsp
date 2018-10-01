<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<title>아이디 중복 체크</title>
	
	<style type="text/css">
		#wrap {
			width: 490px;
			text-align :center;
			margin: 0 auto 0 auto;
		}
		
		#chk{
			text-align :center;
		}
		
		#cancelBtn{
			visibility:visible;
		}
		
		#useBtn{
			 visibility:hidden;
		}

	</style>
	
	<script type="text/javascript">
	
		
		$(function(){
			// 회원가입창의 닉네임 입력란의 값을 가져온다.
			$('#nick').val(opener.document.userInfo.nick.value);
		});
		
		// 아이디 중복체크
		function nickCheck(){

			var nick = document.getElementById("nick").value;

			var blank_pattern = /[\s]/g;
			var special_pattern = /[`~!@#$%^&*|\\\'\";:\/?]/gi;

			
			if (!nick || nick.length == 0) {
				alert("닉네임을 입력하지 않았습니다.");
				return false;
			}
			else if( blank_pattern.test(nick) == true){
			    alert('닉네임에 공백은 사용할 수 없습니다.');
			    return false;
			}
			if( special_pattern.test(nick) == true ){
			    alert('닉네임에 특수문자는 사용할 수 없습니다.');
			    return false;
			}
			else
			{
				var param="nick="+nick;
				
				$.ajax({
					url: '/popup/memberNickCheckAction.yo',
					type: 'POST',
					data: param,
					dataType: 'JSON',
					success: function(data) {
						//console.log(data.check);
						var resultText = data.check;
						
						if(resultText == 0){
							alert("사용할수없는닉네임입니다.");
							$("#cancelBtn").css('visibility','visible');
							$("#useBtn").css('visibility','hidden');
							$("#msg").html("");
						} 
						else if(resultText == 1){ 
							$("#cancelBtn").css('visibility','hidden');
							$("#useBtn").css('visibility','visible');
							$("#msg").html("사용 가능한 닉네임입니다.");
						}
					},
					error:function(){
						//
					}
				});
			}
		}
		
		
		// 사용하기 클릭 시 부모창으로 값 전달 
		function sendCheckValue(){
			// 중복체크 결과인 idCheck 값을 전달한다.
			opener.document.userInfo.nickDuplication.value ="nickCheck";
			// 회원가입 화면의 ID입력란에 값을 전달
			opener.document.userInfo.nick.value = document.getElementById("nick").value;
			
			if (opener != null) {
            	opener.chkForm = null;
            	self.close();
			}	
		}	
	</script>
	
</head>
<body>
<div id="wrap">
	<br>
	<b><font size="4" color="gray">아이디 중복체크</font></b>
	<hr size="1" width="460">
	<br>
	<div id="chk">
		<form id="checkForm">
			<input type="text" name="idinput" id="nick">
			<input type="button" value="중복확인" onclick="nickCheck()">
		</form>
		<div id="msg"></div>
		<br>
		<input id="cancelBtn" type="button" value="취소" onclick="window.close()"><br>
		<input id="useBtn" type="button" value="사용하기" onclick="sendCheckValue()">
	</div>
</div>	
</body>
</html>