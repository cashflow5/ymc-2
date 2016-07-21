package com.yougou.kaidian.datasource;

import org.springframework.util.Assert;

public class DataSourceSwitcher {

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static void setDataSource(String dataSource) {
		Assert.notNull(dataSource, "dataSource cannot be null");
		contextHolder.set(dataSource);
	}

	public static void setMaster() {
		setDataSource("master");
	}

	public static void setSlave() {
		setDataSource("slave");
	}
	/**
	 * 新增数据报表数据源key 值
	 */
	public static void setReport() {
		setDataSource("report");
	}

	public static String getDataSource() {
		return (String) contextHolder.get();
	}

	public static void clearDataSource() {
		contextHolder.remove();
	}
}
