package com.coffeeyo.board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import com.coffeeyo.board.model.BoardDAO;
import com.coffeeyo.common.action.Action;

public class BoardAddLikeAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardAddLikeAction execute()");

		response.setContentType("application/json; charset=UTF-8");
		JSONObject jsonobj = new JSONObject();
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("userid");
		
		if(id == null) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('로그아웃 되었습니다.');");
			out.println("location.href = '/'; ");
			out.println("</script>");
			out.close();
			return null;
		}

		BoardDAO bdao = BoardDAO.getInstance();
		
		int boardNum = Integer.parseInt(request.getParameter("num"));
		
		boolean result = bdao.updateLikeCount(boardNum);
		
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
		
		return null;
	}

}
