package com.belle.yitiansystem.merchant.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.other.service.ISqlService;
import com.belle.yitiansystem.merchant.model.vo.QueryAbnormalSaleApplyVoLocal;
import com.belle.yitiansystem.merchant.service.IOrder4MerchantLocalService;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.model.brand.Brand;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-7-14 下午4:46:34
 * @version 0.1.0
 * @copyright yougou.com
 */

@Service("order4MerchantLocalService")
public class Order4MerchantLocalServiceImpl
		implements
			IOrder4MerchantLocalService {
	@Resource
	private ISqlService sqlService;

	@Resource
	private ICommodityBaseApiService commodityBaseApiService;
	@Override
	public PageFinder<Map<String, Object>> getAbnormalSaleApplyList(
			QueryAbnormalSaleApplyVoLocal vo, Query query) {
		List<Object> params = new ArrayList<Object>();

		StringBuffer sqlbuffer = new StringBuffer();
		sqlbuffer
				.append("select  b.*,s.supplier merchant_name,s.supplier_code merchant_code,de.exceptionType,de.auditTime,s.id supplyid from")
				// updated by zhangfeng 2015-12-08 异常售后审核列表 过滤掉韩国首尔直发订单
				//.append(" tbl_order_sale_apply_bill b inner join tbl_order_detail4sub d on b.order_sub_id = d.order_sub_id")
				.append(" tbl_order_sale_apply_bill b inner join tbl_order_sub o on (b.order_no = o.order_sub_no and o.order_source_no not like 'SOZF%')")
				.append(" inner join tbl_order_sub_expand d on o.id = d.order_sub_id")
				// updated end 
				.append(" inner join tbl_sp_supplier s on s.supplier_code = d.merchant_code inner join tbl_order_sale_apply_abnormal_detail de on de.apply_id = b.id")
				.append(" WHERE 1=1 ");

		StringBuffer wherebuffer = new StringBuffer();

		/**
		 * 订单号
		 */
		String orderNo = vo.getOrderNo();
		if (orderNo != null && !StringUtils.isEmpty(orderNo.trim())) {
			vo.setOrderNo(orderNo.trim());
			sqlbuffer.append(" and b.order_no = ?");
			params.add(orderNo);
		}
		/**
		 * 售后申请单号
		 */
		String applyNo = vo.getApplyNo();
		if (applyNo != null && !StringUtils.isEmpty(applyNo = applyNo.trim())) {
			vo.setApplyNo(applyNo);
			sqlbuffer.append(" and b.apply_no = ?");
			params.add(applyNo);
		}

		/**
		 * 商家编号
		 */
		String merchantCode = vo.getMerchantCode();
		if (merchantCode != null
				&& !StringUtils.isEmpty(merchantCode = merchantCode.trim())) {
			vo.setMerchantCode(merchantCode);
			sqlbuffer.append(" and d.merchant_code = ?");
			params.add(merchantCode);
		}

		/**
		 * 商家名称
		 */
		String merchantName = vo.getMerchantName();
		if (merchantName != null
				&& !StringUtils.isEmpty(merchantName = merchantName.trim())) {
			vo.setMerchantName(merchantName);
			sqlbuffer.append(" and s.supplier like ?");
			params.add("%" + merchantName + "%");
		}
		/**
		 * 品牌
		 */
		String brandName = vo.getBrandName();
		if (null != brandName && !StringUtils.isEmpty(brandName)) {
			vo.setBrandName(brandName);
			List<Brand> brands = commodityBaseApiService
					.getBrandListLikeBrandName("%" + brandName, (short) 1);
			List<String> brandNos = new ArrayList<String>();
			if (CollectionUtils.isNotEmpty(brands)) {
				for (Brand brand : brands) {
					brandNos.add(brand.getBrandNo());
				}
			}
			if (brandNos.size() > 0) {
				sqlbuffer
						.append("and s.id in (select supply_id from tbl_sp_limit_brand where brand_no in(");
				for (int i = 0, length = brandNos.size(); i < length; i++) {
					if (i < length - 1) {
						sqlbuffer.append("'" + brandNos.get(i) + "',");
					} else {
						sqlbuffer.append("'" + brandNos.get(i) + "'");
					}
				}
				sqlbuffer.append("))");
			}
		}

		/**
		 * 登记类型
		 */
		String exceptionType = vo.getExceptionType();
		if (exceptionType != null && !StringUtils.isEmpty(exceptionType.trim())) {
			vo.setExceptionType(exceptionType);
			sqlbuffer
.append(" and de.exceptionType = ? ");
			params.add(exceptionType);
		}

		/**
		 * 状态
		 */
		String status = vo.getStatus();
		if (status != null && !StringUtils.isEmpty(status.trim())) {
			vo.setStatus(status);
			sqlbuffer.append(" and b.status = ?");
			params.add(status);
		}

		if (vo.getCreateTimeStart() != null) {
			wherebuffer.append(" AND b.create_time >= '"
					+ vo.getCreateTimeStart() + " 00:00:00'");
		}
		if (vo.getCreateTimeEnd() != null) {
			wherebuffer.append(" AND b.create_time <= '"
					+ vo.getCreateTimeEnd() + " 23:59:59'");
		}

		if (!StringUtils.isEmpty(vo.getSupplierYgContacts())) {
			wherebuffer
					.append(" AND d.merchant_code in(select t.merchant_code from tbl_merchant_supplier_expand t inner join tbl_supplier_yg_contact s on t.yg_contact_user_id = s.user_id where s.user_name like '%"
							+ vo.getSupplierYgContacts() + "%')");
		}
		String orderStr = "b.create_time desc";

		String sql = sqlbuffer.toString();
		int index = sql.indexOf("from");
		if (index != -1) {
			sql = "select count( b.id) " + sql.substring(index);
		}
		int count = sqlService.getCountBySql(sql, wherebuffer, params)
				.intValue();
		// 拼接查询条件
		PageFinder<Map<String, Object>> pageFinder = sqlService
				.getObjectsBySql(sqlbuffer.toString(), query,
						wherebuffer, count,
						params, orderStr);
		return pageFinder;
	}

}
