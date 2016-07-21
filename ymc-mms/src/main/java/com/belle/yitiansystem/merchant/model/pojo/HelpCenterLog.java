package com.belle.yitiansystem.merchant.model.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 商家帮助中心内容实体
 * 
 * @author huang.tao
 *
 */
@Entity
@Table(name = " tbl_merchant_help_log")
public class HelpCenterLog {

	private String id;
	
	/** 菜单ID */
	private String menuId;
	
	/** 操作内容 */
	private String content;
	
	private Date updateTime;
	
	private String operator;
	
	public HelpCenterLog() {}

	public HelpCenterLog(String menuId, String content,
			Date updateTime, String operator) {
		this.menuId = menuId;
		this.content = content;
		this.updateTime = updateTime;
		this.operator = operator;
	}

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "menu_id",  length = 32)
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@Column(name = "content", length = 255)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time", nullable = false, length = 19)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "operator",  length = 50)
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
}
