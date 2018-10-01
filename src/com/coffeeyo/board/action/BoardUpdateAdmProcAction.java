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

public class BoardUpdateAdmProcAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardUpdateAction execute()");

		//request.setCharacterEncoding("UTF-8");

		String pageNum = (String)request.getParameter("pageNum");
		
		// ���ε� ���� ������
		int fileSize= 5*1024*1024;
		// ���ε�� ���� ������
		String uploadPath = null;
		
		if(pageNum==null){
			response.sendRedirect("../admin/boardListAction.yo");
			return null;
		}
		
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

		BoardDAO bdao = BoardDAO.getInstance();
		
		
		// ���Ͼ��ε� 
		uploadPath = request.getServletContext().getRealPath("/view/upload/board");
		
		MultipartRequest multi = new MultipartRequest
				(request, uploadPath, fileSize, "UTF-8", new DefaultFileRenamePolicy());

		// �����̸� ��������
		String fileName = "";
		Enumeration<String> names = multi.getFileNames();
		if(names.hasMoreElements())
		{
			String name = names.nextElement();
			fileName = multi.getFilesystemName(name);
		}
		
		Board bo = new Board();
		bo.setSubject(multi.getParameter("subject"));
		bo.setComm(multi.getParameter("content"));
		
		bo.setImage(fileName);
		bo.setNotiyn(multi.getParameter("notiyn"));
		
		bo.setBidx(Integer.parseInt(multi.getParameter("num")));
		
		
		boolean check = bdao.updateBoardManage(bo);
		
		if (check == true) {;
			response.sendRedirect("../admin/boardListAction.yo");
			return null;
		} else if (check == false) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('��������');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;

		} else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('��������');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		}
	}
}
