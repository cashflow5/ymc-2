
package com.belle.yitiansystem.merchant.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.belle.yitiansystem.merchant.model.entity.SupplierVo;
import com.belle.yitiansystem.merchant.model.entity.SupplierVoQuery;
import com.yougou.merchant.api.supplier.vo.MerchantOperationLog;
import com.yougou.merchant.api.common.Query;
public interface SupplierVoMapper {

	/**
	 * 插入商家操作日志
	 * 
	 * @param log
	 */
	void insertMerchantLog(MerchantOperationLog log);

	/**
	 * 查询商家操作日志
	 * 
	 * @param merchantCode
	 *            rowbound --没有用到
	 * @return
	 */
	// List<MerchantOperationLog> queryMerchantOperLog(String merchantCode,
	// RowBounds rowbound);
	List<MerchantOperationLog> queryMerchantOperLog(String merchantCode);
	
	List<MerchantOperationLog> queryMerchantOperLogByPage(@Param("merchantCode")String merchantCode,@Param("query")Query query);
	
	int countForMerchantOperLog(@Param("merchantCode")String merchantCode);
	
	List<MerchantOperationLog> queryMerchantOperLogByOperType(@Param("merchantCode")String merchantCode,@Param("operationType")String operationType,@Param("query")Query query);
	
	int countForOperLogByOperType(@Param("merchantCode")String merchantCode,@Param("operationType")String operationType);
	
	/**
	 * 根据合同编号查合同操作日志
	 * @param contractNo
	 * @return
	 */
	List<MerchantOperationLog> queryMerchantContractOperLog(String contractNo);

	/**
	 * 根据商家编码获取供应商对象
	 * 
	 * @param merchantCode
	 * @return
	 */
	public SupplierVo getSupplierByMerchantCode(String merchantCode);

	int countByQuery(SupplierVoQuery example);

	int deleteByQuery(SupplierVoQuery example);

	int deleteByPrimaryKey(String id);

	int insert(SupplierVo record);

	int insertSelective(SupplierVo record);

	List<SupplierVo> selectByQuery(SupplierVoQuery example);

	SupplierVo selectByPrimaryKey(String id);

	int updateByQuerySelective(@Param("record") SupplierVo record,
			@Param("example") SupplierVoQuery example);

	int updateByQuery(@Param("record") SupplierVo record,
			@Param("example") SupplierVoQuery example);

	int updateByPrimaryKeySelective(SupplierVo record);

	int updateByPrimaryKey(SupplierVo record);
}