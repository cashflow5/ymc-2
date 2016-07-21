/**
 * 
 */
package com.yougou.kaidian.commodity.component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import com.yougou.bi.api.IBIApi;
import com.yougou.bi.api.SaleTopVo;
import com.yougou.kaidian.commodity.constant.CommodityConstant;
import com.yougou.kaidian.commodity.model.vo.CommoditySubmitResultVo;
import com.yougou.kaidian.commodity.model.vo.CommoditySubmitVo;
import com.yougou.kaidian.commodity.service.ICommodityService;
import com.yougou.kaidian.common.commodity.pojo.Brand;
import com.yougou.kaidian.common.commodity.pojo.Cat;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.common.util.CommonUtil;
import com.yougou.kaidian.framework.settings.CommoditySettings;
import com.yougou.kaidian.framework.util.SessionUtil;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.stock.service.IStockService;
import com.yougou.kaidian.stock.service.IWarehouseService;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.api.ICommodityMerchantApiService;
import com.yougou.pc.model.category.Category;
import com.yougou.pc.model.commodityinfo.Commodity;
import com.yougou.pc.model.commodityinfo.CommodityProp;
import com.yougou.pc.model.product.Product;
import com.yougou.pc.model.prop.PropItem;
import com.yougou.pc.model.prop.PropValue;
import com.yougou.pc.model.sensitive.SensitiveCheckLog;
import com.yougou.pc.model.sensitive.SensitiveWord;
import com.yougou.pc.vo.commodity.CommodityPropVO;
import com.yougou.pc.vo.commodity.CommodityVO;
import com.yougou.wms.wpi.common.exception.WPIBussinessException;
import com.yougou.wms.wpi.inventory.service.IInventoryDomainService;

/**
 * 商品逻辑处理组件
 * 
 * @author huang.tao
 *
 */
@Component
public class CommodityComponent {
	private static final Logger logger = LoggerFactory.getLogger(CommodityComponent.class);
	@Resource
	private CommoditySettings settings;
	@Resource
	private ICommodityService commodityService;
	@Resource
	private ICommodityMerchantApiService commodityApi;
	@Resource
	private ICommodityBaseApiService commodityBaseApiService;
	@Resource
	private IInventoryDomainService inventoryDomainService;
	@Resource
	private IWarehouseService warehouseService;
	@Resource 
	private IStockService stockService;
	@Resource
	private IBIApi biApi;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	/**
	 * 新增或修改商品 公共方法
	 * @param fromTabId 来自于哪个列表tab点击进来的
	 * @param model
	 * @param request
	 */
	public void goAddOrUpdateCommodityCommon(String fromTabId, ModelMap model, HttpServletRequest request) {
		String prefix = "goAddOrUpdateCommodityCommon#-> ";
		//上传图片的数量
		model.addAttribute("ADD_OR_UPDATE_COMMODITY_IMAGE_COUNT", CommodityConstant.ADD_OR_UPDATE_COMMODITY_IMAGE_COUNT);
		//是否入优购仓库，入优购仓
		model.addAttribute("SUPPLIER_IS_INPUT_YOUGOU_WAREHOUSE_IN", CommodityConstant.SUPPLIER_IS_INPUT_YOUGOU_WAREHOUSE_IN);
		//是否入优购仓库，不入优购仓
		model.addAttribute("SUPPLIER_IS_INPUT_YOUGOU_WAREHOUSE_NOT_IN", CommodityConstant.SUPPLIER_IS_INPUT_YOUGOU_WAREHOUSE_NOT_IN);
		//上传的图片数量
		//model.addAttribute("ADD_OR_UPDATE_COMMODITY_IMAGE_COUNT", CommodityConstant.ADD_OR_UPDATE_COMMODITY_IMAGE_COUNT);
		//1000*1000大图的后缀
		model.addAttribute("IMG_NAME_SIZE_1000_1000_SUFFIX", CommodityConstant.IMG_NAME_SIZE_1000_1000_SUFFIX);
		//图片扩展名
		model.addAttribute("IMG_NAME_EXT_TYPE", CommodityConstant.IMG_NAME_EXT_TYPE);
		//商品状态,新建
		model.addAttribute("COMMODITY_STATUS_DRAFT", CommodityConstant.COMMODITY_STATUS_DRAFT);
		//商品状态,审核拒绝
		model.addAttribute("COMMODITY_STATUS_REFUSE", CommodityConstant.COMMODITY_STATUS_REFUSE);
		//查询商品的页面tabId，未提交审核
		model.addAttribute("QUERY_COMMODITY_PAGE_TAB_ID_DRAFT", CommodityConstant.QUERY_COMMODITY_PAGE_TAB_ID_DRAFT);
		//查询商品的页面tabId，待审核
		model.addAttribute("QUERY_COMMODITY_PAGE_TAB_ID_PENDING", CommodityConstant.QUERY_COMMODITY_PAGE_TAB_ID_PENDING);
		//查询商品的页面tabId，审核通过
		model.addAttribute("QUERY_COMMODITY_PAGE_TAB_ID_PASS", CommodityConstant.QUERY_COMMODITY_PAGE_TAB_ID_PASS);
		//查询商品的页面tabId，审核拒绝
		model.addAttribute("QUERY_COMMODITY_PAGE_TAB_ID_REFUSE", CommodityConstant.QUERY_COMMODITY_PAGE_TAB_ID_REFUSE);
		//查询商品的页面tabId，全部商品
		model.addAttribute("QUERY_COMMODITY_PAGE_TAB_ID_ALL", CommodityConstant.QUERY_COMMODITY_PAGE_TAB_ID_ALL);
		
		//商品描述图片合法正则表达式
		model.addAttribute("YOUGOU_VALID_IMAGE_REGEX", settings.yougouValidImageRegex);
		
		fromTabId = CommonUtil.getTrimString(fromTabId);
		if (fromTabId.length() == 0) {
			fromTabId = CommodityConstant.QUERY_COMMODITY_PAGE_TAB_ID_DRAFT;
		}
		logger.info("{} fromTabId: {}" ,prefix, fromTabId);
		model.addAttribute("fromTabId", fromTabId);
		
		//商家编号
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		
		logger.info("{} supplier_code: {}",prefix, merchantCode);
		int isInputYougouWarehouse = CommodityConstant.SUPPLIER_IS_INPUT_YOUGOU_WAREHOUSE_NOT_IN;
		if (SessionUtil.getSessionProperty(request, CommodityConstant.IS_INPUT_YOUGOU_WAREHOUSE_KEY) != null) {
			try {
				isInputYougouWarehouse = Integer.parseInt(SessionUtil.getSessionProperty(request, CommodityConstant.IS_INPUT_YOUGOU_WAREHOUSE_KEY));
			} catch (NumberFormatException e) {
			}
		}
		logger.info("{} isInputYougouWarehouse: {}", prefix, isInputYougouWarehouse);
		model.addAttribute("isInputYougouWarehouse", isInputYougouWarehouse);
		
		//查询品牌、分类
		this.initBrand(model, request);
		
		//当前年份
		int curYear = Calendar.getInstance().get(Calendar.YEAR);
		int startYear = curYear - CommodityConstant.ADD_OR_UPDATE_COMMODITY_YEAR_GROUND;
		int endYear = startYear + CommodityConstant.ADD_OR_UPDATE_COMMODITY_YEAR_LENGTH - 1;
		int[] yearArr = new int[CommodityConstant.ADD_OR_UPDATE_COMMODITY_YEAR_LENGTH];
		for (int i = startYear, j = 0; i <= endYear; i++, j++) {
			yearArr[j] = i;
		}
		model.addAttribute("curYear", curYear);
		model.addAttribute("yearArr", yearArr);
	}
	
	/**
	 * <p>初始化查询条件</p>
	 * <p>包括  [品牌、分类]</p>
	 * 
	 * @param modelMap
	 * @param queryVo
	 */
	public void initCommonProperty(ModelMap modelMap) {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		List<Brand> lstBrand = commodityService.queryBrandList(merchantCode);
		modelMap.put("lstBrand", lstBrand);

		List<Cat> lstCat = commodityService.queryCatList(merchantCode, "");
		modelMap.put("lstCat", lstCat);
	}
	
	public void initBrand(ModelMap modelMap, HttpServletRequest request) {
		List<Brand> lstBrand = commodityService.queryBrandList(YmcThreadLocalHolder.getMerchantCode());
		modelMap.put("lstBrand", lstBrand);
	}
	
	public void fillCommodityVo(CommodityVO vo) {
		if (null == vo) return;
		
		//填充商品默认扩展属性
		if (StringUtils.isNotBlank(vo.getCatId())) {
			logger.info("merchantCode: {} | fill Commodity supplierCode: {} propItem default value start.", vo.getMerchantCode(), vo.getSupplierCode());
			
			List<PropItem> propItemList = commodityApi.getPropMsgByCatId(vo.getCatId());
			CommodityPropVO propVo = this.getDefaultPropValueByName(propItemList, "货品来源", "招商");
			if (null != propVo) {
				vo.getCommodityPropVOList().add(propVo);
				logger.info("merchantCode: {} | fill Commodity supplierCode: {} propItemName: 货品来源 default value is 招商.", vo.getMerchantCode(), vo.getSupplierCode());
			}
			
			logger.info("merchantCode: {} | fill Commodity supplierCode: {} propItem default value end.", vo.getMerchantCode(), vo.getSupplierCode());
		}
	}
	
	public void fillCommodityUpdateVo(Commodity updateVo, Commodity vo) {
		if (null == updateVo) return;
		
		//填充商品默认扩展属性
		Category category = commodityBaseApiService.getCategoryByStructName(vo.getCatStructName());
		if (null != category && StringUtils.isNotBlank(category.getId())) {
			logger.info("merchantCode: {} | fill updateCommodity supplierCode: {} propItem default value start.", updateVo.getMerchantCode(), updateVo.getSupplierCode());
			
			CommodityProp _prop = this.getCommodityPropByName(vo.getCommdoityProps(), "货品来源");
			
			List<PropItem> propItemList = commodityApi.getPropMsgByCatId(category.getId());
			CommodityPropVO propVo = this.getDefaultPropValueByName(propItemList, "货品来源", "招商");
			if (null != propVo) {
				if (_prop == null) {
					_prop = new CommodityProp();
				
					//属性项编号
					_prop.setPropItemNo(propVo.getPropItemNo());
					//属性项名称
					_prop.setPropItemName(propVo.getPropItemName());
					//属性值编号
					_prop.setPropValueNo(propVo.getPropValueNo());
					//属性值名称
					_prop.setPropValueName(propVo.getPropValue());
					updateVo.getCommdoityProps().add(_prop);
					logger.info("merchantCode: {} | fill updateCommodity supplierCode: {} propItemName: 货品来源 default value is 招商.", updateVo.getMerchantCode(), vo.getSupplierCode());
				} else {
					updateVo.getCommdoityProps().add(_prop);
				}
			}
			
			logger.info("merchantCode: {} | fill updateCommodity supplierCode: {} propItem default value end.", updateVo.getMerchantCode(), updateVo.getSupplierCode());
		}
	}
	
	/**
	 * 清除被删除货品的库存
	 * @param submitVo 新增或修改商品 提交时 用的vo
	 * @param resultVo 新增或修改商品 提交 结果 vo
	 * @param inventoryDomainService 预占及库存接口
	 * @param commodity 未修改前的商品
	 * @return true|false
	 */
	public boolean clearDeletedProdInventory(CommoditySubmitVo submitVo, CommoditySubmitResultVo resultVo, 
			com.yougou.pc.model.commodityinfo.Commodity commodity) {
		String prefix = "clearDeletedProdInventory#-> ";
		//修改后的货品编号
		List<String> prodNoListNew = new ArrayList<String>();
		//原来的货品编号
		List<String> prodNoListOriginal = new ArrayList<String>();
		
		List<Map<String, String>> prodIdList = submitVo.getProdIdList();
		//获取修改后的货品编号
		Map<String, String> prodIdInfo = null;
		for (int i = 0, len = prodIdList.size(); i < len; i++) {
			prodIdInfo = prodIdList.get(i);
			String productNo = prodIdInfo.get("productNo");
			prodNoListNew.add(productNo);
		}
		//修改后的货品排序
		Collections.sort(prodNoListNew);
		
		List<Product> productList = commodity.getProducts();
		//获取原来的货品编号
		Product product = null;
		for (int i = 0, len = productList.size(); i < len; i++) {
			product = productList.get(i);
			String productNo = product.getProductNo();
			prodNoListOriginal.add(productNo);
		}
		//原来的货品排序
		Collections.sort(prodNoListOriginal);
		//仓库编码
		String wareHouseCode = submitVo.getWareHouseCode();
		logger.info("{} wareHouseCode: {}", prefix, wareHouseCode);
		//获取被删除的货品编号
		List<String> deletedProdNoList = getDeletedProdNoForUpdate(prodNoListNew, prodNoListOriginal);
		//Map<货品编号，更新时间>
		Map<String, Date> mapTmp = null;
		for (String prodNo : deletedProdNoList) {
			try {
				mapTmp = inventoryDomainService.updateInventoryForMerchant(prodNo, wareHouseCode, 0, NumberUtils.INTEGER_ZERO);
			} catch (WPIBussinessException e) {
				logger.error("{} returnCode: {}, returnMessage. ", new Object[]{prefix, e.getReturnCode(), e.getReturnMessage()});
			}
			
			if (MapUtils.isEmpty(mapTmp)) {
				logger.info("{} 清除货品：{}库存失败.", new Object[]{prefix, prodNo});
				resultVo.setErrorMsg("保存库存失败");
				return false;
			} else {
				logger.info("{} 清除货品库存, result: {}.", new Object[]{prefix, mapTmp.toString()});
			}
		}
		return true;
	}
	
	/**
	 * 获取被删除的货品编号
	 * @param prodNoListNew 修改后的货品编号
	 * @param prodNoListOriginal 修改前的货品编号
	 * @return 
	 */
	private List<String> getDeletedProdNoForUpdate(
			List<String> prodNoListNew, List<String> prodNoListOriginal) {
		List<String> deletedProdNos = new ArrayList<String>();
		for (int i = 0, len = prodNoListOriginal.size(); i < len; i++) {
			//原货品编号
			String prodNoOriginal = prodNoListOriginal.get(i);
			boolean isExist = false;
			for (int j = 0, len1 = prodNoListNew.size(); j < len1; j++) {
				//修改后货品编号
				String prodNoNew = prodNoListNew.get(j);
				if (prodNoOriginal.equals(prodNoNew)) {
					isExist = true;
					break;
				}
			}
			if (!isExist) {
				deletedProdNos.add(prodNoOriginal);
			}
		}
		return deletedProdNos;
	}
	
	/**
	 * <p>更新库存</p>
	 * @param submitVo
	 * @param resultVo
	 * @param productCodes <code>Map&ltthirdPartyCode, ProductNo&gt</code> 
	 * @param warehouseCacheService
	 * @param commodityApi
	 * @param inventoryDomainService
	 * @return
	 * @throws Exception
	 */
	public String updateStockForAdd(CommoditySubmitVo submitVo, CommoditySubmitResultVo resultVo,
			Map<String, String> productCodes) throws Exception {
		if (MapUtils.isEmpty(productCodes)) {
			String prodStr = commodityApi.getProductNoBySupplier(submitVo.getSupplierCode(), submitVo.getMerchantCode());
			logger.info("updateStockCommon#-> prodStr: {}" , prodStr);
			if (prodStr.indexOf(CommodityConstant.SUCCESS) == -1) {
				return "设置库存信息时，无法获取货品";
			}
			JSONObject prodResultJsonObj = JSONObject.fromObject(prodStr);
			JSONObject prodJsonObj = prodResultJsonObj.getJSONObject(CommodityConstant.SUCCESS);
			if (null == productCodes) productCodes = new HashMap<String, String>();
			for (Object thirdPartyCodeObj : prodJsonObj.keySet()) {
				String thirdPartyCode = thirdPartyCodeObj.toString();
				String productNo = prodJsonObj.getString(thirdPartyCode);
				productCodes.put(thirdPartyCode, productNo);
			}
		}
		
		return updateStockCommon(submitVo, resultVo, productCodes);
	}
	
	/**
	 * 获取审核状态
	 * @param isAudit DB中审核状态
	 * @param commodityStatus 商品状态
	 * @return 
	 */
	public String getAuditStatus(Integer isAudit, Integer commodityStatus) {
		String auditStatus = "";
		//审核通过
		if (CommodityConstant.COMMODITY_IS_AUDIT_PASS == isAudit) {
			auditStatus = CommodityConstant.AUDIT_STATUS_PASS;
		//新建
		} else if (CommodityConstant.COMMODITY_STATUS_DRAFT == commodityStatus) {
			auditStatus = CommodityConstant.AUDIT_STATUS_DRAFT;
		//提交审核
		} else if (CommodityConstant.COMMODITY_STATUS_PENDING == commodityStatus) {
			auditStatus = CommodityConstant.AUDIT_STATUS_PENDING;
		//审核拒绝
		} else if (CommodityConstant.COMMODITY_STATUS_REFUSE == commodityStatus) {
			auditStatus = CommodityConstant.AUDIT_STATUS_REFUSE;
		}
		return auditStatus;
	}
	
	private String updateStockCommon(CommoditySubmitVo submitVo, CommoditySubmitResultVo resultVo, 
			Map<String, String> productCodes) throws Exception {
		String prefix = "updateStockCommonNew#-> ";
		
		if (MapUtils.isEmpty(productCodes)) {
			return "设置库存信息时，无法获取货品信息";
		}
		
		String wareHouseCode = warehouseService.queryWarehouseCodeByMerchantCode(submitVo.getMerchantCode());
		
		submitVo.setWareHouseCode(wareHouseCode);
		//获取第三方条码/库存 map
		Map<String, Integer> thirdPartyCodeStockMap = getThirdPartyCodeStockMap(submitVo);
		//Map<货品编号，更新时间>
		Map<String, Object> mapTmp = null;
		for (String thirdPartyCode : productCodes.keySet()) {
			String productNo = productCodes.get(thirdPartyCode);
			Integer stockNum = thirdPartyCodeStockMap.get(thirdPartyCode);
			logger.info("{} thirdPartyCode:{}, stockNum: {}", new Object[]{prefix , thirdPartyCode , stockNum});
			stockNum = stockNum == null ? 0 : stockNum;
			try {
				//mapTmp = inventoryDomainService.updateInventoryForMerchant(productNo, wareHouseCode, stockNum, NumberUtils.INTEGER_ZERO);
				mapTmp = stockService.updateInventoryForMerchant(submitVo.getMerchantCode(), wareHouseCode, productNo, stockNum);
			} catch (WPIBussinessException e) {
				logger.error("{},{} update inventory error", new Object[]{prefix , productNo, e});
				//e.printStackTrace();
			}
			if (mapTmp.containsKey(null)) {
				return String.valueOf(mapTmp.get(null));
			}
			logger.info("{}:{}",prefix , mapTmp.toString());
		}
		return null;
	}
	
	/**
	 * 获取第三方条码/库存 map
	 * @param submitVo
	 * @return
	 */
	private Map<String, Integer> getThirdPartyCodeStockMap(CommoditySubmitVo submitVo) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		String[] thirdPartyCodes = submitVo.getThirdPartyCode();
		Integer[] stocks = submitVo.getStockInt();
		for (int i = 0, len = thirdPartyCodes.length; i < len; i++) {
			map.put(thirdPartyCodes[i], stocks[i]);
		}
		return map;
	}
	
	/**
	 * 通过默认值获取属性对象
	 * 
	 * @param propItemList 分类关联的属性对象
	 * @param propItemName 属性名称
	 * @param defaultValue 默认属性值
	 * @return Object
	 */
	private CommodityPropVO getDefaultPropValueByName(List<PropItem> propItemList, String propItemName, String defaultValue) {
		CommodityPropVO propVo = null;
		
		if (CollectionUtils.isNotEmpty(propItemList)) {
			for(PropItem propItem : propItemList) {
				if(propItemName.equals(propItem.getPropItemName())) {
					PropValue val = null;
					for (PropValue vals : propItem.getPropValues()) {
						if (defaultValue.equals(vals.getPropValueName())) {
							val = vals;
						}
					}
					if (null == val) continue;
					
					propVo = new CommodityPropVO();
					propVo.setPropItemNo(propItem.getPropItemNo());
					propVo.setPropItemName(propItem.getPropItemName());
					propVo.setPropValueNo(val.getPropValueNo());
					propVo.setPropValue(val.getPropValueName());
				}
			}
		}
		
		return propVo;
	}
	
	private CommodityProp getCommodityPropByName(List<CommodityProp> props, String propItemName) {
		CommodityProp prop = null;
		
		if (CollectionUtils.isNotEmpty(props)) {
			for (CommodityProp commodityProp : props) {
				if (propItemName.equals(commodityProp.getPropItemName())) {
					prop = commodityProp;
					break;
				}
			}
		}
		
		return prop;
	}
	
	/**
	 * checkSensitiveWord:检测是否包括敏感词 
	 * @param content 待检测内容
	 * @param separator 敏感词分隔符，默认英文逗号
	 * @author li.n1 
	 * @return 返回含有的敏感词，否则返回空字符串
	 * @since JDK 1.6 
	 * @date 2015-9-14 下午4:49:52
	 */
	public String checkSensitiveWord(String separator, String content){
		String result = commodityApi.checkSensitiveWord(content);
		
		return "SUCCESS".equalsIgnoreCase(result) ? "" : 
			StringUtils.isNotBlank(separator) ? result.replaceAll(",", separator) : result;
	}
	
	/**
	 * insertSensitiveWordCheckLog:批量增加敏感词操作日志 
	 * @param sensitiveCheckLog 日志内容
	 * @author li.n1 
	 * @return true 插入成功   false 插入失败
	 * @since JDK 1.6 
	 * @date 2015-9-14 下午4:52:14
	 */
	public boolean insertSensitiveWordCheckLogByBatch(List<SensitiveCheckLog> sensitiveCheckLog){
		//成功返回SUCCESS否则返回ERROR
		String result = commodityApi.insertSensitiveWordCheckLog(sensitiveCheckLog);
		if(result.toLowerCase().equals("success")){
			return true;
		}
		return false;
	}
	
	/**
	 * insertSensitiveWordCheckLog:单条增加敏感词操作日志 
	 * @param sensitiveCheckLog 日志内容
	 * @author li.n1 
	 * @return true 插入成功   false 插入失败
	 * @since JDK 1.6 
	 * @date 2015-9-14 下午4:52:14
	 */
	public boolean insertSensitiveWordCheckLogByOne(SensitiveCheckLog sensitiveCheckLog){
		sensitiveCheckLog.setOperateTime(new Date());
		sensitiveCheckLog.setSource(SensitiveCheckLog.Source.MERCHANT.getValue());
		List<SensitiveCheckLog> logs = new ArrayList<SensitiveCheckLog>();
		logs.add(sensitiveCheckLog);
		return this.insertSensitiveWordCheckLogByBatch(logs);
	}
	
	/**
	 * getAllSensitiveWords:获取所有的敏感词
	 * @author li.n1 
	 * @return 
	 * @since JDK 1.6 
	 * @date 2015-9-14 下午4:56:20
	 */
	public List<SensitiveWord> getAllSensitiveWords(){
		return commodityApi.getAllSensitiveWords();
	}
	
	@SuppressWarnings("unchecked")
	public  Map<String, Integer> querySaleNumForBIByCommodityNos(
			List<String> commodityNos, String merchantCode) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		SaleTopVo saleTopVo = new SaleTopVo();
		saleTopVo.setMerchantCode(merchantCode);
		//分组查询
		int group = 1, _count = 0;
		List<String> commodityNoList = new ArrayList<String>(0);
		Map<String, Integer> _saleNums = null;
		for(int i = 0; i < commodityNos.size(); i++) {
			if (MapUtils.isEmpty(_saleNums)) {
				try {
					_saleNums = (Map<String, Integer>) this.redisTemplate.opsForHash().get(CacheConstant.C_COMMODITY_SALENUM_KEY, merchantCode);
				} catch (Exception e1) {
					logger.error("从缓存获取商品销量数据异常。", e1);
				}
			}
			// 判断缓存是否能获取到销售数据
			if (MapUtils.isNotEmpty(_saleNums) && _saleNums.containsKey(commodityNos.get(i))) {
				map.put(commodityNos.get(i), _saleNums.get(commodityNos.get(i)));
				continue;
			} 
			_count++;
			commodityNoList.add(commodityNos.get(i));
			
			//每一百条为单位提交
			if (((_count + 1) == 30 * group) || (i == commodityNos.size() - 1)) {
				try {
					saleTopVo.setCommodityNoList(commodityNoList);
					Map<String, Integer> _sales = biApi.getSaleMapForMerchant(saleTopVo);
					map.putAll(_sales);
					//销量数据放入缓存
					if (MapUtils.isEmpty(_saleNums)) _saleNums = new HashMap<String, Integer>();
					_saleNums.putAll(_sales);
					this.redisTemplate.opsForHash().put(CacheConstant.C_COMMODITY_SALENUM_KEY, merchantCode, _saleNums);
					this.redisTemplate.expire(CacheConstant.C_COMMODITY_SALENUM_KEY, 2, TimeUnit.HOURS);
				} catch (Exception e) {
					logger.error("调用Bi查询销售数量接口异常。", e);
				}
				group++;
				commodityNoList = new ArrayList<String>(0);
			}
		}
		return map;
	}
}
