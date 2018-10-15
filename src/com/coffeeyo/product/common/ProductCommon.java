package com.coffeeyo.product.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.coffeeyo.product.comment.model.ProductComm;
import com.coffeeyo.product.comment.model.ProductCommDao;
import com.coffeeyo.product.model.Category;
import com.coffeeyo.product.model.CategoryDao;
import com.coffeeyo.product.model.Product;
import com.coffeeyo.product.model.ProductDao;

public class ProductCommon {
	public static ArrayList<Product> prodRecommList(long cidx) throws Exception {
		System.out.println("ProductCommon prodRecommList()");
				
		ProductDao dao = ProductDao.getInstance();
		
		ArrayList<Product> prodList = null;
		prodList = dao.getRecommProduct(cidx);
		
		return prodList;
	}
	
	public static HttpServletRequest productList(HttpServletRequest request) throws Exception {
		System.out.println("ProductCommon productList()");
		request.setCharacterEncoding("UTF-8");
		
		// ���� ������ ��ȣ �����
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
		int maxPage = (int)(listCount/10.0 + 0.9);
		
		// ���� ����ڰ� �ּ�â���� ������ ��ȣ�� maxPage ���� ���� ���� �Է½�
		// maxPage�� �ش��ϴ� ����� �����ش�.
		if(spage > maxPage) spage = maxPage;
		listOpt.put("start", spage*10-9);
		
		List<Product> prodList = null;
		prodList = dao.getAllProduct(listOpt);
				
		//���� ������ ��ȣ
		int startPage = (int)(spage/5.0 + 0.8) * 5 - 4;
		//������ ������ ��ȣ
		int endPage = startPage + 4;
		if(endPage > maxPage)	endPage = maxPage;
		
		request.setAttribute("prodList", prodList);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("cateList", cateList);
		
		// 4�� ��������ȣ ����
		request.setAttribute("spage", spage);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("listCount", listCount);	
				
		return request;
	}
	
	public static HttpServletRequest productListIndex(HttpServletRequest request) throws Exception {
		System.out.println("ProductCommon productList()");
		request.setCharacterEncoding("UTF-8");
		
				
		ProductDao dao = ProductDao.getInstance();
		CategoryDao cateDao = CategoryDao.getInstance();
				
		List<Product> prodList1 = null;
		prodList1 = dao.getAllProduct(1);
		
		List<Product> prodList2 = null;
		prodList2 = dao.getAllProduct(2);
		
		List<Product> prodList3 = null;
		prodList3 = dao.getAllProduct(3);
		
		List<Product> recommList = null;
		recommList = prodRecommList(0);
		
		List<Category> cateList = null;
		cateList = cateDao.getAllCategory();

		request.setAttribute("cateList", cateList);
		request.setAttribute("recommList", recommList);
		request.setAttribute("prodList1", prodList1);
		request.setAttribute("prodList2", prodList2);
		request.setAttribute("prodList3", prodList3);
				
		return request;
	}
	
	public static HttpServletRequest ProductDetail(HttpServletRequest request) throws Exception {
		System.out.println("ProductCommon ProductDetail()");
		request.setCharacterEncoding("UTF-8");
		
		int pidx = Integer.parseInt(request.getParameter("pid"));
		int cidx = Integer.parseInt(request.getParameter("cid"));
		
		String pageNum = request.getParameter("pageNum");
		System.out.println("pageNum="+pageNum);
				
		CategoryDao cateDao = CategoryDao.getInstance();
		List<Category> cateList = null;
		cateList = cateDao.getAllCategory();
		
		ProductDao dao = ProductDao.getInstance();
		Product po = dao.getProduct(pidx);
		
		
		ProductCommDao commentDAO = ProductCommDao.getInstance();
		// ���� ������ ��ȣ �����
		int spg = 1;
		String pNum = request.getParameter("pNum");
		
		if(pNum != null && !pNum.equals(""))	spg = Integer.parseInt(pNum);
		
		// �� ȭ�鿡 10���� �Խñ��� ����������
		// ������ ��ȣ�� �� 5��, ���ķδ� [����]���� ǥ��
		
		
		int listCount = commentDAO.getProdCommentListCount(pidx);
		
		float commentAvg = commentDAO.getProdCommentPointAvg(pidx);
		
		// ��ü ������ ��
		int maxPg = (int)(listCount/10.0 + 0.9);
		
		// ���� ����ڰ� �ּ�â���� ������ ��ȣ�� maxPage ���� ���� ���� �Է½�
		// maxPage�� �ش��ϴ� ����� �����ش�.
		HashMap<String, Object> listOpt = new HashMap<String, Object>();
		if(spg > maxPg) spg = maxPg;
		listOpt.put("start", spg*10-9);
		
		// �Խñ� ��ȣ�� �̿��Ͽ� �ش� �ۿ� �ִ� ��� ����� �����´�.
		
		listOpt.put("pidx", pidx);
		ArrayList<ProductComm> commentList = commentDAO.getCommentList(listOpt);
		
		// ����� 1���� �ִٸ� request�� commentList�� �����Ѵ�.
		if(commentList.size() > 0)	request.setAttribute("commentList", commentList);
		
		//���� ������ ��ȣ
		int sPage = (int)(spg/5.0 + 0.8) * 5 - 4;
		//������ ������ ��ȣ
		int ePage = sPage + 4;
		if(ePage > maxPg)	ePage = maxPg;
		

		request.setAttribute("po", po);
		request.setAttribute("cateList", cateList);
		request.setAttribute("num", pidx);
		request.setAttribute("cidx", cidx);
		request.setAttribute("pageNum", pageNum);
		
		request.setAttribute("spg", spg);
		request.setAttribute("maxPg", maxPg);
		request.setAttribute("sPage", sPage);
		request.setAttribute("ePage", ePage);
		request.setAttribute("listCount", listCount);	
		request.setAttribute("commentAvg", commentAvg);
		
		return request;
	}
	
}
