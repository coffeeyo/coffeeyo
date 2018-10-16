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
		
		// ������ ����� �۹�ȣ�� �����´�.
		int comment_num = Integer.parseInt(request.getParameter("num"));

		ProductCommDao dao = ProductCommDao.getInstance();
		ProductComm comment = dao.getComment(comment_num);
		
		// ��� ������ request�� �����Ѵ�.
		request.setAttribute("comment", comment);
		
		return "../view/popup/prodCommentUpdateForm.jsp";
	}
}
