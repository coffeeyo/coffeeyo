package com.coffeeyo.order.action;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.member.model.Member;
import com.coffeeyo.order.model.Order;
import com.coffeeyo.order.model.OrderDao;
import com.coffeeyo.order.model.OrderItem;
import com.coffeeyo.order.model.OrderItemDao;

public class OrderHistoryDetailAdmAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
				//할일
				
				// 파라미터 받고
				request.setCharacterEncoding("UTF-8");
				
				HttpSession session = request.getSession();
				String id =null;
				
				// orderno 파라미터 받음
				String orderno= request.getParameter("orderno").toString();
				System.out.println("OrderHistoryDetailAdmAction.java의 orderno="+orderno);
				String userid= request.getParameter("userid").toString();
				System.out.println("userid="+userid);
				/* 추가된 부분, session 은 try{} catch(){} 으로 처리 해야 함.*/
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
					return null;
				}
				// 비즈니스로직
				OrderDao dao = OrderDao.getInstance();
				Order ord = new Order();
				OrderItemDao oidao	=	OrderItemDao.getInstance();
				OrderItem oitem	= new OrderItem();
				Order ordrt	=	null;
				
				
				ord.setUserid(userid);	// 세션에서의 id말고 주문과 join된 아이디 뽑아내기 set
				ord.setOrderno(orderno);	//orderno set
				
				
				Member member	=	new Member();
				//select member에서 성명,닉네임, 전화번호 받아서 request로 넘기기
				member	=	dao.getSelectMember(ord);
				
				//select ArrayList<OrderItem>
				ArrayList<OrderItem> oitemList = null;
				oitemList = oidao.getAllOrderItemAdm(ord);
				
				// orders에 readytm과 total을 뽑아낸다
				ordrt	=	dao.getOrderConfirmAdm(ord);
				
				// 모델
				request.setAttribute("member", member);
				request.setAttribute("oitemList", oitemList);
				request.setAttribute("ordrt", ordrt);
				
				// 뷰
		return "../view/admin/order/orderHistoryDetailAdm.jsp";
	}

}
