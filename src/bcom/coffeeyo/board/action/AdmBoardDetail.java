package bcom.coffeeyo.board.action;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.coffeeyo.common.action.Action;

import bcom.coffeeyo.board.model.BoardDAO;
import bcom.coffeeyo.board.model.BoardVO;

public class AdmBoardDetail implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//할일
		//파라미터 받기
		String strNo=request.getParameter("oriNo");
		int oriNo=Integer.parseInt(strNo);
		String nowPage=request.getParameter("nowPage");
		PreparedStatement stmt=null;
		ResultSet rs=null;
		String sql="";
		
		//비지니스 로직
		
		//세션에 이용자가 본 게시물의 글번호를 누적해서 기록하기
		boolean isHit=false;
		//조회수 증가여부를 판단하는 변수
		HttpSession session=request.getSession();
		ArrayList hitList=(ArrayList)session.getAttribute("SHOW");
		//세션에 이미 본 게시물 번호를 확인해서 
		//존재하면 조회수 증가를 하지 않고
		//존재하지 않으면 조회수 증가 처리하기
		if(hitList==null || hitList.size()==0) {
			//어떤 글도 보지 않은 경우 -> 최초의 1번은 리스트 생성과 세션기록해주기
			isHit=true;
			hitList=new ArrayList();
			hitList.add(oriNo);
			session.setAttribute("SHOW", hitList);
		}
		else if(hitList.contains(oriNo)){
			//이 글을 본적이 있는 사람의 경우 -> 조회수 증가X
			isHit=false;
		}
		else {
			//다른 글은 봤지만 이 글은 보지 않은 경우 -> 조회수 증가
			isHit=true;
			hitList.add(oriNo);
			//내용이 추가 됐으니깐 세션 내용 갱신해줘야함
			session.setAttribute("SHOW", hitList);
		}
		//조회수 증가하기
		BoardDAO dao=new BoardDAO();
		if(isHit) {
			dao.updateBoardReadCnt(oriNo);					
		}
		//상세보기를 하기 위해 해당 글정보를 검색
		BoardVO vo=dao.selectDetail(oriNo);
		//댓글정보 검색하기 
		ArrayList list=dao.selectComment(oriNo);
		
		dao.close();
				
		//모델에 넘기기
		request.setAttribute("oriNo", oriNo);
		request.setAttribute("nowPage", nowPage);
		request.setAttribute("DATA", vo);
		request.setAttribute("COMM", list);
		return "../view/board/boardAdmDetail.jsp";
	}
}
