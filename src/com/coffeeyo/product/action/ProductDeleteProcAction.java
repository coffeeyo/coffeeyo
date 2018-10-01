package com.coffeeyo.product.action;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.product.model.ProductDao;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class ProductDeleteProcAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardDeleteProcAction execute()");	
		
		// 업로드 파일 사이즈
		int fileSize= 5*1024*1024;
		// 업로드될 폴더 절대경로
		String uploadPath = null;
				
		// 파일업로드 
		uploadPath = request.getServletContext().getRealPath("/view/upload/product");
		
		MultipartRequest multi = new MultipartRequest
				(request, uploadPath, fileSize, "UTF-8", new DefaultFileRenamePolicy());
		
		String num = multi.getParameter("num");
		int prodNum = Integer.parseInt(num);
		
		System.out.println("num : "+num);
		
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
		
		int useCnt = dao.getProdUseCnt(prodNum);

		if(useCnt > 0) {			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('주문건이 존재하여 삭제할 수 없습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		}
		
		
		// 삭제할 글에 있는 파일 정보를 가져온다.
		String fileName = dao.getFileName(prodNum);
		// 글 삭제 - 답글이 있을경우 답글도 모두 삭제한다.
		boolean result = dao.deleteProduct(prodNum);
		
		
		// 파일삭제 
		if(fileName != null)
		{			
			// 파일의 절대경로를 만든다.
			String filePath = uploadPath + "/" + fileName;

			File file = new File(filePath);
			if(file.exists()) file.delete(); // 파일은 1개만 업로드 되므로 한번만 삭제하면 된다.
		}
		
		if(result){
			response.sendRedirect("../admin/productListAction.yo");
			return null;
		}
		else {
			return null;
		}
	}	
}
