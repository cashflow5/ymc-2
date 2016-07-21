package com.yougou.kaidian.order.model;

import com.yougou.ordercenter.vo.merchant.output.QueryOrderStockOutputDto;
/**
 * 包装备货清单列表输出字段（增加异常标志）
 * @author luo.q1
 *
 */
public class MerchantQueryOrderStockOutputDto extends QueryOrderStockOutputDto {

	private static final long serialVersionUID = 1L;
	
	private int isException;// 是否异常，何种异常(异常解释见常量类：OrderConstant.java)
	
	// 商家备注颜色
	private String markColor;
	// 商家备注
	private String markNote;

	public int getIsException() {
		return isException;
	}

	public void setIsException(int isException) {
		this.isException = isException;
	}

	public String getMarkColor() {
		return markColor;
	}

	public String getMarkNote() {
		return markNote;
	}

	public void setMarkColor(String markColor) {
		this.markColor = markColor;
	}

	public void setMarkNote(String markNote) {
		this.markNote = markNote;
	}
	
	
}
