package com.belle.yitiansystem.merchant.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.other.service.ISqlService;
import com.belle.yitiansystem.merchant.model.vo.ContactsVoLocal;
import com.belle.yitiansystem.merchant.service.IMerchantContactLocalService;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-7-15 下午2:58:08
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@Service("merchantContactLocalService")
public class MerchantContactLocalServiceImpl
		implements
			IMerchantContactLocalService {
	@Resource
	private ISqlService sqlService;
	@Override
	public PageFinder<Map<String, Object>> getContactList(
			ContactsVoLocal vo, Query query) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sqlbuffer = new StringBuffer();
		sqlbuffer
				.append("select c.*,s.supplier ,s.supplier_code ,yc.user_name yccontact from tbl_sp_supplier_contact c")
				.append(" inner join tbl_sp_supplier s on s.id = c.supply_id left join tbl_merchant_supplier_expand ex on ex.merchant_code = s.supplier_code and yg_contact_user_id is not null")
				.append(" left join tbl_supplier_yg_contact yc on yc.user_id = ex.yg_contact_user_id")
				.append(" WHERE 1=1 ");

		StringBuffer wherebuffer = new StringBuffer();

		String supplyId = vo.getSupplyId();
		if (supplyId != null
				&& !StringUtils.isEmpty(supplyId = supplyId.trim())) {
			vo.setSupplier(supplyId);
			sqlbuffer.append(" and c.supply_id = ?");
			params.add(supplyId);
		}
		/**
		 * 姓名
		 */
		String contact = vo.getContact();
		if (contact != null && !StringUtils.isEmpty(contact = contact.trim())) {
			vo.setContact(contact);
			sqlbuffer.append(" and c.contact like ?");
			params.add("%" + contact + "%");
		}
		/**
		 * 联系电话
		 */
		String telePhone = vo.getTelePhone();
		if (telePhone != null
				&& !StringUtils.isEmpty(telePhone = telePhone.trim())) {
			vo.setTelePhone(telePhone);
			sqlbuffer.append(" and (c.tele_phone = ? or c.mobile_phone = ?)");
			params.add(telePhone);
			params.add(telePhone);
		}

		/**
		 * 电子邮箱
		 */
		String email = vo.getEmail();
		if (email != null && !StringUtils.isEmpty(email = email.trim())) {
			vo.setEmail(email);
			sqlbuffer.append(" and c.email = ?");
			params.add(email);
		}

		/**
		 * 类型
		 */
		Integer type = vo.getType();
		if (type != null && type.intValue() != 0) {
			vo.setType(type);
			sqlbuffer.append(" and c.type = ?");
			params.add(type);
		}
		String orderStr = "c.id desc";

		// 拼接查询条件
		PageFinder<Map<String, Object>> pageFinder = sqlService
				.getObjectsBySql(sqlbuffer.toString(), query, wherebuffer,
						params, orderStr);
		return pageFinder;
	}

}
