package com.yougou.kaidian.user.service.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.yougou.component.area.api.IAreaApi;
import com.yougou.component.email.api.IEmailApi;
import com.yougou.component.email.model.MailSenderInfo;
import com.yougou.component.email.model.SubjectIdType;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.util.CommonUtil;
import com.yougou.kaidian.common.util.Md5Encrypt;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.kaidian.common.vo.AuthorityComparator;
import com.yougou.kaidian.framework.util.SessionUtil;
import com.yougou.kaidian.user.dao.MerchantUsersMapper;
import com.yougou.kaidian.user.dao.MessageMapper;
import com.yougou.kaidian.user.dao.SupplierContractMapper;
import com.yougou.kaidian.user.dao.SupplierLinkManMapper;
import com.yougou.kaidian.user.model.pojo.CommonUseLogisticsCompany;
import com.yougou.kaidian.user.model.pojo.MerchantUsers;
import com.yougou.kaidian.user.model.pojo.MerchantconsignmentAdress;
import com.yougou.kaidian.user.model.pojo.MerchantsAuthority;
import com.yougou.kaidian.user.model.pojo.Message;
import com.yougou.kaidian.user.model.pojo.SupplierLinkMan;
import com.yougou.kaidian.user.model.pojo.UserAuthority;
import com.yougou.kaidian.user.model.vo.MerchantsUserAuthorityVo;
import com.yougou.kaidian.user.model.vo.SupplierlinkManVo;
import com.yougou.kaidian.user.service.IMerchantUsers;
import com.yougou.ordercenter.api.order.IOrderForMerchantApi;
import com.yougou.pc.model.brand.Brand;
import com.yougou.wms.wpi.logisticscompany.domain.LogisticsCompanyDomain;
import com.yougou.wms.wpi.logisticscompany.service.ILogisticsCompanyDomainService;
import com.yougou.wms.wpi.warehouse.service.IWarehouseCacheService;

/**
 * 用户信息service类
 * 
 * @author wang.m
 * @Date 2012-03-12
 * 
 */
@Service
public class MerchantUsersServiceImpl implements IMerchantUsers,Serializable{
	@Resource
	private MerchantUsersMapper merchantUsersMapper;
	@Resource
	private SupplierContractMapper supplierContractMapper;
	@Resource
	private SupplierLinkManMapper supplierLinkManMapper;
	@Resource
	private IWarehouseCacheService warehouseCacheService;
	@Resource
	private IEmailApi emailApi;
	private Logger logger = LoggerFactory.getLogger(MerchantUsersServiceImpl.class);
	@Resource
	private IAreaApi areaApi;
    @Resource
    private IOrderForMerchantApi orderForMerchantApi;
    @Resource
    private ILogisticsCompanyDomainService logisticsCompanyDomainService;
    @Resource
	private MessageMapper messageMapper;
	/**
	 * 根据用户名和密码登录
	 * 
	 * @author wang.m
	 * @param loginAccount
	 *            用户名称
	 * @param loginPassword
	 *            登录
	 * @return
	 */
	public Map<String, Object> merchantLoginByUserName(String loginAccount) {
		return merchantUsersMapper.merchantsLogin(loginAccount);
	}
	
	public Map<String, Object> merchantById(String id) {
		return merchantUsersMapper.merchantById(id);
	}
	
	/**
	 * 根据商家编码获取当前商家的信息和仓库信息
	 * @param merchantCode
	 * @return
	 */
	public Map<String, Object> getPresentMerchantAndWarehouse(String merchantCode) {
		Map<String, Object> merchantAndWarehouse=merchantUsersMapper.getMerchantAndWarehouse(merchantCode);
		Map<String, String> warehouse=warehouseCacheService.getWarehouseByMerchantCode(MapUtils.getString(merchantAndWarehouse, "supplier_code"));
		if(MapUtils.isNotEmpty(warehouse)){
			String virtual_warehouse_code=warehouse.keySet().toArray()[0].toString();
			merchantAndWarehouse.put("warehouse_code",virtual_warehouse_code);
			merchantAndWarehouse.put("virtual_warehouse_name", warehouse.get(virtual_warehouse_code));
			
			String warehouseId=warehouseCacheService.getWarehouseIdByCode(MapUtils.getString(merchantAndWarehouse, "inventory_code"));
			merchantAndWarehouse.put("warehouseId", warehouseId);
		}
		return merchantAndWarehouse;
	}

	/**
	 * 从session商家基本信息
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getMerchantsUser(HttpServletRequest request) {
		Map<String, Object> users = null;
		try {
			Map<String, Object> merchantUsers = (Map<String, Object>) request.getSession().getAttribute("merchantUsers");
			if (null != merchantUsers) {
				users = merchantUsersMapper.queryMerchantsListBySupplierId(merchantUsers.get("id").toString());
			}
		} catch (Exception e) {
			logger.error("从session商家基本信息失败!",e);
		}
		return users;
	}

	/**
	 * 根据登录信息查询商家合同基本信息
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageFinder<Map<String, Object>> getSupplierContractList(HttpServletRequest request, Query query) {
		List<Map<String, Object>> contractList = null;
		Integer count = 0;
		RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
		Map<String, Object> merchantUsers = (Map<String, Object>) request.getSession().getAttribute("merchantUsers");
		if (null != merchantUsers) {
			contractList = supplierContractMapper.getSupplierContractList(merchantUsers.get("supplierId").toString(), rowBounds);
			count = supplierContractMapper.getContractCounts(merchantUsers.get("supplierId").toString());// 获取总条数
		}
		PageFinder<Map<String, Object>> pageFinder = new PageFinder<Map<String, Object>>(query.getPage(), query.getPageSize(), count);
		pageFinder.setData(contractList);
		return pageFinder;
	}

	/**
	 * 根据登录信息查询商家联系人信息列表
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageFinder<SupplierLinkMan> getSupplierLinkManList(HttpServletRequest request, Query query) {
		List<SupplierLinkMan> linkMan = null;
		Integer count = 0;
		RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
		Map<String, Object> merchantUsers = (Map<String, Object>) request.getSession().getAttribute("merchantUsers");
		if (null != merchantUsers) {
			linkMan = supplierLinkManMapper.getSupplierLinkManList(merchantUsers.get("supplierId").toString(), rowBounds);
			count = supplierLinkManMapper.getLinkManCounts(merchantUsers.get("supplierId").toString());// 获取总条数
		}
		PageFinder<SupplierLinkMan> pageFinder = new PageFinder<SupplierLinkMan>(query.getPage(), query.getPageSize(), count);
		pageFinder.setData(linkMan);
		return pageFinder;
	}

	/**
	 * 添加商家联系人信息
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	public boolean add_linkMan(ModelMap map, SupplierLinkMan supplierLinkMan, HttpServletRequest request) {
		boolean bool = false;
		try {
			SupplierLinkMan linkMan = new SupplierLinkMan();
			String id = UUIDGenerator.getUUID();
			linkMan.setId(id);// 主键ID
			Map<String, Object> merchantUsers = SessionUtil.getUnionUser(request);////
			if (null != merchantUsers) {
				linkMan.setSupplierId(merchantUsers.get("supplierId").toString());// 商家ID
			}
			linkMan.setContact(supplierLinkMan.getContact());// 姓名
			linkMan.setTelePhone(supplierLinkMan.getTelePhone());// 电话号码
			linkMan.setMobilePhone(supplierLinkMan.getMobilePhone());// 手机号码
			linkMan.setEmail(supplierLinkMan.getEmail());// email
			linkMan.setFax(supplierLinkMan.getFax());// 传真
			linkMan.setType(supplierLinkMan.getType());// 类型
			linkMan.setAddress(supplierLinkMan.getAddress());// 地址
			int counts = supplierLinkManMapper.add_linkMan(linkMan);
			if (counts > 0) {
				bool = true;
			}
		} catch (Exception e) {
			logger.error("添加商家联系人信息失败!",e);
		}
		return bool;
	}

	/**
	 * 修改商家联系人信息
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	public boolean update_linkMan(ModelMap map, SupplierLinkMan supplierLinkMan) {
		boolean bool = false;
		SupplierLinkMan linkMan = new SupplierLinkMan();
		linkMan.setId(supplierLinkMan.getId());// 主键ID
		linkMan.setSupplierId(supplierLinkMan.getSupplierId());// 商家ID
		linkMan.setContact(supplierLinkMan.getContact());// 姓名
		linkMan.setTelePhone(supplierLinkMan.getTelePhone());// 电话号码
		linkMan.setMobilePhone(supplierLinkMan.getMobilePhone());// 手机号码
		linkMan.setEmail(supplierLinkMan.getEmail());// email
		linkMan.setFax(supplierLinkMan.getFax());// 传真
		linkMan.setType(supplierLinkMan.getType());// 类型
		linkMan.setAddress(supplierLinkMan.getAddress());// 地址
		int counts = supplierLinkManMapper.update_linkMan(linkMan);
		if (counts > 0) {
			bool = true;
		}
		return bool;
	}

	/**
	 * 根据联系人主键id查询联系人对象信息
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	public SupplierLinkMan getSupplierLinkManById(String id) {
		return supplierLinkManMapper.getSupplierLinkManById(id);
	}

	/**
	 * 根据条件查询帐号信息列表
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageFinder<Map<String, Object>> queryMerchantsByWhere(Query query, MerchantUsers merchantUsers, String loginName, HttpServletRequest request) {
		List<Map<String, Object>> merchantsList = null;
		Integer count = 0;
		RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
		Map<String, Object> mer = (Map<String, Object>) request.getSession().getAttribute("merchantUsers");
		String merchantCode = MapUtils.getString(mer, "supplier_code");
		if (null != mer && StringUtils.isNotBlank(merchantCode)) {
			merchantUsers.setMerchantCode(merchantCode);
			merchantsList = merchantUsersMapper.queryMerchantsByWhere(merchantUsers,loginName, rowBounds);
			count = merchantUsersMapper.getMerchantsCounts(merchantCode,loginName);// 获取总条数
		}
		PageFinder<Map<String, Object>> pageFinder = new PageFinder<Map<String, Object>>(query.getPage(), query.getPageSize(), count);
		pageFinder.setData(merchantsList);
		return pageFinder;
	}

	/**
	 * 添加帐号信息
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public boolean add_merchants(MerchantUsers merchant, HttpServletRequest request) {
		MerchantUsers mer = new MerchantUsers();
		boolean bool = false;
		
		try {
			Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("merchantUsers");
			mer.setLoginName(merchant.getLoginName());
			if(StringUtils.isNotBlank(merchant.getPassword())){
				mer.setStrength(CommonUtil.getPasswordPower(merchant.getPassword()));
			}
			// 密码md5加密
			String password = Md5Encrypt.md5(merchant.getPassword());
			mer.setPassword(password);
			mer.setUserName(merchant.getUserName());
			if (user != null) {
				String code = (String) user.get("supplier_code");
				if (StringUtils.isNotBlank(code)) {
					mer.setMerchantCode(code);
				} else {
					mer.setMerchantCode(" ");
				}
			} else {
				mer.setMerchantCode(" ");
			}
			mer.setId(UUIDGenerator.getUUID());
			mer.setMobileCode(merchant.getMobileCode());
			mer.setRemark(merchant.getRemark());
			mer.setStatus(1);
			mer.setIsAdministrator(0);// 不是超级管理员
			mer.setCreateTime(formDate());
			mer.setDeleteFlag(1);//未删除
			int counts = merchantUsersMapper.add_merchants(mer);
			if (counts > 0) {
				bool = true;
			}
		} catch (Exception e) {
			logger.error("添加账号信息失败!",e);
		}
		return bool;
	}

	/**
	 * 修改帐号信息
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	@Transactional
	public boolean update_merchants(MerchantUsers merchant, HttpServletRequest request) {
		MerchantUsers mer = new MerchantUsers();
		boolean bool= false;
		try {
			// 判断是否修改了密码
			if (StringUtils.isNotBlank(merchant.getPassword())) {
				//密码强度
				mer.setStrength(CommonUtil.getPasswordPower(merchant.getPassword()));
				String LoginPassword = Md5Encrypt.md5(merchant.getPassword());
				mer.setPassword(LoginPassword);
			}
			mer.setLoginName(merchant.getLoginName());
			mer.setId(merchant.getId());
			mer.setMobileCode(merchant.getMobileCode());
			mer.setUserName(merchant.getUserName());
			mer.setRemark(merchant.getRemark());
			mer.setCreateTime(formDate());
			mer.setDeleteFlag(1);
			int counts = merchantUsersMapper.update_merchants(mer);
			if (counts > 0) {
				bool = true;
			}
		} catch (Exception e) {
			logger.error("修改帐号信息失败!",e);
		}
		return bool;
	}

	/**
	 * 修改帐号基本状�?
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	@Transactional
	public boolean updateStatus(MerchantUsers mer) {
		boolean bool = false;
		int counts = merchantUsersMapper.updateStauts(mer);
		if (counts > 0) {
			bool = true;
		}
		return bool;
	}

	/**
	 * 根据ID加载商家帐号信息
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	public MerchantUsers inits_merchants(String id) {
		return merchantUsersMapper.inits_merchants(id);
	}

	/**
	 * 初始化首页数据
	 * 
	 * @return
	 */
	public Map<String, Object> showMerchantMessage(String merchantUserId) {
		Map<String, Object> map = null;
		try {
			if (null != merchantUserId) {
				map = merchantUsersMapper.showMerchantMessage(merchantUserId);
			}
		} catch (Exception e) {
			logger.error("初始化首页数据失败!",e);
		}
		return map;
	}

	/**
	 * 获取当前用户的商家列表
	 */
	public PageFinder<Map<String, Object>> showYougouAdminMerchant(Query query, String merchantUserId, String merchantCode, String merchantName, List<Brand> brands) {
		List<Map<String, Object>> lstMerchantResult = null;
		RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
		MerchantUsers merchantUsers = new MerchantUsers();
		merchantUsers.setId(merchantUserId);
		merchantUsers.setMerchantCode(merchantCode);
		merchantUsers.setMerchantName(merchantName);
		StringBuffer brand_in=new StringBuffer();
		if (null != brands) {
			for (Brand brand : brands) {
				brand_in.append("\"" + brand.getBrandNo() + "\",");
			}
			if (brand_in.length() > 0) {
				brand_in.setLength(brand_in.length() - 1);
			}
		}
		merchantUsers.setBrands(brand_in.toString());
		lstMerchantResult = merchantUsersMapper.queryYougouAdminMerchants(merchantUsers, rowBounds);
		int count = merchantUsersMapper.queryYougouAdminMerchantCount(merchantUsers);
		PageFinder<Map<String, Object>> pageFinder = new PageFinder<Map<String, Object>>(query.getPage(), query.getPageSize(), count, lstMerchantResult);
		return pageFinder;
	}

	/**
	 * 判断帐号是否存在
	 * 
	 * @author wang.m
	 * @Date 2012-03-15
	 * 
	 */
	public boolean exitsLoginAccount(String loginAccount) {
		int bool = merchantUsersMapper.exitsLoginAccount(loginAccount);
		if (bool > 0) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 添加联系人时候 判断手机是否存在
	 * 
	 * @author wang.m
	 * @Date 2012-03-15
	 * 
	 */
	public boolean exitsTelePhone(String mobilePhone,HttpServletRequest request) {
		String suppliyId="";
		//获取session中的供应商Id
		Map<String, Object> merchantUsers = (Map<String, Object>) request.getSession().getAttribute("merchantUsers");
		if (null != merchantUsers) {
			suppliyId = (String) (merchantUsers.get("supplierId")==null?"":merchantUsers.get("supplierId"));
		}
		SupplierlinkManVo vo=new SupplierlinkManVo();
		vo.setNewPhone(mobilePhone);
		vo.setSupplilyId(suppliyId);
		int bool = supplierLinkManMapper.exitsTelePhone(vo);
		if (bool > 0) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 添加联系人时候 判断email是否存在
	 * 
	 * @author wang.m
	 * @Date 2012-03-15
	 * 
	 */
	public boolean exitsEmail(String email) {
		int bool = supplierLinkManMapper.exitsEmail(email);
		if (bool > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 修改密码
	 * 
	 * @author wang.m
	 * @Date 2010-03-16
	 */
	@Transactional
	public boolean update_password(String newPassword, HttpServletRequest request) {
		try {
			// 获取登录session中的信息
			Map<String, Object> mer = SessionUtil.getUnionUser(request);
			if (mer != null) {
				MerchantUsers user = new MerchantUsers();
				String password = Md5Encrypt.md5(newPassword);
				user.setPassword(password);
				user.setId(mer.get("id").toString());
				int counts = merchantUsersMapper.update_password(user);
				if (counts > 0) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			logger.error("修改密码失败!",e);
		}
		return false;
	}
	
	/**
	 * 修改密码与密码强度
	 * 
	 * @author li.n3
	 * @Date 2015-07-06
	 */
	@Transactional
	public boolean update_passwordAndPower(String newPassword, String power , HttpServletRequest request) {
		try {
			// 获取登录session中的信息
			Map<String, Object> mer = SessionUtil.getUnionUser(request);
			if (mer != null) {
				MerchantUsers user = new MerchantUsers();
				String password = Md5Encrypt.md5(newPassword);
				user.setPassword(password);
				user.setStrength(power);
				user.setId(mer.get("id").toString());
				merchantUsersMapper.update_password(user);
				return true;
			}
		} catch (Exception e) {
			logger.error("修改密码失败!",e);
		}
		return false;
	}
	
	/**
	 * 修改密码
	 * 
	 * @author wang.m
	 * @Date 2010-03-16
	 */
	@Transactional
	public boolean update_passwordNoLogin(String id,String newPassword) {
		try {
				MerchantUsers user = new MerchantUsers();
				String password = Md5Encrypt.md5(newPassword);
				user.setPassword(password);
				user.setId(id);
				int counts = merchantUsersMapper.update_password(user);
				if (counts > 0) {
					return true;
				} else {
					return false;
				}
		} catch (Exception e) {
			logger.error("修改密码失败!",e);
		}
		return false;
	}

	/**
	 * 验证输入的原密码是否正确
	 * 
	 * @author wang.m
	 * @Date 2010-03-16
	 */
	public boolean extisPassword(String password, HttpServletRequest request) {
		// 获取登录session中的信息
		Map<String, Object> mer = SessionUtil.getUnionUser(request);
		//根据session中的信息查询用户密码
		if (mer != null) {
			String oldPassowrd="";
			String userid=mer.get("id").toString();
			if(StringUtils.isNotBlank(userid)){
				 oldPassowrd=merchantUsersMapper.getMerchantUserById(userid);
			}
			String pass = Md5Encrypt.md5(password);
			if (pass.equals(oldPassowrd)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * 根据登录用户查询具有的权限
	 * 
	 */
	public List<Map<String, Object>> getMerchantsAuthority(HttpServletRequest request, Integer authrityModule) {
		Map<String, Object> merchantUsers = (Map<String, Object>) request.getSession().getAttribute("merchantUsers");
		List<Map<String, Object>> merchantsAuthority = null;
		if (null != merchantUsers) {
			// 获取登录信息中的用户ID
			String uid = merchantUsers.get("id").toString();
			// 根据用户Id查询所具有的商品模块的权限
			MerchantsUserAuthorityVo authorigyVo = new MerchantsUserAuthorityVo();
			authorigyVo.setId(uid);
			authorigyVo.setAuthrityModule(authrityModule);
			merchantsAuthority = merchantUsersMapper.getMerchantsAuthority(authorigyVo);
		}
		return merchantsAuthority;
	}

	/**
	 * 根据登录用户查询具有根菜单的权限
	 * 
	 */
	public Map<String, Object> getMerchantsAuthorityByUserId(HttpServletRequest request, String authrityName) {
		Map<String, Object> merchantUsers = (Map<String, Object>) request.getSession().getAttribute("merchantUsers");
		Map<String, Object> merchantsAuthority = null;
		if (null != merchantUsers) {
			// 获取登录信息中的用户ID
			String uid = merchantUsers.get("id").toString();
			// 根据用户Id查询所具有的商品模块的权限
			MerchantsUserAuthorityVo authorigyVo = new MerchantsUserAuthorityVo();
			authorigyVo.setId(uid);
			authorigyVo.setAuthrityName(authrityName);
			merchantsAuthority = merchantUsersMapper.getMerchantsAuthorityByUserId(authorigyVo);

		}
		return merchantsAuthority;
	}

	/**
	 * 根据登录用户查询商家拥有的根模块所具有的菜单权限
	 * 
	 */
	public Map<String, Object> getMerchantsUserAuthorityByUserId(HttpServletRequest request, String authrityName) {
		Map<String, Object> merchantUsers = (Map<String, Object>) request.getSession().getAttribute("merchantUsers");
		Map<String, Object> merchantsAuthority = null;
		if (null != merchantUsers) {
			// 获取登录信息中的用户ID
			String uid = merchantUsers.get("id").toString();
			// 根据用户Id查询所具有的商品模块的权限
			MerchantsUserAuthorityVo authorigyVo = new MerchantsUserAuthorityVo();
			authorigyVo.setId(uid);
			authorigyVo.setAuthrityName(authrityName);
			merchantsAuthority = merchantUsersMapper.getMerchantsUserAuthorityByUserId(authorigyVo);

		}
		return merchantsAuthority;

	}

	/**
	 * 根据子账户商家编号查询管理员账户id所具备的权限列表
	 * 
	 */
	public List<Map<String, Object>> getMerchantChildUserAuthorityList(String uid, String supplierCode) {
		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> UserAuthorityListMap = new ArrayList<Map<String, Object>>();
		try {
			if (StringUtils.isNotBlank(supplierCode)) {
				// 根据子账户商家编号查询超级管理员账户ID
				String usrid = merchantUsersMapper.getMerchantUserIdByMerchantCode(supplierCode);
				if (StringUtils.isNotBlank(supplierCode)) {
					// 根据管理员账户ID查询该账户具备的资源权限列表
					UserAuthorityListMap = merchantUsersMapper.getMerchantChildUserAuthorityList(usrid);
				}
			}
			// 查询子账户所具备的权限
			List<Map<String, Object>> childMap = getUserAuthorityList(uid);
			Map<String, Object> ma = new HashMap<String, Object>();
			if (childMap != null && childMap.size() > 0) {
				for (Map<String, Object> map2 : childMap) {
					ma.put(map2.get("id").toString(), map2.get("id").toString());
				}
			}
			// 过滤子帐号已经用户的权限
			if (UserAuthorityListMap != null && UserAuthorityListMap.size() > 0) {
				for (Map<String, Object> map2 : UserAuthorityListMap) {
					if (ma.containsValue(map2.get("id").toString())) {
						// 如果已经具备，则跳过
					} else {
						map.add(map2);
					}
				}
			}
		} catch (Exception e) {
			logger.error("查看理员账户id所具备的权限信息失败!",e);
		}
		return map;
	}

	/**
	 * 查询子账户所具备的权限
	 * 
	 */
	public List<Map<String, Object>> getUserAuthorityList(String uid) {
		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotBlank(uid)) {
			// 根据帐号ID查询该账户具备的资源权限列表
			map = merchantUsersMapper.getMerchantChildUserAuthorityList(uid);
		}
		return map;
	}

	/**
	 * 分配商家帐号权限
	 * 
	 * @author wang.m
	 * @Date 2012-03-26
	 */
	@Transactional
	public boolean addUserAuthority(String userid, String authority) throws Exception {
		boolean bool = false;
		try {
			if (StringUtils.isNotBlank(userid)) {
				// 先删除这个用户所具备的资源
				boolean boole = deleteUserAuthorityByPramas(userid);
				if (StringUtils.isNotBlank(authority)) {
					if (boole) {
						String[] strAdd = authority.split(";");
						if (strAdd.length > 0) {
							for (String string : strAdd) {
								UserAuthority userAuthority = new UserAuthority();
								// 获取uuid
								String uuid = UUIDGenerator.getUUID();
								userAuthority.setId(uuid);
								userAuthority.setUserId(userid);
								userAuthority.setAuthorityId(string);
								userAuthority.setCreateDate(formDate());
								userAuthority.setRemark("");
								int count = merchantUsersMapper.addMerchantUserAuthority(userAuthority);
								if (count > 0) {
									bool = true;
								} else {
									bool = false;
								}
							}
						}
					}
				}
				bool = boole;
			}
		} catch (Exception e) {
			logger.error("分配商家帐号{}权限失败!",userid,e);
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
	private boolean deleteUserAuthorityByPramas(String userId) {
		boolean bool = false;
		try {
			merchantUsersMapper.deleteMerchantUserAuthorityByUserId(userId);
			bool = true;
		} catch (Exception e) {
			bool = false;
			logger.error("删除商家帐号{}权限失败!",userId,e);
		}
		return bool;
	}

	/**
	 * 转换时间格式
	 * 
	 */
	private String formDate() {
		Date da = new Date();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sim.format(da);
		return str;
	}

	/**
	 * 根据商家id查询商家发货地址
	 * 
	 * @throws Exception
	 */
	public MerchantconsignmentAdress getConsignmentAdressList(HttpServletRequest request) {
		MerchantconsignmentAdress merchantconsignmentAdress = null;
		// 获取session中的商家id
		Map<String, Object> map = SessionUtil.getUnionUser(request);
		if (map != null && map.size() > 0) {
			String supplierId = map.get("supplierId").toString();
			// 根据商家id查询商家发货地址
			MerchantconsignmentAdress consignmentAdress = merchantUsersMapper.getConsignmentAdressList(supplierId);
			if (consignmentAdress != null) {
				merchantconsignmentAdress = consignmentAdress;
			}
		}
		return merchantconsignmentAdress;
	}

	/**
	 * 根据商家id查询商家发货地址
	 * 
	 * @throws Exception
	 */
	@Transactional
	public Integer update_merchant_consignmentAdress(HttpServletRequest request, MerchantconsignmentAdress merchantconsignmentAdress) {
		Integer counts = 0;
		if (merchantconsignmentAdress != null) {
			MerchantconsignmentAdress consignment = new MerchantconsignmentAdress();
			consignment.setConsignmentName(merchantconsignmentAdress.getConsignmentName());
			consignment.setHometown(merchantconsignmentAdress.getHometown());
			consignment.setAdress(merchantconsignmentAdress.getAdress());
			consignment.setPhone(merchantconsignmentAdress.getPhone());
			consignment.setPostCode(merchantconsignmentAdress.getPostCode());
			consignment.setTell(merchantconsignmentAdress.getTell());
			consignment.setCreateTime(formDate());
			consignment.setRemark("");
			//获取session中的商家id
			Map<String, Object> map = SessionUtil.getUnionUser(request);
			if (map != null && map.size() > 0) {
				String supplierId = map.get("supplierId").toString();
				String userName = map.get("login_name").toString();
				consignment.setSupplyId(supplierId);
				consignment.setCreater(userName);
			}
			
			// 如果id存在在执行修改方法
			if (StringUtils.isNotBlank(merchantconsignmentAdress.getId())) {
				consignment.setId(merchantconsignmentAdress.getId());
				int count = merchantUsersMapper.updateMerchantConsignmentAdress(consignment);
				if (count > 0) {
					counts = 1;
				} else {
					counts = 0;
				}
			} else {
				// 获取uuid
				String uuid = UUIDGenerator.getUUID();
				consignment.setId(uuid);
				int count = merchantUsersMapper.addMerchantConsignmentAdress(consignment);
				if (count > 0) {
					counts = 2;
				} else {
					counts = 0;
				}
			}
		}
		return counts;
	}

	/**
	 * 根据ID查询子地区
	 */
	public List<Map<String, Object>> getChildAreaByNo(String no, Integer level) {
//		List<Map<String, Object>> map = null;
//		if (StringUtils.isNotBlank(no)) {
//			Area area = new Area();
//			area.setNo(no + "-");
//			area.setLevel(level);
//			map = merchantUsersMapper.getChildAreaByNo(area);
//		}
//		return map;
        List<Map<String, Object>> relist = new ArrayList<Map<String, Object>>();
        for (com.yougou.component.area.model.Area area : areaApi.getChildAreaByNo(no)) {
            if (area.getLevel() == level) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", area.getName());
                map.put("no", area.getNo());
                relist.add(map);
            }
        }
        return relist;
	}

	/*
	 * 获取省市区第一级结果集数据
	 */
	public List<Map<String, Object>> getAreaList() {
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
	 * 根据商家id查询商家发货地址
	 * 
	 * @throws Exception
	 */
	public Map<String, Object> getConsignmentAdressById(String id) {
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(id)) {
			map = merchantUsersMapper.getConsignmentAdressById(id);
		}
		return map;
	}

	/**
	 * 查询物流公司数据集合
	 * 
	 */
	public List<Map<String, Object>> getLogisticscompanList() {
        List<Map<String, Object>> expressInfos = null;
        List<LogisticsCompanyDomain> logisticsCompanyDomainList = logisticsCompanyDomainService.queryAllIsUserLogisticsCompany();
        if (CollectionUtils.isNotEmpty(logisticsCompanyDomainList)) {
            expressInfos = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = null;
            for (LogisticsCompanyDomain domain : logisticsCompanyDomainList) {
                if (domain.getIsMerchant() == 2) {
                    map = new HashMap<String, Object>();
                    map.put("id", domain.getId());
                    map.put("logistic_company_code", domain.getLogisticCompanyCode());
                    map.put("logistics_company_name", domain.getLogisticsCompanyName());
                    expressInfos.add(map);
                }
            }
        }
        return expressInfos;
	}

	/**
	 * 根据物流公司Id获取快递单模版信息
	 */
	public Map<String, Object> getExpressTemplateByLogisticsId(String id) {
		Map<String, Object> merchantExpressTemplate = null;
		if (StringUtils.isNotBlank(id)) {
			merchantExpressTemplate = merchantUsersMapper.getExpressTemplateByLogisticsId(id);
		}
		return merchantExpressTemplate;
	}

	/**
	 * 根据模版Id获取快递单模版信息
	 */
	public Map<String, Object> getExpressTemplateById(String id) {
		Map<String, Object> merchantExpressTemplate = null;
		if (StringUtils.isNotBlank(id)) {
			merchantExpressTemplate = merchantUsersMapper.getExpressTemplateById(id);
		}
		return merchantExpressTemplate;
	}

	/**
	 * 根据订单号查询商家的收货人相关的信息
	 */
	public Map<String, Object> getOrderInfo(String orderSubNo,HttpServletRequest request) {
		String supplier_code="";
        Map<String, Object> merchantUsers = (Map<String, Object>) request.getSession().getAttribute("merchantUsers");
        if (null != merchantUsers) {
            supplier_code = (String) (merchantUsers.get("supplier_code")==null?"":merchantUsers.get("supplier_code"));
        }
        com.yougou.ordercenter.model.order.OrderSub orderSub = orderForMerchantApi.findOrderSubByOrderSubNoAndMerchantCode(orderSubNo, supplier_code);
        Map<String, Object> orderMap = null;
        if (null != orderSub) {
            orderMap = new HashMap<String, Object>();
            orderMap.put("order_sub_no", orderSubNo);
            orderMap.put("user_name", orderSub.getOrderConsigneeInfo().getUserName());
            orderMap.put("province", orderSub.getOrderConsigneeInfo().getProvinceName());
            orderMap.put("city", orderSub.getOrderConsigneeInfo().getCityName());
            orderMap.put("area", orderSub.getOrderConsigneeInfo().getAreaName());
            orderMap.put("consignee_address", orderSub.getOrderConsigneeInfo().getConsigneeAddress());
            orderMap.put("mobile_phone", orderSub.getOrderConsigneeInfo().getMobilePhone());
            orderMap.put("zip_code", orderSub.getOrderConsigneeInfo().getZipCode());
            orderMap.put("constact_phone", orderSub.getOrderConsigneeInfo().getConstactPhone());
            orderMap.put("product_send_quantity", orderSub.getProductSendQuantity());
            orderMap.put("out_shop_name", orderSub.getOutShopName());
            orderMap.put("total_price", orderSub.getTotalPrice());
            orderMap.put("message", orderSub.getMessage());
        }
        return orderMap;
	}

	/**
	 * 根据订单号查询商家的发货地址相关的信息
	 */
	public Map<String, Object> getMerchantConsignmentadress(HttpServletRequest request) {
		Map<String, Object> orderMap = null;
		Map<String, Object> merchantUsers = (Map<String, Object>) request.getSession().getAttribute("merchantUsers");
		if (null != merchantUsers) {
			// 获取session中的商家ID
			String supplyid = merchantUsers.get("supplierId").toString();
			if (StringUtils.isNotBlank(supplyid)) {
				orderMap = merchantUsersMapper.getMerchantConsignmentadress(supplyid);
			}
		}
		return orderMap;
	}
	/**
	 * 查询所有的资源信息
	 */
	public List<Map<String, Object>> getMerchantsAuthorityList(){
		return merchantUsersMapper.getMerchantsAuthorityList();
	}
	/**
 	 * 修改联系人时候  判断手机是否存在
 	 * @author wang.m
 	 * @Date 2012-05-28
 	 * 
 	 */
 	public boolean exitsNewPhone(String oldPhone,String newPhone,HttpServletRequest request) {
 		String suppliyId="";
		boolean bool=false;
		try {
			//获取session中的供应商Id
			Map<String, Object> merchantUsers = (Map<String, Object>) request.getSession().getAttribute("merchantUsers");
			if (null != merchantUsers) {
				suppliyId = (String) (merchantUsers.get("supplierId")==null?"":merchantUsers.get("supplierId"));
			}
			bool = false;
			SupplierlinkManVo vo=new SupplierlinkManVo();
			vo.setNewPhone(newPhone);
			vo.setOldPhone(oldPhone);
			vo.setSupplilyId(suppliyId);
			int count=supplierLinkManMapper.exitsNewPhone(vo);
			if(count>0){
				bool=true;
			}
		} catch (Exception e) {
			logger.error("修改联系人时候  判断手机是否存在",e);
		}
 		return bool;
 	}
 		
	/**
	 * 改写商家菜单权限逻辑
	 * 
	 * @param request
	 * @throws Exception
	 */
 	@Override
	public void putMerchantAuthoritySession(String uid, HttpServletRequest request) throws Exception {
 		List<MerchantsAuthority> authoritys = merchantUsersMapper.getMerchantAuthorityById(uid);
 		//所有授权的资源url
 		Set<String> authrityUrls = new HashSet<String>();
 		Map<String, List<MerchantsAuthority>> _map = new TreeMap<String, List<MerchantsAuthority>>(new AuthorityComparator());
 		List<MerchantsAuthority> childAuthorityList=null;
 		String key="";
 		/*Document document = XmlTool.createDocument(MerchantUsersServiceImpl.class
 				.getClassLoader().getResource("authority.xml").getPath());
 		Element root = XmlTool.getRootElement(document);
 		List<Element> indexResources = root.elementByID("index").elements();
 		Element menuEle = null;*/
 		for (MerchantsAuthority _authority : authoritys) {
 			if (StringUtils.isNotBlank(_authority.getAuthrityURL())){
 				/*for(Iterator<Element> it = root.elementIterator();it.hasNext();){
 					menuEle = it.next();
 					if(_authority.getAuthrityURL().indexOf(XmlTool.getAttributeVal(menuEle, "url"))!=-1){
 						for(Iterator<Element> eit = menuEle.elementIterator();eit.hasNext();){
 							authrityUrls.add(XmlTool.getAttributeVal(eit.next(), "url"));
 						}
 						it.remove();
 						break;
 	 				}
 				}*/
 				//authrityUrls.add("/"+_authority.getAuthrityURL());
 				authrityUrls.add(_authority.getAuthrityURL());
 			}
 			key=_authority.getParentAuthrityName()+"@~"+_authority.getSortNo();
 			childAuthorityList=_map.get(key);
 			if(childAuthorityList==null){
 				childAuthorityList=new ArrayList<MerchantsAuthority>();
 	 			_map.put(key, childAuthorityList);
 			}
 			childAuthorityList.add(_authority);
		}
 		/*for(Element e : indexResources){
 			authrityUrls.add(XmlTool.getAttributeVal(e, "url"));
 		}*/
 		SessionUtil.setSaveSession("authorityList", authrityUrls, request);
 		SessionUtil.setSaveSession("authrityMap", _map, request);
	}
 	
 	/**
 	 * 保存邮箱信息
 	 */
	public void saveEmail(String email,String id) throws Exception{
		Map pram=new HashMap<String,String>();
		pram.put("email", email);
		pram.put("id", id);
		merchantUsersMapper.saveEmail(pram);
	}
	
	/**
	 * 更新邮箱信息
	 */
	public void updateEmail(String id,String email,int emailStatus) throws Exception{
		Map pram=new HashMap<String,String>();
		pram.put("email", email);
		pram.put("id", id);
		pram.put("emailstatus", emailStatus);
		merchantUsersMapper.updateEmail(pram);
	}

	/**
	 * 发送邮件
	 */
	@Override
	public void sandMail(String addresss, String title, String content,String loginName)
			throws Exception {
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setToAddress(addresss);
		mailInfo.setTitle(title);
		mailInfo.setContent(content);
		mailInfo.setSubject(SubjectIdType.SUBJECT_ID_MERCHANT_FINDPWD);
		mailInfo.setModelType(com.yougou.component.email.model.ModelType.MODEL_TYPE_MERCHANT_FINDPWD);
		emailApi.sendNow(mailInfo);
		//logger.info("邮箱地址:"+addresss+" 状态:"+state.getEmailState());//配置同步才支持
		//记录信息发送的日志
		//短信记录
		Message message = new Message();
		message.setId(UUIDGenerator.getUUID());
		message.setOperated(new Date());
		message.setLoginName(loginName);
		message.setTo(addresss);
		//信息的类型   0短信  1邮件
		message.setType("1");
		message.setContent(content);
		messageMapper.saveMessage(message);
	}
	
	/**
	 * 发送邮件
	 */
	@Override
	public void sendMail(String addresss, String title, 
			String content, String subjectType, 
			String modelType, String loginName)
			throws Exception {
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setToAddress(addresss);
		mailInfo.setTitle(title);
		mailInfo.setContent(content);
		mailInfo.setSubject(subjectType);
		mailInfo.setModelType(modelType);
		emailApi.sendNow(mailInfo);
		//logger.info("邮箱地址:"+addresss+" 状态:"+state.getEmailState());//配置同步才支持
		//记录信息发送的日志
		//短信记录
		Message message = new Message();
		message.setId(UUIDGenerator.getUUID());
		message.setOperated(new Date());
		message.setLoginName(loginName);
		message.setTo(addresss);
		//信息的类型   0短信  1邮件
		message.setType("1");
		message.setContent(content);
		messageMapper.saveMessage(message);
	}

	@Override
	public String queryUidByMerchantCode(String merchantCode) {
		return merchantUsersMapper.queryUidByMerchantCode(merchantCode);
	}

	//查找商家常用的快递
	@Override
	public List<Map<String, Object>> getfrequentUseCompanyList(String merchantCode)
			throws Exception {
        return merchantUsersMapper.queryCommonUseLogisticsCompany(merchantCode);
	}

	@Override
	public boolean saveCommonUseExpress(CommonUseLogisticsCompany company) {
		boolean flag = false;
		try{
			merchantUsersMapper.saveCommonUseExpress(company);
			flag = true;
		}catch(Exception e){
			logger.error("设置商家常用快递，服务器发生错误！",e);
		}
		return flag;
	}

	@Override
	public boolean checkHasCommonUseLogistics(String merchantCode) {
		int size = merchantUsersMapper.getCommonUseLogisticsSize(merchantCode);
		return size>0?true:false;
	}
	
	@Override
	public boolean deleteCommonUseExpress(String merchantCode) {
		boolean flag = false;
		try{
			merchantUsersMapper.deleteCommonUseExpress(merchantCode);
			flag = true;
		}catch(Exception e){
			logger.error("删除商家常用快递，服务器发生错误！",e);
		}
		return flag;
	}


	@Override
	public boolean updateMobilePhone(String loginName,String mobileCode) {
		boolean flag = false;
		try{
			merchantUsersMapper.updateMobilePhone(loginName,mobileCode);
			flag = true;
		}catch(Exception e){
			flag = false;
			logger.error("商家账号{}，绑定手机号码发生错误：",new Object[]{loginName,e});
		}
		return flag;
	}
	
	@Override
	public Map<String,Object> getMobilePhoneOfLoginName(String loginName) {
		return merchantUsersMapper.getMobilePhoneOfLoginName(loginName);
	}
	
	@Override
	public boolean exitsTelePhoneOfMerchantUsers(String mobileCode,String loginName) {
		boolean flag = false;
		try{
			if(merchantUsersMapper.exitsTelePhoneOfMerchantUsers(mobileCode,loginName)>0){
				flag = false;
			}else{
				flag = true;
			}
		}catch(Exception e){
			logger.error("查询手机号码是否已绑定账号发生错误",e);
		}
		return flag;
	}
	
	@Override
	public boolean exitsEmailOfMerchantUsers(String email) {
		boolean flag = false;
		try{
			if(merchantUsersMapper.exitsEmailOfMerchantUsers(email)>0){
				flag = false;
			}else{
				flag = true;
			}
		}catch(Exception e){
			logger.error("查询邮箱：{}是否已绑定账号发生错误",new Object[]{email,e});
		}
		return flag;
	}

	@Override
	public int getMerchantAuthorityCountVoById(String uid) {
		return merchantUsersMapper.getMerchantAuthorityCountVoById(uid);
	}
	@Override
	public List<String> queryAccountByMerchantCode(String merchantCode) {
		return merchantUsersMapper.queryAccountByMerchantCode(merchantCode);
	}
}
