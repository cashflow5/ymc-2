package com.yougou.kaidian.user.model.pojo;

import java.io.Serializable;
import java.util.Date;



/**
 * 意见反馈
 * @author he.wc
 *
 */
public class Feeback implements Serializable {

	private static final long serialVersionUID = -6530987148207935650L;

	private String id;
	
	/**
	 * 商家编码
	 */
	private String merchantCode;
	
	/**
	 * 商家名称 
	 */
	private String merchantName;
	
	/**
	 * 一级意见类型 
	 */
	private String firstCate;
	
	/**
	 * 二级意见类型 
	 */
	private String secondCate;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 邮件
	 */
	private String email;
	
	/**
	 * 电话
	 */
	private String phone;
	
	/**
	 * 是否回复,1:是,0:否
	 */
	private String isReply = "0";
	
	/**
	 * 是否查看,1:是,0:否
	 */
	private String isRead = "0";
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
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
