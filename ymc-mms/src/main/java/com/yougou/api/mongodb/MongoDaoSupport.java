package com.yougou.api.mongodb;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

/**
 * MongoDB 数据访问对象支持类
 * 
 * @author 杨梦清
 * 
 */
@Component
public abstract class MongoDaoSupport extends DaoSupport implements BeanFactoryAware {

	/** Mongo 连接对象 **/
	private Mongo mongo;
	/** Mongo 数据库对象 **/
	private DB db;
	/** Mongo 数据库名称 **/
	private String dbName;
	/** Mongo 用户名称 **/
	private String userName;
	/** Mongo 用户口令 **/
	private String passwd;
	/** Mongo 是否记录日志 **/
	private boolean debuggable;
	/** SpringBean 工厂 **/
	private BeanFactory beanFactory;
	/** Mongo 数据库连接是否已认证 **/
	private boolean authenticated;
	
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public void setDebuggable(boolean debuggable) {
		this.debuggable = debuggable;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}
	
	@Override
	protected void checkDaoConfig() throws IllegalArgumentException {
		if (mongo == null && ListableBeanFactory.class.isInstance(beanFactory)) {
			mongo = BeanFactoryUtils.beanOfType(ListableBeanFactory.class.cast(beanFactory), Mongo.class);
		}
		Assert.notNull(mongo, "Get '" + Mongo.class.getName() + "' object can not be null.");
		
		// off/on mongodb debug
		if (debuggable) {
			System.setProperty("DEBUG.DB", Boolean.toString(debuggable));
			System.setProperty("DB.TRACE", Boolean.toString(debuggable));
			Logger.getLogger("com.mongodb.TRACE").setLevel(Level.INFO);
		}
		
		if (db == null) {
			db = mongo.getDB(dbName);
		}
		Assert.notNull(db, "Get '" + (DB.class.getName() + "@" + dbName) + "' object can not be null.");
		
		authenticate();
	}
	
	/**
	 * 获取 MongoDB 文档数据库
	 * 
	 * @return DB
	 */
	protected DB getDb() {
		authenticate();
		return db;
	}
	
	/**
	 * 认证口令
	 */
	protected void authenticate() {
		synchronized (db) {
			if (!authenticated && StringUtils.hasText(userName) && StringUtils.hasText(passwd))  {
				try {
					authenticated = db.authenticate(userName, passwd.toCharArray());
				} catch (MongoException ex) {
					if (!WriteConcern.NONE.equals(mongo.getWriteConcern())) {
						throw ex;
					}
					logger.error("Ignore 'MongoException' because mongo WriteConcern is 'NONE' for the current environment.", ex);
				} finally {
					logger.info("MongoDB authenticate: " + authenticated);
				}
			}
		}
	}
}
