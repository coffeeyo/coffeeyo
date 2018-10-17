package com.coffeeyo.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.member.model.Member;
import com.coffeeyo.member.model.MemberDAO;

public class JoinProcAction implements Action {
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8"); // 인코딩
		
		MemberDAO dao = MemberDAO.getInstance();
		
		// 입력된 정보를 자바빈에 세팅한다.
		Member member = new Member();
		member.setUserid(request.getParameter("userid"));
		member.setPasswd(request.getParameter("passwd"));
		member.setUname(request.getParameter("uname"));
		member.setGender(Integer.parseInt(request.getParameter("gender")));
		member.setHp(request.getParameter("hp"));
		member.setNick(request.getParameter("nick"));
		
		// 회원가입 실행
		dao.insertMember(member);
		
		Member info = dao.getMember(member.getUserid());
		//로그인 성공 -> 세션에 아이디를 저장
		HttpSession session=request.getSession();
   		session.setAttribute("userid", member.getUserid());
   		session.setAttribute("nick", info.getNick());
   		session.setAttribute("ulevel", info.getUlevel());
   		session.setAttribute("strLevel", info.getStrLevel());
				
   		// 가입성공 메시지를 세션에 담는다.
   		request.getSession().setAttribute("msg", "1");
   		
		//return "/view/member/resultFormAction.me";
   		response.sendRedirect("/member/resultFormAction.yo");
   		return "none";
	}
}
