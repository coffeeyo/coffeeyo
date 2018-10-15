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
				//����
				
				// �Ķ���� �ް�
				request.setCharacterEncoding("UTF-8");
				
				HttpSession session = request.getSession();
				String id =null;
				
				// orderno �Ķ���� ����
				String orderno= request.getParameter("orderno").toString();
				System.out.println("OrderHistoryDetailAdmAction.java�� orderno="+orderno);
				String userid= request.getParameter("userid").toString();
				System.out.println("userid="+userid);
				/* �߰��� �κ�, session �� try{} catch(){} ���� ó�� �ؾ� ��.*/
				try {
					id = session.getAttribute("userid").toString();
				}
				catch(Exception  e) {
					
				}
				
				if(id == null) {
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<script>");
					out.println("alert('�α׾ƿ� �Ǿ����ϴ�..');");
					out.println("location.href='/';");
					out.println("</script>");
					out.close();
					return null;
				}
				// ����Ͻ�����
				OrderDao dao = OrderDao.getInstance();
				Order ord = new Order();
				OrderItemDao oidao	=	OrderItemDao.getInstance();
				OrderItem oitem	= new OrderItem();
				Order ordrt	=	null;
				
				
				ord.setUserid(userid);	// ���ǿ����� id���� �ֹ��� join�� ���̵� �̾Ƴ��� set
				ord.setOrderno(orderno);	//orderno set
				
				
				Member member	=	new Member();
				//select member���� ����,�г���, ��ȭ��ȣ �޾Ƽ� request�� �ѱ��
				member	=	dao.getSelectMember(ord);
				
				//select ArrayList<OrderItem>
				ArrayList<OrderItem> oitemList = null;
				oitemList = oidao.getAllOrderItemAdm(ord);
				
				// orders�� readytm�� total�� �̾Ƴ���
				ordrt	=	dao.getOrderConfirmAdm(ord);
				
				// ��
				request.setAttribute("member", member);
				request.setAttribute("oitemList", oitemList);
				request.setAttribute("ordrt", ordrt);
				
				// ��
		return "../view/admin/order/orderHistoryDetailAdm.jsp";
	}

}
