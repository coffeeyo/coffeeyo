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
		
		
		BoardDAO dao = BoardDAO.getInstance();
		// 삭제할 글에 있는 파일 정보를 가져온다.
		String fileName = dao.getFileName(boardNum);
		// 글 삭제 - 답글이 있을경우 답글도 모두 삭제한다.
		boolean result = dao.deleteBoard(boardNum);
		
		// 파일삭제 
		if(fileName != null)
		{
			// 파일이 있는 폴더의 절대경로를 가져온다.
			String folder = request.getServletContext().getRealPath("/view/upload/board");
			
			// 파일의 절대경로를 만든다.
			String filePath = folder + "/" + fileName;

			File file = new File(filePath);
			if(file.exists()) file.delete(); // 파일은 1개만 업로드 되므로 한번만 삭제하면 된다.
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
