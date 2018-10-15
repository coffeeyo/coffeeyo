package bcom.coffeeyo.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffeeyo.common.action.Action;

import bcom.coffeeyo.board.model.BoardDAO;

public class AdmBoardDelete implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�Ķ����
		String strNo=request.getParameter("oriNo");
		int oriNo=Integer.parseInt(strNo);
		String nowPage=request.getParameter("nowPage");
		
		//�����Ͻ� ����
		BoardDAO dao=new BoardDAO();
		dao.deleteBoard(oriNo);
		dao.close();
		
		//�𵨿� �ѱ��
		request.setAttribute("oriNo", oriNo);
		request.setAttribute("nowPage", nowPage);
		
		//��
		return "../view/board/boardAdmDelete.jsp";
	}

}
