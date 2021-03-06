package com.coffeeyo.product.comment.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.product.comment.model.ProductComm;
import com.coffeeyo.product.comment.model.ProductCommDao;

public class ProdCommentUpdateAction implements Action
{
	@Override
	public String execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		response.setContentType("application/json; charset=UTF-8");
		JSONObject jsonobj = new JSONObject();
		
		// 파라미터를 가져온다.
		int comment_num = Integer.parseInt(request.getParameter("comment_num"));
		String comment_content = request.getParameter("comment_content");
		
		ProductCommDao dao = ProductCommDao.getInstance();
		
		ProductComm comment = new ProductComm();
		comment.setPcidx(comment_num);
		comment.setComm(comment_content);
		
		boolean result = dao.updateComment(comment);
		
		PrintWriter out = response.getWriter();
		
		// 정상적으로 댓글을 수정했을경우 1을 전달한다.
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
