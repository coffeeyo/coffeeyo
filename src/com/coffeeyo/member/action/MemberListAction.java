package com.coffeeyo.member.action;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.member.model.Member;
import com.coffeeyo.member.model.MemberDAO;

public class MemberListAction implements Action {
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// ���� ������ ��ȣ �����
		int spage = 1;
		String pageNum = request.getParameter("pageNum");
		
		if(pageNum != null && !pageNum.equals(""))	spage = Integer.parseInt(pageNum);
		
		// �˻����ǰ� �˻������� �����´�.
		String opt = request.getParameter("opt");
		String condition = request.getParameter("condition");
		
		// �˻����ǰ� ������ Map�� ��´�.
		HashMap<String, Object> listOpt = new HashMap<String, Object>();
		listOpt.put("opt", opt);
		listOpt.put("condition", condition);
				
		MemberDAO dao = MemberDAO.getInstance();
		int listCount = dao.getMemberListCount(listOpt);
		
		
		// �� ȭ�鿡 10���� �Խñ��� ����������
		// ������ ��ȣ�� �� 5��, ���ķδ� [����]���� ǥ��
		
		// ��ü ������ ��
		int maxPage = (int)(listCount/10.0 + 0.9);
		
		// ���� ����ڰ� �ּ�â���� ������ ��ȣ�� maxPage ���� ���� ���� �Է½�
		// maxPage�� �ش��ϴ� ����� �����ش�.
		if(spage > maxPage) spage = maxPage;
		listOpt.put("start", spage*10-9);
		
		
		ArrayList<Member> memberList = dao.getMemberList(listOpt);
		
		//���� ������ ��ȣ
		int startPage = (int)(spage/5.0 + 0.8) * 5 - 4;
		//������ ������ ��ȣ
		int endPage = startPage + 4;
		if(endPage > maxPage)	endPage = maxPage;
		
		request.setAttribute("memberList", memberList);
		request.setAttribute("pageNum", pageNum);
		
		// 4�� ��������ȣ ����
		request.setAttribute("spage", spage);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("listCount", listCount);
		
		return "/view/admin/member/memberList.jsp";
	}
}
