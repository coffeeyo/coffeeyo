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
		
		// ���̵�� ��й�ȣ�� �����´�.
		String id = request.getParameter("id");
		String password = request.getParameter("pass");
		
		// DB���� ���̵�, ��й�ȣ Ȯ��
		MemberDAO dao = MemberDAO.getInstance();
		int check = dao.loginCheck(id, password);
		
		if(check == 0)	// ��й�ȣ Ʋ����� -> �ٽ� �α��� ȭ������ �̵�
		{ 
			// �α��� ���н� �޽����� request�� ��´�.
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('��й�ȣ�� ���� �ʽ��ϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		}
		else if(check == 2) // Ż���� ���̵��� ��� -> �ٽ� �α��� ȭ������ �̵�
		{
	   		response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('Ż���� ���̵� �Դϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		}
		else if(check == -1) // ���̵� ���� ��� -> �ٽ� �α��� ȭ������ �̵�
		{
	   		response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('���Ե��� ���� ���̵� �Դϴ�.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		}
		else
		{
			Member info = dao.getMember(id);
			//�α��� ���� -> ���ǿ� ���̵� ����
	   		session.setAttribute("userid", id);
	   		session.setAttribute("nick", info.getNick());
	   		session.setAttribute("ulevel", info.getUlevel());
	   		session.setAttribute("strLevel", info.getStrLevel());
	   		
	   		// �α��� ������ ����ȭ������ �̵�
	   		return "/";
		}
	}
}
