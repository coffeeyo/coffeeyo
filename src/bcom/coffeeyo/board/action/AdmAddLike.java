package bcom.coffeeyo.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;

import bcom.coffeeyo.board.model.BoardDAO;
import bcom.coffeeyo.board.model.BoardVO;

public class AdmAddLike implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//파라미터받기
		System.out.println("addlike.java들어옴");
		String strNo=request.getParameter("oriNo");
		int oriNo=Integer.parseInt(strNo);
		String nowPage=request.getParameter("nowPage");
		HttpSession session=request.getSession();
		String userid=(String) session.getAttribute("userid");
		
		//비지니스 로직
		//LikeCheck테이블을 가져와서
		//해당 게시물에 해당유저 아이디와 상태값이 있는지 확인하기
		BoardDAO dao=new BoardDAO();
		BoardVO vo=dao.selectLikeCheck(oriNo, userid);
		String check=vo.getUserid();
		int num=0;
		//좋아요 체크 게시물에 해당유저 아이디가 없을 때
		if(check==null || check.length()==0) {
			//좋아요체크 시퀀스 생성
			//해당유저 아이디와 상태값(1) 입력
			int likeidx=dao.getLSeq();
			//좋아요체크 테이블에 insert
			dao.insertLikeCheck(likeidx, oriNo, userid);
			//게시물 좋아요 증가처리
			dao.updateBoardLikeCount(num,oriNo);
		}
		//좋아요 체크 게시물에 해당유저 아이디가 존재할 때 
		else {
			//해당유저의 likeidx, status 검색
			int likeidx=vo.getLikeidx();
			int status=vo.getStatus();
			//상태값 업데이트 처리하기
			dao.updateLikeCheck(status, likeidx);
			//게시물 좋아요 감소처리			
			dao.updateBoardLikeCount(status,oriNo);
		}

		dao.close();
		
		//모델
		request.setAttribute("oriNo", oriNo);
		request.setAttribute("nowPage", nowPage);
		//뷰
		return "../view/board/toBoardAdmDetail.jsp";
	}
}
