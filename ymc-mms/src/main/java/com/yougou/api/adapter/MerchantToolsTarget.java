package com.yougou.api.adapter;

import java.util.Date;

import com.yougou.api.Implementor;

/**
 * 招商系统工具类适配接口
 * 
 * @author 杨梦清
 *
 */
public interface MerchantToolsTarget extends Implementor {

	/**
	 * 获取系统当前时间 yougou.time.get
	 * 
	 * @return Date
	 */
	Date getTime();
}
