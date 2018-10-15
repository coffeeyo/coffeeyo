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
		
		// �˻����ǰ� �˻������� �����´�.
		String opt = request.getParameter("opt");
		String condition = request.getParameter("condition");
		String strCidx = request.getParameter("cidx");
		int cidx = 0;
		if(strCidx != null && !strCidx.equals("")) cidx = Integer.parseInt(strCidx);
		
		// �˻����ǰ� ������ Map�� ��´�.
		HashMap<String, Object> listOpt = new HashMap<String, Object>();
		listOpt.put("opt", opt);
		listOpt.put("condition", condition);
		listOpt.put("cidx", cidx);
		
		ProductDao dao = ProductDao.getInstance();
		int listCount = dao.getProductListCount(listOpt);
	
		CategoryDao cateDao = CategoryDao.getInstance();
		List<Category> cateList = null;
		cateList = cateDao.getAllCategory();
		
		// �� ȭ�鿡 10���� �Խñ��� ����������
		// ������ ��ȣ�� �� 5��, ���ķδ� [����]���� ǥ��
		
		// ��ü ������ ��
		//int maxPage = (int)(listCount/10.0 + 0.9);
		
		// ���� ����ڰ� �ּ�â���� ������ ��ȣ�� maxPage ���� ���� ���� �Է½�
		// maxPage�� �ش��ϴ� ����� �����ش�.
		//if(spage > maxPage) spage = maxPage;
		listOpt.put("start", spage*10-9);
		
		List<Product> prodList = null;
		prodList = dao.getAllProduct(listOpt);
				
		//���� ������ ��ȣ
		//int startPage = (int)(spage/5.0 + 0.8) * 5 - 4;
		//������ ������ ��ȣ
		//int endPage = startPage + 4;
		//if(endPage > maxPage)	endPage = maxPage;
		
		request.setAttribute("prodList", prodList);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("cateList", cateList);
		request.setAttribute("listCount", listCount);	

		// 4�� ��������ȣ ����
		//request.setAttribute("spage", spage);
		//request.setAttribute("maxPage", maxPage);
		//request.setAttribute("startPage", startPage);
		//request.setAttribute("endPage", endPage);
	

		return "../view/product/productListMem.jsp";
	}
}
