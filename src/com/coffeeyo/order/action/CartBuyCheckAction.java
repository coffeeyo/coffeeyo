package com.coffeeyo.order.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.order.model.Cart;
import com.coffeeyo.order.model.CartDao;

public class CartBuyCheckAction implements Action {
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		CartDao dao = CartDao.getInstance();
		
		
		HttpSession session = request.getSession();
		String id = session.getAttribute("userid").toString();
		
		response.setContentType("application/json; charset=UTF-8");
		JSONObject jsonobj = new JSONObject();
		
		// 파리미터 값을 가져온다.
		String num = request.getParameter("num");
		
		String direct = null;
		direct = request.getParameter("direct");
		String mode = null;
		mode = request.getParameter("mode");
		
		PrintWriter out = response.getWriter();
		
		if(mode.equals("M")) {
			String[] numArr = num.split(",");
			String[] directArr = direct.split(",");
			
			for(int i = 0; i < numArr.length; i++) {
				int cartNum = Integer.parseInt(numArr[i]);
				Cart cart = new Cart();
				cart.setCidx(cartNum);
				cart.setBuychk(directArr[i]);
				cart.setUserid(id);
				
				boolean result = dao.updataBuyCart(cart);
				
				if(result){
					jsonobj.put("check", 1);
				}
				else {
					jsonobj.put("check", 0);
				}
			}
			
		}
		else {
			int cartNum = Integer.parseInt(num);
			Cart cart = new Cart();
			cart.setCidx(cartNum);
			cart.setBuychk(direct);
			cart.setUserid(id);
			//System.out.println(cartNum);
			//System.out.println(direct);
			//System.out.println(id);		
			boolean result = dao.updataBuyCart(cart);
			//System.out.println(result);
			
			if(result){
				jsonobj.put("check", 1);
			}
			else {
				jsonobj.put("check", 0);
			}
		}		
		
		out.print(jsonobj.toString());
		out.flush();
		out.close();
			
		return null;
	}
}
