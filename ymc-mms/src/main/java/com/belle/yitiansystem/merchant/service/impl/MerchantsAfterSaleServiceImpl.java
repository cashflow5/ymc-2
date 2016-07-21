package com.belle.yitiansystem.merchant.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Service;

import com.belle.yitiansystem.merchant.service.IMerchantsAfterSaleService;
import com.yougou.dto.input.ReturnQualityQueryInputDto;
import com.yougou.dto.output.ReturnQualityQueryOutputDto;
import com.yougou.dto.output.ReturnQualityQueryOutputDto.Item;
import com.yougou.dto.output.ReturnQualityQueryOutputDto.ReturnQADetail;

@Service
public class MerchantsAfterSaleServiceImpl implements IMerchantsAfterSaleService {
	
	private static final Log logger = LogFactory.getLog(MerchantsAfterSaleServiceImpl.class);
	
	@Resource
	private SessionFactory sessionFactory;
	
	@Override
	public ReturnQualityQueryOutputDto queryReturnQualityList(ReturnQualityQueryInputDto dto) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(" from ");
		sql.append(" tbl_wms_return_qa_product p ");
		sql.append(" INNER JOIN tbl_wms_return_qa_product_detail d ON(p.id = d.return_qa_product_id) ");
		sql.append(" INNER JOIN tbl_order_sub AS t3 ON(d.order_sub_no = t3.order_sub_no) ");
		sql.append(" INNER JOIN tbl_order_sub_expand t4 on(t3.id = t4.order_sub_id) ");
		sql.append(" INNER JOIN tbl_wms_warehouse t5 ON(t3.warehouse_code = t5.warehouse_code) ");
		sql.append(" LEFT JOIN tbl_order_sale_apply_bill b ON(d.apply_no = b.apply_no) ");
		sql.append(" where t4.merchant_code = ? ");
		
		// 定义 SQL 参数列表
		List<Serializable> sqlParameters = new ArrayList<Serializable>();
		sqlParameters.add(dto.getMerchant_code());
		if (StringUtils.isNotBlank(dto.getOrderNo())) {
			sql.append(" and d.order_sub_no = ? ");
			sqlParameters.add(dto.getOrderNo());
		}
		if (StringUtils.isNotBlank(dto.getApplyNo())) {
			sql.append(" and d.apply_no = ? ");
			sqlParameters.add(dto.getApplyNo());
		}
		if (StringUtils.isNotBlank(dto.getApplyStatus())) {
			sql.append(" and b.`status` = ? ");
			sqlParameters.add(dto.getApplyStatus());
		}
		if (StringUtils.isNotBlank(dto.getOut_order_id())) {
			sql.append(" and t3.original_order_no = ? ");
			sqlParameters.add(dto.getOut_order_id());
		}
		if (null != dto.getApplyStartTime()) {
			sql.append(" and b.create_time >= ? ");
			sqlParameters.add(dto.getApplyStartTime());
		}
		if (null != dto.getApplyEndTime()) {
			sql.append(" and b.create_time <= ? ");
			sqlParameters.add(dto.getApplyEndTime());
		} 
		if (StringUtils.isNotBlank(dto.getReturnId())) {
			sql.append(" and p.id = ? ");
			sqlParameters.add(dto.getReturnId());
		}
		
		Session session = null;
		ReturnQualityQueryOutputDto outputDto = null;
		try {
			session = SessionFactoryUtils.getSession(sessionFactory, true);
			
			SQLQuery sqlQuery = session.createSQLQuery(" select count(DISTINCT p.id) " + sql.toString());
			for (int i = 0; i < sqlParameters.size(); i++) {
				sqlQuery.setParameter(i, sqlParameters.get(i));
			}
			
			int totalCount = ((Number) sqlQuery.uniqueResult()).intValue();
			outputDto = new ReturnQualityQueryOutputDto(dto.getPage_index(), dto.getPage_size(), totalCount);
			
			if (totalCount > 0) {
				sql.insert(0, "p.express_charges as express_fee, p.express_name as return_logistics_name, p.express_code as return_express_code, p.qa_date, p.qa_person, p.remark as qa_remark ");
				sql.insert(0, "t3.express_order_id as express_code, t3.logistics_name, t3.warehouse_code, t5.warehouse_name, ");
				sql.insert(0, "SELECT DISTINCT p.id as return_id, d.order_sub_no, t3.original_order_no as out_order_id, ");
				sqlQuery = session.createSQLQuery(sql.toString());
				sqlQuery.setFirstResult(dto.getPage_index());
				sqlQuery.setMaxResults(dto.getPage_size());
				for (int i = 0; i < sqlParameters.size(); i++) {
					sqlQuery.setParameter(i, sqlParameters.get(i));
				}
				
				sqlQuery.addScalar("return_id", Hibernate.STRING);
				sqlQuery.addScalar("order_sub_no", Hibernate.STRING);
				sqlQuery.addScalar("out_order_id", Hibernate.STRING);
				sqlQuery.addScalar("logistics_name", Hibernate.STRING);
				sqlQuery.addScalar("express_code", Hibernate.STRING);
				sqlQuery.addScalar("warehouse_name", Hibernate.STRING);
				sqlQuery.addScalar("return_logistics_name", Hibernate.STRING);
				sqlQuery.addScalar("return_express_code", Hibernate.STRING);
				sqlQuery.addScalar("express_fee", Hibernate.DOUBLE);
				sqlQuery.addScalar("qa_date", Hibernate.DATE);
				sqlQuery.addScalar("qa_person", Hibernate.STRING);
				sqlQuery.addScalar("qa_remark", Hibernate.STRING);
				sqlQuery.setResultTransformer(new AliasToBeanResultTransformer(ReturnQualityQueryOutputDto.Item.class) {
					@Override
					public Object transformTuple(Object[] tuple, String[] aliases) {
						try {
							Object object = super.transformTuple(tuple, aliases);
							return object;
						} catch (Exception e) {
							throw new HibernateException(e);
						}
					}

				});
				outputDto.setItems(sqlQuery.list());
				
				for (Item item : outputDto.getItems()) {
					item.setReturn_details(this.queryReturnQualityDetailList(item.getReturn_id(), session));
				}
			}
		} catch (Exception e) {
			logger.error("queryReturnQualityDetail -> exception: " + e.getMessage() , e);
			throw new RuntimeException("查询退换货质检明细异常.");
		} finally {
			SessionFactoryUtils.releaseSession(session, sessionFactory);
			sql.delete(0, sql.length());
		}
		
		return outputDto;
	}
	
	private List<ReturnQADetail> queryReturnQualityDetailList(String returnId, Session session) throws Exception {
		if (StringUtils.isBlank(returnId)) {
			return null;
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT b.apply_no, b.`status` as apply_status, b.sale_type as aftersale_type, b.sale_reason as aftersale_reason, ");
		sql.append("b.remark AS sale_remark, b.images_path as attachment, b.createor as applyer, b.create_time as apply_time, d.product_no as prod_no, d.qa_product_no as qa_prod_no, ");
		sql.append("s.commodity_name as prod_name, s.supplier_code, s.sale_price, s.spec_name as commodity_specification_str, ");
		sql.append("s1.supplier_code as qa_supplier_code, s1.commodity_name qa_prod_name, s1.spec_name as qa_commodity_specification_str, ");
		sql.append("d.qa_quantity, d.question_type, d.question_description, d.storage_type, d.qa_description ");
		sql.append("FROM tbl_wms_return_qa_product_detail d LEFT JOIN tbl_order_sale_apply_bill b ON d.apply_no = b.apply_no ");
		sql.append("LEFT JOIN tbl_commodity_product t on(d.product_no = t.product_no) LEFT JOIN tbl_commodity_style s ON(s.id = t.commodity_id) ");
		sql.append("LEFT JOIN tbl_commodity_product t1 on(d.qa_product_no = t1.product_no) LEFT JOIN tbl_commodity_style s1 ON(s1.id = t1.commodity_id) ");
		sql.append("WHERE d.return_qa_product_id = '");
		sql.append(returnId).append("'");
		
		SQLQuery sqlQuery = session.createSQLQuery(sql.toString());
		
		sqlQuery.addScalar("apply_no", Hibernate.STRING);
		sqlQuery.addScalar("apply_status", Hibernate.STRING);
		sqlQuery.addScalar("attachment", Hibernate.STRING);
		sqlQuery.addScalar("applyer", Hibernate.STRING);
		sqlQuery.addScalar("apply_time", Hibernate.DATE);
		sqlQuery.addScalar("aftersale_type", Hibernate.STRING);
		sqlQuery.addScalar("aftersale_reason", Hibernate.STRING);
		sqlQuery.addScalar("sale_remark", Hibernate.STRING);
		sqlQuery.addScalar("prod_no", Hibernate.STRING);
		sqlQuery.addScalar("qa_prod_no", Hibernate.STRING);
		sqlQuery.addScalar("qa_quantity", Hibernate.INTEGER);
		sqlQuery.addScalar("question_type", Hibernate.STRING);
		sqlQuery.addScalar("question_description", Hibernate.STRING);
		sqlQuery.addScalar("storage_type", Hibernate.STRING);
		sqlQuery.addScalar("qa_description", Hibernate.STRING);
		
		sqlQuery.addScalar("supplier_code", Hibernate.STRING);
		sqlQuery.addScalar("prod_name", Hibernate.STRING);
		sqlQuery.addScalar("sale_price", Hibernate.DOUBLE);
		sqlQuery.addScalar("commodity_specification_str", Hibernate.STRING);
		
		sqlQuery.addScalar("qa_supplier_code", Hibernate.STRING);
		sqlQuery.addScalar("qa_prod_name", Hibernate.STRING);
		sqlQuery.addScalar("qa_commodity_specification_str", Hibernate.STRING);
		
		sqlQuery.setResultTransformer(new AliasToBeanResultTransformer(ReturnQualityQueryOutputDto.ReturnQADetail.class) {
			@Override
			public Object transformTuple(Object[] tuple, String[] aliases) {
				try {
					Object object = super.transformTuple(tuple, aliases);
					return object;
				} catch (Exception e) {
					throw new HibernateException(e);
				}
			}
		});
		return sqlQuery.list();
	}

	@Override
	public String getMerchantWarehouseBySupplierCode(String merchant_code) throws Exception {
		String sql = "select w.warehouse_code from tbl_wms_warehouse w where w.suppliers_code='" + merchant_code + "' ";
		Session session = null;
		String warehouseCode = "";
		try {
			session = SessionFactoryUtils.getSession(sessionFactory, true);
			SQLQuery sqlQuery = session.createSQLQuery(sql);
			warehouseCode = sqlQuery.uniqueResult().toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SessionFactoryUtils.releaseSession(session, sessionFactory);
		}
		return warehouseCode;
	}
	
}
