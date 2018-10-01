package com.coffeeyo.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffeeyo.common.action.Action;

public class LogOutProcAction implements Action {
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		request.getSession().invalidate();
		
		// 로그아웃 후 메인화면으로 돌아간다.
		
		return "/";
	}
}
