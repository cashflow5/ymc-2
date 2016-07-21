package com.yougou.kaidian.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.yougou.kaidian.user.model.pojo.SupplierContract;
import com.yougou.kaidian.user.model.pojo.SupplierContractAttachment;
import com.yougou.kaidian.user.model.pojo.SupplierContractTrademark;
import com.yougou.kaidian.user.model.pojo.SupplierContractTrademarkSub;


/**
 * 商家合同信息dao�?
 * @author wang.m
 *
 */
public interface SupplierContractMapper {

	/**
	 * 根据商家登录名称查询商家合同信息列表
	 * @author wang.m
	 * @Date 2012-03-12
	 */
	public List<Map<String,Object>> getSupplierContractList(String supplierID,RowBounds rowBounds);
	
	 /**
	  * 
	  * 获取总记录数
	  */
	 int getContractCounts(String id);
	 
	/**
	 * 根据供应商ID查询当前有效合同
	 * 
	 * @param supplierId
	 * @return 当前有效合同
	 */
	SupplierContract getCurrentContractBySupplierId(@Param("supplierId")String supplierId);
	
	SupplierContract selectSupplierContractById(@Param("id") String id);
	
	 List<SupplierContractAttachment> selectSupplierContractAttachmentByContractId(@Param("contractId") String contractId);
	 List<SupplierContractTrademark> selectSupplierContractTrademark(@Param("contractId") String contractId);
	 List<SupplierContractTrademarkSub> selectSupplierContractTrademarkSubByTrademarkId(@Param("trademarkId") String trademarkId);

}
