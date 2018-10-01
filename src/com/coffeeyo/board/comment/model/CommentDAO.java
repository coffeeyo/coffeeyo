package com.coffeeyo.board.comment.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.coffeeyo.common.util.DBConnection;

public class CommentDAO 
{
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private static CommentDAO instance;
	
	private CommentDAO(){}
	public static CommentDAO getInstance(){
		if(instance==null)
			instance=new CommentDAO();
		return instance;
	}
	
	// �������� �����´�.
	public int getSeq() 
	{
		int result = 1;
		try {
			conn = DBConnection.getConnection();
			
			// ������ ���� �����´�. (DUAL : ������ ���� ������������ �ӽ� ���̺�)
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT BOARDCOMM_SEQ.NEXTVAL FROM DUAL");

			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			
			rs = pstmt.executeQuery(); // ���� ����

			if (rs.next())	result = rs.getInt(1);
			DBConnection.close(rs);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return result;
	} // end getSeq
	
	
	// ��� ���
	public boolean insertComment(Comment comment)
	{
		boolean result = false;
		
		try {
			conn = DBConnection.getConnection();

			// �ڵ� Ŀ���� false�� �Ѵ�.
			conn.setAutoCommit(false);
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO BOARDCOMM ");
			sql.append(" (BCIDX, BIDX, USERID, COMM, CREATEDT)");
			sql.append(" VALUES(?,?,?,?,sysdate)");
	
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setLong(1, comment.getBcidx());
			pstmt.setLong(2, comment.getBidx());
			pstmt.setString(3, comment.getUserid());
			pstmt.setString(4, comment.getComm());
			
			int flag = pstmt.executeUpdate();
			if(flag > 0){
				result = true;
				conn.commit(); // �Ϸ�� Ŀ��
			}
			
		} catch (Exception e) {
			try {
				conn.rollback(); // ������ �ѹ�
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
	} // end insertComment();	
	
	// ��� ��� ��������
	public ArrayList<Comment> getCommentList(int boardNum)
	{
		ArrayList<Comment> list = new ArrayList<Comment>();
		
		try {
			conn = DBConnection.getConnection();
			
			/* 
			 * ����� ������ ó���� �ϰ�ʹٸ� �� ������ ����ϸ� �ȴ�.
			 */
			
			StringBuffer sql = new StringBuffer();
			sql.append("	SELECT BCIDX, BIDX,");
			sql.append("			c.USERID, COMM,");
			sql.append("			c.CREATEDT, m.nick ");
			sql.append("	FROM BOARDCOMM c");
			sql.append("	LEFT JOIN MEMBER m");
			sql.append("	ON c.USERID=m.USERID");
			sql.append("	WHERE BIDX = ?");       
			
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setInt(1, boardNum);
			
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				Comment comment = new Comment();
				comment.setBcidx(rs.getLong("BCIDX"));
				comment.setBidx(rs.getInt("BIDX"));
				comment.setUserid(rs.getString("USERID"));
				comment.setComm(rs.getString("COMM"));
				comment.setCreatedt(rs.getDate("CREATEDT"));
				comment.setNickName(rs.getString("NICK"));

				list.add(comment);
			}
			DBConnection.close(rs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return list;
	} // end getCommentList
	
	// ��� 1���� ������ �����´�.
	public Comment getComment(int comment_num)
	{
		Comment comment = null;
		
		try {
			conn = DBConnection.getConnection();
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM BOARDCOMM WHERE BCIDX = ?");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, comment_num);
			
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				comment = new Comment();
				comment.setBcidx(rs.getLong("BCIDX"));
				comment.setBidx(rs.getLong("BIDX"));
				comment.setUserid(rs.getString("USERID"));
				comment.setComm(rs.getString("COMM"));
				comment.setCreatedt(rs.getDate("CREATEDT"));
			}
			DBConnection.close(rs);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return comment; 
	} // end getComment
	
	
	// ��� ����
	public boolean deleteComment(int comment_num) 
	{
		boolean result = false;

		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false); // �ڵ� Ŀ���� false�� �Ѵ�.

			StringBuffer sql = new StringBuffer();
			sql.append("DELETE FROM BOARDCOMM");
			sql.append(" WHERE BCIDX = ?");
						
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, comment_num);
			
			int flag = pstmt.executeUpdate();
			if(flag > 0){
				result = true;
				conn.commit(); // �Ϸ�� Ŀ��
			}	
			
		} catch (Exception e) {
			try {
				conn.rollback(); // ������ �ѹ�
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return result;
	} // end deleteComment	
	
	
	// ��� ����
	public boolean updateComment(Comment comment) 
	{
		boolean result = false;
		
		try{
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false); // �ڵ� Ŀ���� false�� �Ѵ�.
			
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE BOARDCOMM SET");
			sql.append(" COMM = ?");
			sql.append(" WHERE BCIDX = ?");

			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, comment.getComm());
			pstmt.setLong(2, comment.getBcidx());

			int flag = pstmt.executeUpdate();
			if(flag > 0){
				result = true;
				conn.commit(); // �Ϸ�� Ŀ��
			}
			
		} catch (Exception e) {
			try {
				conn.rollback(); // ������ �ѹ�
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
	} // end updateComment	
}
