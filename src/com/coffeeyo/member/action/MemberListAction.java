package com.coffeeyo.member.action;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.member.model.Member;
import com.coffeeyo.member.model.MemberDAO;

import bcom.coffeeyo.board.util.PageUtil;

public class MemberListAction implements Action {
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 현재 페이지 번호 만들기
		String strPage = request.getParameter("nowPage");
		
		int nowPage=0;
		if(strPage==null || strPage.length()==0) {
			//파라미터가 없다
			nowPage=1;
		}else {
			nowPage=Integer.parseInt(strPage);					
		}
		
		// 검색조건과 검색내용을 가져온다.
		String opt = request.getParameter("opt");
		String condition = request.getParameter("condition");
		
		// 검색조건과 내용을 Map에 담는다.
		HashMap<String, Object> listOpt = new HashMap<String, Object>();
		listOpt.put("opt", opt);
		listOpt.put("condition", condition);
		
		//총 데이터개수 구하기
		MemberDAO dao = MemberDAO.getInstance();
		int listCount = dao.getMemberListCount(listOpt);
		
		
		//페이지 정보를 만들어 놓자
		//한 화면에서 10개의 게시물이 보이도록 하고
		//한 화면에는 3개씩 페이지 이동 기능을 만들 예정
		PageUtil pinfo=new PageUtil(nowPage,listCount,10,3);
		
		ArrayList<Member> memberList = dao.getMemberList(nowPage, pinfo, listOpt);
		
		request.setAttribute("memberList", memberList);
		request.setAttribute("PINFO", pinfo);
		request.setAttribute("listCount", listCount);
		request.setAttribute("nowPage", nowPage);
		
		return "/view/admin/member/memberList.jsp";
	}
}
