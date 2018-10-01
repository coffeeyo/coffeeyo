$(window).scroll(function() {
	var $el = $('.show-on-scroll');
  	
	if($(this).scrollTop() >= 100) $el.addClass('shown');
	else $el.removeClass('shown');
});

function scrollUp() {
	$(window).scrollTop(0);
}

function goAction(link) {
	alert('준비중');
	//location.href = link;
}

function goProdDetail(pidx, cidx) {
	$('#pid').val(pidx);
	$('#cid').val(cidx);
	$('#prodDetailFrm').attr('action','../product/productDetailAction.yo');
	$('#prodDetailFrm').submit();
}

function goBbs() {
	location.href = '/board/boardListAction.yo';
}

function goLogin() {
	location.href = '/member/loginFormAction.yo';
}

function goJoin() {
	location.href = '/member/joinFormAction.yo';
}

function showCart() {
	alert('장바구니 서비스 준비중!');
}

function goCateProduct(cate) {
	$('#cate').val(cate);
	$('#cateFrm').submit();	
}

function setWingToggleHistory() {
	var width = $('.cart_r').css('width');
	
	if(width != '400px') {
		$('#btnCartOpen').text('닫기');
		$.cookie("cartOpen", "N", { expires: -1 });
		$.cookie('cartOpen', 'Y', { expires: 7, path: '/', domain: 'localhost', secure: false });
		$('.cart_r').css('width','400px');
	}
	else {
		$('#btnCartOpen').text('열기');
		$.cookie("cartOpen", "Y", { expires: -1 });
		$.cookie('cartOpen', 'N', { expires: 7, path: '/', domain: 'localhost', secure: false });
		$('.cart_r').css('width','0px');
	}
}