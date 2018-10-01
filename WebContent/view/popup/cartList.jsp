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
						
						
						parent.location.href = '/order/orderFromAction.yo';
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
	<form id="cartFrm" name="cartFrm" method="post">
		<table class="cartList">
		<c:if test="${fn:length(requestScope.cartList) eq 0}">
			<tr>
				<th><center>담긴 상품이 없습니다.</center></th>
			</tr>
		</c:if>
		<c:set var="sum" value="0"/>
		<c:if test="${fn:length(requestScope.cartList) gt 0}">
		  <c:forEach var="cart" items="${requestScope.cartList}" varStatus="status">
		  <tr>	
			  <td>
			  	<input type="checkbox" name="num" value="${cart.cidx}" />
			  </td>
			  <td>
			    <div class="thumbnail">
			        <c:if test="${cart.image ne null}">
						<img src="../view/upload/product/${cart.image}" title="${cart.pname}" alt="${cart.pname}" style="cursor:pointer;"/>
					</c:if>
			    </div>
			  </td>
			  <td>
			  ${cart.pname}<br/>
			  ${cart.amount}<br/>
			  ${cart.price * cart.amount} 원<br/>
			  </td>
			  <td>
			  <input type="button" value="삭제" onclick="deleteCart('${cart.cidx}');" />
			  </td>
		  </tr>
		  <c:set var="sum" value="${sum + (cart.price * cart.amount)}"/>
		  </c:forEach>
		</c:if>
		</table>
		<div class="itemfooter">
			<div>소계 : ${sum}</div>
    		<input type="button" value="주문결제" id="orderSave" />
    	</div>
	</form>
</body>
</html>