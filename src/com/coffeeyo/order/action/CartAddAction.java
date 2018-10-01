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
		int num = Integer.parseInt(request.getParameter("num"));
		String direct = request.getParameter("direct");
		int amt = Integer.parseInt(request.getParameter("amt"));
		int sum = Integer.parseInt(request.getParameter("sum_price"));
		String opt = request.getParameter("opt");
		
		cart.setCidx(cartNum);
		cart.setPidx(num);
		cart.setBuychk(direct);
		cart.setUserid(id);
		cart.setAmount(amt);
		cart.setPrice(sum);
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
			
		return null;
	}
}
