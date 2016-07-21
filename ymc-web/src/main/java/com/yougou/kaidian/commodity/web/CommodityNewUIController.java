package com.yougou.kaidian.commodity.web;  

import java.io.InputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.net.util.Base64;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import com.yougou.fss.api.IFSSYmcApiService;
import com.yougou.kaidian.commodity.component.CommodityComponent;
import com.yougou.kaidian.commodity.constant.CommodityConstant;
import com.yougou.kaidian.commodity.model.vo.CmsCommodityProp;
import com.yougou.kaidian.commodity.model.vo.CommoditySubmitNewVo;
import com.yougou.kaidian.commodity.model.vo.CommoditySubmitResultVo;
import com.yougou.kaidian.commodity.model.vo.CommoditySubmitVo;
import com.yougou.kaidian.commodity.model.vo.ErrorVo;
import com.yougou.kaidian.commodity.model.vo.SensitiveWordVo;
import com.yougou.kaidian.commodity.service.ICommodityExtendService;
import com.yougou.kaidian.commodity.service.ICommodityService;
import com.yougou.kaidian.commodity.service.IImageService;
import com.yougou.kaidian.commodity.service.IMerchantCommodityService;
import com.yougou.kaidian.commodity.util.CommodityUtilNew;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.commodity.util.CommodityPicIndexer;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.common.util.CommonUtil;
import com.yougou.kaidian.common.util.HttpUtil;
import com.yougou.kaidian.framework.constant.Constant;
import com.yougou.kaidian.framework.settings.CommoditySettings;
import com.yougou.kaidian.framework.util.SessionUtil;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.taobao.service.IItemTemplateService;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.api.ICommodityMerchantApiService;
import com.yougou.pc.model.brand.Brand;
import com.yougou.pc.model.category.Category;
import com.yougou.pc.model.commodityinfo.Commodity;
import com.yougou.pc.model.merchant.MerchantImportInfo;
import com.yougou.pc.model.product.Product;
import com.yougou.pc.model.prop.PropItem;
import com.yougou.pc.model.prop.PropValue;
import com.yougou.pc.model.sensitive.SensitiveCheckLog;
import com.yougou.pc.vo.commodity.CommodityPropVO;
import com.yougou.pc.vo.commodity.CommodityVO;
import com.yougou.pc.vo.commodity.ProductVO;
import com.yougou.wms.wpi.common.exception.WPIBussinessException;
import com.yougou.wms.wpi.inventory.domain.vo.InventoryAssistVo;
import com.yougou.wms.wpi.inventory.service.IInventoryForMerchantService;

/**
 * ClassName: CommodityNewUIController
 * Desc: 针对发布商品新UI更换
 * date: 2015-4-23 下午4:02:21
 * @author li.n1 
 * @since JDK 1.6
 */
@Controller
@RequestMapping("/commodity")
public class CommodityNewUIController {
	private static final Logger logger = LoggerFactory.getLogger(CommodityNewUIController.class);
	@Autowired
	private CommodityComponent commodityComponent;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private CommoditySettings settings;
	@Resource
	private IImageService imageService;
	@Autowired
	private IFSSYmcApiService fssYmcApiService;
	@Resource
	private ICommodityMerchantApiService commodityApi;
	@Autowired
	private ICommodityService commodityService;
	@Autowired
	private IMerchantCommodityService merchantCommodityService;
	@Autowired
	private IItemTemplateService itemTemplateService;
	private static final String PRESTR = "merchant.recentedBrdCatg:";
	@Resource
	private ICommodityBaseApiService commodityBaseApiService;
	@Resource
	private ICommodityMerchantApiService commodityMerchantApiService;
	@Resource
	private ICommodityExtendService commodityExtendService;
	@Resource
	private IInventoryForMerchantService inventoryForMerchantService;
	
	//判断是否按尺码修改价格
	//深圳测试4s932
	//正式线上4y644
	@Value("#{configProperties['priceBySizeProp']}")
	private String priceBySizePropNo = "4y644";
	final static String DEFAULT_URL = "javascript:void(0)"; 
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}
	
	
	/**
	 * preAddCommodity:第2步填写商品属性与基本资料
	 * @author li.n1 
	 * @param fromTabId
	 * @param model
	 * @param request
	 * @param submitVo
	 * @return 
	 * @since JDK 1.6
	 */
	@RequestMapping("/preAddCommodityUI")
	public String preAddCommodity(String fromTabId, ModelMap model,
			HttpServletRequest request, CommoditySubmitNewVo submitVo,String templateId) {
		if(StringUtils.isNotBlank(templateId)){
			model.put("templateId", templateId);
		}
		if(StringUtils.isNotBlank(submitVo.getCommodityNo())){
			return "forward:/commodity/preUpdateCommodity.sc";
		}
		// 商家编号
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		storeCurrentBrandAndCatg(merchantCode, submitVo);
		model.put("submitVo", submitVo);
		model.put("merchantCode", merchantCode);
		// 新增或修改商品 公共方法
		commodityComponent.goAddOrUpdateCommodityCommon(fromTabId, model, request);
		logger.warn("调用货品接口ICommodityBaseApiService.getPropValueListByCategoryIdAndItemNo，入参（{},{}）",
				new Object[]{submitVo.getCatId(),priceBySizePropNo});
		List<PropValue> propList = commodityBaseApiService.getPropValueListByCategoryIdAndItemNo(
				submitVo.getCatId(),priceBySizePropNo);
		//有按尺码修改价格属性
		if(propList!=null&&propList.size()>0){
			model.put("isSizePrice", 1);
		}
		model.put("pageSourceId", 0);
		model.put("auditStatus", "");
		model.put("tagTab", "goods");
		return "/manage_publish/commodity/add_commodity_newui";
	}
	
	
	/**
	 * 跳转到 修改商品的页面
	 * @param commodityNo 商品编号
	 * @param fromTabId 来自于哪个列表tab点击进来的
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/preUpdateCommodity")
	public String goUpdateCommodity(String fromTabId, String commodityStatus,
			ModelMap model, HttpServletRequest request,CommoditySubmitNewVo submitVo,
			String isSensitive) {
		//新增或修改商品 公共方法
		commodityComponent.goAddOrUpdateCommodityCommon(fromTabId, model, request);
		//商品编号
		submitVo.setCommodityNo(CommonUtil.getTrimString(submitVo.getCommodityNo()));
		model.addAttribute("commodityNo", submitVo.getCommodityNo());
		logger.info("goUpdateCommodityNewui#-> commodityNo: {}" , submitVo.getCommodityNo());
		com.yougou.pc.model.commodityinfo.Commodity commodity = commodityApi.getCommodityByNo(submitVo.getCommodityNo());
		Category category =  commodityBaseApiService.getCategoryByNo(commodity.getCatNo());
		Brand brand = commodityBaseApiService.getBrandByNo(commodity.getBrandNo());
		//保存修改前的审核状态
		model.put("auditStatus", "13".equals(commodityStatus) ? "2" : "11");
		model.put("commodity", commodity);
		model.put("category", category);
		if(StringUtils.isBlank(submitVo.getCatId())){
			submitVo.setCatId(category.getId());
		}
		if(StringUtils.isBlank(submitVo.getBrandName())){
			submitVo.setBrandName(commodity.getBrandName());
		}
		if(StringUtils.isBlank(submitVo.getBrandNo())){
			submitVo.setBrandNo(commodity.getBrandNo());
		}
		if(StringUtils.isBlank(submitVo.getBrandId())){
			submitVo.setBrandId(brand.getId());
		}
		if(StringUtils.isBlank(submitVo.getRootCatName())){
			submitVo.setRootCatName(category.getStructCatName().split("-")[0]);
		}
		if(StringUtils.isBlank(submitVo.getSecondCatName())){
			submitVo.setSecondCatName(category.getStructCatName().split("-")[1]);
		}
		if(StringUtils.isBlank(submitVo.getCatName())){
			submitVo.setCatName(commodity.getCatName());
		}
		if(StringUtils.isBlank(submitVo.getCatStructName())){
			submitVo.setCatStructName(commodity.getCatStructName());
		}
		if(StringUtils.isBlank(submitVo.getCatNo())){
			submitVo.setCatNo(commodity.getCatNo());
		}
		//图片域名
		model.addAttribute("commodityPreviewDomain", settings.getCommodityPreviewDomain());
		logger.info("goUpdateCommodityNewui#-> commodityPreviewDomain: {}" , settings.getCommodityPreviewDomain());
		model.put("pageSourceId", 1);
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		model.put("merchantCode", merchantCode);
		List<PropValue> propList = commodityBaseApiService.getPropValueListByCategoryIdAndItemNo(
				submitVo.getCatId(),priceBySizePropNo);
		//有按尺码修改价格属性
		if(propList!=null&&propList.size()>0){
			model.put("isSizePrice", 1);
		}
		model.put("submitVo",submitVo);
		model.put("isSensitive", isSensitive);
		return "/manage_publish/commodity/update_commodity_newui";
	}
	
	/**
	 * 通过 商品编号 获取
	 * @param commodityNo 商品编号
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/loadCommodityByNo")
	public String getCommodityByNo(String commodityNo, 
			HttpServletRequest request, ModelMap model) throws Exception {
		final String IS_SUCCESS_KEY = "isSuccess";
		final String ERROR_MSG_KEY = "errorMsg";
		final String COMMODITY_KEY = "commodity";
		final String IMAGE_CACHE = "imageCache";
		
		com.alibaba.fastjson.JSONObject jsonObj = new com.alibaba.fastjson.JSONObject();
		jsonObj.put(IS_SUCCESS_KEY, "false");
		jsonObj.put(ERROR_MSG_KEY, "该件商品不存在");
		
		commodityNo = CommonUtil.getTrimString(commodityNo);
		if (commodityNo.length() == 0) return jsonObj.toString();
		
		com.yougou.pc.model.commodityinfo.Commodity commodity = commodityApi.getCommodityByNo(commodityNo);
		if (commodity == null) jsonObj.toString();
		
		//图片缓存
		Object message = this.redisTemplate.opsForHash().get(CacheConstant.C_IMAGE_MASTER_JMS_KEY, commodityNo);
		if (message != null) jsonObj.put(IMAGE_CACHE, message);
		
		//获取货品的销售状态
		
		List<Product> products=commodity.getProducts();
		int count=0;
		if(products!=null&&products.size()>0){
			for(Product product:products){
				count=commodityService.querySaleCountByroductNo(product.getProductNo());
				if(count>0){
					product.setSellStatus(2);
				}
			}
		}
		jsonObj.put(IS_SUCCESS_KEY, "true");
		jsonObj.put(COMMODITY_KEY, commodity);
		return jsonObj.toString();
	}
	
	/**
	 * 添加商品
	 * @param submitVo 新增或修改商品 提交时 用的vo
	 * @param multiRequest 
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/saveCommodity",method = RequestMethod.POST)
	public String addCommodity(
			CommoditySubmitNewVo submitVo,
			DefaultMultipartHttpServletRequest multiRequest,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<ErrorVo> errorList = new ArrayList<ErrorVo>();
		String merchantCode=YmcThreadLocalHolder.getMerchantCode();
		CommoditySubmitResultVo resultVo = new CommoditySubmitResultVo();
		//1、商家名称、款号，存redis，防止瞬时提交,2秒内
		String str = forbiddenInstantSubmit(merchantCode,submitVo.getStyleNo(),resultVo);
		if(str!=null){
			return str;
		}
		//2、是否按尺码设置价格分类
		List<PropValue> propList = commodityBaseApiService.getPropValueListByCategoryIdAndItemNo(submitVo.getCatId(),priceBySizePropNo);
		
		//3、初始化与校验商品信息
		str = validateAddCommodity(request,submitVo,errorList,propList,response,resultVo);
		if(str!=null){
			if("previewFail".equals(str)){
				return null;
			}
			return str;
		}
		
		try {
			//4、保存前封装CommodityVO
			List<CommodityVO> voList = encapsulateCommodityVo(propList,submitVo,merchantCode);
			if(voList!=null&&voList.size()>0){
				//如果为预览
				if (CommodityConstant.SUBMIT_COMMODITY_IS_PREVIEW_TRUE.equals(
						submitVo.getIsPreview())) {
					request.setAttribute(CommodityConstant.COMMODITY_PREVIEW_COMMODITYVO_KEY, voList.get(0));
					request.setAttribute(CommodityConstant.COMMODITY_PREVIEW_COMMODITY_SUBMIT_VO_KEY, submitVo);
					request.getRequestDispatcher("/commodity/preview.sc").forward(request, response);
					return null;
				}
				if(propList!=null&&propList.size()>0){
					//按尺码设置价格
					return saveBySizeCommodity(voList,errorList,resultVo,submitVo);
				}else{
					//非按尺码设置价格保存
					return saveCommodity(voList,errorList,resultVo,submitVo);
				}
			}
		} catch (RuntimeException e) {
			logger.error("添加商品运行时异常:",e);
			if (e.getClass().getName().equals("java.lang.RuntimeException")) {
				resultVo.setErrorMsg(e.getMessage());
			} else {
				resultVo.setErrorMsg("网络超时,请刷新后重新提交");
			}
			return JSONObject.fromObject(resultVo).toString();
		} catch (Exception e) {
			logger.error("添加商品产生异常:",e);
			resultVo.setErrorMsg("网络超时,请刷新后重新提交");
			return JSONObject.fromObject(resultVo).toString();
		}
		return JSONObject.fromObject(resultVo).toString();
	}
	
	
	/**
	 * 预览
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/preview")
	public void previewImportingCommodity(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			CommodityVO commodityVo = (CommodityVO) request.getAttribute(CommodityConstant.COMMODITY_PREVIEW_COMMODITYVO_KEY);
			CommoditySubmitNewVo commoditySubmitVo = (CommoditySubmitNewVo) request.getAttribute(CommodityConstant.COMMODITY_PREVIEW_COMMODITY_SUBMIT_VO_KEY);
			if (commodityVo == null) {
				throw new NullPointerException("preview commodity error, commodityVo is null.");
			}
			if (commoditySubmitVo == null) {
				throw new NullPointerException("preview commodity error, commoditySubmitVo is null.");
			}
			HttpGet httpGet = null;
			HttpPost httpPost = null;
			if (StringUtils.isNotBlank(commodityVo.getCommodityNo()) && commodityMerchantApiService.getCommodityByNo(commodityVo.getCommodityNo()) != null) {
				httpGet = new HttpGet(MessageFormat.format("{0}?commodityNo={1}", settings.commodityPreviewPersistentUrl, commodityVo.getCommodityNo()));
			} else {
				Map<String, Object> priviewVoMap = new HashMap<String, Object>();
				Category category =  commodityBaseApiService.getCategoryByNo(commodityVo.getCatNo());
				// 基础属性
				priviewVoMap.put("brandName", commodityVo.getBrandName());
				priviewVoMap.put("brandEnglishName", commodityVo.getBrandSpeelingName());
				priviewVoMap.put("styleNo", commodityVo.getStyleNo());
				priviewVoMap.put("specValueName", commodityVo.getSpecName());
				priviewVoMap.put("prodName", commodityVo.getCommodityName());
				priviewVoMap.put("breadcrumb", new String[] { category.getStructCatName().split("-")[0], 
						category.getStructCatName().split("-")[1], commodityVo.getCatName() });
				priviewVoMap.put("publicPrice", commodityVo.getPublicPrice());
				priviewVoMap.put("salePrice", commodityVo.getSalePrice());
				priviewVoMap.put("prodNo", "12345678");
				priviewVoMap.put("prodId", UUID.randomUUID().toString().replace("-", ""));
				priviewVoMap.put("orderDistributionSide", 1);
				priviewVoMap.put("prodPropDesc", commodityVo.getProdDesc());
				
				// 颜色
				List<Map<String,String>> proColorlist = new ArrayList<Map<String,String>>();
				Map<String,String> colorMap = new HashMap<String,String>();
				colorMap.put("src", "");
				colorMap.put("midpic", "");
				colorMap.put("data_name", commodityVo.getSpecName());
				colorMap.put("data_value", commodityVo.getSpecNo());
				proColorlist.add(colorMap);
				priviewVoMap.put("proColor", proColorlist);
				
				// 尺码
				Set<String> proSizeSet = new HashSet<String>();
				for (ProductVO productVO : commodityVo.getProductMsgList()) {
					proSizeSet.add(productVO.getSizeName());
				}
				priviewVoMap.put("proSizeList", proSizeSet);
				
				// 商品的图片
				// 1、picBigUrl  中尺寸图  
				// 2、picLargeUrl  大尺寸图
				// 3、picSmallUrl  小图
				List<Map<String, Object>> proPicsList = new ArrayList<Map<String,Object>>();
				String[] imgFiles = commoditySubmitVo.getImgFileId();
				for (int i = 0; i < imgFiles.length; i++) {
					Map<String, Object> proPicsMap = new HashMap<String, Object>();
					StringBuilder sb = new StringBuilder("");
					if (!"-1".equals(imgFiles[i])) {
						if(imgFiles[i].indexOf("http://")!=-1){
							sb.append(imgFiles[i]);
						}else{
							sb.append(settings.getCommodityPreviewDomain())
							.append(MessageFormat.format(settings.imageFtpPreTempSpace, commodityVo.getMerchantCode()))
							.append(imgFiles[i]).append(".jpg");
						}
						proPicsMap.put("picBigUrl", sb.toString());
						proPicsMap.put("picLargeUrl", sb.toString());
						proPicsMap.put("picSmallUrl", sb.toString());
						proPicsList.add(proPicsMap);
					}
				}
				priviewVoMap.put("defaultGroupPic", proPicsList);
				priviewVoMap.put("showSecoundImageCat", "no");
				
				// 商品扩展属性
				List<CmsCommodityProp> commodityPropList = new ArrayList<CmsCommodityProp>();
				CmsCommodityProp prop = null;
				for (int i = 0, j = commodityVo.getCommodityPropVOList().size(); i < j;) {
					CommodityPropVO commodityPropVO = commodityVo.getCommodityPropVOList().get(i++);
					prop = new CmsCommodityProp();
					prop.setPropItemName(commodityPropVO.getPropItemName());
					prop.setPropValueName(commodityPropVO.getPropValue());
					commodityPropList.add(prop);
				}
				priviewVoMap.put("commdoityPropList", commodityPropList);
				
				String json = JSONObject.fromObject(priviewVoMap).toString();
				httpPost = new HttpPost(settings.commodityPreviewMemoryUrl);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("commodityVo", Base64.encodeBase64URLSafeString(json.getBytes())));
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
				httpPost.setEntity(entity);
			}
			
			InputStream is = HttpUtil.executeMethod(httpGet == null ? httpPost : httpGet);
			response.getOutputStream().write(IOUtils.toByteArray(is));
			response.getOutputStream().flush();
		} catch (Exception ex) {
			IOUtils.write("预览商品失败,请联系优购系统管理员!", response.getOutputStream());
			response.getOutputStream().flush();
		} finally {
			IOUtils.closeQuietly(response.getOutputStream());
		}
	}
	
	
	/**
	 * updateCommodity:修改商品提交
	 * @author li.n1 
	 * @param submitVo
	 * @param multiRequest
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 * @since JDK 1.6 
	 * @date 2015-9-17 上午9:31:11
	 */
	@ResponseBody
	@RequestMapping(value="/updateCommodityNewui",method = RequestMethod.POST)
	public String updateCommodity(
			CommoditySubmitNewVo submitVo,
			DefaultMultipartHttpServletRequest multiRequest,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String merchantCode=YmcThreadLocalHolder.getMerchantCode();
		CommoditySubmitResultVo resultVo = new CommoditySubmitResultVo();
		//1、商家名称、款号，存redis，防止瞬时提交,2秒内
		String str = forbiddenInstantSubmit(merchantCode,submitVo.getStyleNo(),resultVo);
		if(str!=null){
			return str;
		}
		List<PropValue> propList = commodityBaseApiService.getPropValueListByCategoryIdAndItemNo(
				submitVo.getCatId(),priceBySizePropNo);
		List<ErrorVo> errorList = new ArrayList<ErrorVo>();
		if(StringUtils.isEmpty(submitVo.getCommodityNo())){
			resultVo.setErrorMsg("商品编号不能为空");
			return JSONObject.fromObject(resultVo).toString();
		}
		com.yougou.pc.model.commodityinfo.Commodity commodity = commodityApi.getCommodityByNo(submitVo.getCommodityNo());
		if (commodity == null) {
			resultVo.setErrorMsg("该商品不存在");
			return JSONObject.fromObject(resultVo).toString();
		}
		//新增或修改商品信息，初始化用户信息
		CommodityUtilNew.initMerchantUserInfoForAddOrUpdateNew(request, submitVo);
		 
		//设置商品基础配置
		submitVo.setSettings(settings);
		
		submitVo.validateUpdateCommodityForm(errorList, commodity, 
				merchantCommodityService.getCommodityCatNamesByCatStructName(
						submitVo.getCatStructName()),propList);
		CommoditySubmitVo subVo = CommodityUtilNew.buildOldCommodityVO(submitVo);
		//外链处理
		errorList=imageService.verifyCommodityProdDesc(subVo,errorList);
		submitVo.setProdDesc(subVo.getProdDesc());
		if (CollectionUtils.isNotEmpty(errorList)) {
			//如果为预览
			if (CommodityConstant.SUBMIT_COMMODITY_IS_PREVIEW_TRUE.equals(submitVo.getIsPreview())) {
				CommodityUtilNew.getAlertThenCloseScript(response, errorList.get(0).getErrMsg());
				return null;
			} else {
				resultVo.setErrorList(errorList);
				return JSONObject.fromObject(resultVo).toString();				
			}
		}
		
		//如果为预览
		if (CommodityConstant.SUBMIT_COMMODITY_IS_PREVIEW_TRUE.equals(
				submitVo.getIsPreview())) {
			CommodityVO commodityVO = new CommodityVO();
			commodityVO.setCommodityNo(submitVo.getCommodityNo());
			request.setAttribute(CommodityConstant.COMMODITY_PREVIEW_COMMODITYVO_KEY, commodityVO);
			request.setAttribute(CommodityConstant.COMMODITY_PREVIEW_COMMODITY_SUBMIT_VO_KEY, submitVo);
			request.getRequestDispatcher("/commodity/preview.sc").forward(request, response);
			return null;
		}
		
		//构造commodityUpdateVo
		com.yougou.pc.model.commodityinfo.Commodity commodityUpdateVo = 
				CommodityUtilNew.buildCommodityUpdateVo(submitVo,propList);
		//判断修改的信息是不是原来的同款同色同年份
		String resultMsg = checkIsUnique(submitVo,commodityUpdateVo,commodity,propList,resultVo);
		if(resultMsg != null && resultMsg.length() > 0){
			return resultMsg;
		}
		commodityComponent.fillCommodityUpdateVo(commodityUpdateVo, commodity);
		try {
			// 如果商品还没有下架，先下架 added by liang.yx at 2015-3-6
			if(commodity.getStatus() != null && commodity.getStatus().intValue() == Constant.COMMODITY_STATUS_AVIL) {
				String _result = commodityApi.updateCommodityStatusForMerchant(commodity.getCommodityNo(), merchantCode, Constant.COMMODITY_STATUS_DOWN);
				logger.warn("{} :修改前先下架，result:{}",commodity.getCommodityNo(), _result);
			}
			 
			//修改商品(先保存再上传商品描述图片，如先上传商品描述图片再保存宝贝描述会被当前商品对象再次改写)
			String result = commodityApi.saveAllCommodityMsgForMerchant(commodityUpdateVo);
			logger.info("保存商品结果result: {}" ,  result);
			if (!(result.toLowerCase().equals(Constant.SUCCESS))) {
				resultVo.setErrorMsg(result);
				return JSONObject.fromObject(resultVo).toString();
			}
			
			//资料提交成功之后记录敏感词信息
			if(!commodityExtendService.insertCommodityExtendAndLog(submitVo,YmcThreadLocalHolder.getOperater(),"单品发布")){
				logger.error("商家{}插入商品{}扩展表数据与日志报错！",new Object[]{submitVo.getMerchantCode(),submitVo.getCommodityNo()});
			}
			
			// 判断停售和审核通过的商品提交修改
			if (commodity.getStatus()==1||commodity.getStatus()==13||commodity.getStatus()==5) {
				commodityApi.updateCommodityStatusForMerchant(commodity.getCommodityNo(), submitVo.getMerchantCode(), 11);
			}
			
			//发送JMS消息通知Image处理图片
			commodityService.sendJmsForMaster(subVo, commodity.getCommodityDesc() == null ? 0 : commodity.getCommodityDesc().length());
			
			//更新库存
			if ((CommodityConstant.SUPPLIER_IS_INPUT_YOUGOU_WAREHOUSE_NOT_IN + "")
					.equals(submitVo.getIsInputYougouWarehouse())) {
				
				String updateStockErrorMsg = commodityComponent.updateStockForAdd(subVo, resultVo, null);
				if (updateStockErrorMsg != null && updateStockErrorMsg.length() != 0) {
					resultVo.setErrorMsg(updateStockErrorMsg);
					return JSONObject.fromObject(resultVo).toString();
				}
			}
			
			//清除被删除货品的库存
			if (!commodityComponent.clearDeletedProdInventory(subVo, resultVo, commodity)) {
				return JSONObject.fromObject(resultVo).toString();
			}
			//如果是保存并提交审核
			if (CommodityConstant.SUBMIT_COMMODITY_IS_SAVE_SUBMIT_TRUE
					.equals(submitVo.getIsSaveSubmit())) {
				//校验图片完整性
				if (!merchantCommodityService.checkCommodityPicsIntegrality(
						submitVo.getCommodityNo(), submitVo.getSupplierCode(), submitVo.getMerchantCode(),submitVo.getBrandNo())) {
					resultVo.setErrorMsg("图片完整性校验异常");
					return JSONObject.fromObject(resultVo).toString();
				}
				//提交审核
				if (!commodityApi.auditMerchant(submitVo.getCommodityNo(), 
						submitVo.getMerchantCode())) {
					resultVo.setErrorMsg("提交审核失败");
					return JSONObject.fromObject(resultVo).toString();
				}
				//设置为保存并提交审核
				resultVo.setIsAddCommoditySaveSubmit(true);
			}
		} catch (RuntimeException e) {
			logger.error("修改商品运行时异常:",e);
			if (e.getClass().getName().equals("java.lang.RuntimeException")) {
				resultVo.setErrorMsg(e.getMessage());
			} else {
				resultVo.setErrorMsg("网络超时,请刷新后重新提交");
			}
			return JSONObject.fromObject(resultVo).toString();
		} catch (Exception e) {
			logger.error("修改商品产生异常:",e);
			resultVo.setErrorMsg("网络超时,请刷新后重新提交");
			return JSONObject.fromObject(resultVo).toString();
		} 
		resultVo.setIsSuccess(true);
		return JSONObject.fromObject(resultVo).toString();
	}
	
	/**
	 * checkIsUnique:
	 * 按尺码设置价格分类修改：根据款号，颜色，年份，尺码判断是否已存在货品
	 * 非按尺码设置价格类修改：根据款号，颜色，年份判断是否已存在商品
	 * @author li.n1 
	 * @param submitVo
	 * @param commodityUpdateVo
	 * @param commodity
	 * @param propList
	 * @param resultVo
	 * @return 
	 * @since JDK 1.6
	 */
	private String checkIsUnique(CommoditySubmitNewVo submitVo,
			com.yougou.pc.model.commodityinfo.Commodity commodityUpdateVo,
			com.yougou.pc.model.commodityinfo.Commodity commodity, 
			List<PropValue> propList, 
			CommoditySubmitResultVo resultVo) {
		if(propList!=null&&propList.size()>0){
			if(!(commodityUpdateVo.getStyleNo().equals(commodity.getStyleNo()))
				|| !(commodityUpdateVo.getColorName().equals(commodity.getColorName()))
				|| !(commodityUpdateVo.getYears().equals(commodity.getYears()))
				|| !(submitVo.getSizeNo()[0].equals(commodity.getProducts().get(0).getSizeNo()))){
				//修改了其中一个
				//按尺码设置价格的分类还要判断尺码是否使用过
				if(!(commodityService.check(submitVo.getMerchantCode(),commodityUpdateVo.getStyleNo(),
						commodityUpdateVo.getColorName(),
						commodityUpdateVo.getYears(),submitVo.getSizeNo(),propList))){
					//不可修改
					StringBuffer msg = new StringBuffer("已存在同款："+
							commodityUpdateVo.getStyleNo()+"，同色："+submitVo.getSpecName()+
							"，同年份："+commodityUpdateVo.getYears()+"，且同尺码："+submitVo.getSizeName()[0]);
					resultVo.setErrorMsg(msg.append("的商品！").toString());
					return JSONObject.fromObject(resultVo).toString();
				}
			}
		}else{
			if(!(commodityUpdateVo.getStyleNo().equals(commodity.getStyleNo()))
					|| !(commodityUpdateVo.getColorName().equals(commodity.getColorName()))
					|| !(commodityUpdateVo.getYears().equals(commodity.getYears()))){
				//修改了其中一个
				//判断该款同色同年份下是否存在商品编码，存在则提示不允许修改
				//按尺码设置价格的分类还要判断尺码是否使用过
				if(!(commodityService.check(submitVo.getMerchantCode(),commodityUpdateVo.getStyleNo(),
						commodityUpdateVo.getColorName(),
						commodityUpdateVo.getYears(),null,null))){
					//不可修改
					StringBuffer msg = new StringBuffer("已存在同款："+
							commodityUpdateVo.getStyleNo()+"，同色："+submitVo.getSpecName()+
							"，同年份："+commodityUpdateVo.getYears());
					resultVo.setErrorMsg(msg.append("的商品！").toString());
					return JSONObject.fromObject(resultVo).toString();
				}
			}
		}
		return null;
	}
	
	
	//封装commodityVo
	private List<CommodityVO> encapsulateCommodityVo(List<PropValue> propList,
			CommoditySubmitNewVo submitVo, String merchantCode) {
		List<CommodityVO> voList = null;
		//有按尺码修改价格属性
		if(propList!=null&&propList.size()>0){
			String supplierCode = commodityService.getSupplierCodeByStyleNoAndMerchantCode(submitVo.getBrandNo(),
					submitVo.getStyleNo(),merchantCode);
			Map<String,Object> map = commodityService.getCountByStyleNoAndMerchantCodeAndColor(submitVo.getBrandNo(),
					submitVo.getStyleNo(), merchantCode, submitVo.getSpecName());
			voList = CommodityUtilNew.buildCommodityVOList(submitVo,supplierCode,map);
			for(CommodityVO vo : voList){
				commodityComponent.fillCommodityVo(vo);
			}
			logger.warn("=======进入按尺码设置价格======商品属性{}",ToStringBuilder.reflectionToString(voList,ToStringStyle.MULTI_LINE_STYLE));
		}else{
			//木有按尺码修改价格属性，按原来方案流程走
			//构造CommodityVO
			voList = new ArrayList<CommodityVO>();
			CommodityVO commodityVO = CommodityUtilNew.buildCommodityVO(submitVo);
			commodityComponent.fillCommodityVo(commodityVO);
			voList.add(commodityVO);
		}
		return voList;
	}

	//非按尺码设置价格保存商品信息
	private String saveCommodity(List<CommodityVO> voList,
			List<ErrorVo> errorList, CommoditySubmitResultVo resultVo,
			CommoditySubmitNewVo submitVo) throws Exception {
		CommodityVO commodityVO = voList.get(0);
		MerchantImportInfo returnVo = commodityApi.insertCommodityMerchant(commodityVO);
		if (returnVo.getResult().toLowerCase().indexOf(Constant.SUCCESS) == -1) {
			//保存失败
			logger.info("保存商品结果result: {}" , returnVo.getErrorList());
			CommodityUtilNew.transformCommodityError(returnVo.getErrorList(), errorList);
			resultVo.setErrorList(errorList);
			return JSONObject.fromObject(resultVo).toString();
		} else {
			//标记商品资料提交成功
			resultVo.setIsCommoditySubmitSuccess(true);
			submitVo.setCommodityNo(returnVo.getCommodityNo());
			//资料提交成功之后记录敏感词信息
			if(!commodityExtendService.insertCommodityExtendAndLog(submitVo,YmcThreadLocalHolder.getOperater(),"单品发布")){
				logger.error("商家{}插入商品{}扩展表数据与日志报错！",new Object[]{submitVo.getMerchantCode(),submitVo.getCommodityNo()});
			}
		}
		//统一抛出错误异常
		if (CollectionUtils.isNotEmpty(errorList)) {
			resultVo.setErrorList(errorList);
			return JSONObject.fromObject(resultVo).toString();
		}
		//保存图片、库存，创建旗舰店，提交审核
		String str = saveImageAndStockAndStore(resultVo,commodityVO,submitVo,returnVo);
		if(str!=null){
			return str;
		}
		resultVo.setIsSuccess(true);
		return JSONObject.fromObject(resultVo).toString();
	}

	
	private String saveImageAndStockAndStore(
			CommoditySubmitResultVo resultVo, CommodityVO commodityVO,
			CommoditySubmitNewVo submitVo, MerchantImportInfo returnVo) throws Exception {
		try {
			//缓存[通过款色编码存储分类]
			this.redisTemplate.opsForHash().put(CacheConstant.C_COMMODITY_CATEGORY_KEY, commodityVO.getMerchantCode() + "_" + commodityVO.getSupplierCode(), commodityVO.getCatStructname());
			this.redisTemplate.expire(CacheConstant.C_COMMODITY_CATEGORY_KEY, 30, TimeUnit.MINUTES);// 设置属性时，重新设置超时时间续命
		} catch (Exception e) {
			logger.error("设置分类缓存异常.", e);
		}
		CommoditySubmitVo subVo = CommodityUtilNew.buildOldCommodityVO(submitVo);
		//发送JMS消息通知Image处理图片
		commodityService.sendJmsForMaster(subVo, 0);
		//库存
		if ((CommodityConstant.SUPPLIER_IS_INPUT_YOUGOU_WAREHOUSE_NOT_IN + "")
				.equals(submitVo.getIsInputYougouWarehouse())) {
			subVo.setSupplierCode(commodityVO.getSupplierCode());
			String updateStockErrorMsg = commodityComponent.updateStockForAdd(subVo, resultVo, returnVo.getProductCodes());
			if (StringUtils.isNotBlank(updateStockErrorMsg)) {
				resultVo.setErrorMsg(updateStockErrorMsg);
				return JSONObject.fromObject(resultVo).toString();
			}
		}
		
		//如果是保存并提交审核
		if (CommodityConstant.SUBMIT_COMMODITY_IS_SAVE_SUBMIT_TRUE
				.equals(submitVo.getIsSaveSubmit())) {
			//校验图片完整性
			if (!merchantCommodityService.checkCommodityPicsIntegrality(
					submitVo.getCommodityNo(), subVo.getSupplierCode(), submitVo.getMerchantCode(),submitVo.getBrandNo())) {
				resultVo.setErrorMsg("图片完整性校验异常");
				return JSONObject.fromObject(resultVo).toString();
			}
			
			//提交审核
			if (!commodityApi.auditMerchant(submitVo.getCommodityNo(), submitVo.getMerchantCode())) {
				resultVo.setErrorMsg("提交审核失败");
				return JSONObject.fromObject(resultVo).toString();
			}
			//设置为保存并提交审核
			resultVo.setIsAddCommoditySaveSubmit(true);
		}
		
		//给旗舰店插入数据
		try {
			fssYmcApiService.updateStoreIdWhenPublishCommodity(submitVo.getMerchantCode(),commodityVO.getBrandNo(),commodityVO.getCatStructname());
		} catch (Exception e) {
			logger.error("给旗舰店添加商品运行时异常({})",returnVo.getCommodityNo(),e);
		}
		return null;
	}

	//初始化与验证商品信息
	private String validateAddCommodity(HttpServletRequest request,
			CommoditySubmitNewVo submitVo, 
			List<ErrorVo> errorList, List<PropValue> propList,
			HttpServletResponse response,CommoditySubmitResultVo resultVo) throws Exception {
		//新增或修改商品信息，初始化用户信息
		CommodityUtilNew.initMerchantUserInfoForAddOrUpdateNew(request, submitVo);
		//设置商品基础配置
		submitVo.setSettings(settings);
		//表单验证方法， 一次性返回表单错误结果集
		submitVo.validateAddCommodityForm(errorList, 
				merchantCommodityService.getCommodityCatNamesByCatStructName(
						submitVo.getCatStructName()),propList);
		CommoditySubmitVo subVo = CommodityUtilNew.buildOldCommodityVO(submitVo);
		//校验图片
		imageService.verifyCommodityProdDesc(subVo,errorList);
		submitVo.setProdDesc(subVo.getProdDesc());
		if (CollectionUtils.isNotEmpty(errorList)) {
			//如果操作是预览
			if (CommodityConstant.SUBMIT_COMMODITY_IS_PREVIEW_TRUE.equals(
					submitVo.getIsPreview())) {
				CommodityUtilNew.getAlertThenCloseScript(response, errorList.get(0).getErrMsg());
				return "previewFail";
			} else {
				resultVo.setErrorList(errorList);
				return JSONObject.fromObject(resultVo).toString();				
			}
		}
		return null;
	}
	
	//防止瞬时提交
	private String forbiddenInstantSubmit(String merchantCode, String styleNo,
			CommoditySubmitResultVo resultVo) {
		String addCommodity_key="merchant.saveCommodity:"+merchantCode+":"+styleNo;
		Object addTime=redisTemplate.opsForValue().get(addCommodity_key);
		if(addTime!=null){
			Date existTime=(Date)addTime;
			Date now=new Date();
			if((now.getTime()-existTime.getTime())<2000){
				resultVo.setErrorMsg("请勿频繁提交!");
				return JSONObject.fromObject(resultVo).toString();
			}
		}else{
			redisTemplate.opsForValue().set(addCommodity_key, new Date());
			redisTemplate.expire(addCommodity_key, 4, TimeUnit.SECONDS);
		}
		return null;
	}

	//按尺码设置价格的商品保存信息
	private String saveBySizeCommodity(List<CommodityVO> voList,
			List<ErrorVo> errorList, CommoditySubmitResultVo resultVo,
			CommoditySubmitNewVo submitVo) throws Exception {
		int error = 0;
		for(CommodityVO commodityVO : voList){
			MerchantImportInfo returnVo = commodityApi.insertCommodityMerchant(commodityVO);
			if (returnVo.getResult().toLowerCase().indexOf(Constant.SUCCESS) == -1) {
				logger.info("保存按尺码设置价格分类的商品结果result: {}" , returnVo.getErrorList());
				CommodityUtilNew.transformCommodityError(returnVo.getErrorList(), errorList);
				resultVo.setErrorList(errorList);
				//记录错误的个数
				error++;
				//return JSONObject.fromObject(resultVo).toString();
			}else{
				submitVo.setCommodityNo(returnVo.getCommodityNo());
				//保存图片、库存，创建旗舰店，提交审核
				String str = saveImageAndStockAndStore(resultVo,commodityVO,submitVo,returnVo);
				if(str!=null){
					error++;
				}
				//资料提交成功之后记录敏感词的日志
				if(!commodityExtendService.insertCommodityExtendAndLog(submitVo,YmcThreadLocalHolder.getOperater(),"单品发布，按尺码设置价格商品")){
					logger.error("商家{}插入商品{}扩展表数据与日志报错！",new Object[]{submitVo.getMerchantCode(),submitVo.getCommodityNo()});
				}
			}
		}
		
		if(error==0){
			resultVo.setIsSuccess(true);
			resultVo.setIsCommoditySubmitSuccess(true);
		}
		//统一抛出错误异常
		if (CollectionUtils.isNotEmpty(errorList)) {
			resultVo.setErrorList(errorList);
			resultVo.setIsSuccess(false);
			resultVo.setIsCommoditySubmitSuccess(false);
		}
		resultVo.setErrorSize(error);
		resultVo.setSuccessSize(voList.size()-error);
		return JSONObject.fromObject(resultVo).toString();
	}

	/**
	 * 缓存本次选择的品牌和分类
	 * @param merchantCode
	 */
	@SuppressWarnings("unchecked")
	private void storeCurrentBrandAndCatg(String merchantCode, CommoditySubmitNewVo submitVo) {
		List<String[]> recentBrdCatg = (ArrayList<String[]>) redisTemplate.opsForValue().get(PRESTR + merchantCode);
		if (recentBrdCatg == null) {
			recentBrdCatg = new ArrayList<String[]>();
		}
		String[] arr = new String[2];
		arr[0] = submitVo.getBrandName() + "&nbsp;&gt;&nbsp;"
				+ submitVo.getRootCatName() + "&nbsp;&gt;&nbsp;"
				+ submitVo.getSecondCatName() + "&nbsp;&gt;&nbsp;"
				+ submitVo.getCatName();
		String[] catArr = submitVo.getCatStructName().split("-");
		arr[1] = submitVo.getBrandId() + "," + catArr[0] + "," + catArr[0] + "-" + catArr[1] + "," + submitVo.getCatStructName();
		Iterator<String[]> it = recentBrdCatg.iterator();
		String[] arrTmp = null;
		while(it.hasNext()){
			arrTmp = it.next();
			if(arrTmp[1].equalsIgnoreCase(arr[1])){// 有重复的，把它先删除再增加， 这样可排到最前
				it.remove();
			}
		}
		//缓存最近使用的8个分类
		if(recentBrdCatg.size() == 8){
			recentBrdCatg.remove(0);
		}
		recentBrdCatg.add(arr);
		redisTemplate.opsForValue().set(PRESTR + merchantCode, recentBrdCatg);
	}
	
	@ResponseBody
	@RequestMapping("/queryByKeyword")
	public String queryByKeyword(String keyword,ModelMap model,HttpServletRequest request){
		// 商家编号
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		//keyword可能是商品名称、商品款号、商品编号
		//商品名称表未加索引，先不根据商品名称查询，不然查询速度蜗牛般
		//根据keyword查询出的品牌、分类，供应商没有权限，提示
		//根据keyword查询空内容，不存在，提示
		return commodityService.queryAndVerify(keyword,merchantCode);
	}
	
	/**
	 * templateCount:根据分类、商家查询是否存在属性模板 
	 * @author li.n1 
	 * @param catNo
	 * @param key
	 * @param request
	 * @param query
	 * @return 
	 * @since JDK 1.6
	 */
	@ResponseBody
	@RequestMapping("/getTemplateCount")
	public String templateCount(String catNo,String key,HttpServletRequest request,Query query){
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		JSONObject object = new JSONObject();
		object.put("count", itemTemplateService.selectTemplateCount(merchantCode, catNo, "", key));
		return object.toString();
	}
	
	/**
	 * 第一步选择品牌与分类
	 * 跳转到发布单品的页面
	 * @param fromTabId
	 *            来自于哪个列表tab点击进来的
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/addCommodityui")
	public String goAddCommodity(String fromTabId, ModelMap model,
			HttpServletRequest request, CommoditySubmitNewVo submitVo) {
		String step = request.getParameter("step");
		if ("1".equals(step)) {
			return "forward:/commodity/preAddCommodityUI.sc";
		}
		// 商家编号
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		if(StringUtils.isNotBlank(submitVo.getCommodityNo())){
			storeCurrentBrandAndCatg(merchantCode, submitVo);
			model.put("commodityNo", submitVo.getCommodityNo());
		}
		List<String[]> recentBrdCatg = (ArrayList<String[]>) redisTemplate.opsForValue().get(PRESTR + merchantCode);
		if(recentBrdCatg != null){
			Collections.reverse(recentBrdCatg);
			model.put("recentBrdCatg", recentBrdCatg);
		}
		commodityComponent.initBrand(model, request);
		if (StringUtils.isNotBlank(submitVo.getBrandNo())) {
			model.put("brandNo", submitVo.getBrandNo());
		}
		if (StringUtils.isNotBlank(submitVo.getBrandId())) {
			model.put("brandId", submitVo.getBrandId());
		}
		if (StringUtils.isNotBlank(submitVo.getCatStructName())) {
			model.put("catStructName", submitVo.getCatStructName());
		}
		return "/manage_publish/commodity/selBrandAndCatgui";
	}
	
	@ResponseBody
	@RequestMapping("/loadPropertitiesByCatId")
	public String getCommodityPropertitiesByCatId(String catId, ModelMap model, 
			HttpServletRequest request){
		com.alibaba.fastjson.JSONObject jsonObj = new com.alibaba.fastjson.JSONObject();
		jsonObj.put("isSuccess", "false");
		catId = CommonUtil.getTrimString(catId);
		if (catId.length() == 0) {
			jsonObj.put("errorMsg", "请选择分类");
			return jsonObj.toString();
		}
		//获取商品属性
		List<PropItem> propItemList = merchantCommodityService.getPropMsgByCatIdNew(catId, false);
		if (propItemList != null && !propItemList.isEmpty()) {
			for(PropItem propItem : propItemList) {
				if(propItem != null && propItem.getIsShowMall() == null) {
					propItem.setIsShowMall(1);
				}
			}
			jsonObj.put("propList", propItemList);
		}
		List<PropValue> propList = commodityBaseApiService.getPropValueListByCategoryIdAndItemNo(catId,priceBySizePropNo);
		//有按尺码修改价格属性
		if(propList!=null&&propList.size()>0){
			jsonObj.put("isSizePrice", 1);
		}
		//获取颜色
		List<PropValue> colorList = commodityApi.getColorsByCatId(catId);
		if (colorList != null) {
			jsonObj.put("colorList", colorList);
		}
		
		//获取尺码
		List<PropValue> sizeList = commodityApi.getSizeByCatId(catId);
		if (sizeList != null) {
			jsonObj.put("sizeList", sizeList);
		}
		
		//获取分类对象
		String merchantCode=YmcThreadLocalHolder.getMerchantCode();
		int sheetIndex = 1;
		try {
			sheetIndex = CommodityPicIndexer.indexSheets(merchantCommodityService.getCommodityCatNamesByCatID(catId));
		} catch (Exception e) {
			logger.error("通过分类获取sheetIndex异常.{}",merchantCode);
		}
		jsonObj.put("sheetIndex",sheetIndex);
		jsonObj.put("isSuccess", "true");
		return jsonObj.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/checkSensitiveWord",method=RequestMethod.POST)
	public String checkSensitiveWord(SensitiveWordVo wordVo){
		JSONObject object = new JSONObject();
		String checkNameResult = commodityComponent.checkSensitiveWord(null,wordVo.getName());
		List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
		if(StringUtils.isNotBlank(checkNameResult)){
			Map<String,String> resultMap = new HashMap<String,String>();
			resultMap.put("id", "commodity_name");
			resultMap.put("name", "商品名称");
			resultMap.put("sensiveWord", checkNameResult);
			resultList.add(resultMap);
		}
		
		if(StringUtils.isNotBlank(wordVo.getSellingPoint())){
			String checkSellResult = commodityComponent.checkSensitiveWord(null,wordVo.getSellingPoint());
			if(StringUtils.isNotBlank(checkSellResult)){
				Map<String,String> resultMap = new HashMap<String,String>();
				resultMap.put("id", "commodity_selling");
				resultMap.put("name", "商品卖点");
				resultMap.put("sensiveWord", checkSellResult);
				resultList.add(resultMap);
			}
		}
		String checkProdDescResult = commodityComponent.checkSensitiveWord(null,wordVo.getProdDesc());
		if(StringUtils.isNotBlank(checkProdDescResult)){
			Map<String,String> resultMap = new HashMap<String,String>();
			resultMap.put("id", "goods_prodDesc");
			resultMap.put("name", "商品描述");
			resultMap.put("sensiveWord", checkProdDescResult);
			resultList.add(resultMap);
		}
		object.put("result", resultList);
		return object.toString();
	}
	
	
	/**
	 * checkSensitiveWordByAudit:审核前检查敏感词
	 * @author li.n1 
	 * @return 
	 * @since JDK 1.6 
	 * @date 2015-9-18 下午3:46:43
	 */
	@ResponseBody
	@RequestMapping(value="/checkSensitiveWordByAudit",method=RequestMethod.POST)
	public String checkSensitiveWordByAudit(SensitiveWordVo wordVo){
		JSONObject object = new JSONObject();
		List<Map<String,String>> sensitiveList = new ArrayList<Map<String,String>>();
		if (ArrayUtils.isNotEmpty(wordVo.getCommodityNo())) {
			String sensitiveWord = null;
			Commodity commodity = null;
			for (String commodityNo : wordVo.getCommodityNo()) {
				commodity = commodityApi.getCommodityByNo(commodityNo);
				sensitiveWord = commodityComponent.checkSensitiveWord(null, commodity.getCommodityName()+";"+commodity.getSellingPoint()+";"+commodity.getCommodityDesc());
				if(StringUtils.isNotBlank(sensitiveWord)){
					Map<String,String> sensitiveMap = new HashMap<String,String>();
					sensitiveMap.put("supplierCode", commodity.getSupplierCode());
					sensitiveMap.put("sensitiveWord", sensitiveWord);
					sensitiveList.add(sensitiveMap);
				}
			}
		}
		object.accumulate("sensitiveResult", sensitiveList);
		return object.toString();
	}
	
	
	/**
	 * checkOnLineSensitiveWord:在售商品修改名称过滤敏感词
	 * @author li.n1 
	 * @return 
	 * @since JDK 1.6 
	 * @date 2015-9-18 下午3:46:43
	 */
	@ResponseBody
	@RequestMapping(value="/checkOnLineSensitiveWord",method=RequestMethod.POST)
	public String checkOnLineSensitiveWord(SensitiveWordVo wordVo){
		JSONObject object = new JSONObject();
		List<Map<String,String>> sensitiveList = new ArrayList<Map<String,String>>();
		if (ArrayUtils.isNotEmpty(wordVo.getCommodityNo())) {
			String sensitiveWord = null;
			Commodity commodity = null;
			for (String commodityNo : wordVo.getCommodityNo()) {
				commodity = commodityApi.getCommodityByNo(commodityNo);
				sensitiveWord = commodityComponent.checkSensitiveWord(null, wordVo.getName());
				if(StringUtils.isNotBlank(sensitiveWord)){
					Map<String,String> sensitiveMap = new HashMap<String,String>();
					sensitiveMap.put("supplierCode", commodity.getSupplierCode());
					sensitiveMap.put("sensitiveWord", sensitiveWord);
					sensitiveList.add(sensitiveMap);
				}
			}
		}
		object.accumulate("sensitiveResult", sensitiveList);
		return object.toString();
	}
	
	
	@ResponseBody
	@RequestMapping(value="/logSensitiveWord",method=RequestMethod.POST)
	public void logSensitiveWord(SensitiveCheckLog log){
		if(StringUtils.isBlank(log.getComment())){
			log.setComment("单品发布");
		}
		log.setOperateType((short) (StringUtils.isNotBlank(log.getCommodityNo())?
				SensitiveCheckLog.OperateType.UPDATE.getValue():SensitiveCheckLog.OperateType.ADD.getValue()));
		log.setOperatorPerson(YmcThreadLocalHolder.getOperater());
		if(StringUtils.isNotBlank(log.getSensitiveWord())){
			log.setSensitive(true);
		}
		if(!(commodityComponent.insertSensitiveWordCheckLogByOne(log))){
			logger.error("{}检测敏感词，插入日志报错！",log.getComment());
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/logSensitiveWordByNo",method=RequestMethod.POST)
	public void logSensitiveWordByNo(String commodityNo,String commodityName,Short followOperate){
		Commodity commodity = null;
		CommoditySubmitNewVo newVo = null;
		try {
			commodity = commodityApi.getCommodityByNo(commodityNo);
			commodity.setCommodityName(commodityName);
			commodity.setSellingPoint(null);
			commodity.setCommodityDesc(null);
			newVo = CommodityUtilNew.buildCommoditySubmitNewVo(commodity,false);
			newVo.setFollowOperate(followOperate);
			if(!commodityExtendService.insertCommodityExtendAndLog(newVo,
					YmcThreadLocalHolder.getOperater(),"在售商品修改商品名称")){
				logger.error("商家{}插入商品{}扩展表数据与日志报错！",new Object[]{commodity.getMerchantCode(),commodityNo});
			}
		} catch (Exception e) {
			logger.error("商家{}插入商品{}扩展表数据与日志报错！",new Object[]{commodity.getMerchantCode(),commodityNo});
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/logSensitiveWordByAudit",method=RequestMethod.POST)
	public void logSensitiveWordByAudit(String[] commodityNo,Short followOperate){
		if (ArrayUtils.isNotEmpty(commodityNo)) {
			Commodity commodity = null;
			CommoditySubmitNewVo newVo = null;
			for (String no : commodityNo) {
				commodity = commodityApi.getCommodityByNo(no);
				try {
					newVo = CommodityUtilNew.buildCommoditySubmitNewVo(commodity,true);
					newVo.setFollowOperate(followOperate);
					if(!commodityExtendService.insertCommodityExtendAndLog(newVo,
							YmcThreadLocalHolder.getOperater(),"批量发布")){
						logger.error("商家{}插入商品{}扩展表数据与日志报错！",new Object[]{commodity.getMerchantCode(),no});
					}
				} catch (Exception e) {
					logger.error("商家{}插入商品{}扩展表数据与日志报错！",new Object[]{commodity.getMerchantCode(),no});
				}
			}
		}
	}
	
	@RequestMapping("/{no}/generate")
	public String generate(@PathVariable("no") String no){
		String url = DEFAULT_URL;
		try {
			url = commodityBaseApiService.getFullCommodityPageUrl(no);
		} catch (Exception e) {
			logger.error("通过cms接口获取单品页地址异常.", e);
		}
		return "redirect:"+url;
	}
	
	@RequestMapping("/loadSaleQuantity")
	@ResponseBody
	public String loadSaleQuantity(String commodityNos){
		JSONObject object = new JSONObject();
		String[] commodityArr = StringUtils.split(commodityNos, ",");
		if(commodityArr!=null && commodityArr.length>0){
			Map<String, Integer> mapCommoditySale = 
					commodityComponent.querySaleNumForBIByCommodityNos(Arrays.asList(commodityArr), YmcThreadLocalHolder.getMerchantCode());
			object.put("salenums", mapCommoditySale);
		}
		return object.toString();
	}
	
	@RequestMapping("/loadInventoryAndAuditDate")
	@ResponseBody
	public String loadInventoryAndAuditDate(String commodityNos,HttpServletRequest request){
		JsonConfig config = new JsonConfig();
		String[] commodityArr = StringUtils.split(commodityNos, ",");
		String warehouseCode = SessionUtil.getWarehouseCodeFromSession(request);
		List<InventoryAssistVo> qtys = null;
		if(commodityArr!=null && commodityArr.length>0){
			try {
				config.setJsonPropertyFilter(new PropertyFilter() {
					@Override
					public boolean apply(Object source, String name, Object value) {
						// 获取当前需要序列化的对象的类对象
				        Class<?> clazz = source.getClass();
				        if(clazz==InventoryAssistVo.class){
				        	if (name.equals("commodityNo") || 
				        			name.equals("canSalesInventoryNum")) {
				        		//返回false表示不过滤此属性
								return false;
							}
							return true;
				        }
				        return false;
					}
				});
				if (StringUtils.isNotBlank(warehouseCode)) {
					qtys = inventoryForMerchantService.queryCommodityInventory(Arrays.asList(commodityArr),
							warehouseCode);	
				} else {
					qtys = inventoryForMerchantService.queryInvenotryByCommodity(Arrays.asList(commodityArr));
				}
			} catch (WPIBussinessException e) {
				logger.error("查询queryCommodityInventory发生异常.", e);
			}
		}
		return JSONArray.fromObject(qtys==null?"[]":qtys, config).toString();
	}
	
	public String getPriceBySizePropNo() {
		return priceBySizePropNo;
	}

	public void setPriceBySizePropNo(String priceBySizePropNo) {
		this.priceBySizePropNo = priceBySizePropNo;
	}
	
}
