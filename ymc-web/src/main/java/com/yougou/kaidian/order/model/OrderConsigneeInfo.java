package com.yougou.kaidian.order.model;

import java.io.Serializable;
/**
 * 
 * @author daixiaowei
 * @date 2012-03-07 10:53:36
 *
 */
public class OrderConsigneeInfo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_order_consignee_info.id
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_order_consignee_info.user_name
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_order_consignee_info.constact_phone
     *
     * @mbggenerated
     */
    private String constactPhone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_order_consignee_info.email
     *
     * @mbggenerated
     */
    private String email;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_order_consignee_info.mobile_phone
     *
     * @mbggenerated
     */
    private String mobilePhone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_order_consignee_info.delivery_date
     *
     * @mbggenerated
     */
    private String deliveryDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_order_consignee_info.zip_code
     *
     * @mbggenerated
     */
    private String zipCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_order_consignee_info.area
     *
     * @mbggenerated
     */
    private String area;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_order_consignee_info.consignee_address
     *
     * @mbggenerated
     */
    private String consigneeAddress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_order_consignee_info.province
     *
     * @mbggenerated
     */
    private String province;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_order_consignee_info.city
     *
     * @mbggenerated
     */
    private String city;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_order_consignee_info.ship_method
     *
     * @mbggenerated
     */
    private Integer shipMethod;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_order_consignee_info.is_urgent
     *
     * @mbggenerated
     */
    private Integer isUrgent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_order_consignee_info.delivery_costs
     *
     * @mbggenerated
     */
    private Double deliveryCosts;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_order_consignee_info.del_flag
     *
     * @mbggenerated
     */
    private Short delFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_order_consignee_info.split_flag
     *
     * @mbggenerated
     */
    private Short splitFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_order_consignee_info.consignee_no
     *
     * @mbggenerated
     */
    private String consigneeNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_order_consignee_info.order_main_no
     *
     * @mbggenerated
     */
    private String orderMainNo;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_order_consignee_info.id
     *
     * @return the value of tbl_order_consignee_info.id
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_order_consignee_info.id
     *
     * @param id the value for tbl_order_consignee_info.id
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_order_consignee_info.user_name
     *
     * @return the value of tbl_order_consignee_info.user_name
     *
     * @mbggenerated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_order_consignee_info.user_name
     *
     * @param userName the value for tbl_order_consignee_info.user_name
     *
     * @mbggenerated
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_order_consignee_info.constact_phone
     *
     * @return the value of tbl_order_consignee_info.constact_phone
     *
     * @mbggenerated
     */
    public String getConstactPhone() {
        return constactPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_order_consignee_info.constact_phone
     *
     * @param constactPhone the value for tbl_order_consignee_info.constact_phone
     *
     * @mbggenerated
     */
    public void setConstactPhone(String constactPhone) {
        this.constactPhone = constactPhone == null ? null : constactPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_order_consignee_info.email
     *
     * @return the value of tbl_order_consignee_info.email
     *
     * @mbggenerated
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_order_consignee_info.email
     *
     * @param email the value for tbl_order_consignee_info.email
     *
     * @mbggenerated
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_order_consignee_info.mobile_phone
     *
     * @return the value of tbl_order_consignee_info.mobile_phone
     *
     * @mbggenerated
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_order_consignee_info.mobile_phone
     *
     * @param mobilePhone the value for tbl_order_consignee_info.mobile_phone
     *
     * @mbggenerated
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone == null ? null : mobilePhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_order_consignee_info.delivery_date
     *
     * @return the value of tbl_order_consignee_info.delivery_date
     *
     * @mbggenerated
     */
    public String getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_order_consignee_info.delivery_date
     *
     * @param deliveryDate the value for tbl_order_consignee_info.delivery_date
     *
     * @mbggenerated
     */
    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate == null ? null : deliveryDate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_order_consignee_info.zip_code
     *
     * @return the value of tbl_order_consignee_info.zip_code
     *
     * @mbggenerated
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_order_consignee_info.zip_code
     *
     * @param zipCode the value for tbl_order_consignee_info.zip_code
     *
     * @mbggenerated
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_order_consignee_info.area
     *
     * @return the value of tbl_order_consignee_info.area
     *
     * @mbggenerated
     */
    public String getArea() {
        return area;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_order_consignee_info.area
     *
     * @param area the value for tbl_order_consignee_info.area
     *
     * @mbggenerated
     */
    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_order_consignee_info.consignee_address
     *
     * @return the value of tbl_order_consignee_info.consignee_address
     *
     * @mbggenerated
     */
    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_order_consignee_info.consignee_address
     *
     * @param consigneeAddress the value for tbl_order_consignee_info.consignee_address
     *
     * @mbggenerated
     */
    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress == null ? null : consigneeAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_order_consignee_info.province
     *
     * @return the value of tbl_order_consignee_info.province
     *
     * @mbggenerated
     */
    public String getProvince() {
        return province;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_order_consignee_info.province
     *
     * @param province the value for tbl_order_consignee_info.province
     *
     * @mbggenerated
     */
    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_order_consignee_info.city
     *
     * @return the value of tbl_order_consignee_info.city
     *
     * @mbggenerated
     */
    public String getCity() {
        return city;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_order_consignee_info.city
     *
     * @param city the value for tbl_order_consignee_info.city
     *
     * @mbggenerated
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_order_consignee_info.ship_method
     *
     * @return the value of tbl_order_consignee_info.ship_method
     *
     * @mbggenerated
     */
    public Integer getShipMethod() {
        return shipMethod;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_order_consignee_info.ship_method
     *
     * @param shipMethod the value for tbl_order_consignee_info.ship_method
     *
     * @mbggenerated
     */
    public void setShipMethod(Integer shipMethod) {
        this.shipMethod = shipMethod;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_order_consignee_info.is_urgent
     *
     * @return the value of tbl_order_consignee_info.is_urgent
     *
     * @mbggenerated
     */
    public Integer getIsUrgent() {
        return isUrgent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_order_consignee_info.is_urgent
     *
     * @param isUrgent the value for tbl_order_consignee_info.is_urgent
     *
     * @mbggenerated
     */
    public void setIsUrgent(Integer isUrgent) {
        this.isUrgent = isUrgent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_order_consignee_info.delivery_costs
     *
     * @return the value of tbl_order_consignee_info.delivery_costs
     *
     * @mbggenerated
     */
    public Double getDeliveryCosts() {
        return deliveryCosts;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_order_consignee_info.delivery_costs
     *
     * @param deliveryCosts the value for tbl_order_consignee_info.delivery_costs
     *
     * @mbggenerated
     */
    public void setDeliveryCosts(Double deliveryCosts) {
        this.deliveryCosts = deliveryCosts;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_order_consignee_info.del_flag
     *
     * @return the value of tbl_order_consignee_info.del_flag
     *
     * @mbggenerated
     */
    public Short getDelFlag() {
        return delFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_order_consignee_info.del_flag
     *
     * @param delFlag the value for tbl_order_consignee_info.del_flag
     *
     * @mbggenerated
     */
    public void setDelFlag(Short delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_order_consignee_info.split_flag
     *
     * @return the value of tbl_order_consignee_info.split_flag
     *
     * @mbggenerated
     */
    public Short getSplitFlag() {
        return splitFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_order_consignee_info.split_flag
     *
     * @param splitFlag the value for tbl_order_consignee_info.split_flag
     *
     * @mbggenerated
     */
    public void setSplitFlag(Short splitFlag) {
        this.splitFlag = splitFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_order_consignee_info.consignee_no
     *
     * @return the value of tbl_order_consignee_info.consignee_no
     *
     * @mbggenerated
     */
    public String getConsigneeNo() {
        return consigneeNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_order_consignee_info.consignee_no
     *
     * @param consigneeNo the value for tbl_order_consignee_info.consignee_no
     *
     * @mbggenerated
     */
    public void setConsigneeNo(String consigneeNo) {
        this.consigneeNo = consigneeNo == null ? null : consigneeNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_order_consignee_info.order_main_no
     *
     * @return the value of tbl_order_consignee_info.order_main_no
     *
     * @mbggenerated
     */
    public String getOrderMainNo() {
        return orderMainNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_order_consignee_info.order_main_no
     *
     * @param orderMainNo the value for tbl_order_consignee_info.order_main_no
     *
     * @mbggenerated
     */
    public void setOrderMainNo(String orderMainNo) {
        this.orderMainNo = orderMainNo == null ? null : orderMainNo.trim();
    }
}