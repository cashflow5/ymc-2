package com.belle.yitiansystem.merchant.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 商家发货地址设置
 * @author wang.m
 * @DATE 2012-04-28
 *
 */
@Entity
@Table(name = "tbl_merchant_consignmentadress")
public class MerchantconsignmentAdress {

	private String id;
	private String supplyId;//商家id
	private String consignmentName;//发货人姓名
	private Integer phone;//发货人手机
	private String tell;//发货人电话
	private String postCode;//发货人邮编
	private String area;//发货人地区
	private String adress;//发货人地址
	private String creater;//创建人
	private String createTime;//创建时间
	private String remark;//备注
	

    @GenericGenerator(name = "generator", strategy = "uuid.hex")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "supply_id")
	public String getSupplyId() {
		return supplyId;
	}

	public void setSupplyId(String supplyId) {
		this.supplyId = supplyId;
	}
	@Column(name = "consignment_name")
	public String getConsignmentName() {
		return consignmentName;
	}

	public void setConsignmentName(String consignmentName) {
		this.consignmentName = consignmentName;
	}
	@Column(name = "phone")
	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}
	@Column(name = "tell")
	public String getTell() {
		return tell;
	}

	public void setTell(String tell) {
		this.tell = tell;
	}
	@Column(name = "post_code")
	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	@Column(name = "area")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	@Column(name = "adress")
	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}
	@Column(name = "creater")
	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}
	@Column(name = "create_time")
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public MerchantconsignmentAdress(String id, String supplyId,
			String consignmentName, Integer phone, String tell, String postCode,
			String area, String adress, String creater, String createTime,
			String remark) {
		super();
		this.id = id;
		this.supplyId = supplyId;
		this.consignmentName = consignmentName;
		this.phone = phone;
		this.tell = tell;
		this.postCode = postCode;
		this.area = area;
		this.adress = adress;
		this.creater = creater;
		this.createTime = createTime;
		this.remark = remark;
	}

	public MerchantconsignmentAdress() {
		super();
	}
	
}
