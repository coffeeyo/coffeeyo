package com.coffeeyo.order.action;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.order.model.Order;
import com.coffeeyo.order.model.OrderDao;
import com.coffeeyo.order.model.OrderItem;
import com.coffeeyo.order.model.OrderItemDao;

public class OrderHistoryListAdmAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//����
		
				// �Ķ���� �ް�
				request.setCharacterEncoding("UTF-8");
				
				HttpSession session = request.getSession();
				String id =null;
				
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
				
				ord.setUserid(id);
				
				ArrayList<Order> orderList = null;
				
				orderList = dao.getAllOrderAdm(ord);
				
				//System.out.println("CartListFormAction cartList : " + cartList);
				// ��
				request.setAttribute("orderList", orderList);
				// ��
				
		return "../view/admin/order/orderHistoryListAdm.jsp";
	}

}
