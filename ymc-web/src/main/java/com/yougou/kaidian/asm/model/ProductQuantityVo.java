package com.yougou.kaidian.asm.model;

import java.io.Serializable;

public class ProductQuantityVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String productNo;
	private Integer quantity;

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
