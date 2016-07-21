package com.yougou.kaidian.asm.model;

import java.io.Serializable;
import java.util.Date;

public class OrderProductQuantityVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String order_sub_no;
	private String quality_type;
	private String express_code;
	private String express_name;
	private double express_charges;
	private String qa_person;
	private Date qa_date;
	private String user_name;
	private String mobile_phone;
	private String is_exception;
	private String status;
	private String reason;
	private String out_order_id;
	private String commodity_code;
	private String qa_inside_code;
	private String supplier_code;
	private String commodity_name;
	private String description;
	private String is_passed;
	private String size_name;
	private String shstatus;
	private String commodity_id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrder_sub_no() {
		return order_sub_no;
	}
	public void setOrder_sub_no(String order_sub_no) {
		this.order_sub_no = order_sub_no;
	}
	public String getQuality_type() {
		return quality_type;
	}
	public void setQuality_type(String quality_type) {
		this.quality_type = quality_type;
	}
	public String getExpress_code() {
		return express_code;
	}
	public void setExpress_code(String express_code) {
		this.express_code = express_code;
	}
	public String getExpress_name() {
		return express_name;
	}
	public void setExpress_name(String express_name) {
		this.express_name = express_name;
	}
	public double getExpress_charges() {
		return express_charges;
	}
	public void setExpress_charges(double express_charges) {
		this.express_charges = express_charges;
	}
	public String getQa_person() {
		return qa_person;
	}
	public void setQa_person(String qa_person) {
		this.qa_person = qa_person;
	}
	public Date getQa_date() {
		return qa_date;
	}
	public void setQa_date(Date qa_date) {
		this.qa_date = qa_date;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getMobile_phone() {
		return mobile_phone;
	}
	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}
	public String getIs_exception() {
		return is_exception;
	}
	public void setIs_exception(String is_exception) {
		this.is_exception = is_exception;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getOut_order_id() {
		return out_order_id;
	}
	public void setOut_order_id(String out_order_id) {
		this.out_order_id = out_order_id;
	}
	public String getCommodity_code() {
		return commodity_code;
	}
	public void setCommodity_code(String commodity_code) {
		this.commodity_code = commodity_code;
	}
	public String getQa_inside_code() {
		return qa_inside_code;
	}
	public void setQa_inside_code(String qa_inside_code) {
		this.qa_inside_code = qa_inside_code;
	}
	public String getSupplier_code() {
		return supplier_code;
	}
	public void setSupplier_code(String supplier_code) {
		this.supplier_code = supplier_code;
	}
	public String getCommodity_name() {
		return commodity_name;
	}
	public void setCommodity_name(String commodity_name) {
		this.commodity_name = commodity_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIs_passed() {
		return is_passed;
	}
	public void setIs_passed(String is_passed) {
		this.is_passed = is_passed;
	}
	public String getSize_name() {
		return size_name;
	}
	public void setSize_name(String size_name) {
		this.size_name = size_name;
	}
	public String getShstatus() {
		return shstatus;
	}
	public void setShstatus(String shstatus) {
		this.shstatus = shstatus;
	}
	public String getCommodity_id() {
		return commodity_id;
	}
	public void setCommodity_id(String commodity_id) {
		this.commodity_id = commodity_id;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
