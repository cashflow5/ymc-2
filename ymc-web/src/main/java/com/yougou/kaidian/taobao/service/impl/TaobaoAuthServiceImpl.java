package com.yougou.kaidian.taobao.service.impl;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.util.DateUtil2;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.kaidian.taobao.dao.TaobaoAuthinfoMapper;
import com.yougou.kaidian.taobao.dao.TaobaoShopNickMapper;
import com.yougou.kaidian.taobao.model.TaobaoAuthinfo;
import com.yougou.kaidian.taobao.model.TaobaoShop;
import com.yougou.kaidian.taobao.service.ITaobaoAuthService;
import com.yougou.kaidian.taobao.service.ITaobaoDataImportService;

@Service
public class TaobaoAuthServiceImpl implements ITaobaoAuthService {

	private static final Logger log = LoggerFactory.getLogger(TaobaoAuthServiceImpl.class);
	@Resource
	private TaobaoShopNickMapper taobaoShopNickMapper;
	@Resource
	private TaobaoAuthinfoMapper taobaoAuthinfoMapper; 
	@Resource
	private ITaobaoDataImportService taobaoDataImportService;
	
	@Override
	public void insertTaobaoShop(TaobaoShop taobaoShop) {
		taobaoShopNickMapper.insertTaobaoShop(taobaoShop);
	}

	@Override
	public PageFinder<TaobaoShop> getTaobaoShopList(Query query,String merchantCode) {
		RowBounds rowBounds=new RowBounds(query.getOffset(), query.getPageSize());
		int count=taobaoShopNickMapper.getTaobaoShopListCount(merchantCode);
		PageFinder<TaobaoShop> pageFinder = new PageFinder<TaobaoShop>(query.getPage(), query.getPageSize(), count, taobaoShopNickMapper.getTaobaoShopList(merchantCode, rowBounds));
		return pageFinder;
	}

	@Override
	public void updateTaobaoShopStatus(Map<String, Object> paraMap) {
		taobaoShopNickMapper.updateTaobaoShopStatus(paraMap);
	}

	@Override
	public boolean checkTaobaoShopByName(String taobaoShopName) {
		if(taobaoShopNickMapper.getTaobaoShopByNameCount(taobaoShopName)>0){
			return true;
		}
		return false;
	}

	@Override
	public TaobaoShop getTaobaoShopByID(String id) {
		return taobaoShopNickMapper.getTaobaoShopByID(id);
	}

	@Override
	public void updateTaobaoShop(TaobaoShop taobaoShop) {
		taobaoShopNickMapper.updateTaobaoShop(taobaoShop);
	}

	@Override
	public boolean authorization(Map<String, Object> params,String merchantCode,String loginUser) throws Exception{
		log.info("[淘宝导入]商家编码:{}-淘宝授权.params:{},loginUser:{}", merchantCode, params, loginUser);
		String topAppkey = MapUtils.getString(params,"top_appkey");
		String topParameters = MapUtils.getString(params,"top_parameters");
		String topSession = MapUtils.getString(params,"top_session");
		String timestamp = MapUtils.getString(params,"timestamp");
		String sign = MapUtils.getString(params,"sign");
		String agreement = MapUtils.getString(params,"agreement");
		String agreementSign = MapUtils.getString(params,"agreementsign");
		String topSign = MapUtils.getString(params,"top_sign");
		String visitorId = MapUtils.getString(params,"visitor_id");
		String visitorNick = MapUtils.getString(params,"visitor_nick");
		String expiresIn = MapUtils.getString(params,"expires_in");
		
    	String nowStr = DateUtil2.getDateTime(new Date());
    	
		if(timestamp==null){
			timestamp = nowStr;
		}
		
		int iExpiresInTime = Integer.parseInt(expiresIn!=null?expiresIn:"0");
		long lExpiresInTime = 1l;
		try {
			lExpiresInTime = lExpiresInTime*(DateUtil2.getToSecondsByStrDate(timestamp)+iExpiresInTime)*1000;
		} catch (Exception e) {
			log.error("[淘宝导入]商家编码:{}-日期格式转换异常", merchantCode, e);
		}
		Date topExpiresInTime = new Date(lExpiresInTime); 
		//先判断授权账号有没有对应的店铺信息
		TaobaoShop taobaoShop=taobaoShopNickMapper.getTaobaoShopByNickName(visitorNick);
    	if(taobaoShop!=null){
        	Short isUsable=1;
        	TaobaoAuthinfo oldrecord=taobaoAuthinfoMapper.selectByTopVisitorID(visitorId);

        	TaobaoAuthinfo record = new TaobaoAuthinfo();
        	record.setOperated(nowStr);
        	record.setOperater(loginUser);
        	record.setTopAgreement("true".equalsIgnoreCase(agreement)?isUsable:0);
        	record.setTopAgreementsign(agreementSign);
        	record.setTopAppkey(topAppkey);
        	record.setTopExpiresIn(Integer.parseInt(expiresIn));
        	record.setTopExpiresInTime(topExpiresInTime);
        	record.setTopParameters(topParameters);
        	record.setTopSession(topSession);
        	record.setSign(sign);
        	record.setTopSign(topSign);
        	record.setTopTimestamp(timestamp);
        	record.setTopVisitorId(Long.parseLong(visitorId));
        	record.setTopVisitorNick(visitorNick);
        	
    		TaobaoShop taobaoShop1=new TaobaoShop();
    		taobaoShop1.setNickName(visitorNick);
    		taobaoShop1.setNid(Long.parseLong(visitorId));
    		taobaoShop1.setOperated(nowStr);
    		taobaoShop1.setStatus(3);
    		taobaoShop1.setOperater(loginUser);
    		
        	if(oldrecord==null){
            	record.setId(UUIDGenerator.getUUID());
            	record.setMerchantCode(merchantCode);
            	record.setIsUseble(isUsable);
            	taobaoAuthinfoMapper.insertTaobaoAuthinfo(record);
        	}else{
        		record.setId(oldrecord.getId());
        		taobaoAuthinfoMapper.updateByPrimaryKeySelective(record);
        	}
        	taobaoShopNickMapper.updateTaobaoShopByNickName(taobaoShop1);
        	return true;
    	}else{
    		return false;
    	}
	}

	@Async
	public void importTaobaoBasicDataToYougou(Map<String, Object> params,
			String merchantCode, String loginUser) {
		try {
			String topSession = MapUtils.getString(params, "top_session");
			String topAppkey = MapUtils.getString(params, "top_appkey");
			String visitorId = MapUtils.getString(params, "visitor_id");
			String appSecret = taobaoAuthinfoMapper
					.selectTopSecretByKey(topAppkey);
			taobaoDataImportService.importTaobaoBasicDataToYougou(topAppkey,
					appSecret, topSession, merchantCode, loginUser,
					Long.parseLong(visitorId));
		} catch (Exception e) {
			log.error("[淘宝导入]商家编码:{}-淘宝店铺抓取基础数据异常.", merchantCode, e);
		}

	}
	
	public String importTaobaoCatPropToYougou(String sessionKey, String merchantCode, String operater, String cids) throws IllegalAccessException {
		return taobaoDataImportService.importTaobaoItemcatToYougouByParentCids(sessionKey, merchantCode, operater, cids);
	}

	@Override
	public List<TaobaoShop> getAllTaobaoShopListByStatus(String merchantCode, String status) {
		return taobaoShopNickMapper.getAllTaobaoShopListByStatus(merchantCode, status);
	}
}
