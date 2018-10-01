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
			// ȸ������â�� ���̵� �Է¶��� ���� �����´�.
			$('#userId').val(opener.document.userInfo.userid.value);
		});
		
		
		// ���̵� �ߺ�üũ
		function idCheck(){

			var id = $("#userId").val();

			if (!id) {
				alert("���̵� �Է����� �ʾҽ��ϴ�.");
				return false;
			} 
			else if( (id < "0" || id > "9") && (id < "A" || id > "Z") && (id < "a" || id > "z" ) ){ 
				alert("�ѱ� �� Ư�����ڴ� ���̵�� ����Ͻ� �� �����ϴ�.");
				return false;
			}
			else
			{
				var param="id="+id;
				
				$.ajax({
					url: '/popup/memberIdCheckAction.yo',
					type: 'POST',
					data: param,
					dataType: 'JSON',
					success: function(data) {
						//console.log(data.check);
						var resultText = data.check;
						
						if(resultText == 0){
							alert("����Ҽ����� ���̵��Դϴ�.");
							$("#cancelBtn").css('visibility','visible');
							$("#useBtn").css('visibility','hidden');
							$("#msg").html("");
						} 
						else if(resultText == 1){ 
							$("#cancelBtn").css('visibility','hidden');
							$("#useBtn").css('visibility','visible');
							$("#msg").html("��� ������ ���̵��Դϴ�.");
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
			opener.document.userInfo.idDuplication.value ="idCheck";
			// ȸ������ ȭ���� ID�Է¶��� ���� ����
			opener.document.userInfo.id.value = $("#userId").val();
			
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
			<input type="text" name="idinput" id="userId">
			<input type="button" value="�ߺ�Ȯ��" onclick="idCheck()">
		</form>
		<div id="msg"></div>
		<br>
		<input id="cancelBtn" type="button" value="���" onclick="window.close()"><br>
		<input id="useBtn" type="button" value="����ϱ�" onclick="sendCheckValue()">
	</div>
</div>	
</body>
</html>