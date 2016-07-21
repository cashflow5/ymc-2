package com.yougou.api.service;

import java.util.Date;

import org.hibernate.criterion.DetachedCriteria;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.mongodb.DBObject;
import com.yougou.api.model.pojo.ApiAbnormalAlarm;
import com.yougou.api.model.vo.ApiAbnormalAlarmSubject;

/**
 * 
 * @author 杨梦清
 * 
 */
public interface IApiAbnormalAlarmService {
	
	/**
	 * 触发 API 异常报警邮件
	 */ 
	void triggeringApiAbnormalAlarmEmail();
	
	/**
	 * 触发 API 异常报警短信
	 */
	void triggeringApiAbnormalAlarmSms();
	
	/**
	 * 获取异常报警器
	 * 
	 * @param id
	 * @return ApiAbnormalAlarm
	 * @throws Exception
	 */
	ApiAbnormalAlarm getApiAbnormalAlarmById(String id) throws Exception;
	
	/**
	 * 删除异常报警器
	 * 
	 * @param id
	 * @throws Exception
	 */
	void deleteApiAbnormalAlarmById(String id) throws Exception;
	
	/**
	 * 添加异常报警器
	 * 
	 * @param apiAbnormalAlarm
	 * @throws Exception
	 */
	void saveApiAbnormalAlarm(ApiAbnormalAlarm apiAbnormalAlarm) throws Exception;
	
	/**
	 * 修改异常报警器
	 * 
	 * @param apiAbnormalAlarm
	 * @throws Exception
	 */
	void updateApiAbnormalAlarm(ApiAbnormalAlarm apiAbnormalAlarm) throws Exception;
	
	/**
	 * 查询异常报警器
	 * 
	 * @param criteria
	 * @param query
	 * @return PageFinder
	 * @throws Exception
	 */
	PageFinder<ApiAbnormalAlarm> queryApiAbnormalAlarm(DetachedCriteria criteria, Query query) throws Exception;
	
	/**
	 * 查询API异常报警日志信息
	 * 
	 * @param apiAbnormalAlarm
	 * @param notifyStyle
	 * @param fromAbnormalTime
	 * @param toAbnormalTime
	 * @param query
	 * @return PageFinder
	 * @throws Exception
	 */
	PageFinder<DBObject> queryApiAbnormalAlarmFromMongoDB(ApiAbnormalAlarmSubject apiAbnormalAlarm, String notifyStyle, Date fromAbnormalTime, Date toAbnormalTime, Query query) throws Exception;

}

