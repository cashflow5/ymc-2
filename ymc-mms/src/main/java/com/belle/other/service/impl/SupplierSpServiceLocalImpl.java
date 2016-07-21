package com.belle.other.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.yougou.merchant.api.common.Query;
import com.belle.other.model.pojo.SupplierSp;
import com.belle.other.service.ISupplierSpLocalService;
import com.belle.yitiansystem.merchant.dao.ISupplierSpDao;
import com.belle.yitiansystem.merchant.model.vo.MerchantsVo;
import com.yougou.merchant.api.supplier.vo.SupplierQueryVo;
import com.yougou.merchant.api.supplier.vo.SupplierVo;
import com.yougou.yop.api.internal.util.StringUtils;
import com.belle.yitiansystem.merchant.dao.mapper.MerchantMapper;
/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-7-14 上午10:06:22
 * @version 0.1.0 
 * @copyright yougou.com 
 */
@Service("supplierSpLocalService")
public class SupplierSpServiceLocalImpl implements ISupplierSpLocalService {

	@Resource
	private ISupplierSpDao supplierSpDao;
	
	@Resource
	private MerchantMapper merchantMapper;
	
	@Override
	public PageFinder<SupplierSp> querySupplierListByPage(SupplierQueryVo vo,
			Query query, MerchantsVo merchantsVo) {
		StringBuilder hql = new StringBuilder(
				"select new SupplierSp(s.id, s.supplierCode, s.supplier,s.englishName, s.simpleName, s.contact,");
		hql.append("s.telePhone, s.email, s.fax, s.address,");
		hql.append("s.url, s.remark, s.isValid, s.supplierType,");
		hql.append("s.bank, s.subBank, s.dutyCode, s.creator,");
		hql.append("s.account, s.payType, s.conTime,s.updateTimestamp,s.updateDate,s.inventoryCode,s.isInputYougouWarehouse,s.couponsAllocationProportion,s.updateUser)");
		hql.append("from SupplierSp s");
		if (!StringUtils.isEmpty(merchantsVo.getSupplierYgContacts())) {
			hql.append(",MerchantSupplierExpand e ,SupplierYgContact c where e.YgContactUserId = c.userId and  s.supplierCode = e.merchantCode");
		}else{
			hql.append(" where 1=1");
		}
		List<Object> parms = new ArrayList<Object>();

		if (!StringUtils.isEmpty(vo.getSupplier())) {// 供应商名称
			hql.append(" and s.supplier like '%" + vo.getSupplier() + "%'");
		}
		if (!StringUtils.isEmpty(vo.getSupplierCode())) {// 供应商编码
			hql.append(" and s.supplierCode like '%" + vo.getSupplierCode()
					+ "%'");
		}
		if (vo.getIsValid() != null) {// 状态
			hql.append(" and s.isValid = " + vo.getIsValid());
		}
		if (vo.getIsInputYougouWarehouse() != null) {// 合作模式
			hql.append(" and s.isInputYougouWarehouse = "
					+ vo.getIsInputYougouWarehouse());
		}
		if (vo.getBrandNos() != null) {// 品牌
			hql.append("and s.id in (select supplyId from SpLimitBrand where brandNo in(");
			for (int i = 0, length = vo.getBrandNos().size(); i < length; i++) {
				if (i < length - 1) {
					hql.append("'"+vo.getBrandNos().get(i) + "',");
				} else {
					hql.append("'" + vo.getBrandNos().get(i) + "'");
				}
			}
			hql.append("))");
		}
		if (!StringUtils.isEmpty(merchantsVo.getSupplierYgContacts())) {// 商家负责人
			hql.append(" and c.userName like '%"
					+ merchantsVo.getSupplierYgContacts() + "%'");
		}
		hql.append(" and s.deleteFlag=1 order by s.updateDate desc");
		PageFinder<SupplierSp> pageFinder = supplierSpDao.pagedByHQL(
				hql.toString(), query.getPage(), query.getPageSize(),
				parms.toArray());
		return pageFinder;
	}
	
	
	@Override
	public PageFinder<SupplierVo> querySupplierListByPage(SupplierQueryVo queryVo,
			Query query) {
		
		PageFinder<SupplierVo> pageFinder = null;
		
		int count = merchantMapper.countByQuery(queryVo);
		
		if(0<count){
			
			List<SupplierVo> supplierList = merchantMapper.selectByQuery(queryVo,query);
			
			pageFinder = new PageFinder<SupplierVo>(query.getPage(), query.getPageSize(),count,supplierList);
			
		}
		
		return pageFinder;
	}
	
	@Override
	public List<SupplierSp> querySupplierList(SupplierQueryVo vo,
			 MerchantsVo merchantsVo) {
		StringBuilder hql = new StringBuilder(
				"select new SupplierSp(s.id, s.supplierCode, s.supplier,s.englishName, s.simpleName, s.contact,");
		hql.append("s.telePhone, s.email, s.fax, s.address,");
		hql.append("s.url, s.remark, s.isValid, s.supplierType,");
		hql.append("s.bank, s.subBank, s.dutyCode, s.creator,");
		hql.append("s.account, s.payType, s.conTime,s.updateTimestamp,s.updateDate,s.inventoryCode,s.isInputYougouWarehouse,s.couponsAllocationProportion,s.updateUser)");
		hql.append("from SupplierSp s");
		if (!StringUtils.isEmpty(merchantsVo.getSupplierYgContacts())) {
			hql.append(",MerchantSupplierExpand e ,SupplierYgContact c where e.YgContactUserId = c.userId and  s.supplierCode = e.merchantCode");
		}else{
			hql.append(" where 1=1");
		}
		List<Object> parms = new ArrayList<Object>();

		if (!StringUtils.isEmpty(vo.getSupplier())) {// 供应商名称
			hql.append(" and s.supplier like '%" + vo.getSupplier() + "%'");
		}
		if (!StringUtils.isEmpty(vo.getSupplierCode())) {// 供应商编码
			hql.append(" and s.supplierCode like '%" + vo.getSupplierCode()
					+ "%'");
		}
		if (vo.getIsValid() != null) {// 状态
			hql.append(" and s.isValid = " + vo.getIsValid());
		}
		if (vo.getIsInputYougouWarehouse() != null) {// 合作模式
			hql.append(" and s.isInputYougouWarehouse = "
					+ vo.getIsInputYougouWarehouse());
		}
		if (vo.getBrandNos() != null) {// 品牌
			hql.append("and s.id in (select supplyId from SpLimitBrand where brandNo in(");
			for (int i = 0, length = vo.getBrandNos().size(); i < length; i++) {
				if (i < length - 1) {
					hql.append("'"+vo.getBrandNos().get(i) + "',");
				} else {
					hql.append("'" + vo.getBrandNos().get(i) + "'");
				}
			}
			hql.append("))");
		}
		if (!StringUtils.isEmpty(merchantsVo.getSupplierYgContacts())) {// 商家负责人
			hql.append(" and c.userName like '%"
					+ merchantsVo.getSupplierYgContacts() + "%'");
		}
		if ( null!=vo.getSupplierType() ) {//商家类型
			hql.append(" and s.supplierType = '"+ vo.getSupplierType()+"'");
		}
		hql.append(" and s.deleteFlag=1 order by s.updateDate desc");
		List<SupplierSp> list = supplierSpDao.find( hql.toString(),parms.toArray() );
		return list;
	}
	
	@Override
	public String getSupplierNameById(String supplierId) {
		SupplierSp sp = supplierSpDao.getById(supplierId);
		if (null != sp) {
			return sp.getSupplier();
		}
		return null;
	}


	@Override
	public int countTotalRemind(SupplierQueryVo queryVo) {
		return  merchantMapper.countTotalRemind(queryVo);
	}


}
