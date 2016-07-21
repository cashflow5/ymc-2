package com.belle.other.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Service;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.other.service.ISqlService;

/**
 * sql操作service
 * @author liuwenjun
 * create time 2012-1-9
 */
@Service
public class SqlServiceSupport implements ISqlService {
	private static final Logger logger = Logger.getLogger(DaoSupport.class);
	
	@Resource(name="dataSource1")
	private DataSource dataSource1;
	
	private String batch_size ="500"; //批量提交数
	
	
	public boolean deleteObject(String sql) throws Exception {
		return insertObject(sql, null);
	}
	
	@Override
	public boolean deleteObject(String sql, Object[] param) throws Exception {
		return insertObject(sql, param);
	}

	@Override
	public boolean updateObject(String sql, Object[] param) throws Exception {
		return insertObject(sql, param);
	}

	@Override
	public boolean updateObjects(String sql, List<Object[]> params)
			throws Exception {
		return insertObjects(sql, params);
	}

	@Override
	public boolean insertObject(String sql, Object[] param) throws Exception {
		List<Object[]> params = new ArrayList<Object[]>();
		params.add(param);
		return insertObjects(sql, params);
	}

	@Override
	public boolean insertObjects(String sql, List<Object[]> params)
			throws Exception {
		PreparedStatement pstmt = null;
		int count = 0;
		Connection conn = null;
		try {
			conn = getConnectionForMaster(); // 获取数据库连接
			count = Integer.parseInt(batch_size);
			conn.setAutoCommit(false); // 设置手动提交事务
			pstmt = conn.prepareStatement(sql); // 创建PreparedStatement对象
			// 赋值
			for (int i = 0; i < params.size(); i++) {
				Object[] values = params.get(i);
				for (int j = 0; j < values.length; j++) {
					pstmt.setObject(j + 1, values[j]);
				}
				pstmt.addBatch();
				// 批量数等于 batch_size 时 提交数据
				if (i != 0 && (i % count == 0)) {
					int ids[] = pstmt.executeBatch(); // 执行操作
					if (ids.length == count + 1) {
						conn.commit(); // 提交事务
					} else {
						conn.rollback(); // 事务回滚
					}
					pstmt.clearBatch();
				}
			}
			int ids[] = pstmt.executeBatch(); // 执行操作
			if (ids.length <= count) {
				conn.commit(); // 提交事务
			} else {
				conn.rollback(); // 事务回滚
			}
		} catch (Exception e) {
			logger.error("操作失败！",e);
			return false; // 如果异常就返回false
		} finally {
			close(conn,pstmt,null); // 关闭相关连接
		}
		return true;
	}
	
	@Override
	public PageFinder<Map<String, Object>> getDISTINCTObjectsBySql(String sql,Query query,
			StringBuffer sqlWhere, List<Object> params, String orderBy) {
		PageFinder<Map<String,Object>> pf = null;
		if(sqlWhere != null && sqlWhere.length()>0) {
			sql += sqlWhere.toString();
		}
		String countSql = "";
		sql = sql.toLowerCase();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection(); // 获取数据库连接
			if(logger.isInfoEnabled()) {
				logger.info("sql:>>>>>" + countSql);
			}
			//得到总得记录数
			int index = sql.lastIndexOf("from");
			if (index != -1) {
				countSql = "select count(DISTINCT t1.id) " + sql.substring(index);
			}
			pstmt = conn.prepareStatement(countSql);
			pstmt = setParams(pstmt, params);
			rs = pstmt.executeQuery();
			Object result = null;
			if (rs.next()) {
				result = rs.getObject(1);
			}
			int total = ((Long)result).intValue();
			
			if(total>0) {
				/** 得到当前结果集 start **/
				pf = new PageFinder<Map<String,Object>>(query
						.getPage(), query.getPageSize(), total);
				if(StringUtils.isNotBlank(orderBy)) {
					sql += " order by " + orderBy;
				}
				sql += " limit " + pf.getStartOfPage()+","+ query.getPageSize() ;
				pstmt = conn.prepareStatement(sql);
				pstmt = setParams(pstmt, params);
				rs = pstmt.executeQuery();
				if(logger.isInfoEnabled()) {
					logger.info("sql:>>>>>" + sql);
				}
				Map<String, Object> map = null;
				List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
				while (rs.next()) {
					map = new HashMap<String, Object>();
					ResultSetMetaData rsmd = rs.getMetaData();
					for (int i = 0; i < rsmd.getColumnCount();) {
						++i;
						String key = rsmd.getColumnLabel(i).toLowerCase();
						if (map.containsKey(key)) {
							throw new IllegalArgumentException("有两个相同key值  为：" + key);
						}
						map.put(key, rs.getObject(i));
					}
					maps.add(map);
				}
				pf.setData(maps);
			}
		} catch (Exception e) {
			logger.error("查询出错", e);
		} finally {
			close(conn, pstmt, rs);
		}

		return pf;
	}

	@Override
	public PageFinder<Map<String, Object>> getObjectsBySql(String sql,Query query,
			StringBuffer sqlWhere, List<Object> params, String orderBy) {
		PageFinder<Map<String,Object>> pf = null;
		if(sqlWhere != null && sqlWhere.length()>0) {
			sql += sqlWhere.toString();
		}
		String countSql = "";
		sql = sql.toLowerCase();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection(); // 获取数据库连接
			if(logger.isInfoEnabled()) {
				logger.info("sql:>>>>>" + countSql);
			}
			//得到总得记录数
			int index = sql.indexOf("from");
			if (index != -1) {
				countSql = "select count(*) " + sql.substring(index);
			}
			pstmt = conn.prepareStatement(countSql);
			pstmt = setParams(pstmt, params);
			rs = pstmt.executeQuery();
			Object result = null;
			if (rs.next()) {
				result = rs.getObject(1);
			}
			int total = ((Long)result).intValue();
			
			if(total>0) {
				/** 得到当前结果集 start **/
				pf = new PageFinder<Map<String,Object>>(query
						.getPage(), query.getPageSize(), total);
				if(StringUtils.isNotBlank(orderBy)) {
					sql += " order by " + orderBy;
				}
				sql += " limit " + pf.getStartOfPage()+","+ query.getPageSize() ;
				pstmt = conn.prepareStatement(sql);
				pstmt = setParams(pstmt, params);
				rs = pstmt.executeQuery();
				if(logger.isInfoEnabled()) {
					logger.info("sql:>>>>>" + sql);
				}
				Map<String, Object> map = null;
				List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
				while (rs.next()) {
					map = new HashMap<String, Object>();
					ResultSetMetaData rsmd = rs.getMetaData();
					for (int i = 0; i < rsmd.getColumnCount();) {
						++i;
						String key = rsmd.getColumnLabel(i).toLowerCase();
						if (map.containsKey(key)) {
							throw new IllegalArgumentException("有两个相同key值  为：" + key);
						}
						map.put(key, rs.getObject(i));
					}
					maps.add(map);
				}
				pf.setData(maps);
			}
		} catch (Exception e) {
			logger.error("查询出错", e);
		} finally {
			close(conn, pstmt, rs);
		}

		return pf;
	}
	
	@Override
	public PageFinder<Map<String, Object>> getObjectsBySql(String sql,Query query,
			StringBuffer sqlWhere,int total, List<Object> params, String orderBy) {
		PageFinder<Map<String,Object>> pf = null;
		if(sqlWhere != null && sqlWhere.length()>0) {
			sql += sqlWhere.toString();
		}
		sql = sql.toLowerCase();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection(); // 获取数据库连接
			if(total>0) {
				/** 得到当前结果集 start **/
				pf = new PageFinder<Map<String,Object>>(query
						.getPage(), query.getPageSize(), total);
				if(StringUtils.isNotBlank(orderBy)) {
					sql += " order by " + orderBy;
				}
				sql += " limit " + pf.getStartOfPage()+","+ query.getPageSize() ;
				pstmt = conn.prepareStatement(sql);
				pstmt = setParams(pstmt, params);
				rs = pstmt.executeQuery();
				if(logger.isInfoEnabled()) {
					logger.info("sql:>>>>>" + sql);
				}
				Map<String, Object> map = null;
				List<Map<String, Object>> maps = new ArrayList<Map<String,Object>>();
				while (rs.next()) {
					map = new HashMap<String, Object>();
					ResultSetMetaData rsmd = rs.getMetaData();
					for (int i = 0; i < rsmd.getColumnCount();) {
						++i;
						String key = rsmd.getColumnLabel(i).toLowerCase();
						if (map.containsKey(key)) {
							throw new IllegalArgumentException("有两个相同key值  为：" + key);
						}
						map.put(key, rs.getObject(i));
					}
					maps.add(map);
				}
				pf.setData(maps);
			}
		} catch (Exception e) {
			logger.error("查询出错", e);
		} finally {
			close(conn, pstmt, rs);
		}
		return pf;
	}
	
	public List<Map<String, Object>> getDatasBySql(String sql) {
		return this.getDatasBySql(sql, null, null);
	}
	public List<Map<String, Object>> getDatasBySql(String sql,
			StringBuffer sqlWhere, List<Object> params) {
		return this.getDatasBySql(sql, sqlWhere, params, null);
	}
	@Override
	public List<Map<String, Object>> getDatasBySql(String sql,
			StringBuffer sqlWhere, List<Object> params, String orderBy) {
		if(sqlWhere != null && sqlWhere.length()>0) {
			sql += sqlWhere.toString();
		}
		sql = sql.toLowerCase();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Map<String, Object>> maps = null;
		Connection conn = null;
		try {
			conn = getConnection(); // 获取数据库连接
			/** 得到当前结果集 start **/
			if(StringUtils.isNotBlank(orderBy)) {
				sql += " order by " + orderBy;
			}
			pstmt = conn.prepareStatement(sql);
			pstmt = setParams(pstmt, params);
			rs = pstmt.executeQuery();
			if(logger.isInfoEnabled()) {
				logger.info("sql:>>>>>" + sql);
			}
			Map<String, Object> map = null;
			maps = new ArrayList<Map<String,Object>>();
			while (rs.next()) {
				map = new HashMap<String, Object>();
				ResultSetMetaData rsmd = rs.getMetaData();
				for (int i = 0; i < rsmd.getColumnCount();) {
					++i;
					String key = rsmd.getColumnLabel(i).toLowerCase();
					if (map.containsKey(key)) {
						throw new IllegalArgumentException("有两个相同key值  为：" + key);
					}
					map.put(key, rs.getObject(i));
				}
				maps.add(map);
			}
		} catch (Exception e) {
			logger.error("出错", e);
		} finally {
			close(conn, pstmt, rs);
		}
		return maps;
	}
	
	public Map<String, Object> getDataBySql(String sql) {
		List<Map<String, Object>> datas = getDatasBySql(sql, null, null, null);
		Map<String, Object> data  = null;
		if(datas!=null && datas.size()>0) {
			data = datas.get(0);
		}
		return data;
	}
	
	@Override
	public Map<String, Object> getDataBySql(String sql, StringBuffer sqlWhere,
			List<Object> params) {
		List<Map<String, Object>> datas = getDatasBySql(sql, sqlWhere, params, null);
		Map<String, Object> data  = null;
		if(datas!=null && datas.size()>0) {
			data = datas.get(0);
		}
		return data;
	}

	@Override
	public Long getCountBySql(String sql, StringBuffer sqlWhere,
			List<Object> params) {
		if(sqlWhere != null && sqlWhere.length()>0) {
			sql += sqlWhere.toString();
		}
		sql = sql.toLowerCase();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Long count = 0l;
		try {
			conn = getConnection(); // 获取数据库连接
			if(logger.isInfoEnabled()) {
				logger.info("sql:>>>>>" + sql);
			}
			pstmt = conn.prepareStatement(sql);
			pstmt = setParams(pstmt, params);
			rs = pstmt.executeQuery();
			Object result = null;
			if (rs.next()) {
				result = rs.getObject(1);
			}
			if(result!=null){
				count = Long.valueOf(result.toString());
			}
			
		} catch (Exception e) {
			logger.error("出错", e);
		} finally {
			close(conn, pstmt, rs);
		}
		return count;
	}
	/**
	 * 批量更新 or 保存
	 * @param map key是sql value是sql里面对应的参数值
	 * @throws Exception
	 */
	public boolean batchAllSaveOrUpdate(Map<String, List<Object[]>> map) throws Exception {
		if (map == null || map.size() == 0)
			return false;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnectionForMaster(); // 获取数据库连接
			conn.setAutoCommit(false);
			for (Iterator<Entry<String, List<Object[]>>> iter = map.entrySet().iterator(); iter.hasNext();) {
				Entry<String, List<Object[]>> entry = iter.next();
				ps = conn.prepareStatement(entry.getKey());
				for (int i = 0; i < entry.getValue().size(); i++) {
					Object[] obj = entry.getValue().get(i);
					for (int j = 0; j < obj.length; j++) {
						ps.setObject(j + 1, obj[j]);
					}

					ps.addBatch();

					if (i == entry.getValue().size() - 1) {

						int[] num = ps.executeBatch();

						if (num.length != entry.getValue().size()) {
							throw new Exception("执行的数据记录不相等");
						}

						ps.clearBatch();

					}
				}
			}

			conn.commit();
		} catch (SQLException e) {
			logger.error("batchAllSaveOrUpdate失败！",e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				return false;
			}
			
			return false;
		} finally {
			close(conn,ps, null);
		}
		return true;
	}



	/**
	 * 设置参数 creator liuwenjun create time 2012-1-6 下午04:31:01
	 * 
	 * @param pstmt
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public PreparedStatement setParams(PreparedStatement pstmt,
			List<Object> params) throws SQLException {
		if (params != null && params.size() > 0) {
			for (int j = 0; j < params.size(); j++) {
				pstmt.setObject(j + 1, params.get(j));
			}
		}
		return pstmt;
	}

	private void close(Connection conn, Statement pstmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			logger.error("出错", e);
		}

		try {
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
		} catch (SQLException e) {
			logger.error("出错", e);
		}
		try {
			if (conn != null) {
				if (!conn.isClosed()) {
					conn.close();
				}
				conn = null;
			}
		} catch (SQLException e) {
			logger.error("出错", e);
		}
	}
	
	/**
	 * 获取 查询数据库连接
	 * 
	 * @return
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception {
		try {
			return dataSource1.getConnection();	
		} catch (Exception e) {
			logger.error("获取查询数据库连接失败！",e);
			throw new Exception();
		}
	}
	
	/**
	 * 获取主库数据库连接
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnectionForMaster() throws Exception{
		try {
			return dataSource1.getConnection();			
		} catch (Exception e) {
			logger.error("获取主数据库连接失败！",e);
			throw new Exception();
		}
	}
	
	@Override
	public boolean updatetObjects(String sql, List<Object[]> params)
			throws Exception {
		PreparedStatement pstmt = null;
		int count = 0; // 记录批量处理次数，100条提交一次
		Connection conn = null;
		try {
			conn = getConnectionForMaster(); // 获取数据库连接
			
			conn.setAutoCommit(false); // 设置手动提交事务
			pstmt = conn.prepareStatement(sql); // 创建PreparedStatement对象
			// 赋值
			for (int i = 0; i < params.size(); i++) {
				Object[] values = params.get(i);
				for (int j = 0; j < values.length; j++) {
					pstmt.setObject(j + 1, values[j]);
				}
				pstmt.addBatch();
				count++;
				//一百次提交一次事物
				if(count==100){
					int ids[] = pstmt.executeBatch(); // 执行操作
					if (ids.length == count) {
						conn.commit(); // 提交事务
					} else {
						conn.rollback(); // 事务回滚
						logger.error("批量更新数据时，出现有更新失败的，待更新的数据100条，成功的数据有："+ids.length+"条；事物回滚！");
					}
					pstmt.clearBatch();
					count=0;//提交一次事物后，count清0，从新计数
				}
			
			}
			int ids[] = pstmt.executeBatch(); // 执行操作
			if (ids.length == count) {
				conn.commit(); // 提交事务
			} else {
				conn.rollback(); // 事务回滚
				logger.error("批量更新数据时，出现有更新失败的，待更新的数据"+count+"条成功的数据有："+ids.length+"条；事物回滚！");
			}
		} catch (Exception e) {
			logger.error("操作失败！",e);
			return false; // 如果异常就返回false
		} finally {
			close(conn,pstmt,null); // 关闭相关连接
		}
		return true;
	}

}
