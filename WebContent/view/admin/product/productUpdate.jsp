<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<link href="https://fonts.googleapis.com/css?family=Barrio"	rel="stylesheet">
	<meta charset="UTF-8">
	<title>Insert title here</title>

	<script type="text/javascript">
		function checkValue(){
			var prod_subject = $('#pname').val();
			var prod_content = $('#comm').val();
			var prod_price = $('#price').val();
			var prod_maketm = $('#maketm').val();
			
			if(!prod_subject){
				alert("상품명을 입력해주세요.");
				$('#pname').focus();
				return false;
			}
			else if(!prod_content){
				alert("상품설명을 입력해주세요.");
				$('#comm').focus();
				return false;
			}
			else if(prod_price == 0 || prod_price == '') {
				alert("가격을 입력해주세요.");
				$('#price').focus();
				return false;
			}
			else if(prod_maketm == 0 || prod_maketm == '') {
				alert("제조소요시간을 입력해주세요.");
				$('#maketm').focus();
				return false;
			}
			
			$('#prodForm').submit();
		}
	
		function prodDelete() {
			$('#prodForm').attr('action', '../admin/productDeleteProcAction.yo')
			$('#prodForm').submit();
		}
		
	</script>
</head>
<body>
<br>
<div class="topBbs">
<br>
	<b><font size="6" color="gray">상품수정</font></b>
	<br>
</div>
<br>
<div id="board">
	<form method="post" action="../admin/productUpdateProcAction.yo" id="prodForm" name="prodForm" enctype="multipart/form-data">
	<input type="hidden" name="pageNum" value="${pageNum}"/>
	<input type="hidden" name="num" value="${num}"/>
	<table class="bbsForm" align="center">

		<tr>
			<td id="title">
				상품명
			</td>
			<td>
				<input id="pname" name="pname" type="text" size="70" maxlength="100" value="${po.pname}"/>
			</td>		
		</tr>
		<tr>
			<td id="title">
				설명
			</td>
			<td>
				<textarea  id="comm" name="comm" cols="72" rows="20">${po.comm}</textarea>			
			</td>		
		</tr>
		<c:if test="${po.image ne null}">
		<tr>
			<td id="title">
				기존 파일
			</td>
			<td>
				<a href="../view/upload/product/${po.image}"> <img src="../view/upload/product/${po.image}" border="0" title="${po.pname}" alt="${po.pname}" /></a>
			</td>	
		</tr>
		</c:if>
		<tr>
			<td id="title">
				이미지첨부
			</td>
			<td>
				<input type="file" name="image" />
			</td>	
		</tr>
		<tr>
			<td id="title">분류</td>
			<td>
				<select name="cidx">
					<option value="0" <c:if test="${po.cidx eq cate.cidx}">selected</c:if>>분류없음</option>
					<c:if test="${fn:length(requestScope.cateList) > 0}">
						<c:forEach var="cate" items="${requestScope.cateList}">
							<option value="${cate.cidx}" <c:if test="${po.cidx eq cate.cidx}">selected</c:if>>${cate.cname}</option>
						</c:forEach>
					</c:if>
				</select>
			</td>
		</tr>
		<tr>
			<td id="title">
				가격
			</td>
			<td>
				<input type="number"  id="price" name="price" value="${po.price}"/>		
			</td>		
		</tr>
		<tr>
			<td id="title">
				제조소요시간(분단위)
			</td>
			<td>
				<input type="number"  id="maketm" name="maketm" value="${po.maketm}" />		
			</td>		
		</tr>
		<tr>
			<td id="title">MD추천</td>
			<td>
				<select name="recomm">
					<option value="0" <c:if test="${po.recomm eq 0}">selected</c:if>>추천안함</option>
					<option value="1" <c:if test="${po.recomm eq 1}">selected</c:if>>추천</option>
				</select>
			</td>
		</tr>
		<tr>
			<td id="title">노출유무</td>
			<td>
				<select name="status">
					<option value="1" <c:if test="${po.status eq 1}">selected</c:if>>노출</option>
					<option value="2" <c:if test="${po.status eq 2}">selected</c:if>>미노출</option>
				</select>
			</td>
		</tr>

		<tr align="center" valign="middle">
			<td colspan="5">
				<input type="reset" value="삭제"  onclick="prodDelete()">
				<input type="button" value="수정"  onclick="checkValue()">
				<input type="button" value="목록" onclick="location.href='../admin/productListAction.yo'">			
			</td>
		</tr>
	</table>	
	</form>
</div>
</body>
</html>