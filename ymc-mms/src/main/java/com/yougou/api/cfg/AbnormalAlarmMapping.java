package com.yougou.api.cfg;

/**
 * 
 * @author 杨梦清
 * 
 */
public class AbnormalAlarmMapping {
	private Class<?> alarmCauserClass;// 引发者类
	private String alarmCauserCode;// 引发者代码
	private Long alarmCauserWeight;// 事件权重
	private Integer ignoreNumbers;// 忽略次数
	private Long cycleTimeline;// 周期时间轴
	private String alarmReceiverEmail;// 报警接收人邮件
	private String alarmReceiverPhone;// 报警接收人手机

	public AbnormalAlarmMapping() {
		super();
	}

	protected AbnormalAlarmMapping(Class<?> alarmCauserClass, Long alarmCauserWeight) {
		this.alarmCauserClass = alarmCauserClass;
		this.alarmCauserWeight = alarmCauserWeight;
	}
	
	protected AbnormalAlarmMapping(AbnormalAlarmMapping another) {
		this.alarmCauserClass = another.alarmCauserClass;
		this.alarmCauserCode = another.alarmCauserCode;
		this.alarmCauserWeight = another.alarmCauserWeight;
		this.ignoreNumbers = another.ignoreNumbers;
		this.cycleTimeline = another.cycleTimeline;
		this.alarmReceiverEmail = another.alarmReceiverEmail;
		this.alarmReceiverPhone = another.alarmReceiverPhone;
	}

	public Class<?> getAlarmCauserClass() {
		return alarmCauserClass;
	}

	public String getAlarmCauserCode() {
		return alarmCauserCode;
	}

	public Long getAlarmCauserWeight() {
		return alarmCauserWeight;
	}

	public Integer getIgnoreNumbers() {
		return ignoreNumbers;
	}

	public Long getCycleTimeline() {
		return cycleTimeline;
	}

	public String getAlarmReceiverEmail() {
		return alarmReceiverEmail;
	}

	public String getAlarmReceiverPhone() {
		return alarmReceiverPhone;
	}

	public static class AbnormalAlarmMappingBuilder {
		
		private AbnormalAlarmMapping mapping;

		public AbnormalAlarmMappingBuilder(Class<?> alarmCauserClass, Long alarmCauserWeight) {
			this.mapping = new AbnormalAlarmMapping(alarmCauserClass, alarmCauserWeight);
		}
		
		public AbnormalAlarmMappingBuilder alarmCauserCode(String alarmCauserCode) {
			this.mapping.alarmCauserCode = alarmCauserCode;
			return this;
		}
		
		public AbnormalAlarmMappingBuilder ignoreNumbers(Integer ignoreNumbers) {
			this.mapping.ignoreNumbers = ignoreNumbers;
			return this;
		}
		
		public AbnormalAlarmMappingBuilder cycleTimeline(Long cycleTimeline) {
			this.mapping.cycleTimeline = cycleTimeline;
			return this;
		}
		
		public AbnormalAlarmMappingBuilder alarmReceiverEmail(String alarmReceiverEmail) {
			this.mapping.alarmReceiverEmail = alarmReceiverEmail;
			return this;
		}
		
		public AbnormalAlarmMappingBuilder alarmReceiverPhone(String alarmReceiverPhone) {
			this.mapping.alarmReceiverPhone = alarmReceiverPhone;
			return this;
		}
		
		public AbnormalAlarmMapping build() {
			return new AbnormalAlarmMapping(mapping);
		}
	}
}
