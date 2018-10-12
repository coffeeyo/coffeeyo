package bcom.coffeeyo.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffeeyo.common.action.Action;

import bcom.coffeeyo.board.model.BoardDAO;

public class CommentUpdate implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//�Ķ���͹ޱ�
		System.out.println("comment Update����");
		String reNo=request.getParameter("reNo");
		int bcidx=Integer.parseInt(reNo);
		String oriNo=request.getParameter("oriNo");
		int bidx=Integer.parseInt(oriNo);
		String nowPage=request.getParameter("nowPage");
		String comm=request.getParameter("comm");

		//�����Ͻ�����
		BoardDAO dao=new BoardDAO();
		dao.updateComment(bcidx, bidx,comm);
		dao.close();
		
		//��
		request.setAttribute("oriNo", bidx);
		request.setAttribute("nowPage", nowPage);
		//��
		return "../board/boardBoardDetail.yo";
	}

}