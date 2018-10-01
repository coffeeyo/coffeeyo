<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
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
	
		$(function(){
			$('select[name="cidx"]').change(function() {
				//$.ajax 이용
				//$(this).val()
				//#pidx 에 상품 리스트 변경
				var cate = $(this).val();
				// cate = 0 이면 상품검색 중단
				if(cate == 0) return;
				//alert(cate);
				var param = "cidx="+cate;
				//alert(param);
				//return false;
				$.ajax({
					url: '/popup/prodListAllAction.yo',
					type: 'POST',
					data: param,
					dataType: 'JSON',
					success: function(data) {
						var resultText = data.prodList;
						//alert('resultText:'+resultText[0].pidx);
						var select = $('#pidx');
						var option0 = $('<option>');
						select.empty();
						
						var option1 = $('<option>').attr('value', '0').text('분류선택');
						select.append(option1);
						
						if(resultText.length > 0){ 
							//#pidx 에 상품 리스트 변경
							for(var i = 0; i < resultText.length; i++) {
								var pidx = resultText[i].pidx;
								var pname = resultText[i].pname;
								
								var option2 = $('<option>').attr('value', pidx).text(pname);
								select.append(option2);
							}
						}
					},
					error:function(){
						//
					}
				});
			});
		});
		
		
	</script>
</head>
<body>

<div class="topBbs">
<br>
	<b><font size="6" color="gray">글쓰기</font></b>
	<br>
</div>

<div id="board">
	<form method="post" action="../board/boardWriteProcAction.yo" id="boardForm" name="boardForm" enctype="multipart/form-data">

	<table class="bbsForm" align="center">
		<tr>
			<td id="title">분류</td>
			<td>
				<select id="cidx" name="cidx">
					<option value="0" selected>분류선택</option>
					<c:if test="${fn:length(requestScope.cateList) > 0}">
						<c:forEach var="cate" items="${requestScope.cateList}">
							<option value="${cate.cidx}">${cate.cname}</option>
						</c:forEach>
					</c:if>
				</select>
			</td>
		</tr>
		<tr>
			<td id="title">상품</td>
			<td>
				<select id="pidx" name="pidx">
				</select>
			</td>
		</tr>
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

		<tr align="center" valign="middle">
			<td colspan="5">
				<input type="reset" value="작성취소" >
				<input type="button" value="등록"  onclick="checkValue()">
				<input type="button" value="목록" onclick="location.href='/board/boardListAction.yo'">			
			</td>
		</tr>
	</table>	
	</form>
</div>
</body>
</html>