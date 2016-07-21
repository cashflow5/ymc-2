package com.belle.other.model.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 类描述：调拨信息
 * 
 * @author xiongjingang
 * @date 2011-5-3 下午02:02:21
 * @email xjg-280@163.com
 */
public class AllocationWmsVo {
	private String allocationId;
	/** 入库仓库编号 */
	private String intoWarehouseCode;
	/** 入库仓库名称 */
	private String intoWarehouseName;
	/** 出库仓库编号 */
	private String outWarehouseCode;
	/** 出库仓库名称 */
	private String outWarehouseName;
	/** 调拨编号 */
	private String allocationCode;
	/** 操作员 */
	private String operatorId;
	/** 创建人 */
	private String creator;
	/** 创建日期 */
	private String createDate;
	/** 申批人 */
	private String approver;
	/** 申批日期 */
	private String approverDate;
	/** 最新修改日期 */
	private String lastModifyDate;
	/** 更新时间戳 */
	private Long updateTimestamp;
	/** 状态 */
	private Integer status;
	/** 调拨日期 */
	private String allocationDate;
	/** 入库仓库编号 */
	// private String merchantCode;
	private String remark;
	/** 最新修改人 */
	// private String merchantName;
	private String lastModifyPerson;
	/**
	 * 联系人
	 */
	private String linkman;

	/**
	 * 联系电话
	 */
	private String phone;

	/**
	 * 淘宝商家ID
	 */
	private String orderSellerId;
	/**
	 * 货品类型
	 */
	private String goodsType;

	private List<AllocationDetailWmsVo> detail = new ArrayList<AllocationDetailWmsVo>();

	public AllocationWmsVo() {
	}

	public AllocationWmsVo(String allocationId, String intoWarehouseCode, String intoWarehouseName, String outWarehouseCode, String outWarehouseName, String allocationCode,
			String operatorId, String creator, String createDate, String approver, String approverDate, String lastModifyDate, Long updateTimestamp, Integer status,
			String allocationDate, String remark, String lastModifyPerson, String linkman, String phone, String orderSellerId, String goodsType, List<AllocationDetailWmsVo> detail) {
		super();
		this.allocationId = allocationId;
		this.intoWarehouseCode = intoWarehouseCode;
		this.intoWarehouseName = intoWarehouseName;
		this.outWarehouseCode = outWarehouseCode;
		this.outWarehouseName = outWarehouseName;
		this.allocationCode = allocationCode;
		this.operatorId = operatorId;
		this.creator = creator;
		this.createDate = createDate;
		this.approver = approver;
		this.approverDate = approverDate;
		this.lastModifyDate = lastModifyDate;
		this.updateTimestamp = updateTimestamp;
		this.status = status;
		this.allocationDate = allocationDate;
		this.remark = remark;
		this.lastModifyPerson = lastModifyPerson;
		this.linkman = linkman;
		this.phone = phone;
		this.orderSellerId = orderSellerId;
		this.goodsType = goodsType;
		this.detail = detail;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getAllocationId() {
		return allocationId;
	}

	public void setAllocationId(String allocationId) {
		this.allocationId = allocationId;
	}

	public String getIntoWarehouseCode() {
		return intoWarehouseCode;
	}

	public void setIntoWarehouseCode(String intoWarehouseCode) {
		this.intoWarehouseCode = intoWarehouseCode;
	}

	public String getIntoWarehouseName() {
		return intoWarehouseName;
	}

	public void setIntoWarehouseName(String intoWarehouseName) {
		this.intoWarehouseName = intoWarehouseName;
	}

	public String getOutWarehouseCode() {
		return outWarehouseCode;
	}

	public void setOutWarehouseCode(String outWarehouseCode) {
		this.outWarehouseCode = outWarehouseCode;
	}

	public String getOutWarehouseName() {
		return outWarehouseName;
	}

	public void setOutWarehouseName(String outWarehouseName) {
		this.outWarehouseName = outWarehouseName;
	}

	public String getAllocationCode() {
		return allocationCode;
	}

	public void setAllocationCode(String allocationCode) {
		this.allocationCode = allocationCode;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getApproverDate() {
		return approverDate;
	}

	public void setApproverDate(String approverDate) {
		this.approverDate = approverDate;
	}

	public String getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(String lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
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

	public String getAllocationDate() {
		return allocationDate;
	}

	public void setAllocationDate(String allocationDate) {
		this.allocationDate = allocationDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLastModifyPerson() {
		return lastModifyPerson;
	}

	public void setLastModifyPerson(String lastModifyPerson) {
		this.lastModifyPerson = lastModifyPerson;
	}

	public List<AllocationDetailWmsVo> getDetail() {
		return detail;
	}

	public void setDetail(List<AllocationDetailWmsVo> detail) {
		this.detail = detail;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOrderSellerId() {
		return orderSellerId;
	}

	public void setOrderSellerId(String orderSellerId) {
		this.orderSellerId = orderSellerId;
	}

}
