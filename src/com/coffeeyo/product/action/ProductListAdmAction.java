package com.coffeeyo.product.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.product.common.ProductCommon;

public class ProductListAdmAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("BoardListAction execute()");

		request.setCharacterEncoding("UTF-8");
		
		request = ProductCommon.productList(request);

		return "../view/admin/product/productList.jsp";
	}
}
