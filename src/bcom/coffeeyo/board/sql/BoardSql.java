package bcom.coffeeyo.board.sql;

//�� Ŭ������ ���Ǹ���� ����..�ʿ�� �˷��ִ� ����� ���� Ŭ����

public class BoardSql {

	//���Ǹ�� �ڵ� �������ְ� �ο��ϱ� ���� �ڵ尪�� �̸��� �ο�����
	//��ü �Խù� ��� �˻�
	public static final int SELECT_BOARDLIST = 1;
	//�� ���۰Խù� ��
	public static final int SELECT_TOTALCOUNT=2;
	//���� ���
	public static final int INSERT_BOARD=3;
	//��ȸ�� ����
	public static final int UPDATE_HIT=4;
	//���� �󼼺��� ����
	public static final int SELECT_DETAIL=5;
	//��� �󼼺��� ����
	public static final int SELECT_REPLY=6;
	//���� ���� ����
	public static final int UPDATE_BOARD=7;
	//���� ���� ����
	public static final int DELETE_BOARD=8;
	//�������� �Խù� ��� �˻�
	public static final int SELECT_BOARDLIST_S=9;
	//�������� �Խù� ��� �˻�
	public static final int SELECT_BOARDLIST_C=10;
	//����+�������� �Խù� ��� �˻�
	public static final int SELECT_BOARDLIST_SC=11;
	//�г������� �Խù� ��� �˻�
	public static final int SELECT_BOARDLIST_N=12;
	//�г������� �Խù� ��� �˻�
	public static final int SELECT_BOARDLIST_ID=13;
	
	
	//���Ǹ���� �޶�� �䱸�ϸ� ���Ǹ���� �ִ� �Լ�
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
