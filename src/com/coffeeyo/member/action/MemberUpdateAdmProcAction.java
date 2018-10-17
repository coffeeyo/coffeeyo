package com.coffeeyo.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.member.model.Member;
import com.coffeeyo.member.model.MemberDAO;

public class MemberUpdateAdmProcAction implements Action {
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8"); // ���ڵ�
		
		MemberDAO dao = MemberDAO.getInstance();
		
		// ������ �������ִ� �α����� ID ������ �����´�
		HttpSession session = request.getSession();
		String id = request.getParameter("userid");
		String nowPage = request.getParameter("nowPage");
		String strGender = request.getParameter("gender");
		if(strGender == null) {
			strGender = "0";
		}
		
		// ������ ������ �ڹٺ� �����Ѵ�.
		Member member = new Member();
		member.setUserid(id);
		member.setPasswd(request.getParameter("passwd"));
		//member.setNick(request.getParameter("nick"));
		member.setHp(request.getParameter("hp"));
		member.setGender(Integer.parseInt(strGender));
		member.setBirthday(request.getParameter("birthday"));
		member.setJob(Integer.parseInt(request.getParameter("job")));
		member.setUlevel(Integer.parseInt(request.getParameter("ulevel")));
		member.setStatus(Integer.parseInt(request.getParameter("status")));
				
		dao.updateMemberProc(member);		
		
   		// ȸ������ ���� ���� �޽����� ���ǿ� ��´�.   		
		response.sendRedirect("/admin/resultFormAction.yo?userid="+id+"&msg=0&nowPage="+nowPage);
		
		return "none";
	}
}
