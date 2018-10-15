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
	
	//조회수 증가 처리
	public void updateBoardReadCnt(int oriNo) {
		String sql="UPDATE board SET readcnt=readcnt+1 WHERE bidx=?";
		PreparedStatement stmt=db.getSTMT(con, sql);
		try {
			stmt.setInt(1, oriNo);
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("조회수 증가 처리중 에러발생="+e);
		}
		db.close(stmt);
	}
	//게시판의 좋아요 개수 처리
	public void updateBoardLikeCount(int status, int oriNo) {
		String sql="";
		if (status==1) {//좋아요 감소
			sql="UPDATE board SET likecnt=likecnt-1 WHERE bidx=? ";
		}
		else {//좋아요 증가
			sql="UPDATE board SET likecnt=likecnt+1 WHERE bidx=? ";
		}
		PreparedStatement stmt=db.getSTMT(con, sql);
		ResultSet rs=null;
		try {
			stmt.setInt(1, oriNo);
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("게시글 좋아요 증가 처리 도중 에러발생="+e);
		}
		db.close(stmt);
	}
	//좋아요 개수 구하기
	public int selectLikeCount(int oriNo) {
		
		int likeCount=0;
		String sql=BoardSql.getSQL(BoardSql.SELECT_TOTALLIKECHECK);
		PreparedStatement stmt=db.getSTMT(con, sql);
		ResultSet rs=null;
		try {
			stmt.setInt(1, oriNo);
			rs=stmt.executeQuery();
			rs.next();
			likeCount=rs.getInt("CNT");
			db.close(rs);
		} catch (SQLException e) {
			System.out.println("좋아요 개수 구하는 도중 에러발생="+e);
		}
		db.close(stmt);
		return likeCount;
	}
	//좋아요 체크 상태값 변경
	public void updateLikeCheck(int status, int likeidx) {
		String sql="";
		if(status==1) {//(추천함1->추천안함0)
			sql="UPDATE likecheck SET status=0 WHERE likeidx=?";
		}
		else {//(추천안함0->추천함1)
			sql="UPDATE likecheck SET status=1 WHERE likeidx=?";
		}
		PreparedStatement stmt=db.getSTMT(con, sql);
		try {
			stmt.setInt(1, likeidx);
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("좋아요 체크 상태값 변경 중 에러발생="+e);
		}
		db.close(stmt);
	}
	//좋아요 체크 테이블 검색
	public BoardVO selectLikeCheck(int oriNo, String userid) {
		String sql=BoardSql.getSQL(BoardSql.SELECT_LIKECHECK);
		PreparedStatement stmt=db.getSTMT(con, sql);
		BoardVO vo=new BoardVO();
		try {
			stmt.setInt(1, oriNo);
			stmt.setString(2, userid);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				vo.setLikeidx(rs.getInt("LIKEIDX"));
				vo.setBidx(rs.getInt("BIDX"));
				vo.setUserid(rs.getString("USERID"));
				vo.setStatus(rs.getInt("STATUS"));	
			}
			db.close(rs);		
		} catch (Exception e) {
			System.out.println("좋아요 체크 테이블 검색 중 오류발생="+e);
		}
		db.close(stmt);
		return vo;
	}
	//좋아요 체크 테이블에 추가
	public void insertLikeCheck(int seq, int oriNo, String userid) {
		String sql=BoardSql.getSQL(BoardSql.INSERT_LIKECHECK);
		PreparedStatement stmt=db.getSTMT(con, sql);
		try {
			stmt.setInt(1, seq);
			stmt.setInt(2, oriNo);
			stmt.setString(3, userid);
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("좋아요 체크 테이블 추가 중 오류발생="+e);
		}
		db.close(stmt);
	}
	//댓글 삭제하기
	public void deleteComment(int reNo, int oriNo) {
		String sql=BoardSql.getSQL(BoardSql.DELETE_COMMENT);
		PreparedStatement stmt=db.getSTMT(con, sql);
		try {
			stmt.setInt(1, reNo);
			stmt.setInt(2, oriNo);
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("댓글 삭제처리 중 오류발생");
		}
		db.close(stmt);
	}
	//댓글 수정하기
	public void updateComment(int reNo, int oriNo, String comm) {
		String sql=BoardSql.getSQL(BoardSql.UPDATE_COMMENT);
		PreparedStatement stmt=db.getSTMT(con, sql);
		try {
			stmt.setString(1, comm);
			stmt.setInt(2, reNo);
			stmt.setInt(3, oriNo);
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("댓글 수정 중 오류발생");
		}
		db.close(stmt);
	}
	
	//게시글 삭제하기
	public void deleteBoard(int oriNo) {
		String sql=BoardSql.getSQL(BoardSql.DELETE_BOARD);
		PreparedStatement stmt=db.getSTMT(con, sql);
		try {
			stmt.setInt(1, oriNo);
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("게시글 status=2(안보이게) 처리중 에러발생="+e);
		}
		db.close(stmt);
	}
	
	//게시글 수정하기
	public void updateBoard(int test, int oriNo, BoardVO vo) {
		String sql="";
		PreparedStatement stmt=null;
		if(test==1) {//파일선택시
			sql=BoardSql.getSQL(BoardSql.UPDATE_BOARD);	
			try {
				stmt=db.getSTMT(con, sql);
				stmt.setInt(1, vo.getCidx());
				stmt.setInt(2, vo.getPidx());
				stmt.setString(3, vo.getSubject());
				stmt.setString(4, vo.getComm());
				stmt.setString(5, vo.getImage());
				stmt.setString(6, vo.getNotiyn());
				stmt.setInt(7, oriNo);
				stmt.executeUpdate();
			} catch (Exception e) {
				System.out.println("파일선택한 게시글 수정중 오류발생="+e);
			}
		}
		else {//파일선택안할시
			sql=BoardSql.getSQL(BoardSql.UPDATE_BOARDN);
			try {
				stmt=db.getSTMT(con, sql);
				stmt.setInt(1, vo.getCidx());
				stmt.setInt(2, vo.getPidx());
				stmt.setString(3, vo.getSubject());
				stmt.setString(4, vo.getComm());
				stmt.setString(5, vo.getNotiyn());
				stmt.setInt(6, oriNo);
				stmt.executeUpdate();
			} catch (Exception e) {
				System.out.println("파일선택안한 게시글 수정중 오류발생="+e);
			}	
		}
		db.close(stmt);
	}
	
	//댓글 불러오기
	public ArrayList selectComment(int oriNo) {
		String sql=BoardSql.getSQL(BoardSql.SELECT_COMMENT);
		PreparedStatement stmt=db.getSTMT(con, sql);
		ArrayList list=new ArrayList();
		try {
			stmt.setInt(1, oriNo);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()) {
				BoardVO vo=new BoardVO();
				vo.setBidx(rs.getInt("BIDX"));
				vo.setBcidx(rs.getInt("BCIDX"));
				vo.setUserid(rs.getString("USERID"));
				vo.setNick(rs.getString("NICK"));
				vo.setComm(rs.getString("COMM"));
				vo.setCreatedt(rs.getDate("CREATEDT"));
				list.add(vo);
			}
			db.close(rs);
		} catch (Exception e) {
			System.out.println("댓글 불러오는 중 에러발생="+e);
		}
		db.close(stmt);
		return list;
	}
	//댓글 작성하기
	public void insertComment(int bcidx, int oriNo, String userid, String comm) {
		String sql=BoardSql.getSQL(BoardSql.INSERT_COMMENT);
		PreparedStatement stmt=db.getSTMT(con, sql);
		try {
			stmt.setInt(1, bcidx);
			stmt.setInt(2, oriNo);
			stmt.setString(3, userid);
			stmt.setString(4, comm);
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("댓글 작성중 에러발생="+e);
		}
		db.close(stmt);
	}
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
			vo.setNotiyn(rs.getString("NOTIYN"));
			vo.setUserid(rs.getString("USERID"));
			vo.setPidx(rs.getInt("PIDX"));
			vo.setCidx(rs.getInt("CIDX"));
			vo.setCname(rs.getString("CNAME"));
			vo.setPname(rs.getString("PNAME"));
			vo.setNick(rs.getString("NICK"));
			vo.setSubject(rs.getString("SUBJECT"));
			vo.setComm(rs.getString("COMM"));
			vo.setImage(rs.getString("IMAGE"));
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
				stmt.setString(1, "%"+condition+"%");
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
				stmt.setString(1, "%"+condition+"%");
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
				stmt.setString(1, "%"+condition+"%");
				stmt.setString(2, "%"+condition+"%");
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
				stmt.setString(1, "%"+condition+"%");
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
				vo.setNotiyn(rs.getString("NOTIYN"));
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
				sql="select count(*) as cnt from BOARD where SUBJECT like ? and status=1";
				pstmt=db.getSTMT(con, sql);
				pstmt.setString(1, '%'+condition+'%');
			}
			//내용으로 검색한 글의 개수 구하기
			else if(opt.equals("1")) {
				sql="select count(*) as cnt from BOARD where COMM like ? and status=1";
				pstmt=db.getSTMT(con, sql);
				pstmt.setString(1, '%'+condition+'%');
			}
			//제목+내용으로 검색한 글의 개수 구하기
			else if(opt.equals("2")) {
				sql="select count(*) as cnt from BOARD where (SUBJECT like ? or COMM like ?)  and status=1";
				pstmt=db.getSTMT(con, sql);
				pstmt.setString(1, '%'+condition+'%');
				pstmt.setString(2, '%'+condition+'%');
			}
			//닉네임으로 검색한 글의 개수 구하기
			else if(opt.equals("3")) {
				sql="select count(*) as cnt from BOARD b, member m where b.userid=m.userid and m.nick like ? and b.status=1";
				pstmt=db.getSTMT(con, sql);
				pstmt.setString(1, '%'+condition+'%');
			}
			//내글보기로 검색한 글의 개수 구하기
			else if(opt.equals("4")) {
				sql="select count(*) as cnt from BOARD where userid=? and status=1";
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
	
	//분류정보 가져오기
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
	
	//댓글번호 시퀀스 값 가져오기
	public int getCSeq() {
		int result=1;
		String sql="SELECT BOARDCOMM_SEQ.NEXTVAL FROM DUAL";
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
