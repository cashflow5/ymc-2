package com.yougou.kaidian.taobao.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.kaidian.framework.base.BaseController;
import com.yougou.kaidian.framework.util.TaobaoUtil;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.taobao.enums.ResultCode;
import com.yougou.kaidian.taobao.model.TaobaoShop;
import com.yougou.kaidian.taobao.service.ITaobaoAuthService;
import com.yougou.ordercenter.common.DateUtil;

import freemarker.ext.beans.BeansWrapper;

@Controller
@RequestMapping("/taobao")
public class TaobaoAuthController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(TaobaoAuthController.class);
	private final String taobaoFtlPath = "/manage/taobao/";

	@Resource
	private ITaobaoAuthService taobaoAuthService;

	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	private String taobaoAuthCallback = "merchant.taobao.auth";

	/**
	 * 查询商家淘宝店铺
	 * 
	 * @param modelMap
	 * @param request
	 * @param query
	 * @return
	 */
	@RequestMapping("/goTaobaoShop")
	public String goTaobaoShop(ModelMap modelMap, HttpServletRequest request, Query query) {
		modelMap.addAttribute("statics", BeansWrapper.getDefaultInstance().getStaticModels());
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		PageFinder<TaobaoShop> pageFinder = taobaoAuthService.getTaobaoShopList(query, merchantCode);
		modelMap.put("pageFinder", pageFinder);
		return "/manage/taobao/taobao_shop";
	}

	@RequestMapping("/goNewTaobaoShop")
	public String goNewTaobaoShop(ModelMap modelMap, HttpServletRequest request) {
		return "/manage_unless/taobao/add_taobao_shop";
	}

	/**
	 * 修改淘宝店铺信息
	 * 
	 * @param modelMap
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/goUpdateTaobaoShop")
	public String goUpdateTaobaoShop(ModelMap modelMap, HttpServletRequest request, String id) {
		TaobaoShop taobaoShop = taobaoAuthService.getTaobaoShopByID(id);
		modelMap.put("taobaoShop", taobaoShop);
		return "/manage_unless/taobao/edit_taobao_shop";
	}

	/**
	 * 保存新的淘宝店铺
	 * 
	 * @param modelMap
	 * @param request
	 * @param taobaoShop
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveTaobaoShop")
	public String saveTaobaoShop(ModelMap modelMap, HttpServletRequest request, TaobaoShop taobaoShop) {
		JSONObject jsonObject = new JSONObject();
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		String loginName = YmcThreadLocalHolder.getOperater();
		taobaoShop.setId(UUIDGenerator.getUUID());
		taobaoShop.setMerchantCode(merchantCode);
		taobaoShop.setOperater(loginName);
		taobaoShop.setStatus(0);
		taobaoShop.setOperated(DateUtil.getDateTime(new Date()));
		try {
			taobaoAuthService.insertTaobaoShop(taobaoShop);
			jsonObject.put("success", true);
			jsonObject.put("message", "");
		} catch (Exception e) {
			log.error("[淘宝导入]商家编码:{}-保存淘宝店铺信息异常.", merchantCode, e);
			jsonObject.put("success", false);
			jsonObject.put("message", "保存淘宝店铺信息异常.请联系管理员!");
		}
		return jsonObject.toString();
	}

	/**
	 * 更新淘宝店铺信息
	 * 
	 * @param modelMap
	 * @param request
	 * @param taobaoShop
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateTaobaoShop")
	public String updateTaobaoShop(ModelMap modelMap, HttpServletRequest request, TaobaoShop taobaoShop) {
		JSONObject jsonObject = new JSONObject();
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		String loginName = YmcThreadLocalHolder.getOperater();
		taobaoShop.setMerchantCode(merchantCode);
		taobaoShop.setOperater(loginName);
		taobaoShop.setOperated(DateUtil.getDateTime(new Date()));
		taobaoShop.setStatus(null);
		try {
			taobaoAuthService.updateTaobaoShop(taobaoShop);
			jsonObject.put("success", true);
			jsonObject.put("message", "");
		} catch (Exception e) {
			log.error("[淘宝导入]商家编码:{}-修改淘宝店铺信息异常.", merchantCode, e);
			jsonObject.put("success", false);
			jsonObject.put("message", "修改淘宝店铺信息异常.请联系管理员!");
		}
		return jsonObject.toString();
	}

	/**
	 * 检验淘宝店铺名称是否存在
	 * 
	 * @param modelMap
	 * @param request
	 * @param taobaoShopName
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkTaobaoShop")
	public String checkTaobaoShop(ModelMap modelMap, HttpServletRequest request, String taobaoShopName) {
		JSONObject jsonObject = new JSONObject();
		if (taobaoAuthService.checkTaobaoShopByName(taobaoShopName)) {
			jsonObject.put("success", true);
		} else {
			jsonObject.put("success", false);
		}
		return jsonObject.toString();
	}

	/**
	 * 修改淘宝店铺状态
	 * 
	 * @param modelMap
	 * @param request
	 * @param id
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateTaobaoShopStatus")
	public String updateTaobaoShopStatus(ModelMap modelMap, HttpServletRequest request, String id, String status) {
		JSONObject jsonObject = new JSONObject();
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		if (StringUtils.isBlank(id) || StringUtils.isBlank(status)) {
			jsonObject.put("success", false);
			jsonObject.put("message", "输入参数不完整，不允许空值，请检查.");
		} else {
			try {
				Map<String, Object> paraMap = new HashMap<String, Object>();
				paraMap.put("status", status);
				paraMap.put("id", id);
				taobaoAuthService.updateTaobaoShopStatus(paraMap);
				jsonObject.put("success", true);
				jsonObject.put("message", "");
			} catch (Exception e) {
				log.error("[淘宝导入]商家编码:{}-保存淘宝店铺信息异常.", merchantCode, e);
				jsonObject.put("success", false);
				jsonObject.put("message", "保存淘宝店铺信息异常.");
			}
		}
		return jsonObject.toString();
	}

	/**
	 * 跳转淘宝授权页面
	 * 
	 * @return
	 */
	@RequestMapping("/goTaobaoAuthContainer")
	public String goTaobaoAuthContainer() {

		return taobaoFtlPath + "taobao_auth_container";
	}

	/**
	 * 商家淘宝授权完成后回调地址方法
	 * 
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping("/toTaobaoAuthCallback")
	public String toTaobaoAuthCallback(ModelMap modelMap, HttpServletRequest request) {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		String loginName = YmcThreadLocalHolder.getOperater();
		try {
			Map<String, Object> params = this.builderParams(request, false);

			String topParameters = request.getParameter("top_parameters");
			Map<String, String> mapTopParamerters = TaobaoUtil.getParametersMap(topParameters);
			String visitorId = mapTopParamerters.get("visitor_id");
			String visitorNick = mapTopParamerters.get("visitor_nick");
			String expiresIn = mapTopParamerters.get("expires_in");
			String refreshToken = mapTopParamerters.get("refresh_token");
			if (StringUtils.isBlank(visitorNick)) {
				modelMap.put("message", "授权失败,不正确的授权参数,请重新授权!");
				modelMap.put("success", false);
			} else {
				// 防止重复执行
				Object refreshTokenCache = this.redisTemplate.opsForHash().get(taobaoAuthCallback,
						merchantCode + "_" + visitorNick);
				if (refreshTokenCache != null && refreshTokenCache.equals(refreshToken)) {
					modelMap.put("message", "授权失败,请勿重复授权!");
					modelMap.put("success", false);
				} else {
					this.redisTemplate.opsForHash().put(taobaoAuthCallback, merchantCode + "_" + visitorNick,
							refreshToken);
					this.redisTemplate.expire(taobaoAuthCallback, 30, TimeUnit.MINUTES);// 设置属性时，重新设置超时时间续命
					params.put("visitor_id", visitorId);
					params.put("visitor_nick", visitorNick);
					params.put("expires_in", expiresIn);
					logger.info("[淘宝导入]商家编码:{}-用户{}-商家淘宝授权.参数{}", merchantCode, loginName, params);
					boolean isSccuss = taobaoAuthService.authorization(params, merchantCode, loginName);
					if (isSccuss) {
						// 异步导入数据
						taobaoAuthService.importTaobaoBasicDataToYougou(params, merchantCode, loginName);
						modelMap.put("message", visitorNick + ":授权成功!");
						modelMap.put("success", true);
					} else {
						modelMap.put("message", visitorNick + ":授权失败,授权账户与店铺不能匹配,请检查!");
						modelMap.put("success", false);
					}
				}
			}

		} catch (Exception e) {
			log.error("[淘宝导入]商家编码:{}-淘宝店铺授权异常.", merchantCode, e);
			modelMap.put("message", "授权失败,后台服务器异常!");
			modelMap.put("success", false);
		}
		return taobaoFtlPath + "taobao_callback_messge";
	}

	/**
	 * 按照一级分类编码拉取其子级分类、属性、属性值
	 * 
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping("/toTaobaoGetCatPropData")
	@ResponseBody
	public String toTaobaoGetCatPropData(ModelMap modelMap, HttpServletRequest request) {
		String cids = request.getParameter("cids");
		String sessionKey = request.getParameter("sessionKey");
		String operater = request.getParameter("operater");
		JSONObject result = new JSONObject();
		if (sessionKey == null || "".equals(sessionKey)) {
			sessionKey = "ZXhwaXJlc19pbj0zMTUzNjAwMSZpZnJhbWU9MSZyMV9leHBpcmVzX2luPTMxNTM2MDAxJnIyX2V4cGlyZXNfaW49MzE1MzYwMDEmcmVfZXhwaXJlc19pbj0wJnJlZnJlc2hfdG9rZW49NjEwMDExODNmZjBiYzIwMTlmMTcyYWUzMzYxYzkyNmRjNmE5MTZmMWJlZWFkMWExNjU3ODQ3ODQyJnN1Yl90YW9iYW9fdXNlcl9pZD0xNjU3ODQ3ODQyJnN1Yl90YW9iYW9fdXNlcl9uaWNrPTE1t9bW08bsvaK16jq8vMr1JnRzPTE0MDk3OTU1MDI2MDgmdmlzaXRvcl9pZD0xNjA3ODI4MzI5JnZpc2l0b3Jfbmljaz0xNbfW1tPG7L2iteomdzFfZXhwaXJlc19pbj0zMTUzNjAwMSZ3Ml9leHBpcmVzX2luPTMxNTM2MDAx";
		}
		try {
			String impResult = taobaoAuthService.importTaobaoCatPropToYougou(sessionKey, "yougou", operater, cids);
			result.put("resultCode", ResultCode.SUCCESS.getCode());
			result.put("msg", impResult);
			log.info("淘宝一级分类为{},抓取结果为{}", cids, impResult);
		} catch (IllegalAccessException ex) {
			log.error("[淘宝导入]商家编码:{}-获取淘宝分类属性异常.", YmcThreadLocalHolder.getMerchantCode(), ex);
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "参数异常，请找优购系统支持人员！");
		}
		return result.toString();
	}

	@RequestMapping("/getBaseData")
	public String getBaseData(ModelMap modelMap, HttpServletRequest request) {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		String loginName = YmcThreadLocalHolder.getOperater();
		try {
			Map<String, Object> params = this.builderParams(request, false);
			params.put("top_session", "6102a02ecc1a9514cbcb4c0e4cc74b7de4ecc62871a1c99666547549");
			params.put("top_appkey", "12273709");
			params.put("visitor_id", "666547549");
			taobaoAuthService.importTaobaoBasicDataToYougou(params, merchantCode, loginName);

		} catch (Exception e) {
			log.error("[淘宝导入]商家编码:{}-淘宝店铺授权异常.", merchantCode, e);
			modelMap.put("message", "授权失败,后台服务器异常!");
			modelMap.put("success", false);
		}
		return null;
	}
}
