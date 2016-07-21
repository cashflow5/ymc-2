package com.yougou.kaidian.taobao.web;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.framework.base.BaseController;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.taobao.enums.ResultCode;
import com.yougou.kaidian.taobao.exception.BusinessException;
import com.yougou.kaidian.taobao.model.TaobaoItemExtendDto;
import com.yougou.kaidian.taobao.model.TaobaoShop;
import com.yougou.kaidian.taobao.service.ITaobaoAuthService;
import com.yougou.kaidian.taobao.service.ITaobaoItemService;

@Controller
@RequestMapping("/taobao")
public class TaobaoRecycleController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(TaobaoRecycleController.class);

	private final String TAOBAOFTLPATH = "/manage/taobao/";
	@Resource
	private ITaobaoAuthService taobaoAuthService;
	@Resource
	private ITaobaoItemService taobaoItemService;

	@RequestMapping("/goRecycle")
	public String goTaobaoShop(ModelMap modelMap, HttpServletRequest request, Query query) {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		try {
			Map<String, Object> params = this.builderParams(request, false);
			params.put("merchantCode", merchantCode);
			params.put("status", "-1");// -1已删除
			List<TaobaoShop> taobaoShop = taobaoAuthService.getAllTaobaoShopListByStatus(merchantCode, "3");
			modelMap.put("params", params);
			modelMap.put("taobaoShop", taobaoShop);
		} catch (UnsupportedEncodingException e) {
			log.error("[淘宝导入]商家编码:{}-查询淘宝商品产生异常.", merchantCode, e);
		}
		return TAOBAOFTLPATH + "taobao_recycle";
	}

	@RequestMapping("/getRecycleList")
	public String getTaobaoRecycleList(ModelMap modelMap, HttpServletRequest request, Query query)
			throws UnsupportedEncodingException {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		query.setPageSize(20);
		String status = "-1";
		Map<String, Object> params = this.builderParams(request, false);
		params.put("merchantCode", merchantCode);
		params.put("status", status);// -1已删除
		buildListFromString4Params(params);
		PageFinder<TaobaoItemExtendDto> pageFinder = taobaoItemService.getTaobaoItem(params, query);
		modelMap.put("pageFinder", pageFinder);
		return "/manage_unless/taobao/taobao_recycle_list";
	}

	private void buildListFromString4Params(Map<String, Object> params) {
		if (null != params.get("yougouStyleNo") && !StringUtils.isEmpty((String) params.get("yougouStyleNo"))) {
			String yougouStyleNo = (String) params.get("yougouStyleNo");
			List<String> yougouStyleNoList = Arrays.asList(yougouStyleNo.split(","));
			params.put("yougouStyleNoList", yougouStyleNoList);
		}
		if (null != params.get("yougouSupplierCode") && !StringUtils.isEmpty((String) params.get("yougouSupplierCode"))) {
			String yougouSupplierCode = (String) params.get("yougouSupplierCode");
			List<String> yougouSupplierCodeList = Arrays.asList(yougouSupplierCode.split(","));
			params.put("yougouSupplierCodeList", yougouSupplierCodeList);
		}
		if (null != params.get("yougouThirdPartyCode")
				&& !StringUtils.isEmpty((String) params.get("yougouThirdPartyCode"))) {
			String yougouThirdPartyCode = (String) params.get("yougouThirdPartyCode");
			List<String> yougouThirdPartyCodeList = Arrays.asList(yougouThirdPartyCode.split(","));
			params.put("yougouThirdPartyCodeList", yougouThirdPartyCodeList);
		}
	}

	/**
	 * reductTaobao:还原淘宝商品
	 * 
	 * @author li.n1
	 * @return
	 * @since JDK 1.6
	 */
	@ResponseBody
	@RequestMapping("/reductTaobao")
	public String toReductTaobao(ModelMap modelMap, String ids, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		try {
			taobaoItemService.updateTaobaoItemExtend(ids, merchantCode, "0");
			result.put("resultCode", ResultCode.SUCCESS.getCode());
		} catch (Exception e) {
			log.error("[淘宝导入]商家编码:{}-修改淘宝商品异常.", merchantCode, e);
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "系统异常");
		}
		return result.toString();
	}

	/**
	 * toDeleteRecycle:彻底删除商品
	 * 
	 * @author li.n1
	 * @param modelMap
	 * @param ids
	 * @param request
	 * @return
	 * @since JDK 1.6
	 */
	@ResponseBody
	@RequestMapping("/deleteRecycle")
	public String toDeleteRecycle(ModelMap modelMap, String ids, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		try {
			if (StringUtils.isEmpty(ids)) {
				log.error("[淘宝导入]商家编码:{}-彻底删除淘宝商品异常,商品EXTEND_ID为空.", merchantCode);
				throw new BusinessException("参数错误");
			}
			taobaoItemService.deleteTaobaoItemForever(ids.split(","), merchantCode);
			result.put("resultCode", ResultCode.SUCCESS.getCode());
		} catch (BusinessException e) {
			log.error("[淘宝导入]商家编码:{}-彻底删除淘宝商品异常.", merchantCode, e);
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", e.getMessage());
		} catch (Exception e) {
			log.error("[淘宝导入]商家编码:{}-彻底删除淘宝商品异常.", merchantCode, e);
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "系统异常");
		}
		return result.toString();
	}
}
