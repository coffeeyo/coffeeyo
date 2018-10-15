package com.coffeeyo.order.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.order.model.Cart;
import com.coffeeyo.order.model.Order;
import com.coffeeyo.order.model.OrderDao;
import com.coffeeyo.product.model.Category;
import com.coffeeyo.product.model.CategoryDao;

public class OrderPayConfirmAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ConfirmAction Controller");
		
		//����
		
		// �Ķ���� �ް�
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		String id =null;
		
		String orderno= request.getAttribute("orderno").toString();
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
		ord.setOrderno(orderno);
		
		//ord.setOrderno(orderno); 		//------------------------------ �����������
		dao.getOrderConfirm(ord);
		//���������� �����Ͱ� �Ѱ� �����ϴ� �𵨡ڡڡ�
		ord.getOrderno();
		ord.getTotal();
		ord.getReadytm();
		
		CategoryDao cateDao = CategoryDao.getInstance();
		List<Category> cateList = null;
		cateList = cateDao.getAllCategory();

		request.setAttribute("cateList", cateList);
		request.setAttribute("ORD",ord);
		
		// ��
		
		return "../view/order/orderPayConfirm.jsp";
	}

}
