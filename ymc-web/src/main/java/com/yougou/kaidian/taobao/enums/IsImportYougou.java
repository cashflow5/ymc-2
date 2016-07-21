package com.yougou.kaidian.taobao.enums;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-7-31 下午2:36:45
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public enum IsImportYougou {
	STATUS_DELETED("-1", "删除"), STATUS_IMPORTED("1", "已导入优购"), STATUS_NOT_IMPORTED("0", "未导入优购");
	private String status;
	private IsImportYougou(String status, String desc) {
		this.status = status;
	}
	public String getStatus() {
		return this.status;
	}
}
