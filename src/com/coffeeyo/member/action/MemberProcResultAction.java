package com.coffeeyo.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffeeyo.common.action.Action;

public class MemberProcResultAction implements Action {
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8"); // ÀÎÄÚµù
		String msg = request.getParameter("msg");
		request.setAttribute("msg", msg);
		
		return "/view/member/resultForm.jsp";
	}
}
