package com.yougou.kaidian.taobao.model;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-8-20 上午10:15:39
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public class TaobaoItemExtendDto extends TaobaoItemExtend {
	private String title;
	private String detailUrl;
	private String nick;
	private String price;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetailUrl() {
		return detailUrl;
	}
	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
