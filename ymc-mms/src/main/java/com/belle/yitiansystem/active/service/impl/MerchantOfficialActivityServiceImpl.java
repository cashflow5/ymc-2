package com.belle.yitiansystem.active.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.yitiansystem.active.service.MerchantOfficialActivityService;
import com.belle.yitiansystem.active.vo.MerchantActiveSignup;
import com.belle.yitiansystem.merchant.dao.mapper.MerchantOfficialActivityMapper;
import com.yougou.active.api.IOfficialActiveApiService;
import com.yougou.active.vo.OfficialActiveVo;
import com.yougou.kaidian.common.base.Query;


/**
 * 
 * 商家报名官方
 * 
 * @author zhang.wj
 * 
 */
@Service
public class MerchantOfficialActivityServiceImpl implements
		MerchantOfficialActivityService {
	
	private static final Logger logger = LoggerFactory.getLogger(MerchantOfficialActivityServiceImpl.class);
	 
	
	@Resource
	private MerchantOfficialActivityMapper merchantOfficialActivityMapper;
	
	@Resource
	private IOfficialActiveApiService officialActiveApiService;
	
	public PageFinder<Map<String, Object>> queryMerchant(Map<String,Object> map,Query query)throws Exception{
		//查询商家数据
		List<Map<String, Object>> list = merchantOfficialActivityMapper.queryMerchant(map);
		int count =merchantOfficialActivityMapper.queryMerchantCount(map);
		
		//如果为空直接返回
		if (CollectionUtils.isEmpty(list)) {
			return new PageFinder<Map<String, Object>>(query.getPage(), query.getPageSize(), 0, list);
		}	
		
		PageFinder<Map<String, Object>> pageFinder = 
				new PageFinder<Map<String, Object>>(query.getPage(), query.getPageSize(),count);
		pageFinder.setData(list);
		
		
		return pageFinder;
	}
	public int  queryMerchantCount(String activeId)throws Exception{
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("activeId", activeId);
		return merchantOfficialActivityMapper.queryMerchantCount(map);
	}
	
	 
	/**
	 * 查询商品信息
	 * @param map
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public PageFinder<Map<String, Object>> queryCommodity(Map<String,Object> map,Query query)throws Exception{
		//查询商家数据
				List<Map<String, Object>> list = merchantOfficialActivityMapper.queryCommodity(map);
				int count =merchantOfficialActivityMapper.queryCommodityCount(map);
				
				//如果为空直接返回
				if (CollectionUtils.isEmpty(list)) {
					return new PageFinder<Map<String, Object>>(query.getPage(), query.getPageSize(), 0, list);
				}	
				
				PageFinder<Map<String, Object>> pageFinder = 
						new PageFinder<Map<String, Object>>(query.getPage(), query.getPageSize(),count);
				
				pageFinder.setData(list);
				
				return pageFinder;
	}
	
	/**
	 * 更新报名状态为报名结束
	 */
	public void updateOfficialActiveStatusOver(){
		try{
			List<MerchantActiveSignup> merchantActiveSignupList = merchantOfficialActivityMapper.selectMerchantActiveSignupNotAudited();
			Map<String,OfficialActiveVo> map = new HashMap<String,OfficialActiveVo>();
			OfficialActiveVo officialActiveVo = null;
			for(MerchantActiveSignup merchantActiveSignup : merchantActiveSignupList){
				if(map.containsKey(merchantActiveSignup.getActiveId())){
					officialActiveVo = map.get(merchantActiveSignup.getActiveId());
				}else{
					officialActiveVo = officialActiveApiService.getOfficialActiveVoById(merchantActiveSignup.getActiveId());
					map.put(merchantActiveSignup.getActiveId(), officialActiveVo);
				}
				if(officialActiveVo != null){
					if(officialActiveVo.getMerchantAuditEndTime().getTime() < System.currentTimeMillis()){
						merchantActiveSignup.setStatus((short)5);
						merchantActiveSignup.setAuditRemark("系统更新审批结束的报名为报名结束");
						merchantActiveSignup.setUpdateTime(new Date());
						merchantOfficialActivityMapper.updateByPrimaryKey(merchantActiveSignup);
					}								
				}else{
					logger.error("更新审批时间结束之后，未审核通过的报名状态.未找到官方活动数据，activeId="+merchantActiveSignup.getActiveId());
				}
			}
		}catch(Exception e){
			logger.error("更新审批时间结束之后，未审核通过的报名状态为报名结束出现异常",e);
		}
	}
	

}
