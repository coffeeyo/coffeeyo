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
	public static final int SELECT_COMMENT=6;
	//���� ���� ����(���ϼ����Ұ��)
	public static final int UPDATE_BOARD=7;
	//���� ���� ����(���ϼ��þ��Ұ��)
	public static final int UPDATE_BOARDN=15;
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
	//��� ����ϱ� ����
	public static final int INSERT_COMMENT=14;
	//��� �����ϱ� ����
	public static final int UPDATE_COMMENT=16;
	//��� �����ϱ� ����
	public static final int DELETE_COMMENT=17;	
	
	//���Ǹ���� �޶�� �䱸�ϸ� ���Ǹ���� �ִ� �Լ�
	public static String getSQL(int code) {
		
		StringBuffer buff = new StringBuffer();
	
		switch(code) {
		case 17://DELETE_COMMENT
			buff.append("");
			buff.append("");
			buff.append("");
			buff.append("");
			buff.append("");
			buff.append("");
			break;
		case 16://UPDATE_COMMENT
			buff.append("UPDATE ");
			buff.append("    boardcomm ");
			buff.append("SET ");
			buff.append("    comm=?, ");
			buff.append("    updatedt=SYSDATE ");
			buff.append("WHERE ");
			buff.append("    bcidx=? ");
			buff.append("AND ");
			buff.append("    bidx=? ");
			break;
		case 15://UPDATE_BOARDN
			buff.append("UPDATE ");
			buff.append("    board ");
			buff.append("SET ");
			buff.append("    cidx=?, ");
			buff.append("    pidx=?, ");
			buff.append("    subject=?, ");
			buff.append("    comm=?, ");
			buff.append("    updatedt=SYSDATE ");
			buff.append("WHERE ");
			buff.append("    bidx=?");
			break;
		case 14://INSERT_COMMENT
			buff.append("INSERT INTO boardcomm ");
			buff.append("    (bcidx, bidx, userid, comm, createdt, status) ");
			buff.append("VALUES ");
			buff.append("    (?,?,?,?,SYSDATE,1)");
			break;
		case 13://SELECT_BOARDLIST_ID
			buff.append("SELECT bidx, pname, nick, subject, readcnt, likecnt, b.createdt ");
			buff.append("    FROM board b, product p, member m ");
			buff.append("    WHERE b.pidx=p.pidx ");
			buff.append("        AND m.userid=b.userid ");
			buff.append("        AND b.userid=? ");
			buff.append("        AND b.status=1 ");
			buff.append("            ORDER BY notiyn ASC, bidx DESC ");
			break;
		case 12://SELECT_BOARDLIST_N
			buff.append("SELECT bidx, pname, nick, subject, readcnt, likecnt, b.createdt ");
			buff.append("    FROM board b, product p, member m ");
			buff.append("    WHERE b.pidx=p.pidx ");
			buff.append("        AND m.userid=b.userid ");
			buff.append("        AND nick like ? ");
			buff.append("        AND b.status=1 ");
			buff.append("            ORDER BY notiyn ASC, bidx DESC ");
			break;
		case 11://SELECT_BOARDLIST_SC
			buff.append("SELECT bidx, pname, nick, subject, readcnt, likecnt, b.createdt ");
			buff.append("    FROM board b, product p, member m ");
			buff.append("    WHERE b.pidx=p.pidx ");
			buff.append("        AND m.userid=b.userid ");
			buff.append("        AND (subject like ? OR b.comm like ?) ");
			buff.append("        AND b.status=1 ");
			buff.append("            ORDER BY notiyn ASC, bidx DESC ");
			break;
		case 10://SELECT_BOARDLIST_C
			buff.append("SELECT bidx, pname, nick, subject, readcnt, likecnt, b.createdt ");
			buff.append("    FROM board b, product p, member m ");
			buff.append("    WHERE b.pidx=p.pidx ");
			buff.append("        AND m.userid=b.userid ");
			buff.append("        AND b.comm like ? ");
			buff.append("        AND b.status=1 ");
			buff.append("            ORDER BY notiyn ASC, bidx DESC ");
			break;
		case 9://SELECT_BOARDLIST_S
			buff.append("SELECT bidx, pname, nick, subject, readcnt, likecnt, b.createdt ");
			buff.append("    FROM board b, product p, member m ");
			buff.append("    WHERE b.pidx=p.pidx ");
			buff.append("        AND m.userid=b.userid ");
			buff.append("        AND subject like ? ");
			buff.append("        AND b.status=1 ");
			buff.append("            ORDER BY notiyn ASC, bidx DESC ");
			break;
			
		case 8://DELETE_BOARD
			buff.append("UPDATE  ");
			buff.append("    board ");
			buff.append("SET ");
			buff.append("    status=2");
			buff.append("WHERE  ");
			buff.append("    bidx=? ");
			break;
			
		case 7://UPDATE_BOARD
			buff.append("UPDATE ");
			buff.append("    board ");
			buff.append("SET ");
			buff.append("    cidx=?, ");
			buff.append("    pidx=?, ");
			buff.append("    subject=?, ");
			buff.append("    comm=?, ");
			buff.append("    image=? ,");
			buff.append("    updatedt=SYSDATE ");
			buff.append("WHERE ");
			buff.append("    bidx=?");
			break;
			
		case 6://SELECT_COMMENT
			buff.append("SELECT ");
			buff.append("    bcidx, bc.bidx, bc.userid, nick, comm, bc.createdt, bc.updatedt, bc.status ");
			buff.append("FROM ");
			buff.append("    boardcomm bc, member m ");
			buff.append("WHERE ");
			buff.append("    bc.userid=m.userid ");
			buff.append("AND ");
			buff.append("    bidx=? ");
			buff.append("AND ");
			buff.append("    bc.status=1 ");
			buff.append("ORDER BY ");
			buff.append("    bc.bcidx DESC ");			
			break;
			
		case 5://SELECT_DETAIL
			buff.append("SELECT b.userid, p.pidx, c.cidx, cname, pname, nick, subject, b.comm, b.image, likecnt, readcnt, b.createdt ");
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
			buff.append("WHERE status=1");
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
			buff.append("and ");
			buff.append("    board.status=1 ");
			buff.append("order by notiyn asc, bidx desc");
			break;
		}
		return 	buff.toString();
	}
}
