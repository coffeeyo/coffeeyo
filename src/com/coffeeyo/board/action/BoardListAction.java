package com.coffeeyo.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffeeyo.board.common.BoardCommon;
import com.coffeeyo.common.action.Action;

public class BoardListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("BoardListAction execute()");

		request.setCharacterEncoding("UTF-8");
		
		request = BoardCommon.boardList(request);

		return "../view/board/list.jsp";
	}
}