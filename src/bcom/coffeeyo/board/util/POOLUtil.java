package bcom.coffeeyo.board.util;

//일반적인 JDBC처리내용
import java.sql.*;

import javax.naming.Context;
//커넥션풀과 관련
import javax.naming.InitialContext;
import javax.sql.DataSource;

/* 
 * JSP문서나 서블릿 문서에서 커넥션풀을 이용해서
 * DB연동작업을 할 경우에 대비하여
 * 데이터베이스에 관련된 기능을 제공할 유틸리티 클래스
 */
public class POOLUtil {

	DataSource pool;	//커넥션 풀을 기억할 전역변수
	
	//누군가가 이 클래스를 new시키는 순간
	//환경설정에 만들어놓은 커넥션풀을 알아놓도록 하자
	public POOLUtil() {
		try {
			//환경설정 파일의 내용을 읽을 클래스를 준비한다
			//사용할 클래스 : InitialContext
			InitialContext initContext = new InitialContext();
			//이 클래스를 이용해서 환경설정 파일에 등록된 커넥션 풀을 알아낸다
			//함수 : lookup
			Context poolName  = (Context)initContext.lookup("java:/comp/env");
			
			//커넥션 풀을 만들어냄
			pool = (DataSource)poolName.lookup("jdbc/orcl");
		}
		catch(Exception e) {
			System.out.println("커넥션 풀 생성 에러 = "+e);
		}
	}//생성자의 끝
	
	//누군가가 커넥션을 달라고 하면 커넥션을 제공하는 함수
	public Connection getCON() {
		Connection con=null;
		try {
			con = pool.getConnection();
		} catch (Exception e) {
			System.out.println("커넥션에러="+e);
		}
		return con;
	}
	//누군가가 Statement가 필요하면 대신 만들어주는 함수를 만들자
		public Statement getSTMT(Connection con) {
			Statement stmt=null;
			try {
				stmt=con.createStatement();
			} catch (Exception e) {
				System.out.println("Statement 생성에러="+e);
			}
			return stmt;
		}
		
		//누군가가 PreparedStatement가 필요하면 대신 만들어주는 함수를 만들자
		public PreparedStatement getSTMT(Connection con, String sql) {
			//매개변수의 역할
			//PreparedStatement를 만들기 위해서는 Connection이 필요하며
			//PreparedStatement는 미리 질의 명령을 알려줘야하므로 질의명령이 필요
			PreparedStatement stmt=null;
			try {
				stmt=con.prepareStatement(sql);
			} catch (Exception e) {
				System.out.println("PreparedStatement 생성에러="+e);
			}
			return stmt;
		}
		
		//이 작업도 간편하게 하기 위해서 닫는 기능도 제공하겠다
		public void close(Object o) {
			//매개변수의 역할
			//이 함수가 닫아야하는 객체를 받아오기 위해서 매개변수를 준비
			//닫아야할 객체가 여러개이므로 이들 모두를 한번에 받을 수 있도록
			//최상위 클래스를 이용해서 받는다
			try {
				if(o instanceof Connection) {
					Connection temp=(Connection)o;
					temp.close();
				}
				else if(o instanceof Statement) {
					Statement temp=(Statement)o;
					temp.close();
				}
				else if(o instanceof PreparedStatement) {
					PreparedStatement temp=(PreparedStatement)o;
					temp.close();
				}
				else if(o instanceof ResultSet) {
					ResultSet temp=(ResultSet)o;
					temp.close();
				}			
			} catch (Exception e) {
				System.out.println("닫기에러"+e);
			}
		}	
}//클래스의 끝
