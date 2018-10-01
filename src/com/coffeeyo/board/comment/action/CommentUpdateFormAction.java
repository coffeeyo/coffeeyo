package com.coffeeyo.board.comment.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffeeyo.board.comment.model.Comment;
import com.coffeeyo.board.comment.model.CommentDAO;
import com.coffeeyo.common.action.Action;

public class CommentUpdateFormAction implements Action
{
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 수정할 댓글의 글번호를 가져온다.
		int comment_num = Integer.parseInt(request.getParameter("num"));

		CommentDAO dao = CommentDAO.getInstance();
		Comment comment = dao.getComment(comment_num);
		
		// 댓글 정보를 request에 세팅한다.
		request.setAttribute("comment", comment);
		
		return "../view/popup/commentUpdateForm.jsp";
	}
}
