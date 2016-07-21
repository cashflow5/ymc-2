package com.belle.yitiansystem.merchant.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.other.model.pojo.SupplierContactSp;
import com.belle.other.model.pojo.SupplierContract;
import com.belle.other.model.pojo.SupplierSp;
import com.belle.other.model.pojo.SupplierSp.CooperationModel;
import com.belle.yitiansystem.merchant.dao.IMerchantOperationLogDao;
import com.belle.yitiansystem.merchant.dao.mapper.MerchantOperationLogMapper;
import com.belle.yitiansystem.merchant.model.pojo.MerchantOperationLog;
import com.belle.yitiansystem.merchant.model.pojo.MerchantOperationLog.OperationType;
import com.belle.yitiansystem.merchant.model.pojo.MerchantRejectedAddress;
import com.belle.yitiansystem.merchant.model.pojo.MerchantUser;
import com.belle.yitiansystem.merchant.service.IMerchantOperationLogService;
import com.yougou.fss.api.vo.ShopVO;
import com.yougou.merchant.api.supplier.vo.SupplierVo;

@Service
public class MerchantOperationLogServiceImpl implements IMerchantOperationLogService {
	
	private static final Map<String, String> BASIC_DATA_TRANSLATABLE_FIELDS = new HashMap<String, String>();
	
	private static final Map<String, String> ACCOUNT_TRANSLATABLE_FIELDS = new HashMap<String, String>();
	
	private static final Map<String, String> CONTACT_TRANSLATABLE_FIELDS = new HashMap<String, String>();
	
	private static final Map<String, String> CONTRACT_TRANSLATABLE_FIELDS = new HashMap<String, String>();
	
	private static final Map<String, String> AFTER_SERVICE_TRANSLATABLE_FIELDS = new HashMap<String, String>();
	
	private static final Map<String, String> SHOP_TRANSLATABLE_FIELDS = new HashMap<String, String>();
	
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	static {
		// 商家资料
		BASIC_DATA_TRANSLATABLE_FIELDS.put("supplier", "供应商名称");
		BASIC_DATA_TRANSLATABLE_FIELDS.put("supplierType", "供应商类型");
		BASIC_DATA_TRANSLATABLE_FIELDS.put("isInputYougouWarehouse", "仓库类型");
		BASIC_DATA_TRANSLATABLE_FIELDS.put("setOfBooksName", "成本账套名称");
		BASIC_DATA_TRANSLATABLE_FIELDS.put("taxRate", "税率");
		BASIC_DATA_TRANSLATABLE_FIELDS.put("shipmentType", "验收差异处理方式");
		BASIC_DATA_TRANSLATABLE_FIELDS.put("couponsAllocationProportion", "优惠券分摊比例");
		BASIC_DATA_TRANSLATABLE_FIELDS.put("contact", "银行开户名");
		BASIC_DATA_TRANSLATABLE_FIELDS.put("businessLocal", "营业执照所在地");
		BASIC_DATA_TRANSLATABLE_FIELDS.put("account", "公司银行帐号");
		BASIC_DATA_TRANSLATABLE_FIELDS.put("businessValidity", "营业执照有效期");
		BASIC_DATA_TRANSLATABLE_FIELDS.put("subBank", "开户行支行名称");
		BASIC_DATA_TRANSLATABLE_FIELDS.put("tallageNo", "税务登记证号");
		BASIC_DATA_TRANSLATABLE_FIELDS.put("bankLocal", "开户行银行所在地");
		BASIC_DATA_TRANSLATABLE_FIELDS.put("institutional", "组织机构代码");
		BASIC_DATA_TRANSLATABLE_FIELDS.put("businessLicense", "营业执照号");
		BASIC_DATA_TRANSLATABLE_FIELDS.put("taxpayer", "纳税人识别号");
		// 商家帐户
		ACCOUNT_TRANSLATABLE_FIELDS.put("userName", "帐户名称");
		ACCOUNT_TRANSLATABLE_FIELDS.put("loginName", "登录名称");
		ACCOUNT_TRANSLATABLE_FIELDS.put("password", "登录密码");
		ACCOUNT_TRANSLATABLE_FIELDS.put("status", "是否启用");
		ACCOUNT_TRANSLATABLE_FIELDS.put("isAdministrator", "是否商家管理员");
		ACCOUNT_TRANSLATABLE_FIELDS.put("deleteFlag", "是否删除");
		ACCOUNT_TRANSLATABLE_FIELDS.put("isYougouAdmin", "是否优购管理员");
		// 联系人
		CONTACT_TRANSLATABLE_FIELDS.put("contact", "姓名");
		CONTACT_TRANSLATABLE_FIELDS.put("type", "类型");
		CONTACT_TRANSLATABLE_FIELDS.put("telePhone", "电话号码");
		CONTACT_TRANSLATABLE_FIELDS.put("mobilePhone", "手机号码");
		CONTACT_TRANSLATABLE_FIELDS.put("fax", "传真号码");
		CONTACT_TRANSLATABLE_FIELDS.put("email", "电子邮箱");
		CONTACT_TRANSLATABLE_FIELDS.put("address", "地址");
		// 合同
		CONTRACT_TRANSLATABLE_FIELDS.put("contractNo", "合同编号");
		CONTRACT_TRANSLATABLE_FIELDS.put("effectiveDate", "有效日期(起)");
		CONTRACT_TRANSLATABLE_FIELDS.put("failureDate", "有效日期(止)");
		CONTRACT_TRANSLATABLE_FIELDS.put("clearingForm", "结算方式");
		// 售后
		AFTER_SERVICE_TRANSLATABLE_FIELDS.put("supplierName", "商家名称");
		AFTER_SERVICE_TRANSLATABLE_FIELDS.put("consigneeName", "收货人姓名");
		AFTER_SERVICE_TRANSLATABLE_FIELDS.put("consigneePhone", "收货人手机");
		AFTER_SERVICE_TRANSLATABLE_FIELDS.put("consigneeTell", "收货人电话");
		AFTER_SERVICE_TRANSLATABLE_FIELDS.put("warehousePostcode", "收货人邮编");
		AFTER_SERVICE_TRANSLATABLE_FIELDS.put("warehouseArea", "收货人地区");
		AFTER_SERVICE_TRANSLATABLE_FIELDS.put("warehouseAdress", "收货人地址");
		//旗舰店
		SHOP_TRANSLATABLE_FIELDS.put("shopName", "店铺名称");
		SHOP_TRANSLATABLE_FIELDS.put("shopURL", "店铺URL");
		SHOP_TRANSLATABLE_FIELDS.put("access", "店铺Acess状态");
		SHOP_TRANSLATABLE_FIELDS.put("auditStatus", "审核状态");
		SHOP_TRANSLATABLE_FIELDS.put("auditFlag", "审核标志");
		
	}
	
	
	@Resource
	private IMerchantOperationLogDao merchantOperationLogDao;
	
	@Resource
	private MerchantOperationLogMapper logMapper;
	
	@Override
	@Transactional
	public void saveMerchantOperationLog(MerchantOperationLog operationLog) throws Exception {
		merchantOperationLogDao.save(operationLog);
	}

	@Override
	public PageFinder<MerchantOperationLog> queryMerchantOperationLog(String merchantCode, Query query) throws Exception {
		Session session = null;
		try {
			session = merchantOperationLogDao.getHibernateSession();
			Criteria criteria = session.createCriteria(MerchantOperationLog.class);
			criteria.add(Restrictions.eq("merchantCode", merchantCode));
			criteria.addOrder(Order.desc("operated"));
			return merchantOperationLogDao.pagedByCriteria(criteria, query.getPage(), query.getPageSize());
		} finally {
			merchantOperationLogDao.releaseHibernateSession(session);
		}
	}

	@Override
	public PageFinder<MerchantOperationLog> queryMerchantOperationLogByOperationType(String merchantCode, com.yougou.merchant.api.supplier.vo.MerchantOperationLog.OperationType operationType, Query query) throws Exception {
		Session session = null;
		try {
			session = merchantOperationLogDao.getHibernateSession();
			Criteria criteria = session.createCriteria(MerchantOperationLog.class);
			if(StringUtils.isNotEmpty(merchantCode)){
				criteria.add(Restrictions.eq("merchantCode", merchantCode));
			}
			criteria.add(Restrictions.eq("operationType", operationType));
			criteria.addOrder(Order.desc("operated"));
			return merchantOperationLogDao.pagedByCriteria(criteria, query.getPage(), query.getPageSize());
		} finally {
			merchantOperationLogDao.releaseHibernateSession(session);
		}
	}
	
	@Override
	public String buildMerchantBasicDataOperationNotes(SupplierSp source, SupplierSp target) throws Exception {
		if (target == null) {
			throw new NullPointerException("target");
		}
		if (source == null) {
			return "新建商家";
		}
		
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : BASIC_DATA_TRANSLATABLE_FIELDS.entrySet()) {
			Object o1 = PropertyUtils.getProperty(source, entry.getKey());
			Object o2 = PropertyUtils.getProperty(target, entry.getKey());
			if (!ObjectUtils.equals(o1, o2)) {
				if (StringUtils.equals("isInputYougouWarehouse", entry.getKey())) {
					CooperationModel[] cooperationModels = SupplierSp.CooperationModel.values();
					o1 = cooperationModels[(Integer) o1].getDescription();
					o2 = cooperationModels[(Integer) o2].getDescription();
				} else if (StringUtils.equals("shipmentType", entry.getKey())) {
					o1 = ObjectUtils.equals(NumberUtils.INTEGER_ONE, o1) ? "销退" : "验退";
					o2 = ObjectUtils.equals(NumberUtils.INTEGER_ONE, o2) ? "销退" : "验退";
				}
				sb.append(MessageFormat.format("将“{0}”由【{1}】修改为【{2}】{3}", entry.getValue(), o1, o2, LINE_SEPARATOR));
			}
		}
		return sb.toString();
	}

	@Override
	public String buildMerchantAccountOperationNotes(MerchantUser source, MerchantUser target) throws Exception {
		if (target == null) {
			throw new NullPointerException("target");
		}
		if (source == null) {
			return "新建商家帐号";
		}
		
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : ACCOUNT_TRANSLATABLE_FIELDS.entrySet()) {
			Object o1 = PropertyUtils.getProperty(source, entry.getKey());
			Object o2 = PropertyUtils.getProperty(target, entry.getKey());
			if (!ObjectUtils.equals(o1, o2)) {
				if (StringUtils.equals("status", entry.getKey())) {
					o1 = ObjectUtils.equals(NumberUtils.INTEGER_ONE, o1) ? "启用" : "锁定";
					o2 = ObjectUtils.equals(NumberUtils.INTEGER_ONE, o2) ? "启用" : "锁定";
				} else if (StringUtils.equals("isAdministrator", entry.getKey()) || StringUtils.equals("deleteFlag", entry.getKey()) || StringUtils.equals("isYougouAdmin", entry.getKey())) {
					o1 = ObjectUtils.equals(NumberUtils.INTEGER_ONE, o1) ? "是" : "不是";
					o2 = ObjectUtils.equals(NumberUtils.INTEGER_ONE, o2) ? "是" : "不是";
				}
				sb.append(MessageFormat.format("将“{0}”由【{1}】修改为【{2}】{3}", entry.getValue(), o1, o2, LINE_SEPARATOR));
			}
		}
		return sb.toString();
	}

	@Override
	public String buildMerchantContactOperationNotes(SupplierContactSp source, SupplierContactSp target) throws Exception {
		if (target == null) {
			throw new NullPointerException("target");
		}
		if (source == null) {
			return "新建商家联系人";
		}
		
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : CONTACT_TRANSLATABLE_FIELDS.entrySet()) {
			Object o1 = PropertyUtils.getProperty(source, entry.getKey());
			Object o2 = PropertyUtils.getProperty(target, entry.getKey());
			if (!ObjectUtils.equals(o1, o2)) {
				if (StringUtils.equals("type", entry.getKey())) {
					o1 = ObjectUtils.equals(NumberUtils.INTEGER_ONE, o1) ? "业务" : ObjectUtils.equals(2, o1) ? "售后" : ObjectUtils.equals(3, o1) ? "仓储" : ObjectUtils.equals(4, o1) ? "财务" : ObjectUtils.equals(5, o1) ? "技术" : "未知";
					o2 = ObjectUtils.equals(NumberUtils.INTEGER_ONE, o2) ? "业务" : ObjectUtils.equals(2, o2) ? "售后" : ObjectUtils.equals(3, o2) ? "仓储" : ObjectUtils.equals(4, o2) ? "财务" : ObjectUtils.equals(5, o2) ? "技术" : "未知";
				}
				sb.append(MessageFormat.format("将“{0}”由【{1}】修改为【{2}】{3}", entry.getValue(), o1, o2, LINE_SEPARATOR));
			}
		}
		return sb.toString();
	}

	@Override
	public String buildMerchantContractOperationNotes(SupplierContract source, SupplierContract target) throws Exception {
		if (target == null) {
			throw new NullPointerException("target");
		}
		if (source == null) {
			return "新建商家合同";
		}
		
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : CONTRACT_TRANSLATABLE_FIELDS.entrySet()) {
			Object o1 = PropertyUtils.getProperty(source, entry.getKey());
			Object o2 = PropertyUtils.getProperty(target, entry.getKey());
			if (!ObjectUtils.equals(o1, o2)) {
				if (StringUtils.equals("clearingForm", entry.getKey())) {
					o1 = ObjectUtils.equals(NumberUtils.INTEGER_ONE, o1) ? "底价结算" : ObjectUtils.equals(2, o1) ? "扣点结算" : ObjectUtils.equals(3, o1) ? "配折结算" : ObjectUtils.equals(4, o1) ? "促销结算" : "未知";
					o2 = ObjectUtils.equals(NumberUtils.INTEGER_ONE, o2) ? "底价结算" : ObjectUtils.equals(2, o2) ? "扣点结算" : ObjectUtils.equals(3, o2) ? "配折结算" : ObjectUtils.equals(4, o2) ? "促销结算" : "未知";
				}
				sb.append(MessageFormat.format("将“{0}”由【{1}】修改为【{2}】{3}", entry.getValue(), o1, o2, LINE_SEPARATOR));
			}
		}
		return sb.toString();
	}

	@Override
	public String buildMerchantAfterServiceAddrOperationNotes(MerchantRejectedAddress source, MerchantRejectedAddress target) throws Exception {
		if (target == null) {
			throw new NullPointerException("target");
		}
		if (source == null) {
			return "新建商家售后退货地址";
		}
		
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : AFTER_SERVICE_TRANSLATABLE_FIELDS.entrySet()) {
			Object o1 = PropertyUtils.getProperty(source, entry.getKey());
			Object o2 = PropertyUtils.getProperty(target, entry.getKey());
			if (!ObjectUtils.equals(o1, o2)) {
				sb.append(MessageFormat.format("将“{0}”由【{1}】修改为【{2}】{3}", entry.getValue(), o1, o2, LINE_SEPARATOR));
			}
		}
		return sb.toString();
	}

	/** 
	 * 商家店铺日志操作 
	 * @see com.belle.yitiansystem.merchant.service.IMerchantOperationLogService#buildMerchantShopOperationNotes(java.lang.Object, com.yougou.fss.api.vo.ShopVO) 
	 */
	@Override
	public String buildMerchantShopOperationNotes(ShopVO source, ShopVO target) throws Exception {
		if (target == null) {
			//throw new NullPointerException("target");
			return "删除店铺【"+source.getShopName()+"】";
		}
		if (source == null) {
			return "创建店铺【"+target.getShopName()+"】";
		}
		//String str1 = null;
		//String str2 = null;
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : SHOP_TRANSLATABLE_FIELDS.entrySet()) {
			Object o1 = PropertyUtils.getProperty(source, entry.getKey());
			Object o2 = PropertyUtils.getProperty(target, entry.getKey());
			if("auditStatus".equals(entry.getKey())){
				if((Integer)o1==0){
					o1 = "待提交";
				}else if((Integer)o1==1){
					o1 = "待审核";
				}else if((Integer)o1==3){
					o1 = "审核通过";
				}else{
					o1 = "审核未通过";
				}
				if((Integer)o2==0){
					o2 = "待提交";
				}else if((Integer)o2==1){
					o2 = "待审核";
				}else if((Integer)o2==3){
					o2 = "审核通过";
				}else{
					o2 = "审核未通过";
				}
			}else if("access".equals(entry.getKey())){
				if("Y".equals((String)o1)){
					o1 = "开启";
				}else{
					o1 = "关闭";
				}
				if("Y".equals((String)o2)){
					o2 = "开启";
				}else{
					o2 = "关闭";
				}
			}else if("auditFlag".equals(entry.getKey())){
				if("0".equals((String)o1)){
					o1 = "需要审核";
				}else{
					o1 = "不需要审核";
				}
				if("0".equals((String)o2)){
					o2 = "需要审核";
				}else{
					o2 = "不需要审核";
				}
			}
			if (!ObjectUtils.equals(o1, o2)) {
				sb.append(MessageFormat.format("将“{0}”由【{1}】修改为【{2}】{3}", entry.getValue(), o1, o2, LINE_SEPARATOR));
			}
		}
		if(sb.toString().length()<=0){
			sb.append("未作任何修改！");
		}
		return sb.toString();
	}
	
	/** 
	 * 商家店铺规则日志操作 
	 * @see com.belle.yitiansystem.merchant.service.IMerchantOperationLogService#buildMerchantShopOperationNotes(java.lang.Object, com.yougou.fss.api.vo.ShopVO) 
	 */
	@Override
	public String buildMerchantShopRuleOperationNotes(String source, String target) throws Exception {
		StringBuilder sb = new StringBuilder();
		if (!ObjectUtils.equals(source, target)) {
			sb.append(MessageFormat.format("将“{0}”由【{1}】修改为【{2}】{3}", "经营品类", source, target, LINE_SEPARATOR));
		}
		if(sb.toString().length()<=0){
			sb.append("未作任何修改！");
		}
		return sb.toString();
	}

	/** 
	 * TODO 自定义的OperationType日志查询 
	 * @see com.belle.yitiansystem.merchant.service.IMerchantOperationLogService#queryMerchantOperationLogByOperationType(java.lang.String, com.belle.yitiansystem.merchant.model.pojo.MerchantOperationLog.OperationType, com.belle.infrastructure.orm.basedao.Query) 
	 */
	@Override
	public PageFinder<MerchantOperationLog> queryMerchantOperationLogByOperationType(
			String merchantCode, OperationType operationType, Query query)
			throws Exception {
		Session session = null;
		try {
			session = merchantOperationLogDao.getHibernateSession();
			Criteria criteria = session.createCriteria(MerchantOperationLog.class);
			if(StringUtils.isNotEmpty(merchantCode)){
				criteria.add(Restrictions.eq("merchantCode", merchantCode));
			}
			criteria.add(Restrictions.eq("operationType", operationType));
			criteria.addOrder(Order.desc("operated"));
			return merchantOperationLogDao.pagedByCriteria(criteria, query.getPage(), query.getPageSize());
		} finally {
			merchantOperationLogDao.releaseHibernateSession(session);
		}
	}

	@Override
	public PageFinder<MerchantOperationLog> queryMerchantOperationLog(
			SupplierVo supplierVo, Query query) {
		//Amend by LQ on 20150420
		int count = 0;
		count = logMapper.selectMerchantOperationLogCount(supplierVo.getSupplierCode(),supplierVo.getId());
		if( count<1 ){
			return new PageFinder<MerchantOperationLog>(query.getPage(), query.getPageSize(), 0, null);
		}else{
			
			List<Map> logList = logMapper.selectMerchantOperationLog(supplierVo.getSupplierCode(),supplierVo.getId(),query);
			List<MerchantOperationLog> resultList = new ArrayList<MerchantOperationLog>();
			for(Map map:logList){
				MerchantOperationLog log = new MerchantOperationLog();
				log.setMerchantCode((String)map.get("merchant_code"));
				log.setId((String)map.get("id"));
				log.setOperator((String)map.get("operator"));
				log.setOperated((Date)map.get("operated"));
				log.setOperationNotes((String)map.get("operation_notes"));
				String type =(String)map.get("operation_type");
				log.setOperationType(Enum.valueOf(OperationType.class, type));
				resultList.add(log);
				
			}
			return new PageFinder<MerchantOperationLog>(query.getPage(),
					query.getPageSize(), count, resultList);
		}
	}

	@Override
	@Transactional
	public void addMerchantOperationLog(String merchantCode,
			OperationType type, String msg, HttpServletRequest request) {
		MerchantOperationLog  log = new MerchantOperationLog();
		log.setMerchantCode(merchantCode);
		log.setOperationType(type);
		log.setOperator(GetSessionUtil.getSystemUser(request).getLoginName());
		log.setOperated(new Date());
		log.setOperationNotes(msg);
		try {
			merchantOperationLogDao.save(log);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void addMerchantOperationLog(String merchantCode,
			OperationType type, String msg, String userName) {
		MerchantOperationLog  log = new MerchantOperationLog();
		log.setMerchantCode(merchantCode);
		log.setOperationType(type);
		log.setOperator(userName);
		log.setOperated(new Date());
		log.setOperationNotes(msg);
		try {
			merchantOperationLogDao.save(log);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public PageFinder<MerchantOperationLog> queryMerchantOperationLogByUser(
			String userId,
			com.yougou.merchant.api.supplier.vo.MerchantOperationLog.OperationType operationType,
			Query query) {
		Session session = null;
		try {
			session = merchantOperationLogDao.getHibernateSession();
			Criteria criteria = session.createCriteria(MerchantOperationLog.class);
			if(StringUtils.isNotEmpty(userId)){
				criteria.add(Restrictions.eq("userId", userId));
			}
			criteria.add(Restrictions.eq("operationType", operationType));
			criteria.addOrder(Order.desc("operated"));
			return merchantOperationLogDao.pagedByCriteria(criteria, query.getPage(), query.getPageSize());
		} finally {
			merchantOperationLogDao.releaseHibernateSession(session);
		}
	}

}