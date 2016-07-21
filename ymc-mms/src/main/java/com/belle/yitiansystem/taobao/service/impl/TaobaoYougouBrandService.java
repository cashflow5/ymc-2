package com.belle.yitiansystem.taobao.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.yitiansystem.merchant.model.pojo.MerchantOperationLog.OperationType;
import com.belle.yitiansystem.merchant.service.IMerchantOperationLogService;
import com.belle.yitiansystem.taobao.dao.mapper.TaobaoYougouBrandMapper;
import com.belle.yitiansystem.taobao.exception.BusinessException;
import com.belle.yitiansystem.taobao.model.TaobaoBrand;
import com.belle.yitiansystem.taobao.model.TaobaoYougouBrand;
import com.belle.yitiansystem.taobao.service.ITaobaoYougouBrandService;
import com.yougou.merchant.api.common.PageFinder;
import com.yougou.merchant.api.common.Query;
import com.yougou.merchant.api.common.UUIDGenerator;

@Service("taobaoYougouBrandService")
public class TaobaoYougouBrandService implements ITaobaoYougouBrandService {

	@Resource
	private TaobaoYougouBrandMapper taobaoYougouBrandMapper;
	
	@Override
	public PageFinder<TaobaoYougouBrand> findTaoaoYougouBrandList(
			Map<String, Object> map, Query query) {
		RowBounds rowBounds = new RowBounds(query.getOffset(),
				query.getPageSize());
		int count = taobaoYougouBrandMapper.selectTaoaoYougouBrandCount(map);
		if (count == 0) {
			return new PageFinder<TaobaoYougouBrand>(query.getPage(),
					query.getPageSize(), 0, null);
		}
		List<TaobaoYougouBrand> list = taobaoYougouBrandMapper.selectTaoaoYougouBrandList(map, rowBounds);
		if (CollectionUtils.isEmpty(list)) {
			return new PageFinder<TaobaoYougouBrand>(query.getPage(),
					query.getPageSize(), 0, null);
		}
		PageFinder<TaobaoYougouBrand> pageFinder = new PageFinder<TaobaoYougouBrand>(
				query.getPage(), query.getPageSize(), count, list);
		return pageFinder;
	}

	@Override
	public List<TaobaoYougouBrand> findTaoaoYougouBrandList(
			Map<String, Object> map) {
		return taobaoYougouBrandMapper.selectTaoaoYougouBrandList(map);
	}

	@Override
	@Transactional
	public void deleteBatch(String ids,HttpServletRequest request) throws BusinessException {
		if(StringUtils.isEmpty(ids)){
			throw new BusinessException("参数错误");
		}
		String[] idArray = ids.split(",");
		List<String> idList =  Arrays.asList(idArray);
		
		String logStr = "" ;
		TaobaoYougouBrand tyb = null;
		for(String id:idArray){
			tyb= this.taobaoYougouBrandMapper.selectTYBById(id);	
			if(tyb!=null){
				logStr+= "淘宝品牌名称："+tyb.getTaobaoBrandName()+" bid:"+tyb.getTaobaoBid()+
						" 优购品牌名称: "+tyb.getYougouBrandName()+" 优购品牌No："+tyb.getYougouBrandNo() +"】 ";
			}
		}
		
		this.taobaoYougouBrandMapper.deleteBatch(idList);
		
		merchantOperationLogService.addMerchantOperationLog(
				"TAOBAO_BAND_BIND_DELETE", OperationType.TAOBAO_B_BIND,
				"淘宝品牌与优购品牌绑定删除 ：" +logStr, request);
	}

	@Transactional
	@Override
	public void saveTaobaoYougouBrandBatch(String[] yougouBrandNos, String[] yougouBrandNames, String[] taobaoBrandNos, String[] taobaoBrandNames, String creater) throws BusinessException {
		if (null == yougouBrandNos || null == yougouBrandNames
				|| null == taobaoBrandNos || null == taobaoBrandNames
				|| yougouBrandNos.length != yougouBrandNames.length
				|| yougouBrandNos.length != taobaoBrandNos.length
				|| yougouBrandNos.length != taobaoBrandNames.length) {
			throw	new  BusinessException("参数错误!");
		}
		int _len = yougouBrandNos.length;
		List<TaobaoYougouBrand> list = new ArrayList<TaobaoYougouBrand>();
		TaobaoYougouBrand brand = null;
		String logStr="";
		for(int i=0;i<_len;i++){
			brand = new TaobaoYougouBrand();
			brand.setId(UUIDGenerator.getUUID());
			brand.setTaobaoBid(taobaoBrandNos[i]);
			brand.setTaobaoBrandName(taobaoBrandNames[i]);
			brand.setYougouBrandNo(yougouBrandNos[i]);
			brand.setYougouBrandName(yougouBrandNames[i]);
			brand.setOperater(creater);
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			brand.setOperated(format.format(new Date()));
			logStr+= "【 淘宝品牌名称："+taobaoBrandNames[i]+" bid:"+taobaoBrandNos[i]+
					" 优购品牌名称: "+yougouBrandNames[i]+" 优购品牌No："+ yougouBrandNos[i] +"】 ";
			list.add(brand);
		}
		if(!list.isEmpty()){
			this.taobaoYougouBrandMapper.insertTaobaoYougouBrandBatch(list);
			merchantOperationLogService.addMerchantOperationLog(
					"TAOBAO_BAND_BIND_ADD", OperationType.TAOBAO_B_BIND,
					"淘宝品牌与优购品牌绑定：" +logStr, creater);
		}
 	}
	@Resource
	private IMerchantOperationLogService merchantOperationLogService;

	@Override
	public List<TaobaoBrand> findTaobaoBrandList(String taobaoBrandName) {
		if(StringUtils.isEmpty(taobaoBrandName)){
			return new ArrayList<TaobaoBrand>();
		}
		return taobaoYougouBrandMapper.selectTaobaoBrandList(taobaoBrandName);
	}
	
}
