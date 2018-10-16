<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>상품상세조회</title>
<style>
.top_title{width:1000px; margin:auto; font-size: 30px; font-weight: bold;}
.content{width:1000px; margin:auto;}

#page_action{width:1000px; margin:auto; text-align:center}

.top_b{text-align:right;width:1000px; margin:auto;margin-top:10px;}
.lname{font-weight:bold; padding-right:10px; padding-left:20px;}

.Reviews{width:1000px; margin:auto;}
.grade{padding-top:20px;font-weight:bold;font-size:25px; }
#pname {font-weight:bold; width: 200px; text-overflow: ellipsis;
    -o-text-overflow: ellipsis; overflow: hidden;
    white-space: nowrap; word-wrap: normal !important; display: block;
    color:green;
    font-size:16px;}
#price{font-weight:bold;font-size:18px;}
#ptitle{font-weight:bold;font-size:30px;color:green;}
#sum{font-weight:bold;font-size:25px;color:orange;padding-top:15px;}
#price_sum{font-weight:bold;font-size:25px;color:orange;border:1px solid gray; padding-left:150px; }
.content{display:flex;padding:20px 10px 20px 0px}
.incontent{padding-left:20px}
#Review_button {
    background-color: #4CAF50; /* Green */
    border: none;
    color: white;
    padding: 22px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    cursor: pointer;
}
.Review_wr{display:flex;}
</style>
<script>
	
	//댓글 등록
	/*function writeCmt()
	{
		var board = $('#comment_board').val();
		var id = $('#comment_id').val();
		var content = $('#comment_content').val();
		
		if(!content)
		{
			alert("내용을 입력하세요.");
			return false;
		}
		else
		{	
			var param="comment_board="+board+"&comment_id="+id+"&comment_content="+content;
	
			$.ajax({
				url: '../product/prodCommentWriteAction.yo',
				type: 'POST',
				data: param,
				dataType: 'JSON',
				success: function(data) {
					var resultText = data.check;
					
					if(resultText == 1){ 
						document.location.reload(); // 상세보기 창 새로고침
					}
				},
				error:function(){
					//
				}
			});
		}
	}
	
	
	// 댓글 삭제창
	function cmDeleteOpen(comment_num){
		var msg = confirm("댓글을 삭제합니다.");	
		if(msg == true){ // 확인을 누를경우
			deleteCmt(comment_num);
		}
		else{
			return false; // 삭제취소
		}
	}
	
	// 댓글 삭제
	function deleteCmt(comment_num)
	{
		var param="comment_num="+comment_num;
		
		$.ajax({
			url: '../product/prodCommentDeleteAction.yo',
			type: 'POST',
			data: param,
			dataType: 'JSON',
			success: function(data) {
				var resultText = data.check;
				
				if(resultText == 1){ 
					document.location.reload(); // 상세보기 창 새로고침
				}
			},
			error:function(){
				//
			}
		});
	}
	
	// 댓글 수정창
	function cmUpdateOpen(comment_num){
		window.name = "parentForm";
		window.open("../popup/prodCommentUpdateFormAction.yo?num="+comment_num,
					"updateForm", "width=570, height=350, resizable = no, scrollbars = no");
	}
	
	function addLinke() {
		var board = $('#comment_board').val();
				
		var param="num="+board;
		
		$.ajax({
			url: '../board/boardAddLikeAction.yo',
			type: 'POST',
			data: param,
			dataType: 'JSON',
			success: function(data) {
				var resultText = data.check;
				
				if(resultText == 1){ 
					document.location.reload(); // 상세보기 창 새로고침
				}
			},
			error:function(){
				//
			}
		});
	}
	
	function loginFail() {
		alert('로그인 후 투표 가능');
	}
	
	function prodComm(pNum) {
		
	}*/

	//장바구니 담기 함수
	function addCart() {
		var direct = 'N';
		var pdNum = $('#pdNum').val();
		//기본가격 값
		var basicPrice=${po.price};
		numBasicPrice = parseInt(basicPrice); 
		
		//수량 옵션 값
		var pdCount=$("#pdCount").val();
		var numPdCn=parseInt(pdCount);
		
		//컵사이즈 옵션 값
		var cupSize = document.getElementById("cupSize");	
		var cupSizeVal= cupSize.options[cupSize.selectedIndex].value;
		
		if(cupSizeVal=="medium"){
			cupSizeVal=0;
		}
		else if(cupSizeVal=="large"){
			cupSizeVal=500;
		}
		else if(cupSizeVal=="Grande"){
			cupSizeVal=1000;
		}
	
		//시럽 옵션 값	
		var syrubAdd = document.getElementById("syrubAdd");	
		var syrubVal= syrubAdd.options[syrubAdd.selectedIndex].value;
		if(syrubVal=="basic"){
			syrubVal=0;
		}
		else{
			syrubVal=500;
		}
		//얼음 옵션 값	
		var ice = document.getElementById("iceAdd");	
		var iceVal= ice.options[ice.selectedIndex].value;
		if(iceVal=="hot"){
			iceVal=0;
		}
		else{
			iceVal=600;
		}
				
		//샷 옵션 값
		var shotCount=$("#shotAdd").val();
		var numShCn=parseInt(shotCount)*600;
		
		var opt="컵사이즈: "+cupSizeVal+"& 시럽: "+syrubVal+"& 얼음: "+iceVal+"& 샷: "+shotCount;
		var optPrice=cupSizeVal+syrubVal+iceVal+numShCn;
		var priceSum=numBasicPrice*numPdCn+optPrice;
        var param = "pdNum="+pdNum+"&pdCount="+pdCount+"&numBasicPrice="+numBasicPrice+"&optPrice="+optPrice+"&opt="+opt+"&direct="+direct;
        
     
        alert(param);
        //return;
        $.ajax({
			url: '/popup/cartAddAction.yo',
			type: 'POST',
			data: param,
			dataType: 'JSON',
			success: function(data) {
				var resultText = data.check;
				//alert(resultText);
				if(resultText == 1){ 
					$('#btnCartOpen').text('닫기');
					$.cookie("cartOpen", "N", { expires: -1 });
					$.cookie('cartOpen', 'Y', { expires: 7, path: '/', domain: 'localhost', secure: false });
					$('.cart_r').css('width','400px');
					
					//location.reload();
				}
			},
			error:function(){
			}
		});
		
		return;
	}
	//바로구매 함수
	function direcBuy() {		
		var direct = 'Y';
		var pdNum = $('#pdNum').val();
		var price = $("#basicPrice").val();
		//수량 옵션 값
		var pdCount=$("#pdCount").val();
		//컵사이즈 옵션 값
		var cupSize = document.getElementById("cupSize");	
		var cupSizeVal= cupSize.options[cupSize.selectedIndex].value;
		//시럽 옵션 값	
		var syrub = document.getElementById("syrubAdd");	
		var syrubVal= syrub.options[syrub.selectedIndex].value;
		//얼음 옵션 값	
		var ice = document.getElementById("iceAdd");	
		var iceVal= ice.options[ice.selectedIndex].value;
		//샷 옵션 값
		var shotCount=$("#shotAdd").val();
		
		//alert("pdCount="+pdCount+"price="+price+"&cupSizeVal="+cupSizeVal+"&syrubVal="+syrubVal+"&iceVal="+iceVal+"&shotCount"+shotCount);
        
        var param = "pdNum="+pdNum+"&pdCount="+pdCount+"&cupSizeVal="+cupSizeVal+"&syrubVal="+syrubVal+"&iceVal="+iceVal+"&shotCount"+shotCount;
        //alert(param);
        //return;
        $.ajax({
			url: '/popup/cartAddAction.yo',
			type: 'POST',
			data: param,
			dataType: 'JSON',
			success: function(data) {
				var resultText = data.check;
				//alert(resultText);
				if(resultText == 1){ 
					$('#btnCartOpen').text('열기');
					$.cookie("cartOpen", "Y", { expires: -1 });
					$.cookie('cartOpen', 'N', { expires: 7, path: '/', domain: 'localhost', secure: false });
					$('.cart_r').css('width','0px');
					
					location.href = '/order/orderFromAction.yo';
				}
			},
			error:function(){
			}
		});

   	
		return;
	}
	function goList(){
		location.href="../product/productListAction.yo?pageNum=${spage}&cidx=${po.cidx}";
	}

	function sumPrice() {
		//기본가격 값
		var basicPrice=${po.price};
		numBasicPrice = parseInt(basicPrice); 
		
		//수량 옵션 값
		var pdCount=$("#pdCount").val();
		var numPdCn=parseInt(pdCount);
		
		//컵사이즈 옵션 값
		var cupSize = document.getElementById("cupSize");	
		var cupSizeVal= cupSize.options[cupSize.selectedIndex].value;
		
		if(cupSizeVal=="medium"){
			cupSizeVal=0;
		}
		else if(cupSizeVal=="large"){
			cupSizeVal=500;
		}
		else if(cupSizeVal=="Grande"){
			cupSizeVal=1000;
		}
	
		//시럽 옵션 값	
		var syrubAdd = document.getElementById("syrubAdd");	
		var syrubVal= syrubAdd.options[syrubAdd.selectedIndex].value;
		if(syrubVal=="basic"){
			syrubVal=0;
		}
		else{
			syrubVal=500;
		}
		//얼음 옵션 값	
		var ice = document.getElementById("iceAdd");	
		var iceVal= ice.options[ice.selectedIndex].value;
		if(iceVal=="hot"){
			iceVal=0;
		}
		else{
			iceVal=600;
		}
				
		//샷 옵션 값
		var shotCount=$("#shotAdd").val();
		var numShCn=parseInt(shotCount)*600;
		
		var optPrice=cupSizeVal+syrubVal+iceVal+numShCn;
		
		
		
		var priceSum=numBasicPrice*numPdCn+optPrice;
		$("#priceSum").attr("value",priceSum);
		
	}	

//옵션에 대한 기능 
 $(function(){
	 $("#plusPd").click(function(){
		 var pdCount=$("#pdCount").attr("value");
		 if(pdCount==10){
			 alert("수량은 10개 까지 추가할 수 있습니다.");
			 $("#pdCount").attr("value",10);
		 }
		 else{
			 var num_count=parseInt(pdCount)+1;
			 $("#pdCount").attr("value",num_count);
		 }
		 sumPrice(); 
		 
	 });
	 $("#minusPd").click(function(){
		 var pdCount=$("#pdCount").attr("value");
		 if(pdCount==1){
			 $("#pdCount").attr("value",1);
		 }
		 else{
			 var num_count=parseInt(pdCount)-1;
			 $("#pdCount").attr("value",num_count);
		 }
		 sumPrice();
	 });
	 	 
	 $("#plusShot").click(function(){
		 var shotplus=$("#shotAdd").attr("value");
		 if(shotplus==4){
			 alert("Shot은 4번까지 추가할 수 있습니다.");
			 $("#shotAdd").attr("value",4);
		 }
		 else{
			 var numshot=parseInt(shotplus)+1;
			 $("#shotAdd").attr("value",numshot)
		 }
		 sumPrice();
	 });
	
	 $("#minusShot").click(function(){
		 var shotplus=$("#shotAdd").attr("value");
		 if(shotplus==0){
				$("#shotAdd").attr("value",0);
		 }
		 else{
			 var numshot=parseInt(shotplus)-1;
			 $("#shotAdd").attr("value",numshot);
		 }
		 sumPrice();

	 });
	
});


	
</script>
</head>
<body>
<div class="top_title" >
		<p>${po.cateName} 상품 주문하기</p>
</div>
		<div class="content">
			<div class="inpic">
				<c:if test="${po.image ne null}">
					<img src="../view/upload/product/${po.image}" border="0" title="${po.pname}" alt="${po.pname}" width="350px" height="350px" />
				</c:if>
			</div>
			<div class="incontent">
				<p id="ptitle">${po.pname}</p>
				<p>기본가격:&nbsp;&nbsp;<span id="price">${po.price} 원</span></p>
				
				<p> 수량: <input type="button" name="minus_btn" id="minusPd" value="-" />
                           	<input type="text" name="count" id="pdCount" size="3" value="1" readonly />
                            <input type="button" name="plus_btn" id="plusPd" value="+" /></p>
				<br/>
			
					<div class="in_subcontent">
						<p>옵션 선택</p>
							<ul>
                                <li class="cup"><p>
                                        컵사이즈: <select name="cup_size" class="select" id="cupSize" onchange="sumPrice();">
                                        				<option value="medium">Medium</option>	<%--기본사이즈 --%>	
                                        				<option value="large">Large</option>			<%--+500원 --%>
                                        				<option value="Grande">Grande</option>		<%--+1000원 --%>
                                        			</select></p>
                                        			
                                  </li>
                                  <li class="syrup">
                                  		<p>시럽추가: 
                                        			<select name="syrub_add" class="select" id="syrubAdd" onchange="sumPrice();">
                                        				<option value="basic">시럽없음</option>					<%--기본시럽 --%>
                                        				<option value="vanilla">바닐라 시럽</option>			<%--+500원 --%>
                                        				<option value="Hazelnut">헤이즐넛 시럽</option>		<%--+500원 --%>
                                        				<option value="Caramel">캬라멜 시럽</option>			<%--+500원 --%>
                                        			</select></p>
                                  </li>
                                  <li class="ice">
                                   		<p>얼음: <select name="ice_add" class="select" id="iceAdd" onchange="sumPrice();">
                                        				<option value="hot">HOT</option>				<%--기본사이즈 --%>	
                                        				<option value="usually">보통</option>			<%--600원 --%>	
                                        				<option value="less">적게</option>				<%--600원 --%>	
                                        				<option value="more">많게</option>				<%--600원 --%>		
                                        			</select></p>
                                  </li>   
                                  <li class="shot">
                                  <p>샷추가: <input type="button" name="minus_btn" id="minusShot" value="-" />
                                                <input type="text" name="shot_add" id="shotAdd" size="3" value="0" readonly/>
                                                <input type="button" name="plus_btn" id="plusShot" value="+" /></p>
                                  </li>       	
                                  
                           </ul>
                           <input type="hidden" id="pdNum" value="${po.pidx}"/>  
                                     
                    </div>
   
            <div class="in_subcontent" id="sum">
				총합계: <input type="text" id="priceSum" value="${po.price}" style="text-align:right" readonly/>  원
			</div>
		</div>
	</div>
	<div class="in_subcontent" id="page_action">
		<input type="button" name="cart" id="cartBtn" value="장바구니 담기" onclick="addCart();"/>
		<input type="button" name="buy" id="buyBtn" value="바로구매" onclick="direcBuy();"/>
		<input type="button" name="buy" id="listBtn" value="목록" onclick="goList();"/>
	</div>	
	
	<div class="Reviews">
		
		<div class="grade">고객총평점:  <fmt:formatNumber value="${commentAvg}" pattern="0.0"/>/5</div>
			
<%-- 댓글 등록: 로그인 했을 경우에만 댓글 작성 --%>
		<c:if test="${sessionScope.userid !=null}">
			<form id="writeCommentForm">
				<input type="hidden" id="comment_board" name="comment_board" value="${num}">
				<input type="hidden" id="comment_id" name="comment_id" value="${sessionScope.userid}">
				<div>
					<div class="small_text"><p>별점주기
						<select id="core">
							<option value="0">0</option>
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
						</select>
						</p>
					</div>
					<div class="Review_wr">
						<div><textarea name="comment_content" id="comment_content" rows="3" cols="100" maxlength="1000"></textarea></div>
						<div><button name="Review" id="Review_button" onclick="writeCmt()">등록</button></div>
					</div>
				</div>
			</form>
		</c:if>
			
<%-- 댓글 목록 --%>			
	<c:if test="${requestScope.commentList != null}">
		<c:forEach var="comment" items="${requestScope.commentList}">		
			<div class="Review_list">
				<ul display="block">
					<li style="border-style:1">
						<div>
							<span id="nick">닉네임 데이터 불러와서 표시,</span>
							<span id="jumsu1">별점 데이터 불러와서 표시,</span>
							<span id="jumsu2">점수 데이터 불러와서 표시</span>
						</div>
						<p id="review_detail">상품평 본문 불러와서 표시</p>
						<p id="Date_Created">작성일 데이터 불러와서 표시</p>
						<p><input type="button" name="update" id="review_update" value="수정"/>
								<input type="button" name="delete" id="review_delete" value="삭제"/>
						</p>
					</li>
				</ul>
			</div>
		</c:forEach>
	</c:if>
</body>

</body>
</html>