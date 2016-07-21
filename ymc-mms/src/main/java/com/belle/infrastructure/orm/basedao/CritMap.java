package com.belle.infrastructure.orm.basedao;

//by will
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class CritMap {
	
	/**
	 * 记录日志
	 */
	private Logger logger=Logger.getLogger(CritMap.class);

	private int maxSize;// 获取数据条件

	/** 属性比较类型. */
	public enum MatchType {
		IS_NULL, // 判断是null
		IS_NONULL, // 判断是not null
		EQUAL, // 等于
		NOT_EQUAL,//不等于
		L_LIKE, // 左模糊查询
		R_LIKE, // 右模糊查询
		LIKE, // 全模糊查询
		GREATER, // 大于
		LESS, // 小于
		GREATER_EQUAL, // 大于等于
		LESS_EQUAL, // 小于等于
		ORDER_ASC, // 升序
		ORDER_DESC, // 降序
		FECH, // 级联
		ALIASFECH, // 别名级联
		IN; //
	}

	Map<String, Map<String, Object>> rootMap;

	public CritMap() {
		rootMap = new ConcurrentHashMap<String, Map<String, Object>>();
		rootMap.put(MatchType.IS_NULL.name(), new ConcurrentHashMap<String, Object>());
		rootMap.put(MatchType.IS_NONULL.name(), new ConcurrentHashMap<String, Object>());
		rootMap.put(MatchType.EQUAL.name(), new ConcurrentHashMap<String, Object>());
		rootMap.put(MatchType.NOT_EQUAL.name(), new ConcurrentHashMap<String, Object>());
		rootMap.put(MatchType.L_LIKE.name(), new ConcurrentHashMap<String, Object>());
		rootMap.put(MatchType.R_LIKE.name(), new ConcurrentHashMap<String, Object>());
		rootMap.put(MatchType.LIKE.name(), new ConcurrentHashMap<String, Object>());
		rootMap.put(MatchType.GREATER.name(), new ConcurrentHashMap<String, Object>());
		rootMap.put(MatchType.GREATER_EQUAL.name(), new ConcurrentHashMap<String, Object>());
		rootMap.put(MatchType.LESS.name(), new ConcurrentHashMap<String, Object>());
		rootMap.put(MatchType.LESS_EQUAL.name(), new ConcurrentHashMap<String, Object>());
		rootMap.put(MatchType.ORDER_ASC.name(), new ConcurrentHashMap<String, Object>());
		rootMap.put(MatchType.ORDER_DESC.name(), new ConcurrentHashMap<String, Object>());
		rootMap.put(MatchType.FECH.name(), new ConcurrentHashMap<String, Object>());
		rootMap.put(MatchType.ALIASFECH.name(), new ConcurrentHashMap<String, Object>());
		rootMap.put(MatchType.IN.name(), new ConcurrentHashMap<String, Object>());
	}

	// 判断是NULL
	public void addIsNull(String fieldNme) {
		if(StringUtils.isBlank(fieldNme)){
			logger.error("引用CritMap.java中addIsNull()时传入参数为空！");
		}
		(rootMap.get(MatchType.IS_NULL.name())).put(fieldNme, fieldNme);
	}

	// 判断是not NULL
	public void addIsNoNull(String fieldNme) {
		if(StringUtils.isBlank(fieldNme)){
			logger.error("引用CritMap.java中addIsNoNull()时传入参数为空！");
		}
		(rootMap.get(MatchType.IS_NONULL.name())).put(fieldNme, fieldNme);
	}

	public void addEqual(String fieldNme, Object value) {
		if(StringUtils.isBlank(fieldNme)){
			logger.error("引用CritMap.java中addEqual()时传入参数为空！");
		}
		(rootMap.get(MatchType.EQUAL.name())).put(fieldNme, value);
	}
	
	public void addNotEqual(String fieldNme, Object value) {
		if(StringUtils.isBlank(fieldNme)){
			logger.error("引用CritMap.java中addNotEqual()时传入参数为空！");
		}
		(rootMap.get(MatchType.NOT_EQUAL.name())).put(fieldNme, value);
	}

	public void addRightLike(String fieldNme, String value) {
		if(StringUtils.isBlank(fieldNme)){
			logger.error("引用CritMap.java中addRightLike()时传入参数为空！");
		}
		rootMap.get(MatchType.R_LIKE.name()).put(fieldNme, value);
	}

	public void addLeftLike(String fieldNme, String value) {
		if(StringUtils.isBlank(fieldNme)){
			logger.error("引用CritMap.java中addLeftLike()时传入参数为空！");
		}
		rootMap.get(MatchType.L_LIKE.name()).put(fieldNme, value);
	}

	public void addLike(String fieldNme, String value) {
		if(StringUtils.isBlank(fieldNme)){
			logger.error("引用CritMap.java中addLike()时传入参数为空！");
		}
		rootMap.get(MatchType.LIKE.name()).put(fieldNme, value);
	}

	public void addGreat(String fieldNme, Object value) {
		if(StringUtils.isBlank(fieldNme)){
			logger.error("引用CritMap.java中addGreat()时传入参数为空！");
		}
		rootMap.get(MatchType.GREATER.name()).put(fieldNme, value);
	}

	public void addGreatAndEq(String fieldNme, Object value) {
		if(StringUtils.isBlank(fieldNme)){
			logger.error("引用CritMap.java中addGreatAndEq()时传入参数为空！");
		}
		rootMap.get(MatchType.GREATER_EQUAL.name()).put(fieldNme, value);
	}

	public void addLess(String fieldNme, Object value) {
		if(StringUtils.isBlank(fieldNme)){
			logger.error("引用CritMap.java中addLess()时传入参数为空！");
		}
		rootMap.get(MatchType.LESS.name()).put(fieldNme, value);
	}

	public void addLessAndEq(String fieldNme, Object value) {
		if(StringUtils.isBlank(fieldNme)){
			logger.error("引用CritMap.java中addLessAndEq()时传入参数为空！");
		}
		rootMap.get(MatchType.LESS_EQUAL.name()).put(fieldNme, value);
	}

	public void addAsc(String fieldNme) {
		if(StringUtils.isBlank(fieldNme)){
			logger.error("引用CritMap.java中addAsc()时传入参数为空！");
		}
		rootMap.get(MatchType.ORDER_ASC.name()).put(fieldNme, fieldNme);
	}

	public void addDesc(String fieldNme) {
		if(StringUtils.isBlank(fieldNme)){
			logger.error("引用CritMap.java中addDesc()时传入参数为空！");
		}
		rootMap.get(MatchType.ORDER_DESC.name()).put(fieldNme, fieldNme);
	}

	public void addFech(String fechTableName) {
		if(StringUtils.isBlank(fechTableName)){
			logger.error("引用CritMap.java中addFech()时传入参数为空！");
		}
		rootMap.get(MatchType.FECH.name()).put(fechTableName, fechTableName);
	}

	public void addMaxSize(int size) {
		this.maxSize = size;
	}

	public int getMaxSize() {
		return this.maxSize;
	}

	/**
	 * 设置别名 则可进行子查询
	 * 
	 * @param alias
	 *            关联对象别名
	 * @param fechTableName
	 *            关联对象名
	 */
	public void addFech(String alias, String fechTableName) {
		rootMap.get(MatchType.ALIASFECH.name()).put(alias, fechTableName);
	}

	/**
	 * 设置IN 可进行多字段包含查询
	 * 
	 * @param alias
	 *            关联字段
	 * @param object
	 *            字段值集合
	 * @author dsy
	 * @date 2011-04-21
	 */
	public void addIN(String fieldNme, Object object) {
		rootMap.get(MatchType.IN.name()).put(fieldNme, object);
	}

	public Map<String, Object> getFieldMap(String str) {
		return rootMap.get(str);
	}

	public static void main(String[] args) {
	}

}
