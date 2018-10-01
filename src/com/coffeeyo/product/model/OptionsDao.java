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
	
	// �������� �����´�.
	public int getSeq()
	{
		int result = 1;
		
		try {
			conn = DBConnection.getConnection();
			
			// ������ ���� �����´�. (DUAL : ������ ���� ������������ �ӽ� ���̺�)
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT OPTIONCD_SEQ.NEXTVAL FROM DUAL");
			
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
}
