package com.coffeeyo.common.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 * 커넥션을 얻어오는 클래스 - JNDI
 */
public class DBConnection 
{
	/**
	 * 커넥션 풀로 부터 커넥션 객체를 가져와 리턴한다.
	 * @return Connection
	 * @throws SQLException
	 * @throws NamingException
	 * @throws ClassNotFoundException
	 */
	public static Connection getConnection() throws SQLException, NamingException, 
	ClassNotFoundException{
			Context initCtx = new InitialContext();
			
			//initCtx의 lookup메서드를 이용해서 "java:comp/env" 에 해당하는 객체를 찾아서 evnCtx에 삽입
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			
			//envCtx의 lookup메서드를 이용해서 "jdbc/orcl"에 해당하는 객체를 찾아서 ds에 삽입
			DataSource ds = (DataSource) envCtx.lookup("jdbc/orcl");
			
			//getConnection메서드를 이용해서 커넥션 풀로 부터 커넥션 객체를 얻어내어 conn변수에 저장
			Connection conn = ds.getConnection();
			return conn;
	}
	
	//Statement를 제공하는 함수
	public static Statement getStmt(Connection con) {
		Statement stmt = null;
		try {
			stmt = con.createStatement();
		} catch (Exception e) {
			System.out.println("Statement 생성 에러="+e);
			//e.printStackTrace();
		}
		
		return stmt;
	}
	
	//PreparedStatement를 제공하는 함수
	public static PreparedStatement getPstmt(Connection con, String sql) {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
		} catch (Exception e) {
			System.out.println("PreparedStatement 생성 에러="+e);
			//e.printStackTrace();
		}
		
		return pstmt;
	}
	
	public static void close(Object o) {
		try {
			if(o instanceof Connection) {
				((Connection)o).close();
			}
			else if(o instanceof Statement) {
				((Statement)o).close();
			}
			else if(o instanceof PreparedStatement) {
				((PreparedStatement)o).close();
			}
			else if(o instanceof ResultSet) {
				((ResultSet)o).close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}	