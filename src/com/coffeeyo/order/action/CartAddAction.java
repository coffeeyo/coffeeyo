package com.coffeeyo.order.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.order.model.Cart;
import com.coffeeyo.order.model.CartDao;

public class CartAddAction implements Action {
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		CartDao dao = CartDao.getInstance();
		Cart cart = new Cart();
		
		HttpSession session = request.getSession();
		String id = session.getAttribute("userid").toString();
		
		response.setContentType("application/json; charset=UTF-8");
		JSONObject jsonobj = new JSONObject();
		
		// 파리미터 값을 가져온다.
		int cartNum = dao.getSeq();
		int pdNum = Integer.parseInt(request.getParameter("pdNum"));
		String direct = request.getParameter("direct");
		int pdCount = Integer.parseInt(request.getParameter("pdCount"));
		int price = Integer.parseInt(request.getParameter("numBasicPrice"));
		String opt = request.getParameter("opt");
		String strOptPrice = request.getParameter("optPrice");
		int optPrice = 0;
		if(strOptPrice != null) {
			optPrice = Integer.parseInt(strOptPrice);
		}
		
		cart.setCidx(cartNum);
		cart.setPidx(pdNum);
		cart.setBuychk(direct);
		cart.setUserid(id);
		cart.setAmount(pdCount);
		cart.setPrice(price);
		cart.setOptprice(optPrice);
		cart.setOptions(opt);
				
		boolean result = dao.insertCart(cart);
		
		PrintWriter out = response.getWriter();
				
		if(result){
			jsonobj.put("check", 1);
		}
		else {
			jsonobj.put("check", 0);
		}
		
		out.print(jsonobj.toString());
		out.flush();
		out.close();
				
		return "none";
	}
}
