package com.coffeeyo.order.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.order.model.Cart;
import com.coffeeyo.order.model.CartDao;
import com.coffeeyo.order.model.Order;
import com.coffeeyo.order.model.OrderDao;
import com.coffeeyo.order.model.OrderItem;
import com.coffeeyo.product.model.Category;
import com.coffeeyo.product.model.CategoryDao;

public class OrderFormAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("OrderFormAction execute()");

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
			return "none";
		}
		
		CartDao dao = CartDao.getInstance();
		Cart cart = new Cart();
		cart.setUserid(id);
		cart.setBuychk("Y");
		//
		//	tm�� īƮ���� �����ҿ�ð��� �ҷ��ͼ� ���� �����̴�.
		int tm	=	dao.getReadyTime(cart);
		
		// �ֹ��� �����ϰ�
		Order ord	= new Order();
		OrderDao orddao	= OrderDao.getInstance();
		// orddao���� �ֹ���ȣ�� ���� ���� ��´�
		String orderno =orddao.getSeq();
		// Order�� VO�� orderno�� �ִ´�
		ord.setOrderno(orderno);
		
		// īƮ����Ʈ�� �������� orderItem�� �����Ѵ�.
		ArrayList<Cart> cartList = dao.getAllCart(cart);
		
		//System.out.println("CartListFormAction cartList : " + cartList);
		for(int i=0; i<cartList.size(); i++) {
			OrderItem oitem	= new OrderItem();
			oitem.setOrderno(orderno);
			
			
		}
		CategoryDao cateDao = CategoryDao.getInstance();
		List<Category> cateList = null;
		cateList = cateDao.getAllCategory();
		
		
		request.setAttribute("buyChkList", cartList);
		request.setAttribute("cateList", cateList);

		return "../view/order/orderForm.jsp";
	}
}
