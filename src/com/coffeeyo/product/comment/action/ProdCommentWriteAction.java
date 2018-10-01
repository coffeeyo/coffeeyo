package com.coffeeyo.product.comment.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.product.comment.model.ProductComm;
import com.coffeeyo.product.comment.model.ProductCommDao;

public class ProdCommentWriteAction implements Action
{
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ProductCommDao dao = ProductCommDao.getInstance();
		ProductComm comment = new ProductComm();
		response.setContentType("application/json; charset=UTF-8");
		JSONObject jsonobj = new JSONObject();
		
		// 파리미터 값을 가져온다.
		int comment_board = Integer.parseInt(request.getParameter("comment_board"));
		String comment_id = request.getParameter("comment_id");
		String comment_content = request.getParameter("comment_content");
		Long comment_point = Long.parseLong(request.getParameter("comment_point"));
		
		comment.setPcidx(dao.getSeq());	// 댓글 번호는 시퀀스값으로
		comment.setPidx(comment_board);
		comment.setUserid(comment_id);
		comment.setComm(comment_content);
		comment.setPcpoint(comment_point);
		
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
