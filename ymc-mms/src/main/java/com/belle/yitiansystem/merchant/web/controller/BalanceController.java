/**
 * 
 */
package com.belle.yitiansystem.merchant.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.belle.yitiansystem.systemmgmt.util.SysconfigProperties;

/**
 * 结算管理Controller
 * 
 * @author huang.tao
 *
 */
@Controller
@RequestMapping("/yitiansystem/merchants/balance")
public class BalanceController {
	
	private Logger logger = Logger.getLogger(BalanceController.class);
	
	@Resource
	private SysconfigProperties sysconfig;
	
	private final static String BALANCE_BILL_URL = "/finance/merchants/balancebill/index.sc";
	
	private final static String REDEPLOYPRICE_BILL_URL = "/finance/merchants/redeployprice/index.sc?status=0&queryFlag=only";
	
	@RequestMapping("to_balance")
	public ModelAndView forwardBalancebills(HttpServletRequest request) throws Exception {
        String url = getFmsHostURL() + BALANCE_BILL_URL;
        logger.info("招商系统跳转至财务结算单页url：" + url);
		return new ModelAndView("redirect:" + url);
	}
	
	@RequestMapping("to_Redeployprice")
	public ModelAndView forwardRedeploypricebills(HttpServletRequest request) throws Exception {
        String url = getFmsHostURL() + REDEPLOYPRICE_BILL_URL;
        logger.info("招商系统跳转至财务调价单页url：" + url);
		return new ModelAndView("redirect:" + url);
	}
	
	/**
	 * 获取fms配置的服务器地址
	 * 
	 * @return eg. fms.host=http://10.0.30.72:8080/bms
	 * @throws Exception
	 */
	private String getFmsHostURL() throws Exception {
        String fmshost = sysconfig.getFmshost();
        if (StringUtils.isBlank(fmshost) || fmshost.contains("$")) {
        	fmshost = sysconfig.getFmshost();
        }
        
        return fmshost;
	}
}
