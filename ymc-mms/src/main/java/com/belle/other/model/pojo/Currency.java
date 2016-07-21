package com.belle.other.model.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 *@作  者: Shineg
 *@日  期: 2011-4-7 上午10:40:09
 *@版  本: 1.0
 * 货币信息 
 */
@Entity
@Table(name = "TBL_FIN_CURRENCY")
public class Currency implements Serializable{
	
	private static final long serialVersionUID = -237483526036569406L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "ID",length = 32)
	private String id;
	
	@Column(name = "NAME",length = 20)
	private String name;				//货币名称
	
	@Column(name = "RATE")
	private Double rate;				//货币汇率
	
	@Column(name = "SYMBOL",length = 10)
	private String symbol;				//货币符号
	
	@Column(name = "SHORT_NAME",length = 10)
	private String shortName;			//货币简称
	
	@Column(name = "DEFAULT_FLAG")
	private Integer defaultFlag;		//默认标识(0:是     1:否)
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public Integer getDefaultFlag() {
		return defaultFlag;
	}
	public void setDefaultFlag(Integer defaultFlag) {
		this.defaultFlag = defaultFlag;
	}
}
