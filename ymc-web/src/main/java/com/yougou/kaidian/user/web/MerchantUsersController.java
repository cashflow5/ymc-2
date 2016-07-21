package com.yougou.kaidian.user.web;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.belle.finance.costsettlement.costsetofbooks.model.vo.CostSetofBooks;
import com.yougou.component.email.model.SubjectIdType;
import com.yougou.kaidian.commodity.model.vo.MessageBean;
import com.yougou.kaidian.commodity.util.CommodityUtilNew;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.common.constant.ErrorConstant;
import com.yougou.kaidian.common.util.DateUtil;
import com.yougou.kaidian.common.util.FileFtpUtil;
import com.yougou.kaidian.common.util.HttpUtil;
import com.yougou.kaidian.common.util.Md5Encrypt;
import com.yougou.kaidian.common.util.StringUtil;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.kaidian.framework.beans.CooperationModel;
import com.yougou.kaidian.framework.constant.Constant;
import com.yougou.kaidian.framework.constant.SystemConfig;
import com.yougou.kaidian.framework.exception.YMCException;
import com.yougou.kaidian.framework.permission.ResourceMonitor;
import com.yougou.kaidian.framework.servlet.IJsonTool;
import com.yougou.kaidian.framework.servlet.JsonTool;
import com.yougou.kaidian.framework.settings.CommoditySettings;
import com.yougou.kaidian.framework.util.SessionUtil;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.stock.service.IWarehouseService;
import com.yougou.kaidian.user.constant.UserConstant;
import com.yougou.kaidian.user.dao.MerchantSupplierExpandMapper;
import com.yougou.kaidian.user.dao.SupplierContractMapper;
import com.yougou.kaidian.user.model.pojo.ApiKey;
import com.yougou.kaidian.user.model.pojo.Area;
import com.yougou.kaidian.user.model.pojo.ContactsFormVo;
import com.yougou.kaidian.user.model.pojo.HelpMenu;
import com.yougou.kaidian.user.model.pojo.MerchantCenterOperationLog;
import com.yougou.kaidian.user.model.pojo.MerchantUsers;
import com.yougou.kaidian.user.model.pojo.MerchantconsignmentAdress;
import com.yougou.kaidian.user.model.pojo.MerchantsAuthority;
import com.yougou.kaidian.user.model.pojo.SupplierContract;
import com.yougou.kaidian.user.model.pojo.SupplierLinkMan;
import com.yougou.kaidian.user.service.FeebackService;
import com.yougou.kaidian.user.service.IApiKeyService;
import com.yougou.kaidian.user.service.IMerchantCenterOperationLogService;
import com.yougou.kaidian.user.service.IMerchantCenterTrustIpService;
import com.yougou.kaidian.user.service.IMerchantInfo;
import com.yougou.kaidian.user.service.IMerchantUsers;
import com.yougou.merchant.api.supplier.service.ISupplierService;
import com.yougou.merchant.api.supplier.vo.ContactsVo;
import com.yougou.merchant.api.supplier.vo.MerchantRejectedAddressVo;
import com.yougou.merchant.api.supplier.vo.MerchantSupplierExpand;
import com.yougou.merchant.api.supplier.vo.MerchantUser;
import com.yougou.merchant.api.supplier.vo.RejectedAddressVo;
import com.yougou.merchant.api.supplier.vo.SupplierVo;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.model.brand.Brand;
import com.yougou.wms.wpi.warehouse.domain.WarehouseDomain;

/**
 * 用户信息controll类
 * 
 * @author wang.m
 * @Date 2012-03-12
 * 
 */
@Controller
@RequestMapping("merchants/login")
public class MerchantUsersController {

	private Logger logger = LoggerFactory.getLogger(MerchantUsersController.class);
	
	@Resource
	private IMerchantUsers merchantUsers;
	@Resource
	private CommoditySettings commoditySettings;
	@Resource
	private FeebackService feebackService;
	@Resource
	private ISupplierService iSupplierService;
	@Resource(name = "redisTemplate")
	private RedisTemplate<String, Object> redisTemplate;
	@Resource(name = "stringRedisTemplate")
	private SetOperations<String, String> setOperations;
	@Resource(name = "stringRedisTemplate")
	private HashOperations<String, String, String> hashOperations;
	@Resource
	private IWarehouseService warehouseService;
	@Resource
	private SystemConfig systemConfig;
	
    @Resource
    @Qualifier(value = "jmsTemplate")
    private AmqpTemplate amqpTemplate;
    @Resource
    private ICommodityBaseApiService commodityBaseApiService;
    @Resource
    private IMerchantCenterOperationLogService  operationLogService;
    @Resource
    private IMerchantCenterTrustIpService trustIpService;
    @Autowired
    private ResourceMonitor monitor;
    @Autowired
    private IApiKeyService apiKeyService;
    @Resource
	private MerchantSupplierExpandMapper merchantSupplierExpandMapper;
	@Resource
	private IMerchantInfo merchantInfo;
	@Resource
	private ISupplierService supplierService;
	/**
	 * 跳转到登录首页
	 * 
	 * @author wang.m
	 * @param request
	 * @param loginAccount
	 * @param loginPassword
	 * @return
	 */
	@RequestMapping("/to_login")
	public String to_login() {
		return "manage/login/merchants_login";
	}
	
	/**
	 * 获取商家合作模式
	 * 
	 * @author wang.m
	 * @param request
	 * @param loginAccount
	 * @param loginPassword
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/getSupplierDistributionSide")
    public String getSupplierDistributionSide(String merchantCode) throws Exception {
        CooperationModel model = null;
        if (StringUtils.isNotBlank(merchantCode)) {
            SupplierVo _vo = iSupplierService.getSupplierByMerchantCode(merchantCode);
            Integer identifier = _vo.getIsInputYougouWarehouse();
            model = CooperationModel.forIdentifier(identifier);
        }
        return model == null ? "" : model.getDescription() + "," + model.getDistributionType();
    }
	
	/**
	 * 登录验证
	 * @param request
	 * @param loginName
	 * @param pwd
	 * @param code
	 * @param map
	 * @param query
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/merchantsLogin")
	public String merchantsLogin(HttpServletRequest request, final String loginName,String pwd, String code) {
		Map<String, Object> loginUser = null;
		// 用户名或密码为空
		if (StringUtils.isBlank(loginName) || StringUtils.isBlank(pwd)) return "{\"msg\":\"loginNameOrPwdIsNull\"}";
		// 验证码为空
		if (StringUtils.isBlank(code)) return "{\"msg\":\"codeIsNull\"}";
		
		String imagevalidate = (String) request.getSession().getAttribute("login_validate_image");
		// 判断验证码是否正确
		if (!code.equalsIgnoreCase(imagevalidate)) return "{\"msg\":\"codeIsFault\"}";
		// 服务器验证码不存在
		if (StringUtils.isBlank(imagevalidate)) return "{\"msg\":\"codeIsExpire\"}";
		ExecutorService threadPool = null;
		try {
			loginUser = merchantUsers.merchantLoginByUserName(loginName);
			// 用户名不存在
			if (loginUser == null) return "{\"msg\":\"loginNameIsNotExist\"}";
			
			String password = Md5Encrypt.md5(pwd);
			// 判断密码是否正确
			if (!password.equalsIgnoreCase(MapUtils.getString(loginUser, "password"))) return "{\"msg\":\"pwdIsFault\"}";
					
			if ("0".equals(MapUtils.getString(loginUser, "is_yougou_admin"))) {
				String merchant_code = MapUtils.getString(loginUser, "merchant_code");
				if (StringUtils.isBlank(merchant_code)) return "{\"msg\":\"merchant_codeIsNull\"}";
				Map<String, Object> merchantUser = merchantUsers.getPresentMerchantAndWarehouse(merchant_code);
				/* -1:停用 1:启用 2:新建 */
				String is_valid = MapUtils.getString(merchantUser, "is_valid");
				if (StringUtils.isNotBlank(is_valid)) {
					if ("-1".equals(is_valid)) return "{\"msg\":\"is_valid_stop\"}";
					else if ("2".equals(is_valid)) return "{\"msg\":\"is_valid_new\"}";
					
					String status = MapUtils.getString(loginUser, "status");
					String isDelete = MapUtils.getString(loginUser, "delete_flag");
					if (!"1".equals(status)) return "{\"msg\":\"isCongeal\"}";
					else if (!"1".equals(isDelete)) return "{\"msg\":\"isDelete\"}";	
				}
				
				if (MapUtils.isNotEmpty(merchantUser)) loginUser.putAll(merchantUser);
			}else if("2".equals(MapUtils.getString(loginUser, "is_yougou_admin"))){
				String status = MapUtils.getString(loginUser, "status");
				if (!"1".equals(status)) return "{\"msg\":\"isCongeal\"}";
			}
			
			// 将登录对象保存到session?
			SessionUtil.setSaveSession("merchantUsers", loginUser, request);
			String brower=request.getHeader("user-agent");//获取浏览器名称 
			logger.warn("统计商家登录情况:商家编码:{}({}) 访问IP：{} 浏览器: {}",
					new Object[]{MapUtils.getString(loginUser, "supplier_code"),
					MapUtils.getString(loginUser, "supplier"),
					HttpUtil.getRemortIP(request),brower});
			//先判断是否绑定手机，没有绑定手机不验证身份
			// updated by zhangfeng 2016.5.22 直接异步线程处理，不用每次创建单个线程池处理
			/*threadPool = Executors.newFixedThreadPool(1);// 线程池
			final MerchantCenterOperationLog operationLog = new MerchantCenterOperationLog(request);
			//避免拦截器之前，未设置值导致为空
			operationLog.setLoginName(MapUtils.getString(loginUser, "login_name"));
			operationLog.setMerchantCode(MapUtils.getString(loginUser, "merchant_code"));
			operationLog.setType(1);
			threadPool.execute(new Runnable(){
				@Override
				public void run() {
					if(!(operationLogService.insertOperationLog(operationLog))){
						logger.error("记录登录日志报错！");
					}
					Map<String,Object> mobileMap = merchantUsers.getMobilePhoneOfLoginName(loginName);
					final String mobileCode = MapUtils.getString(mobileMap, "mobile_code");
					trustIpService.checkTrustAddrByAddr(operationLog,loginName,mobileCode);
				}
			});*/
			// 异步保存登陆日志  先判断是否绑定手机，没有绑定手机不验证身份
			final MerchantCenterOperationLog operationLog = new MerchantCenterOperationLog(request);
			//避免拦截器之前，未设置值导致为空
			operationLog.setLoginName(MapUtils.getString(loginUser, "login_name"));
			operationLog.setMerchantCode(MapUtils.getString(loginUser, "merchant_code"));
			operationLog.setType(1);
			Runnable run = new Runnable(){
				@Override
				public void run() {
					logger.warn("线程{},开始记录用户登录日志", Thread.currentThread().getName());
					if(!(operationLogService.insertOperationLog(operationLog))){
						logger.error("记录登录日志报错！");
					}
					Map<String,Object> mobileMap = merchantUsers.getMobilePhoneOfLoginName(loginName);
					final String mobileCode = MapUtils.getString(mobileMap, "mobile_code");
					trustIpService.checkTrustAddrByAddr(operationLog,loginName,mobileCode);
					logger.warn("线程{},结束记录用户登录日志", Thread.currentThread().getName());
				}
			};
			Thread thread = new Thread(run);
    		thread.setName("merchantsLogin 商家中心用户登录日志记录");
    		thread.start();
		} catch (Exception e) {
			logger.error("用户登录系统产生异常:",e);
			return "{\"msg\":\"failure\"}";
		}finally{
			if (threadPool != null) {
				threadPool.shutdown();
				threadPool = null;
			}
		}
		return "{\"msg\":\"sucuess\"}";
	}

	@ResponseBody
	@RequestMapping("/checkCode")
	public String checkCode(HttpServletRequest request, String code) {
		if (StringUtils.isBlank(code))
			return "codeIsNull";

		String imagevalidate = (String) request.getSession().getAttribute(
				"login_validate_image");
		// 判断验证码是否正确
		if (!code.equalsIgnoreCase(imagevalidate))
			return "codeIsFault";
		// 服务器验证码不存在
		if (StringUtils.isBlank(imagevalidate))
			return "codeIsExpire";

		return "sucuess";
	}

	/**
	 * 跳转到首页
	 * 
	 * @author wang.m
	 * @param request
	 * @param loginAccount
	 * @param loginPassword
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/to_index")
	public String to_index(HttpServletRequest request, ModelMap map) throws Exception {
		Map<String, Object> mapMerchantUser = (Map<String, Object>) request.getSession().getAttribute("merchantUsers");
		final String loginName = MapUtils.getString(mapMerchantUser, "login_name");
		logger.info("Merchant user :{} to_index.", loginName);
		// 先将登录时间存放到临时缓存
		String historyLoginTime = (String) this.redisTemplate.opsForHash()
				.get(CacheConstant.C_USER_LOGIN_TIME_TEMP_KEY, YmcThreadLocalHolder.getOperater());
		this.redisTemplate.opsForHash().put(CacheConstant.C_USER_LOGIN_TIME_TEMP_KEY,
				YmcThreadLocalHolder.getOperater(), DateUtil.getFormatByDate(new Date()));
		this.redisTemplate.opsForHash().put(CacheConstant.C_USER_LOGIN_TIME_KEY, 
				YmcThreadLocalHolder.getOperater(), historyLoginTime);
		String uid = MapUtils.getString(mapMerchantUser, "id");
		String merchantCode = null;
		Set<String> authrityMap = null;
		if (MapUtils.isNotEmpty(mapMerchantUser) && StringUtils.isNotBlank(uid)) {
			Map<String, Object> mapMerchantMessage = merchantUsers.showMerchantMessage(uid);
			map.addAttribute("merchant", mapMerchantMessage);
			merchantCode = MapUtils.getString(mapMerchantUser, "merchantCode");
			//假如是优购管理员,将选出的商家权限选出
			if("1".equals(MapUtils.getString(mapMerchantUser, "is_yougou_admin", null))){
				//主账号的id
				String userId=merchantUsers.queryUidByMerchantCode(merchantCode);
				uid=userId;
			}
			//判断登录IP是否是可信任的IP
			//插入马上读取，防止数据还未插入成功，就先读取出来，所以先从缓存读取，从缓存读取出来之后，再把缓存删掉
			String result = (String)redisTemplate.opsForValue().get(YmcThreadLocalHolder.getOperater()+":isAuthentication");
			if(StringUtils.isNotBlank(result) && Constant.SUCCESS.equals(result)){
				redisTemplate.delete(YmcThreadLocalHolder.getOperater()+":isAuthentication");
			}
			Set<String> allAuthrityUrls = (Set<String>)redisTemplate.opsForHash()
					.get(CacheConstant.C_All_RESOURCE, "allResource");
			SessionUtil.setSaveSession("allRes", allAuthrityUrls, request);
			this.putMerchantAuthoritySession(uid,request);
			//没有菜单权限,跳到错误页面提示
			authrityMap = (Set<String>) request.getSession().getAttribute("authorityList");
			logger.info("权限集合数量：{}",(authrityMap!=null?authrityMap.size():0));
			if("0".equals(MapUtils.getString(mapMerchantUser, "is_yougou_admin",null)) 
					&& CollectionUtils.isEmpty(authrityMap)){
				map.addAttribute("errorDesc", "该登录用户没有设置功能菜单!</br>登录用户如果是商家主账户请联系优购管理员设置,如果是子账户请联系商家主账户管理员!");
				return "manage/comm/error";
			}
			if (mapMerchantMessage == null && 
					("1".equals(MapUtils.getString(mapMerchantUser, "is_yougou_admin", null))
							||"2".equals(MapUtils.getString(mapMerchantUser, "is_yougou_admin", null)))) {
				if (StringUtils.isBlank(merchantCode)) {
					map.addAttribute("isSetMerchant", "true");// 标识进入首页
					mapMerchantUser.put("isSetMerchant", "true");
				} else {
					mapMerchantMessage = new HashMap<String, Object>(3);
					mapMerchantMessage.put("supplier_code", merchantCode);
					mapMerchantMessage.put("supplier", mapMerchantUser.get("supplier"));
					mapMerchantMessage.put("status", mapMerchantUser.get("status"));
					Integer isInputYougouWarehouse = MapUtils.getInteger(mapMerchantUser, "is_input_yougou_warehouse", 1);
					if (isInputYougouWarehouse == 0) //不入优购仓
						try {
							String warehouseCode = warehouseService.queryWarehouseCodeByMerchantCode(merchantCode);
							mapMerchantMessage.put("warehouse_code", warehouseCode);
							mapMerchantUser.put("warehouse_code", warehouseCode);
						} catch (Exception e) {
							throw new YMCException("未设置仓库编码", ErrorConstant.getErrorCode(ErrorConstant.MODULE_SYSTEM, ErrorConstant.E_0002));
						}
					
					map.addAttribute("merchant", mapMerchantMessage);
				}
			}
		}
		request.getSession().setAttribute("merchantUsers", mapMerchantUser);
		//商家登录成功后,临时图片文件夹校验商家编码文件夹，不存在新建一个
		if (mapMerchantUser.get("supplier_code") != null) {
			File merchantPicDir = new File(commoditySettings.picDir + File.separator + mapMerchantUser.get("supplier_code") + File.separator);
			if (!merchantPicDir.exists()) {
				merchantPicDir.mkdirs();
			}
		}
		map.addAttribute("tagTab", "main");// 标识进入首页
		logger.info("Merchant user :{} to_index success.", YmcThreadLocalHolder.getOperater());
		//查询帮助菜单
		Map<String,List<HelpMenu>> menuMap = feebackService.queryHelpMenuListByObj();
		//map.addAttribute("historyLoginTime", historyLoginTime);
		map.addAttribute("helpMenus_comm", menuMap.get("comm"));
		map.addAttribute("helpMenus_order", menuMap.get("order"));
		map.addAttribute("helpMenus_api", menuMap.get("api"));
		map.addAttribute("help_url", helpIndexUrl);
		//加入首页报表分析时间段
		map.addAttribute("analyzeSellDates", StringUtil.listToString(DateUtil.getDateStrMMddByNeerWeek(8), ","));
		//增加判断订单提示跳转的url
		//authrityMap = (Set<String>) request.getSession().getAttribute("authorityList");
		String order_authrity_url = null;
		String order_outstock_url = null;
		if (CollectionUtils.isNotEmpty(authrityMap)) {
			for (String _authrity_url : authrityMap) {
				if(_authrity_url!=null){
					//超时未发货订单
					if (_authrity_url.indexOf("order/queryPunishOrderList.sc")!=-1){
						map.addAttribute("a_timeOutOrder", true);
						continue;
					}
					//待商家处理工单
					if (_authrity_url.indexOf("dialoglist/query.sc")!=-1){
						map.addAttribute("a_gongdan", true);
						continue;
					}
					//库存少于5
					if (_authrity_url.indexOf("wms/supplyStockInput/querySupplyGenStock.sc")!=-1){
						map.addAttribute("a_orderTipsStockUnder", true);
						continue;
					}
					//待处理售后申请
					if (_authrity_url.indexOf("afterSale/queryAfterSaleList.sc")!=-1){
						map.addAttribute("a_afterSale", true);
						continue;
					}
					//待处理赔付工单
					if (_authrity_url.indexOf("afterSale/compensate_handling_list.sc")!=-1){
						map.addAttribute("a_compensate_count", true);
						continue;
					}
					
					//在线商品
					if (_authrity_url.indexOf("commodity/goQueryOnSaleCommodity.sc")!=-1){
						map.addAttribute("a_onSaleCommodity", true);
						continue;
					}
					//待销售商品
					if (_authrity_url.indexOf("commodity/goWaitSaleCommodity.sc")!=-1){
						map.addAttribute("a_waitSaleCommodity", true);
						continue;
					}
					//需开具发票
					if (_authrity_url.indexOf("invoice/query.sc")!=-1){
						map.addAttribute("a_query", true);
						continue;
					}
					if (_authrity_url.indexOf("order/toDocumentPrintingNew.sc")!=-1) {
						order_authrity_url = _authrity_url;
						order_outstock_url = "order/toPrintNewOutstock.sc";
						continue;
					} else if (_authrity_url.indexOf("order/toDocumentPrinting.sc")!=-1) {
						order_authrity_url = _authrity_url;
						order_outstock_url = "order/toPrintOutstock.sc";
						continue;
					} else if (_authrity_url.indexOf("order/fastdelivery/notexported.sc")!=-1) {
						order_authrity_url = _authrity_url;
						order_outstock_url = "order/fastdelivery/outoforders.sc";
					}
				} 
			}
		}
		if(order_authrity_url!=null && order_authrity_url.startsWith("/")){
			//把开头的/去掉，否则首页链接有问题
			map.addAttribute("order_authrity_url", order_authrity_url.substring(1));
		}else{
			map.addAttribute("order_authrity_url", order_authrity_url);
		}
		map.addAttribute("order_outstock_url", order_outstock_url);
		map.put("id", uid);
		//屏蔽优购管理员与业务管理员账号，只针对商家的账号进行判断
		if(MapUtils.getString(mapMerchantUser, "supplier_code")!=null 
				&& MapUtils.getInteger(mapMerchantUser, "is_yougou_admin")==0){
			Map<String, Object> mobilemap = merchantUsers.getMobilePhoneOfLoginName(YmcThreadLocalHolder.getOperater());
			if(StringUtils.isNotBlank(MapUtils.getString(mobilemap, "mobile_code"))){
				//已绑定手机号码
				map.put("mobilePhone",1);
			}else{
				//未绑定手机号码且为子账号
				if(mobilemap!=null && 
						NumberUtils.toInt(String.valueOf(MapUtils.getInteger(mobilemap, "is_administrator")))==0){
					//权限重新分配，防止账号绕过没有绑定手机的提示，直接访问url访问
					SessionUtil.setSaveSession("authorityList", new HashSet<String>(), request);
					return "manage_publish/security/no_mobile_warn";
				}
			}
		}
		return "manage/merchants/merchant_index";
	}

	@SuppressWarnings("unchecked")
	private void putMerchantAuthoritySession(String uid,HttpServletRequest request) {
		Map<String,Set<String>> authReourecesMap = 
				(Map<String,Set<String>>)redisTemplate.opsForHash()
				.get(CacheConstant.C_USER_REOURCE_AUTH, "authReourecesMap");
		Map<String,Map<String, List<MerchantsAuthority>>> authMap = 
				(Map<String,Map<String, List<MerchantsAuthority>>>)redisTemplate.opsForHash()
				.get(CacheConstant.C_USER_AUTH, "authMap");
		Map<String,Integer> userAuthCountMap = 
				(Map<String,Integer>)redisTemplate.opsForHash().get(CacheConstant.C_USERS_AUTH_COUNT,"authCount");
		//查询数据库菜单数量是否与缓存的数量一致，不一致就重新查询数据库，并更新缓存
		if(userAuthCountMap!=null  
				&& NumberUtils.toInt(String.valueOf(userAuthCountMap.get(uid))) 
					!= merchantUsers.getMerchantAuthorityCountVoById(uid)){
			logger.warn("该用户的权限菜单缓存数量与数据库不一致，重新查询数据库获取用户uid{}权限，并放入缓存",new Object[]{uid});
			//缓存没有，查数据库表一遍
			try {
				monitor.loadAuthResource(uid);
				authMap = (Map<String,Map<String, List<MerchantsAuthority>>>)redisTemplate.opsForHash()
						.get(CacheConstant.C_USER_AUTH, "authMap");
				authReourecesMap = 
						(Map<String,Set<String>>)redisTemplate.opsForHash()
						.get(CacheConstant.C_USER_REOURCE_AUTH, "authReourecesMap");
			} catch (Exception e) {
				logger.error("该用户的权限菜单缓存数量与数据库不一致，重新查询数据库获取用户uid{}权限，并放入缓存失败",new Object[]{uid,e});
			}
		}
		SessionUtil.setSaveSession("authorityList", authReourecesMap.get(uid), request);
 		SessionUtil.setSaveSession("authrityMap", authMap.get(uid), request);
	}

	/**
	 * 跳转到设置商家列表
	 * 
	 * @author wang.m
	 * @param request
	 * @param loginAccount
	 * @param loginPassword
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/toSetMerchant")
	public String toSetMerchant(HttpServletRequest request, ModelMap modelMap, Query query, String merchantCode, String merchantName, String brand) throws Exception {
		query.setPageSize(10);
		List<Brand> brandList = null;
		if (StringUtils.isNotEmpty(brand)) {
			brandList = commodityBaseApiService.getBrandListLikeBrandName("%" + brand, (short) 1);
		}
		Map<String, Object> mapMerchantUser = (Map<String, Object>) request.getSession().getAttribute("merchantUsers");
		if (mapMerchantUser.get("is_yougou_admin") != null && ("1".equals(mapMerchantUser.get("is_yougou_admin").toString())||"2".equals(mapMerchantUser.get("is_yougou_admin").toString()))) {
			modelMap.put("pageFinder", merchantUsers.showYougouAdminMerchant(query, mapMerchantUser.get("id").toString(), merchantCode, merchantName, brandList));
		}
		modelMap.put("merchantCode", merchantCode);
		modelMap.put("merchantName", merchantName);
		modelMap.put("brand", brand);
		return "manage_unless/merchants/merchant_yougou_list";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/toSetPresentMerchant")
	public String toSetPresentMerchant(HttpServletRequest request, ModelMap modelMap, String merchantCode) throws Exception {
		Map<String, Object> mapMerchantUser = (Map<String, Object>) request.getSession().getAttribute("merchantUsers");
		if("1".equals(MapUtils.getString(mapMerchantUser, "is_yougou_admin", null))
				||"2".equals(MapUtils.getString(mapMerchantUser, "is_yougou_admin", null))){
			mapMerchantUser.putAll(merchantUsers.getPresentMerchantAndWarehouse(merchantCode));
		}
		modelMap.addAttribute("message", "成功设置当前商家！");
		modelMap.addAttribute("refreshflag", "1");
		mapMerchantUser.put("merchantCode", merchantCode);
		request.getSession().setAttribute("merchantUsers", mapMerchantUser);
		modelMap.addAttribute("methodName", "/merchants/login/to_index.sc");
		modelMap.addAttribute("result", "success");
		return "manage_unless/merchants/merchants_message";
	}

	/**
	 * 跳转到前台商家基本信息页面
	 * 
	 * @author wang.m
	 * @param request
	 * @param loginAccount
	 * @param loginPassword
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/to_MerchantsUser")
	public String to_MerchantsUser(String listKind,ModelMap modelMap, Query query, HttpServletRequest request) throws Exception {
//			Map<String, Object> userList = merchantUsers.getMerchantsUser(request);
//			map.addAttribute("userList", userList);
//			map.addAttribute("tagTab", "setting-base");
			String merchantCode = YmcThreadLocalHolder.getMerchantCode();
			
			modelMap.addAttribute("listKind", listKind);
			// 根据id查询商家基本信息
			SupplierVo vo = supplierService.getSupplierByMerchantCode(merchantCode);//merchantServiceNew.getSupplierVoById(id);
			modelMap.addAttribute("supplier", vo);

			// 商家扩展表信息
			MerchantSupplierExpand supplierExpand = merchantSupplierExpandMapper.selectBySupplierId(vo.getId());
			modelMap.addAttribute("supplierExpand", supplierExpand);		
			
			SupplierContract supplierContract = merchantInfo.getSupplierContractBySupplierId(vo.getId());
			modelMap.put("supplierContract", supplierContract);		
			return "manage/merchants/view_supplier_merchant";
			//return "manage/merchants/merchant_user_list";
	}
	
	@RequestMapping("/to_merchant_shop")
	public String to_merchant_shop(String listKind,ModelMap modelMap, Query query, HttpServletRequest request) throws Exception {
		modelMap.addAttribute("listKind", listKind);
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		// 根据id查询商家基本信息
		SupplierVo vo = supplierService.getSupplierByMerchantCode(merchantCode);
		modelMap.addAttribute("supplier", vo);

		// 是招商供应商才查询
		MerchantUser merchantUser = null;
		MerchantRejectedAddressVo rejectedAddress = null;
		if (null != vo) {
			// 根据商家编号查询商家登录信息
			merchantUser = merchantInfo.getMerchantsBySuppliceCode(merchantCode);//merchantServiceNew.getMerchantsBySuppliceCode(merchantCode);

			rejectedAddress = merchantSupplierExpandMapper.getRejectedAddressBySupplierCode(merchantCode);
		}

		modelMap.addAttribute("merchantUser", merchantUser);

		modelMap.addAttribute("rejectedAddress", rejectedAddress);

		// 构建5种联系人组成的FormVo
		ContactsFormVo contactsFormVo = buildContactsFormVo(vo.getId());

		modelMap.addAttribute("contactsFormVo", contactsFormVo);	
		return "manage/merchants/view_supplier_shop";
	}
	
	/**
     * 查看供应商合同--招商供应商
     * @param modelMap
     * @param request
     * @param id
     * @param type
     * @return
     * @throws Exception
     */
    @RequestMapping("to_view_supplier_contract")
   	public String toViewSupplierContract(ModelMap modelMap, String id, String listKind) throws Exception {
    	modelMap.addAttribute("listKind", listKind);
    	
    	// 财务成本套帐
		List<CostSetofBooks> costSetofBooksList = merchantInfo.getCostSetofBooksList();
		modelMap.addAttribute("costSetofBooksList", costSetofBooksList);
    	// 根据id查询商家基本信息
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		// 根据id查询商家基本信息
		SupplierVo vo = supplierService.getSupplierByMerchantCode(merchantCode);
		modelMap.addAttribute("supplier", vo);
		
		SupplierContract supplierContract = merchantInfo.getSupplierContractBySupplierId(vo.getId());
		modelMap.put("supplierContract", supplierContract);
		
		// 商家扩展表信息
		MerchantSupplierExpand supplierExpand = merchantSupplierExpandMapper.selectBySupplierId(vo.getId());
		modelMap.addAttribute("supplierExpand", supplierExpand);	
		
    	return "manage/merchants/view_supplier_contract";
   	}
	private ContactsFormVo buildContactsFormVo(String supplierId) {
		ContactsFormVo contactsFormVo = new ContactsFormVo();
		contactsFormVo.setSupplyId(supplierId);
		List<ContactsVo> contactList = merchantSupplierExpandMapper.getContactsBySupplierId(supplierId);
		contactsFormVo.setContactList(contactList);
		contactsFormVo.initFormVoByContactList();// 初始化各字段

		return contactsFormVo;
	}

	/**
	 * 跳转到前台商家合同页面
	 * 
	 * @author wang.m
	 * @param request
	 * @param loginAccount
	 * @param loginPassword
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/to_supplierContract")
	public String to_supplierContract(ModelMap map, Query query, HttpServletRequest request) throws Exception {
		PageFinder<Map<String, Object>> pageFinder = merchantUsers.getSupplierContractList(request, query);
		if (pageFinder != null && pageFinder.getData() != null && pageFinder.getData().size() > 0) {
			map.addAttribute("pageFinder", pageFinder);
		} else {
			map.addAttribute("pageFinder", null);
		}
		map.addAttribute("tagTab", "setting-base");
		return "manage/merchants/merchant_contract_list";
	}

	/**
	 * 跳转到前台商家联系人页面
	 * 
	 * @author wang.m
	 * @param request
	 * @param loginAccount
	 * @param loginPassword
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/to_supplierLinkMan")
	public String to_supplierLinkMan(ModelMap map, Query query, HttpServletRequest request) throws Exception {
		PageFinder<SupplierLinkMan> pageFinder = merchantUsers.getSupplierLinkManList(request, query);
		if (pageFinder != null && pageFinder.getData() != null && pageFinder.getData().size() > 0) {
			map.addAttribute("pageFinder", pageFinder);
		} else {
			map.addAttribute("pageFinder", null);
		}
		map.addAttribute("tagTab", "setting-base");
		return "manage/merchants/merchant_linkman_list";
	}

	/**
	 * 跳转到前台添加商家联系人页面
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/to_addLinkMan")
	public String to_addLinkMan(ModelMap map, HttpServletRequest request) throws Exception {
		Map<String, Object> merchant = merchantUsers.getMerchantsUser(request);
		map.addAttribute("merchant", merchant);
		map.addAttribute("tagTab", "setting-base");
		return "/manage_unless/merchants/add_merchant_linkman";
	}

	/**
	 * 添加商家联系人信息
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/add_linkMan")
	public String add_linkMan(ModelMap map, SupplierLinkMan supplierLinkMan, HttpServletRequest request) throws Exception {
		boolean bool = merchantUsers.add_linkMan(map, supplierLinkMan, request);
		if (bool) {
			map.addAttribute("message", "添加联系人信息成功！");
			map.addAttribute("refreshflag", "1");
			map.addAttribute("methodName", "/merchants/login/to_supplierLinkMan.sc");
			map.addAttribute("tagTab", "setting-base");
			map.addAttribute("result", "success");
			return "/manage/merchants/merchants_message";
		} else {
			map.addAttribute("message", "添加联系人信息失败！");
			map.addAttribute("refreshflag", "1");
			map.addAttribute("methodName", "/merchants/login/to_supplierLinkMan.sc");
			map.addAttribute("tagTab", "setting-base");
			map.addAttribute("result", "fail");
			return "/manage/merchants/merchants_message";
		}
	}

	/**
	 * 跳转到前台修改商家联系人页面
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/to_updateLinkMan")
	public String to_updateLinkMan(ModelMap map, String id) throws Exception {
		SupplierLinkMan spContact = merchantUsers.getSupplierLinkManById(id);
		map.addAttribute("spContact", spContact);
		map.addAttribute("tagTab", "setting-base");
		return "/manage/merchants/update_merchant_linkman";
	}

	/**
	 * 修改商家联系人信息
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/update_linkMan")
	public String update_linkMan(ModelMap map, SupplierLinkMan supplierLinkMan) throws Exception {
		boolean bool = merchantUsers.update_linkMan(map, supplierLinkMan);
		if (bool) {
			map.addAttribute("message", "修改联系人信息成功!");
			map.addAttribute("refreshflag", "1");
			map.addAttribute("methodName", "/merchants/login/to_supplierLinkMan.sc");
			map.addAttribute("tagTab", "setting-base");
			map.addAttribute("result", "success");
			return "/manage/merchants/merchants_message";
		} else {
			map.addAttribute("message", "修改联系人信息失败!");
			map.addAttribute("refreshflag", "1");
			map.addAttribute("methodName", "/merchants/login/to_supplierLinkMan.sc");
			map.addAttribute("tagTab", "setting-base");
			map.addAttribute("result", "fail");
			return "/manage/merchants/merchants_message";
		}
	}

	/**
	 * 跳转到商家中心帐号信息显示页面
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/to_merchants")
	public String to_merchants(ModelMap map, Query query, 
			MerchantUsers merchant, 
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		//验证主账号的身份
		String loginName = YmcThreadLocalHolder.getOperater();
		Map<String,Object> mobileMap = merchantUsers.getMobilePhoneOfLoginName(loginName);
		//根据商家编码、登录账号查询手机是否绑定
		int masterAccountOrNot = NumberUtils.toInt(String.valueOf(MapUtils.getInteger(mobileMap, "is_administrator")));
		Map<String, Object> loginBean = SessionUtil.getUnionUser(request);
		map.addAttribute("merchantUsers", loginBean);
		if(masterAccountOrNot==1){
			//主账号
			String mobileCode = MapUtils.getString(mobileMap, "mobile_code");
			if(StringUtils.isNotBlank(mobileCode)){
				//已绑定
				//是否通过身份验证，一次登录验证，本次登录不用再次验证
				String isAuthentication = (String)request.getSession().getAttribute("isAuthentication");
				if(Constant.SUCCESS.equals(isAuthentication)){
					PageFinder<Map<String, Object>> pageFinder = merchantUsers.queryMerchantsByWhere(query, merchant, loginName ,request);
					if (pageFinder != null && pageFinder.getData() != null && pageFinder.getData().size() > 0) {
						map.addAttribute("pageFinder", pageFinder);
					} else {
						map.addAttribute("pageFinder", null);
					}
					map.addAttribute("merchant", merchant);
					map.addAttribute("loginName",loginName);
					map.addAttribute("isAdministrator",masterAccountOrNot);
					map.addAttribute("tagTab", "setting-base");
				}else{
					return "manage/security/identity_verificate";
				}
			}else{
				//未绑定，跳转到账号管理页面
				CommodityUtilNew.getYgDgAlertThenRedirectScript(response, "请先绑定手机号码！", "/merchants/security/accountSecurity.sc");
				return null;
			}
		}else{
			//子账号
			PageFinder<Map<String, Object>> pageFinder = merchantUsers.queryMerchantsByWhere(query, merchant, loginName ,request);
			if (pageFinder != null && pageFinder.getData() != null && pageFinder.getData().size() > 0) {
				map.addAttribute("pageFinder", pageFinder);
			} else {
				map.addAttribute("pageFinder", null);
			}
			map.addAttribute("merchant", merchant);
			map.addAttribute("loginName",loginName);
			map.addAttribute("isAdministrator",masterAccountOrNot);
			map.addAttribute("tagTab", "setting-base");
		}
		return "manage/merchants/merchants_list";
	}

	/**
	 * 跳转到商家中心添加账号页
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 */
	@RequestMapping("/to_addMerchants")
	public String to_addMerchants(ModelMap map) {
		map.addAttribute("tagTab", "setting-base");
		return "manage_unless/merchants/add_merchants";
	}
	
	/**
	 * 添加帐号信息
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/add_merchants", method = RequestMethod.POST)
	public String add_merchants(ModelMap map, MerchantUsers merchant, HttpServletRequest request) throws Exception {
		map.addAttribute("refreshflag", "1");
		map.addAttribute("methodName", "/merchants/login/to_merchants.sc");
		map.addAttribute("tagTab", "setting-base");
		map.addAttribute("result", "fail");
		//先判断手机号是否唯一
		if(StringUtils.isNotBlank(merchant.getMobileCode())){
			if(merchantUsers.exitsTelePhoneOfMerchantUsers(merchant.getMobileCode(),null)){
				boolean bool = merchantUsers.add_merchants(merchant, request);
				if (bool) {
					map.addAttribute("result", "success");
					map.addAttribute("message", "添加帐号信息成功!");
				}else{
					map.addAttribute("message", "添加帐号信息失败!");
				}
			}else{
				map.addAttribute("message", "添加帐号信息失败，该手机号码已使用!");
			}
		}else{
			map.addAttribute("message", "添加账号信息失败，手机号码为空!");
		}
		return "manage_unless/merchants/merchants_message";
		
	}

	/**
	 * 根据ID加载商家帐号信息
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/inits_merchants")
	public String inits_merchants(ModelMap map, String id) throws Exception {
		MerchantUsers merchant = merchantUsers.inits_merchants(id);
		map.addAttribute("merchant", merchant);
		map.addAttribute("tagTab", "setting-base");
		return "manage_unless/merchants/update_merchants";
	}

	/**
	 * 修改商家基本状态
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/updateStatus")
	public String updateStatus(ModelMap map, MerchantUsers mer) throws Exception {
		map.addAttribute("refreshflag", "1");
		map.addAttribute("methodName", "/merchants/login/to_merchants.sc");
		map.addAttribute("tagTab", "setting-base");
		map.addAttribute("result", "fail");
		boolean bool = merchantUsers.updateStatus(mer);
		if (bool) {
			map.addAttribute("result", "success");
			map.addAttribute("message", "修改状态成功!");
		} else {
			map.addAttribute("message", "修改状态失败!");
		}
		return "manage_unless/merchants/merchants_message";
	}

	/**
	 * 修改帐号信息
	 * 
	 * @author wang.m
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/update_merchants",method = RequestMethod.POST)
	public String update_merchants(ModelMap map, MerchantUsers merchant, HttpServletRequest request) throws Exception {
		map.addAttribute("refreshflag", "1");
		map.addAttribute("methodName", "/merchants/login/to_merchants.sc");
		map.addAttribute("tagTab", "setting-base");
		map.addAttribute("result", "fail");
		if(StringUtils.isNotBlank(merchant.getMobileCode())){
			if(merchantUsers.exitsTelePhoneOfMerchantUsers(merchant.getMobileCode(),merchant.getLoginName())){
				boolean bool = merchantUsers.update_merchants(merchant, request);
				if (bool) {
					map.addAttribute("result", "success");
					map.addAttribute("message", "修改帐号信息成功!");
				} else {
					map.addAttribute("message", "修改帐号信息失败!");
				}
			}else{
				map.addAttribute("message", "修改帐号信息失败，该手机号码已使用!");
			}
		}else{
			map.addAttribute("message", "修改帐号信息失败，手机号码为空!");
		}
		return "manage_unless/merchants/merchants_message";
	}

	/**
	 * 下载合同附件
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping("/downMerchantsContractFile")
	public void downMerchantsContractFile(HttpServletResponse response, HttpServletRequest request, String fileName) {
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			FileFtpUtil ftp = new FileFtpUtil("10.10.10.181", 9999, "merchant", "ScU{{vPRc)X<");
			ftp.login();
			fileName = new String(fileName.getBytes("ISO8859_1"), "UTF-8");
			// 这个就就是弹出下载对话框的关键代�?
			response.setContentType("application/x-download ");
			response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859_1"));
			ftp.downRemoteFile("/contract/" + fileName, os);
			os.flush();
			ftp.logout();
		} catch (IOException e) {
			logger.error("下载合同附件IO流报错！",e);
		} catch (Exception e) {
			logger.error("下载合同附件报错！",e);
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				logger.error("下载合同附件，关闭流报错！",e);
			}

		}
	}

	/**
	 * 添加帐号信息时候 判断帐号是否存在
	 * 
	 * @author wang.m
	 * @throws Exception 
	 * @Date 2012-03-15
	 * 
	 */
	@ResponseBody
	@RequestMapping("/exitsLoginAccount")
	public String exitsLoginAccount(String loginAccount) throws Exception {
		boolean isExitsAccount = merchantUsers.exitsLoginAccount(loginAccount);
		if (isExitsAccount) {
			return "sucuess";
		} else {
			return "faise";
		}
	}

	/**
	 * 添加联系人时候 判断手机是否存在
	 * 
	 * @author wang.m
	 * @throws Exception 
	 * @Date 2012-03-15
	 * 
	 */
	@ResponseBody
	@RequestMapping("/exitsTelePhone")
	public String exitsTelePhone(String mobilePhone, HttpServletRequest request) throws Exception {
		boolean bool = merchantUsers.exitsTelePhone(mobilePhone, request);
		if (bool) {
			return "sucuess";
		} else {
			return "faise";
		}

	}

	/**
	 * 添加联系人时候 判断email是否存在
	 * 
	 * @author wang.m
	 * @throws Exception 
	 * @Date 2012-03-15
	 * 
	 */
	@ResponseBody
	@RequestMapping("/exitsEmail")
	public String exitsEmail(String email) throws Exception {
		boolean bool = merchantUsers.exitsEmail(email);
		if (bool) {
			return "sucuess";
		} else {
			return "faise";
		}

	}

	/**
	 * 退出系统
	 * 
	 * @author wang.m
	 */
	@RequestMapping("/to_Back")
    public String to_Back(ModelMap map, HttpServletRequest request) {
        // 删除session中的用户登录信息
        SessionUtil.removeSession(request);
        // 删除redis中的用户登录信息
        Cookie cookies[] = request.getCookies();
        Cookie sCookie = null;
        String sid = "";
        for (int i = 0; i < cookies.length; i++) {
            sCookie = cookies[i];
            if (sCookie.getName().equals("YGKD_SID")) {
                sid = sCookie.getValue();
                break;
            }
        }
        this.redisTemplate.opsForHash().delete(sid, "fssMap");
        return "manage/login/merchants_login";
    }

	/**
	 * 跳转到修改密码页面
	 * 
	 * @author wang.m
	 * @Date 2010-03-16
	 */
	@RequestMapping("/to_updatePassword")
	public String to_updatePassword(ModelMap map) {
		map.addAttribute("tagTab", "setting-base");
		return "manage/merchants/update_password";
	}

	/**
	 * 修改密码
	 * 
	 * @author wang.m
	 * @throws Exception 
	 * @Date 2010-03-16
	 */
	@RequestMapping("/update_password")
	public String update_password(ModelMap map, String newPassword, HttpServletRequest request) throws Exception {
		if (StringUtils.isNotBlank(newPassword)) {
			boolean bool = merchantUsers.update_password(newPassword, request);
			if (bool) {
				map.addAttribute("message", "修改密码成功!");
				map.addAttribute("refreshflag", "1");
				map.addAttribute("methodName", "/merchants/login/to_merchants.sc");
				map.addAttribute("tagTab", "setting-base");
				map.addAttribute("result", "success");
				return "manage_unless/merchants/merchants_message";
			} else {
				map.addAttribute("message", "修改密码失败!");
				map.addAttribute("refreshflag", "1");
				map.addAttribute("methodName", "/merchants/login/to_merchants.sc");
				map.addAttribute("tagTab", "setting-base");
				map.addAttribute("result", "fail");
				return "manage_unless/merchants/merchants_message";
			}
		}
		return "";
	}

	/**
	 * 验证输入的原密码是否正确
	 * 
	 * @author wang.m
	 * @throws Exception 
	 * @Date 2010-03-16
	 */
	@ResponseBody
	@RequestMapping("/extisPassword")
	public String extisPassword(ModelMap map, String password, HttpServletRequest request) throws Exception {
		if (StringUtils.isNotBlank(password)) {
			boolean bool = merchantUsers.extisPassword(password, request);
			if (bool) {
				return "sucuess";
			} else {
				return "failse";
			}
		}
		return "failse";
	}
	
	/**
	 * 跳转到分配子账户权限页面
	 * 
	 * @param uid账户ID
	 * @author wang.m
	 * @throws Exception 
	 * @date 2012-04-18
	 */

	@RequestMapping("/to_distributeAuthority")
	public String to_distributeAuthority(ModelMap map, String uid, String supplierCode) throws Exception {
		// 根据子账户商家编号查询管理员账户id所具备的权限列表
		List<Map<String, Object>> userAuthorityList = merchantUsers.getMerchantChildUserAuthorityList(uid, supplierCode);

		// 查询子账户已经具备的权限
		List<Map<String, Object>> authorityList = merchantUsers.getUserAuthorityList(uid);
		map.addAttribute("userAuthorityList", userAuthorityList);
		map.addAttribute("authorityList", authorityList);
		map.addAttribute("userId", uid);
		return "manage_unless/merchants/to_merchant_user_authority";
	}

	/**
	 * 给商家帐号分配权限
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/assign_Merchants_User_Authority")
	public String assign_Merchants_User_Authority(String userId, String authority, ModelMap model) throws Exception {
		boolean bool = merchantUsers.addUserAuthority(userId, authority);
		if (bool) {
			try{
				monitor.loadAuthResource(userId);
			}catch(Exception e){
				logger.error("更新用户Id为{}的权限redis缓存失败",userId,e);
			}
			model.addAttribute("message", "添加帐号权限成功!");
			model.addAttribute("refreshflag", "1");
			model.addAttribute("methodName", "/merchants/login/to_merchants.sc");
			model.addAttribute("tagTab", "setting-base");
			model.addAttribute("result", "success");
			return "manage_unless/merchants/merchants_message";
		} else {
			model.addAttribute("message", "添加帐号权限失败!");
			model.addAttribute("result", "fail");
			model.addAttribute("refreshflag", "1");
			model.addAttribute("methodName", "/merchants/login/to_merchants.sc");
			model.addAttribute("tagTab", "setting-base");
			return "manage_unless/merchants/merchants_message";
		}
	}

	/**
	 * 据商家id查询商家发货地址
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/to_merchant_consignmentAdress_list")
	public String to_merchant_consignmentAdress_list(ModelMap model, HttpServletRequest request, String flag) throws Exception {
		// 根据商家id查询商家的发货地址
		MerchantconsignmentAdress merchantconsignmentAdress = merchantUsers.getConsignmentAdressList(request);
		model.addAttribute("tagTab", "setting-base");
		// 判断是否已经存在信息，如果存在，跳转到查询页面
		if (null != merchantconsignmentAdress && !"1".equals(flag)) {
			model.addAttribute("consignmentAdress", merchantconsignmentAdress);
			return "manage/merchants/select_merchant_consignmentAdress";
		} else if (null != merchantconsignmentAdress) {// 跳转到修改页面
			String pst = merchantconsignmentAdress.getHometown();
			if (StringUtils.isNotBlank(pst)) {
				String[] area = pst.split("-");
				if (null != area && area.length > 0) {
					String province = area[0];
					model.addAttribute("province", province);
				}
			}
			model.addAttribute("consignmentAdress", merchantconsignmentAdress);
			// 获取省市区第一级结果集数据
			List<Map<String, Object>> areaList = merchantUsers.getAreaList();
			model.addAttribute("areaList", areaList);
			return "manage/merchants/update_merchant_consignmentAdress";
		} else {// 跳转到添加页面
				// 获取省市区第一级结果集数据
			List<Map<String, Object>> areaList = merchantUsers.getAreaList();
			model.addAttribute("areaList", areaList);
			model.addAttribute("flag", 2);
			return "manage/merchants/merchant_consignmentAdress_list";
		}

	}

	/**
	 * 查询商家退货地址
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/toMerchantRejectedAddressList")
	public String toMerchantRejectedAddressList(ModelMap model, HttpServletRequest request, String flag) throws Exception {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		String merchantName = YmcThreadLocalHolder.getMerchantName();
		RejectedAddressVo rejectedAddressVo = null;
		if ( StringUtils.isNotBlank(merchantCode) ) {
			rejectedAddressVo = iSupplierService.getMerchantRejectedAddress(merchantCode);
		}
		model.addAttribute("tagTab", "setting-base");
//		// 判断是否已经存在信息，如果存在，跳转到查询页面
		if (null != rejectedAddressVo) {
			model.addAttribute("rejectedAddress", rejectedAddressVo);
			String pst = rejectedAddressVo.getWarehouseArea();
			if (StringUtils.isNotBlank(pst)) {
				String[] area = pst.split("-");
				if (null != area && area.length > 0) {
					String province = area[0];
					model.addAttribute("province", province);
				}
			}
			model.put("edit_display", "none");
			model.put("unedit_display", "''");
		} else {
			rejectedAddressVo = new RejectedAddressVo();
			rejectedAddressVo.setSupplierCode(merchantCode);
			rejectedAddressVo.setSupplierName(merchantName);
			model.addAttribute("rejectedAddress", rejectedAddressVo);
			model.put("edit_display", "''");
			model.put("unedit_display", "none");
		}
		List<Map<String, Object>> areaList = merchantUsers.getAreaList();
		model.addAttribute("areaList", areaList);
		return "manage/merchants/merchant_rejected_address_list";
	}
	/**
	 * 据商家id查询商家发货地址
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/saveMerchantRejectedAddress")
	public String saveMerchantRejectedAddress(ModelMap model, HttpServletRequest request, MerchantRejectedAddressVo rejectedAddressVo) throws Exception{
		String loginName = YmcThreadLocalHolder.getOperater();
		if (StringUtils.isNotBlank(loginName) ){
				rejectedAddressVo.setCreaterPerson( loginName );
				rejectedAddressVo.setCreaterTime(DateUtil.getFormatByDate(new Date()));
		}
		iSupplierService.saveMerchantRejectedAddress(rejectedAddressVo);
		model.addAttribute("message", "操作成功!");
		model.addAttribute("refreshflag", "1");
		model.addAttribute("methodName", "/merchants/login/toMerchantRejectedAddressList.sc");
		model.addAttribute("tagTab", "setting-base");
		model.addAttribute("result", "success");
		return "manage/merchants/merchants_message";
	}
	/**
	 * 据商家id查询商家发货地址
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/update_merchant_consignmentAdress")
	public String update_merchant_consignmentAdress(HttpServletRequest request, ModelMap model, MerchantconsignmentAdress merchantconsignmentAdress) throws Exception {
		Integer count = merchantUsers.update_merchant_consignmentAdress(request, merchantconsignmentAdress);
		if (count > 0) {
			model.addAttribute("message", "操作成功!");
			model.addAttribute("refreshflag", "1");
			model.addAttribute("methodName", "/merchants/login/to_merchant_consignmentAdress_list.sc");
			model.addAttribute("tagTab", "setting-base");
			model.addAttribute("result", "success");
			//发送MQ
			WarehouseDomain warehouse=new WarehouseDomain();
	        warehouse.setContact(merchantconsignmentAdress.getConsignmentName());//发货人姓名
	        warehouse.setMobilePhone(merchantconsignmentAdress.getPhone());//发货人手机
	        warehouse.setTelPhone(merchantconsignmentAdress.getTell());//发货人电话
	        warehouse.setZipCode(merchantconsignmentAdress.getPostCode());//发货人邮编
	        warehouse.setWarehouseAddress(merchantconsignmentAdress.getHometown().replaceAll("-", "")+merchantconsignmentAdress.getAdress());//发货人地区（省市区）+发货人地址
	        warehouse.setSuppliersCode( YmcThreadLocalHolder.getMerchantCode() ); // 供应商编号 
	        amqpTemplate.convertAndSend("wms.merchant.warehouse", warehouse);
			
			return "manage/merchants/merchants_message";
//			return "redirect:/merchants/login/to_merchant_consignmentAdress_list.sc";
		} else {
			model.addAttribute("message", "操作失败!");
			model.addAttribute("refreshflag", "1");
			model.addAttribute("methodName", "/merchants/login/to_merchant_consignmentAdress_list.sc");
			model.addAttribute("tagTab", "setting-base");
			model.addAttribute("result", "fail");
			return "manage/merchants/merchants_message";
		}
	}

	/**
	 * 根据ID查询
	 * 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/queryChildById")
	public String queryChildById(HttpServletRequest request, @RequestParam("no") String no, @RequestParam("level") Integer level) throws Exception {
		String id = request.getParameter("id");
		String nameStr = "";
		IJsonTool jsonToole = new JsonTool();
		List<Map<String, Object>> areaMap = merchantUsers.getChildAreaByNo(no, level);
		if(id != null){
			// 根据商家id查询商家的发货地址
			Map<String, Object> consignmentAdressMap = merchantUsers.getConsignmentAdressById(id);
			if (null != consignmentAdressMap) {
				String str = consignmentAdressMap.get("area").toString();
				if (StringUtils.isNotBlank(str)) {
					String[] strSplit = str.split("-");
					if (null != strSplit && strSplit.length > 0) {
						if (level == 2) {
							nameStr = strSplit[1];
						} else if (level == 3) {
							nameStr = strSplit[2];
						}
					}
				}
			}
		}
		List<Area> areaList = new ArrayList<Area>();
		for (Map<String, Object> map : areaMap) {
			Area area = new Area();
			area.setNo(map.get("no").toString());
			area.setName(map.get("name").toString());
			area.setNameStr(nameStr);
			areaList.add(area);
		}
		StringBuffer jsonDate = jsonToole.convertObj2jason(areaList);
		return jsonDate.toString();
	}

	/**
	 * 根据ID查询
	 * 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/queryChildByLevelAndNo")
	public String queryChildByLevelAndNo(@RequestParam("no") String no, @RequestParam("level") Integer level) throws Exception {
		String nameStr = "";
		IJsonTool jsonToole = new JsonTool();
		List<Map<String, Object>> areaMap = merchantUsers.getChildAreaByNo(no, level);
		List<Area> areaList = new ArrayList<Area>();
		for (Map<String, Object> map : areaMap) {
			Area area = new Area();
			area.setNo(map.get("no").toString());
			area.setName(map.get("name").toString());
			area.setNameStr(nameStr);
			areaList.add(area);
		}

		StringBuffer jsonDate = jsonToole.convertObj2jason(areaList);
		return jsonDate.toString();
	}

	/**
	 * 修改联系人时候 判断手机是否存在
	 * 
	 * @author wang.m
	 * @throws Exception 
	 * @Date 2012-05-28
	 * 
	 */
	@ResponseBody
	@RequestMapping("/exitsNewPhone")
	public String exitsNewPhone(String oldPhone, String newPhone, HttpServletRequest request) throws Exception {
		boolean bool = merchantUsers.exitsNewPhone(oldPhone, newPhone, request);
		if (bool) {
			return "sucuess";
		} else {
			return "faise";
		}
	}
	
	@Value("#{commodityPreviewSettings['help.static.page.url']}")
	private String helpIndexUrl;;
	
	@RequestMapping("/to_help")
	public ModelAndView toHelpCenter(ModelMap map, HttpServletRequest request) {
		map.put("help_url", helpIndexUrl);
		return new ModelAndView("manage/merchants/help_index", map);
	}
	
	/**
	 * 找回密码(跳转)
	 * @param map
	 * @param request
	 * @return
	 */
	@RequestMapping("/findpassword")
	public ModelAndView findpassword(ModelMap map, HttpServletRequest request) {
		return new ModelAndView("manage_unless/passport/find-password", map);
	}
	
	@RequestMapping("/setpassword")
	public ModelAndView setpassword(ModelMap map, HttpServletRequest request) {
		String code=request.getParameter("code");
		map.put("code", code);
		Long sandTime = (Long) this.redisTemplate.opsForHash().get(CacheConstant.C_USER_RESETPASSWORD_ID_TIME, code);
		if(sandTime!=null){
			if((System.currentTimeMillis()-sandTime)>(1000*60*60)){
				map.put("isScuess", false);
				map.put("message", "重置密码链接超过期限，请重新设置!");
			}else{
				return new ModelAndView("manage_unless/passport/find-password-2", map);
			}
		}else{
			map.put("isScuess", false);
			map.put("message", "无效的密码重置请求，该链接可能已使用过，请重新操作!");	
		}
		return new ModelAndView("manage_unless/passport/find-password-3", map);
	}
	/**
	 * 校验登录名或者邮箱是否存在
	 * @param loginName
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/checkpassport")
	public String exitsLoginName(String loginName,String captcha,HttpServletRequest request) throws Exception {
		IJsonTool jsonToole = new JsonTool();
		MessageBean mes=new MessageBean();
        String imagevalidate = (String) request.getSession().getAttribute("login_validate_image");
		// 判断验证码是否正确
		if (!captcha.equalsIgnoreCase(imagevalidate)){
			mes.setSucess(false);
			mes.setMessage("验证码输入不正确,请检查!");
			return jsonToole.convertObj2jason(mes).toString();
		}
		//查询用户名
		Map<String, Object> user = merchantUsers.merchantLoginByUserName(loginName);
		if(user!=null){
			//查询邮箱
			if(user.get("email")==null||"".equals(user.get("email").toString())){
				mes.setSucess(false);
				mes.setMessage("该用户未绑定邮箱,请联系管理员重置!");
				return jsonToole.convertObj2jason(mes).toString();
			}
			
			if(user.get("emailstatus")==null||Integer.parseInt(user.get("emailstatus").toString())!=1){
				mes.setSucess(false);
				mes.setMessage("该用户绑定邮箱未激活,请激活邮箱!");
				return jsonToole.convertObj2jason(mes).toString();
			}
			//发送邮件
			String reseturl=systemConfig.getRestPwdEmailSrc()+"?code="+MapUtils.getString(user, "id");
			//邮件内容
			StringBuffer strB=new StringBuffer();
			strB.append("<img src='http://s1.ygimg.cn/template/common/images/logo-yg.png'>");
			strB.append("<div>--------------------------------------------------------------------------------------------------</div>");
			strB.append("<div><strong>亲爱的优购供应商： </strong></div>");
			strB.append("<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 您通过邮件<font color='#ff0000'><strong>找回密码</strong></font>，请妥善保管。请点击以下链接重置您的密码!若无法点击，请将链接复制到浏览器打开。</div>");
			strB.append("<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 提示：如果您不需要修改密码，或者您从未申请密码重置，请忽略本邮件!该链接只能在收到邮件后访问1次，超过1个小时或者访问超过1次，链接将自动失效。</div>");
			strB.append("<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href='"+reseturl+"'>"+reseturl+"</a></div>");
			merchantUsers.sandMail(MapUtils.getString(user, "email"),"优购-商家中心-重置密码",strB.toString(),loginName);
			this.redisTemplate.opsForHash().put(CacheConstant.C_USER_RESETPASSWORD_ID_TIME, MapUtils.getString(user, "id"), System.currentTimeMillis());
			mes.setSucess(true);
			mes.setMessage("重置密码链接已发送到邮箱："+user.get("email").toString()+",请查收!");
			logger.warn("重置密码链接已发送到邮箱{}",user.get("email"));
			return jsonToole.convertObj2jason(mes).toString();
		}else{
			mes.setSucess(false);
			mes.setMessage("该用户在系统不存在,请检查!");
			return jsonToole.convertObj2jason(mes).toString();
		}
	}
	
	@ResponseBody
	@RequestMapping("/savemail")
	public String saveEmail(String email,String id,String status,HttpServletRequest request) {
		//保存邮箱
		try {
			if(status!=null&&"0".equals(status)){
				merchantUsers.saveEmail(email, id);
			}else{
				merchantUsers.updateEmail(id,email,0);
			}
			//发送邮件
			String activaturl=systemConfig.getEmailActivateSrc()+"?code="+id;
			//邮件内容
			StringBuffer strB=new StringBuffer();
			strB.append("<img src='http://s1.ygimg.cn/template/common/images/logo-yg.png'>");
			strB.append("<div>--------------------------------------------------------------------------------------------------</div>");
			strB.append("<div><strong>亲爱的优购供应商： </strong></div>");
			strB.append("<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 您设置的邮件地址已经与你的账号绑定，请妥善保管。请点击以下链接激活邮箱绑定!若无法点击，请将链接复制到浏览器打开。</div>");
			strB.append("<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 提示：该链接只能在收到邮件后访问1次，超过2个小时或者访问超过1次，链接将自动失效。</div>");
			strB.append("<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href='"+activaturl+"'>"+activaturl+"</a></div>");
			merchantUsers.sendMail(email,"优购-商家中心-激活账户邮箱",strB.toString(),SubjectIdType.SUBJECT_ID_MALL_USERCENTER_EMAIL_BIND,
					com.yougou.component.email.model.ModelType.MODEL_TYPE_MALL_USERCENTER_EMAIL_BIND,YmcThreadLocalHolder.getOperater());
			this.redisTemplate.opsForHash().put(CacheConstant.C_USER_ACTIVATEPASSWORD_ID_TIME, id, System.currentTimeMillis());
			return "1";
		} catch (Exception e) {
			logger.error("激活邮件保存产生异常:",e);
			return "0";
		}
	}
	
	@RequestMapping("/activatemail")
	public ModelAndView activateEmail(String code,ModelMap map,HttpServletRequest request) throws Exception {
		Long sandTime = (Long) this.redisTemplate.opsForHash().get(CacheConstant.C_USER_ACTIVATEPASSWORD_ID_TIME, code);
		if(sandTime!=null){
			if((System.currentTimeMillis()-sandTime)>(1000*60*60*2)){
				map.put("isScuess", false);
				map.put("message", "激活邮箱链接超过期限，请重新设置!");
			}else{
				Map<String, Object> user = merchantUsers.merchantById(code);
				String email=MapUtils.getString(user, "email");
				merchantUsers.updateEmail(code,email,1);
				map.put("isScuess", true);
				map.put("message", "成功激活邮箱,可以通过激活邮箱找回遗忘密码!");
				this.redisTemplate.opsForHash().delete(CacheConstant.C_USER_ACTIVATEPASSWORD_ID_TIME, code);
			}
		}else{
			map.put("isScuess", false);
			map.put("message", "无效的激活邮箱链接请求，该链接可能已使用过!");	
		}
		return new ModelAndView("manage_unless/passport/activatemail", map);
	}
	
	@RequestMapping("/resetpassword")
	public ModelAndView reSetPassword(String userPwd, String code,ModelMap map,HttpServletRequest request) throws Exception {
		Long sandTime = (Long) this.redisTemplate.opsForHash().get(CacheConstant.C_USER_RESETPASSWORD_ID_TIME, code);
		if(sandTime!=null){
			if((System.currentTimeMillis()-sandTime)>(1000*60*60)){
				map.put("isScuess", false);
				map.put("message", "重置密码链接超过期限，请重新设置!");
			}else{
				if(merchantUsers.update_passwordNoLogin(code,userPwd)){
					map.put("isScuess", true);
					map.put("message", "密码修改成功!");
					this.redisTemplate.opsForHash().delete(CacheConstant.C_USER_RESETPASSWORD_ID_TIME, code);
				}else{
					map.put("isScuess", false);
					map.put("message", "重置密码失败,请返回登录页重新操作或者联系管理员!");
				}
			}
		}else{
			map.put("isScuess", false);
			map.put("message", "无效的密码重置请求，该链接可能已使用过，请重新操作!");	
		}
		return new ModelAndView("manage_unless/passport/find-password-3", map);
	}
	
	/**
	 * 根据登录账号查询登录日志
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/queryLoginLog")
	public String queryLoginLog(ModelMap model, HttpServletRequest request, Query query) throws Exception{
		String loginName = YmcThreadLocalHolder.getOperater();
		Map<String, Object> mapMerchantUser = (Map<String, Object>) request.getSession().getAttribute("merchantUsers");
		if( StringUtils.isNotBlank(loginName) ) {
				List<String> loginNames = new ArrayList<String>();
				Map params = new HashMap();
				query.setPageSize(10);
				params.put("query", query);
				//主账号可以查看所有子账号的登录日志，优购管理员跟主账号一样信息
				if("1".equals(MapUtils.getString(mapMerchantUser, "is_administrator","0")) || 
					!("0".equals(MapUtils.getString(mapMerchantUser, "is_yougou_admin","0")))){
					//查询该主账号下的所有子账号以及当前登录账号
					loginNames = merchantUsers.queryAccountByMerchantCode(MapUtils.getString(mapMerchantUser, "supplier_code"));
				}else{
					loginNames.add(loginName);
				}
				params.put("loginNames", loginNames);
				PageFinder<MerchantCenterOperationLog> pageFinder = operationLogService.selectByLoginName(params);
				model.put("pageFinder", pageFinder);
		}
		return "manage/merchants/login_log_list";
	}
	
	/**
	 * appkeyList:商家获取APPKEY
	 * @author li.n1 
	 * @return 
	 * @since JDK 1.6
	 */
	@RequestMapping(value="/appkey")
	public String appkey(ModelMap map){
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		Map<String,Object> appMap = apiKeyService.findAppKeyIsExist(merchantCode);
		if(((Number)appMap.get("app_count")).intValue()<=0){
			//1、不存在自动生成appkey/secret，默认开启，只生成，不插入数据库
			ApiKey apiKey = this.generateApiKey(merchantCode);
			//1保存Appkey 2、绑定商家 3、授权api
			boolean flag = true;
			try {
				flag = apiKeyService.generateApiKeyAndBandingAndAuthorize(merchantCode,apiKey);
			} catch (Exception e) {
				logger.error("商家中心绑定商家：{}，appkey：{}，发生异常",
						new Object[]{merchantCode,
						ToStringBuilder.reflectionToString(apiKey, ToStringStyle.SHORT_PREFIX_STYLE),
						e});
				flag = false;
			}
			if(flag){
				//刷新API授权缓存
				putApiToRedis(apiKey.getAppKey());
				//刷新appkey验证缓存
				String hashKey = apiKey.getAppKey();
				//加入redis缓存
				putAppkeyToRedis(hashKey, apiKey.getAppSecret(), merchantCode);
				//生成appkey/绑定、授权都是成功的！
				map.put("appkey", hashKey);
				map.put("secret", apiKey.getAppSecret());
				map.put("status", apiKey.getStatus());
				map.put("apiId", apiKey.getId());
			}else{
				map.put("result", 500);
			}
		}else{
			//存在直接显示
			map.put("appkey", appMap.get("app_key"));
			map.put("secret", appMap.get("app_secret"));
			map.put("status", appMap.get("app_status"));
			map.put("apiId", appMap.get("api_id"));
		}
		return "manage/merchants/appkey";
	}

	private ApiKey generateApiKey(String merchantCode) {
		String loginName = YmcThreadLocalHolder.getOperater();
		// 生成密匙
		String appKey = new java.rmi.server.UID().toString().replaceAll("(:|-)", "_");
		// 生成密匙口令
		List<String> appSecretSections = Arrays.asList(appKey.split(""));
		Collections.shuffle(appSecretSections);
		String appSecret = Md5Encrypt.md5(appSecretSections.toString());
		ApiKey apiKey = new ApiKey();
		apiKey.setId(UUIDGenerator.getUUID());
		apiKey.setAppKey(appKey);
		apiKey.setAppSecret(appSecret);
		/**
		 * 1 开启  0未开启
		 */
		apiKey.setStatus(1);
		apiKey.setUpdateTime(DateUtil.getFormatByDate(new Date()));
		apiKey.setUpdateUser(loginName+"("+merchantCode+")");
		
		return apiKey;
	}
	
	@RequestMapping(value="/changeApiStatus",method=RequestMethod.POST)
	@ResponseBody
	public String changeApiStatus(String apiId, String appkey, 
			String appSecret, Integer status){
		JSONObject object = new JSONObject();
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		if(!(setOperations.isMember(CacheConstant.APPKEY_YOUGOU_STATUS, appkey))){
			//被系统自动关闭，不允许开启
			boolean b = apiKeyService.changeApiStatus(apiId,status);
			if(b){
				//刷新appkey验证缓存
				if(status==1){
					//加入缓存
					putAppkeyToRedis(appkey, appSecret,merchantCode);
				}else{
					//删除缓存
					removeAppkeyToRedis(appkey);
				}
				object.put("code", 200);
				object.put("msg", "修改状态成功！");
			}else{
				object.put("code", 500);
				object.put("msg", "修改状态失败！");
			}
		}else{
			object.put("code", 500);
			object.put("msg", "您的Appkey已被优购锁定，请联系系统支持人员开启！请参考以下原因：<br><ul><li>1、appkey流量超标自动关闭</li><li>2、优购招商运营关闭</li></ul>");
		}
		return object.toString();
	}
	
	private void putApiToRedis(String appkey){
		for(String apiId : UserConstant.APIIDS){
			String value = StringUtils.join(new Object[]{apiId,appkey}, "#");
			setOperations.add(CacheConstant.API_APPKEY_SET_KEY,value);
		}
	}
	
	private void putAppkeyToRedis(String appkey, String appSecret,String merchantCode){
		removeAppkeyToRedis(appkey);
		//刷新appkey验证缓存
		String value = StringUtils.join(new Object[]{appSecret,merchantCode}, "#");
		hashOperations.put(CacheConstant.APPKEY_SECRET_KEY, appkey, value);
	}
	
	private void removeAppkeyToRedis(String appkey){
		//删除appkey验证缓存
		hashOperations.delete(CacheConstant.APPKEY_SECRET_KEY, appkey);
	}
	
	

	@Value("#{configProperties['contract.ftp.server']}")
	private String contractFtpServer;
	
	@Value("#{configProperties['contract.ftp.port']}")
	private String contractFtpPort;

	@Value("#{configProperties['contract.ftp.username']}")
	private String contractFtpUsername;

	@Value("#{configProperties['contract.ftp.password']}")
	private String contractFtpPassword;

	@Value("#{configProperties['contract.ftp.path']}")
	private String contractFtpPath;
	  /**
     * 合同附件下载
     * @param response
     * @param request
     * @param fileName
     * @param realName
     * @param outputStream
     * @modify 2015-05-06 li_j1
     */
    @RequestMapping("/downLoadContractAttachment")
	public void downLoadContractAttachment(HttpServletResponse response,HttpServletRequest request, String fileName,String realName,OutputStream outputStream) {
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			FTPClientConfig comfig = new FTPClientConfig(
					FTPClientConfig.SYST_NT);
			comfig.setServerLanguageCode("zh");
			FileFtpUtil ftp = new FileFtpUtil(contractFtpServer, 
					Integer.valueOf(contractFtpPort),
					contractFtpUsername,contractFtpPassword);
			ftp.login();

			// 这个就就是弹出下载对话框的关键代码
			response.setContentType("application/x-msdownload; charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"),"iso8859-1"));
			ftp.downRemoteFile(contractFtpPath + "/" + realName, os);
			os.flush();
			ftp.logout();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
    @Resource
    private SupplierContractMapper supplierContractMapper ;
    /**
     * 验证是否超过60天
     * @param request
     * @param code
     * @return
     */
    @ResponseBody
	@RequestMapping("/checkOverDays")
	public String checkOverDays(HttpServletRequest request) {
    	
    	JSONObject result = new JSONObject();
      String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		
		// 根据id查询商家基本信息
		SupplierVo vo;
		 
		try {
			vo = supplierService.getSupplierByMerchantCode(merchantCode);
			// 商家扩展表信息
			//modelMap.addAttribute("supplierExpand", supplierExpand);		
			MerchantSupplierExpand supplierExpand = merchantSupplierExpandMapper.selectBySupplierId(vo.getId());
			
			 
			//需要提示的标志
			boolean needNotes = false;
			//过期标志
			boolean isOverDay = false;
			if(supplierExpand.getContractRemainingDays() == null|| supplierExpand.getBusinessRemainingDays()==null
		||supplierExpand.getInstitutionalRemainingDays()== null ||supplierExpand.getMarkRemainingDays() == null){
				//如果为空 ，跳出
				result.put("needNotes",needNotes );
				result.put("isOverDay", isOverDay);
				
				return result.toString();
			}
			//优先判断合同时间--------- 
			//如果 不需要续签 不提示合同isNeedRenew
			SupplierContract contract = this.supplierContractMapper.getCurrentContractBySupplierId(vo.getId());
			
			if(StringUtils.isEmpty(contract.getIsNeedRenew())||!"0".equals(contract.getIsNeedRenew())){
				
				//合同在0-60天
				if(supplierExpand.getContractRemainingDays()>0 && supplierExpand.getContractRemainingDays()<60){
					needNotes = true;
				}else if(supplierExpand.getContractRemainingDays()<=0 ){
					needNotes = true;
					isOverDay = true;
				}
			}
			//营业执照
			if(supplierExpand.getBusinessRemainingDays()>0 &&supplierExpand.getBusinessRemainingDays()<60){
				needNotes = true;
			}else if(supplierExpand.getBusinessRemainingDays()<=0){
				needNotes = true;
			}
			//institutional_remaining_days组织机构
			if(supplierExpand.getInstitutionalRemainingDays()>0 && supplierExpand.getInstitutionalRemainingDays()<60){
				needNotes = true;
			}else if(supplierExpand.getInstitutionalRemainingDays()<=0){
				needNotes = true;
			 
			}
			//商标到期mark_remaining_days
			if(supplierExpand.getMarkRemainingDays()>0 && supplierExpand.getMarkRemainingDays()<60){
				needNotes = true;
			}else if(supplierExpand.getMarkRemainingDays()<=0){
				needNotes = true;
				 
			}
			//result.put("noteSum",noteSum );
			result.put("needNotes",needNotes );
			result.put("isOverDay", isOverDay);
			//SupplierContract supplierContract = merchantInfo.getSupplierContractBySupplierId(vo.getId());
		} catch (Exception e) {
			logger.error("验证商家是否过期超过60天，系统发生错误！",e);
		}//merchantServiceNew.getSupplierVoById(id);
		//modelMap.addAttribute("supplier", vo);

		//modelMap.put("supplierContract", supplierContract);		
    	
		return result.toString();
	}
    
    
    @RequestMapping("toShowNote")
   	public ModelAndView toShowNote(HttpServletRequest request,ModelMap modelMap) throws Exception {
    	 String merchantCode = YmcThreadLocalHolder.getMerchantCode(); 
		  
    	 SupplierVo  vo = supplierService.getSupplierByMerchantCode(merchantCode);
		 MerchantSupplierExpand supplierExpand = merchantSupplierExpandMapper.selectBySupplierId(vo.getId());
		 SupplierContract supplierContract = merchantInfo.getSupplierContractBySupplierId(vo.getId());
			//modelMap.put("supplierContract", supplierContract);
		 
		//需要提示的标志
			boolean needNotes = false;
			//过期标志
			boolean isOverDay = false;
			int noteSum = 0;
			//优先判断合同时间---------
			//合同在0-90天
			//如果 不需要续签 不提示合同isNeedRenew
			SupplierContract contract = this.supplierContractMapper.getCurrentContractBySupplierId(vo.getId());
			
			if(supplierExpand.getContractRemainingDays()>0 && supplierExpand.getContractRemainingDays()<60
					&& "1".equals(contract.getIsNeedRenew())){
				needNotes = true;
				noteSum++;
				modelMap.put("contract_in_day", supplierExpand.getContractRemainingDays());
			}else if(supplierExpand.getContractRemainingDays()<=0 ){
				isOverDay = true;
				noteSum++;
				modelMap.put("contract_out_day", supplierExpand.getContractRemainingDays());
				modelMap.put("contract_fail_day", supplierContract.getFailureDate());
			}
			//营业执照
			if(supplierExpand.getBusinessRemainingDays()>0 &&supplierExpand.getBusinessRemainingDays()<90){
				needNotes = true;
				noteSum++;
				modelMap.put("bussiness_in_day", supplierExpand.getBusinessRemainingDays());
			}else if(supplierExpand.getBusinessRemainingDays()<=0){
				needNotes = true;
				noteSum++;
				modelMap.put("bussiness_out_day", supplierExpand.getBusinessRemainingDays());
				modelMap.put("bussiness_fail_day", supplierExpand.getBusinessValidityEnd());
			}
			//institutional_remaining_days组织机构
			if(supplierExpand.getInstitutionalRemainingDays()>0 && supplierExpand.getInstitutionalRemainingDays()<90){
				needNotes = true;
				noteSum++;
				modelMap.put("institution_in_day", supplierExpand.getInstitutionalRemainingDays());
			}else if(supplierExpand.getInstitutionalRemainingDays()<=0){
				needNotes = true;
				noteSum++;
				modelMap.put("institution_out_day", supplierExpand.getInstitutionalRemainingDays());
				modelMap.put("institution_fail_day", supplierExpand.getInstitutionalValidityEnd());
			}
			//商标到期mark_remaining_days
			if(supplierExpand.getMarkRemainingDays()>0 && supplierExpand.getMarkRemainingDays()<90){
				needNotes = true;
				noteSum++;
				modelMap.put("mark_in_day", (supplierExpand.getMarkRemainingDays()));
			}else if(supplierExpand.getMarkRemainingDays()<=0){
				needNotes = true;
				noteSum++;
				modelMap.put("mark_out_day", (-supplierExpand.getMarkRemainingDays()));
				 
			}
			modelMap.put("needNotes",needNotes );
			modelMap.put("isOverDay", isOverDay);
			modelMap.put("noteSum",noteSum );
    	return new ModelAndView("manage/passport/carousel", modelMap);
   	}
}