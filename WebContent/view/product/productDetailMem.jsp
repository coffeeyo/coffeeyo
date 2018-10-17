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

#ptitle{font-weight:bold;font-size:35px;color:green;margin-bottom:20px;}
#sum{font-weight:bold;font-size:25px;color:#c8852c;padding-top:15px;}
#price_sum{font-weight:bold;font-size:25px;color:orange;border:1px solid gray; padding-left:150px; }
.content{display:flex;padding:20px 10px 20px 0px}
.incontent{padding-left:20px}
#Review_button {
    background-color: #4CAF50; /* Green */
    border: none;
    color: white;
    padding: 20px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    cursor: pointer;
}
.Review_wr{display:flex;}
#writer{color:darkblue;font-weight:bold;}
.cate2{font-weight:bold;font-size:18px;color:#191970;}
.cate3{padding-top:5px;padding-bottom:5px;}
#optTab{width:250px;margin-left:40px;}
.actionBtn{background-color: #202050; /* Green */
    border: none;
    color: white;
    padding: 7px 32px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    margin: 15px 5px;
    cursor: pointer;
    font-size: 15px;
    border-radius: 8px;
    font-weight:bold;}
.subBtn{    background-color: silver; /* Green */
    border: 1px solid silver;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    cursor: pointer;
    border-radius: 2px;
    width: 30px;
}
.bbscomm{margin-bottom:50px;}
.inpic{ display: inline-block;
      box-shadow: 0px 0px 20px -5px rgba(0, 0, 0, 0.8);
     }
.inpic img{border-radius: 10px;}
      

</style>
<script>
	//댓글 등록
	function writeCmt() {
		var board = $('#comment_board').val();
		var id = $('#comment_id').val();
		var content = $('#comment_content').val();
		var pdScore = document.getElementById("score");
		var pdScoreval = pdScore.options[pdScore.selectedIndex].value;
		
		if (!content) {
			alert("내용을 입력하세요.");
			return false;
		} else {
			var param = "comment_board=" + board + "&comment_id=" + id
					+ "&comment_content=" + content + "&comment_point="
					+ pdScoreval;
			
			
			$.ajax({
				url: '/popup/prodCommentWriteAction.yo',
				type: 'POST',
				data: param,
				dataType: 'JSON',
				success: function(data) {
					var resultText = data.check;
					
					if (resultText == 1) {
						document.location.reload(); // 상세보기 창 새로고침
					}
					else {
						alert(data);
					}
				},
				error : function() {
					alert('error');
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
				
			}
		});
	}
	
	// 댓글 수정창
	function cmUpdateOpen(comment_num){
		window.name = "parentForm";
		window.open("../popup/prodCommentUpdateFormAction.yo?num="+comment_num,
					"updateForm", "width=570, height=350, resizable = no, scrollbars = no");
	}
	
	/*function addLinke() {
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
	}*/
	
	function loginFail() {
		alert('로그인 후 투표 가능');
	}
	function prodComm(pNum) {
		submit();
	}
	//장바구니 담기 함수
	function addCart() {
		var direct = 'N';
		var pdNum = $('#pdNum').val();
		//기본가격 값
		var basicPrice = ${po.price};
		numBasicPrice = parseInt(basicPrice);
		//수량 옵션 값
		var pdCount = $("#pdCount").val();
		var numPdCn = parseInt(pdCount);
		//컵사이즈 옵션 값
		var cupSize = document.getElementById("cupSize");
		var cupSizeVal = cupSize.options[cupSize.selectedIndex].value;
		//시럽 옵션 값	
		var syrubAdd = document.getElementById("syrubAdd");
		var syrubVal = syrubAdd.options[syrubAdd.selectedIndex].value;
		//얼음 옵션 값	
		var ice = document.getElementById("iceAdd");
		var iceVal = ice.options[ice.selectedIndex].value;
		//샷 옵션 값
		var shotCount = $("#shotAdd").val();
		var numShCn = parseInt(shotCount) * 600;

		var opt = "컵사이즈= " + cupSizeVal + ", 시럽= " + syrubVal + ", 얼음= " + iceVal + ", 샷= " + shotCount;

		if (cupSizeVal == "medium") {
			cupSizeVal = 0;
		} else if (cupSizeVal == "large") {
			cupSizeVal = 500;
		} else if (cupSizeVal == "Grande") {
			cupSizeVal = 1000;
		}

		if (syrubVal == "basic") {
			syrubVal = 0;
		} else {
			syrubVal = 500;
		}
		if (iceVal == "hot") {
			iceVal = 0;
		} else {
			iceVal = 600;
		}

		var optPrice = cupSizeVal + syrubVal + iceVal + numShCn;
		var priceSum = (numBasicPrice+optPrice)*numPdCn;
		
		
		var param = "pdNum=" + pdNum + "&pdCount=" + pdCount
				+ "&numBasicPrice=" + numBasicPrice + "&optPrice=" + optPrice
				+ "&opt=" + opt + "&direct=" + direct;

		
		//선택 값을 ajax 형태로 넘기기
		$.ajax({
			url : '/popup/cartAddAction.yo',
			type : 'POST',
			data : param,
			dataType : 'JSON',
			success : function(data) {
				var resultText = data.check;
				
				if (resultText == 1) {
					$('#btnCartOpen').text('닫기');
					$.cookie("cartOpen", "N", {
						expires : -1
					});
					$.cookie('cartOpen', 'Y', {
						expires : 7,
						path : '/',
						domain : 'localhost',
						secure : false
					});
					$('.cart_r').css('width', '400px');

					var url = $('#cartFrame').attr('src');
					$('#cartFrame').attr('src', url);
				}
			},
			error : function() {
			}
		});

		return;
	}
	//바로구매 함수
	function direcBuy() {
		var direct = 'Y';
		var pdNum = $('#pdNum').val();
		//기본가격 값
		var basicPrice = ${po.price};
		numBasicPrice = parseInt(basicPrice);

		//수량 옵션 값
		var pdCount = $("#pdCount").val();
		var numPdCn = parseInt(pdCount);

		//컵사이즈 옵션 값
		var cupSize = document.getElementById("cupSize");
		var cupSizeVal = cupSize.options[cupSize.selectedIndex].value;

		if (cupSizeVal == "medium") {
			cupSizeVal = 0;
		} else if (cupSizeVal == "large") {
			cupSizeVal = 500;
		} else if (cupSizeVal == "Grande") {
			cupSizeVal = 1000;
		}

		//시럽 옵션 값	
		var syrubAdd = document.getElementById("syrubAdd");
		var syrubVal = syrubAdd.options[syrubAdd.selectedIndex].value;
		if (syrubVal == "basic") {
			syrubVal = 0;
		} else {
			syrubVal = 500;
		}
		//얼음 옵션 값	
		var ice = document.getElementById("iceAdd");
		var iceVal = ice.options[ice.selectedIndex].value;
		if (iceVal == "hot") {
			iceVal = 0;
		} else {
			iceVal = 600;
		}

		//샷 옵션 값
		var shotCount = $("#shotAdd").val();
		var numShCn = parseInt(shotCount) * 600;

		var opt = "컵사이즈: " + cupSizeVal + "& 시럽: " + syrubVal + "& 얼음: "
				+ iceVal + "& 샷: " + shotCount;
		var optPrice = cupSizeVal + syrubVal + iceVal + numShCn;
		var priceSum = numBasicPrice * numPdCn + optPrice;
		var param = "pdNum=" + pdNum + "&pdCount=" + pdCount
				+ "&numBasicPrice=" + numBasicPrice + "&optPrice=" + optPrice
				+ "&opt=" + opt + "&direct=" + direct;

		$.ajax({
			url : '/popup/cartAddAction.yo',
			type : 'POST',
			data : param,
			dataType : 'JSON',
			success : function(data) {
				var resultText = data.check;
				//alert(resultText);
				if (resultText == 1) {
					$('#btnCartOpen').text('열기');
					$.cookie("cartOpen", "Y", {
						expires : -1
					});
					$.cookie('cartOpen', 'N', {
						expires : 7,
						path : '/',
						domain : 'localhost',
						secure : false
					});
					$('.cart_r').css('width', '0px');

					location.href = '/order/orderFormAction.yo';
				}
			},
			error : function() {
			}
		});

		return;
	}
	function goList() {
		location.href = "../product/productListAction.yo?pageNum=${spage}&cidx=${po.cidx}";
	}

	function sumPrice() {
		//기본가격 값
		var basicPrice = ${po.price};
		numBasicPrice = parseInt(basicPrice);
		//수량 옵션 값
		var pdCount = $("#pdCount").val();
		var numPdCn = parseInt(pdCount);
		//컵사이즈 옵션 값
		var cupSize = document.getElementById("cupSize");
		var cupSizeVal = cupSize.options[cupSize.selectedIndex].value;

		if (cupSizeVal == "medium") {
			cupSizeVal = 0;
		} else if (cupSizeVal == "large") {
			cupSizeVal = 500;
		} else if (cupSizeVal == "Grande") {
			cupSizeVal = 1000;
		}

		//시럽 옵션 값	
		var syrubAdd = document.getElementById("syrubAdd");
		var syrubVal = syrubAdd.options[syrubAdd.selectedIndex].value;
		if (syrubVal == "basic") {
			syrubVal = 0;
		} else {
			syrubVal = 500;
		}
		//얼음 옵션 값	
		var ice = document.getElementById("iceAdd");
		var iceVal = ice.options[ice.selectedIndex].value;
		if (iceVal == "hot") {
			iceVal = 0;
		} else {
			iceVal = 600;
		}

		//샷 옵션 값
		var shotCount = $("#shotAdd").val();
		var numShCn = parseInt(shotCount) * 600;
		var optPrice = cupSizeVal + syrubVal + iceVal + numShCn;		//옵션가격 총합
		var priceSum = (numBasicPrice+optPrice)*numPdCn;				//총 가격
		$("#priceSum").attr("value", priceSum);
	}

	//옵션 선택관련 기능 
	$(function() {
		$("#plusPd").click(function() {
			var pdCount = $("#pdCount").attr("value");
			if (pdCount == 10) {
				alert("수량은 10개 까지 추가할 수 있습니다.");
				$("#pdCount").attr("value", 10);
			} else {
				var num_count = parseInt(pdCount) + 1;
				$("#pdCount").attr("value", num_count);
			}
			sumPrice();
		});
		$("#minusPd").click(function() {
			var pdCount = $("#pdCount").attr("value");
			if (pdCount == 1) {
				$("#pdCount").attr("value", 1);
			} else {
				var num_count = parseInt(pdCount) - 1;
				$("#pdCount").attr("value", num_count);
			}
			sumPrice();
		});
		$("#plusShot").click(function() {
			var shotplus = $("#shotAdd").attr("value");
			if (shotplus == 4) {
				alert("Shot은 4번까지 추가할 수 있습니다.");
				$("#shotAdd").attr("value", 4);
			} else {
				var numshot = parseInt(shotplus) + 1;
				$("#shotAdd").attr("value", numshot)
			}
			sumPrice();
		});
		$("#minusShot").click(function() {
			var shotplus = $("#shotAdd").attr("value");
			if (shotplus == 0) {
				$("#shotAdd").attr("value", 0);
			} else {
				var numshot = parseInt(shotplus) - 1;
				$("#shotAdd").attr("value", numshot);
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
					<img src="../view/upload/product/${po.image}" border="0" title="${po.pname}" alt="${po.pname}" width="380px" height="380px" />
				</c:if>
			</div>
			<div class="incontent">
				<p id="ptitle">${po.pname}</p>
				<p class="cate2">기본가격:&nbsp;&nbsp;<span id="price">${po.price} 원</span></p>
					<div class="in_subcontent">
						<p class="cate2">옵션 선택</p>
							
								<table id="optTab">
									<tr>
		                                <td class="cate3">▶ 컵사이즈:</td>
		                                <td> <select name="cup_size" class="select" id="cupSize" onchange="sumPrice();">
		                                        				<option value="medium">Medium</option>	<%--기본사이즈 --%>	
		                                        				<option value="large">Large</option>			<%--+500원 --%>
		                                        				<option value="Grande">Grande</option>		<%--+1000원 --%>
		                                        			</select>
		                                        			
		                                  
		                                  </td>
	                                  </tr>
                               		<tr>
	                               		<td class="cate3">▶ 시럽추가:</td>
										<td><select name="syrub_add" class="select" id="syrubAdd"
											onchange="sumPrice();">
												<option value="basic">시럽없음</option>
												<%--기본시럽 --%>
												<option value="vanilla">바닐라 시럽</option>
												<%--+500원 --%>
												<option value="Hazelnut">헤이즐넛 시럽</option>
												<%--+500원 --%>
												<option value="Caramel">캬라멜 시럽</option>
												<%--+500원 --%>
										</select></td>

									<tr>
										<td class="cate3">▶ 얼음:</td>
										<td> <select name="ice_add" class="select" id="iceAdd" onchange="sumPrice();">
                                        				<option value="hot">HOT</option>				<%--기본사이즈 --%>	
                                        				<option value="usually">보통</option>			<%--600원 --%>	
                                        				<option value="less">적게</option>				<%--600원 --%>	
                                        				<option value="more">많게</option>				<%--600원 --%>		
                                        			</select>
                                       	</td>
                                   
                               		</tr>
                               		<tr>
	                                  <td class="cate3">▶ 샷추가: </td>
	                                  <td><input type="button" class="subBtn" name="minus_btn" id="minusShot" value="-" />
	                                                <input type="text" name="shot_add" id="shotAdd" size="3" value="0" readonly/>
	                                                <input type="button" class="subBtn" name="plus_btn" id="plusShot" value="+" />
	                                  </td>
                                  </tr>       	
                            </table>      
                 
                           <input type="hidden" id="pdNum" value="${po.pidx}"/>  
                                     
                    </div>
          		<br/>
              <p ><span class="cate2">수량: &nbsp;</span> <input type="button" class="subBtn" name="minus_btn" id="minusPd" value="-" />
                           	<input type="text" name="count" id="pdCount" size="3" value="1" style="text-align:center" readonly />
                            <input type="button" class="subBtn" name="plus_btn" id="plusPd" value="+" /></p>
   			
            <div class="in_subcontent" id="sum">
				총합계: <input type="text" id="priceSum" value="${po.price}" style="text-align:right;padding-right:5px;" readonly/>  원
			</div>
		</div>
	</div>
	<div class="in_subcontent" id="page_action">
		<input type="button" class="actionBtn" name="cart" id="cartBtn" value="장바구니 담기" onclick="addCart();"/>
		<input type="button" class="actionBtn" name="buy" id="buyBtn" value="바로구매" onclick="direcBuy();"/>
		<input type="button" class="actionBtn" name="list" id="listBtn" value="목록" onclick="goList();"/>
	</div>	
	
<!-- 댓글 부분 -->
	<div class="Reviews">
		<div class="grade">고객총평점:  
										<c:if test="${commentAvg>=0 && commentAvg<0.5}">
								        	<img src="../img/별점0.png" width="200px" height="40px">
								        </c:if>
								        <c:if test="${commentAvg>=0.5 && commentAvg<1}">
								        	<img src="../img/별점0.5.png" width="200px" height="40px">
								        </c:if>
								        <c:if test="${commentAvg>=1 && commentAvg<1.5}">
								        	<img src="../img/별점1.png" width="200px" height="40px">
								        </c:if>
								        <c:if test="${commentAvg>=1.5 && commentAvg<2}">
								        	<img src="../img/별점1.5.png" width="200px" height="40px">
								        </c:if>
								        <c:if test="${commentAvg>=2 && commentAvg<2.5}">
								        	<img src="../img/별점2.png" width="200px" height="40px">
								        </c:if>
								        <c:if test="${commentAvg>=2.5 && commentAvg<3}">
								        	<img src="../img/별점2.5.png" width="200px" height="40px">
								        </c:if>
								        <c:if test="${commentAvg>=3 && commentAvg<3.5}">
								        	<img src="../img/별점3.png" width="200px" height="40px">
								        </c:if>
								        <c:if test="${commentAvg>=3.5 && commentAvg<4}">
								        	<img src="../img/별점3.5.png" width="200px" height="40px">
								        </c:if>
								        <c:if test="${commentAvg>=4 && commentAvg<4.5}">
								        	<img src="../img/별점4.png" width="200px" height="40px">
								        </c:if>
								        <c:if test="${commentAvg>=4.5 && commentAvg<5}">
								        	<img src="../img/별점4.5.png" width="200px" height="40px">
								        </c:if>
								        <c:if test="${commentAvg==5}">
								      		<img src="../img/별점5.png" width="200px" height="40px">
								        </c:if>		
		<fmt:formatNumber value="${commentAvg}" pattern="0.0"/>/5</div>
		<table class="bbscomm">
		
			<!-- 로그인 했을 경우만 댓글 작성가능 -->
			<c:if test="${sessionScope.userid !=null}">
			
			<form id="writeCommentForm" method="post" action="#">
				<input type="hidden" id="comment_board" name="comment_board" value="${num}"/>
				<input type="hidden" id="comment_id" name="comment_id" value="${sessionScope.userid}"/>
			<tr>
				
				<!-- 아이디-->
				<td style="width:150px;padding:10px; background:lightgray;text-align:center;">
					<div id="writer">${sessionScope.nick}</div>
					<div class="small_text"><p>별점주기
						<select id="score">
							<option value="0">0</option>
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
						</select>
						</p>
					</div>
				</td>
				<!-- 본문 작성-->
				<td style="padding:5px">
					<div>
						<div><textarea name="comment_content" id="comment_content" rows="3" cols="100" maxlength="1000"></textarea></div>
					</div>
				</td>
				<!-- 댓글 등록 버튼 -->
				<td align="center">
					<div id="btn">
						<button type="button" id="Review_button" onclick="writeCmt();">등록</button>
					</div>
				</td>
			</tr>
			</form>
			
			</c:if>
			
			<!-- 댓글 목록 -->	
			<c:if test="${requestScope.commentList != null}">
				<c:forEach var="comment" items="${requestScope.commentList}">
					<tr>
						<!-- 아이디, 작성날짜 -->
						<td style="width:150px;height:50px;padding:10px; background:#e7e6d2;border-bottom: 1px solid lightgray;">
							<div >					
								${comment.nickName} : ${comment.pcpoint} / 5
								
							</div>
						</td>
						<!-- 본문내용 -->
						<td style="width:650px;padding:5px;border-bottom: 1px solid lightgray;" >
							<div class="text_wrapper" style="padding-top:15px;">
								${fn:replace(comment.comm, cn, br)}
								<p style="color:gray;font-size:8px;">	
								<fmt:parseDate value="${comment.createDay}" var="dateFmt" pattern="yyyy-MM-dd HH:mm:SS"/>
								<fmt:formatDate value="${dateFmt}" pattern="yyyy년MM월dd일 HH시mm분SS초"/> </p>
							</div>
							</td>
							<!-- 버튼 -->
						<td style="border-bottom: 1px solid lightgray;text-align:center">	
							<div  id="btn">
							<c:if test="${comment.userid == sessionScope.userid}">
							
								<a href="javascript:void(0)" onclick="cmUpdateOpen(${comment.pcidx})">[수정]</a><br/>
								<a href="javascript:void(0)" onclick="cmDeleteOpen(${comment.pcidx})">[삭제]</a>
							</c:if>		
							</div>
						</td>
					</tr>
					
				</c:forEach>
			</c:if>
			
			
		</table>
		
		
	</div>
<form id="prodComm" action="../product/productDetailAction.yo" method="post">
<input type="hidden" id="pageNum" name="pageNum" value="${pageNum}" />
<input type="hidden" id="pNum" name="pNum" value="${pNum}" />
<input type="hidden" id="ePage" name="ePage" value="${ePage}" />
</form>
</body>
</html>