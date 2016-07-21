package com.belle.yitiansystem.merchant.web.controller;  

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.yitiansystem.merchant.constant.PunishConstant;
import com.belle.yitiansystem.merchant.model.pojo.MerchantOperationLog;
import com.belle.yitiansystem.merchant.model.pojo.MerchantOperationLog.OperationType;
import com.belle.yitiansystem.merchant.model.pojo.PunishSettle;
import com.belle.yitiansystem.merchant.model.vo.PunishSettleVo;
import com.belle.yitiansystem.merchant.service.IMerchantOperationLogService;
import com.belle.yitiansystem.merchant.service.IPunishSettleService;

/**
 * ClassName: PunishSettleController
 * Desc: 商家处罚结算
 * date: 2014-12-26 下午3:01:55
 * @author li.n1 
 * @since JDK 1.6
 */
@Controller
@RequestMapping("/yitiansystem/merchants/punishsettle")
public class PunishSettleController extends BaseController{
	private final String BASE_PATH = "yitiansystem/merchants/punish/";
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PunishSettleController.class);
	@Resource
	private IPunishSettleService punishSettleService;
	@Resource
	private IMerchantOperationLogService merchantOperationLogService;
	
	// 数据绑定
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(PunishConstant.DATE_FORMAT_DATETIME);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateTimeFormat, true));
	}
	
	@RequestMapping("/to_punishsettle")
	public String to_punishSettleList(ModelMap modelMap,Query query,PunishSettleVo vo) {
		PageFinder<PunishSettle> pageFinder = punishSettleService.getPunishSettle(query,vo);
		modelMap.addAttribute("pageFinder", pageFinder);
		modelMap.addAttribute("punishSettleVo", vo);
		return BASE_PATH + "merchants_punishsettle_list";
	}
	
	@ResponseBody
	@RequestMapping("/submitPunishOrder")
	public String submitPunishOrder(@RequestParam("id") String id,HttpServletRequest request){
		String validPerson = GetSessionUtil.getSystemUser(request).getUsername();
		PunishSettle settle = punishSettleService.getPunishSettle(id);
		settle.setAudit(validPerson);
		settle.setAuditTime(new Date());
		settle.setStatus("1");
		boolean flag = punishSettleService.submitPunishSettle(settle);
		if(!flag){
			logger.error("提交违规订单给财务结算，id为：{}，发生错误！",id);
		}else{
			//调用财务接口，提交到财务
			//true or false ,后面接信息，成功即success，失败就失败的理由
			//Map<Boolean,String> map = FinanceforMerchantApi.submitPunishSettle(settle);
			//使用mq，数据放入mq，等财务月末获取
			punishSettleService.vaildPunishMQ(settle);
			//记录日志
			List<Map<String,Object>> punishIdList = punishSettleService.getPunishIdBySettleId(settle.getId());
			MerchantOperationLog operationLog = null;
			for(Map<String,Object> idMap : punishIdList){
				operationLog = new MerchantOperationLog();
				operationLog.setOperationNotes("违规结算提交财务");
				operationLog.setMerchantCode((String)idMap.get("punish_id"));
				operationLog.setOperator(validPerson);
				operationLog.setOperated(new Date());
				operationLog.setOperationType(OperationType.PUNISHORDER);
				try {
					merchantOperationLogService.saveMerchantOperationLog(operationLog);
				} catch (Exception e) {
					logger.error("记录提交违规订单给财务结算日志报错，违规结算id：{},报错信息：{}！", new Object[]{id,e});
				}
			}
		}
		JSONObject object = new JSONObject();
		object.put("result", Boolean.toString(flag));
		return object.toString();
	}
	
	@ResponseBody
	@RequestMapping("/cancelPunishOrder")
	public String cancelPunishSettle(@RequestParam("id") String id,HttpServletRequest request){
		boolean flag = punishSettleService.cancelPunishSettle(id);
		if(!flag){
			logger.error("取消违规订单id为：{}，发生错误！",id);
		}else{
			String validPerson = GetSessionUtil.getSystemUser(request).getUsername();
			List<Map<String,Object>> punishIdList = punishSettleService.getPunishIdBySettleId(id);
			MerchantOperationLog operationLog = null;
			for(Map<String,Object> idMap : punishIdList){
				operationLog = new MerchantOperationLog();
				operationLog.setOperationNotes("取消违规结算");
				operationLog.setMerchantCode((String)idMap.get("punish_id"));
				operationLog.setOperator(validPerson);
				operationLog.setOperated(new Date());
				operationLog.setOperationType(OperationType.PUNISHORDER);
				try {
					merchantOperationLogService.saveMerchantOperationLog(operationLog);
				} catch (Exception e) {
					logger.error("取消提交违规订单给财务结算日志报错，违规结算id：{},报错信息：{}！", new Object[]{id,e});
				}
			}
			flag = punishSettleService.updatePunishOrderSubmitStatus(id);
		}
		JSONObject object = new JSONObject();
		object.put("result", Boolean.toString(flag));
		return object.toString();
	}
}
