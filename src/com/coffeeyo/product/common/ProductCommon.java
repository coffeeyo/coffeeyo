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
		
		// 현재 페이지 번호 만들기
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
		int maxPage = (int)(listCount/10.0 + 0.9);
		
		// 만약 사용자가 주소창에서 페이지 번호를 maxPage 보다 높은 값을 입력시
		// maxPage에 해당하는 목록을 보여준다.
		if(spage > maxPage) spage = maxPage;
		listOpt.put("start", spage*10-9);
		
		List<Product> prodList = null;
		prodList = dao.getAllProduct(listOpt);
				
		//시작 페이지 번호
		int startPage = (int)(spage/5.0 + 0.8) * 5 - 4;
		//마지막 페이지 번호
		int endPage = startPage + 4;
		if(endPage > maxPage)	endPage = maxPage;
		
		request.setAttribute("prodList", prodList);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("cateList", cateList);
		
		// 4개 페이지번호 저장
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
		// 현재 페이지 번호 만들기
		int spg = 1;
		String pNum = request.getParameter("pNum");
		
		if(pNum != null && !pNum.equals(""))	spg = Integer.parseInt(pNum);
		
		// 한 화면에 10개의 게시글을 보여지게함
		// 페이지 번호는 총 5개, 이후로는 [다음]으로 표시
		
		
		int listCount = commentDAO.getProdCommentListCount(pidx);
		
		float commentAvg = commentDAO.getProdCommentPointAvg(pidx);
		
		// 전체 페이지 수
		int maxPg = (int)(listCount/10.0 + 0.9);
		
		// 만약 사용자가 주소창에서 페이지 번호를 maxPage 보다 높은 값을 입력시
		// maxPage에 해당하는 목록을 보여준다.
		HashMap<String, Object> listOpt = new HashMap<String, Object>();
		if(spg > maxPg) spg = maxPg;
		listOpt.put("start", spg*10-9);
		
		// 게시글 번호를 이용하여 해당 글에 있는 댓글 목록을 가져온다.
		
		listOpt.put("pidx", pidx);
		ArrayList<ProductComm> commentList = commentDAO.getCommentList(listOpt);
		
		// 댓글이 1개라도 있다면 request에 commentList를 세팅한다.
		if(commentList.size() > 0)	request.setAttribute("commentList", commentList);
		
		//시작 페이지 번호
		int sPage = (int)(spg/5.0 + 0.8) * 5 - 4;
		//마지막 페이지 번호
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
