package bcom.coffeeyo.board.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;
import com.coffeeyo.product.model.Category;
import com.coffeeyo.product.model.CategoryDao;

import bcom.coffeeyo.board.model.BoardDAO;

public class WriteForm implements Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		System.out.println("WriteForm��Ʈ�ѷ�");
		//����
		//������ �̿��ؼ� �α��εǾ��ִ��� Ȯ���ϰ�
		HttpSession session=req.getSession();
		
		String uid=(String)session.getAttribute("userid");
		String unick=(String)session.getAttribute("nick");
		//�α��� ���� �ʾҴٸ� �α����� �����ֱ�
		if(uid==null || uid.length()==0) {
			//return "../Member/LoginForm.jsp";
			try {
				resp.sendRedirect("../member/loginFormAction.yo");
			} catch (Exception e) {
				System.out.println("���ǿ� ���̵� ��� �α��������� �̵��ϴ� �� ���� �߻�="+e);
			}
			return "none";
		}
		//�α��� ���� �� �۾����� �����ֱ�
		else {
			//��������
			BoardDAO dao=new BoardDAO();
			ArrayList clist=dao.selectCategory();
			ArrayList plist1=dao.selectColdbrew();
			ArrayList plist2=dao.selectEspresso();
			ArrayList plist3=dao.selectFrappuccino();
			
			CategoryDao cateDao = CategoryDao.getInstance();
			List<Category> cateList = null;
			cateList = cateDao.getAllCategory();

			req.setAttribute("cateList", cateList);
			
			//��
			req.setAttribute("CLIST", clist);
			req.setAttribute("PLIST1", plist1);
			req.setAttribute("PLIST2", plist2);
			req.setAttribute("PLIST3", plist3);
			//��
			dao.close();
			return "../view/board/boardWriteForm.jsp";			
		}
	}

}
