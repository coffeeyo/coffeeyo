package com.coffeeyo.board.action;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.board.model.Board;
import com.coffeeyo.board.model.BoardDAO;
import com.coffeeyo.common.action.Action;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class BoardWriteAdmProcAction implements Action {
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
		
		BoardDAO bdao = BoardDAO.getInstance();
		Board bo = new Board();
		
		int seq = bdao.getSeq();
		
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
		String cidx = multi.getParameter("cidx");
		String pidx = multi.getParameter("pidx");
		
		if(cidx == null) {
			cidx = "0";
		}
		if(pidx == null) {
			pidx = "0";
		}
		
		bo.setBidx(seq);
		bo.setUserid(id);
		bo.setCidx(Integer.parseInt(cidx));
		bo.setPidx(Integer.parseInt(pidx));
		bo.setSubject(multi.getParameter("subject"));
		bo.setComm(multi.getParameter("content"));
		bo.setImage(fileName);
		bo.setNotiyn(multi.getParameter("notiyn"));
		
		boolean result = bdao.boardInsert(bo);
		
		System.out.println("ContentAction execute() result : " + result);

		response.sendRedirect("../admin/boardListAction.yo");
		
		return null;
	}	
}
