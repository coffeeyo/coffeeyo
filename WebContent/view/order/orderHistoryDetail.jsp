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
    <title>orderHistoryDetail.jsp</title>
    <script src="jquery-3.3.1.min.js"></script>
    <script>
    function f1(){}
        $(function(){

        });
    </script>
</head>
<body>
<span>주문서내역상세</span> <span>네비게이션바</span>
<hr>
<form id="order_detail_form" name="order_detail_form" >
    <table border="1" width="70%" align="center">
    		<tr>
    			<td colspan="2" align="center"> 상품정보</td>
    			<td  align="center">상품소계</td>
    		</tr>
		<c:forEach var="data" items="${oitemList}">
	        <tr>
	            <td align="center">
	            	<img src="../view/upload/product/${data.image}" width="150px" height="150px"/>
            	</td>
	            <td>
	           	주문번호: ${data.orderno}<br/>
	           	주문항목번호: ${data.itemno}<br/>
				상품명: ${data.pname} <br/>
				상품가격: ${data.price}원<br/>
				옵	션:  ${data.options}<br/>
				옵션가격:  ${data.optprice}원<br/>
				수	량: ${data.amount}개<br/>
	            </td>
	            <td  align="center">${(data.price+data.optprice)*data.amount}원</td>
	        </tr>
		</c:forEach>	 	        
	        <tr>
	            <td colspan="3" align="right">
					<h2>최종결제금액</h2>
					<h2>${ORD.total}원</h2>
	            </td>
	        </tr>
	        <tr>
	            <td colspan="3" align="right">
					<h2>상품수령 예상시간  :	${ORD.readytm}</h2>
	            </td>
	        </tr>
	        <td  colspan="3" align="center">
	        	<h2><input type="button" value="구매목록"  onclick="location.href='../order/orderHistoryListAction.yo'"></h2>
	        </td>
        
    </table>
</form>
</body>
</html>