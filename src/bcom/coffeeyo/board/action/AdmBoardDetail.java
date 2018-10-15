package bcom.coffeeyo.board.action;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;

import bcom.coffeeyo.board.model.BoardDAO;
import bcom.coffeeyo.board.model.BoardVO;

public class AdmBoardDetail implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//����
		//�Ķ���� �ޱ�
		String strNo=request.getParameter("oriNo");
		int oriNo=Integer.parseInt(strNo);
		String nowPage=request.getParameter("nowPage");
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String sql="";
		
		//�����Ͻ� ����
		
		//���ǿ� �̿��ڰ� �� �Խù��� �۹�ȣ�� �����ؼ� ����ϱ�
		boolean isHit=false;
		//��ȸ�� �������θ� �Ǵ��ϴ� ����
		HttpSession session=request.getSession();
		ArrayList hitList=(ArrayList)session.getAttribute("SHOW");
		//���ǿ� �̹� �� �Խù� ��ȣ�� Ȯ���ؼ� 
		//�����ϸ� ��ȸ�� ������ ���� �ʰ�
		//�������� ������ ��ȸ�� ���� ó���ϱ�
		if(hitList==null || hitList.size()==0) {
			//� �۵� ���� ���� ��� -> ������ 1���� ����Ʈ ������ ���Ǳ�����ֱ�
			isHit=true;
			hitList=new ArrayList();
			hitList.add(oriNo);
			session.setAttribute("SHOW", hitList);
		}
		else if(hitList.contains(oriNo)){
			//�� ���� ������ �ִ� ����� ��� -> ��ȸ�� ����X
			isHit=false;
		}
		else {
			//�ٸ� ���� ������ �� ���� ���� ���� ��� -> ��ȸ�� ����
			isHit=true;
			hitList.add(oriNo);
			//������ �߰� �����ϱ� ���� ���� �����������
			session.setAttribute("SHOW", hitList);
		}
		//��ȸ�� �����ϱ�
		BoardDAO dao=new BoardDAO();
		if(isHit) {
			dao.updateBoardReadCnt(oriNo);					
		}
		//�󼼺��⸦ �ϱ� ���� �ش� �������� �˻�
		BoardVO vo=dao.selectDetail(oriNo);
		//������� �˻��ϱ� 
		ArrayList list=dao.selectComment(oriNo);
		
		dao.close();
				
		//�𵨿� �ѱ��
		request.setAttribute("oriNo", oriNo);
		request.setAttribute("nowPage", nowPage);
		request.setAttribute("DATA", vo);
		request.setAttribute("COMM", list);
		return "../view/board/boardAdmDetail.jsp";
	}
}
