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
    <title>orderHistoryList</title>
    <script src="jquery-3.3.1.min.js"></script>
    <script>
    function f1(){}
        $(function(){

        });
    </script>

</head>
<body>
<div align="left"><table width="70%" align="center"><tr><td><h3>주문내역 목록</h3></td></tr></table> </div>
<hr>
<form>
    <table border="1" width="70%" align="center">
        <tr>
            <th>기간별</th>
            <th><input type="button" onclick="" value="오늘"></th>
            <th><input type="button" onclick="" value="1개월"></th>
            <th><input type="button" onclick="" value="3개월"></th>
            <th><input type="button" onclick="" value="6개월"></th>
            <th><input type="button" onclick="" value="전체"></th>
            <th>2018-01-01~2018-09-13</th>
        </tr>
        <tr>
            <th>전체검색</th>
            <td colspan="5" name="order_search"><input type="text" maxlength="30"></td>
            <td style="text-align:right;">
                <input type="button" onclick="f1" value="조회">
            </td>
        </tr>
    </table>
    <table border="1" width="70%" align="center">
        <tr>
            <td colspan="2" align="center"><h4>상품정보</h4></td>
            <td>결제금액</td>
            <td>주문일시</td>
            <td>상품상세보기 및 상품평보기</td>
        </tr>
        <c:forEach var="data" items="${orderList}">
        <tr>
            <td align="center">
                <a href="../order/orderHistoryDetailAction.yo?orderno=${data.orderno}">
                    <img src="../view/upload/product/${data.image}" width="150px" height="150px"/>
                    주문내역상세보기
                </a>
            </td>
            <td>
					주문번호:${data.orderno}<br/>
        			상품명: ${data.pname} <br/>
        			상품가격: ${data.price}원<br/>
           			옵	션: ${data.options}<br/>
           			옵션가격: ${data.optprice}원<br/>
           			수	량: ${data.amount}개<br/>
            </td>
            <td>결제금액: ${(data.price+data.optprice)*data.amount}원  <%-- ${data.optprice} --%></td>
            <td>주문일시: ${data.orddt} </td>
            <td align="center">
                <input type="button" value="바로가기" onlick="location.href='#'">	<%-- #상품명 넣을 곳 --%>
            </td>
        </tr>
        </c:forEach>
    </table>
</form>
</body>
</html>