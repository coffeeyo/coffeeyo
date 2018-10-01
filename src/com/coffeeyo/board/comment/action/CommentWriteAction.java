package com.coffeeyo.board.comment.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.coffeeyo.board.comment.model.Comment;
import com.coffeeyo.board.comment.model.CommentDAO;
import com.coffeeyo.common.action.Action;

public class CommentWriteAction implements Action
{
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		CommentDAO dao = CommentDAO.getInstance();
		Comment comment = new Comment();
		response.setContentType("application/json; charset=UTF-8");
		JSONObject jsonobj = new JSONObject();
		
		// 파리미터 값을 가져온다.
		int comment_board = Integer.parseInt(request.getParameter("comment_board"));
		String comment_id = request.getParameter("comment_id");
		String comment_content = request.getParameter("comment_content");
		
		comment.setBcidx(dao.getSeq());	// 댓글 번호는 시퀀스값으로
		comment.setBidx(comment_board);
		comment.setUserid(comment_id);
		comment.setComm(comment_content);
		
		boolean result = dao.insertComment(comment);

		PrintWriter out = response.getWriter();
		
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
