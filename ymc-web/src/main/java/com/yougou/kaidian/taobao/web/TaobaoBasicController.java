package com.yougou.kaidian.taobao.web;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yougou.kaidian.commodity.service.ICommodityService;
import com.yougou.kaidian.commodity.service.IMerchantCommodityService;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.commodity.pojo.Brand;
import com.yougou.kaidian.framework.base.BaseController;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.taobao.enums.AuthType;
import com.yougou.kaidian.taobao.enums.BindType;
import com.yougou.kaidian.taobao.enums.ResultCode;
import com.yougou.kaidian.taobao.exception.BusinessException;
import com.yougou.kaidian.taobao.model.TaobaoShop;
import com.yougou.kaidian.taobao.model.TaobaoYougouBrand;
import com.yougou.kaidian.taobao.model.TaobaoYougouItemCat;
import com.yougou.kaidian.taobao.service.ITaobaoAuthService;
import com.yougou.kaidian.taobao.service.ITaobaoBasisService;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.api.ICommodityMerchantApiService;
import com.yougou.pc.model.category.Category;
import com.yougou.pc.model.prop.PropItem;

/**
 * 优购商家绑定基本信息数据（品牌、类目、类目属性、类目属性值）
 * 
 * @author
 *
 */
@Controller
@RequestMapping("/taobao")
public class TaobaoBasicController extends BaseController {

	private final String taobaoFtlPath = "/manage/taobao/";

	private static final Logger log = LoggerFactory.getLogger(TaobaoBasicController.class);

	@Resource
	private ITaobaoBasisService taobaoBasicService;

	@Resource
	private ICommodityService commodityService;

	@Resource
	ICommodityMerchantApiService commodityApi;

	@Resource
	private ICommodityBaseApiService commodityBaseApiService;
	@Resource
	private ITaobaoAuthService taobaoAuthService;

	@Resource
	private IMerchantCommodityService merchantCommodityService;

	/**
	 * 获取当前商家优购授权的品牌以及从淘宝抓取授权的品牌 跳转优购淘宝品牌绑定信息页面
	 * 
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping("/goYougouTaobaoBrand")
	public String goYougouTaobaoBrand(ModelMap modelMap, HttpServletRequest request, Query query) throws Exception {
		/**
		 * 获取有过品牌接口 List<Brand> lstBrand =
		 * commodityService.queryBrandList(SessionUtil
		 * .getMerchantCodeFromSession(request));
		 */
		Map<String, Object> params = this.builderParams(request, false);
		params.put("bindType", BindType.HAVEBIND.getValue());
		params.put("merchantCode", YmcThreadLocalHolder.getMerchantCode());
		PageFinder<TaobaoYougouBrand> pageFinder = taobaoBasicService.findTaobaoYougouBrandList(params, query);
		modelMap.addAttribute("pageFinder", pageFinder);
		modelMap.addAttribute("params", params);
		return taobaoFtlPath + "yougou_taobao_brand";
	}

	/**
	 * 保存优购淘宝品牌绑定信息页面
	 * 
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/toSaveYougouTaobaoBrand")
	public String toSaveYougouTaobaoBrand(ModelMap modelMap, HttpServletRequest request, Query query)
			throws UnsupportedEncodingException {
		Map<String, Object> params = this.builderParams(request, false);
		params.put("merchantCode", YmcThreadLocalHolder.getMerchantCode());
		if (null == params.get("id")) {
			params.put("bindType", BindType.NOBIND.getValue());
		}
		PageFinder<TaobaoYougouBrand> pageFinder = taobaoBasicService.findTaobaoYougouBrandList(params, query);
		// 查询优购品牌
		List<Brand> lstBrand = commodityService.queryBrandList(YmcThreadLocalHolder.getMerchantCode());
		modelMap.addAttribute("pageFinder", pageFinder);
		modelMap.addAttribute("yougouBrands", lstBrand);
		modelMap.addAttribute("params", params);
		return "/manage_unless/taobao/yougou_taobao_brand_message";
	}

	@ResponseBody
	@RequestMapping("/saveYougouTaobaoBrand")
	public String saveYougouTaobaoBrand(ModelMap modelMap, HttpServletRequest request, String dataStr) {
		JSONObject result = new JSONObject();
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		try {
			taobaoBasicService.saveTaobaoYougouBrand(dataStr, merchantCode);
			result.put("resultCode", ResultCode.SUCCESS.getCode());
		} catch (BusinessException e) {
			log.error("[淘宝导入]商家编码:{}-绑定淘宝品牌异常.", merchantCode, e);
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			result.put("msg", e.getMessage());
		} catch (Exception e) {
			log.error("[淘宝导入]商家编码:{}-绑定淘宝品牌异常.", merchantCode, e);
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "系统异常!");
		}
		return result.toString();
	}

	@ResponseBody
	@RequestMapping("/delYougouTaobaoBrand")
	public String delYougouTaobaoBrand(ModelMap modelMap, HttpServletRequest request, String ids) {
		JSONObject result = new JSONObject();
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		try {
			taobaoBasicService.delTaobaoYougouBrand(ids, merchantCode);
			result.put("resultCode", ResultCode.SUCCESS.getCode());
		} catch (BusinessException e) {
			log.error("[淘宝导入]商家编码:{}-删除淘宝品牌绑定异常.", merchantCode, e);
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			result.put("msg", e.getMessage());
		} catch (Exception e) {
			log.error("[淘宝导入]商家编码:{}-删除淘宝品牌绑定异常.", merchantCode, e);
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "系统异常!");
		}
		return result.toString();
	}

	/**
	 * 根据session中的merchantCode获取未绑定的类目 跳转优购淘宝类目绑定信息页面
	 * 
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/goYougouTaobaoItemCat")
	public String goYougouTaobaoItemCat(ModelMap modelMap, HttpServletRequest request, Query query)
			throws UnsupportedEncodingException {
		modelMap.addAttribute("yougouTree",
				taobaoBasicService.findYougouItemCatZTree(YmcThreadLocalHolder.getMerchantCode(), ""));
		return taobaoFtlPath + "yougou_taobao_item_cat";
	}

	@RequestMapping("/getYougouTaobaoItemCatList")
	public String getYougouTaobaoItemCatList(ModelMap modelMap, HttpServletRequest request, Query query)
			throws UnsupportedEncodingException {
		Map<String, Object> params = this.builderParams(request, false);
		params.put("bindType", BindType.HAVEBIND.getValue());
		params.put("merchantCode", YmcThreadLocalHolder.getMerchantCode());
		PageFinder<TaobaoYougouItemCat> pageFinder = taobaoBasicService.findTaobaoYougouItemCatList(params, query);
		modelMap.addAttribute("pageFinder", pageFinder);
		modelMap.addAttribute("params", params);
		return "/manage_unless/taobao/yougou_taobao_item_cat_list";
	}

	/**
	 * 获取优购分类，淘宝分类
	 * 
	 * @param modelMap
	 * @param yougouCatNo
	 * @param taobaoCid
	 * @param request
	 * @return
	 */
	@RequestMapping("/toYougouTaobaoItemCat")
	public String toSaveYougouTaobaoItemCat(ModelMap modelMap, HttpServletRequest request) {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		List<TaobaoShop> shopList = taobaoAuthService.getAllTaobaoShopListByStatus(merchantCode,
				AuthType.STATUS3.getStatus());
		modelMap.addAttribute("shopList", shopList);
		JSONObject yougouTree = new JSONObject();
		yougouTree.put("yougouTree",
				taobaoBasicService.findYougouItemCatZTree(YmcThreadLocalHolder.getMerchantCode(), ""));
		modelMap.addAttribute("yougouTree", yougouTree.toString());
		return "/manage/taobao/yougou_taobao_item_cat_bind";
	}

	@ResponseBody
	@RequestMapping("/getTaobaoItemTree")
	public String getTaobaoItemTree(HttpServletRequest request, String nickId) {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		JSONObject result = new JSONObject();
		try {
			result.put("taobaoTree", taobaoBasicService.findTaobaoYougouItemCatZTree(merchantCode, nickId));
			result.put("resultCode", ResultCode.SUCCESS.getCode());
		} catch (BusinessException e) {
			log.error("[淘宝导入]商家编码:{}-获取淘宝分类异常.", merchantCode, e);
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "系统异常!");
		} catch (Exception e) {
			log.error("[淘宝导入]商家编码:{}-获取淘宝分类异常.", merchantCode, e);
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "系统异常!");
		}
		return result.toString();
	}

	@ResponseBody
	@RequestMapping("/getYougouItemTree")
	public String getYougouItemTree(HttpServletRequest request, String nickId, String structName) {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		JSONObject result = new JSONObject();
		try {
			result.put("yougouTree",
					taobaoBasicService.findYougouItemCatZTree(YmcThreadLocalHolder.getMerchantCode(), structName));
		} catch (Exception e) {
			log.error("[淘宝导入]商家编码:{}-获取优购分类异常.", merchantCode, e);
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "系统异常!");
		}
		return result.toString();
	}

	/**
	 * 删除分类绑定
	 * 
	 * @param modelMap
	 * @param request
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delYougouTaobaoItemCat")
	public String delYougouTaobaoItemCat(ModelMap modelMap, HttpServletRequest request, String ids) {
		JSONObject result = new JSONObject();
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		try {
			taobaoBasicService.delTaobaoYougouItemCat(ids, merchantCode);
			result.put("resultCode", ResultCode.SUCCESS.getCode());
		} catch (BusinessException e) {
			log.error("[淘宝导入]商家编码:{}-删除淘宝分类绑定异常.", merchantCode, e);
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			result.put("msg", e.getMessage());
		} catch (Exception e) {
			log.error("[淘宝导入]商家编码:{}-删除淘宝分类绑定异常.", merchantCode, e);
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "系统异常!");
		}
		return result.toString();
	}

	/**
	 * 绑定分类
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/bindTaobaoYougouItemCat")
	public String findTaobaoYougouItemCatZTree(HttpServletRequest request, ModelMap modelMap, String yougouItemId,
			String bindId) throws Exception {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		try {
			Map<String, Object> params = this.builderParams(request, false);
			params.put("merchantCode", merchantCode);
			taobaoBasicService.bindYougouItemCate(params);
		} catch (BusinessException e) {
			log.error("[淘宝导入]商家编码:{}-淘宝分类绑定异常.", merchantCode, e);
			throw e;
		} catch (Exception e) {
			log.error("[淘宝导入]商家编码:{}-淘宝分类绑定异常.", merchantCode, e);
			throw e;
		}
		modelMap.put("catId", yougouItemId);
		modelMap.put("bindId", bindId);
		TaobaoYougouItemCat cat = taobaoBasicService.getTaobaoYougouItemCatById(bindId);
		modelMap.put("taobaoCid", cat.getTaobaoCid());
		return "/manage/taobao/yougou_taobao_item_cat_pro_bind";
	}

	/**
	 * 查询优购分类属性，淘宝分类属性
	 * 
	 * @param catId
	 * @param bindId
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getYougouTaobaoPro")
	public String getYougouProAndTaobaoPro(String catId, String bindId, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		try {
			List<PropItem> yougouPropList = merchantCommodityService.getPropMsgByCatIdNew(catId, true);
			result.put("yougouPropList", yougouPropList);
			result.put("taobaoProList", taobaoBasicService.selectTaobaoItemPro4Bind(bindId, merchantCode));
			result.put("resultCode", ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			log.error("[淘宝导入]商家编码:{}-获取优购、淘宝属性异常.", merchantCode, e);
			result.put("msg", "获取优购、淘宝属性异常");
			result.put("resultCode", ResultCode.ERROR.getCode());
		}

		return result.toString();
	}

	/**
	 * 获取淘宝分类属性值
	 * 
	 * @param pid
	 * @param cid
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getTaobaoItemProVal")
	public String getTaobaoItemProVal(Long pid, Long cid, String catBindId, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		try {
			result.put("proVals", taobaoBasicService.findTaobaoItemProVal(pid, cid, merchantCode, catBindId));
			result.put("resultCode", ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			log.error("[淘宝导入]商家编码:{}-获取淘宝属性值异常.", merchantCode, e);
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "获取淘宝属性值异常");
		}
		return result.toString();
	}

	@ResponseBody
	@RequestMapping("/bindTaobaoYougouItemProAndVal")
	public String bindTaobaoYougouItemProAndVal(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		try {
			taobaoBasicService.bindTaobaoYougouItemProAndVal(request);
			result.put("resultCode", ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			log.error("[淘宝导入]商家编码:{}-绑定分类属性属性值异常.", merchantCode, e);
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "绑定分类属性属性值异常");
		}
		return result.toString();
	}

	@RequestMapping("/bindTaobaoYougouItemProAndValSuccess")
	public String bindTaobaoYougouItemProAndValSuccess(HttpServletRequest request) {
		return "/manage/taobao/yougou_taobao_item_prop_success";
	}

	@RequestMapping("/goUpdateTaobaoYougouPro")
	public String goUpdateTaobaoYougouPro(ModelMap modelMap, String bindId) {
		modelMap.put("bindId", bindId);
		TaobaoYougouItemCat cat = taobaoBasicService.getTaobaoYougouItemCatById(bindId);
		Category category = commodityBaseApiService.getCategoryByNo(cat.getYougouCatNo());
		modelMap.put("catId", category.getId());
		modelMap.put("taobaoCid", cat.getTaobaoCid());
		return "/manage/taobao/yougou_taobao_item_cat_pro_bind";
	}

	/**
	 * 下载淘宝商品
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/downloadItem")
	public String downloadItem(HttpServletRequest request, String ids) {
		JSONObject result = new JSONObject();
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		String operater = YmcThreadLocalHolder.getOperater();
		try {

			Map<String, Integer> resultTotal = taobaoBasicService.downloadItem(ids, merchantCode, operater);
			result.put("resultTotal", resultTotal);
			result.put("resultCode", ResultCode.SUCCESS.getCode());
		} catch (BusinessException e) {
			log.error("[淘宝导入]商家编码:{}-下载淘宝商品异常.", merchantCode, e);
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", e.getMessage());
		} catch (Exception e) {
			log.error("[淘宝导入]商家编码:{}-下载淘宝商品异常.", merchantCode, e);
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "下载淘宝商品异常");
		}
		return result.toString();
	}
}
