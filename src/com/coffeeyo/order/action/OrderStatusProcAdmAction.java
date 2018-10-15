package com.coffeeyo.order.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.order.model.Order;
import com.coffeeyo.order.model.OrderDao;

public class OrderStatusProcAdmAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
System.out.println("OrderStatusProcAdmActionController");
		
		//����
		
		// �Ķ���� �ް�
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		String id =null;
		
		String orderno= request.getParameter("orderno").toString();
		System.out.println("������orderno="+orderno);
		String userid= request.getParameter("userid").toString();
		System.out.println("������userid="+userid);
		/* �߰��� �κ�, session �� try{} catch(){} ���� ó�� �ؾ� ��.*/
		
		
		// ����Ͻ�����
		
		OrderDao dao = OrderDao.getInstance();
		Order ord = new Order();
		ord.setUserid(userid);
		ord.setOrderno(orderno);
		
		dao.updateStatusProc(ord);
		dao.getOrderConfirm(ord);
//		//���������� �����Ͱ� �Ѱ� �����ϴ� �𵨡ڡڡ�
//		ord.getOrderno();
//		ord.getTotal();
//		ord.getReadytm();
		request.setAttribute("ORD",ord);
		
		// ��
		return "../view/admin/order/orderStatusProc.jsp";
	}

}
