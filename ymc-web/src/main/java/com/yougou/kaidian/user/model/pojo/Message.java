package com.yougou.kaidian.user.model.pojo;  

import java.io.Serializable;
import java.util.Date;
/**
 * ClassName: Message
 * Desc: 短信或邮件记录Model
 * date: 2015-10-12 下午2:27:07
 * @author li.n1 
 * @since JDK 1.6
 */
public class Message implements Serializable{
	private static final long serialVersionUID = 3883082196175761068L;
	private String id;
	/**
	 * 发送内容
	 */
	private String content;
	/**
	 * 信息备注
	 */
	private String comment;
	/**
	 * 收信息者
	 */
	private String to;
	/**
	 * 当前登录者
	 */
	private String loginName;
	/**
	 * 发送时间
	 */
	private Date operated;
	/**
	 * 信息的类型   0短信  1邮件
	 */
	private String type;
	
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public Date getOperated() {
		return operated;
	}
	public void setOperated(Date operated) {
		this.operated = operated;
	}
	public String getType() {
		return type;
	}
	/**
	 * setType:信息的类型   0短信  1邮件
	 * @author li.n1 
	 * @param type 
	 * @since JDK 1.6 
	 * @date 2015-10-12 下午2:45:48
	 */
	public void setType(String type) {
		this.type = type;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginName() {
		return loginName;
	}
}
