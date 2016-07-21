package com.yougou.kaidian.active.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.yougou.active.api.IOfficialActiveApiService;
import com.yougou.active.vo.OfficialActiveVo;
import com.yougou.kaidian.active.dao.MerchantActiveCommodityMapper;
import com.yougou.kaidian.active.dao.MerchantActiveSignupMapper;
import com.yougou.kaidian.active.service.IOfficialActiveService;
import com.yougou.kaidian.active.vo.MerchantActiveCommodity;
import com.yougou.kaidian.active.vo.MerchantActiveCommodityQuery;
import com.yougou.kaidian.active.vo.MerchantActiveSignup;
import com.yougou.kaidian.active.vo.MerchantActiveSignupQuery;
import com.yougou.kaidian.common.base.Query;

@Service
public class OfficialActiveServiceImpl implements IOfficialActiveService {

	@Resource
	private MerchantActiveCommodityMapper merchantActiveCommodityMapper;
	
	@Resource
	private MerchantActiveSignupMapper merchantActiveSignupMapper;
	
	@Resource
	private IOfficialActiveApiService officialActiveApiService;
	/**
	 * 保存活动商品信息
	 * @param commodity
	 * @return
	 */
	public int saveActiveCommodity(MerchantActiveCommodity activeCommodity){
		return merchantActiveCommodityMapper.insert(activeCommodity);
	}
	
	public int updateActiveCommodity(MerchantActiveCommodity activeCommodity){
		return merchantActiveCommodityMapper.updateByPrimaryKey(activeCommodity);
	}
	
	public int signupOfficialActive(MerchantActiveSignup activeSignup){
		return merchantActiveSignupMapper.insert(activeSignup);
	}
	
	public MerchantActiveSignup getMerchantActiveSignup(String activeId, String merchantCode){
		return merchantActiveSignupMapper.selectByIdAndMerchantCode(activeId,merchantCode);
	}
	
	public MerchantActiveSignup getMerchantActiveSignupById(String id){
		return merchantActiveSignupMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<MerchantActiveCommodity> selectMerchantCommodityList(
			MerchantActiveCommodity commodity, Query query) {
		return merchantActiveCommodityMapper.selectMerchantCommodityList(commodity, query);
	}
	
	@Override
	public int getMerchantCommodityCount(MerchantActiveCommodityQuery query) {
		return merchantActiveCommodityMapper.countByQuery(query);
	}
	
	@Override
	public List<MerchantActiveCommodity> getMerchantCommodityList(MerchantActiveCommodityQuery query){
		return merchantActiveCommodityMapper.selectByQuery(query);
	}

	@Override
	public int selectMerchantCommodityCount(MerchantActiveCommodity commodity) {
		return merchantActiveCommodityMapper.selectMerchantCommodityCount(commodity);
	}
	
	public int deleteMerchantActiveCommodity(String commodityIds){
		String[] commodityIdArr = commodityIds.split(",");
		MerchantActiveCommodityQuery query = new MerchantActiveCommodityQuery();
		MerchantActiveCommodityQuery.Criteria criteria = query.createCriteria();
		criteria.andIdIn(Arrays.asList(commodityIdArr));
		return merchantActiveCommodityMapper.deleteByQuery(query);
	}
	
	public int updateOfficialActiveSignup(MerchantActiveSignup signup){
		return merchantActiveSignupMapper.updateByPrimaryKeySelective(signup);
	}

	@Override
	public List<MerchantActiveSignup> selectMerchantActiveSignupList(
			MerchantActiveSignup signup, Query query) {
		MerchantActiveSignupQuery commodityQuery = new MerchantActiveSignupQuery();
		MerchantActiveSignupQuery.Criteria criteria = commodityQuery.createCriteria();
		criteria.setLimitValue(query.getOffset(), query.getPageSize());
		if(StringUtils.isNotBlank(signup.getMerchantCode())){
			criteria.andMerchantCodeEqualTo(signup.getMerchantCode());
		}
		if(signup.getStatus() != null && signup.getStatus() != 0){
			criteria.andStatusEqualTo(signup.getStatus());
		}
		if(signup.getActiveType() != null && signup.getActiveType() != 0){
			criteria.andActiveTypeEqualTo(signup.getActiveType());
		}
		if(StringUtils.isNotBlank(signup.getActiveName())){
			criteria.andActiveNameLike("%"+signup.getActiveName()+"%");
		}
		commodityQuery.setOderProperty("update_time");
		commodityQuery.setOderType("DESC");
		List<MerchantActiveSignup> merchantActiveSignupList = merchantActiveSignupMapper.selectByQuery(commodityQuery);
		for(MerchantActiveSignup merchantActiveSignup : merchantActiveSignupList){
			OfficialActiveVo  officialActiveVo = officialActiveApiService.getOfficialActiveVoById(merchantActiveSignup.getActiveId());
			if(null != officialActiveVo){
				merchantActiveSignup.setActiveType(officialActiveVo.getActiveType());
				merchantActiveSignup.setStartTime(officialActiveVo.getStartTime());
				merchantActiveSignup.setEndTime(officialActiveVo.getEndTime());
				merchantActiveSignup.setSignUpStartTime(officialActiveVo.getSignUpStartTime());
				merchantActiveSignup.setSignUpEndTime(officialActiveVo.getSignUpEndTime());
			}
		}
		return merchantActiveSignupList;
	}

	@Override
	public int selectMerchantActiveSignupCount(MerchantActiveSignup signup) {
		MerchantActiveSignupQuery commodityQuery = new MerchantActiveSignupQuery();
		MerchantActiveSignupQuery.Criteria criteria = commodityQuery.createCriteria();
		if(StringUtils.isNotBlank(signup.getMerchantCode())){
			criteria.andMerchantCodeEqualTo(signup.getMerchantCode());
		}
		if(signup.getStatus() != null && signup.getStatus() != 0){
			criteria.andStatusEqualTo(signup.getStatus());
		}
		if(StringUtils.isNotBlank(signup.getActiveName())){
			criteria.andActiveNameLike("%"+signup.getActiveName()+"%");
		}
		return merchantActiveSignupMapper.countByQuery(commodityQuery);
	}
	
	public MerchantActiveCommodity getActiveCommodityById(String commodityId){
		return merchantActiveCommodityMapper.selectByPrimaryKey(commodityId);
	}	
}
