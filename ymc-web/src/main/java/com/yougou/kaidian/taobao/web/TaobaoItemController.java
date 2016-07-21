package com.yougou.kaidian.taobao.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yougou.kaidian.commodity.component.CommodityComponent;
import com.yougou.kaidian.commodity.constant.CommodityConstant;
import com.yougou.kaidian.commodity.model.vo.CommoditySubmitNewVo;
import com.yougou.kaidian.commodity.service.ICommodityService;
import com.yougou.kaidian.commodity.service.IImageService;
import com.yougou.kaidian.commodity.service.IMerchantCommodityService;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.commodity.util.CommodityPicIndexer;
import com.yougou.kaidian.common.util.CommonUtil;
import com.yougou.kaidian.common.util.DateUtil2;
import com.yougou.kaidian.common.util.FileFtpUtil;
import com.yougou.kaidian.framework.base.BaseController;
import com.yougou.kaidian.framework.settings.CommoditySettings;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.taobao.dao.TaobaoItemImgMapper;
import com.yougou.kaidian.taobao.enums.IsImportYougou;
import com.yougou.kaidian.taobao.enums.ResultCode;
import com.yougou.kaidian.taobao.exception.BusinessException;
import com.yougou.kaidian.taobao.model.TaobaoCommodityImportInfo;
import com.yougou.kaidian.taobao.model.TaobaoItemExtendDto;
import com.yougou.kaidian.taobao.model.TaobaoItemImg;
import com.yougou.kaidian.taobao.model.TaobaoShop;
import com.yougou.kaidian.taobao.service.ITaobaoAuthService;
import com.yougou.kaidian.taobao.service.ITaobaoBasisService;
import com.yougou.kaidian.taobao.service.ITaobaoDataImportService;
import com.yougou.kaidian.taobao.service.ITaobaoItemService;
import com.yougou.kaidian.taobao.vo.ErrorVo;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.api.ICommodityMerchantApiService;
import com.yougou.pc.model.brand.Brand;
import com.yougou.pc.model.category.Category;
import com.yougou.pc.model.product.Product;
import com.yougou.pc.model.prop.PropItem;
import com.yougou.pc.model.prop.PropValue;

@Controller
@RequestMapping("/taobao")
public class TaobaoItemController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(TaobaoItemController.class);

	private final String taobaoFtlPath = "/manage/taobao/";
	@Resource
	private ITaobaoItemService taobaoItemService;
	@Resource
	private ITaobaoAuthService taobaoAuthService;
	@Resource
	private ICommodityBaseApiService commodityBaseApiService;
	@Resource
	private ICommodityMerchantApiService commodityApi;
	@Resource
	private ICommodityService commodityService;
	@Resource
	private ITaobaoBasisService taobaoBasicService;

	@Resource
	private IMerchantCommodityService merchantCommodityService;

	@Resource
	private CommoditySettings commoditySettings;

	@Resource
	private IImageService imageService;
	@Resource
	private ITaobaoDataImportService taobaoDataImportService;

	private static String NOIMPORT2YOUGOU_ = "noimport2yougou_";

	// 判断是否按尺码修改价格
	// 深圳测试4s932
	// 正式线上4y644
	@Value("#{configProperties['priceBySizeProp']}")
	private String priceBySizePropNo = "4y644";
	@Autowired
	private CommodityComponent commodityComponent;

	@Resource
	private CommoditySettings settings;

	@Resource
	private TaobaoItemImgMapper taobaoItemImgMapper;

	/**
	 * 跳转到淘宝商品数据列表主页面
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/goTaobaoItemList")
	public String goTaobaoItemList(ModelMap modelMap, HttpServletRequest request, Query query) {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		String status = IsImportYougou.STATUS_NOT_IMPORTED.getStatus();

		try {
			Map<String, Object> params = this.builderParams(request, false);
			params.put("merchantCode", merchantCode);
			if (params.get("status") != null && StringUtils.isNotBlank(params.get("status").toString())) {
				status = params.get("status").toString();
			}
			params.put("status", status);// 1:已导入 0:未导入

			List<TaobaoShop> taobaoShop = taobaoAuthService.getAllTaobaoShopListByStatus(merchantCode, "3");
			modelMap.put("params", params);
			modelMap.put("taobaoShop", taobaoShop);
		} catch (UnsupportedEncodingException e) {
			log.error("[淘宝导入]商家编码:{}-查询淘宝商品产生异常.", merchantCode, e);
		}
		modelMap.put("status", status);
		if (IsImportYougou.STATUS_IMPORTED.getStatus().equals(status)) {
			return taobaoFtlPath + "taobao_item_list_already";
		} else {
			modelMap.addAttribute("yougouTree",
					taobaoBasicService.findYougouItemCatZTree(YmcThreadLocalHolder.getMerchantCode(), ""));
			return taobaoFtlPath + "taobao_item_list_noimport";
		}
	}

	/**
	 * 查询淘宝商品数据列表
	 * 
	 * @param modelMap
	 * @param request
	 * @param query
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/getTaobaoItemList")
	public String getYougouTaobaoItemCatList(ModelMap modelMap, HttpServletRequest request, Query query)
			throws UnsupportedEncodingException {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		String status = IsImportYougou.STATUS_NOT_IMPORTED.getStatus();
		Map<String, Object> params = this.builderParams(request, false);
		params.put("merchantCode", merchantCode);
		// modfity by lsm
		if (null != params.get("status") && StringUtils.isNotEmpty(params.get("status").toString())) {
			status = params.get("status").toString();
		}
		params.put("status", status);// 1:已导入 0:未导入

		// Add by LQ.
		buildListFromString4Params(params);

		PageFinder<TaobaoItemExtendDto> pageFinder = taobaoItemService.getTaobaoItem(params, query);
		modelMap.put("pageFinder", pageFinder);
		if (IsImportYougou.STATUS_IMPORTED.getStatus().equals(status)) {
			return "/manage_unless/taobao/taobao_item_list_already_list";
		} else {
			return "/manage_unless/taobao/taobao_item_list_noimport_list";
		}
	}

	/**
	 * 组装查询条件，商品款号，款色编码，货品条码 以逗号分隔，多值查询
	 * 
	 * @param params
	 */
	private void buildListFromString4Params(Map<String, Object> params) {
		String yougouStyleNo = (String) params.get("yougouStyleNo");
		String yougouSupplierCode = (String) params.get("yougouSupplierCode");
		String yougouCommodityNo = (String) params.get("yougouCommodityNo");
		String yougouThirdPartyCode = (String) params.get("yougouThirdPartyCode");

		if (StringUtils.isNotEmpty(yougouStyleNo)) {
			List<String> yougouStyleNoList = Arrays.asList(yougouStyleNo.split(","));
			params.put("yougouStyleNoList", yougouStyleNoList);
		}
		if (StringUtils.isNotEmpty(yougouSupplierCode)) {

			List<String> yougouSupplierCodeList = Arrays.asList(yougouSupplierCode.split(","));
			params.put("yougouSupplierCodeList", yougouSupplierCodeList);
		}
		if (StringUtils.isNotEmpty(yougouCommodityNo)) {
			List<String> yougouCommodityNoList = Arrays.asList(yougouCommodityNo.split(","));
			params.put("yougouCommodityNoList", yougouCommodityNoList);
		}
		if (StringUtils.isNotEmpty(yougouThirdPartyCode)) {
			List<String> yougouThirdPartyCodeList = Arrays.asList(yougouThirdPartyCode.split(","));
			params.put("yougouThirdPartyCodeList", yougouThirdPartyCodeList);
		}
	}

	/**
	 * 商家淘宝商品数据导入优购平台
	 * 
	 * @param modelMap
	 */
	@ResponseBody
	@RequestMapping("toTaobaoItemImportYougou")
	public String toTaobaoItemImportYougou(ModelMap modelMap, HttpServletRequest request) {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		String loginName = YmcThreadLocalHolder.getOperater();
		JSONObject result = new JSONObject();
		try {
			Map<String, Object> params = this.builderParams(request, false);
			String extend_id = MapUtils.getString(params, "extend_id");
			String isAudit = MapUtils.getString(params, "isAudit");
			if (StringUtils.isNotBlank(extend_id)) {
				String extend_idArry[] = StringUtils.split(extend_id, ",");
				// 如果是保存并提交审核
				String warnInfo = "";
				if (!CommodityConstant.SUBMIT_COMMODITY_IS_SAVE_SUBMIT_CONTINUE.equals(isAudit)
						&& !CommodityConstant.SUBMIT_COMMODITY_IS_SAVE_SUBMIT_YOUGOU.equals(isAudit)) {
					Map<String, String> infoMap = taobaoItemService.checkTaobaoImportSensitiveWords(extend_idArry);
					if (infoMap.size() > 0) {
						for (String sensitiveWord : infoMap.keySet()) {
							warnInfo += "款色编码：" + infoMap.get(sensitiveWord) + " 检测到敏感词：<span style=\"color:red\">"
									+ sensitiveWord + "</span><br/>";
						}
						logger.info("[淘宝导入]商家编辑:{}-淘宝商品导入优购检测到敏感词{}", merchantCode, warnInfo);
						if (CommodityConstant.SUBMIT_COMMODITY_IS_SAVE_SUBMIT_TRUE.equals(isAudit)) {
							result.put("code", "100");
							result.put("message", warnInfo);
							return result.toString();
						}
					}
				} else if (CommodityConstant.SUBMIT_COMMODITY_IS_SAVE_SUBMIT_CONTINUE.equals(isAudit)) {
					isAudit = CommodityConstant.SUBMIT_COMMODITY_IS_SAVE_SUBMIT_TRUE;
				} else if (CommodityConstant.SUBMIT_COMMODITY_IS_SAVE_SUBMIT_YOUGOU.equals(isAudit)) {
					isAudit = CommodityConstant.SUBMIT_COMMODITY_IS_SAVE_YOUGOU;
				}

				List<TaobaoCommodityImportInfo> taobaoCommodityImportInfoList = taobaoItemService
						.importTaobaoItemDataToYougou(merchantCode, extend_idArry, loginName, isAudit);
				List<String> errorInfo = new ArrayList<String>();
				for (TaobaoCommodityImportInfo taobaoCommodityImportInfo : taobaoCommodityImportInfoList) {
					errorInfo.addAll(taobaoCommodityImportInfo.getErrorList());
				}
				String message = StringUtils.join(errorInfo, ";");

				if (StringUtils.isNotBlank(warnInfo) && StringUtils.isEmpty(message)
						&& !CommodityConstant.SUBMIT_COMMODITY_IS_SAVE_SUBMIT_TRUE.equals(isAudit)
						&& !CommodityConstant.SUBMIT_COMMODITY_IS_SAVE_SUBMIT_CONTINUE.equals(isAudit)) {
					result.put("code", "300");
					result.put("message", warnInfo);
					return result.toString();
				}
				message = StringUtils.isEmpty(message) ? "导入成功！" : message;

				result.put("code", "200");
				result.put("message", message);
			} else {
				result.put("code", "500");
				result.put("message", "输出参数不完整，请检查!");
			}
		} catch (Exception e) {
			log.error("[淘宝导入]商家编码:{}-淘宝商品导入优购产生异常.", merchantCode, e);
			result.put("code", "500");
			result.put("message", "系统后台产生异常,请联系管理员!");
		}
		return result.toString();
	}

	/**
	 * 删除淘宝商品数据
	 * 
	 * @param modelMap
	 */
	@ResponseBody
	@RequestMapping("deleteTaobaoItemExtend")
	public String toDeleteTaobaoItem(ModelMap modelMap, String ids, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		try {
			taobaoItemService.deleteTaobaoItemExtend(ids, merchantCode);
			result.put("resultCode", ResultCode.SUCCESS.getCode());
		} catch (BusinessException e) {
			log.error("[淘宝导入]商家编码:{}-删除淘宝商品异常.", merchantCode, e);
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", e.getMessage());
		} catch (Exception e) {
			log.error("[淘宝导入]商家编码:{}-删除淘宝商品异常.", merchantCode, e);
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "系统异常");
		}
		return result.toString();
	}

	/**
	 * 检测品牌，分类是否已经绑定
	 * 
	 * @param modelMap
	 * @param ids
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("checkBindInfo")
	public String checkBindInfo(ModelMap modelMap, long numIid, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		try {
			taobaoItemService.checkBindInfo(numIid, merchantCode);
			result.put("resultCode", ResultCode.SUCCESS.getCode());
		} catch (BusinessException e) {
			log.error("[淘宝导入]商家编码:{}-获取绑定信息异常.", merchantCode, e);
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", e.getMessage());
		} catch (Exception e) {
			log.error("[淘宝导入]商家编码:{}-获取绑定信息异常.", merchantCode, e);
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "系统异常");
		}
		return result.toString();
	}

	/**
	 * 通过 商品编号 获取
	 * 
	 * @param commodityNo 商品编号
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/loadExtendById")
	public String getCommodityByNo(String extendId, HttpServletRequest request, ModelMap model) throws Exception {
		final String IS_SUCCESS_KEY = "isSuccess";
		final String ERROR_MSG_KEY = "errorMsg";
		final String COMMODITY_KEY = "commodity";
		final String IMAGE_CACHE = "imageCache";

		JSONObject jsonObj = new JSONObject();
		jsonObj.put(IS_SUCCESS_KEY, "false");
		jsonObj.put(ERROR_MSG_KEY, "该件商品不存在");

		extendId = CommonUtil.getTrimString(extendId);
		com.yougou.pc.model.commodityinfo.Commodity commodity = this.taobaoItemService.getExtendFormatCommodity(
				extendId, 0);

		List<TaobaoItemImg> imageList = taobaoItemImgMapper.queryTaobaoItemImgByExtendId(extendId);
		JSONObject imageJson = new JSONObject();
		imageJson.put("images", imageList);

		// 图片缓存
		jsonObj.put(IMAGE_CACHE, imageList.toArray());

		// 获取货品的销售状态
		if (commodity != null) {
			List<Product> products = commodity.getProducts();
			int count = 0;
			if (products != null && products.size() > 0) {
				for (Product product : products) {
					count = commodityService.querySaleCountByroductNo(product.getProductNo());
					if (count > 0) {
						product.setSellStatus(2);
					}
				}
			}
		}
		jsonObj.put(IS_SUCCESS_KEY, "true");
		jsonObj.put(COMMODITY_KEY, commodity);
		return jsonObj.toString();
	}

	/**
	 * 修改商品（新）
	 * 
	 * 与商品修改同步，共用一个页面
	 * 
	 * @return
	 */
	@RequestMapping("/toUpdateTaobaoItemNew")
	public String toUpdateTaobaoItemToNew(ModelMap modelMap, long numIid, String extendId, HttpServletRequest request)
			throws Exception {

		String ftlUrl = "/manage_publish/taobao/update_taobao_item_newui";
		String prefix = "goUpdateCommodityNewui#-> ";
		// 新增或修改商品 公共方法
		commodityComponent.goAddOrUpdateCommodityCommon("", modelMap, request);

		/****************************
		 * 因为 淘宝导入时候，并没有进入商品库，所以也就不存在commodityNo ,commodity 对象也没有，这里将
		 * taobaoExtend 对象进行转换
		 * 
		 ***************************/
		com.yougou.pc.model.commodityinfo.Commodity commodity = taobaoItemService.getExtendFormatCommodity(extendId,
				numIid);
		Category category = commodityBaseApiService.getCategoryByNo(commodity.getCatNo());
		Brand brand = commodityBaseApiService.getBrandByNo(commodity.getBrandNo());
		CommoditySubmitNewVo submitVo = new CommoditySubmitNewVo();
		// 保存修改前的审核状态
		modelMap.put("auditStatus", "2");//
		modelMap.put("commodity", commodity);
		modelMap.put("category", category);
		if (StringUtils.isBlank(submitVo.getCatId())) {
			submitVo.setCatId(category.getId());
		}
		if (StringUtils.isBlank(submitVo.getBrandName())) {
			submitVo.setBrandName(commodity.getBrandName());
		}
		if (StringUtils.isBlank(submitVo.getBrandNo())) {
			submitVo.setBrandNo(commodity.getBrandNo());
		}
		if (StringUtils.isBlank(submitVo.getBrandId())) {
			submitVo.setBrandId(brand.getId());
		}
		if (StringUtils.isBlank(submitVo.getRootCatName())) {
			submitVo.setRootCatName(category.getStructCatName().split("-")[0]);
		}
		if (StringUtils.isBlank(submitVo.getSecondCatName())) {
			submitVo.setSecondCatName(category.getStructCatName().split("-")[1]);
		}
		if (StringUtils.isBlank(submitVo.getCatName())) {
			submitVo.setCatName(commodity.getCatName());
		}
		if (StringUtils.isBlank(submitVo.getCatStructName())) {
			submitVo.setCatStructName(commodity.getCatStructName());
		}
		if (StringUtils.isBlank(submitVo.getCatNo())) {
			submitVo.setCatNo(commodity.getCatNo());
		}
		// 图片域名
		modelMap.addAttribute("commodityPreviewDomain", settings.getCommodityPreviewDomain());
		logger.debug(prefix + "commodityPreviewDomain: " + settings.getCommodityPreviewDomain());
		modelMap.put("pageSourceId", 1);
		modelMap.put("merchantCode", YmcThreadLocalHolder.getMerchantCode());
		List<PropValue> propList = commodityBaseApiService.getPropValueListByCategoryIdAndItemNo(submitVo.getCatId(),
				priceBySizePropNo);
		// 有按尺码修改价格属性
		if (propList != null && propList.size() > 0) {
			modelMap.put("isSizePrice", 1);
		}
		modelMap.put("submitVo", submitVo);
		modelMap.put("commodityNo", extendId);
		modelMap.put("numIid", numIid);
		modelMap.put("extendId", extendId);

		return ftlUrl;
	}

	/**
	 * 修改淘宝商品
	 * 
	 * @param modelMap
	 * @param numIid
	 * @return
	 */
	@RequestMapping("/toUpdateTaobaoItem")
	public String toUpdateTaobaoItem(ModelMap modelMap, long numIid, String extendId, HttpServletRequest request)
			throws Exception {

		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		Map<String, Object> resultMap = taobaoItemService.getTaobaoItem4Update(merchantCode, numIid, extendId);
		Set<String> set = resultMap.keySet();
		Iterator<String> iterator = set.iterator();
		String key = null;
		while (iterator.hasNext()) {
			key = iterator.next();
			modelMap.put(key, resultMap.get(key));
		}

		return taobaoFtlPath + "update_taobao_item";
	}

	/**
	 * 获取优购的分类
	 * 
	 * @param modelMap
	 * @param request
	 * @param brandId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryBrandCat")
	public String queryBrandCat(ModelMap modelMap, HttpServletRequest request, String brandId) {
		List<Category> list = commodityService.getAllCategoryByBrandId(YmcThreadLocalHolder.getMerchantCode(), brandId);

		if (list == null || list.isEmpty()) {
			return null;
		}

		JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(new PropertyFilter() {
			@Override
			public boolean apply(Object source, String name, Object value) {
				if (name.equals("id") || name.equals("catName") || name.equals("structName") || name.equals("catLeave")
						|| name.equals("catNo") || name.equals("parentId")) {
					return false;
				}
				return true;
			}
		});
		JSONArray jsonArray = JSONArray.fromObject(list, config);
		return jsonArray.toString();
	}

	@ResponseBody
	@RequestMapping("/getCommodityPropertitiesByCatNo")
	public String getCommodityPropertitiesByCatId(String catNo, ModelMap model, HttpServletRequest request)
			throws Exception {
		JSONObject jsonObj = new JSONObject();
		final String IS_SUCCESS_KEY = "isSuccess";
		final String ERROR_MSG_KEY = "errorMsg";
		final String PROP_LIST = "propList";
		final String COLOR_LIST = "colorList";
		final String SIZE_LIST = "sizeList";
		jsonObj.put(IS_SUCCESS_KEY, "false");
		catNo = CommonUtil.getTrimString(catNo);
		if (catNo.length() == 0) {
			jsonObj.put(ERROR_MSG_KEY, "请选择分类");
			return jsonObj.toString();
		}

		jsonObj.put(IS_SUCCESS_KEY, "true");
		Category category = commodityBaseApiService.getCategoryByNo(catNo);
		// 获取商品属性
		String catId = category.getId();
		List<PropItem> propItemList = merchantCommodityService.getPropMsgByCatIdNew(catId, false);
		if (propItemList != null && !propItemList.isEmpty()) {
			for (PropItem propItem : propItemList) {
				if (propItem != null && propItem.getIsShowMall() == null) {
					propItem.setIsShowMall(1);
				}
			}
			jsonObj.put(PROP_LIST, propItemList);
		}

		// 获取颜色
		List<PropValue> colorList = commodityApi.getColorsByCatId(catId);
		if (colorList != null) {
			jsonObj.put(COLOR_LIST, colorList);
		}

		// 获取尺码
		List<PropValue> sizeList = commodityApi.getSizeByCatId(catId);
		if (sizeList != null) {
			jsonObj.put(SIZE_LIST, sizeList);
		}
		// 获取角度图最小张数
		jsonObj.put("latestPics", CommodityPicIndexer.indexNumbers(
				CommoditySettings.COMMODITY_MAGNIFIER_IMAGE_DEFAULT_NUMBERS, category.getStructCatName().split("_")));
		return jsonObj.toString();
	}

	/**
	 * 商家中心-修改淘宝商品-保存
	 * 
	 * @param request
	 * @param response
	 * @param isPreview
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updateTaobaoItem")
	public String addCommodity(HttpServletRequest request, HttpServletResponse response, int isPreview)
			throws Exception {
		Map<String, Object> params = this.builderParams(request, false);
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		List<ErrorVo> errorList = null;
		if (isPreview == 0) {
			JSONObject json = new JSONObject();
			try {
				errorList = taobaoItemService.updateTaobaoItem(params, merchantCode, request);
				json.put("resultCode", ResultCode.SUCCESS.getCode());
				if (CollectionUtils.isNotEmpty(errorList)) {
					json.put("errorList", errorList);
				}
			} catch (BusinessException e) {
				log.error("[淘宝导入]商家编码:{}-修改淘宝商品异常.", merchantCode, e);
				json.put("resultCode", ResultCode.ERROR.getCode());
				json.put("msg", e.getMessage());
				return json.toString();
			} catch (Exception e) {
				log.error("[淘宝导入]商家编码:{}-修改淘宝商品异常.", merchantCode, e);
				json.put("resultCode", ResultCode.ERROR.getCode());
				json.put("msg", "系统异常");
			}
			return json.toString();
		} else if (isPreview == 1) {
			// 预览暂时没实现
		}
		return null;
	}

	/**
	 * 商家中心-导出修改商品-第一步：生成商品excel文件
	 * 
	 * @param request
	 * @param itemIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/createItemExcel")
	public String createItemExcel(HttpServletRequest request, String itemIds) {
		JSONObject jsonObj = new JSONObject();
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		FileOutputStream outputStream = null;
		try {
			String dateStr = DateUtil2.getCurrentDateTimeToStr();
			XSSFWorkbook workbook = this.taobaoItemService.getItemBook(itemIds, merchantCode);
			File file = new File(this.getAbsoluteFilepath(merchantCode));
			if (!file.exists()) {
				file.mkdirs();
			}
			File excelFile = new File(this.getAbsoluteFilepath(merchantCode) + NOIMPORT2YOUGOU_ + dateStr + ".xlsx");
			outputStream = new FileOutputStream(excelFile);
			workbook.write(outputStream);
			outputStream.flush();
			boolean result = imageService.ftpUpload(excelFile, "/merchantpics/exceltemp/" + merchantCode);
			if (result) {
				jsonObj.put("resultCode", ResultCode.SUCCESS.getCode());
				jsonObj.put("url", dateStr + ".xlsx");
			} else {
				jsonObj.put("resultCode", ResultCode.ERROR.getCode());
				jsonObj.put("msg", "系统异常，导出商品失败");
			}

		} catch (BusinessException e) {
			log.error("[淘宝导入]商家编码:{}-导出商品失败.", merchantCode, e);
			jsonObj.put("resultCode", ResultCode.ERROR.getCode());
			jsonObj.put("msg", e.getMessage());
		} catch (Exception e) {
			log.error("[淘宝导入]商家编码:{}-导出商品失败.", merchantCode, e);
			jsonObj.put("resultCode", ResultCode.ERROR.getCode());
			jsonObj.put("msg", "系统异常，导出商品失败");
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (Exception e) {
					log.error("[淘宝导入]商家编码:{}-IO异常，导出失败.", merchantCode, e);
				}
			}
		}
		return jsonObj.toString();
	}

	private String getAbsoluteFilepath(String merchantCode) {
		return new StringBuilder(commoditySettings.picDir).append(File.separator).append("exceltemp")
				.append(File.separator).append(merchantCode).append(File.separator).toString();
	}

	/**
	 * 商家中心-导出修改商品-第二步：excel文件下载
	 * 
	 * @param name
	 * @param request
	 * @param response
	 */
	@RequestMapping("/excelDownload")
	public void excelDownload(@RequestParam String name, HttpServletRequest request, HttpServletResponse response) {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		OutputStream outputStream = null;
		try {
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader(
					"Content-Disposition",
					MessageFormat.format("attachment; filename={0}",
							encodeDownloadAttachmentFilename(request, "淘宝商品_" + name)));
			outputStream = response.getOutputStream();
			FileFtpUtil ftpUtil = new FileFtpUtil(commoditySettings.imageFtpServer, commoditySettings.imageFtpPort,
					commoditySettings.imageFtpUsername, commoditySettings.imageFtpPassword);
			ftpUtil.login();
			ftpUtil.downRemoteFile("/merchantpics/exceltemp/" + merchantCode + "/" + NOIMPORT2YOUGOU_ + name,
					outputStream);
			outputStream.flush();
			ftpUtil.logout();
		} catch (Exception e) {
			log.error("[淘宝导入]商家编码:{}-文件下载失败.", merchantCode, e);
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					log.error("[淘宝导入]商家编码:{}-IO流关闭异常.", merchantCode, e);
				}
			}
		}
	}

	/**
	 * 下载文件转码
	 * 
	 * @param request
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	private String encodeDownloadAttachmentFilename(HttpServletRequest request, String filename) throws Exception {
		return StringUtils.indexOf(request.getHeader("User-Agent"), "MSIE") != -1 ? URLEncoder
				.encode(filename, "UTF-8") : new String(filename.getBytes("UTF-8"), "ISO-8859-1");
	}

	/**
	 * 淘宝导入-导入修改商品
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/exportExcel")
	public String exportExcel(MultipartHttpServletRequest request, HttpServletResponse response) {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		JSONObject jsonObject = new JSONObject();
		try {
			Iterator<String> itr = request.getFileNames();
			MultipartFile mpf;
			if (itr.hasNext()) {
				String dateStr = DateUtil2.getCurrentDateTimeToStr();
				mpf = request.getFile(itr.next());
				File file = new File(this.getAbsoluteFilepath(merchantCode));
				if (!file.exists()) {
					file.mkdirs();
				}
				String fileName = NOIMPORT2YOUGOU_ + dateStr + ".xlsx";
				String fileUrl = this.getAbsoluteFilepath(merchantCode) + fileName;
				File excelFile = new File(fileUrl);
				mpf.transferTo(excelFile);
				jsonObject.put("result", this.taobaoItemService.exportExcel(excelFile, merchantCode));
				jsonObject.put("url", dateStr + ".xlsx");
				imageService.ftpUpload(excelFile, "/merchantpics/exceltemp/" + merchantCode);
			}
			jsonObject.put("resultCode", ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			log.error("[淘宝导入]商家编码:{}-导入淘宝商品异常.", merchantCode, e);
			jsonObject.put("resultCode", ResultCode.ERROR.getCode());
			jsonObject.put("result", "导入淘宝商品异常!" + e.getMessage() == null ? "" : e.getMessage());
		}
		return jsonObject.toString();
	}

	@RequestMapping("/goCsvImport")
	public String goCsvImport(ModelMap modelMap, HttpServletRequest request, Query query) {
		return taobaoFtlPath + "taobao_csv_import";
	}

	/**
	 * 根据模板初始化商品
	 * 
	 * @param request HttpServletRequest
	 * @param ids 格式：numiid|extendId,numiid|extendId
	 * @return Json格式字符串
	 */
	@ResponseBody
	@RequestMapping("/initDataFromTemplate")
	public String initDataFromTemplate(HttpServletRequest request, String ids) {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		JSONObject jsonObject = new JSONObject();
		try {
			Map<String, String> result = this.taobaoDataImportService.initDataFromTemplate(ids, merchantCode);
			int failureCount = Integer.valueOf(result.get("failureCount")).intValue();
			String msg = "";
			if (failureCount > 0) {
				msg = "成功初始化" + result.get("successCount") + "个商品,失败" + failureCount + "个。" + result.get("msg");
			} else {
				msg = "成功初始化" + result.get("successCount") + "个商品";
			}
			logger.info("[淘宝导入]商家编码:{}成功初始化{}个商品,失败{}个.失败信息{}.", merchantCode, result.get("successCount"), failureCount, result.get("msg"));
			jsonObject.put("msg", msg);
			jsonObject.put("resultCode", ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			log.error("[淘宝导入]商家编码:{}-初始化商品异常.", merchantCode, e);
			jsonObject.put("resultCode", ResultCode.ERROR.getCode());
			jsonObject.put("msg", "初始化失败！" + e.getMessage());
		}
		return jsonObject.toString();
	}

}
