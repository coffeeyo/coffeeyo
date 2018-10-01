package com.coffeeyo.board.comment.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.coffeeyo.board.comment.model.CommentDAO;
import com.coffeeyo.common.action.Action;

public class CommentDeleteAction implements Action
{
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		int comment_num = Integer.parseInt(request.getParameter("comment_num"));
		response.setContentType("application/json; charset=UTF-8");
		JSONObject jsonobj = new JSONObject();
		
		CommentDAO dao = CommentDAO.getInstance();
		boolean result = dao.deleteComment(comment_num);
		
		PrintWriter out = response.getWriter();

		// 정상적으로 댓글을 삭제했을경우 1을 전달한다.
		if(result){
			jsonobj.put("check", 1);
		}
		else {
			jsonobj.put("check", 0);
		}
		
		out.print(jsonobj.toString());
		out.flush();
		out.close();
		
		return null;
	}
}
