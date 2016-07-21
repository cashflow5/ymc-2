package com.yougou.kaidian.user.model.pojo;

import java.io.Serializable;
import java.util.Date;



/**
 * 意见反馈回复
 * @author he.wc
 *
 */
public class FeebackReply implements Serializable {


	private static final long serialVersionUID = 3244349741750264667L;
	
	private String id;
	
	/**
	 * 意见反馈ID
	 */
	private String feebackId;
	
	/**
	 * 内容
	 */
	private String replyContent;
	
	/**
	 * 回复人
	 */
	private String replyPerson;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	/**
	 * 是否删除
	 */
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
