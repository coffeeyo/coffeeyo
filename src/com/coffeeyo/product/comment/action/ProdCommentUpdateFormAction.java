package com.coffeeyo.product.comment.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.product.comment.model.ProductComm;
import com.coffeeyo.product.comment.model.ProductCommDao;

public class ProdCommentUpdateFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("ProdCommentUpdateFormAction.java");
		
		// 수정할 댓글의 글번호를 가져온다.
		int comment_num = Integer.parseInt(request.getParameter("num"));

		ProductCommDao dao = ProductCommDao.getInstance();
		ProductComm comment = dao.getComment(comment_num);
		
		// 댓글 정보를 request에 세팅한다.
		request.setAttribute("comment", comment);
		
		return "../view/popup/prodCommentUpdateForm.jsp";
	}
}
