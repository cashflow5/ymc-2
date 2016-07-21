package com.yougou.api.adapter;

import java.util.Map;

import com.yougou.api.Implementor;

/**
 * 分销商API
 * @author song.nh
 *
 */
public interface ChainApiDistributorsTarget extends Implementor {
	
	/**
	 * 预存款金额查询
	 * 
	 * distributorsAccount
	 * 
	 * yougou.account.distributors
	 * 
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public Object distributorsAccount(Map<String, Object> parameterMap) throws Exception;
	
	/**
	 * 详细的预存款余额查询
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public Object detailDistributorsAccount(Map<String, Object> parameterMap) throws Exception;
}
