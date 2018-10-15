package com.coffeeyo.product.action;


import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.product.comment.model.ProductComm;
import com.coffeeyo.product.comment.model.ProductCommDao;
import com.coffeeyo.product.model.Category;
import com.coffeeyo.product.model.CategoryDao;
import com.coffeeyo.product.model.Product;
import com.coffeeyo.product.model.ProductDao;

public class ProductListAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ProductListAction execute()");
		request.setCharacterEncoding("UTF-8");
		//request = ProductCommon.productList(request);

		int spage = 1;
		String pageNum = request.getParameter("pageNum");
		
		if(pageNum != null && !pageNum.equals(""))	spage = Integer.parseInt(pageNum);
	
		
		
		// 검색조건과 검색내용을 가져온다.
		String opt = request.getParameter("opt");
		String condition = request.getParameter("condition");
		String strCidx = request.getParameter("cidx");
		int cidx = 0;
		if(strCidx != null && !strCidx.equals("")) cidx = Integer.parseInt(strCidx);
		
		// 검색조건과 내용을 Map에 담는다.
		HashMap<String, Object> listOpt = new HashMap<String, Object>();
		listOpt.put("opt", opt);
		listOpt.put("condition", condition);
		listOpt.put("cidx", cidx);
		
		ProductDao dao = ProductDao.getInstance();
		
		int listCount = dao.getProductListCount(listOpt);
	
		CategoryDao cateDao = CategoryDao.getInstance();
		List<Category> cateList = null;
		cateList = cateDao.getAllCategory();
			
		listOpt.put("start", spage*10-9);
		
		List<Product> prodList = null;
		//prodList = dao.memProductList(listOpt);
		prodList = dao.memProductList(listOpt);
		
						
		request.setAttribute("spage", spage);
		request.setAttribute("listCount", listCount);	
		request.setAttribute("cateList", cateList);
		request.setAttribute("prodList", prodList);
		
	
		
	

		return "../view/product/productListMem.jsp";
	}
}
