package com.yougou.api.adapter.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import com.yougou.api.adapter.ChainApiInventoryTarget;
import com.yougou.dms.api.ApiInventoryService;
import com.yougou.dto.input.ChangeInventoryChainInputDto;
import com.yougou.dto.input.QueryInventoryChainInputDto;

/**
 * 分销库存适配器
 * @author huang.wq
 * 2012-12-19
 */
@Component
public class ChainApiInventoryAdapter implements ChainApiInventoryTarget {

	@Resource
	private ApiInventoryService apiInventoryService;
	
	/**
	 * 多个商品库存同步接口，查询商品库存
	 * 获取商品的库存，可以指定多个商品的货号查询，最多不超过50个货号
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public Object queryInventoryChain(Map<String, Object> parameterMap) throws Exception {
		QueryInventoryChainInputDto inputDto = new QueryInventoryChainInputDto();
		BeanUtils.populate(inputDto, parameterMap);
		//int page_index = (inputDto.getPage_index()/inputDto.getPage_size())+1;
		//inputDto.setPage_index(page_index);
		return apiInventoryService.queryInventoryChain(inputDto);
	}
	
	/**
	 * 增量商品库存同步接口，查询增量商品库存
	 * 获取有改动的商品库存，需指定库存更改开始时间和结束时间，每页最多返回50个商品信息
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public Object changeInventoryChain(Map<String, Object> parameterMap) throws Exception {
		ChangeInventoryChainInputDto inputDto = new ChangeInventoryChainInputDto();
		BeanUtils.populate(inputDto, parameterMap);
		//int page_index = (inputDto.getPage_index()/inputDto.getPage_size())+1;
		//inputDto.setPage_index(page_index);
		return apiInventoryService.changeInventoryChain(inputDto);
	}
	
}
