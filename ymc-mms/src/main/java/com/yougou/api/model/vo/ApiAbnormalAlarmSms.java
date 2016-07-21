package com.yougou.api.model.vo;

import com.yougou.api.annotation.Documented;

/**
 * 
 * @author 杨梦清
 * 
 */
@Documented(name = "tbl_merchant_api_abnormal_alarm_sms")
public class ApiAbnormalAlarmSms extends ApiAbnormalAlarmSubject {

	private static final long serialVersionUID = 5469856051264988231L;

	public ApiAbnormalAlarmSms() {
		super(NotifyStyle.SMS);
	}

}
