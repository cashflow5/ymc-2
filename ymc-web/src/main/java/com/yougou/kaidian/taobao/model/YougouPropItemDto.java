package com.yougou.kaidian.taobao.model;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.yougou.pc.model.prop.PropValue;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-7-26 上午9:47:57
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public class YougouPropItemDto {
	private String propItemNo;
	private Integer isShowMall;
	private String propItemName;
	private String itemPropBindId;// 淘宝优购属性绑定ID
	private List<PropValue> propValues;
	public String getPropItemNo() {
		return propItemNo;
	}
	public void setPropItemNo(String propItemNo) {
		this.propItemNo = propItemNo;
	}

	public Integer getIsShowMall() {
		return isShowMall;
	}
	public void setIsShowMall(Integer isShowMall) {
		this.isShowMall = isShowMall;
	}
	public String getPropItemName() {
		return propItemName;
	}
	public void setPropItemName(String propItemName) {
		this.propItemName = propItemName;
	}
	public String getItemPropBindId() {
		return itemPropBindId;
	}
	public void setItemPropBindId(String itemPropBindId) {
		this.itemPropBindId = itemPropBindId;
	}
	public List<PropValue> getPropValues() {
		return propValues;
	}
	public void setPropValues(List<PropValue> propValues) {
		this.propValues = propValues;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
