package com.yougou.dto.input;

import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;


public class RejectionQualityInputDto extends InputDto {

	private static final long serialVersionUID = -8034054997275151938L;
	
	/* 质检商品货品条码、质检商品数量、收货快递公司、收货快递单号、
	 * 快递费用、拒收类型、质检问题类型、质检入库类型、质检描述、
	 * (质检商家、质检时间)由系统获取
	 */
	private String rejectionId;
	
	/** 订单号(目前暂未使用) */
	private String orderNo;
	
	/** 质检商品货品条码 (多个货品条码，使用逗号,分隔)*/
	private String thirdPartyCode;
	
	/** 拒收类型 */
	private Integer rejectionType;
	
	/** 质检商品数量(使用逗号,分隔) */
	private String quantity;
	
	/** 收货快递单号 */
	private String expressCode;
	
	/** 快递费用 */
	private Double expressfee;
	
	/** 是否到付（YES:到付  NO:不到付） */
	private String cashOnDelivery;
	
	/** 质检问题类型(非质量问题:GOOD 质量问题:BAD)((使用逗号,分隔)) */
	private String questionType;
	
	/** 质检入库类型 (使用逗号,分隔)*/
	private String storageType;
	
	/** 质检描述 (使用逗号,分隔)*/
	private String qaDescription;
	
	/** 质检时间(yyyy-MM-dd HH:mm:ss) */
	private Date qaDate;
	
	public boolean handleParams() throws Exception {
		String[] thirdPartyCodes = null;
		String[] storageTypes = null;
		String[] qaDescriptions = null;
		String[] questionTypes = null; 
		String[] quantitys = null;
		if (StringUtils.isNotBlank(thirdPartyCode)) {
			thirdPartyCodes = thirdPartyCode.trim().split(",");
		}
		
		//质检入库类型
		if (StringUtils.isBlank(storageType)) {
			storageTypes = new String[thirdPartyCodes.length];
			for (int i = 0; i < thirdPartyCodes.length; i++) {
				storageTypes[i] = "GOOD";
			}
			storageType = StringUtils.join(storageTypes, ",");
		}
		storageTypes = storageType.trim().split(",");
		
		
		if (StringUtils.isNotBlank(qaDescription)) {
			qaDescriptions = qaDescription.trim().split(",");
		}
		
		//质检问题类型
		if (StringUtils.isBlank(questionType)) {
			questionTypes = new String[thirdPartyCodes.length];
			for (int i = 0; i < thirdPartyCodes.length; i++) {
				questionTypes[i] = "GOOD";
			}
			questionType = StringUtils.join(questionTypes, ",");
		} 
		questionTypes = questionType.trim().split(",");
		
		//质检数量
		if (StringUtils.isBlank(quantity)) {
			quantitys = new String[thirdPartyCodes.length];
			for (int i = 0; i < thirdPartyCodes.length; i++) {
				quantitys[i] = "1";
			}
			quantity = StringUtils.join(quantitys, ",");
		}
		quantitys = quantity.trim().split(",");
		
		//质检时间
		if (null == qaDate) {
			qaDate = new Date();
		}
		
		Pattern pattern_type = Pattern.compile("(BAD)|(GOOD)");
		if (thirdPartyCodes.length != storageTypes.length) {
			throw new RuntimeException("storageType数量与thirdPartyCode数量不一致，请核对.");
		} else if (thirdPartyCodes.length == storageTypes.length) {
			for (String type : storageTypes) {
				if (!pattern_type.matcher(type.trim()).matches()) {
					throw new RuntimeException("storageType必须输入(BAD)或者(GOOD).");
				}
			}
		} 
		if (null != qaDescriptions && thirdPartyCodes.length != qaDescriptions.length) {
			throw new RuntimeException("qaDescription数量与thirdPartyCode数量不一致，请核对.");
		} 
		if (thirdPartyCodes.length != questionTypes.length) {
			throw new RuntimeException("questionType数量与thirdPartyCode数量不一致，请核对.");
		} else if (thirdPartyCodes.length == questionTypes.length) {
			for (String type : questionTypes) {
				if (!pattern_type.matcher(type.trim()).matches()) {
					throw new RuntimeException("questionType必须输入(BAD)或者(GOOD).");
				}
			}
		} 
		if (thirdPartyCodes.length != quantitys.length) {
			throw new RuntimeException("quantity数量与thirdPartyCode数量不一致，请核对.");
		} else if (thirdPartyCodes.length == quantitys.length) {
			Pattern pattern = Pattern.compile("[1-9][0-9]{0,1}");
			for (String q : quantitys) {
				if (!pattern.matcher(q.trim()).matches()) {
					throw new RuntimeException("quantity输入格式不正确.");
				}
			}
		}
		
		expressfee = 0.0;
		cashOnDelivery = "NO";
		return true;
	}
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getThirdPartyCode() {
		return thirdPartyCode;
	}

	public void setThirdPartyCode(String thirdPartyCode) {
		this.thirdPartyCode = thirdPartyCode;
	}

	public Integer getRejectionType() {
		return rejectionType;
	}

	public void setRejectionType(Integer rejectionType) {
		this.rejectionType = rejectionType;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getExpressCode() {
		return expressCode;
	}

	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}

	public Double getExpressfee() {
		return expressfee;
	}

	public void setExpressfee(Double expressfee) {
		this.expressfee = expressfee;
	}
	
	public String getQuestionType() {
		return this.questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public String getRejectionId() {
		return rejectionId;
	}

	public void setRejectionId(String rejectionId) {
		this.rejectionId = rejectionId;
	}

	public Date getQaDate() {
		return qaDate;
	}

	public void setQaDate(Date qaDate) {
		this.qaDate = qaDate;
	}

	public void setQaDescription(String qaDescription) {
		this.qaDescription = qaDescription;
	}

	public String getQaDescription() {
		return qaDescription;
	}
	
	public String getCashOnDelivery() {
		return cashOnDelivery;
	}

	public void setCashOnDelivery(String cashOnDelivery) {
		this.cashOnDelivery = cashOnDelivery;
	}
	
}
