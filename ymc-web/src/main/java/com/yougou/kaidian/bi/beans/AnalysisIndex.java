package com.yougou.kaidian.bi.beans;  

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: AnalysisIndex
 * Desc: 指标VO
 * date: 2015-8-25 下午6:25:27
 * @author li.n1 
 * @since JDK 1.6
 */
public class AnalysisIndex implements Serializable {
	/** 
	 * serialVersionUID:序列化 
	 * @since JDK 1.6 
	 * @date 2015-8-25 下午6:25:42 
	 */ 
	private static final long serialVersionUID = 1446443502627329645L;
	/**
	 * 指标主键
	 */
	private String id;
	/**
	 * 指标中文名称
	 */
	private String label;
	/**
	 * 英文
	 */
	private String enName;
	/**
	 * 指标顺序
	 */
	private String order;
	/**
	 * 指标创建时间
	 */
	private Date createTime; 
	/**
	 * 指标类型，0 经营概况指标 1 商品分析指标
	 */
	private int type;
	/**
	 * 小数保留位数，为0表示为整数
	 */
	private String digitDecimal;
	/**
	 * 是否百分数，1是，0否
	 */
	private String percent;
	/**
	 * 是否可以汇总累加的指标，1是，0否
	 */
	private String sumIndex;
	/**
	 * 如果为商品分析的指标，所属的维度，0通用分类维度  1只针对商品维度   2只针对分类维度
	 * 为null,不属于商品分析的指标
	 */
	private String dimension; 
	/**
	 * 是否默认显示指标,1是  0否
	 */
	private String defaultOrNo;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDigitDecimal() {
		return digitDecimal;
	}
	public void setDigitDecimal(String digitDecimal) {
		this.digitDecimal = digitDecimal;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	public String getSumIndex() {
		return sumIndex;
	}
	public void setSumIndex(String sumIndex) {
		this.sumIndex = sumIndex;
	}
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
	}
	public String getDefaultOrNo() {
		return defaultOrNo;
	}
	public void setDefaultOrNo(String defaultOrNo) {
		this.defaultOrNo = defaultOrNo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
