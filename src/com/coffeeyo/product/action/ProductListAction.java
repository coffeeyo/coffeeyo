package com.coffeeyo.product.action;


import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffeeyo.common.action.Action;

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
		
		// 한 화면에 10개의 게시글을 보여지게함
		// 페이지 번호는 총 5개, 이후로는 [다음]으로 표시
		
		// 전체 페이지 수
		//int maxPage = (int)(listCount/10.0 + 0.9);
		
		// 만약 사용자가 주소창에서 페이지 번호를 maxPage 보다 높은 값을 입력시
		// maxPage에 해당하는 목록을 보여준다.
		//if(spage > maxPage) spage = maxPage;
		listOpt.put("start", spage*10-9);
		
		List<Product> prodList = null;
		prodList = dao.getAllProduct(listOpt);
				
		//시작 페이지 번호
		//int startPage = (int)(spage/5.0 + 0.8) * 5 - 4;
		//마지막 페이지 번호
		//int endPage = startPage + 4;
		//if(endPage > maxPage)	endPage = maxPage;
		
		request.setAttribute("prodList", prodList);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("cateList", cateList);
		request.setAttribute("listCount", listCount);	

		// 4개 페이지번호 저장
		//request.setAttribute("spage", spage);
		//request.setAttribute("maxPage", maxPage);
		//request.setAttribute("startPage", startPage);
		//request.setAttribute("endPage", endPage);
	

		return "../view/product/productListMem.jsp";
	}
}
