package com.belle.other.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.yougou.tools.common.utils.ServiceLocator;

public class JDBCBatchUpdateUtils {


	private static final Logger logger = Logger.getLogger(JDBCBatchUpdateUtils.class);

	private static JDBCBatchUpdateUtils jdbcUtils = null;
	
	private String batch_size ="200"; //批量提交数
	
	public static synchronized JDBCBatchUpdateUtils getInstance() {

		if (jdbcUtils == null) {
			jdbcUtils = new JDBCBatchUpdateUtils();
		}
		return jdbcUtils;
	}
	
	
	public Connection getConnection() throws Exception {
		try {
			ServiceLocator serviceLocator = ServiceLocator.getInstance();
			DataSource dataSource = (DataSource) serviceLocator.getBeanFactory().getBean("dataSource1");
			Connection conn = dataSource.getConnection();			
			return conn;
		} catch (Exception e) {
			logger.error("获取数据库连接失败！",e);
			throw new Exception();
		}
	}
	
	
	public Connection getQueryConnection() throws Exception {
		try {
			ServiceLocator serviceLocator = ServiceLocator.getInstance();
			DataSource dataSource = (DataSource) serviceLocator.getBeanFactory().getBean("dataSource_query");
			Connection conn = dataSource.getConnection();			
			return conn;
		} catch (Exception e) {
			logger.error("获取查询数据库连接失败！",e);
			throw new Exception();
		}
	}
	
	
	public boolean batchSaveOrUpdate(String sql, List<Object[]> paramList) {
		PreparedStatement pstmt = null;
		int count = 0;
		Connection conn = null;
		try {
			conn = getConnection(); // 获取数据库连接
			count = Integer.parseInt(batch_size);
			conn.setAutoCommit(false); // 设置手动提交事务
			pstmt = conn.prepareStatement(sql); // 创建PreparedStatement对象
			// 赋值
			for (int i = 0; i < paramList.size(); i++) {

				Object[] values = paramList.get(i);
				for (int j = 0; j < values.length; j++) {
					pstmt.setObject(j + 1, values[j]);
				}
				pstmt.addBatch();

				// 批量数等于 batch_size 时 提交数据
				if (i != 0 && ((i+1) % count == 0)) {
					int ids[] = pstmt.executeBatch(); // 执行操作
					if (ids.length == count) {
						conn.commit(); // 提交事务
					} else {
						conn.rollback(); // 事务回滚
					}
					pstmt.clearBatch();
				}
			}

			int ids[] = pstmt.executeBatch(); // 执行操作
			if (ids.length == paramList.size() % count) {
				conn.commit(); // 提交事务
			} else {
				conn.rollback(); // 事务回滚
			}
		} catch (Exception e) {
			logger.error("batchSaveOrUpdate失败！",e);
			return false; // 如果异常就返回false
		} finally {
			close(pstmt, conn); // 关闭相关连接
		}
		return true;
	}
	
	private void close(PreparedStatement pstmt, Connection conn) {
		try {
			if (pstmt != null)
				pstmt.close();
		} catch (SQLException e) {
			logger.error("close失败！",e);
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				logger.error("close失败！",e);
			}
		}
	}
	
	
	/**
	 * 关闭数据库相关连接
	 * 
	 * @param connection
	 */
	public void close(ResultSet rs, Statement st, Connection conn) {
		try {
			if (rs != null)
				rs.close();
			rs = null;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null)
					st.close();
				st = null;
			} catch (SQLException e) {
				logger.error("close失败！",e);
			} finally {
				try {
					if (conn != null)
						conn.close();
					conn = null;
				} catch (SQLException e) {
					logger.error("close失败！",e);
				}
			}
		}
	}
	
	public boolean batchAllSaveOrUpdate(Map<String, List<Object[]>> map) throws Exception {
		if (map == null || map.size() == 0)
			return false;
		try {
			for (Iterator<Entry<String, List<Object[]>>> iter = map.entrySet().iterator(); iter.hasNext();) {
				Entry<String, List<Object[]>> entry = iter.next();
				// 动态获取preparedstatement对象
				String sql =entry.getKey();
				List<Object[]> paramList = entry.getValue();
				boolean flag= batchSaveOrUpdate(sql, paramList);
				if(!flag ){
					return false;
				}
			}
//			conn.commit();
		} catch (Exception e) {
			logger.error("batchAllSaveOrUpdate失败！",e);
			return false;
		} 
		return true;
	}
	
	
	public static int getCountFromSql(String sql) throws Exception{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int i = 0;
		try {
			conn= JDBCBatchUpdateUtils.getInstance().getQueryConnection();
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				i= rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCBatchUpdateUtils.getInstance().close(rs, ps, conn);
		}
		
		return i;
	}
}
