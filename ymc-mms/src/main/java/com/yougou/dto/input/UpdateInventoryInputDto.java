package com.yougou.dto.input;

import org.apache.commons.lang.math.NumberUtils;

public class UpdateInventoryInputDto extends InputDto {

	private static final long serialVersionUID = 5095250152277694763L;
	private Integer quantity;
	private String third_party_code;
	private Integer update_type = NumberUtils.INTEGER_ZERO;

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getThird_party_code() {
		return third_party_code;
	}

	public void setThird_party_code(String third_party_code) {
		this.third_party_code = third_party_code;
	}

	public Integer getUpdate_type() {
		return update_type;
	}

	public void setUpdate_type(Integer update_type) {
		this.update_type = update_type;
	}

}
