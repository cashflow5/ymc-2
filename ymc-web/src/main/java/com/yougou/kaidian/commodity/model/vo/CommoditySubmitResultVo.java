package com.yougou.kaidian.commodity.model.vo;

import java.util.List;

/**
 * 新增或修改商品 提交 结果 vo
 * @author huang.wq
 * 2012-11-16
 */
public class CommoditySubmitResultVo {
	
	/**
	 * 默认报错消息
	 */
	public static final String DEFAULT_ERROR_MSG = "商品资料提交失败";
	
	/**
	 * 是否成功
	 */
	private Boolean isSuccess = false;
	/**
	 * 错误消息
	 */
	private String errorMsg = DEFAULT_ERROR_MSG;
	/**
	 * 商品资料是否提交成功
	 */
	private Boolean isCommoditySubmitSuccess = false;
	/**
	 * 新增商品时，是否为保存并提交审核
	 */
	private Boolean isAddCommoditySaveSubmit = false;
	
	private List<ErrorVo> errorList;
	
	/**
	 * 保存成功个数
	 */
	private int successSize = 0;
	/**
	 * 保存失败个数
	 */
	private int errorSize = 0;
	
	public boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Boolean getIsCommoditySubmitSuccess() {
		return isCommoditySubmitSuccess;
	}

	public void setIsCommoditySubmitSuccess(Boolean isCommoditySubmitSuccess) {
		this.isCommoditySubmitSuccess = isCommoditySubmitSuccess;
	}

	public Boolean getIsAddCommoditySaveSubmit() {
		return isAddCommoditySaveSubmit;
	}

	public void setIsAddCommoditySaveSubmit(Boolean isAddCommoditySaveSubmit) {
		this.isAddCommoditySaveSubmit = isAddCommoditySaveSubmit;
	}

	public List<ErrorVo> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<ErrorVo> errorList) {
		this.errorList = errorList;
	}

	public int getSuccessSize() {
		return successSize;
	}

	public void setSuccessSize(int successSize) {
		this.successSize = successSize;
	}

	public int getErrorSize() {
		return errorSize;
	}

	public void setErrorSize(int errorSize) {
		this.errorSize = errorSize;
	}
	
}
