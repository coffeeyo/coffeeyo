package com.coffeeyo.order.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.order.model.Cart;
import com.coffeeyo.order.model.CartDao;
import com.coffeeyo.order.model.Order;
import com.coffeeyo.order.model.OrderDao;
import com.coffeeyo.order.model.OrderItem;
import com.coffeeyo.order.model.OrderItemDao;

public class OrderProcAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("OrderProcAction execute()");

		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		String id =null;
		
		/* 추가된 부분, session 은 try{} catch(){} 으로 처리 해야 함.*/
		// sessioin에서 불러온 id 확인 id가 없으면 로그아웃
		try {
			id = session.getAttribute("userid").toString();
		}
		catch(Exception  e) {
			
		}
		
		if(id == null) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그아웃 되었습니다..');");
			out.println("location.href='/';");
			out.println("</script>");
			out.close();
			return "none";
		}
		
		//결제하기 버튼을 누르면 결제처리하는 프로세스
		//	변수선언
		//	orderDAO
		// orderItemDAO
		//	cartDAO
		
		CartDao cdao = CartDao.getInstance();
		Cart cart = new Cart();
		
		cart.setUserid(id);
		cart.setBuychk("Y");
		
		//	tm은 카트에서 제조소요시간을 불러와서 담은 변수이다.
		int tm	=	cdao.getReadyTime(cart);
		System.out.println("tm="+tm);
		
		//	readytm은 카트에서 불러온 각 주문에 대한 total금액이다.
		long total	=	cdao.getTotalPrice(cart);
		
		// 주문을 생성하고
		Order ord	= new Order();
		OrderDao orddao	= OrderDao.getInstance();
		// orddao에서 주문번호를 만든 것을 담는다
		String orderno =orddao.getSeq();
		
		// Order의 VO에 orderno,를 넣는다
		ord.setOrderno(orderno);
		
		String readytm = null;
		// 현재 시각 구하기
		java.util.Date today = new java.util.Date();
		//	날짜변환   포맷변경 ( 년월일 시분초)
		SimpleDateFormat time	=	new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 현재 시간에서 제조시간 더하기
		  Calendar cal = Calendar.getInstance();
		  
		  cal.setTime(today);
		  cal.add(Calendar.MINUTE, tm);
		  readytm = time.format(cal.getTime());  
		  System.out.println("readytm="+readytm);	//확인용
		  
		  
		  ord.setReadytm(readytm);		// DATE타입으로 readytm을 넣어준다
		  
		//int count	=	orddao.getCountStatus(ord);	//	오늘날짜에서의 준비 중인 커피의 수
		
		//Order의 VO에total 를 넣는다
		ord.setTotal(total);
		ord.setUserid(id);
		ord.setOrderno(orderno);
		// orders 테이블 orderitem 테이블에 insert문 + cart check 'y' 삭제
		orddao.orderProc(ord);
		request.setAttribute("orderno", orderno);
		
		return "../order/orderPayConfirmAction.yo";
	}

}
