package com.belle.infrastructure.log.model.pojo;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * TblYitiansystemSystemlog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "tbl_yitiansystem_systemlog")
public class Systemlog implements java.io.Serializable {

	// Fields

	private String id;
	private String message;   //日志消息
	private Date logtime;      //日志时间
	private String userid;       //操作人ID
	private String username;       //操作人名称
	private String codeModule;        //模块名
	private String classname;         //类名
	private String logType;        //日志类型
	private String remark;         //备注

	// Constructors

	/** default constructor */
	public Systemlog() {
	}

	/** full constructor */
	public Systemlog(String message, Date logtime,
			String userid, String username, String codeModule,
			String classname, String logType, String remark) {
		this.message = message;
		this.logtime = logtime;
		this.userid = userid;
		this.username = username;
		this.codeModule = codeModule;
		this.classname = classname;
		this.logType = logType;
		this.remark = remark;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "message", length = 1000)
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name = "logtime", length = 19)
	public Date getLogtime() {
		return this.logtime;
	}

	public void setLogtime(Date logtime) {
		this.logtime = logtime;
	}

	@Column(name = "userid", length = 32)
	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Column(name = "username", length = 50)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "code_module", length = 50)
	public String getCodeModule() {
		return this.codeModule;
	}

	public void setCodeModule(String codeModule) {
		this.codeModule = codeModule;
	}

	@Column(name = "classname", length = 50)
	public String getClassname() {
		return this.classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	@Column(name = "log_type", length = 20)
	public String getLogType() {
		return this.logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}