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
		System.out.println("AdmBoardList컨트롤러다");
		
		//할일
		//보고싶은 페이지를 알아낸다
		//파라미터 nowPage
		HashMap<String,Object> listOpt=new HashMap<String, Object>();
		String opt=req.getParameter("opt");
		String condition=req.getParameter("condition");
		listOpt.put("opt", opt);
		listOpt.put("condition", condition);
		
		String strPage=req.getParameter("nowPage");
		int nowPage=0;
		if(strPage==null || strPage.length()==0) {
			//파라미터가 없다
			nowPage=1;
		}else {
			nowPage=Integer.parseInt(strPage);					
		}
		
		//비지니스로직 수행
		//리턴되어진 결과를 뷰에게 모델로 전달해야한다
		BoardDAO dao=new BoardDAO();

		//총 데이터개수 구하기
		int totalCount=dao.pageCal(listOpt);
		
		//페이지 정보를 만들어 놓자
		//한 화면에서 10개의 게시물이 보이도록 하고
		//한 화면에는 3개씩 페이지 이동 기능을 만들 예정
		PageUtil pinfo=new PageUtil(nowPage,totalCount,10,3);

		//게시물 원글 검색 질의 명령수행하면 모든 게시물이 리턴되어진다
		ArrayList list=dao.selectList(nowPage, pinfo, listOpt);
		
		//사용이 끝난 커넥션은 반드시 커넥션 풀에 반납해야
		//다음 작업에서 커넥션을 사용할 수 있다
		dao.close();
		
		//모델..뷰에게 MODEL을 전달한다라고 표현
		req.setAttribute("LIST",list);
		req.setAttribute("PINFO", pinfo);
		req.setAttribute("COUNT", totalCount);
	
		//뷰에서는 이 데이터를 이용해서 목록을 출력한다
		return "../view/board/boardAdmList.jsp";
	}
}
