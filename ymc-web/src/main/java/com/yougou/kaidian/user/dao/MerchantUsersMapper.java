package com.yougou.kaidian.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.yougou.kaidian.common.vo.MerchantsAuthorityVo;
import com.yougou.kaidian.user.model.pojo.Area;
import com.yougou.kaidian.user.model.pojo.CommonUseLogisticsCompany;
import com.yougou.kaidian.user.model.pojo.MerchantUsers;
import com.yougou.kaidian.user.model.pojo.MerchantconsignmentAdress;
import com.yougou.kaidian.user.model.pojo.MerchantsAuthority;
import com.yougou.kaidian.user.model.pojo.UserAuthority;
import com.yougou.kaidian.user.model.vo.MerchantsUserAuthorityVo;

/**
 * 商家信息dao�?
 * 
 * @author wang.m
 * 
 */
public interface MerchantUsersMapper {

	/**
	 * 登录
	 * 
	 * @author wang.m
	 * @Date 2012-03-12
	 */
	Map<String, Object> merchantsLogin(String loginAccount);
	
	Map<String, Object> merchantById(String id);
	
	/**
	 * 根据商家编码查询用户id
	 * @param merchantCode
	 * @return
	 */
	String queryUidByMerchantCode(String merchantCode);
	

	/**
	 * 根据用户获取商家和商家对应仓库信息
	 * @param merchantCode
	 * @return
	 */
	Map<String, Object> getMerchantAndWarehouse(String merchantCode);
	
	/**
	 * 查询优购管理员所属商家
	 * @param merchantUserId
	 * @param merchantCode
	 * @param merchantName
	 * @return
	 */
	List<Map<String, Object>> queryYougouAdminMerchants(MerchantUsers merchantUsers, RowBounds rowBounds);

	/**
	 * 查询优购管理员所属商家数量
	 * @param merchantUserId
	 * @param merchantCode
	 * @param merchantName
	 * @return
	 */
	int queryYougouAdminMerchantCount(MerchantUsers merchantUsers);
	/**
	 * 根据商品登录信息查询商家基本信息详情
	 * 
	 * @author wang.m
	 * @Date 2012-03-12
	 */
	Map<String, Object> queryMerchantsListBySupplierId(String id);

	/**
	 * 根据条件查询帐号信息列表
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	List<Map<String, Object>> queryMerchantsByWhere(@Param("merchantUsers") MerchantUsers merchantUsers,@Param("lgName") String lgName, RowBounds rowBounds);

	/**
	 * 获取总条数
	 * 
	 * @param id
	 */
	int getMerchantsCounts(@Param("id") String id,@Param("lgName") String lgName);

	/**
	 * 添加帐号信息
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	int add_merchants(MerchantUsers mer);

	/**
	 * 修改帐号信息
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	int update_merchants(MerchantUsers mer);

	/**
	 * 修改帐号基本状�?
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	int updateStauts(MerchantUsers mer);

	/**
	 * 根据ID加载商家帐号信息
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	MerchantUsers inits_merchants(String id);

	/**
	 * 根据登录ID显示主页信息
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	Map<String, Object> showMerchantMessage(String id);

	int exitsLoginAccount(String loginAccount);

	/**
	 * 修改密码
	 * 
	 * @author wang.m
	 * @Date 2010-03-16
	 */
	int update_password(MerchantUsers mer);

	/**
	 * 根据登录名称查询所具有的权限
	 * 
	 * @author wang.m
	 * 
	 */
	List<Map<String, Object>> getMerchantsAuthority(MerchantsUserAuthorityVo authrityModule);

	/**
	 * 根据登录名称查询所具有根菜单的权限
	 * 
	 * @author wang.m
	 * 
	 */
	Map<String, Object> getMerchantsAuthorityByUserId(MerchantsUserAuthorityVo authrityModule);

	/**
	 * 根据登录用户查询商家帐号所拥有的权限
	 * 
	 */
	List<Map<String, Object>> getMerchantsUserAuthority(MerchantsUserAuthorityVo authrityModule);

	/**
	 * 根据登录用户查询商家拥有的根模块所具有的菜单权限
	 * 
	 */
	Map<String, Object> getMerchantsUserAuthorityByUserId(MerchantsUserAuthorityVo authrityModule);

	/**
	 * 根据子账户商家编号查询超级管理员账户ID
	 */
	String getMerchantUserIdByMerchantCode(String supplierCode);

	/**
	 * 根据管理员账户ID查询该账户具备的资源权限列表
	 */
	List<Map<String, Object>> getMerchantChildUserAuthorityList(String uid);

	/**
	 * 添加子帐号权限
	 */
	int addMerchantUserAuthority(UserAuthority userAuthority);

	/**
	 * 删除子帐号权限
	 */
	int deleteMerchantUserAuthorityByUserId(String uid);

	/*
	 * 根据商家id查询商家发货地址
	 */
	MerchantconsignmentAdress getConsignmentAdressList(String id);

	/*
	 * 添加商家发货地址信息
	 */
	int addMerchantConsignmentAdress(MerchantconsignmentAdress merchantconsignmentAdress);

	/*
	 * 修改商家发货地址信息
	 */
	int updateMerchantConsignmentAdress(MerchantconsignmentAdress merchantconsignmentAdress);

	/**
	 * 获取省市区第一级结果集数据
	 * 
	 * @return
	 */
	List<Map<String, Object>> getAreaList();

	/**
	 * 根据ID查询子地区
	 * 
	 * @return
	 */
	List<Map<String, Object>> getChildAreaByNo(Area area);

	/**
	 * 根据商家id查询商家发货地址
	 * 
	 * @throws Exception
	 */
	Map<String, Object> getConsignmentAdressById(String id);

	/**
	 * 查询物流公司数据集合
	 * 
	 */
	List<Map<String, Object>> getLogisticscompanList();

	/**
	 * 根据物流公司Id获取快递单模版信息
	 */
	Map<String, Object> getExpressTemplateByLogisticsId(String id);

	/**
	 * 根据模版Id获取快递单模版信息
	 */
	Map<String, Object> getExpressTemplateById(String id);

	/**
	 * 根据订单号查询商家的收货人相关的信息
	 */
	Map<String, Object> getOrderInfo(String orderSubNo);

	/**
	 * 根据订单号查询商家的发货地址相关的信息
	 */
	Map<String, Object> getMerchantConsignmentadress(String supplyid);
	
	/**
	 * 查询所有的资源信息
	 */
	List<Map<String, Object>> getMerchantsAuthorityList();
	
	/**
	 * 根据用户id查询密码
	 */
	String getMerchantUserById(String Userid);
	
	/**
	 * 通过用户查询商家菜单权限
	 * 
	 * @param uid
	 * @return
	 */
	List<MerchantsAuthority> getMerchantAuthorityById(String uid);
	
	/**
	 * 通过用户查询商家菜单权限
	 * 
	 * @param uid
	 * @return
	 */
	List<MerchantsAuthorityVo> getMerchantAuthorityVoById(String uid);
	
	/**
	 * 保存邮箱信息
	 * @param email
	 * @param loginName
	 */
	void saveEmail(Map<String,String> pram);
	
	void updateEmail(Map<String,String> pram);

	List<Map<String, Object>> queryCommonUseLogisticsCompany(String merchantCode);

	void saveCommonUseExpress(CommonUseLogisticsCompany company);
	/**
	 * getCommonUseLogisticsSize:查询商家设置的常用快递个数
	 * @author li.n1 
	 * @param merchantCode
	 * @return 
	 * @since JDK 1.6
	 */
	int getCommonUseLogisticsSize(String merchantCode);
	/**
	 * deleteCommonUseExpress:删除商家设置的常用快递
	 * @author li.n1 
	 * @param merchantCode 
	 * @since JDK 1.6
	 */
	void deleteCommonUseExpress(String merchantCode);
	
	/**
	 * 查询所有状态为启用的商家
	 * @return
	 */
	List<String> getAllMerchant();

	Map<String,Object> findAppKeyIsExist(String merchantCode);
	
	void updateMobilePhone(@Param("loginName") String loginName,
			@Param("mobileCode") String mobileCode);
	
	/**
	 * getMobilePhoneOfLoginName:当前登录用户是否绑定手机号，是否主账号 
	 * @author li.n1 
	 * @param loginName
	 * @return 
	 * @since JDK 1.6
	 */
	Map<String,Object> getMobilePhoneOfLoginName(@Param("loginName") String loginName);

	int exitsTelePhoneOfMerchantUsers(@Param("mobileCode") String mobileCode,@Param("loginName") String loginName);

	int exitsEmailOfMerchantUsers(String email);
	/**
	 * 通过用户查询商家菜单权限个数
	 * 
	 * @param uid
	 * @return
	 */
	int getMerchantAuthorityCountVoById(String uid);
	
	List<String> queryAccountByMerchantCode(String merchantCode);
}
