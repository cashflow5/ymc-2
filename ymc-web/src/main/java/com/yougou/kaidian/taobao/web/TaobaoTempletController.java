package com.yougou.kaidian.taobao.web;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yougou.kaidian.commodity.service.IMerchantCommodityService;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.framework.base.BaseController;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.taobao.enums.ResultCode;
import com.yougou.kaidian.taobao.model.TaobaoYougouItemCatTemplet;
import com.yougou.kaidian.taobao.service.ITaobaoBasisService;
import com.yougou.kaidian.taobao.service.ITaobaoTempletService;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.api.ICommodityMerchantApiService;
import com.yougou.pc.model.category.Category;
import com.yougou.pc.model.prop.PropItem;

@Controller
@RequestMapping("/taobao")
public class TaobaoTempletController extends BaseController {

	private final String taobaoFtlPath = "/manage/taobao/";

	private static final Logger log = LoggerFactory.getLogger(TaobaoTempletController.class);

	@Resource
	private ITaobaoBasisService taobaoBasicService;

	@Resource
	private ITaobaoTempletService taobaoTempletService;

	@Resource
	ICommodityMerchantApiService commodityApi;

	@Resource
	private ICommodityBaseApiService commodityBaseApiService;

	@Resource
	private IMerchantCommodityService merchantCommodityService;

	/**
	 * 根据session中的merchantCode获取未绑定的类目 跳转优购淘宝类目绑定信息页面
	 * 
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/goYougouTaobaoTemplet")
	public String goYougouTaobaoTemplet(ModelMap modelMap, HttpServletRequest request, Query query)
			throws UnsupportedEncodingException {
		modelMap.addAttribute("yougouTree",
				taobaoBasicService.findYougouItemCatZTree(YmcThreadLocalHolder.getMerchantCode(), ""));
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("parentCid", "0");
		modelMap.put("taobaoTree", taobaoTempletService.selectTaobaoItemCatList(params));
		return taobaoFtlPath + "yougou_taobao_item_cat_templet";
	}

	@RequestMapping("/getYougouTaobaoItemCatTempletList")
	public String getYougouTaobaoItemCatTempletList(ModelMap modelMap, HttpServletRequest request, Query query)
			throws UnsupportedEncodingException {
		Map<String, Object> params = this.builderParams(request, false);
		if (params.get("catId") != null && !StringUtils.isEmpty(params.get("catId").toString())) {
			Category category = commodityBaseApiService.getCategoryById(params.get("catId").toString());
			params.put("yougouCatNo", category.getCatNo());
		}
		PageFinder<TaobaoYougouItemCatTemplet> pageFinder = taobaoTempletService.findTaobaoYougouItemCatTemplet(params,
				YmcThreadLocalHolder.getMerchantCode(), query);
		modelMap.addAttribute("pageFinder", pageFinder);
		return "/manage_unless/taobao/yougou_taobao_item_cat_templet_list";
	}

	@ResponseBody
	@RequestMapping("/getTaobaoItemTempletTree")
	public String getTaobaoItemTempletTree(HttpServletRequest request, String nickId) {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		JSONObject result = new JSONObject();
		try {
			result.put("taobaoTree", taobaoTempletService.selectTaobaoItemCatList(this.builderParams(request, false)));
			result.put("resultCode", ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			log.error("[淘宝导入]商家编码:{}-获取淘宝分类异常.", merchantCode, e);
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "系统异常!");
		}
		return result.toString();
	}

	@ResponseBody
	@RequestMapping("/getYougouItemTempletTree")
	public String getYougouItemTempletTree(HttpServletRequest request, String nickId, String structName) {
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

	@RequestMapping("/goYougouTaobaoItemProTemplet")
	public String goYougouTaobaoItemProTemplet(HttpServletRequest request, ModelMap modelMap, String catId,
			String bindId, String catNo) throws Exception {
		if (StringUtils.isEmpty(catId) && StringUtils.isEmpty(catNo) || StringUtils.isEmpty(bindId)) {
			log.error("请求参数错误,分类ID,分类No,绑定Id不能为空！catId:{},catNo:{},bindId:{}", catId, catNo, bindId);
			throw new Exception("请求参数错误,分类ID，分类No ,绑定Id 不能为空！");
		}
		if (StringUtils.isEmpty(catId) && !StringUtils.isEmpty(catNo)) {
			Category category = commodityBaseApiService.getCategoryByNo(catNo);
			if (null == category) {
				log.error("category is  null.分类不存在   catNo:{}" , catNo);
				throw new Exception("分类不存在");
			}
			catId = category.getId();
		}
		TaobaoYougouItemCatTemplet catTemplet = taobaoTempletService.findTaobaoYougouItemCatById(bindId);
		if (null == catTemplet) {
			log.error("分类绑定不存在  bindId:{}", bindId);
			throw new Exception("分类绑定不存在");
		}
		modelMap.put("catTemplet", catTemplet);
		modelMap.put("catId", catId);
		modelMap.put("taobaoCid", catTemplet.getTaobaoCid().toString());
		return taobaoFtlPath + "yougou_taobao_item_cat_pro_templet";
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
	@RequestMapping("/getYougouTaobaoProTemplet")
	public String getYougouTaobaoProTemplet(String catId, String taobaoCid, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			List<PropItem> yougouPropList = merchantCommodityService.getPropMsgByCatIdNew(catId, false);
			result.put("yougouPropList", yougouPropList);
			result.put("taobaoProList", taobaoTempletService.findTaobaoYougouItemCatPropTempletAndValues(this
					.builderParams(request, false)));
			result.put("resultCode", "200");
		} catch (Exception e) {
			log.error("[淘宝导入]商家编码:{}-获取优购、淘宝属性异常.", YmcThreadLocalHolder.getMerchantCode(), e);
			result.put("msg", "获取优购、淘宝属性异常");
			result.put("resultCode", "500");
		}
		return result.toString();
	}

}
