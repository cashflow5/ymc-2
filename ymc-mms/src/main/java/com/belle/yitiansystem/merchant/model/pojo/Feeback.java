package com.belle.yitiansystem.merchant.model.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;



/**
 * 意见反馈
 * @author he.wc
 *
 */
@Entity
@Table(name = "tbl_merchant_feeback")
public class Feeback implements Serializable {

	
	private static final long serialVersionUID = -4627707374534480670L;

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	private String id;
	
	/**
	 * 商家编码
	 */
	@Column(name = "merchant_code")
	private String merchantCode;
	
	/**
	 * 商家名称 
	 */
	@Column(name = "merchant_name")
	private String merchantName;
	
	/**
	 * 一级意见类型 
	 */
	@Column(name = "first_cate")
	private String firstCate;
	
	/**
	 * 二级意见类型 
	 */
	@Column(name = "second_cate")
	private String secondCate;
	
	/**
	 * 标题
	 */
	@Column(name = "title")
	private String title;
	
	/**
	 * 内容
	 */
	@Column(name = "content")
	private String content;
	
	/**
	 * 邮件
	 */
	@Column(name = "email")
	private String email;
	
	/**
	 * 电话
	 */
	@Column(name = "phone")
	private String phone;
	
	/**
	 * 是否回复,1:是,0:否
	 */
	@Column(name = "is_reply")
	private String isReply = "0";
	
	/**
	 * 是否查看,1:是,0:否
	 */
	@Column(name = "is_read")
	private String isRead = "0";
	
	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	@Column(name = "update_time")
	private Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getFirstCate() {
		return firstCate;
	}

	public void setFirstCate(String firstCate) {
		this.firstCate = firstCate;
	}

	public String getSecondCate() {
		return secondCate;
	}

	public void setSecondCate(String secondCate) {
		this.secondCate = secondCate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIsReply() {
		return isReply;
	}

	public void setIsReply(String isReply) {
		this.isReply = isReply;
	}

	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "Feeback [id=" + id + ", merchantCode=" + merchantCode + ", merchantName=" + merchantName
				+ ", firstCate=" + firstCate + ", secondCate=" + secondCate + ", title=" + title + ", content="
				+ content + ", email=" + email + ", phone=" + phone + ", isReply=" + isReply + ", isRead=" + isRead
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
	
	
	
}
