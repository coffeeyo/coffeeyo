package bcom.coffeeyo.board.action;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffeeyo.common.action.Action;

import bcom.coffeeyo.board.model.BoardDAO;
import bcom.coffeeyo.board.model.BoardVO;

public class BoardDetail implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//할일
		//파라미터 받기
		String strNo=request.getParameter("oriNo");
		int oriNo=Integer.parseInt(strNo);
		String nowPage=request.getParameter("nowPage");
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String sql="";
		
		//상세보기를 하기 위해 해당 글정보를 검색
		BoardDAO dao=new BoardDAO();
		BoardVO vo=dao.selectDetail(oriNo);
		
		//댓글정보 검색하기 
		ArrayList list=dao.selectComment(oriNo);
		dao.close();
		
		//모델에 넘기기
		request.setAttribute("oriNo", oriNo);
		request.setAttribute("nowPage", nowPage);
		request.setAttribute("DATA", vo);
		request.setAttribute("COMM", list);
		return "../view/board/boardDetail.jsp";
	}
}
