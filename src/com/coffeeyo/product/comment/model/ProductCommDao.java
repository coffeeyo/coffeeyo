package com.coffeeyo.product.comment.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.coffeeyo.common.util.DBConnection;

public class ProductCommDao {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private static ProductCommDao instance;
	
	private ProductCommDao() {}
	
	public static ProductCommDao getInstance() {
		if(instance==null)
			instance = new ProductCommDao();
			
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
			sql.append("SELECT PRODCOMM_SEQ.NEXTVAL FROM DUAL");
			
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			// ���� ����
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
	
	// ��� ���
	public boolean insertComment(ProductComm comment)
	{
		boolean result = false;
		
		try {
			conn = DBConnection.getConnection();

			// �ڵ� Ŀ���� false�� �Ѵ�.
			conn.setAutoCommit(false);
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO BOARDCOMM ");
			sql.append(" (BCIDX, BIDX, USERID, COMM, PCPOINT, CREATEDT)");
			sql.append(" VALUES(?,?,?,?,?,sysdate)");
	
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setLong(1, comment.getPcidx());
			pstmt.setLong(2, comment.getPidx());
			pstmt.setString(3, comment.getUserid());
			pstmt.setString(4, comment.getComm());
			pstmt.setLong(5, comment.getPcpoint());
			
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
			} finally {
				DBConnection.close(pstmt);
				DBConnection.close(conn);
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
	public ArrayList<ProductComm> getCommentList(HashMap<String, Object> listOpt)
	{
		ArrayList<ProductComm> list = new ArrayList<ProductComm>();
		
		int prodNum = (Integer)listOpt.get("pidx");
		int start = (Integer)listOpt.get("start");
		
		try {
			conn = DBConnection.getConnection();
			
			/* 
			 * ����� ������ ó���� �ϰ�ʹٸ� �� ������ ����ϸ� �ȴ�.
			 */
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM");
			sql.append(" (SELECT  ROWNUM AS rnum, data.* FROM ");
			sql.append("	(SELECT PCIDX, PIDX,");
			sql.append("			c.USERID, COMM, PCPOINT, ");
			sql.append("			c.CREATEDT, m.nick ");
			sql.append("	FROM PRODCOMM c");
			sql.append("	LEFT JOIN MEMBER m");
			sql.append("	ON c.USERID=m.USERID");
			sql.append("	WHERE PIDX = ? AND c.STATUS=1) ");
			sql.append(" data) ");
			sql.append("WHERE rnum >= ? and rnum <= ?");
			
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setInt(1, prodNum);
			pstmt.setInt(2, start);
			pstmt.setInt(3, start+9);
			
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				ProductComm comment = new ProductComm();
				comment.setPcidx(rs.getLong("PCIDX"));
				comment.setPidx(rs.getInt("PIDX"));
				comment.setUserid(rs.getString("USERID"));
				comment.setComm(rs.getString("COMM"));
				comment.setPcpoint(rs.getLong("PCPOINT"));
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
	public ProductComm getComment(int comment_num)
	{
		ProductComm comment = null;
		
		try {
			conn = DBConnection.getConnection();
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT PCIDX, PIDX, USERID, COMM, CREATEDT   FROM PRODCOMM WHERE PCIDX = ?");
			
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setInt(1, comment_num);
			
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				comment = new ProductComm();
				comment.setPcidx(rs.getLong("PCIDX"));
				comment.setPidx(rs.getLong("PIDX"));
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
			sql.append("DELETE FROM PRODCOMM");
			sql.append(" WHERE PCIDX = ?");
						
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
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
			} finally {
				DBConnection.close(pstmt);
				DBConnection.close(conn);
			}
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return result;
	} // end deleteComment	
	
	
	// ��� ����
	public boolean updateComment(ProductComm comment) 
	{
		boolean result = false;
		
		try{
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false); // �ڵ� Ŀ���� false�� �Ѵ�.
			
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE PRODCOMM SET");
			sql.append(" COMM = ?");
			sql.append(" WHERE PCIDX = ?");

			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setString(1, comment.getComm());
			pstmt.setLong(2, comment.getPcidx());

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
			} finally {
				DBConnection.close(pstmt);
				DBConnection.close(conn);
			}
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return result;
	} // end updateComment	
	
	public int getProdCommentListCount(long pidx) {
		int result = 0;
		
		try {
			conn = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer();
			
			sql.append("select count(*) from PRODCOMM where PIDX = ? AND STATUS=1");
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setLong(1, pidx);
			
			sql.delete(0, sql.toString().length());
			
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
	}
	
	public float getProdCommentPointAvg(long pidx) {
		float result = 0.0F;
		
		try {
			conn = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer();
			
			sql.append("select (sum(pcpoint)/count(*)) from PRODCOMM where PIDX = ? AND STATUS=1");
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setLong(1, pidx);
			
			sql.delete(0, sql.toString().length());
			
			rs = pstmt.executeQuery();
			if(rs.next())
				result = rs.getFloat(1);
			DBConnection.close(rs);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		
		return result;
	}
}
