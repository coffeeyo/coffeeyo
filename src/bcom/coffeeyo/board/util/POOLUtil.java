package bcom.coffeeyo.board.util;

//�Ϲ����� JDBCó������
import java.sql.*;

import javax.naming.Context;
//Ŀ�ؼ�Ǯ�� ����
import javax.naming.InitialContext;
import javax.sql.DataSource;

/* 
 * JSP������ ���� �������� Ŀ�ؼ�Ǯ�� �̿��ؼ�
 * DB�����۾��� �� ��쿡 ����Ͽ�
 * �����ͺ��̽��� ���õ� ����� ������ ��ƿ��Ƽ Ŭ����
 */
public class POOLUtil {

	DataSource pool;	//Ŀ�ؼ� Ǯ�� ����� ��������
	
	//�������� �� Ŭ������ new��Ű�� ����
	//ȯ�漳���� �������� Ŀ�ؼ�Ǯ�� �˾Ƴ����� ����
	public POOLUtil() {
		try {
			//ȯ�漳�� ������ ������ ���� Ŭ������ �غ��Ѵ�
			//����� Ŭ���� : InitialContext
			InitialContext initContext = new InitialContext();
			//�� Ŭ������ �̿��ؼ� ȯ�漳�� ���Ͽ� ��ϵ� Ŀ�ؼ� Ǯ�� �˾Ƴ���
			//�Լ� : lookup
			Context poolName  = (Context)initContext.lookup("java:/comp/env");
			
			//Ŀ�ؼ� Ǯ�� ����
			pool = (DataSource)poolName.lookup("jdbc/orcl");
		}
		catch(Exception e) {
			System.out.println("Ŀ�ؼ� Ǯ ���� ���� = "+e);
		}
	}//�������� ��
	
	//�������� Ŀ�ؼ��� �޶�� �ϸ� Ŀ�ؼ��� �����ϴ� �Լ�
	public Connection getCON() {
		Connection con=null;
		try {
			con = pool.getConnection();
		} catch (Exception e) {
			System.out.println("Ŀ�ؼǿ���="+e);
		}
		return con;
	}
	//�������� Statement�� �ʿ��ϸ� ��� ������ִ� �Լ��� ������
		public Statement getSTMT(Connection con) {
			Statement stmt=null;
			try {
				stmt=con.createStatement();
			} catch (Exception e) {
				System.out.println("Statement ��������="+e);
			}
			return stmt;
		}
		
		//�������� PreparedStatement�� �ʿ��ϸ� ��� ������ִ� �Լ��� ������
		public PreparedStatement getSTMT(Connection con, String sql) {
			//�Ű������� ����
			//PreparedStatement�� ����� ���ؼ��� Connection�� �ʿ��ϸ�
			//PreparedStatement�� �̸� ���� ����� �˷�����ϹǷ� ���Ǹ���� �ʿ�
			PreparedStatement stmt=null;
			try {
				stmt=con.prepareStatement(sql);
			} catch (Exception e) {
				System.out.println("PreparedStatement ��������="+e);
			}
			return stmt;
		}
		
		//�� �۾��� �����ϰ� �ϱ� ���ؼ� �ݴ� ��ɵ� �����ϰڴ�
		public void close(Object o) {
			//�Ű������� ����
			//�� �Լ��� �ݾƾ��ϴ� ��ü�� �޾ƿ��� ���ؼ� �Ű������� �غ�
			//�ݾƾ��� ��ü�� �������̹Ƿ� �̵� ��θ� �ѹ��� ���� �� �ֵ���
			//�ֻ��� Ŭ������ �̿��ؼ� �޴´�
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
				System.out.println("�ݱ⿡��"+e);
			}
		}	
}//Ŭ������ ��
