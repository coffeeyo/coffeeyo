package bcom.coffeeyo.board.action;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coffeeyo.common.action.Action;

import bcom.coffeeyo.board.model.BoardDAO;
import bcom.coffeeyo.board.util.PageUtil;

public class AdmBoardList implements Action {

	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("AdmBoardList��Ʈ�ѷ���");
		
		//����
		//������� �������� �˾Ƴ���
		//�Ķ���� nowPage
		HashMap<String,Object> listOpt=new HashMap<String, Object>();
		String opt=req.getParameter("opt");
		String condition=req.getParameter("condition");
		listOpt.put("opt", opt);
		listOpt.put("condition", condition);
		
		String strPage=req.getParameter("nowPage");
		int nowPage=0;
		if(strPage==null || strPage.length()==0) {
			//�Ķ���Ͱ� ����
			nowPage=1;
		}else {
			nowPage=Integer.parseInt(strPage);					
		}
		
		//�����Ͻ����� ����
		//���ϵǾ��� ����� �信�� �𵨷� �����ؾ��Ѵ�
		BoardDAO dao=new BoardDAO();

		//�� �����Ͱ��� ���ϱ�
		int totalCount=dao.pageCal(listOpt);
		
		//������ ������ ����� ����
		//�� ȭ�鿡�� 10���� �Խù��� ���̵��� �ϰ�
		//�� ȭ�鿡�� 3���� ������ �̵� ����� ���� ����
		PageUtil pinfo=new PageUtil(nowPage,totalCount,10,3);

		//�Խù� ���� �˻� ���� ��ɼ����ϸ� ��� �Խù��� ���ϵǾ�����
		ArrayList list=dao.selectList(nowPage, pinfo, listOpt);
		
		//����� ���� Ŀ�ؼ��� �ݵ�� Ŀ�ؼ� Ǯ�� �ݳ��ؾ�
		//���� �۾����� Ŀ�ؼ��� ����� �� �ִ�
		dao.close();
		
		//��..�信�� MODEL�� �����Ѵٶ�� ǥ��
		req.setAttribute("LIST",list);
		req.setAttribute("PINFO", pinfo);
		req.setAttribute("COUNT", totalCount);
	
		//�信���� �� �����͸� �̿��ؼ� ����� ����Ѵ�
		return "../view/board/boardAdmList.jsp";
	}
}
