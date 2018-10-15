package bcom.coffeeyo.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;

import bcom.coffeeyo.board.model.BoardDAO;

public class CommentUpdate implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//�Ķ���͹ޱ�
		String reNo=request.getParameter("reNo");
		int bcidx=Integer.parseInt(reNo);
		String oriNo=request.getParameter("oriNo");
		int bidx=Integer.parseInt(oriNo);
		String nowPage=request.getParameter("nowPage");
		String comm=request.getParameter("comm");
		HttpSession session=request.getSession();
		String userid=(String)session.getAttribute("userid");

		//�����Ͻ�����
		BoardDAO dao=new BoardDAO();
		dao.updateComment(bcidx, bidx,comm);
		dao.close();
		
		//��
		request.setAttribute("userid", userid);
		request.setAttribute("oriNo", bidx);
		request.setAttribute("nowPage", nowPage);
		
		//��
		return "../view/board/toBoardDetail.jsp";
	}

}
