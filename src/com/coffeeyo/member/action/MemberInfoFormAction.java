package com.coffeeyo.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.member.model.Member;
import com.coffeeyo.member.model.MemberDAO;

public class MemberInfoFormAction implements Action {
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// ������ �������ִ� �α����� ID ������ �����´�
		HttpSession session = request.getSession();
		String id = session.getAttribute("userid").toString();
		
		// �� ���̵� �ش��ϴ� ȸ�������� �����´�.
		MemberDAO dao = MemberDAO.getInstance();
		Member member = dao.getMember(id);
		
		request.setAttribute("memberInfo", member);
				
		return "/view/member/memberInfoForm.jsp";
	}
}
