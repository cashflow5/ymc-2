package com.yougou.api.model.vo;

import com.yougou.api.annotation.Documented;

/**
 * 
 * @author 杨梦清
 * 
 */
@Documented(name = "tbl_merchant_api_abnormal_alarm_email")
public class ApiAbnormalAlarmEmail extends ApiAbnormalAlarmSubject {

	private static final long serialVersionUID = -8848078279787033816L;

	public ApiAbnormalAlarmEmail() {
		super(NotifyStyle.EMAIL);
	}

}

