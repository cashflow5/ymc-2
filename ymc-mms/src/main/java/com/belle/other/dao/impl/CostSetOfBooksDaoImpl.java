package com.belle.other.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.belle.finance.costsettlement.costsetofbooks.model.vo.CostSetofBooks;
import com.belle.other.dao.ICostSetOfBooksDao;
import com.belle.other.util.JDBCBatchUpdateUtils;

@Repository
public class CostSetOfBooksDaoImpl implements ICostSetOfBooksDao{
	
	/**
	 * 查询所有的成本帐套
	 */
	@Override
	public List<CostSetofBooks> queryAllCostSetOfBooks() throws Exception{
		
		StringBuffer sql = new StringBuffer(" SELECT id,set_of_books_code,set_of_books_name,creator,create_time,is_del ");
		sql.append(" FROM tbl_fin_cost_set_of_books ");
		sql.append(" WHERE is_del = 0 ");
		sql.append(" ORDER BY create_time DESC ");
		
		List<CostSetofBooks> listCostSetOfBooks = new ArrayList<CostSetofBooks>();
		CostSetofBooks costSetOfBooks = null;
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try { 
			
			 conn = JDBCBatchUpdateUtils.getInstance().getQueryConnection();
	         ps = conn.prepareStatement(sql.toString());
	         rs = ps.executeQuery();
	        
	         while(rs.next()){
	        	 costSetOfBooks = new CostSetofBooks();
	        	 costSetOfBooks.setId(rs.getString("id"));
	        	 costSetOfBooks.setSetOfBooksCode(rs.getString("set_of_books_code"));
	        	 costSetOfBooks.setSetOfBooksName(rs.getString("set_of_books_name"));
	        	 costSetOfBooks.setCreator(rs.getString("creator"));
	        	 costSetOfBooks.setCreateTime(rs.getTimestamp("create_time"));
	        	 costSetOfBooks.setIsDel(rs.getInt("is_del"));
	        	 
	        	 listCostSetOfBooks.add(costSetOfBooks);
	         }
	         
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != rs){
				rs.close();
			}
			if(null != ps){
				ps.close();
			}
			if(null!=conn){
				conn.close();
			}
		}
		
		return listCostSetOfBooks;
	}
	
	/**
	 * 查询所有的成本帐套
	 * 
	 * 当 isTemp = true时，排除临时帐套的
	 */
	@Override
	public List<CostSetofBooks> queryAllCostSetOfBooks(boolean isTemp) throws Exception{
		
		StringBuffer sql = new StringBuffer(" SELECT id,set_of_books_code,set_of_books_name,creator,create_time,is_del ");
		sql.append(" FROM tbl_fin_cost_set_of_books ");
		sql.append(" WHERE is_del = 0 ");
//		sql.append(" AND set_of_books_name NOT LIKE '%临时%'");
		sql.append(" AND set_of_books_code != 'ZT20120706694264'");//不包含临时帐套的
		sql.append(" ORDER BY create_time DESC ");
		
		List<CostSetofBooks> listCostSetOfBooks = new ArrayList<CostSetofBooks>();
		CostSetofBooks costSetOfBooks = null;
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try { 
			
			 conn = JDBCBatchUpdateUtils.getInstance().getQueryConnection();
	         ps = conn.prepareStatement(sql.toString());
	         rs = ps.executeQuery();
	        
	         while(rs.next()){
	        	 costSetOfBooks = new CostSetofBooks();
	        	 costSetOfBooks.setId(rs.getString("id"));
	        	 costSetOfBooks.setSetOfBooksCode(rs.getString("set_of_books_code"));
	        	 costSetOfBooks.setSetOfBooksName(rs.getString("set_of_books_name"));
	        	 costSetOfBooks.setCreator(rs.getString("creator"));
	        	 costSetOfBooks.setCreateTime(rs.getTimestamp("create_time"));
	        	 costSetOfBooks.setIsDel(rs.getInt("is_del"));
	        	 
	        	 listCostSetOfBooks.add(costSetOfBooks);
	         }
	         
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != rs){
				rs.close();
			}
			if(null != ps){
				ps.close();
			}
			if(null!=conn){
				conn.close();
			}
		}
		
		return listCostSetOfBooks;
	}

	@Override
	public CostSetofBooks queryCostSetOfBooksById(String id) throws Exception{
		
		StringBuffer sql = new StringBuffer(" SELECT id,set_of_books_code,set_of_books_name,creator,create_time,is_del ");
		sql.append(" FROM tbl_fin_cost_set_of_books ");
		sql.append(" WHERE id = ? ");
		
		CostSetofBooks costSetOfBooks = null;
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try { 
			
			 conn = JDBCBatchUpdateUtils.getInstance().getQueryConnection();
	         ps = conn.prepareStatement(sql.toString());
	         ps.setString(1, id);
	         rs = ps.executeQuery();
	        
	         if(rs.next()){
	        	 costSetOfBooks = new CostSetofBooks();
	        	 costSetOfBooks.setId(rs.getString("id"));
	        	 costSetOfBooks.setSetOfBooksCode(rs.getString("set_of_books_code"));
	        	 costSetOfBooks.setSetOfBooksName(rs.getString("set_of_books_name"));
	        	 costSetOfBooks.setCreator(rs.getString("creator"));
	        	 costSetOfBooks.setCreateTime(rs.getDate("create_time"));
	        	 costSetOfBooks.setIsDel(rs.getInt("is_del"));
	         }
	         
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != rs){
				rs.close();
			}
			if(null != ps){
				ps.close();
			}
			if(null!=conn){
				conn.close();
			}
		}
		
		return costSetOfBooks;
	}
	
	@Override
	public CostSetofBooks queryCostSetofBooksByCode(String setOfBooksCode) throws Exception{
		StringBuffer sql = new StringBuffer(" SELECT id,set_of_books_code,set_of_books_name,creator,create_time,is_del ");
		sql.append(" FROM tbl_fin_cost_set_of_books ");
		sql.append(" WHERE is_del = 0");
		if(StringUtils.isNotBlank(setOfBooksCode)){
			sql.append(" AND set_of_books_code = '").append(setOfBooksCode.trim()).append("'");
		}
		
		CostSetofBooks costSetOfBooks = null;
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try { 
			
			 conn = JDBCBatchUpdateUtils.getInstance().getQueryConnection();
	         ps = conn.prepareStatement(sql.toString());
	         rs = ps.executeQuery();
	        
	         if(rs.next()){
	        	 costSetOfBooks = new CostSetofBooks();
	        	 costSetOfBooks.setId(rs.getString("id"));
	        	 costSetOfBooks.setSetOfBooksCode(rs.getString("set_of_books_code"));
	        	 costSetOfBooks.setSetOfBooksName(rs.getString("set_of_books_name"));
	        	 costSetOfBooks.setCreator(rs.getString("creator"));
	        	 costSetOfBooks.setCreateTime(rs.getDate("create_time"));
	        	 costSetOfBooks.setIsDel(rs.getInt("is_del"));
	         }
	         
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != rs){
				rs.close();
			}
			if(null != ps){
				ps.close();
			}
			if(null!=conn){
				conn.close();
			}
		}
		return costSetOfBooks;

	}

	@Override
	public List<CostSetofBooks> queryAllBuyOutCostSetOfBooks() throws Exception {
		
		StringBuffer sql = new StringBuffer(" SELECT id,set_of_books_code,set_of_books_name,creator,create_time,is_del ");
		sql.append(" FROM tbl_fin_cost_set_of_books ");
		sql.append(" WHERE is_del = 0 ");
		sql.append(" AND set_of_books_name LIKE '%买断%'");
//		sql.append(" AND set_of_books_code != 'ZT20120706694264'");//不包含临时帐套的
		sql.append(" ORDER BY create_time DESC ");
		
		List<CostSetofBooks> listCostSetOfBooks = new ArrayList<CostSetofBooks>();
		CostSetofBooks costSetOfBooks = null;
		
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		
		try { 
			
			 conn = JDBCBatchUpdateUtils.getInstance().getQueryConnection();
	         ps = conn.prepareStatement(sql.toString());
	         rs = ps.executeQuery();
	        
	         while(rs.next()){
	        	 costSetOfBooks = new CostSetofBooks();
	        	 costSetOfBooks.setId(rs.getString("id"));
	        	 costSetOfBooks.setSetOfBooksCode(rs.getString("set_of_books_code"));
	        	 costSetOfBooks.setSetOfBooksName(rs.getString("set_of_books_name"));
	        	 costSetOfBooks.setCreator(rs.getString("creator"));
	        	 costSetOfBooks.setCreateTime(rs.getTimestamp("create_time"));
	        	 costSetOfBooks.setIsDel(rs.getInt("is_del"));
	        	 
	        	 listCostSetOfBooks.add(costSetOfBooks);
	         }
	         
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != rs){
				rs.close();
			}
			if(null != ps){
				ps.close();
			}
			if(null!=conn){
				conn.close();
			}
		}
		
		return listCostSetOfBooks;
	}
	
}
