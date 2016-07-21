package com.belle.other.service;

import java.util.List;
import java.util.Map;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;

/**
 * JDBC操作数据库（写这个方法的原因  hibernate返回Map的时候  如果两张表中有相同的列名  就会报错）
 * @author liuwenjun
 * create time 2012-1-6
 */
public interface ISqlService {
	/**
	 * 查询多条记录
	 * creator liuwenjun
	 * create time 2012-7-30 下午04:51:22
	 * @param sql
	 * @param sqlWhere
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> getDatasBySql(String sql,
			StringBuffer sqlWhere, List<Object> params) ;
	/**
	 * 查询多条记录
	 * creator liuwenjun
	 * create time 2012-7-30 下午04:51:02
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> getDatasBySql(String sql);
	/**
	 * 查询单挑数据
	 * creator liuwenjun
	 * create time 2012-7-30 下午04:39:38
	 * @param sql
	 * @return
	 */
	public Map<String, Object> getDataBySql(String sql);
	/**
	 * 分页查询DISTINCT
	 * creator liuwenjun
	 * create time 2012-6-18 上午11:32:51
	 * @param sql
	 * @param query
	 * @param sqlWhere
	 * @param params
	 * @param orderBy
	 * @return
	 */
	public PageFinder<Map<String, Object>> getDISTINCTObjectsBySql(String sql,Query query,
			StringBuffer sqlWhere, List<Object> params, String orderBy) ;
	/**
	 * 批量更新 or 保存
	 * @param map key是sql value是sql里面对应的参数值
	 * @throws Exception
	 */
	public boolean batchAllSaveOrUpdate(Map<String, List<Object[]>> map) throws Exception ;
	/**
	 * 删除记录
	 * creator liuwenjun
	 * create time 2012-1-13 上午09:40:56
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public boolean deleteObject(String sql) throws Exception;
	/**
	 * 删除记录
	 * creator liuwenjun
	 * create time 2012-1-12 下午02:19:06
	 * @param sql
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public boolean deleteObject(String sql, Object[] param)throws Exception  ;
	/**
	 * 更新单条记录
	 * creator liuwenjun
	 * create time 2012-1-12 下午02:18:55
	 * @param sql
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public boolean updateObject(String sql, Object[] param)throws Exception  ;
	/**
	 * 更新多条记录
	 * creator liuwenjun
	 * create time 2012-1-12 下午02:18:35
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public boolean updateObjects(String sql, List<Object[]> params) throws Exception ;
	/**
	 * 插入单条数据
	 * creator liuwenjun
	 * create time 2012-1-12 下午12:14:27
	 * @param sql
	 * @param param
	 * @return
	 */
	public boolean insertObject(String sql, Object[] param)  throws Exception;
	/**
	 * 批量添加数据
	 * creator liuwenjun
	 * create time 2012-1-12 下午12:02:33
	 * @param sql
	 * @param values
	 * @return
	 * @throws Exception
	 */
	public boolean insertObjects(String sql, List<Object[]> params) throws Exception ;

	/**
	 * sql分页查询
	 * creator liuwenjun
	 * create time 2012-1-6 下午04:17:25
	 * @param sql sql语句 记得在后面加上where 1=1 
	 * @param sqlWhere sql的条件 如 and t1.order_no like ?
	 * @param params   sqlwhere条件对应的参数 " A% "
	 * @param orderBy 排序条件 " t1.create_time desc "
	 * @return
	 */
	public PageFinder<Map<String, Object>> getObjectsBySql(String sql, Query query, StringBuffer sqlWhere, List<Object> params, String orderBy) ;
	
	/**
	 * sql分页查询
	 * creator daixiaowei
	 * create time 2012-1-6 下午04:17:25
	 * @param sql sql语句 记得在后面加上where 1=1 
	 * @param sqlWhere sql的条件 如 and t1.order_no like ?
	 * @param params   sqlwhere条件对应的参数 " A% "
	 * @param orderBy 排序条件 " t1.create_time desc "
	 * @param total 传入总记录数
	 * @return
	 */
	public PageFinder<Map<String, Object>> getObjectsBySql(String sql,Query query,
			StringBuffer sqlWhere,int total, List<Object> params, String orderBy);
	
	/**
	 * 通过条件查询总记录数  (用户导出数据用)
	 * creator liuwenjun
	 * create time 2012-1-6 下午06:05:00
	 * @param sql sql语句 记得在后面加上where 1=1 
	 * @param sqlWhere sql的条件 如 and t1.order_no like ?
	 * @param params   sqlwhere条件对应的参数 " A% "
	 * @return
	 */
	public Long getCountBySql(String sql,StringBuffer sqlWhere, List<Object> params) ;
	
	/**
	 * 通过条件查询总记录 (用户导出数据用)
	 * creator liuwenjun
	 * create time 2012-1-6 下午06:05:31
	 * @param sql sql语句 记得在后面加上where 1=1 
	 * @param sqlWhere sql的条件 如 and t1.order_no like ?
	 * @param params sqlwhere条件对应的参数 " A% "
	 * @param orderBy 排序条件 " t1.create_time desc "
	 * @return
	 */
	public List<Map<String, Object>> getDatasBySql(String sql,StringBuffer sqlWhere, List<Object> params, String orderBy) ;
	/**
	 * 得到单个对象 转化成Map
	 * creator liuwenjun
	 * create time 2012-1-12 下午12:24:00
	 * @param sql sql语句 记得在后面加上where 1=1 
	 * @param sqlWhere sql的条件 如 and t1.order_no like ?
	 * @param params sqlwhere条件对应的参数 " A% "
	 * @return
	 */
	public Map<String,Object> getDataBySql(String sql,StringBuffer sqlWhere, List<Object> params) ;
	
	/**
	 * 批量更新
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public boolean updatetObjects(String sql, List<Object[]> params)
			throws Exception ;
}
