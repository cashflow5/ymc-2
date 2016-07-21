package com.yougou.kaidian.taobao.vo;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.taobao.api.domain.Item;

public class TaobaoCsvItemVO {

	private int index;
	private Item item;
	//---add by lsm  原始数组下标，用于提示信息定位到几行
	private int arrayIndex;
	
	public int getArrayIndex() {
		return arrayIndex;
	}
	public void setArrayIndex(int arrayIndex) {
		this.arrayIndex = arrayIndex;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
