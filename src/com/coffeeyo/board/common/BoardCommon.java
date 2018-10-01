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
		
		// �Խñ� ��ȣ�� �̿��Ͽ� �ش� �ۿ� �ִ� ��� ����� �����´�.
		CommentDAO commentDAO = CommentDAO.getInstance();
		ArrayList<Comment> commentList = commentDAO.getCommentList(num);
		
		// ����� 1���� �ִٸ� request�� commentList�� �����Ѵ�.
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
		
		BoardDAO bdao = BoardDAO.getInstance();
		int listCount = bdao.getBoardListCount(listOpt);
		
		CategoryDao cateDao = CategoryDao.getInstance();
		List<Category> cateList = null;
		cateList = cateDao.getAllCategory();
		
		// �� ȭ�鿡 10���� �Խñ��� ����������
		// ������ ��ȣ�� �� 5��, ���ķδ� [����]���� ǥ��
		
		// ��ü ������ ��
		int maxPage = (int)(listCount/10.0 + 0.9);
		
		// ���� ����ڰ� �ּ�â���� ������ ��ȣ�� maxPage ���� ���� ���� �Է½�
		// maxPage�� �ش��ϴ� ����� �����ش�.
		if(spage > maxPage) spage = maxPage;
		listOpt.put("start", spage*10-9);
		

		List<Board> boardList = null;
		boardList = bdao.getBoardList(listOpt);
		
		//���� ������ ��ȣ
		int startPage = (int)(spage/5.0 + 0.8) * 5 - 4;
		//������ ������ ��ȣ
		int endPage = startPage + 4;
		if(endPage > maxPage)	endPage = maxPage;
		
		request.setAttribute("boardList", boardList);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("cateList", cateList);
		
		// 4�� ��������ȣ ����
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
