package com.yougou.kaidian.framework.beans;

/**
 * 合作模式
 * 
 * @author yang.mq
 *
 */
public enum CooperationModel {
	/*
	 * 提示：严禁调整枚举常量的先后顺序(因数据库 tbl_sp_supplier.is_input_yougou_warehouse 记录的是枚举常量的顺序值).
	 */
	NON_ENTER_YOUGOU_WAREHOUSE_MERCHANT_DELIVERY(0, "不入优购仓库，商家发货", "商家配送", true),
	ENTER_YOUGOU_WAREHOUSE_YOUGOU_DELIVERY(1, "入优购仓库，优购发货", "优购配送"),
	NON_ENTER_YOUGOU_WAREHOUSE_YOUGOU_DELIVERY(2, "不入优购仓库，优购发货", "优购配送");
	
	private Integer identifier;
	private String description;
	private String distributionType;
	private boolean checked;

	private CooperationModel(Integer identifier, String description, String distributionType) {
		this(identifier, description, distributionType, false);
	}
	
	private CooperationModel(Integer identifier, String description) {
		this(identifier, description, null, false);
	}

	private CooperationModel(Integer identifier, String description, String distributionType, boolean checked) {
		this.identifier = identifier;
		this.description = description;
		this.distributionType = distributionType;
		this.checked = checked;
	}
	
	public Integer getIdentifier() {
		return identifier;
	}

	public String getDescription() {
		return description;
	}

	public boolean isChecked() {
		return checked;
	}
	
	public String getDistributionType() {
		return distributionType;
	}

	/**
	 * 按枚举标识符
	 * 
	 * @param identifier
	 * @return CooperationModel
	 */
	public static CooperationModel forIdentifier(Integer identifier) {
		for (CooperationModel element : CooperationModel.values()) {
			if (element.getIdentifier().equals(identifier)) {
				return element;
			}
		}
		return null;
	}
}
