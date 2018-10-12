package bcom.coffeeyo.board.action;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffeeyo.common.action.Action;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import bcom.coffeeyo.board.model.BoardDAO;
import bcom.coffeeyo.board.model.BoardVO;

public class UpdateProc implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("update proc입니다");
		
		// 업로드 파일 사이즈
		int fileSize= 5*1024*1024;
		// 업로드될 폴더 절대경로
		String uploadPath = null;
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
		
		//파라미터받기
		String cname=multi.getParameter("cname");
		int cidx=Integer.parseInt(cname);
		String pname=multi.getParameter("pname");
		int pidx=Integer.parseInt(pname);
		String strNo=multi.getParameter("oriNo");
		int oriNo=Integer.parseInt(strNo);
		String nowPage=multi.getParameter("nowPage");
		String subject=multi.getParameter("subject");
		String comm=multi.getParameter("comm");

		//비지니스 로직 수행
		BoardDAO dao=new BoardDAO();
		BoardVO vo=new BoardVO();
		vo.setCidx(cidx);
		vo.setPidx(pidx);
		vo.setSubject(subject);
		vo.setComm(comm);
		vo.setImage(fileName);
		
		if(fileName!=null) {
			//파일선택할때
			System.out.println("파일선택");
			dao.updateBoard(1, oriNo, vo);			
		}else{
			System.out.println("파일선택X");
			//파일선택안할때
			dao.updateBoard(2, oriNo, vo);
		}
		dao.close();
		
		//모델
		request.setAttribute("oriNo", oriNo);
		request.setAttribute("nowPage", nowPage);
		//뷰
		return "../view/board/boardUpdateProc.jsp";
	}

}
