/**
 * 
 */
package com.belle.yitiansystem.merchant.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.belle.finance.biz.dubbo.ICostSetOfBooksDubboService;
import com.belle.finance.costsettlement.costsetofbooks.model.vo.CostSetofBooks;
import com.belle.infrastructure.util.DateUtil;
import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.infrastructure.util.Md5Encrypt;
import com.belle.infrastructure.util.UUIDGenerator;
import com.belle.other.model.pojo.SupplierSp;
import com.belle.yitiansystem.merchant.constant.MerchantConstant;
import com.belle.yitiansystem.merchant.dao.impl.MerchantRejectedAddressDaoImpl;
import com.belle.yitiansystem.merchant.dao.mapper.MerchantMapper;
import com.belle.yitiansystem.merchant.dao.mapper.MerchantSupplierExpandMapper;
import com.belle.yitiansystem.merchant.dao.mapper.SupplierVoMapper;
import com.belle.yitiansystem.merchant.exception.MerchantSystemException;
import com.belle.yitiansystem.merchant.model.pojo.AttachmentFormVo;
import com.belle.yitiansystem.merchant.model.pojo.SupplierContract;
import com.belle.yitiansystem.merchant.service.IMerchantOperationLogService;
import com.belle.yitiansystem.merchant.service.IMerchantServiceNew;
import com.belle.yitiansystem.merchant.service.ISupplierContractService;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.belle.yitiansystem.taobao.exception.BusinessException;
import com.yougou.component.email.api.IEmailApi;
import com.yougou.component.email.model.MailSenderInfo;
import com.yougou.component.email.model.SubjectIdType;
import com.yougou.kaidian.common.util.DateUtil2;
import com.yougou.merchant.api.pic.service.IPictureService;
import com.yougou.merchant.api.pic.service.vo.MerchantPictureCatalog;
import com.yougou.merchant.api.supplier.service.IBrandCatApi;
import com.yougou.merchant.api.supplier.service.IMerchantsApi;
import com.yougou.merchant.api.supplier.service.ISupplierService;
import com.yougou.merchant.api.supplier.vo.BrandCatRelation;
import com.yougou.merchant.api.supplier.vo.BrandVo;
import com.yougou.merchant.api.supplier.vo.CatVo;
import com.yougou.merchant.api.supplier.vo.ContactsVo;
import com.yougou.merchant.api.supplier.vo.MerchantOperationLog;
import com.yougou.merchant.api.supplier.vo.MerchantRejectedAddressVo;
import com.yougou.merchant.api.supplier.vo.MerchantSupplierExpand;
import com.yougou.merchant.api.supplier.vo.MerchantUser;
import com.yougou.merchant.api.supplier.vo.SupplierVo;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.model.category.Category;
import com.yougou.purchase.api.IPurchaseApiService;
import com.yougou.purchase.model.Supplier;
import com.yougou.purchase.model.SupplierContact;

/**
 * @author huang.tao
 *
 */
@Service
public class MerchantServiceNewImpl implements IMerchantServiceNew {

	private final static Logger logger = LoggerFactory
			.getLogger(MerchantServiceNewImpl.class);

	@Value("${email.activate.src}")
	private String emailActivateSrc = "http://kaidian.yougou.com/merchants/login/activatemail.sc";

	@Resource
	private ICostSetOfBooksDubboService costSetofBookApi;
	@Resource
	private ISupplierService supplierService;
	@Resource
	private ICommodityBaseApiService commodityBaseApiService;
	@Resource
	private IEmailApi emailApi;
	@Resource
	private IMerchantsApi merchantsApi;
	@Resource
	private IBrandCatApi brandcatApi;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private IPurchaseApiService purchaseApiService;
	@Resource
	private MerchantRejectedAddressDaoImpl merchantRejectedAddressDaoImpl;
	@Resource
	private IMerchantOperationLogService merchantOperationLogService;
	@Resource
	private ISupplierContractService supplierContractService;
	@Resource
	private IPictureService pictureService;
	@Resource
	private MerchantSupplierExpandMapper merchantSupplierExpandMapper;
	@Resource
	private MerchantMapper merchantMapper;
	@Resource
	private SupplierVoMapper supplierVoMapper;

	// 优购科技（商家销售）ZT20140417305780 正式环境的，ZT20140903837400 测试环境的
	private final String BOOKCODE_SJXS = "ZT20140417305780,ZT20140903837400";
	// 临时账套(优购商家发货)
	private final String BOOKCODE_SJFH = "ZT20120706694264";
	// 跨境电商（首尔直发）
	private final String BOOKCODE_KJDS_SEZF = "ZT20151203198386";

	// FIXME 如果成本套账code是固定的，是否可以写死？
	@Override
	public List<CostSetofBooks> getCostSetofBooksList() throws Exception {
		List<CostSetofBooks> list = costSetofBookApi.queryAllCostSetOfBooks();
		if (CollectionUtils.isNotEmpty(list)) {
			List<CostSetofBooks> tmpList = new ArrayList<CostSetofBooks>();
			for (CostSetofBooks books : list) {
				if (BOOKCODE_SJXS.indexOf(books.getSetOfBooksCode()) != -1) {
					tmpList.add(books);
				} else if (BOOKCODE_SJFH.equalsIgnoreCase(books.getSetOfBooksCode())) {
					tmpList.add(books);
				} else if(BOOKCODE_KJDS_SEZF.equalsIgnoreCase(books.getSetOfBooksCode())){
					tmpList.add(books);
				}
			}
			list = tmpList;
		}
		return list;
	}
 
	@Override
	public List<CostSetofBooks> getAllCostSetofBooksList() throws Exception {
		return costSetofBookApi.queryAllCostSetOfBooks();
	}

	@Override
	public SupplierVo getSupplierVoById(String id) {
		return merchantMapper.selectByPrimaryKey(id);
	}

	@Override
	public SupplierVo getMerchantVoByCode(String merchantCode) {
		SupplierVo _vo = new SupplierVo();
		_vo.setSupplierCode(merchantCode);
		_vo.setIsValid(null);
		List<SupplierVo> vos = null;
		try {
			vos = supplierService.querySupplierByVo(_vo);
		} catch (Exception e) {
			logger.error("调用mct Api获取招商供应商异常.", e);
		}
		return CollectionUtils.isNotEmpty(vos) ? vos.get(0) : null;
	}

	// @Override
	// public boolean addMerchant(HttpServletRequest req, SupplierSp supplierSp,
	// String bankNoHidden, String catNameHidden) {
	// SupplierVo supplier = new SupplierVo();
	// try {
	// SystemmgtUser user = GetSessionUtil.getSystemUser(req);
	// supplier.setId(UUIDGenerator.getUUID());
	// supplier.setUpdateTimestamp(System.currentTimeMillis());// 时间戳
	// supplier.setSupplier(supplierSp.getSupplier());
	// supplier.setSupplierCode(CodeGenerate.getSupplierCode());// 商家编号
	// supplier.setContact(supplierSp.getContact());// 开户名称
	// supplier.setAccount(supplierSp.getAccount());// 开户银行账号
	// supplier.setSubBank(supplierSp.getSubBank());// 开户子银行
	// supplier.setBankLocal(supplierSp.getBankLocal());// 开户所在地
	// supplier.setBusinessLicense(supplierSp.getBusinessLicense());// 营业执照号
	// supplier.setBusinessLocal(supplierSp.getBusinessLocal());// 营业执照所在地
	// supplier.setBusinessValidity(supplierSp.getBusinessValidity());// 营业执照有效期
	// supplier.setTaxpayer(supplierSp.getTaxpayer());// 纳税人识别号
	// supplier.setInstitutional(supplierSp.getInstitutional());// 组织机构代码
	// supplier.setTallageNo(supplierSp.getTallageNo());// 税务登记证号
	// supplier.setTaxRate(supplierSp.getTaxRate());// 税率
	// supplier.setCouponsAllocationProportion(supplierSp.getCouponsAllocationProportion());//
	// 优惠券分摊比例
	// supplier.setIsValid(2);// 供应商状态
	// supplier.setSupplierType(supplierSp.getSupplierType());// 供应商类型
	// supplier.setIsInputYougouWarehouse(supplierSp.getIsInputYougouWarehouse());//
	// 是否入优购仓库
	// supplier.setIsUseYougouWms(supplierSp.getIsUseYougouWms());// 是否使用优购WMS
	// supplier.setSetOfBooksCode(supplierSp.getSetOfBooksCode());// 成本帐套编号
	// supplier.setSetOfBooksName(supplierSp.getSetOfBooksName());// 成本帐套名称
	// supplier.setCreator(user != null ? user.getUsername() : null);// 创建人
	// supplier.setUpdateUser(user != null ? user.getUsername() : null);// 修改人
	// supplier.setUpdateDate(new Date());// 修改时间 添加时默认为创建时间
	// supplier.setDeleteFlag(1);// 未删除标志
	// supplier.setShipmentType(supplierSp.getShipmentType());// 按发货预结算
	// //supplier.setTradeCurrency(supplierSp.getTradeCurrency());
	//
	// //招商商家绑定用户
	// com.yougou.merchant.api.supplier.vo.MerchantUser merchantUser = new
	// com.yougou.merchant.api.supplier.vo.MerchantUser();
	// merchantUser.setLoginName(supplierSp.getLoginAccount());// 登录名称
	// // 对密码进行MD5加密
	// String password = Md5Encrypt.md5(supplierSp.getLoginPassword());
	// merchantUser.setId(UUIDGenerator.getUUID());
	// merchantUser.setPassword(password);// 登录密码
	// merchantUser.setMerchantCode(supplier.getSupplierCode());// 商家编号
	// merchantUser.setUserName("");// 商家真实信息
	// merchantUser.setCreateTime(DateUtil.getDateTime(new Date()));
	// merchantUser.setStatus(1);// 状态 1表示可用
	// merchantUser.setIsAdministrator(1); // 1表示管理员
	// merchantUser.setDeleteFlag(1);// 未删除标志
	// merchantUser.setIsYougouAdmin(0);
	// supplier.setUser(merchantUser);
	//
	// this.bulidSupplierBrandCat(supplier, bankNoHidden, catNameHidden);
	//
	// //保存
	// supplierService.insertSupplierVoForMerchant(supplier);
	// } catch (Exception e) {
	// logger.error("新增招商供应商异常.", e);
	// return false;
	// }
	// return true;
	// }
	//
	// @Override
	// public boolean updateMerchant(HttpServletRequest req, SupplierSp
	// supplierSp, String bankNoHidden, String catNameHidden) {
	// SupplierVo supplier = new SupplierVo();
	// try {
	// SystemmgtUser user = GetSessionUtil.getSystemUser(req);
	// supplier.setId(supplierSp.getId()); // 主键Id
	// supplier.setUpdateTimestamp(System.currentTimeMillis());// 时间戳
	// supplier.setSupplier(supplierSp.getSupplier());// 商家名称
	// supplier.setSupplierCode(supplierSp.getSupplierCode());// 商家编号
	// supplier.setContact(supplierSp.getContact());// 开户名称
	// supplier.setAccount(supplierSp.getAccount());// 开户银行账号
	// supplier.setSubBank(supplierSp.getSubBank());// 开户子银行
	// supplier.setBankLocal(supplierSp.getBankLocal());// 开户所在地
	// supplier.setBusinessLicense(supplierSp.getBusinessLicense());// 营业执照号
	// supplier.setBusinessLocal(supplierSp.getBusinessLocal());// 营业执照所在地
	// supplier.setBusinessValidity(supplierSp.getBusinessValidity());// 营业执照有效期
	// supplier.setTaxpayer(supplierSp.getTaxpayer());// 纳税人识别号
	// supplier.setInstitutional(supplierSp.getInstitutional());// 组织机构代码
	// supplier.setTallageNo(supplierSp.getTallageNo());// 税务登记证号
	// supplier.setTaxRate(supplierSp.getTaxRate());// 税率
	// supplier.setCouponsAllocationProportion(supplierSp.getCouponsAllocationProportion());//
	// 优惠券分摊比例
	// supplier.setIsValid(supplierSp.getIsValid());// 供应商状态
	// supplier.setSupplierType(supplierSp.getSupplierType());// 供应商类型
	// supplier.setIsInputYougouWarehouse(supplierSp.getIsInputYougouWarehouse());//
	// 是否入优购仓库
	// supplier.setSetOfBooksCode(supplierSp.getSetOfBooksCode());// 成本帐套编号
	// supplier.setSetOfBooksName(supplierSp.getSetOfBooksName());// 成本帐套名称
	// //supplier.setCreator(user != null ? user.getUsername() : null);// 创建人
	// supplier.setUpdateUser(user != null ? user.getUsername() : null);// 修改人
	// supplier.setUpdateDate(new Date());// 修改时间 添加时默认为创建时间
	// supplier.setDeleteFlag(1);// 未删除标志
	// supplier.setShipmentType(supplierSp.getShipmentType());// 按发货预结算
	// supplier.setInventoryCode(supplierSp.getInventoryCode());//仓库编码
	// /*if(supplierSp.getTradeCurrency() != null) {
	// supplier.setTradeCurrency(supplierSp.getTradeCurrency());
	// }*/
	// supplier.setIsUseYougouWms(supplierSp.getIsUseYougouWms());//是否使用优购WMS
	//
	// this.bulidSupplierBrandCat(supplier, bankNoHidden, catNameHidden);
	//
	// //保存
	// supplierService.updateSupplierVoForMerchant(supplier);
	// } catch (Exception e) {
	// logger.error("修改招商供应商异常.", e);
	// return false;
	// }
	// return true;
	// }

	@Override
	public boolean updateHistoryMerchants(HttpServletRequest req,
			SupplierSp supplierSp, String bankNoHidden, String catNameHidden) {
		SupplierVo supplier = new SupplierVo();

		try {
			supplier.setId(supplierSp.getId());
			supplier.setSupplierCode(supplierSp.getSupplierCode());
			SystemmgtUser user = GetSessionUtil.getSystemUser(req);
			supplier.setCreator(user != null ? user.getUsername() : null);

			// 招商商家绑定用户
			com.yougou.merchant.api.supplier.vo.MerchantUser merchantUser = new com.yougou.merchant.api.supplier.vo.MerchantUser();
			merchantUser.setLoginName(supplierSp.getLoginAccount());// 登录名称
			// 对密码进行MD5加密
			String password = Md5Encrypt.md5(supplierSp.getLoginPassword());
			merchantUser.setId(UUIDGenerator.getUUID());
			merchantUser.setPassword(password);// 登录密码
			merchantUser.setMerchantCode(supplierSp.getSupplierCode());// 商家编号
			merchantUser.setUserName("");// 商家真实信息
			merchantUser.setCreateTime(DateUtil.getDateTime(new Date()));
			merchantUser.setStatus(1);// 状态 1表示可用
			merchantUser.setIsAdministrator(1); // 1表示管理员
			merchantUser.setDeleteFlag(1);// 未删除标志
			merchantUser.setIsYougouAdmin(0);
			supplier.setUser(merchantUser);

			// 设置品牌分类授权
			this.bulidSupplierBrandCat(supplier, bankNoHidden, catNameHidden);

			// save
			brandcatApi.updateLimitBrandCatObj(supplier);
		} catch (Exception e) {
			logger.error("修改招商供应商品牌分类以及用户信息异常.", e);
			return false;
		}

		return true;
	}

	@Override
	public boolean updateMerchantsBankAndCat( SupplierVo supplier, String bankNoHidden, String catNameHidden) {
		try {
			// 设置品牌分类授权
			this.bulidSupplierBrandCat(supplier, bankNoHidden, catNameHidden);
			// save
			brandcatApi.updateLimitBrandCatObj(supplier);
		} catch (Exception e) {
			logger.error("修改招商供应商品牌分类授权异常.", e);
			return false;
		}

		return true;
	}

	/**
	 * 组装授权品类
	 * 
	 * @param supplier
	 * @param bankNoHidden
	 * @param catNameHidden
	 */
	private void bulidSupplierBrandCat(SupplierVo supplier,
			String bankNoHidden, String catNameHidden) {
		// 品牌|分类关系
		if (StringUtils.isNotBlank(bankNoHidden)) { // bankNoHidden:[Hfjt;POwu;CMs6]
			// 授权品牌
			List<BrandVo> _brand_list = new ArrayList<BrandVo>();
			// 授权分类
			List<CatVo> _cat_list = new ArrayList<CatVo>();
			// 授权品牌与分类关系
			List<BrandCatRelation> _brandcat_list = new ArrayList<BrandCatRelation>();

			String[] _arraybank = bankNoHidden.split(";");

			Map<String, String> brankNoIdMap = new HashMap<String, String>();
			Map<String, String> catStructNameIdMap = new HashMap<String, String>();
			if (ArrayUtils.isNotEmpty(_arraybank)) {
				BrandVo _temp_brand = null;
				for (String _brandNo : _arraybank) {
					_temp_brand = new BrandVo();
					_temp_brand.setId(UUIDGenerator.getUUID());
					_temp_brand.setSupplyId(supplier.getId());
					_temp_brand.setBrandNo(_brandNo);

					_brand_list.add(_temp_brand);
					// 组织品牌授权Id与No的对应关系
					brankNoIdMap.put(_brandNo, _temp_brand.getId());
				}
				supplier.setBrandVos(_brand_list);
			}

			// 分类字符串
			// [brand_no;struct_name_brand_no;struct_name_brand_no;struct_name]
			if (StringUtils.isNotBlank(catNameHidden)) {
				Set<String> catSet = new HashSet<String>();
				String[] catNameStr = catNameHidden.split("_");
				for (String string : catNameStr) {
					String[] catStr = string.split(";");
					catSet.add(catStr[1]);
				}
				if (CollectionUtils.isNotEmpty(catSet)) {
					CatVo _temp_cat = null;
					for (String set : catSet) {
						_temp_cat = new CatVo();
						_temp_cat.setId(UUIDGenerator.getUUID());
						_temp_cat.setSupplyId(supplier.getId());
						_temp_cat.setStructName(set);
						Category c = commodityBaseApiService
								.getCategoryByStructName(set);
						_temp_cat.setCatNo(c.getCatNo());

						_cat_list.add(_temp_cat);
						// 分类授权Id与structname的对应关系
						catStructNameIdMap.put(set, _temp_cat.getId());
					}
					supplier.setCatVos(_cat_list);
				}

				BrandCatRelation relation = null;
				for (String string : catNameStr) {
					String[] catStr = string.split(";");
					relation = new BrandCatRelation();
					relation.setId(UUIDGenerator.getUUID());
					relation.setBrandId(brankNoIdMap.get(catStr[0]));
					relation.setCatId(catStructNameIdMap.get(catStr[1]));
					_brandcat_list.add(relation);
				}
				if (CollectionUtils.isNotEmpty(_brandcat_list)) {
					supplier.setBrandcatRelations(_brandcat_list);
				}
			}
		}
	}

	/** 缓存商家绑定邮箱 */
	public static final String C_USER_ACTIVATEPASSWORD_ID_TIME = "com.yougou.kaidian.merchant.user.activatepassword.id";

	@Override
	public boolean updateEmail(String id, String email, String operatorName)
			throws Exception {
		// 发送邮件
		String activaturl = emailActivateSrc + "?code=" + id;
		// 邮件内容
		StringBuffer strB = new StringBuffer();
		strB.append("<img src='http://s1.ygimg.cn/template/common/images/logo-yg.png'>");
		strB.append("<div>--------------------------------------------------------------------------------------------------</div>");
		strB.append("<div><strong>亲爱的优购供应商： </strong></div>");
		strB.append("<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 您设置的邮件地址已经与你的账号绑定，请妥善保管。请点击以下链接激活邮箱绑定!若无法点击，请将链接复制到浏览器打开。</div>");
		strB.append("<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 提示：该链接只能在收到邮件后访问1次，超过2个小时或者访问超过1次，链接将自动失效。</div>");
		strB.append("<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href='"
				+ activaturl + "'>" + activaturl + "</a></div>");

		// 比较邮箱是否一致
		com.yougou.merchant.api.supplier.vo.MerchantUser oldUser = merchantsApi
				.getMerchantUserById(id);
		if (StringUtils.isNotBlank(oldUser.getEmail())
				&& oldUser.getEmail().equals(email)
				&& oldUser.getEmailstatus() == 1)
			return true;

		com.yougou.merchant.api.supplier.vo.MerchantUser vo = new com.yougou.merchant.api.supplier.vo.MerchantUser();
		vo.setId(id);
		vo.setEmail(email);
		vo.setEmailstatus(NumberUtils.INTEGER_ZERO); // 设置未激活
		vo.setDeleteFlag(NumberUtils.INTEGER_ONE);
		merchantsApi.updateMerchantUser(vo);
		this.sandMail(email, "优购-商家中心-激活账户邮箱", strB.toString());
		this.redisTemplate.opsForHash().put(C_USER_ACTIVATEPASSWORD_ID_TIME,
				id, System.currentTimeMillis());

		// 增加修改邮箱日志
		MerchantOperationLog log = new MerchantOperationLog();
		log.setId(UUIDGenerator.getUUID());
		log.setMerchantCode(oldUser.getMerchantCode());
		log.setOperator(operatorName);
		log.setOperated(new Date());
		log.setOperationType(MerchantOperationLog.OperationType.ACCOUNT);
		String oldEmail = "";
		if (!StringUtils.isEmpty(oldUser.getEmail())) {
			oldEmail = oldUser.getEmail();
		}
		log.setOperationNotes(MessageFormat.format("修改商家原绑定邮箱【{0}】至新绑定邮箱【{1}】",
				oldEmail, email));
		merchantsApi.saveMerchantOperationLog(log);

		return true;
	}

	@Override
	public boolean updateEmail(MerchantUser merchantUser, String operatorName)
			throws Exception {
		String id = merchantUser.getId();
		String email = merchantUser.getEmail().trim();
		// 发送邮件
		String activaturl = emailActivateSrc + "?code=" + id;
		// 邮件内容
		StringBuffer strB = new StringBuffer();
		strB.append("<img src='http://s1.ygimg.cn/template/common/images/logo-yg.png'>");
		strB.append("<div>--------------------------------------------------------------------------------------------------</div>");
		strB.append("<div><strong>亲爱的优购供应商： </strong></div>");
		strB.append("<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 您设置的邮件地址已经与你的账号绑定，请妥善保管。请点击以下链接激活邮箱绑定!若无法点击，请将链接复制到浏览器打开。</div>");
		strB.append("<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 提示：该链接只能在收到邮件后访问1次，超过2个小时或者访问超过1次，链接将自动失效。</div>");
		strB.append("<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href='"
				+ activaturl + "'>" + activaturl + "</a></div>");

		// 比较邮箱是否一致
		com.yougou.merchant.api.supplier.vo.MerchantUser oldUser = merchantsApi
				.getMerchantUserById(id);
		if (StringUtils.isNotBlank(oldUser.getEmail())
				&& oldUser.getEmail().equals(email)
				&& oldUser.getEmailstatus() == 1)
			return true;

		com.yougou.merchant.api.supplier.vo.MerchantUser vo = new com.yougou.merchant.api.supplier.vo.MerchantUser();
		vo.setId(id);
		vo.setEmail(email);
		vo.setEmailstatus(NumberUtils.INTEGER_ZERO); // 设置未激活
		vo.setDeleteFlag(NumberUtils.INTEGER_ONE);
		merchantsApi.updateMerchantUser(vo);
		this.sandMail(email, "优购-商家中心-激活账户邮箱", strB.toString());
		this.redisTemplate.opsForHash().put(C_USER_ACTIVATEPASSWORD_ID_TIME,
				id, System.currentTimeMillis());

		// 增加修改邮箱日志
		MerchantOperationLog log = new MerchantOperationLog();
		log.setId(UUIDGenerator.getUUID());
		log.setMerchantCode(oldUser.getId());// 修改
		log.setOperator(operatorName);
		log.setOperated(new Date());
		log.setUserId(id);//
		log.setRemark(merchantUser.getRemark());
		log.setOperationType(MerchantOperationLog.OperationType.ACCOUNT);//
		String oldEmail = "";
		if (!StringUtils.isEmpty(oldUser.getEmail())) {
			oldEmail = oldUser.getEmail();
		}
		log.setOperationNotes(MessageFormat.format("修改商家原绑定邮箱【{0}】至新绑定邮箱【{1}】",
				oldEmail, email));
		// TODO 备注

		merchantsApi.saveMerchantOperationLog(log);

		return true;
	}

	/**
	 * 发送邮件
	 */
	public void sandMail(String addresss, String title, String content)
			throws Exception {
		// TODO Auto-generated method stub
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setToAddress(addresss);
		mailInfo.setTitle(title);
		mailInfo.setContent(content);
		mailInfo.setSubject(SubjectIdType.SUBJECT_ID_MERCHANT_FINDPWD);
		mailInfo.setModelType(com.yougou.component.email.model.ModelType.MODEL_TYPE_MERCHANT_FINDPWD);
		emailApi.sendNow(mailInfo);
	}

	@Override
	public boolean deleteMerchantUser(String id, String user) {
		if (StringUtils.isBlank(id))
			return false;

		com.yougou.merchant.api.supplier.vo.MerchantUser tempUser = merchantsApi
				.getMerchantUserById(id);
		try {
			com.yougou.merchant.api.supplier.vo.MerchantUser vo = new com.yougou.merchant.api.supplier.vo.MerchantUser();
			vo.setId(id);
			vo.setDeleteFlag(NumberUtils.INTEGER_ZERO);
			merchantsApi.updateMerchantUser(vo);

			// 记录日志
			MerchantOperationLog log = new MerchantOperationLog();
			log.setId(UUIDGenerator.getUUID());
			log.setMerchantCode(tempUser.getMerchantCode());
			log.setOperator(user);
			log.setOperated(new Date());
			log.setOperationType(MerchantOperationLog.OperationType.ACCOUNT);
			log.setOperationNotes(MessageFormat.format("删除商家帐号【{0}】",
					tempUser.getLoginName()));
			merchantsApi.saveMerchantOperationLog(log);
		} catch (Exception e) {
			logger.error(
					MessageFormat.format("删除商家账号[{0}]异常",
							tempUser.getLoginName()), e);
			return false;
		}
		return true;
	}

	@Override
	public boolean updateMerchantState(String id, Integer status, String user) {
		if (StringUtils.isBlank(id) || null == status)
			return false;

		com.yougou.merchant.api.supplier.vo.MerchantUser tempUser = merchantsApi
				.getMerchantUserById(id);
		String operationNotes = NumberUtils.INTEGER_ONE.equals(status) ? "启用商家帐号【{0}】"
				: "锁定商家帐号【{0}】";
		try {
			com.yougou.merchant.api.supplier.vo.MerchantUser vo = new com.yougou.merchant.api.supplier.vo.MerchantUser();
			vo.setId(id);
			vo.setStatus(status);
			vo.setDeleteFlag(NumberUtils.INTEGER_ONE);
			merchantsApi.updateMerchantUser(vo);

			// 记录日志
			MerchantOperationLog log = new MerchantOperationLog();
			log.setId(UUIDGenerator.getUUID());
			log.setMerchantCode(tempUser.getMerchantCode());
			log.setOperator(user);
			log.setOperated(new Date());
			log.setOperationType(MerchantOperationLog.OperationType.ACCOUNT);
			log.setOperationNotes(MessageFormat.format(operationNotes,
					tempUser.getLoginName()));
			merchantsApi.saveMerchantOperationLog(log);
		} catch (Exception e) {
			logger.error(
					MessageFormat.format(operationNotes,
							tempUser.getLoginName())
							+ " 异常。", e);
			return false;
		}
		return true;
	}

	@Override
	public boolean addLinkmanList(SupplierContact contact, String operator)
			throws Exception {
		Integer result = this.purchaseApiService.insertSupplierContact(contact);

		/** 添加商家联系人日志 Modifier by yang.mq **/
		MerchantOperationLog log = new MerchantOperationLog();
		log.setId(UUIDGenerator.getUUID());
		String supplierCode = this.getSupplierVoById(contact.getSupplyId())
				.getSupplierCode();
		log.setMerchantCode(supplierCode);
		log.setOperator(operator);
		log.setOperated(new Date());
		log.setOperationType(MerchantOperationLog.OperationType.CONTACT);
		log.setOperationNotes(MessageFormat.format("新建商家({0})联系人({1})",
				new Object[] { supplierCode, contact.getContact() }));
		merchantsApi.saveMerchantOperationLog(log);

		return result > 0 ? true : false;
	}

	@Override
	public boolean updateLinkmanList(SupplierContact contact, String operator)
			throws Exception {
		Integer result = this.purchaseApiService.updateSupplierContact(contact);

		/** 添加商家联系人日志 Modifier by yang.mq **/
		MerchantOperationLog log = new MerchantOperationLog();
		String supplierCode = this.getSupplierVoById(contact.getSupplyId())
				.getSupplierCode();
		log.setId(UUIDGenerator.getUUID());
		log.setMerchantCode(supplierCode);
		log.setOperator(operator);
		log.setOperated(new Date());
		log.setOperationType(MerchantOperationLog.OperationType.CONTACT);
		log.setOperationNotes(MessageFormat.format("修改商家({0})联系人({1})",
				new Object[] { supplierCode, contact.getContact() }));
		merchantsApi.saveMerchantOperationLog(log);

		return result > 0 ? true : false;
	}

	/********* 新商家入驻 . Start from here . Add by LQ on 20150714 ************/

	@Override
	public int saveMerchantRejectedAddress(
			MerchantRejectedAddressVo merchantRejectedAddressVo)
			throws Exception {

		return merchantSupplierExpandMapper
				.saveRejectedAddress(merchantRejectedAddressVo);
	}

	@Override
	public int updateRejectedAddress(
			MerchantRejectedAddressVo merchantRejectedAddressVo)
			throws Exception {
		MerchantRejectedAddressVo vo = merchantSupplierExpandMapper
				.selectRejectedAddressById(merchantRejectedAddressVo.getId());
		if (null != vo) {
			return merchantSupplierExpandMapper
					.updateRejectedAddress(merchantRejectedAddressVo);
		} else {
			return merchantSupplierExpandMapper
					.saveRejectedAddress(merchantRejectedAddressVo);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = MerchantSystemException.class)
	public void updateSupplierMerchant(Map<String, Object> params)
			throws Exception {
		Supplier supplier = (Supplier) params.get("supplier");
		// String supplierId = (String)params.get("supplierId");
		// SupplierContract supplierContract =
		// (SupplierContract)params.get("supplierContract");
		MerchantUser merchantUser = (MerchantUser) params.get("merchantUser");
		MerchantRejectedAddressVo rejectedAddress = (MerchantRejectedAddressVo) params
				.get("rejectedAddress");
		List<SupplierContact> contactVoList = (List<SupplierContact>) params
				.get("contactVoList");
		MerchantSupplierExpand supplierExpand = (MerchantSupplierExpand) params
				.get("supplierExpand");
		MerchantPictureCatalog pictureCatalog = (MerchantPictureCatalog) params
				.get("pictureCatalog");

		Integer result = purchaseApiService.updateSupplier(supplier);
		if (result > 0) {// 调商品接口保存商家主表成功
			// 待改造：调批量插入接口
			// if( null!=contactVoList && 0<contactVoList.size() ){
			// purchaseApiService.insertSupplierContactList( contactVoList);
			// }
			if (null != contactVoList && 0 < contactVoList.size()) {
				for (int i = 0; i < contactVoList.size(); i++) {
					// 6种联系人有非必填属性，这里可能为新增。
					Integer updateNum = purchaseApiService
							.updateSupplierContact(contactVoList.get(i));
					if (null == updateNum || updateNum < 1) {
						purchaseApiService.insertSupplierContact(contactVoList
								.get(i));
					}
				}
			}
			//
			updateRejectedAddress(rejectedAddress);
			//
			updateMerchantUser(merchantUser);
			// 保存合同
			supplierContractService.updateContractForSupplier(params);

			// 更新扩展表的到期剩余天数
			SupplierContract supplierContract = (SupplierContract) params
					.get("supplierContract");
			supplierExpand.setContractRemainingDays(supplierContract
					.getContractRemainingDays());
			supplierExpand.setMarkRemainingDays(supplierContract
					.getMarkRemainingDays());
			// 保存扩展表信息
			updateSupplierExpand(supplierExpand);

		} else {// 调商品接口保存商家主表失败
			throw new BusinessException("调商品接口保存修改商家主表失败.");
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = MerchantSystemException.class)
	public String saveSupplierMerchant(Map<String, Object> params)
			throws Exception {

		Supplier supplier = (Supplier) params.get("supplier");
		MerchantUser merchantUser = (MerchantUser) params.get("merchantUser");
		MerchantRejectedAddressVo rejectedAddress = (MerchantRejectedAddressVo) params
				.get("rejectedAddress");
		List<SupplierContact> contactVoList = (List<SupplierContact>) params
				.get("contactVoList");
		MerchantSupplierExpand supplierExpand = (MerchantSupplierExpand) params
				.get("supplierExpand");
		MerchantPictureCatalog pictureCatalog = (MerchantPictureCatalog) params
				.get("pictureCatalog");
		Integer result = purchaseApiService.insertSupplier(supplier);
		if (result > 0) {// 调商品接口保存商家主表成功
			// 待改造：调批量插入接口
			// if( null!=contactVoList && 0<contactVoList.size() ){
			// purchaseApiService.insertSupplierContactList( contactVoList);
			// }
			// try {
			for (int i = 0; i < contactVoList.size(); i++) {
				purchaseApiService.insertSupplierContact(contactVoList.get(i));
			}
			//
			saveMerchantRejectedAddress(rejectedAddress);

			//
			saveMerchantUser(merchantUser);
			// 保存合同
			supplierContractService.saveContractForSupplier(params);

			// 更新扩展表的到期剩余天数
			SupplierContract supplierContract = (SupplierContract) params
					.get("supplierContract");
			supplierExpand.setContractRemainingDays(supplierContract
					.getContractRemainingDays());
			supplierExpand.setMarkRemainingDays(supplierContract
					.getMarkRemainingDays());
			// 保存扩展表信息
			saveSupplierExpand(supplierExpand);
			// 为供应商添加图片空间默认商品目录
			pictureService.insertPicCatalog(pictureCatalog);
			// } catch (Exception e) {
			// // 手动回滚
			// supplier.setDeleteFlag( MerchantConstant.DELETED );
			// supplier.setSupplier("可删除");
			// purchaseApiService.updateSupplier(supplier);
			// //throw new BusinessException(e.getMessage());
			// throw new BusinessException("本地保存商家附属表失败.");
			// }

		} else {// 调商品接口保存商家主表失败
			throw new BusinessException("调商品接口保存商家主表失败.");
		}

		return "success";
	}

	/**
	 * 根据商家编号查询商家登录信息
	 */
	@Override
	public MerchantUser getMerchantsBySuppliceCode(String supplierCode) {
		MerchantUser merchantUser = new MerchantUser();
		merchantUser.setMerchantCode(supplierCode);
		merchantUser.setDeleteFlag(MerchantConstant.NOT_DELETED);
		merchantUser.setIsAdministrator(MerchantConstant.YES);
		List<MerchantUser> merchantUserList = merchantSupplierExpandMapper
				.queryMerchantUserList(merchantUser);
		if (merchantUserList != null && merchantUserList.size() > 0) {
			merchantUser = merchantUserList.get(0);
		}
		return merchantUser;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = MerchantSystemException.class)
	public int saveMerchantUser(MerchantUser merchantUser)
			throws BusinessException {
		int result;
		try {
			result = merchantSupplierExpandMapper
					.insertMerchantUser(merchantUser);
		} catch (Exception e) {
			logger.error("创建商家的登陆账号信息失败！", e);
			throw new BusinessException("创建商家的登陆账号信息失败！");
		}

		return result;
	}

	// @Override
	// public int saveMerchantUser(MerchantUser merchantUser) throws Exception{
	// return merchantSupplierExpandMapper.insertMerchantUser(merchantUser);
	//
	// }

	// @Override
	// public int updateMerchantUser(MerchantUser merchantUser) throws
	// BusinessException {
	// int result;
	// try {
	// result = merchantSupplierExpandMapper.updateMerchantUser(merchantUser);
	// } catch (Exception e) {
	// logger.error("更新商家的登陆账号信息失败！",e);
	// throw new BusinessException("创建商家的登陆账号信息失败！");
	// }
	//
	// return result;
	// }
	@Override
	public int updateMerchantUser(MerchantUser merchantUser) {
		MerchantUser vo = merchantSupplierExpandMapper
				.selectMerchantUserById(merchantUser.getId());
		if (null != vo) {
			return merchantSupplierExpandMapper
					.updateMerchantUser(merchantUser);
		} else {
			return merchantSupplierExpandMapper
					.insertMerchantUser(merchantUser);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = MerchantSystemException.class)
	public int saveSupplierExpand(MerchantSupplierExpand supplierExpand)
			throws BusinessException {
		int result;
		try {
			result = merchantSupplierExpandMapper.insert(supplierExpand);
		} catch (Exception e) {
			logger.error("插入招商商家的扩展表详细信息失败！", e);
			throw new BusinessException("插入招商商家的扩展表详细信息失败！");
		}

		return result;
	}

	// @Override
	// public int saveSupplierExpand( MerchantSupplierExpand supplierExpand )
	// throws Exception {
	// // TODO Auto-generated method stub
	// return merchantSupplierExpandMapper.insert(supplierExpand);
	// }

	@Override
	public int updateSupplierExpand(MerchantSupplierExpand supplierExpand)
			throws BusinessException {
		int result;
		try {
			result = merchantSupplierExpandMapper
					.updateByPrimaryKeySelective(supplierExpand);
		} catch (Exception e) {
			logger.error("更新招商商家的扩展表详细信息失败！", e);
			throw new BusinessException("更新招商商家的扩展表详细信息失败！");
		}
		return result;
	}

	// @Override
	// public int updateSupplierExpand( MerchantSupplierExpand supplierExpand )
	// throws Exception {
	// // TODO Auto-generated method stub
	// return
	// merchantSupplierExpandMapper.updateByPrimaryKeySelective(supplierExpand);
	// }

	@Override
	public MerchantSupplierExpand getSupplierExpandVoById(String supplierId) {
		MerchantSupplierExpand supplierExpand = merchantSupplierExpandMapper
				.selectBySupplierId(supplierId);
		if (supplierExpand != null) {
			return supplierExpand;
		} else {
			return null;
		}
	}

	@Override
	public MerchantSupplierExpand getSupplierExpandVoByCode(String merchantCode) {
		MerchantSupplierExpand supplierExpand = merchantSupplierExpandMapper
				.selectByMerchantCode(merchantCode);
		if (supplierExpand != null) {
			return supplierExpand;
		} else {
			return null;
		}
	}

	@Override
	public List<ContactsVo> getContactsBySupplierId(String supplierId) {
		return merchantSupplierExpandMapper.getContactsBySupplierId(supplierId);
	}

	@Override
	public MerchantRejectedAddressVo getRejectedAddressBySupplierCode(
			String supplierCode) {
		List<MerchantRejectedAddressVo> list = merchantSupplierExpandMapper
				.getRejectedAddressBySupplierCode(supplierCode);
		if (null != list && 0 < list.size()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void insertMerchantLog(MerchantOperationLog log) {
		supplierVoMapper.insertMerchantLog(log);
	}

	/**
	 * 更新资质
	 * 
	 * @param params
	 * @throws Exception
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = MerchantSystemException.class)
	public void updateNatural(MerchantSupplierExpand supplierExpand,
			AttachmentFormVo attachmentFormVo) throws Exception {

		supplierContractService
				.saveOrUpdateByAttachmentFormVo(attachmentFormVo);
		if (null != attachmentFormVo.getMarkRemainingDays()) {
			supplierExpand.setMarkRemainingDays(attachmentFormVo.getMarkRemainingDays());
		}
		supplierExpand.setUptateTime(DateUtil2.getCurrentDateTimeToStr2());
		merchantSupplierExpandMapper.updateByPrimaryKeySelective(supplierExpand);
		//保存品类授权
		SupplierVo supplier = new SupplierVo();
		supplier.setId(attachmentFormVo.getSupplierId());
		updateMerchantsBankAndCat( supplier,
					   attachmentFormVo.getBankNoHidden(), attachmentFormVo.getCatNameHidden() );
	}

	@Override
	public boolean updateMobile(String id, String mobile, String loginName) {
		com.yougou.merchant.api.supplier.vo.MerchantUser oldUser = merchantsApi
				.getMerchantUserById(id);
		if (StringUtils.isNotBlank(oldUser.getMobileCode())
				&& oldUser.getMobileCode().equals(mobile))
			return true;

		com.yougou.merchant.api.supplier.vo.MerchantUser vo = new com.yougou.merchant.api.supplier.vo.MerchantUser();
		vo.setId(id);
		vo.setMobileCode(mobile);
		vo.setDeleteFlag(NumberUtils.INTEGER_ONE);
		merchantsApi.updateMerchantUser(vo);
		// 增加修改手机日志
		MerchantOperationLog log = new MerchantOperationLog();
		log.setId(UUIDGenerator.getUUID());
		log.setMerchantCode(oldUser.getMerchantCode());
		log.setOperator(loginName);
		log.setOperated(new Date());
		log.setOperationType(MerchantOperationLog.OperationType.ACCOUNT);
		String oldEmail = "";
		if (!StringUtils.isEmpty(oldUser.getMobileCode())) {
			oldEmail = oldUser.getMobileCode();
		}
		log.setOperationNotes(MessageFormat.format("商家手机号码从【{0}】修改为【{1}】",
				oldEmail, mobile));
		merchantsApi.saveMerchantOperationLog(log);
		return true;
	}

	@Override
	public boolean updateMobile(MerchantUser merchantUser, String loginName) {
		String id = merchantUser.getId();
		String mobile = merchantUser.getMobileCode().trim();
		com.yougou.merchant.api.supplier.vo.MerchantUser oldUser = merchantsApi
				.getMerchantUserById(id);
		if (StringUtils.isNotBlank(oldUser.getMobileCode())
				&& oldUser.getMobileCode().equals(mobile))
			return true;

		com.yougou.merchant.api.supplier.vo.MerchantUser vo = new com.yougou.merchant.api.supplier.vo.MerchantUser();
		vo.setId(id);
		vo.setMobileCode(mobile);
		vo.setDeleteFlag(NumberUtils.INTEGER_ONE);
		merchantsApi.updateMerchantUser(vo);
		// 增加修改手机日志
		MerchantOperationLog log = new MerchantOperationLog();
		log.setId(UUIDGenerator.getUUID());
		log.setMerchantCode(oldUser.getMerchantCode());
		log.setUserId(id);//
		log.setOperator(loginName);
		log.setOperated(new Date());
		log.setRemark(merchantUser.getRemark());
		log.setOperationType(MerchantOperationLog.OperationType.ACCOUNT);//
		String oldEmail = "";
		if (!StringUtils.isEmpty(oldUser.getMobileCode())) {
			oldEmail = oldUser.getMobileCode();
		}
		log.setOperationNotes(MessageFormat.format("商家手机号码从【{0}】修改为【{1}】",
				oldEmail, mobile));
		// TODO 备注

		merchantsApi.saveMerchantOperationLog(log);
		return true;
	}

	@Override
	public boolean queryExistContactOfThisType(SupplierContact contact)
			throws Exception {
		// TODO Auto-generated method stub
		int count = merchantSupplierExpandMapper.queryExistContactOfThisType(
				contact.getSupplyId(), contact.getType());

		if (count > 0) {
			return true;
		} else {
			return false;
		}

	}
	
	/**
	 * 获取商家信息
	 * @param map
	 * @return
	 */
	public  List<Map<String,Object>>  getMerchantInfo(Map<String,List<String>> map){
		return merchantMapper.getMerchantInfo(map);
	
	}
}
