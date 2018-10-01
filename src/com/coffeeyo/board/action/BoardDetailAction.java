package com.coffeeyo.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.board.common.BoardCommon;

public class BoardDetailAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardDetailAction execute()");
		request.setCharacterEncoding("UTF-8");
				
		request = BoardCommon.boardDetail(request);

		return "../view/board/detail.jsp";
	}

}
