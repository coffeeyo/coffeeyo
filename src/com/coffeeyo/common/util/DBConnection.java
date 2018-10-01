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
 * Ŀ�ؼ��� ������ Ŭ���� - JNDI
 */
public class DBConnection 
{
	/**
	 * Ŀ�ؼ� Ǯ�� ���� Ŀ�ؼ� ��ü�� ������ �����Ѵ�.
	 * @return Connection
	 * @throws SQLException
	 * @throws NamingException
	 * @throws ClassNotFoundException
	 */
	public static Connection getConnection() throws SQLException, NamingException, 
	ClassNotFoundException{
			Context initCtx = new InitialContext();
			
			//initCtx�� lookup�޼��带 �̿��ؼ� "java:comp/env" �� �ش��ϴ� ��ü�� ã�Ƽ� evnCtx�� ����
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			
			//envCtx�� lookup�޼��带 �̿��ؼ� "jdbc/orcl"�� �ش��ϴ� ��ü�� ã�Ƽ� ds�� ����
			DataSource ds = (DataSource) envCtx.lookup("jdbc/orcl");
			
			//getConnection�޼��带 �̿��ؼ� Ŀ�ؼ� Ǯ�� ���� Ŀ�ؼ� ��ü�� ���� conn������ ����
			Connection conn = ds.getConnection();
			return conn;
	}
	
	//Statement�� �����ϴ� �Լ�
	public static Statement getStmt(Connection con) {
		Statement stmt = null;
		try {
			stmt = con.createStatement();
		} catch (Exception e) {
			System.out.println("Statement ���� ����="+e);
			//e.printStackTrace();
		}
		
		return stmt;
	}
	
	//PreparedStatement�� �����ϴ� �Լ�
	public static PreparedStatement getPstmt(Connection con, String sql) {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
		} catch (Exception e) {
			System.out.println("PreparedStatement ���� ����="+e);
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