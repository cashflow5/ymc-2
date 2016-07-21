package com.belle.yitiansystem.systemmgmt.model.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 系统操作日志
 * @author 赵敏延 2012-09-24
 *
 */
@Entity
@Table(name = "tbl_operate_log")
public class OperateLog implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	private String id;
	
	/**
	 * 系统ID,比如订单、财务、wms、cms等系统
	 */
	@Column(name = "portal_id")
	private Integer portal_id;
	
	/**
	 * 操作类型,比如添加、修改、删除、审核等动作
	 */
	@Column(name = "operate_type")
	private Integer operate_type;
	
	/**
	 * 功能名称,比如优惠券管理、商品管理等名称
	 */
	@Column(name = "mod_name")
	private String mod_name;
	
	/**
	 * 操作信息ID,能标识本次操作信息的编号
	 */
	@Column(name = "info_id")
	private String info_id;
	
	/**
	 * 操作信息名称,能标识本次操作信息的名称
	 */
	@Column(name = "info_name")
	private String info_name;
	
	/**
	 * 操作备注
	 */
	@Column(name = "operate_desc")
	private String operate_desc;
	
	/**
	 * 操作用户ID
	 */
	@Column(name = "user_id")
	private String user_id;
	
	/**
	 * 操作用户名
	 */
	@Column(name = "user_name")
	private String user_name;
	
	/**
	 * 操作时间
	 */
	@Column(name = "create_time")
	private Date create_time;
	
	/**
	 * 操作IP
	 */
	@Column(name = "operate_ip")
	private String operate_ip;
	
	
	public OperateLog() {
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getPortal_id() {
		return portal_id;
	}
	public void setPortal_id(Integer portal_id) {
		this.portal_id = portal_id;
	}
	public Integer getOperate_type() {
		return operate_type;
	}
	public void setOperate_type(Integer operate_type) {
		this.operate_type = operate_type;
	}
	public String getMod_name() {
		return mod_name;
	}
	public void setMod_name(String mod_name) {
		this.mod_name = mod_name;
	}
	public String getInfo_id() {
		return info_id;
	}
	public void setInfo_id(String info_id) {
		this.info_id = info_id;
	}
	public String getInfo_name() {
		return info_name;
	}
	public void setInfo_name(String info_name) {
		this.info_name = info_name;
	}
	public String getOperate_desc() {
		return operate_desc;
	}
	public void setOperate_desc(String operate_desc) {
		this.operate_desc = operate_desc;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getOperate_ip() {
		return operate_ip;
	}
	public void setOperate_ip(String operate_ip) {
		this.operate_ip = operate_ip;
	}
	
	
}
