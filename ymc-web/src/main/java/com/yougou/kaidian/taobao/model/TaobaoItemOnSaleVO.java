package com.yougou.kaidian.taobao.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.taobao.api.domain.Item;

public class TaobaoItemOnSaleVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private long totalResult;
	private List<Item> lstItem;

	public long getTotalResult() {
		return totalResult;
	}

	public void setTotalResult(long totalResult) {
		this.totalResult = totalResult;
	}

	public List<Item> getLstItem() {
		return lstItem;
	}

	public void setLstItem(List<Item> lstItem) {
		this.lstItem = lstItem;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
