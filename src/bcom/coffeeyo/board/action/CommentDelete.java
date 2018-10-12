package bcom.coffeeyo.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffeeyo.common.action.Action;

public class CommentDelete implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//파라미터받기
		
		//비지니스로직수행
		
		//모델
		
		//뷰
		return "../board/boardBoardDetail.yo";
	}

}
