<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.coffeeyo.order.model.CartDao"%>
<%@page import="com.coffeeyo.order.model.Cart"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html lang="en">
<head>
<link href="https://fonts.googleapis.com/css?family=Barrio"	rel="stylesheet">
<meta charset="UTF-8">
    <title>Title</title>
    <script src="jquery-3.3.1.min.js"></script>
    <script>
    function f1(){}
        $(function(){

        });
    </script>
</head>
<body>
<span>구매결정 확인</span> <span>네비게이션바</span>
<hr>
<h1 style="text-align:center;">상품 결제가 완료되었습니다</h1>
<h3 style="text-align:center;">구매하신 제품을 꼭 수령 해주세요!</h3>
<form method="post" id="order_confirm" name="order_confirm">
	<table border="1" width="70%" align="center" class="orderList">
	    <tr>
	        <td><h1>총 결제 금액</h1></td>
	        <th><h1>${ORD.total}원</h1></th>
	    </tr>
	    <tr>
	        <td><h1>상품수령 예상시간</h1></td>
	        <th><h1>${ORD.readytm}</h1></th>
	    </tr>
	    <tr>
	        <th colspan="2">
	            <input type="button" value="구매내역 확인"  onclick="location.href='../order/orderHistoryListAction.yo'">
	            <input type="button"  value="쇼핑 계속하기" onclick="location.href='/'">
	        </th>
	    </tr>
	</table>
</form>
</body>
</html>