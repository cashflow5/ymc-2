package com.yougou.kaidian.commodity.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.time.DateUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import com.yougou.kaidian.commodity.constant.CommodityConstant;
import com.yougou.kaidian.commodity.dao.CommodityMapper;
import com.yougou.kaidian.commodity.dao.CommodityPropertyMapper;
import com.yougou.kaidian.commodity.model.vo.CommodityAndProductExportVo;
import com.yougou.kaidian.commodity.model.vo.CommodityQueryVo;
import com.yougou.kaidian.commodity.model.vo.CommoditySubmitVo;
import com.yougou.kaidian.commodity.model.vo.ProductExportVo;
import com.yougou.kaidian.commodity.service.ICommodityService;
import com.yougou.kaidian.commodity.service.IMerchantCommodityService;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.commodity.pojo.Brand;
import com.yougou.kaidian.common.commodity.pojo.Cat;
import com.yougou.kaidian.common.commodity.pojo.Commodity;
import com.yougou.kaidian.common.commodity.util.CommodityPicIndexer;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.common.util.DateUtil2;
import com.yougou.kaidian.common.util.StringUtil;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.kaidian.common.vo.CommodityImage;
import com.yougou.kaidian.common.vo.Image4BatchUploadMessage;
import com.yougou.kaidian.common.vo.Image4SingleCommodityMessage;
import com.yougou.kaidian.framework.settings.CommoditySettings;
import com.yougou.kaidian.taobao.exception.BusinessException;
import com.yougou.merchant.api.supplier.service.IMerchantImageService;
import com.yougou.merchant.api.supplier.vo.ImageJmsVo;
import com.yougou.ordercenter.api.asm.IAsmApi;
import com.yougou.ordercenter.vo.asm.QueryTraceSaleVo;
import com.yougou.ordercenter.vo.asm.TraceSaleQueryResult;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.api.ICommodityMerchantApiService;
import com.yougou.pc.model.category.Category;
import com.yougou.pc.model.prop.PropValue;
import com.yougou.wms.wpi.common.exception.WPIBussinessException;
import com.yougou.wms.wpi.inventory.domain.vo.InvAssistVo;
import com.yougou.wms.wpi.inventory.domain.vo.InventoryAssistVo;
import com.yougou.wms.wpi.inventory.service.IInventoryForMerchantService;

@Service
public class CommodityServiceImpl implements ICommodityService {

	private static final Logger logger = LoggerFactory.getLogger(CommodityServiceImpl.class);
	@Resource
	private CommodityPropertyMapper catMapper;
	@Resource
	private CommodityMapper commodityMapper;
	@Resource
	private ICommodityBaseApiService commodityBaseApiService;
	@Resource
	private IMerchantCommodityService merchantCommodityService;
	@Resource
	private ICommodityMerchantApiService commodityApi;
	@Resource
	@Qualifier(value="jmsTemplate")
	private AmqpTemplate amqpTemplate;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private CommoditySettings commoditySetting;
	@Resource
	private IMerchantImageService merchantImageService;
	@Resource
	private IInventoryForMerchantService inventoryForMerchantService;
	@Resource
	private IAsmApi asmApiImpl;
	private Pattern imgPattern = Pattern.compile("([^(/)]*).jpg");
	
	/*
	 * 本次变更为从tbl_sp_brand表中获取商品授权品牌
	 * 然后调用dubbo接口获取品牌的id以及品牌名称
	 * @see com.yougou.commodity.service.ICommodityService#queryBrandList(java.lang.String)
	 */
	@Override
	public List<Brand> queryBrandList(String merchantCode) {
		if (StringUtils.isBlank(merchantCode))
			return null;
		
		List<Brand> _brands = catMapper.querySupplierBrandList(merchantCode);
		List<Brand> brands = new ArrayList<Brand>();
		Brand brand = null;
		for (Brand _brand : _brands) {
			com.yougou.pc.model.brand.Brand b = commodityBaseApiService.
					getBrandByNo(_brand.getBrandNo());
			if (null == b || b.getDelFlag() == null || b.getDelFlag() != 1) 
				continue;
			
			brand = new Brand();
			brand.setBrandName(b.getBrandName());
			brand.setBrandNo(b.getBrandNo());
			brand.setId(b.getId());
			brand.setSpeelingName(b.getSpeelingName());
			brand.setDeleteflag(b.getDelFlag());
			brands.add(brand);
		}
		
		return brands;
	}

	@Override
	public List<Cat> queryCatList(String merchantCode, String structName) {
		List<Cat> list = new ArrayList<Cat>();
		List<Cat> _cats = catMapper.querySupplierCatListByStructName(merchantCode, structName);
		if (CollectionUtils.isNotEmpty(_cats)) {
			Set<String> set = new HashSet<String>();
			// 查询first cat
			if (StringUtils.isBlank(structName)) {
				for (Cat _cat : _cats) {
					if (StringUtils.isBlank(_cat.getStructName()) 
							|| _cat.getStructName().length() != 8) 
						continue;
					set.add(_cat.getStructName().substring(0, 2));
				}
			} else if (structName.trim().length() == 2) { // 查询second cat
				for (Cat _cat : _cats) {
					if (StringUtils.isBlank(_cat.getStructName()) 
							|| _cat.getStructName().length() != 8) 
						continue;
					set.add(_cat.getStructName().substring(0, 5));
				}
			} else if (structName.trim().length() == 5) { // 查询third cat
				for (Cat _cat : _cats) {
					if (StringUtils.isBlank(_cat.getStructName()) 
							|| _cat.getStructName().length() != 8) 
						continue;
					set.add(_cat.getStructName());
				}
			}
			if (CollectionUtils.isNotEmpty(set)) {
				Cat _temp = null;
				for (String str : set) {
					Category category = commodityBaseApiService.getCategoryByStructName(str);
					if (null != category && category.getDeleteFlag() == 1) {
						_temp = new Cat();
						_temp.setCatName(category.getCatName());
						_temp.setId(category.getId());
						_temp.setNo(category.getCatNo());
						_temp.setDeleteFlag(category.getDeleteFlag().intValue());
						_temp.setStructName(category.getStructName());
						list.add(_temp);
					}
				}
			}
		}
		
		return list;
	}
	
	@Override
	public List<Cat> querySupplierFirstCatListByBrandNo(String merchantCode, String brandNo) {
		List<Cat> list = new ArrayList<Cat>();
		List<Cat> _cats = catMapper.queryCategoryListByBrandNo(merchantCode, brandNo);
		if (CollectionUtils.isNotEmpty(_cats)) {
			Set<String> set = new HashSet<String>();
			// 查询first cat
			for (Cat _cat : _cats) {
				if (StringUtils.isBlank(_cat.getStructName()) 
						|| _cat.getStructName().length() != 8) 
					continue;
				set.add(_cat.getStructName().substring(0, 2));
			}
			
			if (CollectionUtils.isNotEmpty(set)) {
				Cat _temp = null;
				for (String str : set) {
					Category category = commodityBaseApiService.getCategoryByStructName(str);
					if (null != category && category.getDeleteFlag() == 1) {
						_temp = new Cat();
						_temp.setCatName(category.getCatName());
						_temp.setId(category.getId());
						_temp.setNo(category.getCatNo());
						_temp.setDeleteFlag(category.getDeleteFlag().intValue());
						_temp.setStructName(category.getStructName());
						list.add(_temp);
					}
				}
			}
		}
		
		return list;
	}
	
	/**
	 * merchantCode 是商家Id  【2c94811d321e2edc01321ebc2989040e】
	 */
	@Override
	public List<Cat> queryCatList(Integer structLength, String merchantCode, String structName) {
		return this.queryCatList(merchantCode, structName);
	}
	
	/**
	 * <p>通过brandId获取brandNo</p>
	 * @param brandId
	 * @return brandNo
	 */
	private String transformBrandNoByBrandId(String brandId) {
		com.yougou.pc.model.brand.Brand brand = commodityBaseApiService.getBrandById(brandId);
		return brand != null ? brand.getBrandNo() : null;
	}
	private String transformBrandIdByBrandNo(String brandNo) {
		com.yougou.pc.model.brand.Brand brand = commodityBaseApiService.getBrandByNo(brandNo);
		return brand != null ? brand.getId() : null;
	}
	//判断是否为brandId
	private boolean isBrandId(String brandId) {
		if (brandId.length() != 32) return false;
		return true;
	}
	
	@Override
	public List<Category> getAllCategoryByBrandId(String merchantCode, String brandId) {
		if (StringUtils.isBlank(brandId) || StringUtils.isBlank(merchantCode)) {
			return null;
		}
		/*
		 * 为了解决接口调用传值不一致的问题, 该接口设计时使用brandId, 
		 * 但是修改商品时, 未返回brandId, 所以传过来的是brandNo
		 */
		String brandNo = isBrandId(brandId) ? this.transformBrandNoByBrandId(brandId) : brandId;
		brandId = isBrandId(brandId) ? brandId : this.transformBrandIdByBrandNo(brandNo);
		List<Category> list = new ArrayList<Category>();
		if (StringUtils.isNotBlank(brandNo)) {
			//获取品牌下所有有效的三级分类
			List<Category> base_categorys = commodityBaseApiService.getCategoryListByBrandId(brandId, (short)1, null);
			List<Cat> _cats = catMapper.queryCategoryListByBrandNo(merchantCode, brandNo);
			//为了兼容之前的老数据（商家中心没有维护品牌和分类间的关系）
			if (CollectionUtils.isEmpty(_cats)) { //默认该品牌有所有分类(数据修复后，该逻辑应去掉)
				_cats = catMapper.querySupplierCatList(merchantCode);
			}
			
			if (CollectionUtils.isNotEmpty(_cats) && CollectionUtils.isNotEmpty(base_categorys)) {
				//筛选品牌有效的三级分类
				for (Cat _cat : _cats) {
					for (Category base_cat : base_categorys) {
						if (_cat.getNo().equals(base_cat.getCatNo())) {
							list.add(base_cat);
							break;
						}
					}
				}
			}
		}
		
		Set<String> firstORsecondCat = new HashSet<String>(); 
		//逆推第一级和第二级分类
		for (Category category : list) {
			if (StringUtils.isBlank(category.getStructName()) 
					|| category.getStructName().length() != 8)
				continue;
			
			firstORsecondCat.add(category.getStructName().substring(0, 2));
			firstORsecondCat.add(category.getStructName().substring(0, 5));
		}
		if (CollectionUtils.isNotEmpty(firstORsecondCat)) {
			for (String str : firstORsecondCat) {
				list.add(commodityBaseApiService.getCategoryByStructName(str));
			}
		}
		
		for (Category category : list) {
			category.setParentId(this.subCategory(category.getStructName()));
		}
		
		return list;
	}

	/**
	 * 截取分类的父级
	 * 
	 * @param structName 10-11-12
	 * @return 10-11
	 */
	private String subCategory(String structName) {
		if (StringUtils.isBlank(structName)) 
			return null;
		
		int index = structName.lastIndexOf("-");
		if (index == -1) {
			return "0";
		}
		
		return structName.substring(0, index);
	}
	
	public PageFinder<Commodity> queryCommodityList(Query query, CommodityQueryVo commodityQueryVo, ModelMap map) {
		Boolean flag = StringUtils.isNotEmpty(commodityQueryVo.getOnSaleQuantiry());
		List<Commodity> lstCommodity = null;
		int page = query.getPage(),pageSize = query.getPageSize();
		if (flag) {
			query = null;
		} 
		lstCommodity = commodityMapper.queryCommodityList(commodityQueryVo,query);
		if (CollectionUtils.isEmpty(lstCommodity))
			return new PageFinder<Commodity>(page, pageSize, 0, null);
		
		List<String> commodityNoList = new ArrayList<String>();
		for (Commodity commodity : lstCommodity) {
			commodityNoList.add(commodity.getCommodityNo());
		}
		//查询销量，采取异步请求处理
		//Map<String, Integer> mapCommoditySale = this.querySaleNumForBIByCommodityNos(commodityNoList, commodityQueryVo.getMerchantCode());
		
		//查询可售库存
		Map<String, Integer> qtyMap = new HashMap<String, Integer>();
		try {
			List<InventoryAssistVo> qtys = null;
			if (StringUtils.isNotBlank(commodityQueryVo.getWarehouseCode())) {
				qtys = inventoryForMerchantService.queryCommodityInventory(commodityNoList, commodityQueryVo.getWarehouseCode());
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
			logger.error("查询queryCommodityInventory发生异常.", e);
		}
		int onSaleQuantity=0;
		List<Commodity> lstCommodity_new =new  ArrayList<Commodity>();
		for (Commodity _commodity : lstCommodity) {
			onSaleQuantity=qtyMap.containsKey(_commodity.getCommodityNo()) ? qtyMap.get(_commodity.getCommodityNo()) : 0;
			_commodity.setOnSaleQuantity(onSaleQuantity);
			//单品页链接
			_commodity.setProdUrl("/commodity/"+_commodity.getCommodityNo()+"/generate.sc");
			//_commodity.setSaleQuantity(mapCommoditySale.containsKey(_commodity.getCommodityNo()) ? mapCommoditySale.get(_commodity.getCommodityNo()) : 0);
			if (flag && onSaleQuantity >= Integer.valueOf(commodityQueryVo.getOnSaleQuantiry())) {
				lstCommodity_new.add(_commodity);
			}
		}
		PageFinder<Commodity> pageFinder = null;
		if (flag) {
			pageFinder = new PageFinder<Commodity>(page, pageSize, lstCommodity_new.size(), lstCommodity_new.subList((page-1)*pageSize, Math.min(pageSize*page,lstCommodity_new.size())));
		} else {
			int count = commodityMapper.queryCommodityCount(commodityQueryVo);
			pageFinder = new PageFinder<Commodity>(page, pageSize, count, lstCommodity);
		}
		map.put("commodityNos", StringUtils.join(commodityNoList, ","));
		return pageFinder;
	}
	
	/**
	 * 根据供应商编码查询相应的货品和商品信息
	 * @param supplyCode 供应商编码
	 * @author wang.m
	 * @Date 2012-05-10
	 * @return
	 */
	public Map<String,Object> getProductBySupplyCode(String supplyCode){
		Map<String,Object> commMap=null;
		if(StringUtils.isNotBlank(supplyCode)){
			commMap=commodityMapper.getProductByprouductNo(supplyCode);
		}
		return commMap;
	}

	
	@Override
	public List<ProductExportVo> queryExportProductList(CommodityQueryVo commodityQueryVo) throws Exception {
		return commodityMapper.queryExportProductList(commodityQueryVo);
	}

	@Override
	public PageFinder<Map<String, Object>> queryWaitSaleCommodityList(Query query, CommodityQueryVo vo, ModelMap map) {
		List<Map<String, Object>> lstCommodity  = 
				commodityMapper.queryWaitSaleCommodity(vo,query);
		if (CollectionUtils.isEmpty(lstCommodity)) 
			return new PageFinder<Map<String, Object>>(query.getPage(), query.getPageSize(), 0, lstCommodity);
			
		Object imgMessage=null;
		Date submit_audit_date=null;
		StringBuffer sb = new StringBuffer(256);
		for (Map<String, Object> _obj : lstCommodity) {
			String commodityNo = MapUtils.getString(_obj, COMMODITY_NO, "");
			if (StringUtils.isNotBlank(commodityNo)) 
				sb.append(commodityNo+",");
			imgMessage=this.redisTemplate.opsForHash().get(CacheConstant.C_IMAGE_MASTER_JMS_KEY, commodityNo);
			_obj.put("jmsFinish", imgMessage==null?0:1);
			
			submit_audit_date=commodityApi.getLastAuditTimeByCommodityNo(commodityNo, CommodityConstant.COMMODITY_UPDATE_LOG_TYPE_SUBMIT);
			_obj.put("submit_audit_date", submit_audit_date==null?null:DateUtil2.getDateTime(submit_audit_date));
			//completeCommodity(_obj);
			if ((CommodityConstant.COMMODITY_STATUS_REFUSE + "")
					.equals(MapUtils.getString(_obj, COMMODITY_STATUS, ""))) {
				_obj.put(REFUSE_REASON, commodityApi.getLastRefuseReasonByCommodityNo(commodityNo));
			}
		}
		//查询可售库存，采取异步请求处理
		map.put("commodityNos", sb.substring(0, sb.length()-1));
		return new PageFinder<Map<String, Object>>(query.getPage(),
				query.getPageSize(), this.queryWaitSaleCommodityCount(vo), lstCommodity);
	}
	
	/**
	 * <p>补全商品数据</p>
	 * 
	 * @param commodityMap
	 */
	/*
	private void completeCommodity(Map<String, Object> commodityMap, Map<String, Integer> qtyMap) {
		if (MapUtils.isEmpty(commodityMap)) {
			return;
		}
		String commodityNo = MapUtils.getString(commodityMap, COMMODITY_NO, "");
		commodityMap.put(ON_SALE_QUANTITY, qtyMap.containsKey(commodityNo) ? qtyMap.get(commodityNo) : 0);
		
		// 审核拒绝才能展示拒绝原因
		if (!(CommodityConstant.COMMODITY_STATUS_REFUSE + "")
				.equals(MapUtils.getString(commodityMap, COMMODITY_STATUS, ""))) {
			commodityMap.put(REFUSE_REASON, null);
		}
	}
	
	/**
	 * <p>补全商品数据</p>
	 * 
	 * @param commodityMap
	 */
	/*
	private void completeCommodity(Map<String, Object> commodityMap) {
		if (MapUtils.isEmpty(commodityMap)) {
			return;
		}
		// 审核拒绝才能展示拒绝原因
		if (!(CommodityConstant.COMMODITY_STATUS_REFUSE + "")
				.equals(MapUtils.getString(commodityMap, COMMODITY_STATUS, ""))) {
			commodityMap.put(REFUSE_REASON, null);
		}
	}*/
	
	final static String COMMODITY_NO = "commodity_no";
	
	final static String ON_SALE_QUANTITY = "on_sale_quantity";
	
	final static String COMMODITY_STATUS = "commodity_status";
	
	final static String REFUSE_REASON = "refuse_reason";
	
	private int queryWaitSaleCommodityCount(CommodityQueryVo vo) {
		return commodityMapper.queryWaitSaleCommodityCount(vo);	
	}

	@Override
	public List<ProductExportVo> exportWaitSaleProductList(CommodityQueryVo vo) throws Exception {
		return commodityMapper.exportWaitSaleProductList(vo);
	}

	
	@Override
	public void sendJmsForMaster(CommoditySubmitVo submitVo, int oldLength) {
		boolean isYgimgTemp=true;
		if(submitVo.getProdDesc()!=null){
			isYgimgTemp=submitVo.getProdDesc().indexOf("ygimg.cn/pics/merchantpics")==-1;
		}
		//角度图与描述图未更改时、不发送MQ
		int newLength = submitVo == null ? 0 : submitVo.getProdDesc().length();
		if (newLength == oldLength && isYgimgTemp && "0,0,0,0,0,0,0".equals(StringUtil.join(submitVo.getImgFileId(), ","))) 
			return;
		
		//发送MQ
		// 定位放大镜图索引
		int sheetIndex = 1;
		try {
			if(StringUtils.isNotBlank(submitVo.getStructName())){
				sheetIndex = CommodityPicIndexer.indexSheets(merchantCommodityService.getCommodityCatNamesByCatStructName(submitVo.getStructName()));
			}else{
				sheetIndex = CommodityPicIndexer.indexSheets(merchantCommodityService.getCommodityCatNamesByCatID(submitVo.getCatId()));
			}
		} catch (Exception e) {	}
		Image4SingleCommodityMessage imgMessage=new Image4SingleCommodityMessage();
		String[] picURL=submitVo.getImgFileId();
		CommodityImage[] commodityImageArray=new CommodityImage[picURL.length];
		for(int i=0;i<picURL.length;i++){
			commodityImageArray[i]=new CommodityImage();
			commodityImageArray[i].setIndex(i+1);
			if(picURL[i].length()>3){
				Matcher matcher = imgPattern.matcher(picURL[i]);
				if(matcher.find()){
					commodityImageArray[i].setPicName(matcher.group(0));
				}else{
					commodityImageArray[i].setPicName("0");
				}
			}else{
				commodityImageArray[i].setPicName("0");
			}
			commodityImageArray[i].setPicUrl(picURL[i]);
		}

		imgMessage.setCommodityImages(commodityImageArray);
		imgMessage.setCommodityNo(submitVo.getCommodityNo());
		imgMessage.setCreateTime(new Date());
		imgMessage.setFtpRelativePath(MessageFormat.format(commoditySetting.imageFtpPreTempSpace, submitVo.getMerchantCode()));
		imgMessage.setId(UUIDGenerator.getUUID());
		imgMessage.setMerchantCode(submitVo.getMerchantCode());
		imgMessage.setProDesc((newLength == oldLength) && isYgimgTemp? null : submitVo.getProdDesc());
		imgMessage.setSeqNo(sheetIndex);
		imgMessage.setStatus(0);
		imgMessage.setUrlFragment(this.getCommodityUrlFragment(submitVo));
		//消息持久化到cache和DB
		this.redisTemplate.opsForHash().put(CacheConstant.C_IMAGE_MASTER_JMS_KEY, submitVo.getCommodityNo(), imgMessage);
		
		ImageJmsVo jmsVo = new ImageJmsVo();
		jmsVo.setCommodityNo(imgMessage.getCommodityNo());
		jmsVo.setMerchantCode(imgMessage.getMerchantCode());
		jmsVo.setCreateTime(new Date());
		//l 单品               批量--p(l图) m(b图)
		jmsVo.setPicType("l");
		jmsVo.setProDesc(imgMessage.getProDesc());
		jmsVo.setSeqNo(imgMessage.getSeqNo());
		jmsVo.setUrlFragment(imgMessage.getUrlFragment());
		jmsVo.setStatus(0);
		jmsVo.setId(imgMessage.getId());
		jmsVo.setImageId(ArrayUtils.isEmpty(submitVo.getImgFileId()) ? "" : StringUtil.join(submitVo.getImgFileId(), ","));
		merchantImageService.addImageJms(jmsVo);
		try {
			amqpTemplate.convertAndSend("ymc.handleimage.queue", imgMessage);
			logger.warn("发送Jms消息(角度图片逻辑处理)到队列ymc.handleimage.queue，消息详情为：{}",
					ToStringBuilder.reflectionToString(imgMessage, ToStringStyle.SHORT_PREFIX_STYLE));
		} catch (AmqpException e) {
			logger.error("发送切图(单品)产生异常:商品编码{} 商家编码{}，报错信息",new Object[]{submitVo.getMerchantCode(),submitVo.getCommodityNo(),e});
		}
	}
	

	@Override
	public void sendJmsForBatch(CommoditySubmitVo submitVo, String imgType,String picUrl) {
		// 定位放大镜图索引
		int sheetIndex = 1;
		try {
			sheetIndex = CommodityPicIndexer.indexSheets(merchantCommodityService.getCommodityCatNamesByCatStructName(submitVo.getStructName()));
		} catch (Exception e) {logger.error("定位放大镜图索引获取发生错误：",e);	}
		Image4BatchUploadMessage message2=new Image4BatchUploadMessage();
		message2.setCommodityNo(submitVo.getCommodityNo());
		message2.setCreateTime(new Date());
		message2.setFtpRelativePath(MessageFormat.format(commoditySetting.imageFtpPreTempSpace, submitVo.getMerchantCode()));
		message2.setId(UUIDGenerator.getUUID());
		message2.setMerchantCode(submitVo.getMerchantCode());
		message2.setPicName(submitVo.getProdDesc());
		message2.setPicType(imgType);
		message2.setPicUrl(picUrl);
		message2.setSeqNo(sheetIndex);
		message2.setStatus(0);
		message2.setUrlFragment(this.getCommodityUrlFragment(submitVo));
		
		//消息持久化到cache和DB
		this.redisTemplate.opsForHash().put(CacheConstant.C_IMAGE_BATCH_JMS_KEY, submitVo.getCommodityNo(), message2);
		
		ImageJmsVo jmsVo = new ImageJmsVo();
		jmsVo.setCommodityNo(message2.getCommodityNo());
		jmsVo.setMerchantCode(message2.getMerchantCode());
		jmsVo.setCreateTime(new Date());
		jmsVo.setPicType(message2.getPicType());
		jmsVo.setProDesc(message2.getPicName());
		jmsVo.setSeqNo(message2.getSeqNo());
		jmsVo.setUrlFragment(message2.getUrlFragment());
		jmsVo.setStatus(message2.getStatus());
		jmsVo.setId(message2.getId());
		jmsVo.setImageId("");
		merchantImageService.addImageJms(jmsVo);
		try {
			logger.warn("发送Jms消息(批量上传图片逻辑处理)到队列ymc.handleimage.batch.queue，消息详情为：{}",
					ToStringBuilder.reflectionToString(message2, ToStringStyle.SHORT_PREFIX_STYLE));
			amqpTemplate.convertAndSend("ymc.handleimage.batch.queue", message2);
		} catch (AmqpException e) {
			logger.error("发送JMS消息失败：",e);
		}
	}
	

	@Override
	public void sendJms4SingleCommodityImg(CommoditySubmitVo submitVo)
			throws Exception {
		int sheetIndex = 1;
			sheetIndex = CommodityPicIndexer
					.indexSheets(merchantCommodityService
							.getCommodityCatNamesByCatStructName(submitVo
									.getStructName()));

		Image4SingleCommodityMessage imgMessage = new Image4SingleCommodityMessage();
		String[] picURL = submitVo.getImgFileId();
		CommodityImage[] commodityImageArray = new CommodityImage[picURL.length];
		for (int i = 0; i < picURL.length; i++) {
			commodityImageArray[i] = new CommodityImage();
			commodityImageArray[i].setIndex(i + 1);
			if (picURL[i].length() > 3) {
				Matcher matcher = imgPattern.matcher(picURL[i]);
				if (matcher.find()) {
					commodityImageArray[i].setPicName(matcher.group(0));
				} else {
					commodityImageArray[i].setPicName("0");
				}
			} else {
				commodityImageArray[i].setPicName("0");
			}
			commodityImageArray[i].setPicUrl(picURL[i]);
		}
		imgMessage.setCommodityImages(commodityImageArray);
		imgMessage.setCommodityNo(submitVo.getCommodityNo());
		imgMessage.setCreateTime(new Date());
		imgMessage.setFtpRelativePath(MessageFormat.format(
				commoditySetting.imageFtpPreTempSpace,
				submitVo.getMerchantCode()));
		imgMessage.setId(UUIDGenerator.getUUID());
		imgMessage.setMerchantCode(submitVo.getMerchantCode());
		imgMessage.setProDesc(submitVo.getProdDesc());
		imgMessage.setSeqNo(sheetIndex);
		imgMessage.setStatus(0);
		imgMessage.setUrlFragment(this.getCommodityUrlFragment(submitVo));

		ImageJmsVo jmsVo = new ImageJmsVo();
		jmsVo.setCommodityNo(imgMessage.getCommodityNo());
		jmsVo.setMerchantCode(imgMessage.getMerchantCode());
		jmsVo.setCreateTime(new Date());
		// l 单品 批量--p(l图) m(b图)
		jmsVo.setPicType("l");
		jmsVo.setProDesc(imgMessage.getProDesc());
		jmsVo.setSeqNo(imgMessage.getSeqNo());
		jmsVo.setUrlFragment(imgMessage.getUrlFragment());
		jmsVo.setStatus(0);
		jmsVo.setId(imgMessage.getId());
		jmsVo.setImageId(ArrayUtils.isEmpty(submitVo.getImgFileId())
				? ""
				: StringUtil.join(submitVo.getImgFileId(), ","));

		// commodityApi.updateCommodityDescForMerchant(message.getCommodityNo(),
		// message.getMerchantCode(), newDesc);
		merchantImageService.addImageJms(jmsVo);
		logger.warn("发送单个商品上传图片 描述图，角度图JMS消息到队列ymc.handleimage.queue，消息详情为：{}",
				ToStringBuilder.reflectionToString(imgMessage, ToStringStyle.SHORT_PREFIX_STYLE));
		amqpTemplate.convertAndSend("ymc.handleimage.queue", imgMessage);
	}

	/**
	 * 商品图片URL片段(合成规则：商品品牌英文名称/商品年份/优购商品编码)
	 * 
	 * @param submitVo
	 * @return [looneytunes/2013/99899701]
	 */
	private String getCommodityUrlFragment(CommoditySubmitVo submitVo) {
		if (StringUtils.isBlank(submitVo.getCommodityNo()) || StringUtils.isBlank(submitVo.getYears())
				|| StringUtils.isBlank(submitVo.getCommodityNo())) 
			return null;
		logger.warn("品牌编号=={}",submitVo.getBrandNo());
		com.yougou.pc.model.brand.Brand brand = commodityBaseApiService.getBrandByNo(submitVo.getBrandNo());
		if (null == brand) return null;
		
		return new StringBuilder("/").append(brand.getSpeelingName()).append("/")
				.append(submitVo.getYears()).append("/").append(submitVo.getCommodityNo()).append("/").toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Integer> queryShopCommodityTips(String merchantCode) {
		Map<String, Integer> tips = null; 
		try {
			tips = (Map<String, Integer>) redisTemplate.opsForValue().get(CacheConstant.C_COMMODITY_SHOUYE_TIPS_KEY+":"+merchantCode);
			logger.warn("从redis缓存中获取首页商品相关数量的缓存为{}",JSONObject.fromObject(tips).toString());
		} catch (Exception e) {
			logger.error("首页获取商品提示数据异常。", e);
		}
		
		if (MapUtils.isNotEmpty(tips)) return tips;
		logger.warn("商家{}从redis缓存中获取首页商品相关数量缓存为null，重新从数据库获取首页商品相关数量",merchantCode);
		try {
			//recyclebin_flag   s.recyclebin_flag = 0  
			//tips = commodityApi.getCommodityStatusInfoByMerchantCode(merchantCode);
			//商品组接口没有过滤掉回车站的商品，又不愿修改接口，所以查询自己同步过来的库，自己实现
			List<Map<String,Object>> tipsList = commodityMapper.getCommodityStatusInfoByMerchantCode(merchantCode);
			tips = new HashMap<String,Integer>();
			for(Map<String,Object> map : tipsList){
				tips.put((String)map.get("commodity_status"), ((Number)map.get("cc")).intValue());
			}
			CommodityQueryVo vo = new CommodityQueryVo();
			vo.setIsAudit(2);
			vo.setStatus(1);
			vo.setMerchantCode(merchantCode);
			Integer approveCount = commodityMapper.queryWaitSaleCommodityCount(vo);
			tips.put("approveCount", approveCount);
			//获取商家待处理工单
			com.yougou.ordercenter.common.Query query = new com.yougou.ordercenter.common.Query();
			query.setPageSize(1);
			QueryTraceSaleVo queryTraceSaleVo = new QueryTraceSaleVo();
			queryTraceSaleVo.setMerchantCode(merchantCode);
			queryTraceSaleVo.setTraceStatus(3);
			queryTraceSaleVo.setUrgencyDegree(-1);
			queryTraceSaleVo.setStartTime(new java.text.SimpleDateFormat("yyyy-MM-dd").format(DateUtils.addDays(new Date(), -30)));
			queryTraceSaleVo.setEndTime(new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			com.yougou.ordercenter.common.PageFinder<TraceSaleQueryResult> dialog_list = asmApiImpl.getTraceSaleQueryResults(queryTraceSaleVo, query);
            tips.put("dialogCount", dialog_list == null ? 0 : dialog_list.getRowCount());
		} catch (Exception e) {
			logger.error("query商品店铺提醒时异常.", e);
		}
		redisTemplate.opsForValue().set(CacheConstant.C_COMMODITY_SHOUYE_TIPS_KEY+":"+merchantCode, tips);
		redisTemplate.expire(CacheConstant.C_COMMODITY_SHOUYE_TIPS_KEY+":"+merchantCode, 5, TimeUnit.MINUTES);
		logger.warn("从数据库获取首页商品相关数量，重新放置缓存中，设置5分钟过期！"); 
		return tips;
	}

	@Override
	public int querySaleCountByroductNo(String productNo) throws Exception {
		return commodityMapper.querySaleCountByroductNo(productNo);
	}

	/** 
	 * 在售商品分批查询导出
	 * @see com.yougou.kaidian.commodity.service.ICommodityService#queryExportCommodityList(com.yougou.kaidian.commodity.model.vo.CommodityQueryVo, int) 
	 */
	@Override
	public List<Commodity> queryExportCommodityList(
			CommodityQueryVo commodityQueryVo, int size) throws Exception {
		//总记录数
    	int count = commodityMapper.queryCommodityCountByExport(commodityQueryVo);
    	List<Commodity> list = new ArrayList<Commodity>();
    	//循环次数
    	int xunSize = (count%size)==0?(count/size):(count/size+1);
    	Query query = new Query(size);
    	for(int i=0;i<xunSize;i++){
    		query.setPage(i+1);
    		list.addAll(commodityMapper.queryExportCommodityList(commodityQueryVo,query));
    	}
    	return list;
	}

	/** 
	 *  在售商品货品条码分批查询导出
	 * @see com.yougou.kaidian.commodity.service.ICommodityService#queryExportProductList(com.yougou.kaidian.commodity.model.vo.CommodityQueryVo, int) 
	 */
	@Override
	public List<ProductExportVo> queryExportProductList(
			CommodityQueryVo commodityQueryVo, int size) {
		//总记录数
    	int count = commodityMapper.queryProductCountByExport(commodityQueryVo);
    	List<ProductExportVo> list = new ArrayList<ProductExportVo>();
    	//循环次数
    	int xunSize = (count%size)==0?(count/size):(count/size+1);
    	Query query = new Query(size);
    	for(int i=0;i<xunSize;i++){
    		query.setPage(i+1);
    		list.addAll(commodityMapper.queryExportProductList(commodityQueryVo,query));
    	}
		return list;
	}

	/** 
	 * 待售商品分批查询导出 
	 * @see com.yougou.kaidian.commodity.service.ICommodityService#exportWaitSaleCommodityList(com.yougou.kaidian.commodity.model.vo.CommodityQueryVo, int) 
	 */
	@Override
	public List<Commodity> exportWaitSaleCommodityList(
			CommodityQueryVo commodityQueryVo, Query query) {
		//总记录数
		int count = commodityMapper.queryWaitSaleCommodityCountByExport(commodityQueryVo);
		List<Commodity> list = new ArrayList<Commodity>();
		int size = query.getPageSize();
		//循环次数
    	int xunSize = (count%size)==0?(count/size):(count/size+1);
    	//RowBounds rowBounds = null;
    	for(int i=0;i<xunSize;i++){
    		//rowBounds = new RowBounds((i*size), size);
    		query = new Query(size);
    		query.setPage(i+1);
    		list.addAll(commodityMapper.exportWaitSaleCommodityList(commodityQueryVo,query));
    	}
		return list;
	}

	/** 
	 * 待售商品分批 导出
	 * @see com.yougou.kaidian.commodity.service.ICommodityService#exportWaitSaleProductList(com.yougou.kaidian.commodity.model.vo.CommodityQueryVo, int) 
	 */
	@Override
	public List<ProductExportVo> exportWaitSaleProductList(
			CommodityQueryVo commodityQueryVo, Query query) {
		//总记录数
    	int count = commodityMapper.queryWaitSaleProductCountByExport(commodityQueryVo);
    	List<ProductExportVo> list = new ArrayList<ProductExportVo>();
    	int size = query.getPageSize();
    	//循环次数
    	int xunSize = (count%size)==0?(count/size):(count/size+1);
    	//RowBounds rowBounds = null;
    	for(int i=0;i<xunSize;i++){
    		//rowBounds = new RowBounds((i*size), size);
    		query = new Query(size);
    		query.setPage(i+1);
    		list.addAll(commodityMapper.exportWaitSaleProductList(commodityQueryVo,query));
    	}
		return list;
	}
	/** 
	 * @see com.yougou.kaidian.commodity.service.ICommodityService#exportWaitSaleCommodityAndProductList(com.yougou.kaidian.commodity.model.vo.CommodityQueryVo, int) 
	 */
	@Override
	public List<CommodityAndProductExportVo> exportWaitSaleCommodityAndProductList(
			CommodityQueryVo commodityQueryVo, Query query) {
		//总记录数
		int count = commodityMapper.queryWaitSaleCommodityAndProductCountByExport(commodityQueryVo,
				commodityQueryVo.getCommodityNoList(),
				commodityQueryVo.getSupplierCodeList(),commodityQueryVo.getStyleNoList(),
				commodityQueryVo.getThirdPartyCodeList());
		List<CommodityAndProductExportVo> list = new ArrayList<CommodityAndProductExportVo>();
		int size = query.getPageSize();
		//循环次数
    	int xunSize = (count%size)==0?(count/size):(count/size+1);
    	//RowBounds rowBounds = null;
    	for(int i=0;i<xunSize;i++){
    		//rowBounds = new RowBounds((i*size), size);
    		query = new Query(size);
    		query.setPage(i+1);
    		list.addAll(commodityMapper.exportWaitSaleCommodityAndProductList(commodityQueryVo,
    				commodityQueryVo.getCommodityNoList(),
    				commodityQueryVo.getSupplierCodeList(),commodityQueryVo.getStyleNoList(),
    				commodityQueryVo.getThirdPartyCodeList(),query));
    	}
		return list;
	}
	
	public int commodityAutoOffShelves(){
		final int pageSize = 500;
		int count = commodityMapper.select30DaysNoUpdateCommodityCount();
		int pageTotal = count%pageSize==0?count/pageSize:count/pageSize+1;
		RowBounds rowBounds = null;
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -30);
		Date curDate = c.getTime();
		for(int i=0;i<pageTotal;i++){
			rowBounds = new RowBounds((i*pageSize), pageSize);
			List<Commodity> list = commodityMapper.select30DaysNoUpdateCommodity(rowBounds);
			
			Map<String,List<String>> resultMap = contertCommodityList2Map(list);
			
			String merchantCode = null;
			List<String> commodityNos = null;
			for (Map.Entry<String, List<String>> entry : resultMap.entrySet()) {
				merchantCode = entry.getKey();
				commodityNos = entry.getValue();
				List<InvAssistVo> invAssists = inventoryForMerchantService.getStocksByMerchantCodeAndCommodityNos(merchantCode,commodityNos);
				for(InvAssistVo vo :invAssists){
					//如果库存为0，并且库存30天没更新
					if(vo.getInventoryQuantity()!=null&&vo.getInventoryQuantity().intValue()==0&&vo.getModityDate().before(curDate)){
						commodityApi.updateCommodityStatusForMerchant(vo.getCommodityNo(),merchantCode, 1);
					}
				}
			}
		}
		return count;
	}
	
	private Map<String,List<String>> contertCommodityList2Map(List<Commodity> list){
		Map<String,List<String>> resultMap = new HashMap<String,List<String>>();
		for(Commodity commodity:list){
			if(null!=resultMap.get(commodity.getMerchantCode())){
				resultMap.get(commodity.getMerchantCode()).add(commodity.getCommodityNo());
			}else{
				List<String> commodityNos = new ArrayList<String>();
				commodityNos.add(commodity.getCommodityNo());
				resultMap.put(commodity.getMerchantCode(), commodityNos);
			}
		}
		return resultMap;
	}
	
	public int deleteCommodity2RecycleAuto(){
		final int pageSize = 500;
		int count = commodityMapper.select60DaysNoUpdateCommodityCount4WaitSale();
		int pageTotal = count%pageSize==0?count/pageSize:count/pageSize+1;
		RowBounds rowBounds = null;
		for(int i=0;i<pageTotal;i++){
			rowBounds = new RowBounds((i*pageSize), pageSize);
			List<Commodity> list = commodityMapper.select60DaysNoUpdateCommodity4WaitSale(rowBounds);
			for(Commodity commodity:list){
				try{
					commodityApi.deleteMerchantCommodity2RecycleByCommodityNo(commodity.getMerchantCode(), commodity.getCommodityNo());
				}catch(Exception e){
					continue;
				}
			}
		}
		return count;
	}
	
	public void updateOnsalePrice(String commodityNo,String merchantCode,String price,String type) throws BusinessException{
		final String type1 = "1";
		final String type2 = "2";
		//先下架
		logger.warn("商家{}修改商品{}的价格为{}下架！",new Object[]{merchantCode,commodityNo,price});
		commodityApi.updateCommodityStatusForMerchant(commodityNo, merchantCode, 1);
		//修改价格
		com.yougou.pc.model.commodityinfo.Commodity commodity = commodityApi.getCommodityByNo(commodityNo);
		if(null==commodity){
			throw new BusinessException("商品不存在");
		}
		if(type1.equals(type)){
			commodity.setSellPrice(Double.parseDouble(price));
		}else if(type2.equals(type)){
			commodity.setMarkPrice(Double.parseDouble(price));
		}else{
			throw new BusinessException("参数错误");
		}
		try{
			String resultStr = commodityApi.saveAllCommodityMsgForMerchant(commodity);
			if(!"SUCCESS".equals(resultStr)){
				throw new BusinessException(resultStr);
			}
			//提交审核
			commodityApi.auditMerchant(commodityNo, merchantCode);
		}catch(Exception e){
			throw new BusinessException(e.getMessage(),e);
		}
		
	}
	
	@Override
	public Map<String,Object> getCountByStyleNoAndMerchantCodeAndColor(String brandNo, String styleNo,
			String merchantCode, String color) {
		return commodityMapper.getCountByStyleNoAndMerchantCodeAndColor(brandNo,styleNo,merchantCode,color);
	}
	@Override
	public String getSupplierCodeByStyleNoAndMerchantCode(String brandNo, String styleNo,
			String merchantCode) {
		return commodityMapper.getSupplierCodeByStyleNoAndMerchantCode(brandNo,styleNo,merchantCode);
	}
	@Override
	public boolean check(String merchantCode, String styleNo, String colorName, 
			String years, String[] sizeNo, List<PropValue> propList) {
		if(propList!=null && propList.size() > 0){
			//按尺码设置价格
			if(commodityMapper.getCommoditySize(merchantCode,styleNo,colorName,years,sizeNo[0],propList)>0){
				return false;
			}
		}else{
			if(commodityMapper.getCommoditySize(merchantCode,styleNo,colorName,years,null,propList)>0){
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String queryAndVerify(String keyword, String merchantCode) {
		//map主要有brand_no,cat_no，cat_structname
		Map<String,Object> jsonmap = new HashMap<String,Object>();
		JsonConfig config = new JsonConfig();
		List<Map<String,Object>> list = commodityMapper.queryCommodityByKeyword(keyword,merchantCode);
		if(list!=null&&list.size()>0){
			//取第一个
			Map<String,Object> map = list.get(0); 
			//按产品要求，只在该商家范围查询
			//Map<String,Object> resultMap = catMapper.queryCategoryListByBrandNoAndCate(merchantCode, 
			//		MapUtils.getString(map, "brand_no"),MapUtils.getString(map, "cat_no"));
			//if(resultMap!=null){
			//已经授权
			List<Category> catelist = this.getAllCategoryByBrandId(merchantCode, MapUtils.getString(map, "brand_no"));
			if (catelist == null || catelist.isEmpty())
				return null;
			config.setJsonPropertyFilter(new PropertyFilter() {
				@Override
				public boolean apply(Object source, String name, Object value) {
					// 获取当前需要序列化的对象的类对象
			        Class<?> clazz = source.getClass();
			        if(clazz==Category.class){
			        	if (name.equals("id") || name.equals("catName") 
								|| name.equals("structName") || name.equals("catLeave") 
								|| name.equals("catNo") || name.equals("parentId")) {
							return false;
						}
						return true;
			        }
			        return false;
				}
			});
			jsonmap.put("cate", catelist);
			jsonmap.put("selcate", MapUtils.getString(map, "brand_no")+";"+MapUtils.getString(map, "cat_structname"));
			jsonmap.put("resultcode", 1);
			//}else{
				//没有授权
			//	jsonmap.put("resultcode", 0);
			//}
		}else{
			//商品不存在了
			jsonmap.put("resultcode", -1);
		}
		JSONArray jsonArray = JSONArray.fromObject(jsonmap, config);
		return jsonArray.toString();
	}

}
