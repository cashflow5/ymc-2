package com.belle.yitiansystem.mctnotice.model.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tbl_merchant_notice")
public class MctNotice implements Serializable{

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
	 * 公告类型
	 */
	@Column(name = "notice_type")
	private String noticeType;
	
	/**
	 * 商家类型
	 */
	@Column(name = "merchant_type")
	private String merchantType;
	
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
	 * 创建时间
	 */
	@Column(name = "create_time")
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	@Column(name = "update_time")
	private Date updateTime;
	
	/**
	 * 作者
	 */
	@Column(name = "author")
	private String author;
	
	/**
	 * 是否置顶
	 */
	@Column(name = "is_top")
	private String isTop;
	
	/**
	 * 是否置顶
	 */
	@Column(name = "is_red")
	private String isRed;

	public String getIsRed() {
		return isRed;
	}

	public void setIsRed(String isRed) {
		this.isRed = isRed;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}
}
