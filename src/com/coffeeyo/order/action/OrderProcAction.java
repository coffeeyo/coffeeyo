package com.coffeeyo.order.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.order.model.Cart;
import com.coffeeyo.order.model.CartDao;
import com.coffeeyo.order.model.Order;
import com.coffeeyo.order.model.OrderDao;
import com.coffeeyo.order.model.OrderItem;
import com.coffeeyo.order.model.OrderItemDao;

public class OrderProcAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("OrderProcAction execute()");

		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		String id =null;
		
		/* �߰��� �κ�, session �� try{} catch(){} ���� ó�� �ؾ� ��.*/
		// sessioin���� �ҷ��� id Ȯ�� id�� ������ �α׾ƿ�
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
		
		//�����ϱ� ��ư�� ������ ����ó���ϴ� ���μ���
		//	��������
		//	orderDAO
		// orderItemDAO
		//	cartDAO
		
		CartDao cdao = CartDao.getInstance();
		Cart cart = new Cart();
		
		cart.setUserid(id);
		cart.setBuychk("Y");
		
		//	tm�� īƮ���� �����ҿ�ð��� �ҷ��ͼ� ���� �����̴�.
		int tm	=	cdao.getReadyTime(cart);
		System.out.println("tm="+tm);
		
		//	readytm�� īƮ���� �ҷ��� �� �ֹ��� ���� total�ݾ��̴�.
		long total	=	cdao.getTotalPrice(cart);
		
		// �ֹ��� �����ϰ�
		Order ord	= new Order();
		OrderDao orddao	= OrderDao.getInstance();
		// orddao���� �ֹ���ȣ�� ���� ���� ��´�
		String orderno =orddao.getSeq();
		
		// Order�� VO�� orderno,�� �ִ´�
		ord.setOrderno(orderno);
		
		String readytm = null;
		// ���� �ð� ���ϱ�
		java.util.Date today = new java.util.Date();
		//	��¥��ȯ   ���˺��� ( ����� �ú���)
		SimpleDateFormat time	=	new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// ���� �ð����� �����ð� ���ϱ�
		  Calendar cal = Calendar.getInstance();
		  
		  cal.setTime(today);
		  cal.add(Calendar.MINUTE, tm);
		  readytm = time.format(cal.getTime());  
		  System.out.println("readytm="+readytm);	//Ȯ�ο�
		  
		  
		  ord.setReadytm(readytm);		// DATEŸ������ readytm�� �־��ش�
		  
		//int count	=	orddao.getCountStatus(ord);	//	���ó�¥������ �غ� ���� Ŀ���� ��
		
		//Order�� VO��total �� �ִ´�
		ord.setTotal(total);
		ord.setUserid(id);
		ord.setOrderno(orderno);
		// orders ���̺� orderitem ���̺� insert�� + cart check 'y' ����
		orddao.orderProc(ord);
		request.setAttribute("orderno", orderno);
		
		return "../order/orderPayConfirmAction.yo";
	}

}
