package com.belle.other.model.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;


/**
 * 
 * Logisticscompany entity.
 * 
 * @author MyEclipse Persistence Tools 物流公司
 */
@Entity
@Table(name = "tbl_wms_logisticscompany")
public class Logisticscompany implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 9142836430324684762L;
	//落地仓库配置  add 20110831_1006 by duansiyuan
	private String logisticsConfigStr;
	private String id;
	// 物流公司编号
	private String logisticCompanyCode;
	// 物流公司名称
	@Size(max = 100, message = "logisticscompany.entity.name.ck.error")
	private String logisticsCompanyName;
	// 物流公司网址
	@Size(max = 120, message = "logisticscompany.entity.url.ck.error")
	private String logisticsCompanyUrl;
	// 物流公司联系人
	@Size(max = 60, message = "logisticscompany.entity.url.ck.error")
	private String contact;
	// 电话
	@Pattern(regexp = "(^[0-9]{3,4}\\-[0-9]{7,8}$)|(^[0-9]{2,5}\\-[0-9]{3,5}\\-[0-9]{3,6}$)|(^[0-9]{2,5}\\-[0-9]{2,5}\\-[0-9]{2,6}\\-[0-9]{2,6}$)", message = "entity.telphone.ck.error")
	private String telPhone;
	// 手机
	@Pattern(regexp = "^(1[3|4|5|8][0-9]\\d{8})$", message = "entity.mobilephone.ck.error")
	private String mobilePhone;
	private String qq;
	private String msn;
	// 物流公司电子邮件
	// @Pattern(regexp =
	// "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$",
	// message = "entity.email.ck.error")
	@Pattern(regexp = "^(\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)$", message = "entity.email.ck.error")
	private String email;
	// 物流公司地址
	@Size(max = 255, message = "entity.address.ck.error")
	private String logisticsCompanyAddress;
	private Integer status;
	// 创建时间
	private Date createDate;
	@Size(max = 250, message = "entity.remark.ck.error")
	// 备注
	private String remark;
	// 排序
	private Integer sort;
	// 否是支持货到付款
	private Integer isCod;
	// 是否支持物流配送接口
	private Integer isSldi;
	// 是否打印快递单
	private Integer isPrint;

	private Integer priorityRating;
	private String mark_code;  // 快递公司标识编码
	private String mark_name;  //快递公司标识名称
	
	//支持订单来源    全部 0; 优购 1; 非优购 2;
	private Integer supportSource;
	
	// 公司全称
	private String logisticCompanyFullname;
	// 邮编
	private String logisticCompanyPostcode;
	// 快递100物流编码
	private String logisticCompanyPost100Code;
	// 淘宝物流编码
	private String logisticCompanyTaobaoCode;
	// 匹配优先级
	private Integer logisticCompanyPriority;
	
	private Integer isMerchant;//使用方  1为优购   2 为商家使用   (wang.M)
	
	private Short isFreightCollect;//0：不支持运费到付；	1：支持运费到付:
	
	private String groupCode;//关联组编号
	
	private String groupName;//关联组名称
	
	@Column(name = "group_code")
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	@Column(name = "group_name")
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name = "is_freight_collect")
	 public Short getIsFreightCollect() {
		return isFreightCollect;
	}


	public void setIsFreightCollect(Short isFreightCollect) {
		this.isFreightCollect = isFreightCollect;
	}


	@Column(name = "is_merchant")
	public Integer getIsMerchant() {
		return isMerchant;
	}


	public void setIsMerchant(Integer isMerchant) {
		this.isMerchant = isMerchant;
	}
	
	//落地仓库配置  add 20110831_1006 by duansiyuan
    @Column(name = "logistics_config_str")
    public String getLogisticsConfigStr() {
        return logisticsConfigStr;
    }


    public void setLogisticsConfigStr(String logisticsConfigStr) {
        this.logisticsConfigStr = logisticsConfigStr;
    }

	@Column(name = "priority_rating")
	public Integer getPriorityRating() {
		return priorityRating;
	}

	public void setPriorityRating(Integer priorityRating) {
		this.priorityRating = priorityRating;
	}

	@Column(name = "is_sldi")
	public Integer getIsSldi() {
		return isSldi;
	}

	public void setIsSldi(Integer isSldi) {
		this.isSldi = isSldi;
	}

	@Column(name = "is_cod")
	public Integer getIsCod() {
		return isCod;
	}

	public void setIsCod(Integer isCod) {
		this.isCod = isCod;
	}

	private Date updateDate;
	private Long updateTimestamp;

	// Constructors

	/** default constructor */
	public Logisticscompany() {
	}

	/** minimal constructor */
	public Logisticscompany(String logisticCompanyCode, String logisticsCompanyName, String logisticsCompanyUrl, String contact, String telPhone,
			String mobilePhone, String email, String logisticsCompanyAddress, Integer status, Date createDate) {
		this.logisticCompanyCode = logisticCompanyCode;
		this.logisticsCompanyName = logisticsCompanyName;
		this.logisticsCompanyUrl = logisticsCompanyUrl;
		this.contact = contact;
		this.telPhone = telPhone;
		this.mobilePhone = mobilePhone;
		this.email = email;
		this.logisticsCompanyAddress = logisticsCompanyAddress;
		this.status = status;
		this.createDate = createDate;
	}

	/** full constructor */
	public Logisticscompany(String logisticCompanyCode, String logisticsCompanyName, String logisticsCompanyUrl, String contact, String telPhone,
			String mobilePhone, String qq, String msn, String email, String logisticsCompanyAddress, Integer status, Date createDate, String remark,
			Integer sort, String groupCode, String groupName) {
		this.logisticCompanyCode = logisticCompanyCode;
		this.logisticsCompanyName = logisticsCompanyName;
		this.logisticsCompanyUrl = logisticsCompanyUrl;
		this.contact = contact;
		this.telPhone = telPhone;
		this.mobilePhone = mobilePhone;
		this.qq = qq;
		this.msn = msn;
		this.email = email;
		this.logisticsCompanyAddress = logisticsCompanyAddress;
		this.status = status;
		this.createDate = createDate;
		this.remark = remark;
		this.sort = sort;
		this.groupCode = groupCode;
		this.groupName = groupName;
	}

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "logistic_company_code", nullable = false, length = 32)
	public String getLogisticCompanyCode() {
		return this.logisticCompanyCode;
	}

	public void setLogisticCompanyCode(String logisticCompanyCode) {
		this.logisticCompanyCode = logisticCompanyCode;
	}

	@Column(name = "logistics_company_name", nullable = false, length = 200)
	public String getLogisticsCompanyName() {
		return this.logisticsCompanyName;
	}

	public void setLogisticsCompanyName(String logisticsCompanyName) {
		this.logisticsCompanyName = logisticsCompanyName;
	}

	@Column(name = "logistics_company_url", nullable = false, length = 120)
	public String getLogisticsCompanyUrl() {
		return this.logisticsCompanyUrl;
	}

	public void setLogisticsCompanyUrl(String logisticsCompanyUrl) {
		this.logisticsCompanyUrl = logisticsCompanyUrl;
	}

	@Column(name = "contact", nullable = false, length = 60)
	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(name = "tel_phone", nullable = false, length = 30)
	public String getTelPhone() {
		return this.telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	@Column(name = "mobile_phone", nullable = false, length = 30)
	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name = "qq", length = 30)
	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Column(name = "msn", length = 120)
	public String getMsn() {
		return this.msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	@Column(name = "email", nullable = false, length = 120)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "logistics_company_address", nullable = false, length = 200)
	public String getLogisticsCompanyAddress() {
		return this.logisticsCompanyAddress;
	}

	public void setLogisticsCompanyAddress(String logisticsCompanyAddress) {
		this.logisticsCompanyAddress = logisticsCompanyAddress;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", nullable = false)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "sort")
	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date")
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "update_timestamp")
	public Long getUpdateTimestamp() {
		return this.updateTimestamp;
	}

	public void setUpdateTimestamp(Long updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

	@Column(name = "is_print")
	public Integer getIsPrint() {
		return isPrint;
	}

	public void setIsPrint(Integer isPrint) {
		this.isPrint = isPrint;
	}


	@Column(name="mark_code")
	public String getMark_code() {
		return mark_code;
	}


	public void setMark_code(String markCode) {
		mark_code = markCode;
	}

	@Column(name="mark_name")
	public String getMark_name() {
		return mark_name;
	}


	public void setMark_name(String markName) {
		mark_name = markName;
	}
	
	@Column(name="support_source")
	public Integer getSupportSource() {
		return supportSource;
	}


	public void setSupportSource(Integer supportSource) {
		this.supportSource = supportSource;
	}
	@Column(name = "logistic_company_fullname")
	public String getLogisticCompanyFullname() {
		return logisticCompanyFullname;
	}

	public void setLogisticCompanyFullname(String logisticCompanyFullname) {
		this.logisticCompanyFullname = logisticCompanyFullname;
	}

	@Column(name = "logistic_company_postcode")
	public String getLogisticCompanyPostcode() {
		return logisticCompanyPostcode;
	}

	public void setLogisticCompanyPostcode(String logisticCompanyPostcode) {
		this.logisticCompanyPostcode = logisticCompanyPostcode;
	}
	@Column(name = "logistic_company_post100code")
	public String getLogisticCompanyPost100Code() {
		return logisticCompanyPost100Code;
	}

	public void setLogisticCompanyPost100Code(String logisticCompanyPost100Code) {
		this.logisticCompanyPost100Code = logisticCompanyPost100Code;
	}
	@Column(name = "logistic_company_taobaocode")
	public String getLogisticCompanyTaobaoCode() {
		return logisticCompanyTaobaoCode;
	}

	public void setLogisticCompanyTaobaoCode(String logisticCompanyTaobaoCode) {
		this.logisticCompanyTaobaoCode = logisticCompanyTaobaoCode;
	}
	@Column(name = "logistic_company_priority") 
	public Integer getLogisticCompanyPriority() {
		return logisticCompanyPriority;
	}
	
	public void setLogisticCompanyPriority(Integer logisticCompanyPriority) {
		this.logisticCompanyPriority = logisticCompanyPriority;
	}
	

}