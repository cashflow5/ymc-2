package com.yougou.kaidian.user.model.pojo;

import java.util.Date;

/**
 * 
 *物流公司实体类
 *@author wang.m
 */
public class Logisticscompan implements java.io.Serializable{

	private String logisticsConfigStr;
	private String id;
	// 物流公司编号
	private String logisticCompanyCode;
	// 物流公司名称
	private String logisticsCompanyName;
	// 物流公司网址
	private String logisticsCompanyUrl;
	// 物流公司联系人
	private String contact;
	// 电话
	private String telPhone;
	// 手机
	private String mobilePhone;
	private String qq;
	private String msn;
	private String email;
	// 物流公司地址
	private String logisticsCompanyAddress;
	private Integer status;
	// 创建时间
	private Date createDate;
	// 备注
	private String remark;
	// 排序
	private Integer sort;
	// 否是支持货到付款
	private Integer isCod;
	// 是否支持物流配送接口
	private Integer isSldi;
	// 是否打印快递单
	private Integer isPrint;

	private Integer priorityRating;
	private String mark_code;  // 快递公司标识编码
	private String mark_name;  //快递公司标识名称
	
	//支持订单来源    全部 0; 优购 1; 非优购 2;
	private Integer supportSource;
	
	// 公司全称
	private String logisticCompanyFullname;
	// 邮编
	private String logisticCompanyPostcode;
	// 快递100物流编码
	private String logisticCompanyPost100Code;
	// 淘宝物流编码
	private String logisticCompanyTaobaoCode;
	// 匹配优先级
	private Integer logisticCompanyPriority;
	
	private Integer isMerchant;//使用方  1为优购   2 为商家使用   (wang.M)

	private Date updateDate;
	private Long updateTimestamp;
	
	public String getLogisticsConfigStr() {
		return logisticsConfigStr;
	}
	public void setLogisticsConfigStr(String logisticsConfigStr) {
		this.logisticsConfigStr = logisticsConfigStr;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLogisticCompanyCode() {
		return logisticCompanyCode;
	}
	public void setLogisticCompanyCode(String logisticCompanyCode) {
		this.logisticCompanyCode = logisticCompanyCode;
	}
	public String getLogisticsCompanyName() {
		return logisticsCompanyName;
	}
	public void setLogisticsCompanyName(String logisticsCompanyName) {
		this.logisticsCompanyName = logisticsCompanyName;
	}
	public String getLogisticsCompanyUrl() {
		return logisticsCompanyUrl;
	}
	public void setLogisticsCompanyUrl(String logisticsCompanyUrl) {
		this.logisticsCompanyUrl = logisticsCompanyUrl;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getTelPhone() {
		return telPhone;
	}
	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getMsn() {
		return msn;
	}
	public void setMsn(String msn) {
		this.msn = msn;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLogisticsCompanyAddress() {
		return logisticsCompanyAddress;
	}
	public void setLogisticsCompanyAddress(String logisticsCompanyAddress) {
		this.logisticsCompanyAddress = logisticsCompanyAddress;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getIsCod() {
		return isCod;
	}
	public void setIsCod(Integer isCod) {
		this.isCod = isCod;
	}
	public Integer getIsSldi() {
		return isSldi;
	}
	public void setIsSldi(Integer isSldi) {
		this.isSldi = isSldi;
	}
	public Integer getIsPrint() {
		return isPrint;
	}
	public void setIsPrint(Integer isPrint) {
		this.isPrint = isPrint;
	}
	public Integer getPriorityRating() {
		return priorityRating;
	}
	public void setPriorityRating(Integer priorityRating) {
		this.priorityRating = priorityRating;
	}
	public String getMark_code() {
		return mark_code;
	}
	public void setMark_code(String mark_code) {
		this.mark_code = mark_code;
	}
	public String getMark_name() {
		return mark_name;
	}
	public void setMark_name(String mark_name) {
		this.mark_name = mark_name;
	}
	public Integer getSupportSource() {
		return supportSource;
	}
	public void setSupportSource(Integer supportSource) {
		this.supportSource = supportSource;
	}
	public String getLogisticCompanyFullname() {
		return logisticCompanyFullname;
	}
	public void setLogisticCompanyFullname(String logisticCompanyFullname) {
		this.logisticCompanyFullname = logisticCompanyFullname;
	}
	public String getLogisticCompanyPostcode() {
		return logisticCompanyPostcode;
	}
	public void setLogisticCompanyPostcode(String logisticCompanyPostcode) {
		this.logisticCompanyPostcode = logisticCompanyPostcode;
	}
	public String getLogisticCompanyPost100Code() {
		return logisticCompanyPost100Code;
	}
	public void setLogisticCompanyPost100Code(String logisticCompanyPost100Code) {
		this.logisticCompanyPost100Code = logisticCompanyPost100Code;
	}
	public String getLogisticCompanyTaobaoCode() {
		return logisticCompanyTaobaoCode;
	}
	public void setLogisticCompanyTaobaoCode(String logisticCompanyTaobaoCode) {
		this.logisticCompanyTaobaoCode = logisticCompanyTaobaoCode;
	}
	public Integer getLogisticCompanyPriority() {
		return logisticCompanyPriority;
	}
	public void setLogisticCompanyPriority(Integer logisticCompanyPriority) {
		this.logisticCompanyPriority = logisticCompanyPriority;
	}
	public Integer getIsMerchant() {
		return isMerchant;
	}
	public void setIsMerchant(Integer isMerchant) {
		this.isMerchant = isMerchant;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Long getUpdateTimestamp() {
		return updateTimestamp;
	}
	public void setUpdateTimestamp(Long updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}
	public Logisticscompan(String logisticsConfigStr, String id,
			String logisticCompanyCode, String logisticsCompanyName,
			String logisticsCompanyUrl, String contact, String telPhone,
			String mobilePhone, String qq, String msn, String email,
			String logisticsCompanyAddress, Integer status, Date createDate,
			String remark, Integer sort, Integer isCod, Integer isSldi,
			Integer isPrint, Integer priorityRating, String mark_code,
			String mark_name, Integer supportSource,
			String logisticCompanyFullname, String logisticCompanyPostcode,
			String logisticCompanyPost100Code,
			String logisticCompanyTaobaoCode, Integer logisticCompanyPriority,
			Integer isMerchant, Date updateDate, Long updateTimestamp) {
		super();
		this.logisticsConfigStr = logisticsConfigStr;
		this.id = id;
		this.logisticCompanyCode = logisticCompanyCode;
		this.logisticsCompanyName = logisticsCompanyName;
		this.logisticsCompanyUrl = logisticsCompanyUrl;
		this.contact = contact;
		this.telPhone = telPhone;
		this.mobilePhone = mobilePhone;
		this.qq = qq;
		this.msn = msn;
		this.email = email;
		this.logisticsCompanyAddress = logisticsCompanyAddress;
		this.status = status;
		this.createDate = createDate;
		this.remark = remark;
		this.sort = sort;
		this.isCod = isCod;
		this.isSldi = isSldi;
		this.isPrint = isPrint;
		this.priorityRating = priorityRating;
		this.mark_code = mark_code;
		this.mark_name = mark_name;
		this.supportSource = supportSource;
		this.logisticCompanyFullname = logisticCompanyFullname;
		this.logisticCompanyPostcode = logisticCompanyPostcode;
		this.logisticCompanyPost100Code = logisticCompanyPost100Code;
		this.logisticCompanyTaobaoCode = logisticCompanyTaobaoCode;
		this.logisticCompanyPriority = logisticCompanyPriority;
		this.isMerchant = isMerchant;
		this.updateDate = updateDate;
		this.updateTimestamp = updateTimestamp;
	}
	public Logisticscompan() {
		super();
	}

	

}