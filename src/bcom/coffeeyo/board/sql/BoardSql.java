package bcom.coffeeyo.board.sql;

//이 클래스는 질의명령을 보관..필요시 알려주는 기능을 가진 클래스

public class BoardSql {

	//질의명령 코드 가독성있게 부여하기 위해 코드값에 이름을 부여하자
	//전체 게시물 목록 검색
	public static final int SELECT_BOARDLIST = 1;
	//총 원글게시물 수
	public static final int SELECT_TOTALCOUNT=2;
	//원글 등록
	public static final int INSERT_BOARD=3;
	//조회수 증가
	public static final int UPDATE_HIT=4;
	//원글 상세보기 질의
	public static final int SELECT_DETAIL=5;
	//댓글 상세보기 질의
	public static final int SELECT_REPLY=6;
	//원글 수정 질의
	public static final int UPDATE_BOARD=7;
	//원글 삭제 질의
	public static final int DELETE_BOARD=8;
	//제목으로 게시물 목록 검색
	public static final int SELECT_BOARDLIST_S=9;
	//내용으로 게시물 목록 검색
	public static final int SELECT_BOARDLIST_C=10;
	//제목+내용으로 게시물 목록 검색
	public static final int SELECT_BOARDLIST_SC=11;
	//닉네임으로 게시물 목록 검색
	public static final int SELECT_BOARDLIST_N=12;
	//닉네임으로 게시물 목록 검색
	public static final int SELECT_BOARDLIST_ID=13;
	
	
	//질의명령을 달라고 요구하면 질의명령을 주는 함수
	public static String getSQL(int code) {
		
		StringBuffer buff = new StringBuffer();
	
		switch(code) {
		case 13://SELECT_BOARDLIST_ID
			buff.append("SELECT bidx, pname, nick, subject, readcnt, likecnt, b.createdt ");
			buff.append("    FROM board b, product p, member m ");
			buff.append("    WHERE b.pidx=p.pidx ");
			buff.append("        AND m.userid=b.userid ");
			buff.append("        AND b.userid=? ");
			buff.append("            ORDER BY notiyn ASC, bidx DESC ");
			break;
		case 12://SELECT_BOARDLIST_N
			buff.append("SELECT bidx, pname, nick, subject, readcnt, likecnt, b.createdt ");
			buff.append("    FROM board b, product p, member m ");
			buff.append("    WHERE b.pidx=p.pidx ");
			buff.append("        AND m.userid=b.userid ");
			buff.append("        AND nick like ? ");
			buff.append("            ORDER BY notiyn ASC, bidx DESC ");
			break;
		case 11://SELECT_BOARDLIST_SC
			buff.append("SELECT bidx, pname, nick, subject, readcnt, likecnt, b.createdt ");
			buff.append("    FROM board b, product p, member m ");
			buff.append("    WHERE b.pidx=p.pidx ");
			buff.append("        AND m.userid=b.userid ");
			buff.append("        AND (subject like ? OR b.comm like ?) ");
			buff.append("            ORDER BY notiyn ASC, bidx DESC ");
			break;
		case 10://SELECT_BOARDLIST_C
			buff.append("SELECT bidx, pname, nick, subject, readcnt, likecnt, b.createdt ");
			buff.append("    FROM board b, product p, member m ");
			buff.append("    WHERE b.pidx=p.pidx ");
			buff.append("        AND m.userid=b.userid ");
			buff.append("        AND b.comm like ? ");
			buff.append("            ORDER BY notiyn ASC, bidx DESC ");
			break;
		case 9://SELECT_BOARDLIST_S
			buff.append("SELECT bidx, pname, nick, subject, readcnt, likecnt, b.createdt ");
			buff.append("    FROM board b, product p, member m ");
			buff.append("    WHERE b.pidx=p.pidx ");
			buff.append("        AND m.userid=b.userid ");
			buff.append("        AND subject like ? ");
			buff.append("            ORDER BY notiyn ASC, bidx DESC ");
			break;
			
		case 8:
			buff.append("UPDATE ");
			buff.append("	ReBoard ");
			buff.append("SET ");
			buff.append("	rb_IsShow='N' ");
			buff.append("WHERE ");
			buff.append("	rb_No=? ");
			buff.append("and ");
			buff.append("	rb_Password=?");
			System.out.println(buff.toString());
			break;
			
		case 7:
			buff.append("UPDATE ");
			buff.append("	ReBoard ");
			buff.append("SET ");
			buff.append("	rb_Title=?, ");
			buff.append("	rb_Content=?, ");
			buff.append("	rb_Password=? ");
			buff.append("WHERE ");
			buff.append("	rb_No=? ");
			buff.append("and ");
			buff.append("	rb_IsShow='Y' ");
			break;
			
		case 6:
			buff.append("SELECT ");
			buff.append("	r_No		AS no, ");
			buff.append("	r_OriNo		AS oriNo, ");
			buff.append("	r_Writer	AS writer, ");
			buff.append("	r_Title		AS title, ");
			buff.append("	r_Content	AS body, ");
			buff.append("	r_Date		AS wday, ");
			buff.append("	r_Good		AS good, ");
			buff.append("	r_Bad		AS bad ");
			buff.append("FROM ");
			buff.append("	Reply ");
			buff.append("WHERE ");
			buff.append("	r_OriNo=? ");
			buff.append("AND ");
			buff.append("	r_IsShow='Y' ");
			buff.append("ORDER BY ");
			buff.append("	r_No DESC ");
			break;
			
		case 5://SELECT_DETAIL
			buff.append("SELECT b.userid, cname, pname, nick, subject, b.comm, likecnt, readcnt, b.createdt ");
			buff.append("    FROM board b, category c, product p, member m ");
			buff.append("    WHERE b.cidx=c.cidx ");
			buff.append("        AND ");
			buff.append("        b.pidx=p.pidx ");
			buff.append("        AND ");
			buff.append("        b.userid=m.userid ");
			buff.append("        AND ");
			buff.append("        b.bidx=? ");
			break;

		case 4:
			buff.append("UPDATE ");
			buff.append("	ReBoard ");
			buff.append("SET ");
			buff.append("	rb_Hit=rb_Hit+1 ");
			buff.append("WHERE ");
			buff.append("	rb_No=?");
			break;
			
		case 3:	//INSERT_BOARD
			buff.append("INSERT INTO BOARD");
			buff.append("(BIDX, USERID, CIDX, PIDX, SUBJECT, COMM, IMAGE,");
			buff.append(" NOTIYN, LIKECNT, READCNT, CREATEDT) ");
			buff.append(" VALUES(");
			buff.append("?,?,?,?,?,?,?,");
			buff.append("?,0,0,sysdate");
			buff.append(" )");
			
			break;
			
		case 2:	//SELECT_TOTALCOUNT
			buff.append("SELECT ");
			buff.append("	COUNT(*) AS cnt ");
			buff.append("FROM ");
			buff.append("	Board ");
			break;
			
		case 1:  //SELECT_BOARDLIST
			buff.append("select ");
			buff.append("	    bidx, ");
			buff.append("    pname, ");
			buff.append("    nick, ");
			buff.append("    subject, ");
			buff.append("	    readcnt, ");
			buff.append("    likecnt, ");
			buff.append("    board.createdt ");
			buff.append("from ");
			buff.append("    board,product,member ");
			buff.append("where ");
			buff.append("    board.pidx=product.pidx ");
			buff.append("and ");
			buff.append("    board.userid=member.userid ");
			buff.append("order by notiyn asc, bidx desc");
			break;
		}
		return 	buff.toString();
	}
}
