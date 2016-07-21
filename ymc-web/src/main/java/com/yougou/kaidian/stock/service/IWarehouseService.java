/**
 * 
 */
package com.yougou.kaidian.stock.service;


/**
 * 仓库相关服务
 * 
 * @author huang.tao
 *
 */
public interface IWarehouseService {
	
	/**
	 * 获取商家关联的仓库编码
	 * 
	 * @param merchantCode | 商家编码
	 * @return eg:01111123145805
	 */
	String queryWarehouseCodeByMerchantCode(String merchantCode) throws Exception;
	
}
