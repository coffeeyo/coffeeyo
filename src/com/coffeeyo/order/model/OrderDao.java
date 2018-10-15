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
	
	// ���� ����¡ ó�� ������ ����--------------------
	private POOLUtil db;
	private OrderDao() {
		db=new POOLUtil();
		conn=db.getCON();
	}
	// ������� ����¡ --------------------------------
	public static OrderDao getInstance() {
		if(instance==null)
			instance = new OrderDao();
		
		return instance;
	}
	
	// �������� �����´�.  �̰��� �ֹ���ȣ�� ��ȯ�Ѵ�.
	public String getSeq()
	{
		String result = "";
		
		try {
			conn = DBConnection.getConnection();
			
			// ������ ���� �����´�. (DUAL : ������ ���� ������������ �ӽ� ���̺�)
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ORDERS_SEQ.NEXTVAL FROM DUAL");
			
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			// ���� ����
			rs = pstmt.executeQuery();
			/*
			 * 	4. �߹�ó��(����) �� ���� PG ������ ���� ������
	�ֹ��� userid �������� ��ٱ��Ͽ� ��� ����     BUYCHK = 'Y' �ΰ͸�
	ORDERS ���̺��� �԰� �Ұ� ${sum}�� ���� price�� �Ű������� �̿��ϰ�,
	ORDNO �� OrderProcAction ���� �����͸� �����ϱ����� OrderDao.getSeq() �� ���� ��¥�������� ������
	��) S+��¥_����sequence��ȣ
	=> S20180826_1
	
	-��¥����
	Date now = new Date();
	SimpleDateFormat vans = new SimpleDateFormat("yyyyMMdd");
	String wdate = vans.format(now);
	
	-�ֹ���ȣ����
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
	
	
	// �Ʒ����ʹ� ��ȯ �ۼ�
	
	// select order ���� �� �Ѹ��� ��
	public Order getOrderConfirm(Order order) {
		Order orderConfirm = null;
		try {
			conn = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer();
			orderConfirm = new Order();
			
			//	�̰���  select �ؼ� orders�� orderItem�� product�� ���� ���̴�.
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
	}	// end of select order ���� �� �Ѹ��� ��
	
	//ArrayList<Order> 
	public ArrayList<Order> getAllOrder(Order order, PageUtil pinfo, HashMap<String, Object> listOpt) {
		ArrayList<Order> orderList = null;
		String startDay = (String)listOpt.get("startDay");
		String endDay = (String)listOpt.get("endDay");
		try {
			conn = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer();
			orderList = new ArrayList<Order>();
			
			//	�̰���  select �ؼ� orders�� orderItem�� product�� ���� ���̴�.
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
				//�����ͺ��̽� �۾� �����͸� �ʿ���� �����Ϳ��� ������
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
	
	// select�� count(���ó�¥�� status==1)
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
				System.out.println("getCountStatus(Order order)�� pstmt = "+pstmt);
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
			
			// �ڵ� Ŀ���� false�� �Ѵ�.
			conn.setAutoCommit(false);
			 //�ٽ� ¥���Ѵ�!!!!!!!!!!!!!!!!!!!!!!!!!!!	-- �ƴѰ�??10.10
			sql.append("INSERT INTO orders ");
			sql.append("(ORDNO, USERID, TOTAL, PAYYN , READYTM,  ");
			sql.append(" ORDDT, STATUS ) ");
			sql.append(" VALUES(");
			sql.append("?, ?, ?, 2, ?,  ");
			sql.append(" sysdate, 1 ");
			sql.append(" )");
						
			// ordno�� ���� �� ������//
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setString(1, ord.getOrderno());
			pstmt.setString(2, ord.getUserid());
			pstmt.setLong(3, ord.getTotal());
			pstmt.setString(4, ord.getReadytm());
			// pstmt.setInt(4, order.getPayyn());	//	Payyn�� insert �� �� '2'�� �����Ǿ��ٰ� �����Ѵ�
			//	pstmt.setInt(6, order.getStatus());	// status�� insert �� �� 1�� ���۵ȴ�
			//	pstmt.setDate(6, order.getOrddt());	sysdate�� ���� �����ص� �ȴ�.
			
			
			 // insert Orders ��(orders  �� userId, total,  cart( pidx�� ����Ű�� �ִ� ��ǰ�� �ϳ�) )
			int flag1 = pstmt.executeUpdate();
			sql.delete(0, sql.toString().length());
			
			 
			
			// insert OrderItem ��(orders �� userId �� �̿��Ͽ� cart�� userid�� buychk='Y"�ΰ��� orderitem �� ���� )
			// insert into orderitem ( ���� �÷���) as (select ���� �÷���(pidx, price, optprice, amount, options from cart where userid=? and buychk='Y')
			// orderitem orderno, itemno 											select  ordervo�� orderno, orderitem_seq.nextVal    
			int flag2 = 0;
			if(flag1 > 0){
				// insert OrderItem ��
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
				// delete Cart �� 
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
				conn.commit(); // �Ϸ�� Ŀ��
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
	
	//ArrayList<Order> �����ڿ� �ֹ���ȸ�� ������
		public ArrayList<Order> getAllOrderAdm(Order order, PageUtil pinfo, HashMap<String, Object> listOpt) {
			ArrayList<Order> orderList = null;
			String startDay = (String)listOpt.get("startDay");
			String endDay = (String)listOpt.get("endDay");
			try {
				conn = DBConnection.getConnection();
				StringBuffer sql = new StringBuffer();
				orderList = new ArrayList<Order>();
				
				//	�̰���  select �ؼ� orders�� orderItem�� product�� ���� ���̴�.
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
					//�����ͺ��̽� �۾� �����͸� �ʿ���� �����Ϳ��� ������
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
		
		//  select order �����ڿ� �ֹ���Ȳ �� �Ѹ��� ��
		public Order getOrderConfirmAdm(Order order) {
			Order orderConfirm = null;
			try {
				conn = DBConnection.getConnection();
				StringBuffer sql = new StringBuffer();
				orderConfirm = new Order();
				
				//	�̰���  select �ؼ� orders�� orderItem�� product�� ���� ���̴�.
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
			System.out.println("orderConfirm�����="+orderConfirm);
			return orderConfirm;
		}	// end of select order �����ڿ� �ֹ���Ȳ �� �Ѹ��� ��
		
		// select Member�� �̾Ƴ���!!!!
		public Member getSelectMember(Order order) {
			Member selMember = null;
			try {
				conn = DBConnection.getConnection();
				StringBuffer sql = new StringBuffer();
				selMember = new Member();
				
				//	�̰���  select �ؼ� orders�� orderItem�� product�� ���� ���̴�.
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
			System.out.println("selMember�����="+selMember);
			return selMember;
		}	// end of select order �����ڿ� �ֹ���Ȳ �� �Ѹ��� ��
		
		
		public boolean updateStatusProc(Order order) {
			boolean result = false;
			
			try{
				conn = DBConnection.getConnection();
				conn.setAutoCommit(false); // �ڵ� Ŀ���� false�� �Ѵ�.
				
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
				// eles if�� �� �ȵǴ°ɱ� �׷��� ��¿ �� ���� ó���ϷḸ �ߴ�
				
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				System.out.println("order.getStatus()�� update����="+sql.toString());
				pstmt.setString(1, order.getOrderno());
				pstmt.setString(2, order.getUserid());
				System.out.println("order.getOrderno()"+order.getOrderno());
				int flag = pstmt.executeUpdate();
				if(flag > 0){
					result = true;
					conn.commit(); // �Ϸ�� Ŀ��
				}
				
			} catch (Exception e) {
				try {
					conn.rollback(); // ������ �ѹ�
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
		
		//---------------------------------------------����¡ó��
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
				System.out.println("getCountStatus(Order order)�� pstmt = "+pstmt);
				
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
				System.out.println("getCountStatus(Order order)�� pstmt = "+pstmt);
								
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
