package com.yougou.kaidian.commodity.model.pojo;

import java.util.Date;

public class MerchantCommodityPics {
	private String id;
	private Integer outerPicId;// 优购图片ID
	private String innerPicName;// 商家图片名称
	private String thumbnaiPicName;// 商家图片缩略图名称
	private String innerPicPath;// 商家图片路径
	private PicCategory picCategory;//商家图片分类
	private Long picSize;// 图片大小(字节)
	private Date created;//图片上传时间
	private String merchantCode;// 商家编码
	
	private String commodityNo;// 图片商品编号(冗余字段)
	private String picType;// 图片类型(冗余字段)
	private String picPath;// 图片路径(冗余字段)
	private String picName;// 图片名称(冗余字段)
	private Short picStatus;//图片状态(冗余字段,11新建，12提交审核，13审核拒绝)

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getOuterPicId() {
		return outerPicId;
	}

	public void setOuterPicId(Integer outerPicId) {
		this.outerPicId = outerPicId;
	}

	public String getInnerPicPath() {
		return innerPicPath;
	}

	public void setInnerPicPath(String innerPicPath) {
		this.innerPicPath = innerPicPath;
	}

	public PicCategory getPicCategory() {
		return picCategory;
	}

	public void setPicCategory(PicCategory picCategory) {
		this.picCategory = picCategory;
	}

	public String getInnerPicName() {
		return innerPicName;
	}

	public void setInnerPicName(String innerPicName) {
		this.innerPicName = innerPicName;
	}

	public Long getPicSize() {
		return picSize;
	}

	public void setPicSize(Long picSize) {
		this.picSize = picSize;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getCommodityNo() {
		return commodityNo;
	}

	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getPicType() {
		return picType;
	}

	public void setPicType(String picType) {
		this.picType = picType;
	}

	public String getPicPath() {
		return picPath;
	}
	
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	
	public Short getPicStatus() {
		return picStatus;
	}

	public void setPicStatus(Short picStatus) {
		this.picStatus = picStatus;
	}

	public String getThumbnaiPicName() {
		return thumbnaiPicName;
	}

	public void setThumbnaiPicName(String thumbnaiPicName) {
		this.thumbnaiPicName = thumbnaiPicName;
	}


	public static enum PicCategory {
		
	}
}
