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

public class ProductAddProcAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ContentAction execute()");
		//request.setCharacterEncoding("UTF-8");
		
		// 업로드 파일 사이즈
		int fileSize= 5*1024*1024;
		// 업로드될 폴더 절대경로
		String uploadPath = null;
		
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
			out.println("alert('권한이 없습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		}
		
		ProductDao dao = ProductDao.getInstance();
		Product po = new Product();
		
		int seq = dao.getSeq();
		
		// 파일업로드 
		uploadPath = request.getServletContext().getRealPath("/view/upload/product");
		
		MultipartRequest multi = new MultipartRequest
				(request, uploadPath, fileSize, "UTF-8", new DefaultFileRenamePolicy());

		// 파일이름 가져오기
		String fileName = "";
		Enumeration<String> names = multi.getFileNames();
		if(names.hasMoreElements())
		{
			String name = names.nextElement();
			fileName = multi.getFilesystemName(name);
		}
		String cidx = multi.getParameter("cidx");
		
		if(cidx == null) {
			cidx = "0";
		}
		
		po.setPidx(seq);
		po.setUserid(id);
		po.setCidx(Integer.parseInt(cidx));
		po.setPname(multi.getParameter("pname"));
		po.setComm(multi.getParameter("comm"));
		po.setImage(fileName);
		po.setPrice(Integer.parseInt(multi.getParameter("price")));
		po.setMaketm(Integer.parseInt(multi.getParameter("maketm")));
		po.setRecomm(Integer.parseInt(multi.getParameter("recomm")));
		po.setStatus(Integer.parseInt(multi.getParameter("status")));
		
		boolean result = dao.insertProduct(po);
		
		System.out.println("ContentAction execute() result : " + result);
		
		response.sendRedirect("../admin/productListAction.yo");
		return null;
	}
}
