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
		
		request.setCharacterEncoding("UTF-8"); // 인코딩
		
		MemberDAO dao = MemberDAO.getInstance();
		
		// 세션이 가지고있는 로그인한 ID 정보를 가져온다
		HttpSession session = request.getSession();
		String id = session.getAttribute("userid").toString();
		
		// 수정할 정보를 자바빈에 세팅한다.
		Member member = new Member();
		member.setUserid(id);
		member.setPasswd(request.getParameter("passwd"));
		//member.setNick(request.getParameter("nick"));
		member.setHp(request.getParameter("hp"));
		member.setGender(Integer.parseInt(request.getParameter("gender")));
		member.setBirthday(request.getParameter("birthday"));
		member.setJob(Integer.parseInt(request.getParameter("job")));
		
		dao.updateMember(member);
		
   		// 회원정보 수정 성공 메시지를 세션에 담는다.
   		session.setAttribute("msg", "0");
   		
		response.sendRedirect("/member/resultFormAction.yo");
		return null;
	}
}
