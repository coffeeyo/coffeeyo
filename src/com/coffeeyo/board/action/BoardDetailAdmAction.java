package com.coffeeyo.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffeeyo.board.common.BoardCommon;
import com.coffeeyo.common.action.Action;

public class BoardDetailAdmAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardDetailAdmAction execute()");
		request.setCharacterEncoding("UTF-8");
		
		request = BoardCommon.boardDetail(request);

		return "../view/admin/board/boardDetail.jsp";
	}
}
