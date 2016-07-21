package com.yougou.kaidian.taobao.enums;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-7-25 上午10:44:23
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public enum YougouMustType {
	MUST(1), NOTMUST(0);
	private int type;

	YougouMustType(int type) {

		this.type = type;
	}
	public int getValue() {

		return type;
	}
}
