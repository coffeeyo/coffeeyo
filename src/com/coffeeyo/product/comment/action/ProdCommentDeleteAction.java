package com.coffeeyo.product.comment.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.product.comment.model.ProductCommDao;

public class ProdCommentDeleteAction implements Action
{
	@Override
	public String execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
	
		int comment_num = Integer.parseInt(request.getParameter("comment_num"));
		response.setContentType("application/json; charset=UTF-8");
		JSONObject jsonobj = new JSONObject();
		
		ProductCommDao dao = ProductCommDao.getInstance();
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
		
		return "none";
	}
}
