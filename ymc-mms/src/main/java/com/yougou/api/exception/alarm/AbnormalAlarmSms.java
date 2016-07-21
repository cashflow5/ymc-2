package com.yougou.api.exception.alarm;

import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.yougou.api.annotation.Documented;
import com.yougou.api.cfg.AbnormalAlarmMapping;
import com.yougou.api.constant.YOPBusinessCode;
import com.yougou.api.exception.YOPRuntimeException;
import com.yougou.api.model.vo.ApiAbnormalAlarmSms;
import com.yougou.api.model.vo.ApiAbnormalAlarmSubject.NotifyState;
import com.yougou.api.model.vo.ApiAbnormalAlarmSubject.NotifyStyle;
import com.yougou.api.mongodb.GenericMongoDao;

/**
 * 优购开放平台<b>短信</b>异常报警信息
 * 
 * @author 杨梦清
 * 
 */
public class AbnormalAlarmSms extends CyclerAbnormalAlarm {

	private GenericMongoDao genericMongoDao;
	
	public AbnormalAlarmSms(AbnormalAlarmMapping mapping, GenericMongoDao genericMongoDao) {
		super(mapping);
		this.genericMongoDao = genericMongoDao;
	}

	@Override
	protected Long getWeight() {
		return 2L;
	}

	@Override
	protected void processMessage(Throwable message, long timestamp) {
		try {
			DBObject dbObject = new BasicDBObject();
			dbObject.put("code", YOPBusinessCode.ERROR);
			dbObject.put("content", message.getMessage());
			dbObject.put("abnormalTime", new Date(timestamp));
			dbObject.put("receivers", mapping.getAlarmReceiverPhone());
			dbObject.put("notifyState", NotifyState.WAITING.getValue());
			dbObject.put("notifyStyle", NotifyStyle.SMS.getValue());
			String collectionName = ApiAbnormalAlarmSms.class.getAnnotation(Documented.class).name();
			genericMongoDao.insert(collectionName, dbObject);
		} catch (Exception ex) {
			throw new YOPRuntimeException(YOPBusinessCode.RUNTIME_ERROR, ex.getMessage());
		}
	}
}

