package com.belle.yitiansystem.merchant.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.yitiansystem.merchant.constant.MerchantConstant;
import com.belle.yitiansystem.merchant.dao.mapper.MerchantContractUpdateHistoryMapper;
import com.belle.yitiansystem.merchant.dao.mapper.MerchantMapper;
import com.belle.yitiansystem.merchant.dao.mapper.MerchantSupplierExpandMapper;
import com.belle.yitiansystem.merchant.dao.mapper.MerchantUserMapper;
import com.belle.yitiansystem.merchant.dao.mapper.MerchantUserRoleMapper;
import com.belle.yitiansystem.merchant.dao.mapper.SupplierContractMapper;
import com.belle.yitiansystem.merchant.dao.mapper.SupplierVoMapper;
import com.belle.yitiansystem.merchant.enums.MerchantStatusEnum;
import com.belle.yitiansystem.merchant.model.entity.SimpleContract;
import com.belle.yitiansystem.merchant.model.entity.SimpleSupplierVo;
import com.belle.yitiansystem.merchant.model.entity.SupplierVo;
import com.belle.yitiansystem.merchant.model.pojo.SupplierContract;
import com.belle.yitiansystem.merchant.service.IMerchantOperatorApi;
import com.belle.yitiansystem.merchant.service.IMerchantsService;
import com.belle.yitiansystem.merchant.util.MerchantUtil;
import com.yougou.api.model.pojo.ApiKeyMetadata;
import com.yougou.api.service.IApiKeyService;
import com.yougou.api.service.IApiService;
import com.yougou.component.email.api.IEmailApi;
import com.yougou.component.email.model.MailSenderInfo;
import com.yougou.component.email.model.SubjectIdType;
import com.yougou.fss.api.IFSSYmcApiService;
import com.yougou.fss.api.vo.FSSResult;
import com.yougou.fss.api.vo.ShopVO;
import com.yougou.kaidian.common.util.DateUtil2;
import com.yougou.merchant.api.common.Query;
import com.yougou.merchant.api.common.UUIDGenerator;
import com.yougou.merchant.api.supplier.vo.MerchantContractUpdateHistory;
import com.yougou.merchant.api.supplier.vo.MerchantContractUpdateHistory.ProcessingType;
import com.yougou.merchant.api.supplier.vo.MerchantContractUpdateHistoryQuery;
import com.yougou.merchant.api.supplier.vo.MerchantOperationLog;
import com.yougou.merchant.api.supplier.vo.MerchantOperationLog.OperationType;
import com.yougou.merchant.api.supplier.vo.MerchantSupplierExpand;
import com.yougou.merchant.api.supplier.vo.MerchantUser;
import com.yougou.merchant.api.supplier.vo.MerchantUserQuery;
import com.yougou.merchant.api.supplier.vo.MerchantUserRole;
import com.yougou.merchant.api.supplier.vo.MerchantUserRoleQuery;
import com.yougou.merchant.api.supplier.vo.YmcResult;
import com.yougou.ordercenter.common.DateUtil;
import com.yougou.purchase.api.IPurchaseApiService;
import com.yougou.purchase.model.Supplier;
import com.yougou.wms.wpi.inventory.service.IInventoryForMerchantService;
import com.yougou.wms.wpi.warehouse.domain.ResultMsg;
import com.yougou.wms.wpi.warehouse.domain.vo.WarehouseDomainForMerchant;

@Service("merchantOperatorApi")
public class MerchantOperatorApiImpl implements IMerchantOperatorApi {
	
	private Logger logger = Logger.getLogger(MerchantOperatorApiImpl.class);
	
	private static final String[] BASE_ROLE_NAMES= {"使用商家中心权限组"," 使用ERP系统对接权限组","已关闭部分权限的权限组"};
	//商家账号启用状态
	private static final int MERCHANT_USER_STATUS_USED =  1;
	// 商家账号停用状态
	private static final int MERCHANT_USER_STATUS_STOP =  0;
	
	private static final int IS_ADMINISTAROR = 1;
	
	private static final String IS_USED_ERP = "1";
	//wms type 是否停用 1：启用 0：停用
	private static final 	String WMS_STARTUP = "1";
	//wms type 是否停用 1：启用 0：停用
	private static final 	String WMS_STOP = "0";
	// WMS返回结果 成功标志
	private static final String WMS_RETURN_CODE_SUCCESS = "1";
	/**
	 * is_use_yougou_wms` int(1) NOT NULL DEFAULT '0' COMMENT '是否使用优购WMS 默认否0 是1'
	 */
	private static final int NO_WMS = 0;
	// 前台fss店铺关闭状态
	private static final String ACCESS_DENY = "N";
	/**
	 * 系统定时任务 （用于记录日志时候的operator）
	 */
	private static final String SYTEM_AUTO_RUNNING = "system_auto";
	/**
	 * mark_remaining_days` int(11) DEFAULT NULL COMMENT '授权资质剩余有效天数', 
	 * `contract_remaining_days` int(11) DEFAULT NULL COMMENT '合同剩余有效天数', 
	 * `business_remaining_days` int(11) DEFAULT NULL COMMENT '营业执照剩余有效天数', 
	 * `institutional_remaining_days` int(11) DEFAULT NULL COMMENT '组织机构代码证剩余有效天数'

	 */
	public static final int OVER_TIME = 0;
	
	@Resource
	private MerchantUserMapper merchantUserMapper;
	@Resource
	private MerchantUserRoleMapper merchantUserRoleMapper;
	@Resource
	private MerchantContractUpdateHistoryMapper merchantContractUpdateHistoryMapper;
	@Resource
	private IPurchaseApiService purchaseApiService;
	@Resource
	private SupplierVoMapper supplierVoMapper;
	@Resource
	com.yougou.wms.wpi.warehouse.service.IWarehouseCacheService warehouseCacheService;//WarehouseCacheServiceImpl.//saveWarehouseDomainForMerchant(WarehouseDomainForMerchant)
	@Resource
	private SupplierContractMapper  supplierContractMapper;
	@Resource
	private IFSSYmcApiService iFSSYmcApiService;
	
	@Resource
	private IApiKeyService apiKeyService;
	
	@Resource
	private IApiService apiService;
	
	@Resource
	private IMerchantsService merchantsService;
	
	@Resource
	private MerchantSupplierExpandMapper merchantSupplierExpandMapper;
	
	@Resource
	private MerchantMapper merchantMapper;
	
	
	@Override
	//TODO 合同到期的商家  ---------不能启用
	public YmcResult startUpAccout(String merchantCode,String operator,String[] baseRoleIdArray,HttpServletRequest request) {
		//绑定账号getContractIdBySupplierId
		YmcResult  result = new YmcResult();
		if(StringUtils.isEmpty(merchantCode)){
			result = new YmcResult(YmcResult.ERROR_CODE, "merchantCode 商家编码为空");
			return result;
		} 
		try {
			//查询商家
//			SupplierVo supplier = this.supplierVoMapper.getSupplierByMerchantCode(merchantCode);
			Supplier  supplier = 	purchaseApiService.getSupplierBySupplierCode(merchantCode);
			if(supplier == null){
				result = new YmcResult(YmcResult.ERROR_CODE, "商家不存在");
				return result;
			}
			//记录当前状态  
			int oldStatus = supplier.getIsValid();
			
			//查询商家主账户
			MerchantUserQuery userQuery= new MerchantUserQuery();
			com.yougou.merchant.api.supplier.vo.MerchantUserQuery.Criteria userCri = userQuery.createCriteria();
			userCri.andMerchantCodeEqualTo(merchantCode);
			userCri.andIsAdministratorEqualTo(IS_ADMINISTAROR);
			List<MerchantUser> userList = this.merchantUserMapper.selectByQuery(userQuery);
			if(CollectionUtils.isEmpty(userList)){
				result = new YmcResult(YmcResult.ERROR_CODE, "商家尚未新建主账户，请新建商家登录主账号");
				return result;
			}
			//主账号只有一个，取第一个 
			MerchantUser user = userList.get(0);
		
			SupplierContract contract = supplierContractMapper.getCurrentContractBySupplierId(supplier.getId());
			if(null != contract){
				String fail = contract.getFailureDate();
				if(StringUtils.isNotEmpty(fail)){
					Date failDate = DateUtil.getdate(fail);
					long failDates = DateUtil.getMillis(failDate);
					long date = System.currentTimeMillis()-failDates;
					if(date >0){
						result = new YmcResult(YmcResult.ERROR_CODE, "该商家合同到期，不能启用");
						return result;
					}
				}
				
				//合同变为生效
				contract.setStatus(MerchantConstant.CONTRACT_STATUS_EFFECTIVE);
				supplierContractMapper.updateSupplierContract(contract);
				
				//如果是ERP ，启用app key ，不管权限有没有分配，都要操作
				if(IS_USED_ERP.equals(contract.getIsUseERP())){
					try {
						//分配app key 
						apiKeyService.initAppKey(merchantCode, user.getLoginName());
						//刷新缓存
						apiKeyService.freshRedisCache(merchantCode,"1");
						apiService.refreshApiCache();
						//apiKeyService.generateApiKey(request,user.getLoginName(),user.getMerchantCode());
					} catch (Exception e) {
						logger.error("分配APP KEY 失敗，原因："+e);
					}
				}
			}
			
			/** 处理商家所有用户的权限 **/
			initMerchantAuthority(result,merchantCode,baseRoleIdArray,contract);
			
		
			supplier.setUpdateDate(new Date());
			supplier.setIsValid(MerchantConstant.MERCHANT_STATUS_ENABLE);
			
			//更改商家表子系统状态  所有账号启用
			userQuery= new MerchantUserQuery();
			userCri = userQuery.createCriteria();
			userCri.andMerchantCodeEqualTo(merchantCode);
			
			MerchantUser userRecord = new MerchantUser();
			userRecord.setStatus(MERCHANT_USER_STATUS_USED);
			this.merchantUserMapper.updateByQuerySelective(userRecord, userQuery);
			 
			//调用WMS接口自动分配
			
			//如果是入优购仓 
			/****************
			 * mdofiy by lsm 2015/8/3 
			 * 如果商家的仓库编码不为空，则走启用接口
			 */
			
			if(supplier.getIsUseYougouWms() == NO_WMS){
				String warehouseName = MerchantUtil.generateWarehouseName( supplier.getSupplier() );
				//如果商家的仓库编码不为空，则走 启用接口
				if(StringUtils.isNotEmpty(supplier.getInventoryCode())){
					ResultMsg msg = warehouseCacheService.updateWarehouseDomainForMerchant(supplier.getInventoryCode(), WMS_STARTUP);
					//如果返回失败，则调用新增接口
					if(!WMS_RETURN_CODE_SUCCESS.equals(msg.getResult())){
						WarehouseDomainForMerchant wms = new WarehouseDomainForMerchant();
						 
						wms.setWarehouseName( warehouseName );
						wms.setSuppliersCode(merchantCode);
						wms.setSuppliersName(supplier.getSupplier());
						
						msg = warehouseCacheService.saveWarehouseDomainForMerchant(wms);
						if(WMS_RETURN_CODE_SUCCESS.equals(msg.getResult())){
							supplier.setInventoryCode(msg.getData().toString());
						}else{
							logger.error("wms 新增仓库失败："+msg.getDescription());
						}
						logger.info("wms 新增仓库接口返回："+msg);
					}
				}else{
					
					WarehouseDomainForMerchant wms = new WarehouseDomainForMerchant();
					wms.setWarehouseName(warehouseName);
					wms.setSuppliersCode(merchantCode);
					wms.setSuppliersName(supplier.getSupplier());
					
					ResultMsg msg = warehouseCacheService.saveWarehouseDomainForMerchant(wms);
					if(WMS_RETURN_CODE_SUCCESS.equals(msg.getResult())){
						supplier.setInventoryCode(msg.getData().toString());
					}else{
						logger.error("wms 新增仓库失败："+msg.getDescription());
					}
					logger.info("wms 新增仓库接口返回："+msg);
				}
				
			}
			// 根据id有值的字段就更新，空的字段忽略
			// FIXME 这里没必要更新的字段也被更新了
			purchaseApiService.updateSupplier(supplier);
			
			MerchantOperationLog operationLog = new MerchantOperationLog();
			operationLog.setMerchantCode(merchantCode);
			operationLog.setOperator(operator);
			operationLog.setId(UUIDGenerator.getUUID());
			operationLog.setOperated(new Date());
			operationLog.setOperationType(OperationType.ACCOUNT);
			operationLog.setOperationNotes("更新商家权限组：商家("+merchantCode+") 初始化基本权限");
//					+ (StringUtils.isEmpty(showName)?"": "权限组名称:"+showName )
			supplierVoMapper.insertMerchantLog(operationLog);
			
			//记录日志-供应商状态流转日志 
        	MerchantContractUpdateHistory updateHistory = new MerchantContractUpdateHistory();
        	updateHistory.setContractNo(null != contract ? contract.getContractNo() : "");
        	updateHistory.setSupplierId(supplier.getId());
        	updateHistory.setOperator(operator);
        	updateHistory.setProcessing(ProcessingType.USER_STARTUP.getDescription());
        	updateHistory.setRemark("启用商家");
        	updateHistory.setType(MerchantConstant.LOG_FOR_MERCHANT);
        	updateHistory.setUpdateAfter(MerchantStatusEnum.getValue(supplier.getIsValid()));
        	updateHistory.setUpdateBefore(MerchantStatusEnum.getValue(oldStatus));
        	updateHistory.setUpdateField("isValid");
        	YmcResult ymcResult =  addContractLog(updateHistory);
        	if(YmcResult.OK_CODE.equals(ymcResult.getCode())){
        		logger.info(" 新增商家流转日志成功："+ updateHistory);
        	}else{
        		logger.error(" 新增商家流转日志失败："+ updateHistory);
        	}

        	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		
		return result;
	}
	
	private boolean initMerchantAuthority(YmcResult result,String merchantCode,String [] baseRoleIdArray,SupplierContract contract){
		MerchantUserQuery example = new MerchantUserQuery();
		com.yougou.merchant.api.supplier.vo.MerchantUserQuery.Criteria exampleCri = example.createCriteria();
		exampleCri.andMerchantCodeEqualTo( merchantCode );
		List<MerchantUser> userList = merchantUserMapper.selectByQuery(example);// 所有用户
		
		if( null!=userList && 0<userList.size() ){
			for(MerchantUser user:userList ){
				// step1:恢复旧的权限组
				String userId = user.getId();
				MerchantUserRoleQuery roleQuery = new MerchantUserRoleQuery();
				com.yougou.merchant.api.supplier.vo.MerchantUserRoleQuery.Criteria roleCri = roleQuery.createCriteria();
				roleCri.andUserIdLike( userId+"_bak" );
				MerchantUserRole ur = new MerchantUserRole();
				ur.setUserId(userId);
				int rowCount = merchantUserRoleMapper.updateByQuerySelective(ur, roleQuery);
				merchantUserRoleMapper.updateUserAuthorityFromBak(userId+"_bak");// 子账号的恢复权限主要在另一张表
				//step2:主账号 删除‘已关闭部分权限组’权限.
				Integer  isAdmin = user.getIsAdministrator();
				if( isAdmin == MerchantConstant.YES ){//主账号
					rowCount = merchantUserRoleMapper.deleteUserRole(user.getId(),baseRoleIdArray[2]);// 
				}
				
				// step3:若无任何权限且是主账号，则插入基本权限
				//     判断权限是否为空
				if( isAdmin == MerchantConstant.YES ){//主账号
					roleQuery = new MerchantUserRoleQuery();
					roleCri = roleQuery.createCriteria();
					roleCri.andUserIdLike( userId );
					List<MerchantUserRole> roleList = merchantUserRoleMapper.selectByQuery(roleQuery);
					if( null==roleList|| 0==roleList.size() ){
						// 初始化2个权限组当中的一个：
						String showName;
						MerchantUserRole userRole = new MerchantUserRole();
						if(IS_USED_ERP.equals(contract.getIsUseERP())){
							userRole.setRoleId(baseRoleIdArray[1]);
							showName = BASE_ROLE_NAMES[1];
						}else{
							userRole.setRoleId(baseRoleIdArray[0]);
							showName = BASE_ROLE_NAMES[0];
						}
						userRole.setUserId(user.getId());
						userRole.setId(UUIDGenerator.getUUID());
						userRole.setCreateDate(new Date());
						userRole.setRemark(showName);
						merchantUserRoleMapper.insert(userRole);
					}
				}
					
				//保存成功之后再更新缓存
				try {
					merchantsService.loadAuthResource(user.getId());
				} catch (Exception e) {
					logger.error("合同到期超过60天的job:更新商家("+merchantCode+")用户("+user.getLoginName()+")的权限缓存发生异常！"+e);
				}
				
			}
		}else{
			logger.error("合同到期超过60天的job:overDay60CloseOperation，商家切换权限时未找到该商家("+merchantCode+")的登陆用户。");
			return false;
		}
		return true;
	}

	@Resource
	private IInventoryForMerchantService inventoryForMerchantService;
	 
	@Override
	public YmcResult stopAccout(String merchantCode,String operator) {
		YmcResult  result = new YmcResult();
		/**
		 * 闭所有的账号就行
		 */
		MerchantUserQuery userQuery= new MerchantUserQuery();
		com.yougou.merchant.api.supplier.vo.MerchantUserQuery.Criteria userCri = userQuery.createCriteria();
		
		userCri.andMerchantCodeEqualTo(merchantCode);
		
		MerchantUser userRecord = new MerchantUser();
		userRecord.setStatus(MERCHANT_USER_STATUS_STOP);
		this.merchantUserMapper.updateByQuerySelective(userRecord, userQuery);
		
		// 更改 状态
		SupplierVo vo = this.supplierVoMapper.getSupplierByMerchantCode(merchantCode);
		int oldStatus = vo.getIsValid();
		vo.setIsValid(MerchantConstant.MERCHANT_STATUS_DISABLE);
		vo.setUpdateDate(new Date());
		
		//supplierVoMapper.updateByPrimaryKey(vo);
		Supplier  sp = 	purchaseApiService.getSupplierBySupplierCode(merchantCode);
		sp.setUpdateDate(new Date());
		sp.setIsValid(MerchantConstant.MERCHANT_STATUS_DISABLE);
		purchaseApiService.updateSupplier(sp);
		
		//合同变为失效
		SupplierContract contract = supplierContractMapper.getCurrentContractBySupplierId(vo.getId());
		if(contract != null){ //兼容老商家
			if(contract.getContractRemainingDays() == null || contract.getContractRemainingDays() < 1){
				contract.setStatus(MerchantConstant.CONTRACT_STATUS_EXPIRED);
			}
			supplierContractMapper.updateSupplierContract(contract);
			//ERP 商家停用时候删除缓存
			
			if(IS_USED_ERP.equals(contract.getIsUseERP())){
				
				try {
					//刷新缓存
					apiKeyService.freshRedisCache(merchantCode,"0");
					apiService.refreshApiCache();
				} catch (Exception e) {
					logger.error("停用商家刷新缓存失败");
				}
			}
		}
		
		ResultMsg msg = null;
		//TODO 通知WMS 停用仓库
		if(StringUtils.isNotEmpty(vo.getInventoryCode())){
			
			msg = warehouseCacheService.updateWarehouseDomainForMerchant(vo.getInventoryCode(), WMS_STOP);
		}else{
			//先查下是否有仓库   再清理仓库
			Map<String,String> map = warehouseCacheService.getWarehouseByMerchantCode(merchantCode);
			if( MapUtils.isNotEmpty(map) ){
				msg = inventoryForMerchantService.deleteMerchantInvByWarehouseCode(merchantCode);
			}
		}
		MerchantOperationLog operationLog = new MerchantOperationLog();
		operationLog.setMerchantCode(merchantCode);
		if(SYTEM_AUTO_RUNNING.equals(operator)){
			operationLog.setOperator("系统定时任务");
			operationLog.setOperationNotes("合同过期超过90天，系统定时任务停用商家：商家("+merchantCode+")停用仓库 "+(msg==null?"":msg.getResult())
					);
		}else{
			operationLog.setOperationNotes("停用商家：商家("+merchantCode+")停用仓库 "+(msg==null?"":msg.getResult())
					);
			operationLog.setOperator(operator);
		}
		operationLog.setId(UUIDGenerator.getUUID());
		operationLog.setOperated(new Date());
		operationLog.setOperationType(OperationType.ACCOUNT);
		
		
		
		//记录日志-供应商状态流转日志 
    	MerchantContractUpdateHistory updateHistory = new MerchantContractUpdateHistory();
    	updateHistory.setContractNo(contract != null ? contract.getContractNo():"");
    	updateHistory.setSupplierId(vo.getId());
    	updateHistory.setOperator(operator);
    	updateHistory.setProcessing(ProcessingType.USER_STOP.getDescription());
    	if(SYTEM_AUTO_RUNNING.equals(operator)){
    		updateHistory.setRemark("定时任务停用商家");
    	}else{
    		updateHistory.setRemark("手动停用商家");
    		
    	}
    	updateHistory.setType(MerchantConstant.LOG_FOR_MERCHANT);
    	updateHistory.setUpdateAfter(MerchantStatusEnum.getValue(vo.getIsValid()));
    	updateHistory.setUpdateBefore(MerchantStatusEnum.getValue(oldStatus));
    	updateHistory.setUpdateField("isValid");
    	YmcResult ymcResult =  addContractLog(updateHistory);
    	if(YmcResult.OK_CODE.equals(ymcResult.getCode())){
    		logger.info(" 新增商家流转日志成功："+ updateHistory);
    	}else{
    		logger.error(" 新增商家流转日志失败："+ updateHistory);
    	}
    	
		//warehouseCacheService.updateWarehouseByMerchantCode(arg0)
		return result;

	}


	@Override
	public YmcResult addMerchantLog(MerchantOperationLog log) {
		if(StringUtils.isEmpty(log.getId())){
			log.setId(UUIDGenerator.getUUID());
		}
		YmcResult  result = new YmcResult();
		if(StringUtils.isEmpty(log.getMerchantCode())){
			result = new YmcResult(YmcResult.ERROR_CODE, "merchantCode 商家编码为空");
			return result;
		}
		supplierVoMapper.insertMerchantLog(log);
		return result;
	}


	@Override
	public YmcResult addContractLog(MerchantContractUpdateHistory log) {
		if(StringUtils.isEmpty(log.getId())){
			log.setId(UUIDGenerator.getUUID());
		}
		YmcResult  result = new YmcResult();
		if(MerchantConstant.LOG_FOR_MERCHANT.equals(log.getType())){
			if(StringUtils.isEmpty(log.getSupplierId())){
				result = new YmcResult(YmcResult.ERROR_CODE, "supplierId 商家ID为空");
				return result;
			}
		}else if(MerchantConstant.LOG_FOR_CONTRACT.equals(log.getType())){
			if(StringUtils.isEmpty(log.getContractNo())){
				result = new YmcResult(YmcResult.ERROR_CODE, "contractNo 合同编号为空");
				return result;
			}
		}else{
			result = new YmcResult(YmcResult.ERROR_CODE, "type 日志类型为空");
			return result;
		}
		merchantContractUpdateHistoryMapper.insert(log);
		return result ;
	}

	public YmcResult  checkOverDaysMerchant(){
		YmcResult result = new YmcResult();
		ResultMsg msg;
		List<SimpleSupplierVo>  list = merchantMapper.selectByOverDays();
		ApiKeyMetadata apiKeyMetadata = null;
		boolean refreshFlag = false;
		for(SimpleSupplierVo vo :list){
//			  if(StringUtils.isNotEmpty(vo.getInventoryCode())){
//				msg =  warehouseCacheService.updateWarehouseDomainForMerchant(vo.getInventoryCode(), WMS_STOP);
//			  }else{
				  
				  msg = inventoryForMerchantService.deleteMerchantInvByWarehouseCode(vo.getMerchantCode()); 
//			  }
				  
			  try {
				  apiKeyMetadata = apiKeyService.queryApiKeyByMetadataVal(vo.getMerchantCode());
				  if(null != apiKeyMetadata){					  
					  apiKeyService.backupApiLicence(apiKeyMetadata.getId());
					  refreshFlag = true;
				  }
			} catch (Exception e) {
				logger.error("备份处理API库存更新和查询授权出现异常"+e);
			}
			  logger.info("清理 到期的库存：merchantCode:"+vo.getMerchantCode()+" 返回结果："+msg.getResult());
		 }
		
		//刷新缓存
		try {
			if(refreshFlag){
				apiService.refreshApiCache();
			}
		} catch (Exception e) {
			logger.error("备份处理API库存更新和查询授权，刷新缓存失败");
		}
		
		////////////////////////
		
	   //合同到期，更新合同状态为失效    
		supplierContractMapper.updateOverDayContract();
		return result;
	}
	
	//private MerchantContractUpdateHistoryMapper merchantContractUpdateHistoryMapper;
	/**
	 * 
	 */
	public List<MerchantContractUpdateHistory> listHistory(String supplierId){
		List<MerchantContractUpdateHistory> list = null;
		MerchantContractUpdateHistoryQuery query = new MerchantContractUpdateHistoryQuery();
		com.yougou.merchant.api.supplier.vo.MerchantContractUpdateHistoryQuery.Criteria c = query.createCriteria();
		c.andSupplierIdEqualTo(supplierId);
		query.setOderProperty("operation_time");
		query.setOderType("desc");
		list = merchantContractUpdateHistoryMapper.selectByQuery(query);
		return list;
	}
	/**
	 * 
	 */
	public List<MerchantOperationLog>   listOperationLog(String supplierCode){
		List<MerchantOperationLog>  list = null;
		list = supplierVoMapper.queryMerchantOperLog(supplierCode);
		return list ; 
	}

	/**
	 * 根据合同编号查询合同状态流转日志
	 * @param contractNo
	 * @return
	 */
	@Override
	public List<MerchantContractUpdateHistory> listContractHistory(String contractNo) {
		MerchantContractUpdateHistoryQuery query = new MerchantContractUpdateHistoryQuery();
		com.yougou.merchant.api.supplier.vo.MerchantContractUpdateHistoryQuery.Criteria c = query.createCriteria();
		c.andContractNoEqualTo(contractNo);
		c.andTypeEqualTo(MerchantConstant.LOG_FOR_CONTRACT);
		query.setOderProperty("operation_time");
		query.setOderType("desc");
		return merchantContractUpdateHistoryMapper.selectByQuery(query);
	}

	/**
	 * 根据合同编号查询合同操作日志
	 * @param contractNo
	 * @return
	 */
	@Override
	public List<MerchantOperationLog> listContractOperationLog(String contractNo) {
		return supplierVoMapper.queryMerchantContractOperLog(contractNo);
	}
	/**
	 * 合同到期最後3个月最后15天發郵件通知商家
	 * 75天到90天
	 * 
	 */
	public YmcResult overDaySendMessage(){
		YmcResult result = new YmcResult();
		List<SimpleContract> list = supplierContractMapper.selectSimpleContract();
		String content= "";
		try {
			for(SimpleContract c :list){
				content = " 尊敬的用户，您的合同已经到期"+(-c.getContractRemainingDays())+
						"天了，将在"+(90+c.getContractRemainingDays())+"天后关闭账号。为了避免关闭账号导致登录问题，" +
								"请及时向优购运营提交续签申请，并完成续签，逾期未提交者视为主动放弃续签，您的店铺将自动关闭，请您及时完成续签，以免造成不必要的损失" ;
				sandMail(c.getEmail(), "合同到期提醒", content);
			}
		} catch (Exception e) {
			logger.error("邮件发送失败"+e); 
			e.printStackTrace();
		}
		return result;
	}
	
	@Resource
	private IEmailApi emailApi;
	/**
	 * 发送邮件
	 */
	public void sandMail(String addresss, String title, String content) throws Exception {
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setToAddress(addresss);
		mailInfo.setTitle(title);
		mailInfo.setContent(content);
		mailInfo.setSubject(SubjectIdType.SUBJECT_ID_MERCHANT_CONTRACT_EXPIRES_NOTIFY);
		mailInfo.setModelType(com.yougou.component.email.model.ModelType.MODEL_TYPE_MERCHANT_CONTRACT_EXPIRES_NOTIFY);
		emailApi.sendNow(mailInfo);
	}

	/**
	 * renew_flag` char(1) DEFAULT NULL COMMENT '续签标识，1当前合同有续签 2本合同是续签合同',
	 * 
	 *	  renew_flag 续签标识的枚举类型 1当前合同无续签 ,2当前合同 有续签3本合同是续签合同 4 历史失效合同' 
		public static final String CONTRACT_RENEW_FLAG_CURRENT = "1";
		public static final String CONTRACT_RENEW_FLAG_CURRENT_WITH_FUTURE = "2";
		public static final String CONTRACT_RENEW_FLAG_FUTURE = "3";
		public static final String CONTRACT_RENEW_FLAG_OLD = "4";
	 */
	public  YmcResult useNewContract(){
		YmcResult result = new YmcResult();
		//查询到  未被使用的，  当前合同到期而新合同未到期的
		List<SimpleContract> list = supplierContractMapper.getNotNowContract();
		
		// 更新当前的合同，将原来的合同的 falg 解绑，绑定当前合同 
		SupplierContract old_contract = new SupplierContract();
		SupplierContract new_contract = new SupplierContract();
		MerchantSupplierExpand expand = null;
		
		boolean refreshFlag = false;
		ApiKeyMetadata apiKeyMetadata = null;
		for(SimpleContract c : list){
			//更新
			new_contract  = supplierContractMapper.selectSupplierContractByContractNo(c.getNewContract());
			
			new_contract.setRenewFlag(MerchantConstant.CONTRACT_RENEW_FLAG_CURRENT);
			new_contract.setStatus(MerchantConstant.CONTRACT_STATUS_EFFECTIVE);
			supplierContractMapper.updateSupplierContract(new_contract);
			//取消旧合同的标志
			old_contract = supplierContractMapper.selectSupplierContractByContractNo(c.getOldContract());
			old_contract.setRenewFlag(MerchantConstant.CONTRACT_RENEW_FLAG_OLD);
			old_contract.setStatus(MerchantConstant.CONTRACT_STATUS_EXPIRED);
			supplierContractMapper.updateSupplierContract(old_contract);
			//更新当前使用合同

			expand = this.merchantSupplierExpandMapper.selectBySupplierId(new_contract.getSupplierId());
			
			expand.setContractNo(new_contract.getContractNo());
			merchantSupplierExpandMapper.updateByPrimaryKey(expand);
			try {
				 apiKeyMetadata = apiKeyService.queryApiKeyByMetadataVal(expand.getMerchantCode());
				 if(null != apiKeyMetadata){					  
					 apiKeyService.recoverApiLicence(apiKeyMetadata.getId());
					 refreshFlag = true;
				 }
			} catch (Exception e) {
				logger.error("恢复处理API库存更新和查询授权出现异常"+e);
			}
		}
		//刷新缓存
		try {
			if(refreshFlag){
				apiService.refreshApiCache();
			}
		} catch (Exception e) {
			logger.error("恢复处理API库存更新和查询授权，刷新缓存失败");
		}
		return result;
	}
	
	/**
	 * @author luo.q1
	 * 
	 */
	@Override
	public void overDay60CloseOperation(String[] baseRoleIdArray,Date targetDate) {
		// step1:选出合同到期已超过60天并且未续签的商家(持续一周，检查重新处理)
		Date compareDateFrom = DateUtil2.addDate(targetDate ,-66);
		Date compareDateTo = DateUtil2.addDate(targetDate ,-60);
		List<SimpleContract> list = supplierContractMapper.getContractOverDayNumList(compareDateFrom,compareDateTo);
		
		for(SimpleContract c : list){
			String merchantCode = c.getMerchantCode();
			// step2:切换权限组
			   // 查出所有商家的账户，删除权限组，对于管理员账号，赋予新权限组
			MerchantUserQuery example = new MerchantUserQuery();
			com.yougou.merchant.api.supplier.vo.MerchantUserQuery.Criteria exampleCri = example.createCriteria();
			exampleCri.andMerchantCodeEqualTo( merchantCode );
			List<MerchantUser> userList = merchantUserMapper.selectByQuery(example);
			if( null!=userList && 0<userList.size() ){
				for(MerchantUser user:userList ){
					// 假删除：用户的所有有效权限，‘已关闭部分权限组’除外.(假删除,是将user_id改为了*_bak).
					merchantUserRoleMapper.delValidUserRole(user,baseRoleIdArray[2]);// 
					Integer  isAdmin = user.getIsAdministrator();
					
					if( isAdmin == MerchantConstant.YES ){
						// 查是否已分派过 ‘已关闭部分权限组’
						MerchantUserRoleQuery roleQuery = new MerchantUserRoleQuery();
						com.yougou.merchant.api.supplier.vo.MerchantUserRoleQuery.Criteria roleCri = roleQuery.createCriteria();
						roleCri.andUserIdEqualTo( user.getId().trim() );
						roleCri.andRoleIdEqualTo( baseRoleIdArray[2] );
						List<MerchantUserRole> result = merchantUserRoleMapper.selectByQuery(roleQuery);
						if( null==result || 1 >result.size() ){
							MerchantUserRole userRole = new MerchantUserRole();
							userRole.setUserId(user.getId());
							userRole.setId(UUIDGenerator.getUUID());
							userRole.setCreateDate(new Date());
							userRole.setRoleId(baseRoleIdArray[2]);
							String showName = BASE_ROLE_NAMES[2];
							userRole.setRemark(showName);
							merchantUserRoleMapper.insert(userRole);
						}
					}
					//保存成功之后再更新缓存
					try {
						merchantsService.loadAuthResource(user.getId());
					} catch (Exception e) {
						logger.error("合同到期超过60天的job:更新商家("+merchantCode+")用户("+user.getLoginName()+")的权限缓存发生异常！"+e);
					}
				}
			}else{
				logger.error("合同到期超过60天的job:overDay60CloseOperation，商家切换权限时未找到该商家("+merchantCode+")的登陆用户。");
			}
			
			// step3:调fss接口关闭店铺
			FSSResult fssResult = iFSSYmcApiService.getMerchantShops(merchantCode);
			com.belle.other.model.vo.ResultMsg resultMsg = new com.belle.other.model.vo.ResultMsg();
			if(null!=fssResult ){
				List<ShopVO> shopList = (List<ShopVO>)fssResult.getData();
				if(  null!=shopList && 0<shopList.size() ){
					for( ShopVO vo :shopList ){
						  try {
					            vo = iFSSYmcApiService.selectShopById(vo.getShopId());
					            if( !ACCESS_DENY.equalsIgnoreCase( vo.getAccess()) ){
					            	vo.setAccess(ACCESS_DENY);
					            	vo.setLastUpdateUserId("admin");
							        vo.setAuditDatetime(new Date());
					            	FSSResult result = iFSSYmcApiService.updateShopAccess(vo);
					            	
						            if(result != null) {
						            	String code = result.getCode();
						            	if("0".equals(code)){
						                    resultMsg.setReCode(code);
						                    resultMsg.setSuccess(true);
						                    /** 添加店铺日志——(只为店铺管理模块增加的日志) **/
						                    String loginUser = "";
						            		MerchantOperationLog operationLog = new MerchantOperationLog();
						            		operationLog.setMerchantCode(vo.getShopId());
						            		operationLog.setOperator(loginUser);
						            		operationLog.setOperated(new Date());
						            		operationLog.setOperationType(OperationType.SHOP);
						            		operationLog.setOperationNotes("每日job——合同到期超过60天且未续签关闭商家前台店铺。");
						            		supplierVoMapper.insertMerchantLog(operationLog);
						            	} else {
						                    resultMsg.setSuccess(false);
						                    resultMsg.setMsg(result.getMessage());
						                    resultMsg.setReCode(code);
						                    logger.error(result.getMessage());
						            	}
						            } else {
						                resultMsg.setMsg("job异常日志:更新店铺状态为空，请联系技术支持人员?");
						                logger.error("job异常日志:更新店铺状态为空");
						            }
						            
						  		}
					        } catch (Exception e) {
					            resultMsg.setSuccess(false);
					            logger.error(e.getMessage());
					        }
					}
				}
				
			}
			
		}
		
		
	}
	
	public void overDay90CloseOperation(){
		Date compareDateFrom = DateUtil2.addDate(DateUtil2.getCurrentDate() ,-96);
		Date compareDateTo = DateUtil2.addDate(DateUtil2.getCurrentDate() ,-90);
		List<SimpleContract> list = supplierContractMapper.getContractOverDayNumList(compareDateFrom,compareDateTo);
		for(SimpleContract c: list){
			//调用关闭接口
			this.stopAccout(c.getMerchantCode(), SYTEM_AUTO_RUNNING);
		}
	}
//	@Resource
//	private  MerchantSupplierExpandMapper merchantSupplierExpandMapper;
	public void initExpand(){
		//
		 List<com.yougou.merchant.api.supplier.vo.SupplierVo>  list = merchantMapper.selectNotExistSupplier();
		
		 MerchantSupplierExpand  expand = null;
		 
		 for(com.yougou.merchant.api.supplier.vo.SupplierVo vo:list){
			 expand = new MerchantSupplierExpand();
			 expand.setId(UUIDGenerator.getUUID());
			 expand.setMerchantCode(vo.getSupplierCode());
			 expand.setSupplierId(vo.getId());
			 //老商家  ？ TODO 
			 expand.setIsOld(MerchantConstant.OLD_MERCHANT_FLAG);
			 expand.setDeleteFlag(MerchantConstant.NOT_DELETED);
			 expand.setUptateTime(DateUtil2.getCurrentDateTimeToStr2());
			 merchantSupplierExpandMapper.insert(expand);
		 }
		
	}
	@Override
	public PageFinder<MerchantOperationLog> queryMerchantOperationLog(
			String merchantCode, Query query) {
		int count = supplierVoMapper.countForMerchantOperLog(merchantCode);
		PageFinder<MerchantOperationLog> pageFinder = null;
		if( count>0){
			List<MerchantOperationLog> logList = supplierVoMapper.queryMerchantOperLogByPage(merchantCode, query);
			pageFinder = new PageFinder<MerchantOperationLog>(query.getPage(),query.getPageSize(),count,logList);
		}else{
			pageFinder =  new PageFinder(query.getPage(),query.getPageSize(),0,null);
		}
		return pageFinder;
	}
	
	@Override
	public PageFinder<MerchantOperationLog> queryMOperationLog(
			String merchantCode,String operationType, Query query) {
		int count = supplierVoMapper.countForOperLogByOperType(merchantCode,operationType);
		PageFinder<MerchantOperationLog> pageFinder = null;
		if( count>0){
			List<MerchantOperationLog> logList = supplierVoMapper.queryMerchantOperLogByOperType(merchantCode,operationType, query);
			pageFinder = new PageFinder<MerchantOperationLog>(query.getPage(),query.getPageSize(),count,logList);
		}else{
			pageFinder =  new PageFinder(query.getPage(),query.getPageSize(),0,null);
		}
		return pageFinder;
	}
	
}
