package com.coffeeyo.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.member.model.MemberDAO;

public class MemberLeaveAction implements Action {
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 세션이 가지고있는 로그인한 ID 정보를 가져온다
		HttpSession session = request.getSession();
		String id = session.getAttribute("userid").toString();
		
		MemberDAO dao = MemberDAO.getInstance();
		int check = dao.leaveMember(id);
		
		if(check == 1){
			// 세션의 회원정보 삭제
			session.invalidate();
			
			return "/member/resultFormAction.me";
		}
		else{
			System.out.println("회원 탈퇴 실패");
			return "none";
		}
	}
}
