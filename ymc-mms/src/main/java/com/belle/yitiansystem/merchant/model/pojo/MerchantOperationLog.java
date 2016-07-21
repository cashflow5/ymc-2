package com.belle.yitiansystem.merchant.model.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tbl_merchant_operation_log")
public class MerchantOperationLog {

	private String id;
	private String operator;// 操作人
	private Date operated;// 操作时间
	private OperationType operationType;// 操作类型
	private String operationNotes;// 操作备注
	private String merchantCode;// 商家编码
	private String userId;//商家账号用户Id
	private String remark;//填写的备注
	
	public MerchantOperationLog() {
		super();
	}

	public MerchantOperationLog(String id) {
		super();
		this.id = id;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 32)
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@GeneratedValue(generator = "generator")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "operator", nullable = false, length = 16)
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "operated", nullable = false, length = 19)
	public Date getOperated() {
		return operated;
	}

	public void setOperated(Date operated) {
		this.operated = operated;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "operation_type", nullable = false, length = 16)
	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

	@Column(name = "operation_notes", nullable = false)
	public String getOperationNotes() {
		return operationNotes;
	}

	public void setOperationNotes(String operationNotes) {
		this.operationNotes = operationNotes;
	}

	@Column(name = "merchant_code", nullable = false, length = 32)
	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	@Column(name = "user_id", length = 50)
	public String getUserId() {
		return userId;
	}
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 操作类型
	 * 备注：日志的种类 一种根据type=0或1，标志合同操作日志和商家操作日志；另一种根据operationType来标志，具体如下：
	 *     1.SHOP 店铺列表的操作日志
	 *     2.ACCOUNT 商家权限列表的操作日志
	 *     3.USER 账号管理列表的操作日志
	 *     
	 */
	public static enum OperationType {
		BASIC_DATA("商家资料"), 
		ACCOUNT("商家帐户"), 
		CONTRACT("合同"), 
		CONTACT("联系人"),
		TAOBAO_BAND("淘宝品牌操作"),
		TAOBAO_CAT("淘宝分类操作"),
		TAOBAO_B_BIND("淘宝优购品牌绑定"),
		TAOBAO_CAT_BIND("淘宝优购分类绑定"),
		AFTER_SERVICE("售后"),
		API("API"),
		AppKey_ADD("添加AppKey"), APPKEY_AUTH("授权AppKey"), APPKEY_BINDING("绑定AppKey"), APPKEY_UNBUND("解绑AppKey"), APPKEY_ENABLED("启用AppKey"), APPKEY_DISABLE("禁用AppKey"),
		AUTH_ADD("添加资源"), AUTH_UPDATE("修改资源"), AUTH_DELETE("删除资源"),
		SHOP("旗舰店"),PUNISHORDER("违规订单"),CREATEORUPDATE("新建或更新");
		
		private String description;

		private OperationType(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
	}
}
