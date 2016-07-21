package com.yougou.kaidian.order.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.yougou.kaidian.order.constant.OrderConstant;
import com.yougou.kaidian.order.util.OrderUtil;

/**
 * 
 * @author daixiaowei
 * @date 2012-03-07 10:53:36
 */
public class OrderSubExpand extends OrderSub implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	/**
	 * 子订单id
	 */
	private String orderSubId;
	/**
	 * 订单货品配送方 0优购 1商家
	 */
	private Integer orderDistributionSide;
	/**
	 * 商家订单状态 0,未导出 1,导出 2,已备货
	 */
	private Integer orderExportedStatus;
	/**
	 * 导出日期
	 */
	private Date exportedDate;
	/**
	 * 置为备货日期
	 */
	private Date stockingDate;
	/**
	 * 置为缺货日期
	 */
	private Date backorderDate;

	/**
	 * 备货单号
	 */
	private String backupNo;

	/**
	 * 供应商编码
	 */
	private String supplierCode;

	/**
	 * 打印购物清单次数
	 */
	private Integer printShoppinglistCount;
	/**
	 * 打印快递单次数
	 */
	private Integer printLogisticslistCount;
	/**
	 * 商家编码
	 */
	private String merchantCode;
	/**
	 * 快递单号
	 */
	private String expressCode;

	/************************ 临时字段 ********************************/
	/**
	 * 打印状态 0,未打印 1,已打印
	 * 
	 * @return
	 */
	private Integer orderPrintedStatus;

	/**
	 * 支付方式
	 */
	private String paymentName;
	private String payStatusName;
	/**
	 * 基本状态名称
	 */
	private String baseStatusName;
	/**
	 * 收货人地址 省
	 * 
	 * @return
	 */
	private String province;
	private String provinceName;
	/**
	 * 收货人地址 市
	 * 
	 * @return
	 */
	private String city;
	private String cityName;
	/**
	 * 收货人地址 区
	 * 
	 * @return
	 */
	private String area;
	private String areaName;

	/**
	 * 具体地址
	 */
	private String consigneeAddress;
	/**
	 * 商家货品条码
	 */
	private String thirdPartyCode;
	/**
	 * 是否一单一货 "是"
	 */
	private String singleCargo;
	
	/**
	 * 合并发货
	 */
	private String mergerCargo;

	/**
	 * 选项卡
	 * 
	 * @return
	 */
	private Integer tabNum;
	/**
	 * 开始时间 下单时间
	 */
	private String timeStart;
	/**
	 * 结束时间 下单时间
	 */
	private String timeEnd;

	/**
	 * 开始时间 导出时间
	 */
	private String timeStartExport;
	/**
	 * 结束时间 导出时间
	 */
	private String timeEndExport;

	/**
	 * 开始时间 备货时间
	 */
	private String timeStartStocking;
	/**
	 * 结束时间 备货时间
	 */
	private String timeEndStocking;

	/**
	 * 开始时间 缺货时间
	 */
	private String timeStartOutStock;
	/**
	 * 结束时间 缺货时间
	 */
	private String timeEndOutStock;
	/**
	 * 开始时间 发货时间
	 */
	private String timeStartShipTime;
	/**
	 * 结束时间 发货时间
	 */
	private String timeEndShipTime;

	/**
	 * 供应商款色编码
	 */
	private String suppliersColorModelsCode;
	/**
	 * 排序方式 1，下单时间正序 2，发货时间倒序 3，缺货时间倒序 下单时间倒序 4，下单时间倒序 发货时间正序
	 */
	private Integer orderBy;
	/**
	 * 订单id 连接字符串
	 * 
	 * @return
	 */
	private String orderSubIds;
	/**
	 * 收货人
	 * 
	 * @return
	 */
	private String userName;
	/**
	 * 收货人手机
	 * 
	 * @return
	 */
	private String consigneeMobile;

	/**
	 * 邮编
	 * 
	 * @return
	 */
	private String zipCode;
	/**
	 * 包含的货品总数
	 * 
	 * @return
	 */
	private Integer prodCount;
	/**
	 * 订单明细信息
	 */
	private List<OrderDetail4sub> orderDetailList = new ArrayList<OrderDetail4sub>();
	/**
	 * 新订单状态
	 */
	private Integer orderStatus;

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<OrderDetail4sub> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List<OrderDetail4sub> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}

	public String getConsigneeMobile() {
		return consigneeMobile;
	}

	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}

	public Integer getProdCount() {
		return prodCount;
	}

	public void setProdCount(Integer prodCount) {
		this.prodCount = prodCount;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrderSubIds() {
		return orderSubIds;
	}

	public void setOrderSubIds(String orderSubIds) {
		this.orderSubIds = orderSubIds;
	}

	public Integer getOrderBy() {
		return orderBy;
	}

	/**
	 * 排序方式 1，下单时间正序 2，发货时间倒序 3，缺货时间倒序 下单时间倒序 4，下单时间倒序 发货时间正序
	 */
	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	public String getTimeStartShipTime() {
		return timeStartShipTime;
	}

	public void setTimeStartShipTime(String timeStartShipTime) {
		this.timeStartShipTime = timeStartShipTime;
	}

	public String getTimeEndShipTime() {
		return timeEndShipTime;
	}

	public void setTimeEndShipTime(String timeEndShipTime) {
		this.timeEndShipTime = timeEndShipTime;
	}

	public Integer getPrintShoppinglistCount() {
		return printShoppinglistCount;
	}

	public void setPrintShoppinglistCount(Integer printShoppinglistCount) {
		this.printShoppinglistCount = printShoppinglistCount;
	}

	public Integer getPrintLogisticslistCount() {
		return printLogisticslistCount;
	}

	public void setPrintLogisticslistCount(Integer printLogisticslistCount) {
		this.printLogisticslistCount = printLogisticslistCount;
	}

	public String getPayStatusName() {
		if (super.getPayStatus() != null) {
			payStatusName = OrderUtil.getStatusDisplay(super.getPayStatus());
		}
		return payStatusName;
	}

	public void setPayStatusName(String payStatusName) {
		this.payStatusName = payStatusName;
	}

	public String getSuppliersColorModelsCode() {
		return suppliersColorModelsCode;
	}

	public void setSuppliersColorModelsCode(String suppliersColorModelsCode) {
		this.suppliersColorModelsCode = suppliersColorModelsCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	public String getThirdPartyCode() {
		return thirdPartyCode;
	}

	public void setThirdPartyCode(String thirdPartyCode) {
		this.thirdPartyCode = thirdPartyCode;
	}

	public String getSingleCargo() {
		return singleCargo;
	}

	public void setSingleCargo(String singleCargo) {
		this.singleCargo = singleCargo;
	}

	public Integer getOrderPrintedStatus() {
		return orderPrintedStatus;
	}

	public void setOrderPrintedStatus(Integer orderPrintedStatus) {
		this.orderPrintedStatus = orderPrintedStatus;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getTimeStartExport() {
		return timeStartExport;
	}

	public void setTimeStartExport(String timeStartExport) {
		this.timeStartExport = timeStartExport;
	}

	public String getTimeEndExport() {
		return timeEndExport;
	}

	public void setTimeEndExport(String timeEndExport) {
		this.timeEndExport = timeEndExport;
	}

	public String getTimeStartStocking() {
		return timeStartStocking;
	}

	public void setTimeStartStocking(String timeStartStocking) {
		this.timeStartStocking = timeStartStocking;
	}

	public String getTimeEndStocking() {
		return timeEndStocking;
	}

	public void setTimeEndStocking(String timeEndStocking) {
		this.timeEndStocking = timeEndStocking;
	}

	public String getTimeStartOutStock() {
		return timeStartOutStock;
	}

	public void setTimeStartOutStock(String timeStartOutStock) {
		this.timeStartOutStock = timeStartOutStock;
	}

	public String getTimeEndOutStock() {
		return timeEndOutStock;
	}

	public void setTimeEndOutStock(String timeEndOutStock) {
		this.timeEndOutStock = timeEndOutStock;
	}

	public String getBackupNo() {
		return backupNo;
	}

	public void setBackupNo(String backupNo) {
		this.backupNo = backupNo;
	}

	public String getOrderSubId() {
		return orderSubId;
	}

	public void setOrderSubId(String orderSubId) {
		this.orderSubId = orderSubId;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public Integer getTabNum() {
		return tabNum;
	}

	public void setTabNum(Integer tabNum) {
		this.tabNum = tabNum;
	}

	public String getPaymentName() {
		if (StringUtils.isNotBlank(super.getPayment())) {
			paymentName = OrderConstant.Paymentmethod.valueOf(super.getPayment()).getValue();
		}
		return paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	public String getBaseStatusName() {
		if (super.getBaseStatus() != null) {
			baseStatusName = OrderUtil.getStatusDisplay(super.getBaseStatus());
		}
		return baseStatusName;
	}

	public void setBaseStatusName(String baseStatusName) {
		this.baseStatusName = baseStatusName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public Integer getOrderDistributionSide() {
		return orderDistributionSide;
	}

	public void setOrderDistributionSide(Integer orderDistributionSide) {
		this.orderDistributionSide = orderDistributionSide;
	}

	public Integer getOrderExportedStatus() {
		return orderExportedStatus;
	}

	public void setOrderExportedStatus(Integer orderExportedStatus) {
		this.orderExportedStatus = orderExportedStatus;
	}

	public Date getExportedDate() {
		return exportedDate;
	}

	public void setExportedDate(Date exportedDate) {
		this.exportedDate = exportedDate;
	}

	public Date getStockingDate() {
		return stockingDate;
	}

	public void setStockingDate(Date stockingDate) {
		this.stockingDate = stockingDate;
	}

	public Date getBackorderDate() {
		return backorderDate;
	}

	public void setBackorderDate(Date backorderDate) {
		this.backorderDate = backorderDate;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getExpressCode() {
		return expressCode;
	}

	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}

	public String getMergerCargo() {
		return mergerCargo;
	}

	public void setMergerCargo(String mergerCargo) {
		this.mergerCargo = mergerCargo;
	}
	
	
}