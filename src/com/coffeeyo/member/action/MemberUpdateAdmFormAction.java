package com.coffeeyo.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.member.model.Member;
import com.coffeeyo.member.model.MemberDAO;

public class MemberUpdateAdmFormAction implements Action {
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// ������ �������ִ� �α����� ID ������ �����´�
		HttpSession session = request.getSession();
		String ulevel = session.getAttribute("ulevel").toString();
		String userid = request.getParameter("userid");
		String nowPage = request.getParameter("nowPage");
		
		if(!ulevel.equals("10")) {
			response.sendRedirect("/admin/memberListAction.yo?nowPage="+nowPage);
			return "none";
		}
		
		// ������ ȸ�������� �����´�.
		MemberDAO dao = MemberDAO.getInstance();
		Member member = dao.getMember(userid);
		
		// ModifyFrom.jsp�� ȸ�������� �����ϱ� ���� request�� MemberBean�� �����Ѵ�.
		request.setAttribute("memberInfo", member);
		request.setAttribute("nowPage", nowPage);
				
		return "/view/admin/member/memberUpdateForm.jsp";
	}
}
