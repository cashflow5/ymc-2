package com.belle.yitiansystem.merchant.model.vo;

import java.io.Serializable;
import java.util.List;

import com.belle.yitiansystem.merchant.model.entity.SupplierVo;
import com.belle.yitiansystem.merchant.model.pojo.SupplierContract;
/**
 * 由于supplierVO 在API工程里，所以这个类用用于mms查询商家以及商家合同，合同资质等信息的封装类
 * @author le.sm
 *
 */
public class SupplierInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3833569758760809020L;
	/**
	 * 商家信息
	 */
	private SupplierVo supplierVO;
	/**
	 * 合同
	 */
	private List<SupplierContract>  contractList;
	public SupplierVo getSupplierVO() {
		return supplierVO;
	}
	public void setSupplierVO(SupplierVo supplierVO) {
		this.supplierVO = supplierVO;
	}
	public List<SupplierContract> getContractList() {
		return contractList;
	}
	public void setContractList(List<SupplierContract> contractList) {
		this.contractList = contractList;
	}
	
	

}
