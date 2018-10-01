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
		
		// ���ε� ���� ������
		int fileSize= 5*1024*1024;
		// ���ε�� ���� ������
		String uploadPath = null;
				
		// ���Ͼ��ε� 
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
			out.println("alert('�α׾ƿ� �Ǿ����ϴ�..');");
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
			out.println("alert('������ �����ϴ�.');");
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
			out.println("alert('�ֹ����� �����Ͽ� ������ �� �����ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		}
		
		
		// ������ �ۿ� �ִ� ���� ������ �����´�.
		String fileName = dao.getFileName(prodNum);
		// �� ���� - ����� ������� ��۵� ��� �����Ѵ�.
		boolean result = dao.deleteProduct(prodNum);
		
		
		// ���ϻ��� 
		if(fileName != null)
		{			
			// ������ �����θ� �����.
			String filePath = uploadPath + "/" + fileName;

			File file = new File(filePath);
			if(file.exists()) file.delete(); // ������ 1���� ���ε� �ǹǷ� �ѹ��� �����ϸ� �ȴ�.
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
