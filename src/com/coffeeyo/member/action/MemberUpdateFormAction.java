package com.coffeeyo.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.member.model.Member;
import com.coffeeyo.member.model.MemberDAO;

public class MemberUpdateFormAction implements Action {
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 세션이 가지고있는 로그인한 ID 정보를 가져온다
		HttpSession session = request.getSession();
		String id = session.getAttribute("userid").toString();
		
		// 수정할 회원정보를 가져온다.
		MemberDAO dao = MemberDAO.getInstance();
		Member member = dao.getMember(id);
		
		// ModifyFrom.jsp에 회원정보를 전달하기 위해 request에 MemberBean을 세팅한다.
		request.setAttribute("memberInfo", member);
				
		return "/view/member/memberUpdateForm.jsp";
	}
}
