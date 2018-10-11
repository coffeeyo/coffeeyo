package bcom.coffeeyo.board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import bcom.coffeeyo.board.sql.BoardSql;
import bcom.coffeeyo.board.util.POOLUtil;
import bcom.coffeeyo.board.util.PageUtil;

public class BoardDAO 
{
	private POOLUtil db;
	private Connection con;
	
	public BoardDAO() {
		db=new POOLUtil();
		con=db.getCON();
	}
	
	//�Լ� �߰�
	//�Խù� �󼼺���
	public BoardVO selectDetail(int oriNo) {
		String sql=BoardSql.getSQL(BoardSql.SELECT_DETAIL);
		PreparedStatement stmt=db.getSTMT(con, sql);
		ResultSet rs=null;
		BoardVO vo=new BoardVO();
		try {
			stmt.setInt(1, oriNo);
			rs=stmt.executeQuery();
			rs.next();
			vo.setUserid(rs.getString("USERID"));
			vo.setCname(rs.getString("CNAME"));
			vo.setPname(rs.getString("PNAME"));
			vo.setNick(rs.getString("NICK"));
			vo.setSubject(rs.getString("SUBJECT"));
			vo.setComm(rs.getString("COMM"));
			vo.setLikecnt(rs.getInt("LIKECNT"));
			vo.setReadcnt(rs.getInt("READCNT"));
			vo.setCreatedt(rs.getDate("CREATEDT"));
		} catch (Exception e) {
			System.out.println("�Խù� �󼼺��� �� �����߻�="+e);
		}
		db.close(rs);
		db.close(stmt);
		return vo;
	}
	//�Խ��� ��Ϻ���
	public ArrayList selectList(int nowPage, PageUtil pinfo, HashMap<String,Object> listOpt) {
		String opt = (String)listOpt.get("opt");
		String condition = (String)listOpt.get("condition");
		String sql="";
		ArrayList list=new ArrayList();
		PreparedStatement stmt=null;
		ResultSet rs=null;
		System.out.println("opt="+opt+"conditon="+condition);
		System.out.println("nowPage="+nowPage+"pinfo.getNowPage()="+pinfo.getNowPage());

		//�۸�� ��ü�� ������ ��
		if(opt==null) {
			sql=BoardSql.getSQL(BoardSql.SELECT_BOARDLIST);
			System.out.println("��üsql="+sql);
			stmt=db.getSTMT(con, sql);
		}
		//�������� �˻�
		else if(opt.equals("0")) {
			sql=BoardSql.getSQL(BoardSql.SELECT_BOARDLIST_S);
			System.out.println("����sql="+sql);
			stmt=db.getSTMT(con, sql);
			try {
				stmt.setString(1, condition);
			} catch (Exception e) {
				System.out.println("������� �۸�� ��ȸ �� �����߻�="+e);
			}
		}
		//�������� �˻�
		else if(opt.equals("1")) {
			sql=BoardSql.getSQL(BoardSql.SELECT_BOARDLIST_C);
			System.out.println("����sql="+sql);
			stmt=db.getSTMT(con, sql);
			try {
				stmt.setString(1, condition);
			} catch (Exception e) {
				System.out.println("������� �۸�� ��ȸ �� �����߻�="+e);
			}
		}
		//����+�������� �˻�
		else if(opt.equals("2")) {
			sql=BoardSql.getSQL(BoardSql.SELECT_BOARDLIST_SC);
			System.out.println("����+����sql="+sql);
			stmt=db.getSTMT(con, sql);
			try {
				stmt.setString(1, condition);
				stmt.setString(2, condition);
			} catch (Exception e) {
				System.out.println("���� �� ������� �۸�� ��ȸ �� �����߻�="+e);
			}
		}
		//�г������� �˻�
		else if(opt.equals("3")) {
			sql=BoardSql.getSQL(BoardSql.SELECT_BOARDLIST_N);
			System.out.println("�г���sql="+sql);
			stmt=db.getSTMT(con, sql);
			try {
				stmt.setString(1, condition);
			} catch (Exception e) {
				System.out.println("�г��ӱ��� �۸�� ��ȸ �� �����߻�="+e);
			}
		}
		//���ۺ���� �˻�
		else if(opt.equals("4")) {
			sql=BoardSql.getSQL(BoardSql.SELECT_BOARDLIST_ID);
			System.out.println("���ۺ���sql="+sql);
			stmt=db.getSTMT(con, sql);
			try {
				stmt.setString(1, condition);
			} catch (Exception e) {
				System.out.println("���ۺ���� �۸�� ��ȸ �� �����߻�="+e);
			}
		}
		try {
			rs=stmt.executeQuery();
			int skip=((pinfo.getNowPage()-1)*pinfo.getListCount());
			for(int i=0;i<skip;i++) {
				rs.next();
				//�����ͺ��̽� �۾� �����͸� �ʿ���� �����Ϳ��� ������
			}
			//�츮�� ���߿��� �ʿ��� ������ ������ �信�� �����ؾ� �Ѵ�
			//�ش� ������ ������ ������ �����ʹ� ������
			//�������������� ������ �����͸� ������ ����Ѵ�
			for(int i=0;i<pinfo.getListCount() && rs.next() ;i++) {

				//�۹�ȣ, ��ǰ��, �г���, ����, ��ȸ��, ��õ��, �ۼ���
				BoardVO vo=new BoardVO();
				vo.setBidx(rs.getInt("BIDX"));
				vo.setPname(rs.getString("PNAME"));
				vo.setNick(rs.getString("NICK"));
				vo.setSubject(rs.getString("SUBJECT"));
				vo.setReadcnt(rs.getInt("READCNT"));
				vo.setLikecnt(rs.getInt("LIKECNT"));
				vo.setCreatedt(rs.getDate("CREATEDT"));

				//�� VO�� list�� �־��ش�
				list.add(vo);
			}
		}
		catch(Exception e) {
			System.out.println("executeQuery ������ �����߻�="+e);
		}
		db.close(rs);
		db.close(stmt);
		return list;
	}
	
	//�����Ͱ��� ���ϱ�
	public int pageCal(HashMap<String,Object> listOpt) {
		int totalCount=0;
		String sql="";
		String opt=(String)listOpt.get("opt");
		String condition=(String)listOpt.get("condition");
		PreparedStatement pstmt=null;
		ResultSet rs=null;
	
		try {
			//��ü ���� ���� ���ϱ�
			if(opt==null) {
				sql=BoardSql.getSQL(BoardSql.SELECT_TOTALCOUNT);
				System.out.println("�� �����Ͱ��� ���ϴ� ����="+sql);
				pstmt=db.getSTMT(con, sql);
				rs=pstmt.executeQuery(sql);
			}
			//�������� �˻��� ���� ���� ���ϱ�
			else if(opt.equals("0")) {
				sql="select count(*) as cnt from BOARD where SUBJECT like ?";
				pstmt=db.getSTMT(con, sql);
				pstmt.setString(1, '%'+condition+'%');
			}
			//�������� �˻��� ���� ���� ���ϱ�
			else if(opt.equals("1")) {
				sql="select count(*) as cnt from BOARD where COMM like ?";
				pstmt=db.getSTMT(con, sql);
				pstmt.setString(1, '%'+condition+'%');
			}
			//����+�������� �˻��� ���� ���� ���ϱ�
			else if(opt.equals("2")) {
				sql="select count(*) as cnt from BOARD where SUBJECT like ? or COMM like ?";
				pstmt=db.getSTMT(con, sql);
				pstmt.setString(1, '%'+condition+'%');
				pstmt.setString(2, '%'+condition+'%');
			}
			//�г������� �˻��� ���� ���� ���ϱ�
			else if(opt.equals("3")) {
				sql="select count(*) as cnt from BOARD b, member m where b.userid=m.userid and m.nick like ?";
				pstmt=db.getSTMT(con, sql);
				pstmt.setString(1, '%'+condition+'%');
			}
			//���ۺ���� �˻��� ���� ���� ���ϱ�
			else if(opt.equals("4")) {
				sql="select count(*) as cnt from BOARD where userid=?";
				pstmt=db.getSTMT(con, sql);
				pstmt.setString(1, condition);
			}
			rs=pstmt.executeQuery();
			rs.next();
			totalCount=rs.getInt("CNT");
		}catch(Exception e) {
			System.out.println("�� ������ ���� ���ϴ� �߻��� ����="+e);
		}
		db.close(rs);
		db.close(pstmt);
		return totalCount;
	}
	
	//�з����� ��������(�����)
	public ArrayList selectCategory() {
		String sql="select cidx, cname from category where status=1 order by cname";
		Statement stmt=db.getSTMT(con);
		ArrayList list=new ArrayList();
		try {
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				BoardVO vo=new BoardVO();
				vo.setCidx(rs.getInt("CIDX"));
				vo.setCname(rs.getString("CNAME"));
				list.add(vo);
			}
			db.close(rs);
		} catch (Exception e) {
			System.out.println("�з����� �������� �� �߻��� ����="+e);
		}
		db.close(stmt);
		return list;	
	}
	
	//�з����� ��������(������)
	public ArrayList selectAdmCategory() {
		String sql="select cidx, cname from category order by cname";
		Statement stmt=db.getSTMT(con);
		ArrayList list=new ArrayList();
		try {
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				BoardVO vo=new BoardVO();
				vo.setCidx(rs.getInt("CIDX"));
				vo.setCname(rs.getString("CNAME"));
				list.add(vo);
			}
			db.close(rs);
		} catch (Exception e) {
			System.out.println("�з����� �������� �� �߻��� ����="+e);
		}
		db.close(stmt);
		return list;	
	}
	
	//�ݵ��� ��ǰ ��������
	public ArrayList selectColdbrew() {
		String sql="select cidx, pidx, pname from product where status=1 and cidx=1 order by pname";
		Statement stmt=db.getSTMT(con);
		ArrayList list=new ArrayList();
		try {
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				BoardVO vo=new BoardVO();
				vo.setCidx(rs.getInt("CIDX"));
				vo.setPidx(rs.getInt("PIDX"));
				vo.setPname(rs.getString("PNAME"));
				list.add(vo);
			}
			db.close(rs);
		} catch (Exception e) {
			System.out.println("��ǰ���� �������� �� �߻��� ����="+e);
		}
		db.close(stmt);
		return list;	
	}
	//���������� ��ǰ ��������
	public ArrayList selectEspresso() {
		String sql="select cidx, pidx, pname from product where status=1 and cidx=2 order by pname";
		Statement stmt=db.getSTMT(con);
		ArrayList list=new ArrayList();
		try {
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				BoardVO vo=new BoardVO();
				vo.setCidx(rs.getInt("CIDX"));
				vo.setPidx(rs.getInt("PIDX"));
				vo.setPname(rs.getString("PNAME"));
				list.add(vo);
			}
			db.close(rs);
		} catch (Exception e) {
			System.out.println("��ǰ���� �������� �� �߻��� ����="+e);
		}
		db.close(stmt);
		return list;	
	}
	//����Ǫġ�� ��ǰ ��������
	public ArrayList selectFrappuccino() {
		String sql="select cidx, pidx, pname from product where status=1 and cidx=3 order by pname";
		Statement stmt=db.getSTMT(con);
		ArrayList list=new ArrayList();
		try {
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				BoardVO vo=new BoardVO();
				vo.setCidx(rs.getInt("CIDX"));
				vo.setPidx(rs.getInt("PIDX"));
				vo.setPname(rs.getString("PNAME"));
				list.add(vo);
			}
			db.close(rs);
		} catch (Exception e) {
			System.out.println("��ǰ���� �������� �� �߻��� ����="+e);
		}
		db.close(stmt);
		return list;	
	}
	
	//�Խ��� ������ �� ��������
	public int getSeq() {
		int result=1;
		String sql="SELECT BOARD_SEQ.NEXTVAL FROM DUAL";
		Statement stmt=db.getSTMT(con);
		
		try {
			ResultSet rs=stmt.executeQuery(sql);
			rs.next();
			result=rs.getInt(1);
			db.close(rs);
		} catch (SQLException e) {
			System.out.println("������ �� �������� �� �߻��� ����="+e);
		}
		db.close(stmt);
		return result;
	}
	
	//���ƿ�üũ ������ �� ��������
	public int getLSeq() {
		int result=1;
		String sql="SELECT LIKECHECK_SEQ.NEXTVAL FROM DUAL";
		Statement stmt=db.getSTMT(con);
		try {
			ResultSet rs=stmt.executeQuery(sql);
			rs.next();
			result=rs.getInt(1);
			db.close(rs);
		} catch (SQLException e) {
			System.out.println("������ �� �������� �� �߻��� ����="+e);
		}
		db.close(stmt);
		return result;
	}
	
	//�Խñ� �ۼ��ϱ�
	public void insertBoard(BoardVO vo) {
		String sql=BoardSql.getSQL(BoardSql.INSERT_BOARD);
		PreparedStatement stmt=db.getSTMT(con, sql);
		try {
			stmt.setInt(1, vo.getBidx());
			stmt.setString(2, vo.getUserid());
			stmt.setInt(3, vo.getCidx());
			stmt.setInt(4, vo.getPidx());
			stmt.setString(5, vo.getSubject());
			stmt.setString(6, vo.getComm());
			stmt.setString(7, vo.getImage());
			stmt.setString(8, vo.getNotiyn());
			stmt.executeUpdate();
			System.out.println(sql);
		} catch (Exception e) {
			System.out.println("�Խñ� insert ���� �� �߻��� ����="+e);
		}
		db.close(stmt);
	}
	//Ŀ�ؼ� ����
	public void close() {
		db.close(con);
	}
}
