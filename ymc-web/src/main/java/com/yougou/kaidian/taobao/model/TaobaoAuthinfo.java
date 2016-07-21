package com.yougou.kaidian.taobao.model;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public class TaobaoAuthinfo {
    private String id;

    private String topAppkey;

    private String topParameters;

    private String topSession;

    private String topTimestamp;

    private Short topAgreement;

    private String topAgreementsign;
    
    private String sign;

    private String topSign;

    private Long topVisitorId;

    private String topVisitorNick;

    private Integer topExpiresIn;

    private Date topExpiresInTime;

    private String merchantCode;

    private Short isUseble;

    private String operater;

    private String operated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTopAppkey() {
        return topAppkey;
    }

    public void setTopAppkey(String topAppkey) {
        this.topAppkey = topAppkey == null ? null : topAppkey.trim();
    }

    public String getTopParameters() {
        return topParameters;
    }

    public void setTopParameters(String topParameters) {
        this.topParameters = topParameters == null ? null : topParameters.trim();
    }

    public String getTopSession() {
        return topSession;
    }

    public void setTopSession(String topSession) {
        this.topSession = topSession == null ? null : topSession.trim();
    }

    public String getTopTimestamp() {
        return topTimestamp;
    }

    public void setTopTimestamp(String topTimestamp) {
        this.topTimestamp = topTimestamp == null ? null : topTimestamp.trim();
    }

    public Short getTopAgreement() {
        return topAgreement;
    }

    public void setTopAgreement(Short topAgreement) {
        this.topAgreement = topAgreement;
    }

    public String getTopAgreementsign() {
        return topAgreementsign;
    }

    public void setTopAgreementsign(String topAgreementsign) {
        this.topAgreementsign = topAgreementsign == null ? null : topAgreementsign.trim();
    }

    public String getTopSign() {
        return topSign;
    }

    public void setTopSign(String topSign) {
        this.topSign = topSign == null ? null : topSign.trim();
    }

    public Long getTopVisitorId() {
        return topVisitorId;
    }

    public void setTopVisitorId(Long topVisitorId) {
        this.topVisitorId = topVisitorId;
    }

    public String getTopVisitorNick() {
        return topVisitorNick;
    }

    public void setTopVisitorNick(String topVisitorNick) {
        this.topVisitorNick = topVisitorNick == null ? null : topVisitorNick.trim();
    }

    public Integer getTopExpiresIn() {
        return topExpiresIn;
    }

    public void setTopExpiresIn(Integer topExpiresIn) {
        this.topExpiresIn = topExpiresIn;
    }

    public Date getTopExpiresInTime() {
        return topExpiresInTime;
    }

    public void setTopExpiresInTime(Date topExpiresInTime) {
        this.topExpiresInTime = topExpiresInTime;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode == null ? null : merchantCode.trim();
    }

    public Short getIsUseble() {
        return isUseble;
    }

    public void setIsUseble(Short isUseble) {
        this.isUseble = isUseble;
    }

    public String getOperater() {
        return operater;
    }

    public void setOperater(String operater) {
        this.operater = operater == null ? null : operater.trim();
    }

    public String getOperated() {
        return operated;
    }

    public void setOperated(String operated) {
        this.operated = operated == null ? null : operated.trim();
    }

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}