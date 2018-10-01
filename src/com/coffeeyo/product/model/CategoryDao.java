package com.coffeeyo.product.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.coffeeyo.common.util.DBConnection;

public class CategoryDao {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private static CategoryDao instance;
	
	private CategoryDao() {}
	
	public static CategoryDao getInstance() {
		if(instance==null)
			instance = new CategoryDao();
		
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
			sql.append("SELECT CATEGORY_SEQ.NEXTVAL FROM DUAL");
			
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
	
	public Category getCategory(long cidx) {
		Category cate = null;
		
		try {
			conn = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer();
			cate = new Category();
			
			sql.append("SELECT * ");
			sql.append("FROM CATEGORY ");
			sql.append("WHERE CIDX=? ");
			
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setLong(1, cidx);
			sql.delete(0, sql.toString().length());
			rs = pstmt.executeQuery();
			rs.next();
			
			cate.setCidx(rs.getLong("CIDX"));
			cate.setCname(rs.getString("CNAME"));
			DBConnection.close(rs);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return cate;
	}
	
	public ArrayList<Category> getAllCategory() {
		ArrayList<Category> cateList = null;
		
		try {
			conn = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer();
			cateList = new ArrayList<Category>();
				
			sql.append("SELECT * ");
			sql.append("FROM CATEGORY ");
			sql.append("WHERE STATUS=1 ");
			
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			sql.delete(0, sql.toString().length());
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				Category cate = new Category();
				cate.setCidx(rs.getLong("CIDX"));
				cate.setCname(rs.getString("CNAME"));
				cateList.add(cate);
			}
			//System.out.println("cateList="+cateList.size());
			DBConnection.close(rs);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return cateList;
	}
}
