package com.yougou.kaidian.order.model;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @author daixiaowei
 * @date 2012-03-07 10:53:36
 *
 */
public class OrderRemark implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_order_remark.id
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_order_remark.content
     *
     * @mbggenerated
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_order_remark.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_order_remark.exception_type
     *
     * @mbggenerated
     */
    private String exceptionType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_order_remark.operate_user
     *
     * @mbggenerated
     */
    private String operateUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_order_remark.order_no
     *
     * @mbggenerated
     */
    private String orderNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_order_remark.remark_type
     *
     * @mbggenerated
     */
    private Integer remarkType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_order_remark.order_remark_no
     *
     * @mbggenerated
     */
    private String orderRemarkNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_order_remark.del_flag
     *
     * @mbggenerated
     */
    private Short delFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_order_remark.split_flag
     *
     * @mbggenerated
     */
    private Short splitFlag;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_order_remark.id
     *
     * @return the value of tbl_order_remark.id
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_order_remark.id
     *
     * @param id the value for tbl_order_remark.id
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_order_remark.content
     *
     * @return the value of tbl_order_remark.content
     *
     * @mbggenerated
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_order_remark.content
     *
     * @param content the value for tbl_order_remark.content
     *
     * @mbggenerated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_order_remark.create_time
     *
     * @return the value of tbl_order_remark.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_order_remark.create_time
     *
     * @param createTime the value for tbl_order_remark.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_order_remark.exception_type
     *
     * @return the value of tbl_order_remark.exception_type
     *
     * @mbggenerated
     */
    public String getExceptionType() {
        return exceptionType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_order_remark.exception_type
     *
     * @param exceptionType the value for tbl_order_remark.exception_type
     *
     * @mbggenerated
     */
    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType == null ? null : exceptionType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_order_remark.operate_user
     *
     * @return the value of tbl_order_remark.operate_user
     *
     * @mbggenerated
     */
    public String getOperateUser() {
        return operateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_order_remark.operate_user
     *
     * @param operateUser the value for tbl_order_remark.operate_user
     *
     * @mbggenerated
     */
    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser == null ? null : operateUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_order_remark.order_no
     *
     * @return the value of tbl_order_remark.order_no
     *
     * @mbggenerated
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_order_remark.order_no
     *
     * @param orderNo the value for tbl_order_remark.order_no
     *
     * @mbggenerated
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_order_remark.remark_type
     *
     * @return the value of tbl_order_remark.remark_type
     *
     * @mbggenerated
     */
    public Integer getRemarkType() {
        return remarkType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_order_remark.remark_type
     *
     * @param remarkType the value for tbl_order_remark.remark_type
     *
     * @mbggenerated
     */
    public void setRemarkType(Integer remarkType) {
        this.remarkType = remarkType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_order_remark.order_remark_no
     *
     * @return the value of tbl_order_remark.order_remark_no
     *
     * @mbggenerated
     */
    public String getOrderRemarkNo() {
        return orderRemarkNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_order_remark.order_remark_no
     *
     * @param orderRemarkNo the value for tbl_order_remark.order_remark_no
     *
     * @mbggenerated
     */
    public void setOrderRemarkNo(String orderRemarkNo) {
        this.orderRemarkNo = orderRemarkNo == null ? null : orderRemarkNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_order_remark.del_flag
     *
     * @return the value of tbl_order_remark.del_flag
     *
     * @mbggenerated
     */
    public Short getDelFlag() {
        return delFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_order_remark.del_flag
     *
     * @param delFlag the value for tbl_order_remark.del_flag
     *
     * @mbggenerated
     */
    public void setDelFlag(Short delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_order_remark.split_flag
     *
     * @return the value of tbl_order_remark.split_flag
     *
     * @mbggenerated
     */
    public Short getSplitFlag() {
        return splitFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_order_remark.split_flag
     *
     * @param splitFlag the value for tbl_order_remark.split_flag
     *
     * @mbggenerated
     */
    public void setSplitFlag(Short splitFlag) {
        this.splitFlag = splitFlag;
    }
}