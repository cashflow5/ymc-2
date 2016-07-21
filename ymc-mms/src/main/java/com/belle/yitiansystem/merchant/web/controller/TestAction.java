package com.belle.yitiansystem.merchant.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.belle.yitiansystem.merchant.service.IContractNewApi;
import com.belle.yitiansystem.merchant.service.IMerchantOperatorApi;
import com.belle.yitiansystem.systemmgmt.util.SysconfigProperties;
import com.yougou.merchant.api.supplier.vo.YmcResult;


@Controller
@RequestMapping("/merchant/test")
public class TestAction {

	@Resource
	private IContractNewApi contractNewApi;
	@Resource
	private IMerchantOperatorApi merchantOperatorApi;
	@Resource
	private SysconfigProperties sysconfigProperties;
	@ResponseBody
	@RequestMapping("/auditContract")
	public String auditContract(ModelMap model, String contractId, HttpServletRequest request) throws Exception{
		YmcResult result = new YmcResult();
		 String supplierId = "SP20141107842307";
		 String contractNo = "0cac50a2c71e4488bc42ed4badee57aa";
		 String status = "3";
		 String operatorName = "lww";
		 result =  contractNewApi.auditContract(supplierId, contractNo, status, operatorName);
		System.out.println(result);

		
		//contractNewApi.auditContract(merchantCode, contractId, status, operatorName)
		return result.toString();
	}
	
	@ResponseBody
	@RequestMapping("/getMerchantInfo")
	public String getMerchantInfo(ModelMap model, String contractId, HttpServletRequest request) throws Exception{

		YmcResult result = new YmcResult();
		 String merchantCode = "SP20141107842307";
		 
			  result =  contractNewApi.getMerchantInfo(merchantCode) ;
		return result.toString();
	}	
	/**
	 * 更新 扩展表里四个时间
	 * @param model
	 * @param contractId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/updateDateInfo")
	public String updateDateInfo(ModelMap model, String contractId, HttpServletRequest request) throws Exception{

		YmcResult result = new YmcResult();
		 //String merchantCode = "SP20141107842307";
		 
			  result =  contractNewApi.updateDateInfo() ;
		return result.toString();
	}	
	
	@ResponseBody
	@RequestMapping("/startUpAccout")
	public String startUpAccout(ModelMap model, String contractId, HttpServletRequest request) throws Exception{

		YmcResult result = new YmcResult();
		 //String merchantCode = "SP20141107842307";
		
			  result =  merchantOperatorApi.startUpAccout("SP20141107842307", "admin",sysconfigProperties.getBaseRoleIdArray(),request);
		return result.toString();
	}	
	
	

	@ResponseBody
	@RequestMapping("/stopAccout")
	public String stopAccout(ModelMap model, String contractId, HttpServletRequest request) throws Exception{

		YmcResult result = new YmcResult();
		 //String merchantCode = "SP20141107842307";
		 
			  result =  merchantOperatorApi.stopAccout("SP20141107842307", "admin");
		return result.toString();
	}	
}
