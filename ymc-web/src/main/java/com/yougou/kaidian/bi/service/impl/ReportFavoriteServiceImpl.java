package com.yougou.kaidian.bi.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.yougou.kaidian.bi.dao.MMSDBReportFavoriteMapper;
import com.yougou.kaidian.bi.service.IReportFavoriteService;
import com.yougou.kaidian.commodity.component.CommodityStatus;
import com.yougou.kaidian.commodity.constant.CommodityConstant;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.common.util.DateUtil2;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.pc.api.ICommodityMerchantApiService;
import com.yougou.wms.wpi.common.exception.WPIBussinessException;
import com.yougou.wms.wpi.inventory.domain.vo.InventoryAssistVo;
import com.yougou.wms.wpi.inventory.service.IInventoryForMerchantService;

/**
 * 数据报表-收藏夹操作服务实现类
 * @author zhang.f1
 *
 */
@Service
public class ReportFavoriteServiceImpl implements IReportFavoriteService {
	private static final Logger logger = LoggerFactory.getLogger(ReportFavoriteServiceImpl.class);
	@Resource
	private MMSDBReportFavoriteMapper mmsdbReportFavoriteMapper ;
	
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Resource
	private ICommodityMerchantApiService commodityApi;
	
	@Resource
	private IInventoryForMerchantService inventoryForMerchantService;
	
	final static String COMMODITY_STATUS = "commodity_status";
	
	final static String REFUSE_REASON = "refuse_reason";
	
	@Override
	public void addFavoriteClassify(Map params) throws Exception {
		// TODO Auto-generated method stub
		params.put("order_by", this.queryClassifyOrderBy(params));
		mmsdbReportFavoriteMapper.addFavoriteClassify(params);
	}

	@Override
	public void updateFavoriteClassify(Map params) throws Exception {
		// TODO Auto-generated method stub
		mmsdbReportFavoriteMapper.updateFavoriteClassify(params);
	}

	@Override
	@Transactional
	public void deleteFavoriteClassify(String classifyId) throws Exception {
		// TODO Auto-generated method stub
		//获取商品信息进行删除
		List<Map<String, Object>>   favoriteCommodityList=mmsdbReportFavoriteMapper.queryfavoriteCommodityId(classifyId);
		Map   commodityIdmap=new HashMap();
		List<String>  list=new ArrayList<String>();
		if(favoriteCommodityList!=null && favoriteCommodityList.size()>0){
			for (int i = 0; i < favoriteCommodityList.size(); i++) {
				Map<String, Object>  map=favoriteCommodityList.get(i);
				list.add((String)map.get("fvr_commodity_id"));
			}
		}
		commodityIdmap.put("fvr_commodity_ids", list);
		mmsdbReportFavoriteMapper.batchDeleteFavoriteCommodityInfo(commodityIdmap);
		//删除收藏夹表
		mmsdbReportFavoriteMapper.deleteFavoriteClassify(classifyId);
		//删除收藏夹跟商品的关联表
		mmsdbReportFavoriteMapper.deleteClassifyCommodity(classifyId);
		
	}

	@Override
	public List<Map> queryFavoriteClassify(Map params) throws Exception {
		// TODO Auto-generated method stub
		List<Map>   favoriteClassifyList=mmsdbReportFavoriteMapper.queryFavoriteClassify(params);
		
		 return favoriteClassifyList;
	}

	@Override
	public int queryClassifyOrderBy(Map params) throws Exception {
		// TODO Auto-generated method stub
		return mmsdbReportFavoriteMapper.queryClassifyOrderBy(params);
	}

	@Override
	public void addFavoriteCommodity(Map params) throws Exception {
		// TODO Auto-generated method stub
		mmsdbReportFavoriteMapper.addFavoriteCommodity(params);
	}

	@Override
	@Transactional
	public void deleteFavoriteCommodity(Map params) throws Exception {
		// TODO Auto-generated method stub
		mmsdbReportFavoriteMapper.deleteFavoriteCommodityClassifyByNo(params);
		mmsdbReportFavoriteMapper.deleteFavoriteCommodity(params);
	}

	@Override
	@Transactional
	public void deleteFavoriteCommodityById(String id) throws Exception {
		// TODO Auto-generated method stub
		this.deleteFavoriteCommodityClassify(id);
		mmsdbReportFavoriteMapper.deleteFavoriteCommodityById(id);
		
	}

	@Override
	public List<Map> queryFavoriteCommodity(Map params) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer queryFavoriteCommodityCount(Map params) throws Exception {
		// TODO Auto-generated method stub
		return mmsdbReportFavoriteMapper.queryFavoriteCommodityCount(params);
	}

	@Override
	@Transactional
	public void addFavoriteCommodityClassify(Map params) throws Exception {
		// TODO Auto-generated method stub
		//新增收藏商品归类，将某个商品归类，首先删除其原有归类
		this.deleteFavoriteCommodityClassify((String)params.get("fvr_commodity_id"));
		//新增收藏商品所属归类
		mmsdbReportFavoriteMapper.addFavoriteCommodityClassify(params);
	}

	@Override
	@Transactional
	public void batchAddFavoriteCommodityClassify(Map params) throws Exception {
		// TODO Auto-generated method stub
		//先删除收藏商品原有的归类关系
		mmsdbReportFavoriteMapper.batchDeleteFavoriteCommodityClassify(params);
		// 然后新增收藏商品所属归类
		mmsdbReportFavoriteMapper.batchAddFavoriteCommodityClassify(params);
	}

	@Override
	public void deleteFavoriteCommodityClassify(String fvrCommodityId)
			throws Exception {
		// TODO Auto-generated method stub
		mmsdbReportFavoriteMapper.deleteFavoriteCommodityClassify(fvrCommodityId);
	}

	@Override
	public void batchDeleteFavoriteCommodityClassify(Map params)
			throws Exception {
		// TODO Auto-generated method stub
		mmsdbReportFavoriteMapper.batchDeleteFavoriteCommodityInfo(params);
		mmsdbReportFavoriteMapper.batchDeleteFavoriteCommodityClassify(params);
	}
	@Override
	public PageFinder<Map<String, Object>> queryFavoriteClassifyInfo(
			Query query, Map<String,Object> map) throws Exception {
		query.setPageSize(10);
		if(query != null){
			map.put("start", (query.getPage() -1) * query.getPageSize());
			map.put("pageSize", query.getPageSize());
		}

		//查询收藏数据
		List<Map<String, Object>> list = mmsdbReportFavoriteMapper.queryFavoriteCommodity(map);
		Integer count = mmsdbReportFavoriteMapper.queryFavoriteCommodityCount(map);
		
		//如果为空直接返回
		if (CollectionUtils.isEmpty(list)) {
			return new PageFinder<Map<String, Object>>(query.getPage(), query.getPageSize(), 0, list);
		}	
		
		
		List<String> commodityNoList = new ArrayList<String>();
		Object imgMessage=null;
		Date submit_audit_date=null;
		for (Map<String, Object> _obj : list) {

            
            //<p class="ml25">需参加周年庆</p> 
			
			_obj.put("firstClassifyName", getfirstClassifyName((String)_obj.get("first_classify_name")));
			//获取 商品编号 
			String commodityNo = MapUtils.getString(_obj, "no", "");
			if (StringUtils.isNotBlank(commodityNo)){ 
				commodityNoList.add(commodityNo);
			}
			imgMessage=this.redisTemplate.opsForHash().get(CacheConstant.C_IMAGE_MASTER_JMS_KEY, commodityNo);
			_obj.put("jmsFinish", imgMessage==null?0:1);
			
			submit_audit_date=commodityApi.getLastAuditTimeByCommodityNo(commodityNo, CommodityConstant.COMMODITY_UPDATE_LOG_TYPE_SUBMIT);
			_obj.put("submit_audit_date", submit_audit_date==null?null:DateUtil2.getDateTime(submit_audit_date));
			_obj.put("refuse_reason", commodityApi.getLastRefuseReasonByCommodityNo(commodityNo));
		}
		
		//查询可售库存
		Map<String, Integer> qtyMap = new HashMap<String, Integer>();
		try {
			List<InventoryAssistVo> qtys = null;
			if (StringUtils.isNotBlank((String)map.get("warehouseCode"))) {
				qtys = inventoryForMerchantService.queryCommodityInventory(commodityNoList, ((String)map.get("warehouseCode")));	
	            if (CollectionUtils.isNotEmpty(qtys)) {
	                for (InventoryAssistVo qtyVo : qtys) {
	                    qtyMap.put(qtyVo.getCommodityNo(), qtyVo.getCanSalesInventoryNum());
	                }
	            }
			} else {
				qtys = inventoryForMerchantService.queryInvenotryByCommodity(commodityNoList);
	            if (CollectionUtils.isNotEmpty(qtys)) {
	                for (InventoryAssistVo qtyVo : qtys) {
	                    qtyMap.put(qtyVo.getCommodityNo(), qtyVo.getInventoryQuantity());
	                }
	            }
			}

		} catch (WPIBussinessException e) {
			logger.error("商家编码：{},查询queryCommodityInventory发生异常.",YmcThreadLocalHolder.getMerchantCode(), e);
		}
		
		for (int i = 0, len = list.size(); i < len; i++) {
			completeCommodity(list.get(i), qtyMap);
		}
		
		
		
		PageFinder<Map<String, Object>> pageFinder = 
				new PageFinder<Map<String, Object>>(query.getPage(), query.getPageSize(),count);
		
		pageFinder.setData(list);
		
		return pageFinder;
	}
	
	/**
	 * <p>补全商品数据</p>
	 * 
	 * @param commodityMap
	 */
	private void completeCommodity(Map<String, Object> commodityMap, Map<String, Integer> qtyMap) {
		if (MapUtils.isEmpty(commodityMap)) {
			return;
		}
		String commodityNo = MapUtils.getString(commodityMap, "commodity_no", "");
		commodityMap.put("on_sale_quantity", qtyMap.containsKey(commodityNo) ? qtyMap.get(commodityNo) : 0);
		
			commodityMap.put("commodity_status_name",CommodityStatus.getStatusName((Integer)commodityMap.get("commodity_status")));
		
		// 审核拒绝才能展示拒绝原因
		if (!(CommodityConstant.COMMODITY_STATUS_REFUSE + "")
				.equals(MapUtils.getString(commodityMap, COMMODITY_STATUS, ""))) {
			commodityMap.put(REFUSE_REASON, null);
		}
	}
	
	@Override
	public List<Map<String, Object>> queryExportFavoriteInfo(Map<String, Object> map)
			throws Exception {
		List<Map<String, Object>> list = mmsdbReportFavoriteMapper.queryFavoriteCommodity(map);
		return list;
	}

	@Override
	public List<Map<String, Object>> queryFavoriteCommodityInfo(Map params)
			throws Exception {
		return mmsdbReportFavoriteMapper.queryFavoriteCommodityInfo(params);
	}

	@Override
	public List<Map<String, Object>> queryFavoriteInfo(Map params)
			throws Exception {
		
		return mmsdbReportFavoriteMapper.queryFavoriteInfo(params);
	}
    private String  getfirstClassifyName(String firstClassifyName){
    	StringBuffer  sb=new StringBuffer();
    	if(StringUtils.isNotBlank(firstClassifyName)){
    		
    		String [] classifyNameList=firstClassifyName.split(",");
    		for (int i = 0; i < classifyNameList.length; i++) {
    			if(i==(classifyNameList.length-1)  ||  i==2){
    				sb.append("<p class=\"ml25\">"+classifyNameList[i]);
    			}else{
    				sb.append("<p class=\"ml25\">"+classifyNameList[i]+"</p>");
    			}
    				
				if(i>=2){
					return sb.toString();
				}
			}
    	}
    	return sb.toString();
    	
    }
}
