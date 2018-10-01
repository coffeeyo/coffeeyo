package com.coffeeyo.board.action;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.board.model.BoardDAO;
import com.coffeeyo.common.action.Action;

public class BoardDeleteAdmProcAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardDeleteProcAction execute()");		
		
		String num = request.getParameter("num");
		int boardNum = Integer.parseInt(num);
		
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
			out.println("alert('���������� �����ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		}
		
		
		BoardDAO dao = BoardDAO.getInstance();
		// ������ �ۿ� �ִ� ���� ������ �����´�.
		String fileName = dao.getFileName(boardNum);
		// �� ���� - ����� ������� ��۵� ��� �����Ѵ�.
		boolean result = dao.deleteBoard(boardNum);
		
		// ���ϻ��� 
		if(fileName != null)
		{
			// ������ �ִ� ������ �����θ� �����´�.
			String folder = request.getServletContext().getRealPath("/view/upload/board");
			
			// ������ �����θ� �����.
			String filePath = folder + "/" + fileName;

			File file = new File(filePath);
			if(file.exists()) file.delete(); // ������ 1���� ���ε� �ǹǷ� �ѹ��� �����ϸ� �ȴ�.
		}
		
		if(result){
			response.sendRedirect("../admin/boardListAction.yo");
			return null;
		}
		else {
			return null;
		}
	}	
}
