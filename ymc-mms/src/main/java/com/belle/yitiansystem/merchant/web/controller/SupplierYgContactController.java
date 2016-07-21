package com.belle.yitiansystem.merchant.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.belle.infrastructure.orm.basedao.Query;
import com.belle.yitiansystem.merchant.model.pojo.SupplierYgContact;
import com.belle.yitiansystem.merchant.service.ISupplierYgContactService;

/**
 * 商家专员管理
 * 
 * @author luo.hl
 * 
 */
@Controller
@RequestMapping("/yitiansystem/merchants/supplierContact")
public class SupplierYgContactController {

    private static final Logger logger = Logger.getLogger(SupplierYgContactController.class);

    @Resource
	private ISupplierYgContactService supplierYgContactService;

    /**
	 * 跳转到商家专员列表
	 * 
	 * @param query
	 * @param modelMap
	 * @param merchantsVo
	 * @param isCanAssign
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("to_contact_list")
	public String toContactList(Query query, ModelMap modelMap, SupplierYgContact supplierYgContact, String isCanAssign, String merchantName, String brand) throws Exception {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("merchantName", merchantName);
			map.put("brands", brand);
			modelMap.addAttribute("pageFinder", supplierYgContactService.getSupplierYgContactList(supplierYgContact, query, map));
			modelMap.addAttribute("supplierYgContact", supplierYgContact);
			modelMap.addAttribute("merchantName", merchantName);
			modelMap.addAttribute("brand", brand);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "yitiansystem/merchants/supplier_yg_contact_list";
	}
	
	/**
	 * 跳转到新增页面
	 * 
	 * @param modelMap
	 * @param flag
	 * @param supplierId
	 * @return
	 */
	@RequestMapping("to_save_yg_contact")
	public String to_SupplierContactt(ModelMap modelMap, String userId,
			String supplierId) {
		if (StringUtils.isNotBlank(userId)) {
			SupplierYgContact contact = supplierYgContactService
					.getSupplierYgContactById(userId);
			modelMap.put("contact", contact);
		}
		return "yitiansystem/merchants/add_supplier_yg_contact";
	}

	@ResponseBody
	@RequestMapping("saveContact")
	public String saveContact(ModelMap modelMap,
			SupplierYgContact supplierYgContact) {
		JSONObject json = new JSONObject();

		try{
			supplierYgContactService.saveYgContact(supplierYgContact);
		}catch(Exception e){
			logger.error("保存货品负责人出错：" + e.getMessage(), e);
			json.put("resultCode", "500");
			json.put("msg", e.getMessage());
			return json.toString();
		}
		json.put("resultCode", "200");
		return json.toString();
	}

	@RequestMapping("showSupplierInfo")
	public String showSupplierInfo(ModelMap modelMap, String userId) {
		try {
			List<Map<String, String>> listOut = supplierYgContactService.getSupplierSpOut(userId);
			List<Map<String, String>> listIn = supplierYgContactService.getSupplierSpIn(userId);
			modelMap.put("listOut", listOut);
			modelMap.put("userId", userId);
			modelMap.put("listIn", listIn);
		} catch (Exception e) {
			logger.error("查询商家信息出错：" + e.getMessage(), e);
		}
		return "yitiansystem/merchants/supplier_yg_contact_manage";
	}

	@ResponseBody
	@RequestMapping("bindContact")
	public String bindContact(ModelMap modelMap, String supplierCode,
			String userId) {
		JSONObject json = new JSONObject();
		try {
			supplierYgContactService.bindContact(supplierCode, userId);
		} catch (Exception e) {
			logger.error("绑定商家出错：" + e.getMessage(), e);
			json.put("resultCode", "500");
			json.put("msg", e.getMessage());
			return json.toString();
		}
		json.put("resultCode", "200");
		return json.toString();
	}

}
