package com.coffeeyo.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffeeyo.common.action.Action;

public class BoardWriteAdmFrmAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardWriteFormAction execute()");
		request.setCharacterEncoding("UTF-8");

		return "../view/admin/board/boardWrite.jsp";
	}
}
