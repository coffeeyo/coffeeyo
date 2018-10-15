package com.coffeeyo.product.action;

import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.product.model.Product;
import com.coffeeyo.product.model.ProductDao;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class ProductUpdateProcAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ProductUpdateProcAction execute()");

		//request.setCharacterEncoding("UTF-8");
		
		// 업로드 파일 사이즈
		int fileSize= 5*1024*1024;
		// 업로드될 폴더 절대경로
		String uploadPath = null;
				
		// 파일업로드 
		uploadPath = request.getServletContext().getRealPath("/view/upload/product");
		
		MultipartRequest multi = new MultipartRequest
				(request, uploadPath, fileSize, "UTF-8", new DefaultFileRenamePolicy());

		String pageNum = (String)multi.getParameter("pageNum");
		System.out.println("pageNum= "+pageNum);		
			
		if(pageNum==null){
			response.sendRedirect("../admin/productListAction.yo");
			return null;
		}
	
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

		ProductDao dao = ProductDao.getInstance();

		// 파일이름 가져오기
		String fileName = "";
		Enumeration<String> names = multi.getFileNames();
		if(names.hasMoreElements())
		{
			String name = names.nextElement();
			fileName = multi.getFilesystemName(name);
		}
		
		Product po = new Product();
		po.setPidx(Integer.parseInt(multi.getParameter("num")));
		System.out.println("num="+po.getPidx());
		po.setCidx(Integer.parseInt(multi.getParameter("cidx")));
		
		//po.setUserid(id);
		po.setPname(multi.getParameter("pname"));
		po.setComm(multi.getParameter("comm"));
		po.setImage(fileName);
		po.setPrice(Integer.parseInt(multi.getParameter("price")));
		po.setMaketm(Integer.parseInt(multi.getParameter("maketm")));
		po.setRecomm(Integer.parseInt(multi.getParameter("recomm")));
		po.setStatus(Integer.parseInt(multi.getParameter("status")));
				
		boolean check = dao.updateProduct(po);
		
		if (check == true) {
			response.sendRedirect("../admin/productListAction.yo?pageNum="+pageNum);
			return null;

		} else if (check == false) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('수정실패');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;

		} else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('수정실패');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		}
	}
	
}
