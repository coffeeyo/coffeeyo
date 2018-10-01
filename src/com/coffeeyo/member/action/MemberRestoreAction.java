package com.coffeeyo.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.member.model.MemberDAO;

public class MemberRestoreAction implements Action {
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 세션이 가지고있는 로그인한 ID 정보를 가져온다
		HttpSession session = request.getSession();
		String id = request.getParameter("userid");
		
		MemberDAO dao = MemberDAO.getInstance();
		int check = dao.restoreMember(id);
		
		if(check == 1){
			session.setAttribute("msg", "3");
			
			response.sendRedirect("/admin/resultFormAction.yo");
			return null;
		}
		else{
			System.out.println("회원 복원 실패");
			return null;
		}
	}
}
