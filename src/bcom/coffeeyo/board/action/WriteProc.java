package bcom.coffeeyo.board.action;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import bcom.coffeeyo.board.model.BoardDAO;
import bcom.coffeeyo.board.model.BoardVO;

public class WriteProc implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("자바파일로 넘어옴");
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
		
		//파라미터 받기
		String cname=multi.getParameter("cname");
		int cidx=Integer.parseInt(cname);
		String pname=multi.getParameter("pname");
		int pidx=Integer.parseInt(pname);
		String subject=multi.getParameter("subject");
		String comm=multi.getParameter("comm");
		String notiyn="N";
		HttpSession session=request.getSession();
		String userid=(String)session.getAttribute("userid");
		
		
		//비지니스 로직 수행
		BoardDAO dao=new BoardDAO();
		int bidx=dao.getSeq();
		BoardVO vo=new BoardVO();
		vo.setBidx(bidx);
		vo.setUserid(userid);
		vo.setCidx(cidx);
		vo.setPidx(pidx);
		vo.setSubject(subject);
		vo.setComm(comm);
		vo.setImage(fileName);
		vo.setNotiyn(notiyn);
	
		dao.insertBoard(vo);
		dao.close();

		System.out.println("bidx="+bidx);
		System.out.println("cidx="+cidx+"pidx="+pidx);
		System.out.println(fileName+','+notiyn);
		System.out.println(subject+','+comm+','+userid);
		//뷰
		return "../view/board/boardWriteProc.jsp";
	}

}
