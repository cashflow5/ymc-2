package com.belle.yitiansystem.merchant.web.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.belle.finance.costsettlement.costsetofbooks.model.vo.CostSetofBooks;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.infrastructure.util.Md5Encrypt;
import com.belle.other.model.pojo.SupplierSp;
import com.belle.other.service.ISupplierSpLocalService;
import com.belle.other.util.CodeGenerate;
import com.belle.yitiansystem.merchant.constant.MerchantConstant;
import com.belle.yitiansystem.merchant.enums.ContractStatusEnum;
import com.belle.yitiansystem.merchant.enums.MerchantStatusEnum;
import com.belle.yitiansystem.merchant.model.entity.DateComparator;
import com.belle.yitiansystem.merchant.model.pojo.AttachmentFormVo;
import com.belle.yitiansystem.merchant.model.pojo.ContactsFormVo;
import com.belle.yitiansystem.merchant.model.pojo.SupplierContract;
import com.belle.yitiansystem.merchant.model.vo.MerchantsVo;
import com.belle.yitiansystem.merchant.service.IMerchantOperationLogService;
import com.belle.yitiansystem.merchant.service.IMerchantOperatorApi;
import com.belle.yitiansystem.merchant.service.IMerchantServiceNew;
import com.belle.yitiansystem.merchant.service.IMerchantsService;
import com.belle.yitiansystem.merchant.service.ISupplierContractService;
import com.belle.yitiansystem.merchant.util.MerchantComponent;
import com.belle.yitiansystem.merchant.util.MerchantUtil;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.belle.yitiansystem.systemmgmt.util.SysconfigProperties;
import com.yougou.kaidian.common.commodity.pojo.Cat;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.common.util.DateUtil2;
import com.yougou.merchant.api.common.UUIDGenerator;
import com.yougou.merchant.api.pic.service.vo.MerchantPictureCatalog;
import com.yougou.merchant.api.supplier.service.IBrandCatApi;
import com.yougou.merchant.api.supplier.service.IMerchantsApi;
import com.yougou.merchant.api.supplier.service.ISupplierService;
import com.yougou.merchant.api.supplier.vo.BrandVo;
import com.yougou.merchant.api.supplier.vo.ContactsVo;
import com.yougou.merchant.api.supplier.vo.MerchantContractUpdateHistory;
import com.yougou.merchant.api.supplier.vo.MerchantContractUpdateHistory.ProcessingType;
import com.yougou.merchant.api.supplier.vo.MerchantOperationLog;
import com.yougou.merchant.api.supplier.vo.MerchantOperationLog.OperationType;
import com.yougou.merchant.api.supplier.vo.MerchantRejectedAddressVo;
import com.yougou.merchant.api.supplier.vo.MerchantSupplierExpand;
import com.yougou.merchant.api.supplier.vo.MerchantUser;
import com.yougou.merchant.api.supplier.vo.SupplierQueryVo;
import com.yougou.merchant.api.supplier.vo.SupplierVo;
import com.yougou.merchant.api.supplier.vo.YmcResult;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.model.brand.Brand;
import com.yougou.pc.model.category.Category;
import com.yougou.purchase.api.IPurchaseApiService;
import com.yougou.purchase.model.Supplier;
import com.yougou.purchase.model.SupplierContact;
import com.yougou.wms.wpi.warehouse.service.IWarehouseCacheService;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * @title 新商家入驻需求的商家管理模块类
 * @since 2015-07-16
 * @author luo.q1
 *
 */
@Controller
@RequestMapping("/yitiansystem/merchants/manage")
public class MerchantManageController {

	private static Logger logger = Logger.getLogger(MerchantsController.class);
	@Resource
	private ISupplierService supplierService;
	@Resource
	private IBrandCatApi brandcatApi;
	@Resource
	private IMerchantsApi merchantsApi;
	@Resource
	private IMerchantServiceNew merchantServiceNew;
	// 供应商service类
	@Resource
	private IMerchantsService merchantsService;
	@Resource
	private IMerchantOperationLogService merchantOperationLogService;
	@Resource
	private ICommodityBaseApiService commodityBaseApiService;
	@Resource
	private IPurchaseApiService purchaseApiService;
	@Resource
	private ISupplierSpLocalService supplierSpLocalService;
	@Resource
	private IWarehouseCacheService warehouseCacheService;
	@Resource
	private ISupplierContractService supplierContractService;
	@Resource
	private SysconfigProperties sysconfigProperties;
	@Resource
	private RedisTemplate<String, String> redisTemplate;
	@Resource
	private RedisTemplate<String, Object> redisTemplateForSupplierCreate;
	@Resource
    private IMerchantOperatorApi merchantOperatorApi;
	@Resource
	private MerchantComponent merchantComponent;
	
	/**
	 * 
	 * 跳转到 业务审核商家列表页面 / 财务审核商家列表页面
	 * 
	 * @Date 2015-06-25
	 * @author luo.q
	 * @throws Exception
	 */
	@RequestMapping("to_supplier_List")
	public String toFirstAuditList(Query query, ModelMap modelMap, MerchantsVo merchantsVo, String listKind) {

		SupplierQueryVo vo = new SupplierQueryVo();
		// 根据参数listKind ， 确定是哪个页面的查询，设置SupplierQueryVo
		if (StringUtils.isNotBlank(listKind)
				&& (MerchantConstant.LIST_BUSINESS_APPROVAL.equalsIgnoreCase(listKind) || MerchantConstant.LIST_FINANCE_APPROVAL
						.equalsIgnoreCase(listKind))) {
			vo.setListKind(listKind);
		}
		vo.setSupplier(merchantsVo.getSupplier());
		vo.setSupplierCode(merchantsVo.getSupplierCode());
		vo.setIsValid((merchantsVo.getIsValid() == null || merchantsVo.getIsValid() == 0) ? null : merchantsVo.getIsValid());
		vo.setIsInputYougouWarehouse(merchantsVo.getIsInputYougouWarehouse());
		vo.setSupplierYgContacts(merchantsVo.getSupplierYgContacts());
		if (StringUtils.isNotEmpty(merchantsVo.getSequence())) {
			vo.setSequence(merchantsVo.getSequence());
		}
		if (StringUtils.isNotEmpty(merchantsVo.getOrderBy())) {
			vo.setOrderBy(merchantsVo.getOrderBy());
		}
		if (StringUtils.isNotEmpty(merchantsVo.getFlagForReminds())) {
			vo.setFlagForReminds(merchantsVo.getFlagForReminds());
		}
		if(StringUtils.isNotEmpty(merchantsVo.getUpdateUser())){
			vo.setUpdateUser(merchantsVo.getUpdateUser());
		}
		if(StringUtils.isNotEmpty(merchantsVo.getIsNeedRenew())){
			vo.setIsNeedRenew(merchantsVo.getIsNeedRenew());
		}
		if(StringUtils.isNotEmpty(merchantsVo.getIsNewContract())){
			vo.setIsNewContract(merchantsVo.getIsNewContract());
		}
		// 供应商类型 1：招商 2：普通
		if (null != merchantsVo.getType() && 0 < merchantsVo.getType()) {
			switch (merchantsVo.getType()) {
			case MerchantConstant.SUPPLIER_TYPE_MERCHANT:
				vo.setSupplierType(MerchantConstant.SUPPLIER_TYPE_MERCHANT_ZH);
				break;
			default:
				vo.setSupplierType(MerchantConstant.SUPPLIER_TYPE_BELLE_ZH);
				break;
			}
		} else if (StringUtils.isNotBlank(listKind)
				&& (MerchantConstant.LIST_BUSINESS_APPROVAL.equalsIgnoreCase(listKind) || MerchantConstant.LIST_FINANCE_APPROVAL
						.equalsIgnoreCase(listKind))) {// 对于两种审核列表来说，只显示招商供应商
			vo.setSupplierType(MerchantConstant.SUPPLIER_TYPE_MERCHANT_ZH);
		}

		// 通过品牌名称查询到No
		if (StringUtils.isNotBlank(merchantsVo.getBrandName())) {
			List<Brand> brands = commodityBaseApiService.getBrandListLikeBrandName("%" + merchantsVo.getBrandName(),
					(short) 1);
			if (CollectionUtils.isNotEmpty(brands)) {
				List<String> brandNos = new ArrayList<String>();
				for (Brand brand : brands) {
					brandNos.add(brand.getBrandNo());
				}
				vo.setBrandNos(brandNos);
			}
		}

		// 查资质提醒总数
		int totalRemind = supplierSpLocalService.countTotalRemind(vo);
		modelMap.addAttribute("totalRemind", totalRemind);

		com.yougou.merchant.api.common.Query _query = new com.yougou.merchant.api.common.Query(query.getPageSize());
		_query.setPage(query.getPage());
		// com.yougou.merchant.api.common.PageFinder<SupplierVo> pageFinder =
		// supplierService.querySupplierListByPage(vo, _query);
		PageFinder<SupplierVo> pageFinder = supplierSpLocalService.querySupplierListByPage(vo, _query);
		if (pageFinder != null && CollectionUtils.isNotEmpty(pageFinder.getData())) {
			for (SupplierVo _supplier : pageFinder.getData()) {
				// 续签中合同
		    	com.belle.yitiansystem.merchant.model.pojo.SupplierContract supplierContract 
		    	= supplierContractService.getSupplierRenewContract(_supplier.getId());
		    	if(null != supplierContract){
		    		_supplier.setRenewContractStatus(supplierContract.getStatus());
		    	}else{
		    		_supplier.setRenewContractStatus("0");
		    	}
		    	
				List<String> supplyIds = new ArrayList<String>();
				supplyIds.add(_supplier.getId());
				List<BrandVo> _brands = brandcatApi.queryLimitBrandBysupplyId(supplyIds);
				if (CollectionUtils.isNotEmpty(_brands)) {
					for (BrandVo _brandVo : _brands) {
						Brand temp = commodityBaseApiService.getBrandByNo(_brandVo.getBrandNo());
						_brandVo.setBrandName(temp == null ? "" : temp.getBrandName());
					}
				}
				_supplier.setBrandVos(_brands);

			}
		}

		modelMap.addAttribute("pageFinder", pageFinder);
		modelMap.addAttribute("merchantsVo", merchantsVo);
		modelMap.addAttribute("listKind", listKind);

		return "yitiansystem/merchants/merchants_list";
	}

	/**
	 * 启用商家
	 * @param modelMap
	 * @param merchantCode
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("to_startAccout")
	public String startUp_supplier(ModelMap modelMap,String merchantCode, HttpServletRequest req) throws Exception {
		String result = "success";
		SystemmgtUser user = GetSessionUtil.getSystemUser(req);
		String userName = (user != null) ? user.getUsername() : "";
		YmcResult ymc =  merchantOperatorApi.startUpAccout(merchantCode,userName,sysconfigProperties.getBaseRoleIdArray(),req);
		if(!YmcResult.OK_CODE.equals(ymc.getCode())){
			result=ymc.getMessage() ;
		}
		return result;
	}
			
	/**
	 * 重用商家
	 * @param modelMap
	 * @param merchantCode
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("to_stopAccout")
	public String stop_supplier(ModelMap modelMap,String merchantCode, HttpServletRequest req) throws Exception {
		String result = "success";
		SystemmgtUser user = GetSessionUtil.getSystemUser(req);
		String userName = (user != null) ? user.getUsername() : "";
		YmcResult ymc =  merchantOperatorApi.stopAccout(merchantCode,userName);
		if(!YmcResult.OK_CODE.equals(ymc.getCode())){
			result = ymc.getMessage() ;
		} 
		return result;
	}
	/**
	 * 跳转到添加招商供应商页面 - new
	 * 
	 * @author luo.q 2015-07-01
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/to_add_supplier")
	public ModelAndView to_add_supplier(ModelMap model,String redisKey) throws Exception {
		// 成本帐套名称
		List<CostSetofBooks> costSetofBooksList = null;
		String modelName = "yitiansystem/merchants/supplier/add_information";
		try {
			costSetofBooksList = merchantServiceNew.getCostSetofBooksList();
		} catch (Exception e) {
			logger.error("未查到所有的成本帐套名称枚举，请检查接口！",e);
		}
		model.addAttribute("costSetofBooksList", costSetofBooksList);
		// 获取省市区第一级结果集数据
		List<Map<String, Object>> areaList = merchantsService.getAreaList();
		model.addAttribute("areaList", areaList);
		model.addAttribute("maxFileSize", merchantComponent.getMaxFileSize() + "");
		
		Supplier supplier = null;
		String contractNo = null;
		MerchantUser merchantUser = null;
		MerchantRejectedAddressVo rejectedAddress = null;
		MerchantSupplierExpand supplierExpand = null;
		SupplierContract supplierContract = null;
		ContactsFormVo contactsFormVo = null;// 构建5种联系人组成的FormVo
		// 为了本次缓存，建立redisKey(从草稿箱跳入添加页面的，则使用旧的redisKey)
		if( StringUtils.isEmpty(redisKey) ){
			
			String uuid = UUIDGenerator.getUUID();
			redisKey = DateUtil2.getCurrentDateTimeToStr()+"-"+uuid;
			contractNo = MerchantUtil.initContractNo(redisTemplate);
			
		}else{
			/** 从草稿箱点击编辑进入添加供应商页面    **/
			Map<String,Object>  resourceMap = MerchantUtil.getSupplierSegmentCache(CacheConstant.C_MERCHANT_NAME_KEY,redisKey, redisTemplateForSupplierCreate);
			if( null!=resourceMap ){
				// 1. 合同编号
				supplierContract = (SupplierContract)resourceMap.get(CacheConstant.KEY_SUPPLIER_CONTRACT);
				if( null!=supplierContract && StringUtils.isNotEmpty(supplierContract.getContractNo() ) ){
					contractNo = supplierContract.getContractNo();
				}else{
					contractNo = MerchantUtil.initContractNo(redisTemplate);
				}
				
				rejectedAddress = (MerchantRejectedAddressVo)resourceMap.get(CacheConstant.KEY_REJECT_ADDRESS);
				Object supplierObject = resourceMap.get(CacheConstant.KEY_SUPPLIER);
				if( null!=supplierObject && supplierObject instanceof Supplier ){
					supplier = (Supplier) supplierObject;
				}else if(null!=supplierObject && supplierObject instanceof Map ){
					HashMap<String,Object> tempMap = (HashMap<String,Object>)supplierObject;
					supplier = MerchantUtil.rebuildSupplierVoFromMap(tempMap);
					//临时方案
					if(tempMap != null){
						logger.warn("线上商家草稿名称问题Map： supplier:"+tempMap.get("supplier") +" simpleName:"+tempMap.get("simpleName"));
					}
					if(supplier != null){
						logger.warn("线上商家草稿名称问题Supplier： supplier:"+supplier.getSupplier() +" simpleName:"+supplier.getSimpleName());
					}
					if(supplier != null && StringUtils.isBlank(supplier.getSupplier())){
						Object strObj = resourceMap.get(CacheConstant.KEY_SUPPLIER_NAME);
						if(strObj != null){
							String supplierName = (String)strObj;
						    supplier.setSupplier(supplierName);
						}
					}
				}
				/*else if(null!=supplierObject && supplierObject instanceof SupplierVo ){
					SupplierVo supplierVo = (SupplierVo)supplierObject;
					supplier = MerchantUtil.rebuildSupplierFromRedisObject(supplierVo);
				}*/// 由于两个VO有同名不同类型的属性暂时不使用
				
				merchantUser = (MerchantUser)resourceMap.get(CacheConstant.KEY_MERCHANT_USER);
				supplierExpand = (MerchantSupplierExpand)resourceMap.get(CacheConstant.KEY_SUPPLIER_EXPAND);
				contactsFormVo = (ContactsFormVo)resourceMap.get(CacheConstant.KEY_CONTACTS_FORM);
				AttachmentFormVo attachmentFormVo = (AttachmentFormVo)resourceMap.get(CacheConstant.KEY_ATTACHMENT_FORM);
				if(attachmentFormVo != null){
					String brandStrs = attachmentFormVo.getBankNoNameHidden();    			
					List<String> brandNos = null;
					String bankNoHidden = attachmentFormVo.getBankNoHidden();
					if(StringUtils.isNotBlank(bankNoHidden)){
						brandNos = Arrays.asList(bankNoHidden.split(";"));
					}
					List<String> brandStructs = null;
					String catNameHidden = attachmentFormVo.getCatNameHidden();
					if(StringUtils.isNotBlank(catNameHidden)){
						brandStructs = Arrays.asList(catNameHidden.split("_"));
					}   			
					if(brandNos != null && brandStructs != null){
						List<Category> treeModes = merchantComponent.getCategoryTreeMode(brandNos);    						
						merchantComponent.setTreeModesCheckStatus(treeModes, brandStructs);    			
						model.addAttribute("treeModes", treeModes);
					}
					model.addAttribute("brandStrs", brandStrs);
				}
			}else{
				logger.error("该商家草稿已失效！redisKey："+redisKey);
			}
			
			modelName = "yitiansystem/merchants/supplier/edit_draft_information";//
		}
		model.addAttribute("supplier", supplier);
		model.addAttribute("supplierExpand", supplierExpand);
		model.addAttribute("merchantUser", merchantUser); 
		model.put("supplierContract", supplierContract);
		model.addAttribute("rejectedAddress", rejectedAddress);
		model.addAttribute("contactsFormVo", contactsFormVo);
		model.put("contractNo", contractNo);
		model.addAttribute("redisKey", redisKey);
		
		return new ModelAndView(modelName, model);
	
	}

	/**
	 * 草稿列表
	 * @param query
	 * @param modelMap
	 * @param merchantsVo
	 * @return
	 */
	@RequestMapping("draft_supplier_List")
	public String toDraftSupplierList(Query query, ModelMap modelMap, MerchantsVo merchantsVo) {
		int start = 0;
		int count = 0;
		int pageNo = query.getPage();
		int pageSize = query.getPageSize();
		int pageCount = 0;
		PageFinder<SupplierVo> pageFinder = new PageFinder<SupplierVo>(pageNo,pageSize,count);
		List<SupplierVo> list = MerchantUtil.queryAllSupplierSegmentCache(CacheConstant.C_MERCHANT_NAME_KEY,merchantsVo, redisTemplateForSupplierCreate);
		if( null!=list && list.size()>0 ){
			count = list.size();
			if( count%pageSize>0 ){
				pageCount = count /pageSize +1;
			}else{
				pageCount = count /pageSize ;
			}
			
			pageFinder.setPageCount(pageCount);
			pageFinder.setRowCount(count);
			Collections.sort(list, new DateComparator() );
		
			List<SupplierVo> resultList = new ArrayList<SupplierVo>();
		
			/**分页算法**/
			start = (pageNo-1)*pageSize;
			if( start<count){
				for(int i=start;i<(start+pageSize) && i<count;i++ ){
					resultList.add(list.get(i));
				}
				pageFinder.setData(resultList);
			}else if(start<count){
				resultList = null;
			}
		}
		modelMap.addAttribute("pageFinder", pageFinder);
		modelMap.addAttribute("merchantsVo", merchantsVo);
		return "yitiansystem/merchants/merchants_draft_list";
	}
	
	/**
	 * 商家创建时，存草稿到缓存，更新草稿
	 * @param modelMap
	 * @param supplier
	 * @param supplierId
	 * @param supplierExpand
	 * @param supplierContract
	 * @param merchantUser
	 * @param rejectedAddress
	 * @param contactsFormVo
	 * @param attachmentFormVo
	 * @param isPendingApproval
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("save_supplier_segment")
	public String saveSupplierSegment(ModelMap modelMap, Supplier supplier, String redisKey,
			MerchantSupplierExpand supplierExpand, SupplierContract supplierContract, MerchantUser merchantUser,
			MerchantRejectedAddressVo rejectedAddress, ContactsFormVo contactsFormVo, 
			AttachmentFormVo attachmentFormVo, HttpServletRequest req) {
		
		SystemmgtUser user = GetSessionUtil.getSystemUser(req);
		String userName = (user != null) ? user.getUsername() : "";
		String supplierName = supplier.getSupplier().trim();
		HashMap<String,Object> resourceMap = new HashMap<String,Object>();
		//  商家VO构建
		Date curDate = DateUtil2.getCurrentDateTime();
		supplier.setUpdateUser( userName );// 修改人
		supplier.setUpdateDate( curDate );// 修改时间
		// Important!
		supplierContractService.updateSupplierContractAccordingFormVo(attachmentFormVo, supplierContract);//
	
		resourceMap.put(CacheConstant.KEY_SUPPLIER_NAME,supplierName);
		resourceMap.put(CacheConstant.KEY_UPDATE_USER,userName);
		resourceMap.put(CacheConstant.KEY_UPDATE_TIME, curDate);
		Map<String,Object> map;
		try {
			map = MerchantUtil.buildMapFromSupplierVO(supplier);
		} catch (Exception e1) {
			logger.error("新建供应商-保存草稿到缓存发生(" + supplierName + ")异常：", e1);
			return curDate.toString()+" 保存草稿失败-商家VO转换map缓存发生异常，请与技术支持人员联系！";
		}
		resourceMap.put(CacheConstant.KEY_SUPPLIER, map);
		// step 2- rejectedAddress VO 构建
		resourceMap.put(CacheConstant.KEY_REJECT_ADDRESS, rejectedAddress);
		// step3 - contactVoList构建
		resourceMap.put(CacheConstant.KEY_CONTACTS_FORM, contactsFormVo);
		// step4 - merchantUser
		resourceMap.put(CacheConstant.KEY_MERCHANT_USER, merchantUser);
		// step5 - supplierContract
		resourceMap.put(CacheConstant.KEY_SUPPLIER_CONTRACT, supplierContract);
		// step6 - supplierExpand
		resourceMap.put(CacheConstant.KEY_SUPPLIER_EXPAND, supplierExpand);
		// step7 - attachmentFormVo
		resourceMap.put(CacheConstant.KEY_ATTACHMENT_FORM, attachmentFormVo);
		
		try {
			MerchantUtil.saveSupplierSegmentCache(CacheConstant.C_MERCHANT_NAME_KEY,redisKey, redisTemplateForSupplierCreate, resourceMap);
		} catch (Exception e) {
			logger.error("新建供应商-保存草稿到缓存发生(" + supplierName + ")异常：", e);
			return curDate.toString()+" 保存草稿失败！";
		}
		return curDate.toString()+" 成功保存草稿.";
	}
	
	/**
	 * 删除商家草稿
	 * @param modelMap
	 * @param redisKey
	 * @return
	 */
	@ResponseBody
	@RequestMapping("del_supplier_draft")
	public String delSupplierDraft (ModelMap modelMap, String redisKey){
		try {
			HashMap<String,Object> map = MerchantUtil.getSupplierSegmentCache(CacheConstant.C_MERCHANT_NAME_KEY, redisKey, redisTemplateForSupplierCreate);
			if( null!=map ){
				 MerchantUtil.removeFromSupplierSegmentCache(CacheConstant.C_MERCHANT_NAME_KEY, redisKey, redisTemplateForSupplierCreate);
			}
		} catch (Exception e) {
			String msg = "删除缓存中的草稿发生异常.";
			logger.error(msg,e);
			return msg;
		}
		return "success";
	}

	@ResponseBody
	@RequestMapping("get_trademark_detail_ajax")
	public String getTradeMarkDetailAjax (ModelMap modelMap, String redisKey){
		JSONObject result = new JSONObject();
		HashMap<String,Object> map = MerchantUtil.getSupplierSegmentCache(CacheConstant.C_MERCHANT_NAME_KEY, redisKey, redisTemplateForSupplierCreate);
		if( null!=map ){
		    SupplierContract supplierContract = (SupplierContract)map.get(CacheConstant.KEY_SUPPLIER_CONTRACT);
	    	result.put("trademarkList",supplierContract.getTrademarkList());
	      	
		}
		return  result.toString();
	}

	/**
	 * 保存招商供应商-创建-luo.q
	 * 
	 * @param modelMap
	 * @param supplierSp
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("add_supplier")
	public String add_supplier(ModelMap modelMap, Supplier supplier, String redisKey,
			MerchantSupplierExpand supplierExpand, SupplierContract supplierContract, MerchantUser merchantUser,
			MerchantRejectedAddressVo rejectedAddress, ContactsFormVo contactsFormVo, 
			AttachmentFormVo attachmentFormVo, String isPendingApproval, HttpServletRequest req) {

		SystemmgtUser user = GetSessionUtil.getSystemUser(req);
		String userName = (user != null) ? user.getUsername() : "";
		String supplierName = supplier.getSupplier();
		// step 1- 商家VO构建
		String uuid = UUIDGenerator.getUUID();
		String supplierCode = CodeGenerate.getSupplierCode();
		String supplierId = uuid;
		supplier.setId(supplierId);
		supplier.setUpdateTimestamp(System.currentTimeMillis());// 时间戳
		supplier.setSupplierCode(supplierCode);// 商家编号
		supplier.setCreator(userName);// 创建人
		supplier.setUpdateUser(userName);// 修改人
		supplier.setCreatorname(userName);// 创建人
		supplier.setUpdateUsername(userName);// 修改人
		supplier.setUpdateDate(DateUtil2.getCurrentDateTime());// 修改时间
																// 添加时默认为创建时间
		String setOfBooksName = merchantComponent.getNameBySetOfBooksCode( supplier.getSetOfBooksCode() );
		supplier.setSetOfBooksName(setOfBooksName);// 设置成本套账名称
		supplier.setDeleteFlag(MerchantConstant.NOT_DELETED);// 未删除标志
		supplier.setSupplierType(MerchantConstant.SUPPLIER_TYPE_MERCHANT_ZH);
		supplier.setSupplierTypeCode(MerchantConstant.SUPPLIER_TYPE_MERCHANT + "");
		
		// 判断是否提交审核
		if (StringUtils.isBlank(isPendingApproval)) {
			supplier.setIsValid(MerchantConstant.MERCHANT_STATUS_NEW);// 供应商状态
			supplierContract.setStatus( MerchantConstant.CONTRACT_STATUS_NEW );
		} else {
			supplier.setIsValid(MerchantConstant.MERCHANT_STATUS_BUSINESS_AUDITING);// 供应商状态
			supplierContract.setStatus( MerchantConstant.CONTRACT_STATUS_BUSINESS_AUDITING );
		}

		// step 2- rejectedAddress VO 构建
		String uuid2 = UUIDGenerator.getUUID();
		rejectedAddress.setId(uuid2);
		rejectedAddress.setSupplierName(supplierName);
		rejectedAddress.setCreaterPerson(userName);
		rejectedAddress.setCreaterTime(DateUtil2.getCurrentDateTimeToStr2());
		rejectedAddress.setSupplierCode(supplierCode);
		// step3 - contactVoList构建
		contactsFormVo.setSupplier(supplierName);
		contactsFormVo.setSupplierCode(supplierCode);
		contactsFormVo.setSupplyId(supplierId);
		List<SupplierContact> contactVoList = contactsFormVo.generateContactVoList();

		// step4 - merchantUser
		String uuid4 = UUIDGenerator.getUUID();
		merchantUser.setId(uuid4);
		merchantUser.setCreater(userName);
		merchantUser.setUserName("");// 非空项
		merchantUser.setStatus(MerchantConstant.ACCOUNT_LOCK);// 非空项
		merchantUser.setCreateTime(DateUtil2.getCurrentDateTimeToStr2());
		merchantUser.setMerchantCode(supplierCode);// 非空项
		merchantUser.setDeleteFlag(MerchantConstant.NOT_DELETED);// 非空项
		merchantUser.setIsAdministrator(MerchantConstant.YES);
		merchantUser.setIsYougouAdmin(MerchantConstant.NO );// 非空项
		// 对密码进行MD5加密
		String password = Md5Encrypt.md5(merchantUser.getPassword());
		merchantUser.setPassword(password);
		// step5 - supplierContract
		supplierContract.setSupplierId(supplierId);
		supplierContract.setBindStatus(MerchantConstant.CONTRACT_BIND);
		supplierContract.setSupplierCode(supplierCode);
		supplierContract.setSupplier(supplierName);

		// step6 -
		String uuid6 = UUIDGenerator.getUUID();
		supplierExpand.setId(uuid6);
		supplierExpand.setSupplierId(supplierId);
		supplierExpand.setMerchantCode(supplierCode);
		supplierExpand.setUptateTime(DateUtil2.getCurrentDateTimeToStr2());
		supplierExpand.setDeleteFlag(MerchantConstant.NOT_DELETED);
		int dateDiff1 = DateUtil2.getDiffDateFromToday(supplierExpand.getBusinessValidityEnd());
		supplierExpand.setBusinessRemainingDays(dateDiff1);
		int dateDiff2 = DateUtil2.getDiffDateFromToday(supplierExpand.getInstitutionalValidityEnd());
		supplierExpand.setInstitutionalRemainingDays(dateDiff2);
		supplierExpand.setIsOld( MerchantConstant.NEW_MERCHANT_FLAG);
		//

		// step 7 为供应商添加图片空间默认商品目录
		MerchantPictureCatalog pictureCatalog = new MerchantPictureCatalog();
		pictureCatalog.setCatalogName("默认目录");
		pictureCatalog.setId(uuid);
		pictureCatalog.setMerchantCode(supplier.getSupplierCode());
		pictureCatalog.setParentId("0");
		pictureCatalog.setShopId(null);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplier", supplier);
		params.put("supplierId", supplierId);
		params.put("supplierContract", supplierContract);
		params.put("merchantUser", merchantUser);
		params.put("rejectedAddress", rejectedAddress);
		params.put("contactVoList", contactVoList);
		params.put("attachmentFormVo", attachmentFormVo);
		params.put("supplierExpand", supplierExpand);
		params.put("pictureCatalog", pictureCatalog);
		// 保存
		try {
			merchantServiceNew.saveSupplierMerchant(params);
			
			//保存品类授权
			SupplierVo supplierVo = new SupplierVo();
			supplierVo.setId(supplierId);
			merchantServiceNew.updateMerchantsBankAndCat( supplierVo, attachmentFormVo.getBankNoHidden(), attachmentFormVo.getCatNameHidden());
			
			// 删除草稿缓存
			Map<String,Object>  resourceMap = MerchantUtil.getSupplierSegmentCache(CacheConstant.C_MERCHANT_NAME_KEY,redisKey, redisTemplateForSupplierCreate);
			if( null!=resourceMap ){
				MerchantUtil.removeFromSupplierSegmentCache(CacheConstant.C_MERCHANT_NAME_KEY,redisKey, redisTemplateForSupplierCreate);
			}
			
			try{	
	    		//记录日志-供应商状态流转日志 
	        	MerchantContractUpdateHistory updateHistory = new MerchantContractUpdateHistory();
	        	updateHistory.setContractNo("");
	        	updateHistory.setSupplierId(supplier.getId());
	        	updateHistory.setOperator(userName);
	        	updateHistory.setProcessing(OperationType.ADD_MERCHANT.getDescription());
	        	updateHistory.setRemark("");
	        	updateHistory.setType(MerchantConstant.LOG_FOR_MERCHANT);
	        	updateHistory.setUpdateAfter(MerchantStatusEnum.getValue(supplier.getIsValid()));
	        	updateHistory.setUpdateBefore("");
	        	updateHistory.setUpdateField("isValid");
	        	
	        	YmcResult ymcResult = merchantOperatorApi.addContractLog(updateHistory);
	        	if(YmcResult.ERROR_CODE.equals(ymcResult.getCode())){
	        		logger.error("新建供应商(" + supplierName + ")增加状态流转日志异常."+ymcResult.getMessage());
	        	}
	        	
	        	//记录日志-供应商的合同状态流转日志 
	        	MerchantContractUpdateHistory updateHistoryForContract = new MerchantContractUpdateHistory();
	        	updateHistoryForContract.setContractNo( supplierContract.getContractNo() );
	        	updateHistoryForContract.setSupplierId(supplier.getId());
	        	updateHistoryForContract.setOperator(userName);
	        	updateHistoryForContract.setProcessing(OperationType.ADD_CONTRACT.getDescription());
	        	updateHistoryForContract.setRemark("");
	        	updateHistoryForContract.setType(MerchantConstant.LOG_FOR_CONTRACT);
	        	updateHistoryForContract.setUpdateAfter( ContractStatusEnum.getValue(supplierContract.getStatus() ) );
	        	updateHistoryForContract.setUpdateBefore("");
	        	updateHistoryForContract.setUpdateField("status");
	        	
	        	YmcResult ymcResultForContract = merchantOperatorApi.addContractLog(updateHistoryForContract);
	        	if(YmcResult.ERROR_CODE.equals(ymcResultForContract.getCode())){
	        		logger.error("新建供应商的合同(" + supplierContract.getContractNo() + ")增加状态流转日志异常."+ymcResultForContract.getMessage());
	        	}
	        	
	        	// 记录操作日志
	        	MerchantOperationLog log = new MerchantOperationLog();
				log.setType( MerchantConstant.LOG_FOR_MERCHANT );
				log.setOperator(user.getUsername());
				log.setContractNo("");
				log.setOperationNotes("创建新的商家");
				log.setOperationType(OperationType.ADD_MERCHANT.getDescription());
				log.setMerchantCode( supplier.getSupplierCode() );
				merchantServiceNew.insertMerchantLog(log);
				
				// 记录操作日志-合同
	        	MerchantOperationLog logForContract = new MerchantOperationLog();
				logForContract.setType( MerchantConstant.LOG_FOR_CONTRACT );
				logForContract.setOperator(user.getUsername());
				logForContract.setContractNo( supplierContract.getContractNo() );
				logForContract.setOperationNotes("创建新的供应商合同");
				logForContract.setOperationType(OperationType.ADD_CONTRACT.getDescription());
				logForContract.setMerchantCode( supplier.getSupplierCode() );
				merchantServiceNew.insertMerchantLog(logForContract);
	        	
	    	} catch (Exception e) {
				logger.error("新建供应商(" + supplierName + ")增加日志异常.",e);
			}
	    	
		} catch (Exception e) {
			logger.error("新建供应商(" + supplierName + ")异常：", e);
			return "【商家名称：" + supplierName + "】创建失败！"+e.getMessage();
		}

		return "success";

	}

	// 编辑商家页面-招商商家 Add by luo.q
	@RequestMapping("to_update_supplier_merchant")
	public String to_update_supplier_merchant(ModelMap modelMap, String id, String editType) throws Exception {
		try{
		
		// 根据id查询商家基本信息
		SupplierVo vo = merchantServiceNew.getSupplierVoById(id);
		modelMap.addAttribute("supplier", vo);
		
		// 查询授权品牌.[brandNo;brandName_brandNo;brandName...]
		String brandStrs = merchantsService.queryAuthorizationBrandBysupplierId(id);
		List<String> brandNos = merchantsService.getAuthorizationBrandNos(id);
		//h6rM;swsport_a6ta;布来亚克_02q2;速比涛
		//[h6rM, a6ta, 02q2]
		// 品牌分类关系集合
		List<String> brandStructs = merchantsService.queryAuthorizationBrandCatBysupplierId(id);
		//[02q2;11-12-28, h6rM;18-15-11, a6ta;15-13-43, h6rM;18-12-12, 02q2;11-12-26, 02q2;11-14-20, a6ta;15-11-21, a6ta;15-11-22, 02q2;11-11-11, 02q2;11-11-24, 02q2;11-12-22, a6ta;15-14-15, h6rM;18-32-10, h6rM;18-24-10, a6ta;15-10-16, 02q2;11-13-13, 02q2;11-11-14, a6ta;15-14-13, a6ta;15-12-26, a6ta;15-11-23, h6rM;18-22-10, h6rM;18-34-10, a6ta;15-13-29, 02q2;11-13-14, a6ta;15-14-14, 02q2;11-12-11, a6ta;15-14-11, a6ta;15-13-55, 02q2;11-12-30, a6ta;15-14-16, a6ta;15-13-41, a6ta;15-13-30, 02q2;11-11-17, 02q2;11-14-21, a6ta;15-11-28, a6ta;15-11-12, a6ta;15-13-51, a6ta;15-10-11, 02q2;11-11-25, a6ta;15-13-59, h6rM;18-11-19, 02q2;11-12-13, a6ta;15-13-45, a6ta;15-13-50, a6ta;15-14-17, h6rM;18-35-10, a6ta;15-12-27, h6rM;18-12-11, a6ta;15-11-25, 02q2;11-13-18, a6ta;15-11-17, a6ta;15-13-36, a6ta;15-12-13, 02q2;11-11-26, 02q2;11-12-18, h6rM;18-30-10, a6ta;15-11-15, 02q2;11-11-20, h6rM;18-11-18, a6ta;15-12-17, a6ta;15-12-15, a6ta;15-11-30, h6rM;18-19-10, a6ta;15-13-35, a6ta;15-13-37, a6ta;15-13-47, 02q2;11-12-25, a6ta;15-13-52, a6ta;15-13-48, a6ta;15-11-13, a6ta;15-13-53, h6rM;18-29-10, h6rM;18-18-10, 02q2;11-13-16, a6ta;15-13-57, h6rM;18-37-10, a6ta;15-13-39, a6ta;15-12-12, a6ta;15-12-11, a6ta;15-12-19, 02q2;11-11-21, 02q2;11-11-27, a6ta;15-13-49, 02q2;11-12-27, a6ta;15-11-19, a6ta;15-12-16, h6rM;18-16-10, 02q2;11-12-29, h6rM;18-11-20, a6ta;15-13-34, a6ta;15-13-44, h6rM;18-21-10, 02q2;11-11-12, a6ta;15-13-15, 02q2;11-12-21, 02q2;11-14-18, a6ta;15-13-56, a6ta;15-10-13, a6ta;15-12-21, a6ta;15-13-13, h6rM;18-11-26, h6rM;18-11-24, a6ta;15-11-26, a6ta;15-12-23, h6rM;18-11-11, a6ta;15-11-24, h6rM;18-11-13, a6ta;15-12-18, h6rM;18-27-10, a6ta;15-11-20, 02q2;11-14-16, h6rM;18-12-16, a6ta;15-14-12, 02q2;11-13-11, 02q2;11-14-17, h6rM;18-11-14, a6ta;15-12-20, a6ta;15-13-46, a6ta;15-13-23, a6ta;15-13-40, a6ta;15-12-25, 02q2;11-12-14, a6ta;15-11-29, a6ta;15-10-18, a6ta;15-11-16, h6rM;18-26-10, a6ta;15-10-19, h6rM;18-28-10, 02q2;11-12-31, 02q2;11-11-23, a6ta;15-13-54, a6ta;15-11-27, h6rM;18-17-10, 02q2;11-13-12, a6ta;15-13-14, h6rM;18-11-12, h6rM;18-12-13, a6ta;15-10-17, h6rM;18-23-10, h6rM;18-25-10, a6ta;15-13-32, h6rM;18-15-15, h6rM;18-36-10, h6rM;18-15-14, 02q2;11-14-11, a6ta;15-11-14, a6ta;15-12-14, 02q2;11-14-14, a6ta;15-10-15, h6rM;18-11-15, a6ta;15-10-20, 02q2;11-11-16, 02q2;11-13-17, h6rM;18-12-15, h6rM;18-15-10, 02q2;11-12-20, 02q2;11-14-13, 02q2;11-12-19, a6ta;15-10-14, h6rM;18-11-16, 02q2;11-12-12, h6rM;18-20-10, 02q2;11-14-12, h6rM;18-11-23, h6rM;18-11-21, 02q2;11-11-22, h6rM;18-12-14, h6rM;18-11-22, a6ta;15-13-58, 02q2;11-12-17, 02q2;11-14-19, h6rM;18-15-13, 02q2;11-11-29, 02q2;11-12-32, h6rM;18-11-17, a6ta;15-13-42, h6rM;18-11-25, h6rM;18-33-10, a6ta;15-13-33, a6ta;15-11-18, 02q2;11-11-19, a6ta;15-12-24, h6rM;18-31-10, 02q2;11-13-15, h6rM;18-38-10, a6ta;15-11-11, 02q2;11-11-18, 02q2;11-12-15, a6ta;15-10-12, 02q2;11-11-13]
		List<Category> treeModes = merchantComponent.getCategoryTreeMode(brandNos);
					
		merchantComponent.setTreeModesCheckStatus(treeModes, brandStructs);
		
		modelMap.addAttribute("brandStrs", brandStrs);
		modelMap.addAttribute("treeModes", treeModes);

		// 商家扩展表信息
		MerchantSupplierExpand supplierExpand = merchantServiceNew.getSupplierExpandVoById(id);
		modelMap.addAttribute("supplierExpand", supplierExpand);

		// 财务成本套帐
		List<CostSetofBooks> costSetofBooksList = null;
		try {
			costSetofBooksList = merchantServiceNew.getCostSetofBooksList();
		} catch (Exception e) {
			logger.error("未查到所有的成本帐套名称枚举，请检查接口！",e);
		}
		modelMap.addAttribute("costSetofBooksList", costSetofBooksList);

		// 是招商供应商才查询
		MerchantUser merchantUser = null;
		MerchantRejectedAddressVo rejectedAddress = null;
		if (null != vo) {
			// 根据商家编号查询商家登录信息
			merchantUser = merchantServiceNew.getMerchantsBySuppliceCode(vo.getSupplierCode());

			rejectedAddress = merchantServiceNew.getRejectedAddressBySupplierCode(vo.getSupplierCode());

			if (null != rejectedAddress) {// 地址拆分 供页面使用！
				rejectedAddress.initAddressForForm();
			}
		}

		modelMap.addAttribute("merchantUser", merchantUser);

		// 获取省市区第一级结果集数据
		List<Map<String, Object>> areaList = merchantsService.getAreaList();

		modelMap.addAttribute("areaList", areaList);
		SupplierContract supplierContract = supplierContractService.buildSupplierContractSet(id);
		modelMap.put("supplierContract", supplierContract);

		modelMap.addAttribute("maxFileSize", merchantComponent.getMaxFileSize() + "");

		modelMap.addAttribute("rejectedAddress", rejectedAddress);

		// 构建5种联系人组成的FormVo
		ContactsFormVo contactsFormVo = buildContactsFormVo(id);

		modelMap.addAttribute("contactsFormVo", contactsFormVo);
		}catch(Exception e){
			logger.error("编辑商家异常",e);
		}
		modelMap.put("editType", editType);
		return "yitiansystem/merchants/supplier/update_information";
	}

	private ContactsFormVo buildContactsFormVo(String supplierId) {
		ContactsFormVo contactsFormVo = new ContactsFormVo();
		contactsFormVo.setSupplyId(supplierId);
		List<ContactsVo> contactList = merchantServiceNew.getContactsBySupplierId(supplierId);
		contactsFormVo.setContactList(contactList);
		contactsFormVo.initFormVoByContactList();// 初始化各字段

		return contactsFormVo;
	}

	/**
	 * 保存招商供应商-修改-luo.q
	 * 
	 * @param modelMap
	 * @param supplier
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping("update_supplier_merchant")
	public String update_supplier(ModelMap modelMap, Supplier supplier,
			MerchantSupplierExpand supplierExpand, SupplierContract supplierContract, MerchantUser merchantUser,
			MerchantRejectedAddressVo rejectedAddress, ContactsFormVo contactsFormVo,
			AttachmentFormVo attachmentFormVo, String isPendingApproval, 
			String edit,HttpServletRequest req) {

		if (StringUtils.isNotBlank(supplierContract.getSupplierId())) {
			SystemmgtUser user = GetSessionUtil.getSystemUser(req);
			String userName = (user != null) ? user.getUsername() : "";
			String supplierName = supplier.getSupplier();
			Integer originalStatus = supplier.getIsValid();
			// 预处理 --id的处理
			supplier.setId(supplierContract.getSupplierId());
			supplierExpand.setId(supplierExpand.getSupplierExpandId());
			supplierContract.setId(supplierContract.getContractId());
			
			if( StringUtils.isNotBlank(merchantUser.getMerchantUserId()) ){// 后续处理为首次添加的情况 
				merchantUser.setId(merchantUser.getMerchantUserId());
				merchantUser.setPassword(null);
			}else{
				String uuid4 = UUIDGenerator.getUUID();
				merchantUser.setId(uuid4);
				merchantUser.setCreater(userName);
				merchantUser.setUserName("");// 非空项
				merchantUser.setStatus(MerchantConstant.ACCOUNT_LOCK);
				merchantUser.setCreateTime(DateUtil2.getCurrentDateTimeToStr2());
				merchantUser.setMerchantCode(supplier.getSupplierCode());
				merchantUser.setDeleteFlag(MerchantConstant.NOT_DELETED);
				merchantUser.setIsAdministrator(MerchantConstant.YES);
				merchantUser.setIsYougouAdmin(MerchantConstant.NO );//
				// 对密码进行MD5加密
				String password = Md5Encrypt.md5(merchantUser.getPassword());
				merchantUser.setPassword(password);
			}
			
			if( StringUtils.isNotBlank(rejectedAddress.getRejectedAddressId())){// 后续处理为首次添加的情况 
				rejectedAddress.setId(rejectedAddress.getRejectedAddressId());
			}else{
				rejectedAddress.setId(UUIDGenerator.getUUID());
				rejectedAddress.setSupplierName(supplierName);
				rejectedAddress.setCreaterPerson(userName);
				rejectedAddress.setCreaterTime(DateUtil2.getCurrentDateTimeToStr2());
				rejectedAddress.setSupplierCode(supplier.getSupplierCode());
			}
			// 查出 修改之前的合同
			SupplierContract supplierContractBeforeUpdated = supplierContractService.getSupplierContractById( supplierContract.getContractId() );
			String originalStatusForContract = supplierContractBeforeUpdated.getStatus();
			if(StringUtils.isBlank(originalStatusForContract)){
				originalStatusForContract = MerchantConstant.CONTRACT_STATUS_NEW;
			}
			// step 1- 商家VO构建
			String supplierCode = supplier.getSupplierCode();
			supplier.setUpdateTimestamp(System.currentTimeMillis());// 时间戳
			supplier.setUpdateUser(userName);// 修改人
			supplier.setUpdateUsername(userName);// 修改人
			supplier.setUpdateDate(DateUtil2.getCurrentDateTime());// 修改时间
			String setOfBooksName = merchantComponent.getNameBySetOfBooksCode( supplier.getSetOfBooksCode() );
			supplier.setSetOfBooksName(setOfBooksName);// 设置成本套账名称
			// 供应商状态--若提交审核，状态进阶
			// 判断是否提交审核
			if (StringUtils.isNotBlank(isPendingApproval)) {
				supplier.setIsValid(MerchantUtil.getNextStatus(supplier.getIsValid()));// 供应商状态
				supplierContract.setStatus( MerchantUtil.getNextStatus(supplierContract.getStatus()) );
				
				//判断是否需要调财务接口创建或修改收款单
				// 1-保证金收款单
				boolean result1 = true;
				String depositStatus = supplierContractBeforeUpdated.getDepositStatus();
				BigDecimal depositBeforeUpdated = supplierContractBeforeUpdated.getDeposit();
				if( MerchantConstant.CONTRACT_STATUS_FINANCE_REFUSED.equals(originalStatusForContract) &&
						supplierContract.getDeposit().compareTo(BigDecimal.ZERO) > 0 && 
							(null == depositBeforeUpdated || supplierContract.getDeposit().compareTo(depositBeforeUpdated)!=0 )
						){
					if( null == depositBeforeUpdated || depositBeforeUpdated.compareTo(BigDecimal.ZERO) == 0){// 未创建过收款单
						result1 = supplierContractService.createFinanceDepositCashBill(supplierContract,userName,null);
					}else{	//创建过收款单
						if( MerchantConstant.FEE_STATUS_WAIT_PAY.equals( depositStatus ) ){
							result1 = supplierContractService.updateFinanceDepositCashBill(supplierContract, userName, supplierContractBeforeUpdated, null);
						}
					}
					
				}
				//2-平台使用费收款单
				//判断是否需要调财务接口创建或修改收款单
				boolean result2 = true;
				String useFeeStatus = supplierContractBeforeUpdated.getUseFeeStatus();
				BigDecimal useFeeBeforeUpdated = supplierContractBeforeUpdated.getUseFee();
				if( MerchantConstant.CONTRACT_STATUS_FINANCE_REFUSED.equals(originalStatusForContract) &&
						supplierContract.getUseFee().compareTo(BigDecimal.ZERO) > 0 && 
							(null == useFeeBeforeUpdated || supplierContract.getUseFee().compareTo(useFeeBeforeUpdated)!=0 )
						){
					if( null == useFeeBeforeUpdated || useFeeBeforeUpdated.compareTo(BigDecimal.ZERO) == 0){// 未创建过收款单
						result2 =supplierContractService.createFinanceUseFeeCashBill(supplierContract,userName);
					}else{	//创建过收款单
						if( MerchantConstant.FEE_STATUS_WAIT_PAY.equals( useFeeStatus ) ){
							result2 = supplierContractService.updateFinanceUseFeeCashBill(supplierContract, userName, supplierContractBeforeUpdated);
						}
					}
					
				}
				
				if(!result1 || !result2 ){
	  				StringBuffer msg = new StringBuffer("调财务接口操作收款单失败:");
	  				if(!result1){
	  					msg.append(" 保证金 ");
	  				}
	  				if(!result2){
	  					msg.append(" 平台使用费 ");
	  				}
	  				msg.append("的收款单未成功更新");
	  				return msg.toString();
	  			}
			}

			// step3 - contactVoList构建
			contactsFormVo.setSupplier(supplierName);
			contactsFormVo.setSupplierCode(supplierCode);
			contactsFormVo.setSupplyId(supplierContract.getSupplierId());
			List<SupplierContact> contactVoList = contactsFormVo.generateContactVoList();

			// step4 - merchantUser 见上面
			// step5 - supplierContract

			// step6 - 扩展表
			supplierExpand.setUptateTime(DateUtil2.getCurrentDateTimeToStr2());
			int dateDiff1 = DateUtil2.getDiffDateFromToday(supplierExpand.getBusinessValidityEnd());
			supplierExpand.setBusinessRemainingDays(dateDiff1);
			int dateDiff2 = DateUtil2.getDiffDateFromToday(supplierExpand.getInstitutionalValidityEnd());
			supplierExpand.setInstitutionalRemainingDays(dateDiff2);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("supplier", supplier);
			params.put("supplierId", supplierContract.getSupplierId());
			params.put("supplierContract", supplierContract);
			params.put("merchantUser", merchantUser);
			params.put("rejectedAddress", rejectedAddress);
			params.put("contactVoList", contactVoList);
			params.put("attachmentFormVo", attachmentFormVo);
			params.put("supplierExpand", supplierExpand);
			// 保存
			try {
				merchantServiceNew.updateSupplierMerchant(params);
				
				//保存品类授权
				SupplierVo supplierVo = new SupplierVo();
				supplierVo.setId(supplier.getId());
				merchantServiceNew.updateMerchantsBankAndCat(supplierVo, attachmentFormVo.getBankNoHidden(), attachmentFormVo.getCatNameHidden());
				
				//恢复API授权
    			supplierContractService.recoverApiLicenceBySupplierId(supplierContract.getSupplierId());
				
				if( originalStatus!=supplier.getIsValid() ){
					try{	
			    		//记录日志-供应商状态流转日志 
			        	MerchantContractUpdateHistory updateHistory = new MerchantContractUpdateHistory();
			        	updateHistory.setContractNo("");
			        	updateHistory.setSupplierId(supplierContract.getSupplierId());
			        	updateHistory.setOperator(userName);
			        	updateHistory.setProcessing(OperationType.UPDATE_MERCHANT.getDescription());
			        	updateHistory.setRemark("");
			        	updateHistory.setType(MerchantConstant.LOG_FOR_MERCHANT);
			        	updateHistory.setUpdateAfter(MerchantStatusEnum.getValue(supplier.getIsValid()));
			        	updateHistory.setUpdateBefore(MerchantStatusEnum.getValue(originalStatus));
			        	updateHistory.setUpdateField("isValid");
			        	
			        	YmcResult ymcResult = merchantOperatorApi.addContractLog(updateHistory);
			        	if(YmcResult.ERROR_CODE.equals(ymcResult.getCode())){
			        		logger.error("修改供应商(" + supplierName + ")增加状态流转日志异常."+ymcResult.getMessage());
			        	}
			        	
			        	//记录日志-供应商的合同状态流转日志 
			        	MerchantContractUpdateHistory updateHistoryForContract = new MerchantContractUpdateHistory();
			        	updateHistoryForContract.setContractNo( supplierContract.getContractNo() );
			        	updateHistoryForContract.setSupplierId(supplier.getId());
			        	updateHistoryForContract.setOperator(userName);
			        	updateHistoryForContract.setProcessing(OperationType.ADD_CONTRACT.getDescription());
			        	updateHistoryForContract.setRemark("");
			        	updateHistoryForContract.setType(MerchantConstant.LOG_FOR_CONTRACT);
			        	updateHistoryForContract.setUpdateAfter( ContractStatusEnum.getValue(supplierContract.getStatus() ) );
			        	updateHistoryForContract.setUpdateBefore(ContractStatusEnum.getValue(originalStatusForContract ) );
			        	updateHistoryForContract.setUpdateField("status");
			        	
			        	YmcResult ymcResultForContract = merchantOperatorApi.addContractLog(updateHistoryForContract);
			        	if(YmcResult.ERROR_CODE.equals(ymcResultForContract.getCode())){
			        		logger.error("修改供应商的合同(" + supplierContract.getContractNo() + ")增加状态流转日志异常."+ymcResultForContract.getMessage());
			        	}
			        	
			    	} catch (Exception e) {
						logger.error("修改供应商(" + supplierName + ")增加状态流转日志异常.",e);
					}
				}
				// 添加供应商修改日志
				try {
					MerchantOperationLog log = new MerchantOperationLog();
					log.setType(MerchantConstant.LOG_FOR_MERCHANT);
					log.setOperator(userName);
					if(StringUtils.isNotBlank(edit) && edit.equals("F_EDIT")){
						log.setOperationNotes("财务修改商家");
					}else{
						log.setOperationNotes("修改商家");
					}
					log.setOperationType(OperationType.UPDATE_MERCHANT.getDescription());
					log.setMerchantCode(supplierCode);
					merchantServiceNew.insertMerchantLog(log);

				} catch (Exception e) {
					logger.error("修改供应商(" + supplierName + ")增加修改日志异常.",e);
				}

			} catch (Exception e) {
				logger.error("修改供应商(" + supplierName + ")异常：", e);
				return "【商家名称：" + supplierName + "】修改失败！"+e.getMessage();
			}

			return "success";

		} else {
			return "请选择已经存在的供应商进行修改！";
		}
	}

	/**
	 * 查看供应商--商家信息
	 * 
	 * @param modelMap
	 * @param id
	 * @param listKind
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("to_view_supplier_merchant")
	public String toViewSupplierMerchant(ModelMap modelMap, String id, String listKind) throws Exception {
		modelMap.addAttribute("listKind", listKind);
		// 根据id查询商家基本信息
		SupplierVo vo = merchantServiceNew.getSupplierVoById(id);
		modelMap.addAttribute("supplier", vo);

		// 商家扩展表信息
		MerchantSupplierExpand supplierExpand = merchantServiceNew.getSupplierExpandVoById(id);
		modelMap.addAttribute("supplierExpand", supplierExpand);		
		
		SupplierContract supplierContract = supplierContractService.getSupplierContractBySupplierId(id);
		modelMap.put("supplierContract", supplierContract);		
		//
		String merchantCode = vo.getSupplierCode();
		Map<String,String>  warehouseMap = null;
		String inventoryCode = "";
		String warehouseName = "";
		if( StringUtils.isNotEmpty(merchantCode) ){
			try {
				warehouseMap = warehouseCacheService.getWarehouseByMerchantCode(merchantCode);
				if( null!=warehouseMap && warehouseMap.size()>0 ){
					Iterator<String> keyIter = warehouseMap.keySet().iterator();
					inventoryCode = keyIter.next();
					warehouseName = warehouseMap.get(inventoryCode);
				}
			} catch (Exception e) {
				logger.error("调用WMS接口查询商家的仓库失败。",e);
			}
			if( StringUtils.isEmpty(inventoryCode) ){
				inventoryCode = vo.getInventoryCode();
				warehouseName = MerchantUtil.generateWarehouseName( vo.getSupplier() );
			}
		}
		
		modelMap.put("inventoryCode", inventoryCode);
		modelMap.put("warehouseName", warehouseName);
		
		return "yitiansystem/merchants/supplier/view_supplier_merchant";
	}

	/**
	 * 查看供应商--店铺信息
	 * 
	 * @param modelMap
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("to_view_supplier_shop")
	public String toViewSupplierShop(ModelMap modelMap, String id, String listKind) throws Exception {
		
		try{
		
		modelMap.addAttribute("listKind", listKind);
		// 根据id查询商家基本信息
		SupplierVo vo = merchantServiceNew.getSupplierVoById(id);
		modelMap.addAttribute("supplier", vo);

		// 是招商供应商才查询
		MerchantUser merchantUser = null;
		MerchantRejectedAddressVo rejectedAddress = null;
		if (null != vo) {
			// 根据商家编号查询商家登录信息
			merchantUser = merchantServiceNew.getMerchantsBySuppliceCode(vo.getSupplierCode());

			rejectedAddress = merchantServiceNew.getRejectedAddressBySupplierCode(vo.getSupplierCode());
		}

		modelMap.addAttribute("merchantUser", merchantUser);

		modelMap.addAttribute("rejectedAddress", rejectedAddress);

		// 构建5种联系人组成的FormVo
		ContactsFormVo contactsFormVo = buildContactsFormVo(id);

		modelMap.addAttribute("contactsFormVo", contactsFormVo);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查看商家店铺出现异常",e);
		}

		return "yitiansystem/merchants/supplier/view_supplier_shop";
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
		List<CostSetofBooks> costSetofBooksList = null;
		try {
			costSetofBooksList = merchantServiceNew.getAllCostSetofBooksList();
		} catch (Exception e) {
			logger.error("未查到所有的成本帐套名称枚举，请检查接口！",e);
		}
		modelMap.addAttribute("costSetofBooksList", costSetofBooksList);
    	// 根据id查询商家基本信息
		SupplierVo vo = merchantServiceNew.getSupplierVoById(id);
		modelMap.addAttribute("supplier", vo);
		
		// 品牌二级分类信息查询
		List<Cat> catList = merchantsService.buildBrandCatSecondLevelList(id);
		modelMap.addAttribute("catList",catList); 
					
		SupplierContract supplierContract = supplierContractService.getSupplierContractBySupplierId(id);
		modelMap.put("supplierContract", supplierContract);
//		modelMap.put("headPath", sysconfigProperties.getContractFtpPathRead());
		// 商家扩展表信息
		MerchantSupplierExpand supplierExpand = merchantServiceNew.getSupplierExpandVoById(id);
		modelMap.addAttribute("supplierExpand", supplierExpand);	
    	return "yitiansystem/merchants/supplier/view_supplier_contract";
   	}
    
    /**
     * 跳转到审批供应商页面
     * @param modelMap
     * @param id
     * @param listKind
     * @return
     * @throws Exception
     */
    @RequestMapping("to_audit_supplier")
   	public String toAuditSupplier(ModelMap modelMap, String id, String listKind) throws Exception {
    	modelMap.addAttribute("listKind", listKind);
    	modelMap.addAttribute("supplierId", id);
    	return "yitiansystem/merchants/supplier/audit_supplier";
    }
    
    /**
     * 校验业务审核-验证单据是否处于业务待审核状态
     * @param supplierId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("to_validate_business_audit_supplier")
    public String validateBusinessAuditSupplier(String supplierId, boolean isPass) throws Exception {
    	JSONObject result = new JSONObject();
    	SupplierVo vo = merchantServiceNew.getSupplierVoById(supplierId);
    	if(vo != null && vo.getIsValid() == MerchantConstant.MERCHANT_STATUS_BUSINESS_AUDITING){
    		String msg = "";
    		if(isPass){    			
    			// 商家扩展表信息
    			MerchantSupplierExpand supplierExpand = merchantServiceNew.getSupplierExpandVoById(supplierId);
    			if(supplierExpand == null){
    				msg += "未找到商家扩展数据，请重试！";
    			}else{
    				if(supplierExpand.getMarkRemainingDays() == null || supplierExpand.getMarkRemainingDays() <= 0){
    					msg += "商标授权已经过期，不能审核通过！<br/><br/>";
    				}
    				if(supplierExpand.getContractRemainingDays() == null || supplierExpand.getContractRemainingDays() <= 0){
    					msg += "合同已经过期，不能审核通过！";
    				}
    				/** 暂时不判断
	    			if(supplierExpand.getInstitutionalRemainingDays() == null || supplierExpand.getInstitutionalRemainingDays() <= 0){
	    				msg += "组织机构代码已经过期，不能审核通过！<br/><br/>";
	    			}
	    			if(supplierExpand.getBusinessRemainingDays() == null || supplierExpand.getBusinessRemainingDays() <= 0){
	    				msg += "营业执照已经过期，不能审核通过！<br/><br/>";
	    			}
    				 */
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
    		result.put("msg", "商家状态不是业务待审核！");
    	}
    	return result.toString();
    }
    
    /**
     * 校验业务审核-验证单据是否处于财务待审核状态，收款单是否已收款等
     * @param supplierId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("to_validate_finance_audit_supplier")
    public String validateFinanceAuditSupplier(String supplierId, boolean isPass) throws Exception {
    	JSONObject result = new JSONObject();
    	SupplierVo vo = merchantServiceNew.getSupplierVoById(supplierId);
    	if(vo != null){
    		if(vo.getIsValid() == MerchantConstant.MERCHANT_STATUS_BUSINESS_AUDITED){    		
    			String msg = "";
    			if(isPass){
	    			MerchantSupplierExpand supplierExpand = null;
	        		// 商家扩展表信息
	        		supplierExpand = merchantServiceNew.getSupplierExpandVoById(supplierId);
	        		if(supplierExpand == null){
	        			msg +=  "未找到商家扩展数据，请重试！";
	        		}else{    			
	        			if(supplierExpand.getMarkRemainingDays() == null || supplierExpand.getMarkRemainingDays() <= 0){
	        				msg += "商标授权已经过期，不能审核通过！<br/><br/>";
	        			}
	        			if(supplierExpand.getContractRemainingDays() == null || supplierExpand.getContractRemainingDays() <= 0){
	        				msg += "合同已经过期，不能审核通过！<br/><br/>";
	        			}
	        			/** 暂时不判断
	        			if(supplierExpand.getInstitutionalRemainingDays() == null || supplierExpand.getInstitutionalRemainingDays() <= 0){
	        				msg += "组织机构代码已经过期，不能审核通过！<br/><br/>";
	        			}
	        			if(supplierExpand.getBusinessRemainingDays() == null || supplierExpand.getBusinessRemainingDays() <= 0){
	        				msg += "营业执照已经过期，不能审核通过！<br/><br/>";
	        			}*/
	    	    		SupplierContract supplierContract = supplierContractService.getSupplierCurrentContract(supplierId);
	    	    		if(supplierContract != null){
	    	    			if(String.valueOf(MerchantConstant.YES).equals(supplierContract.getIsNeedDeposit()) 
	    	    					&& !MerchantConstant.FEE_STATUS_PAYED.equals(supplierContract.getDepositStatus())){
	    	    				msg += "保证金尚未收款，不能审核通过！<br/><br/>";
	    	    			}
	    	    			if(String.valueOf(MerchantConstant.YES).equals(supplierContract.getIsNeedUseFee()) 
	    	    					&& !MerchantConstant.FEE_STATUS_PAYED.equals(supplierContract.getUseFeeStatus())){
	    	    				msg += "平台使用费尚未收款，不能审核通过！";
	    	    			}
	    	    		}else{
	    	    			msg += "未找到供应商合同数据，请重试！";
	    	    		}
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
    			result.put("msg", "商家状态不是业务审核通过！");
    		}
    	}else{
    		result.put("result", "failure");
			result.put("msg", "未找到供应商数据，请重试！");
    	}
    	
    	return result.toString();
    }
    
    
    /**
     * 业务审核供应商
     * 
     * @param supplierId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("business_audit_supplier")
    public String businessAuditSupplier(String supplierId, boolean isPass, String remark, HttpServletRequest request) throws Exception {
    	JSONObject result = new JSONObject();
    	SupplierVo supplierVo = merchantServiceNew.getSupplierVoById(supplierId);
    	SystemmgtUser user = GetSessionUtil.getSystemUser(request);
    	Supplier supplier = new Supplier();
    	supplier.setId(supplierId);
		supplier.setUpdateTimestamp(System.currentTimeMillis());// 时间戳
		supplier.setUpdateUser(user != null ? user.getUsername() : null);// 修改人
		supplier.setUpdateUsername(user != null ? user.getUsername() : null);// 修改人
		supplier.setUpdateDate(new Date());// 修改时间 添加时默认为创建时间
		
		SupplierContract supplierContract = supplierContractService.getSupplierCurrentContract(supplierId);
		supplierContract.setSupplierCode(supplierVo.getSupplierCode());
		boolean useFeeStatus = false;
		boolean depositStatus = false;
		if(isPass){
			//创建保证金收款单
			SupplierContract currentContract = new SupplierContract();
			currentContract.setDeposit(new BigDecimal(-10));
			currentContract.setUseFee(new BigDecimal(-10));
			if(StringUtils.isEmpty(supplierContract.getDepositStatus())){
				depositStatus = supplierContractService.createFinanceDepositCashBill(supplierContract,user.getUsername(),null);
			}else if(MerchantConstant.FEE_STATUS_WAIT_PAY.equals(supplierContract.getDepositStatus())){
				//修改保证金
				depositStatus = supplierContractService.updateFinanceDepositCashBill(supplierContract,user.getUsername(),currentContract,null);
			}else{
				depositStatus = true;
			}
			
			//业务审核通过时，创建平台使用费收款单+3
			if(StringUtils.isEmpty(supplierContract.getUseFeeStatus())){
				useFeeStatus = supplierContractService.createFinanceUseFeeCashBill(supplierContract,user.getUsername());
			}else if(MerchantConstant.FEE_STATUS_WAIT_PAY.equals(supplierContract.getUseFeeStatus())){
				//修改平台使用费
				useFeeStatus = supplierContractService.updateFinanceUseFeeCashBill(supplierContract,user.getUsername(),currentContract);
			}else{
				useFeeStatus = true;
			}
			
			if(!useFeeStatus || !depositStatus){
				result.put("result", "failure");
	    		result.put("msg", "调用财务接口创建收款单失败，请重试！");
	    		logger.error("调用财务接口创建收款单失败，请重试！contract_no="+supplierContract.getContractNo());
	    		return result.toString();
			}
		}
		
		if(isPass){			
			supplier.setIsValid(MerchantConstant.MERCHANT_STATUS_BUSINESS_AUDITED);
		}else{
			supplier.setIsValid(MerchantConstant.MERCHANT_STATUS_BUSINESS_REFUSED);
		}
		Integer count = purchaseApiService.updateSupplier(supplier);
		
    	if(count > 0){
    		String oldStatus = supplierContract.getStatus();
    		supplierContract.setUpdateUser(user.getUsername());
    		supplierContract.setUpdateTime(DateUtil2.getDateTime(new Date(System.currentTimeMillis())));	
    		if(isPass){
    			supplierContract.setStatus(MerchantConstant.CONTRACT_STATUS_BUSINESS_AUDITED);
    		}else{
    			supplierContract.setStatus(MerchantConstant.CONTRACT_STATUS_BUSINESS_REFUSED);
    		}
    		supplierContractService.updateSupplierContract(supplierContract);
    		
    		try{	
	    		//记录日志-合同状态流转日志
	        	MerchantContractUpdateHistory updateHistory = new MerchantContractUpdateHistory();
	        	updateHistory.setContractNo(supplierContract.getContractNo());
	        	updateHistory.setSupplierId(supplierVo.getId());
	        	updateHistory.setOperator(user.getUsername());
	        	updateHistory.setProcessing(ProcessingType.BUSINESS_AUDIT.getDescription());
	        	updateHistory.setRemark(remark);
	        	updateHistory.setType(MerchantConstant.LOG_FOR_CONTRACT);
	        	updateHistory.setUpdateAfter(ContractStatusEnum.getValue(supplierContract.getStatus()));
	        	updateHistory.setUpdateBefore(ContractStatusEnum.getValue(oldStatus));
	        	updateHistory.setUpdateField("status");
	        	YmcResult ymcResult = merchantOperatorApi.addContractLog(updateHistory);
	        	if(YmcResult.ERROR_CODE.equals(ymcResult.getCode())){
	        		logger.error("审核供应商(" + supplierContract.getSupplier() + ")增加合同状态流转日志异常."+ymcResult.getMessage());
	        	}
	    	} catch (Exception e) {
				logger.error("审核供应商(" + supplierContract.getSupplier() + ")增加合同状态流转日志异常.",e);
			}
	    	
	    	
	    	try{	
	    		//记录日志-供应商状态流转日志 
	        	MerchantContractUpdateHistory updateHistory = new MerchantContractUpdateHistory();
	        	updateHistory.setContractNo(supplierContract.getContractNo());
	        	updateHistory.setSupplierId(supplierVo.getId());
	        	updateHistory.setOperator(user.getUsername());
	        	updateHistory.setProcessing(ProcessingType.BUSINESS_AUDIT_MERCHANT.getDescription());
	        	updateHistory.setRemark(remark);
	        	updateHistory.setType(MerchantConstant.LOG_FOR_MERCHANT);
	        	updateHistory.setUpdateAfter(MerchantStatusEnum.getValue(supplier.getIsValid()));
	        	updateHistory.setUpdateBefore(MerchantStatusEnum.getValue(supplierVo.getIsValid()));
	        	updateHistory.setUpdateField("isValid");
	        	YmcResult ymcResult = merchantOperatorApi.addContractLog(updateHistory);
	        	if(YmcResult.ERROR_CODE.equals(ymcResult.getCode())){
	        		logger.error("审核供应商(" + supplierContract.getSupplier() + ")增加商家状态流转日志异常."+ymcResult.getMessage());
	        	}
	    	} catch (Exception e) {
				logger.error("审核供应商(" + supplierContract.getSupplier() + ")增加商家状态流转日志异常.",e);
			}
    		
    		result.put("result", "success");
    		result.put("msg", "操作成功！");
    	}else{
    		result.put("result", "failure");
    		result.put("msg", "操作失败，请重试！");
    	}    	
    	
    	return result.toString();
    }
    
    /**
     * 财务审核供应商
     * 
     * @param supplierId
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("finance_audit_supplier")
    public String financeAuditSupplier(String supplierId, boolean isPass, String remark, HttpServletRequest request) throws Exception {
    	JSONObject result = new JSONObject();
    	SupplierVo supplierVo = merchantServiceNew.getSupplierVoById(supplierId);
    	SystemmgtUser user = GetSessionUtil.getSystemUser(request);
    	Supplier supplier = new Supplier();
    	supplier.setId(supplierId);
		supplier.setUpdateTimestamp(System.currentTimeMillis());// 时间戳
		supplier.setUpdateUser(user != null ? user.getUsername() : null);// 修改人
		supplier.setUpdateUsername(user != null ? user.getUsername() : null);// 修改人
		supplier.setUpdateDate(new Date());// 修改时间 添加时默认为创建时间
		if(isPass){			
			supplier.setIsValid(MerchantConstant.MERCHANT_STATUS_FINANCE_AUDITED);
		}else{
			supplier.setIsValid(MerchantConstant.MERCHANT_STATUS_FINANCE_REFUSED);
		}
		Integer count = purchaseApiService.updateSupplier(supplier);
		
    	if(count > 0){
    		SupplierContract supplierContract = supplierContractService.getSupplierCurrentContract(supplierId);
    		String oldStatus = supplierContract.getStatus();
    		supplierContract.setUpdateUser(user.getUsername());
    		supplierContract.setUpdateTime(DateUtil2.getDateTime(new Date(System.currentTimeMillis())));	
    		if(isPass){
    			supplierContract.setStatus(MerchantConstant.CONTRACT_STATUS_FINANCE_AUDITED);
    		}else{
    			supplierContract.setStatus(MerchantConstant.CONTRACT_STATUS_FINANCE_REFUSED);
    		}
    		supplierContractService.updateSupplierContract(supplierContract);
	    	try{	
	    		//记录日志-合同状态流转日志
	        	MerchantContractUpdateHistory updateHistory = new MerchantContractUpdateHistory();
	        	updateHistory.setContractNo(supplierContract.getContractNo());
	        	updateHistory.setSupplierId(supplierVo.getId());
	        	updateHistory.setOperator(user.getUsername());
	        	updateHistory.setProcessing(ProcessingType.FINANCE_AUDIT.getDescription());
	        	updateHistory.setRemark(remark);
	        	updateHistory.setType(MerchantConstant.LOG_FOR_CONTRACT);
	        	updateHistory.setUpdateAfter(ContractStatusEnum.getValue(supplierContract.getStatus()));
	        	updateHistory.setUpdateBefore(ContractStatusEnum.getValue(oldStatus));
	        	updateHistory.setUpdateField("status");
	        	YmcResult ymcResult = merchantOperatorApi.addContractLog(updateHistory);
	        	if(YmcResult.ERROR_CODE.equals(ymcResult.getCode())){
	        		logger.error("审核供应商(" + supplierContract.getSupplier() + ")增加合同状态流转日志异常."+ymcResult.getMessage());
	        	}
	    	} catch (Exception e) {
				logger.error("审核供应商(" + supplierContract.getSupplier() + ")增加合同状态流转日志异常.",e);
			}
	    	
	    	
	    	try{	
	    		//记录日志-供应商状态流转日志 
	        	MerchantContractUpdateHistory updateHistory = new MerchantContractUpdateHistory();
	        	updateHistory.setContractNo(supplierContract.getContractNo());
	        	updateHistory.setSupplierId(supplierVo.getId());
	        	updateHistory.setOperator(user.getUsername());
	        	updateHistory.setProcessing(ProcessingType.FINANCE_AUDIT_MERCHANT.getDescription());
	        	updateHistory.setRemark(remark);
	        	updateHistory.setType(MerchantConstant.LOG_FOR_MERCHANT);
	        	updateHistory.setUpdateAfter(MerchantStatusEnum.getValue(supplier.getIsValid()));
	        	updateHistory.setUpdateBefore(MerchantStatusEnum.getValue(supplierVo.getIsValid()));
	        	updateHistory.setUpdateField("isValid");
	        	
	        	YmcResult ymcResult = merchantOperatorApi.addContractLog(updateHistory);
	        	if(YmcResult.ERROR_CODE.equals(ymcResult.getCode())){
	        		logger.error("审核供应商(" + supplierContract.getSupplier() + ")增加商家状态流转日志异常."+ymcResult.getMessage());
	        	}
	    	} catch (Exception e) {
				logger.error("审核供应商(" + supplierContract.getSupplier() + ")增加商家状态流转日志异常.",e);
			}
	    	
    		result.put("result", "success");
    		result.put("msg", "操作成功！");
    	}else{
    		result.put("result", "failure");
    		result.put("msg", "操作失败，请重试！");
    	}
    	
    	
    	return result.toString();
    }
    
    /**
     * 查看供应商--日志信息
     * @param modelMap
     * @param request
     * @param id   商家ID
     * @param type
     * @return
     * @throws Exception
     */
    @RequestMapping("to_view_supplier_log")
   	public String toViewSupplierLog(ModelMap modelMap, String id, String listKind) throws Exception {
    	modelMap.put("listKind",listKind);
    	SupplierVo supplier = merchantServiceNew.getSupplierVoById(id);
      	modelMap.put("id", id); 
    	List<MerchantContractUpdateHistory>  historyList = merchantOperatorApi.listHistory(supplier.getId());
    	modelMap.put("historyList",historyList);
    	modelMap.addAttribute("supplier", supplier);
    	List<MerchantOperationLog> logList = merchantOperatorApi.listOperationLog(supplier.getSupplierCode());
    	modelMap.put("logList",logList);
    	
    	return "yitiansystem/merchants/supplier/view_supplier_log";
    	 
   	}
    
    /**
     * 跳转到更新资质及品类授权页面
     * @param modelMap
     * @param id
     * @return
     */
    @RequestMapping("to_update_natural_and_auth")
    public String toUpdateNatural(ModelMap modelMap,String id){
    	if (StringUtils.isNotBlank(id)){
    		SupplierVo supplier = merchantServiceNew.getSupplierVoById(id);
    		modelMap.addAttribute("supplier", supplier);
	    	// 商家扩展表信息
			MerchantSupplierExpand supplierExpand = merchantServiceNew.getSupplierExpandVoById(id);
			modelMap.addAttribute("supplierExpand", supplierExpand);
			SupplierVo vo = merchantServiceNew.getSupplierVoById(id);
			modelMap.addAttribute("supplier", vo);
	    	SupplierContract supplierContract = supplierContractService.buildSupplierContractSet(id);
			modelMap.put("supplierContract", supplierContract);
			
			int defaultMaxFileSize = MerchantConstant.DEFAULT_CONTRACT_ATTACHMENT_FILE_SIZE; //10M
	    	String cfgFileSize = sysconfigProperties.getContractFtpMaxFileSize();
	    	int maxFileSize = StringUtils.isNotBlank(cfgFileSize) ? 
	    			Integer.valueOf(cfgFileSize) : defaultMaxFileSize;
			modelMap.addAttribute("maxFileSize",maxFileSize+"");
			
			// 查询授权品牌.[brandNo;brandName_brandNo;brandName...]
			List<Category> treeModes = null;
			String brandStrs = null;
			try {
				brandStrs = merchantsService.queryAuthorizationBrandBysupplierId(id);
				List<String> brandNos = merchantsService.getAuthorizationBrandNos(id);
				//h6rM;swsport_a6ta;布来亚克_02q2;速比涛
				//[h6rM, a6ta, 02q2]
				// 品牌分类关系集合
				List<String> brandStructs = merchantsService.queryAuthorizationBrandCatBysupplierId(id);
				//[02q2;11-12-28, h6rM;18-15-11, a6ta;15-13-43, h6rM;18-12-12, 02q2;11-12-26, 02q2;11-14-20, a6ta;15-11-21, a6ta;15-11-22, 02q2;11-11-11, 02q2;11-11-24, 02q2;11-12-22, a6ta;15-14-15, h6rM;18-32-10, h6rM;18-24-10, a6ta;15-10-16, 02q2;11-13-13, 02q2;11-11-14, a6ta;15-14-13, a6ta;15-12-26, a6ta;15-11-23, h6rM;18-22-10, h6rM;18-34-10, a6ta;15-13-29, 02q2;11-13-14, a6ta;15-14-14, 02q2;11-12-11, a6ta;15-14-11, a6ta;15-13-55, 02q2;11-12-30, a6ta;15-14-16, a6ta;15-13-41, a6ta;15-13-30, 02q2;11-11-17, 02q2;11-14-21, a6ta;15-11-28, a6ta;15-11-12, a6ta;15-13-51, a6ta;15-10-11, 02q2;11-11-25, a6ta;15-13-59, h6rM;18-11-19, 02q2;11-12-13, a6ta;15-13-45, a6ta;15-13-50, a6ta;15-14-17, h6rM;18-35-10, a6ta;15-12-27, h6rM;18-12-11, a6ta;15-11-25, 02q2;11-13-18, a6ta;15-11-17, a6ta;15-13-36, a6ta;15-12-13, 02q2;11-11-26, 02q2;11-12-18, h6rM;18-30-10, a6ta;15-11-15, 02q2;11-11-20, h6rM;18-11-18, a6ta;15-12-17, a6ta;15-12-15, a6ta;15-11-30, h6rM;18-19-10, a6ta;15-13-35, a6ta;15-13-37, a6ta;15-13-47, 02q2;11-12-25, a6ta;15-13-52, a6ta;15-13-48, a6ta;15-11-13, a6ta;15-13-53, h6rM;18-29-10, h6rM;18-18-10, 02q2;11-13-16, a6ta;15-13-57, h6rM;18-37-10, a6ta;15-13-39, a6ta;15-12-12, a6ta;15-12-11, a6ta;15-12-19, 02q2;11-11-21, 02q2;11-11-27, a6ta;15-13-49, 02q2;11-12-27, a6ta;15-11-19, a6ta;15-12-16, h6rM;18-16-10, 02q2;11-12-29, h6rM;18-11-20, a6ta;15-13-34, a6ta;15-13-44, h6rM;18-21-10, 02q2;11-11-12, a6ta;15-13-15, 02q2;11-12-21, 02q2;11-14-18, a6ta;15-13-56, a6ta;15-10-13, a6ta;15-12-21, a6ta;15-13-13, h6rM;18-11-26, h6rM;18-11-24, a6ta;15-11-26, a6ta;15-12-23, h6rM;18-11-11, a6ta;15-11-24, h6rM;18-11-13, a6ta;15-12-18, h6rM;18-27-10, a6ta;15-11-20, 02q2;11-14-16, h6rM;18-12-16, a6ta;15-14-12, 02q2;11-13-11, 02q2;11-14-17, h6rM;18-11-14, a6ta;15-12-20, a6ta;15-13-46, a6ta;15-13-23, a6ta;15-13-40, a6ta;15-12-25, 02q2;11-12-14, a6ta;15-11-29, a6ta;15-10-18, a6ta;15-11-16, h6rM;18-26-10, a6ta;15-10-19, h6rM;18-28-10, 02q2;11-12-31, 02q2;11-11-23, a6ta;15-13-54, a6ta;15-11-27, h6rM;18-17-10, 02q2;11-13-12, a6ta;15-13-14, h6rM;18-11-12, h6rM;18-12-13, a6ta;15-10-17, h6rM;18-23-10, h6rM;18-25-10, a6ta;15-13-32, h6rM;18-15-15, h6rM;18-36-10, h6rM;18-15-14, 02q2;11-14-11, a6ta;15-11-14, a6ta;15-12-14, 02q2;11-14-14, a6ta;15-10-15, h6rM;18-11-15, a6ta;15-10-20, 02q2;11-11-16, 02q2;11-13-17, h6rM;18-12-15, h6rM;18-15-10, 02q2;11-12-20, 02q2;11-14-13, 02q2;11-12-19, a6ta;15-10-14, h6rM;18-11-16, 02q2;11-12-12, h6rM;18-20-10, 02q2;11-14-12, h6rM;18-11-23, h6rM;18-11-21, 02q2;11-11-22, h6rM;18-12-14, h6rM;18-11-22, a6ta;15-13-58, 02q2;11-12-17, 02q2;11-14-19, h6rM;18-15-13, 02q2;11-11-29, 02q2;11-12-32, h6rM;18-11-17, a6ta;15-13-42, h6rM;18-11-25, h6rM;18-33-10, a6ta;15-13-33, a6ta;15-11-18, 02q2;11-11-19, a6ta;15-12-24, h6rM;18-31-10, 02q2;11-13-15, h6rM;18-38-10, a6ta;15-11-11, 02q2;11-11-18, 02q2;11-12-15, a6ta;15-10-12, 02q2;11-11-13]
				treeModes = merchantComponent.getCategoryTreeMode(brandNos);
				merchantComponent.setTreeModesCheckStatus(treeModes, brandStructs);
			} catch (Exception e) {
				logger.error("查询和构建授权品牌和分类树出错 ",e);
			}
			modelMap.addAttribute("brandStrs", brandStrs);
			modelMap.addAttribute("treeModes", treeModes);
    	}
    	return "yitiansystem/merchants/supplier/update_natural";
    }
   
    /**
     * 保存更新资质及品牌授权信息
     * @param modelMap
     * @param supplierId
     * @return
     */
    @ResponseBody
    @RequestMapping("update_natural_and_auth")
    public String updateNatural(ModelMap modelMap, MerchantSupplierExpand supplierExpand,
    							AttachmentFormVo attachmentFormVo, HttpServletRequest req ){
    	String supplierId = attachmentFormVo.getSupplierId() ;
    	String contractId = attachmentFormVo.getContractId();
	    if (StringUtils.isNotBlank( supplierId ) && StringUtils.isNotBlank( contractId ) ) {
	        try {
	
    			SystemmgtUser user = GetSessionUtil.getSystemUser(req);
    			String userName = (user != null) ? user.getUsername() : "";
    			int dateDiff1 = DateUtil2.getDiffDateFromToday(supplierExpand.getBusinessValidityEnd());
    			supplierExpand.setBusinessRemainingDays(dateDiff1);
    			int dateDiff2 = DateUtil2.getDiffDateFromToday(supplierExpand.getInstitutionalValidityEnd());
    			supplierExpand.setInstitutionalRemainingDays(dateDiff2);
			    
    			// supplierExpandId
    			supplierExpand.setId( supplierExpand.getSupplierExpandId() );
    			
    			// 方法主体
    			merchantServiceNew.updateNatural(supplierExpand, attachmentFormVo);
    			
    			//恢复API授权
    			supplierContractService.recoverApiLicenceBySupplierId(supplierId);
    			
			   // 记录操作日志
				try {
					SupplierVo supplierVo = merchantServiceNew.getSupplierVoById(supplierId);
					
					MerchantOperationLog log = new MerchantOperationLog();
					log.setType(MerchantConstant.LOG_FOR_MERCHANT);
					log.setOperator(userName);
					log.setOperationNotes("更新资质及品牌授权");
					log.setOperationType(OperationType.UPDATE_NATURAL.getDescription());
					log.setMerchantCode(supplierVo.getSupplierCode());
					
					merchantServiceNew.insertMerchantLog(log);

				} catch (Exception e) {
					logger.error("为‘更新资质及品牌授权’操作增加修改日志发生异常.",e);
				}
			} catch (Exception e) {
				logger.error("更新资质及品牌授权保存失败.",e);
				return "更新资质及品牌授权保存失败，发生异常！";
			}
	    	return "success";
	    }else{
	    	return "商家Id和商家合同Id都不可以为空，请重新选择商家";
	    }
    }
    
    /**
     * 查看资质信息
     * @param modelMap
     * @param id
     * @return
     */
    @RequestMapping("view_natural")
    public String viewNatural(ModelMap modelMap,String id){
    	// 商家扩展表信息
		MerchantSupplierExpand supplierExpand = merchantServiceNew.getSupplierExpandVoById(id);
		modelMap.addAttribute("supplierExpand", supplierExpand);
		
    	SupplierContract supplierContract = supplierContractService.buildSupplierContractSet(id);
		modelMap.put("supplierContract", supplierContract);
//		String headPath = sysconfigProperties.getContractFtpPathRead();
//		modelMap.put("headPath", headPath);
		modelMap.put("supplierId", id);
		// 品牌二级分类信息查询
		List<Cat> catList = null;
		try {
			catList = merchantsService.buildBrandCatSecondLevelList(id);
			modelMap.addAttribute("catList",catList); 
		} catch (Exception e) {
			logger.error("查询并构建商家的品牌和二级分类信息发生异常 ",e);
		}
    	return "yitiansystem/merchants/supplier/view_natural";
    }
    
    /***
     * 判断商家是否有当前合同；是否有续签提交审核中
     * 两处被调用：打开更新资质页面之前、打开续签页面之前
     * @param modelMap
     * @param id 商家id
     * @return id 
     */
    @ResponseBody
    @RequestMapping("check_contract_for_supplier")
    public String checkContractForSupplier(ModelMap modelMap,String id){
    	try{
    	
    	if(StringUtils.isNotEmpty(id)){
	    	
	    	try {
				this.supplierService.getSupplierById(id);
			} catch (Exception e) {
				logger.error("根据商家ID查询供应商发生异常 ",e);
				return "502";//未找到该供应商，请确定供应商ID是否正确。
			}
	    	
	    	// 合同是否需要续签
	    	com.belle.yitiansystem.merchant.model.pojo.SupplierContract currentSupplierContract 
	    	= supplierContractService.getSupplierCurrentContract(id);
	    	if( null!=currentSupplierContract ){
		    	String isNeedRenew = currentSupplierContract.getIsNeedRenew();
		    	if(StringUtils.isNotBlank(isNeedRenew) && MerchantConstant.NO_NEED_RENEW.equals(isNeedRenew) ){
		    		return "501";//该供应商的合同不需要续签。
		    	}
	    	}else{
	    		return "500";// 该供应商还未创建合同，不可以操作续签。
	    	}
		    	
	    	// 续签中合同
	    	com.belle.yitiansystem.merchant.model.pojo.SupplierContract supplierContract 
	    	= supplierContractService.getSupplierRenewContract(id);
	    	if(null!=supplierContract){
		    	String status = supplierContract.getStatus();
		    	// 非新建、财务审核不通过 状态，不弹出续签或续签修改页面。
		    	if( StringUtils.isNotBlank(status)&& !(  MerchantConstant.CONTRACT_STATUS_NEW.equals(status) ||
		    			MerchantConstant.CONTRACT_STATUS_FINANCE_REFUSED.equals(status) ) ){
		    		return "503";// 已经在续签中，请耐心等待续签结果。
		    	}
		    }
	    	return id;
		    	
	    	
    	}else{
    		return "";
    	}
    	
    	}catch(Exception e){
    		logger.error("续签异常 ",e);
    	}
    	return "";
    }
    
    /***
     * 将商家撤回到新建状态
     * @param modelMap
     * @param id 商家id
     * @return success/failed
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping("recall_merchant")
    public String recallMerchant(ModelMap modelMap,String id, HttpServletRequest request) throws Exception{
    	String result = "";
		SupplierVo supplierVo = this.supplierService.getSupplierById(id);
		if(null == supplierVo){
			 result = "未找到招商供应商，请重试";
		}else{			
			if(MerchantConstant.MERCHANT_STATUS_BUSINESS_AUDITING == supplierVo.getIsValid()
					|| MerchantConstant.MERCHANT_STATUS_BUSINESS_AUDITED == supplierVo.getIsValid()){
			
				SystemmgtUser user = GetSessionUtil.getSystemUser(request);
		    	Supplier supplier = new Supplier();
		    	supplier.setId(id);
				supplier.setUpdateTimestamp(System.currentTimeMillis());// 时间戳
				supplier.setUpdateUser(user != null ? user.getUsername() : null);// 修改人
				supplier.setUpdateUsername(user != null ? user.getUsername() : null);// 修改人
				supplier.setUpdateDate(new Date());// 修改时间 添加时默认为创建时间
				supplier.setIsValid(MerchantConstant.MERCHANT_STATUS_NEW);
				Integer count = purchaseApiService.updateSupplier(supplier);
				SupplierContract supplierContract = supplierContractService.getSupplierCurrentContract(id);
		    	if(count > 0){
		    		
		    		if( null!=supplierContract ){
		    			
			    		String oldStatus = supplierContract.getStatus();
			    		supplierContract.setUpdateUser(user.getUsername());
			    		supplierContract.setUpdateTime(DateUtil2.getDateTime(new Date(System.currentTimeMillis())));	
			    		supplierContract.setStatus(MerchantConstant.CONTRACT_STATUS_NEW);
			    		supplierContractService.updateSupplierContract(supplierContract);
		    		
				    	try{	
				    		//记录日志-合同状态流转日志
				        	MerchantContractUpdateHistory updateHistory = new MerchantContractUpdateHistory();
				        	updateHistory.setContractNo(supplierContract.getContractNo());
				        	updateHistory.setSupplierId(supplierVo.getId());
				        	updateHistory.setOperator(user.getUsername());
				        	updateHistory.setProcessing(ProcessingType.RECALL_AUDIT.getDescription());
				        	updateHistory.setRemark("撤回审核");
				        	updateHistory.setType(MerchantConstant.LOG_FOR_CONTRACT);
				        	updateHistory.setUpdateAfter(ContractStatusEnum.getValue(supplierContract.getStatus()));
				        	updateHistory.setUpdateBefore(ContractStatusEnum.getValue(oldStatus));
				        	updateHistory.setUpdateField("status");
				        	YmcResult ymcResult = merchantOperatorApi.addContractLog(updateHistory);
				        	if(YmcResult.ERROR_CODE.equals(ymcResult.getCode())){
				        		logger.error("审核供应商(" + supplierContract.getSupplier() + ")增加合同状态流转日志异常."+ymcResult.getMessage());
				        	}
				    	} catch (Exception e) {
							logger.error("审核供应商(" + supplierContract.getSupplier() + ")增加合同状态流转日志异常.",e);
						}
			    	
		    		}
		    		
			    	try{	
			    		//记录日志-供应商状态流转日志 
			        	MerchantContractUpdateHistory updateHistory = new MerchantContractUpdateHistory();
			        	updateHistory.setContractNo(supplierContract.getContractNo());
			        	updateHistory.setSupplierId(supplierVo.getId());
			        	updateHistory.setOperator(user.getUsername());
			        	updateHistory.setProcessing(ProcessingType.RECALL_AUDIT.getDescription());
			        	updateHistory.setRemark("撤回审核");
			        	updateHistory.setType(MerchantConstant.LOG_FOR_MERCHANT);
			        	updateHistory.setUpdateAfter(MerchantStatusEnum.getValue(supplier.getIsValid()));
			        	updateHistory.setUpdateBefore(MerchantStatusEnum.getValue(supplierVo.getIsValid()));
			        	updateHistory.setUpdateField("isValid");
			        	
			        	YmcResult ymcResult = merchantOperatorApi.addContractLog(updateHistory);
			        	if(YmcResult.ERROR_CODE.equals(ymcResult.getCode())){
			        		logger.error("审核供应商(" + supplierContract.getSupplier() + ")增加商家状态流转日志异常."+ymcResult.getMessage());
			        	}
			    	} catch (Exception e) {
						logger.error("审核供应商(" + supplierContract.getSupplier() + ")增加商家状态流转日志异常.",e);
					}
		    	}
		    	result =  "success";
	    	}else{
	    		result = "商家不是待审核状态，不能撤消！";
	    	}
		}
		
		return result;
    }
	
		private void writeLogForRenew(SupplierContract contract, String originalStatus,boolean isSubmit,Supplier supplier,SystemmgtUser user){
			
			// 添加供应商修改日志
			try {
				MerchantOperationLog log = new MerchantOperationLog();
				log.setType( MerchantConstant.LOG_FOR_CONTRACT );
				log.setOperator(user.getUsername());
				log.setContractNo(contract.getContractNo());
				if(StringUtils.isEmpty(contract.getId())){
					log.setOperationNotes("续签商家");
					log.setOperationType(OperationType.RENEW_MERCHANT.getDescription());
				}else{
					log.setOperationNotes("修改续签");
					log.setOperationType(OperationType.UPDATE_RENEW_MERCHANT.getDescription());
				}
				log.setMerchantCode( supplier.getSupplierCode() );
				merchantServiceNew.insertMerchantLog(log);

			} catch (Exception e) {
				logger.error("为续签供应商(" + supplier.getSupplier() + ")增加操作日志异常.",e);
			}
			
			try{	
				if(isSubmit || StringUtils.isEmpty(contract.getId())){
					//记录日志-合同状态流转日志
					MerchantContractUpdateHistory updateHistory = new MerchantContractUpdateHistory();
					updateHistory.setContractNo(contract.getContractNo());
					updateHistory.setSupplierId(contract.getSupplierId());
					updateHistory.setOperator(user.getUsername());
					if(StringUtils.isEmpty(contract.getId())){
						if(isSubmit){
							updateHistory.setProcessing(ProcessingType.RENEW_MERCHANT.getDescription());
							updateHistory.setUpdateAfter(MerchantConstant.CONTRACT_STATUS_BUSINESS_AUDITED_ZH);
						}else{
							updateHistory.setProcessing(ProcessingType.CREATE_CONTRACT.getDescription());
							updateHistory.setUpdateAfter(MerchantConstant.CONTRACT_STATUS_NEW_ZH);
						}
						//updateHistory.setUpdateAfter(ContractStatusEnum.getValue(contract.getStatus()));
						updateHistory.setUpdateBefore("");
					}else{
						if(isSubmit){
							updateHistory.setProcessing(ProcessingType.UPDATE_RENEW_MERCHANT.getDescription());
							updateHistory.setUpdateAfter(MerchantConstant.CONTRACT_STATUS_BUSINESS_AUDITED_ZH);
							if(StringUtils.isNotBlank(originalStatus)){
								updateHistory.setUpdateBefore(ContractStatusEnum.getValue(originalStatus));
							}else{
								updateHistory.setUpdateBefore(MerchantConstant.CONTRACT_STATUS_NEW_ZH);
							}
						}
					}
					updateHistory.setType(MerchantConstant.LOG_FOR_CONTRACT);
					updateHistory.setUpdateField("status");
					YmcResult ymcResult = merchantOperatorApi.addContractLog(updateHistory);
					if(YmcResult.ERROR_CODE.equals(ymcResult.getCode())){
						logger.error("续签供应商(" + supplier.getSupplier() + ")增加合同状态流转日志异常."+ymcResult.getMessage());
					}
				}
	    	} catch (Exception e) {
				logger.error("续签供应商(" + supplier.getSupplier() + ")增加合同状态流转日志异常.",e);
			}

			
		}
		
		  /**
	     * 进入续签页面
	     * @param modelMap
	     * @param id 商家id
	     * @return
	     */
	    @RequestMapping("to_renew_supplier_contract")
	    public String toRenewSupplierContract(ModelMap modelMap,String id){
	    	SupplierVo supplier = null;
	    	if(StringUtils.isNotEmpty(id)){
	    		try {
					supplier = this.supplierService.getSupplierById(id);
				} catch (Exception e) {
					return "未找到该预备续签合同的商家！";
				}
	    		modelMap.addAttribute("renew_contract","1");
	    		
	    		// 当前合同-即将过期合同
	    		com.belle.yitiansystem.merchant.model.pojo.SupplierContract currentSupplierContract 
		    	= supplierContractService.getSupplierCurrentContract(id);
	    		// 上期保证金合计起来
	    		if( null!=currentSupplierContract ){
	    			
	    			// 1. 合计上期保证金
	    			if( MerchantConstant.CONTRACT_TRANSFER_FLAG.equals(currentSupplierContract.getIsTransferDeposit() ) ) {
	    				BigDecimal current = currentSupplierContract.getDeposit();
	    				BigDecimal last = currentSupplierContract.getPreDeposit();
	    				if(last!=null){
	    					BigDecimal sum = current;
							try {
								sum = current.add( last );
							} catch (Exception e) {
								logger.error(" 合计上期保证金发生数据异常，请检查是否有录入不合法BigDecimal数字。",e);
							}
	    					currentSupplierContract.setDeposit(sum);
	    				}
	    			}
	    			
	    		}
	    		
	    		modelMap.addAttribute("currentSupplierContract",currentSupplierContract);
	    		modelMap.addAttribute("contractIdForTrademark",currentSupplierContract.getId());
	    		
	    		// 续签中合同
		    	com.belle.yitiansystem.merchant.model.pojo.SupplierContract renewSupplierContract 
		    	= supplierContractService.getSupplierRenewContractAndItsAttachments(id);
		    	if( null!=renewSupplierContract){
		    		modelMap.addAttribute("supplierContract",renewSupplierContract);
		    		modelMap.addAttribute("contractIdForTrademark",renewSupplierContract.getId());
		    	}
		    	
		    	
		    	modelMap.addAttribute("contractNo",MerchantUtil.initContractNo(redisTemplate));
		    	
		    	modelMap.addAttribute("supplier", supplier);
		    	//成本帐套名称
				List<CostSetofBooks> costSetofBooksList = null;
				try {
					costSetofBooksList = merchantServiceNew.getAllCostSetofBooksList();
				} catch (Exception e) {
					logger.error("未查到所有的成本帐套名称枚举，请检查接口！",e);
				}
				modelMap.addAttribute("costSetofBooksList", costSetofBooksList);
				// 获取省市区第一级结果集数据
				List<Map<String, Object>> areaList = merchantsService.getAreaList();
				modelMap.addAttribute("areaList", areaList);
		    	int defaultMaxFileSize = MerchantConstant.DEFAULT_CONTRACT_ATTACHMENT_FILE_SIZE; //10M
		    	String cfgFileSize = sysconfigProperties.getContractFtpMaxFileSize();
		    	int maxFileSize = StringUtils.isNotBlank(cfgFileSize) ? 
		    			Integer.valueOf(cfgFileSize) : defaultMaxFileSize;
				modelMap.addAttribute("maxFileSize",maxFileSize+"");
				
				// 商家扩展表信息
				MerchantSupplierExpand supplierExpand = merchantServiceNew.getSupplierExpandVoById(id);
				modelMap.addAttribute("supplierExpand",supplierExpand);
				
				try {
					// 查询授权品牌.[brandNo;brandName_brandNo;brandName...]
					String brandStrs = merchantsService.queryAuthorizationBrandBysupplierId(id);
					List<String> brandNos = merchantsService.getAuthorizationBrandNos(id);
					// 品牌分类关系集合
					List<String> brandStructs = merchantsService.queryAuthorizationBrandCatBysupplierId(id);
					List<Category> treeModes = merchantComponent.getCategoryTreeMode(brandNos);
					merchantComponent.setTreeModesCheckStatus(treeModes, brandStructs);
					modelMap.addAttribute("brandStrs", brandStrs);
					modelMap.addAttribute("treeModes", treeModes);
				} catch (Exception e) {
					logger.error("进入续签页面：查询授权品牌和品牌分类关系集合发生异常 ",e);
				}
	    	}
	    	return "yitiansystem/merchants/supplier/renew_supplier_contract";
	    }
	    
		
		 /**
  		 * 续签招商合同信息修改
  		 * @param modelMap
  		 * @param request
  		 * @param supplierContract
  		 * @param currentContractId
  		 * @param supplier
  		 * @param isSubmit
  		 * @param attachmentFormVo
  		 * @return
  		 */
  		@ResponseBody
  	    @RequestMapping("update_renew_new_contract")
  		public String updateRenewNewContract(ModelMap modelMap, HttpServletRequest request, SupplierContract supplierContract,
  				String currentContractId ,Supplier supplier,AttachmentFormVo attachmentFormVo, MerchantSupplierExpand supplierExpand,
  				boolean isSubmit) {
  	    	JSONObject result = new JSONObject();
  	    	
    		SystemmgtUser user = GetSessionUtil.getSystemUser(request); 
    		supplier.setId(supplierContract.getSupplierId());
    		supplierExpand.setId( supplierExpand.getSupplierExpandId() );
    		String originalStatus = supplierContract.getStatus();//
    		SupplierContract currentSupplierContract = supplierContractService.getSupplierContractById(currentContractId);
    		
    		Map<String, Object> params = new HashMap<String, Object>();
    		params.put("attachmentFormVo", attachmentFormVo);
    		// 初始化上期是否转入
    		String isTransferFlag = supplierContract.getIsTransferDeposit();
    		if(StringUtils.isBlank(isTransferFlag)){
    			supplierContract.setIsTransferDeposit(MerchantConstant.CONTRACT_NO_TRANSFER_FLAG);
    		}
    		
    		// step1: 若有提交审核，则先 更新收款单
    		if(isSubmit){
    			boolean result1 = true;
    			boolean result2 = true;
				//提交审核的时候才判断是否需要更新收款单 
				SupplierContract contractBeforeUpdated = supplierContractService.getSupplierContractById(supplierContract.getId());
				if(StringUtils.isEmpty(contractBeforeUpdated.getDepositStatus())){
					result1 = supplierContractService.createFinanceDepositCashBill(supplierContract,user.getUsername(),currentSupplierContract);
				}else if(MerchantConstant.FEE_STATUS_WAIT_PAY.equals(contractBeforeUpdated.getDepositStatus())){ // 检查 收款单的状态是否未缴纳， 未缴纳的才可以修改
					//修改保证金  
					result1 = supplierContractService.updateFinanceDepositCashBill(supplierContract,user.getUsername(),contractBeforeUpdated,currentSupplierContract);
				}
				
				if(StringUtils.isEmpty(contractBeforeUpdated.getUseFeeStatus())){
					result2 = supplierContractService.createFinanceUseFeeCashBill(supplierContract,user.getUsername());
				}else if(MerchantConstant.FEE_STATUS_WAIT_PAY.equals(contractBeforeUpdated.getUseFeeStatus())){
					//修改平台使用费
					result2 =supplierContractService.updateFinanceUseFeeCashBill(supplierContract,user.getUsername(),contractBeforeUpdated);
				}
				
				if(!result1 || !result2 ){
	  				StringBuffer msg = new StringBuffer("调财务接口更新收款单失败:");
	  				if(!result1){
	  					msg.append(" 保证金 ");
	  				}
	  				if(!result2){
	  					msg.append(" 平台使用费 ");
	  				}
	  				msg.append("的收款单未成功更新");
	  				result.put("resultCode", "500");
	  	      		result.put("msg",msg.toString());
	  				return result.toString();
	  			}
				
				//  记录下是否需要更新当前合同的保证金状态
				if( MerchantConstant.CONTRACT_TRANSFER_FLAG.equals(isTransferFlag ) && (
						null== contractBeforeUpdated.getIsTransferDeposit() ||
						MerchantConstant.CONTRACT_NO_TRANSFER_FLAG.equals(contractBeforeUpdated.getIsTransferDeposit()) ) ){
					params.put("updateCurrentContractFlag",true);
				}
    		}
  	    		
    		params.put("currentSupplierContract", currentSupplierContract);// 必须放在收款单接口调用之后，因为可能会改变currentSupplierContract的保证金状态
    		
  	    	try{	
  	    		// step2: 实现续签修改
  	    		supplierContractService.updateRenewSupplierContract(supplierContract, supplier, user.getUsername(), isSubmit, params);
  	    		merchantServiceNew.updateSupplierExpand(supplierExpand);
  	    		writeLogForRenew(supplierContract,originalStatus, isSubmit,supplier, user);
  	    		
  	    		result.put("resultCode", "200");
  	    	}catch(Exception e){
  	    		result.put("resultCode", "500");
  	    		result.put("msg","更新收款单后，商家后台实现修改续签信息的操作失败。");
  	    		logger.error("续签合同失败：",e);
  	    	}
  	    	return result.toString();
  		}
  		
		
	    /**
	  	 * 续签招商合同(新 20151210)
	  	 * @param modelMap
	  	 * @param request
	  	 * @param supplierContract
	  	 * @param currentContractId
	  	 * @param supplier
	  	 * @param isSubmit
	  	 * @param attachmentFormVo
	  	 * @return
	  	 */
	  	@ResponseBody
	    @RequestMapping("renew_new_contract")
	  	public String renewNewContract(ModelMap modelMap, HttpServletRequest request, SupplierContract supplierContract,
	  			String currentContractId ,Supplier supplier,AttachmentFormVo attachmentFormVo, MerchantSupplierExpand supplierExpand,
	  			boolean isSubmit) {
	      	   
		    JSONObject result = new JSONObject();
	  		
	  		SystemmgtUser user = GetSessionUtil.getSystemUser(request); 
	  		supplier.setId(supplierContract.getSupplierId());
	  		supplierExpand.setId( supplierExpand.getSupplierExpandId() );
	  		SupplierContract currentSupplierContract = supplierContractService.getSupplierContractById(currentContractId);
	  	
	  		// 初始化上期是否转入
	  		String isTransferFlag = supplierContract.getIsTransferDeposit();
	  		if(StringUtils.isBlank(isTransferFlag)){
	  			supplierContract.setIsTransferDeposit(MerchantConstant.CONTRACT_NO_TRANSFER_FLAG);
	  		}
	  		
	  		// step1: 若有提交审核，则先创建收款单
	  		if(isSubmit){
				//创建保证金收款单
	  			boolean result1 = supplierContractService.createFinanceDepositCashBill(supplierContract,user.getUsername(),currentSupplierContract);
				//创建平台使用费收款单
	  			boolean result2 = supplierContractService.createFinanceUseFeeCashBill(supplierContract,user.getUsername());
	  			if(!result1 || !result2 ){
	  				StringBuffer msg = new StringBuffer("调财务接口创建收款单失败:");
	  				if(!result1){
	  					msg.append(" 保证金 ");
	  				}
	  				if(!result2){
	  					msg.append(" 平台使用费 ");
	  				}
	  				msg.append("的收款单未成功创建");
	  				result.put("resultCode", "500");
	  	      		result.put("msg",msg.toString());
	  				return result.toString();
	  			}
	  		}
	  		
	  		Map<String, Object> params = new HashMap<String, Object>();
	  		params.put("attachmentFormVo", attachmentFormVo);
	  		params.put("currentSupplierContract", currentSupplierContract);
	      		// step2:实现续签
	      	try{
	      		supplierContractService.renewSupplierContract(supplierContract, supplier, user.getUsername(), isSubmit, params);
	      		
	      		merchantServiceNew.updateSupplierExpand(supplierExpand);
	      		
	      		supplierContract.setId(null);
	      		writeLogForRenew(supplierContract,"", isSubmit,supplier, user);
	      		
	      		result.put("resultCode", "200");
	      	}catch(Exception e){
	      		result.put("resultCode", "500");
	      		result.put("msg","创建收款单后，商家后台实现续签的操作失败。");
	      		logger.error("续签合同失败：",e);
	      	}
	      	return result.toString();
	  	}
		
		/**
		 * 
		 * 判断商家登录名称与手机号码是否已经存在
		 * 
		 * @Date 2015-08-15
		 * @throws UnsupportedEncodingException
		 */
		@ResponseBody
		@RequestMapping("exitsLoginAccountAndMobile")
		public String exitsLoginAccountAndMobile(String loginAccount,String mobileCode) {
			boolean bool = false;
			boolean mobileBool = false;
			JSONObject jsObject = new JSONObject();
			try {
				String name = new String(loginAccount.getBytes("ISO-8859-1"), "UTF-8");
				bool = merchantsService.exitsLoginAccount(name);
				mobileBool = merchantsService.existsTelePhoneInMerchants(mobileCode,null);
			} catch (UnsupportedEncodingException e) {
				logger.error(MessageFormat.format("存在此账户{}。", loginAccount), e);
			}
			jsObject.put("isAccountExits", bool?"success":"fail");
			jsObject.put("isMobileExits", mobileBool?"success":"fail");
			return jsObject.toString();
		}
		/**
		 * 查看日志：查看对用户做的操作的日志，比如 手机、邮箱（账号管理列表）
		 * @param userId
		 * @param modelMap
		 * @param request
		 * @return
		 */
		@RequestMapping("viewUserOperationLog")
		public String viewUserOperationLog(ModelMap model,String id, HttpServletRequest request,Query query) 
				throws Exception{
			PageFinder<com.belle.yitiansystem.merchant.model.pojo.MerchantOperationLog> pageFinder = 
					merchantOperationLogService.queryMerchantOperationLogByUser(id,OperationType.ACCOUNT, query);// id 为userId
			model.addAttribute("pageFinder", pageFinder);
			model.addAttribute("id", id);
			return "yitiansystem/merchants/base/view_user_operation_log";
		}
		
		/**
		 * 账号管理-修改手机或邮箱页面
		 * @param model
		 * @param id
		 * @param flag
		 * @param request
		 * @return
		 */
		@RequestMapping("toUserEdit")
		public String toUserUpdate(ModelMap model,String id,String flag, HttpServletRequest request) {
			if(StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(flag) ){
				MerchantUser merchantUser = merchantsApi.getMerchantUserById(id);
				model.addAttribute("merchantUser", merchantUser);
			}
			model.addAttribute("flag", flag);
			return "yitiansystem/merchants/base/to_user_edit";
		}
}
