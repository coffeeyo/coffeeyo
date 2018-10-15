package com.coffeeyo.product.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.product.common.ProductCommon;

public class ProductDetailAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ProductUpdateFormAction execute()");

		request.setCharacterEncoding("UTF-8");
		
		request = ProductCommon.ProductDetail(request);
		
		return "../view/product/productDetailMem.jsp";
	}
}
