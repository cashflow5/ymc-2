package com.yougou.api.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.mongodb.DBObject;
import com.yougou.api.model.pojo.ApiAbnormalAlarm;
import com.yougou.api.model.vo.ApiAbnormalAlarmEmail;
import com.yougou.api.model.vo.ApiAbnormalAlarmSubject.NotifyState;
import com.yougou.api.model.vo.ApiAbnormalAlarmSubject.NotifyStyle;
import com.yougou.api.service.IApiAbnormalAlarmService;

/**
 * API异常预警控制器
 * 
 * @author 杨梦清
 * 
 */
@Controller
@RequestMapping("/openapimgt/abnormalalarm")
public class ApiAbnormalAlarmController {
	
	@Resource
	private IApiAbnormalAlarmService apiAbnormalAlarmService;
	
	@RequestMapping("/delete")
	public void deleteApiFilter(String id, HttpServletResponse response) throws Exception {
		try {
			apiAbnormalAlarmService.deleteApiAbnormalAlarmById(id);
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}
	
	@RequestMapping("/saveorupdate")
	public void saveOrUpdateApiFilter(ApiAbnormalAlarm apiAbnormalAlarm, HttpServletResponse response) throws Exception {
		try {
			if (StringUtils.isBlank(apiAbnormalAlarm.getId())) {
				apiAbnormalAlarm.setId(null);
				apiAbnormalAlarmService.saveApiAbnormalAlarm(apiAbnormalAlarm);
			} else {
				apiAbnormalAlarmService.updateApiAbnormalAlarm(apiAbnormalAlarm);
			}
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}
	
	@RequestMapping("/view")
	public ModelAndView viewApiFilter(String id) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ApiAbnormalAlarm apiAbnormalAlarm = null;
		if (StringUtils.isNotBlank(id)) {
			apiAbnormalAlarm = apiAbnormalAlarmService.getApiAbnormalAlarmById(id);
		}
		resultMap.put("apiAbnormalAlarm", apiAbnormalAlarm);
		return new ModelAndView("yitiansystem/merchant/apimgt/view_abnormal_alarm", resultMap);
	}
	
	@RequestMapping("/prelist")
	public ModelAndView preListApiFilter() throws Exception {
		return new ModelAndView("yitiansystem/merchant/apimgt/list_abnormal_alarm");
	}
	
	@RequestMapping("/list")
	public ModelAndView listApiFilter(ApiAbnormalAlarm apiAbnormalAlarm, Query query) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		DetachedCriteria criteria = DetachedCriteria.forClass(ApiAbnormalAlarm.class);
		if (StringUtils.isNotBlank(apiAbnormalAlarm.getAlarmCauserCode())) {
			criteria.add(Restrictions.eq("alarmCauserCode", apiAbnormalAlarm.getAlarmCauserCode()));
		}
		if (StringUtils.isNotBlank(apiAbnormalAlarm.getAlarmCauserClass())) {
			criteria.add(Restrictions.like("alarmCauserClass", apiAbnormalAlarm.getAlarmCauserClass(), MatchMode.ANYWHERE));
		}
		
		PageFinder<ApiAbnormalAlarm> pageFinder = apiAbnormalAlarmService.queryApiAbnormalAlarm(criteria, query);
		resultMap.put("pageFinder", pageFinder);
		resultMap.put("apiAbnormalAlarm", apiAbnormalAlarm);
		return new ModelAndView("yitiansystem/merchant/apimgt/list_abnormal_alarm", resultMap);
	}
	
	@RequestMapping("/log/prelist")
	public ModelAndView preListApiLog() throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("notifyStyles", NotifyStyle.values());
		resultMap.put("notifyStates", NotifyState.values());
		return new ModelAndView("yitiansystem/merchant/apimgt/list_abnormal_alarm_log", resultMap);
	}
	
	@RequestMapping("/log/list")
	public ModelAndView listApiLog(ApiAbnormalAlarmEmail apiAbnormalAlarm, String notifyStyle, Date fromAbnormalTime, Date toAbnormalTime, Query query) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		PageFinder<DBObject> pageFinder = apiAbnormalAlarmService.queryApiAbnormalAlarmFromMongoDB(apiAbnormalAlarm, notifyStyle, fromAbnormalTime, toAbnormalTime, query);
		resultMap.put("pageFinder", pageFinder);
		resultMap.put("apiAbnormalAlarm", apiAbnormalAlarm);
		resultMap.put("notifyStyle", notifyStyle);
		resultMap.put("fromAbnormalTime", fromAbnormalTime);
		resultMap.put("toAbnormalTime", toAbnormalTime);
		resultMap.put("notifyStyles", NotifyStyle.values());
		resultMap.put("notifyStates", NotifyState.values());
		return new ModelAndView("yitiansystem/merchant/apimgt/list_abnormal_alarm_log", resultMap);
	}
	
	@ResponseBody
	@RequestMapping("/triggering/email")
	public String triggeringApiAbnormalAlarmEmail() {
		apiAbnormalAlarmService.triggeringApiAbnormalAlarmEmail();
		return "true";
	}
	
	@ResponseBody
	@RequestMapping("/triggering/sms")
	public String triggeringApiAbnormalAlarmSms() {
		apiAbnormalAlarmService.triggeringApiAbnormalAlarmSms();
		return "true";
	}
	
	/**
	 * 解码密钥
	 * 
	 * @param token
	 * @return String
	 */
	public String decodeToken(String token) {
		return null;
	}
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}
}

