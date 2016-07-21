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
 * 意见反馈回复
 * @author he.wc
 *
 */
@Entity
@Table(name = "tbl_merchant_feeback_reply")
public class FeebackReply implements Serializable {


	private static final long serialVersionUID = 1850130291272484868L;

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	private String id;
	
	/**
	 * 意见反馈ID
	 */
	@Column(name = "feeback_id")
	private String feebackId;
	
	/**
	 * 内容
	 */
	@Column(name = "reply_content")
	private String replyContent;
	
	/**
	 * 回复人
	 */
	@Column(name = "reply_person")
	private String replyPerson;
	
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
	 * 是否删除
	 */
	@Column(name = "is_deleted")
	private String isDeleted;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFeebackId() {
		return feebackId;
	}

	public void setFeebackId(String feebackId) {
		this.feebackId = feebackId;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getReplyPerson() {
		return replyPerson;
	}

	public void setReplyPerson(String replyPerson) {
		this.replyPerson = replyPerson;
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

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String toString() {
		return "FeebackReply [id=" + id + ", feebackId=" + feebackId + ", replyContent=" + replyContent
				+ ", replyPerson=" + replyPerson + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", isDeleted=" + isDeleted + "]";
	}
	
	
}
