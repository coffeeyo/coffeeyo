package com.coffeeyo.order.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.order.model.Order;
import com.coffeeyo.order.model.OrderDao;
import com.coffeeyo.order.model.OrderItem;
import com.coffeeyo.order.model.OrderItemDao;
import com.coffeeyo.product.model.Category;
import com.coffeeyo.product.model.CategoryDao;

public class OrderHistoryDetailAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//����
		
		
		// �Ķ���� �ް�
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		String id =null;
		System.out.println("1111");
		String orderno= request.getParameter("orderno").toString();
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
			return "none";
		}
		// ����Ͻ�����
		OrderDao dao = OrderDao.getInstance();
		Order ord = new Order();
		OrderItemDao oidao	=	OrderItemDao.getInstance();
		OrderItem oitem	=	new OrderItem();
		
		ord.setUserid(id);
		ord.setOrderno(orderno);
		
		oitem.setOrderno(orderno);
		System.out.println("orderno detail="+orderno);

		//System.out.println("CartListFormAction cartList : " + cartList);
		// ��
		dao.getOrderConfirm(ord);
		ArrayList<OrderItem> oitemList = null;				
		oitemList	=	oidao.getAllOrderItem(ord);
		
		//���������� �����Ͱ� �Ѱ� �����ϴ� �𵨡ڡڡ�
		ord.getTotal();
		ord.getReadytm();
		
		
		CategoryDao cateDao = CategoryDao.getInstance();
		List<Category> cateList = null;
		cateList = cateDao.getAllCategory();

		request.setAttribute("cateList", cateList);
		request.setAttribute("ORD",ord);
		request.setAttribute("oitemList", oitemList);
		// ��
				
		return "../view/order/orderHistoryDetail.jsp";
	}

}
