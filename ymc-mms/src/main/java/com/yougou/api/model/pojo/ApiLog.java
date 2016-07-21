package com.yougou.api.model.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.yougou.api.annotation.Documented;

@Entity
@Table(name = "tbl_merchant_api_log")
@Documented(name = "tbl_merchant_api_log")
public class ApiLog implements java.io.Serializable {

	private static final long serialVersionUID = 1557534809178005470L;
	private String id;
	private String apiMethod;
	private String operationParameters;
	private String operationResult;
	private String clientIp;
	private String operator;
	private Date operated;
	private Boolean isCallSucess;

	public ApiLog() {
	}
	
	public ApiLog(String id) {
		this.id = id;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, length = 32)
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@GeneratedValue(generator = "generator")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "api_method", nullable = false, length = 128)
	public String getApiMethod() {
		return this.apiMethod;
	}

	public void setApiMethod(String apiMethod) {
		this.apiMethod = apiMethod;
	}

	@Column(name = "operation_parameters", length = 65535)
	public String getOperationParameters() {
		return this.operationParameters;
	}

	public void setOperationParameters(String operationParameters) {
		this.operationParameters = operationParameters;
	}

	@Column(name = "operation_result", length = 65535)
	public String getOperationResult() {
		return this.operationResult;
	}

	public void setOperationResult(String operationResult) {
		this.operationResult = operationResult;
	}

	@Column(name = "client_ip", length = 39)
	public String getClientIp() {
		return this.clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	@Column(name = "operator", nullable = false, length = 32)
	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "operated", nullable = false, length = 19)
	public Date getOperated() {
		return this.operated;
	}

	public void setOperated(Date operated) {
		this.operated = operated;
	}


	public Boolean getIsCallSucess() {
		return isCallSucess;
	}

	public void setIsCallSucess(Boolean isCallSucess) {
		this.isCallSucess = isCallSucess;
	}

	@Override
	public String toString() {
		return new StringBuilder(" com.yougou.api.model.pojo.ApiLog { ")
		.append("id = ")
		.append(id)
		.append(", apiMethod = ")
		.append(apiMethod)
		.append(", operationParameters = ")
		.append(operationParameters)
		.append(", operationResult = ")
		.append(operationResult)
		.append(", clientIp = ")
		.append(clientIp)
		.append(", operator = ")
		.append(operator)
		.append(", operated = ")
		.append(operated)
		.append(" } ")
		.toString();
	}

}
