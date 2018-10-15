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
import com.coffeeyo.product.model.Category;
import com.coffeeyo.product.model.CategoryDao;

public class OrderHistoryListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//할일
		// 선배님코드 따서 만든거------------------------------시작
		
		
		//	선배님코드 따서 만든거------------------------------끝
		
		// 파라미터 받고
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		String id =null;
		
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
		ord.setUserid(id);
		
		ArrayList<Order> orderList = null;
		orderList = dao.getAllOrder(ord);		//페이징 실패시 다시 원상복구

		//System.out.println("CartListFormAction cartList : " + cartList);
		// 모델
		
		//-----------------------------페이징 검색 처리 못하겠다
		//-------------------------------페이징 검색 처리 끝
		
		
		CategoryDao cateDao = CategoryDao.getInstance();
		List<Category> cateList = null;
		cateList = cateDao.getAllCategory();

		request.setAttribute("cateList", cateList);
		request.setAttribute("orderList", orderList);
		// 뷰
				
		return "../view/order/orderHistoryList.jsp";
	}

}
