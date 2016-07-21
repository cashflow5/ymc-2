package com.belle.other.model.vo;

public class WarehouseWmsVo {
	/** 仓库ID */
	private String id;
	/** 仓库编号 */
	private String warehouseCode;
	/** 仓库名称 */
	private String warehouseName;
	/** 仓库面积 */
	private Double warehouseAcreage;
	/** 联系人 * */
	private String contact;
	/** 联系座机 */
	private String telPhone;
	/** 手机 */
	private String mobilePhone;
	/** 更新时间戳 */
	private Long updateTimestamp;
	/** 电子邮箱 */
	private String email;
	/** 仓库地址 */
	private String warehouseAddress;
	/** 仓库描述 */
	private String remark;
	/** 创建时间 */
//	private String createDate;
	/** 商家编码 */
	private String merchantCode;
	/** 商家名称 */
	private String merchantName;
	/** 是否有效(状态) */
	private Integer status;
	/** 是否有PDA */
	private Integer isPda;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
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

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWarehouseAddress() {
		return warehouseAddress;
	}
	public void setWarehouseAddress(String warehouseAddress) {
		this.warehouseAddress = warehouseAddress;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
//	public String getCreateDate() {
//		return createDate;
//	}
//	public void setCreateDate(String createDate) {
//		this.createDate = createDate;
//	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public Double getWarehouseAcreage() {
		return warehouseAcreage;
	}
	public void setWarehouseAcreage(Double warehouseAcreage) {
		this.warehouseAcreage = warehouseAcreage;
	}
	public Long getUpdateTimestamp() {
		return updateTimestamp;
	}
	public void setUpdateTimestamp(Long updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIsPda() {
		return isPda;
	}
	public void setIsPda(Integer isPda) {
		this.isPda = isPda;
	}

	
	
}
