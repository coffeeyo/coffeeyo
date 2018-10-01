package com.coffeeyo.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.member.model.Member;
import com.coffeeyo.member.model.MemberDAO;

public class MemberUpdateProcAction implements Action {
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8"); // ���ڵ�
		
		MemberDAO dao = MemberDAO.getInstance();
		
		// ������ �������ִ� �α����� ID ������ �����´�
		HttpSession session = request.getSession();
		String id = session.getAttribute("userid").toString();
		
		// ������ ������ �ڹٺ� �����Ѵ�.
		Member member = new Member();
		member.setUserid(id);
		member.setPasswd(request.getParameter("passwd"));
		//member.setNick(request.getParameter("nick"));
		member.setHp(request.getParameter("hp"));
		member.setGender(Integer.parseInt(request.getParameter("gender")));
		member.setBirthday(request.getParameter("birthday"));
		member.setJob(Integer.parseInt(request.getParameter("job")));
		
		dao.updateMember(member);
		
   		// ȸ������ ���� ���� �޽����� ���ǿ� ��´�.
   		session.setAttribute("msg", "0");
   		
		response.sendRedirect("/member/resultFormAction.yo");
		return null;
	}
}
