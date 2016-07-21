package com.yougou.kaidian.taobao.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.taobao.exception.BusinessException;
import com.yougou.kaidian.taobao.model.pojo.ItemPropTemplate;
import com.yougou.kaidian.taobao.model.pojo.ItemTemplate;
import com.yougou.kaidian.taobao.service.IItemTemplateService;

@Controller
@RequestMapping("/commodity")
public class CommodityTemplateController {

	private static final Logger log = LoggerFactory.getLogger(CommodityTemplateController.class);

	@Resource
	private IItemTemplateService itemTemplateService;

	@ResponseBody
	@RequestMapping("/saveTemplate")
	public String saveTemplate(String[] propItemNo, String[] propItemName, String[] propValueNo,
			String[] propValueName, String commodityName, String catNo, HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject result = new JSONObject();
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		try {
			itemTemplateService.addItemTemplate(propItemNo, propItemName, propValueNo, propValueName, commodityName,
					catNo, merchantCode);
			result.put("resultCode", "200");
		} catch (BusinessException e) {
			result.put("resultCode", "500");
			result.put("msg", e.getMessage());
			log.error("[淘宝导入]商家编码:{}-保存模板失败.", merchantCode, e);
		} catch (Exception e) {
			result.put("resultCode", "500");
			result.put("msg", "保存模板失败");
			log.error("[淘宝导入]商家编码:{}-保存模板失败.", merchantCode, e);
		}
		return result.toString();
	}

	@RequestMapping("itemTemplate")
	public String itemTemplate(ModelMap modelMap, String cateNames, String cateNo, HttpServletRequest request)
			throws UnsupportedEncodingException {
		cateNames = URLDecoder.decode(cateNames, "UTF-8");
		modelMap.put("cateNames", cateNames);
		modelMap.put("cateNo", cateNo);
		return "/manage_unless/commodity/item_template";
	}

	@RequestMapping("itemTpl")
	public String itemTpl(ModelMap modelMap, String cateNames, String catNo, String from, HttpServletRequest request)
			throws UnsupportedEncodingException {
		cateNames = URLDecoder.decode(cateNames, "UTF-8");
		modelMap.put("cateNames", cateNames);
		modelMap.put("cateNo", catNo);
		// 来源，是从哪里点击进去的
		// 目前有selcat，表从选择分类页面点击进来
		// 有newui，表从仿淘宝填写商品信息页面点击进来
		modelMap.put("from", from);
		return "/manage_unless/commodity/item_tpl";
	}

	@RequestMapping("getItemTemplateList")
	public String getItemTemplateList(ModelMap modelMap, String cateNo, String key, HttpServletRequest request,
			Query query) {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		PageFinder<ItemTemplate> pageFinder = itemTemplateService
				.findItemTemplateList(merchantCode, cateNo, key, query);
		modelMap.put("pageFinder", pageFinder);
		return "/manage_unless/commodity/item_templatet_list";
	}

	@ResponseBody
	@RequestMapping("/delTemplate")
	public String delTemplate(String id, HttpServletRequest request, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		try {
			itemTemplateService.deleteItemTemplate(id, merchantCode);
			result.put("resultCode", "200");
		} catch (BusinessException e) {
			result.put("resultCode", "500");
			result.put("msg", e.getMessage());
			log.error("[淘宝导入]商家编码:{}-删除模板失败.", merchantCode, e);
		} catch (Exception e) {
			result.put("resultCode", "500");
			result.put("msg", "删除失败");
			log.error("[淘宝导入]商家编码:{}-删除模板失败.", merchantCode, e);
		}
		return result.toString();
	}

	@ResponseBody
	@RequestMapping("/useTemplate")
	public String useTemplate(String id, HttpServletRequest request, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		try {
			List<ItemPropTemplate> list = itemTemplateService.findItemPropTemplateList(merchantCode, id);
			result.put("list", list);
			result.put("resultCode", "200");
		} catch (Exception e) {
			result.put("resultCode", "500");
			result.put("msg", "加载模板失败");
			log.error("[淘宝导入]商家编码:{}-加载模板失败.", merchantCode, e);
		}
		return result.toString();
	}
}
