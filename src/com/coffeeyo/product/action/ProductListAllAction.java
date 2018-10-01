package com.coffeeyo.product.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.product.model.Product;
import com.coffeeyo.product.model.ProductDao;

public class ProductListAllAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("BoardListAction execute()");

		response.setContentType("application/json; charset=UTF-8");
		JSONObject jsonobj = new JSONObject();
		
		long cate = Integer.parseInt(request.getParameter("cidx"));
		
		ProductDao dao = ProductDao.getInstance();
		List<Product> prodList = null;
		prodList = dao.getAllProduct(cate);
		
		//request.setAttribute("prodList", prodList);

		PrintWriter out = response.getWriter();
		
		// 정상적으로 댓글을 수정했을경우 1을 전달한다.		
		JSONArray jArray = new JSONArray();
	    for (Product prod : prodList)
	    {
	         JSONObject prodJSON = new JSONObject();
	         prodJSON.put("pidx", prod.getPidx());
	         prodJSON.put("pname", prod.getPname());
	         
	         jArray.add(prodJSON);
	    }
	    jsonobj.put("prodList", jArray);
		
		//System.out.println("prodList :" + jsonobj);
		
		out.print(jsonobj.toString());
		out.flush();
		out.close();

		return null;
	}
}
