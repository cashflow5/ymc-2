package com.yougou.kaidian.user.model.vo;

import java.io.Serializable;
import java.util.List;



/**
 * 意见反馈
 * @author he.wc
 *
 */
public class FeebackVo implements Serializable {

	private static final long serialVersionUID = -5545263696520933354L;

	private String id;
	
	/**
	 * 内容
	 */
	private String content;

	/**
	 * 优购回复
	 */
	private List<String> replyList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<String> replyList) {
		this.replyList = replyList;
	}

	
}
