package com.coffeeyo.order.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.order.model.Order;
import com.coffeeyo.order.model.OrderDao;

public class OrderStatusProcAdmAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
System.out.println("OrderStatusProcAdmActionController");
		
		//할일
		
		// 파라미터 받고
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		String id =null;
		
		String orderno= request.getParameter("orderno").toString();
		System.out.println("마지막orderno="+orderno);
		String userid= request.getParameter("userid").toString();
		System.out.println("마지막userid="+userid);
		/* 추가된 부분, session 은 try{} catch(){} 으로 처리 해야 함.*/
		
		
		// 비즈니스로직
		
		OrderDao dao = OrderDao.getInstance();
		Order ord = new Order();
		ord.setUserid(userid);
		ord.setOrderno(orderno);
		
		dao.updateStatusProc(ord);
		dao.getOrderConfirm(ord);
//		//여러종류의 데이터가 한개 존재하는 모델★★★
//		ord.getOrderno();
//		ord.getTotal();
//		ord.getReadytm();
		request.setAttribute("ORD",ord);
		
		// 뷰
		return "../view/admin/order/orderStatusProc.jsp";
	}

}
