package com.coffeeyo.order.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.order.model.Cart;
import com.coffeeyo.order.model.CartDao;

public class CartListFormAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CartListFormAction execute()");

		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		String id = session.getAttribute("userid").toString();
		
		CartDao dao = CartDao.getInstance();
		Cart cart = new Cart();
		cart.setUserid(id);
		cart.setBuychk("N");
		
		ArrayList<Cart> cartList = dao.getAllCart(cart);
		//System.out.println("CartListFormAction cartList : " + cartList);
		request.setAttribute("cartList", cartList);

		return "../view/popup/cartList.jsp";
	}
}
