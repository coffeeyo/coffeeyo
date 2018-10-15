package bcom.coffeeyo.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.product.model.Category;
import com.coffeeyo.product.model.CategoryDao;

import bcom.coffeeyo.board.model.BoardDAO;

public class BoardDelete implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//파라미터
		String strNo=request.getParameter("oriNo");
		int oriNo=Integer.parseInt(strNo);
		String nowPage=request.getParameter("nowPage");
		
		//비지니스 로직
		BoardDAO dao=new BoardDAO();
		dao.deleteBoard(oriNo);
		dao.close();
		
		CategoryDao cateDao = CategoryDao.getInstance();
		List<Category> cateList = null;
		cateList = cateDao.getAllCategory();

		request.setAttribute("cateList", cateList);
		
		//모델에 넘기기
		request.setAttribute("oriNo", oriNo);
		request.setAttribute("nowPage", nowPage);
		
		//뷰
		return "../view/board/boardDelete.jsp";
	}

}
