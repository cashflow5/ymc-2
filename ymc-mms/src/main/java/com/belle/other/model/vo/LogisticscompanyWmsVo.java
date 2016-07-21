package com.belle.other.model.vo;

public class LogisticscompanyWmsVo {
	/** 物流公司ID */
	private String id;
	/** 物流公司编号 */
	private String logisticsCompanyCode;
	/** 物流公司名称 */
	private String logisticsCompanyName;
	/** 物流公司序号 */
	private Integer sort;
	/** 物流公司网址 */
	private String logisticsCompanyUrl;
	/** 联系人 */
	private String contact;
	/** 更新时间戳　 */
	private Long updateTimestamp;
	/** 座机 */
	private String telPhone;
	/** 手机 */
	private String mobilePhone;
	/** 电子邮件 */
	private String email;
	/** 物流公司地址 */
	private String logisticsCompanyAddress;
	/** 物流公司描述 */
	private String remark;
	/** 是否有效 */
	private Integer status;
	/** qq */
	private String qq;
	/** msn */
	private String msn;
	/** 是否打印快递单 */
	private String isPrint;
	
	/** 组id */
	private String groupCode;
	/**组名称*/
	private String groupName;
	
	private int isFreightCollect;

	public LogisticscompanyWmsVo() {
		super();
	}

	public LogisticscompanyWmsVo(String id, String logisticsCompanyCode, String logisticsCompanyName, Integer sort, String logisticsCompanyUrl, String contact, Long updateTimestamp, String telPhone,
			String mobilePhone, String email, String logisticsCompanyAddress, String remark, Integer status, String qq, String msn,String groupCode,String groupName) {
		super();
		this.id = id;
		this.logisticsCompanyCode = logisticsCompanyCode;
		this.logisticsCompanyName = logisticsCompanyName;
		this.sort = sort;
		this.logisticsCompanyUrl = logisticsCompanyUrl;
		this.contact = contact;
		this.updateTimestamp = updateTimestamp;
		this.telPhone = telPhone;
		this.mobilePhone = mobilePhone;
		this.email = email;
		this.logisticsCompanyAddress = logisticsCompanyAddress;
		this.remark = remark;
		this.status = status;
		this.qq = qq;
		this.msn = msn;
		this.groupCode = groupCode;
		this.groupName = groupName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogisticsCompanyCode() {
		return logisticsCompanyCode;
	}

	public void setLogisticsCompanyCode(String logisticsCompanyCode) {
		this.logisticsCompanyCode = logisticsCompanyCode;
	}

	public String getLogisticsCompanyName() {
		return logisticsCompanyName;
	}

	public void setLogisticsCompanyName(String logisticsCompanyName) {
		this.logisticsCompanyName = logisticsCompanyName;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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

	public Long getUpdateTimestamp() {
		return updateTimestamp;
	}

	public void setUpdateTimestamp(Long updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getIsPrint() {
		return isPrint;
	}

	public void setIsPrint(String isPrint) {
		this.isPrint = isPrint;
	}

	public int getIsFreightCollect() {
		return isFreightCollect;
	}

	public void setIsFreightCollect(int isFreightCollect) {
		this.isFreightCollect = isFreightCollect;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
