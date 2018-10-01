package com.coffeeyo.index.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.product.common.ProductCommon;

public class IndexListAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("IndexListAction execute()");

		request.setCharacterEncoding("UTF-8");
		
		request = ProductCommon.productListIndex(request);

		return "/index.jsp";
	}
}
