package com.belle.yitiansystem.merchant.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/yitiansystem/merchants/taobao")
public class TaobaoController {

	private final String taobaoFtlPath = "yitiansystem/merchants/taobao/";
	
	/**
	 * 查询淘宝开放平台APPKEY信息
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("goTaobaoTopappList")
	public String goTaobaoTopappList(ModelMap modelMap){
		return taobaoFtlPath+"taobao_topapp_list";
	}

	/**
	 * 跳转到创建淘宝开放平台APPKEY信息页面
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("goCreateTaobaoTopapp")
	public String goCreateTaobaoTopapp(ModelMap modelMap){
		return taobaoFtlPath+"taobao_topapp_create";
	}
	
	/**
	 * 跳转到修改淘宝开放平台APPKEY信息页面
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("goModifyTaobaoTopapp")
	public String goModifyTaobaoTopapp(ModelMap modelMap){
		return taobaoFtlPath+"taobao_topapp_modify";
	}

	/**
	 * 保存淘宝开放平台APPKEY信息页面
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("toSaveTaobaoTopapp")
	public String toSaveTaobaoTopapp(ModelMap modelMap){
		return taobaoFtlPath+"taobao_message";
	}

	/**
	 * 查询商家淘宝授权信息
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("goTaobaoAuthinfoList")
	public String goTaobaoAuthinfoList(ModelMap modelMap){
		return taobaoFtlPath+"taobao_authinfo_list";
	}
	
	/**
	 * 将商家淘宝授权置为是否可用
	 * @param modelMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping("toUpdateTaobaoAuthinfoIsUseble")
	public void toUpdateTaobaoAuthinfoIsUseble(ModelMap modelMap){
		return ;
	}

}
