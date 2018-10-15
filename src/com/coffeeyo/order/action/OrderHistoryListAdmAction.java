package com.coffeeyo.order.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.order.model.Order;
import com.coffeeyo.order.model.OrderDao;
import com.coffeeyo.order.model.OrderItem;
import com.coffeeyo.order.model.OrderItemDao;

import bcom.coffeeyo.board.util.PageUtil;

public class OrderHistoryListAdmAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//할일
		
		// 파라미터 받고
		request.setCharacterEncoding("UTF-8");
		
		String startDay = request.getParameter("startDay");
		String endDay = request.getParameter("endDay");
		
		if(startDay != null && startDay.equals("")) {
			startDay = null;
		}
		if(endDay != null && endDay.equals("")) {
			endDay = null;
		}
		
		// 현재 페이지 번호 만들기
		String strPage = request.getParameter("nowPage");
		
		int nowPage=0;
		if(strPage==null || strPage.length()==0) {
			//파라미터가 없다
			nowPage=1;
		}else {
			nowPage=Integer.parseInt(strPage);					
		}
		
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
		// 검색조건과 내용을 Map에 담는다.
		HashMap<String, Object> listOpt = new HashMap<String, Object>();
		listOpt.put("startDay", startDay);
		listOpt.put("endDay", endDay);

		OrderDao dao = OrderDao.getInstance();
		
		int listCount = dao.getOrderCount(listOpt);
		
		Order ord = new Order();
		
		ord.setUserid(id);
		
		//페이지 정보를 만들어 놓자
		//한 화면에서 10개의 게시물이 보이도록 하고
		//한 화면에는 3개씩 페이지 이동 기능을 만들 예정
		PageUtil pinfo=new PageUtil(nowPage,listCount,10,3);
		
		ArrayList<Order> orderList = null;
		
		orderList = dao.getAllOrderAdm(ord, pinfo, listOpt);
		
		//System.out.println("CartListFormAction cartList : " + cartList);
		// 모델
		request.setAttribute("orderList", orderList);
		request.setAttribute("PINFO", pinfo);
		request.setAttribute("listCount", listCount);
		request.setAttribute("nowPage", nowPage);
		request.setAttribute("startDay", startDay);
		request.setAttribute("endDay", endDay);
		// 뷰
				
		return "../view/admin/order/orderHistoryListAdm.jsp";
	}

}
