package com.belle.infrastructure.log.model.vo;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import com.belle.infrastructure.constant.Constant;
import com.belle.infrastructure.log.model.pojo.Systemlog;
import com.belle.infrastructure.util.DateUtil;


/**
 * 重写Log4j 实现算定义日志信息封装
 * 
 * @author yhb
 * 
 * @version 创建时间：2011-4-12 下午04:22:04
 */
public class AppLog implements Log, Serializable {

	private static final String FQCN = Log4JLogger.class.getName();

	private transient Logger logger = null;

	private String name = null;
	private static Priority traceLevel;

	public AppLog() {
	}
	

	public AppLog(String name) {
		this.name = name;
		this.logger = getLogger();
	}

	
	public AppLog(Logger logger) {
		if (logger == null) {
			throw new IllegalArgumentException("Warning - null logger in constructor; possible log4j misconfiguration.");
		}

		this.name = logger.getName();
		this.logger = logger;
	}
	

	/**
	 * 写入日志
	 * @param message 日志信息
	 */
	public void sysLog(String message) {
	    sysLog(null,message,null);
	}

	/**
	 * 写入日志
	 * @param username  用户名
	 * @param message	日志信息
	 *  @param codeModule 模块名
	 */
	public void sysLog(String username, String message,String codeModule) {
	    Systemlog sysLog = new Systemlog();
        sysLog.setMessage(message);
        sysLog.setUsername(username);
        sysLog.setCodeModule(codeModule); 
        sysLog.setLogtime(DateUtil.getCurrentDateTime());
        sysLog.setLogType(Constant.LOG_COMMON_SYSTEM);   
        sysLog.setClassname(this.name.substring(this.name.lastIndexOf(".")+1));
        error(sysLog);
	}

	/**
	 * 写入日志
	 * @param appMessage 应用 日志对象
	 */
	public void sysLog(Systemlog sysLog) {
		if (sysLog != null) {
			error(sysLog);
		}
	}

	
	
	
	
	public void trace(Object message) {
	    
		getLogger().log(FQCN, traceLevel, message, null);
	}

	public void trace(Object message, Throwable t) {
		getLogger().log(FQCN, traceLevel, message, t);
	}

	public void debug(Object message) {
		getLogger().log(FQCN, Priority.DEBUG, message, null);
	}

	public void debug(Object message, Throwable t) {
		getLogger().log(FQCN, Priority.DEBUG, message, t);
	}

	public void info(Object message) {
		getLogger().log(FQCN, Priority.INFO, message, null);
	}

	public void info(Object message, Throwable t) {
		getLogger().log(FQCN, Priority.INFO, message, t);
	}

	public void warn(Object message) {
		getLogger().log(FQCN, Priority.WARN, message, null);
	}

	public void warn(Object message, Throwable t) {
		getLogger().log(FQCN, Priority.WARN, message, t);
	}

	public void error(Object message) {
		getLogger().log(FQCN, Priority.ERROR, message, null);
	}

	public void error(Object message, Throwable t) {
		getLogger().log(FQCN, Priority.ERROR, message, t);
	}

	public void fatal(Object message) {
		getLogger().log(FQCN, Priority.FATAL, message, null);
	}

	public void fatal(Object message, Throwable t) {
		getLogger().log(FQCN, Priority.FATAL, message, t);
	}

	public Logger getLogger() {
		if (this.logger == null) {
			this.logger = Logger.getLogger(this.name);
		}
		return this.logger;
	}

	public boolean isDebugEnabled() {
		return getLogger().isDebugEnabled();
	}

	public boolean isErrorEnabled() {
		return getLogger().isEnabledFor(Priority.ERROR);
	}

	public boolean isFatalEnabled() {
		return getLogger().isEnabledFor(Priority.FATAL);
	}

	public boolean isInfoEnabled() {
		return getLogger().isInfoEnabled();
	}

	public boolean isTraceEnabled() {
		return getLogger().isEnabledFor(traceLevel);
	}

	public boolean isWarnEnabled() {
		return getLogger().isEnabledFor(Priority.WARN);
	}

	static {
		if (!Priority.class.isAssignableFrom(Level.class)) {
			throw new InstantiationError("Log4J 1.2 not available");
		}

		try {
			traceLevel = (Priority) Level.class.getDeclaredField("TRACE").get(
					null);
		} catch (Exception ex) {
			traceLevel = Priority.DEBUG;
		}
	}
}
