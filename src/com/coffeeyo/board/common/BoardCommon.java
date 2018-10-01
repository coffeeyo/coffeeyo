package com.coffeeyo.board.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.coffeeyo.board.comment.model.Comment;
import com.coffeeyo.board.comment.model.CommentDAO;
import com.coffeeyo.board.model.Board;
import com.coffeeyo.board.model.BoardDAO;
import com.coffeeyo.product.model.Category;
import com.coffeeyo.product.model.CategoryDao;
import com.coffeeyo.product.model.Product;
import com.coffeeyo.product.model.ProductDao;

public class BoardCommon {
	
	public static HttpServletRequest boardDetail(HttpServletRequest request) throws Exception {
		System.out.println("BoardCommon boardDetail()");
		request.setCharacterEncoding("UTF-8");
		
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum = request.getParameter("pageNum");
		
		BoardDAO bdao = BoardDAO.getInstance();
		bdao.updateCount(num);
		Board bo = bdao.getDetail(num);
		
		long cate = bo.getCidx();
		long prod = bo.getPidx();
		
		CategoryDao cdao = CategoryDao.getInstance();
		Category cateInfo = cdao.getCategory(cate);
		
		List<Category> cateList = null;
		cateList = cdao.getAllCategory();
		
		ProductDao pdao = ProductDao.getInstance();
		Product prodInfo = pdao.getProduct(prod);
		
		// 게시글 번호를 이용하여 해당 글에 있는 댓글 목록을 가져온다.
		CommentDAO commentDAO = CommentDAO.getInstance();
		ArrayList<Comment> commentList = commentDAO.getCommentList(num);
		
		// 댓글이 1개라도 있다면 request에 commentList를 세팅한다.
		if(commentList.size() > 0)	request.setAttribute("commentList", commentList);
		
		
		request.setAttribute("bo", bo);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("num", num);
		request.setAttribute("cateInfo", cateInfo);
		request.setAttribute("cateList", cateList);
		request.setAttribute("prodInfo", prodInfo);
		
		return request;
	}
	
	public static HttpServletRequest boardList(HttpServletRequest request) throws Exception {
		System.out.println("BoardCommon boardDetail()");
		request.setCharacterEncoding("UTF-8");
		
		// 현재 페이지 번호 만들기
		int spage = 1;
		String pageNum = request.getParameter("pageNum");
		
		if(pageNum != null && !pageNum.equals(""))	spage = Integer.parseInt(pageNum);
		
		// 검색조건과 검색내용을 가져온다.
		String opt = request.getParameter("opt");
		String condition = request.getParameter("condition");
		
		// 검색조건과 내용을 Map에 담는다.
		HashMap<String, Object> listOpt = new HashMap<String, Object>();
		listOpt.put("opt", opt);
		listOpt.put("condition", condition);
		
		BoardDAO bdao = BoardDAO.getInstance();
		int listCount = bdao.getBoardListCount(listOpt);
		
		CategoryDao cateDao = CategoryDao.getInstance();
		List<Category> cateList = null;
		cateList = cateDao.getAllCategory();
		
		// 한 화면에 10개의 게시글을 보여지게함
		// 페이지 번호는 총 5개, 이후로는 [다음]으로 표시
		
		// 전체 페이지 수
		int maxPage = (int)(listCount/10.0 + 0.9);
		
		// 만약 사용자가 주소창에서 페이지 번호를 maxPage 보다 높은 값을 입력시
		// maxPage에 해당하는 목록을 보여준다.
		if(spage > maxPage) spage = maxPage;
		listOpt.put("start", spage*10-9);
		

		List<Board> boardList = null;
		boardList = bdao.getBoardList(listOpt);
		
		//시작 페이지 번호
		int startPage = (int)(spage/5.0 + 0.8) * 5 - 4;
		//마지막 페이지 번호
		int endPage = startPage + 4;
		if(endPage > maxPage)	endPage = maxPage;
		
		request.setAttribute("boardList", boardList);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("cateList", cateList);
		
		// 4개 페이지번호 저장
		request.setAttribute("spage", spage);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("listCount", listCount);	
		
		List<Board> noticeList = null;
		noticeList = bdao.getNoticeList();
		request.setAttribute("noticeList", noticeList);
		
		return request;
	}
}
