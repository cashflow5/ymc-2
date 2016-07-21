package com.belle.yitiansystem.merchant.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.belle.finance.costsettlement.costsetofbooks.model.vo.CostSetofBooks;
import com.belle.infrastructure.constant.Constant;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.infrastructure.util.DateUtil;
import com.belle.infrastructure.util.FileFtpUtil;
import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.other.model.pojo.SupplierSp;
import com.belle.yitiansystem.merchant.constant.MerchantConstant;
import com.belle.yitiansystem.merchant.enums.ContractStatusEnum;
import com.belle.yitiansystem.merchant.model.pojo.AttachmentFormVo;
import com.belle.yitiansystem.merchant.model.pojo.SupplierContract;
import com.belle.yitiansystem.merchant.model.pojo.SupplierContractTrademark;
import com.belle.yitiansystem.merchant.model.pojo.SupplierSp4MyBatis;
import com.belle.yitiansystem.merchant.service.IMerchantOperatorApi;
import com.belle.yitiansystem.merchant.service.IMerchantServiceNew;
import com.belle.yitiansystem.merchant.service.IMerchantsService;
import com.belle.yitiansystem.merchant.service.ISupplierContractService;
import com.belle.yitiansystem.merchant.util.ExportHelper;
import com.belle.yitiansystem.merchant.util.MerchantComponent;
import com.belle.yitiansystem.merchant.util.MerchantUtil;
import com.belle.yitiansystem.merchant.util.SpringFTPUtil;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.belle.yitiansystem.systemmgmt.util.SysconfigProperties;
import com.yougou.kaidian.common.commodity.pojo.Cat;
import com.yougou.kaidian.common.util.DateUtil2;
import com.yougou.merchant.api.supplier.service.ISupplierService;
import com.yougou.merchant.api.supplier.vo.MerchantContractUpdateHistory;
import com.yougou.merchant.api.supplier.vo.MerchantContractUpdateHistory.ProcessingType;
import com.yougou.merchant.api.supplier.vo.MerchantOperationLog;
import com.yougou.merchant.api.supplier.vo.MerchantOperationLog.OperationType;
import com.yougou.merchant.api.supplier.vo.MerchantSupplierExpand;
import com.yougou.merchant.api.supplier.vo.SupplierVo;
import com.yougou.merchant.api.supplier.vo.YmcResult;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.model.category.Category;
import com.yougou.purchase.api.IPurchaseApiService;
import com.yougou.purchase.model.Supplier;

/**
 * 新合同管理类  
 * @author li.j1
 *
 */

@Controller
@RequestMapping("/yitiansystem/merchants/businessorder")
public class MerchantsContractController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MerchantsContractController.class);
	
	@Resource
	private ISupplierService supplierService;
	
	@Resource
	private ISupplierContractService supplierContractService;
	
	@Resource
	private SysconfigProperties sysconfigProperties;
	
	@Resource
	private IMerchantServiceNew merchantServiceNew;
	// 供应商service类
	@Resource
	private IMerchantsService merchantsService;
	
	@Resource
	private RedisTemplate<String, String> redisTemplate;
	
	@Resource
	private IPurchaseApiService purchaseApiService;
	
	@Resource
	private MessageChannel ftpChannelContract;
	
	@Resource
    private IMerchantOperatorApi merchantOperatorApi;
	
	@Resource
	private ICommodityBaseApiService commodityBaseApiService;
	
	@Resource
	private MerchantComponent merchantComponent;
	
	/**
     * 跳转到合同列表页
     * @param query
     * @param modelMap
     * @param merchantsVo
     * @return
     * @throws Exception
     */
    @RequestMapping("to_supplierContractList")
    public String toSupplierContractList(ModelMap modelMap, String listKind) throws Exception {
    	modelMap.addAttribute("listKind", listKind);
  	  	return "yitiansystem/merchants/supplier_contract";
    }
    
	/**
     * 跳转到添加/修改普通供应商页面
     * @param modelMap ModelMap
     * @param id 供应商ID
     * @return 页面路径
     */
    @RequestMapping("to_add_contract")
	public String toAddContract(ModelMap modelMap, String id) {
    	if(!StringUtils.isEmpty(id)){
    		com.belle.yitiansystem.merchant.model.pojo.SupplierContract supplierContract = supplierContractService.getSupplierContract(id);
          	modelMap.put("supplierContract",supplierContract);
    	}else{
    		modelMap.put("contractNo",MerchantUtil.initContractNo(redisTemplate));
    	}
    	modelMap.addAttribute("maxFileSize",merchantComponent.getMaxFileSize()+"");
		return "yitiansystem/merchants/add_contractList";
	}
	
	/**
     * 跳转到添加/修改招商供应商页面
     * @param modelMap ModelMap
     * @param id 供应商ID
     * @return 页面路径
     * @throws Exception 
     */
    @RequestMapping("to_add_contract_merchant")
    public String toAddContractMerchants(ModelMap modelMap, String id, String supplierId) throws Exception {
    	SupplierVo supplier = null;
    	if(StringUtils.isNotEmpty(supplierId)){
    		supplier = this.supplierService.getSupplierById(supplierId);
    	}
    	if(StringUtils.isNotEmpty(id)){
    		com.belle.yitiansystem.merchant.model.pojo.SupplierContract supplierContract = supplierContractService.getSupplierContract(id);
    		modelMap.addAttribute("supplierContract",supplierContract);
    		if (null == supplier){
    			supplier = this.supplierService.getSupplierByName(supplierContract.getSupplier());
    			supplierId = supplierContract.getSupplierId();
    		}
    		if (StringUtils.isNotBlank(supplierId)){
    			
    			MerchantSupplierExpand supplierExpand = merchantServiceNew.getSupplierExpandVoById(supplierId);
    			modelMap.addAttribute("supplierExpand",supplierExpand);  // 商家扩展表信息
    			
    			// 查询授权品牌.[brandNo;brandName_brandNo;brandName...]
    			String brandStrs = merchantsService.queryAuthorizationBrandBysupplierId(supplierContract.getSupplierId());
    			List<String> brandNos = merchantsService.getAuthorizationBrandNos(supplierContract.getSupplierId());
    			// 品牌分类关系集合
    			List<String> brandStructs = merchantsService.queryAuthorizationBrandCatBysupplierId(supplierContract.getSupplierId());
    			List<Category> treeModes = merchantComponent.getCategoryTreeMode(brandNos);
    						
    			merchantComponent.setTreeModesCheckStatus(treeModes, brandStructs);
    			
    			modelMap.addAttribute("brandStrs", brandStrs);
    			modelMap.addAttribute("treeModes", treeModes);
    		}
    	}else if(StringUtils.isNotEmpty(supplierId)){
    		modelMap.put("contractNo",MerchantUtil.initContractNo(redisTemplate));
    	}
    	modelMap.addAttribute("supplier", supplier);

    	//成本帐套名称
		modelMap.addAttribute("costSetofBooksList", merchantServiceNew.getAllCostSetofBooksList());
		// 获取省市区第一级结果集数据
		modelMap.addAttribute("areaList", merchantsService.getAreaList());
		modelMap.addAttribute("maxFileSize",merchantComponent.getMaxFileSize()+"");
		return "yitiansystem/merchants/add_contractList_merchant";
    }

    /**
     * 修改合同
     */
    @ResponseBody
    @RequestMapping("saveContract")
	public String saveContract(ModelMap modelMap,HttpServletRequest request,com.belle.yitiansystem.merchant.model.pojo.SupplierContract contract,String[]contract_attachment,String[] trademark,
			String[] authorizer,String[] type,String[] registeredTrademark,String[]registeredStartDate,String[] registeredEndDate,String[]beAuthorizer,
			String[]authorizStartdate,String[]authorizEnddate) {
    	JSONObject result = new JSONObject();
    	try{
    		SystemmgtUser user = GetSessionUtil.getSystemUser(request);
    	
    		if( null!=contract && StringUtils.isEmpty(contract.getSupplierId() )){
    			String supplierName = contract.getSupplier();
    			SupplierVo supplierVo = supplierService.getSupplierByName(supplierName);
    			if( null!=supplierVo && null!=supplierVo.getId() ){
    				contract.setSupplierId(supplierVo.getId());
    				contract.setBindStatus(MerchantConstant.CONTRACT_BIND);
    			}
    		}
    		supplierContractService.saveSupplierContract(contract, user.getUsername(), contract_attachment, trademark, authorizer, type, registeredTrademark, registeredStartDate, registeredEndDate, beAuthorizer, authorizStartdate, authorizEnddate);
    		result.put("resultCode", "200");
    	}catch(Exception e){
    		result.put("resultCode", "500");
    		result.put("msg","系统异常，保存失败");
    		logger.error("保存合同失败：",e);
    	}
    	return result.toString();
	}

	/**
	 * 保存招商供应商信息
	 * @param modelMap
	 * @param request
	 * @param contract
	 * @param contract_attachment
	 * @param trademark
	 * @param authorizer
	 * @param type
	 * @param registeredTrademark
	 * @param registeredStartDate
	 * @param registeredEndDate
	 * @param beAuthorizer
	 * @param authorizStartdate
	 * @param authorizEnddate
	 * @return
	 */
	@ResponseBody
    @RequestMapping("saveContractMerchant")
	public String saveContractMerchant(ModelMap modelMap, HttpServletRequest request, SupplierContract contract,
			Supplier supplier,AttachmentFormVo attachmentFormVo, boolean isSubmit) {
    	JSONObject result = new JSONObject();
    	try{
    		SystemmgtUser user = GetSessionUtil.getSystemUser(request); 
    		supplier.setId(contract.getSupplierId());
    		
    		SupplierVo supplierVo = supplierService.getSupplierByName(contract.getSupplier());
    		contract.setSupplierCode(supplierVo.getSupplierCode());
    		contract.setBindStatus(MerchantConstant.CONTRACT_BIND);
    		String oldContractId = contract.getId();
    		String oldContractStatus = contract.getStatus();
    		
    		if( isSubmit ){
    			supplierContractService.saveSupplierContractMerchant(contract, supplier, user.getUsername(), isSubmit, attachmentFormVo);
    		}else{
    			supplierContractService.saveSupplierContractSimple(contract, supplier, user.getUsername(), attachmentFormVo);
    		}
    		//保存品类授权
			merchantServiceNew.updateMerchantsBankAndCat(supplierVo,
				attachmentFormVo.getBankNoHidden(), attachmentFormVo.getCatNameHidden() );
    		if( isSubmit ){
	    		//恢复API授权
	    		supplierContractService.recoverApiLicenceBySupplierId(contract.getSupplierId());
    		}
    		// 添加供应商合同修改日志
			try {
				MerchantOperationLog log = new MerchantOperationLog();
				log.setType(MerchantConstant.LOG_FOR_CONTRACT);
				log.setOperator(user.getUsername());
				log.setContractNo(contract.getContractNo());
				if(StringUtils.isEmpty(oldContractId)){
					log.setOperationNotes("创建合同");
					log.setOperationType(OperationType.ADD_CONTRACT.getDescription());
				}else{
					log.setOperationNotes("修改合同");
					log.setOperationType(OperationType.UPDATE_CONTRACT.getDescription());
				}
				log.setMerchantCode(supplierVo.getSupplierCode());
				merchantServiceNew.insertMerchantLog(log);

			} catch (Exception e) {
				logger.error("供应商合同(" + contract.getContractNo() + ")增加修改日志异常.",e);
			}
			
			try{	
				if(isSubmit || StringUtils.isEmpty(oldContractId)){
					//记录日志-合同状态流转日志
					MerchantContractUpdateHistory updateHistory = new MerchantContractUpdateHistory();
					updateHistory.setContractNo(contract.getContractNo());
					updateHistory.setSupplierId(contract.getSupplierId());
					updateHistory.setOperator(user.getUsername());
					if(StringUtils.isEmpty(oldContractId)){
						if(isSubmit){
							updateHistory.setProcessing(ProcessingType.CREATE_SUBMIT.getDescription());
							updateHistory.setUpdateAfter(MerchantConstant.CONTRACT_STATUS_BUSINESS_AUDITED_ZH);
						}else{
							updateHistory.setProcessing(ProcessingType.CREATE_CONTRACT.getDescription());
							updateHistory.setUpdateAfter(MerchantConstant.CONTRACT_STATUS_NEW_ZH);
						}
						updateHistory.setUpdateBefore("");
					}else{
						if(isSubmit){
							updateHistory.setProcessing(ProcessingType.SUBMIT_FINANCE.getDescription());
							updateHistory.setUpdateAfter(MerchantConstant.CONTRACT_STATUS_BUSINESS_AUDITED_ZH);
							if(StringUtils.isNotBlank(oldContractStatus)){
								updateHistory.setUpdateBefore(ContractStatusEnum.getValue(oldContractStatus));
							}else{
								updateHistory.setUpdateBefore(MerchantConstant.CONTRACT_STATUS_NEW_ZH);
							}
						}
					}
					updateHistory.setType(MerchantConstant.LOG_FOR_CONTRACT);
					updateHistory.setUpdateField("status");
					YmcResult ymcResult = merchantOperatorApi.addContractLog(updateHistory);
					if(YmcResult.ERROR_CODE.equals(ymcResult.getCode())){
						logger.error("供应商合同(" + contract.getSupplier() + ")增加合同状态流转日志异常."+ymcResult.getMessage());
					}
				}
	    	} catch (Exception e) {
				logger.error("供应商合同(" + contract.getSupplier() + ")增加合同状态流转日志异常.",e);
			}
    		
    		result.put("resultCode", "200");
    	}catch(Exception e){
    		result.put("resultCode", "500");
    		result.put("msg",StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "系统异常，保存失败");
    		logger.error("保存合同失败：",e);
    	}
    	return result.toString();
	}
	
	
	/**
     * 合同附件上传
     * @param request
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/attachmentUpload")
	public String attachmentUpload(MultipartHttpServletRequest request,HttpSession session) {
    	logger.info("------合同管理--开始上传文件------");
    	int maxFileSize = merchantComponent.getMaxFileSize();
		JSONObject result = new JSONObject();
		String innerFilePath = sysconfigProperties.getContractFtpPath()+"/";
		Iterator<String> itr = request.getFileNames();
		String resultCode = "";
		String msg = "";
		String fileName = "";
		String realName = "";
		if (itr.hasNext()) {
			MultipartFile multipartFile = request.getFile(itr.next());
			fileName = multipartFile.getOriginalFilename();
			String type = fileName.substring(fileName.lastIndexOf("."));
			type = type.trim();
			realName = System.currentTimeMillis()+type;
			// 格式校验
			if( type.equalsIgnoreCase("doc")|| type.equalsIgnoreCase("xls")|| type.equalsIgnoreCase("docx")|| 
					type.equalsIgnoreCase("xlsx")|| type.equalsIgnoreCase("pdf")|| type.equalsIgnoreCase("txt")||
						type.equalsIgnoreCase("jpg")|| type.equalsIgnoreCase("bmp")|| type.equalsIgnoreCase("png")||
							type.equalsIgnoreCase("jpeg")|| type.equalsIgnoreCase("rar")
					){
				logger.error("附件格式只能是 doc、xls、docx、xlsx 、pdf、txt、jpg、bmp、png、jpeg,或者打包rar格式上传。被禁止上传的文件格式："+type);
				result.put("resultCode","500");
				result.put("fileName", fileName);
				result.put("realName", realName);
				result.put("msg", "附件格式只能是 doc、xls、docx、xlsx 、pdf、txt、jpg、bmp、png、jpeg,或者打包rar格式上传。请上传有效的格式文件");
				return result.toString();
			}
			// 大小校验
			long size = multipartFile.getSize();
			if (size > maxFileSize) {
				logger.error("上传文件超过"+(maxFileSize/1024/1024)+"M，不能上传");
				result.put("resultCode","500");
				result.put("fileName", fileName);
				result.put("realName", realName);
				result.put("msg", "文件最大不能超过"+(maxFileSize/1024/1024)+"M");
				return result.toString();
			}
			
			try {
							
				boolean flag = SpringFTPUtil.ftpUpload(ftpChannelContract,multipartFile.getInputStream(), realName, innerFilePath);
				if(flag){
					logger.info("上传文件成功");
					resultCode = "200";
				}else{
					logger.error("SpringFTPUtil上传文件失败");
					resultCode = "500";
					msg = "上传文件失败";
				}
				
			} catch (Exception e) {
				logger.error("上传文件出现异常",e);
				resultCode = "500";
				msg = "上传文件失败";
			}
			
		}else{
			logger.error("上传的文件为空");
			resultCode = "500";
			msg = "上传的文件为空";
		}
		result.put("resultCode",resultCode);
		result.put("fileName", fileName);
		result.put("realName", realName);
		result.put("msg", msg);
		logger.info("-------合同管理--上传文件结束-----");
		return result.toString();
	}
    
    /**
     * 获取合同列表
     * @param modelMap
     * @param request
     * @param query
     * @param type
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("getContractList")
 	public String getContractList(ModelMap modelMap, HttpServletRequest request,com.yougou.merchant.api.common.Query query,
 			String type, String listKind) throws Exception {
    	Map<String,Object> param = this.builderParams(request, true);
    	SystemmgtUser user = GetSessionUtil.getSystemUser(request);
    	param.put("curUser", user.getUsername());
    	PageFinder<com.belle.yitiansystem.merchant.model.pojo.SupplierContract> pageFinder = this.supplierContractService.findSupplierContractList(param, query);
    	modelMap.put("pageFinder",pageFinder);
    	modelMap.put("listKind",listKind);
    	return "yitiansystem/merchants/supplier_contract_list";
 	}
    
    /**
     * 查看供应商合同--普通供应商
     * @param modelMap
     * @param id
     * @param type
     * @return
     * @throws Exception
     */
    @RequestMapping("getContractDetail")
   	public String getContractDetail(ModelMap modelMap,String id,String type) throws Exception {
    	com.belle.yitiansystem.merchant.model.pojo.SupplierContract supplierContract = supplierContractService.getSupplierContract(id);
      	modelMap.put("supplierContract",supplierContract);
    	modelMap.put("type",type);
      	return "yitiansystem/merchants/supplier_contract_detail";
   	}
    
    /**
     * 查看供应商合同--招商供应商
     * @param modelMap
     * @param id   商家ID
     * @param type
     * @return
     * @throws Exception
     */
    @RequestMapping("getContractDetail_merchant")
   	public String getContractDetailMerchant(ModelMap modelMap,String id,String listKind) throws Exception {
    	com.belle.yitiansystem.merchant.model.pojo.SupplierContract supplierContract = supplierContractService.getSupplierContract(id);
      	modelMap.put("supplierContract",supplierContract);
      	String supplierId = supplierContract.getSupplierId();
      	if (StringUtils.isNotBlank(supplierId)) {
			SupplierVo supplier = this.supplierService.getSupplierByName(supplierContract.getSupplier());
			modelMap.addAttribute("supplier", supplier);
			MerchantSupplierExpand supplierExpand = merchantServiceNew.getSupplierExpandVoById(supplierId);
			modelMap.addAttribute("supplierExpand",supplierExpand);  
			// 品牌二级分类信息查询
			List<Cat> catList = merchantsService.buildBrandCatSecondLevelList(supplierId);
			modelMap.addAttribute("catList",catList);  
		}
      	BigDecimal totalRefundDeposit =  BigDecimal.ZERO;
      	if(String.valueOf(MerchantConstant.YES).equals(supplierContract.getIsTransferDeposit())){
      		totalRefundDeposit = totalRefundDeposit.add(supplierContract.getPreDeposit() == null ? BigDecimal.ZERO : supplierContract.getPreDeposit());
      	}
      	totalRefundDeposit = totalRefundDeposit.add(supplierContract.getDeposit() == null ? BigDecimal.ZERO : supplierContract.getDeposit());
      	modelMap.addAttribute("totalRefundDeposit", totalRefundDeposit);
      	// 成本帐套名称
 		List<CostSetofBooks> costSetofBooksList = merchantServiceNew.getAllCostSetofBooksList();
 		modelMap.addAttribute("costSetofBooksList", costSetofBooksList);
    	modelMap.put("listKind",listKind);
//		modelMap.put("headPath", sysconfigProperties.getContractFtpPathRead());
    	
      	return "yitiansystem/merchants/supplier_contract_detail_merchant";
   	}
    
    /**
     * 查看供应商合同--招商供应商--日志信息
     * @param modelMap
     * @param id   商家ID
     * @param type
     * @return
     * @throws Exception
     */
    @RequestMapping("getContractDetail_log")
    public String getContractDetailLog(ModelMap modelMap,String contractNo,String contractId, String listKind) throws Exception {
    	modelMap.put("listKind",listKind);
    	modelMap.put("contractNo",contractNo);
    	modelMap.put("contractId",contractId);
    	List<MerchantContractUpdateHistory>  historyList = merchantOperatorApi.listContractHistory(contractNo);
    	modelMap.put("historyList",historyList);
    	List<MerchantOperationLog> logList = merchantOperatorApi.listContractOperationLog(contractNo);
    	modelMap.put("logList",logList);
    	return "yitiansystem/merchants/supplier_contract_detail_log";
    }
    
    /**
     * 异步查询合同授权资质信息
     * @param modelMap
     * @param id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("getContractDetailAjax")
   	public String getContractDetailAjax(ModelMap modelMap,String id) throws Exception {
    	JSONObject result = new JSONObject();
    	com.belle.yitiansystem.merchant.model.pojo.SupplierContract supplierContract = supplierContractService.getSupplierContract(id);
    	List<SupplierContractTrademark> trademarkList = null;
    	if( null!=supplierContract ){
    		trademarkList = supplierContract.getTrademarkList() ;
    	}
    	result.put("trademarkList",trademarkList);
      	return  result.toString();
   	}
    
    /**
     * 合同附件下载
     * @param response
     * @param fileName
     * @param realName
     * @param outputStream
     * @modify 2015-05-06 li_j1
     */
    @RequestMapping("/downLoadContractAttachment")
	public void downLoadContractAttachment(HttpServletResponse response, String fileName,String realName,OutputStream outputStream) {
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			FTPClientConfig comfig = new FTPClientConfig(
					FTPClientConfig.SYST_NT);
			comfig.setServerLanguageCode("zh");
			FileFtpUtil ftp = new FileFtpUtil(sysconfigProperties.getContractFtpServer(), 
					Integer.valueOf(sysconfigProperties.getContractFtpPort()),
					sysconfigProperties.getContractFtpUsername(), sysconfigProperties.getContractFtpPassword());
			ftp.login();

			// 这个就就是弹出下载对话框的关键代码
			response.setContentType("application/x-msdownload; charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"),"iso8859-1"));
			ftp.downRemoteFile(sysconfigProperties.getContractFtpPath() + "/" + realName, os);
			os.flush();
			ftp.logout();
		} catch (Exception e) {
			logger.error("下载附件出现异常",e);
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				logger.error("下载附件出现异常",e);
			}
		}
	}
    
    /**
     * 选择供应商的页面
     * @param model
     * @param supplierCode
     * @param supplierType
     * @param request
     * @return
     * @throws Exception 
     */
	@RequestMapping("/to_suppliersp4Contract")
	public String to_suppliersp4Contract(HttpServletRequest request,ModelMap model, Query query, Supplier supplier) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("invoiceFlag", request.getParameter("invoiceFlag"));
		params.put("supplier", supplier.getSupplier());
		params.put("supplierCode", supplier.getSupplierCode());
		if("1".equals(supplier.getSupplierType())){
			params.put("supplierType", "招商供应商");
			params.put("isOld", "2");
		}else{
			params.put("supplierType", "普通供应商");
		}
		model.addAttribute("invoiceFlag",  request.getParameter("invoiceFlag"));
		model.addAttribute("supplier", supplier.getSupplier());
		model.addAttribute("supplierCode", supplier.getSupplierCode());
		model.addAttribute("supplierType", supplier.getSupplierType());
		
		com.yougou.merchant.api.common.Query _query = new com.yougou.merchant.api.common.Query(query.getPageSize());
		_query.setPage(query.getPage());
		
		PageFinder<SupplierSp4MyBatis> pageFinder = supplierContractService.selectSupplier4Contact(params,_query);
		model.addAttribute("pageFinder", pageFinder);
		return "yitiansystem/merchants/suppliersp_list4contract";
	}
	
	/**
	 * 导出合同列表
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/exportContractList")
	public void exportContractList(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception 
	{
		Map<String, Object> param = new HashMap<String,Object>();
		try {
			param = this.builderParams(request, false);
		} catch (UnsupportedEncodingException e1) {
			logger.error("合同列表导出出错：无法从请求构建参数MAP",e1);
			throw new Exception("合同列表导出出错：无法从请求构建参数MAP");
		}
    	SystemmgtUser user = GetSessionUtil.getSystemUser(request);
    	param.put("curUser", user.getUsername());
    	
		try {
			List<com.belle.yitiansystem.merchant.model.pojo.SupplierContract> contractList =  this.supplierContractService.findSupplierContractListForExport(param);
			List<Object[]> list = new ArrayList<Object[]>();
			Object[] _obj = null;
			for (com.belle.yitiansystem.merchant.model.pojo.SupplierContract supplierContract : contractList) {
				_obj = new Object[16];
				String isUploaded = "未上传";
				String isBounded = "未绑定";
				String tradeMarksString = "";
				List<SupplierContractTrademark> trademarkList = supplierContract.getTrademarkList() ;
				if( 0<supplierContract.getAuthorityAttachmentCount() && 0< supplierContract.getContractAttachmentCount() 
						&& 0<supplierContract.getNaturalAttachmentCount() && 0< supplierContract.getTrademarkAttachmentCount()
							){
					isUploaded = "已上传";
				}
				if( MerchantConstant.CONTRACT_BIND.equals(supplierContract.getBindStatus()) ){
					isBounded = "已绑定";
				}
				if( null!=trademarkList && 0<trademarkList.size() ){
					for(int i=0;i<trademarkList.size();i++){
						SupplierContractTrademark tradeMark = (SupplierContractTrademark)trademarkList.get(i);
						if( null!=tradeMark && !StringUtils.isEmpty(tradeMark.getTrademark()) ){
							tradeMarksString = "".equals(tradeMarksString)?tradeMark.getTrademark():tradeMarksString+","+tradeMark.getTrademark();
						}
					}
				}
				supplierContract.setStatusName(ContractStatusEnum.getValue(supplierContract.getStatus()));
				_obj[0] = supplierContract.getCreateTime();
				_obj[1] = supplierContract.getSupplier();
				_obj[2] = tradeMarksString;
				_obj[3] = supplierContract.getMarkRemainingDays();
				_obj[4] = supplierContract.getSupplierCode();
				_obj[5] = supplierContract.getSupplierType();
				_obj[6] = supplierContract.getContractNo();
				_obj[7] = supplierContract.getDeclarant();
				_obj[8] = supplierContract.getEffectiveDate();
				_obj[9] = supplierContract.getFailureDate();
				_obj[10] = supplierContract.getContractRemainingDays();
				_obj[11] = supplierContract.getStatusName();
				_obj[12] = supplierContract.getYccontact();
				_obj[13] = supplierContract.getUpdateTime();
				_obj[14] = isUploaded;
				_obj[15] = isBounded;
				
				list.add(_obj);
			}
			
			ExportHelper exportHelper = new ExportHelper();
			String sheetName = DateUtil.formatDateByFormat(new Date(), "yyyy-MM-dd");

			String fileRealPath = request.getSession().getServletContext().getRealPath(Constant.TEMPLATE_PATH );
			// 模板路径
			String templatePath = fileRealPath +"/"+ Constant.CONTRACT_XLS;
			// {(开始行),(总列数)}
			int[] paras = { 1, 16 };
			// 数值列 {0,1,2,3,....}
//			int[] numCol = { 9, 10,11,12 };
			// "合计"标题 {(开始列),(结束列)}
			int[] amountCol = null;
			// 指定索引值(0,3)....
			Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
			indexMap.put(0, 0);
			indexMap.put(1, 1);
			indexMap.put(2, 2);
			indexMap.put(3, 3);
			indexMap.put(4, 4);
			indexMap.put(5, 5);
			indexMap.put(6, 6);
			indexMap.put(7, 7);
			indexMap.put(8, 8);
			indexMap.put(9, 9);
			indexMap.put(10, 10);
			indexMap.put(11, 11);
			indexMap.put(12, 12);
			indexMap.put(13, 13);
			indexMap.put(14, 14);
			indexMap.put(15, 15);

			exportHelper.doExport(list, templatePath, Constant.CONTRACT_XLS, sheetName, null, paras, null, amountCol, indexMap, false, response);
		} catch (Exception e) {
			logger.error("合同列表导出出错：将数据导出生成excel出错",e);
			throw new Exception("合同列表导出出错：将数据导出生成excel出错");
		}
		
	}
	
	/**
     * 校验财务审核合同-验证单据是否处于财务待审核状态，收款单是否已收款等
     * @param contractId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("to_validate_finance_audit_contract")
    public String validateFinanceAuditContract(String contractId,boolean isPass) throws Exception {
    	JSONObject result = new JSONObject();
    	SupplierContract contract = supplierContractService.getSupplierContractById(contractId);
    	String msg = "";
    	if(contract != null){
			if(!MerchantConstant.CONTRACT_STATUS_BUSINESS_AUDITED.equals(contract.getStatus())){
				msg += "合同状态不是业务审核通过，请重试！";
			}else if(isPass){
    			if(contract.getMarkRemainingDays() ==null || contract.getMarkRemainingDays() <= 0){
    				msg += "商标授权已经过期，不能审核通过！<br/><br/>";
    			}
    			if(contract.getContractRemainingDays() == null || contract.getContractRemainingDays() <= 0){
    				msg += "合同已经过期，不能审核通过！<br/><br/>";
    			}
				if(String.valueOf(MerchantConstant.YES).equals(contract.getIsNeedDeposit()) 
						&& !MerchantConstant.FEE_STATUS_PAYED.equals(contract.getDepositStatus())){
					msg += "保证金尚未收款，不能审核通过！<br/><br/>";
				}
				if(String.valueOf(MerchantConstant.YES).equals(contract.getIsNeedUseFee()) 
						&& !MerchantConstant.FEE_STATUS_PAYED.equals(contract.getUseFeeStatus())){
					msg += "平台使用费尚未收款，不能审核通过！";
				}
			}
			if(StringUtils.isNotBlank(msg)){
				result.put("result", "failure");
				result.put("msg", msg);
			}else{
				result.put("result", "success");
			}
    	}else{
    		result.put("result", "failure");
    		result.put("msg", "未找到合同数据，请重试！");
    	}
    	
    	return result.toString();
    }
    
    /**
     * 财务审核合同
     * 
     * @param contractId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("finance_audit_contract")
    public String financeAuditContract(String contractId, boolean isPass, String remark, HttpServletRequest request) throws Exception {
    	JSONObject result = new JSONObject();
    	SupplierContract vo = supplierContractService.getSupplierContractById(contractId);
    	SystemmgtUser user = GetSessionUtil.getSystemUser(request);
    	SupplierContract contract = new SupplierContract();
    	contract.setId(contractId);
		contract.setUpdateTime(DateUtil2.getCurrentDateTimeToStr2());
		contract.setUpdateUser(user.getUsername());
		if(isPass){		
			if(MerchantConstant.CONTRACT_RENEW_FLAG_FUTURE.equals(vo.getRenewFlag())){
				contract.setStatus(MerchantConstant.CONTRACT_STATUS_FINANCE_AUDITED);
			}else{
				if(vo.getContractRemainingDays() != null && vo.getContractRemainingDays()>0){
					contract.setStatus(MerchantConstant.CONTRACT_STATUS_EFFECTIVE);
				}else{
					contract.setStatus(MerchantConstant.CONTRACT_STATUS_EXPIRED);
				}
			}
		}else{
			contract.setStatus(MerchantConstant.CONTRACT_STATUS_FINANCE_REFUSED);
		}
		
		try{			
			supplierContractService.updateSupplierContract(contract);
			
			try{	
	    		//记录日志-合同状态流转日志
	        	MerchantContractUpdateHistory updateHistory = new MerchantContractUpdateHistory();
	        	updateHistory.setContractNo(vo.getContractNo());
	        	updateHistory.setSupplierId(vo.getSupplierId());
	        	updateHistory.setOperator(user.getUsername());
	        	updateHistory.setProcessing(ProcessingType.FINANCE_AUDIT.getDescription());
	        	updateHistory.setRemark(remark);
	        	updateHistory.setType(MerchantConstant.LOG_FOR_CONTRACT);
	        	updateHistory.setUpdateAfter(ContractStatusEnum.getValue(contract.getStatus()));
	        	updateHistory.setUpdateBefore(ContractStatusEnum.getValue(vo.getStatus()));
	        	updateHistory.setUpdateField("status");
	        	YmcResult ymcResult = merchantOperatorApi.addContractLog(updateHistory);
	        	if(YmcResult.ERROR_CODE.equals(ymcResult.getCode())){
	        		logger.error("审核供应商合同(" + contract.getSupplier() + ")增加合同状态流转日志异常."+ymcResult.getMessage());
	        	}
	    	} catch (Exception e) {
				logger.error("审核供应商合同(" + contract.getSupplier() + ")增加合同状态流转日志异常.");
			}
			result.put("result", "success");
			result.put("msg", "操作成功！");
		}catch(Exception e){
			result.put("result", "failure");
    		result.put("msg", "操作失败，请重试！");
		}
		
    	return result.toString();
    }
	
    /**
     * 申请退款
     * 
     * @param contractId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("request_refund")
    public String requestRefund(String contractId, String type, HttpServletRequest request) throws Exception {
    	JSONObject result = new JSONObject();
    	
    	SystemmgtUser user = GetSessionUtil.getSystemUser(request);
    	SupplierContract supplierContract = supplierContractService.getSupplierContractById(contractId);
    	
    	if(!MerchantConstant.FEE_STATUS_PAYED.equals(supplierContract.getDepositStatus()) && "deposit".equals(type)){
    		result.put("result", "failure");
    		result.put("msg", "保证金不是已收款状态，请确认！");
    		return result.toString();
    	}
    	
    	if(!MerchantConstant.FEE_STATUS_PAYED.equals(supplierContract.getUseFeeStatus()) && "useFee".equals(type)){
    		result.put("result", "failure");
    		result.put("msg", "平台使用费不是已收款状态，请确认！");
    		return result.toString();
    	}
    	boolean refundResult = supplierContractService.createFinanceRefundBill(supplierContract, user.getUsername(), type);
    	if(refundResult){
    		result.put("result", "success");
    		result.put("msg", "申请退款成功！");
    	}else{
    		result.put("result", "failure");
    		result.put("msg", "申请退款失败，请重试！");
    	}
    	return result.toString();
    }   
}
