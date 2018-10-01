<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>      
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<title> 댓글 답변 </title>
	
	<style type="text/css">
		#wrap {
			width: 550px;
			margin: 0 auto 0 auto;
			text-align :center;
		}
	
		#commentUpdateForm{
			text-align :center;
		}
	</style>
	
	<script type="text/javascript">
		function checkValue()
		{
			var form = document.forms[0];
			// 전송할 값을 변수에 담는다.	
			var comment_num = $('#comment_num').val();
			var comment_content = $('#comment_content').val();
			
			if(!comment_content)
			{
				alert("내용을 입력하세요");
				return false;
			}
			else{
				var param="comment_num="+comment_num+"&comment_content="+comment_content;
				
				$.ajax({
					url: '../board/commentUpdateAction.yo',
					type: 'POST',
					data: param,
					dataType: 'JSON',
					success: function(data) {
						var resultText = data.check;
						
						if(resultText == 1){ 
							if (opener != null) {
								window.opener.document.location.reload(); 
								opener.updateForm = null;
						        self.close();
							}
						}
					},
					error:function(){
						//
					}
				});
			}
		}
		
	</script>
	
</head>
<body>
<div id="wrap">
	<br>
	<b><font size="5" color="gray">댓글수정</font></b>
	<hr size="1" width="550">
	<br>

	<div id="commentUpdateForm">
		<form name="updateInfo" target="parentForm">
			<input type="hidden" id="comment_num" name="comment_num" value="${comment.bcidx}"/>
			<textarea rows="7" cols="70" id="comment_content" name="comment_content">${comment.comm}</textarea>
			<br><br>
			<input type="button" value="등록" onclick="checkValue()">
			<input type="button" value="창닫기" onclick="window.close()">
		</form>
	</div>
</div>	
</body>
</html>