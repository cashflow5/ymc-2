package com.yougou.api.adapter.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import com.belle.finance.biz.dubbo.IDistributorsAccountOperateDubboService;
import com.yougou.api.adapter.ChainApiDistributorsTarget;
import com.yougou.dto.input.DistributorsAccountInputDto;
import com.yougou.dto.output.DistributorsAccountOutputDto;

@Component
public class ChainApiDistributorsAdapter implements ChainApiDistributorsTarget {
	
//	@Resource
//	private DistributorsAccountService distributorsAccountService;
	
	@Resource
	private IDistributorsAccountOperateDubboService accountService;
	
	@Override
	public Object distributorsAccount(Map<String, Object> parameterMap)throws Exception {
		DistributorsAccountInputDto inputDto = new DistributorsAccountInputDto();
		BeanUtils.populate(inputDto, parameterMap);
		DistributorsAccountOutputDto outputDto = new DistributorsAccountOutputDto();
		
		List<Map<String, Object>> distributorsAccountList;
		distributorsAccountList = accountService.queryDistributorsAccountForAPI(inputDto.getMerchant_code());
		outputDto.setDistributorsAccountList(distributorsAccountList);
		return outputDto;
	}

	/**
	 * 详细的预存款余额查询
	 */
	@Override
	public Object detailDistributorsAccount(Map<String, Object> parameterMap)throws Exception {
		DistributorsAccountInputDto inputDto = new DistributorsAccountInputDto();
		BeanUtils.populate(inputDto, parameterMap);
		
		DistributorsAccountOutputDto outputDto = new DistributorsAccountOutputDto();
		List<Map<String, Object>> distributorsAccountList = accountService.queryDetailDistributorsAccountForAPI(inputDto.getMerchant_code(),
						inputDto.getTradeNo(), inputDto.getAccount(),
						inputDto.getStartTime(), inputDto.getEndTime(),
						inputDto.getPage_index(), inputDto.getPage_size());
		outputDto.setDistributorsAccountList(distributorsAccountList);
		return outputDto;
	}
}
