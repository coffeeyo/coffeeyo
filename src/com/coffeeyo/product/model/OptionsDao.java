package com.coffeeyo.product.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.coffeeyo.common.util.DBConnection;

public class OptionsDao {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private static OptionsDao instance;
	
	private OptionsDao() {}
	
	public static OptionsDao getInstance() {
		if(instance==null)
			instance = new OptionsDao();
		
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
			sql.append("SELECT OPTIONCD_SEQ.NEXTVAL FROM DUAL");
			
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
}
