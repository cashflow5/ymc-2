package com.yougou.kaidian.user.model.pojo;




/**
 * 发货模版实体类
 * @author wang.m
 * @DATE 2012-04-26
 *
 */
public class MerchantExpressTemplate {
    
	private String id;//主键ID
 	
	private String expressName;//快递单名称
	
	private String logisticsId;//物流公司Id
	
	private Double width;//模版宽
	
	private Double heigth;//高
	
	private String fontSize;//字体大小
	
	private Integer isBold;//字体是否加粗  0 不加粗  1加粗
	
	private String backGroundImage;//图片 
	
	//发货人模块
	private String shipmentsName;//发货人姓名
	
	private String shipmentsOneArea;//发货人1级地区
	
	private String shipmentsTwoArea;//发货人2级地区
	
	private String shipmentsThreeArea;//发货人3级地区
	
	private String shipmentsAdress;//发货人地址
	
	private String shipmentsPhone;//发货人手机
	
	private String shipmentsTell;//发货人电话
	
	private String shipmentsEmail;//邮编

	
	//收货人模块
	private String orderSubNo;//订单号
	
	private String consigneeName;//收货人姓名
	
    private String consigneeOneArea;//收货人1级地区
	
	private String consigneeTwoArea;//收货人2级地区
	
	private String consigneeThreeArea;//收货人3级地区
	
	private String consigneeAdress;//收货人地址
	
	private String consigneePhone;//收货人手机
	
	private String consigneeTell;//收货人电话
	
	private String consigneeEmail;//邮编
	
    private String consigneeYear;//当前日期-年
	
	private String consigneeMonth;//当前日期-年
	
	private String consigneeDay;//当前日期-年
	
	private String commodityNum;//发货商家数量
	
	private String orderSourceId;//订单来源
	
	private String number;//对号
	
	private String money;//收款金额
	
	private String remark;//备注
	
	private String tbody;//模板主体内容

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	public String getLogisticsId() {
		return logisticsId;
	}

	public void setLogisticsId(String logisticsId) {
		this.logisticsId = logisticsId;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeigth() {
		return heigth;
	}

	public void setHeigth(Double heigth) {
		this.heigth = heigth;
	}

	public String getFontSize() {
		return fontSize;
	}

	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}

	public Integer getIsBold() {
		return isBold;
	}

	public void setIsBold(Integer isBold) {
		this.isBold = isBold;
	}

	public String getBackGroundImage() {
		return backGroundImage;
	}

	public void setBackGroundImage(String backGroundImage) {
		this.backGroundImage = backGroundImage;
	}

	public String getShipmentsName() {
		return shipmentsName;
	}

	public void setShipmentsName(String shipmentsName) {
		this.shipmentsName = shipmentsName;
	}

	public String getShipmentsOneArea() {
		return shipmentsOneArea;
	}

	public void setShipmentsOneArea(String shipmentsOneArea) {
		this.shipmentsOneArea = shipmentsOneArea;
	}

	public String getShipmentsTwoArea() {
		return shipmentsTwoArea;
	}

	public void setShipmentsTwoArea(String shipmentsTwoArea) {
		this.shipmentsTwoArea = shipmentsTwoArea;
	}

	public String getShipmentsThreeArea() {
		return shipmentsThreeArea;
	}

	public void setShipmentsThreeArea(String shipmentsThreeArea) {
		this.shipmentsThreeArea = shipmentsThreeArea;
	}

	public String getShipmentsAdress() {
		return shipmentsAdress;
	}

	public void setShipmentsAdress(String shipmentsAdress) {
		this.shipmentsAdress = shipmentsAdress;
	}

	public String getShipmentsPhone() {
		return shipmentsPhone;
	}

	public void setShipmentsPhone(String shipmentsPhone) {
		this.shipmentsPhone = shipmentsPhone;
	}

	public String getShipmentsTell() {
		return shipmentsTell;
	}

	public void setShipmentsTell(String shipmentsTell) {
		this.shipmentsTell = shipmentsTell;
	}

	public String getShipmentsEmail() {
		return shipmentsEmail;
	}

	public void setShipmentsEmail(String shipmentsEmail) {
		this.shipmentsEmail = shipmentsEmail;
	}

	public String getOrderSubNo() {
		return orderSubNo;
	}

	public void setOrderSubNo(String orderSubNo) {
		this.orderSubNo = orderSubNo;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeOneArea() {
		return consigneeOneArea;
	}

	public void setConsigneeOneArea(String consigneeOneArea) {
		this.consigneeOneArea = consigneeOneArea;
	}

	public String getConsigneeTwoArea() {
		return consigneeTwoArea;
	}

	public void setConsigneeTwoArea(String consigneeTwoArea) {
		this.consigneeTwoArea = consigneeTwoArea;
	}

	public String getConsigneeThreeArea() {
		return consigneeThreeArea;
	}

	public void setConsigneeThreeArea(String consigneeThreeArea) {
		this.consigneeThreeArea = consigneeThreeArea;
	}

	public String getConsigneeAdress() {
		return consigneeAdress;
	}

	public void setConsigneeAdress(String consigneeAdress) {
		this.consigneeAdress = consigneeAdress;
	}

	public String getConsigneePhone() {
		return consigneePhone;
	}

	public void setConsigneePhone(String consigneePhone) {
		this.consigneePhone = consigneePhone;
	}

	public String getConsigneeTell() {
		return consigneeTell;
	}

	public void setConsigneeTell(String consigneeTell) {
		this.consigneeTell = consigneeTell;
	}

	public String getConsigneeEmail() {
		return consigneeEmail;
	}

	public void setConsigneeEmail(String consigneeEmail) {
		this.consigneeEmail = consigneeEmail;
	}

	public String getConsigneeYear() {
		return consigneeYear;
	}

	public void setConsigneeYear(String consigneeYear) {
		this.consigneeYear = consigneeYear;
	}

	public String getConsigneeMonth() {
		return consigneeMonth;
	}

	public void setConsigneeMonth(String consigneeMonth) {
		this.consigneeMonth = consigneeMonth;
	}

	public String getConsigneeDay() {
		return consigneeDay;
	}

	public void setConsigneeDay(String consigneeDay) {
		this.consigneeDay = consigneeDay;
	}

	public String getCommodityNum() {
		return commodityNum;
	}

	public void setCommodityNum(String commodityNum) {
		this.commodityNum = commodityNum;
	}

	public String getOrderSourceId() {
		return orderSourceId;
	}

	public void setOrderSourceId(String orderSourceId) {
		this.orderSourceId = orderSourceId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTbody() {
		return tbody;
	}

	public void setTbody(String tbody) {
		this.tbody = tbody;
	}

	public MerchantExpressTemplate(String id, String expressName,
			String logisticsId, Double width, Double heigth, String fontSize,
			Integer isBold, String backGroundImage, String shipmentsName,
			String shipmentsOneArea, String shipmentsTwoArea,
			String shipmentsThreeArea, String shipmentsAdress,
			String shipmentsPhone, String shipmentsTell, String shipmentsEmail,
			String orderSubNo, String consigneeName, String consigneeOneArea,
			String consigneeTwoArea, String consigneeThreeArea,
			String consigneeAdress, String consigneePhone,
			String consigneeTell, String consigneeEmail, String consigneeYear,
			String consigneeMonth, String consigneeDay, String commodityNum,
			String orderSourceId, String number, String money, String remark,
			String tbody) {
		super();
		this.id = id;
		this.expressName = expressName;
		this.logisticsId = logisticsId;
		this.width = width;
		this.heigth = heigth;
		this.fontSize = fontSize;
		this.isBold = isBold;
		this.backGroundImage = backGroundImage;
		this.shipmentsName = shipmentsName;
		this.shipmentsOneArea = shipmentsOneArea;
		this.shipmentsTwoArea = shipmentsTwoArea;
		this.shipmentsThreeArea = shipmentsThreeArea;
		this.shipmentsAdress = shipmentsAdress;
		this.shipmentsPhone = shipmentsPhone;
		this.shipmentsTell = shipmentsTell;
		this.shipmentsEmail = shipmentsEmail;
		this.orderSubNo = orderSubNo;
		this.consigneeName = consigneeName;
		this.consigneeOneArea = consigneeOneArea;
		this.consigneeTwoArea = consigneeTwoArea;
		this.consigneeThreeArea = consigneeThreeArea;
		this.consigneeAdress = consigneeAdress;
		this.consigneePhone = consigneePhone;
		this.consigneeTell = consigneeTell;
		this.consigneeEmail = consigneeEmail;
		this.consigneeYear = consigneeYear;
		this.consigneeMonth = consigneeMonth;
		this.consigneeDay = consigneeDay;
		this.commodityNum = commodityNum;
		this.orderSourceId = orderSourceId;
		this.number = number;
		this.money = money;
		this.remark = remark;
		this.tbody = tbody;
	}

	public MerchantExpressTemplate() {
		super();
	}
}
