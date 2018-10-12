package bcom.coffeeyo.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffeeyo.common.action.Action;

import bcom.coffeeyo.board.model.BoardDAO;

public class CommentUpdate implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//파라미터받기
		System.out.println("comment Update들어옴");
		String reNo=request.getParameter("reNo");
		int bcidx=Integer.parseInt(reNo);
		String oriNo=request.getParameter("oriNo");
		int bidx=Integer.parseInt(oriNo);
		String nowPage=request.getParameter("nowPage");
		String comm=request.getParameter("comm");

		//비지니스로직
		BoardDAO dao=new BoardDAO();
		dao.updateComment(bcidx, bidx,comm);
		dao.close();
		
		//모델
		request.setAttribute("oriNo", bidx);
		request.setAttribute("nowPage", nowPage);
		//뷰
		return "../board/boardBoardDetail.yo";
	}

}
