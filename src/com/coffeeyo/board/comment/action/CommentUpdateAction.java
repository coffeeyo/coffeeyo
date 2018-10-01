package com.coffeeyo.board.comment.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.coffeeyo.board.comment.model.Comment;
import com.coffeeyo.board.comment.model.CommentDAO;
import com.coffeeyo.common.action.Action;

public class CommentUpdateAction implements Action
{
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setContentType("application/json; charset=UTF-8");
		JSONObject jsonobj = new JSONObject();
		
		// �Ķ���͸� �����´�.
		int comment_num = Integer.parseInt(request.getParameter("comment_num"));
		String comment_content = request.getParameter("comment_content");
		
		CommentDAO dao = CommentDAO.getInstance();
		
		Comment comment = new Comment();
		comment.setBcidx(comment_num);
		comment.setComm(comment_content);
		
		boolean result = dao.updateComment(comment);
		
		PrintWriter out = response.getWriter();
		
		// ���������� ����� ����������� 1�� �����Ѵ�.
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
