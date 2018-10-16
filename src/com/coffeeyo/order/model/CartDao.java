package com.coffeeyo.order.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.coffeeyo.common.util.DBConnection;
import com.coffeeyo.product.model.Product;

public class CartDao {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private static CartDao instance;
	
	private CartDao() {}
	
	public static CartDao getInstance() {
		if(instance==null)
			instance = new CartDao();
			
		return instance;
	}
	
	// 시퀀스를 가져온다.
	public int getSeq()
	{
		int result = 1;
		
		try {
			conn = DBConnection.getConnection();
			
			// 시퀀스 값을 가져온다. (DUAL : 시퀀스 값을 가져오기위한 임시 테이블)
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT CART_SEQ.NEXTVAL FROM DUAL");
			
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			// 쿼리 실행
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
		
	public boolean insertCart(Cart cart) {
		boolean result = false;
		
		try {
			conn = DBConnection.getConnection();
			
			// 자동 커밋을 false로 한다.
			conn.setAutoCommit(false);
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO CART ");
			sql.append("(CIDX, USERID, PIDX, OPTIONS, ");
			sql.append(" AMOUNT, PRICE, OPTPRICE, CREATEDT, BUYCHK) ");
			sql.append(" VALUES(");
			sql.append("?,?,?,?,");
			sql.append("?,?,?,sysdate,?");
			sql.append(" )");
						
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setLong(1, cart.getCidx());
			pstmt.setString(2, cart.getUserid());
			pstmt.setLong(3, cart.getPidx());
			pstmt.setString(4, cart.getOptions());
			pstmt.setInt(5, cart.getAmount());
			pstmt.setLong(6, cart.getPrice());
			pstmt.setLong(7, cart.getOptprice());
			pstmt.setString(8, cart.getBuychk());

			int flag = pstmt.executeUpdate();
			if(flag > 0){
				result = true;
				conn.commit(); // 완료시 커밋
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
	} // end insertCart
	
	public boolean updataBuyCart(Cart cart) {
		boolean result = false;
		
		try{
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false); // 자동 커밋을 false로 한다.
			
			StringBuffer sql = new StringBuffer();
			
			sql.append("UPDATE CART SET ");
			sql.append(" 	BUYCHK=? ");
			sql.append("WHERE CIDX=? ");
			sql.append("	AND USERID=? ");

			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setString(1, cart.getBuychk());
			pstmt.setLong(2, cart.getCidx());
			pstmt.setString(3, cart.getUserid());
			
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
	
	public boolean updateCart(Cart cart) 
	{
		boolean result = false;
		
		try{
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false); // 자동 커밋을 false로 한다.
			
			StringBuffer sql = new StringBuffer();
			
			sql.append("UPDATE CART SET ");
			sql.append(" OPTIONS=? ");
			sql.append(" ,AMOUNT=? ");
			sql.append(" ,PRICE=? ");
			sql.append(" ,BUYCHK=? ");
			sql.append("WHERE CIDX=?");

			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setString(1, cart.getOptions());
			pstmt.setInt(2, cart.getAmount());
			pstmt.setLong(3, cart.getPrice());
			pstmt.setString(4, cart.getBuychk());
			pstmt.setLong(5, cart.getCidx());
			
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
	} // end updateCart
	
	// 장바구니 삭제
	public boolean deleteCart(Cart cart) 
	{
		boolean result = false;

		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false); // 자동 커밋을 false로 한다.

			StringBuffer sql = new StringBuffer();
			
			String mode = cart.getMode();
			String userId = cart.getUserid();
			
			if(mode.equals("S")) {
				sql.append("DELETE FROM CART");
				sql.append(" WHERE CIDX = ?");
				
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setLong(1, cart.getCidx());
			}
			else {
				sql.append("DELETE FROM CART");
				sql.append(" WHERE USERID = ? AND BUYCHK = 'Y' ");
				
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setString(1, userId);
			}
			
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
	} // end deleteCart
	
	
	// 
	public ArrayList<Cart> getAllCart(Cart cart) {
		ArrayList<Cart> cartList = null;
		try {
			conn = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer();
			cartList = new ArrayList<Cart>();
			
			if(cart.getUserid() != null) {
				sql.append("SELECT");
				sql.append(" C.CIDX, C.USERID, C.PIDX, OPTIONS, ");
				sql.append(" AMOUNT, C.PRICE, OPTPRICE, BUYCHK, P.PNAME, P.IMAGE ");
				sql.append("FROM CART C ");
				sql.append("LEFT JOIN PRODUCT P ");
				sql.append("ON C.PIDX=P.PIDX ");
				sql.append("WHERE C.USERID=? ");
				if(cart.getBuychk().equals("Y")) {
					sql.append("AND C.BUYCHK='Y' ");
				}
				sql.append(" ORDER BY C.CIDX desc ");
				
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setString(1, cart.getUserid());
				
				sql.delete(0, sql.toString().length());
			}			
			
			rs = pstmt.executeQuery();
			while(rs.next()) 
			{
				Cart ct = new Cart();
				
				ct.setCidx(rs.getLong("CIDX"));
				ct.setUserid(rs.getString("USERID"));
				ct.setPidx(rs.getLong("PIDX"));
				ct.setOptions(rs.getString("OPTIONS"));
				ct.setAmount(rs.getInt("AMOUNT"));
				ct.setPrice(rs.getInt("PRICE"));
				ct.setOptprice(rs.getInt("OPTPRICE"));
				ct.setPname(rs.getString("PNAME"));
				ct.setImage(rs.getString("IMAGE"));
				
				cartList.add(ct);
			}
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
		return cartList;
	}
	
	// 주문에 넣을 readytm을 구하기 위한 메서드이다. 
	public int getReadyTime(Cart cart) {
		int tm = 0;
		try {
			conn = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer();
			
			if(cart.getUserid() != null) {
				sql.append("SELECT");
				sql.append(" sum( (P.maketm * C.AMOUNT) ) as mktm ");
				sql.append("FROM CART C ");
				sql.append("LEFT JOIN PRODUCT P ");
				sql.append("ON C.PIDX=P.PIDX ");
				sql.append("WHERE C.USERID=? ");
				if(cart.getBuychk().equals("Y")) {
					sql.append("AND C.BUYCHK='Y' ");
				}
				sql.append(" ORDER BY C.CIDX desc ");
				
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setString(1, cart.getUserid());
				
				sql.delete(0, sql.toString().length());
			}			
			
			rs = pstmt.executeQuery();
			rs.next();
			tm=rs.getInt("mktm");
			
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
		return tm;
	}
	
	// 주문에 넣을 total을 구하기 위한 메서드이다. 
		public long getTotalPrice(Cart cart) {
			long total = 0;
			try {
				conn = DBConnection.getConnection();
				StringBuffer sql = new StringBuffer();
				
				if(cart.getUserid() != null) {
					sql.append("SELECT	");
					sql.append(" sum( ((C.PRICE+NVL(C.OPTPRICE,0) )* C.AMOUNT) ) as total ");
					sql.append("FROM CART C ");
					sql.append("WHERE C.USERID=? ");
					if(cart.getBuychk().equals("Y")) {
						sql.append("AND C.BUYCHK='Y' ");
					}
					sql.append(" ORDER BY C.CIDX desc ");
					
					//pstmt = conn.prepareStatement(sql.toString());
					pstmt = DBConnection.getPstmt(conn, sql.toString());
					pstmt.setString(1, cart.getUserid());
					
					sql.delete(0, sql.toString().length());
				}			
				
				rs = pstmt.executeQuery();
				rs.next();
				total=rs.getLong("total");
				
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
			return total;
		}
	
}
