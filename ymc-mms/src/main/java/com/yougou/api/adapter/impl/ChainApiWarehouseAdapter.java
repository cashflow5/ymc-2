package com.yougou.api.adapter.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import com.yougou.api.adapter.ChainApiWarehouseTarget;
import com.yougou.dms.api.ApiWarehouseService;
import com.yougou.dto.input.QueryallWarehouseChainInputDto;

/**
 * 分销仓库适配器
 * @author he.wc
 *
 */
@Component
public class ChainApiWarehouseAdapter implements ChainApiWarehouseTarget {

	@Resource
	private ApiWarehouseService apiWarehouseService;
	
	/**
	 * 查询所有优购仓库
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object queryallWarehouseChain(Map<String, Object> parameterMap)
			throws Exception {
		QueryallWarehouseChainInputDto inputDto = new QueryallWarehouseChainInputDto();
		BeanUtils.populate(inputDto, parameterMap);
		return apiWarehouseService.queryallWarehouseChain(inputDto);
	}
	
}
