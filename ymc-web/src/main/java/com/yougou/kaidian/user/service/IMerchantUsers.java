package com.yougou.kaidian.user.service;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.ModelMap;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.user.model.pojo.CommonUseLogisticsCompany;
import com.yougou.kaidian.user.model.pojo.MerchantUsers;
import com.yougou.kaidian.user.model.pojo.MerchantconsignmentAdress;
import com.yougou.kaidian.user.model.pojo.SupplierLinkMan;
import com.yougou.pc.model.brand.Brand;

/**
 * 用户信息service接口�?
 * 
 * @author wang.m
 * @Date 2012-03-12
 * 
 */
public interface IMerchantUsers {
	/**
	 * 根据用户名和密码登录
	 * 
	 * @author wang.m
	 * @param loginAccount
	 *            用户�?
	 * @param loginPassword
	 *            登录
	 * @return
	 */
	public Map<String, Object> merchantLoginByUserName(String loginAccount) throws Exception;
	
	/**
	 * 根据id查询用户
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> merchantById(String id) throws Exception;


	/**
	 * 根据登录信息查询商家基本信息
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	public Map<String, Object> getMerchantsUser(HttpServletRequest request) throws Exception;
	
	/**
	 * 保存邮箱信息
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	public void saveEmail(String email,String id) throws Exception;
	
	/**
	 * 修改邮箱信息
	 * @param id
	 * @param email
	 * @param emailStatus
	 * @throws Exception
	 */
	public void updateEmail(String id,String email,int emailStatus) throws Exception;
	
	/**
	 * 发送邮件
	 * @param addresss
	 * @param title
	 * @param content
	 * @throws Exception
	 */
	public void sandMail(String addresss,String title,String content, String loginName) throws Exception;

	/**
	 * 根据登录信息查询商家合同基本信息
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	public PageFinder<Map<String, Object>> getSupplierContractList(HttpServletRequest request, Query query) throws Exception;

	/**
	 * 根据登录信息查询商家联系人信息列�?
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	public PageFinder<SupplierLinkMan> getSupplierLinkManList(HttpServletRequest request, Query query) throws Exception;

	/**
	 * 添加商家联系人信�?
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	public boolean add_linkMan(ModelMap map, SupplierLinkMan supplierLinkMan, HttpServletRequest request) throws Exception;

	/**
	 * 修改商家联系人信�?
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	public boolean update_linkMan(ModelMap map, SupplierLinkMan supplierLinkMan) throws Exception;

	/**
	 * 根据联系人主键id查询联系人对象信�?
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	public SupplierLinkMan getSupplierLinkManById(String id) throws Exception;

	/**
	 * 根据条件查询帐号信息列表
	 * 
	 * @author wang.m
	 * @param request
	 * @param loginName 当前登录者账号
	 * @return
	 */
	public PageFinder<Map<String, Object>> queryMerchantsByWhere(Query query, MerchantUsers merchantUsers,String loginName, HttpServletRequest request) throws Exception;

	/**
	 * 添加帐号信息
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	public boolean add_merchants(MerchantUsers merchant, HttpServletRequest reques) throws Exception;

	/**
	 * 修改帐号信息
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	public boolean update_merchants(MerchantUsers merchant, HttpServletRequest request) throws Exception;

	/**
	 * 修改帐号基本状�?
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	public boolean updateStatus(MerchantUsers mer) throws Exception;

	/**
	 * 根据ID加载商家帐号信息
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	public MerchantUsers inits_merchants(String id) throws Exception;

	/**
	 * 根据登录Id初始化首页数据
	 * 
	 * @return
	 */
	public Map<String, Object> showMerchantMessage(String merchantUserId) throws Exception ;
	
	/**
	 * 获取当前用户的商家列表
	 * @param query
	 * @param merchantUserId
	 * @param merchantCode
	 * @param merchantName
	 * @return
	 */
	public PageFinder<Map<String, Object>> showYougouAdminMerchant(Query query, String merchantUserId, String merchantCode, String merchantName, List<Brand> brands) throws Exception;

	/**
	 * 根据商家编码获取当前商家的信息和仓库信息
	 * @param merchantCode
	 * @return
	 */
	public Map<String, Object> getPresentMerchantAndWarehouse(String merchantCode) throws Exception;

	/**
	 * 判断帐号是否存在
	 * 
	 * @author wang.m
	 * @Date 2012-03-15
	 * 
	 */
	public boolean exitsLoginAccount(String loginAccount) throws Exception;

	/**
	 * 添加联系人时候 判断手机是否存在
	 * 
	 * @author wang.m
	 * @Date 2012-03-15
	 * 
	 */
	public boolean exitsTelePhone(String mobilePhone,HttpServletRequest request) throws Exception;

	/**
	 * 添加联系人时候 判断email是否存在
	 * 
	 * @author wang.m
	 * @Date 2012-03-15
	 * 
	 */
	public boolean exitsEmail(String email) throws Exception;

	/**
	 * 修改密码
	 * 
	 * @author wang.m
	 * @Date 2010-03-16
	 */
	public boolean update_password(String newPassword, HttpServletRequest request) throws Exception;
	
	/**
	 * 修改密码
	 * 
	 * @author wang.m
	 * @Date 2010-03-16
	 */
	public boolean update_passwordNoLogin(String id,String newPassword) throws Exception;

	/**
	 * 验证输入的原密码是否正确
	 * 
	 * @author wang.m
	 * @Date 2010-03-16
	 */
	public boolean extisPassword(String password, HttpServletRequest request) throws Exception;

	/**
	 * 根据登录用户查询具有的权限
	 * 
	 */
	public List<Map<String, Object>> getMerchantsAuthority(HttpServletRequest request, Integer authrityModule) throws Exception;

	/**
	 * 根据登录用户查询具有根菜单的权限
	 * 
	 */
	public Map<String, Object> getMerchantsAuthorityByUserId(HttpServletRequest request, String authrityName) throws Exception;

	/**
	 * 根据登录用户查询商家拥有的根模块所具有的菜单权限
	 * 
	 */
	public Map<String, Object> getMerchantsUserAuthorityByUserId(HttpServletRequest request, String authrityName) throws Exception;

	/**
	 * 根据子账户商家编号查询管理员账户id所具备的权限列表
	 * 
	 */
	public List<Map<String, Object>> getMerchantChildUserAuthorityList(String uid, String supplierCode) throws Exception;

	/**
	 * 查询子账户所具备的权限
	 * 
	 */
	public List<Map<String, Object>> getUserAuthorityList(String supplierCode) throws Exception;

	/**
	 * 分配商家帐号权限
	 * 
	 * @author wang.m
	 * @Date 2012-03-26
	 */
	public boolean addUserAuthority(String userid, String authority) throws Exception;

	/**
	 * 根据商家id查询商家发货地址
	 * 
	 * @throws Exception
	 */
	public MerchantconsignmentAdress getConsignmentAdressList(HttpServletRequest request) throws Exception;

	/**
	 * 根据商家id查询商家发货地址
	 * 
	 * @throws Exception
	 */
	public Integer update_merchant_consignmentAdress(HttpServletRequest request, MerchantconsignmentAdress merchantconsignmentAdress) throws Exception;

	/**
	 * 根据ID查询子地区
	 * 
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getChildAreaByNo(String id, Integer level) throws Exception;

	/*
	 * 获取省市区第一级结果集数据
	 */
	public List<Map<String, Object>> getAreaList() throws Exception;

	/**
	 * 根据商家id查询商家发货地址
	 * 
	 * @throws Exception
	 */
	public Map<String, Object> getConsignmentAdressById(String id) throws Exception;

	/**
	 * 查询物流公司数据集合
	 * 
	 */
	public List<Map<String, Object>> getLogisticscompanList() throws Exception;
	/**
	 * getfrequentUseCompanyList:查询常用的快递公司 
	 * @author li.n1 
	 * @return
	 * @throws Exception 
	 * @since JDK 1.6
	 */
	public List<Map<String, Object>> getfrequentUseCompanyList(String merchantCode) throws Exception;

	/**
	 * 根据物流公司Id获取快递单模版信息
	 */
	public Map<String, Object> getExpressTemplateByLogisticsId(String id) throws Exception;

	/**
	 * 根据模版Id获取快递单模版信息
	 */
	public Map<String, Object> getExpressTemplateById(String id) throws Exception;

	/**
	 * 根据订单号查询商家的收货人相关的信息
	 */
	public Map<String, Object> getOrderInfo(String orderSubNo,HttpServletRequest request) throws Exception;

	/**
	 * 根据订单号查询商家的收货人相关的信息
	 */
	public Map<String, Object> getMerchantConsignmentadress(HttpServletRequest request) throws Exception;

	/**
	 * 查询所有的资源信息
	 */
	public List<Map<String, Object>> getMerchantsAuthorityList() throws Exception;
	
	/**
 	 * 修改联系人时候  判断手机是否存在
 	 * @author wang.m
 	 * @Date 2012-05-28
 	 * 
 	 */
 	public boolean exitsNewPhone(String oldPhone,String newPhone,HttpServletRequest request) throws Exception;
 	
 	/**
	 * 改写商家菜单权限逻辑
	 * 
	 * @param uid 用户Id
	 * @param request
	 * @throws Exception
	 */
 	void putMerchantAuthoritySession(String uid, HttpServletRequest request) throws Exception;
 	
 	public String queryUidByMerchantCode(String merchantCode);

	public boolean saveCommonUseExpress(CommonUseLogisticsCompany company);
	/**
	 * checkHasCommonUseLogistics:判断商家是否设置常用快递 true是  false否
	 * @author li.n1 
	 * @param merchantCode 商家编码
	 * @return 
	 * @since JDK 1.6
	 */
	public boolean checkHasCommonUseLogistics(String merchantCode);
	/**
	 * deleteCommonUseExpress:根据商家编码删除该商户设置的常用快递
	 * @author li.n1 
	 * @param merchantCode 商家编码
	 * @return 
	 * @since JDK 1.6
	 */
	public boolean deleteCommonUseExpress(String merchantCode);
	
	/**
	 * update_mobilePhone:绑定手机号 
	 * @author li.n1 
	 * @param mobileCode 
	 * @param loginName 
	 * @return 
	 * @since JDK 1.6
	 */
	public boolean updateMobilePhone(String loginName, String mobileCode);
	/**
	 * sendMail:发送邮件 
	 * @author li.n1 
	 * @param addresss
	 * @param title
	 * @param content
	 * @param subjectType
	 * @param modelType
	 * @param loginName 登录用户名
	 * @throws Exception 
	 * @since JDK 1.6
	 */
	void sendMail(String addresss, String title, String content,String subjectType, 
			String modelType, String loginName)
			throws Exception;
	/**
	 * getMobilePhoneOfLoginName:根据登录账号得到手机号码 ,是否主账号
	 * @author li.n1 
	 * @param merchantCode
	 * @param loginName
	 * @return 
	 * @since JDK 1.6
	 */
	public Map<String,Object> getMobilePhoneOfLoginName(String loginName);

	public boolean update_passwordAndPower(String password, String power,
			HttpServletRequest request);
	/**
	 * exitsTelePhoneOfMerchantUsers:检查手机号码是否已经绑定了账号 
	 * @author li.n1 
	 * @param mobileCode 手机号码
	 * @return 
	 * @since JDK 1.6
	 */
	public boolean exitsTelePhoneOfMerchantUsers(String mobileCode,String loginName);
	/**
	 * exitsEmailOfMerchantUsers:检查邮箱是否已经绑定了账号 
	 * @author li.n1 
	 * @param email
	 * @return 
	 * @since JDK 1.6
	 */
	public boolean exitsEmailOfMerchantUsers(String email);
	/**
	 * getMerchantAuthorityCountVoById:得到该账号的权限菜单个数 
	 * @author li.n1 
	 * @param uid
	 * @return 
	 * @since JDK 1.6
	 */
	public int getMerchantAuthorityCountVoById(String uid);
	/**
	 * querySubAccountByMerchantCode:根据商家编码查询所有子账号
	 * @author li.n1 
	 * @param string
	 * @return 
	 * @since JDK 1.6 
	 * @date 2016-1-8 下午3:01:01
	 */
	public List<String> queryAccountByMerchantCode(String merchantCode);
	 
}
