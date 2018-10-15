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
		//����
		
		// �Ķ���� �ް�
		request.setCharacterEncoding("UTF-8");
		
		String startDay = request.getParameter("startDay");
		String endDay = request.getParameter("endDay");
		
		if(startDay != null && startDay.equals("")) {
			startDay = null;
		}
		if(endDay != null && endDay.equals("")) {
			endDay = null;
		}
		
		// ���� ������ ��ȣ �����
		String strPage = request.getParameter("nowPage");
		
		int nowPage=0;
		if(strPage==null || strPage.length()==0) {
			//�Ķ���Ͱ� ����
			nowPage=1;
		}else {
			nowPage=Integer.parseInt(strPage);					
		}
		
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
		// �˻����ǰ� ������ Map�� ��´�.
		HashMap<String, Object> listOpt = new HashMap<String, Object>();
		listOpt.put("startDay", startDay);
		listOpt.put("endDay", endDay);

		OrderDao dao = OrderDao.getInstance();
		
		int listCount = dao.getOrderCount(listOpt);
		
		Order ord = new Order();
		
		ord.setUserid(id);
		
		//������ ������ ����� ����
		//�� ȭ�鿡�� 10���� �Խù��� ���̵��� �ϰ�
		//�� ȭ�鿡�� 3���� ������ �̵� ����� ���� ����
		PageUtil pinfo=new PageUtil(nowPage,listCount,10,3);
		
		ArrayList<Order> orderList = null;
		
		orderList = dao.getAllOrderAdm(ord, pinfo, listOpt);
		
		//System.out.println("CartListFormAction cartList : " + cartList);
		// ��
		request.setAttribute("orderList", orderList);
		request.setAttribute("PINFO", pinfo);
		request.setAttribute("listCount", listCount);
		request.setAttribute("nowPage", nowPage);
		request.setAttribute("startDay", startDay);
		request.setAttribute("endDay", endDay);
		// ��
				
		return "../view/admin/order/orderHistoryListAdm.jsp";
	}

}
