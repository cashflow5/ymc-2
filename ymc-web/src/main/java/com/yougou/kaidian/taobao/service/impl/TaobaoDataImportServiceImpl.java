package com.yougou.kaidian.taobao.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Brand;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.ItemCat;
import com.taobao.api.domain.ItemImg;
import com.taobao.api.domain.ItemProp;
import com.taobao.api.domain.PropImg;
import com.taobao.api.domain.PropValue;
import com.taobao.api.domain.SellerAuthorize;
import com.taobao.api.domain.Sku;
import com.yougou.kaidian.common.util.DateUtil;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.kaidian.common.vo.ImageTaobaoMessage;
import com.yougou.kaidian.framework.exception.YMCException;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.taobao.common.TaobaoImportUtils;
import com.yougou.kaidian.taobao.constant.TaobaoImportConstants;
import com.yougou.kaidian.taobao.dao.TaobaoBrandMapper;
import com.yougou.kaidian.taobao.dao.TaobaoItemCatMapper;
import com.yougou.kaidian.taobao.dao.TaobaoItemExtendMapper;
import com.yougou.kaidian.taobao.dao.TaobaoItemExtendPropMapper;
import com.yougou.kaidian.taobao.dao.TaobaoItemImgMapper;
import com.yougou.kaidian.taobao.dao.TaobaoItemMapper;
import com.yougou.kaidian.taobao.dao.TaobaoItemPropImgMapper;
import com.yougou.kaidian.taobao.dao.TaobaoItemPropMapper;
import com.yougou.kaidian.taobao.dao.TaobaoItemPropValueMapper;
import com.yougou.kaidian.taobao.dao.TaobaoItemSkuMapper;
import com.yougou.kaidian.taobao.dao.TaobaoYougouItemCatTempletMapper;
import com.yougou.kaidian.taobao.dao.TaobaoYougouItemPropMapper;
import com.yougou.kaidian.taobao.enums.IsImportYougou;
import com.yougou.kaidian.taobao.exception.BusinessException;
import com.yougou.kaidian.taobao.model.TaobaoApiReturnData;
import com.yougou.kaidian.taobao.model.TaobaoBrand;
import com.yougou.kaidian.taobao.model.TaobaoItem;
import com.yougou.kaidian.taobao.model.TaobaoItemCat;
import com.yougou.kaidian.taobao.model.TaobaoItemCatProp;
import com.yougou.kaidian.taobao.model.TaobaoItemCatPropValue;
import com.yougou.kaidian.taobao.model.TaobaoItemExtend;
import com.yougou.kaidian.taobao.model.TaobaoItemExtendDto;
import com.yougou.kaidian.taobao.model.TaobaoItemExtendYougouPropValueSize;
import com.yougou.kaidian.taobao.model.TaobaoItemImg;
import com.yougou.kaidian.taobao.model.TaobaoItemOnSaleVO;
import com.yougou.kaidian.taobao.model.TaobaoItemProp;
import com.yougou.kaidian.taobao.model.TaobaoItemPropImg;
import com.yougou.kaidian.taobao.model.TaobaoItemPropValVO;
import com.yougou.kaidian.taobao.model.TaobaoItemPropValue;
import com.yougou.kaidian.taobao.model.TaobaoItemSku;
import com.yougou.kaidian.taobao.model.TaobaoYougouBrand;
import com.yougou.kaidian.taobao.model.TaobaoYougouItemCat;
import com.yougou.kaidian.taobao.model.TaobaoYougouItemProp;
import com.yougou.kaidian.taobao.service.ITaobaoDataImportService;
import com.yougou.kaidian.taobao.topapi.ITaobaoItemGet;
import com.yougou.kaidian.taobao.topapi.ITaobaoItemcatsAuthorizeGet;
import com.yougou.kaidian.taobao.topapi.ITaobaoItemcatsGet;
import com.yougou.kaidian.taobao.topapi.ITaobaoItempropsGet;
import com.yougou.kaidian.taobao.topapi.ITaobaoItempropvaluesGet;
import com.yougou.kaidian.taobao.topapi.ITaobaoItemsOnsaleGet;
import com.yougou.kaidian.taobao.topapi.impl.YougouTaobaoClient;
import com.yougou.kaidian.taobao.vo.ErrorVo;
import com.yougou.kaidian.taobao.vo.TaobaoCsvItemVO;
import com.yougou.kaidian.taobao.vo.TaobaoImportVo;
import com.yougou.merchant.api.supplier.service.IMerchantImageService;
import com.yougou.merchant.api.supplier.vo.ImageJmsVo;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.api.ICommodityMerchantApiService;
import com.yougou.pc.model.category.Category;

@Service(value="taobaoDataImportService")
public class TaobaoDataImportServiceImpl implements ITaobaoDataImportService {
	
	private static final Logger log = LoggerFactory.getLogger(TaobaoDataImportServiceImpl.class);
	
	@Resource
	private TaobaoBrandMapper taobaoBrandMapper;

	@Resource
	private TaobaoItemCatMapper taobaoItemCatMapper;
	
	@Resource
	private TaobaoItemPropMapper taobaoItemPropMapper;

	@Resource
	private TaobaoItemPropValueMapper taobaoItemPropValueMapper;
	
	@Resource
	private TaobaoItemExtendPropMapper taobaoItemExtendPropMapper;

	@Resource
	private TaobaoItemMapper taobaoItemMapper;

	@Resource
	private TaobaoItemExtendMapper taobaoItemExtendMapper;
	
	@Resource
	private TaobaoItemImgMapper taobaoItemImgMapper;

	@Resource
	private TaobaoItemPropImgMapper taobaoItemPropImgMapper;
	
	@Resource
	private TaobaoItemSkuMapper taobaoItemSkuMapper;
	
	@Resource
	private ITaobaoItemcatsAuthorizeGet taobaoItemcatsAuthorizeGet;
	
	@Resource
	private ITaobaoItemcatsGet taobaoItemcatsGet;
	
	@Resource
	private ITaobaoItempropsGet taobaoItempropsGet;
	
	@Resource
	private ITaobaoItempropvaluesGet taobaoItempropvaluesGet;
	
	@Resource
	private ITaobaoItemsOnsaleGet taobaoItemsOnsaleGet;
	
	@Resource
	private ITaobaoItemGet taobaoItemGet;
	
	@Resource
	@Qualifier(value="jmsTemplate")
	private AmqpTemplate amqpTemplate;
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Resource
	private IMerchantImageService merchantImageService;
	
	@Resource
	private ICommodityMerchantApiService commodityMerchantApi;
	
	@Resource
	private ICommodityBaseApiService commodityBaseApiService;
	
	@Resource
	private TaobaoYougouItemCatTempletMapper taobaoYougouItemCatTempletMapper;
	
	@Resource
	private TaobaoYougouItemPropMapper taobaoYougouItemPropMapper;
	
	private Map<ImpDataCount, Integer> mapImpSumCatProp = null; 
	
	/**
	 * 导入淘宝类目、品牌（根据授权优购对应的APPKEY）
	 * @param appKey
	 * @param appSecret
	 * @param sessionKey
	 * @param merchantCode
	 * @param operater
	 * @param nickId
	 * @return
	 * @throws IllegalAccessException
	 */
	public boolean importTaobaoBasicDataToYougou(String appKey, String appSecret, String sessionKey, String merchantCode, String operater, Long nickId) throws IllegalAccessException {
		if(appKey != null && !"".equals(appKey = appKey.trim()) && appSecret != null && !"".equals(appSecret = appSecret.trim())) {
			YmcThreadLocalHolder.setTaobaoClient( YougouTaobaoClient.getYougouTaobaoClient(appKey, appSecret) );
		} else {
			YmcThreadLocalHolder.setTaobaoClient( YougouTaobaoClient.getYougouTaobaoClient() );
		}
		return importTaobaoBasicDataToYougou(sessionKey, merchantCode, operater, nickId);
	}
	
	/**
	 * 导入淘宝类目、品牌（根据授权优购对应默认的APPKEY）
	 * @param sessionKey
	 * @param merchantCode
	 * @param operater
	 * @param nickId
	 * @return
	 * @throws IllegalAccessException
	 */
	private boolean importTaobaoBasicDataToYougou(String sessionKey, String merchantCode, String operater, Long nickId) throws IllegalAccessException {
		setInitParamData(sessionKey, merchantCode, operater, nickId);
		TaobaoApiReturnData<SellerAuthorize> taobaoApiReturnData = null;
		try {
			TaobaoClient taobaoClient = YmcThreadLocalHolder.getTaobaoClient();//Add by LQ.
			Map<String, String> mapParamData = initParamMap();
			taobaoApiReturnData = taobaoItemcatsAuthorizeGet.getItemcatsAuthorizeGet(taobaoClient, mapParamData);
		} catch (IllegalAccessException e) {
			log.error("[淘宝导入]商家编码:{}-淘宝接口调用异常", merchantCode, e);
		}catch (ApiException e) {
			log.error("[淘宝导入]商家编码:{}-淘宝接口调用异常", merchantCode, e);
		}
		if(taobaoApiReturnData != null) {
			SellerAuthorize sellerAuthorize = taobaoApiReturnData.getReturnData();
			if (sellerAuthorize != null) {
				importTaobaoSellerAuthorizeBrandToYougou(sellerAuthorize.getBrands());
				importTaobaoSellerAuthorizeItemcatToYougou(sellerAuthorize.getItemCats());
				importTaobaoSellerAuthorizeItemcatToYougou(sellerAuthorize.getXinpinItemCats());
			}
		}
		return true;
	}

	/**
	 * 按照淘宝一级类目ID导入类目数据
	 */
	public String importTaobaoItemcatToYougouByParentCids(String sessionKey, String merchantCode, String operater, String cids) throws IllegalAccessException {
		mapImpSumCatProp = new EnumMap<ImpDataCount, Integer>(ImpDataCount.class);
		setInitParamData(sessionKey, merchantCode, operater, null);
		//查找其下级分类
		TaobaoApiReturnData<List<ItemCat>> taobaoApiReturnData = null;
		try {
			TaobaoClient taobaoClient = YmcThreadLocalHolder.getTaobaoClient();//Add by LQ.
			Map<String, String> mapParamData = initParamMap();
			taobaoApiReturnData = taobaoItemcatsGet.getItemcatGet(taobaoClient, mapParamData, cids);
			if(taobaoApiReturnData != null) {
				List<ItemCat> lstItemCat2 = taobaoApiReturnData.getReturnData();
				importTaobaoItemcatToYougou(lstItemCat2);
			}
		} catch (IllegalAccessException e) {
			log.error("[淘宝导入]商家编码:{}-淘宝接口调用异常", merchantCode, e);
		} catch (ApiException e) {
			log.error("[淘宝导入]商家编码:{}-淘宝接口调用异常", merchantCode, e);
		}
		return getImpDataSumStr();
	}
	/**
	 * 导入淘宝卖家授权品牌数据到优购
	 * @param lstBrand
	 * @return
	 */
	@Transactional
	private boolean importTaobaoSellerAuthorizeBrandToYougou(List<Brand> lstBrand) {
		if (lstBrand != null && !lstBrand.isEmpty()) {
			TaobaoBrand taobaoBrand = null;
			TaobaoYougouBrand taobaoYougouBrand = null;
			List<TaobaoBrand> lstTaobaoBrand = new ArrayList<TaobaoBrand>(lstBrand.size());
			List<TaobaoYougouBrand> lstTaobaoYougouBrand = new ArrayList<TaobaoYougouBrand>(lstBrand.size());
			for (Brand brand : lstBrand) {
				// 根据pid、vid查询淘宝品牌表（tbl_merchant_taobao_brand）是否存在记录
				// 如果不存在加入列表
				taobaoBrand = taobaoBrandMapper.getTaobaoBrandByPidVid(brand.getPid(), brand.getVid());
				
				Long nickId = YmcThreadLocalHolder.getNickId();//Add by LQ on 20150602
				
				if (taobaoBrand != null) {
					if (taobaoBrandMapper.getTaobaoYougouBrandByBid(YmcThreadLocalHolder.getMerchantCode(), nickId, taobaoBrand.getBid()) == null) {
						taobaoYougouBrand = new TaobaoYougouBrand();
						taobaoYougouBrand.setId(UUIDGenerator.getUUID());
						taobaoYougouBrand.setTaobaoBid(taobaoBrand.getBid());
						taobaoYougouBrand.setNickId(nickId);
						taobaoYougouBrand.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
						taobaoYougouBrand.setOperater(YmcThreadLocalHolder.getOperater());
						taobaoYougouBrand.setOperated(DateUtil.getFormatByDate(new Date()));
						lstTaobaoYougouBrand.add(taobaoYougouBrand);
					}
				} else {
					taobaoBrand = new TaobaoBrand();
					String uuid = UUIDGenerator.getUUID();
					taobaoBrand.setBid(uuid);
					taobaoBrand.setVid(brand.getVid());
					taobaoBrand.setName(brand.getName());
					taobaoBrand.setPid(brand.getPid());
					taobaoBrand.setPropName(brand.getPropName());
					taobaoBrand.setOperater(YmcThreadLocalHolder.getOperater());
					taobaoBrand.setOperated(DateUtil.getFormatByDate(new Date()));
					lstTaobaoBrand.add(taobaoBrand);
					taobaoYougouBrand = new TaobaoYougouBrand();
					taobaoYougouBrand.setId(UUIDGenerator.getUUID());
					taobaoYougouBrand.setTaobaoBid(uuid);
					taobaoYougouBrand.setNickId(nickId);
					taobaoYougouBrand.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
					taobaoYougouBrand.setOperater(YmcThreadLocalHolder.getOperater());
					taobaoYougouBrand.setOperated(DateUtil.getFormatByDate(new Date()));
					lstTaobaoYougouBrand.add(taobaoYougouBrand);
				}
			}
			if (lstTaobaoBrand != null && !lstTaobaoBrand.isEmpty()) {
				taobaoBrandMapper.insertTaobaoBrandList(lstTaobaoBrand);
			}
			if (lstTaobaoYougouBrand != null && !lstTaobaoYougouBrand.isEmpty()) {
				taobaoBrandMapper.insertTaobaoYougouBrandList(lstTaobaoYougouBrand);
			}
		}
		return true;
	}

	/**
	 * 导入淘宝卖家授权分类数据到优购
	 */
	@Transactional
	public void importTaobaoSellerAuthorizeItemcatToYougou(List<ItemCat> lstItemCat) {
		//根据merchantCode,nickId,cid判断是否存在于tbl_merchant_taobao_yougou_cat表数据
		//如果不存在则存入tbl_merchant_taobao_yougou_cat
		if (lstItemCat != null && !lstItemCat.isEmpty()) {
			TaobaoYougouItemCat taobaoYougouItemCat ;
			for (ItemCat itemcat : lstItemCat) {
				long cid = itemcat.getCid();
				
				Long nickId = YmcThreadLocalHolder.getNickId();//Add by LQ on 20150602
				
				if(taobaoItemCatMapper.getTaobaoYougouItemCatByCid(YmcThreadLocalHolder.getMerchantCode(), nickId, cid)==null) {
					taobaoYougouItemCat = new TaobaoYougouItemCat();
					taobaoYougouItemCat.setId(UUIDGenerator.getUUID());
					taobaoYougouItemCat.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
					taobaoYougouItemCat.setNickId(nickId);
					taobaoYougouItemCat.setOperated(DateUtil.getFormatByDate(new Date()));
					taobaoYougouItemCat.setOperater(YmcThreadLocalHolder.getOperater());
					taobaoYougouItemCat.setTaobaoCid(cid);
					taobaoItemCatMapper.insertTaobaoYougouItemCat(taobaoYougouItemCat);
				}
			}
		}
		importTaobaoItemcatToYougou(lstItemCat);
	}
	
	/**
	 * 导入淘宝类目
	 */
	@Transactional
	private int importTaobaoItemcatToYougou(List<ItemCat> lstItemCat) {
		int iEndCatCount = 0;
		if (lstItemCat != null && !lstItemCat.isEmpty()) {
			for (ItemCat itemcat : lstItemCat) {
				long cid = itemcat.getCid();
				//根据cid判断是否存在于tbl_merchant_taobao_cat表数据
				TaobaoItemCat taobaoItemCat = taobaoItemCatMapper.getTaobaoItemCatByCid(cid);
				if(taobaoItemCat == null) {
					//保存到tbl_merchant_yougou_cat
					taobaoItemCat = new TaobaoItemCat();
					taobaoItemCat.setCid(itemcat.getCid());
					taobaoItemCat.setIsParent(itemcat.getIsParent());
					taobaoItemCat.setName(itemcat.getName());
					taobaoItemCat.setParentCid(itemcat.getParentCid());
					taobaoItemCat.setSortOrder(itemcat.getSortOrder());
					taobaoItemCat.setStatus(itemcat.getStatus());
					taobaoItemCat.setOperated(DateUtil.getFormatByDate(new Date()));
					taobaoItemCat.setOperater(YmcThreadLocalHolder.getOperater());
					taobaoItemCatMapper.insertTaobaoItemCat(taobaoItemCat);
					if(!itemcat.getIsParent()) {
						iEndCatCount ++;
					}
				}
				importTaobaoYougouItemCat(itemcat);
			}
		}
		return iEndCatCount;
	}
	/**
	 * 关联末级淘宝类目导入
	 * @param itemcat
	 * @return
	 */
	@Transactional
	private int importTaobaoYougouItemCat(ItemCat itemcat){
		long cid = itemcat.getCid();
		int iEndCatCount = 0;
		//判断是否是末级类目
		//如果是末级类目那么判断是否存在于tbl_merchant_taobao_yougou_cat表
		//如果不是末级查找其下级分类
		if (!itemcat.getIsParent()) {
			importTaobaoItemProp(cid);
		} else {
			//查找其下级分类
			TaobaoApiReturnData<List<ItemCat>> taobaoApiReturnData = null;
			try {
				TaobaoClient taobaoClient = YmcThreadLocalHolder.getTaobaoClient();//Add by LQ.
				Map<String, String> mapParamData = initParamMap();
				taobaoApiReturnData = taobaoItemcatsGet.getSubItemcatsGet(taobaoClient, mapParamData, cid);
				if(taobaoApiReturnData != null) {
					List<ItemCat> lstItemCat2 = taobaoApiReturnData.getReturnData();
					iEndCatCount = importTaobaoItemcatToYougou(lstItemCat2);
					putImpCount(ImpDataCount.ENDCAT, iEndCatCount);
				}
			} catch (IllegalAccessException e) {
				log.error("[淘宝导入]商家编码:{}-淘宝接口调用异常", YmcThreadLocalHolder.getMerchantCode(), e);
			} catch (ApiException e) {
				log.error("[淘宝导入]商家编码:{}-淘宝接口调用异常", YmcThreadLocalHolder.getMerchantCode(), e);
			}
		}
		return iEndCatCount;
	}
	
	private Map<String,String> initParamMap(){
		Map<String,String>	mapParamData = new HashMap<String, String>();
		mapParamData.put("sessionKey", YmcThreadLocalHolder.getSessionKey() );
		mapParamData.put("merchantCode", YmcThreadLocalHolder.getMerchantCode() );
		mapParamData.put("operater", YmcThreadLocalHolder.getOperater() );
		mapParamData.put("nickId", ""+YmcThreadLocalHolder.getNickId());
		return mapParamData;
	}
	
	/**
	 * 抓取末级分类属性
	 * 判断是否存在有对应的商品属性（不存在写入商品属性）
	 * 判断是否存在有分类对应的商品属性（不存在写入分类对应的商品属性）
	 * @param cid
	 * @return
	 */
	private boolean importTaobaoItemProp(long cid){
		return importTaobaoItemProp(cid, null);
	}
	/**
	 * 抓取末级分类属性
	 * 判断是否存在有对应的商品属性（不存在写入商品属性）
	 * 判断是否存在有分类对应的商品属性（不存在写入分类对应的商品属性）
	 * @param cid
	 * @return
	 */
	@Transactional
	private boolean importTaobaoItemProp(long cid, String childPath) {
		List<ItemProp> lstItemProp = null;
		try {
			TaobaoClient taobaoClient = YmcThreadLocalHolder.getTaobaoClient();//Add by LQ.
			Map<String, String> mapParamData = initParamMap();
			TaobaoApiReturnData<List<ItemProp>> taobaoApiReturnData = taobaoItempropsGet.getItempropsGet(taobaoClient, mapParamData, cid, childPath);
			if(taobaoApiReturnData != null) {
				lstItemProp = taobaoApiReturnData.getReturnData();
			}
		} catch (IllegalAccessException e) {
			log.error("[淘宝导入]商家编码:{}-抓取淘宝末级分类属性接口异常", YmcThreadLocalHolder.getMerchantCode(), e);
		} catch (ApiException e) {
			log.error("[淘宝导入]商家编码:{}-抓取淘宝末级分类属性接口异常", YmcThreadLocalHolder.getMerchantCode(), e);
		}
		List<TaobaoItemProp> lstTaobaoItemProp = null;
		List<TaobaoItemCatProp> lstTaobaoItemCatProp = null;
		if(lstItemProp != null && !lstItemProp.isEmpty()) {
			int size = lstItemProp.size();
			lstTaobaoItemProp = new ArrayList<TaobaoItemProp>(size);
			lstTaobaoItemCatProp = new ArrayList<TaobaoItemCatProp>(size);
			TaobaoItemProp taobaoItemProp = null;
			TaobaoItemCatProp taobaoItemCatProp = null;
			for(ItemProp itemProp : lstItemProp) {
				long pid = itemProp.getPid();
				taobaoItemCatProp = new TaobaoItemCatProp();
				taobaoItemCatProp.setCid(cid);
				taobaoItemCatProp.setPid(pid);
				//按照pid判断是否在DB中存在有对应的商品属性
				taobaoItemProp = taobaoItemPropMapper.getTaobaoItemProp(pid);
				if(taobaoItemProp == null) {
					taobaoItemProp = new TaobaoItemProp();
					taobaoItemProp.setPid(itemProp.getPid());
					taobaoItemProp.setName(itemProp.getName());
					taobaoItemProp.setMust(itemProp.getMust());
					taobaoItemProp.setMulti(itemProp.getMulti());
					taobaoItemProp.setStatus(itemProp.getStatus());
					taobaoItemProp.setSortOrder(itemProp.getSortOrder());
					taobaoItemProp.setParentPid(itemProp.getParentPid());
					taobaoItemProp.setParentVid(itemProp.getParentVid());
					taobaoItemProp.setIsKeyProp(itemProp.getIsKeyProp());
					taobaoItemProp.setIsSaleProp(itemProp.getIsSaleProp());
					taobaoItemProp.setIsColorProp(itemProp.getIsColorProp());
					taobaoItemProp.setIsEnumProp(itemProp.getIsEnumProp());
					taobaoItemProp.setIsInputProp(itemProp.getIsInputProp());
					taobaoItemProp.setIsItemProp(itemProp.getIsItemProp());
					taobaoItemProp.setIsAllowAlias(itemProp.getIsAllowAlias());
					taobaoItemProp.setOperater(YmcThreadLocalHolder.getOperater());
					taobaoItemProp.setOperated(DateUtil.getFormatByDate(new Date()));
					lstTaobaoItemProp.add(taobaoItemProp);
					//同时增加分类对应的商品属性
					lstTaobaoItemCatProp.add(taobaoItemCatProp);
					
				} else {
					//根据cid、pid判断是否存在分类对应的商品属性
					int iCount = taobaoItemPropMapper.getTaobaoItemCatPropCount(taobaoItemCatProp);
					if(iCount == 0) {
						lstTaobaoItemCatProp.add(taobaoItemCatProp);
					}
				}
				if(pid != TaobaoImportConstants.BRAND_P_ID) {
					//直接从属性接口的返回属性值列表取
					importTaobaoItemPropValue(cid, pid, itemProp.getPropValues());
				} else {
					importTaobaoItemBrand(itemProp.getPropValues());
				}
			}
		}
		//导入淘宝商品属性
		if(lstTaobaoItemProp != null && !lstTaobaoItemProp.isEmpty()) {
			taobaoItemPropMapper.insertTaobaoItemPropList(lstTaobaoItemProp);
			log.info("[淘宝导入]商家编码:{}-导入淘宝商品属性个数{}", YmcThreadLocalHolder.getMerchantCode(), lstTaobaoItemProp.size());
		}
		//导入淘宝类目属性
		if(lstTaobaoItemCatProp != null && !lstTaobaoItemCatProp.isEmpty()) {
			taobaoItemPropMapper.insertTaobaoItemCatPropList(lstTaobaoItemCatProp);
			int iSize = lstTaobaoItemCatProp.size();
			this.putImpCount(ImpDataCount.PROP, iSize);
			log.info("[淘宝导入]商家编码:{}-导入淘宝分类属性个数{}", YmcThreadLocalHolder.getMerchantCode(), iSize);
		}
		return true;
	}
	
	/**
	 * 导入淘宝分类属性值
	 * @param cid
	 * @param pid
	 * @param lstPropValue
	 * @return
	 */
	@Transactional
	private synchronized boolean importTaobaoItemPropValue(long cid, long pid, List<PropValue> lstPropValue){
		//添加商品属性值(需要判断属性值是否存在)
		if(lstPropValue != null && !lstPropValue.isEmpty()) {
			int iCount = lstPropValue.size();
			List<TaobaoItemPropValue> lstTaobaoItemPropValue = new ArrayList<TaobaoItemPropValue>(iCount);
			List<TaobaoItemCatPropValue> lstTaobaoItemCatPropValue = new ArrayList<TaobaoItemCatPropValue>(iCount);
			TaobaoItemPropValue taobaoItemPropValue = null;
			TaobaoItemCatPropValue taobaoItemCatPropValue = null;
			for(PropValue propValue : lstPropValue) {
				long vid = propValue.getVid();
				taobaoItemPropValue = taobaoItemPropValueMapper.getTaobaoItemPropValueByVid(vid);
				if(taobaoItemPropValue == null) {
					taobaoItemPropValue = new TaobaoItemPropValue();
					taobaoItemPropValue.setVid(vid);
					taobaoItemPropValue.setName(propValue.getName());
					if(propValue.getIsParent() != null && propValue.getIsParent()) {
						taobaoItemPropValue.setIsParent(propValue.getIsParent());
					} else {
						taobaoItemPropValue.setIsParent(false);
					}
					taobaoItemPropValue.setOperater(YmcThreadLocalHolder.getOperater());
					taobaoItemPropValue.setOperated(DateUtil.getFormatByDate(new Date()));
					lstTaobaoItemPropValue.add(taobaoItemPropValue);
					taobaoItemCatPropValue = new TaobaoItemCatPropValue();
					taobaoItemCatPropValue.setCid(cid);
					taobaoItemCatPropValue.setPid(pid);
					taobaoItemCatPropValue.setVid(vid);
					lstTaobaoItemCatPropValue.add(taobaoItemCatPropValue);
				} else {
					taobaoItemCatPropValue = taobaoItemPropValueMapper.getTaobaoItemCatPropValueByVid(cid, pid, vid);
					if(taobaoItemCatPropValue == null) {
						taobaoItemCatPropValue = new TaobaoItemCatPropValue();
						taobaoItemCatPropValue.setCid(cid);
						taobaoItemCatPropValue.setPid(pid);
						taobaoItemCatPropValue.setVid(vid);
						lstTaobaoItemCatPropValue.add(taobaoItemCatPropValue);
					}
				}
				if(propValue.getIsParent() != null && propValue.getIsParent()){
					String childPidvid = pid+":"+propValue.getVid();
					importTaobaoItemProp(cid, childPidvid);
				}
			}
			if(lstTaobaoItemPropValue != null && !lstTaobaoItemPropValue.isEmpty()) {
				taobaoItemPropValueMapper.insertTaobaoItemPropValueList(lstTaobaoItemPropValue);
			}
			if(lstTaobaoItemCatPropValue != null && !lstTaobaoItemCatPropValue.isEmpty()) {
				taobaoItemPropValueMapper.insertTaobaoItemCatPropValueList(lstTaobaoItemCatPropValue);
				int iSize = lstTaobaoItemCatPropValue.size();
				putImpCount(ImpDataCount.PROPVAL, iSize);
			}
		}
		return true;
	}
	@Transactional
	private void importTaobaoItemBrand(List<PropValue> lstPropValue) {
		final String TAOBAO_BRAND = "TAOBAO_BRAND";
		final int TIME_OUT = 30;
		//
		@SuppressWarnings("unchecked")
		List<String> lstTaobaoPV =  (List<String>)redisTemplate.opsForHash().get(TAOBAO_BRAND, TAOBAO_BRAND);
		if(lstTaobaoPV == null || lstTaobaoPV.isEmpty()) {
			lstTaobaoPV = taobaoBrandMapper.getTaobaoBrandPV();
			redisTemplate.opsForHash().put(TAOBAO_BRAND, TAOBAO_BRAND, lstTaobaoPV);
			redisTemplate.expire(TAOBAO_BRAND, TIME_OUT, TimeUnit.MINUTES);
		}
		List<TaobaoBrand> lstTaobaoBrand = new ArrayList<TaobaoBrand>();
		TaobaoBrand taobaoBrand = null;
		if(lstPropValue!=null){
			for(PropValue p : lstPropValue) {
				//如果缓存中不存在对应的品牌，那么把该品牌添加到DB，并且更新到缓存。
				if (p != null) {
					if (!lstTaobaoPV.contains(TaobaoImportConstants.P_ID_SEARCH + p.getVid())) {
						taobaoBrand = taobaoBrandMapper.getTaobaoBrandByPidVid(
								TaobaoImportConstants.BRAND_P_ID, p.getVid());
						if (taobaoBrand == null) {
							taobaoBrand = new TaobaoBrand();
							String uuid = UUIDGenerator.getUUID();
							taobaoBrand.setBid(uuid);
							taobaoBrand.setVid(p.getVid());
							taobaoBrand.setName(p.getName());
							taobaoBrand.setPid(TaobaoImportConstants.BRAND_P_ID);
							taobaoBrand.setPropName("品牌");
							taobaoBrand.setOperater(YmcThreadLocalHolder.getOperater());
							taobaoBrand.setOperated(DateUtil.getFormatByDate(new Date()));
							lstTaobaoBrand.add(taobaoBrand);
							lstTaobaoPV.add(TaobaoImportConstants.P_ID_SEARCH + p.getVid());
						}
					}
				}
			}
		}
		if (lstTaobaoBrand != null && !lstTaobaoBrand.isEmpty()) {
			taobaoBrandMapper.insertTaobaoBrandList(lstTaobaoBrand);
		}
	}

	/**
	 * 按照分类导入商家淘宝店铺在售商品（根据授权优购对应的APPKEY）
	 * @param appKey
	 * @param appSecret
	 * @param sessionKey
	 * @param merchantCode
	 * @param operater
	 * @param nickId
	 * @return
	 * @throws IllegalAccessException
	 */
	@Override
	public Map<String,Integer> importTaobaoOnSalaItemByCidToYougou(String appKey, String appSecret, String sessionKey, String merchantCode, String operater, Long nickId, Long cid) throws IllegalAccessException {
		
		if(appKey != null && !"".equals(appKey = appKey.trim()) && appSecret != null && !"".equals(appSecret = appSecret.trim())) {
			YmcThreadLocalHolder.setTaobaoClient( YougouTaobaoClient.getYougouTaobaoClient(appKey, appSecret) );
		} else {
			YmcThreadLocalHolder.setTaobaoClient( YougouTaobaoClient.getYougouTaobaoClient() );
		}
		return importTaobaoOnSalaItemToYougou(sessionKey, merchantCode, operater, nickId, cid);
	}

	/**
	 * 导入商家淘宝店铺全部在售商品（根据授权优购对应的APPKEY）
	 * @param appKey
	 * @param appSecret
	 * @param sessionKey
	 * @param merchantCode
	 * @param operater
	 * @param nickId
	 * @param cid
	 * @return
	 * @throws IllegalAccessException
	 */
	@Override
	public Map<String, Integer> importTaobaoAllOnSalaItemToYougou(String appKey, String appSecret, String sessionKey, String merchantCode, String operater, Long nickId) throws IllegalAccessException {
		return importTaobaoOnSalaItemByCidToYougou(appKey, appSecret, sessionKey, merchantCode, operater, nickId, null);
	}

	/**
	 * 导入商家淘宝店铺在售商品（根据授权优购对应默认的APPKEY）
	 * @param sessionKey
	 * @param merchantCode
	 * @param operater
	 * @param nickId
	 * @return
	 * @throws IllegalAccessException
	 */
	private Map<String, Integer> importTaobaoOnSalaItemToYougou(String sessionKey, String merchantCode, String operater, Long nickId, Long cid) throws IllegalAccessException {
		Map<String,Integer> resultTotal = new HashMap<String,Integer>();
		resultTotal.put("itemTotal", 0);
		resultTotal.put("extendTotal", 0);
		YmcThreadLocalHolder.setNickId(nickId);
		setInitParamData(sessionKey, merchantCode, operater, nickId);
		long pageNo = 1l;
		final long pageSize = 100l;
		long pageCount = 1l;
		long total = 0l;
		TaobaoItemOnSaleVO taobaoItemOnSaleVO = null;
		TaobaoApiReturnData<TaobaoItemOnSaleVO> taobaoApiReturnData = null;
		TaobaoClient taobaoClient = YmcThreadLocalHolder.getTaobaoClient();//Add by LQ.
		Map<String, String> mapParamData = initParamMap();
		try {
			taobaoApiReturnData = taobaoItemsOnsaleGet.getItemsOnsaleGet(taobaoClient, mapParamData, cid, pageNo, pageSize);
			if(taobaoApiReturnData != null) {
				taobaoItemOnSaleVO = taobaoApiReturnData.getReturnData();
			}
		} catch (IllegalAccessException e) {
			log.error("[淘宝导入]商家编码:{}-获取商家淘宝店铺在售商品API出现异常",YmcThreadLocalHolder.getMerchantCode(), e);
		} catch (ApiException e) {
			log.error("[淘宝导入]商家编码:{}-获取商家淘宝店铺在售商品API出现异常",YmcThreadLocalHolder.getMerchantCode(), e);
		}
		if(taobaoItemOnSaleVO == null) {
			return resultTotal;
		}
		total = taobaoItemOnSaleVO.getTotalResult();
		List<Item> lstItem = null;
		if (total > 0) {
			lstItem = taobaoItemOnSaleVO.getLstItem();
			for (Item item : lstItem) {
				Map<String,Integer>  singleTotal = importTaobaoItemData(item,merchantCode);
				resultTotal.put("itemTotal", resultTotal.get("itemTotal").intValue()+singleTotal.get("itemTotal").intValue());
				resultTotal.put("extendTotal", resultTotal.get("extendTotal").intValue()+singleTotal.get("extendTotal").intValue());
			}
		}
		if(total > pageSize) {
			pageCount = total / pageSize;
			if(total % pageSize == 0) {
				pageCount --;
			}
			for(int i = 0; i < pageCount; i ++) {
				try {
					taobaoApiReturnData = taobaoItemsOnsaleGet.getItemsOnsaleGet(taobaoClient, null, cid, ++ pageNo, pageSize);
					if(taobaoApiReturnData != null) {
						taobaoItemOnSaleVO = taobaoApiReturnData.getReturnData();
					}
				} catch (IllegalAccessException e) {
					log.error("[淘宝导入]商家编码:{}-获取商家淘宝店铺在售商品API出现异常",YmcThreadLocalHolder.getMerchantCode(), e);
				} catch (ApiException e) {
					log.error("[淘宝导入]商家编码:{}-获取商家淘宝店铺在售商品API出现异常",YmcThreadLocalHolder.getMerchantCode(), e);
				}
				if(taobaoItemOnSaleVO != null) {
					lstItem = taobaoItemOnSaleVO.getLstItem();
					for(Item item : lstItem) {
						Map<String,Integer>  singleTotal = importTaobaoItemData(item,merchantCode);
						resultTotal.put("itemTotal", resultTotal.get("itemTotal").intValue()+singleTotal.get("itemTotal").intValue());
						resultTotal.put("extendTotal", resultTotal.get("extendTotal").intValue()+singleTotal.get("extendTotal").intValue());
					}
				}
			}
		}
		return resultTotal;
	}
	
	/**
	 * 导入淘宝商品数据
	 * @param sessionKey
	 * @param item
	 */
	private Map<String, Integer> importTaobaoItemData(Item item,String merchantCode) throws IllegalAccessException{
		Map<String,Integer> resultTotal = new HashMap<String,Integer>();
		resultTotal.put("itemTotal", 0);
		resultTotal.put("extendTotal", 0);
		long numIid = item.getNumIid();
		try {
			TaobaoClient taobaoClient = YmcThreadLocalHolder.getTaobaoClient();//Add by LQ.
			Map<String, String> mapParamData = initParamMap();
			TaobaoApiReturnData<Item> taobaoApiReturnData = taobaoItemGet.getItemGet(taobaoClient, mapParamData, numIid);
			item = taobaoApiReturnData.getReturnData();
			
		} catch (IllegalAccessException e) {
			log.error("[淘宝导入]商家编码:{}-获取淘宝商品详情数据异常",YmcThreadLocalHolder.getMerchantCode(), e);
		} catch (ApiException e) {
			log.error("[淘宝导入]商家编码:{}-获取淘宝商品详情数据异常",YmcThreadLocalHolder.getMerchantCode(), e);
		}
		if(item != null) {
			ImageJmsVo message=new ImageJmsVo();
			message.setId(UUIDGenerator.getUUID());
			message.setMerchantCode(merchantCode);
			message.setCommodityNo(String.valueOf(numIid));
			message.setPicType("t");
			message.setStatus(0);
			message.setUrlFragment("/merchantpics/taobao/"+merchantCode);
			Date now=new Date();
			message.setCreateTime(now);
			message.setUpdateTime(now);
			//导入主表信息
			TaobaoCsvItemVO taobaoitem =new TaobaoCsvItemVO();
			taobaoitem.setItem(item);
			boolean isImportTaobaoItem = importTaobaoItem(taobaoitem, message,null,null,merchantCode);
			
			if(isImportTaobaoItem){
				int itemTotal = resultTotal.get("itemTotal") + 1;
				resultTotal.put("itemTotal",itemTotal);
				//导入商品属性图片
				importItemPropImg(numIid, item.getPropImgs());
			} 
			//导入SKU信息
			Map<String, Set<String>> mapItemSkuData = importItemSku(numIid,
					item.getCid(), item.getSkus(),isImportTaobaoItem,merchantCode);
			//导入扩展信息
			List<String> extendIds = importItemExtendData(numIid,
					item.getCid(), mapItemSkuData,item,merchantCode);
			if(extendIds!=null&&!extendIds.isEmpty()){
				int extendTotal = resultTotal.get("extendTotal") + extendIds.size();
				resultTotal.put("extendTotal",extendTotal);
			}
			if(extendIds!=null&&!extendIds.isEmpty()&&!isImportTaobaoItem){
				resultTotal.put("itemTotal",resultTotal.get("extendTotal")+1);
			}
			//导入商品图片
			importItemImg(numIid, item.getItemImgs(), extendIds, message);
			if(!StringUtils.isEmpty(message.getImageId())){
				// 发送mq通知处理图片
				merchantImageService.addImageJms(message);
				ImageTaobaoMessage imgMessage = new ImageTaobaoMessage();
				imgMessage.setId(message.getId());
				imgMessage.setImgArry(StringUtils.split(message.getImageId(),
						","));
				imgMessage.setMerchantCode(merchantCode);
				imgMessage.setNumIid(message.getCommodityNo());
				imgMessage.setProDesc(message.getProDesc());
				try {
					amqpTemplate.convertAndSend("ymc.handleimage.taobao.queue",	imgMessage);
				} catch (AmqpException e) {
					log.error("[淘宝导入]商家编码:{}商品编码{}-淘宝商品发送处理图片产生异常:", merchantCode, numIid, e);
				}
			}
		}
		return resultTotal;
	}
	
	
	
	/**
	 * 重写  导入商品图片方法  --add by lsm 
	 * @param numIid
	 * @param lstItemImg
	 * @return
	 */
	@Transactional
	private String importItemImgTODB(long numIid, List<ItemImg> lstItemImg, List<String> extendIds) {
		StringBuilder strBuileder=new StringBuilder();
		
		if(CollectionUtils.isNotEmpty(lstItemImg)) {
			List<TaobaoItemImg> lstTaobaoItemImg = new ArrayList<TaobaoItemImg>(lstItemImg.size());
			int index=1;
			TaobaoItemImg taobaoItemImg = null;
			for(ItemImg itemImg : lstItemImg) {
				String imgUrl = itemImg.getUrl();
				if(extendIds != null && extendIds.size()>0) {
					for(String extendId : extendIds) {
						if(StringUtils.isNotBlank(extendId)) {
							taobaoItemImg = new TaobaoItemImg();
							taobaoItemImg.setImgId(UUIDGenerator.getUUID());
							taobaoItemImg.setNumIid(numIid);
							taobaoItemImg.setExtendId(extendId);
							taobaoItemImg.setId(itemImg.getId());
							taobaoItemImg.setPosition(itemImg.getPosition());
							taobaoItemImg.setUrl(imgUrl);
							lstTaobaoItemImg.add(taobaoItemImg);
						}
					}
					//将外部平台的图片下载校验转成优购的
					if(index != 1){
						strBuileder.append(",");
					}
					strBuileder.append(imgUrl);
				} 
				index++;
			}
			
			if(CollectionUtils.isNotEmpty(lstTaobaoItemImg)) {
				for(String extendId : extendIds){
					taobaoItemImgMapper.deleteTaobaoItemImgByExtendId(extendId);
				}
				taobaoItemImgMapper.insertTaobaoItemImgList(lstTaobaoItemImg);
			}
		}
		return strBuileder.toString();
	}	
	
	class UploadImage implements Runnable{

		private long numIid;
		private String itemDesc;
		private ImageJmsVo message;
		private String merchantCode;
		
		public UploadImage(long numIid,String itemDesc,
				ImageJmsVo message,String merchantCode){
			this.numIid = numIid;
			this.merchantCode = merchantCode;
			this.message = message;
			this.itemDesc = itemDesc;
		}
		
		@Override
		public void run() {
			message.setId(UUIDGenerator.getUUID());
			message.setMerchantCode(merchantCode);
			message.setCommodityNo(String.valueOf(numIid));
			message.setPicType("t");
			message.setStatus(0);
			message.setUrlFragment("/merchantpics/taobao/"+merchantCode);
			Date now=new Date();
			message.setCreateTime(now);
			message.setUpdateTime(now);
			
			message.setProDesc(itemDesc);
			// 发送mq通知处理图片
			merchantImageService.addImageJms(message);
			ImageTaobaoMessage imgMessage = new ImageTaobaoMessage();
			imgMessage.setId(message.getId());
			imgMessage.setImgArry(StringUtils.split(message.getImageId(),
					","));
			imgMessage.setMerchantCode(merchantCode);
			imgMessage.setNumIid(message.getCommodityNo());
			imgMessage.setProDesc(message.getProDesc());
			try {
				amqpTemplate.convertAndSend("ymc.handleimage.taobao.queue",imgMessage);
			} catch (AmqpException e) {
				log.error("[淘宝导入]商家编码:{}商品编码{}-淘宝商品发送处理图片产生异常:", merchantCode, numIid, e);
			}
			
		}
		
	}

	@Override
	@Transactional
	public void importTaobaoItemData4CSVForSimple(TaobaoCsvItemVO tbitem, 
			String merchantCode,TaobaoImportVo importVo) {
	 
		 Item item = tbitem.getItem();
			try {
				//1 重复判断判断，如果商品重复，其他操作一概不用
				TaobaoItem taobaoItemInDB = taobaoItemMapper.getTaobaoItemByNumIid(item.getNumIid());
				if(taobaoItemInDB!=null){
					int existTotal = importVo.getResultTotal().get("existTotal") == null ? 1 : importVo.getResultTotal().get("existTotal") + 1;
					importVo.getResultTotal().put("existTotal",existTotal);
					if(importVo.getErrorList()!=null){
						importVo.getErrorList().add(new ErrorVo(tbitem.getArrayIndex(),MessageFormat.format("第{0} 行 {1}商品ID {2} 已经存在，请勿重复导入",new Object[]{(tbitem.getArrayIndex()+TaobaoImportConstants.CSV_INDEX),TaobaoImportUtils.subStr(item.getTitle()),item.getNumIid().toString()})));//
					}
				}else{
					saveTaobaoItemAndImage(merchantCode, item, importVo.getResultTotal());
				}
			 
			}  catch (YMCException e) {
				importVo.getResultTotal().put("failCount", importVo.getResultTotal().get("failCount")==null?1:importVo.getResultTotal().get("failCount")+1);
				importVo.getErrorList().add(new ErrorVo(tbitem.getArrayIndex(),"第 "+(tbitem.getArrayIndex()+TaobaoImportConstants.CSV_INDEX)+" 行"+": 解析失败"+e.getMessage()));
				log.error("[淘宝导入]商家编码:{}-导入淘宝商品{},{}行:解析失败", merchantCode, item.getTitle(),(tbitem.getArrayIndex()+TaobaoImportConstants.CSV_INDEX), e);
			}catch (Exception e) {
				importVo.getResultTotal().put("failCount", importVo.getResultTotal().get("failCount")==null?1:importVo.getResultTotal().get("failCount")+1);
				importVo.getErrorList().add(new ErrorVo(tbitem.getArrayIndex(),"第 "+(tbitem.getArrayIndex()+TaobaoImportConstants.CSV_INDEX)+" 行"+": 解析失败"));
				log.error("[淘宝导入]商家编码:{}-导入淘宝商品{},{}行:解析失败", merchantCode, item.getTitle(),(tbitem.getArrayIndex()+TaobaoImportConstants.CSV_INDEX), e);
			}
	}
	
	/**
	 * 导入淘宝商品数据(csv)
	 * @param sessionKey
	 * @param item
	 * @throws IllegalAccessException 
	 */
	public void importTaobaoItemData4CSV(String merchantCode,String operter,
			TaobaoImportVo importVo) throws IllegalAccessException{
		//为了保险起见，重新设置ThreadLocal
		setInitParamData(merchantCode, operter);
		
		Map<String,Integer> resultTotal = new HashMap<String,Integer>();
		resultTotal.put("itemTotal", 0);
		resultTotal.put("extendTotal", 0);
		resultTotal.put("existTotal", 0);
		
		int failCount = 0;
		Item item = null;
		for(TaobaoCsvItemVO tbitem : importVo.getVoList()) {
			try {
				item = tbitem.getItem();
				
				//1 重复判断判断，如果商品重复，其他操作一概不用
				TaobaoItem taobaoItem = taobaoItemMapper.getTaobaoItemByNumIid(item.getNumIid());
				if(taobaoItem != null){
					int existTotal = resultTotal.get("existTotal") + 1;
					resultTotal.put("existTotal",existTotal);
					if(importVo.getErrorList()!=null){
						importVo.getErrorList().add(new ErrorVo(tbitem.getArrayIndex(),MessageFormat.format("第{0} 行 {1}商品ID {2} 已经存在，请勿重复导入",new Object[]{(tbitem.getArrayIndex()+TaobaoImportConstants.CSV_INDEX),TaobaoImportUtils.subStr(item.getTitle()),item.getNumIid().toString()})));//
					}
				}else{
					//保存数据
					saveTaobaoItemAndImage(merchantCode, item, resultTotal);
				}
			 
			} catch (Exception e) {
				failCount++;
				importVo.getErrorList().add(new ErrorVo(tbitem.getArrayIndex(),"第 "+(tbitem.getArrayIndex()+TaobaoImportConstants.CSV_INDEX)+" 行"+":"+e.getMessage()));
				log.error("[淘宝导入]商家编码:{}-导入淘宝商品{},{}行:解析失败", merchantCode, item.getTitle(),(tbitem.getArrayIndex()+TaobaoImportConstants.CSV_INDEX), e);
			}
		}
		resultTotal.put("failCount", failCount);
		importVo.setResultTotal(resultTotal);
	}
	
	/**
	 * 保存导入csv数据到数据库，发送MQ消息，处理图片
	 * @param merchantCode
	 * @param item
	 * @param resultTotal
	 * @throws Exception
	 */
	public void saveTaobaoItemAndImage(String merchantCode, Item item, Map<String,Integer> resultTotal) throws Exception{
		//保存商品信息
		insertTaobaoItemTODB(item,merchantCode);
		
		long numIid = item.getNumIid();
		//成功总数+1
		resultTotal.put("itemTotal",resultTotal.get("itemTotal")+ 1);
		
		//保存商品属性图片信息
		importItemPropImg(numIid, item.getPropImgs());
		
		//保存SKU信息
		Map<String, Set<String>> mapItemSkuData = importItemSku(numIid,
				item.getCid(), item.getSkus(), true, merchantCode);
		
		//保存商品扩展信息
		List<String> extendIds = importItemExtendData(numIid, item.getCid(), mapItemSkuData, item, merchantCode);
		if(CollectionUtils.isNotEmpty(extendIds)){
			resultTotal.put("extendTotal",resultTotal.get("extendTotal") + extendIds.size());
		}
		 
		//导入商品图片
		String imgStr = importItemImgTODB(numIid, item.getItemImgs(), extendIds);
		
		ImageJmsVo message = new ImageJmsVo();
		message.setImageId(imgStr); 
		//处理图片功能抽离
		if(StringUtils.isNotEmpty(imgStr)){
			UploadImage image = new UploadImage(numIid, item.getDesc(), message, merchantCode);
			Thread thread = new Thread(image);  
			thread.start();
		}
	}
	/**
	 * 插入tbitem表
	 * @param taobaoitem
	 * @throws IllegalAccessException
	 */
	@Transactional
	private void insertTaobaoItemTODB(Item item,String merchantCode) throws IllegalAccessException{
		TaobaoItem taobaoItem = new TaobaoItem();
		taobaoItem.setApproveStatus(item.getApproveStatus());
		taobaoItem.setCid(item.getCid());
		taobaoItem.setCreated(item.getCreated());
		taobaoItem.setDelistTime(item.getDelistTime());
		taobaoItem.setDescription(item.getDesc());
		taobaoItem.setDetailUrl(item.getDetailUrl());
		taobaoItem.setInputPids(item.getInputPids());
		taobaoItem.setInputStr(item.getInputStr());
		taobaoItem.setIsFenxiao(item.getIsFenxiao());
		taobaoItem.setIsTaobao(item.getIsTaobao());
		taobaoItem.setIsTaobao(item.getIsTiming());
		taobaoItem.setIsXinpin(item.getIsXinpin());
		taobaoItem.setItemSize(item.getItemSize());
		taobaoItem.setItemWeight(item.getItemWeight());
		taobaoItem.setListTime(item.getListTime());
		taobaoItem.setModified(item.getModified());
		taobaoItem.setNick(item.getNick());
		taobaoItem.setNumIid(item.getNumIid());
		taobaoItem.setOuterId(item.getOuterId());
		taobaoItem.setPicUrl(item.getPicUrl());
		taobaoItem.setPrice(item.getPrice());
		taobaoItem.setProps(item.getProps());
		taobaoItem.setPropsName(item.getPropsName());
		taobaoItem.setSellPoint(item.getSellPoint());
		taobaoItem.setTitle(item.getTitle());
		taobaoItem.setViolation(item.getViolation());
		taobaoItem.setNickId(YmcThreadLocalHolder.getNickId());
		taobaoItem.setMerchantCode( merchantCode);
		taobaoItem.setOperater(YmcThreadLocalHolder.getOperater());
		taobaoItem.setOperated(DateUtil.getFormatByDate(new Date()));
		DateTime dateTime = new DateTime();
		taobaoItem.setYougouYears(String.valueOf(dateTime.getYear()));
		taobaoItem.setYougouStyleNo(item.getOuterId());
		 
		taobaoItemMapper.insertTaobaoItem(taobaoItem);
	}

		
		
	
	
	/**
	 * 导入商品具体数据（包括商品图片，属性图片，SKU）
	 * @param item
	 * @return
	 */
	@Transactional
	private boolean importTaobaoItem(TaobaoCsvItemVO taobaoitem, ImageJmsVo message,List<String> errorList,Map<String,
			Integer> resultTotal,String merchantCode) throws IllegalAccessException{
		Item item = taobaoitem.getItem();
		if(item != null) {
			//查询是否在淘宝上表中已经存在
			TaobaoItem taobaoItem = taobaoItemMapper.getTaobaoItemByNumIid(item.getNumIid());
			if(taobaoItem == null) {
				taobaoItem = new TaobaoItem();
				taobaoItem.setApproveStatus(item.getApproveStatus());
				taobaoItem.setCid(item.getCid());
				taobaoItem.setCreated(item.getCreated());
				taobaoItem.setDelistTime(item.getDelistTime());
				taobaoItem.setDescription(item.getDesc());
				taobaoItem.setDetailUrl(item.getDetailUrl());
				taobaoItem.setInputPids(item.getInputPids());
				taobaoItem.setInputStr(item.getInputStr());
				taobaoItem.setIsFenxiao(item.getIsFenxiao());
				taobaoItem.setIsTaobao(item.getIsTaobao());
				taobaoItem.setIsTaobao(item.getIsTiming());
				taobaoItem.setIsXinpin(item.getIsXinpin());
				taobaoItem.setItemSize(item.getItemSize());
				taobaoItem.setItemWeight(item.getItemWeight());
				taobaoItem.setListTime(item.getListTime());
				taobaoItem.setModified(item.getModified());
				taobaoItem.setNick(item.getNick());
				taobaoItem.setNumIid(item.getNumIid());
				taobaoItem.setOuterId(item.getOuterId());
				taobaoItem.setPicUrl(item.getPicUrl());
				taobaoItem.setPrice(item.getPrice());
				taobaoItem.setProps(item.getProps());
				taobaoItem.setPropsName(item.getPropsName());
				taobaoItem.setSellPoint(item.getSellPoint());
				taobaoItem.setTitle(item.getTitle());
				taobaoItem.setViolation(item.getViolation());
				
				Long nickId = YmcThreadLocalHolder.getNickId();//Add by LQ on 20150602
				
				taobaoItem.setNickId(nickId);
				taobaoItem.setMerchantCode(merchantCode);
				taobaoItem.setOperater(YmcThreadLocalHolder.getOperater());
				taobaoItem.setOperated(DateUtil.getFormatByDate(new Date()));
				DateTime dateTime = new DateTime();
				taobaoItem.setYougouYears(String.valueOf(dateTime.getYear()));
				taobaoItem.setYougouStyleNo(item.getOuterId());
				//处理淘宝商品描述图->转换成优购的
				message.setProDesc(item.getDesc());
				taobaoItemMapper.insertTaobaoItem(taobaoItem);
				return true;
			}else{
				int existTotal = resultTotal.get("existTotal") + 1;
				resultTotal.put("existTotal",existTotal);
				if(errorList!=null){
					errorList.add(MessageFormat.format("第{0} 行 {1}商品ID {2} 已经存在，请勿重复导入",new Object[]{(taobaoitem.getArrayIndex()+TaobaoImportConstants.CSV_INDEX),TaobaoImportUtils.subStr(item.getTitle()),item.getNumIid().toString()}));//
				}
				return false;
			}
		}
		return false;
	}

	
	/**
	 * 导入商品图片
	 * @param numIid
	 * @param lstItemImg
	 * @return
	 */
	@Transactional
	private boolean importItemImg(long numIid, List<ItemImg> lstItemImg, List<String> extendIds, ImageJmsVo message) {
		if(lstItemImg != null && !lstItemImg.isEmpty()) {
			List<TaobaoItemImg> lstTaobaoItemImg = new ArrayList<TaobaoItemImg>(lstItemImg.size());
			StringBuilder strBuileder=new StringBuilder();
			int index=1;
			TaobaoItemImg taobaoItemImg = null;
			for(ItemImg itemImg : lstItemImg) {
				String imgUrl = itemImg.getUrl();
				if(extendIds != null && extendIds.size()>0) {
					for(String extendId : extendIds) {
						if(extendId != null && !"".equals(extendId)) {
							taobaoItemImg = new TaobaoItemImg();
							taobaoItemImg.setImgId(UUIDGenerator.getUUID());
							taobaoItemImg.setNumIid(numIid);
							taobaoItemImg.setExtendId(extendId);
							taobaoItemImg.setId(itemImg.getId());
							taobaoItemImg.setPosition(itemImg.getPosition());
							taobaoItemImg.setUrl(imgUrl);
							lstTaobaoItemImg.add(taobaoItemImg);
						}
					}
					//将外部平台的图片下载校验转成优购的
					if(index != 1){
						strBuileder.append(",");
					}
					strBuileder.append(imgUrl);
				} 
				index++;
			}
			message.setImageId(strBuileder.toString());
			if(lstTaobaoItemImg != null && !lstTaobaoItemImg.isEmpty()) {
				for(String extendId : extendIds){
					taobaoItemImgMapper.deleteTaobaoItemImgByExtendId(extendId);
				}
				taobaoItemImgMapper.insertTaobaoItemImgList(lstTaobaoItemImg);
			}
		}
		return true;
	}
	
	/**
	 * 导入商品属性图片
	 * @param numIid
	 * @param lstPropImg
	 * @return
	 */
	@Transactional
	private void importItemPropImg(long numIid, List<PropImg> lstPropImg) {
		if(CollectionUtils.isNotEmpty(lstPropImg)) {
			TaobaoItemPropImg taobaoItemPropImg = null;
			List<TaobaoItemPropImg> lstTaobaoItemPropImg = new ArrayList<TaobaoItemPropImg>(lstPropImg.size());
			for(PropImg propImg : lstPropImg) {
				taobaoItemPropImg = new TaobaoItemPropImg();
				taobaoItemPropImg.setPropId(UUIDGenerator.getUUID());
				taobaoItemPropImg.setNumIid(numIid);
				taobaoItemPropImg.setId(propImg.getId());
				taobaoItemPropImg.setPosition(propImg.getPosition());
				taobaoItemPropImg.setUrl(propImg.getUrl());
				taobaoItemPropImg.setProperties(propImg.getProperties());
				lstTaobaoItemPropImg.add(taobaoItemPropImg);
			}
			if(CollectionUtils.isNotEmpty(lstTaobaoItemPropImg)) {
				taobaoItemPropImgMapper.deleteTaobaoItemPropImgByNumIid(numIid);
				taobaoItemPropImgMapper.insertTaobaoItemPropImgList(lstTaobaoItemPropImg);
			}
		}
	}
	
	/**
	 * 导入商品SKU
	 * @param numIid
	 * @param lstSku
	 * @return
	 */
	private Map<String, Set<String>> importItemSku(long numIid, long cid, List<Sku> lstSku,boolean isImportTaobaoItem,String merchantCode) {
		Map<String, Set<String>> mapItemSkuData = null;
		if(CollectionUtils.isNotEmpty(lstSku)) {
			TaobaoItemSku taobaoItemSku = null;
			List<TaobaoItemSku> lstTaobaoSku = new ArrayList<TaobaoItemSku>(lstSku.size());
			mapItemSkuData = new HashMap<String, Set<String>>();
			String sizePid = taobaoItemPropMapper.getTaobaoPidYougouSizeMapperingByCid(merchantCode, cid);
			for(Sku sku : lstSku) {
				taobaoItemSku = new TaobaoItemSku();
				taobaoItemSku.setBarcode("");
				taobaoItemSku.setNumIid(numIid);
				taobaoItemSku.setOuterId(sku.getOuterId());
				taobaoItemSku.setProperties(sku.getProperties());
				taobaoItemSku.setPropertiesName(sku.getPropertiesName());
				taobaoItemSku.setQuantity(sku.getQuantity());
				taobaoItemSku.setSkuId(sku.getSkuId());
				lstTaobaoSku.add(taobaoItemSku);
				setItemSkuProp(mapItemSkuData, sku.getProperties(), sku.getPropertiesName(), sku.getOuterId(),sizePid);
			}
			//新导入的商品才插入SKU信息
			if(CollectionUtils.isNotEmpty(lstTaobaoSku) && isImportTaobaoItem) {
				try {
					addSku(lstTaobaoSku, numIid);
				} catch (Exception e) {
					log.error("[淘宝导入]商家编码:{}商品编码{}-插入SKU信息异常:", merchantCode, numIid, e);
				}
			}
		}
		return mapItemSkuData;
	}

	/**
	 * 
	 * @param list
	 * @param numIid
	 */
	public synchronized void addSku(List<TaobaoItemSku> list, long numIid){
		taobaoItemSkuMapper.deleteTaobaoItemSkuByNumIid(numIid);
		taobaoItemSkuMapper.insertTaobaoItemSkuList(list);
	}
	
	/**
	 * 组装SKU中的颜色和对应的尺码，按照优购的商品规则（精确到款色）做对应的商品扩展
	 * @param mapItemSkuProp
	 * @param properties
	 * @param propertiesName
	 * @param barcode
	 */
	private void setItemSkuProp(Map<String, Set<String>> mapItemSkuProp, String properties, String propertiesName, String barcode,String sizePid){
		String[] arrProp = properties.split(";");
		String[] arrPropName = propertiesName.split(";");
		if(arrProp != null && arrPropName != null) {
			int indexsize=0;
			if(StringUtils.isNotBlank(sizePid)){
				indexsize=properties.indexOf(sizePid);
			}else{
				sizePid="20549";
				indexsize=properties.indexOf(sizePid);
				if(indexsize==-1){
					sizePid="21433";
					indexsize=properties.indexOf(sizePid);
				}
			}
			if(getColorPidIndex(properties) < indexsize){//1627207
				for(int i = 0; i < arrProp.length;i++){
					setItemSkuPropDetail(mapItemSkuProp,arrProp[i],arrPropName[i],barcode,sizePid);
				}
			}else{
				for(int i = arrProp.length-1; i >=0;i--){
					setItemSkuPropDetail(mapItemSkuProp,arrProp[i],arrPropName[i],barcode,sizePid);
				}
			}
		}
	}
	
	private void setItemSkuPropDetail(Map<String, Set<String>> mapItemSkuProp,String prop,String arrPropName,String barcode,String sizePid){
		if(prop != null && arrPropName != null) {
			String propName = arrPropName.substring(prop.length()+1, arrPropName.length());
			String[] arrSubName = propName.split(":");
			if(arrSubName != null && arrSubName.length > 1) {
				//判断如果起始字符和颜色分类pid匹配并且在map容器中不存在，则把颜色作为key写入到map容器中
				//如果已经容器中已经包含了对应的颜色，则把颜色写入到颜色变量
				// 1626984 1627207 1627843 1627975 6986510 122276380  代表颜色的pid
				if(hasStartWidthColorPid(prop) && !mapItemSkuProp.containsKey(arrSubName[1])) {
					if(arrSubName[1] != null) {
						YmcThreadLocalHolder.setColorName( arrSubName[1] );//Amend by LQ.
						mapItemSkuProp.put(arrSubName[1], null);
					}
				} else if(mapItemSkuProp != null && mapItemSkuProp.containsKey(arrSubName[1])) {
					YmcThreadLocalHolder.setColorName( arrSubName[1] );//Amend by LQ. 
				}
				if(prop.startsWith(sizePid)) {
					//Add by LQ.
					String colorName = YmcThreadLocalHolder.getColorName();
					
					Set<String> setItemSkuSize = mapItemSkuProp.get(colorName);
					if(setItemSkuSize == null) {
						setItemSkuSize = new HashSet<String>();
						mapItemSkuProp.put(colorName, setItemSkuSize);
					}
					setItemSkuSize.add(prop+":"+barcode+":"+arrSubName[1]);
				}
			}
		}
	}
	
	/**
	 * 	插入item扩展表(得到颜色对应的extends_id)
	 *	根据绑定尺码表插入尺码属性扩展表
	 *	按照颜色增加商品图片对应的图片数据
	 * @param numIid
	 * @param mapItemSkuProp
	 * @return
	 */
	private List<String> importItemExtendData(long numIid, long cid, Map<String, Set<String>> mapItemSkuProp,Item item,String merchantCode) throws IllegalAccessException{
		List<String> extendIds = new ArrayList<String>();
		if(mapItemSkuProp != null && !mapItemSkuProp.isEmpty()) {
			Iterator<String> iteItemColor = mapItemSkuProp.keySet().iterator();
			int index=1;
			
			Map<String,Object> params01 = new HashMap<String,Object>();
			params01.put("numIid", numIid);
			int count = taobaoItemExtendMapper.selectTaobaoItemExtendCount(params01);
			String checkStatus = "2";
			if(count > 0){//不是第一次插入
				checkStatus = "0";
			}
			while (iteItemColor.hasNext()) {
				String strItemColor = iteItemColor.next();
				Set<String> setItemSku = mapItemSkuProp.get(strItemColor);
				
				//根据numIid  颜色查询扩展信息是否存在
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("numIid", numIid);
				params.put("yougouSpecName", strItemColor);
				RowBounds rowBounds = new RowBounds(0,10);
				List<TaobaoItemExtendDto> itemextends = taobaoItemExtendMapper.selectTaobaoItemExtend(params,rowBounds);
				String extendId = null;
				if(CollectionUtils.isEmpty(itemextends)){
					String styleNo=item.getOuterId();
					String yougouSupplierCode = styleNo + "-" + Math.round(Math.random() * 899 + 100) + (index++);
					extendId = insertItemExtends(numIid, strItemColor,yougouSupplierCode,item.getTitle(),checkStatus, merchantCode); 
					if(null != extendId){
						extendIds.add(extendId);
						insertItemExtendsSizeProp(numIid, extendId, cid, setItemSku, merchantCode);
					}
				}else{//更新状态
					TaobaoItemExtendDto dto = itemextends.get(0);
					if( IsImportYougou.STATUS_DELETED.getStatus().equals(dto.getIsImportYougou())){//删除状态
						dto.setIsImportYougou(IsImportYougou.STATUS_NOT_IMPORTED.getStatus());// Amend  by LQ.
						taobaoItemExtendMapper.updateByPrimaryKeySelective(dto);
					}
				}
				
			}
		}
		return extendIds;
	}
	
	/**
	 * 插入淘宝商品扩展表（按优购商品细分款色）
	 * @param numIid
	 * @param itemColor
	 * @return
	 */
	@Transactional
	private String insertItemExtends(long numIid, String itemColor,String yougouSupplierCode,String title,String checkStatus,String merchantCode) {
		
		
		String extendId = UUIDGenerator.getUUID();
		TaobaoItemExtend taobaoItemExtend = new TaobaoItemExtend();
		taobaoItemExtend.setExtendId(extendId);
		taobaoItemExtend.setTitle(title);
		taobaoItemExtend.setNumIid(numIid);
		taobaoItemExtend.setMerchantCode(merchantCode);
		taobaoItemExtend.setYougouSpecName(itemColor);
		taobaoItemExtend.setYougouPrice(0D);
		taobaoItemExtend.setYougouSupplierCode(yougouSupplierCode);
		taobaoItemExtend.setCheckStatus(checkStatus);
		taobaoItemExtend.setOperater(YmcThreadLocalHolder.getOperater());
		
		if("0".equals(checkStatus)){//不是第一次插入
			TaobaoItem taobaoItem = taobaoItemMapper.getTaobaoItemByNumIid(numIid);
			taobaoItemExtend.setYougouDescription(taobaoItem.getDescription());
			taobaoItemExtend.setCheckStatus("0");
			//插入角度图
			String anglePics = taobaoItem.getAnglePic();
			if(StringUtils.isNotEmpty(anglePics)){
				String[] anglePicArray = anglePics.split("图");
				TaobaoItemImg img = null;
				String yougouUrl = "";
				List<TaobaoItemImg> lstTaobaoItemImg = new ArrayList<TaobaoItemImg>();
				String thumbnail = "";
				for(String anglePic:anglePicArray){
					String[] orderPicArry = anglePic.split("序");
					img = new TaobaoItemImg();
					img.setImgId(UUIDGenerator.getUUID());
					img.setExtendId(extendId);
					img.setNumIid(numIid);
					img.setPosition(Long.valueOf(orderPicArry[1]));
					yougouUrl = orderPicArry[0];
					img.setYougouUrl(yougouUrl);
					img.setId(1L);
					img.setUrl("");
					thumbnail = yougouUrl.substring(0,yougouUrl.lastIndexOf("."))+".png";
					img.setYougouUrlThumbnail(thumbnail);
					if("0".equals(orderPicArry[1])){
						taobaoItemExtend.setDefaultPic(thumbnail);
					}
					lstTaobaoItemImg.add(img);
				}
				if(!lstTaobaoItemImg.isEmpty()){
					taobaoItemImgMapper.insertTaobaoItemImgList(lstTaobaoItemImg);
				}
			}
		// hgx 2015-05-4 淘宝导入彻底 删除一个款下面的一个款式之后，重新导入该文件，提示成功为0。因为该地方返回空，导致后面没列入统计。暂不确定该地方的影响范围
		}
		taobaoItemExtend.setOperated(DateUtil.getFormatByDate(new Date()));
		taobaoItemExtendMapper.insertTaobaoItemExtend(taobaoItemExtend);
		return extendId;
	}
	
	/**
	 * 插入商品尺码属性数据
	 * @param numIid
	 * @param extendId
	 * @param cid
	 * @param setItemSku
	 * @return
	 */
	@Transactional
	private void insertItemExtendsSizeProp(long numIid, String extendId, long cid, Set<String> setItemSku,String  merchantCode) {
		List<TaobaoItemExtendYougouPropValueSize> lstExtendPropValueSize = null;
		if (CollectionUtils.isNotEmpty(setItemSku)) {
			lstExtendPropValueSize = new ArrayList<TaobaoItemExtendYougouPropValueSize>(setItemSku.size());
			long pid = 0;//用于查询是否有对应的尺码绑定
			Map<String, TaobaoItemPropValVO> mapTaobaoItemPropValue = new HashMap<String, TaobaoItemPropValVO>();
			Map<String, com.yougou.pc.model.prop.PropValue> mapSizeData = null;
			TaobaoItemExtendYougouPropValueSize extendPropValueSize = null;
			TaobaoYougouItemCat taobaoYougouItemCat ;
			for (Iterator<String> itItemSku = setItemSku.iterator(); itItemSku.hasNext();) {
				String temp = itItemSku.next();
				String[] arrSkuData = temp.split(":");
				TaobaoItemPropValVO taobaoItemPropValVO = null;
				//查询商家是否根据昵称、商家编码、淘宝cid、pid、vid有对应的尺码绑定
				if(arrSkuData != null) {
					int length = arrSkuData.length;
					if(pid == 0) {
						if(length > 1) {
							
							Long nickId = YmcThreadLocalHolder.getNickId();//Add by LQ on 20150602
							
							pid = Long.valueOf(arrSkuData[0]);
							List<TaobaoItemPropValVO> lstTaobaoItemPropValue = taobaoItemPropValueMapper.getTaobaoYougouItemPropValByCidPid(merchantCode, nickId, cid, pid);
							if(CollectionUtils.isNotEmpty(lstTaobaoItemPropValue)) {
								for(TaobaoItemPropValVO tempTaobaoItemPropValVO : lstTaobaoItemPropValue) {
									if(tempTaobaoItemPropValVO != null) {
										mapTaobaoItemPropValue.put(tempTaobaoItemPropValVO.getPropval(), tempTaobaoItemPropValVO);
									}
								}
							} 
							
							//根据淘宝cid查询和优购中间绑定分类获取yougouCatNo;
							taobaoYougouItemCat =taobaoItemCatMapper.getTaobaoYougouItemCatByCid(merchantCode, nickId, cid);
							if(taobaoYougouItemCat != null && taobaoYougouItemCat.getYougouCatNo() != null) {
								Category category = commodityBaseApiService.getCategoryByNo(taobaoYougouItemCat.getYougouCatNo());
								if(category != null) {
									List<com.yougou.pc.model.prop.PropValue> lstSizeProp = commodityMerchantApi.getSizeByCatId(category.getId());
									if(CollectionUtils.isNotEmpty(lstSizeProp)) {
										mapSizeData = new HashMap<String, com.yougou.pc.model.prop.PropValue>(lstSizeProp.size());
										for(com.yougou.pc.model.prop.PropValue propValue : lstSizeProp) {
											mapSizeData.put(propValue.getPropValueName(), propValue);
										}
									}
								}
							}
						}
					}
					String skuSize = null;
					if(length > 1) {
						taobaoItemPropValVO = mapTaobaoItemPropValue.get(arrSkuData[1]);
						if(taobaoItemPropValVO != null) {
							skuSize = length > 3 ? arrSkuData[3] : "";
							extendPropValueSize = new TaobaoItemExtendYougouPropValueSize();
							extendPropValueSize.setId(UUIDGenerator.getUUID());
							extendPropValueSize.setNumIid(numIid);
							extendPropValueSize.setBarcode(length > 2 ? arrSkuData[2] : "");
							extendPropValueSize.setMerchantCode(merchantCode);
							extendPropValueSize.setExtendId(extendId);
							extendPropValueSize.setYougouPropValueNo(taobaoItemPropValVO.getYougouPropValueNo());
							extendPropValueSize.setYougouPropValueName(skuSize);
							lstExtendPropValueSize.add(extendPropValueSize);
						}else{
							if(length > 3) {
								skuSize = arrSkuData[3];
								//针对淘宝靴子分类特除处理
								if(cid==TaobaoImportConstants.CID_SP){
									skuSize=skuSize.toLowerCase();
									skuSize=skuSize.replaceAll("eur", "");
								}
								if(mapSizeData!=null&&mapSizeData.containsKey(skuSize)) {
									com.yougou.pc.model.prop.PropValue propValue = mapSizeData.get(skuSize);
									extendPropValueSize = new TaobaoItemExtendYougouPropValueSize();
									extendPropValueSize.setId(UUIDGenerator.getUUID());
									extendPropValueSize.setNumIid(numIid);
									extendPropValueSize.setBarcode(arrSkuData[2]);
									extendPropValueSize.setMerchantCode(merchantCode);
									extendPropValueSize.setExtendId(extendId);
									extendPropValueSize.setYougouPropValueNo(propValue.getPropValueNo());
									extendPropValueSize.setYougouPropValueName(skuSize);
									lstExtendPropValueSize.add(extendPropValueSize);
								}
							}
						}
					}	
				}
			}
			//插入商品尺码扩展属性
			if(CollectionUtils.isNotEmpty(lstExtendPropValueSize)) {
				taobaoItemExtendPropMapper.insertTaobaoItemYougouPropValueSizeBatch(lstExtendPropValueSize);
			}
		}
	}
	
	/**
	 * 设置初始调用淘宝接口参数数据
	 * @param sessionKey
	 * @param merchantCode
	 * @param operater
	 * @param nickId
	 * @throws IllegalAccessException
	 */
	private void setInitParamData(String sessionKey, String merchantCode, String operater, Long nickId) throws IllegalAccessException {
		log.info("[淘宝导入]商家编码:{}-设置初始调用淘宝接口参数数据.sessionKey:{},operater:{},nickId:{}", merchantCode, sessionKey, operater, nickId);
		if(StringUtils.isBlank(sessionKey)) {
			log.error("[淘宝导入]商家编码:{}-sessionKey为空", merchantCode);
			throw new IllegalAccessException("sessionKey == null");
		}
		if(StringUtils.isBlank(merchantCode)) {
			log.error("[淘宝导入]商家编码:{}-merchantCode为空", merchantCode);
			throw new IllegalAccessException("merchantCode == null");
		}
		
		YmcThreadLocalHolder.setSessionKey(sessionKey);
		
		if(nickId != null) {
			YmcThreadLocalHolder.setNickId(nickId);
		}
		
		//为了保险起见，重新设置ThreadLocal
		if( StringUtils.isBlank( YmcThreadLocalHolder.getMerchantCode() ) ){
			YmcThreadLocalHolder.setMerchantCode(merchantCode);
		}
		if( StringUtils.isNotBlank(operater) && ( StringUtils.isBlank( YmcThreadLocalHolder.getOperater() ) 
				|| "system".equalsIgnoreCase(YmcThreadLocalHolder.getOperater()) ) ){
			YmcThreadLocalHolder.setOperater( operater );
		}
	}
	
	/**
	 * 为了保险起见，重新设置ThreadLocal
	 * @param merchantCode
	 * @param operater
	 * @throws IllegalAccessException
	 */
	private void setInitParamData(String merchantCode,String operater) throws IllegalAccessException {
		log.info("[淘宝导入]商家编码:{}-设置初始调用淘宝接口参数数据.operater:{}", merchantCode, operater);
		if(merchantCode == null || (merchantCode = merchantCode.trim()).length() == 0) {
			log.error("[淘宝导入]商家编码:{}-merchantCode为空", merchantCode);
			throw new IllegalAccessException("merchantCode == null");
		} 
		
		//为了保险起见，重新设置ThreadLocal
		if( StringUtils.isBlank( YmcThreadLocalHolder.getMerchantCode() ) ){
			YmcThreadLocalHolder.setMerchantCode(merchantCode);
		}
		if( StringUtils.isNotBlank(operater) && ( StringUtils.isBlank( YmcThreadLocalHolder.getOperater() ) 
				|| "system".equalsIgnoreCase(YmcThreadLocalHolder.getOperater()) ) ){
			YmcThreadLocalHolder.setOperater( operater );
		}
		
	}
	
	public void putImpCount(ImpDataCount impDataCount, Integer count){
		if(count != null && count > 0) {
			Integer sum = mapImpSumCatProp.get(impDataCount);
			if(sum != null) {
				sum += count;
			} else {
				sum = count;
			}
			mapImpSumCatProp.put(impDataCount, sum);
		}
	}
	
	public String getImpDataSumStr() {
		StringBuffer result = new StringBuffer();
		Integer iCount = 0;
		iCount = mapImpSumCatProp.get(ImpDataCount.ENDCAT);
		if(iCount != null && iCount > 0) {
			result.append("增加淘宝末级分类有:");
			result.append(iCount);
			result.append("个\n");
		}
		iCount = mapImpSumCatProp.get(ImpDataCount.PROP);
		if(iCount != null && iCount > 0) {
			result.append("增加淘宝属性有:");
			result.append(iCount);
			result.append("个\n");
		}
		iCount = mapImpSumCatProp.get(ImpDataCount.PROPVAL);
		if(iCount != null && iCount > 0) {
			result.append("增加淘宝属性值有:");
			result.append(iCount);
			result.append("个\n");
		}
		String s = result.toString();
		if("".equals(s)) {
			return "没有您所要抓取的数据！";
		}
		return s;
		
	}
	
	private enum ImpDataCount {
		ENDCAT, PROP, PROPVAL;
	}
	 
	/**
	 * 复制模板数据到
	 * @param merchantCode
	 * @param nickId
	 * @param taobaoYougouBrandMapper
	 */
	@Transactional
	public synchronized void addTaobaoYougou(String merchantCode,long nickId,
			TaobaoYougouBrand taobaoYougouBrandIn,long cid ,TaobaoYougouItemCat taobaoYougouItemCatIn
			){
		TaobaoYougouBrand taobaoYougouBrand=taobaoBrandMapper.getTaobaoYougouBrandByBid(merchantCode,nickId,taobaoYougouBrandIn.getTaobaoBid());
		if(taobaoYougouBrand==null){
			taobaoYougouBrand=new TaobaoYougouBrand();
			taobaoYougouBrand.setId(UUIDGenerator.getUUID());
			taobaoYougouBrand.setMerchantCode(merchantCode);
			taobaoYougouBrand.setNickId(nickId);
			taobaoYougouBrand.setTaobaoBid(taobaoYougouBrandIn.getTaobaoBid());
			taobaoYougouBrand.setYougouBrandNo(taobaoYougouBrandIn.getYougouBrandNo());
			taobaoYougouBrand.setYougouBrandName(taobaoYougouBrandIn.getYougouBrandName());
			taobaoYougouBrand.setOperater(merchantCode);
			taobaoYougouBrand.setOperated(DateUtil.getFormatByDate(new Date()));
			taobaoBrandMapper.insertTaobaoYougouBrand(taobaoYougouBrand);
		}
		
		//第三步，淘宝分类对应模板复制到tbl_merchant_taobao_yougou_itemcat
		if(taobaoItemCatMapper.getTaobaoYougouItemCatByCid(merchantCode,nickId,cid)==null){
			TaobaoYougouItemCat taobaoYougouItemCat=new TaobaoYougouItemCat();
			taobaoYougouItemCat.setId(UUIDGenerator.getUUID());
			taobaoYougouItemCat.setMerchantCode(merchantCode);
			taobaoYougouItemCat.setNickId(nickId);
			taobaoYougouItemCat.setOperated(DateUtil.getFormatByDate(new Date()));
			taobaoYougouItemCat.setOperater(merchantCode);
			taobaoYougouItemCat.setTaobaoCid(taobaoYougouItemCatIn.getTaobaoCid());
			taobaoYougouItemCat.setTaobaoCatFullName(taobaoYougouItemCatIn.getTaobaoCatFullName());
			taobaoYougouItemCat.setYougouCatNo(taobaoYougouItemCatIn.getYougouCatNo());
			taobaoYougouItemCat.setYougouCatFullName(taobaoYougouItemCatIn.getYougouCatFullName());
			taobaoYougouItemCat.setYougouCatStruct(taobaoYougouItemCatIn.getYougouCatStruct());
			taobaoItemCatMapper.insertTaobaoYougouItemCat(taobaoYougouItemCat);
		}
		//处理属性
		//校验和新增数据不一致，暂时注释掉换成新的校验方法,先不删除
		Map<String, Object> params = new HashMap<String, Object>();    
		params.put("merchantCode", merchantCode);
		params.put("nickId", nickId);
		params.put("taobaoCid", cid);
		List<TaobaoYougouItemProp> proList = taobaoYougouItemPropMapper.selectTaobaoYougouItemPropList(params);
		if(CollectionUtils.isEmpty(proList)){
			taobaoYougouItemCatTempletMapper.copyItemPropTemplet2YougouItemPropByCid(merchantCode, nickId, merchantCode, DateUtil.getFormatByDate(new Date()), cid);
		}
		//处理属性值
		int count=taobaoYougouItemCatTempletMapper.selectTaobaoYougouItemPropValueByCid(merchantCode, nickId, cid);
		if(count==0){
			taobaoYougouItemCatTempletMapper.copyItemPropValueTemplet2YougouItemPropValueByCid(merchantCode, nickId, merchantCode, DateUtil.getFormatByDate(new Date()), cid);
		}
	}
	
	/**
	 * 根据模板初始化商品
	 */
	public Map<String, String> initDataFromTemplate(String numiidAndExtendIdstr,String merchantCode)throws BusinessException{
		YmcThreadLocalHolder.setMerchantCode(merchantCode);
		log.info("[淘宝导入]商家编码:{}-对淘宝商品{}用模板进行了初始化", merchantCode, numiidAndExtendIdstr);
		//查询所有淘宝商品
		if(StringUtils.isEmpty(numiidAndExtendIdstr)){
			log.error("[淘宝导入]商家编码:{}-传入参数错误，淘宝商品ID为空:", merchantCode);
			throw new BusinessException("传入参数错误，淘宝商品ID为空");
		}
		String[] numiidAndExtendIdstrArray = numiidAndExtendIdstr.split(",");
		
		Map<String,List<String>> numiidAndExtendIdMap = new HashMap<String,List<String>>();
		
		for(String numiidAndExtendId : numiidAndExtendIdstrArray){
			String[] numiidAndExtendIds = numiidAndExtendId.split("\\|");
			String numiid = numiidAndExtendIds[0];
			String extendId = numiidAndExtendIds[1];
			if(numiidAndExtendIdMap.get(numiid) == null){
				List<String> extendIds = new ArrayList<String>();
				extendIds.add(extendId);
				numiidAndExtendIdMap.put(numiid, extendIds);
			}else{
				numiidAndExtendIdMap.get(numiid).add(extendId);
			}
		}
		
		int count = 0;		
		int total = numiidAndExtendIdstrArray.length;
		String msg = "";
		for(String numiid : numiidAndExtendIdMap.keySet()){
			TaobaoItem item = taobaoItemMapper.getTaobaoItemByNumIid(Long.valueOf(numiid));
			try{
				initData(item, numiidAndExtendIdMap.get(numiid), merchantCode);
			}catch(BusinessException e){
				msg += "商品 "+ item.getTitle() +" 初始化失败,原因："+e.getMessage()+"\n";
				log.error("[淘宝导入]商家编码:{}商品{}-初始化失败:", merchantCode,  item.getTitle(), e);
				continue;
			}
			count += numiidAndExtendIdMap.get(numiid).size();
		}	
		Map<String, String> result = new HashMap<String, String>();
		result.put("successCount", count+"");
		result.put("failureCount", (total-count)+"");
		result.put("msg", msg);
		return result;
	}
	
	/**
	 * 根据模板初始化商品
	 * @param item
	 * @param exntendIds
	 * @param merchantCode
	 * @throws BusinessException
	 */
	@Transactional
	private void initData(TaobaoItem item, List<String>exntendIds, String merchantCode) throws BusinessException{
		//获取品牌
		String propsStr = item.getProps(); 
		String band_v_Str = StringUtils.substringBetween(propsStr, TaobaoImportConstants.P_ID_SEARCH+":", ";");
		if(StringUtils.isBlank(band_v_Str)){
			band_v_Str = StringUtils.substringAfter(propsStr, TaobaoImportConstants.P_ID_SEARCH+":");
		}
		Long cid = item.getCid();
		//处理品牌
		//第一步，查询淘宝品牌在数据库存不存在
		Long brand_pid = TaobaoImportConstants.BRAND_P_ID;
		Long brand_vid = Long.valueOf(band_v_Str);
		TaobaoBrand taobaoBrand = taobaoBrandMapper.getTaobaoBrandByPidVid(brand_pid,brand_vid);
		if(taobaoBrand == null){
			log.error("[淘宝导入]商家编码:{}-淘宝商品数据中品牌在数据库中不存在.pid:{},vid:{}", merchantCode, brand_pid, brand_vid);
			throw new BusinessException("淘宝商品数据中品牌在数据库中不存在,pid:"+brand_pid+"  vid:"+brand_vid);
		}
		//第二步，查询淘宝品牌有没有和优购品牌做对应关系
		TaobaoYougouBrand taobaoYougouBrandMapper=taobaoBrandMapper.getBrandMapperByBid(taobaoBrand.getBid());
		if(taobaoYougouBrandMapper == null){
			log.error("[淘宝导入]商家编码:{}-淘宝商品数据中品牌没有设置与yougou对应关系.pid:{},vid:{}", merchantCode, brand_pid, brand_vid);
			throw new BusinessException("淘宝商品数据中品牌没有设置与yougou对应关系,pid:"+brand_pid+"  vid:"+brand_vid);
		}
		//第三步，查询该供应商是否有对应品牌的授权
		List<com.yougou.pc.model.brand.Brand> brandList = commodityMerchantApi.getBrandByMerchantCode(merchantCode);
		boolean hasBrand = false;
		for(com.yougou.pc.model.brand.Brand brand:brandList){
			if(brand.getBrandNo().equalsIgnoreCase(taobaoYougouBrandMapper.getYougouBrandNo())){
				hasBrand=true;
				break;
			}
		}
		if(!hasBrand){
			log.error("[淘宝导入]商家编码:{}-没有分配淘宝品牌:{} 对应的优购品牌:{}的权限.", merchantCode, taobaoYougouBrandMapper.getTaobaoBrandName(), taobaoYougouBrandMapper.getYougouBrandName());
			throw new BusinessException("供应商："+merchantCode+" 没有分配淘宝品牌："+taobaoYougouBrandMapper.getTaobaoBrandName()+" 对应的优购品牌:"+taobaoYougouBrandMapper.getYougouBrandName()+"权限.");
		}
		
		Long nickId = YmcThreadLocalHolder.getNickId();//Add by LQ on 20150602
		
		//第四步，品牌对应模板复制到tbl_merchant_taobao_yougou_brand
		TaobaoYougouBrand taobaoYougouBrand = taobaoBrandMapper.getTaobaoYougouBrandByBid(merchantCode,nickId,taobaoYougouBrandMapper.getTaobaoBid());
		String id = null;
		if(taobaoYougouBrand == null){
			taobaoYougouBrand = new TaobaoYougouBrand();
			id = UUIDGenerator.getUUID();
			taobaoYougouBrand.setId(UUIDGenerator.getUUID());
		}
		taobaoYougouBrand.setMerchantCode(merchantCode);
		taobaoYougouBrand.setNickId(nickId);
		taobaoYougouBrand.setTaobaoBid(taobaoYougouBrandMapper.getTaobaoBid());
		taobaoYougouBrand.setYougouBrandNo(taobaoYougouBrandMapper.getYougouBrandNo());
		taobaoYougouBrand.setYougouBrandName(taobaoYougouBrandMapper.getYougouBrandName());
		taobaoYougouBrand.setOperater(merchantCode);
		taobaoYougouBrand.setOperated(DateUtil.getFormatByDate(new Date()));
		if(null == id){
			taobaoBrandMapper.updateTaobaoYougouBrandByTaobaoBid(taobaoYougouBrand);
		}else{
			taobaoBrandMapper.insertTaobaoYougouBrand(taobaoYougouBrand);
		}
		
		//处理分类
		//第一步，查询淘宝分类在数据库存不存在
		TaobaoItemCat taobaoItemCat = taobaoItemCatMapper.getTaobaoItemCatByCid(cid);
		if(taobaoItemCat == null){
			log.error("[淘宝导入]商家编码:{}-淘宝商品数据中分类信息在数据库中不存在.cid:{}", merchantCode, cid);
			throw new BusinessException("淘宝商品数据中分类信息在数据库中不存在,cid:"+cid);
		}
		//第二步，查询淘宝分类有没有和优购做对应关系
		TaobaoYougouItemCat taobaoYougouItemCatMapper = taobaoItemCatMapper.getTaobaoYougouItemCatByCidTemplet(cid);
		if(taobaoYougouItemCatMapper == null){
			log.error("[淘宝导入]商家编码:{}-淘宝商品数据中分类信息没有和优购做对应关系.cid:{}", merchantCode, cid);
			throw new BusinessException("淘宝商品数据中分类信息没有和优购做对应关系,cid:"+cid);
		}
		//第三步，淘宝分类对应模板复制到tbl_merchant_taobao_yougou_itemcat
		TaobaoYougouItemCat taobaoYougouItemCat = taobaoItemCatMapper.getTaobaoYougouItemCatByCid(merchantCode, nickId, cid);
		String catId = null;
		if(taobaoYougouItemCat==null){
			catId = UUIDGenerator.getUUID();
			taobaoYougouItemCat=new TaobaoYougouItemCat();
			taobaoYougouItemCat.setId(UUIDGenerator.getUUID());
		}
		taobaoYougouItemCat.setMerchantCode(merchantCode);
		taobaoYougouItemCat.setNickId(nickId);
		taobaoYougouItemCat.setOperated(DateUtil.getFormatByDate(new Date()));
		taobaoYougouItemCat.setOperater(merchantCode);
		taobaoYougouItemCat.setTaobaoCid(taobaoYougouItemCatMapper.getTaobaoCid());
		taobaoYougouItemCat.setTaobaoCatFullName(taobaoYougouItemCatMapper.getTaobaoCatFullName());
		taobaoYougouItemCat.setYougouCatNo(taobaoYougouItemCatMapper.getYougouCatNo());
		taobaoYougouItemCat.setYougouCatFullName(taobaoYougouItemCatMapper.getYougouCatFullName());
		taobaoYougouItemCat.setYougouCatStruct(taobaoYougouItemCatMapper.getYougouCatStruct());
		if(null == catId){
			taobaoItemCatMapper.updateTaobaoYougouItemCatByCid(taobaoYougouItemCat);
		}else{
			taobaoItemCatMapper.insertTaobaoYougouItemCat(taobaoYougouItemCat);
		}
		//清理淘宝商品表中的 品牌，分类的值
		item.setYougouCateNo("");
		item.setYougouBrandNo("");
		item.setYougouBrandName("");
		taobaoItemMapper.updateByPrimaryKeySelective(item);
		
		//处理属性
		//删除属性对应关系
		taobaoYougouItemPropMapper.deletePropByCid(cid, merchantCode);
		//根据新模板重新复制
		taobaoYougouItemCatTempletMapper.copyItemPropTemplet2YougouItemPropByCid(merchantCode, nickId, merchantCode, DateUtil.getFormatByDate(new Date()), cid);
		//处理属性值
		//删除属性值对应关系
		taobaoYougouItemPropMapper.deletePropValueByCid(cid, merchantCode);
		//根据新模板重新复制属性值
		taobaoYougouItemCatTempletMapper.copyItemPropValueTemplet2YougouItemPropValueByCid(merchantCode, nickId, merchantCode, DateUtil.getFormatByDate(new Date()), cid);
		//删除商品的属性和属性值
		taobaoItemExtendPropMapper.deleteTaobaoItemYougouPropValueByNumIid(item.getNumIid(), merchantCode);
		
		Map<String, Set<String>> mapItemSkuData = new HashMap<String, Set<String>>();
		//查询sku信息
		List<TaobaoItemSku> skuList = taobaoItemSkuMapper.queryTaobaoItemSkuByNumIid(item.getNumIid());
		String sizePid = taobaoItemPropMapper.getTaobaoPidYougouSizeMapperingByCid(merchantCode, cid);
		for(TaobaoItemSku sku : skuList){
			setItemSkuProp(mapItemSkuData, sku.getProperties(), sku.getPropertiesName(), sku.getOuterId(),sizePid);
		}
		TaobaoItemExtend extendStatus = null;
		//查询扩展信息
		//删除尺码信息
		for(String extendId : exntendIds){
			TaobaoItemExtend extend = taobaoItemExtendMapper.getTaobaoItemExtendByExtendId(extendId);
			Set<String> setItemSku = mapItemSkuData.get(extend.getYougouSpecName());
			//删除尺寸信息
			taobaoItemExtendPropMapper.deleteTaobaoItemYougouPropValueSizeByExtendId(extendId, merchantCode);
			insertItemExtendsSizeProp(item.getNumIid(), extend.getExtendId(), cid,setItemSku,merchantCode);
			
			//更换状态
			extendStatus = new TaobaoItemExtend();
			extendStatus.setExtendId(extendId);
			extendStatus.setCheckStatus(TaobaoImportConstants.STATUS_NOT_ALLOW);
			extendStatus.setMerchantCode(merchantCode);
			taobaoItemExtendMapper.updateByPrimaryKeySelective(extendStatus) ;
		}
	}
	 
	/**
	 * 是否以颜色pid开头
	 * @param props
	 * @return
	 */
	private boolean hasStartWidthColorPid(String props){
		for(String colorPid : TaobaoImportConstants.COLOR_PIDS){
			if(props.startsWith(colorPid)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 是否包含颜色pid, 如果包含，返回其位置
	 * @param props
	 * @return
	 */
	private int getColorPidIndex(String props){
		int index = -1;
		for(String colorPid : TaobaoImportConstants.COLOR_PIDS){
			index = props.indexOf(colorPid);
			if(index != -1){
				break;
			}
		}
		return index;
	}
	

 
	@Override
	/**
	 * 检查brand 和cat
	 */
	public void checkBrandAndCat(TaobaoImportVo importVo,String merchantCode){
		List<com.yougou.pc.model.brand.Brand> brandList = commodityMerchantApi.getBrandByMerchantCode(merchantCode);
		Long brand_pid=TaobaoImportConstants.BRAND_P_ID;
		/**
		 * 判断品牌是否存在
		 */
		for(Long brand_vid :importVo.getTaobaoBrandMap().keySet()){
			//处理品牌
			//第一步，查询淘宝品牌在数据库存不存在
			TaobaoBrand taobaoBrand = taobaoBrandMapper.getTaobaoBrandByPidVid(brand_pid,brand_vid);
			if(taobaoBrand==null){
				log.error("[淘宝导入]商家编码:{}-{}品牌在数据库中不存在.pid:{},vid:{}", merchantCode, importVo.getTaobaoBrandMap().get(brand_vid), brand_pid, brand_vid);
				importVo.getErrorList().add(new ErrorVo(0, (importVo.getTaobaoBrandMap().get(brand_vid))+"品牌在数据库中不存在,pid:"+brand_pid+"  vid:"+brand_vid));
				continue;
			}
			//第二步，查询淘宝品牌有没有和优购品牌做对应关系
			TaobaoYougouBrand taobaoYougouBrand = taobaoBrandMapper.getBrandMapperByBid(taobaoBrand.getBid());
			if(taobaoYougouBrand == null){
				log.error("[淘宝导入]商家编码:{}-{}品牌没有设置与yougou对应关系.pid:{},vid:{}", merchantCode, taobaoBrand.getName(), brand_pid, brand_vid);
				importVo.getErrorList().add(new ErrorVo(0,taobaoBrand.getName() +"品牌没有设置与yougou对应关系,pid:"+brand_pid+"  vid:"+brand_vid));
				continue;
			}
			//第三步，查询该供应商是否有对应品牌的授权			
			boolean hasBrand=false;
			for(com.yougou.pc.model.brand.Brand brand : brandList){
				if(brand.getBrandNo().equalsIgnoreCase(taobaoYougouBrand.getYougouBrandNo())){
					hasBrand=true;
					break;
				}
			}
			if (!hasBrand) {
				log.error("[淘宝导入]商家编码:{}-没有分配淘宝品牌:{}对应的优购品牌:{}的权限.", merchantCode, taobaoYougouBrand.getTaobaoBrandName(), taobaoYougouBrand.getYougouBrandName());
				importVo.getErrorList().add(new ErrorVo(0,(importVo.getTaobaoBrandMap().get(brand_vid))+ "供应商：" + merchantCode
						+ " 没有分配淘宝品牌：" + (StringUtils.isEmpty(taobaoYougouBrand .getTaobaoBrandName()) ? ""
						: taobaoYougouBrand.getTaobaoBrandName()) + " 对应的优购品牌:"
				+ taobaoYougouBrand.getYougouBrandName() + " 的权限."));
				continue;
			}
		}
		
		for(Long cid : importVo.getTaobaoCatMap().keySet()){					
			//处理分类
			//第一步，查询淘宝分类在数据库存不存在
			TaobaoItemCat taobaoItemCat = taobaoItemCatMapper.getTaobaoItemCatByCid(cid);
			if(taobaoItemCat == null){
				log.error("[淘宝导入]商家编码:{}-{}分类信息在数据库中不存在.cid:{}", merchantCode,importVo.getTaobaoCatMap().get(cid)[0], cid);
				importVo.getErrorList().add(new ErrorVo(0,importVo.getTaobaoCatMap().get(cid)[0]+"分类信息在数据库中不存在,cid:"+cid));
			    continue;
			}
			
			//  第二步，查询淘宝分类有没有和优购做对应关系
			TaobaoYougouItemCat taobaoYougouItemCat = taobaoItemCatMapper.getTaobaoYougouItemCatByCidTemplet(cid);
			if(taobaoYougouItemCat == null){
				log.error("[淘宝导入]商家编码:{}-{}分类信息没有和优购做对应关系.cid:{}", merchantCode, taobaoItemCat.getName(), cid);
				importVo.getErrorList().add(new ErrorVo(0,taobaoItemCat.getName()+"分类信息没有和优购做对应关系,cid:"+cid));
				continue;
			}
			
			//第四步，品牌对应模板复制到tbl_merchant_taobao_yougou_brand	
			String brandName = importVo.getTaobaoCatMap().get(cid)[0];
			String vid = importVo.getTaobaoCatMap().get(cid)[1];
			TaobaoBrand taobaoBrand = taobaoBrandMapper.getTaobaoBrandByPidVid(brand_pid,Long.parseLong(vid));
			if(taobaoBrand==null){
				log.error("[淘宝导入]商家编码:{}-{}品牌在数据库中不存在.cid:{},pid:{},vid:{}", merchantCode, brandName, cid, brand_pid, vid);
				importVo.getErrorList().add(new ErrorVo(0, (brandName)+"品牌在数据库中不存在,pid:"+brand_pid+"  vid:"+vid));
				continue;
			}
			TaobaoYougouBrand taobaoYougouBrand = taobaoBrandMapper.getBrandMapperByBid(taobaoBrand.getBid());
			if(taobaoYougouBrand == null){
				log.error("[淘宝导入]商家编码:{}-{}品牌没有设置与yougou对应关系.cid:{},pid:{},vid:{}", merchantCode, taobaoBrand.getName(), cid, brand_pid, vid);
				importVo.getErrorList().add(new ErrorVo(0,taobaoBrand.getName() +"品牌没有设置与yougou对应关系,pid:"+brand_pid+"  vid:"+vid));
				continue;
			}
			addTaobaoYougou(merchantCode, 0, taobaoYougouBrand, cid, taobaoYougouItemCat);
		}
	}
 
}