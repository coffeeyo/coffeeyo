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
    	.width70{
    		width:70%;
    		border-bottom: 0.5px solid #77563c;
    	}
    		.style1 tr:hover { 
		background-color: #EFE4B0; 
		}
		
    </style>
<script>
</script>
</head>
<body>
<table width="70%" align="center" ><tr><td><h2>주문서</h2></td></tr></table>
	
<hr>
<div class="board">
	<form action="../order/orderProcAction.yo " method="post" id="order_form" name="order_form" >
	    <table  width="70%" align="center" >
	        <tr style="border-bottom: 0.5px solid #77563c;">
	            <td><h3>상품이미지</h3></td>
            	<td><h3>상품정보</h3></td>
	            <td ><h3>상품 구매금액</h3></td>
	        </tr>
	        <c:if test="${fn:length(requestScope.buyChkList) eq 0}">
				<tr >
					<th></th>
				</tr>
			</c:if>
			<c:set var="sum" value="0"/>
			<c:if test="${fn:length(requestScope.buyChkList) gt 0}">
			  <c:forEach var="cart" items="${requestScope.buyChkList}" varStatus="status">
			  <tr style="border-bottom: 0.5px solid #77563c;">	
				  <td>
				        <c:if test="${cart.image ne null}">
							<img src="../view/upload/product/${cart.image}" title="${cart.pname}" alt="${cart.pname}" style="cursor:pointer;" width="150px" height="150px"/>
						</c:if>
				  </td>
				  <td>
					  상품명: ${cart.pname}<br/>
					  옵션:${cart.options}<br/>
					  상품가격:${cart.price}<br/>
					  옵션가격:${cart.optprice}<br/>
					  수량: ${cart.amount}<br/>
				  </td>
				  <td>
					 <h4> ${(cart.price + cart.optprice)*cart.amount} 원</h4>
				  </td>
			  </tr>
			  <c:set var="sum" value="${sum + (cart.price + cart.optprice)*cart.amount}"/>
			  </c:forEach>
			</c:if>
	        <tr style="border-bottom: 0.5px solid #77563c;">
	        	<div class="itemfooter">
		            <td colspan="3" align="right">
		                <h4>최종결제금액<br/>
		               		<div>총계 : ${sum}원</div>
		               </h4>
		            </td>
	            </div>
	        </tr>
	        <tr>
	            <td colspan="3" align=right>
	                <h4><input type="submit" value="결제하기"></h4>
	            </th>
	        </tr>
	    </table>
	</form>	
</div>	
	
	
	<%--
	1. 주문서 작성 폼을 작성할때 필수값
	<input type="hidden" name="price" value="${sum}"/>
	
	2. 주문서 작성 폼은 현재페이지를 호출하고 있는 컨트롤러에서 session 정보 즁 userid를 이용해서 닉네임 만 뿌려주고
	실제 주문 처리시에는 서블릿에서 구해지는 userid를 이용할 계혹이므로 사용자 주문서 작성폼은 간결하게 작성할것.
	
	3. 예상 소요 시간의 계산은 장바구니에 들어있는 상품중 BUYCHK = 'Y' 이것 기준으로
	PID(상품ID) 에 해당하는 상품 정보중 MAKETM(소요시간)을 이용하되 AMOUNT(장바구니 수량)을 곱한 시간이 되어야 한다.
	
	아직 완료 되지 않은 주문을 ORDNO 오름차준으로 순차적으로 
	1) 먼저 주문한 건에 대한 시간을 합산 한 값
	2) 현재 주문자의 시간을 합산 한 값
	을 더하여 총 걸리는 시간을 산출한다.
	
	4. 추문처리(결제) 는 실제 PG 연동을 하지 않으며
	주문자 userid 기준으로 장바구니에 담긴 것중     BUYCHK = 'Y' 인것만
	ORDERS 테이블에는 함계 소계 ${sum}을 담은 price를 매개변수로 이용하고,
	ORDNO 는 OrderProcAction 에서 데이터를 삽입하기전에 OrderDao.getSeq() 의 값과 날짜조합으로 만들어낸다
	예) S+날짜_고유sequence번호
	=> S20180826_1
	
	-날짜생성
	Date now = new Date();
	SimpleDateFormat vans = new SimpleDateFormat("yyyyMMdd");
	String wdate = vans.format(now);
	
	-주문번호생성
	String order_key = OrderDao.getSeq();
	String trade_num = "S"+wdate+"_"+order_key;
	
	
	ORDERS 테이블의 ORDNO 의 trade_num 을 이용하여 이용한다.
	ORDNO	= trade_num
	USERID	= 세선에 저장돈 userId
	TOTAL	= 주문서작성 폼에서 넘긴 price 파라미터
	PAYYN	= 2:	기본적으로 결제한 것으로 처리한다,	1:결제대기, 2:결제완료, 3:결제취소,
	READYTM = 총 걸리는 시간 입력
	ORDDT	= 주문일자로 SYSDATE 입력(고정값)
	STATUS	= 1: 주문상태로  1:준비중, 2:준비완료, 3:수령완료
	
	ORDERITEM 테이블
	ORDNO	= ORDERS 테이블에서 사용되었던 ORDNO 사용
	ITEMNO	= OrderItemDao.getSeq() 을 이용한다.
	PIDX	= CART테이블에서 BUYCHK='Y' 인것기준으로 동일한 PIDX 이용한다.
	OPTIONS = CART테이블에서 OPTONS 내용을 그대로 이용한다.
	AMOUNT	= CART테이블에서 AMOUNT 내용을 그대로 이용한다.
	PRICE	= CART테이블에서 PRICE 내용을 그대로 이용한다.
	
	
	
	5. 주문처리완료
	CART 테이블에서  BUYCHK='Y' 이면서 현재 USERID 에 해당하는 데이터를 모두 물리적 삭체 처리한다.
	
	주문 완료 페이지로 이동한다.
	 
	
	
	--%>
	
	</div>

<%-- 선배님 작성 부분
 <!-- 장바구니 목록 부분 -->
	<br>
	<div class="board">
		<table class="cartList">
		<c:if test="${fn:length(requestScope.buyChkList) eq 0}">
			<tr>
				<th></th>
			</tr>
		</c:if>
		<c:set var="sum" value="0"/>
		<c:if test="${fn:length(requestScope.buyChkList) gt 0}">
		  <c:forEach var="cart" items="${requestScope.buyChkList}" varStatus="status">
		  <tr>	
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
		  </tr>
		  <c:set var="sum" value="${sum + (cart.price * cart.amount)}"/>
		  </c:forEach>
		</c:if>
		</table>
		<div class="itemfooter">
			<div>소계 : ${sum}원</div>
    	</div>
	</div>
	<div> --%>

</body>
</html>