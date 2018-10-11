package com.coffeeyo.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffeeyo.common.action.Action;

public class MemberProcResultAdmAction implements Action {
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8"); // ÀÎÄÚµù
		
		String id = request.getParameter("userid");
		String nowPage = request.getParameter("nowPage");
		String msg = request.getParameter("msg");
		
		request.setAttribute("id", id);
		request.setAttribute("nowPage", nowPage);
		request.setAttribute("msg", msg);
		
		return "/view/admin/member/resultForm.jsp";
	}
}
