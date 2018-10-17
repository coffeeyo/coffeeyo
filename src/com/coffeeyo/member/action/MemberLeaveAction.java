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
		
		// ������ �������ִ� �α����� ID ������ �����´�
		HttpSession session = request.getSession();
		String id = session.getAttribute("userid").toString();
		
		MemberDAO dao = MemberDAO.getInstance();
		int check = dao.leaveMember(id);
		
		if(check == 1){
			// ������ ȸ������ ����
			session.invalidate();
			
			return "/member/resultFormAction.me";
		}
		else{
			System.out.println("ȸ�� Ż�� ����");
			return "none";
		}
	}
}
