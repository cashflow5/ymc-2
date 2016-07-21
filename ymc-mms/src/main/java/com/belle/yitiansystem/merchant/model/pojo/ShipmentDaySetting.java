package com.belle.yitiansystem.merchant.model.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 招商商家发货日期设置表
 * @author he.wc
 *
 */
@Entity
@Table(name = "tbl_merchant_shipment_day_setting")
public class ShipmentDaySetting implements Serializable {
	private static final long serialVersionUID = 6844971781898995802L;
	
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	private String id;
	//日期
	
	@Column(name = "date")
	private Date date;
	
	//年份
	@Column(name = "year")
	private Integer year;
	
	//月份
	@Column(name = "month")
	private Integer month;
	
	//日
	@Column(name = "day")
	private Integer day;
	
	//是否是发货日
	@Column(name = "is_shipment_day")
	private String isShipmentDay;
	
	//创建时间
	@Column(name = "create_time")
	private Date createTime;
	
	//修改时间
	@Column(name = "update_time")
	private Date updateTime;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Integer getYear() {
		return year;
	}
	
	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public String getIsShipmentDay() {
		return isShipmentDay;
	}

	public void setIsShipmentDay(String isShipmentDay) {
		this.isShipmentDay = isShipmentDay;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "MerchantShipmentDay [id=" + id + ", date=" + date + ", year=" + year + ", month=" + month + ", day="
				+ day + ", isShipmentDay=" + isShipmentDay + ", createTime=" + createTime + ", updateTime="
				+ updateTime + "]";
	}
	
	
}
