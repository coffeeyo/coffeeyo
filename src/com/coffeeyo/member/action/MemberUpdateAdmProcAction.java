package com.coffeeyo.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.member.model.Member;
import com.coffeeyo.member.model.MemberDAO;

public class MemberUpdateAdmProcAction implements Action {
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8"); // 인코딩
		
		MemberDAO dao = MemberDAO.getInstance();
		
		// 세션이 가지고있는 로그인한 ID 정보를 가져온다
		HttpSession session = request.getSession();
		String id = request.getParameter("userid");
		String nowPage = request.getParameter("nowPage");
		String strGender = request.getParameter("gender");
		if(strGender == null) {
			strGender = "0";
		}
		
		// 수정할 정보를 자바빈에 세팅한다.
		Member member = new Member();
		member.setUserid(id);
		member.setPasswd(request.getParameter("passwd"));
		//member.setNick(request.getParameter("nick"));
		member.setHp(request.getParameter("hp"));
		member.setGender(Integer.parseInt(strGender));
		member.setBirthday(request.getParameter("birthday"));
		member.setJob(Integer.parseInt(request.getParameter("job")));
		member.setUlevel(Integer.parseInt(request.getParameter("ulevel")));
		member.setStatus(Integer.parseInt(request.getParameter("status")));
				
		dao.updateMemberProc(member);		
		
   		// 회원정보 수정 성공 메시지를 세션에 담는다.   		
		response.sendRedirect("/admin/resultFormAction.yo?userid="+id+"&msg=0&nowPage="+nowPage);
		
		return "none";
	}
}
