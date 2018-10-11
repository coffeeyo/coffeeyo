package com.coffeeyo.member.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.coffeeyo.member.model.Member;

import bcom.coffeeyo.board.util.PageUtil;

import com.coffeeyo.common.util.DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class MemberDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private static MemberDAO instance;
	
	private MemberDAO(){}
	public static MemberDAO getInstance(){
		if(instance == null)
			instance = new MemberDAO();
		return instance;
	}
	
	public int loginCheck(String id, String pw) 
	{

		String dbPw = ""; // db���� ���� ��й�ȣ�� ���� ����
		int check = -1;

		try {
			// ���� - ���� �Էµ� ���̵�� DB���� ��й�ȣ�� ��ȸ�Ѵ�.
			StringBuffer query = new StringBuffer();
			query.append("SELECT PASSWD, STATUS FROM MEMBER WHERE USERID=?");

			conn = DBConnection.getConnection();
			//pstmt = conn.prepareStatement(query.toString());
			pstmt = DBConnection.getPstmt(conn, query.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) // �Է��� ���̵� �ش��ϴ� ��� �������
			{
				dbPw = rs.getString("passwd"); // ����� ������ �ִ´�.

				if (dbPw.equals(pw)) 
					check = 1; // �Ѱܹ��� ����� ������ ��� ��. ������ ��������
				else 				 
					check = 0; // DB�� ��й�ȣ�� �Է¹��� ��й�ȣ �ٸ�, ��������
				
				if(rs.getInt("STATUS") == 2)
					check = 2;
				
			} else {
				check = -1; // �ش� ���̵� ���� ���
			}
			DBConnection.close(rs);
			return check;

		} catch (Exception e) {
			e.printStackTrace();
			return check;
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
	}

	public void insertMember(Member mb) {
		try {

			conn = DBConnection.getConnection();
			
			String sql = "insert into member (" +
								" userid, passwd , uname, nick, hp, ulevel, status, createdt" +
								") " +
								" values(?,?,?,?,?,1,1,SYSDATE)";
			//pstmt = conn.prepareStatement(sql);
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setString(1, mb.getUserid());
			pstmt.setString(2, mb.getPasswd());
			pstmt.setString(3, mb.getUname());
			pstmt.setString(4, mb.getNick());
			pstmt.setString(5, mb.getHp());

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
	}
	
	public int idCheck(String id) {
		int check = -1;
		try {

			conn = DBConnection.getConnection();

			String sql = "select nvl(count(userid),0) cnt from member where userid=?";
			//pstmt = conn.prepareStatement(sql);
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				
				if (rs.getInt("cnt") == 0) {
					check = 1;
				} else {
					check = 0;
				}
			} else {
				check = 1;
			}
			DBConnection.close(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return check;
	}
	
	public int nickCheck(String nick) {
		int check = -1;
		try {

			conn = DBConnection.getConnection();

			String sql = "select nvl(count(nick),0) cnt from member where nick=?";
			//pstmt = conn.prepareStatement(sql);
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setString(1, nick);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				
				if (rs.getInt("cnt") == 0) {
					check = 1;
				} else {
					check = 0;
				}
			} else {
				check = 1;
			}
			DBConnection.close(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return check;
	}

	public Member getMember(String id) {
		Member mb = null;
		try {
			conn = DBConnection.getConnection();

			String sql = "select * from member where userid=?";
			//pstmt = conn.prepareStatement(sql);
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				mb = new Member();
				mb.setUserid(rs.getString("userid"));
				mb.setPasswd(rs.getString("passwd"));
				mb.setUname(rs.getString("uname"));
				mb.setNick(rs.getString("nick"));
				mb.setHp(rs.getString("hp"));
				mb.setGender(rs.getInt("gender"));
				mb.setBirthday(rs.getString("birthday"));
				mb.setJob(rs.getInt("job"));
				mb.setUlevel(rs.getInt("ulevel"));
				mb.setStatus(rs.getInt("status"));
				mb.setCreatedt(rs.getDate("createdt"));
				mb.setUpdatedt(rs.getDate("updatedt"));
			}
			DBConnection.close(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return mb;
	}

	public int updateMember(Member mb) {
		int check = -1;
		try {
			conn = DBConnection.getConnection();
			// �ڵ� Ŀ���� false�� �Ѵ�.
			conn.setAutoCommit(false);
			
			String sql = "select passwd, nick from member where userid=?";
			//pstmt = conn.prepareStatement(sql);
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setString(1, mb.getUserid());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				if (!mb.getPasswd().equals("")) {
					if (mb.getPasswd().equals(rs.getString("passwd"))) {
						StringBuilder sb = new StringBuilder();
						sb.append("update member set ");
						sb.append("hp=?, ");
						sb.append("gender=?, ");
						sb.append("birthday=?, ");
						sb.append("job=?, ");
						sb.append("updatedt=SYSDATE ");
						sb.append("where userid=?");
						
						//pstmt = conn.prepareStatement(sb.toString());
						pstmt = DBConnection.getPstmt(conn, sb.toString());
						pstmt.setString(1, 	mb.getHp());
						pstmt.setInt(2, 	mb.getGender());
						pstmt.setString(3, 	mb.getBirthday());
						pstmt.setInt(4, 	mb.getJob());
						pstmt.setString(5, 	mb.getUserid());

						int flag = pstmt.executeUpdate();
						
						if(flag > 0){
							check = 1;		// ����
							conn.commit(); 	// �Ϸ�� Ŀ��
						}
					}
					else
					{
						StringBuilder sb = new StringBuilder();
						sb.append("update member set ");
						sb.append("passwd=?, ");
						sb.append("nick=?, ");
						sb.append("hp=?, ");
						sb.append("gender=?, ");
						sb.append("birthday=?, ");
						sb.append("job=?, ");
						sb.append("updatedt=SYSDATE ");
						sb.append("where userid=?");
						
						//pstmt = conn.prepareStatement(sb.toString());
						pstmt = DBConnection.getPstmt(conn, sb.toString());
						pstmt.setString(1, 	mb.getPasswd());
						pstmt.setString(2, 	mb.getNick());
						pstmt.setString(3, 	mb.getHp());
						pstmt.setInt(4, 	mb.getGender());
						pstmt.setString(5, 	mb.getBirthday());
						pstmt.setInt(6, 	mb.getJob());
						pstmt.setString(7, 	mb.getUserid());

						int flag = pstmt.executeUpdate();
						
						if(flag > 0){
							check = 1;		// ����
							conn.commit(); 	// �Ϸ�� Ŀ��
						}
					}
				} else {
					StringBuilder sb = new StringBuilder();
					sb.append("update member set ");
					sb.append("hp=?, ");
					sb.append("gender=?, ");
					sb.append("birthday=?, ");
					sb.append("job=?, ");
					sb.append("updatedt=SYSDATE ");
					sb.append("where userid=?");
					
					//pstmt = conn.prepareStatement(sb.toString());
					pstmt = DBConnection.getPstmt(conn, sb.toString());
					pstmt.setString(1, 	mb.getHp());
					pstmt.setInt(2, 	mb.getGender());
					pstmt.setString(3, 	mb.getBirthday());
					pstmt.setInt(4, 	mb.getJob());
					pstmt.setString(5, 	mb.getUserid());

					int flag = pstmt.executeUpdate();
					
					if(flag > 0){
						check = 1;		// ����
						conn.commit(); 	// �Ϸ�� Ŀ��
					}
				}
			}
			DBConnection.close(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return check;
	}

	public int leaveMember(String id) {
		int check = 9;

		try {
			conn = DBConnection.getConnection();
			
			String sql = "update member set status=2 where userid=?";
			//pstmt = conn.prepareStatement(sql);
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setString(1, id);
			int flag = pstmt.executeUpdate();
			
			if(flag > 0){
				check = 1;		// ����
				conn.commit(); 	// �Ϸ�� Ŀ��
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}

		return check;
	}
	
	public int restoreMember(String id) {
		int check = 9;

		try {
			conn = DBConnection.getConnection();
			
			String sql = "update member set status=1 where userid=?";
			//pstmt = conn.prepareStatement(sql);
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setString(1, id);
			int flag = pstmt.executeUpdate();
			
			if(flag > 0){
				check = 1;		// ����
				conn.commit(); 	// �Ϸ�� Ŀ��
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}

		return check;
	}
	
	// ���� ������ �������� �޼���
	public int getMemberListCount(HashMap<String, Object> listOpt)
	{
		int result = 0;
		String opt = (String)listOpt.get("opt");
		String condition = (String)listOpt.get("condition");
		
		try {
			conn = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer();
			
			if(opt == null)	// ��ü���� ����
			{
				sql.append("select count(*) from MEMBER");
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				
				// StringBuffer�� ����.
				sql.delete(0, sql.toString().length());
			}
			else if(opt.equals("0")) // �������� �˻��� ȸ����
			{
				sql.append("select count(*) from MEMBER where UNAME like ?");
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setString(1, '%'+condition+'%');
				
				sql.delete(0, sql.toString().length());
			}
			else if(opt.equals("1")) // ���̵�� �˻��� ȸ����
			{
				sql.append("select count(*) from MEMBER where USERID like ?");
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
	} // end getMemberListCount

	public ArrayList<Member> getMemberList(int nowPage, PageUtil pinfo, HashMap<String, Object> listOpt) {
		ArrayList<Member> memberList = new ArrayList<Member>();

		String opt = (String)listOpt.get("opt");
		String condition = (String)listOpt.get("condition");
		
		try {
			conn = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer();
						
			if(opt == null) {
				//sql.append("SELECT * FROM");
				//sql.append(" (SELECT  ROWNUM AS rnum, data.* FROM ");
				//sql.append("	(SELECT * ");
				sql.append("SELECT * ");
				sql.append("	FROM MEMBER ");
				sql.append("	ORDER BY createdt desc");   
				//sql.append("	ORDER BY createdt desc)");              
				//sql.append(" data) ");
				//sql.append("WHERE rnum >= ? and rnum <= ?");
				
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				//pstmt.setInt(1, start);
				//pstmt.setInt(2, start+9);
				
				// StringBuffer�� ����.
				sql.delete(0, sql.toString().length());
			}
			else if(opt.equals("0")) // �������� �˻�
			{
				//sql.append("SELECT * FROM");
				//sql.append(" (SELECT  ROWNUM AS rnum, data.* FROM ");
				//sql.append("	(SELECT * ");
				sql.append("SELECT * ");
				sql.append("	FROM MEMBER ");
				sql.append(" 	WHERE UNAME like ?");
				sql.append("	ORDER BY createdt desc");    
				//sql.append("	ORDER BY createdt desc)");              
				//sql.append(" data) ");
				//sql.append("WHERE rnum >= ? and rnum <= ?");
				
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setString(1, "%"+condition+"%");
				//pstmt.setInt(2, start);
				//pstmt.setInt(3, start+9);
				
				sql.delete(0, sql.toString().length());
			}
			else if(opt.equals("1")) // ���̵�� �˻�
			{
				//sql.append("SELECT * FROM");
				//sql.append(" (SELECT  ROWNUM AS rnum, data.* FROM ");
				//sql.append("	(SELECT * ");
				sql.append("SELECT * ");
				sql.append("	FROM MEMBER ");
				sql.append(" 	WHERE USERID like ?");
				sql.append("	ORDER BY createdt desc");    
				//sql.append("	ORDER BY createdt desc)");              
				//sql.append(" data) ");
				//sql.append("WHERE rnum >= ? and rnum <= ?");
				
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setString(1, "%"+condition+"%");
				//pstmt.setInt(2, start);
				//pstmt.setInt(3, start+9);
				
				sql.delete(0, sql.toString().length());
			}

			rs = pstmt.executeQuery();
			int skip=((pinfo.getNowPage()-1)*pinfo.getListCount());
			for(int i = 0; i < skip; i++) {
				rs.next();
				//�����ͺ��̽� �۾� �����͸� �ʿ���� �����Ϳ��� ������
			}
			
			for(int i = 0; i < pinfo.getListCount() && rs.next(); i++) {
				Member mb = new Member();
				mb.setUserid(rs.getString("userid"));
				mb.setUname(rs.getString("uname"));
				mb.setNick(rs.getString("nick"));
				mb.setHp(rs.getString("hp"));
				mb.setGender(rs.getInt("gender"));
				mb.setBirthday(rs.getString("birthday"));
				mb.setUlevel(rs.getInt("ulevel"));
				mb.setStatus(rs.getInt("status"));
				mb.setCreatedt(rs.getDate("createdt"));
				
				memberList.add(mb);

			}
			DBConnection.close(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return memberList;
	}
	
	public int updateMemberProc(Member mb) {
		int check = -1;
		try {
			conn = DBConnection.getConnection();
			// �ڵ� Ŀ���� false�� �Ѵ�.
			conn.setAutoCommit(false);
			
			String sql = "select passwd, nick from member where userid=?";
			//pstmt = conn.prepareStatement(sql);
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setString(1, mb.getUserid());
			rs = pstmt.executeQuery();

			if (rs.next()) {
				if (!mb.getPasswd().equals("")) {
					if (mb.getPasswd().equals(rs.getString("passwd"))) {
						StringBuilder sb = new StringBuilder();
						sb.append("update member set ");
						sb.append("hp=?, ");
						sb.append("gender=?, ");
						sb.append("birthday=?, ");
						sb.append("job=?, ");
						sb.append("ulevel=?, ");
						sb.append("status=?, ");
						sb.append("updatedt=SYSDATE ");
						sb.append("where userid=?");
						
						//pstmt = conn.prepareStatement(sb.toString());
						pstmt = DBConnection.getPstmt(conn, sb.toString());
						pstmt.setString(1, 	mb.getHp());
						pstmt.setInt(2, 	mb.getGender());
						pstmt.setString(3, 	mb.getBirthday());
						pstmt.setInt(4, 	mb.getJob());
						pstmt.setInt(5, 	mb.getUlevel());
						pstmt.setInt(6, 	mb.getStatus());
						pstmt.setString(7, 	mb.getUserid());

						int flag = pstmt.executeUpdate();
						
						if(flag > 0){
							check = 1;		// ����
							conn.commit(); 	// �Ϸ�� Ŀ��
						}
					}
					else
					{
						StringBuilder sb = new StringBuilder();
						sb.append("update member set ");
						sb.append("passwd=?, ");
						sb.append("hp=?, ");
						sb.append("gender=?, ");
						sb.append("birthday=?, ");
						sb.append("job=?, ");
						sb.append("ulevel=?, ");
						sb.append("status=?, ");
						sb.append("updatedt=SYSDATE ");
						sb.append("where userid=?");
						
						//pstmt = conn.prepareStatement(sb.toString());
						pstmt = DBConnection.getPstmt(conn, sb.toString());
						pstmt.setString(1, 	mb.getPasswd());
						pstmt.setString(2, 	mb.getNick());
						pstmt.setString(3, 	mb.getHp());
						pstmt.setInt(4, 	mb.getGender());
						pstmt.setString(5, 	mb.getBirthday());
						pstmt.setInt(6, 	mb.getJob());
						pstmt.setInt(7, 	mb.getUlevel());
						pstmt.setInt(8, 	mb.getStatus());
						pstmt.setString(9, 	mb.getUserid());

						int flag = pstmt.executeUpdate();
						
						if(flag > 0){
							check = 1;		// ����
							conn.commit(); 	// �Ϸ�� Ŀ��
						}
					}
				} else {
					check = 0;
				}
			}
			DBConnection.close(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return check;
	}
}