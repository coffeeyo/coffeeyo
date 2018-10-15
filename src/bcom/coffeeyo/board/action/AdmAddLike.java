package bcom.coffeeyo.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;

import bcom.coffeeyo.board.model.BoardDAO;
import bcom.coffeeyo.board.model.BoardVO;

public class AdmAddLike implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//�Ķ���͹ޱ�
		System.out.println("addlike.java����");
		String strNo=request.getParameter("oriNo");
		int oriNo=Integer.parseInt(strNo);
		String nowPage=request.getParameter("nowPage");
		HttpSession session=request.getSession();
		String userid=(String) session.getAttribute("userid");
		
		//�����Ͻ� ����
		//LikeCheck���̺��� �����ͼ�
		//�ش� �Խù��� �ش����� ���̵�� ���°��� �ִ��� Ȯ���ϱ�
		BoardDAO dao=new BoardDAO();
		BoardVO vo=dao.selectLikeCheck(oriNo, userid);
		String check=vo.getUserid();
		int num=0;
		//���ƿ� üũ �Խù��� �ش����� ���̵� ���� ��
		if(check==null || check.length()==0) {
			//���ƿ�üũ ������ ����
			//�ش����� ���̵�� ���°�(1) �Է�
			int likeidx=dao.getLSeq();
			//���ƿ�üũ ���̺� insert
			dao.insertLikeCheck(likeidx, oriNo, userid);
			//�Խù� ���ƿ� ����ó��
			dao.updateBoardLikeCount(num,oriNo);
		}
		//���ƿ� üũ �Խù��� �ش����� ���̵� ������ �� 
		else {
			//�ش������� likeidx, status �˻�
			int likeidx=vo.getLikeidx();
			int status=vo.getStatus();
			//���°� ������Ʈ ó���ϱ�
			dao.updateLikeCheck(status, likeidx);
			//�Խù� ���ƿ� ����ó��			
			dao.updateBoardLikeCount(status,oriNo);
		}

		dao.close();
		
		//��
		request.setAttribute("oriNo", oriNo);
		request.setAttribute("nowPage", nowPage);
		//��
		return "../view/board/toBoardAdmDetail.jsp";
	}
}
