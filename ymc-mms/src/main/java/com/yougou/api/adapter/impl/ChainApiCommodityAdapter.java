package com.yougou.api.adapter.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import com.yougou.api.adapter.ChainApiCommodityTarget;
import com.yougou.dms.api.ApiCommodityService;
import com.yougou.dto.input.BrandInputDto;
import com.yougou.dto.input.CategoryInputDto;
import com.yougou.dto.input.ChangeCommodityChainInputDto;
import com.yougou.dto.input.DetailCommodityChainInputDto;
import com.yougou.dto.input.PicCommodityChainInputDto;


/**
 * 分销商品适配器
 * 
 * @author huang.wq 2012-12-27
 */
@Component
public class ChainApiCommodityAdapter implements ChainApiCommodityTarget {

	@Resource
	private ApiCommodityService apiCommodityService;

	/**
	 * 单个商品详情查询接口
	 * 
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public Object detailCommodityChain(Map<String, Object> parameterMap) throws Exception {
		DetailCommodityChainInputDto inputDto = new DetailCommodityChainInputDto();
		BeanUtils.populate(inputDto, parameterMap);
		return apiCommodityService.detailCommodityChain(inputDto);
	}

	/**
	 * 增量同步商品接口
	 * 
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public Object changeCommodityChain(Map<String, Object> parameterMap) throws Exception {
		ChangeCommodityChainInputDto inputDto = new ChangeCommodityChainInputDto();
		BeanUtils.populate(inputDto, parameterMap);
		//int page_index = (inputDto.getPage_index()/inputDto.getPage_size())+1;
		//inputDto.setPage_index(page_index);
		
		return apiCommodityService.changeCommodityChain(inputDto);
	}

	/**
	 * 商品图片链接查询接口
	 */
	public Object picCommodityChain(Map<String, Object> parameterMap) throws Exception {
		PicCommodityChainInputDto inputDto = new PicCommodityChainInputDto();
		BeanUtils.populate(inputDto, parameterMap);
		//int page_index = (inputDto.getPage_index()/inputDto.getPage_size())+1;
		//inputDto.setPage_index(page_index);
		return apiCommodityService.picCommodityChain(inputDto);
	}



	@Override
	public Object queryBrandChain(Map<String, Object> parameterMap) throws Exception {
		BrandInputDto inputDto = new BrandInputDto();
		BeanUtils.populate(inputDto, parameterMap);
		return apiCommodityService.queryBrand(inputDto);
	}

	@Override
	public Object queryCategoryChain(Map<String, Object> parameterMap) throws Exception {
		CategoryInputDto inputDto = new CategoryInputDto();
		BeanUtils.populate(inputDto, parameterMap);
		return apiCommodityService.queryCategory(inputDto);
	}
	
	/**
	 * 商品上架查询接口
	 * @param paraterMap
	 * @return
	 * @throws Exception
	 */
	public Object upCommodityChain(Map<String, Object> parameterMap) throws Exception {
		return apiCommodityService.upCommodityChain(parameterMap);
	}

	/**
	 * 商品下架查询接口
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public Object downCommodityChain(Map<String, Object> parameterMap) throws Exception {
		return apiCommodityService.downCommodityChain(parameterMap);
	}
	
}
