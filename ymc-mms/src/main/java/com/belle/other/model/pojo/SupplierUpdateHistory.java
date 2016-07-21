package com.belle.other.model.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 供应商更新历史
 * @author zhubin
 * date：2012-1-5 下午5:25:53
 */
@Entity
@Table(name="tbl_sp_supplier_update_history")
public class SupplierUpdateHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//主键ID
	private String id ;
	
	//操作人
	private String operator;
	
	//操作时间
	private Date operatorTime;
	
	//操作
	private String processing;
	
	//字段
	private String field;
	
	//修改前
	private String update_before;
	
	//修改后
	private String update_after;
	
	//所属供应商
	private SupplierSp supplier;
	
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
	
	@Column(name = "operator", length = 50)
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Column(name = "operation_time", length = 25)
	public Date getOperatorTime() {
		return operatorTime;
	}

	public void setOperatorTime(Date operatorTime) {
		this.operatorTime = operatorTime;
	}
	@Column(name = "processing")
	public String getProcessing() {
		return processing;
	}

	public void setProcessing(String processing) {
		this.processing = processing;
	}
	@Column(name = "update_field")
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	@Column(name = "update_before")
	public String getUpdate_before() {
		return update_before;
	}

	public void setUpdate_before(String update_before) {
		this.update_before = update_before;
	}
	@Column(name = "update_after")
	public String getUpdate_after() {
		return update_after;
	}

	public void setUpdate_after(String update_after) {
		this.update_after = update_after;
	}

	
	
	
	
	
	

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "supplier_id")
	public SupplierSp getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierSp supplier) {
		this.supplier = supplier;
	}
	
	
	
	
}
