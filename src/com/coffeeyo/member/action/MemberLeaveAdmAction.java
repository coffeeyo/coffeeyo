package com.coffeeyo.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.member.model.MemberDAO;

public class MemberLeaveAdmAction implements Action {
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// ������ �������ִ� �α����� ID ������ �����´�
		HttpSession session = request.getSession();
		String id = request.getParameter("userid");
		
		MemberDAO dao = MemberDAO.getInstance();
		int check = dao.leaveMember(id);
		
		if(check == 1){
			session.setAttribute("msg", "2");
			response.sendRedirect("/admin/resultFormAction.yo");
			return "none";
		}
		else{
			System.out.println("ȸ�� Ż�� ����");
			return "none";
		}
	}
}
