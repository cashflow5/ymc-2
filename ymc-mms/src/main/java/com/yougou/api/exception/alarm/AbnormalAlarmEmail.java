package com.yougou.api.exception.alarm;

import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.yougou.api.annotation.Documented;
import com.yougou.api.cfg.AbnormalAlarmMapping;
import com.yougou.api.constant.YOPBusinessCode;
import com.yougou.api.exception.YOPRuntimeException;
import com.yougou.api.model.vo.ApiAbnormalAlarmEmail;
import com.yougou.api.model.vo.ApiAbnormalAlarmSubject.NotifyState;
import com.yougou.api.model.vo.ApiAbnormalAlarmSubject.NotifyStyle;
import com.yougou.api.mongodb.GenericMongoDao;

/**
 * 优购开放平台<b>邮件</b>异常报警信息
 * 
 * @author 杨梦清
 * 
 */
public class AbnormalAlarmEmail extends CyclerAbnormalAlarm {

	private GenericMongoDao genericMongoDao;

	public AbnormalAlarmEmail(AbnormalAlarmMapping mapping, GenericMongoDao genericMongoDao) {
		super(mapping);
		this.genericMongoDao = genericMongoDao;
	}

	@Override
	protected Long getWeight() {
		return 1L;
	}

	@Override
	protected void processMessage(Throwable message, long timestamp) {
		try {
			DBObject dbObject = new BasicDBObject();
			dbObject.put("code", YOPBusinessCode.ERROR);
			dbObject.put("content", ExceptionUtils.getStackTrace(message));
			dbObject.put("abnormalTime", new Date(timestamp));
			dbObject.put("receivers", mapping.getAlarmReceiverEmail());
			dbObject.put("notifyState", NotifyState.WAITING.getValue());
			dbObject.put("notifyStyle", NotifyStyle.EMAIL.getValue());
			String collectionName = ApiAbnormalAlarmEmail.class.getAnnotation(Documented.class).name();
			genericMongoDao.insert(collectionName, dbObject);
		} catch (Exception ex) {
			throw new YOPRuntimeException(YOPBusinessCode.RUNTIME_ERROR, ex.getMessage());
		}
	}
}
