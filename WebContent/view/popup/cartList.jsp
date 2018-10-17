<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.coffeeyo.order.model.CartDao"%>
<%@page import="com.coffeeyo.order.model.Cart"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html lang="ko">
<head>
<link href="https://fonts.googleapis.com/css?family=Barrio"	rel="stylesheet">
<meta charset="UTF-8">
<style>
* { padding:0px; margin:0px; }
body {
 	overflow-y: auto;
 	overflow-x: hidden;
 	background-color:transparent;
}
.cartImg {
	cursor:pointer;
}
</style>
<script>
	$(function(){
		var total = $('input[name="num"]').length;
		//alert(total);
		$('#orderSave').click(function(){
			//선택한것을, BUYCHK='Y'로 체크하고
			//checkbox num
			var cnt = $('input[name="num"]:checked').length;
			
			var total = $('input[name="num"]').length;
			
			var result = false;
			var i = 1;
			var num = '';
			var direct = '';
			$('input[name="num"]').each(function() { 
		        //alert($(this).val());
		        
		        num += $(this).val();
		        
		        if($(this).prop('checked')) {
		        	direct += 'Y';
		        }
		        else {
		        	direct += 'N';
		        }
		        
		        if(i < total) {
		        	num += ',';
		        	direct += ',';
		        }
		        
		        i++;
		   	});
			
			var param = 'num='+num+'&direct='+direct+'&mode=M';
			//alert(param);
			//return;
			$.ajax({
				url: '/popup/cartBuyCheckAction.yo',
				type: 'POST',
				data: param,
				dataType: 'JSON',
				success: function(data) {
					var resultText = data.check;
					//alert(resultText);
					if(resultText == 1){ 
						if(cnt == 0) {
							parent.location.href = '/';
							return;
						}
						
						$('#btnCartOpen').text('열기');
						$.cookie("cartOpen", "Y", { expires: -1 });
						$.cookie('cartOpen', 'N', { expires: 7, path: '/', domain: 'localhost', secure: false });
						$('.cart_r').css('width','0px');
						
						
						parent.location.href = "../order/orderFormAction.yo";
					}
					else {
						result = false;
					}
				},
				error:function(){
					result = false;
				}
			});			
		});
	});
	
	function deleteCart(num) {
		var param = 'num='+num+'&mode=S'; // mode - S:Single, M:multi
		
		$.ajax({
			url: '/popup/cartDeleteAction.yo',
			type: 'POST',
			data: param,
			dataType: 'JSON',
			success: function(data) {
				var resultText = data.check;
				//alert(resultText);
				if(resultText == 1){					
					parent.location.reload();
				}
			},
			error:function(){
				//
			}
		});
	}
</script>
</head>
<body>
<%
	// 줄바꿈 
	pageContext.setAttribute("br", "<br/>");
	pageContext.setAttribute("crcn", "\r\n");
%>
	<form id="cartFrm" name="cartFrm" method="post">
		<table class="cartList">
		<c:if test="${fn:length(requestScope.cartList) eq 0}">
			<tr>
				<td colspan="4" align="center">담긴 상품이 없습니다.</td>
			</tr>
		</c:if>
		<c:set var="sum" value="0"/>
		<c:if test="${fn:length(requestScope.cartList) gt 0}">
		  <c:forEach var="cart" items="${requestScope.cartList}" varStatus="status">
		  <tr  style="border-bottom: 1px dashed black;">	
			  <td style="width: 20px; text-align: right;">
			  	<input type="checkbox" name="num" value="${cart.cidx}" />
			  </td>
			  <td style="width:204px">
			    <div class="thumb">
			        <c:if test="${cart.image ne null}">
						<img src="../view/upload/product/${cart.image}" title="${cart.pname}" alt="${cart.pname}" class="cartImg" />
					</c:if>
			    </div>
			  </td>
			  <td>
			  <c:set var="amount" value="${cart.amount}"/>
			  <c:set var="price" value="${cart.price}"/>
			  <c:set var="optprice" value="${cart.optprice}"/>
			  ${cart.pname}<br/>
			  수량: ${amount}<br/>
			  옵션: ${fn:replace(cart.options, crcn, br)}<br/>
			  단가: <fmt:formatNumber value="${price}" type="number" />원<br/>
			  옵션합계: <fmt:formatNumber value="${optprice}" type="number" />원<br/>
			  <c:set var="subTotal" value="${(price + optprice) * amount}"/>
			  소계:<fmt:formatNumber value="${subTotal}" type="number" />원<br/>
			  </td>
			  <td>
			  <input type="button" value="삭제" onclick="deleteCart('${cart.cidx}');"  class="btn-primary" />
			  </td>
		  </tr>
		  <c:set var="sum" value="${sum + subTotal}"/>
		  </c:forEach>
		</c:if>
		</table>
		<p></p>
		<div class="itemfooter">
			<div>합계 : <fmt:formatNumber value="${sum}" type="number" />원</div>
    		<input type="button" value="주문결제" id="orderSave"  class="btn-success"  />
    	</div>
	</form>
</body>
</html>