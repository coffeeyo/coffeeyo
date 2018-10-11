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
		
		// ���� ������ ��ȣ �����
		String strPage = request.getParameter("nowPage");
		
		int nowPage=0;
		if(strPage==null || strPage.length()==0) {
			//�Ķ���Ͱ� ����
			nowPage=1;
		}else {
			nowPage=Integer.parseInt(strPage);					
		}
		
		// �˻����ǰ� �˻������� �����´�.
		String opt = request.getParameter("opt");
		String condition = request.getParameter("condition");
		
		// �˻����ǰ� ������ Map�� ��´�.
		HashMap<String, Object> listOpt = new HashMap<String, Object>();
		listOpt.put("opt", opt);
		listOpt.put("condition", condition);
		
		//�� �����Ͱ��� ���ϱ�
		MemberDAO dao = MemberDAO.getInstance();
		int listCount = dao.getMemberListCount(listOpt);
		
		
		//������ ������ ����� ����
		//�� ȭ�鿡�� 10���� �Խù��� ���̵��� �ϰ�
		//�� ȭ�鿡�� 3���� ������ �̵� ����� ���� ����
		PageUtil pinfo=new PageUtil(nowPage,listCount,10,3);
		
		ArrayList<Member> memberList = dao.getMemberList(nowPage, pinfo, listOpt);
		
		request.setAttribute("memberList", memberList);
		request.setAttribute("PINFO", pinfo);
		request.setAttribute("listCount", listCount);
		request.setAttribute("nowPage", nowPage);
		
		return "/view/admin/member/memberList.jsp";
	}
}
