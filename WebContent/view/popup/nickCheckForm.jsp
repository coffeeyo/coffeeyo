<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<title>���̵� �ߺ� üũ</title>
	
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
			// ȸ������â�� �г��� �Է¶��� ���� �����´�.
			$('#nick').val(opener.document.userInfo.nick.value);
		});
		
		// ���̵� �ߺ�üũ
		function nickCheck(){

			var nick = document.getElementById("nick").value;

			var blank_pattern = /[\s]/g;
			var special_pattern = /[`~!@#$%^&*|\\\'\";:\/?]/gi;

			
			if (!nick || nick.length == 0) {
				alert("�г����� �Է����� �ʾҽ��ϴ�.");
				return false;
			}
			else if( blank_pattern.test(nick) == true){
			    alert('�г��ӿ� ������ ����� �� �����ϴ�.');
			    return false;
			}
			if( special_pattern.test(nick) == true ){
			    alert('�г��ӿ� Ư�����ڴ� ����� �� �����ϴ�.');
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
							alert("����Ҽ����´г����Դϴ�.");
							$("#cancelBtn").css('visibility','visible');
							$("#useBtn").css('visibility','hidden');
							$("#msg").html("");
						} 
						else if(resultText == 1){ 
							$("#cancelBtn").css('visibility','hidden');
							$("#useBtn").css('visibility','visible');
							$("#msg").html("��� ������ �г����Դϴ�.");
						}
					},
					error:function(){
						//
					}
				});
			}
		}
		
		
		// ����ϱ� Ŭ�� �� �θ�â���� �� ���� 
		function sendCheckValue(){
			// �ߺ�üũ ����� idCheck ���� �����Ѵ�.
			opener.document.userInfo.nickDuplication.value ="nickCheck";
			// ȸ������ ȭ���� ID�Է¶��� ���� ����
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
	<b><font size="4" color="gray">���̵� �ߺ�üũ</font></b>
	<hr size="1" width="460">
	<br>
	<div id="chk">
		<form id="checkForm">
			<input type="text" name="idinput" id="nick">
			<input type="button" value="�ߺ�Ȯ��" onclick="nickCheck()">
		</form>
		<div id="msg"></div>
		<br>
		<input id="cancelBtn" type="button" value="���" onclick="window.close()"><br>
		<input id="useBtn" type="button" value="����ϱ�" onclick="sendCheckValue()">
	</div>
</div>	
</body>
</html>