
package com.belle.yitiansystem.merchant.dao.mapper;

import com.yougou.merchant.api.supplier.vo.ContactsVo;
import com.yougou.merchant.api.supplier.vo.MerchantRejectedAddressVo;
import com.yougou.merchant.api.supplier.vo.MerchantSupplierExpand;
import com.yougou.merchant.api.supplier.vo.MerchantSupplierExpandQuery;
import com.yougou.merchant.api.supplier.vo.MerchantUser;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface MerchantSupplierExpandMapper {
    int countByQuery(MerchantSupplierExpandQuery example);

    int deleteByQuery(MerchantSupplierExpandQuery example);

    int deleteByPrimaryKey(String id);

    int insert(MerchantSupplierExpand record);

    int insertSelective(MerchantSupplierExpand record);

    List<MerchantSupplierExpand> selectByQuery(MerchantSupplierExpandQuery example);

    MerchantSupplierExpand selectBySupplierId(@Param("supplierId")String supplierId);
    
    MerchantSupplierExpand selectByMerchantCode(@Param("merchantCode")String merchantCode);
    
    int updateByQuerySelective(@Param("record") MerchantSupplierExpand record, @Param("example") MerchantSupplierExpandQuery example);

    int updateByQuery(@Param("record") MerchantSupplierExpand record, @Param("example") MerchantSupplierExpandQuery example);

    int updateByPrimaryKeySelective(MerchantSupplierExpand record);

    int updateByPrimaryKey(MerchantSupplierExpand record);
    /**
	 * 获取该货品没有关联的商家
	 * 
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> querySupplierSpOut(Map<String, String> map);

	/**
	 * 获取该货品已经关联的商家
	 * 
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> querySupplierSpIn(Map<String, String> map);

	/*** 商家登陆用户操作 start *******/
	public List<MerchantUser> queryMerchantUserList(MerchantUser user);
	
	public Integer queryMerchantUserCount(MerchantUser user);
	
	public int insertMerchantUser(MerchantUser vo);
	
	public int updateMerchantUser(MerchantUser vo);
	
	public MerchantUser selectMerchantUserById(@Param("id")String id);
	/*** 商家登陆用户操作 end *******/

	/** 商家联系人 操作 start */
	public List<ContactsVo> getContactsBySupplierId(@Param("supplierId")String supplierId);
	// 商家联系人是否已经存在  
	public int queryExistContactOfThisType(@Param("supplierId")String supplierId,@Param("type")Integer type);
	/** 商家联系人 操作 end */
	
	/** 商家退换货地址操作 start */
	public  int saveRejectedAddress( MerchantRejectedAddressVo rejectedAddress);

	public List<MerchantRejectedAddressVo> getRejectedAddressBySupplierCode(
			@Param("merchantCode")String merchantCode);
	
	public int updateRejectedAddress( MerchantRejectedAddressVo merchantRejectedAddressVo);
	
	public MerchantRejectedAddressVo selectRejectedAddressById(@Param("id")String id);
	/** 商家退换货地址操作 end */

	
	
}
