package com.belle.infrastructure.spring;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

public class DataSourceSwitcher {

	/**
	 * logger
	 */
	private static final Logger logger = Logger.getLogger(DataSourceSwitcher.class);

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static void setDataSource(String dataSource) {
		Assert.notNull(dataSource, "dataSource cannot be null");
		contextHolder.set(dataSource);
	}

	public static void setMaster(){
		clearDataSource();
		setDataSource("master");
		logger.info("启用主数据库连接Master");
    }

	public static void setSlave() {
		clearDataSource();
		setDataSource("slave1");
		logger.info("启用从数据库连接"+"slave1");
	}

	public static void setDataSourceSys(){
		clearDataSource();
		setDataSource("dataSys");
		logger.info("启用系统数据库连接dataSys");
	}
	
	public static String getDataSource() {
		String dataSource = contextHolder.get();
		if(dataSource == null)
			dataSource = "slave1";
		logger.debug("启用数据库连接 "+dataSource);
		return dataSource;
	}

	public static void clearDataSource() {
		contextHolder.remove();
	}
}


