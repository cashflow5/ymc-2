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
 * 商家帮助中心图片实体
 * 
 * @author huang.tao
 *
 */
@Entity
@Table(name = " tbl_merchant_help_img")
public class HelpCenterImg {

	private String id;
	
	/** 图片名称 */
	private String picName;
	
	private Date created;

	private Date updated;
	
	public HelpCenterImg() {}

	public HelpCenterImg(String picName, Date created,
			Date updated) {
		this.picName = picName;
		this.created = created;
		this.updated = updated;
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

	@Column(name = "pic_name",  length = 32)
	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = false, length = 19)
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated", nullable = false, length = 19)
	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}


	
}
