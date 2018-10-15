package com.coffeeyo.order.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import com.coffeeyo.common.util.DBConnection;
import com.coffeeyo.member.model.Member;
import bcom.coffeeyo.board.util.POOLUtil;
import bcom.coffeeyo.board.util.PageUtil;

public class OrderDao {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private static OrderDao instance;
	
	// 여기 페이징 처리 때문에 수정--------------------
	private POOLUtil db;
	private OrderDao() {
		db=new POOLUtil();
		conn=db.getCON();
	}
	// 여기까지 페이징 --------------------------------
	public static OrderDao getInstance() {
		if(instance==null)
			instance = new OrderDao();
		
		return instance;
	}
	
	// 시퀀스를 가져온다.  이곳은 주문번호를 반환한다.
	public String getSeq()
	{
		String result = "";
		
		try {
			conn = DBConnection.getConnection();
			
			// 시퀀스 값을 가져온다. (DUAL : 시퀀스 값을 가져오기위한 임시 테이블)
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ORDERS_SEQ.NEXTVAL FROM DUAL");
			
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			// 쿼리 실행
			rs = pstmt.executeQuery();
			/*
			 * 	4. 추문처리(결제) 는 실제 PG 연동을 하지 않으며
	주문자 userid 기준으로 장바구니에 담긴 것중     BUYCHK = 'Y' 인것만
	ORDERS 테이블에는 함계 소계 ${sum}을 담은 price를 매개변수로 이용하고,
	ORDNO 는 OrderProcAction 에서 데이터를 삽입하기전에 OrderDao.getSeq() 의 값과 날짜조합으로 만들어낸다
	예) S+날짜_고유sequence번호
	=> S20180826_1
	
	-날짜생성
	Date now = new Date();
	SimpleDateFormat vans = new SimpleDateFormat("yyyyMMdd");
	String wdate = vans.format(now);
	
	-주문번호생성
	String order_key = OrderDao.getSeq();
	String trade_num = "S"+wdate+"_"+order_key;
			  */
			Date now = new Date();
			SimpleDateFormat vans = new SimpleDateFormat("yyyyMMdd");
			String wdate = vans.format(now);
			
			if(rs.next())	result = "O"+wdate+"_"+rs.getInt(1);
			DBConnection.close(rs);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return result;	
	} // end getSeq
	
	
	// 아래부터는 승환 작성
	
	// select order 결제 후 뿌리는 것
	public Order getOrderConfirm(Order order) {
		Order orderConfirm = null;
		try {
			conn = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer();
			orderConfirm = new Order();
			
			//	이것은  select 해서 orders와 orderItem과 product를 구한 것이다.
			if(order.getUserid() != null) {
				sql.append("SELECT * ");
				sql.append("FROM ORDERS ");
				sql.append("WHERE USERID=? ");
				sql.append("	and ORDNO=? ");
				sql.append(" ORDER BY ORDNO desc ");
				
				System.out.println("select order="+sql.toString());
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setString(1, order.getUserid());
				pstmt.setString(2, order.getOrderno());
				
				sql.delete(0, sql.toString().length());
			}			
			
			rs = pstmt.executeQuery();
			while(rs.next()) 
			{
				
				
				order.setOrderno(rs.getString("ORDNO"));
				order.setUserid(rs.getString("USERID"));
				order.setTotal(rs.getLong("TOTAL"));
				order.setPayyn(rs.getInt("PAYYN"));
				order.setReadytm(rs.getString("READYTM"));
				order.setOrddt(rs.getDate("ORDDT"));
				order.setStatus(rs.getInt("STATUS"));
				
			}
			DBConnection.close(rs);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return order;
	}	// end of select order 결제 후 뿌리는 것
	
	//ArrayList<Order> 
	public ArrayList<Order> getAllOrder(Order order, PageUtil pinfo, HashMap<String, Object> listOpt) {
		ArrayList<Order> orderList = null;
		String startDay = (String)listOpt.get("startDay");
		String endDay = (String)listOpt.get("endDay");
		try {
			conn = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer();
			orderList = new ArrayList<Order>();
			
			//	이것은  select 해서 orders와 orderItem과 product를 구한 것이다.
			if(order.getUserid() != null) {
				sql.append("SELECT");
				sql.append(" ORD.ORDNO as ORDERNO, ORD.USERID as USERID, TOTAL, PAYYN, ");
				sql.append(" ORD.READYTM as READYTM, ORD.ORDDT as ORDDT, ORD.STATUS as STATUS,  ");
				sql.append(" (SELECT PNAME FROM PRODUCT WHERE PIDX = ( ");
				sql.append(" 			SELECT PIDX FROM ORDERITEM WHERE ORDNO = ORD.ORDNO AND ROWNUM = 1  ");
				sql.append(" 		) ");
				sql.append(" ) PNAME, ");
				sql.append(" (SELECT IMAGE FROM PRODUCT WHERE PIDX = (  ");
				sql.append(" 			SELECT PIDX FROM ORDERITEM WHERE ORDNO = ORD.ORDNO AND ROWNUM = 1 ");
				sql.append(" 		) ");
				sql.append(" ) IMAGE,  ");
				sql.append(" (SELECT COUNT(*) cnt FROM ORDERITEM WHERE ORDNO = ORD.ORDNO) AMOUNT  ");
				sql.append("FROM ORDERS ORD ");
				sql.append("WHERE ORD.USERID=? ");
				if(startDay != null && endDay != null) {
					sql.append(" AND TO_CHAR(ORD.ORDDT, 'YYYY-MM-DD') BETWEEN ? AND ? ");
				}
				sql.append(" ORDER BY ORD.ORDNO desc ");
				
				System.out.println("select orderlist="+sql.toString());
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setString(1, order.getUserid());
				if(startDay != null && endDay != null) {
					pstmt.setString(2, startDay);
					pstmt.setString(3, endDay);
				}
				sql.delete(0, sql.toString().length());
			}			
			
			rs = pstmt.executeQuery();
			int skip=((pinfo.getNowPage()-1)*pinfo.getListCount());
			for(int i = 0; i < skip; i++) {
				rs.next();
				//데이터베이스 작업 포인터를 필요없는 데이터에서 내린다
			}
			for(int i = 0; i < pinfo.getListCount() && rs.next(); i++) 
			{
				Order ord = new Order();
				/*sql.append(" ORD.ORDNO as ORDERNO, ORD.USERID as USERID, TOTAL, PAYYN, ");
				sql.append(" ORD.READYTM as READYTM, ORD.ORDDT as ORDDT, ORD.STATUS as STATUS,  ORI.OPTIONS as OPTIONS, ORI.AMOUNT as AMOUNT, ");
				sql.append(" ORI.PRICE as PRICE,  P.PNAME as PNAME, P.IMAGE as IMAGE, P.MAKETM as MAKETM ");*/
				
				ord.setOrderno(rs.getString("ORDERNO"));
				ord.setUserid(rs.getString("USERID"));
				ord.setTotal(rs.getLong("TOTAL"));
				ord.setPayyn(rs.getInt("PAYYN"));
				ord.setReadytm(rs.getString("READYTM"));
				ord.setOrddt(rs.getDate("ORDDT"));
				ord.setStatus(rs.getInt("STATUS"));
				ord.setPname(rs.getString("PNAME"));
				ord.setImage(rs.getString("IMAGE"));
				ord.setAmount(rs.getInt("AMOUNT"));
				
				orderList.add(ord);
			}
			System.out.println("orderList="+orderList.size());
			DBConnection.close(rs);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return orderList;
	} //end of ArrayList<Order> 
	
	// select문 count(오늘날짜의 status==1)
	public int getCountStatus(Order order) {
		
		int count = 0;
		try {
			conn = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer();
			
				sql.append("SELECT	");
				sql.append(" NVL(count(*),0) as count ");
				sql.append("FROM ORDERS ");
				sql.append("WHERE  ");
				sql.append("	status=1 ");
				sql.append("AND ");
				sql.append("	trunc(orddt,'dd')=trunc(sysdate,'dd')	 ");
				
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				System.out.println("getCountStatus(Order order)의 pstmt = "+pstmt);
				sql.delete(0, sql.toString().length());
			rs = pstmt.executeQuery();
			
			rs.next();
			count=rs.getInt("count");
			//System.out.println("cartList="+cartList.size());
			DBConnection.close(rs);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return count;
	}
	
	
	public int orderProc(Order ord) {
		int result = 0;
		PreparedStatement pstmt2 = null ;
		PreparedStatement pstmt3 = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			
			conn = DBConnection.getConnection();
			
			// 자동 커밋을 false로 한다.
			conn.setAutoCommit(false);
			 //다시 짜야한다!!!!!!!!!!!!!!!!!!!!!!!!!!!	-- 아닌가??10.10
			sql.append("INSERT INTO orders ");
			sql.append("(ORDNO, USERID, TOTAL, PAYYN , READYTM,  ");
			sql.append(" ORDDT, STATUS ) ");
			sql.append(" VALUES(");
			sql.append("?, ?, ?, 2, ?,  ");
			sql.append(" sysdate, 1 ");
			sql.append(" )");
						
			// ordno에 넣을 것 만들자//
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setString(1, ord.getOrderno());
			pstmt.setString(2, ord.getUserid());
			pstmt.setLong(3, ord.getTotal());
			pstmt.setString(4, ord.getReadytm());
			// pstmt.setInt(4, order.getPayyn());	//	Payyn은 insert 될 때 '2'로 결제되었다고 가정한다
			//	pstmt.setInt(6, order.getStatus());	// status는 insert 될 때 1로 시작된다
			//	pstmt.setDate(6, order.getOrddt());	sysdate로 들어가니 삭제해도 된다.
			
			
			 // insert Orders 문(orders  의 userId, total,  cart( pidx가 가리키고 있는 상품중 하나) )
			int flag1 = pstmt.executeUpdate();
			sql.delete(0, sql.toString().length());
			
			 
			
			// insert OrderItem 문(orders 의 userId 를 이용하여 cart의 userid와 buychk='Y"인것을 orderitem 에 저장 )
			// insert into orderitem ( 대응 컬럼들) as (select 대응 컬럼들(pidx, price, optprice, amount, options from cart where userid=? and buychk='Y')
			// orderitem orderno, itemno 											select  ordervo의 orderno, orderitem_seq.nextVal    
			int flag2 = 0;
			if(flag1 > 0){
				// insert OrderItem 문
				sql.append("INSERT INTO ORDERITEM ");
				sql.append("(ORDNO, ITEMNO, PIDX, OPTIONS , AMOUNT,  ");
				sql.append(" PRICE, OPTPRICE )  ");
				sql.append(" (select ?, orderitem_seq.nextVal, pidx,  options, amount, price, optprice  ");
				sql.append(" from cart where userid=? and buychk='Y') ");
				
				System.out.println("insert orderitem="+sql.toString());

				pstmt2 = DBConnection.getPstmt(conn, sql.toString());
				pstmt2.setString(1,ord.getOrderno());
				pstmt2.setString(2,ord.getUserid());
				
				flag2 = pstmt2.executeUpdate();
				sql.delete(0, sql.toString().length());
			}
			
			int flag3 = 0;
			if(flag2 > 0){
				// delete Cart 문 
				// delete from cart where userid=? and buychk='Y'
				
				sql.append("delete from cart where userid=? and buychk='Y' ");
				
				System.out.println("delete cart="+sql.toString());
				pstmt3 = DBConnection.getPstmt(conn, sql.toString());
				pstmt3.setString(1, ord.getUserid());
				
				flag3 = pstmt3.executeUpdate();
				sql.delete(0, sql.toString().length());
			}
			
			System.out.println("flag3:"+flag3);
			if(flag3 > 0){
				result = 1;
				conn.commit(); // 완료시 커밋
			}
			
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			} finally {
				DBConnection.close(pstmt3);
				DBConnection.close(pstmt2);
				DBConnection.close(pstmt);
				DBConnection.close(conn);
			}
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		
		return result;
	}
	
	//ArrayList<Order> 관리자용 주문조회용 쿼리문
		public ArrayList<Order> getAllOrderAdm(Order order, PageUtil pinfo, HashMap<String, Object> listOpt) {
			ArrayList<Order> orderList = null;
			String startDay = (String)listOpt.get("startDay");
			String endDay = (String)listOpt.get("endDay");
			try {
				conn = DBConnection.getConnection();
				StringBuffer sql = new StringBuffer();
				orderList = new ArrayList<Order>();
				
				//	이것은  select 해서 orders와 orderItem과 product를 구한 것이다.
				if(order.getUserid() != null) {
					sql.append("SELECT  ");
					sql.append("  ORD.ORDNO as ORDERNO, ORD.USERID as USERID, TOTAL, PAYYN, ");
					sql.append(" ORD.READYTM as READYTM, ORD.ORDDT as ORDDT, ORD.STATUS as STATUS, ");
					sql.append(" (SELECT PNAME FROM PRODUCT WHERE PIDX = ( ");
					sql.append(" 			SELECT PIDX FROM ORDERITEM WHERE ORDNO = ORD.ORDNO AND ROWNUM = 1  ");
					sql.append(" 		) ");
					sql.append(" ) PNAME, ");
					sql.append(" (SELECT IMAGE FROM PRODUCT WHERE PIDX = (  ");
					sql.append(" 			SELECT PIDX FROM ORDERITEM WHERE ORDNO = ORD.ORDNO AND ROWNUM = 1 ");
					sql.append(" 		) ");
					sql.append(" ) IMAGE,  ");
					sql.append(" (SELECT COUNT(*) cnt FROM ORDERITEM WHERE ORDNO = ORD.ORDNO) AMOUNT  ");
					sql.append("FROM ORDERS ORD ");
					if(startDay != null && endDay != null) {
						sql.append("WHERE  ");
						sql.append("TO_CHAR(ORD.ORDDT, 'YYYY-MM-DD') BETWEEN ? AND ? ");
					}
					sql.append(" ORDER BY ORD.ORDNO desc ");
					
					System.out.println("select orderlist="+sql.toString());
					//pstmt = conn.prepareStatement(sql.toString());
					pstmt = DBConnection.getPstmt(conn, sql.toString());
					if(startDay != null && endDay != null) {
						pstmt.setString(1, startDay);
						pstmt.setString(2, endDay);
					}
					sql.delete(0, sql.toString().length());
				}			
				
				rs = pstmt.executeQuery();
				int skip=((pinfo.getNowPage()-1)*pinfo.getListCount());
				for(int i = 0; i < skip; i++) {
					rs.next();
					//데이터베이스 작업 포인터를 필요없는 데이터에서 내린다
				}
				for(int i = 0; i < pinfo.getListCount() && rs.next(); i++) 
				{
					Order ord = new Order();
					/*sql.append(" ORD.ORDNO as ORDERNO, ORD.USERID as USERID, TOTAL, PAYYN, ");
					sql.append(" ORD.READYTM as READYTM, ORD.ORDDT as ORDDT, ORD.STATUS as STATUS,  ORI.OPTIONS as OPTIONS, ORI.AMOUNT as AMOUNT, ");
					sql.append(" ORI.PRICE as PRICE,  P.PNAME as PNAME, P.IMAGE as IMAGE, P.MAKETM as MAKETM ");*/
					
					ord.setOrderno(rs.getString("ORDERNO"));
					ord.setUserid(rs.getString("USERID"));
					ord.setTotal(rs.getLong("TOTAL"));
					ord.setPayyn(rs.getInt("PAYYN"));
					ord.setReadytm(rs.getString("READYTM"));
					ord.setOrddt(rs.getDate("ORDDT"));
					ord.setStatus(rs.getInt("STATUS"));
					ord.setPname(rs.getString("PNAME"));
					ord.setImage(rs.getString("IMAGE"));
					ord.setAmount(rs.getInt("AMOUNT"));
					
					orderList.add(ord);
				}
				System.out.println("orderList="+orderList.size());
				DBConnection.close(rs);
			}
			catch(Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			} finally {
				DBConnection.close(pstmt);
				DBConnection.close(conn);
			}
			return orderList;
		} //end of ArrayList<Order>
		
		//  select order 관리자용 주문현황 때 뿌리는 것
		public Order getOrderConfirmAdm(Order order) {
			Order orderConfirm = null;
			try {
				conn = DBConnection.getConnection();
				StringBuffer sql = new StringBuffer();
				orderConfirm = new Order();
				
				//	이것은  select 해서 orders와 orderItem과 product를 구한 것이다.
				if(order.getUserid() != null) {
					sql.append("SELECT * ");
					sql.append("FROM ORDERS ");
					sql.append("WHERE ORDNO=? ");
					sql.append(" ORDER BY ORDNO desc ");
					
					System.out.println("select order="+sql.toString());
					//pstmt = conn.prepareStatement(sql.toString());
					pstmt = DBConnection.getPstmt(conn, sql.toString());
					pstmt.setString(1, order.getOrderno());
					
					sql.delete(0, sql.toString().length());
				}			
				
				rs = pstmt.executeQuery();
				while(rs.next()) 
				{
					orderConfirm.setOrderno(rs.getString("ORDNO"));
					orderConfirm.setUserid(rs.getString("USERID"));
					orderConfirm.setTotal(rs.getLong("TOTAL"));
					orderConfirm.setPayyn(rs.getInt("PAYYN"));
					orderConfirm.setReadytm(rs.getString("READYTM"));
					orderConfirm.setOrddt(rs.getDate("ORDDT"));
					orderConfirm.setStatus(rs.getInt("STATUS"));
				}
				DBConnection.close(rs);
			}
			catch(Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			} finally {
				DBConnection.close(pstmt);
				DBConnection.close(conn);
			}
			System.out.println("orderConfirm뭘까요="+orderConfirm);
			return orderConfirm;
		}	// end of select order 관리자용 주문현황 때 뿌리는 것
		
		// select Member을 뽑아낸다!!!!
		public Member getSelectMember(Order order) {
			Member selMember = null;
			try {
				conn = DBConnection.getConnection();
				StringBuffer sql = new StringBuffer();
				selMember = new Member();
				
				//	이것은  select 해서 orders와 orderItem과 product를 구한 것이다.
				if(order.getUserid() != null) {
					sql.append("SELECT * ");
					sql.append("FROM MEMBER ");
					sql.append("WHERE USERID=? ");
					
					System.out.println("select order="+sql.toString());
					//pstmt = conn.prepareStatement(sql.toString());
					pstmt = DBConnection.getPstmt(conn, sql.toString());
					pstmt.setString(1, order.getUserid());
					
					sql.delete(0, sql.toString().length());
				}			
				
				rs = pstmt.executeQuery();
				while(rs.next()) 
				{
					selMember.setUserid(rs.getString("USERID"));
					selMember.setUname(rs.getString("UNAME"));
					selMember.setNickName(rs.getString("NICK"));
					selMember.setHp(rs.getString("HP"));
					selMember.setJob(rs.getInt("JOB"));
					selMember.setGender(rs.getInt("GENDER"));
					selMember.setStatus(rs.getInt("STATUS"));
					
				}
				DBConnection.close(rs);
			}
			catch(Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			} finally {
				DBConnection.close(pstmt);
				DBConnection.close(conn);
			}
			System.out.println("selMember뭘까요="+selMember);
			return selMember;
		}	// end of select order 관리자용 주문현황 때 뿌리는 것
		
		
		public boolean updateStatusProc(Order order) {
			boolean result = false;
			
			try{
				conn = DBConnection.getConnection();
				conn.setAutoCommit(false); // 자동 커밋을 false로 한다.
				
				StringBuffer sql = new StringBuffer();
				
					sql.append("UPDATE ORDERS SET ");
					sql.append(" 	STATUS=2 ");
					sql.append("WHERE ORDNO=? ");
					sql.append("	AND USERID=? ");
				
//				else if(order.getStatus()==2) {
//					sql.append("UPDATE ORDERS SET ");
//					sql.append(" 	STATUS=1 ");
//					sql.append("WHERE ORDNO=? ");
//					sql.append("	AND USERID=? ");
//				}
				// eles if가 왜 안되는걸까 그래서 어쩔 수 없이 처리완료만 했다
				
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				System.out.println("order.getStatus()의 update쿼리="+sql.toString());
				pstmt.setString(1, order.getOrderno());
				pstmt.setString(2, order.getUserid());
				System.out.println("order.getOrderno()"+order.getOrderno());
				int flag = pstmt.executeUpdate();
				if(flag > 0){
					result = true;
					conn.commit(); // 완료시 커밋
				}
				
			} catch (Exception e) {
				try {
					conn.rollback(); // 오류시 롤백
				} catch (SQLException sqle) {
					sqle.printStackTrace();
				} finally {
					DBConnection.close(pstmt);
					DBConnection.close(conn);
				}
				throw new RuntimeException(e.getMessage());
			} finally {
				DBConnection.close(pstmt);
				DBConnection.close(conn);
			}
			return result;
		}
		
		//---------------------------------------------페이징처리
		public int getOrderCount(HashMap<String, Object> listOpt) {
			String startDay = (String)listOpt.get("startDay");
			String endDay = (String)listOpt.get("endDay");
			String userId = (String)listOpt.get("userId");
			
			int count = 0;
			try {
				conn = DBConnection.getConnection();
				StringBuffer sql = new StringBuffer();
				
				sql.append("SELECT	");
				sql.append(" NVL(count(*),0) as cnt ");
				sql.append("FROM ORDERS ");
				sql.append("WHERE  ");
				sql.append(" userid = ?  ");
				
				if(startDay != null && endDay != null) {
					sql.append("AND TO_CHAR(ORDDT, 'YYYY-MM-DD') BETWEEN ? AND ? ");
				}
				
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				System.out.println("getCountStatus(Order order)의 pstmt = "+pstmt);
				
				pstmt.setString(1, userId);
				
				if(startDay != null && endDay != null) {
					pstmt.setString(2, startDay);
					pstmt.setString(3, endDay);
				}
				sql.delete(0, sql.toString().length());
				
				rs = pstmt.executeQuery();
				
				rs.next();
				count=rs.getInt("cnt");
				
				DBConnection.close(rs);
			}
			catch(Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			} finally {
				DBConnection.close(pstmt);
				DBConnection.close(conn);
			}
			return count;
		}
		
		public int getOrderAdmCount(HashMap<String, Object> listOpt) {
			String startDay = (String)listOpt.get("startDay");
			String endDay = (String)listOpt.get("endDay");
			
			int count = 0;
			try {
				conn = DBConnection.getConnection();
				StringBuffer sql = new StringBuffer();
				
				sql.append("SELECT	");
				sql.append(" NVL(count(*),0) as cnt ");
				sql.append("FROM ORDERS ");
				
				if(startDay != null && endDay != null) {
					sql.append("WHERE  ");
					sql.append("TO_CHAR(ORDDT, 'YYYY-MM-DD') BETWEEN ? AND ? ");
				}
				
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				System.out.println("getCountStatus(Order order)의 pstmt = "+pstmt);
								
				if(startDay != null && endDay != null) {
					pstmt.setString(1, startDay);
					pstmt.setString(2, endDay);
				}
				sql.delete(0, sql.toString().length());
				
				rs = pstmt.executeQuery();
				
				rs.next();
				count=rs.getInt("cnt");
				
				DBConnection.close(rs);
			}
			catch(Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			} finally {
				DBConnection.close(pstmt);
				DBConnection.close(conn);
			}
			return count;
		}
		
}
