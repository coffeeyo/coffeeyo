package bcom.coffeeyo.board.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffeeyo.common.action.Action;

import bcom.coffeeyo.board.model.BoardDAO;
import bcom.coffeeyo.board.model.BoardVO;

public class AdmUpdateForm implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�Ķ���� �ޱ�
		String strNo=request.getParameter("oriNo");
		int oriNo=Integer.parseInt(strNo);
		String nowPage=request.getParameter("nowPage");
		
		//���������ϱ�
		//���۹�ȣ �Խñ� ��������
		BoardDAO dao=new BoardDAO();
		BoardVO vo=dao.selectDetail(oriNo);

		
		//�з�����, ��ǰ���� ��������
		ArrayList clist=dao.selectCategory();
		ArrayList plist1=dao.selectColdbrew();
		ArrayList plist2=dao.selectEspresso();
		ArrayList plist3=dao.selectFrappuccino();
		
		//��
		request.setAttribute("DATA", vo);
		request.setAttribute("CLIST", clist);
		request.setAttribute("PLIST1", plist1);
		request.setAttribute("PLIST2", plist2);
		request.setAttribute("PLIST3", plist3);
		request.setAttribute("oriNo", oriNo);
		request.setAttribute("nowPage", nowPage);
		
		//��
		return "../view/board/boardAdmUpdateForm.jsp";
	}
}
