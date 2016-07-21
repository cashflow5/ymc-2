package com.belle.yitiansystem.systemmgmt.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 积分
 * 积分设置
 * @author xietongjian
 *
 */
@Entity
@Table(name = "tbl_systemmgt_integral")
public class SystemmgtIntegral implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -667398091217755271L;
	private String id;			//主键
	private String integralNo;	//积分编码
	private String integralName;//积分规则名称
	private Integer integral;	//积分数
	private Short deleteFlag;	//是否删除标识
	private String remark;		//备注说明

	
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
	
	@Column(name = "integral_no")
	public String getIntegralNo() {
		return integralNo;
	}
	public void setIntegralNo(String integralNo) {
		this.integralNo = integralNo;
	}
	
	@Column(name = "integral_name")
	public String getIntegralName() {
		return integralName;
	}
	public void setIntegralName(String integralName) {
		this.integralName = integralName;
	}
	
	@Column(name = "integral")
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	
	@Column(name = "delete_flag")
	public Short getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Short deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/** full constructor */
	public SystemmgtIntegral(String id, String integralNo, String integralName,
			Integer integral, Short deleteFlag, String remark) {
		super();
		this.id = id;
		this.integralNo = integralNo;
		this.integralName = integralName;
		this.integral = integral;
		this.deleteFlag = deleteFlag;
		this.remark = remark;
	}
	
	/** default constructor */
	public SystemmgtIntegral() {
	}

}
