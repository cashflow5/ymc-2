package com.yougou.api.adapter;

import java.util.Map;

import com.yougou.api.Implementor;

/**
 * 分销仓库适配器接口
 * @author huang.wq
 * 2013-3-4
 */
public interface ChainApiWarehouseTarget extends Implementor {

	/**
	 * 查询所有优购仓库
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	Object queryallWarehouseChain(Map<String, Object> parameterMap) throws Exception;
	
}
