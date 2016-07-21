package com.yougou.kaidian.commodity.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 商家中心版式设置，版式模板VO
 * @author zhang.f1
 *
 */
public class LayoutTemplate implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id; //主键
	private String layoutName; //版式名称
	private String merchantCode; //商家编码
	private Date createTime; //创建时间
	private Date updateTime ;// 更新时间
	private Integer type; //版式类型
	private String isAll = "N"; // 是否绑定所有商品
	private String layoutHtml; //版式html
	private String htmlFilePath;//版式静态页地址
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLayoutName() {
		return layoutName;
	}
	public void setLayoutName(String layoutName) {
		this.layoutName = layoutName;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getLayoutHtml() {
		return layoutHtml;
	}
	public void setLayoutHtml(String layoutHtml) {
		this.layoutHtml = layoutHtml;
	}
	public String getIsAll() {
		return isAll;
	}
	public void setIsAll(String isAll) {
		this.isAll = isAll;
	}
	public String getHtmlFilePath() {
		return htmlFilePath;
	}
	public void setHtmlFilePath(String htmlFilePath) {
		this.htmlFilePath = htmlFilePath;
	}
	
	
}
