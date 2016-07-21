package com.belle.yitiansystem.merchant.service;


import java.util.List;
import java.util.Map;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.other.model.pojo.SupplierSp;
import com.belle.yitiansystem.merchant.model.pojo.SupplierYgContact;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-7-10 下午6:16:39
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public interface ISupplierYgContactService {
	public PageFinder<SupplierYgContact> getSupplierYgContactList(SupplierYgContact contact, Query query, Map<String, String> map);

	public SupplierYgContact getSupplierYgContactById(String userId);

	public void saveYgContact(SupplierYgContact contact) throws Exception;

	/**
	 * 未管理的商家
	 * 
	 * @param userId
	 * @param supplierName
	 * @return
	 */
	public List<Map<String,String>> getSupplierSpOut(String userId);

	/**
	 * 已经管理的商家
	 * 
	 * @param userId
	 * @param supplierName
	 * @return
	 */
	public List<Map<String,String>> getSupplierSpIn(String userId);

	/**
	 * 绑定商家
	 * 
	 * @param supplierCodeStr
	 * @param userId
	 * @throws Exception
	 */
	public void bindContact(String supplierCodeStr, String userId)
			throws Exception;

	public List<String> getSupplierList(String userName);
}
