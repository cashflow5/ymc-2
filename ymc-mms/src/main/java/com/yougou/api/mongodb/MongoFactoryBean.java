package com.yougou.api.mongodb;

import java.util.List;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

/**
 * Mongo 工厂类
 * 
 * @author 杨梦清
 * 
 */
public class MongoFactoryBean implements FactoryBean<Mongo>, DisposableBean {

	/** Mongo 主机 **/
	private String host;
	/** Mongo 端口 **/
	private Integer port;
	/** Mongo 查询选项 **/
	private Integer options;
	/** 写入行为 **/
	private WriteConcern writeConcern;
	/** 读取偏好 **/
	private ReadPreference readPreference;
	/** 一主一从模式 **/
	private ServerAddressPair serverAddressPair;
	/** 一主多从模式 **/
	private List<ServerAddress> serverAddressList;
	
	// singleton instance
	private Mongo mongo;

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public void setOptions(Integer options) {
		this.options = options;
	}

	public void setWriteConcern(WriteConcern writeConcern) {
		this.writeConcern = writeConcern;
	}

	public void setReadPreference(ReadPreference readPreference) {
		this.readPreference = readPreference;
	}

	public void setServerAddressPair(ServerAddressPair serverAddressPair) {
		this.serverAddressPair = serverAddressPair;
	}

	public void setServerAddressList(List<ServerAddress> serverAddressList) {
		this.serverAddressList = serverAddressList;
	}

	@Override
	@SuppressWarnings("deprecation")
	public Mongo getObject() throws Exception {
		if (mongo == null) {
			// Mongo 属性(最大请求线程数=主机连接数*线程队列数)
			MongoOptions mongoOptions = new MongoOptions();
			// 失效自动连接
			mongoOptions.autoConnectRetry = true;
			// 最大自动连接时效
			mongoOptions.maxAutoConnectRetryTime = 5000L;
			// 主机连接数
			mongoOptions.connectionsPerHost = 20;
			// 线程队列数
			mongoOptions.threadsAllowedToBlockForConnectionMultiplier = 5;
			// 建立链接超时时间
			mongoOptions.connectTimeout = 10000;
			// 执行IO操作超时时间
			mongoOptions.socketTimeout = 45000;
			// 创建 Mongo 对象
			if (serverAddressPair != null) {
				mongo = new Mongo(serverAddressPair.master, serverAddressPair.slave, mongoOptions);
			} else if (serverAddressList != null) {
				mongo = new Mongo(serverAddressList, mongoOptions);
			} else {
				mongo = new Mongo(new ServerAddress(host, port), mongoOptions);
			}
			// 设置 Mongo 对象属性
			if (writeConcern != null) {
				mongo.setWriteConcern(writeConcern);
			}
			if (readPreference != null) {
				mongo.setReadPreference(readPreference);
			}
			if (options != null) {
				mongo.setOptions(options);
			}
		}
		return mongo;
	}

	@Override
	public Class<?> getObjectType() {
		return Mongo.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void destroy() throws Exception {
		mongo.close();
	}
	
	public static class ServerAddressPair {
		private ServerAddress master;
		private ServerAddress slave;

		public ServerAddressPair(ServerAddress master, ServerAddress slave) {
			this.master = master;
			this.slave = slave;
		}
	}
}
