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
	    function f1(days){
			var sdate = new Date(); 
			var edate = new Date();
			
			if(days == '2') {
				edate.setMonth( edate.getMonth() + 1 );
			}
			else if(days == '3') {
				edate.setMonth( edate.getMonth() + 2 );
			}
			else if(days == '4') {
				edate.setMonth( edate.getMonth() + 3 );
			}
			
			var year1 = sdate.getFullYear(); 
			var month1 = new String(sdate.getMonth()+1); 
			var day1 = new String(sdate.getDate()); 
			
			var year2 = edate.getFullYear(); 
			var month2 = new String(edate.getMonth()+1); 
			var day2 = new String(edate.getDate()); 
			
			var startDay = '';
			var endDay = '';
	
			// 한자리수일 경우 0을 채워준다. 
			if(month1.length == 1){ 
			  month1 = "0" + month1; 
			} 
			if(day1.length == 1){ 
			  day1 = "0" + day1; 
			} 
			var sDay = year1 + "-" + month1 + "-" + day1;
			
			if(month2.length == 1){ 
			  month2 = "0" + month2; 
			} 
			if(day2.length == 1){ 
			  day2 = "0" + day2; 
			} 
			var eDay = year2 + "-" + month2 + "-" + day2;
			//alert(today)
			
			if(days != 'all') {
				startDay = sDay;
				endDay = eDay;
			}
			else {
				startDay = '';
				endDay = '';
			}
			
			if(days != 'all') {
				$('#srchDate').html(startDay + ' ~ ' + endDay);
			}
			else {
				$('#srchDate').html('');
			}
		
			$('#startDay').val(startDay);
			$('#endDay').val(endDay);
		}
        $(function(){
        	f1('1');
        	
        	var sDayData = '${startDay}';
			var eDayData = '${endDay}';
			
			if(sDayData != '' && eDayData != '') {
				$('#srchDate').html(sDayData + ' ~ ' + eDayData);
				$('#startDay').val(sDayData);
				$('#endDay').val(eDayData);
			}
        });
    </script>

</head>
<body>
<div align="left"><table width="70%" align="center"><tr><td><h3>주문내역 목록</h3></td></tr></table> </div>
<hr>
<form action="/order/orderHistoryListAction.yo" method="post">
    <table border="1" width="70%" align="center">
        <tr>
            <th>기간별</th>
            <th><input type="button" onclick="f1('1')" value="오늘"></th>
            <th><input type="button" onclick="f1('2')" value="1개월"></th>
            <th><input type="button" onclick="f1('3')" value="3개월"></th>
            <th><input type="button" onclick="f1('4')" value="6개월"></th>
            <th><input type="button" onclick="f1('all')" value="전체"></th>
            <th width="340">
            	<span id="srchDate"></span>
           		<input type="hidden" id="startDay" name="startDay" />
           		<input type="hidden" id="endDay" name="endDay" />
           		<input type="submit" value="조회" style="float:right;">
            </th>
        </tr>
    </table>
    <table width="70%" align="center">
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
					주문자아이디: ${data.userid}	<br/>
        			상품명: ${data.pname} <c:if test="${data.amount gt 1 }"> 외 ${data.amount - 1} 건</c:if> 
            </td>
            <td>결제금액: ${data.total}원  <%-- ${data.optprice} --%></td>
            <td>주문일시: ${data.orddt} </td>
            <td align="center">
                <input type="button" value="바로가기" onclick="location.href='#'">	<%-- #상품명 넣을 곳 --%>
            </td>
        </tr>
        </c:forEach>
    </table>
</form>
<br>
<div class="bbsPageForm">
	<c:if test="${fn:length(requestScope.orderList) ne 0}">
		<c:if test="${PINFO.startPage ne 1}">
			<a href='/order/orderHistoryListAction.yo?nowPage=${PINFO.nowPage-1}'>[ 이전 ]</a>
		</c:if>
		
		<c:forEach var="page" begin="${PINFO.startPage}" end="${PINFO.endPage}">
			<c:if test="${PINFO.nowPage eq page}">
				${page}&nbsp;
			</c:if>
			<c:if test="${PINFO.nowPage ne page}">
				<a href='/order/orderHistoryListAction.yo?nowPage=${page}'>${page}&nbsp;</a>
			</c:if>
		</c:forEach>
		
		<c:if test="${PINFO.endPage ne PINFO.totalPage }">
			<a href='/order/orderHistoryListAction.yo?nowPage=${PINFO.nowPage+1}'>[다음]</a>
		</c:if>
	</c:if>
</div>
</body>
</html>