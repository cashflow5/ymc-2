package com.belle.yitiansystem.merchant.model.pojo;  

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * ClassName: StockPunishRuleDetail
 * Desc: 缺货处罚规则明细
 * date: 2014-12-10 下午5:57:06
 * @author li.n1 
 * @since JDK 1.6
 */
@Entity
@Table(name = "tbl_sp_supplier_punish_rule_detail")
public class StockPunishRuleDetail implements java.io.Serializable{

	/** 
	 * serialVersionUID:序列化 
	 * @since JDK 1.6 
	 */ 
	private static final long serialVersionUID = 4692882953288064599L;
	
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	private String id;
	//处罚规则id
	@Column(name = "punish_rule_id")
	private String punishRuleId;
	//规则范围起
	@Column(name = "punish_rate_begin")
	private double punishRateBegin;
	//规则范围止
	@Column(name = "punish_rate_end")
	private double punishRateEnd;
	//处罚比例
	@Column(name = "punish_rule")
	private double punishRule;
	//创建时间
	@Column(name = "create_time")
	private Date creatTime;
	//修改时间
	@Column(name = "update_time")
	private Timestamp updateTime;
	
	/**
	 * @param id
	 * @param punishRateBegin
	 * @param punishRateEnd
	 * @param punishRule
	 */
	public StockPunishRuleDetail(){}
	public StockPunishRuleDetail(String id, double punishRateBegin,
			double punishRateEnd, double punishRule) {
		this.id = id;
		this.punishRateBegin = punishRateBegin;
		this.punishRateEnd = punishRateEnd;
		this.punishRule = punishRule;
	}



	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPunishRuleId() {
		return punishRuleId;
	}
	public void setPunishRuleId(String punishRuleId) {
		this.punishRuleId = punishRuleId;
	}
	public double getPunishRateBegin() {
		return punishRateBegin;
	}
	public void setPunishRateBegin(double punishRateBegin) {
		this.punishRateBegin = punishRateBegin;
	}
	public double getPunishRateEnd() {
		return punishRateEnd;
	}
	public void setPunishRateEnd(double punishRateEnd) {
		this.punishRateEnd = punishRateEnd;
	}
	public double getPunishRule() {
		return punishRule;
	}
	public void setPunishRule(double punishRule) {
		this.punishRule = punishRule;
	}
	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
}
