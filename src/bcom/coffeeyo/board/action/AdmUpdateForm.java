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
		
		//파라미터 받기
		String strNo=request.getParameter("oriNo");
		int oriNo=Integer.parseInt(strNo);
		String nowPage=request.getParameter("nowPage");
		
		//로직수행하기
		//원글번호 게시글 가져오기
		BoardDAO dao=new BoardDAO();
		BoardVO vo=dao.selectDetail(oriNo);

		
		//분류정보, 상품정보 가져오기
		ArrayList clist=dao.selectCategory();
		ArrayList plist1=dao.selectColdbrew();
		ArrayList plist2=dao.selectEspresso();
		ArrayList plist3=dao.selectFrappuccino();
		
		//모델
		request.setAttribute("DATA", vo);
		request.setAttribute("CLIST", clist);
		request.setAttribute("PLIST1", plist1);
		request.setAttribute("PLIST2", plist2);
		request.setAttribute("PLIST3", plist3);
		request.setAttribute("oriNo", oriNo);
		request.setAttribute("nowPage", nowPage);
		
		//뷰
		return "../view/board/boardAdmUpdateForm.jsp";
	}
}
