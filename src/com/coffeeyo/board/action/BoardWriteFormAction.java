package com.coffeeyo.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.product.model.Category;
import com.coffeeyo.product.model.CategoryDao;

public class BoardWriteFormAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardWriteFormAction execute()");
		request.setCharacterEncoding("UTF-8");
				
		CategoryDao cateDao = CategoryDao.getInstance();
		List<Category> cateList = null;
		cateList = cateDao.getAllCategory();
		
		request.setAttribute("cateList", cateList);

		return "/view/board/writeForm.jsp";
	}
}
