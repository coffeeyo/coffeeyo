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
	
	//함수 추가
	//게시물 상세보기
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
			System.out.println("게시물 상세보기 중 에러발생="+e);
		}
		db.close(rs);
		db.close(stmt);
		return vo;
	}
	//게시판 목록보기
	public ArrayList selectList(int nowPage, PageUtil pinfo, HashMap<String,Object> listOpt) {
		String opt = (String)listOpt.get("opt");
		String condition = (String)listOpt.get("condition");
		String sql="";
		ArrayList list=new ArrayList();
		PreparedStatement stmt=null;
		ResultSet rs=null;
		System.out.println("opt="+opt+"conditon="+condition);
		System.out.println("nowPage="+nowPage+"pinfo.getNowPage()="+pinfo.getNowPage());

		//글목록 전체를 보여줄 때
		if(opt==null) {
			sql=BoardSql.getSQL(BoardSql.SELECT_BOARDLIST);
			System.out.println("전체sql="+sql);
			stmt=db.getSTMT(con, sql);
		}
		//제목으로 검색
		else if(opt.equals("0")) {
			sql=BoardSql.getSQL(BoardSql.SELECT_BOARDLIST_S);
			System.out.println("제목sql="+sql);
			stmt=db.getSTMT(con, sql);
			try {
				stmt.setString(1, condition);
			} catch (Exception e) {
				System.out.println("제목기준 글목록 조회 중 에러발생="+e);
			}
		}
		//내용으로 검색
		else if(opt.equals("1")) {
			sql=BoardSql.getSQL(BoardSql.SELECT_BOARDLIST_C);
			System.out.println("내용sql="+sql);
			stmt=db.getSTMT(con, sql);
			try {
				stmt.setString(1, condition);
			} catch (Exception e) {
				System.out.println("내용기준 글목록 조회 중 에러발생="+e);
			}
		}
		//제목+내용으로 검색
		else if(opt.equals("2")) {
			sql=BoardSql.getSQL(BoardSql.SELECT_BOARDLIST_SC);
			System.out.println("제목+내용sql="+sql);
			stmt=db.getSTMT(con, sql);
			try {
				stmt.setString(1, condition);
				stmt.setString(2, condition);
			} catch (Exception e) {
				System.out.println("제목 및 내용기준 글목록 조회 중 에러발생="+e);
			}
		}
		//닉네임으로 검색
		else if(opt.equals("3")) {
			sql=BoardSql.getSQL(BoardSql.SELECT_BOARDLIST_N);
			System.out.println("닉네임sql="+sql);
			stmt=db.getSTMT(con, sql);
			try {
				stmt.setString(1, condition);
			} catch (Exception e) {
				System.out.println("닉네임기준 글목록 조회 중 에러발생="+e);
			}
		}
		//내글보기로 검색
		else if(opt.equals("4")) {
			sql=BoardSql.getSQL(BoardSql.SELECT_BOARDLIST_ID);
			System.out.println("내글보기sql="+sql);
			stmt=db.getSTMT(con, sql);
			try {
				stmt.setString(1, condition);
			} catch (Exception e) {
				System.out.println("내글보기로 글목록 조회 중 에러발생="+e);
			}
		}
		try {
			rs=stmt.executeQuery();
			int skip=((pinfo.getNowPage()-1)*pinfo.getListCount());
			for(int i=0;i<skip;i++) {
				rs.next();
				//데이터베이스 작업 포인터를 필요없는 데이터에서 내린다
			}
			//우리는 이중에서 필요한 개수만 꺼내서 뷰에게 전달해야 한다
			//해당 페이지 이전에 보여줄 데이터는 버린다
			//현재페이지에서 보여줄 데이터만 꺼내서 사용한다
			for(int i=0;i<pinfo.getListCount() && rs.next() ;i++) {

				//글번호, 상품명, 닉네임, 제목, 조회수, 추천수, 작성일
				BoardVO vo=new BoardVO();
				vo.setBidx(rs.getInt("BIDX"));
				vo.setPname(rs.getString("PNAME"));
				vo.setNick(rs.getString("NICK"));
				vo.setSubject(rs.getString("SUBJECT"));
				vo.setReadcnt(rs.getInt("READCNT"));
				vo.setLikecnt(rs.getInt("LIKECNT"));
				vo.setCreatedt(rs.getDate("CREATEDT"));

				//그 VO를 list에 넣어준다
				list.add(vo);
			}
		}
		catch(Exception e) {
			System.out.println("executeQuery 실행중 오류발생="+e);
		}
		db.close(rs);
		db.close(stmt);
		return list;
	}
	
	//데이터개수 구하기
	public int pageCal(HashMap<String,Object> listOpt) {
		int totalCount=0;
		String sql="";
		String opt=(String)listOpt.get("opt");
		String condition=(String)listOpt.get("condition");
		PreparedStatement pstmt=null;
		ResultSet rs=null;
	
		try {
			//전체 글의 개수 구하기
			if(opt==null) {
				sql=BoardSql.getSQL(BoardSql.SELECT_TOTALCOUNT);
				System.out.println("총 데이터개수 구하는 쿼리="+sql);
				pstmt=db.getSTMT(con, sql);
				rs=pstmt.executeQuery(sql);
			}
			//제목으로 검색한 글의 개수 구하기
			else if(opt.equals("0")) {
				sql="select count(*) as cnt from BOARD where SUBJECT like ?";
				pstmt=db.getSTMT(con, sql);
				pstmt.setString(1, '%'+condition+'%');
			}
			//내용으로 검색한 글의 개수 구하기
			else if(opt.equals("1")) {
				sql="select count(*) as cnt from BOARD where COMM like ?";
				pstmt=db.getSTMT(con, sql);
				pstmt.setString(1, '%'+condition+'%');
			}
			//제목+내용으로 검색한 글의 개수 구하기
			else if(opt.equals("2")) {
				sql="select count(*) as cnt from BOARD where SUBJECT like ? or COMM like ?";
				pstmt=db.getSTMT(con, sql);
				pstmt.setString(1, '%'+condition+'%');
				pstmt.setString(2, '%'+condition+'%');
			}
			//닉네임으로 검색한 글의 개수 구하기
			else if(opt.equals("3")) {
				sql="select count(*) as cnt from BOARD b, member m where b.userid=m.userid and m.nick like ?";
				pstmt=db.getSTMT(con, sql);
				pstmt.setString(1, '%'+condition+'%');
			}
			//내글보기로 검색한 글의 개수 구하기
			else if(opt.equals("4")) {
				sql="select count(*) as cnt from BOARD where userid=?";
				pstmt=db.getSTMT(con, sql);
				pstmt.setString(1, condition);
			}
			rs=pstmt.executeQuery();
			rs.next();
			totalCount=rs.getInt("CNT");
		}catch(Exception e) {
			System.out.println("총 데이터 개수 구하다 발생한 에러="+e);
		}
		db.close(rs);
		db.close(pstmt);
		return totalCount;
	}
	
	//분류정보 가져오기(사용자)
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
			System.out.println("분류정보 가져오는 중 발생한 에러="+e);
		}
		db.close(stmt);
		return list;	
	}
	
	//분류정보 가져오기(관리자)
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
			System.out.println("분류정보 가져오는 중 발생한 에러="+e);
		}
		db.close(stmt);
		return list;	
	}
	
	//콜드브루 상품 가져오기
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
			System.out.println("상품정보 가져오는 중 발생한 에러="+e);
		}
		db.close(stmt);
		return list;	
	}
	//에스프레소 상품 가져오기
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
			System.out.println("상품정보 가져오는 중 발생한 에러="+e);
		}
		db.close(stmt);
		return list;	
	}
	//프라푸치노 상품 가져오기
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
			System.out.println("상품정보 가져오는 중 발생한 에러="+e);
		}
		db.close(stmt);
		return list;	
	}
	
	//게시판 시퀀스 값 가져오기
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
			System.out.println("시퀀스 값 가져오는 중 발생한 에러="+e);
		}
		db.close(stmt);
		return result;
	}
	
	//좋아요체크 시퀀스 값 가져오기
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
			System.out.println("시퀀스 값 가져오는 중 발생한 에러="+e);
		}
		db.close(stmt);
		return result;
	}
	
	//게시글 작성하기
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
			System.out.println("게시글 insert 실행 중 발생한 에러="+e);
		}
		db.close(stmt);
	}
	//커넥션 종료
	public void close() {
		db.close(con);
	}
}
