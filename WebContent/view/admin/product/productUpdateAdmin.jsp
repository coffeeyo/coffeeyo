<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
<link href="https://fonts.googleapis.com/css?family=Barrio"	rel="stylesheet">
<meta charset="UTF-8">
<title>상품수정</title>
<style> 
.top_title {width:1000px; text-align:left; margin:auto; font-size: 30px; font-weight: bold;}
td, th{padding:5px 10px; border:1px solid gray;}
.inner_tr {border:1px solid gray;}
.inner_tb {margin:auto;width:1000px;}
.item_col {background:lightgray; font-weight:bold; text-align:center;}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
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
			$('#prodForm').attr('action', '/admin/productDeleteProcAction.yo')
			$('#prodForm').submit();
		}
	
	</script>
</head>
<body>
	<%--화면 이름 표시 영역 --%>
	<div class="top_title" >
		<p>상품 상세보기</p>
	</div>
	<%--상품 상세보기 폼 영역 --%>
<form method="post" action="../admin/productUpdateProcAction.yo" id="prodForm" name="prodForm" enctype="multipart/form-data">
	<input type="hidden" name="pageNum" value="${pageNum}"/>
	<input type="hidden" name="num" value="${num}"/>
		<table class="inner_tb">
			<tr class="inner_tr">
				<td class="item_col">상품분류</td>
				<td><select name="cidx">
						<option value="0" <c:if test="${po.cidx eq cate.cidx}">selected</c:if>>분류없음</option>
						<c:if test="${fn:length(requestScope.cateList) > 0}">
							<c:forEach var="cate" items="${requestScope.cateList}">
								<option value="${cate.cidx}" <c:if test="${po.cidx eq cate.cidx}">selected</c:if>>${cate.cname}</option>
							</c:forEach>
						</c:if>
					</select>
				</td>
			</tr>
			<tr class="inner_tr">
				<td class="item_col">상품명</td>
				<td><input id="pname" name="pname" type="text" size="99" maxlength="100" value="${po.pname}"/></td>
			</tr>
			<tr class="inner_tr">
				<td class="item_col">가격</td>
				<td><input type="number" id="price" name="price" min="0" value="${po.price}" /></td>
			</tr>
			<tr class="inner_tr">
				<td class="item_col">제조소요시간</td>
				<td><select name="maketm">
							<option value="0" <c:if test="${po.maketm eq 0}">selected</c:if>>선택하세요</option>
							<option value="3" <c:if test="${po.maketm eq 3}">selected</c:if>>3분</option>
							<option value="5" <c:if test="${po.maketm eq 5}">selected</c:if>>5분</option>
							<option value="7" <c:if test="${po.maketm eq 7}">selected</c:if>>7분</option>
							<option value="10" <c:if test="${po.maketm eq 10}">selected</c:if>>10분</option>
							<option value="15" <c:if test="${po.maketm eq 15}">selected</c:if>>15분</option>
							<option value="20" <c:if test="${po.maketm eq 20}">selected</c:if>>20분</option>
						</select>
				</td>
			</tr>
			<tr class="inner_tr">
				<td class="item_col">MD추천</td>
				<td><select name="recomm">
							<option value="0" <c:if test="${po.recomm eq 0}">selected</c:if>>추천안함</option>
							<option value="1" <c:if test="${po.recomm eq 1}">selected</c:if>>추천</option>
						</select>
				</td>
			</tr>
			<tr class="inner_tr">
				<td class="item_col">상품설명</td>
				<td><textarea id="comm" name="comm" cols="100" rows="10">${po.comm}</textarea>
				</td>
			</tr>
			<tr  class="inner_tr">
			<td  class="item_col">	기존 파일	</td>
			<td>
				<a href="../view/upload/product/${po.image}"> <img src="../view/upload/product/${po.image}" border="0" title="${po.pname}" alt="${po.pname}" /></a>
			</td>	
		</tr>
			<tr class="inner_tr">
				<td class="item_col">이미지첨부</td>
				<td><input type="file" name="image" /></td>
			</tr>
			<tr class="inner_tr">
				<td class="item_col">노출유무</td>
				<td><select name="status">
							<option value="1" <c:if test="${po.status eq 1}">selected</c:if>>노출</option>
							<option value="2" <c:if test="${po.status eq 2}">selected</c:if>>미노출</option>
						</select>
				</td>
			</tr>
		
			<tr align="center" valign="middle" >
				<td colspan="5" >
					<input type="button" value="저장" onclick="checkValue()" />
					<input type="button" id="lBtn" value="목록" onclick="location.href='../admin/productListAction.yo?pageNum=${pageNum}'"/>
					<!--input type="reset" value="삭제"  onclick="prodDelete()" />-->
				</td>
			</tr>
		</table>
</form>	
</body>
</html>