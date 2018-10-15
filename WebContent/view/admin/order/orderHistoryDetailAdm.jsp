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
<title>orderHistoryDetailAdm.jsp</title>
	<style>
    	.width70{
    		width:70%;
    		border-bottom: 2px solid #77563c;
    	}
    </style>
    <script src="jquery-3.3.1.min.js"></script>
    <script>
    function f1(){}
        $(function(){

        });
    </script>
</head>
<body>
<table width="70%" align="center" ><tr><td><h2>주문현황 상세목록</h2></td></tr></table>
<hr>
<form>
    <table class="width70" align="center" >
        <tr>
            <td colspan="2">
                <h1>주문자정보 정보</h1>
            </td>
        </tr>
        <tr>
            <td>
                이름: ${member.uname}
            </td>
            <td>
                닉네임: ${member.nickName}
            </td>
        </tr>
        <tr>
            <td>
                전화번호: ${member.hp}
            </td>
            <td>
                		회원상태: ${member.status} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(1:이용회원 2:탈퇴회원)
            </td>
        </tr>
    </table>
    <br/>
    <table class="width70" align="center" >
    
        <tr class="width70">
            <td><h4>상품이미지</h4></td>
            <td><h4>상품정보</h4></td>
            <td><h4>상품구매금액</h4></td>
        </tr>
		<c:forEach var="data" items="${oitemList}">
			<tr class="width70">
			    <td><img src="../view/upload/product/${data.image}" width="150px" height="150px"/></td>
			    <td>
			    	주문번호: ${data.orderno}<br/>
			    	항목번호: ${data.itemno}<br/>
        			상품명: ${data.pname} <br/>
        			상품가격: ${data.price}원<br/>
           			옵	션: ${data.options}<br/>
           			옵션가격: ${data.optprice}원<br/>
           			수	량: ${data.amount}개<br/>
			    </td>
			    <td align="center">결제금액: ${(data.price+data.optprice)*data.amount} </td>
			</tr>
		</c:forEach>
        <tr>
            <td align="center" colspan="2">
               <h3>최종 결제 금액</h3>
            </td>
            <td align="center">
               <h3> ${ordrt.total}원</h3>
            </td>
        </tr>
        <tr>
            <td align="center" colspan="2">
               <h3> 상품수령 예상시간</h3>
            </td>
            <td align="center">
              <h3>  ${ordrt.readytm}</h3>
            </td>
        </tr>
        <tr>
            <td align="center" colspan="3">
                
                <h4><input type="button" value="처리완료"  onclick="location.href='../admin/orderStatusProcAction.yo?userid=${ordrt.userid}&orderno=${ordrt.orderno}'">
                <input type="button" value="주문현황목록"  onclick="location.href='../admin/orderHistoryListAction.yo'"></h4>
                <h4>처리상태: ${ordrt.status} (1: 처리중 2:처리완료)</h4>
            </td>
        </tr>
    </table>
</form>
</body>
</html>