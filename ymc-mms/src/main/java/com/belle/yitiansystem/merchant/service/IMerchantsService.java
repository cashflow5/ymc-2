package com.belle.yitiansystem.merchant.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.belle.finance.costsettlement.costsetofbooks.model.vo.CostSetofBooks;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.other.model.pojo.SupplierContactSp;
import com.belle.other.model.pojo.SupplierContract;
import com.belle.other.model.pojo.SupplierSp;
import com.belle.yitiansystem.merchant.model.pojo.MerchantExpressTemplate;
import com.belle.yitiansystem.merchant.model.pojo.MerchantRejectedAddress;
import com.belle.yitiansystem.merchant.model.pojo.MerchantUser;
import com.belle.yitiansystem.merchant.model.pojo.MerchantsAuthority;
import com.belle.yitiansystem.merchant.model.pojo.MerchantsRole;
import com.belle.yitiansystem.merchant.model.pojo.RoleAuthority;
import com.belle.yitiansystem.merchant.model.pojo.UserRole;
import com.belle.yitiansystem.merchant.model.vo.MerchantsVo;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.yougou.kaidian.common.commodity.pojo.Cat;

/**
 * 
 * 招商系统-招商基本信息service接口类
 * 
 * @author wang.m
 * @date 2012-03-05
 * 
 */
public interface IMerchantsService {

	/**
	 * 根据模块父id字段获取权限列表
	 * 
	 */
	public List<MerchantsAuthority> getMerchantsAuthorityByPid(String pid) ;
	/**
	 * 更新权限组状态
	 * 
	 */
	public void update_roleStatus(String id) throws Exception;
	/**
	 * 判断该角色是否已经用户该资源
	 * 
	 */
	public List<RoleAuthority> findRoleAuthoriryList(String roleId);
	/**
	 * 
	 * 根据条件查询招商信息集合数据
	 * 
	 * @author wang.m
	 * @Date 2012-03-05
	 * 
	 */
	@Deprecated
	public PageFinder<Map<String, Object>> queryMerchantsList(Query query, MerchantsVo merchantsVo);
	
	/**
	 * 
	 * 查询所有菜单权限
	 * 
	 * @author wang.m
	 * @Date 2012-03-05
	 * 
	 */
	public List<Map<String, Object>> queryAllMerchantsAuthorityList();
	
	/**
	 * 根据商家ID获取商家品牌列表
	 * 
	 * @param merchantId
	 * @return
	 */
	//public List<Brand> getMerchantBrandListByMerchantId(String merchantId);

	/**
	 * 根据供应商ID查询联系人列表
	 * 
	 * @author wang.m
	 * @Date 2012-03-05
	 */
	@Deprecated
	public PageFinder<Map<String, Object>> querySupplierContactSpList(Query query, MerchantsVo merchantsVo, String supplierId);

	/**
	 * 根据供应商ID查询合同列表
	 * 
	 * @author wang.m
	 * @Date 2012-03-05
	 */
	@Deprecated
	public PageFinder<Map<String, Object>> querysupplierContractList(Query query, MerchantsVo merchantsVo, String supplierId);

	/**
	 * 
	 * 添加联系人信息
	 * 
	 * @Date 2012-03-06
	 * @author wang.m
	 */
	public boolean add_linkmanList(SupplierContactSp supplierContactSp, String suplierName, String operator) throws Exception;

	/**
	 * 
	 * 修改联系人信息
	 * 
	 * @Date 2012-03-06
	 * @author wang.m
	 */
	public boolean update_linkmanList(Query query, ModelMap modelMap, SupplierContactSp supplierContactSp, String suplierName, SystemmgtUser systemmgtUser) throws Exception;

	/**
	 * 
	 * 添加合同信息
	 * 
	 * @Date 2012-03-06
	 * @author wang.m
	 * @throws Exception
	 */
	public boolean add_supplierContract(Query query, ModelMap modelMap, String effective, String failure, SupplierContract supplierContract, String suplierName,
			HttpServletRequest req) throws Exception;

	/**
	 * 
	 * 修改合同信息
	 * 
	 * @Date 2012-03-06
	 * @author wang.m
	 * @throws Exception
	 */
	public boolean update_supplierContract(Query query, ModelMap modelMap, String effective, String failure, SupplierContract supplierContract, String suplierName,
			HttpServletRequest req) throws Exception;

	/**
	 * 
	 * 判断联系人表中是否存在该手机号码
	 * 
	 * @param telePhone
	 *            电子邮箱
	 * @Date 2012-03-06
	 * @author wang.m
	 */
	public boolean existsTelePhone(String telePhone);

	/**
	 * 
	 * 判断联系人表中是否存在该电子邮箱
	 * 
	 * @param email
	 *            电子邮箱
	 * @Date 2012-03-06
	 * @author wang.m
	 */
	public boolean existsEmail(String email);

	/**
	 * 查询商家信息列表
	 * 
	 * @return List<SupplierSp>
	 */
	public List<SupplierSp> getSupplies(String supplierspName);

	/**
	 * 
	 * 根据联系人ID加载修改页面数据
	 * 
	 * @Date 2012-03-06
	 * @author wang.m
	 * @throws Exception
	 */
	public SupplierContactSp initial_linkmanList(Query query, ModelMap modelMap, String id);

	/**
	 * 
	 * 根据合同ID加载修改页面数据
	 * 
	 * @Date 2012-03-06
	 * @author wang.m
	 * @throws Exception
	 */
	public SupplierContract initial_supplierContract(Query query, ModelMap modelMap, String id);

	/**
	 * 
	 * 判断商家登录名称是否已经存在
	 * 
	 * @Date 2012-03-07
	 * @author wang.m
	 */
	public boolean exitsLoginAccount(String loginAccount);

	/**
	 * 
	 * 添加普通供应商信息
	 * 
	 * @Date 2012-03-07
	 * @author wang.m
	 * @throws Exception
	 */
	public Integer addSupplier(HttpServletRequest req, SupplierSp supplierSp);

	/**
	 * 
	 * 添加招商供应商信息
	 * 
	 * @Date 2012-03-07
	 * @author wang.m
	 * @throws Exception
	 */
	public Integer addMerchant(HttpServletRequest req, SupplierSp supplierSp, String bankNameHidden, String catNameHidden);

	/**
	 * 
	 * 修改商家信息
	 * 
	 * @Date 2012-03-07
	 * @author wang.m
	 */
	public boolean update_merchants(ModelMap modelMap, Query query, SupplierSp supplierSp, HttpServletRequest req, String bankName, String catName);

	/**
	 * 
	 * 修改招商供应商信息
	 * 
	 * @Date 2012-03-07
	 * @author wang.m
	 * @throws Exception
	 */
	public Integer updateMerchant(HttpServletRequest req, SupplierSp supplierSp, String bankNameHidden, String catNameHidden, String brandList, String catList) throws Exception;

	/**
	 * 根据ID查询商家基本信息
	 * 
	 * @author wang.m
	 * @Date 2012-03-07
	 */
	public SupplierSp getSupplierSpById(String id);

	/**
	 * 供应商名称查询id
	 * 
	 * @author wang.m
	 * @Date 2012-03-07
	 */
	public String getSupplierSpBySupplier(String supplier);

	/**
	 * 根据商家ID查询商家分类权限基本信息
	 * 
	 * @author wang.m     modity huang.tao 2013-6-22 废弃此方法
	 * @Date 2012-03-08
	 * 
	 */
	@Deprecated
	public String[] getSpLimitCatBysupplierId(String id) throws SQLException;

	/**
	 * 根据商家ID查询商家授权品牌--分类权限
	 * @return
	 * modity huang.tao 2013-6-22 废弃此方法
	 * @throws SQLException
	 */
	@Deprecated
	public String[] getSpLimitBrandCatBysupplierId(String id) throws SQLException;
	
	/**
	 * 通过商家ID获取授权的品牌-分类关系权限
	 * @param id
	 * @return 
	 * @throws SQLException
	 */
	public List<String> queryAuthorizationBrandCatBysupplierId(String id) throws SQLException;
	
	/**
	 * 根据商家ID查询商家品牌权限基本信息
	 * 
	 * @author wang.m   modity huang.tao 2013-6-22 废弃此方法
	 * @Date 2012-03-08
	 */
	@Deprecated
	public String[] getSpLimitBrandBysupplierId(String id) throws SQLException;
	
	/**
	 * 通过商家ID查询授权品牌列表
	 * @param id
	 * @return brandNos
	 * @throws SQLException
	 */
	public List<String> getAuthorizationBrandNos(String id) throws SQLException;
	
	/**
	 * 通过商家ID查询授权的品牌
	 * @param id
	 * @return brand_no;brand_name _ brand_no;brand_name
	 * @throws SQLException
	 */
	public String queryAuthorizationBrandBysupplierId(String id) throws SQLException;
	
	/**
	 * 根据用户名称查询用户对象
	 * 
	 * @author wang.m
	 * @Date 2012-03-08
	 */
	public SystemmgtUser getSystemmgtUserByUserName(String loginUserName);

	/**
	 * wms调用接口 商家绑定仓库编码
	 * 
	 * @param merchantsCode
	 *            商家编号
	 * @param warehouseName
	 *            仓库编码
	 * @throws Exception
	 */
	public boolean update_merchant_virtualWarehouseCode(String merchantsCode, String warehouseCode, HttpServletRequest req);

	/**
	 * 查询所有商家角色集合
	 * 
	 * @author wang.m
	 * @Date 2012-03-26
	 * 
	 */
	public List<MerchantsRole> getMerchantsRoleList();

	/**
	 * 查询所有商家资源集合
	 * 
	 * @author wang.m
	 * @Date 2012-03-26
	 * 
	 */
	public List<MerchantsAuthority> getMerchantsAuthorityList(String uid);

	/**
	 * 查询商家Id查询该用户具体那些权限
	 * 
	 * @author wang.m
	 * @Date 2012-03-26
	 * 
	 */
	public List<Map<String, Object>> getMerchantsRoleDaoImplById(String id) throws SQLException;

	/**
	 * 删除商家信息
	 * 
	 * @author wang.M
	 * 
	 */
	public boolean delete_merchants(String id, String supplierCode) throws Exception;

	/**
	 * 添加商家权限
	 * 
	 * @author wang.m
	 * @Date 2012-03-26
	 */
	public boolean saveUserAuthority(String uid, String authority) throws Exception;

	/**
	 * 分配商家帐号权限
	 * 
	 * @author wang.m
	 * @Date 2012-03-26
	 */
	public boolean addUserAuthority(String userid, String authority, SystemmgtUser systemmgtUser) throws Exception;

	/**
	 * 查询商家用户集合列表
	 * 
	 * @author wang.m
	 * @Date 2012-03-26
	 */
	public PageFinder<Map<String, Object>> queryMerchantsAuthorityList(Query query, MerchantsAuthority merchantsAuthority);

	/**
	 * 
	 * 根据Id查询商家资源对象
	 */
	public MerchantsAuthority getMerchantsAuthorityByid(String id);

	/**
	 * 
	 * 修改商家资源对象
	 */
	public boolean updateMerchantsAuthority(MerchantsAuthority merchantsAuthority);

	/**
	 * 
	 * 添加商家资源对象
	 */
	public boolean addMerchantsAuthority(MerchantsAuthority merchantsAuthority);

	/**
	 * 查询商家帐号列表
	 */
	public PageFinder<Map<String, Object>> queryMerchantsUser(Query query, MerchantUser merchantUser);

	/**
	 * 查询商家角色列表
	 */
	public PageFinder<Map<String, Object>> queryMerchantsRole(Query query, MerchantsRole merchantsRole,String authorityName);
	
	public List<MerchantsRole> queryAllMerchantsRole();
	
	/**
	 * 查询商家角色列表
	 * 
	 * @author wang.m
	 */
	public List<UserRole> queryUserRole(String user_id) ;
	
	public void saveUserRole(String userId,String[] role,String merchantCode,String opertor) throws Exception ;

	/**
	 * 增加商家角色
	 * 
	 * @author wang.m
	 * @Date 2012-03-27
	 * 
	 */
	public boolean addMerchantsRole(MerchantsRole merchantsRole,String[] authritys);

	/**
	 * 修改商家角色
	 * 
	 * @author wang.m
	 * @Date 2012-03-27
	 * 
	 */
	public boolean update_merchants_role(MerchantsRole smerchantsRole,String[] authritys);

	/**
	 * 初始化角色修改页面数据
	 * 
	 * @author wang.m
	 * @Date 2012-03-27
	 * 
	 */
	public MerchantsRole initialMerchantsRole(String rid);

	/**
	 * 查看改角色已经用户的资源
	 */
	public List<Map<String, Object>> getRoleAuthorityList(String rid) throws SQLException;

	/**
	 * 给角色分配资源
	 * 
	 * @author wang.m
	 * 
	 */
	public boolean addRoleAuthority(String roleId, String authorityId);

	/**
	 * 判断商家名称是否已经存在
	 * 
	 * @author wang.m
	 * @Date 2012-03-29
	 */
	public Integer existMerchantSupplieName(String supplieName);

	/**
	 * 删除角色
	 * 
	 * @author wang.m
	 */
	public boolean delete_role(String id);

	/**
	 * 删除资源
	 * 
	 * @author wang.m
	 */
	public boolean delete_authority(String id);

	/**
	 * 根据商家编号查询商家登录信息
	 */
	public MerchantUser getMerchantsBySuppliceCode(String supplierCode);

	/**
	 * 
	 * 根据供应商Id查询供应商名称
	 * 
	 * @author wang.m
	 * @DATE 2012-03-31
	 */
	public String getSupplerNameById(String supplierId);

	/**
	 * 修改商家登录密码
	 * 
	 * @author wang.m
	 * @DATE 2012-04-06
	 * 
	 */
	public boolean updatePassword(MerchantUser merchantUser, SystemmgtUser systemmgtUser);

	/**
	 * 
	 * 根据商家帐号Id查询商家已拥有的资源列表
	 */
	public List<Map<String, Object>> getMerchantsAuthorityByUserId(String userId) throws SQLException;

	/**
	 * 判断合同编号是否存在
	 * 
	 * @param contractNo
	 *            合同编号
	 */
	public boolean exits_contractNo(String contractNo, String supplierSpId);

	/**
	 * 
	 * 根据供应商Id查询商家超级管理员账户Id
	 */
	public String getMerchantUserBySupplierId(String id) throws SQLException;

	/**
	 * 
	 * 根据商家账户Id查询
	 */
	public MerchantUser getMerchantUserById(String merchantUserId) throws SQLException;

	/**
	 * 
	 * 保存商家账户
	 */
	public void saveMerchantUser(MerchantUser merchantUser) throws Exception;

	/**
	 * 
	 * 在启用的情况下 修改商家品牌和分类信息
	 * 
	 * @Date 2012-03-07
	 * @author wang.m
	 */
	@Deprecated
	public boolean update_merchantsBankAndCat(SupplierSp supplierSp, HttpServletRequest req, String bankNameHidden, String catNameHidden, String brandList, String catList);

	/**
	 * 查询财务成本帐套数据集合
	 * 
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public List<CostSetofBooks> getCostSetofBooksList() throws Exception;

	/**
	 * 跳转到商家发货地址设置页面
	 * 
	 * @author wang.m
	 * @Date 2012-04-28
	 */
	public PageFinder<SupplierSp> getSupplierSpList(SupplierSp supplierSp, Query query);

	/**
	 * 保存快递单模块信息
	 * 
	 * @author wang.m
	 * @Date 2012-05-02
	 */
	public boolean saveExpressTemplate(HttpServletRequest req, MerchantExpressTemplate merchantExpressTemplate);

	/**
	 * 根据物流公司Id获取快递单模版信息
	 */
	public MerchantExpressTemplate getExpressTemplateByLogisticsId(String id);

	/**
	 * 根据订单号查询商家的收货人相关的信息
	 */
	public Map<String, Object> getMerchantConsignmentadress(String supplyid);

	/**
	 * 查询商家售后退货地址列表
	 * 
	 * @author wang.m
	 * @date 2012-05-11
	 */
	public PageFinder<MerchantRejectedAddress> getMerchantRejectedAddressList(Query query, MerchantRejectedAddress merchantRejectedAddress, String brand);

	/**
	 * 保存商家售后退货地址数据
	 * 
	 * @author wang.m
	 * @date 2012-05-11
	 */
	public boolean saveMerchantRejectedAddress(HttpServletRequest req, MerchantRejectedAddress merchantRejectedAddress);

	/**
	 * 查询id查询商家售后退货地址列表
	 * 
	 * @author wang.m
	 * @date 2012-05-11
	 */
	public MerchantRejectedAddress getMerchantRejectedAddressById(String id);

	/**
	 * 根据ID查询子地区
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getChildAreaByNo(String id, Integer level);

	/*
	 * 获取省市区第一级结果集数据
	 */
	public List<Map<String, Object>> getAreaList();

	/**
	 * 判断商家退货地址是否已经存在
	 * 
	 * @throws Exception
	 */
	public boolean exictRejectedAddressCount(String supplierCode);

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
	public Integer addMerhcantlog(String supplierCode, String conductType, String operateField, String conductBeforeInfo, String conductAfterInfo, String updateUser);

	/**
	 * 跳转商家更新历史记录页面
	 * 
	 * @author wang.m
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public PageFinder<Map<String, Object>> getSupplierUpdateHistoryList(Query query, MerchantsVo merchantsVo, String id);

	/*
	 * 查询商家结算编码机会
	 * 
	 * @author wang.m
	 * 
	 * @throws Exception
	 */
	public List<Map<String, Object>> getTraderMaintainList();

	/**
	 * 查询商家优购管理员拥有商家
	 * 
	 * @author zhuang.rb
	 * @Date 2013-01-18
	 */
	public PageFinder<Map<String, Object>> queryYougouAdminMerchantList(Query query, String merchantUserId, String merchantCode, String merchantName, Integer isInputYougouWarehouse);

	/**
	 * 查询商家优购管理员不拥有的商家
	 * 
	 * @author zhuang.rb
	 * @Date 2013-01-18
	 */
	public PageFinder<Map<String, Object>> queryMerchantNotHadList(Query query, String merchantUserId, String merchantCode, String merchantName, Integer isInputYougouWarehouse);

	/**
	 * 根据id删除商家优购管理员拥有商家信息
	 */
	public boolean delYougouAdminsMerchant(String id);

	/**
	 * 保存商家优购管理员拥有商家信息
	 */
	public boolean saveYougouAdminMerchant(String merchantUserId, String[] merchantCodes) throws Exception;
	
	public boolean update_user_auth(String roleId);
	
	public void loadAuthResource(String userId) throws Exception;
	
	public List<UserRole> queryUserRoleByRole(String id);
	 
	/**
	 * delAuthResource:删除账号权限 
	 * @author li.n1 
	 * @param id 
	 * @since JDK 1.6
	 */
	public void delAuthResource(String id) throws Exception;
	/**
	 * deleteCacheByKey:根据key删除缓存，不需要与数据库交互 
	 * @author li.n1 
	 * @param key
	 * @return 
	 * @since JDK 1.6
	 */
	public int deleteCacheByKey(String key);
	
	/**
	 * 根据商家编码查询商家售后退货地址列表
	 * 
	 * @author wang.m
	 * @date 2012-05-11
	 */
	public MerchantRejectedAddress getMerchantRejectedAddressByCode(String supplierCode);
	
	/**
	 * existsTelePhoneInMerchants:检测手机号码是否唯一 
	 * @author li.n1 
	 * @param mobileCode
	 * @param name 登录账号
	 * @return 
	 * @since JDK 1.6
	 */
	public boolean existsTelePhoneInMerchants(String mobileCode, String name);
	
	/**
	 * 通过商家ID获取授权的品牌-分类二级列表详情
	 * @param supplierId
	 * @return 
	 * @throws Exception
	 */
	 public List<Cat> buildBrandCatSecondLevelList(String supplierId) throws Exception;
}
