package com.belle.yitiansystem.merchant.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.belle.infrastructure.orm.basedao.CritMap;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.other.model.pojo.SupplierSp;
import com.belle.other.service.ISqlService;
import com.belle.yitiansystem.merchant.dao.IMerchantSupplierExpandDao;
import com.belle.yitiansystem.merchant.dao.ISupplierSpDao;
import com.belle.yitiansystem.merchant.dao.ISupplierYgContactDao;
import com.belle.yitiansystem.merchant.dao.mapper.MerchantBrandMapper;
import com.belle.yitiansystem.merchant.dao.mapper.MerchantSupplierExpandMapper;
import com.belle.yitiansystem.merchant.model.pojo.MerchantSupplierExpand;
import com.belle.yitiansystem.merchant.model.pojo.SupplierYgContact;
import com.belle.yitiansystem.merchant.service.ISupplierYgContactService;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.model.brand.Brand;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-7-10 下午6:23:29
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@Service("supplierYgContactService")
public class SupplierYgContactServiceImpl implements ISupplierYgContactService {

	@Resource
	private ISupplierYgContactDao supplierYgContactDao;

	@Resource
	private ISupplierSpDao supplierSpDao;

	@Resource
	private IMerchantSupplierExpandDao merchantSupplierExpandDao;

	@Resource
	private ISqlService sqlService;
	
	@Resource
	private MerchantBrandMapper merchantBrandMapper;
    @Resource
    private ICommodityBaseApiService commodityBaseApiService;
    
	@Resource
	private MerchantSupplierExpandMapper merchantSupplierExpandMapper;

	@Override
	public PageFinder<SupplierYgContact> getSupplierYgContactList(SupplierYgContact contact, Query query, Map<String, String> map) {
		CritMap critMap = new CritMap();
		if (!StringUtils.isEmpty(contact.getUserName())) {
			critMap.addLike("userName", contact.getUserName());
		}
		if (!StringUtils.isEmpty(contact.getEmail())) {
			critMap.addLike("email", contact.getEmail());
		}
		if (!StringUtils.isEmpty(contact.getMobilePhone())) {
			critMap.addLike("mobilePhone", contact.getMobilePhone());
		}
		if (!StringUtils.isEmpty(contact.getTelePhone())) {
			critMap.addLike("telePhone", contact.getTelePhone());
		}
		if (MapUtils.isNotEmpty(map)) {
			List<Brand> brandList = null;
			if (map.containsKey("brands") && StringUtils.isNotEmpty(map.get("brands"))) {
				brandList = commodityBaseApiService.getBrandListLikeBrandName("%" + map.get("brands").toString(), (short) 1);
			}
			if (null != brandList) {
				StringBuffer brand_in = new StringBuffer();
				for (Brand brand : brandList) {
					brand_in.append("\"" + brand.getBrandNo() + "\",");
				}
				if (brand_in.length() > 0) {
					brand_in.setLength(brand_in.length() - 1);
					map.put("brandNos", brand_in.toString());
				}
			}
			if (null != brandList || StringUtils.isNotEmpty(map.get("merchantName"))) {
				List<String> list = merchantBrandMapper.queryMerchantByBrandsOrMerchantName(map);
				if (CollectionUtils.isNotEmpty(list)) {
					critMap.addIN("userId", list.toArray());
				}else{
					return null;
				}
			}
			
		}
		PageFinder<SupplierYgContact> pageFilder = supplierYgContactDao.pagedByCritMap(critMap, query.getPage(), query.getPageSize());

		return pageFilder;
	}

	public SupplierYgContact getSupplierYgContactById(String userId) {
		return supplierYgContactDao.getById(userId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveYgContact(SupplierYgContact contact) throws Exception {

		if (StringUtils.isEmpty(contact.getUserId())) {
			CritMap critMap = new CritMap(); // 检测用户名，邮箱是否存在 //
			critMap.addEqual("userName", contact.getUserName());
			SupplierYgContact resultContact = supplierYgContactDao
					.getObjectByCritMap(critMap, false);
			if (resultContact != null) {
				throw new Exception("用户名已经存在!");
			}
			critMap = new CritMap(); //
			critMap.addEqual("email", contact.getEmail());
			resultContact = supplierYgContactDao.getObjectByCritMap(critMap,
					false);
			if (resultContact != null) {
				throw new Exception("邮箱已经存在!");
			}
			supplierYgContactDao.insert(contact);
		} else {
			supplierYgContactDao.update(contact);
		}

	}

	public List<Map<String,String>> getSupplierSpOut(String userId) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("userId", userId);
		List<Map<String, String>> list = merchantSupplierExpandMapper.querySupplierSpOut(map);
		return list;
	}

	public List<Map<String,String>> getSupplierSpIn(String userId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		List<Map<String, String>> list = merchantSupplierExpandMapper.querySupplierSpIn(map);
		return list;
	}

	@Transactional
	public void bindContact(String supplierCodeStr, String userId)
			throws Exception {
		try {
			if (StringUtils.isEmpty(userId)) {
				throw new Exception("参数错误!");
			}

			// 先将所有商家负责人ID更新为空
			String sql = "update tbl_merchant_supplier_expand set yg_contact_user_id = null where yg_contact_user_id = ?";
			sqlService.updateObject(sql, new String[]{userId});
			if (StringUtils.isEmpty(supplierCodeStr)) {
				return;
			}
			String[] supplierCodes = supplierCodeStr.split(",");
			for (String code : supplierCodes) {
				MerchantSupplierExpand expand = haveBand(code);
				if (expand == null) {
					expand = new MerchantSupplierExpand();
					expand.setMerchantCode(code);
					expand.setYgContactUserId(userId);
					merchantSupplierExpandDao.save(expand);
				} else {
					expand.setYgContactUserId(userId);
					merchantSupplierExpandDao.save(expand);
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private MerchantSupplierExpand haveBand(String merchantCode) {
		StringBuilder sb = new StringBuilder(
				"from MerchantSupplierExpand t where t.merchantCode =  ? ");
		List<MerchantSupplierExpand> list = merchantSupplierExpandDao.find(
				sb.toString(),
				new String[]{merchantCode});
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public List<String> getSupplierList(String userName){
		StringBuilder sb = new StringBuilder(
				"select t.merchantCode from MerchantSupplierExpand t ,SupplierYgContact s where t.YgContactUserId = s.userId and s.userName like ?");
		List<?> list = merchantSupplierExpandDao.find(
				sb.toString(), new String[]{"%"
				+ userName + "%"});
		return (List<String>) list;
	}

}
