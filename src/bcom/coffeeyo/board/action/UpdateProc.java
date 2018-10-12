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
		System.out.println("update proc�Դϴ�");
		
		// ���ε� ���� ������
		int fileSize= 5*1024*1024;
		// ���ε�� ���� ������
		String uploadPath = null;
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
		
		//�Ķ���͹ޱ�
		String cname=multi.getParameter("cname");
		int cidx=Integer.parseInt(cname);
		String pname=multi.getParameter("pname");
		int pidx=Integer.parseInt(pname);
		String strNo=multi.getParameter("oriNo");
		int oriNo=Integer.parseInt(strNo);
		String nowPage=multi.getParameter("nowPage");
		String subject=multi.getParameter("subject");
		String comm=multi.getParameter("comm");

		//�����Ͻ� ���� ����
		BoardDAO dao=new BoardDAO();
		BoardVO vo=new BoardVO();
		vo.setCidx(cidx);
		vo.setPidx(pidx);
		vo.setSubject(subject);
		vo.setComm(comm);
		vo.setImage(fileName);
		
		if(fileName!=null) {
			//���ϼ����Ҷ�
			System.out.println("���ϼ���");
			dao.updateBoard(1, oriNo, vo);			
		}else{
			System.out.println("���ϼ���X");
			//���ϼ��þ��Ҷ�
			dao.updateBoard(2, oriNo, vo);
		}
		dao.close();
		
		//��
		request.setAttribute("oriNo", oriNo);
		request.setAttribute("nowPage", nowPage);
		//��
		return "../view/board/boardUpdateProc.jsp";
	}

}
