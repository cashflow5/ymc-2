package com.yougou.api.service.impl;

import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.yougou.api.annotation.Documented;
import com.yougou.api.dao.IApiAbnormalAlarmDao;
import com.yougou.api.model.pojo.ApiAbnormalAlarm;
import com.yougou.api.model.vo.ApiAbnormalAlarmEmail;
import com.yougou.api.model.vo.ApiAbnormalAlarmSms;
import com.yougou.api.model.vo.ApiAbnormalAlarmSubject;
import com.yougou.api.model.vo.ApiAbnormalAlarmSubject.NotifyState;
import com.yougou.api.model.vo.ApiAbnormalAlarmSubject.NotifyStyle;
import com.yougou.api.mongodb.GenericMongoDao;
import com.yougou.api.service.IApiAbnormalAlarmService;
import com.yougou.api.util.SimpleMailSender;

/**
 * API异常报警业务服务类
 * 
 * @author 杨梦清
 * 
 */
@Service
public class ApiAbnormalAlarmServiceImpl implements IApiAbnormalAlarmService {
	
	private static final Logger LOGGER = Logger.getLogger(IApiAbnormalAlarmService.class);

	@Resource
	private IApiAbnormalAlarmDao apiAbnormalAlarmDao;

	@Resource
	private GenericMongoDao genericMongoDao;

//	@Resource
//	private SmsSender smsSender;
	
	@Override
	public void triggeringApiAbnormalAlarmEmail() {
		// 待通知状态
		DBObject waiting = new BasicDBObject();
		waiting.put("notifyStyle", NotifyStyle.EMAIL.toString());
		waiting.put("notifyState", NotifyState.WAITING.toString());
		// 通知中状态
		DBObject notifying = new BasicDBObject();
		notifying.put("notifyStyle", NotifyStyle.EMAIL.toString());
		notifying.put("notifyState", NotifyState.NOTIFYING.toString());
		notifying.put("notifier", "Job-" + UUID.randomUUID());
		// 已通知状态
		DBObject completed = new BasicDBObject();
		completed.put("notifyState", NotifyState.COMPLETED.toString());
		completed.put("notified", new Date());

		// 占用当前所有待通知状态
		DBCollection dbCollection = genericMongoDao.getCollection(ApiAbnormalAlarmEmail.class.getAnnotation(Documented.class).name());
		/*
		参数说明：
		1）q：查询条件，类似于update语句内where后面的内容
		2）o：更新的对象和一些更新的操作符(如$、$inc等)，也可以理解为关系型数据库update语句内set后面的内容  test 
		3）upsert：如果不存在update的纪录，是否插入objNew这个新的document。true为插入，默认是false，不插入
		4）multi：默认是false,只更新找到的第一条纪录，如果为true,按条件查出来的多条纪录全部更新。
		*/
		dbCollection.update(waiting, new BasicDBObject("$set", notifying), false, true);

		DBCursor dbCursor = dbCollection.find(notifying);
		while (dbCursor.hasNext()) {
			DBObject dbObject = dbCursor.next();
			String[] receivers = StringUtils.split(ObjectUtils.toString(dbObject.get("receivers")), ",");
			String content = ObjectUtils.toString(dbObject.get("content"));
			try {
				SimpleMailSender.sendMail(receivers, "重要：招商系统Api接口异常报警通知,来源于" + getInetAddressInfo(), content, true);
			} catch (Exception ex) {
				LOGGER.error(ex);
			}
		}

		dbCollection.update(notifying, new BasicDBObject("$set", completed), false, true);
	}

	@Override
	public void triggeringApiAbnormalAlarmSms() {
		// 待通知状态
		DBObject waiting = new BasicDBObject();
		waiting.put("notifyStyle", NotifyStyle.SMS.toString());
		waiting.put("notifyState", NotifyState.WAITING.toString());
		// 通知中状态
		DBObject notifying = new BasicDBObject();
		notifying.put("notifyStyle", NotifyStyle.SMS.toString());
		notifying.put("notifyState", NotifyState.NOTIFYING.toString());
		notifying.put("notifier", "Job-" + UUID.randomUUID());
		// 已通知状态
		DBObject completed = new BasicDBObject();
		completed.put("notifyState", NotifyState.COMPLETED.toString());
		completed.put("notified", new Date());

		// 占用当前所有待通知状态
		DBCollection dbCollection = genericMongoDao.getCollection(ApiAbnormalAlarmSms.class.getAnnotation(Documented.class).name());
		/*
		参数说明：
		1）q：查询条件，类似于update语句内where后面的内容
		2）o：更新的对象和一些更新的操作符(如$、$inc等)，也可以理解为关系型数据库update语句内set后面的内容  test 
		3）upsert：如果不存在update的纪录，是否插入objNew这个新的document。true为插入，默认是false，不插入
		4）multi：默认是false,只更新找到的第一条纪录，如果为true,按条件查出来的多条纪录全部更新。
		*/
		dbCollection.update(waiting, new BasicDBObject("$set", notifying), false, true);
		
		DBCursor dbCursor = dbCollection.find(notifying);
		while (dbCursor.hasNext()) {
			//TODO
			/*DBObject dbObject = dbCursor.next();
			String[] receivers = StringUtils.split(ObjectUtils.toString(dbObject.get("receivers")), ",");
			String content = ObjectUtils.toString(dbObject.get("content"));
			SmsVo smsVo = new SmsVo();
			smsVo.setContent(content + ",招商Api接口异常报警,来源于" + getInetAddressInfo());
			smsVo.setModelType(ModelType.MODEL_TYPE_MERCHANT);
			smsVo.setPhone(receivers);
			smsSender.sendNow(smsVo);*/
		}

		dbCollection.update(notifying, new BasicDBObject("$set", completed), false, true);
	}
	
	private String getInetAddressInfo() {
		try {
			return InetAddress.getLocalHost().toString();
		} catch (Exception e) {
			return "UnknownHost";
		}
	}

	@Override
	public ApiAbnormalAlarm getApiAbnormalAlarmById(String id) throws Exception {
		return apiAbnormalAlarmDao.getById(id);
	}

	@Override
	@Transactional
	public void deleteApiAbnormalAlarmById(String id) throws Exception {
		apiAbnormalAlarmDao.removeById(id);
	}

	@Override
	@Transactional
	public void saveApiAbnormalAlarm(ApiAbnormalAlarm apiAbnormalAlarm) throws Exception {
		isValidApiAbnormalAlarmRule(apiAbnormalAlarm);
		apiAbnormalAlarmDao.save(apiAbnormalAlarm);
	}

	@Override
	@Transactional
	public void updateApiAbnormalAlarm(ApiAbnormalAlarm apiAbnormalAlarm) throws Exception {
		Session session = null;
		try {
			isValidApiAbnormalAlarmRule(apiAbnormalAlarm);
			session = apiAbnormalAlarmDao.getHibernateSession();
			ApiAbnormalAlarm another = (ApiAbnormalAlarm) session.load(ApiAbnormalAlarm.class, apiAbnormalAlarm.getId(), LockMode.UPGRADE);
			another.setAlarmCauserClass(apiAbnormalAlarm.getAlarmCauserClass());
			another.setAlarmCauserCode(apiAbnormalAlarm.getAlarmCauserCode());
			another.setAlarmCauserWeight(apiAbnormalAlarm.getAlarmCauserWeight());
			another.setAlarmReceiverEmail(apiAbnormalAlarm.getAlarmReceiverPhone());
			another.setAlarmReceiverPhone(apiAbnormalAlarm.getAlarmReceiverPhone());
			another.setCycleTimeline(apiAbnormalAlarm.getCycleTimeline());
			another.setIgnoreNumbers(apiAbnormalAlarm.getIgnoreNumbers());
			another.setDescription(apiAbnormalAlarm.getDescription());
			session.update(another);
		} finally {
			apiAbnormalAlarmDao.releaseHibernateSession(session);
		}
	}

	@Override
	public PageFinder<ApiAbnormalAlarm> queryApiAbnormalAlarm(DetachedCriteria criteria, Query query) throws Exception {
		Session session = null;
		try {
			session = apiAbnormalAlarmDao.getHibernateSession();
			Criteria executableCriteria = criteria.getExecutableCriteria(session);
			return apiAbnormalAlarmDao.pagedByCriteria(executableCriteria, query.getPage(), query.getPageSize());
		} finally {
			apiAbnormalAlarmDao.releaseHibernateSession(session);
		}
	}
	
	/**
	 * 校验异常报警器规则是否有效
	 * 
	 * @param apiAbnormalAlarm
	 */
	private void isValidApiAbnormalAlarmRule(ApiAbnormalAlarm apiAbnormalAlarm) throws Exception {
		Session session = null;
		try {
			session = apiAbnormalAlarmDao.getHibernateSession();
			Criteria criteria = session.createCriteria(ApiAbnormalAlarm.class);
			criteria.add(Restrictions.eq("alarmCauserClass", apiAbnormalAlarm.getAlarmCauserClass()));
			List<ApiAbnormalAlarm> apiAbnormalAlarms = criteria.list();
			if (CollectionUtils.isNotEmpty(apiAbnormalAlarms)) {
				for (ApiAbnormalAlarm element : apiAbnormalAlarms) {
					boolean isEqualCode = element.getAlarmCauserCode().equalsIgnoreCase(apiAbnormalAlarm.getAlarmCauserCode());
					// 跳过自身
					if (isEqualCode && element.getId().equals(apiAbnormalAlarm.getId())) {
						continue;
					}
					// 验证其它
					if ("*".equalsIgnoreCase(element.getAlarmCauserCode())) {
						throw new UnsupportedOperationException("异常报警器类[" + apiAbnormalAlarm.getAlarmCauserClass() + "@" + element.getAlarmCauserCode() + "]已注册所有报警代码,禁止注册其它报警代码!"); 
					}
					if (isEqualCode) {
						throw new UnsupportedOperationException("异常报警器类[" + apiAbnormalAlarm.getAlarmCauserClass() + "@" + element.getAlarmCauserCode() + "]已经注册!");
					}
				}
			}
		} finally {
			apiAbnormalAlarmDao.releaseHibernateSession(session);
		}
	}

	@Override
	public PageFinder<DBObject> queryApiAbnormalAlarmFromMongoDB(ApiAbnormalAlarmSubject apiAbnormalAlarm, String notifyStyle, Date fromAbnormalTime, Date toAbnormalTime, Query query) throws Exception {
		BasicDBObject condition = new BasicDBObject();
		BasicDBObject conditionGroup = new BasicDBObject();
		
		if (StringUtils.isNotBlank(apiAbnormalAlarm.getCode())) {
			condition.put("code", apiAbnormalAlarm.getCode());
		}
		if (StringUtils.isNotBlank(apiAbnormalAlarm.getReceivers())) {
			condition.put("receivers", Pattern.compile(apiAbnormalAlarm.getReceivers()));
		}
		if (StringUtils.isNotBlank(apiAbnormalAlarm.getContent())) {
			condition.put("content", Pattern.compile(apiAbnormalAlarm.getContent()));
		}
		if (apiAbnormalAlarm.getNotifyState() != null) {
			condition.put("notifyState", apiAbnormalAlarm.getNotifyState().getValue());
		}
		if (fromAbnormalTime != null) {
			conditionGroup.put("$gte", fromAbnormalTime);
		}
		if (toAbnormalTime != null) {
			conditionGroup.put("$lte", toAbnormalTime);
		}
		if (!conditionGroup.isEmpty()) {
			condition.put("abnormalTime", conditionGroup);
		}
		
		Class<?> clazz = NotifyStyle.EMAIL.getValue().equals(notifyStyle) ? ApiAbnormalAlarmEmail.class : ApiAbnormalAlarmSms.class;
		return genericMongoDao.getDBObject(clazz.getAnnotation(Documented.class).name(), condition, new BasicDBObject("abnormalTime", -1), query);

	}
}
