package com.yougou.api.adapter;

import java.util.Map;

import com.yougou.api.Implementor;

/**
 * 分销工具类
 * @author william.zuo
 */
public interface ChainApiToolsTarget extends Implementor {

	
	/**查询所有的省,市,区
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	Object queryallAddressChain(Map<String, Object> parameterMap) throws Exception;
	
}
