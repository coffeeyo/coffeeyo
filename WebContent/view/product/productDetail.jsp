<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
	
	//댓글 등록
	function writeCmt()
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
		
	}
	
	function addCart() {
		var direct = 'N';
		var num = $('#num').val();
		var sum = $('#sum_price').val();
		var amt = $('#amt').val();
		var opt = $('#opt').val();
        
        var param = 'num='+num+'&amt='+amt+'&sum_price='+sum+'&opt'+opt+'&direct='+direct;
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
					$('#btnCartOpen').text('닫기');
					$.cookie("cartOpen", "N", { expires: -1 });
					$.cookie('cartOpen', 'Y', { expires: 7, path: '/', domain: 'localhost', secure: false });
					$('.cart_r').css('width','400px');
					
					location.reload();
				}
			},
			error:function(){
			}
		});
		
		return;
	}
	
	function direcBuy() {		
		var direct = 'Y';
		var num = $('#num').val();
		var sum = $('#sum_price').val();
		var amt = $('#amt').val();
		var opt = $('#opt').val();
        
        var param = 'num='+num+'&amt='+amt+'&sum_price='+sum+'&opt'+opt+'&direct='+direct;
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
</script>
</head>
<body>
<div class="container marketing">
	<div class="mrgn-15"></div>
	<h1>${po.cateName}</h1>
	준비중
	<div class="pordthumb">
		<c:if test="${po.image ne null}">
			<img src="../view/upload/product/${po.image}" title="${po.pname}" alt="${po.pname}" style="cursor:pointer;" />
		</c:if>
	</div>
	<div class="pordRight">
		<p>${po.pname}</p>
		<p>${po.price}원</p>
		
		<p>합계 : <span id="total">${po.price}</span>원</p>
		<p>수량: <input type="number" id="amt" name="amt" value="1" /></p>
		<p>옵션: <textarea id="opt" name="opt">테스트 더미 옵션1</textarea></p>
		<input type="hidden" id="num" name="num" value="${po.pidx}" />
		<input type="hidden" id="sum_price" name="sum_price" value="${po.price}" />
	</div>
  	<div class="prodBtn01">
  		<input type="button" value="장바구니 담기" onclick="addCart()" />  <input type="button" value="바로구매" onclick="direcBuy()" />
  	</div>
	<p/>
	
	
	<!-- 댓글 부분 -->
	<div class="comment">
		<div>고객총평점: ★★★★☆  <fmt:formatNumber value="${commentAvg}" pattern="0.0"/>/5</div>
		<table border="1" bordercolor="lightgray" class="bbscomm">
		
			<!-- 로그인 했을 경우만 댓글 작성가능 -->
			<c:if test="${sessionScope.userid !=null}">
			<tr bgcolor="#F5F5F5">
			<form id="writeCommentForm">
				<input type="hidden" id="comment_board" name="comment_board" value="${num}">
				<input type="hidden" id="comment_id" name="comment_id" value="${sessionScope.userid}">
				<!-- 아이디-->
				<td width="150">
					<div>
						${sessionScope.nick} ☆☆☆☆☆
					</div>
				</td>
				<!-- 본문 작성-->
				<td width="550">
					<div>
						<textarea id="comment_content" name="comment_content" rows="4" cols="70" ></textarea>
					</div>
				</td>
				<!-- 댓글 등록 버튼 -->
				<td width="100">
					<div id="btn">
						<p><a href="#" onclick="writeCmt()">[등록]</a></p>	
					</div>
				</td>
			</form>
			</tr>
			</c:if>
			
			<!-- 댓글 목록 -->	
			<c:if test="${requestScope.commentList != null}">
				<c:forEach var="comment" items="${requestScope.commentList}">
					<tr>
						<!-- 아이디, 작성날짜 -->
						<td width="150">
							<div>					
								${comment.nickName} ☆☆☆☆☆ ${comment.pcpoint} / ?  <br>
								<font size="2" color="lightgray">${comment.createdt}</font>
							</div>
						</td>
						<!-- 본문내용 -->
						<td width="550">
							<div class="text_wrapper">
								${fn:replace(comment.comm, cn, br)}
								<p/>
								<fmt:formatDate value="${comment.createdt}" pattern="yyyy년 MM월dd일 a hh시mm분 ss초"/> 
							</div>
						</td>
						<!-- 버튼 -->
						<td width="100">
							<div  id="btn">
							<c:if test="${comment.userid == sessionScope.userid}">
								<a href="javascript:void(0)" onclick="cmUpdateOpen(${comment.bcidx})">[수정]</a><br>	
								<a href="javascript:void(0)" onclick="cmDeleteOpen(${comment.bcidx})">[삭제]</a>
							</c:if>		
							</div>
						</td>
					</tr>
					
				</c:forEach>
			</c:if>
			
			
		</table>
		
		<br>
		<div class="bbsPageForm">
		
			<c:if test="${fn:length(requestScope.commentList) > 0}">
				<c:if test="${sPage != 1}">
					<a href='../product/productDetailAction.yo?pNum=${sPage-1}'>[ 이전 ]</a>
				</c:if>
				
				<c:forEach var="pNum" begin="${sPage}" end="${ePage}">
					<c:if test="${pNum == spg}">
						${pNum}&nbsp;
					</c:if>
					<c:if test="${pNum != spg}">
						<a href='../product/productDetailAction.yo?pNum=${pNum}'>${pNum}&nbsp;</a>
					</c:if>
				</c:forEach>
				
				<c:if test="${ePage != mxPage }">
					<a href='../product/productDetailAction.yo?pNum=${ePage+1 }'>[다음]</a>
				</c:if>
			</c:if>
			
		</div>
	</div>
	
	
	
</div>
<form id="prodComm" action="../product/productDetailAction.yo" method="post">
<input type="hidden" id="pageNum" name="pageNum" value="${pageNum}" />
<input type="hidden" id="pNum" name="pNum" />
<input type="hidden" id="ePage" name="ePage" />
</form>


</body>
</html>