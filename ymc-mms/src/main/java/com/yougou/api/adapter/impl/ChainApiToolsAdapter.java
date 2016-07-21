package com.yougou.api.adapter.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import com.yougou.api.adapter.ChainApiToolsTarget;
import com.yougou.dms.api.ApiToolsService;
import com.yougou.dto.input.AddressInputDto;

/**
 * @author william.zuo
 *
 */
@Component
public class ChainApiToolsAdapter implements ChainApiToolsTarget {

	@Resource
	private ApiToolsService apiToolsService;

	@Override
	public Object queryallAddressChain(Map<String, Object> parameterMap)
			throws Exception {
		AddressInputDto inputDto=new AddressInputDto();
		BeanUtils.populate(inputDto, parameterMap);
		return apiToolsService.queryallAddress(inputDto);
	}
}
