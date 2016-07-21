package com.yougou.kaidian.bi.vo;

import java.util.Date;

import com.yougou.kaidian.bi.beans.AnalyzePatttern;

public class StatisticsVo {

	// 分析模式
	private AnalyzePatttern analyzePatttern;
	// 开始时间内容
	private String startText;
	// 结束时间内容
	private String endText;
	// 开始时间
	private Date start;
	// 结束时间
	private Date end;
	// 商家编码
	private String merchantCode;
	// 年份
	private String thisYear;

	public AnalyzePatttern getAnalyzePatttern() {
		return analyzePatttern;
	}

	public void setAnalyzePatttern(AnalyzePatttern analyzePatttern) {
		this.analyzePatttern = analyzePatttern;
	}

	public String getStartText() {
		return startText;
	}

	public void setStartText(String startText) {
		this.startText = startText;
	}

	public String getEndText() {
		return endText;
	}

	public void setEndText(String endText) {
		this.endText = endText;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getThisYear() {
		return thisYear;
	}

	public void setThisYear(String thisYear) {
		this.thisYear = thisYear;
	}
}
