package com.coffeeyo.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.member.model.Member;
import com.coffeeyo.member.model.MemberDAO;

public class LoginProcAction implements Action {
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HttpSession session=request.getSession();
		
		// 아이디와 비밀번호를 가져온다.
		String id = request.getParameter("id");
		String password = request.getParameter("pass");
		
		// DB에서 아이디, 비밀번호 확인
		MemberDAO dao = MemberDAO.getInstance();
		int check = dao.loginCheck(id, password);
		
		if(check == 0)	// 비밀번호 틀릴경우 -> 다시 로그인 화면으로 이동
		{ 
			// 로그인 실패시 메시지를 request에 담는다.
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('비밀번호가 맞지 않습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		}
		else if(check == 2) // 탈퇴한 아이디인 경우 -> 다시 로그인 화면으로 이동
		{
	   		response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('탈퇴한 아이디 입니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		}
		else if(check == -1) // 아이디가 없을 경우 -> 다시 로그인 화면으로 이동
		{
	   		response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('가입되지 않은 아이디 입니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		}
		else
		{
			Member info = dao.getMember(id);
			//로그인 성공 -> 세션에 아이디를 저장
	   		session.setAttribute("userid", id);
	   		session.setAttribute("nick", info.getNick());
	   		session.setAttribute("ulevel", info.getUlevel());
	   		session.setAttribute("strLevel", info.getStrLevel());
	   		
	   		// 로그인 성공후 메인화면으로 이동
	   		return "/";
		}
	}
}
