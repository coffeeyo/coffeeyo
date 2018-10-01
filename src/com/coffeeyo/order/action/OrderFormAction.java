package com.coffeeyo.order.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.order.model.Cart;
import com.coffeeyo.order.model.CartDao;
import com.coffeeyo.product.model.Category;
import com.coffeeyo.product.model.CategoryDao;

public class OrderFormAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("OrderFormAction execute()");

		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		String id = session.getAttribute("userid").toString();
		
		CartDao dao = CartDao.getInstance();
		Cart cart = new Cart();
		cart.setUserid(id);
		cart.setBuychk("Y");
		
		ArrayList<Cart> cartList = dao.getAllCart(cart);
		//System.out.println("CartListFormAction cartList : " + cartList);
		
		CategoryDao cateDao = CategoryDao.getInstance();
		List<Category> cateList = null;
		cateList = cateDao.getAllCategory();
		
		request.setAttribute("buyChkList", cartList);
		request.setAttribute("cateList", cateList);

		return "../view/order/orderForm.jsp";
	}
}
