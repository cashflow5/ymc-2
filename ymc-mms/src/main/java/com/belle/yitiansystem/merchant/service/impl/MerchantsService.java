package com.belle.yitiansystem.merchant.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Order;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.Type;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.belle.finance.costsettlement.costsetofbooks.model.vo.CostSetofBooks;
import com.belle.infrastructure.constant.Constant;
import com.belle.infrastructure.orm.basedao.CritMap;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.infrastructure.util.JDBCUtils;
import com.belle.infrastructure.util.Md5Encrypt;
import com.belle.infrastructure.util.UUIDGenerator;
import com.belle.infrastructure.util.UUIDUtil;
import com.belle.infrastructure.util.XmlTool;
import com.belle.other.dao.impl.SupplierContactDaoImpl;
import com.belle.other.dao.impl.SupplierContractDaoImpl;
import com.belle.other.model.pojo.SupplierContactSp;
import com.belle.other.model.pojo.SupplierContract;
import com.belle.other.model.pojo.SupplierSp;
import com.belle.other.service.ISqlService;
import com.belle.other.util.CodeGenerate;
import com.belle.yitiansystem.merchant.dao.impl.MerchantConsumableDaoImpl;
import com.belle.yitiansystem.merchant.dao.impl.MerchantExpressTemplateDaoImpl;
import com.belle.yitiansystem.merchant.dao.impl.MerchantGrantConsumableDaoImpl;
import com.belle.yitiansystem.merchant.dao.impl.MerchantRejectedAddressDaoImpl;
import com.belle.yitiansystem.merchant.dao.impl.MerchantUserDaoImpl;
import com.belle.yitiansystem.merchant.dao.impl.MerchantsAuthorityDaoImpl;
import com.belle.yitiansystem.merchant.dao.impl.MerchantsRoleDaoImpl;
import com.belle.yitiansystem.merchant.dao.impl.RoleAuthorityDaoImpl;
import com.belle.yitiansystem.merchant.dao.impl.SpLimitBrandDaoImpl;
import com.belle.yitiansystem.merchant.dao.impl.SpLimitCatDaoImpl;
import com.belle.yitiansystem.merchant.dao.impl.UserAuthorityDaoImpl;
import com.belle.yitiansystem.merchant.model.pojo.MerchantExpressTemplate;
import com.belle.yitiansystem.merchant.model.pojo.MerchantOperationLog;
import com.belle.yitiansystem.merchant.model.pojo.MerchantOperationLog.OperationType;
import com.belle.yitiansystem.merchant.model.pojo.MerchantRejectedAddress;
import com.belle.yitiansystem.merchant.model.pojo.MerchantUser;
import com.belle.yitiansystem.merchant.model.pojo.MerchantsAuthority;
import com.belle.yitiansystem.merchant.model.pojo.MerchantsRole;
import com.belle.yitiansystem.merchant.model.pojo.RoleAuthority;
import com.belle.yitiansystem.merchant.model.pojo.SpLimitBrand;
import com.belle.yitiansystem.merchant.model.pojo.SpLimitCat;
import com.belle.yitiansystem.merchant.model.pojo.UserRole;
import com.belle.yitiansystem.merchant.model.vo.MerchantsVo;
import com.belle.yitiansystem.merchant.service.IMerchantOperationLogService;
import com.belle.yitiansystem.merchant.service.IMerchantsService;
import com.belle.yitiansystem.merchant.service.ISupplierYgContactService;
import com.belle.yitiansystem.systemmgmt.dao.ISystemmgtUserDao;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.yougou.component.area.api.IAreaApi;
import com.yougou.component.area.model.Area;
import com.yougou.kaidian.common.commodity.pojo.Cat;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.common.vo.AuthorityComparator;
import com.yougou.kaidian.common.vo.MerchantsAuthorityVo;
import com.yougou.merchant.api.supplier.service.ISupplierService;
import com.yougou.merchant.api.supplier.vo.SupplierVo;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.model.brand.Brand;
import com.yougou.pc.model.category.Category;
import com.yougou.purchase.api.IPurchaseApiService;
import com.yougou.purchase.model.SupplierContact;

/**
 * 
 * 招商系统-招商基本信息service类
 * 
 * @author wang.m
 * @date 2012-03-05
 * 
 */
@Service
public class MerchantsService implements IMerchantsService {
	@Resource
	private ISqlService sqlService;
	@Resource
	private SupplierContactDaoImpl SupplierContactDaoImpl;
	@Resource
	private ISystemmgtUserDao userDao;
	/*@Resource
	private SupplierDaoImpl supplierDaoImpl;*/
	// 合同
	@Resource
	private SupplierContractDaoImpl supplierContractDaoImpl;
	@Resource
	private MerchantUserDaoImpl merchantUserDaoImpl;
	@Resource
	private SpLimitCatDaoImpl spLimitCatDaoImpl;
	@Resource
	private SpLimitBrandDaoImpl spLimitBrandDaoImpl;
	
	// 商家权限资源
	@Resource
	private MerchantsAuthorityDaoImpl merchantsAuthorityDaoImpl;
	// 商家角色
	@Resource
	private MerchantsRoleDaoImpl merchantsRoleDaoImpl;
	@Resource
	private RoleAuthorityDaoImpl roleAuthorityDaoImpl;
	/*@Resource
	private ICostSetOfBooksDao costSetOfBooksDao;*/
	@Resource
	private MerchantConsumableDaoImpl merchantConsumableDaoImpl;
	@Resource
	private MerchantGrantConsumableDaoImpl merchantGrantConsumableDaoImpl;
	@Resource
	private MerchantExpressTemplateDaoImpl merchantExpressTemplateDaoImpl;
	@Resource
	private MerchantRejectedAddressDaoImpl merchantRejectedAddressDaoImpl;
	
	@Resource
	private IMerchantOperationLogService merchantOperationLogService;
	
	@Resource
	private ICommodityBaseApiService commodityBaseApiService;
	@Resource
	private ISupplierService supplierService;
	@Resource
	private UserAuthorityDaoImpl userAuthorityDao;
	@Resource
	private ISupplierYgContactService supplierYgContactService;
	
	private Logger logger = Logger.getLogger(MerchantsService.class);
	
    @Resource
    private IAreaApi areaApi;
    @Resource
	private IPurchaseApiService purchaseApiService;
	@Resource
    private com.belle.yitiansystem.merchant.dao.mapper.MerchantBrandMapper merchantBrandMapper;
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 根据条件查询招商信息集合数据
	 * 
	 * @author wang.m
	 * @Date 2012-03-05
	 */
	@Deprecated
	public PageFinder<Map<String, Object>> queryMerchantsList(Query query, MerchantsVo merchantsVo) {	
		String sql = "SELECT DISTINCT t1.id,t1.supplier,t1.supplier_code,t1.coupons_allocation_proportion," +
				"t1.is_valid,t1.update_date,t1.update_user,t1.inventory_code,is_input_yougou_warehouse" +
				" FROM tbl_sp_supplier t1 LEFT JOIN tbl_sp_limit_brand t2 ON t1.id = t2.supply_id" +
				" LEFT JOIN tbl_commodity_brand t3 ON t2.brand_no = t3.brand_no" +
				" WHERE supplier_type='招商供应商' AND t1.delete_flag=1 ";

		// 拼接查询条件
		if (merchantsVo != null) {
			if (StringUtils.isNotBlank(merchantsVo.getSupplier())) {// 商家名称
				sql += " AND t1.supplier LIKE '%" + merchantsVo.getSupplier().trim() + "%'";
			}
			if (StringUtils.isNotBlank(merchantsVo.getBrandName())) {
				sql += " AND t3.brand_name LIKE '%" + merchantsVo.getBrandName().trim() + "%'";
			}
			if (StringUtils.isNotBlank(merchantsVo.getSupplierCode())) {// 商家编码
				sql += " AND t1.supplier_code LIKE '" + merchantsVo.getSupplierCode().trim() + "%'";
			}
			if (null != merchantsVo.getIsValid() && merchantsVo.getIsValid() != 0) {// 状态
				sql += " AND t1.is_valid= " + merchantsVo.getIsValid();
			}
			if (null != merchantsVo.getIsInputYougouWarehouse()) {// 仓库类型
				sql += " AND t1.is_input_yougou_warehouse= " + merchantsVo.getIsInputYougouWarehouse();
			}
		}
		sql += " ORDER BY t1.update_date DESC";
		PageFinder<Map<String, Object>> pageFinder = sqlService.getObjectsBySql(sql, query, null, null, null);
		return pageFinder;
	}
	
	/**
	 * 根据商家ID获取商家品牌列表
	 * @param merchantId
	 * @return
	 */
	/*public List<Brand> getMerchantBrandListByMerchantId(String merchantId){
		final String sql = "SELECT t2.brand_no,t2.brand_name FROM tbl_sp_limit_brand t1 INNER JOIN tbl_commodity_brand t2 ON t1.brand_no = t2.brand_no WHERE t1.supply_id = '"+merchantId+"'";
		List<Map<String, Object>> lstBrand = sqlService.getDatasBySql(sql);
		List<Brand> lstBrandResult = new ArrayList<Brand>(0);
		for(Map<String, Object> mapBrand : lstBrand) {
			Brand brand = new Brand();
			brand.setBrandNo(mapBrand.get("brand_no")!=null?mapBrand.get("brand_no").toString():"");
			brand.setBrandName(mapBrand.get("brand_name")!=null?mapBrand.get("brand_name").toString():"");
			lstBrandResult.add(brand);
			brand = null;
		}
		return lstBrandResult;
	}*/

	/**
	 * 根据供应商ID查询联系人列表
	 * 
	 * @author wang.m
	 * @Date 2012-03-05
	 */
	@Deprecated
	public PageFinder<Map<String, Object>> querySupplierContactSpList(Query query, MerchantsVo merchantsVo, String supplierId) {
		String sql = "SELECT t2.id,t1.supplier,t1.supplier_code,t2.contact,t2.type,t2.tele_phone,t2.mobile_phone," + " t2.fax,t2.email,t2.address FROM tbl_sp_supplier t1 "
				+ " INNER JOIN tbl_sp_supplier_contact t2 WHERE t1.id=t2.supply_id and t1.delete_flag=1 ";
		// 拼接查询条件
		if (StringUtils.isNotBlank(supplierId)) {
			// 供应商ID
			sql += " and t1.id='" + supplierId.trim() + "'";
		}
		// 拼接查询条件
		if (merchantsVo != null) {
			// 商家名称
			if (StringUtils.isNotBlank(merchantsVo.getSupplier())) {
				sql += " and t1.supplier like '" + merchantsVo.getSupplier().trim() + "%'";
			}
			// 商家编码
			if (StringUtils.isNotBlank(merchantsVo.getSupplierCode())) {
				sql += " and t1.supplier_code like '" + merchantsVo.getSupplierCode().trim() + "%'";
			}
			// 姓名
			if (StringUtils.isNotBlank(merchantsVo.getContact())) {
				sql += " and t2.contact like '" + merchantsVo.getContact().trim() + "%'";
			}
			// 手机号码
			if (StringUtils.isNotBlank(merchantsVo.getMobilePhone())) {
				sql += " and t2.mobile_phone like '" + merchantsVo.getMobilePhone().trim() + "%'";
			}
			// 电子邮箱
			if (StringUtils.isNotBlank(merchantsVo.getEmail())) {
				sql += " and t2.email like '" + merchantsVo.getEmail().trim() + "%'";
			}
		}
		sql += " ORDER BY t1.update_date DESC";
		PageFinder<Map<String, Object>> pageFinder = sqlService.getObjectsBySql(sql, query, null, null, null);
		return pageFinder;
	}

	/**
	 * 根据供应商ID查询合同列表
	 * 
	 * @author wang.m
	 * @Date 2012-03-05
	 */
	@Deprecated
	public PageFinder<Map<String, Object>> querysupplierContractList(Query query, MerchantsVo merchantsVo, String supplierId) {
		String sql = "SELECT t2.id,t2.contract_no,t1.is_valid,t1.supplier,t1.supplier_code,t2.clearing_form,t2.effective_date,"
				+ " t2.failure_date,t2.update_time,t2.update_user,t2.attachment FROM tbl_sp_supplier t1 "
				+ " INNER JOIN tbl_sp_supplier_contract t2 WHERE t1.id=t2.supplier_id and t1.delete_flag=1 ";
		// 拼接查询条件
		if (StringUtils.isNotBlank(supplierId)) {
			// 供应商ID
			sql += " and t1.id='" + supplierId.trim() + "'";

		}
		// 拼接查询条件
		if (merchantsVo != null) {
			// 商家名称
			if (StringUtils.isNotBlank(merchantsVo.getSupplier())) {
				sql += " and t1.supplier like '" + merchantsVo.getSupplier().trim() + "%'";
			}
			// 商家编码
			if (StringUtils.isNotBlank(merchantsVo.getSupplierCode())) {
				sql += " and t1.supplier_code like '" + merchantsVo.getSupplierCode().trim() + "%'";
			}
			// 合同编号
			if (StringUtils.isNotBlank(merchantsVo.getContractNo())) {
				sql += " and t2.contract_no like '" + merchantsVo.getContractNo().trim() + "%'";
			}
			// 状态
			if (null != merchantsVo.getIsValid() && merchantsVo.getIsValid() != 0) {
				sql += " and t1.is_valid=" + merchantsVo.getIsValid();
			}
			// 有效时间
			if (StringUtils.isNotBlank(merchantsVo.getEffectiveDate())) {
				sql += " and t2.effective_date >'" + merchantsVo.getEffectiveDate().trim() + "'";
			}
			// 失效时间
			if (StringUtils.isNotBlank(merchantsVo.getFailureDate())) {
				sql += " and t2.failure_date <'" + merchantsVo.getFailureDate().trim() + "'";
			}
		}
		sql += " ORDER BY t2.update_time DESC";
		PageFinder<Map<String, Object>> pageFinder = sqlService.getObjectsBySql(sql, query, null, null, null);
		return pageFinder;
	}

	/**
	 * 
	 * 判断联系人表中是否存在该手机号码
	 * 
	 * @param telePhone
	 *            电子邮箱
	 * @Date 2012-03-06
	 * @author wang.m
	 */
	@Override
	public boolean existsTelePhone(String telePhone) {
		if (StringUtils.isNotBlank(telePhone)) {
			String sql = "select count(t.id) from tbl_sp_supplier_contact t where t.tele_phone = '" + telePhone.trim() + "'";
			Long count = sqlService.getCountBySql(sql, null, null);
			return count > 0;
		}
		return false;
	}

	/**
	 * 
	 * 判断联系人表中是否存在该电子邮箱
	 * 
	 * @param email
	 *            电子邮箱
	 * @Date 2012-03-06
	 * @author wang.m
	 */
	public boolean existsEmail(String email) {
		if (StringUtils.isNotBlank(email)) {
			String sql = "select count(t.id) from tbl_sp_supplier_contact t where t.email = '" + email.trim() + "'";
			Long count = sqlService.getCountBySql(sql, null, null);
			return count > 0;
		}
		return false;
	}

	/**
	 * 
	 * 添加联系人信息
	 * 
	 * @Date 2012-03-06
	 * @author wang.m
	 * @throws Exception
	 */
	@Transactional
	public boolean add_linkmanList(SupplierContactSp supplierContactSp, String suplierName, String operator) throws Exception {
		boolean bool = false;
		try {
			SupplierContactSp supplierSp = new SupplierContactSp();
			if (supplierContactSp != null) {
				SupplierSp SupplierSp = new SupplierSp();
				SupplierVo vo = supplierService.getSupplierByName(suplierName);
				BeanUtils.copyProperties(SupplierSp, vo);
				
				supplierSp.setSupplier(SupplierSp);
				supplierSp.setContact(supplierContactSp.getContact());// 姓名
				supplierSp.setTelePhone(supplierContactSp.getTelePhone());// 手机号码
				supplierSp.setType(supplierContactSp.getType());// 类型
				supplierSp.setAddress(supplierContactSp.getAddress());
				supplierSp.setEmail(supplierContactSp.getEmail());
				supplierSp.setFax(supplierContactSp.getFax());
				supplierSp.setMobilePhone(supplierContactSp.getMobilePhone());
				SupplierContactDaoImpl.save(supplierSp);
				
				SupplierContact supplierContact = new SupplierContact();
				this.purchaseApiService.insertSupplierContact(supplierContact);
				
				/** 添加商家联系人日志 Modifier by yang.mq **/
				MerchantOperationLog operationLog = new MerchantOperationLog();
				operationLog.setMerchantCode(SupplierSp.getSupplierCode());
				operationLog.setOperator(operator);
				operationLog.setOperated(new Date());
				operationLog.setOperationType(OperationType.CONTACT);
				operationLog.setOperationNotes(merchantOperationLogService.buildMerchantContactOperationNotes(null, supplierSp));
				merchantOperationLogService.saveMerchantOperationLog(operationLog);
				
				bool = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("添加供应商添加联系人信息失败!", e);
			bool = false;
		}
		return bool;
	}

	/**
	 * 
	 * 修改联系人信息
	 * 
	 * @Date 2012-03-06
	 * @author wang.m
	 * @throws Exception
	 */
	@Transactional
	public boolean update_linkmanList(Query query, ModelMap modelMap, SupplierContactSp supplierContactSp, String suplierName, SystemmgtUser systemmgtUser) throws Exception {
		boolean bool = false;
		try {
			SupplierContactSp supplierSp = new SupplierContactSp();
			if (supplierContactSp != null) {
				// 供应商对象
				//SupplierSp SupplierSp = supplierDaoImpl.findSupplierByName(suplierName);// 根据供应商名称获取供应商对象
				SupplierSp SupplierSp = new SupplierSp();
				SupplierVo vo = supplierService.getSupplierByName(suplierName);
				BeanUtils.copyProperties(SupplierSp, vo);
				
				supplierSp.setSupplier(SupplierSp);
				supplierSp.setContact(supplierContactSp.getContact());// 姓名
				supplierSp.setTelePhone(supplierContactSp.getTelePhone());// 手机号码
				supplierSp.setType(supplierContactSp.getType());// 类型
				supplierSp.setAddress(supplierContactSp.getAddress());
				supplierSp.setEmail(supplierContactSp.getEmail());
				supplierSp.setFax(supplierContactSp.getFax());
				supplierSp.setMobilePhone(supplierContactSp.getMobilePhone());
				supplierSp.setId(supplierContactSp.getId());
				
				SupplierContactSp supplierSpInfo = SupplierContactDaoImpl.getById(supplierContactSp.getId());
				String operationNotes = merchantOperationLogService.buildMerchantContactOperationNotes(supplierSpInfo, supplierSp);
				
				SupplierContactDaoImpl.merge(supplierSp);
				
				if (StringUtils.isNotBlank(operationNotes)) {
					/** 添加商家联系人日志 Modifier by yang.mq **/
					MerchantOperationLog operationLog = new MerchantOperationLog();
					operationLog.setMerchantCode(SupplierSp.getSupplierCode());
					operationLog.setOperator(systemmgtUser.getUsername());
					operationLog.setOperated(new Date());
					operationLog.setOperationType(OperationType.CONTACT);
					operationLog.setOperationNotes(operationNotes);
					merchantOperationLogService.saveMerchantOperationLog(operationLog);
				}
				
				bool = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("修改供应商添加联系人信息失败!", e);
			bool = false;
		}
		return bool;

	}

	/**
	 * 
	 * 添加合同信息
	 * 
	 * @Date 2012-03-06
	 * @author wang.m
	 * @throws Exception
	 */
	@Deprecated
	@Transactional
	public boolean add_supplierContract(Query query, ModelMap modelMap, String effective, String failure, SupplierContract supplierContract, String suplierName,
			HttpServletRequest req) throws Exception {
		boolean bool = false;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			SupplierContract contract = new SupplierContract();
			if (supplierContract != null) {
				// 供应商对象
				//SupplierSp SupplierSp = this.findSupplierByName(suplierName);// 根据供应商名称获取供应商对象
				SupplierSp SupplierSp = new SupplierSp();
				SupplierVo vo = supplierService.getSupplierByName(suplierName);
				BeanUtils.copyProperties(SupplierSp, vo);
				
				contract.setSupplier(SupplierSp);
				contract.setContractNo(supplierContract.getContractNo());// 合同编号
				contract.setEffectiveDate(format.parse(effective));// 有效日期开始时间
				contract.setFailureDate(format.parse(failure));// 有效日期结束日期
				contract.setClearingForm(supplierContract.getClearingForm());
				contract.setUpdateTime(formDate());// 最后更新时间
				contract.setAttachment(supplierContract.getAttachment());// 附件名称
				SystemmgtUser user = GetSessionUtil.getSystemUser(req);
				String loginUser = "";
				if (user != null) {
					loginUser = user.getUsername();
				}
				contract.setUpdateUser(loginUser);// 操作人
				supplierContractDaoImpl.save(contract);
				
				/** 添加商家联系人日志 Modifier by yang.mq **/
				MerchantOperationLog operationLog = new MerchantOperationLog();
				operationLog.setMerchantCode(SupplierSp.getSupplierCode());
				operationLog.setOperator(loginUser);
				operationLog.setOperated(new Date());
				operationLog.setOperationType(OperationType.CONTRACT);
				operationLog.setOperationNotes(merchantOperationLogService.buildMerchantContractOperationNotes(null, contract));
				merchantOperationLogService.saveMerchantOperationLog(operationLog);
				
				bool = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("添加供应商添加合同信息失败!", e);
			bool = false;
		}
		return bool;
	}

	/**
	 * 
	 * 修改合同信息
	 * 
	 * @Date 2012-03-06
	 * @author wang.m
	 * @throws Exception
	 */
	@Deprecated
	@Transactional
	public boolean update_supplierContract(Query query, ModelMap modelMap, String effective, String failure, SupplierContract supplierContract, String suplierName,
			HttpServletRequest req) throws Exception {
		boolean bool = false;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			SupplierContract contract = new SupplierContract();
			if (supplierContract != null) {
				// 供应商对象
				//SupplierSp SupplierSp = supplierDaoImpl.findSupplierByName(suplierName);// 根据供应商名称获取供应商对象
				SupplierVo supplierInfo = supplierService.getSupplierByName(suplierName);
				SupplierSp SupplierSp = new SupplierSp();
				BeanUtils.copyProperties(SupplierSp, supplierInfo);
				
				contract.setSupplier(SupplierSp);
				contract.setContractNo(supplierContract.getContractNo());// 合同编号
				contract.setEffectiveDate(format.parse(effective));// 有效日期开始时间
				contract.setFailureDate(format.parse(failure));// 有效日期结束日期
				contract.setClearingForm(supplierContract.getClearingForm());
				contract.setUpdateTime(formDate());// 最后更新时间
				contract.setAttachment(supplierContract.getAttachment());// 附件名称
				SystemmgtUser user = GetSessionUtil.getSystemUser(req);
				String loginUser = "";
				if (user != null) {
					loginUser = user.getUsername();
				}
				contract.setUpdateUser(loginUser);// 操作人
				if (StringUtils.isNotBlank(supplierContract.getId())) {
					contract.setId(supplierContract.getId());
				}
				
				SupplierContract contractInfo = supplierContractDaoImpl.getById(supplierContract.getId());
				String operationNotes = merchantOperationLogService.buildMerchantContractOperationNotes(contractInfo, contract);
				
				supplierContractDaoImpl.merge(contract);
				
				if (StringUtils.isNotBlank(operationNotes)) {
					/** 添加商家联系人日志 Modifier by yang.mq **/
					MerchantOperationLog operationLog = new MerchantOperationLog();
					operationLog.setMerchantCode(SupplierSp.getSupplierCode());
					operationLog.setOperator(loginUser);
					operationLog.setOperated(new Date());
					operationLog.setOperationType(OperationType.CONTRACT);
					operationLog.setOperationNotes(operationNotes);
					merchantOperationLogService.saveMerchantOperationLog(operationLog);
				}
				
				bool = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("修改供应商添加合同信息失败!", e);
			bool = false;
		}
		return bool;
	}

	/**
	 * 查询商家信息列表
	 * 
	 * @return List<SupplierSp>
	 */
	public List<SupplierSp> getSupplies(String supplierspName) {
		List<SupplierSp> result = new ArrayList<SupplierSp>();
		/*CritMap critMap = new CritMap();
		critMap.addDesc("updateTimestamp");
		critMap.addEqual("supplierType", "招商供应商");
		critMap.addEqual("deleteFlag", 1);// 删除标志 1未删除
		if (StringUtils.isNotBlank(supplierspName)) {
			critMap.addLike("supplierCode", supplierspName);
		}
		result = supplierDaoImpl.findByCritMap(critMap);
		*/
		SupplierVo _vo = new SupplierVo();
		_vo.setSupplierType("招商供应商");
		_vo.setSupplierCode(supplierspName);
		try {
			List<SupplierVo> list = supplierService.querySupplierByVo(_vo);
			
			if (CollectionUtils.isNotEmpty(list)) {
				SupplierSp supplier = null;
				for (SupplierVo supplierVo : list) {
					supplier = new SupplierSp();
					BeanUtils.copyProperties(supplier, supplierVo);
					result.add(supplier);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return result;
	}

	/**
	 * 
	 * 根据联系人ID加载修改页面数据
	 * 
	 * @Date 2012-03-06
	 * @author wang.m
	 * @throws Exception
	 */
	public SupplierContactSp initial_linkmanList(Query query, ModelMap modelMap, String id) {
		List<SupplierContactSp> result = new ArrayList<SupplierContactSp>();
		CritMap critMap = new CritMap();
		critMap.addEqual("id", id);
		critMap.addFech("supplier");
		result = SupplierContactDaoImpl.findByCritMap(critMap);
		SupplierContactSp supplierContactSp = null;
		if (result != null && result.size() > 0) {
			supplierContactSp = result.get(0);
		}
		return supplierContactSp;
	}

	/**
	 * 
	 * 根据合同ID加载修改页面数据
	 * 
	 * @Date 2012-03-06
	 * @author wang.m
	 * @throws Exception
	 */
	public SupplierContract initial_supplierContract(Query query, ModelMap modelMap, String id) {
		List<SupplierContract> result = new ArrayList<SupplierContract>();
		CritMap critMap = new CritMap();
		critMap.addEqual("id", id);
		critMap.addFech("supplier");
		SupplierContract supplierContract = null;
		result = supplierContractDaoImpl.findByCritMap(critMap);
		if (result != null && result.size() > 0) {
			supplierContract = result.get(0);
		}
		return supplierContract;
	}

	/**
	 * 
	 * 判断商家登录名称是否已经存在
	 * 
	 * @Date 2012-03-07
	 * @author wang.m
	 */
	public boolean exitsLoginAccount(String loginAccount) {
		// 判断是否为空
		if (StringUtils.isNotBlank(loginAccount)) {
			CritMap critMap = new CritMap();
			critMap.addEqual("loginName", loginAccount.trim());
			critMap.addEqual("deleteFlag", 1);// 删除标志 1未删除
			List<MerchantUser> merchantUser = merchantUserDaoImpl.findByCritMap(critMap);
			if (merchantUser != null && merchantUser.size() > 0) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * 
	 * 添加普通供应商信息
	 * 
	 * @Date 2012-03-07
	 * @author wang.m
	 * @throws Exception
	 */
	@Transactional
	@Deprecated
	public Integer addSupplier(HttpServletRequest req, SupplierSp supplierSp) {
		Integer count = 0;
		try {
			SupplierSp supplier = new SupplierSp();
			supplier.setUpdateTimestamp(System.currentTimeMillis());// 时间戳
			supplier.setSupplier(supplierSp.getSupplier());// 供应商名称
			supplier.setSimpleName(supplierSp.getSimpleName());// 简称
			supplier.setAddress(supplierSp.getAddress());// 地址
			supplier.setTaxRate(supplierSp.getTaxRate());// 税率
			supplier.setRemark(supplierSp.getRemark());// 备注
			if (null != supplierSp && supplierSp.getSupplierType() != null && "招商供应商".equals(supplierSp.getSupplierType())) {
				if (supplierSp.getCouponsAllocationProportion() == null) {
					supplier.setCouponsAllocationProportion(supplierSp.getCouponsAllocationProportion());// 优惠券分摊比例
				} else {
					supplier.setCouponsAllocationProportion(0.00);// 优惠券分摊比例
				}
			} else {
				supplier.setCouponsAllocationProportion(0.00);// 优惠券分摊比例
			}
			supplier.setSupplierType(supplierSp.getSupplierType());// 供应商类型
			supplier.setIsInputYougouWarehouse(supplierSp.getIsInputYougouWarehouse());// 是否入优购仓库
			supplier.setSetOfBooksCode(supplierSp.getSetOfBooksCode());// 成本帐套编号
			supplier.setSetOfBooksName(supplierSp.getSetOfBooksName());// 成本帐套名称
			supplier.setBalanceTraderCode(supplierSp.getBalanceTraderCode());// 结算商家编码
			supplier.setBalanceTraderName(supplierSp.getBalanceTraderName());// 结算商家名字
			supplier.setPosSourceNo(supplierSp.getPosSourceNo());// pos供应商编码
			supplier.setIsUseYougouWms(supplierSp.getIsUseYougouWms());//是否使用优购WMS
			supplier.setDeleteFlag(1);// 未删除标志
			supplier.setShipmentType(supplierSp.getShipmentType());// 按发货预结算
			SystemmgtUser user = GetSessionUtil.getSystemUser(req);
			String loginUser = "";
			if (user != null) {
				loginUser = user.getUsername();
			}
			supplier.setCreator(loginUser);// 创建人
			supplier.setUpdateUser(loginUser);// 修改人
			supplier.setUpdateDate(new Date());// 修改时间 添加时默认为创建时间
			// 判定是添加还是修改
			if (StringUtils.isNotBlank(supplierSp.getId())) {
				// 查询修改前的内容
				SupplierSp supplierInfo = getSupplierSpById(supplierSp.getId());
				// 设置财务信息
				if (supplierInfo != null) {
					supplier.setBank(supplierInfo.getBank());
					supplier.setSubBank(supplierInfo.getSubBank());
					supplier.setDutyCode(supplierInfo.getDutyCode());
					supplier.setContact(supplierInfo.getContact());
					supplier.setPayType(supplierInfo.getPayType());
					supplier.setAccount(supplierInfo.getAccount());
					supplier.setConTime(supplierInfo.getConTime());
				}
				if (supplierInfo != null) {
					// 是招商供应商才添加历史记录(优惠券分摊比例)
					if (null != supplierSp && supplierSp.getSupplierType() != null && "招商供应商".equals(supplierSp.getSupplierType())) {
						// 优惠券分摊比例
						Double proportion = supplierInfo.getCouponsAllocationProportion() == null ? 0.0 : supplierInfo.getCouponsAllocationProportion();
						if (supplierSp.getCouponsAllocationProportion() != null && !proportion.equals(supplierSp.getCouponsAllocationProportion())) {
							count = addMerhcantlog(supplierSp.getId(), "修改招商供应商信息", "优惠券分摊比例", proportion.toString(), supplierSp.getCouponsAllocationProportion().toString(),
									loginUser);
						}
					}
					// 成本帐套名称
					String setOfBooksName = supplierInfo.getSetOfBooksName() == null ? "" : supplierInfo.getSetOfBooksName();
					if (!setOfBooksName.equals(supplierSp.getSetOfBooksName())) {
						count = addMerhcantlog(supplierSp.getId(), "修改普通供应商信息", "成本帐套名称", setOfBooksName, supplierSp.getSetOfBooksName(), loginUser);
					}
					// 税率
					Double taxRate = supplierInfo.getTaxRate() == null ? 0.0 : supplierInfo.getTaxRate();
					if (!taxRate.equals(supplierSp.getTaxRate())) {
						count = addMerhcantlog(supplierSp.getId(), "修改普通供应商信息", "税率", taxRate.toString(), supplierSp.getTaxRate().toString(), loginUser);
					}
					// 是否入优购仓库
					Integer warehouse = supplierInfo.getIsInputYougouWarehouse() == null ? 1 : supplierInfo.getIsInputYougouWarehouse();
					if (warehouse != supplierSp.getIsInputYougouWarehouse()) {
						String wareStr = "";
						String wareStr1 = "";
						if (warehouse == 1) {
							wareStr = "入优购仓库";
							wareStr1 = "不入优购仓库";
						} else {
							wareStr = "不入优购仓库";
							wareStr1 = "入优购仓库";
						}
						count = addMerhcantlog(supplierSp.getId(), "修改普通供应商信息", "仓库类型", wareStr, wareStr1, loginUser);
					}
				}
				supplier.setId(supplierSp.getId());
				supplier.setSupplierCode(supplierSp.getSupplierCode());// 供应商编号
				supplier.setIsValid(supplierSp.getIsValid());// 供应商状态
				//supplierDaoImpl.merge(supplier);
				//TODO
			} else {
				String supplierCode = new CodeGenerate().getSupplierCode();
				supplier.setSupplierCode(supplierCode);// 供应商编号
				supplier.setIsValid(2);// 供应商状态 新建
				//supplierDaoImpl.save(supplier);
				//TODO
			}

			count = 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("添加普通供应商信息失败!", e);
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 
	 * 添加招商供应商信息
	 * 
	 * @Date 2012-03-07
	 * @author wang.m
	 * @throws Exception
	 */
	@Transactional
	@Deprecated
	public Integer addMerchant(HttpServletRequest req, SupplierSp supplierSp, String bankNoHidden, String catNameHidden) {
		Integer count = 0;
		String supplierCode = "";
		String loginUser = "";
		try {
			SupplierSp supplier = new SupplierSp();
			if (supplierSp != null) {
				try {
					SystemmgtUser user = GetSessionUtil.getSystemUser(req);
					supplier.setUpdateTimestamp(System.currentTimeMillis());// 时间戳
					supplier.setSupplier(supplierSp.getSupplier());
					supplierCode = new CodeGenerate().getSupplierCode();
					supplier.setSupplierCode(supplierCode);// 商家编号
					supplier.setContact(supplierSp.getContact());// 开户名称
					supplier.setAccount(supplierSp.getAccount());// 开户银行账号
					supplier.setSubBank(supplierSp.getSubBank());// 开户子银行
					supplier.setBankLocal(supplierSp.getBankLocal());// 开户所在地
					supplier.setBusinessLicense(supplierSp.getBusinessLicense());// 营业执照号
					supplier.setBusinessLocal(supplierSp.getBusinessLocal());// 营业执照所在地
					supplier.setBusinessValidity(supplierSp.getBusinessValidity());// 营业执照有效期
					supplier.setTaxpayer(supplierSp.getTaxpayer());// 纳税人识别号
					supplier.setInstitutional(supplierSp.getInstitutional());// 组织机构代码
					supplier.setTallageNo(supplierSp.getTallageNo());// 税务登记证号
					supplier.setTaxRate(supplierSp.getTaxRate());// 税率
					supplier.setCouponsAllocationProportion(supplierSp.getCouponsAllocationProportion());// 优惠券分摊比例
					supplier.setIsValid(2);// 供应商状态
					supplier.setSupplierType(supplierSp.getSupplierType());// 供应商类型
					supplier.setIsInputYougouWarehouse(supplierSp.getIsInputYougouWarehouse());// 是否入优购仓库
					supplier.setIsUseYougouWms(supplierSp.getIsUseYougouWms());// 是否使用优购WMS
					supplier.setSetOfBooksCode(supplierSp.getSetOfBooksCode());// 成本帐套编号
					supplier.setSetOfBooksName(supplierSp.getSetOfBooksName());// 成本帐套名称
					if (user != null) {
						loginUser = user.getUsername();
					}
					supplier.setCreator(loginUser);// 创建人
					supplier.setUpdateUser(loginUser);// 修改人
					supplier.setUpdateDate(new Date());// 修改时间 添加时默认为创建时间
					supplier.setDeleteFlag(1);// 未删除标志
					supplier.setShipmentType(supplierSp.getShipmentType());// 按发货预结算
					supplier.setTradeCurrency(supplierSp.getTradeCurrency());
					//supplierDaoImpl.save(supplier);
					//TODO
					
					/** 添加商家资料日志 Modifier by yang.mq **/
					MerchantOperationLog operationLog = new MerchantOperationLog();
					operationLog.setMerchantCode(supplier.getSupplierCode());
					operationLog.setOperator(supplier.getCreator());
					operationLog.setOperated(new Date());
					operationLog.setOperationType(OperationType.BASIC_DATA);
					operationLog.setOperationNotes(merchantOperationLogService.buildMerchantBasicDataOperationNotes(null, supplier));
					merchantOperationLogService.saveMerchantOperationLog(operationLog);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("添加招商供应商信息失败!", e);
					count = 0;
				}

				try {
					// 商家用户信息
					MerchantUser merchantUser = new MerchantUser();
					merchantUser.setLoginName(supplierSp.getLoginAccount());// 登录名称
					// 对密码进行MD5加密
					String password = Md5Encrypt.md5(supplierSp.getLoginPassword());
					merchantUser.setPassword(password);// 登录密码
					merchantUser.setMerchantCode(supplierCode);// 商家编号
					merchantUser.setUserName("");// 商家真实信息
					merchantUser.setCreateTime(formDate());
					merchantUser.setStatus(1);// 状态 1表示可用
					merchantUser.setIsAdministrator(1); // 1表示管理员
					merchantUser.setDeleteFlag(1);// 未删除标志
					merchantUser.setIsYougouAdmin(0);
					merchantUserDaoImpl.save(merchantUser);
					
					/** 添加商家帐户日志 Modifier by yang.mq **/
					MerchantOperationLog operationLog = new MerchantOperationLog();
					operationLog.setMerchantCode(supplier.getSupplierCode());
					operationLog.setOperator(supplier.getCreator());
					operationLog.setOperated(new Date());
					operationLog.setOperationType(OperationType.ACCOUNT);
					operationLog.setOperationNotes(merchantOperationLogService.buildMerchantAccountOperationNotes(null, merchantUser));
					merchantOperationLogService.saveMerchantOperationLog(operationLog);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("添加商家帐号信息失败!", e);
					count = 0;
				}
			}
			// 添加商家品牌和分类权限设置
			addMerchantBankAndCat(supplier, req, bankNoHidden, catNameHidden, null, null, "1");
			count = 2;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加招商供应商信息失败!", e);
			count = 0;
		}
		return count;
	}

	/**
	 * 
	 * 修改招商供应商信息
	 * 
	 * @Date 2012-03-07
	 * @author wang.m
	 * @throws Exception
	 */
	@Transactional
	public Integer updateMerchant(HttpServletRequest req, SupplierSp supplierSp, String bankNameHidden, String catNameHidden, String brandList, String catList) throws Exception {
		// 查询修改前的内容
		SupplierSp supplierInfo = getSupplierSpById(supplierSp.getId());
		
		//TODO
		// 获取品牌修改前数据
		String[] brandInfos = getSpLimitBrandBysupplierId(supplierSp.getId());
		// 获取分类修改前数据
		String[] catInfos = getSpLimitCatBysupplierId(supplierSp.getId());
		
		SystemmgtUser user = GetSessionUtil.getSystemUser(req);
		String loginUser = "";
		if (user != null) {
			loginUser = user.getUsername();
		}
		SupplierSp supplier = new SupplierSp();
		supplier.setId(supplierSp.getId()); // 主键Id
		supplier.setUpdateTimestamp(System.currentTimeMillis());// 时间戳
		supplier.setSupplier(supplierSp.getSupplier());// 商家名称
		supplier.setSupplierCode(supplierSp.getSupplierCode());// 商家编号
		supplier.setContact(supplierSp.getContact());// 开户名称
		supplier.setAccount(supplierSp.getAccount());// 开户银行账号
		supplier.setSubBank(supplierSp.getSubBank());// 开户子银行
		supplier.setBankLocal(supplierSp.getBankLocal());// 开户所在地
		supplier.setBusinessLicense(supplierSp.getBusinessLicense());// 营业执照号
		supplier.setBusinessLocal(supplierSp.getBusinessLocal());// 营业执照所在地
		supplier.setBusinessValidity(supplierSp.getBusinessValidity());// 营业执照有效期
		supplier.setTaxpayer(supplierSp.getTaxpayer());// 纳税人识别号
		supplier.setInstitutional(supplierSp.getInstitutional());// 组织机构代码
		supplier.setTallageNo(supplierSp.getTallageNo());// 税务登记证号
		supplier.setTaxRate(supplierSp.getTaxRate());// 税率
		supplier.setCouponsAllocationProportion(supplierSp.getCouponsAllocationProportion());// 优惠券分摊比例
		supplier.setIsValid(supplierSp.getIsValid());// 供应商状态
		supplier.setSupplierType(supplierSp.getSupplierType());// 供应商类型
		supplier.setIsInputYougouWarehouse(supplierSp.getIsInputYougouWarehouse());// 是否入优购仓库
		supplier.setSetOfBooksCode(supplierSp.getSetOfBooksCode());// 成本帐套编号
		supplier.setSetOfBooksName(supplierSp.getSetOfBooksName());// 成本帐套名称
		supplier.setCreator(loginUser);// 创建人
		supplier.setUpdateUser(loginUser);// 修改人
		supplier.setUpdateDate(new Date());// 修改时间 添加时默认为创建时间
		supplier.setDeleteFlag(1);// 未删除标志
		supplier.setShipmentType(supplierSp.getShipmentType());// 按发货预结算
		supplier.setInventoryCode(supplierSp.getInventoryCode());//仓库编码
		if(supplierSp.getTradeCurrency() != null) {
			supplier.setTradeCurrency(supplierSp.getTradeCurrency());
		}
		supplier.setIsUseYougouWms(supplierSp.getIsUseYougouWms());//是否使用优购WMS

		String operationNotes = merchantOperationLogService.buildMerchantBasicDataOperationNotes(supplierInfo, supplier);
		
		//supplierDaoImpl.merge(supplier);
		//TODO
		
		// 根据商家Id删除商家品牌和分类权限设置
		deleteMerchantBankAndCat(supplierSp.getId());
		// 添加商家品牌和分类权限设置
		addMerchantBankAndCat(supplierSp, req, bankNameHidden, catNameHidden, brandList, catList, "2");
		
		
		if (ArrayUtils.isNotEmpty(brandInfos)) {
			if (!StringUtils.equals(brandInfos[0], brandList)) {
				operationNotes += MessageFormat.format("将“商品品牌”由【{0}】修改为【{1}】", brandInfos[0], brandList);
			}
		}
		if (ArrayUtils.isNotEmpty(catInfos)) {
			if (!StringUtils.equals(catInfos[0], catList)) {
				operationNotes += MessageFormat.format("将“商品分类”由【{0}】修改为【{1}】", catInfos[0], catList);
			}
		}
		if (StringUtils.isNotBlank(operationNotes)) {
			/** 添加商家资料日志 Modifier by yang.mq **/
			MerchantOperationLog operationLog = new MerchantOperationLog();
			operationLog.setMerchantCode(supplier.getSupplierCode());
			operationLog.setOperator(supplier.getCreator());
			operationLog.setOperated(new Date());
			operationLog.setOperationType(OperationType.BASIC_DATA);
			operationLog.setOperationNotes(operationNotes);
			merchantOperationLogService.saveMerchantOperationLog(operationLog);
		}
		
		return 2;
	}

	/**
	 * 
	 * 修改商家信息
	 * 
	 * @Date 2012-03-07
	 * @author wang.m
	 * @throws Exception
	 */
	@Transactional
	public boolean update_merchants(ModelMap modelMap, Query query, SupplierSp supplierSp, HttpServletRequest req, String bankName, String catName) {
		boolean bool = false;
		try {
			SupplierSp supplier = new SupplierSp();
			if (supplierSp != null) {
				try {
					supplier.setId(supplierSp.getId()); // 主键Id
					supplier.setUpdateTimestamp(System.currentTimeMillis());// 时间戳
					supplier.setSupplier(supplierSp.getSupplier());// 商家名称
					supplier.setSupplierCode(supplierSp.getSupplierCode());// 商家编号
					supplier.setContact(supplierSp.getContact());// 开户名称
					supplier.setAccount(supplierSp.getAccount());// 开户银行账号
					supplier.setSubBank(supplierSp.getSubBank());// 开户子银行
					supplier.setBankLocal(supplierSp.getBankLocal());// 开户所在地
					supplier.setBusinessLicense(supplierSp.getBusinessLicense());// 营业执照号
					supplier.setBusinessLocal(supplierSp.getBusinessLocal());// 营业执照所在地
					supplier.setBusinessValidity(supplierSp.getBusinessValidity());// 营业执照有效期
					supplier.setTaxpayer(supplierSp.getTaxpayer());// 纳税人识别号
					supplier.setInstitutional(supplierSp.getInstitutional());// 组织机构代码
					supplier.setTallageNo(supplierSp.getTallageNo());// 税务登记证号
					supplier.setTaxRate(supplierSp.getTaxRate());// 税率
					supplier.setCouponsAllocationProportion(supplierSp.getCouponsAllocationProportion());// 优惠券分摊比例
					supplier.setIsValid(2);// 供应商状态 2表示新建
					supplier.setSupplierType("招商供应商");
					supplier.setIsInputYougouWarehouse(supplierSp.getIsInputYougouWarehouse());// 是否入优购仓库
					supplier.setSetOfBooksCode(supplierSp.getSetOfBooksCode());// 成本帐套编号
					supplier.setSetOfBooksName(supplierSp.getSetOfBooksName());// 成本帐套名称
					supplier.setBalanceTraderCode(supplierSp.getBalanceTraderCode());// 结算商家编码
					supplier.setBalanceTraderName(supplierSp.getBalanceTraderName());// 结算商家名字

					SystemmgtUser user = GetSessionUtil.getSystemUser(req);
					String loginUser = "";
					if (user != null) {
						loginUser = user.getUsername();
					}
					supplier.setCreator(loginUser);// 创建人
					supplier.setUpdateUser(loginUser);// 修改人
					supplier.setUpdateDate(new Date());// 修改时间 添加时默认为创建时间
					supplier.setDeleteFlag(1);// 未删除标志
					//supplierDaoImpl.merge(supplier);
					//TODO
					
					// 根据商家Id删除商家品牌和分类权限设置
					deleteMerchantBankAndCat(supplierSp.getId());

					// 添加商家品牌和分类权限设置
					addMerchantBankAndCat(supplierSp, req, "", "", bankName, catName, "2");
					bool = true;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error("修改招商供应商信息失败!", e);
					bool = false;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("修改招商供应商信息失败!", e);
			bool = false;
		}
		return bool;
	}

	/**
	 * 根据商家ID查询品牌权限列表
	 */
	private List<SpLimitBrand> getBrankSupplier(String id) {
		CritMap critMap = new CritMap();
		critMap.addEqual("supplyId", id);
		List<SpLimitBrand> list = spLimitBrandDaoImpl.findByCritMap(critMap);
		return list;
	}

	/**
	 * 根据商家ID查询品牌权限列表
	 */
	private List<SpLimitCat> getCatSupplier(String id) {
		CritMap critMap = new CritMap();
		critMap.addEqual("supplyId", id);
		List<SpLimitCat> list = spLimitCatDaoImpl.findByCritMap(critMap);
		return list;
	}

	/**
	 * 根据ID查询商家基本信息
	 * 
	 * @author wang.m
	 * @Date 2012-03-07
	 */
	public SupplierSp getSupplierSpById(String id) {
		SupplierSp supplierSp = new SupplierSp();
		
		SupplierVo _vo = new SupplierVo();
		SupplierVo supplier = null;
		_vo.setId(id);
		_vo.setIsValid(null);
		List<SupplierVo> vos = null;
		try {
			vos = supplierService.querySupplierByVo(_vo);
			
			supplier = CollectionUtils.isNotEmpty(vos) ? vos.get(0) : null;
			BeanUtils.copyProperties(supplierSp, supplier);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return supplierSp;
	}
	
	private SupplierVo getSupplierVoById(String id) {
		SupplierVo _vo = new SupplierVo();
		SupplierVo supplier = null;
		_vo.setId(id);
		List<SupplierVo> vos = null;
		try {
			vos = supplierService.querySupplierByVo(_vo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CollectionUtils.isNotEmpty(vos) ? vos.get(0) : null;
	}

	/**
	 * 根据商家ID查询商家分类权限基本信息
	 * 
	 * @author wang.m
	 * @throws SQLException
	 * @Date 2012-03-08
	 */
	@Deprecated
	public String[] getSpLimitCatBysupplierId(String id) throws SQLException {
		String str[] = new String[2];
		String catStr = "";
		String catHide = "";
		if (StringUtils.isNotBlank(id)) {
			/*
			CritMap critMap = new CritMap();
			critMap.addEqual("supplyId", id);
			List<SpLimitCat> spLimitCat = spLimitCatDaoImpl.findByCritMap(critMap);
			if (null != spLimitCat && spLimitCat.size() > 0) {
				for (SpLimitCat spLimitCat2 : spLimitCat) {
					// 根据分类编号查询3级分类名称
					String threeCatName = this.getCatName(spLimitCat2.getCatNo());
					// 根据3级分类名称查询2级分类名称
					String twoCatName = this.getTowCatName(threeCatName, spLimitCat2.getStructName());
					// 根据3级分类名称和分类结构查询1级分类名称
					String structName = spLimitCat2.getStructName().substring(0, 5);
					String OneCatName = this.getOneCatName(twoCatName, structName);
					catStr += OneCatName + "-" + twoCatName + "-" + threeCatName + ";";
					catHide += spLimitCat2.getStructName() + ";" + spLimitCat2.getId() + "_";
				}
				catStr = catStr.substring(0, catStr.length() - 1);
				catHide = catHide.substring(0, catHide.length() - 1);
			}
			*/
			List<Map<String, Object>> spLimitBrandMaps = sqlService.getDatasBySql("select id, cat_no, struct_name from tbl_sp_limit_cat where supply_id = '" + id + "'");
			if (null != spLimitBrandMaps && spLimitBrandMaps.size() > 0) {
				for (Map<String, Object> spLimitBrandMap : spLimitBrandMaps) {
					String cat_no = MapUtils.getString(spLimitBrandMap, "cat_no");
					String struct_name = MapUtils.getString(spLimitBrandMap, "struct_name");
					// 根据分类编号查询3级分类名称
					String threeCatName = this.getCatName(cat_no);
					// 根据3级分类名称查询2级分类名称
					String twoCatName = this.getTowCatName(threeCatName, struct_name);
					// 根据3级分类名称和分类结构查询1级分类名称
					String structName = struct_name.substring(0, 5);
					String OneCatName = this.getOneCatName(twoCatName, structName);
					catStr += OneCatName + "-" + twoCatName + "-" + threeCatName + ";";
					catHide += struct_name + ";" + spLimitBrandMap.get("id") + "_";
				}
				catStr = catStr.substring(0, catStr.length() - 1);
				catHide = catHide.substring(0, catHide.length() - 1);
			}
		}
		str[0] = catStr;
		str[1] = catHide;
		return str;
	}

	/**
	 * 根据商家ID查询商家授权品牌--分类权限
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	@Deprecated
	public String[] getSpLimitBrandCatBysupplierId(String id) throws SQLException {
		String str[] = new String[2];
		String catStr = "";
		String catHide = "";
		if (StringUtils.isNotBlank(id)) {
			List<Map<String, Object>> spLimitBrandMaps = sqlService.getDatasBySql("SELECT bc.id, c.cat_no, c.struct_name, b.brand_no FROM tbl_sp_limit_cat c LEFT JOIN tbl_sp_limit_brand_cat bc ON c.id = bc.cat_id LEFT JOIN tbl_sp_limit_brand b ON bc.brand_id = b.id WHERE c.supply_id = '" + id + "'");
			if (CollectionUtils.isNotEmpty(spLimitBrandMaps)) {
				for (Map<String, Object> spLimitBrandMap : spLimitBrandMaps) {
					String struct_name = MapUtils.getString(spLimitBrandMap, "struct_name");
					String brand_no = MapUtils.getString(spLimitBrandMap, "brand_no");
					if (StringUtils.isBlank(struct_name)) continue;
					
					Category cat = commodityBaseApiService.getCategoryByStructName(struct_name);
					com.yougou.pc.model.brand.Brand brand = null;
					if (StringUtils.isNotBlank(brand_no)) {
						brand = commodityBaseApiService.getBrandByNo(brand_no);
					}
					catStr += (brand == null ? "" : brand.getBrandName()) + "|" + cat.getStructCatName() + ";";
					catHide += brand_no + ";" + struct_name + ";" + cat.getId() + "_";
				}
				catStr = catStr.substring(0, catStr.length() - 1);
				catHide = catHide.substring(0, catHide.length() - 1);
			}
		}
		str[0] = catStr;
		str[1] = catHide;
		return str;
	}
	
	/**
	 * 通过商家ID获取授权的品牌-分类关系权限
	 */
	public List<String> queryAuthorizationBrandCatBysupplierId(String id) throws SQLException {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		
		List<String> brandStructs = new ArrayList<String>();
		List<Map<String, Object>> spLimitBrandMaps = sqlService.getDatasBySql("SELECT bc.id, c.cat_no, c.struct_name, b.brand_no FROM tbl_sp_limit_cat c LEFT JOIN tbl_sp_limit_brand_cat bc ON c.id = bc.cat_id LEFT JOIN tbl_sp_limit_brand b ON bc.brand_id = b.id WHERE c.supply_id = '" + id + "'");
		if (CollectionUtils.isNotEmpty(spLimitBrandMaps)) {
			for (Map<String, Object> map : spLimitBrandMaps) {
				String struct_name = MapUtils.getString(map, "struct_name");
				String brand_no = MapUtils.getString(map, "brand_no");
				if (StringUtils.isBlank(struct_name) || StringUtils.isBlank(brand_no)) continue;
				
				brandStructs.add(brand_no + ";" + struct_name);
			}
		}
		return brandStructs;
	}
	
	/**
	 * 根据商家ID查询商家品牌权限基本信息
	 * 
	 * @author wang.m
	 * @throws SQLException
	 * @Date 2012-03-08
	 */
	@Deprecated
	public String[] getSpLimitBrandBysupplierId(String id) throws SQLException {
		String str[] = new String[2];
		String brandStr = "";
		String brandHide = "";
		if (StringUtils.isNotBlank(id)) {
			List<Map<String, Object>> spLimitBrandMaps = sqlService.getDatasBySql("select id, brand_no from tbl_sp_limit_brand where supply_id = '" + id + "'");
			if (null != spLimitBrandMaps && spLimitBrandMaps.size() > 0) {
				for (Map<String, Object> spLimitBrandMap : spLimitBrandMaps) {
					String brand_no = MapUtils.getString(spLimitBrandMap, "brand_no");
					// 根据品牌编号查询分类名称
					Brand brand = commodityBaseApiService.getBrandByNo(brand_no);
					String brandName = (brand != null) ? brand.getBrandName() : "";
					brandStr += brandName + ";";
					brandHide += spLimitBrandMap.get("id") + "_" + brand_no + "_" + brandName + ";";
				}
				brandStr = brandStr.substring(0, brandStr.length() - 1);
				brandHide = brandHide.substring(0, brandHide.length() - 1);
			}
		}
		str[0] = brandStr;
		str[1] = brandHide;
		return str;
	}
	
	/**
	 * 通过商家ID查询授权品牌列表
	 */
	public List<String> getAuthorizationBrandNos(String id) throws SQLException {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		
		List<String> brandNos = new ArrayList<String>();
		List<Map<String, Object>> spLimitBrandMaps = sqlService.getDatasBySql("select id, brand_no from tbl_sp_limit_brand where supply_id = '" + id + "'");
		if (CollectionUtils.isNotEmpty(spLimitBrandMaps)) {
			for (Map<String, Object> spLimitBrandMap : spLimitBrandMaps) {
				String brand_no = MapUtils.getString(spLimitBrandMap, "brand_no");
				if (StringUtils.isBlank(brand_no)) continue;
				
				brandNos.add(brand_no);
			}
		}
		return brandNos;
	}
	
	/*
	 * 通过商家ID查询授权的品牌字符串
	 * (non-Javadoc)
	 * @see com.belle.yitiansystem.merchant.service.IMerchantsService#queryAuthorizationBrandBysupplierId(java.lang.String)
	 */
	public String queryAuthorizationBrandBysupplierId(String id) throws SQLException {
		List<String> brandNos = this.getAuthorizationBrandNos(id);
		StringBuffer sb = new StringBuffer("");
		
		if (CollectionUtils.isEmpty(brandNos)) return sb.toString();
		
		for (String brandNo : brandNos) {
			com.yougou.pc.model.brand.Brand brand = commodityBaseApiService.getBrandByNo(brandNo);
			if (brand == null) continue;
			
			String brandName = brand.getBrandName();
			if (sb.length() == 0)
				sb.append(brandNo).append(";").append(brandName);
			else 
				sb.append("_").append(brandNo).append(";").append(brandName);
		}
		return sb.toString();
	}
	
	/**
	 * 根据用户名称查询用户对象
	 * 
	 * @author wang.m
	 * @Date 2012-03-08
	 */
	public SystemmgtUser getSystemmgtUserByUserName(String loginUserName) {
		SystemmgtUser systemmgtUser = null;
		if (StringUtils.isNotBlank(loginUserName)) {
			CritMap critMap = new CritMap();
			critMap.addEqual("loginName", loginUserName);
			List<SystemmgtUser> systemmgtUserList = userDao.findByCritMap(critMap);
			if (systemmgtUserList != null && systemmgtUserList.size() > 0) {
				systemmgtUser = systemmgtUserList.get(0);
			}
		}
		return systemmgtUser;
	}


	/**
	 * 根据3级分类名称查询2级分类名称
	 * 
	 * @author wang.m
	 * @Date 2012-03-20
	 * 
	 */
	public String getTowCatName(String brandNo, String structName) throws SQLException {
		List<Object> obj = null;
		StringBuffer buffer = new StringBuffer(1024);
		buffer.append("SELECT cat_name FROM tbl_commodity_catb2c WHERE struct_name=(SELECT SUBSTRING(struct_name,1,5) FROM tbl_commodity_catb2c WHERE cat_name=? and struct_name=?) ");
		if (StringUtils.isNotBlank(brandNo)) {
			obj = new ArrayList<Object>();
			obj.add(brandNo);
			obj.add(structName);
		}
		String cat = getResouseBySql(buffer.toString(), obj);
		return cat;
	}

	/**
	 * 根据2级分类名称查询1级分类名称
	 * 
	 * @author wang.m
	 * @Date 2012-03-20
	 * 
	 */
	public String getOneCatName(String brandNo, String structName) throws SQLException {
		List<Object> obj = null;
		StringBuffer buffer = new StringBuffer(1024);
		buffer.append("SELECT cat_name FROM tbl_commodity_catb2c WHERE struct_name=(SELECT SUBSTRING(struct_name,1,2) FROM tbl_commodity_catb2c WHERE cat_name=? and struct_name=?)");
		if (StringUtils.isNotBlank(brandNo) && StringUtils.isNotBlank(structName)) {
			obj = new ArrayList<Object>();
			obj.add(brandNo);
			obj.add(structName);
		}
		String cat = getResouseBySql(buffer.toString(), obj);
		return cat;
	}

	/**
	 * wms调用接口 商家绑定仓库编码
	 * 
	 * @param merchantsCode
	 *            商家编号
	 * @param warehouseName
	 *            仓库编码
	 * @throws Exception
	 */
	public boolean update_merchant_virtualWarehouseCode(String merchantsCode, String warehouseCode, HttpServletRequest req) {
		boolean bool = false;
		if (StringUtils.isNotBlank(merchantsCode) && StringUtils.isNotBlank(warehouseCode)) {
			try {
				String sql = "select 1 from tbl_wms_warehouse where warehouse_code = ? and isoutwarehouse = ?";
				Object[] obj = new Object[2];
				obj[0] = warehouseCode;
				obj[1] = NumberUtils.INTEGER_ONE;
				
				/** 虚拟仓库拥有地区仓属性 **/
				if (JDBCUtils.getInstance().count(sql, obj) != 1) {
					//return bool;
				}
				
				SystemmgtUser user = GetSessionUtil.getSystemUser(req);
				String updateUser = "";
				if (user != null) {
					updateUser = user.getUsername();
				}
				
				/** 商家解绑虚拟仓库 **/
				sql = "update tbl_sp_supplier set inventory_code = null, update_user = ?, update_date = ? where inventory_code = ?";
				obj = new Object[3];
				obj[0] = updateUser;// 更新人
				obj[1] = new Date(); // 获取最后更新时间
				obj[2] = warehouseCode;
				sqlService.updateObject(sql, obj);
				
				/** 商家绑定虚拟仓库 **/
				sql = "update tbl_sp_supplier set inventory_code = ?, update_user = ?, update_date = ? where supplier_code = ?";
				obj = new Object[4];
				obj[0] = warehouseCode;
				obj[1] = updateUser;// 更新人
				obj[2] = new Date(); // 获取最后更新时间
				obj[3] = merchantsCode;
				bool = sqlService.updateObject(sql, obj);
			} catch (Exception e) {
				logger.error("wms调用接口 商家绑定仓库编码失败!", e);
				throw new RuntimeException(e);
			}
		}
		return bool;
	}

	/**
	 * 修改商家品牌权限
	 * 
	 * @throws Exception
	 * @throws Exception
	 * 
	 */
	private void update_SpLimitBrand(String id) throws Exception {
		String sql = "delete from tbl_sp_limit_brand where id=?";
		Object[] obj = new Object[1];
		obj[0] = id;
		sqlService.updateObject(sql, obj);
	}

	/**
	 * 修改商家分类权限
	 * 
	 * @throws Exception
	 * 
	 */
	private void update_SpLimitCat(String id) throws Exception {
		String sql = "delete from tbl_sp_limit_cat where id=?";
		Object[] obj = new Object[1];
		obj[0] = id;
		sqlService.updateObject(sql, obj);
	}

	/**
	 * 修改商家品牌--分类关系
	 * 
	 * @throws Exception
	 * 
	 */
	private void update_SpLimitBrand_Cat(String id) throws Exception {
		String sql = "delete from tbl_sp_limit_brand_cat where id=?";
		Object[] obj = new Object[1];
		obj[0] = id;
		sqlService.updateObject(sql, obj);
	}
	
	/**
	 * 根据分类编号查询下面所有的3级分类数据
	 * 
	 * @throws SQLException
	 * 
	 */
	private List<Map<String, Object>> getCommodityCatb2cByStructName(String structName) throws SQLException {
		List<Map<String, Object>> maps = null;
		String sql = "select struct_name,id,no,cat_name from tbl_commodity_catb2c WHERE level = 3 and delete_flag=1 ";
		if (StringUtils.isNotBlank(structName)) {
			// struct_name LIKE ? AND
			sql += " and struct_name like '" + structName + "%'";
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				Map<String, Object> map = null;
				maps = new ArrayList<Map<String, Object>>();
				while (rs.next()) {
					map = new HashMap<String, Object>();
					ResultSetMetaData rsmd = rs.getMetaData();
					for (int i = 0; i < rsmd.getColumnCount();) {
						++i;
						String key = rsmd.getColumnLabel(i).toLowerCase();
						if (map.containsKey(key)) {
							throw new IllegalArgumentException("有两个相同key值  为：" + key);
						}
						map.put(key, rs.getObject(i));
					}
					maps.add(map);
				}
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("根据分类编号查询下面所有的3级分类数据失败!", e);
			} finally {
				close(conn, pstmt, rs);
			}
		}
		return maps;

	}

	/**
	 * 根据分类编号查询3级分类名称
	 * 
	 * @author wang.m param catNo
	 * @throws SQLException
	 */
	private String getCatName(String catNo) throws SQLException {
		StringBuffer buffer = new StringBuffer(1024);
		buffer.append("SELECT cat_name FROM tbl_commodity_catb2c WHERE 1=1");
		if (StringUtils.isNotBlank(catNo)) {
			buffer.append(" and NO ='" + catNo + "'");
		}
		String cat = getResouseBySql(buffer.toString(), null);
		return cat;
	}

	/**
	 * 根据品牌编号查询分类名称
	 * 
	 * @author wang.m param brandNo
	 * @throws SQLException
	 */
	/*private String getBrandName(String brandNo) throws SQLException {
		StringBuffer buffer = new StringBuffer(1024);
		buffer.append("SELECT brand_name FROM tbl_commodity_brand  WHERE 1=1");
		if (StringUtils.isNotBlank(brandNo)) {
			buffer.append("  and brand_no='" + brandNo + "'");
		}
		String cat = getResouseBySql(buffer.toString(), null);
		return cat;
	}*/

	/**
	 * 封装sql语句查询类
	 * 
	 * @throws SQLException
	 * 
	 **/
	private String getResouseBySql(String sql, List<Object> params) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String str = "";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			if (params != null && params.size() > 0) {
				for (int j = 0; j < params.size(); j++) {
					pstmt.setObject(j + 1, params.get(j));
				}
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ResultSetMetaData rsmd = rs.getMetaData();
				for (int i = 0; i < rsmd.getColumnCount();) {
					++i;
					str = (String) rs.getObject(i);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("执行sql语句失败!" + sql, e);
		} finally {
			close(conn, pstmt, rs);
		}
		return str;
	}

	/**
	 * 关键数据库连接类
	 * 
	 * @param conn
	 * @param pstmt
	 * @param rs
	 */
	private void close(Connection conn, Statement pstmt, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}

			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
			if (conn != null) {
				if (!conn.isClosed()) {
					conn.close();
				}
				conn = null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("关闭数据库连接失败!", e);
		}
	}

	private Connection getConnection() {
		return userDao.getHibernateSession().connection();
	}

	/**
	 * 查询所有商家角色集合
	 * 
	 * @author wang.m
	 * @Date 2012-03-26
	 * 
	 */
	public List<MerchantsRole> getMerchantsRoleList() {
		CritMap critMap = new CritMap();
		critMap.addAsc("createTime");
		List<MerchantsRole> merchantsAuthorityList = merchantsRoleDaoImpl.findByCritMap(critMap);
		return merchantsAuthorityList;
	}

	/**
	 * 查询所有商家资源集合
	 * 
	 * @author wang.m
	 * @Date 2012-03-26
	 * 
	 */
	public List<MerchantsAuthority> getMerchantsAuthorityList(String uid) {
		List<MerchantsAuthority> list = new ArrayList<MerchantsAuthority>();
		try {

			CritMap critMap = new CritMap();
			critMap.addAsc("authrityModule");
			critMap.addNotEqual("parentId", "0");
			List<MerchantsAuthority> merchantsAuthorityList = merchantsAuthorityDaoImpl.findByCritMap(critMap);
			for (MerchantsAuthority merchantsAuthority : merchantsAuthorityList) {
				String sql = "SELECT t1.authority_id FROM tbl_merchant_user_authority t1 WHERE t1.authority_id=? and t1.user_id=?";
				List<Object> params = new ArrayList<Object>();
				params.add(merchantsAuthority.getId());
				params.add(uid);
				String aid = getResouseBySql(sql, params);
				if (StringUtils.isNotBlank(aid)) {
					if (aid.equals(merchantsAuthority.getId())) {
					} else {
						list.add(merchantsAuthority);
					}
				} else {
					list.add(merchantsAuthority);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("查询所有商家资源集合失败!", e);
		}
		return list;
	}

	/**
	 * 查询商家Id查询该用户具体那些角色
	 * 
	 * @author wang.m
	 * @throws SQLException
	 * @Date 2012-03-26
	 * 
	 */
	public List<Map<String, Object>> getMerchantsRoleDaoImplById(String id) throws SQLException {
		String sql = "SELECT t1.id,t1.role_name FROM tbl_merchant_role t1 INNER JOIN tbl_merchant_user_role t2 ON t1.id=t2.role_id WHERE 1=1 AND t2.user_id='" + id + "'";
		List<Map<String, Object>> maps = getMapBysql(sql, null);
		return maps;
	}

	/**
	 * 删除商家信息
	 * 
	 * @author wang.M
	 * 
	 */
	public boolean delete_merchants(String id, String supplierCode) throws Exception {
		boolean isDeleteSuccess = false;
		try {
			if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(supplierCode)) {
				// 删除商家信息 修改删除标志为0
				/**
				String sql = "UPDATE tbl_sp_supplier SET delete_flag=0 WHERE is_valid=2 and id=?";
				Object[] obj = new Object[1];
				obj[0] = id;
				sqlService.updateObject(sql, obj);
				*/
				// 删除商家帐号信息
				String sql2 = "UPDATE tbl_merchant_user SET delete_flag=0,create_time=? WHERE merchant_code=? ";
				Object[] obj2 = new Object[2];
				obj2[0] = formDate();
				obj2[1] = supplierCode;
				sqlService.updateObject(sql2, obj2);
				
				// 删除商家联系人信息
				/**
				String sql3 = "DELETE FROM tbl_sp_supplier_contact WHERE supply_id = ?";
				Object[] obj3 = new Object[1];
				obj3[0] = id;
				sqlService.updateObject(sql3, obj3);
				*/
				this.purchaseApiService.deleteSupplierContact(id);
				
				// 删除商家合同信息
				String sql4 = "DELETE FROM tbl_sp_supplier_contract WHERE supplier_id = ?";
				Object[] obj4 = new Object[1];
				obj4[0] = id;
				sqlService.updateObject(sql4, obj4);
				
				// 删除商家操作日志信息
				String sql5 = "DELETE FROM tbl_merchant_operation_log WHERE merchant_code = ?";
				Object[] obj5 = new Object[1];
				obj5[0] = supplierCode;
				sqlService.updateObject(sql5, obj5);
				
				isDeleteSuccess = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("删除商家信息失败!", ex);
			isDeleteSuccess = false;
		}
		return isDeleteSuccess;
	}

	/**
	 * 添加商家权限
	 * 
	 * @author wang.m
	 * @Date 2012-03-26
	 */
	public boolean saveUserAuthority(String uid, String authority) throws Exception {
		boolean bool = false;
		try {
			if (StringUtils.isNotBlank(uid) && StringUtils.isNotBlank(authority)) {
				// 先删除这个用户所具备的角色
				deleteMerchantsAuthorityByPramas(uid);
				// 添加
				String sql = "insert into tbl_merchant_user_role(id,user_id,role_id,create_date,remark) values (?,?,?,?,?)";
				String[] strAdd = authority.split(";");
				if (strAdd.length > 0) {
					for (String string : strAdd) {
						Object[] obj = new Object[5];
						// 获取uuid
						String uuid = UUIDGenerator.getUUID();
						obj[0] = uuid;
						obj[1] = uid;
						obj[2] = string;
						obj[3] = formDate();
						obj[4] = "";
						sqlService.updateObject(sql, obj);
						bool = true;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("添加商家权限失败!", e);
			bool = false;
		}
		return bool;
	}

	/**
	 * 查询商家用户资源集合列表
	 * 
	 * @author wang.m
	 * @Date 2012-03-26
	 */
	public PageFinder<Map<String, Object>> queryMerchantsAuthorityList(Query query, MerchantsAuthority merchantsAuthority) {
		String sql = "SELECT t1.id,t1.parent_id,t2.authrity_name as parent_name,t1.authrity_name,t1.authrity_url,t1.authrity_module,t1.sort_no,t1.create_time,t1.remark from tbl_merchant_authority t1 LEFT JOIN tbl_merchant_authority t2 on t1.parent_id=t2.id where 1=1 ";

		// 拼接查询条件
		if (merchantsAuthority != null) {
			if (StringUtils.isNotBlank(merchantsAuthority.getAuthrityName())) {// 商家名称
				sql += " and t1.authrity_name like '" + merchantsAuthority.getAuthrityName().trim() + "%' ";
			}
		}
		sql += " ORDER BY t1.sort_no asc,t1.create_time desc";
		PageFinder<Map<String, Object>> pageFinder = sqlService.getObjectsBySql(sql, query, null, null, null);
		return pageFinder;
	}
	
	/**
	 * 查询商家用户资源集合列表
	 * 
	 * @author wang.m
	 * @Date 2012-03-26
	 */
	public List<Map<String, Object>> queryAllMerchantsAuthorityList() {
		String sql = "SELECT t1.id,t1.parent_id,t1.authrity_name,t1.authrity_url,t1.authrity_module,t1.sort_no,t1.create_time,t1.remark from tbl_merchant_authority t1 where 1=1 ";


		sql += " ORDER BY t1.sort_no asc";
		List<Map<String, Object>>  authorityList= sqlService.getDatasBySql(sql);
		return authorityList;
	}

	/**
	 * 
	 * 根据Id查询商家权限对象
	 */
	public MerchantsAuthority getMerchantsAuthorityByid(String id) {
		List<MerchantsAuthority> result = new ArrayList<MerchantsAuthority>();
		MerchantsAuthority merchantsAuthority = null;
		CritMap critMap = new CritMap();
		if (StringUtils.isNotBlank(id)) {
			critMap.addEqual("id", id);
		}
		result = merchantsAuthorityDaoImpl.findByCritMap(critMap);
		if (result != null && result.size() > 0) {
			merchantsAuthority = result.get(0);
		}
		return merchantsAuthority;
	}
	
	/**
	 * 
	 * 根据Id查询商家权限对象
	 */
	public List<MerchantsAuthority> getMerchantsAuthorityByPid(String pid) {
		CritMap critMap = new CritMap();
		critMap.addEqual("parentId", pid);
		return merchantsAuthorityDaoImpl.findByCritMap(critMap);
	}

	/**
	 * 
	 * 修改商家资源对象
	 */
	@Transactional
	public boolean updateMerchantsAuthority(MerchantsAuthority merchantsAuthority) {
		boolean bool = false;
		try {
			merchantsAuthorityDaoImpl.merge(merchantsAuthority);
			bool = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("修改商家资源对象失败!", e);
			bool = false;
		}
		return bool;
	}

	/**
	 * 
	 * 添加商家资源对象
	 */
	@Transactional
	public boolean addMerchantsAuthority(MerchantsAuthority merchantsAuthority) {
		boolean bool = false;
		try {
			merchantsAuthority.setId(null);
			merchantsAuthority.setCreateTime(formDate());
			merchantsAuthorityDaoImpl.save(merchantsAuthority);
			bool = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("添加商家资源对象失败!", e);
			bool = false;
		}
		return bool;
	}

	/**
	 * 根据用户Id 删除所具备的权限
	 * 
	 * @author wang.m
	 * @Date 2012-03-27
	 */
	private void deleteMerchantsAuthorityByPramas(String userId) {
		try {
			String sql = "delete from tbl_merchant_user_role where user_id=? ";
			Object[] obj = new Object[1];
			obj[0] = userId;
			sqlService.updateObject(sql, obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("根据用户Id 删除所具备的权限失败!", e);
			e.printStackTrace();
		}
	}

	/**
	 * 查询商家帐号列表
	 */
	public PageFinder<Map<String, Object>> queryMerchantsUser(Query query, MerchantUser merchantUser) {
		String sql = "SELECT t1.id,t1.merchant_code,t2.supplier AS merchant_name,t1.user_name,t1.login_name," +
				"t1.mobile_code,t1.creater,t1.create_time,t1.status,t1.remark,t1.is_yougou_admin" +
				" FROM tbl_merchant_user t1 LEFT JOIN tbl_sp_supplier t2 ON t1.merchant_code = t2.supplier_code" +
				" WHERE t1.delete_flag=1 ";

		// 拼接查询条件
		if (merchantUser != null) {
			if (StringUtils.isNotBlank(merchantUser.getUserName())) {
				sql += " AND t1.user_name LIKE '" + merchantUser.getUserName().trim() + "%'";
			}
			if (StringUtils.isNotBlank(merchantUser.getLoginName())) {// 登录名称
				sql += " AND t1.login_name LIKE '" + merchantUser.getLoginName().trim() + "%'";
			}
			if (StringUtils.isNotBlank(merchantUser.getMerchantCode())) {// 商家编号
				sql += " AND t1.merchant_code LIKE '" + merchantUser.getMerchantCode().trim() + "%'";
			}
			if (NumberUtils.INTEGER_ONE.equals(merchantUser.getIsYougouAdmin())) {
				sql += " AND t1.is_yougou_admin = " + merchantUser.getIsYougouAdmin();
			} else {
				sql += " AND (t1.is_yougou_admin = 0 OR t1.is_yougou_admin is null)";
			}
		}
		sql += " ORDER BY t1.create_time DESC";
		PageFinder<Map<String, Object>> pageFinder = sqlService.getObjectsBySql(sql, query, null, null, null);
		return pageFinder;
	}

	/**
	 * 查询商家角色列表
	 * 
	 * @author wang.m
	 */
	public PageFinder<Map<String, Object>> queryMerchantsRole(Query query, MerchantsRole merchantsRole,String authorityName) {
		String sql = "SELECT t1.id,t1.role_name,t1.create_time,t1.remark,t1.operator,t1.status FROM tbl_merchant_role t1 where 1=1 ";

		// 拼接查询条件
		if (merchantsRole != null) {
			if (StringUtils.isNotBlank(merchantsRole.getRoleName())) {// 角色名称
				sql += " and t1.role_name like '" + merchantsRole.getRoleName().trim() + "%' ";
			}
		}
		
		if(authorityName!=null&&!"".equals(authorityName.trim())){
			sql += " and t1.id in (select ra.role_id from tbl_merchant_role_authority ra left join tbl_merchant_authority a on ra.authority_id=a.id where a.authrity_name like '%"+authorityName+"%')";
		}
		sql += " ORDER BY t1.create_time desc";
		PageFinder<Map<String, Object>> pageFinder = sqlService.getObjectsBySql(sql, query, null, null, null);
		return pageFinder;
	}
	
	/**
	 * 查询商家角色列表
	 * 
	 * @author wang.m
	 */
	public List<MerchantsRole> queryAllMerchantsRole() {
		return merchantsRoleDaoImpl.findBy("status", "1", false);
	}
	
	/**
	 * 查询商家角色列表
	 * 
	 * @author wang.m
	 */
	public List<UserRole> queryUserRole(String user_id) {
		return userAuthorityDao.findBy("userId", user_id, false);
	}
	
	/**
	 * 查询角色对应商家列表
	 * 
	 * @author wang.m
	 */
	public List<UserRole> queryUserRoleByRole(String id) {
		return userAuthorityDao.findBy("roleId", id, false);
	}
	
	@Transactional
	public void saveUserRole(String userId,String[] role,String merchantCode,String opertor) throws Exception {
		List<UserRole> userRoles=userAuthorityDao.findBy("userId", userId, false);
		//删除
		for(UserRole userRole:userRoles){
			boolean exist=false;
			if(role!=null){
				for(String r:role){
					if(userRole.getRoleId().equals(r)){
						exist=true;
						break;
					}
				}
			}
			if(!exist){
				userAuthorityDao.removeById(userRole.getId());
			}
		}
		//添加
		CritMap critMap = null;
		UserRole userRole=null;
		MerchantsRole merchantsRole=null;
		String roleName="";
		if(role!=null){
			for(String r:role){
				critMap = new CritMap();
				critMap.addEqual("userId", userId);
				critMap.addEqual("roleId", r);
				List<UserRole> userRole2=userAuthorityDao.findByCritMap(critMap, false);
				if(userRole2==null||userRole2.size()==0){
					userRole=new UserRole();
					userRole.setRoleId(r);
					userRole.setUserId(userId);
					userRole.setCreateDate(new Date());
					this.addUserRole(userRole);
					merchantsRole = this.initialMerchantsRole(r);
					roleName=roleName+"["+merchantsRole.getRoleName()+"] ";
				}
			}
		}
		/** 添加商家资料日志 Modifier by yang.mq **/
		MerchantOperationLog operationLog = new MerchantOperationLog();
		operationLog.setMerchantCode(merchantCode);
		operationLog.setOperator(opertor);
		operationLog.setOperated(new Date());
		operationLog.setOperationType(OperationType.ACCOUNT);
		operationLog.setOperationNotes("更新商家权限组：权限组("+roleName+")");
		merchantOperationLogService.saveMerchantOperationLog(operationLog);
	}
	
	@Transactional
	public void addUserRole(UserRole userRole) throws Exception{
		userAuthorityDao.save(userRole);
	}

	/**
	 * 增加商家角色
	 * 
	 * @author wang.m
	 * @Date 2012-03-27
	 * 
	 */
	@Transactional
	public boolean addMerchantsRole(MerchantsRole merchantsRole,String[] authritys) {
		boolean bool = false;
		try {
			String roleid=(String)merchantsRoleDaoImpl.save(merchantsRole);
			if(authritys!=null&&authritys.length>0){
				for(String authrity:authritys){
					this.addRoleAuthority(roleid, authrity);
				}
			}
			bool = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("增加商家角色失败!", e);
			bool = false;
		}
		return bool;
	}

	/**
	 * 修改商家角色
	 * 
	 * @author wang.m
	 * @Date 2012-03-27
	 * 
	 */
	@Transactional
	public boolean update_merchants_role(MerchantsRole merchantsRole,String[] authritys) {
		boolean bool = false;
		try {
			merchantsRoleDaoImpl.merge(merchantsRole);
			
			List<RoleAuthority> roleAuthorityList=this.findRoleAuthoriryList(merchantsRole.getId());
			//先删除取消的项
			for(RoleAuthority roleAuthority:roleAuthorityList){
				boolean exist=false;
				for(String authrity:authritys){
					if(roleAuthority.getAuthorityId().equals(authrity)){
						exist=true;
						break;
					}
				}
				if(!exist){
					roleAuthorityDaoImpl.removeById(roleAuthority.getId());
				}
			}
			
			for(String authrity:authritys){
				int count=findRoleAuthoriryExits(merchantsRole.getId(),authrity);
				if(count==0){
					this.addRoleAuthority(merchantsRole.getId(), authrity);
				}
			}
			bool = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("修改商家角色失败!", e);
			bool = false;
		}
		return bool;
	}

	/**
	 * 初始化角色修改页面数据
	 * 
	 * @author wang.m
	 * @Date 2012-03-27
	 * 
	 */
	public MerchantsRole initialMerchantsRole(String rid) {
		MerchantsRole merchantsRole = null;
		CritMap critMap = new CritMap();
		critMap.addEqual("id", rid);
		List<MerchantsRole> merchantsRoleList = merchantsRoleDaoImpl.findByCritMap(critMap);
		if (merchantsRoleList != null && merchantsRoleList.size() > 0) {
			merchantsRole = merchantsRoleList.get(0);
		}
		return merchantsRole;
	}

	/**
	 * 查看该角色已经用户的资源
	 * 
	 * @throws SQLException
	 */
	public List<Map<String, Object>> getRoleAuthorityList(String rid) throws SQLException {
		String sql = "SELECT t2.authrity_name FROM tbl_merchant_role_authority t1 INNER JOIN tbl_merchant_authority t2 ON t1.authority_id=t2.id  WHERE authrity_module<>0 AND t1.role_id='"
				+ rid + "'";
		List<Map<String, Object>> maps = getMapBysql(sql, null);
		return maps;
	}

	/**
	 * 组装sql语句查询
	 * 
	 * @author wang.m
	 * @throws SQLException
	 */
	private List<Map<String, Object>> getMapBysql(String sql, Object[] param) throws SQLException {
		List<Map<String, Object>> maps = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			if (null != param && param.length > 0) {
				for (int i = 0; i < param.length; i++) {
					pstmt.setObject(i + 1, param[i]);
				}
			}
			rs = pstmt.executeQuery();
			Map<String, Object> map = null;
			maps = new ArrayList<Map<String, Object>>();
			while (rs.next()) {
				map = new HashMap<String, Object>();
				ResultSetMetaData rsmd = rs.getMetaData();
				for (int i = 0; i < rsmd.getColumnCount();) {
					++i;
					String key = rsmd.getColumnLabel(i).toLowerCase();
					if (map.containsKey(key)) {
						throw new IllegalArgumentException("有两个相同key值  为：" + key);
					}
					map.put(key, rs.getObject(i));
				}
				maps.add(map);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("组装sql语句查询失败!", e);
		} finally {
			close(conn, pstmt, rs);
		}
		return maps;
	}
	
	/**
	 * 给角色分配资源
	 * 
	 * @author wang.m
	 * 
	 */
	@Transactional
	public boolean addRoleAuthority(String roleId, String authorityId) {
		boolean bool = false;
		if (StringUtils.isNotBlank(roleId) && StringUtils.isNotBlank(authorityId)) {
			String[] aid = authorityId.split(";");
			if (aid.length > 0) {
				for (String str : aid) {
					try {
						// 判断是否已经存在
						Integer counts = findRoleAuthoriryExits(roleId, str);
						if (counts < 1) {
							RoleAuthority roleAuthoriry = new RoleAuthority();
							roleAuthoriry.setAuthorityId(str);
							roleAuthoriry.setRoleId(roleId);
							roleAuthoriry.setCreateDate(new Date());
							roleAuthoriry.setRemark("");
							roleAuthorityDaoImpl.save(roleAuthoriry);
							bool = true;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						logger.error("给角色分配资源失败!", e);
						bool = false;
					}
				}
			}
		}
		return bool;
	}

	/**
	 * 判断该角色是否已经用户该资源
	 * 
	 */
	public Integer findRoleAuthoriryExits(String roleId, String authorityId) {
		CritMap critMap = new CritMap();
		critMap.addEqual("roleId", roleId);
		critMap.addEqual("authorityId", authorityId);
		List<RoleAuthority> roleAuthorityList = roleAuthorityDaoImpl.findByCritMap(critMap);
		return roleAuthorityList.size();
	}
	
	/**
	 * 判断该角色是否已经用户该资源
	 * 
	 */
	public List<RoleAuthority> findRoleAuthoriryList(String roleId) {
		CritMap critMap = new CritMap();
		critMap.addEqual("roleId", roleId);
		List<RoleAuthority> roleAuthorityList = roleAuthorityDaoImpl.findByCritMap(critMap);
		return roleAuthorityList;
	}

	/**
	 * 判断商家名称是否已经存在
	 * 
	 * @author wang.m
	 * @throws Exception 
	 * @Date 2012-03-29
	 */
	public Integer existMerchantSupplieName(String supplieName) {
		Integer counts = 0;		
		try {
			SupplierVo vo = supplierService.getSupplierByName(supplieName.trim());
			if (null != vo && "招商供应商".equals(vo.getSupplierType())) {
				counts = 1;
			}else if(null !=vo && "普通供应商".equals(vo.getSupplierType())){
				counts = 2;
			}else if(null !=vo && "韩货供应商".equals(vo.getSupplierType())){//供应商类型增加一种
				counts = 3;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return counts;
	}

	public void update_roleStatus(String id) throws Exception {
		MerchantsRole merchantsRole=merchantsRoleDaoImpl.findUniqueBy("id", id, false);
		if(merchantsRole.getStatus()==null||"".equals(merchantsRole.getStatus())||"0".equals(merchantsRole.getStatus())){
			merchantsRole.setStatus("1");
		}else{
			merchantsRole.setStatus("0");
		}
		String sql = "update tbl_merchant_role set status = ? where id=? ";
		Object[] obj = new Object[2];
		obj[0] = merchantsRole.getStatus();
		obj[1] = id;
		sqlService.updateObject(sql, obj);
	}
	/**
	 * 删除角色
	 * 
	 * @author wang.m
	 */
	public boolean delete_role(String id) {
		boolean bool = false;
		try {
			
			String sql0 = "select count(1) from tbl_merchant_user_role where role_id=? ";
			List<Object> obj0 = new ArrayList<Object>();
			obj0.add(id);
			Long count=sqlService.getCountBySql(sql0, null, obj0);
			if(count==0){
				String sql1 = "delete from tbl_merchant_role_authority where role_id=? ";
				Object[] obj1 = new Object[1];
				obj1[0] = id;
				sqlService.updateObject(sql1, obj1);
				
				String sql2 = "delete from tbl_merchant_role where id=? ";
				Object[] obj2 = new Object[1];
				obj2[0] = id;
				sqlService.updateObject(sql2, obj2);
				bool = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("删除角色失败!", e);
			bool = false;
		}
		return bool;
	}

	/**
	 * 删除资源
	 * 
	 * @author wang.m
	 */
	public boolean delete_authority(String id) {
		boolean bool = false;
		try {
			Object[] objects = { id };
			if("0".equals(id)){
				logger.error("根节点不允许删除");
				return false;
			}
			// 删除菜单授权
			sqlService.updateObject("delete from tbl_merchant_user_authority where authority_id = ?", objects);
			// 删除权限组授权
			sqlService.updateObject("delete from tbl_merchant_role_authority where authority_id = ?", objects);
			// 删除菜单
			sqlService.updateObject("delete from tbl_merchant_authority where id = ?", objects);
			bool = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("删除资源失败!", e);
			bool = false;
		}
		return bool;
	}

	/**
	 * 根据商家编号查询商家登录信息
	 */
	public MerchantUser getMerchantsBySuppliceCode(String supplierCode) {
		MerchantUser merchantUser = null;
		CritMap critMap = new CritMap();
		critMap.addEqual("merchantCode", supplierCode);
		critMap.addEqual("deleteFlag", 1);// 删除标志 1未删除
		critMap.addEqual("isAdministrator", 1);// 为超级管理员
		List<MerchantUser> merchantUserList = merchantUserDaoImpl.findByCritMap(critMap);
		if (merchantUserList != null && merchantUserList.size() > 0) {
			merchantUser = merchantUserList.get(0);
		}
		return merchantUser;
	}

	/**
	 * 
	 * 根据供应商Id查询供应商名称
	 * 
	 * @author wang.m
	 * @DATE 2012-03-31
	 */
	public String getSupplerNameById(String supplierId) {
		String supplierName = "";
		SupplierVo supplierSp = null;
		
		SupplierVo vo = new SupplierVo();
		vo.setId(supplierId);
		List<SupplierVo> list;
		try {
			list = supplierService.querySupplierByVo(vo);
		} catch (Exception e) {
			logger.error("通过供应商Id查询供应商名称", e);
			return supplierName;
		}
		if (CollectionUtils.isNotEmpty(list)) {
			supplierSp = list.get(0);
			if (null != supplierSp) {
				supplierName = supplierSp.getSupplier();
			}
		}
		return supplierName;
	}

	/**
	 * 修改商家登录密码
	 * 
	 * @author wang.m
	 * @DATE 2012-04-06
	 * 
	 */
	@Transactional
	public boolean updatePassword(MerchantUser merchantUser, SystemmgtUser systemmgtUser) {
		boolean bool = false;
		try {
			// 对密码进行MD5加密
			String password = Md5Encrypt.md5(merchantUser.getPassword());
			
			MerchantUser merchantUserInfo = merchantUserDaoImpl.getById(merchantUser.getId());
			
			String sql = "update tbl_merchant_user set password=? where id=? and delete_flag=1";
			Object[] obj = new Object[2];
			obj[0] = password;
			obj[1] = merchantUser.getId();
			sqlService.updateObject(sql, obj);
			bool = true;
			
			/** 添加商家资料日志 Modifier by yang.mq **/
			MerchantOperationLog operationLog = new MerchantOperationLog();
			operationLog.setMerchantCode(merchantUserInfo.getMerchantCode());
			operationLog.setOperator(systemmgtUser.getUsername());
			operationLog.setOperated(new Date());
			operationLog.setOperationType(OperationType.ACCOUNT);
			operationLog.setOperationNotes("修改商家帐号密码");
			merchantOperationLogService.saveMerchantOperationLog(operationLog);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("修改商家登录密码失败!", e);
			bool = false;
		}
		return bool;
	}

	/**
	 * 
	 * 根据商家帐号Id查询商家已拥有的资源列表
	 * 
	 * @throws SQLException
	 */
	public List<Map<String, Object>> getMerchantsAuthorityByUserId(String userId) throws SQLException {
		String sql = "SELECT t1.id,t1.authrity_name FROM tbl_merchant_authority t1 INNER JOIN tbl_merchant_user_authority t2 ON t1.id=t2.authority_id WHERE t1.authrity_module<>0 AND t2.user_id='"
				+ userId + "'";
		List<Map<String, Object>> maps = getMapBysql(sql, null);
		return maps;
	}

	/**
	 * 分配商家帐号权限
	 * 
	 * @author wang.m
	 * @Date 2012-03-26
	 */
	@Transactional
	public boolean addUserAuthority(String userid, String authority, SystemmgtUser systemmgtUser) throws Exception {
		boolean bool = false;
		try {
			if (StringUtils.isNotBlank(userid)) {
				String diffSql = "select group_concat(t2.authrity_name separator '、') from tbl_merchant_user_authority t1 inner join tbl_merchant_authority t2 on(t1.authority_id = t2.id) where t1.user_id = ?";
				
				Object ownPermissions = JDBCUtils.getInstance().uniqueResult(diffSql, new Object[] { userid });
				
				// 先删除这个用户所具备的资源
				deleteUserAuthorityByPramas(userid);
				if (StringUtils.isNotBlank(authority)) {
					// 添加
					String sql = "insert into tbl_merchant_user_authority(id,user_id,authority_id,create_date,remark) values (?,?,?,?,?)";
					String[] strAdd = authority.split(";");
					if (strAdd.length > 0) {
						for (String string : strAdd) {
							Object[] obj = new Object[5];
							// 获取uuid
							String uuid = UUIDGenerator.getUUID();
							obj[0] = uuid;
							obj[1] = userid;
							obj[2] = string;
							obj[3] = formDate();
							obj[4] = "";
							sqlService.updateObject(sql, obj);
							bool = true;
						}
						// 如子帐户帐限列表中包含超出父帐户的权限，则回收子帐户超出部分的权限
						sql = "select t3.id, t3.authority_id from tbl_merchant_user t1 inner join tbl_merchant_user t2 on(t1.merchant_code = t2.merchant_code) inner join tbl_merchant_user_authority t3 on(t2.id = t3.user_id) where t1.id = ? and t2.is_administrator = ?";
						List<Map<String, Object>> resultMaps = JDBCUtils.getInstance().listResultMap(sql, new Object[] { userid, NumberUtils.INTEGER_ZERO });
						List<Object[]> sqlParams = new ArrayList<Object[]>();
						for (Map<String, Object> resultMap : resultMaps) {
							if (!ArrayUtils.contains(strAdd, MapUtils.getString(resultMap, "authority_id"))) {
								sqlParams.add(new Object[] { MapUtils.getString(resultMap, "id") });
							}
						}
						if (CollectionUtils.isNotEmpty(sqlParams)) {
							JDBCUtils.getInstance().executeBatch(new JDBCUtils.SQLBatch("delete from tbl_merchant_user_authority where id = ?", sqlParams));
						}
					}
				}
				Object finalPermissions = JDBCUtils.getInstance().uniqueResult(diffSql, new Object[] { userid });
				if (!ObjectUtils.equals(ownPermissions, finalPermissions)) {
					MerchantUser merchantUser = merchantUserDaoImpl.getById(userid);
					/** 添加商家帐户日志 Modifier by yang.mq **/
					MerchantOperationLog operationLog = new MerchantOperationLog();
					operationLog.setMerchantCode(merchantUser.getMerchantCode());
					if(systemmgtUser!=null){
						operationLog.setOperator(systemmgtUser.getUsername());
					}else{
						operationLog.setOperator("system自动");
					}
					operationLog.setOperated(new Date());
					operationLog.setOperationType(OperationType.ACCOUNT);
					operationLog.setOperationNotes(MessageFormat.format("分配权限列表由【{0}】修改为【{1}】", ownPermissions, finalPermissions));
					merchantOperationLogService.saveMerchantOperationLog(operationLog);
				}
				bool = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("分配商家帐号权限失败!", e);
			bool = false;
		}
		return bool;
	}

	/**
	 * 根据用户Id 删除所具备的权限
	 * 
	 * @author wang.m
	 * @Date 2012-03-27
	 */
	private void deleteUserAuthorityByPramas(String userId) {
		try {
			String sql = "delete from tbl_merchant_user_authority where user_id=? ";
			Object[] obj = new Object[1];
			obj[0] = userId;
			sqlService.updateObject(sql, obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("根据用户Id 删除所具备的权限失败!", e);
			e.printStackTrace();
		}
	}

	/**
	 * 判断合同编号是否存在
	 * 
	 * @param contractNo
	 *            合同编号
	 */
	public boolean exits_contractNo(String contractNo, String supplierSpId) {
		boolean bool = false;
		CritMap critMap = new CritMap();
		critMap.addEqual("contractNo", contractNo);
		critMap.addFech("supplier", "supplier");
		critMap.addEqual("supplier.id", supplierSpId);
		List<SupplierContract> supplierSpList = supplierContractDaoImpl.findByCritMap(critMap);
		if (supplierSpList != null && supplierSpList.size() > 0) {
			bool = true;
		}
		return bool;
	}

	/**
	 * 
	 * 根据供应商Id查询商家超级管理员账户Id
	 * 
	 * @throws SQLException
	 */
	public String getMerchantUserBySupplierId(String id) throws SQLException {
		String userId = "";
		String sql = "SELECT t1.id FROM tbl_merchant_user t1 INNER JOIN tbl_sp_supplier t2 ON t1.merchant_code=t2.supplier_code WHERE t2.id='" + id
				+ "' AND t1.is_administrator=1 and t1.delete_flag=1 and t2.delete_flag=1";
		List<Map<String, Object>> maps = getMapBysql(sql, null);
		if (maps != null && maps.size() > 0) {
			for (Map<String, Object> map : maps) {
				userId = map.get("id").toString();
			}
		}
		return userId;
	}
	
    /**
     * 
     * 根据商家账户Id查询
     */
	public MerchantUser getMerchantUserById(String merchantUserId) throws SQLException {
		return merchantUserDaoImpl.getById(merchantUserId);
	}

    /**
     * 
     * 保存商家账户
     * @throws Exception 
     */
	@Transactional
	public void saveMerchantUser(MerchantUser merchantUser) throws Exception {
		merchantUserDaoImpl.save(merchantUser);
	}

	/**
	 * 
	 * 在启用的情况下 修改商家品牌和分类信息合同添加商家帐号信息(历史商家信息)
	 * 
	 * @Date 2012-03-07
	 * @author wang.m
	 */
	@Deprecated
	@Transactional
	public boolean update_historyMerchants(SupplierSp supplierSp, HttpServletRequest req, String bankNameHidden, String catNameHidden, String brandList, String catList) {
		boolean bool = false;
		try {
			if (supplierSp != null) {
				// 修改供应商更新时间和更新人
				bool = updateSupplierById(supplierSp.getId(), req);

				// 商家用户信息
				MerchantUser merchantUser = new MerchantUser();
				merchantUser.setLoginName(supplierSp.getLoginAccount());// 登录名称
				// 对密码进行MD5加密
				String password = Md5Encrypt.md5(supplierSp.getLoginPassword());
				merchantUser.setPassword(password);// 登录密码
				merchantUser.setMerchantCode(supplierSp.getSupplierCode());// 商家编号
				merchantUser.setUserName("");// 商家真实信息
				merchantUser.setCreateTime(formDate());
				merchantUser.setStatus(1);// 状态 1表示可用
				merchantUser.setIsAdministrator(1); // 1表示管理员
				merchantUser.setDeleteFlag(1);// 删除标志 1可用
				merchantUserDaoImpl.save(merchantUser);
				
				String username = GetSessionUtil.getSystemUser(req).getUsername();
				
				/** 添加商家帐户日志 Modifier by yang.mq **/
				MerchantOperationLog operationLog = new MerchantOperationLog();
				operationLog.setMerchantCode(supplierSp.getSupplierCode());
				operationLog.setOperator(username);
				operationLog.setOperated(new Date());
				operationLog.setOperationType(OperationType.ACCOUNT);
				operationLog.setOperationNotes(merchantOperationLogService.buildMerchantAccountOperationNotes(null, merchantUser));
				merchantOperationLogService.saveMerchantOperationLog(operationLog);

				// 根据商家Id删除商家品牌和分类权限设置
				deleteMerchantBankAndCat(supplierSp.getId());
				// 添加商家品牌和分类权限设置
				addMerchantBankAndCat(supplierSp, req, bankNameHidden, catNameHidden, brandList, catList, "2");
				
				// 查询修改前的内容
				SupplierSp supplierInfo = getSupplierSpById(supplierSp.getId());
				// 获取品牌修改后数据
				String[] brandInfos = getSpLimitBrandBysupplierId(supplierSp.getId());
				// 获取分类修改后数据
				//String[] catInfos = getSpLimitCatBysupplierId(supplierSp.getId());
				String[] catInfos = getSpLimitBrandCatBysupplierId(supplierSp.getId());
				
				StringBuilder operationNotes = new StringBuilder();
				if (ArrayUtils.isNotEmpty(brandInfos)) {
					if (!StringUtils.equals(brandInfos[0], brandList)) {
						operationNotes.append(MessageFormat.format("将“商品品牌”由【{0}】修改为【{1}】", brandList, brandInfos[0]));
					}
				}
				if (ArrayUtils.isNotEmpty(catInfos)) {
					if (!StringUtils.equals(catInfos[0], catList)) {
						operationNotes.append(MessageFormat.format("将“商品分类”由【{0}】修改为【{1}】", catList, catInfos[0]));
					}
				}
				if (operationNotes.length() > 0) {
					/** 添加商家资料日志 Modifier by yang.mq **/
					operationLog = new MerchantOperationLog();
					operationLog.setMerchantCode(supplierInfo.getSupplierCode());
					operationLog.setOperator(GetSessionUtil.getSystemUser(req).getUsername());
					operationLog.setOperated(new Date());
					operationLog.setOperationType(OperationType.BASIC_DATA);
					operationLog.setOperationNotes(operationNotes.toString());
					merchantOperationLogService.saveMerchantOperationLog(operationLog);
				}
				
				bool = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("修改商家品牌和分类信息合同添加商家帐号信息(历史商家信息)!", e);
			bool = false;
		}
		return bool;
	}

	/**
	 * 
	 * 在启用的情况下 修改商家品牌和分类信息合同添加商家帐号信息(历史商家信息)
	 * 
	 * @Date 2012-03-07
	 * @author wang.m
	 */
	@Deprecated
	@Transactional
	public boolean update_merchantsBankAndCat(SupplierSp supplierSp, HttpServletRequest req, String bankNameHidden, String catNameHidden, String brandList, String catList) {
		boolean bool = false;
		try {
			if (supplierSp != null) {
				// 修改供应商更新时间和更新人
				bool = updateSupplierById(supplierSp.getId(), req);
				// 根据商家Id删除商家品牌和分类权限设置
				deleteMerchantBankAndCat(supplierSp.getId());
				// 添加商家品牌和分类权限设置
				addMerchantBankAndCat(supplierSp, req, bankNameHidden, catNameHidden, brandList, catList, "2");
				
				// 查询修改前的内容
				SupplierSp supplierInfo = getSupplierSpById(supplierSp.getId());
				// 获取品牌修改后数据
				String[] brandInfos = getSpLimitBrandBysupplierId(supplierSp.getId());
				// 获取分类修改后数据
				//String[] catInfos = getSpLimitCatBysupplierId(supplierSp.getId());
				String[] catInfos = this.getSpLimitBrandCatBysupplierId(supplierSp.getId());
				
				StringBuilder operationNotes = new StringBuilder();
				if (ArrayUtils.isNotEmpty(brandInfos)) {
					if (!StringUtils.equals(brandInfos[0], brandList)) {
						operationNotes.append(MessageFormat.format("将“商品品牌”由【{0}】修改为【{1}】", brandList, brandInfos[0]));
					}
				}
				if (ArrayUtils.isNotEmpty(catInfos)) {
					if (!StringUtils.equals(catInfos[0], catList)) {
						operationNotes.append(MessageFormat.format("将“商品分类”由【{0}】修改为【{1}】", catList, catInfos[0]));
					}
				}
				if (operationNotes.length() > 0) {
					/** 添加商家资料日志 Modifier by yang.mq **/
					MerchantOperationLog operationLog = new MerchantOperationLog();
					operationLog.setMerchantCode(supplierInfo.getSupplierCode());
					operationLog.setOperator(GetSessionUtil.getSystemUser(req).getUsername());
					operationLog.setOperated(new Date());
					operationLog.setOperationType(OperationType.BASIC_DATA);
					operationLog.setOperationNotes(operationNotes.toString());
					merchantOperationLogService.saveMerchantOperationLog(operationLog);
				}
				
				bool = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("修改商家品牌和分类信息合同添加商家帐号信息(历史商家信息)!", e);
			bool = false;
		}
		return bool;
	}

	/**
	 * 根据商家Id删除商家品牌和分类权限设置 id 商家主键ID
	 */
	private void deleteMerchantBankAndCat(String id) {
		try {
			//判断是否存在品牌和分类关系，有就删除
			List<Map<String, Object>> spLimitBrandCatMaps = sqlService.getDatasBySql("SELECT bc.id FROM tbl_sp_limit_cat c INNER JOIN tbl_sp_limit_brand_cat bc ON c.id = bc.cat_id INNER JOIN tbl_sp_limit_brand b ON bc.brand_id = b.id WHERE c.supply_id = '" + id + "'");
			if (CollectionUtils.isNotEmpty(spLimitBrandCatMaps)) {
				for (Map<String, Object> map : spLimitBrandCatMaps) {
					String brand_cat_id = MapUtils.getString(map, "id");
					update_SpLimitBrand_Cat(brand_cat_id);
				}
			}
			
			// 判断是否之前选项了品牌 如果选择了就删除掉
			if (StringUtils.isNotBlank(id)) {
				List<SpLimitBrand> brank = this.getBrankSupplier(id);
				if (brank != null && brank.size() > 0) {
					for (SpLimitBrand spLimitBrand : brank) {
						update_SpLimitBrand(spLimitBrand.getId());
					}
				}
			}

			// 判断是否之前选项了分类 如果选择了就删除掉
			if (StringUtils.isNotBlank(id)) {
				List<SpLimitCat> spCat = this.getCatSupplier(id);
				if (spCat != null && spCat.size() > 0) {
					for (SpLimitCat spLimitCat : spCat) {
						update_SpLimitCat(spLimitCat.getId());
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("根据商家Id删除商家品牌和分类权限失败!", e);
		}
	}

	/**
	 * 根据商家Id添加商家品牌和分类权限设置 id 商家主键ID
	 * 
	 * @flag 1表示添加 2 表示修改
	 */
	@Transactional
	private void addMerchantBankAndCat(SupplierSp supplierSp, HttpServletRequest req, String bankNoHidden, String catNameHidden, String brandList, String catList, String flag) {
		try {
			// 修改才调用
			if (StringUtils.isNotBlank(flag) && "2".equals(flag)) {
				SystemmgtUser user = GetSessionUtil.getSystemUser(req);
				String loginUser = "";
				if (user != null) {
					loginUser = user.getUsername();
				}
				// 获取品牌修改前数据
				String[] str = getSpLimitBrandBysupplierId(supplierSp.getId());
				// 获取分类修改前数据
				//String[] str1 = getSpLimitCatBysupplierId(supplierSp.getId());
				String[] str1 = this.getSpLimitBrandCatBysupplierId(supplierSp.getId());
				
				// 查询修改前供应商信息
				SupplierSp supplierInfo = getSupplierSpById(supplierSp.getId());
				if (supplierInfo != null) {
					if (str != null && str.length > 0) {
						String brandStr = str[0];
						if (StringUtils.isNotBlank(brandStr)) {
							// 品牌
							if (!brandStr.trim().equals(brandList.trim())) {
								addMerhcantlog(supplierSp.getId(), "修改招商供应商品牌权限信息", "品牌权限", brandStr, brandList, loginUser);
							}
						}
					}
				}
				if (str1 != null && str1.length > 0) {
					String catStr = str1[0];
					if (StringUtils.isNotBlank(catStr)) {
						// 分类
						if (!catStr.trim().equals(catList.trim())) {
							addMerhcantlog(supplierSp.getId(), "修改招商供应商分类权限信息", "分类权限", catStr, catList, loginUser);
						}
					}
				}
			}
			//brand_no, key=uuid 为了保持品牌和分类关系
			Map<String, String> brand_uuid_key = new HashMap<String, String>();
			Map<String, String> cat_uuid_key = new HashMap<String, String>();
			// 判断是否选择了品牌
			if (StringUtils.isNotBlank(bankNoHidden)) {
				String[] brandNos = bankNoHidden.split(";");
				for (String brandNo : brandNos) {
					if (StringUtils.isNotBlank(brandNo)) {
						brand_uuid_key.put(brandNo, UUIDUtil.getUUID());
						sqlService.updateObject("insert into tbl_sp_limit_brand(id, brand_no, supply_id) values(?, ?, ?)", new Object[] { brand_uuid_key.get(brandNo), brandNo, supplierSp.getId() });
					}
				}
			}
			/*
			 * 分类字符串 [brand_no;struct_name]
			 * 需要保存分类-品牌关系
			 */
			if (StringUtils.isNotBlank(catNameHidden)) {
				//[struct_name;brand_no]
				Set<String> cat_brand_list = new HashSet<String>();
				Set<String> struct_name_list = new HashSet<String>();
				String[] catNameStr = catNameHidden.split("_");
				
				for (String string : catNameStr) {
					String[] catStr = string.split(";");
					List<Category> temp_cats = null;
					if (catStr.length >= 2) {
						temp_cats = commodityBaseApiService.getCategoryListLikeStructName(catStr[1], (short)1, (short)3);
					}
					if (CollectionUtils.isNotEmpty(temp_cats)) {
						for (Category category : temp_cats) {
							cat_brand_list.add(category.getStructName() + ";" + catStr[0]);
							struct_name_list.add(category.getStructName());//StructName去重复
						}
					}
				}
				if (CollectionUtils.isNotEmpty(struct_name_list)) {
					for (String struct_name : struct_name_list) {
						Category _c = commodityBaseApiService.getCategoryByStructName(struct_name);
						if (null == _c) continue;
						
						cat_uuid_key.put(struct_name, UUIDUtil.getUUID());
						sqlService.updateObject("insert into tbl_sp_limit_cat(id, cat_no, supply_id, struct_name) values(?, ?, ?, ?)", new Object[] { cat_uuid_key.get(struct_name), _c.getCatNo(), supplierSp.getId(), _c.getStructName() });
					}
				}
				//存储品牌分类关系（招商分类和品牌存储的tbl_sp_limit_brand和tbl_sp_limit_cat的id）
				for (String string : cat_brand_list) {
					if (StringUtils.isNotBlank(string)) {
						String[] a = string.split(";");
						sqlService.updateObject("insert into tbl_sp_limit_brand_cat(id, brand_id, cat_id) values(?, ?, ?)", new Object[] {UUIDUtil.getUUID(), brand_uuid_key.get(a[1]), cat_uuid_key.get(a[0])});
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("根据商家Id添加商家品牌和分类权限失败!", e);
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("根据商家Id添加商家品牌和分类权限失败!", e);
			e.printStackTrace();
		}
	}

	/**
	 * 转换时间格式 字符串形式
	 * 
	 */
	private String formDate() {
		Date da = new Date();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sim.format(da);
		return str;
	}

	/**
	 * 转换时间格式 Date形式
	 * 
	 */
	private Date stringFormDate() {
		Date date = null;
		try {
			Date da = new Date();
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = sim.parse(sim.format(da));
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.error("转换时间格式 Date形式失败!", e);
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 查询财务成本帐套数据集合
	 * 
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public List<CostSetofBooks> getCostSetofBooksList() throws Exception {
		//List<CostSetofBooks> listCostSetOfBooks = costSetOfBooksDao.queryAllCostSetOfBooks();
		return null;
	}

	/**
	 * 跳转到商家发货地址设置页面
	 * 
	 * @author wang.m
	 * @Date 2012-04-28
	 */
	public PageFinder<SupplierSp> getSupplierSpList(SupplierSp supplierSp, Query query) {
		CritMap critMap = new CritMap();
		critMap.addEqual("supplierType", "招商供应商");
		critMap.addEqual("isValid", 1);
		critMap.addEqual("deleteFlag", 1);// 删除标志 1未删除
		PageFinder<SupplierSp> supplierSpList = null;//supplierDaoImpl.pagedByCritMap(critMap, query.getPage(), query.getPageSize());
		//TODO
		return supplierSpList;
	}

	/**
	 * 保存快递单模块信息
	 * 
	 * @author wang.m
	 * @Date 2012-05-02
	 */
	@Transactional
	public boolean saveExpressTemplate(HttpServletRequest req, MerchantExpressTemplate merchantExpressTemplate) {
		boolean bool = false;
		try {
			MerchantExpressTemplate template = new MerchantExpressTemplate();
			if (null != merchantExpressTemplate && StringUtils.isNotBlank(merchantExpressTemplate.getId())) {
				template.setId(merchantExpressTemplate.getId());
			}
			template.setBackGroundImage(merchantExpressTemplate.getBackGroundImage());
			template.setLogisticsId(merchantExpressTemplate.getLogisticsId());
			template.setWidth(merchantExpressTemplate.getWidth());
			template.setHeigth(merchantExpressTemplate.getHeigth());
			template.setCommodityNum(merchantExpressTemplate.getCommodityNum());
			template.setConsigneeAdress(merchantExpressTemplate.getConsigneeAdress());
			template.setConsigneeDay(merchantExpressTemplate.getConsigneeDay());
			template.setConsigneeEmail(merchantExpressTemplate.getConsigneeEmail());
			template.setConsigneeMonth(merchantExpressTemplate.getConsigneeMonth());
			template.setConsigneeName(merchantExpressTemplate.getConsigneeName());
			template.setConsigneeOneArea(merchantExpressTemplate.getConsigneeOneArea());
			template.setConsigneePhone(merchantExpressTemplate.getConsigneePhone());
			template.setConsigneeTell(merchantExpressTemplate.getConsigneeTell());
			template.setConsigneeThreeArea(merchantExpressTemplate.getConsigneeThreeArea());
			template.setConsigneeTwoArea(merchantExpressTemplate.getConsigneeTwoArea());
			template.setConsigneeYear(merchantExpressTemplate.getConsigneeYear());
			template.setExpressName(merchantExpressTemplate.getExpressName());
			template.setMoney(merchantExpressTemplate.getMoney());
			template.setNumber(merchantExpressTemplate.getNumber());
			template.setOrderSourceId(merchantExpressTemplate.getOrderSourceId());
			template.setOrderSubNo(merchantExpressTemplate.getOrderSubNo());
			template.setRemark(merchantExpressTemplate.getRemark());
			template.setShipmentsAdress(merchantExpressTemplate.getShipmentsAdress());
			template.setShipmentsEmail(merchantExpressTemplate.getShipmentsEmail());
			template.setShipmentsName(merchantExpressTemplate.getShipmentsName());
			template.setShipmentsOneArea(merchantExpressTemplate.getShipmentsOneArea());
			template.setShipmentsPhone(merchantExpressTemplate.getShipmentsPhone());
			template.setShipmentsTell(merchantExpressTemplate.getShipmentsTell());
			template.setShipmentsThreeArea(merchantExpressTemplate.getShipmentsThreeArea());
			template.setShipmentsTwoArea(merchantExpressTemplate.getShipmentsTwoArea());
			template.setIsBold(merchantExpressTemplate.getIsBold());
			template.setFontSize(merchantExpressTemplate.getFontSize());
			template.setTbody(merchantExpressTemplate.getTbody());
			merchantExpressTemplateDaoImpl.merge(template);
			bool = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("保存快递单模块信息失败!", e);
			bool = false;
		}
		return bool;
	}

	/**
	 * 根据物流公司Id获取快递单模版信息
	 */
	public MerchantExpressTemplate getExpressTemplateByLogisticsId(String id) {
		MerchantExpressTemplate merchantExpressTemplate = null;
		CritMap critMap = new CritMap();
		if (StringUtils.isNotBlank(id)) {
			critMap.addEqual("logisticsId", id);
			List<MerchantExpressTemplate> templateList = merchantExpressTemplateDaoImpl.findByCritMap(critMap);
			if (templateList != null && templateList.size() > 0) {
				merchantExpressTemplate = templateList.get(0);
			}
		}

		return merchantExpressTemplate;
	}

	/**
	 * 根据订单号查询商家的收货人相关的信息
	 */
	public Map<String, Object> getMerchantConsignmentadress(String supplyid) {
		String sql = " SELECT t1.id,t1.consignment_name,t1.phone,t1.tell,t1.post_code,t1.area, " + " t1.adress FROM tbl_merchant_consignmentadress t1 where t1.supply_id='"
				+ supplyid + "'";
		Map<String, Object> orderMap = sqlService.getDataBySql(sql, null, null);
		return orderMap;
	}

	/**
	 * 查询商家售后退货地址列表
	 * 
	 * @author wang.m
	 * @date 2012-05-11
	 */
	public PageFinder<MerchantRejectedAddress> getMerchantRejectedAddressList(Query query, MerchantRejectedAddress merchantRejectedAddress, String brand) {
		CritMap critMap = new CritMap();
		if (null != merchantRejectedAddress) {
			// 商家名称
			if (StringUtils.isNotBlank(merchantRejectedAddress.getSupplierName())) {
				critMap.addLike("supplierName", merchantRejectedAddress.getSupplierName());
			}
			// 商家编号
			if (StringUtils.isNotBlank(merchantRejectedAddress.getSupplierCode())) {
				critMap.addLike("supplierCode", merchantRejectedAddress.getSupplierCode());
			}
			// 商家人姓名
			if (StringUtils.isNotBlank(merchantRejectedAddress.getConsigneeName())) {
				critMap.addLike("consigneeName", merchantRejectedAddress.getConsigneeName());
			}
			// 商家手机
			if (StringUtils.isNotBlank( merchantRejectedAddress.getConsigneePhone() ) ) {
				critMap.addLike("consigneePhone", merchantRejectedAddress.getConsigneePhone());
			}
			// 商家电话
			if (StringUtils.isNotBlank(merchantRejectedAddress.getConsigneeTell())) {
				critMap.addLike("consigneeTell", merchantRejectedAddress.getConsigneeTell());
			}
			// 商家负责人
			List<String> merchantCodes = new ArrayList<String>();
			if (StringUtils.isNotBlank(merchantRejectedAddress.getSupplierYgContacts())) {
				merchantCodes = supplierYgContactService.getSupplierList(merchantRejectedAddress.getSupplierYgContacts());

			}
			// 品牌
			List<String> merchantCodes_brand = new ArrayList<String>();
			if (StringUtils.isNotBlank(brand)) {
				List<Brand> brandList = commodityBaseApiService.getBrandListLikeBrandName("%" + brand, (short) 1);
				StringBuffer brand_in = new StringBuffer();
				if (CollectionUtils.isNotEmpty(brandList)) {
					for (Brand brandVo : brandList) {
						brand_in.append("\"" + brandVo.getBrandNo() + "\",");
					}
					if (brand_in.length() > 0) {
						brand_in.setLength(brand_in.length() - 1);
					}
					Map<String, String> map = new HashMap<String, String>();
					map.put("brands", brand_in.toString());
					merchantCodes_brand = merchantBrandMapper.queryMerchantByBrands(map);
				}
			}
			// 合并品牌和商家负责任相应的merchantCode的list
			if (CollectionUtils.isNotEmpty(merchantCodes) && CollectionUtils.isNotEmpty(merchantCodes_brand)) {
				merchantCodes.retainAll(merchantCodes_brand);
				if( null!=merchantCodes && merchantCodes.size()>0 ){
					critMap.addIN("supplierCode", merchantCodes.toArray());
				}
			} else if (CollectionUtils.isNotEmpty(merchantCodes)) {
				critMap.addIN("supplierCode", merchantCodes.toArray());
			} else if (CollectionUtils.isNotEmpty(merchantCodes_brand)) {
				critMap.addIN("supplierCode", merchantCodes_brand.toArray());
			}
		
			critMap.addDesc("createrTime");
		}

		PageFinder<MerchantRejectedAddress> pageFinder = merchantRejectedAddressDaoImpl
				.pagedByCritMap(critMap, query.getPage(), query.getPageSize());

		// PageFinder<MerchantRejectedAddress> pageFinder =
		// merchantRejectedAddressDaoImpl.pagedByHQL(hql, toPage, pageSize,
		// values)
		return pageFinder;
	}

	/**
	 * 保存商家售后退货地址数据
	 * 
	 * @author wang.m
	 * @date 2012-05-11
	 */
	@Transactional
	public boolean saveMerchantRejectedAddress(HttpServletRequest req, MerchantRejectedAddress merchantRejectedAddress) {
		boolean bool = false;
		try {
			MerchantRejectedAddress reject = new MerchantRejectedAddress();
			// 判断是修改还是保存
			if (null != merchantRejectedAddress) {
				reject.setConsigneeName(merchantRejectedAddress.getConsigneeName());
				reject.setConsigneePhone(merchantRejectedAddress.getConsigneePhone());
				reject.setConsigneeTell(merchantRejectedAddress.getConsigneeTell());
				SystemmgtUser user = GetSessionUtil.getSystemUser(req);
				String loginUser = "";
				if (user != null) {
					loginUser = user.getUsername();
				}
				reject.setCreaterPerson(loginUser);
				reject.setCreaterTime(formDate());
				reject.setSupplierName(merchantRejectedAddress.getSupplierName());
				reject.setSupplierCode(merchantRejectedAddress.getSupplierCode());
				reject.setWarehousePostcode(merchantRejectedAddress.getWarehousePostcode());
				reject.setWarehouseArea(merchantRejectedAddress.getWarehouseArea());
				reject.setWarehouseAdress(merchantRejectedAddress.getWarehouseAdress());
				
				String operationNotes;
				
				// 如果存在,则修改
				if (StringUtils.isNotBlank(merchantRejectedAddress.getId())) {
					reject.setId(merchantRejectedAddress.getId());
					MerchantRejectedAddress rejectInfo = merchantRejectedAddressDaoImpl.getById(merchantRejectedAddress.getId());
					operationNotes = merchantOperationLogService.buildMerchantAfterServiceAddrOperationNotes(rejectInfo, reject);
					merchantRejectedAddressDaoImpl.merge(reject);
					
				} else {// 保存
					merchantRejectedAddressDaoImpl.save(reject);
					operationNotes = merchantOperationLogService.buildMerchantAfterServiceAddrOperationNotes(null, reject);
				}
				if (StringUtils.isNotBlank(operationNotes)) {
					/** 添加商家联系人日志 Modifier by yang.mq **/
					MerchantOperationLog operationLog = new MerchantOperationLog();
					operationLog.setMerchantCode(reject.getSupplierCode());
					operationLog.setOperator(loginUser);
					operationLog.setOperated(new Date());
					operationLog.setOperationType(OperationType.AFTER_SERVICE);
					operationLog.setOperationNotes(operationNotes);
					merchantOperationLogService.saveMerchantOperationLog(operationLog);
				}
				
				bool = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("保存商家售后退货地址数据失败!", e);
			bool = false;
		}
		return bool;
	}

	/**
	 * 查询id查询商家售后退货地址列表
	 * 
	 * @author wang.m
	 * @date 2012-05-11
	 */
	public MerchantRejectedAddress getMerchantRejectedAddressById(String id) {
		MerchantRejectedAddress merchantRejectedAddress = null;
		CritMap critMap = new CritMap();
		// 商家名称
		if (StringUtils.isNotBlank(id)) {
			critMap.addEqual("id", id);
		}

		List<MerchantRejectedAddress> addressList = merchantRejectedAddressDaoImpl.findByCritMap(critMap);
		if (addressList != null && addressList.size() > 0) {
			merchantRejectedAddress = addressList.get(0);
		}
		return merchantRejectedAddress;
	}
	
	@Override
	public MerchantRejectedAddress getMerchantRejectedAddressByCode(
			String supplierCode) {
		MerchantRejectedAddress merchantRejectedAddress = null;
		CritMap critMap = new CritMap();
		// 商家名称
		if (StringUtils.isNotBlank(supplierCode)) {
			critMap.addEqual("supplierCode", supplierCode);
		}

		List<MerchantRejectedAddress> addressList = merchantRejectedAddressDaoImpl.findByCritMap(critMap);
		if (addressList != null && addressList.size() > 0) {
			merchantRejectedAddress = addressList.get(0);
		}
		return merchantRejectedAddress;
	}

	/**
	 * 根据ID查询子地区
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getChildAreaByNo(String no, Integer level) {
//		no = no + "-";
//		String sql = " SELECT t1.name,t1.no FROM tbl_systemmg_area t1 WHERE t1.level= " + level + " and t1.no like '%" + no + "%'";
//		List<Map<String, Object>> areaList = sqlService.getDatasBySql(sql, null, null, null);
//		return areaList;
        List<Map<String, Object>> relist = new ArrayList<Map<String, Object>>();
        List<Area> areaList = areaApi.getChildAreaByNo(no);
        if( null!=areaList && 0<areaList.size()){
        	
	        for (com.yougou.component.area.model.Area area :areaList ) {
	            if (area.getLevel() == level) {
	                Map<String, Object> map = new HashMap<String, Object>();
	                map.put("name", area.getName());
	                map.put("no", area.getNo());
	                relist.add(map);
	            }
	        }
        }
        return relist;

	}

	/*
	 * 获取省市区第一级结果集数据
	 */
	public List<Map<String, Object>> getAreaList() {
//		String sql = "SELECT t1.name,t1.no FROM tbl_systemmg_area t1 WHERE t1.level=1 ";
//		List<Map<String, Object>> areaList = sqlService.getDatasBySql(sql, null, null, null);
//		return areaList;
        List<Map<String, Object>> relist=new ArrayList<Map<String, Object>>();
        for(com.yougou.component.area.model.Area area:areaApi.getAreaByLevel(1)){
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("name", area.getName());
            map.put("no", area.getNo());
            relist.add(map);
        }
        return relist;
	}

	/**
	 * 判断商家退货地址是否已经存在
	 * 
	 * @throws Exception
	 */
	public boolean exictRejectedAddressCount(String supplierCode) {
		boolean bool = false;
		CritMap critMap = new CritMap();
		// 商家名称
		if (StringUtils.isNotBlank(supplierCode)) {
			critMap.addEqual("supplierCode", supplierCode);
		}else{
			return bool;
		}
		List<MerchantRejectedAddress> addressList = merchantRejectedAddressDaoImpl.findByCritMap(critMap);
		if (addressList != null && addressList.size() > 0) {
			bool = true;
		}
		return bool;
	}

	/**
	 * 供应商名称查询id
	 * 
	 * @author wang.m
	 * @Date 2012-03-07
	 */
	public String getSupplierSpBySupplier(String supplier) {
		String supplierId = null;
		if (StringUtils.isNotBlank(supplier)) {
			SupplierVo vo = new SupplierVo();
			vo.setSupplier(supplier);
			List<SupplierVo> list;
			try {
				list = supplierService.querySupplierByVo(vo);
			} catch (Exception e) {
				logger.error("通过供应商Id查询供应商名称", e);
				return supplierId;
			}
			if (CollectionUtils.isNotEmpty(list)) {
				supplierId = list.get(0).getId();
			}
		}

		return supplierId;
	}

	/**
	 * 根据Id修改供应商更新时间和更新人
	 * 
	 * @id 供应商Id
	 * @author wang.m
	 * @throws Exception
	 */
	private boolean updateSupplierById(String id, HttpServletRequest req) {
		boolean bool = false;
		if (StringUtils.isNotBlank(id)) {
			try {
				String sql = "update tbl_sp_supplier set update_user=?,update_date=? where id=? and delete_flag=1 ";
				Object[] obj = new Object[3];
				SystemmgtUser user = GetSessionUtil.getSystemUser(req);
				String updateUser = "";
				if (user != null) {
					updateUser = user.getUsername();
				}
				obj[0] = updateUser;// 更新人
				// 获取最后更新时间
				obj[1] = new Date();
				obj[2] = id;
				bool = sqlService.updateObject(sql, obj);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("根据Id修改供应商更新时间和更新人失败!", e);
				bool = false;
			}
		}
		return bool;
	}

	/**
	 * 封装修改商家日志信息
	 * 
	 * @author wang.m
	 * @param conductType
	 *            类型
	 * @param operateField
	 *            字段名称
	 * @param conductBeforeInfo
	 *            修前内容
	 * @param conductAfterInfo
	 *            后内容
	 * @param updateUser
	 *            修改人
	 */
	public Integer addMerhcantlog(String supplierId, String conductType, String operateField, String conductBeforeInfo, String conductAfterInfo, String updateUser) {
		Integer count = 0;
		try {
			String sql = "INSERT INTO tbl_sp_supplier_update_history VALUES(?,?,?,?,?,?,?,?)";
			Object[] obj = new Object[8];
			String uuid = UUIDGenerator.getUUID();
			obj[0] = uuid;
			obj[1] = supplierId;
			obj[2] = updateUser;// 修改人
			obj[3] = formDate();// 时间
			obj[4] = conductType;// 类型
			obj[5] = operateField;// 字段名称
			obj[6] = conductBeforeInfo;// 修改前内容
			obj[7] = conductAfterInfo;// 修改后内容
			boolean bool = sqlService.insertObject(sql, obj);
			if (bool) {
				count = 1;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("封装修改商家日志信息失败!", e);
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 跳转商家更新历史记录页面
	 * 
	 * @author wang.m
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public PageFinder<Map<String, Object>> getSupplierUpdateHistoryList(Query query, MerchantsVo merchantsVo, String id) {
		PageFinder<Map<String, Object>> pageFinder = null;
		if (StringUtils.isNotBlank(id)) {
			String sql = "SELECT t2.supplier,t2.supplier_code,t1.update_field,t1.operator,t1.operation_time,t1.processing,t1.update_before,"
					+ " t1.update_after FROM tbl_sp_supplier_update_history t1 " + " INNER JOIN tbl_sp_supplier t2 ON t1.supplier_id=t2.id "
					+ " WHERE t2.delete_flag=1 and t1.supplier_id='" + id + "'";

			// 拼接查询条件
			if (merchantsVo != null) {
				if (StringUtils.isNotBlank(merchantsVo.getSupplier())) {// 商家名称
					sql += " and t2.supplier like '%" + merchantsVo.getSupplier().trim() + "%' ";
				}
				if (StringUtils.isNotBlank(merchantsVo.getSupplierCode())) {// 商家编码
					sql += " and t2.supplier_code like '" + merchantsVo.getSupplierCode().trim() + "%'";
				}
			}

			sql += " ORDER BY t1.operation_time DESC";
			pageFinder = sqlService.getObjectsBySql(sql, query, null, null, null);

		}
		return pageFinder;
	}

	/*
	 * 查询商家结算编码集合
	 * 
	 * @author wang.m
	 * 
	 * @throws Exception
	 */
	public List<Map<String, Object>> getTraderMaintainList() {
		String sql = "SELECT t1.id,t1.balance_trader_code,t1.balance_trader_name FROM TBL_FIN_TRADER_MAINTAIN t1 WHERE t1.is_del=1 ";
		List<Map<String, Object>> map = sqlService.getDatasBySql(sql, null, null, null);
		return map;
	}

	/**
	 * 查询商家优购管理员拥有商家
	 * 
	 * @author zhuang.rb
	 * @Date 2013-01-18
	 */
	public PageFinder<Map<String, Object>> queryYougouAdminMerchantList(Query query, String merchantUserId, String merchantCode, String merchantName, Integer isInputYougouWarehouse) {
		String sql = "SELECT t2.id,t1.supplier,t1.supplier_code,t1.is_input_yougou_warehouse,t1.delete_flag,t1.is_valid" +
				" FROM tbl_sp_supplier t1 INNER JOIN tbl_merchant_user_supplier t2 ON t1.supplier_code = t2.merchant_code" +
				" WHERE t2.merchant_user_id = '"+merchantUserId+"' AND t1.supplier_type='招商供应商'";
		if(StringUtils.isNotBlank(merchantCode)) {
			sql += " AND t1.supplier_code LIKE '" +merchantCode.trim()+ "%'";
		}
		if(StringUtils.isNotBlank(merchantName)) {
			sql += " AND t1.supplier LIKE '" +merchantName.trim()+ "%'";
		}
		if (isInputYougouWarehouse != null) {
			sql += " AND t1.is_input_yougou_warehouse = '" +isInputYougouWarehouse+ "'";
		}
		sql += " ORDER BY CONVERT(t1.supplier USING GBK)";
		PageFinder<Map<String, Object>> pageFinder = sqlService.getObjectsBySql(sql, query, null, null, null);
		return pageFinder;
	}
	/**
	 * 查询商家优购管理员不拥有的商家
	 * 
	 * @author zhuang.rb
	 * @Date 2013-01-18
	 */
	public PageFinder<Map<String, Object>> queryMerchantNotHadList(Query query, String merchantUserId, String merchantCode, String merchantName, Integer isInputYougouWarehouse) {
		String sql = "SELECT t1.supplier,t1.supplier_code,t1.is_input_yougou_warehouse,t1.delete_flag,t1.is_valid" +
				" FROM tbl_sp_supplier t1 WHERE NOT EXISTS" +
				" (SELECT t2.merchant_code FROM tbl_merchant_user_supplier t2 WHERE t1.supplier_code = t2.merchant_code AND t2.merchant_user_id = '"+merchantUserId+"')" +
				" AND t1.supplier_type='招商供应商' AND t1.delete_flag = 1";
		if(StringUtils.isNotBlank(merchantCode)) {
			sql += " AND t1.supplier_code LIKE '" +merchantCode.trim()+ "%'";
		}
		if(StringUtils.isNotBlank(merchantName)) {
			sql += " AND t1.supplier LIKE '%" +merchantName.trim()+ "%'";
		}
		if (isInputYougouWarehouse != null) {
			sql += " AND t1.is_input_yougou_warehouse = '" +isInputYougouWarehouse+ "'";
		}
		sql += " ORDER BY CONVERT(t1.supplier USING GBK)";
		PageFinder<Map<String, Object>> pageFinder = sqlService.getObjectsBySql(sql, query, null, null, null);
		return pageFinder;
	}

	/**
	 * 根据id删除商家优购管理员拥有商家信息
	 */
	@Transactional
	public boolean delYougouAdminsMerchant(String id) {
		boolean isDeleteSuccess = false;
		try {
			String sql = "DELETE FROM tbl_merchant_user_supplier WHERE id = ?";
			Object[] obj = new Object[1];
			obj[0] = id;
			sqlService.deleteObject(sql, obj);
			isDeleteSuccess = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据id删除商家优购管理员拥有商家信息失败!", e);
			isDeleteSuccess = false;
		}
		return isDeleteSuccess;
	}
	/**
	 * 保存商家优购管理员拥有商家信息
	 */
	public boolean saveYougouAdminMerchant(String merchantUserId, String[] merchantCodes) throws Exception {
		boolean isSaveSuccess = false;
		try {
			if (StringUtils.isNotBlank(merchantUserId) && merchantCodes != null) {
				int length = merchantCodes.length;
				Map<String, List<Object[]>> mapYougouAdminMerchant = new HashMap<String, List<Object[]>>(1);
				List<Object[]> lstYougouAdminMerchant = new ArrayList<Object[]>(length);
				String sql = "INSERT INTO tbl_merchant_user_supplier (id, merchant_user_id, merchant_code) VALUES (?,?,?)";
				if (merchantCodes.length > 0) {
					for (String merchantCode : merchantCodes) {
						Object[] obj = new Object[3];
						String uuid = UUIDGenerator.getUUID();
						obj[0] = uuid;
						obj[1] = merchantUserId;
						obj[2] = merchantCode;
						lstYougouAdminMerchant.add(obj);
					}
				}
				mapYougouAdminMerchant.put(sql, lstYougouAdminMerchant);
				sqlService.batchAllSaveOrUpdate(mapYougouAdminMerchant);
				isSaveSuccess = true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("保存商家优购管理员拥有商家信息!", ex);
			isSaveSuccess = false;
		}
		return isSaveSuccess;
	}
	
	
	@Override
	public boolean update_user_auth(String roleId) {
		boolean flag = false;
		try{
			List<UserRole> userRoles=userAuthorityDao.findBy("roleId", roleId, false);
			for(UserRole ur : userRoles){
				loadAuthResource(ur.getUserId());
			}
			flag = true;
		}catch(Exception e){
			logger.error("权限组修改发生错误！{}",e);
		}
		return flag;
	}
	
	/**
	 * distributeAuthResourceToChildRen:重新给子账号分配权限 
	 * @author li.n1 
	 * @param userId 主账号ID
	 * @param id 子账号ID
	 * @throws Exception 
	 * @since JDK 1.6
	 */
	private void distributeAuthResourceToChildRen(String userId, String id ) throws Exception {
		// 先删除这个用户所具备的资源
		long enter = System.currentTimeMillis();
		Set<String> authrityUrls = null;
		Set<String> childauthrityUrls = null;
		Document document = XmlTool.createDocument(MerchantsService.class
 				.getClassLoader().getResource("authority.xml").getPath());
 		Map<String, List<MerchantsAuthorityVo>> _map = null;
 		Map<String, List<MerchantsAuthorityVo>> _childmap = null;
 		List<MerchantsAuthorityVo> auths = this.getMerchantAuthorityById(id);
		Map<String,Set<String>> authReseMap = 
				(Map<String,Set<String>>)redisTemplate.opsForHash().get(Constant.C_USER_REOURCE_AUTH, "authReourecesMap");
		//获取主账号的可访问的资源url
		if(authReseMap!=null){
			authrityUrls = authReseMap.get(userId);
			childauthrityUrls = authReseMap.get(id);
		}
		Map<String,Map<String, List<MerchantsAuthorityVo>>> aMap = 
				(Map<String,Map<String, List<MerchantsAuthorityVo>>>)redisTemplate.opsForHash().get(Constant.C_USER_AUTH, "authMap");
		//获取主账号可访问的菜单
		if(aMap!=null){
			_map = aMap.get(userId);
			_childmap = aMap.get(id);
		}
		for(MerchantsAuthorityVo _authority : auths){
			if(authrityUrls!=null && !(authrityUrls.contains("/"+_authority.getAuthrityURL()))){
				//主账号没有改菜单权限，子账号也要删除该菜单权限
				DefaultElement node = 
						(DefaultElement)XmlTool.findElementByProperty(document, "url", _authority.getAuthrityURL());
				if(node!=null){
					for(Object obj : node.elements()){
						childauthrityUrls.remove(XmlTool.getAttributeVal((Element)obj, "url"));
					}
				}
				childauthrityUrls.remove("/"+_authority.getAuthrityURL());
			}
			MerchantsAuthority  auth = merchantsAuthorityDaoImpl.findUniqueBy("id", _authority.getParentAuthrityId(), false) ;
			if(_map!=null && !(_map.containsKey(auth.getAuthrityName()+"@~"+auth.getSortNo()))){
				//主账号没有改菜单权限，子账号也要删除该菜单权限
				_childmap.remove(auth.getAuthrityName()+"@~"+auth.getSortNo());
			}
		}
		if(authReseMap!=null){
			authReseMap.remove(id);
			authReseMap.put(id, childauthrityUrls);
			redisTemplate.opsForHash().put(Constant.C_USER_REOURCE_AUTH, "authReourecesMap", authReseMap);
		}
		if(aMap!=null){
			aMap.remove(id);
			aMap.put(id, _childmap);
			redisTemplate.opsForHash().put(Constant.C_USER_AUTH, "authMap", aMap);
		}
		//this.addUserAuthority(id, id, null);
		System.out.println("======spent time(unit:s):======="+(System.currentTimeMillis()-enter)/1000.0);
	}

	@Override
	public void loadAuthResource(String userId) throws Exception{
		long enter = System.currentTimeMillis();
		Set<String> authrityUrls = null;
		Document document = XmlTool.createDocument(MerchantsService.class
 				.getClassLoader().getResource("authority.xml").getPath());
 		Element root = XmlTool.getRootElement(document);
 		Element indexElement = root.element("index");
 		Element menuEle = null;
 		String key="";
 		List<MerchantsAuthorityVo> childAuthorityList=null;
 		Map<String, List<MerchantsAuthorityVo>> _map = null;
 		List<MerchantsAuthorityVo> auths = this.getMerchantAuthorityById(userId);
		_map = new TreeMap<String, List<MerchantsAuthorityVo>>(new AuthorityComparator());
		authrityUrls = new HashSet<String>();
		for(MerchantsAuthorityVo _authority : auths){
			//parenAuth = merchantsAuthorityDaoImpl.findUniqueBy("id", _authority.getParentId(), false);
			if (StringUtils.isNotBlank(_authority.getAuthrityURL())){
 				for(Iterator<Element> it = root.elementIterator();it.hasNext();){
 					menuEle = it.next();
 					if(_authority.getAuthrityURL().indexOf(XmlTool.getAttributeVal(menuEle, "url"))!=-1){
 						for(Iterator<Element> eit = menuEle.elementIterator();eit.hasNext();){
 							authrityUrls.add(XmlTool.getAttributeVal(eit.next(), "url"));
 						}
 						break;
 	 				}
 				}
 				authrityUrls.add("/"+_authority.getAuthrityURL());
 			}
			key=_authority.getParentAuthrityName()+"@~"+_authority.getSortNo();
 			childAuthorityList=_map.get(key);
 			if(childAuthorityList==null){
 				childAuthorityList=new ArrayList<MerchantsAuthorityVo>();
 	 			_map.put(key, childAuthorityList);
 			}
 			childAuthorityList.add(_authority);
		}
		
		for(Iterator<Element> indexIt = indexElement.elementIterator();indexIt.hasNext();){
			authrityUrls.add(XmlTool.getAttributeVal(indexIt.next(), "url"));
		}
		
		Map<String,Set<String>> authReseMap = 
				(Map<String,Set<String>>)redisTemplate.opsForHash().get(Constant.C_USER_REOURCE_AUTH, "authReourecesMap");
		if(authReseMap!=null){
			authReseMap.remove(userId);
			authReseMap.put(userId, authrityUrls);
			redisTemplate.opsForHash().put(Constant.C_USER_REOURCE_AUTH, "authReourecesMap", authReseMap);
		}
		Map<String,Map<String, List<MerchantsAuthorityVo>>> aMap = 
				(Map<String,Map<String, List<MerchantsAuthorityVo>>>)redisTemplate.opsForHash().get(Constant.C_USER_AUTH, "authMap");
		if(aMap!=null){
			aMap.remove(userId);
			aMap.put(userId, _map);
			redisTemplate.opsForHash().put(Constant.C_USER_AUTH, "authMap", aMap);
		}
		
		//对子账号同样更新redis缓存
		//对子账号修改
		//对于主账号之前授予给子账号的的权限，如果主账号还有该权限，子账号也同样有
		//如果主账号没有该权限，子账号也同样没有，需求去除
		MerchantUser user = merchantUserDaoImpl.findUniqueBy("id", userId, false);
		CritMap map = new CritMap();
		map.addEqual("deleteFlag", 1);// 删除标志 1未删除
		map.addEqual("status", 1);	//账号开启
		map.addEqual("isAdministrator", 0);// 为子账号
		map.addEqual("merchantCode", user.getMerchantCode());// 为子账号
		List<MerchantUser> childMerUser  = merchantUserDaoImpl.findByCritMap(map, false);
		for(MerchantUser u : childMerUser){
			distributeAuthResourceToChildRen(user.getId(),u.getId());
		}
		System.out.println("======spent time(unit:s):======="+(System.currentTimeMillis()-enter)/1000.0);
	}

	private List<MerchantsAuthorityVo> getMerchantAuthorityById(String id) {
		List<MerchantsAuthorityVo> authList = new ArrayList<MerchantsAuthorityVo>();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM ( SELECT t2.authrity_name,t2.authrity_url,t2.parent_id,t3.authrity_name AS parent_authrityname,");
		sb.append("t3.sort_no,t2.sort_no as c_sort_no FROM tbl_merchant_user_authority t1");
		sb.append(" INNER JOIN tbl_merchant_authority t2 ON t1.authority_id = t2.id LEFT JOIN tbl_merchant_authority t3 ON t2.parent_id = t3.id");
		sb.append(" WHERE t1.user_id = '"+id+"' AND t2.authrity_module != 0 UNION SELECT t22.authrity_name,t22.authrity_url,t22.parent_id,");
		sb.append(" t23.authrity_name AS parent_AuthrityName,t23.sort_no,t22.sort_no as c_sort_no FROM tbl_merchant_role_authority t11");
		sb.append(" INNER JOIN tbl_merchant_authority t22 ON t11.authority_id = t22.id LEFT JOIN tbl_merchant_authority t23 ON t22.parent_id = t23.id");
		sb.append(" INNER JOIN tbl_merchant_user_role r11 ON t11.role_id = r11.role_id INNER JOIN tbl_merchant_role r22 ON t11.role_id = r22.id");
		sb.append(" WHERE r11.user_id = '"+id+"' AND r22.`status` = '1' ) AS s ORDER BY s.sort_no,s.c_sort_no");
		List<Map<String, Object>> listMap = sqlService.getDatasBySql(sb.toString());
		MerchantsAuthorityVo methAuth = null;
		for(Map<String, Object> map : listMap){
			methAuth = new MerchantsAuthorityVo();
			methAuth.setAuthrityURL((String)map.get("authrity_url"));
			methAuth.setAuthrityName((String)map.get("authrity_name"));
			methAuth.setParentAuthrityId((String)map.get("parent_id"));
			methAuth.setParentAuthrityName((String)map.get("parent_authrityname"));
			methAuth.setSortNo((Integer)map.get("sort_no"));
			authList.add(methAuth);
		}
		return authList;
	}

	/**
	 * 手机号码是否存在 
	 * @see com.belle.yitiansystem.merchant.service.IMerchantsService#existsTelePhoneInMerchants(java.lang.String)
	 * @param 需要验证的手机号码
	 * @param 商家账号，为空表示添加账号，不为空表示修改账号
	 * @return true  已存在手机号码  false 该手机号码可以使用
	 */
	@Override
	public boolean existsTelePhoneInMerchants(String mobileCode,String name) {
		// 判断是否为空
		if (StringUtils.isNotBlank(mobileCode)) {
			CritMap critMap = new CritMap();
			critMap.addEqual("mobileCode", mobileCode.trim());
			critMap.addEqual("deleteFlag", 1);// 删除标志 1未删除
			if(StringUtils.isNotBlank(name)){
				critMap.addNotEqual("loginName", name);//商家的登录名称 
			}
			List<MerchantUser> merchantUser = merchantUserDaoImpl.findByCritMap(critMap);
			if (merchantUser != null && merchantUser.size() > 0) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	 @Override
		public void delAuthResource(String userId) throws Exception {
			long enter = System.currentTimeMillis();
			Set<String> authrityUrls = null;
			Document document = XmlTool.createDocument(MerchantsService.class
	 				.getClassLoader().getResource("authority.xml").getPath());
	 		Element root = XmlTool.getRootElement(document);
	 		Element indexElement = root.element("index");
			authrityUrls = new HashSet<String>();
			List<MerchantsAuthorityVo> auths = this.getMerchantAuthorityById(userId);
			for(Iterator<Element> indexIt = indexElement.elementIterator();indexIt.hasNext();){
				authrityUrls.add(XmlTool.getAttributeVal(indexIt.next(), "url"));
			}
			
			Map<String,Set<String>> authReseMap = 
					(Map<String,Set<String>>)redisTemplate.opsForHash().get(Constant.C_USER_REOURCE_AUTH, "authReourecesMap");
			if(authReseMap!=null){
				authReseMap.remove(userId);
				authReseMap.put(userId, authrityUrls);
				redisTemplate.opsForHash().put(Constant.C_USER_REOURCE_AUTH, "authReourecesMap", authReseMap);
			}
			Map<String,Map<String, List<MerchantsAuthorityVo>>> aMap = 
					(Map<String,Map<String, List<MerchantsAuthorityVo>>>)redisTemplate.opsForHash().get(Constant.C_USER_AUTH, "authMap");
			if(aMap!=null){
				aMap.remove(userId);
				aMap.put(userId, new TreeMap<String, List<MerchantsAuthorityVo>>(new AuthorityComparator()));
				redisTemplate.opsForHash().put(Constant.C_USER_AUTH, "authMap", aMap);
			}
			Map<String,Integer> userAuthCountMap = 
					(Map<String,Integer>)redisTemplate.opsForHash().get(CacheConstant.C_USERS_AUTH_COUNT,"authCount");
			
			if(userAuthCountMap!=null){
				userAuthCountMap.remove(userId);
				userAuthCountMap.put(userId, auths!=null?auths.size():0);
				redisTemplate.opsForHash().put(CacheConstant.C_USERS_AUTH_COUNT, "authCount", userAuthCountMap);
			}
			//对子账号同样更新redis缓存
			//对子账号修改
			//对于主账号之前授予给子账号的的权限，如果主账号还有该权限，子账号也同样有
			//如果主账号没有该权限，子账号也同样没有，需求去除
			MerchantUser user = merchantUserDaoImpl.findUniqueBy("id", userId, false);
			CritMap map = new CritMap();
			map.addEqual("deleteFlag", 1);// 删除标志 1未删除
			map.addEqual("status", 1);	//账号开启
			map.addEqual("isAdministrator", 0);// 为子账号
			map.addEqual("merchantCode", user.getMerchantCode());// 为子账号
			List<MerchantUser> childMerUser  = merchantUserDaoImpl.findByCritMap(map, false);
			for(MerchantUser u : childMerUser){
				distributeAuthResourceToChildRen(user.getId(),u.getId());
			}
			System.out.println("======spent time(unit:s):======="+(System.currentTimeMillis()-enter)/1000.0);
		}
	    
	    @Override
		public int deleteCacheByKey(String key) {
			int result = 0;
			Long len1 = 0L, len2=0L, len3=0L, len4=0L, len5=0L;
			try{
				len1 = redisTemplate.opsForHash().size(key);
			}catch(Exception e){
				len1 = 0L;
				logger.error("redisTemplate.opsForHash()报错！");
			}
			try{
				len2 = redisTemplate.opsForList().size(key);
			}catch(Exception e){
				len2 = 0L;
				logger.error("redisTemplate.opsForList()报错！");
			}
			try{
				len3 = redisTemplate.opsForSet().size(key);
			}catch(Exception e){
				len3 = 0L;
				logger.error("redisTemplate.opsForSet()报错！");
			}
			try{
				len4 = redisTemplate.opsForValue().size(key);
			}catch(Exception e){
				len4  =0L;
				logger.error("redisTemplate.opsForValue()报错！");
			}
			try{
				len5 = redisTemplate.opsForZSet().size(key);
			}catch(Exception e){
				len5 = 0L;
				logger.error("redisTemplate.opsForZSet()报错！");
			}
			try{
				redisTemplate.delete(key);
			}catch(Exception e){
				logger.error("redisTemplate.delete报错，删除缓存失败！！",e);
			}
			if(NumberUtils.toInt(String.valueOf(len1))>0){
				result+=len1;
			}
			if(NumberUtils.toInt(String.valueOf(len2))>0){
				result+=len2;
			}
			if(NumberUtils.toInt(String.valueOf(len3))>0){
				result+=len3;
			}
			if(NumberUtils.toInt(String.valueOf(len4))>0){
				result+=len4;
			}
			if(NumberUtils.toInt(String.valueOf(len5))>0){
				result+=len5;
			}
			return result;
		}

		@Override
		public List<Cat> buildBrandCatSecondLevelList(String supplierId)
				throws Exception {
			List<Cat> result = null;
	    	if( StringUtils.isNotEmpty(supplierId) ){
	    		
	    		// 品牌分类关系集合
				List<com.yougou.kaidian.common.commodity.pojo.Brand> resultList = 
						merchantBrandMapper.querySecondLevelCatsBySupplierId(supplierId);
				if(null!=resultList && 0<resultList.size()){
					result = new ArrayList<Cat>();
					Map<String,Brand> accordingMapForBrand = new HashMap<String,Brand>();
					Map<String,Category> accordingMapForCatFirst = new HashMap<String,Category>();
					for(com.yougou.kaidian.common.commodity.pojo.Brand brand:resultList){
						String brand_NO = brand.getBrandNo();
						String cat_struct = brand.getBrandName();
						if(StringUtils.isEmpty(brand_NO)
								 || StringUtils.isEmpty(cat_struct) ){
							continue;
						}
						String first_cat_struct = cat_struct.substring(0,2);
						Cat category = new Cat();
						
						Brand brandVo = null;
						if( accordingMapForBrand.containsKey(brand_NO)){
							brandVo = accordingMapForBrand.get(brand_NO);
						}else{
							brandVo = commodityBaseApiService.getBrandByNo(brand_NO);
							accordingMapForBrand.put(brand_NO, brandVo);
						}
						
						Category catFirst = null;
						if( accordingMapForCatFirst.containsKey(first_cat_struct)){
							catFirst = accordingMapForCatFirst.get(first_cat_struct);
						}else{
							catFirst = commodityBaseApiService.getCategoryByStructName(first_cat_struct);
							accordingMapForCatFirst.put(first_cat_struct, catFirst);
						}
						
						Category catSecond = commodityBaseApiService.getCategoryByStructName(cat_struct);
						
						if(null!=brandVo){
							category.setNo(brandVo.getBrandName());
						}
						if(null!=catFirst){
							category.setCatName(catFirst.getCatName());
						}
						if(null!=catSecond){
							category.setStructName(catSecond.getCatName());
						}
						result.add(category);
					}
				}
				
	    	}
	    	return result;
		}

	
}

/**
 * 按中文字母排序类
 * @author zhuang.rb
 *
 */
class GBKOrder extends Order {   
	private static final long serialVersionUID = 1L;
	private String encoding = "GBK";   
    private boolean ascending;   
    private boolean ignoreCase;   
    private String propertyName;   
  
    @Override  
    public String toString() {   
        return "CONVERT( " + propertyName + " USING " + encoding + " ) " + (ascending ? "asc" : "desc");   
    }   
  
    @Override  
    public Order ignoreCase() {   
        ignoreCase = true;   
        return this;   
    }   
  
    protected GBKOrder(String propertyName, boolean ascending) {   
        super(propertyName, ascending);   
        this.propertyName = propertyName;   
        this.ascending = ascending;   
    }   
  
    @Override  
    public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {   
        String[] columns = criteriaQuery.getColumnsUsingProjection(criteria, propertyName);   
        Type type = criteriaQuery.getTypeUsingProjection(criteria, propertyName);   
        StringBuffer fragment = new StringBuffer();   
        for (int i = 0; i < columns.length; i++) {   
            SessionFactoryImplementor factory = criteriaQuery.getFactory();   
            boolean lower = ignoreCase && type.sqlTypes(factory)[i] == Types.VARCHAR;   
            if (lower) {   
                fragment.append(factory.getDialect().getLowercaseFunction()).append('(');   
            }   
            fragment.append("CONVERT( " + columns[i] + " USING " + encoding + " )");   
            if (lower)   
                fragment.append(')');   
            fragment.append(ascending ? " asc" : " desc");   
            if (i < columns.length - 1)   
                fragment.append(", ");   
        }   
        return fragment.toString();   
    }   
  
    /**  
     * Ascending order  
     *   
     * @param propertyName  
     * @return Order  
     */  
    public static Order asc(String propertyName) {   
        return new GBKOrder(propertyName, true);   
    }   
  
    /**  
     * Descending order  
     *   
     * @param propertyName  
     * @return Order  
     */  
    public static Order desc(String propertyName) {   
        return new GBKOrder(propertyName, false);   
    }   
    
   
    
}