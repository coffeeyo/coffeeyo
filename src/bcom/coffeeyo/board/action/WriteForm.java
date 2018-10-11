package bcom.coffeeyo.board.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;

import bcom.coffeeyo.board.model.BoardDAO;

public class WriteForm implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		System.out.println("WriteForm컨트롤러");
		//할일
		//세션을 이용해서 로그인되어있는지 확인하고
		HttpSession session=req.getSession();
		
		String uid=(String)session.getAttribute("userid");
		String unick=(String)session.getAttribute("nick");
		//로그인 하지 않았다면 로그인폼 보여주기
		if(uid==null || uid.length()==0) {
			//return "../Member/LoginForm.jsp";
			try {
				resp.sendRedirect("../member/loginFormAction.yo");
			} catch (Exception e) {
				System.out.println("세션에 아이디가 없어서 로그인폼으로 이동하는 중 문제 발생="+e);
			}
			return null;
		}
		//로그인 했을 때 글쓰기폼 보여주기
		else {
			//로직수행
			BoardDAO dao=new BoardDAO();
			ArrayList clist=dao.selectCategory();
			ArrayList plist1=dao.selectColdbrew();
			ArrayList plist2=dao.selectEspresso();
			ArrayList plist3=dao.selectFrappuccino();
			//모델
			req.setAttribute("CLIST", clist);
			req.setAttribute("PLIST1", plist1);
			req.setAttribute("PLIST2", plist2);
			req.setAttribute("PLIST3", plist3);
			//뷰
			dao.close();
			return "../view/board/boardWriteForm.jsp";			
		}
	}

}
