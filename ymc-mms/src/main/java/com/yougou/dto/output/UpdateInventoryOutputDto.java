package com.yougou.dto.output;

import java.util.Date;

public class UpdateInventoryOutputDto extends OutputDto {

	private static final long serialVersionUID = -8268327436336939706L;
	private String third_party_code;
	private Date modified;

	public String getThird_party_code() {
		return third_party_code;
	}

	public void setThird_party_code(String third_party_code) {
		this.third_party_code = third_party_code;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

}
