package com.coffeeyo.board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.board.model.Board;
import com.coffeeyo.board.model.BoardDAO;
import com.coffeeyo.common.action.Action;

public class BoardUpdateAdmFrmAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardUpdateFrmAction execute()");

		request.setCharacterEncoding("UTF-8");

		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		
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
		
		BoardDAO bdao = BoardDAO.getInstance();
		Board bo = bdao.getDetail(num);
		
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
		
		if(pageNum==null){
			response.sendRedirect("../admin/boardListAction.yo");
			return null;
		}


		request.setAttribute("bo", bo);
		request.setAttribute("num", num);
		request.setAttribute("pageNum", pageNum);

		return "../view/admin/board/boardUpdate.jsp";
	}
}
