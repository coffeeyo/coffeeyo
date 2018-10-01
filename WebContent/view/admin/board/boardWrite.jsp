<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link href="https://fonts.googleapis.com/css?family=Barrio"	rel="stylesheet">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>

	<script type="text/javascript">
		function checkValue(){
			var board_subject = $('#subject').val();
			var board_content = $('#content').val();
			
			if(!board_subject){
				alert("제목을 입력해주세요.")
				return false;
			}
			else if(!board_content){
				alert("내용을 입력해주세요.")
				return false;
			}
			
			$('#boardForm').submit();
		}
	
		
	</script>
</head>
<body>
<br>
<div class="topBbs">
<br>
	<b><font size="6" color="gray">글쓰기</font></b>
	<br>
</div>
<br>
<div id="board">
	<form method="post" action="../admin/boardWriteProcAction.yo" id="boardForm" name="boardForm" enctype="multipart/form-data">

	<table class="bbsForm" align="center">

		<tr>
			<td id="title">
				제 목
			</td>
			<td>
				<input id="subject" name="subject" type="text" size="70" maxlength="100"/>
			</td>		
		</tr>
		<tr>
			<td id="title">
				내 용
			</td>
			<td>
				<textarea  id="content" name="content" cols="72" rows="20"></textarea>			
			</td>		
		</tr>
		<tr>
			<td id="title">
				이미지첨부
			</td>
			<td>
				<input type="file" name="image" />
			</td>	
		</tr>
		<tr>
			<td id="title">공지유무</td>
			<td>
				<select name="notiyn">
					<option value="Y" selected>예</option>
					<option value="N">아니오</option>
				</select>
			</td>
		</tr>

		<tr align="center" valign="middle">
			<td colspan="5">
				<input type="reset" value="작성취소" >
				<input type="button" value="등록"  onclick="checkValue()">
				<input type="button" value="목록" onclick="location.href='../admin/boardListAction.yo'">			
			</td>
		</tr>
	</table>	
	</form>
</div>
</body>
</html>