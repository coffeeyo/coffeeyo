package com.coffeeyo.board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.coffeeyo.common.util.DBConnection;
import com.coffeeyo.product.model.Category;
import com.coffeeyo.product.model.Product;

public class BoardDAO 
{
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private static BoardDAO instance;
	
	private BoardDAO(){}
	public static BoardDAO getInstance(){
		if(instance==null)
			instance=new BoardDAO();
		return instance;
	}
	
	// 시퀀스를 가져온다.
	public int getSeq()
	{
		int result = 1;
		
		try {
			conn = DBConnection.getConnection();
			
			// 시퀀스 값을 가져온다. (DUAL : 시퀀스 값을 가져오기위한 임시 테이블)
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT BOARD_SEQ.NEXTVAL FROM DUAL");
			
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			// 쿼리 실행
			rs = pstmt.executeQuery();
			
			if(rs.next())	result = rs.getInt(1);
			DBConnection.close(rs);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		
		return result;	
	} // end getSeq
	
	// 글 삽입
	public boolean boardInsert(Board board)
	{
		boolean result = false;
		
		try {
			conn = DBConnection.getConnection();
			
			// 자동 커밋을 false로 한다.
			conn.setAutoCommit(false);
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO BOARD");
			sql.append("(BIDX, USERID, CIDX, PIDX, SUBJECT, COMM, IMAGE,");
			sql.append(" NOTIYN, LIKECNT, READCNT, CREATEDT) ");
			sql.append(" VALUES(");
			sql.append("?,?,?,?,?,?,?,");
			sql.append("?,0,0,sysdate");
			sql.append(" )");
			
			long num = board.getBidx();			// 글번호(시퀀스 값)
						
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setLong(1, num);
			pstmt.setString(2, board.getUserid());
			pstmt.setLong(3, board.getCidx());
			pstmt.setLong(4, board.getPidx());
			pstmt.setString(5, board.getSubject());
			pstmt.setString(6, board.getComm());
			pstmt.setString(7, board.getImage());
			pstmt.setString(8, board.getNotiyn());

			int flag = pstmt.executeUpdate();
			if(flag > 0){
				result = true;
				conn.commit(); // 완료시 커밋
			}
			
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			} 
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		
		return result;	
	} // end boardInsert();
		
	//공지사항 목록 가져오기
	public ArrayList<Board> getNoticeList()
	{
		ArrayList<Board> list = new ArrayList<Board>();
		
		try {
			conn = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer();
			
			// 글목록 전체를 보여줄 때
			sql.append("SELECT * FROM");
			sql.append(" (SELECT  ROWNUM AS rnum, data.* FROM ");
			sql.append("	(SELECT BIDX, b.USERID,	 CIDX, PIDX,");
			sql.append("			SUBJECT, COMM, IMAGE,");
			sql.append("			NOTIYN, LIKECNT, READCNT,");
			sql.append("			b.CREATEDT, b.UPDATEDT, ");
			sql.append("			m.NICK ");
			sql.append("	FROM BOARD b ");
			sql.append("	LEFT JOIN MEMBER m ");
			sql.append(" 	ON b.USERID=m.USERID");
			sql.append(" 	WHERE b.NOTIYN='Y'");
			sql.append("	ORDER BY BIDX desc)");              
			sql.append(" data) ");
			sql.append("WHERE ROWNUM >= ? and ROWNUM <= ?");
			//System.out.println("sql="+sql.toString());
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setInt(1, 1);
			pstmt.setInt(2, 5);
			
			// StringBuffer를 비운다.
			sql.delete(0, sql.toString().length());
			
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				Board board = new Board();
				board.setRnum(rs.getLong("rnum"));
				board.setBidx(rs.getLong("BIDX"));
				board.setUserid(rs.getString("USERID"));
				board.setCidx(rs.getLong("CIDX"));
				board.setPidx(rs.getLong("PIDX"));
				board.setSubject(rs.getString("SUBJECT"));
				board.setComm(rs.getString("COMM"));
				board.setImage(rs.getString("IMAGE"));
				board.setNotiyn(rs.getString("NOTIYN"));
				board.setLikecnt(rs.getLong("LIKECNT"));
				board.setReadcnt(rs.getLong("READCNT"));
				board.setCreatedt(rs.getDate("CREATEDT"));
				board.setUpdatedt(rs.getDate("UPDATEDT"));
				board.setNickName(rs.getString("NICK"));
				list.add(board);
			}
			System.out.println("list="+list.size());
			DBConnection.close(rs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		
		return list;
	}
	
	// 글목록 가져오기
	public ArrayList<Board> getBoardList(HashMap<String, Object> listOpt)
	{
		ArrayList<Board> list = new ArrayList<Board>();
		
		String opt = (String)listOpt.get("opt");
		String condition = (String)listOpt.get("condition");
		int start = (Integer)listOpt.get("start");
		
		try {
			conn = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer();
			
			// 글목록 전체를 보여줄 때
			if(opt == null)
			{
				sql.append("SELECT * FROM");
				sql.append(" (SELECT  ROWNUM AS rnum, data.* FROM ");
				sql.append("	(SELECT BIDX, b.USERID,	 CIDX, PIDX,");
				sql.append("			SUBJECT, COMM, IMAGE,");
				sql.append("			NOTIYN, LIKECNT, READCNT,");
				sql.append("			b.CREATEDT, b.UPDATEDT, ");
				sql.append("			m.NICK ");
				sql.append("	FROM BOARD b ");
				sql.append("	LEFT JOIN MEMBER m ");
				sql.append(" 	ON b.USERID=m.USERID");
				sql.append("	ORDER BY BIDX desc)");              
				sql.append(" data) ");
				sql.append("WHERE rnum >= ? and rnum <= ?");
				//System.out.println("sql="+sql.toString());
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setInt(1, start);
				pstmt.setInt(2, start+9);
				
				// StringBuffer를 비운다.
				sql.delete(0, sql.toString().length());
			}
			else if(opt.equals("0")) // 제목으로 검색
			{
				sql.append("SELECT * FROM");
				sql.append(" (SELECT  ROWNUM AS rnum, data.* FROM ");
				sql.append("	(SELECT BIDX, b.USERID,	 CIDX, PIDX,");
				sql.append("			SUBJECT, COMM, IMAGE,");
				sql.append("			NOTIYN, LIKECNT, READCNT,");
				sql.append("			b.CREATEDT, b.UPDATEDT, ");
				sql.append("			m.NICK ");
				sql.append("	FROM BOARD b ");
				sql.append("	LEFT JOIN MEMBER m ");
				sql.append(" 	ON b.USERID=m.USERID");
				sql.append(" 	WHERE SUBJECT like ?");
				sql.append("	ORDER BY BIDX desc)");              
				sql.append(" data) ");
				sql.append("WHERE rnum >= ? and rnum <= ?");
				
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setString(1, "%"+condition+"%");
				pstmt.setInt(2, start);
				pstmt.setInt(3, start+9);
				
				sql.delete(0, sql.toString().length());
			}
			else if(opt.equals("1")) // 내용으로 검색
			{
				sql.append("SELECT * FROM");
				sql.append(" (SELECT  ROWNUM AS rnum, data.* FROM ");
				sql.append("	(SELECT BIDX, b.USERID,	 CIDX, PIDX,");
				sql.append("			SUBJECT, COMM, IMAGE,");
				sql.append("			NOTIYN, LIKECNT, READCNT,");
				sql.append("			b.CREATEDT, b.UPDATEDT, ");
				sql.append("			m.NICK ");
				sql.append("	FROM BOARD b ");
				sql.append("	LEFT JOIN MEMBER m ");
				sql.append(" 	ON b.USERID=m.USERID");
				sql.append(" 	WHERE COMM like ?");
				sql.append("	ORDER BY BIDX desc)");              
				sql.append(" data) ");
				sql.append("WHERE rnum >=? and rnum <=?");
				
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setString(1, "%"+condition+"%");
				pstmt.setInt(2, start);
				pstmt.setInt(3, start+9);
				
				sql.delete(0, sql.toString().length());
			}
			else if(opt.equals("2")) // 제목+내용으로 검색
			{
				sql.append("SELECT * FROM");
				sql.append(" (SELECT  ROWNUM AS rnum, data.* FROM ");
				sql.append("	(SELECT BIDX, b.USERID,	 CIDX, PIDX,");
				sql.append("			SUBJECT, COMM, IMAGE,");
				sql.append("			NOTIYN, LIKECNT, READCNT,");
				sql.append("			b.CREATEDT, b.UPDATEDT, ");
				sql.append("			m.NICK ");
				sql.append("	FROM BOARD b ");
				sql.append("	LEFT JOIN MEMBER m ");
				sql.append(" 	ON b.USERID=m.USERID");
				sql.append(" 	WHERE SUBJECT like ?");
				sql.append(" 	OR COMM like ?");
				sql.append("	ORDER  BY BIDX desc)");              
				sql.append(" data) ");
				sql.append("WHERE rnum >=? and rnum <=?");
				
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setString(1, "%"+condition+"%");
				pstmt.setString(2, "%"+condition+"%");
				pstmt.setInt(3, start);
				pstmt.setInt(4, start+9);
				
				sql.delete(0, sql.toString().length());
			}
			else if(opt.equals("3")) // 글쓴이로 검색
			{	
				sql.append("SELECT * FROM");
				sql.append(" (SELECT  ROWNUM AS rnum, data.* FROM ");
				sql.append("	(SELECT BIDX, b.USERID,	 CIDX, PIDX,");
				sql.append("			SUBJECT, COMM, IMAGE,");
				sql.append("			NOTIYN, LIKECNT, READCNT,");
				sql.append("			b.CREATEDT, b.UPDATEDT, ");
				sql.append("			m.NICK ");
				sql.append("	FROM BOARD b ");
				sql.append("	LEFT JOIN MEMBER m ");
				sql.append(" 	ON b.USERID=m.USERID");
				sql.append(" 	WHERE m.NICK like ?");
				sql.append("	ORDER BY BIDX desc)");              
				sql.append(" data) ");
				sql.append("WHERE rnum >=? and rnum <=?");
				
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setString(1, "%"+condition+"%");
				pstmt.setInt(2, start);
				pstmt.setInt(3, start+9);
				
				sql.delete(0, sql.toString().length());
			}
			
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				Board board = new Board();
				board.setRnum(rs.getLong("rnum"));
				board.setBidx(rs.getLong("BIDX"));
				board.setUserid(rs.getString("USERID"));
				board.setCidx(rs.getLong("CIDX"));
				board.setPidx(rs.getLong("PIDX"));
				board.setSubject(rs.getString("SUBJECT"));
				board.setComm(rs.getString("COMM"));
				board.setImage(rs.getString("IMAGE"));
				board.setNotiyn(rs.getString("NOTIYN"));
				board.setLikecnt(rs.getLong("LIKECNT"));
				board.setReadcnt(rs.getLong("READCNT"));
				board.setCreatedt(rs.getDate("CREATEDT"));
				board.setUpdatedt(rs.getDate("UPDATEDT"));
				board.setNickName(rs.getString("NICK"));
				list.add(board);
			}
			System.out.println("list="+list.size());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		
		return list;
	} // end getBoardList
	
	
	// 글의 개수를 가져오는 메서드
	public int getBoardListCount(HashMap<String, Object> listOpt)
	{
		int result = 0;
		String opt = (String)listOpt.get("opt");
		String condition = (String)listOpt.get("condition");
		
		try {
			conn = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer();
			
			if(opt == null)	// 전체글의 개수
			{
				sql.append("select count(*) from BOARD");
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				
				// StringBuffer를 비운다.
				sql.delete(0, sql.toString().length());
			}
			else if(opt.equals("0")) // 제목으로 검색한 글의 개수
			{
				sql.append("select count(*) from BOARD where SUBJECT like ?");
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setString(1, '%'+condition+'%');
				
				sql.delete(0, sql.toString().length());
			}
			else if(opt.equals("1")) // 내용으로 검색한 글의 개수
			{
				sql.append("select count(*) from BOARD where COMM like ?");
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setString(1, '%'+condition+'%');
				
				sql.delete(0, sql.toString().length());
			}
			else if(opt.equals("2")) // 제목+내용으로 검색한 글의 개수
			{
				sql.append("select count(*) from BOARD ");
				sql.append("where SUBJECT like ? or COMM like ?");
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setString(1, '%'+condition+'%');
				pstmt.setString(2, '%'+condition+'%');
				
				sql.delete(0, sql.toString().length());
			}
			else if(opt.equals("3")) // 글쓴이로 검색한 글의 개수
			{
				sql.append("select count(*) from BOARD b left inner join member m on b.userid=m.userid where m.nickD like ?");
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setString(1, '%'+condition+'%');
				
				sql.delete(0, sql.toString().length());
			}
			
			rs = pstmt.executeQuery();
			if(rs.next())	result = rs.getInt(1);
			DBConnection.close(rs);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return result;
	} // end getBoardListCount
	
	
	// 상세보기
	public Board getDetail(int boardNum)
	{	
		Board board = null;
		
		try {
			conn = DBConnection.getConnection();
			
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT BIDX, b.USERID,	 CIDX, PIDX,");
			sql.append("			SUBJECT, COMM, IMAGE,");
			sql.append("			NOTIYN, LIKECNT, READCNT,");
			sql.append("			b.CREATEDT, b.UPDATEDT, ");
			sql.append("			m.NICK ");
			sql.append("	FROM BOARD b ");
			sql.append("	LEFT JOIN MEMBER m ");
			sql.append(" 	ON b.USERID=m.USERID");
			sql.append(" 	WHERE BIDX = ?");
			
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setInt(1, boardNum);
			
			rs = pstmt.executeQuery();
			if(rs.next())
			{
				board = new Board();
				board.setBidx(boardNum);
				board.setUserid(rs.getString("USERID"));
				board.setCidx(rs.getLong("CIDX"));
				board.setPidx(rs.getLong("PIDX"));
				board.setSubject(rs.getString("SUBJECT"));
				board.setComm(rs.getString("COMM"));
				board.setImage(rs.getString("IMAGE"));
				board.setNotiyn(rs.getString("NOTIYN"));
				board.setLikecnt(rs.getLong("LIKECNT"));
				board.setReadcnt(rs.getLong("READCNT"));
				board.setCreatedt(rs.getDate("CREATEDT"));
				board.setUpdatedt(rs.getDate("UPDATEDT"));
				board.setNickName(rs.getString("NICK"));
			}
			DBConnection.close(rs);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return board;
	} // end getDetail()
	
	//좋아요 증가
	public boolean updateLikeCount(int boardNum)
	{
		boolean result = false;
		
		try {
			conn = DBConnection.getConnection();
			
			// 자동 커밋을 false로 한다.
			conn.setAutoCommit(false);
			
			StringBuffer sql = new StringBuffer();
			sql.append("update BOARD set LIKECNT = LIKECNT+1 ");
			sql.append("where BIDX = ?");
			
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setInt(1, boardNum);
			
			int flag = pstmt.executeUpdate();
			if(flag > 0){
				result = true;
				conn.commit(); // 완료시 커밋
			}	
		} catch (Exception e) {
			try {
				conn.rollback(); // 오류시 롤백
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return result;
	}
	
	// 조회수 증가
	public boolean updateCount(int boardNum)
	{
		boolean result = false;
		
		try {
			conn = DBConnection.getConnection();
			
			// 자동 커밋을 false로 한다.
			conn.setAutoCommit(false);
			
			StringBuffer sql = new StringBuffer();
			sql.append("update BOARD set READCNT = READCNT+1 ");
			sql.append("where BIDX = ?");
			
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setInt(1, boardNum);
			
			int flag = pstmt.executeUpdate();
			if(flag > 0){
				result = true;
				conn.commit(); // 완료시 커밋
			}	
		} catch (Exception e) {
			try {
				conn.rollback(); // 오류시 롤백
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return result;
	} // end updateCount
	
	
	// 삭제할 파일명을 가져온다.
	public String getFileName(int boardNum)
	{
		String fileName = null;
		
		try {
			conn = DBConnection.getConnection();
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT IMAGE from BOARD where BIDX=?");
			
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setInt(1, boardNum);
			
			rs = pstmt.executeQuery();
			if(rs.next()) fileName = rs.getString("IMAGE");
			DBConnection.close(rs);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return fileName;
	} // end getFileName
		
	// 게시글 삭제
	public boolean deleteBoard(int boardNum) 
	{
		boolean result = false;

		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false); // 자동 커밋을 false로 한다.

			StringBuffer sql = new StringBuffer();
			sql.append("DELETE FROM BOARD");
			sql.append(" WHERE BIDX = ?");
			
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setInt(1, boardNum);
			
			int flag = pstmt.executeUpdate();
			if(flag > 0){
				result = true;
				conn.commit(); // 완료시 커밋
			}	
			
		} catch (Exception e) {
			try {
				conn.rollback(); // 오류시 롤백
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return result;
	} // end deleteBoard
	
	// 글 수정
	public boolean updateBoard(Board board) 
	{
		boolean result = false;
		
		try{
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false); // 자동 커밋을 false로 한다.
			
			StringBuffer sql = new StringBuffer();
			
			if(board.getImage() != null) {
				sql.append("UPDATE BOARD SET");
				sql.append(" SUBJECT=?");
				sql.append(" ,COMM=?");
				sql.append(" ,IMAGE=?");
				sql.append(" ,CIDX=?");
				sql.append(" ,PIDX=?");
				sql.append(" ,UPDATEDT=SYSDATE ");
				sql.append("WHERE BIDX=?");
	
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setString(1, board.getSubject());
				pstmt.setString(2, board.getComm());
				pstmt.setString(3, board.getImage());
				pstmt.setLong(4, board.getCidx());
				pstmt.setLong(5, board.getPidx());
				pstmt.setLong(6, board.getBidx());
			}
			else
			{
				sql.append("UPDATE BOARD SET");
				sql.append(" SUBJECT=?");
				sql.append(" ,COMM=?");
				sql.append(" ,CIDX=?");
				sql.append(" ,PIDX=?");
				sql.append(" ,UPDATEDT=SYSDATE ");
				sql.append("WHERE BIDX=?");
	
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setString(1, board.getSubject());
				pstmt.setString(2, board.getComm());
				pstmt.setLong(3, board.getCidx());
				pstmt.setLong(4, board.getPidx());
				pstmt.setLong(5, board.getBidx());
			}
			
			int flag = pstmt.executeUpdate();
			if(flag > 0){
				result = true;
				conn.commit(); // 완료시 커밋
			}
			
		} catch (Exception e) {
			try {
				conn.rollback(); // 오류시 롤백
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return result;
	} // end updateBoard
	
	public boolean updateBoardManage(Board board) 
	{
		boolean result = false;
		
		try{
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false); // 자동 커밋을 false로 한다.
			
			StringBuffer sql = new StringBuffer();
			
			if(board.getImage() != null) {
				sql.append("UPDATE BOARD SET");
				sql.append(" SUBJECT=?");
				sql.append(" ,COMM=?");
				sql.append(" ,IMAGE=?");
				sql.append(" ,NOTIYN=?");
				sql.append(" ,UPDATEDT=SYSDATE ");
				sql.append("WHERE BIDX=?");
	
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setString(1, board.getSubject());
				pstmt.setString(2, board.getComm());
				pstmt.setString(3, board.getImage());
				pstmt.setString(4, board.getNotiyn());
				pstmt.setLong(5, board.getBidx());
			}
			else
			{
				sql.append("UPDATE BOARD SET");
				sql.append(" SUBJECT=?");
				sql.append(" ,COMM=?");
				sql.append(" ,NOTIYN=?");
				sql.append(" ,UPDATEDT=SYSDATE ");
				sql.append("WHERE BIDX=?");
	
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setString(1, board.getSubject());
				pstmt.setString(2, board.getComm());
				pstmt.setString(3, board.getNotiyn());
				pstmt.setLong(4, board.getBidx());
			}
			
			int flag = pstmt.executeUpdate();
			if(flag > 0){
				result = true;
				conn.commit(); // 완료시 커밋
			}
			
		} catch (Exception e) {
			try {
				conn.rollback(); // 오류시 롤백
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return result;
	} // end updateBoard
}
