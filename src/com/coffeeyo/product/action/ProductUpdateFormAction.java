package com.coffeeyo.product.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.product.model.Category;
import com.coffeeyo.product.model.CategoryDao;
import com.coffeeyo.product.model.Product;
import com.coffeeyo.product.model.ProductDao;

public class ProductUpdateFormAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ProductUpdateFormAction execute()");

		request.setCharacterEncoding("UTF-8");

		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		System.out.println("pageNum="+pageNum);
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("userid");
		
		if(id == null) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그아웃 되었습니다..');");
			out.println("location.href='/';");
			out.println("</script>");
			out.close();
			return null;
		}
		
		int ulevel = Integer.parseInt(session.getAttribute("ulevel").toString());
		
		if(ulevel < 10) {			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('수정권한이 없습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		}
		
		CategoryDao cateDao = CategoryDao.getInstance();
		List<Category> cateList = null;
		cateList = cateDao.getAllCategory();
		
		ProductDao dao = ProductDao.getInstance();
		Product po = dao.getProduct(num);
		
		
		if(pageNum==null){
			response.sendRedirect("../admin/productListAction.yo");
			return null;
		}
		else {
			request.setAttribute("po", po);
			request.setAttribute("cateList", cateList);
			request.setAttribute("num", num);
			request.setAttribute("pageNum", pageNum);

			return "../view/admin/product/productUpdateAdmin.jsp";
		}
	}
}
