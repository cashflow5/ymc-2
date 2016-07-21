package com.belle.yitiansystem.systemmgmt.model.vo;

import java.io.Serializable;

/**
 * 数据权限VO
 * @author zhubin
 * date：2011-12-21 下午4:36:45
 */
public class PermissionDateVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//分类数组名称
    private String[] categorys;
    
    //品牌数组编号
    private String[] brands;

    //来源数组值
    private String[] sources;
    
    //店铺数组名称
    private String[] sellers;
    
    //描述
    private String remark;	
    
    //组名
    private String groupName;	
    
    
	public String[] getSources() {
		return sources;
	}

	public void setSources(String[] sources) {
		this.sources = sources;
	}

	public String[] getSellers() {
		return sellers;
	}

	public void setSellers(String[] sellers) {
		this.sellers = sellers;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String[] getCategorys() {
		return categorys;
	}

	public void setCategorys(String[] categorys) {
		this.categorys = categorys;
	}

	public String[] getBrands() {
		return brands;
	}

	public void setBrands(String[] brands) {
		this.brands = brands;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
    
    
    
}
