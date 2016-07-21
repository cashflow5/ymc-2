package com.belle.other.model.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/*
 * 结算商家信息维护
 */
@Entity
@Table(name = "TBL_FIN_TRADER_MAINTAIN")
public class TraderMaintain implements Serializable {

	private static final long serialVersionUID = -7055067013403056960L;

	/** 结算商家ID */
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator") 
	@Column(name = "id", length = 32)
	private String id;
	
	/**结算商家编码**/
	@Column(name = "BALANCE_TRADER_CODE",length = 50)
	private String balanceTraderCode;					
	
	/**结算商家名称**/
	@Column(name = "BALANCE_TRADER_NAME",length = 32)
	private String balanceTraderName;					
	
	/**备注**/
	@Column(name = "REMARK",length = 100)
	private String remark;								
	
	/**标识：1：未删除；2：已删除**/
	@Column(name = "IS_DEL")
	private Integer isDel;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBalanceTraderCode() {
		return balanceTraderCode;
	}

	public void setBalanceTraderCode(String balanceTraderCode) {
		this.balanceTraderCode = balanceTraderCode;
	}

	public String getBalanceTraderName() {
		return balanceTraderName;
	}

	public void setBalanceTraderName(String balanceTraderName) {
		this.balanceTraderName = balanceTraderName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
}
