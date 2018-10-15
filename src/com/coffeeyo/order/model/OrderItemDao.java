package com.coffeeyo.order.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.coffeeyo.common.util.DBConnection;

public class OrderItemDao {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private static OrderItemDao instance;
	
	private OrderItemDao() {}
	
	public static OrderItemDao getInstance() {
		if(instance==null)
			instance = new OrderItemDao();
		
		return instance;
	}
	
	// �������� �����´�.
	public int getSeq()
	{
		int result = 1;
		
		try {
			conn = DBConnection.getConnection();
			
			// ������ ���� �����´�. (DUAL : ������ ���� ������������ �ӽ� ���̺�)
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ORDERITEM_SEQ.NEXTVAL FROM DUAL");
			
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			// ���� ����
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
	} // end getSeq
	
	
	//	���⼭���ʹ� ��ȯ�ۼ�
	
	// ������ OrderItem���̺� �߰�
			public boolean insertOrderItem(Order ord, Cart cart, OrderItem oitem) {
				boolean result = false;
				
				try {
					conn = DBConnection.getConnection();
					
					// �ڵ� Ŀ���� false�� �Ѵ�.
					conn.setAutoCommit(false);

					 // insert OrderItem ��
					StringBuffer sql = new StringBuffer();
					sql.append("INSERT INTO ORDERITEM ");
					sql.append("(ORDNO, ITEMNO, PIDX, OPTIONS , AMOUNT,  ");
					sql.append(" PRICE, OPTPRICE ) ");
					sql.append(" VALUES(");
					sql.append("?, ?, ?, ?, ?, ?, ?  ");
					sql.append(" )");
								
					// ordno�� ���� �� ������//
					
					//pstmt = conn.prepareStatement(sql.toString());
					pstmt = DBConnection.getPstmt(conn, sql.toString());
					pstmt.setString(1, ord.getOrderno());
					pstmt.setLong(2, oitem.getItemno());
					pstmt.setLong(3, cart.getPidx());
					pstmt.setString(4, cart.getOptions());
					pstmt.setInt(5, cart.getAmount());
					pstmt.setLong(6, cart.getPrice());
					pstmt.setInt(7, cart.getOptprice());
					// pstmt.setInt(4, order.getPayyn());	//	Payyn�� insert �� �� 'y'�� �����Ǿ��ٰ� �����Ѵ�
					//	pstmt.setInt(6, order.getStatus());	// status�� insert �� �� 1�� ���۵ȴ�
					//	pstmt.setDate(6, order.getOrddt());	sysdate�� ���� �����ص� �ȴ�.

					int flag = pstmt.executeUpdate();
					System.out.println("flat:"+flag);
					if(flag > 0){
						result = true;
						conn.commit(); // �Ϸ�� Ŀ��
					}
					
				} catch (Exception e) {
					try {
						conn.rollback();
					} catch (SQLException sqle) {
						sqle.printStackTrace();
					} finally {
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
			} // end insertOrderItem
	
	
	// ArrayList<OrderItem> �ֹ��ڿ� �ֹ���ȣ�� ���� �ֹ��׸� ���� ������ select�Ѵ�
	public ArrayList<OrderItem> getAllOrderItem(Order order) {
		ArrayList<OrderItem> orderItemList = null;
		try {
			conn = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer();
			orderItemList = new ArrayList<OrderItem>();
			
			//	��� �� �𸣰ڴ� ����� ������ �ʿ���!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			if(order.getUserid() != null) {
				sql.append("SELECT");
				sql.append(" ORI.ORDNO as ORDERNO, ORD.USERID as USERID, TOTAL, PAYYN, ");
				sql.append(" ORD.READYTM as READYTM, ORD.ORDDT as ORDDT, ORD.STATUS as STATUS,  ORI.OPTIONS as OPTIONS, ORI.AMOUNT as AMOUNT, ");
				sql.append(" ORI.PRICE as PRICE, ORI.OPTPRICE as OPTPRICE ,ORI.ITEMNO as ITEMNO ,  P.PNAME as PNAME, P.IMAGE as IMAGE, P.MAKETM as MAKETM ");
				sql.append("FROM ORDERS ORD ");
				sql.append("LEFT JOIN ORDERITEM ORI ");
				sql.append("	ON ORD.ORDNO=ORI.ORDNO ");
				sql.append("LEFT JOIN PRODUCT P ");
				sql.append("	ON ORI.PIDX=P.PIDX ");
				sql.append("WHERE ORD.USERID=? ");
				sql.append("AND ORD.ORDNO=? ");
				sql.append(" ORDER BY ORD.ORDNO desc ");
				
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setString(1, order.getUserid());
				pstmt.setString(2, order.getOrderno());
				System.out.println("select �� orderitemList="+sql.toString());
				sql.delete(0, sql.toString().length());
			}			
			
			rs = pstmt.executeQuery();
			while(rs.next()) 
			{
				OrderItem oitem = new OrderItem();
				
				oitem.setOrderno(rs.getString("ORDERNO"));
				oitem.setItemno(rs.getInt("ITEMNO"));
				oitem.setStatus(rs.getInt("STATUS"));
				oitem.setPname(rs.getString("PNAME"));
				oitem.setImage(rs.getString("IMAGE"));
				oitem.setOptions(rs.getString("OPTIONS"));
				oitem.setAmount(rs.getInt("AMOUNT"));
				oitem.setPrice(rs.getLong("PRICE"));
				oitem.setOptprice(rs.getInt("OPTPRICE"));
				
				orderItemList.add(oitem);
			}
			//System.out.println("orderList="+orderList.size());
			DBConnection.close(rs);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return orderItemList;
	} //end of ArrayList<OrderItem> 
	
	//ArrayList<Order> �����ڿ� �ֹ���ȸ�� ������
		public ArrayList<OrderItem> getAllOrderItemAdm(Order order) {
			ArrayList<OrderItem> orderItemList = null;
			try {
				conn = DBConnection.getConnection();
				StringBuffer sql = new StringBuffer();
				orderItemList = new ArrayList<OrderItem>();
						
						//	�̰���  select �ؼ� orders�� orderItem�� product�� ���� ���̴�.
						if(order.getUserid() != null) {
							sql.append("SELECT");
							sql.append(" ORD.ORDNO as ORDERNO, ORD.USERID as USERID, TOTAL, PAYYN, ");
							sql.append(" ORD.READYTM as READYTM, ORD.ORDDT as ORDDT, ORD.STATUS as STATUS,  ORI.OPTIONS as OPTIONS, ORI.AMOUNT as AMOUNT, ");
							sql.append(" ORI.PRICE as PRICE, ORI.OPTPRICE as OPTPRICE , ORI.ITEMNO as ITEMNO ,P.PNAME as PNAME, P.IMAGE as IMAGE, P.MAKETM as MAKETM ");
							sql.append("FROM ORDERS ORD ");
							sql.append("LEFT JOIN ORDERITEM ORI ");
							sql.append("	ON ORD.ORDNO=ORI.ORDNO ");
							sql.append("LEFT JOIN PRODUCT P ");
							sql.append("	ON ORI.PIDX=P.PIDX ");
							sql.append("	WHERE ORD.ORDNO=? ");
							sql.append(" ORDER BY ORD.ORDNO desc ");
							
							System.out.println("select orderlist="+sql.toString());
							//pstmt = conn.prepareStatement(sql.toString());
							pstmt = DBConnection.getPstmt(conn, sql.toString());
							pstmt.setString(1, order.getOrderno());
							sql.delete(0, sql.toString().length());
						}			
						
						rs = pstmt.executeQuery();
						while(rs.next()) 
						{
							OrderItem oitem = new OrderItem();
							/*sql.append(" ORD.ORDNO as ORDERNO, ORD.USERID as USERID, TOTAL, PAYYN, ");
							sql.append(" ORD.READYTM as READYTM, ORD.ORDDT as ORDDT, ORD.STATUS as STATUS,  ORI.OPTIONS as OPTIONS, ORI.AMOUNT as AMOUNT, ");
							sql.append(" ORI.PRICE as PRICE,  P.PNAME as PNAME, P.IMAGE as IMAGE, P.MAKETM as MAKETM ");*/
							
							oitem.setItemno(rs.getLong("ITEMNO"));
							oitem.setOrderno(rs.getString("ORDERNO"));
							oitem.setStatus(rs.getInt("STATUS"));
							oitem.setPname(rs.getString("PNAME"));
							oitem.setImage(rs.getString("IMAGE"));
							oitem.setPrice(rs.getLong("PRICE"));
							oitem.setAmount(rs.getInt("AMOUNT"));
							oitem.setOptions(rs.getString("OPTIONS"));
							oitem.setOptprice(rs.getInt("OPTPRICE"));
							
							orderItemList.add(oitem);
						}
						System.out.println("orderList="+orderItemList.size());
						DBConnection.close(rs);
					}
					catch(Exception e) {
						e.printStackTrace();
						throw new RuntimeException(e.getMessage());
					} finally {
						DBConnection.close(pstmt);
						DBConnection.close(conn);
					}
					return orderItemList;
				} //end of ArrayList<OrderItemList>
			
			
	
}
