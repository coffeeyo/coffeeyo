package com.coffeeyo.product.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.NamingException;

import com.coffeeyo.common.util.DBConnection;
import com.coffeeyo.common.util.PageUtil;
import com.coffeeyo.product.common.PdpageUtil;


public class ProductDao {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private static ProductDao instance;
	
	private ProductDao() {}
	
	public static ProductDao getInstance() {
		if(instance==null)
			instance = new ProductDao();
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
			sql.append("SELECT PRODUCT_SEQ.NEXTVAL FROM DUAL");
			
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
	
	public Product getProduct(long pidx) {
		Product prod = null;
		
		try {
			conn = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer();
			prod = new Product();
			
			sql.append("SELECT");
			sql.append(" PIDX, c.CNAME, p.CIDX, PNAME, ");
			sql.append(" IMAGE, PRICE, MAKETM, RECOMM, ");
			sql.append(" p.STATUS, p.COMM, p.CREATEDT ");
			sql.append("FROM PRODUCT p ");
			sql.append("right outer join CATEGORY c ");
			sql.append("on p.cidx = c.cidx ");
			sql.append("WHERE p.pidx=? ");
			
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setLong(1, pidx);
			sql.delete(0, sql.toString().length());
			rs = pstmt.executeQuery();
			rs.next(); 
			
			prod.setPidx(rs.getLong("PIDX"));
			prod.setCidx(rs.getLong("CIDX"));
			prod.setCateName(rs.getString("CNAME"));
			prod.setPname(rs.getString("PNAME"));
			prod.setImage(rs.getString("IMAGE"));
			prod.setPrice(rs.getInt("PRICE"));
			prod.setMaketm(rs.getInt("MAKETM"));
			prod.setRecomm(rs.getLong("RECOMM"));
			prod.setStatus(rs.getInt("STATUS"));
			prod.setComm(rs.getString("COMM"));
			prod.setCreatedt(rs.getDate("CREATEDT"));
			
			DBConnection.close(rs);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		
		return prod;
	}
	
	public ArrayList<Product> getRecommProduct(long cidx ) {
		ArrayList<Product> prodList = null;
		
		try {
			conn = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer();
			prodList = new ArrayList<Product>();
			//System.out.println("cidx:"+cidx);
			
			if(cidx > 0) {
				sql.append("SELECT * FROM");
				sql.append(" (SELECT  ROWNUM AS rnum, data.* FROM ");
				sql.append("(SELECT");
				sql.append(" PIDX, c.CNAME, PNAME, IMAGE, ");
				sql.append(" PRICE, MAKETM, RECOMM, p.CIDX, ");
				sql.append(" p.STATUS, p.CREATEDT, ");
				sql.append("(select (sum(pcpoint)/count(*)) from PRODCOMM where pidx=p.pidx) as pavg ");
				sql.append("FROM PRODUCT p ");
				sql.append("left join CATEGORY c ");
				sql.append("on p.cidx = c.cidx ");
				sql.append("WHERE p.cidx=? and p.STATUS = 1 and p.RECOMM = 1 ");
				sql.append(" ORDER BY PIDX desc ");
				sql.append(") ");
				sql.append(" data) ");
				sql.append("WHERE rnum >=? and rnum <=?");
				
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setLong(1, cidx);
				pstmt.setInt(2, 1);
				pstmt.setInt(3, 10);
				
				sql.delete(0, sql.toString().length());
			}
			else {
				sql.append("SELECT * FROM");
				sql.append(" (SELECT  ROWNUM AS rnum, data.* FROM ");
				sql.append("(SELECT");
				sql.append(" PIDX, c.CNAME, PNAME, IMAGE, ");
				sql.append(" PRICE, MAKETM, RECOMM, p.CIDX, ");
				sql.append(" p.STATUS, p.CREATEDT, ");
				sql.append("(select (sum(pcpoint)/count(*)) from PRODCOMM where pidx=p.pidx) as PAVG ");
				sql.append("FROM PRODUCT p ");
				sql.append("left join CATEGORY c ");
				sql.append("on p.cidx = c.cidx ");
				sql.append("WHERE p.STATUS = 1 and p.RECOMM = 1 ");
				sql.append(" ORDER BY PIDX desc ");
				sql.append(") ");
				sql.append(" data) ");
				sql.append("WHERE rnum >=? and rnum <=?");
				
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setInt(1, 1);
				pstmt.setInt(2, 10);
				
				sql.delete(0, sql.toString().length());
			}
			
			rs = pstmt.executeQuery();
			while(rs.next()) 
			{
				Product prod = new Product();
				prod.setPidx(rs.getLong("PIDX"));
				prod.setCidx(rs.getLong("CIDX"));
				prod.setCateName(rs.getString("CNAME"));
				prod.setPname(rs.getString("PNAME"));
				prod.setImage(rs.getString("IMAGE"));
				prod.setPrice(rs.getInt("PRICE"));
				prod.setMaketm(rs.getInt("MAKETM"));
				prod.setRecomm(rs.getInt("RECOMM"));
				prod.setStatus(rs.getInt("STATUS"));
				prod.setCreatedt(rs.getDate("CREATEDT"));
				prod.setPcPointAvg(rs.getFloat("PAVG"));
				prodList.add(prod);
			}
			//System.out.println("prodList="+prodList.size());
			DBConnection.close(rs);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return prodList;
	}
	
	public ArrayList<Product> getAllProduct(HashMap<String, Object> listOpt) {
		ArrayList<Product> prodList = null;
		
		String opt = (String)listOpt.get("opt");
		String condition = (String)listOpt.get("condition");
		int start = (Integer)listOpt.get("start");
		
		try {
			conn = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer();
			prodList = new ArrayList<Product>();
			
			int cidx = (Integer)listOpt.get("cidx");

			if(opt == null) {
				if(cidx > 0) {
					sql.append("SELECT * FROM");
					sql.append(" (SELECT  ROWNUM AS rnum, data.* FROM ");
					sql.append("(SELECT");
					sql.append(" PIDX, c.CNAME, PNAME, IMAGE, ");
					sql.append(" PRICE, MAKETM, RECOMM, P.CIDX, ");
					sql.append(" p.STATUS, p.CREATEDT ");
					sql.append("FROM PRODUCT p ");
					sql.append("left join CATEGORY c ");
					sql.append("on p.cidx = c.cidx ");
					sql.append("WHERE p.cidx=? ");
					sql.append(" ORDER BY PIDX desc ");
					sql.append(") ");
					sql.append(" data) ");
					sql.append("WHERE rnum >=? and rnum <=?");
					
					//pstmt = conn.prepareStatement(sql.toString());
					pstmt = DBConnection.getPstmt(conn, sql.toString());
					pstmt.setLong(1, cidx);
					pstmt.setInt(2, start);
					pstmt.setInt(3, start+100);
					
					sql.delete(0, sql.toString().length());
				}
				else {
					sql.append("SELECT * FROM");
					sql.append(" (SELECT  ROWNUM AS rnum, data.* FROM ");
					sql.append("(SELECT");
					sql.append(" PIDX, c.CNAME, PNAME, IMAGE, ");
					sql.append(" PRICE, MAKETM, RECOMM, P.CIDX, ");
					sql.append(" p.STATUS, p.CREATEDT ");
					sql.append("FROM PRODUCT p ");
					sql.append("left join CATEGORY c ");
					sql.append("on p.cidx = c.cidx ");
					sql.append(" ORDER BY PIDX desc ");
					sql.append(") ");
					sql.append(" data) ");
					sql.append("WHERE rnum >=? and rnum <=?");
					
					//pstmt = conn.prepareStatement(sql.toString());
					pstmt = DBConnection.getPstmt(conn, sql.toString());
					pstmt.setInt(1, start);
					pstmt.setInt(2, start+9);
					
					sql.delete(0, sql.toString().length());
				}
			}
			else if(opt.equals("0")) // 상품명으로 검색
			{
				if(cidx > 0) {
					sql.append("SELECT * FROM");
					sql.append(" (SELECT  ROWNUM AS rnum, data.* FROM ");
					sql.append("(SELECT");
					sql.append(" PIDX, c.CNAME, PNAME, IMAGE, ");
					sql.append(" PRICE, MAKETM, RECOMM, P.CIDX, ");
					sql.append(" p.STATUS, p.CREATEDT ");
					sql.append("FROM PRODUCT p ");
					sql.append("left join CATEGORY c ");
					sql.append("on p.cidx = c.cidx  ");
					sql.append("WHERE p.cidx=? and p.pname like ?  ");
					sql.append("ORDER BY PIDX desc  ");
					sql.append(") ");
					sql.append(" data) ");
					sql.append("WHERE rnum >=? and rnum <=?");
					
					//pstmt = conn.prepareStatement(sql.toString());
					pstmt = DBConnection.getPstmt(conn, sql.toString());
					pstmt.setLong(1, cidx);
					pstmt.setString(2, "%"+condition+"%");
					pstmt.setInt(3, start);
					pstmt.setInt(4, start+9);
					
					sql.delete(0, sql.toString().length());
				}
				else {
					sql.append("SELECT * FROM");
					sql.append(" (SELECT  ROWNUM AS rnum, data.* FROM ");
					sql.append("(SELECT");
					sql.append(" PIDX, c.CNAME, PNAME, IMAGE, ");
					sql.append(" PRICE, MAKETM, RECOMM, P.CIDX, ");
					sql.append(" p.STATUS, p.CREATEDT ");
					sql.append("FROM PRODUCT p ");
					sql.append("left join CATEGORY c ");
					sql.append("on p.cidx = c.cidx  ");
					sql.append(" WHERE p.pname like ? ");
					sql.append(" ORDER BY PIDX desc ");
					sql.append(") ");
					sql.append(" data) ");
					sql.append("WHERE rnum >=? and rnum <=?");
					
					//pstmt = conn.prepareStatement(sql.toString());
					pstmt = DBConnection.getPstmt(conn, sql.toString());
					pstmt.setString(1, "%"+condition+"%");
					pstmt.setInt(2, start);
					pstmt.setInt(3, start+9);
					
					sql.delete(0, sql.toString().length());
				}
			}
			
			rs = pstmt.executeQuery();
			while(rs.next()) 
			{
				Product prod = new Product();
				prod.setPidx(rs.getLong("PIDX"));
				prod.setCidx(rs.getLong("CIDX"));
				prod.setCateName(rs.getString("CNAME"));
				prod.setPname(rs.getString("PNAME"));
				prod.setImage(rs.getString("IMAGE"));
				prod.setPrice(rs.getInt("PRICE"));
				prod.setMaketm(rs.getInt("MAKETM"));
				prod.setRecomm(rs.getInt("RECOMM"));
				prod.setStatus(rs.getInt("STATUS"));
				prod.setCreatedt(rs.getDate("CREATEDT"));
				prodList.add(prod);
			}
			//System.out.println("prodList="+prodList.size());
			DBConnection.close(rs);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return prodList;
	}
	
	public ArrayList<Product> getAllProduct(int cidx) {
		ArrayList<Product> prodList = null;
		try {
			conn = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer();
			prodList = new ArrayList<Product>();
			
			if(cidx > 0) {
				sql.append("SELECT * FROM");
				sql.append(" (SELECT  ROWNUM AS rnum, data.* FROM ");
				sql.append("(SELECT");
				sql.append(" PIDX, c.CNAME, PNAME, IMAGE, ");
				sql.append(" PRICE, MAKETM, RECOMM, P.CIDX, ");
				sql.append(" p.STATUS, p.CREATEDT, ");
				sql.append("(select nvl((sum(pcpoint)/count(*)),0) from PRODCOMM where pidx=p.pidx) as pavg ");
				sql.append("FROM PRODUCT p ");
				sql.append("left join CATEGORY c ");
				sql.append("on p.cidx = c.cidx ");
				sql.append("WHERE p.cidx=? and p.status = 1 ");
				sql.append(" ORDER BY PIDX desc ");
				sql.append(") ");
				sql.append(" data) ");
				sql.append("WHERE rnum >=? and rnum <=?");
				
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setLong(1, cidx);
				pstmt.setInt(2, 1);
				pstmt.setInt(3, 4);
				
				sql.delete(0, sql.toString().length());
			}
			else {
				sql.append("SELECT * FROM");
				sql.append(" (SELECT  ROWNUM AS rnum, data.* FROM ");
				sql.append("(SELECT");
				sql.append(" PIDX, c.CNAME, PNAME, IMAGE, ");
				sql.append(" PRICE, MAKETM, RECOMM, P.CIDX, ");
				sql.append(" p.STATUS, p.CREATEDT, ");
				sql.append("(select nvl((sum(pcpoint)/count(*)),0) from PRODCOMM where pidx=p.pidx) as pavg ");
				sql.append("FROM PRODUCT p ");
				sql.append("left join CATEGORY c ");
				sql.append("on p.cidx = c.cidx ");
				sql.append("WHERE p.status = 1 ");
				sql.append(" ORDER BY PIDX desc ");
				sql.append(") ");
				sql.append(" data) ");
				sql.append("WHERE rnum >=? and rnum <=?");
				
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setInt(1, 1);
				pstmt.setInt(2, 4);
				
				sql.delete(0, sql.toString().length());
			}
			
			
			rs = pstmt.executeQuery();
			while(rs.next()) 
			{
				Product prod = new Product();
				prod.setPidx(rs.getLong("PIDX"));
				prod.setCidx(rs.getLong("CIDX"));
				prod.setCateName(rs.getString("CNAME"));
				prod.setPname(rs.getString("PNAME"));
				prod.setImage(rs.getString("IMAGE"));
				prod.setPrice(rs.getInt("PRICE"));
				prod.setMaketm(rs.getInt("MAKETM"));
				prod.setRecomm(rs.getInt("RECOMM"));
				prod.setStatus(rs.getInt("STATUS"));
				prod.setCreatedt(rs.getDate("CREATEDT"));
				prod.setPcPointAvg(rs.getFloat("PAVG"));
				prodList.add(prod);
			}
			//System.out.println("prodList="+prodList.size());
			DBConnection.close(rs);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return prodList;
	}
	
	public ArrayList<Product> getAllProduct(long cidx) {
		ArrayList<Product> prodList = null;
		
		try {
			conn = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer();
			prodList = new ArrayList<Product>();
			
			if(cidx > 0) {
				sql.append("SELECT");
				sql.append(" PIDX, c.CNAME, PNAME, IMAGE, ");
				sql.append(" PRICE, MAKETM, RECOMM, ");
				sql.append(" p.STATUS, p.CREATEDT ");
				sql.append("FROM PRODUCT p ");
				sql.append("left join CATEGORY c ");
				sql.append("on p.cidx = c.cidx ");
				sql.append(" WHERE p.cidx=? and p.STATUS=1 ");
				sql.append(" ORDER BY PIDX desc ");
				
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setLong(1, cidx);
				
				sql.delete(0, sql.toString().length());
			}
			
			rs = pstmt.executeQuery();
			while(rs.next()) 
			{
				Product prod = new Product();
				prod.setPidx(rs.getLong("PIDX"));
				prod.setPname(rs.getString("PNAME"));
				prodList.add(prod);
			}
			//System.out.println("prodList="+prodList.size());
			DBConnection.close(rs);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return prodList;
	}
	
	// 글의 개수를 가져오는 메서드
	public int getProductListCount(HashMap<String, Object> listOpt)
	{
		int result = 0;
		String opt = (String)listOpt.get("opt");
		String condition = (String)listOpt.get("condition");
		int cidx = (Integer)listOpt.get("cidx");
		
		try {
			conn = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer();
			
			if(opt == null)	// 전체글의 개수
			{
				if(cidx > 0) {
					sql.append("select count(*) from PRODUCT where cidx = ? ");
					//pstmt = conn.prepareStatement(sql.toString());
					pstmt = DBConnection.getPstmt(conn, sql.toString());
					pstmt.setInt(1, cidx);
				} 
				else {
					sql.append("select count(*) from PRODUCT");
					//pstmt = conn.prepareStatement(sql.toString());
					pstmt = DBConnection.getPstmt(conn, sql.toString());
				}
				
				// StringBuffer를 비운다.
				sql.delete(0, sql.toString().length());
			}
			else if(opt.equals("0")) // 제목으로 검색한 글의 개수
			{
				if(cidx > 0) {
					sql.append("select count(*) from PRODUCT where PNAME like ?  and cidx = ? ");
					//pstmt = conn.prepareStatement(sql.toString());
					pstmt = DBConnection.getPstmt(conn, sql.toString());
					pstmt.setString(1, '%'+condition+'%');
					pstmt.setInt(2, cidx);
				}
				else {
					sql.append("select count(*) from PRODUCT where PNAME like ? ");
					//pstmt = conn.prepareStatement(sql.toString());
					pstmt = DBConnection.getPstmt(conn, sql.toString());
					pstmt.setString(1, '%'+condition+'%');
				}
				sql.delete(0, sql.toString().length());
			}
			
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
	} // end getProductListCount
	
	public boolean insertProduct(Product prod) 
	{
		boolean result = false;
		
		try {
			conn = DBConnection.getConnection();
			
			// 자동 커밋을 false로 한다.
			conn.setAutoCommit(false);
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO PRODUCT ");
			sql.append("(PIDX, CIDX, USERID, PNAME, COMM, IMAGE,");
			sql.append(" PRICE, MAKETM, RECOMM, STATUS, CREATEDT) ");
			sql.append(" VALUES(");
			sql.append("?,?,?,?,?,?,");
			sql.append("?,?,?,?,sysdate");
			sql.append(" )");
						
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setLong(1, prod.getPidx());
			pstmt.setLong(2, prod.getCidx());
			pstmt.setString(3, prod.getUserid());
			pstmt.setString(4, prod.getPname());
			pstmt.setString(5, prod.getComm());
			pstmt.setString(6, prod.getImage());
			pstmt.setLong(7, prod.getPrice());
			pstmt.setInt(8, prod.getMaketm());
			pstmt.setLong(9, prod.getRecomm());
			pstmt.setInt(10, prod.getStatus());

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
	} // end insertProduct
	
	public boolean updateProduct(Product prod) 
	{
		boolean result = false;
		
		try{
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false); // 자동 커밋을 false로 한다.
			
			StringBuffer sql = new StringBuffer();
			
			if(prod.getImage() != null) {
				sql.append("UPDATE PRODUCT SET");
				sql.append(" CIDX=?");
				sql.append(" ,PNAME=?");
				sql.append(" ,COMM=?");
				sql.append(" ,IMAGE=?");
				sql.append(" ,PRICE=?");
				sql.append(" ,MAKETM=?");
				sql.append(" ,RECOMM=?");
				sql.append(" ,STATUS=?");
				sql.append("WHERE PIDX=?");
	
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setLong(1, prod.getCidx());
				pstmt.setString(2, prod.getPname());
				pstmt.setString(3, prod.getComm());
				pstmt.setString(4, prod.getImage());
				pstmt.setLong(5, prod.getPrice());
				pstmt.setInt(6, prod.getMaketm());
				pstmt.setLong(7, prod.getRecomm());
				pstmt.setInt(8, prod.getStatus());
				pstmt.setLong(9, prod.getPidx());
			}
			else
			{
				sql.append("UPDATE PRODUCT SET");
				sql.append(" CIDX=?");
				sql.append(" ,PNAME=?");
				sql.append(" ,COMM=?");
				sql.append(" ,PRICE=?");
				sql.append(" ,MAKETM=?");
				sql.append(" ,RECOMM=?");
				sql.append(" ,STATUS=?");
				sql.append("WHERE PIDX=?");
	
				//pstmt = conn.prepareStatement(sql.toString());
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setLong(1, prod.getCidx());
				pstmt.setString(2, prod.getPname());
				pstmt.setString(3, prod.getComm());
				pstmt.setLong(4, prod.getPrice());
				pstmt.setInt(5, prod.getMaketm());
				pstmt.setLong(6, prod.getRecomm());
				pstmt.setInt(7, prod.getStatus());
				pstmt.setLong(8, prod.getPidx());
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
	} // end updateProduct
	
	// 삭제할 파일명을 가져온다.
	public String getFileName(int prodNum)
	{
		String fileName = null;
		
		try {
			conn = DBConnection.getConnection();
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT IMAGE from PRODUCT where PIDX=?");
			
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setInt(1, prodNum);
			
			rs = pstmt.executeQuery();
			if(rs.next()) fileName = rs.getString("IMAGE");
			DBConnection.close(rs);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return fileName;
	} // end getFileName
	
	// 상품 삭제
	public boolean deleteProduct(int prodNum) 
	{
		boolean result = false;

		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false); // 자동 커밋을 false로 한다.

			StringBuffer sql = new StringBuffer();
			sql.append("DELETE FROM PRODUCT");
			sql.append(" WHERE PIDX = ?");
			
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setInt(1, prodNum);
			
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
	} // end deleteProduct
	
	public int getProdUseCnt(int prodNum) {
		int cnt = 0;
		
		try {
			conn = DBConnection.getConnection();
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(*) cnt ");
			sql.append(" from  ORDERITEM  ");
			sql.append("where PIDX=?");
			
			//pstmt = conn.prepareStatement(sql.toString());
			pstmt = DBConnection.getPstmt(conn, sql.toString());
			pstmt.setInt(1, prodNum);
			
			rs = pstmt.executeQuery();
			if(rs.next()) cnt = rs.getInt("cnt");
			DBConnection.close(rs);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return cnt;		
	}
	public ArrayList<Product> memProductList(HashMap<String,Object> listOpt) {
		ArrayList<Product> prodList = null;
		String opt = (String)listOpt.get("opt");
		String condition = (String)listOpt.get("condition");
		
		int start = (Integer)listOpt.get("start");
		
		try {
				
			if(opt == null) {
				conn = DBConnection.getConnection();
				StringBuffer sql = new StringBuffer();
				prodList = new ArrayList<Product>();
				int cidx = (Integer)listOpt.get("cidx");
				
				sql.append("SELECT * FROM ");
				sql.append("(SELECT  ROWNUM AS rnum, data.* FROM ");
				sql.append("(SELECT ");
				sql.append("p.PIDX, c.CNAME, PNAME, IMAGE, ");
				sql.append("PRICE, MAKETM, RECOMM, P.CIDX, ");
				sql.append("p.STATUS, p.CREATEDT, ");
				sql.append("(select nvl((sum(pcpoint)/count(*)),0) from PRODCOMM where pidx=p.pidx) as pavg ");
				sql.append("FROM PRODUCT p ");
				sql.append("left join CATEGORY c ");
				sql.append("on p.cidx = c.cidx ");
				sql.append("WHERE p.cidx=? and p.status = 1 ");
				sql.append(" ORDER BY PIDX desc ");
				sql.append(") ");
				sql.append(" data) ");
				sql.append("WHERE rnum >=? and rnum <=?");
	
				pstmt = DBConnection.getPstmt(conn, sql.toString());
				pstmt.setLong(1, cidx);
				pstmt.setInt(2, start);
				pstmt.setInt(3, start+100);
				sql.delete(0, sql.toString().length());
			}
				
			rs = pstmt.executeQuery();
			while(rs.next()) 
			{
				Product prod = new Product();
				prod.setPidx(rs.getLong("PIDX"));
				prod.setCidx(rs.getLong("CIDX"));
				prod.setCateName(rs.getString("CNAME"));
				prod.setPname(rs.getString("PNAME"));
				prod.setImage(rs.getString("IMAGE"));
				prod.setPrice(rs.getInt("PRICE"));
				prod.setMaketm(rs.getInt("MAKETM"));
				prod.setRecomm(rs.getInt("RECOMM"));
				prod.setStatus(rs.getInt("STATUS"));
				prod.setCreatedt(rs.getDate("CREATEDT"));
				prod.setPcPointAvg(rs.getFloat("PAVG"));
				prodList.add(prod);
			}
			DBConnection.close(rs);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			DBConnection.close(pstmt);
			DBConnection.close(conn);
		}
		return prodList;
	}
}
