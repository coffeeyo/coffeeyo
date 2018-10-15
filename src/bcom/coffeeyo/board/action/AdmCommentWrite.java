package bcom.coffeeyo.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;

import bcom.coffeeyo.board.model.BoardDAO;

public class AdmCommentWrite implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//파라미터 받기
		String strNo=request.getParameter("oriNo");
		int oriNo=Integer.parseInt(strNo);
		String nowPage=request.getParameter("nowPage");
		String comm=request.getParameter("comm");
		HttpSession session=request.getSession();
		String userid=(String)session.getAttribute("userid");
		
		//비지니스 로직 수행하기
		BoardDAO dao=new BoardDAO();
		int bcidx=dao.getCSeq();
		dao.insertComment(bcidx, oriNo, userid, comm);
		dao.close();
		
		//모델
		request.setAttribute("userid", userid);
		request.setAttribute("oriNo", oriNo);
		request.setAttribute("nowPage", nowPage);
		//뷰
		return "../view/board/toBoardAdmDetail.jsp";
	}

}

