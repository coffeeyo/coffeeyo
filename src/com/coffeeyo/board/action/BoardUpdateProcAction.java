package com.coffeeyo.board.action;

import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.board.model.Board;
import com.coffeeyo.board.model.BoardDAO;
import com.coffeeyo.common.action.Action;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;


public class BoardUpdateProcAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardUpdateAction execute()");

		//request.setCharacterEncoding("UTF-8");

		String pageNum = (String)request.getParameter("pageNum");
		
		// 업로드 파일 사이즈
		int fileSize= 5*1024*1024;
		// 업로드될 폴더 절대경로
		String uploadPath = null;
		
		if(pageNum==null){
			response.sendRedirect("../board/boardListAction.yo");
			return null;
		}
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("userid");
		
		if(id == null) {
			response.sendRedirect("../board/boardListAction.yo");
			return null;
		}

		BoardDAO bdao = BoardDAO.getInstance();
		
		
		
		
		// 파일업로드 
		uploadPath = request.getServletContext().getRealPath("/view/upload/board");
		
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
		
		String strPidx = multi.getParameter("pidx");
		if(strPidx == null) strPidx = "0";
		
		Board bo = new Board();
		bo.setSubject(multi.getParameter("subject"));
		bo.setComm(multi.getParameter("content"));
		bo.setCidx(Long.parseLong(multi.getParameter("cidx")));
		bo.setPidx(Long.parseLong(strPidx));
		
		bo.setImage(fileName);
		
		bo.setBidx(Integer.parseInt(multi.getParameter("num")));
		
		
		boolean check = bdao.updateBoard(bo);
		
		if (check == true) {
			response.sendRedirect("../board/boardListAction.yo");
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