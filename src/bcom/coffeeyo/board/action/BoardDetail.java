package bcom.coffeeyo.board.action;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;

import bcom.coffeeyo.board.model.BoardDAO;
import bcom.coffeeyo.board.model.BoardVO;
import bcom.coffeeyo.board.sql.BoardSql;

public class BoardDetail implements Action {

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
		
		//�󼼺��⸦ �ϱ� ���� �ش� �������� �˻�
		BoardDAO dao=new BoardDAO();
		BoardVO vo=dao.selectDetail(oriNo);
		dao.close();
		
		//�𵨿� �ѱ��
		request.setAttribute("oriNo", oriNo);
		request.setAttribute("nowPage", nowPage);
		request.setAttribute("DATA", vo);
		return "../view/board/boardDetail.jsp";
	}
}
