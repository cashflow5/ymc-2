package com.belle.yitiansystem.merchant.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.yougou.merchant.api.supplier.vo.MerchantOperationLog;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.belle.finance.costsettlement.costsetofbooks.model.vo.CostSetofBooks;
import com.belle.infrastructure.constant.Constant;
import com.belle.infrastructure.json.IJsonTool;
import com.belle.infrastructure.json.JsonTool;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.infrastructure.util.DateUtil;
import com.belle.infrastructure.util.FileFtpUtil;
import com.belle.infrastructure.util.FileUtil;
import com.belle.infrastructure.util.FtpUtils;
import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.infrastructure.util.MD5Util;
import com.belle.infrastructure.util.Md5Encrypt;
import com.belle.infrastructure.util.UUIDUtil;
import com.belle.other.model.pojo.SupplierContactSp;
import com.belle.other.model.pojo.SupplierContract;
import com.belle.other.model.pojo.SupplierSp;
import com.belle.other.model.vo.ResultMsg;
import com.belle.other.service.ISupplierSpLocalService;
import com.belle.yitiansystem.merchant.constant.MerchantConstant;
import com.belle.yitiansystem.merchant.model.pojo.MerchantExpressTemplate;
import com.belle.yitiansystem.merchant.model.pojo.MerchantRejectedAddress;
import com.belle.yitiansystem.merchant.model.pojo.MerchantUser;
import com.belle.yitiansystem.merchant.model.pojo.MerchantsAuthority;
import com.belle.yitiansystem.merchant.model.pojo.MerchantsRole;
import com.belle.yitiansystem.merchant.model.pojo.RoleAuthority;
import com.belle.yitiansystem.merchant.model.pojo.UserRole;
import com.belle.yitiansystem.merchant.model.vo.ContactsVoLocal;
import com.belle.yitiansystem.merchant.model.vo.MerchantsVo;
import com.belle.yitiansystem.merchant.model.vo.SupplierExtend;
import com.belle.yitiansystem.merchant.service.IMerchantContactLocalService;
import com.belle.yitiansystem.merchant.service.IMerchantOperationLogService;
import com.belle.yitiansystem.merchant.service.IMerchantOperatorApi;
import com.belle.yitiansystem.merchant.service.IMerchantServiceNew;
import com.belle.yitiansystem.merchant.service.IMerchantsService;
import com.belle.yitiansystem.merchant.service.ISupplierContractService;
import com.belle.yitiansystem.merchant.util.ExportHelper;
import com.belle.yitiansystem.merchant.util.FtpTools;
import com.belle.yitiansystem.systemmgmt.model.pojo.Area;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.belle.yitiansystem.systemmgmt.util.SysconfigProperties;
import com.yougou.merchant.api.common.UUIDGenerator;
import com.yougou.merchant.api.supplier.service.IBrandCatApi;
import com.yougou.merchant.api.supplier.service.IMerchantsApi;
import com.yougou.merchant.api.supplier.service.ISupplierService;
import com.yougou.merchant.api.supplier.vo.BrandVo;
import com.yougou.merchant.api.supplier.vo.MerchantOperationLog.OperationType;
import com.yougou.merchant.api.supplier.vo.MerchantSupplierExpand;
import com.yougou.merchant.api.supplier.vo.SupplierQueryVo;
import com.yougou.merchant.api.supplier.vo.SupplierVo;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.api.ICommodityMerchantApiService;
import com.yougou.pc.model.brand.Brand;
import com.yougou.pc.model.category.Category;
import com.yougou.purchase.api.IPurchaseApiService;
import com.yougou.purchase.model.Supplier;
import com.yougou.purchase.model.SupplierContact;
import com.yougou.purchase.model.SupplierUpdateHistory;
import com.yougou.wms.wpi.logisticscompany.domain.LogisticsCompanyDomain;
import com.yougou.wms.wpi.logisticscompany.service.ILogisticsCompanyDomainService;
import com.yougou.wms.wpi.warehouse.service.IWarehouseCacheService;

/**
 * 招商信息controller类
 * 
 * @author wang.m create time 2012-3-5
 */
@Controller
@RequestMapping("/yitiansystem/merchants/businessorder")
public class MerchantsController extends BaseController{

	private static Logger logger = LoggerFactory.getLogger(MerchantsController.class);
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
	private ILogisticsCompanyDomainService logisticsCompanyService;
	@Resource
	private IMerchantOperationLogService merchantOperationLogService;
	@Resource
	private ICommodityBaseApiService commodityBaseApiService;
	@Resource
	private ICommodityMerchantApiService commodityMerchantApiService;
	@Resource
	private IPurchaseApiService purchaseApiService;
	@Resource
	private FtpTools ftp;
	@Resource
	private ISupplierSpLocalService supplierSpLocalService;
	
	@Resource
	private IMerchantContactLocalService merchantContactLocalService;
	@Resource
	private IWarehouseCacheService warehouseCacheService;
	@Resource
	private ISupplierContractService supplierContractService;
	private SysconfigProperties sysconfigProperties;
	@Resource
    private IMerchantOperatorApi merchantOperatorApi;
	/**
	 * 
	 * 跳转到招商基础信息显示页面
	 * 
	 * @Date 2012-03-05
	 * @author wang.m
	 * @throws Exception 
	 *//*
	@RequestMapping("to_merchantsList")
	public String to_merchantsList(Query query, ModelMap modelMap,
			MerchantsVo merchantsVo, String isCanAssign) throws Exception {
		SupplierQueryVo vo = new SupplierQueryVo();
		vo.setSupplier(merchantsVo.getSupplier());
		vo.setSupplierCode(merchantsVo.getSupplierCode());
		vo.setIsValid((merchantsVo.getIsValid() == null || merchantsVo.getIsValid() == 0) ? null : merchantsVo.getIsValid());
		vo.setIsInputYougouWarehouse(merchantsVo.getIsInputYougouWarehouse());
		//通过品牌名称查询到No
		if (StringUtils.isNotBlank(merchantsVo.getBrandName())) {
			List<Brand> brands = commodityBaseApiService.getBrandListLikeBrandName("%" + merchantsVo.getBrandName(), (short) 1);
			if (CollectionUtils.isNotEmpty(brands)) {
				List<String> brandNos = new ArrayList<String>();
				for (Brand brand : brands) {
					brandNos.add(brand.getBrandNo());
				}
				vo.setBrandNos(brandNos);
			}
		}
		
		com.yougou.merchant.api.common.Query _query = new com.yougou.merchant.api.common.Query(query.getPageSize());
		_query.setPage(query.getPage());
		// com.yougou.merchant.api.common.PageFinder<SupplierVo> pageFinder =
		// supplierService.querySupplierListByPage(vo, _query);
		PageFinder<SupplierSp> pageFinder = supplierSpLocalService
				.querySupplierListByPage(vo, query, merchantsVo);
		if (pageFinder != null && CollectionUtils.isNotEmpty(pageFinder.getData())) {
			MerchantUser merchantUser = null;
			List<Map<String, Object>> authorityList=null;
			for (SupplierSp _supplier : pageFinder.getData()) {
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

				// 是招商供应商才查询
				if(isCanAssign!=null&&"true".equalsIgnoreCase(isCanAssign)){
					// 根据商家编号查询商家登录信息
					merchantUser = merchantsService.getMerchantsBySuppliceCode(_supplier.getSupplierCode());
					if(merchantUser!=null){
						_supplier.setLoginAccount(merchantUser.getLoginName());	
						authorityList=merchantsService.getMerchantsAuthorityByUserId(merchantUser.getId());
						if(authorityList==null||authorityList.size()==0){
							_supplier.setLoginPassword("0");
						}else{
							_supplier.setLoginPassword("1");
						}
					}
				}
			}
		}
		modelMap.addAttribute("pageFinder", pageFinder);
		modelMap.addAttribute("merchantsVo", merchantsVo);
		modelMap.addAttribute("isCanAssign", isCanAssign);
		if(isCanAssign!=null&&"true".equalsIgnoreCase(isCanAssign)){
			return "yitiansystem/merchants/authority_manage";
		}
		return "yitiansystem/merchants/merchants_list";
	}*/
	
	/**
	 * 
	 * 跳转到商家列表页面 或者 商家权限管理列表页面
	 * 根据isCanAssign 来确定，true
	 * @Date 2015-06-25
	 * @author luo.q
	 * @throws Exception 
	 */
	@RequestMapping("to_merchantsList")
	public String to_merchantsList(Query query, ModelMap modelMap,
			MerchantsVo merchantsVo, String isCanAssign) {
		SupplierQueryVo vo = new SupplierQueryVo();
		vo.setSupplier(merchantsVo.getSupplier());
		vo.setSupplierCode(merchantsVo.getSupplierCode());
		vo.setIsValid((merchantsVo.getIsValid() == null || merchantsVo.getIsValid() == 0) ? null : merchantsVo.getIsValid());
		vo.setIsInputYougouWarehouse(merchantsVo.getIsInputYougouWarehouse());
		vo.setSupplierYgContacts( merchantsVo.getSupplierYgContacts() );
		if(StringUtils.isNotEmpty( merchantsVo.getSequence() )){
			vo.setSequence( merchantsVo.getSequence() );
		}
		if(StringUtils.isNotEmpty( merchantsVo.getOrderBy() )){
			vo.setOrderBy( merchantsVo.getOrderBy() );
		}
		
		//供应商类型 1：招商 2：普通
		if(  null!= merchantsVo.getType() &&  0<merchantsVo.getType() ){
			switch( merchantsVo.getType() ){
				case MerchantConstant.SUPPLIER_TYPE_MERCHANT:
					vo.setSupplierType( MerchantConstant.SUPPLIER_TYPE_MERCHANT_ZH );
					break;
				default:
					vo.setSupplierType( MerchantConstant.SUPPLIER_TYPE_BELLE_ZH );
					break;
			}
		}
		
		//通过品牌名称查询到No
		if (StringUtils.isNotBlank(merchantsVo.getBrandName())) {
			List<Brand> brands = commodityBaseApiService.getBrandListLikeBrandName("%" + merchantsVo.getBrandName(), (short) 1);
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
		PageFinder<SupplierVo> pageFinder = supplierSpLocalService
				.querySupplierListByPage(vo, _query);
		if (pageFinder != null && CollectionUtils.isNotEmpty(pageFinder.getData())) {
			MerchantUser merchantUser = null;
			List<Map<String, Object>> authorityList=null;
			for (SupplierVo _supplier : pageFinder.getData()) {
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

				// 是招商供应商才查询 -- 【审核商家列表页面特有逻辑】
				if(isCanAssign!=null&&"true".equalsIgnoreCase(isCanAssign)){
					// 根据商家编号查询商家登录信息
					merchantUser = merchantsService.getMerchantsBySuppliceCode(_supplier.getSupplierCode());
					if(merchantUser!=null){
						_supplier.setLoginAccount(merchantUser.getLoginName());	
						try {
							authorityList=merchantsService.getMerchantsAuthorityByUserId(merchantUser.getId());
						
							if(authorityList==null||authorityList.size()==0){
								_supplier.setLoginPassword("0");
							}else{
								_supplier.setLoginPassword("1");
							}
						} catch (SQLException e) {
							logger.error("审核商家列表：读取招商商家{}的权限集合发生异常。",_supplier.getSupplierCode(),e);
							_supplier.setLoginPassword("0");
						}
					}
				}
			}
		}
		modelMap.addAttribute("pageFinder", pageFinder);
		modelMap.addAttribute("merchantsVo", merchantsVo);
		modelMap.addAttribute("isCanAssign", isCanAssign);
		//
		if(isCanAssign!=null&&"true".equalsIgnoreCase(isCanAssign)){
			return "yitiansystem/merchants/authority_manage";
		}
		return "yitiansystem/merchants/merchants_list";
	}
	
    @RequestMapping("exportMerchantsList")
	public void exportMerchantsList(  ModelMap modelMap,MerchantsVo merchantsVo, String isCanAssign,
									HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		SupplierQueryVo vo = new SupplierQueryVo();
		vo.setSupplier(merchantsVo.getSupplier());
		vo.setSupplierCode(merchantsVo.getSupplierCode());
		vo.setIsValid((merchantsVo.getIsValid() == null || merchantsVo.getIsValid() == 0) ? null : merchantsVo.getIsValid());
		vo.setIsInputYougouWarehouse(merchantsVo.getIsInputYougouWarehouse());
		//供应商类型 1：招商 2：普通
		if(  null!= merchantsVo.getType() &&  0<merchantsVo.getType() ){
			switch( merchantsVo.getType() ){
				case MerchantConstant.SUPPLIER_TYPE_MERCHANT:
					vo.setSupplierType( MerchantConstant.SUPPLIER_TYPE_MERCHANT_ZH );
					break;
				default:
					vo.setSupplierType( MerchantConstant.SUPPLIER_TYPE_BELLE_ZH );
					break;
			}
		}
				
		//通过品牌名称查询到No
		if (StringUtils.isNotBlank(merchantsVo.getBrandName())) {
			List<Brand> brands = commodityBaseApiService.getBrandListLikeBrandName("%" + merchantsVo.getBrandName(), (short) 1);
			if (CollectionUtils.isNotEmpty(brands)) {
				List<String> brandNos = new ArrayList<String>();
				for (Brand brand : brands) {
					brandNos.add(brand.getBrandNo());
				}
				vo.setBrandNos(brandNos);
			}
		}
		
		List<SupplierSp> list = supplierSpLocalService.querySupplierList(vo, merchantsVo);
		
		List<Object[]> resultList = new ArrayList<Object[]>();
		if ( null != list && 0<list.size() ) {
			
//			// 一次查询并封装好 key= brandId，value= 分类的list
//			Map<String,List> brandToCatsMap = new HashMap<String,List>();// 
//			MerchantUser merchantUser = null;
			Object[] _obj = null;
			for (SupplierSp _supplier : list) {
				// 查品牌
				List<String> supplyIds = new ArrayList<String>();
				supplyIds.add(_supplier.getId());
				List<BrandVo> _brands = brandcatApi.queryLimitBrandBysupplyId(supplyIds);
				
				String brandNames = "";
				String catNames = "";
				if( null!=_brands  ){
					if ( 1==_brands.size()) {
						// TODO 用缓存实现
						Brand temp = commodityBaseApiService.getBrandByNo(_brands.get(0).getBrandNo());
						if( null!=temp ){
							String name =  temp.getBrandName();
							// step 1
							brandNames = (name == null ? "" :name );
							
							List<Category> thirdcats = commodityBaseApiService
									.getCategoryListByBrandId(temp.getId(), (short) 1, null);
							for (Category category : thirdcats) {
								if (StringUtils.isBlank(category.getStructName()) || category.getStructName().length() != 8)
									continue;
								catNames = catNames+"  \n"+ category.getCatName();// step 2
							}
						}
					}else{
						
						for (BrandVo _brandVo : _brands) {
							Brand temp = commodityBaseApiService.getBrandByNo(_brandVo.getBrandNo());
							if( null!=temp ){
								String name =  temp.getBrandName();
								brandNames = brandNames +(name == null ? "" :(" "+name) );
								_brandVo.setBrandName(name == null ? "" : name);
								_brandVo.setId(temp.getId());
							}
						}
					}
				}
				 
				_obj = new Object[16];
				_obj[0] = _supplier.getSupplier();
				_obj[1] = _supplier.getSupplierCode();
				_obj[2] =  brandNames;
				_obj[3] =  catNames;
				_obj[4] = "";
				_obj[5] = "";
				_obj[6] = "";
				_obj[7] = "";
				_obj[8] = "";
				_obj[9] = "";
				_obj[10] = "";
				_obj[11] = "";
				_obj[12] = "";
				_obj[13] = "";
				_obj[14] = "";
				_obj[15] = "";
				
				resultList.add(_obj);
				
				// 增加 品牌->分类 的行
				if ( null!=_brands && 1< _brands.size() ) {
					for (BrandVo _brandVo : _brands) {
						String brandName = _brandVo.getBrandName();
						// 获取分类
						Object[] _objForBrand = this.newRowForBrand( brandName );
						if(!StringUtils.isEmpty(_brandVo.getId())){
							List<Category> thirdcats = commodityBaseApiService
									.getCategoryListByBrandId(_brandVo.getId(), (short) 1, null);
							
							if( null!= thirdcats && 0<thirdcats.size() ){
								
								for (Category category : thirdcats) {
									if (StringUtils.isBlank(category.getStructName()) || category.getStructName().length() != 8)
										continue;
									
									_objForBrand[3] = _objForBrand[3]+"  \n"+category.getCatName();
									
								}
								
							}
						}
						resultList.add(_objForBrand);
					}
				}
				
			}
		}
	   
		ExportHelper exportHelper = new ExportHelper();
     
        String sheetName = DateUtil.formatDateByFormat(new Date(),"yyyy-MM-dd");
        String fileRealPath = request.getSession().getServletContext().getRealPath( Constant.TEMPLATE_PATH );
        String templatePath = fileRealPath +"/"+ Constant.MERCHANT_XLS;
        int[] params = {1,16};
        Map<Integer,Integer> indexMap = new HashMap<Integer,Integer>();
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
		exportHelper.doExport( resultList, templatePath, Constant.MERCHANT_XLS,
								sheetName, null, params, null, null, indexMap, false, response);
		
	}
	
	private Object[] newRowForBrand(String brandName){
		
		Object[] _obj = new Object[16];
		
		_obj[0] = "";
		_obj[1] = "";
		_obj[2] =  brandName;
		_obj[3] = "";
		_obj[4] = "";
		_obj[5] = "";
		_obj[6] = "";
		_obj[7] = "";
		_obj[8] = "";
		_obj[9] = "";
		_obj[10] = "";
		_obj[11] = "";
		_obj[12] = "";
		_obj[13] = "";
		_obj[14] = "";
		_obj[15] = "";
		
		return _obj;
	}
	
	@ResponseBody
	@RequestMapping("/getInventoryName")
	public String getInventoryName(@RequestParam String inventoryCode){
		ResultMsg resultMsg = new ResultMsg();
		if(inventoryCode!=null){
			Map<String,Object> map = warehouseCacheService.getwarehouseByWareCode(inventoryCode);
			resultMsg.setReCode("0");
			resultMsg.setReObj(map.get("warehouseName"));
		}else{
			resultMsg.setReCode("1");
			resultMsg.setReObj("根据仓库编号查询仓库名称失败，仓库编码为空！");
			logger.error("根据仓库编号查询仓库名称失败，仓库编码码为空！");
		}
		return JSONObject.fromObject(resultMsg).toString();
	}

	/**
	 * 
	 * 从招商信息跳转到添加联系人页面
	 * 
	 * @Date 2012-03-05
	 * @author wang.m
	 */
	@RequestMapping("to_linkmanList")
	public String to_linkmanList(Query query, ModelMap modelMap,
			MerchantsVo merchantsVo, String supplierSpId) {
		ContactsVoLocal vo = new ContactsVoLocal();
		vo.setSupplier(merchantsVo.getSupplier());
		vo.setSupplierCode(merchantsVo.getSupplierCode());
		vo.setTelePhone(merchantsVo.getTelePhone());
		vo.setEmail(merchantsVo.getEmail());
		vo.setContact(merchantsVo.getContact());
		vo.setType(merchantsVo.getType());
		vo.setSupplyId(StringUtils.isNotBlank(supplierSpId) ? supplierSpId : null);
		//com.yougou.merchant.api.common.PageFinder<ContactsVo> pageFinder = contactApi.queryContactListByVo(vo, _query);
		PageFinder<Map<String,Object>> pageFinder = merchantContactLocalService.getContactList(vo, query);
		modelMap.addAttribute("pageFinder", pageFinder);
		modelMap.addAttribute("merchantsVo", vo);
		modelMap.addAttribute("supplierSpId", vo.getSupplyId());
		return "yitiansystem/merchants/supplier_contactsp_list";
	}

	/**
	 * 
	 * 跳转到添加联系人页面
	 * 
	 * @Date 2012-03-05
	 * @author wang.m
	 */
	@RequestMapping("to_SupplierContactt")
	public String to_SupplierContactt(ModelMap modelMap, String flag,
			String supplierId) {
		if (StringUtils.isNotBlank(supplierId)) {
			// 根据供应商Id查询供应商名称
			String supplierName = supplierSpLocalService
					.getSupplierNameById(supplierId);
			modelMap.addAttribute("supplierId", supplierId);
			modelMap.addAttribute("supplierName", supplierName);
		}
		// 判断区分是普通供应商还是招商供应商页面 1 普通 2 招商
		modelMap.addAttribute("flag", flag);
		return "yitiansystem/merchants/add_supplier_contactsp";
	}

	/**
	 * 
	 * 跳转到添加合同页面
	 * 
	 * @Date 2012-03-05
	 * @author wang.m
	 */
	@RequestMapping("to_contractList")
	public String to_contractList(ModelMap modelMap, String supplierId) {
		if (StringUtils.isNotBlank(supplierId)) {
			// 根据供应商Id查询供应商名称
			String supplierName = merchantsService
					.getSupplerNameById(supplierId);
			modelMap.addAttribute("supplierId", supplierId);
			modelMap.addAttribute("supplierName", supplierName);
		}
		return "yitiansystem/merchants/add_contractList";
	}

	/**
	 * 
	 * 根据合同ID加载修改页面数据
	 * 
	 * @Date 2012-03-06
	 * @author wang.m
	 * @throws Exception
	 */
	@RequestMapping("initial_supplierContract")
	public String initial_supplierContract(Query query, ModelMap modelMap,
			String id, String supplierSpId) throws Exception {
		// 判断合同id是否为空
		if (StringUtils.isNotBlank(id)) {
			SupplierContract supplierContract = merchantsService
					.initial_supplierContract(query, modelMap, id);
			modelMap.addAttribute("contract", supplierContract);
		}
		if (StringUtils.isNotBlank(supplierSpId)) {
			modelMap.addAttribute("supplierId", supplierSpId);
		} else {
			modelMap.addAttribute("supplierId", null);
		}
		return "yitiansystem/merchants/update_contractList";
	}

	/**
	 * 
	 * 修改合同信息
	 * 
	 * @Date 2012-03-06
	 * @author wang.m
	 * @throws Exception
	 */
	@RequestMapping("update_supplierContract")
	public String update_supplierContract(Query query, ModelMap model,
			HttpServletRequest request,
			DefaultMultipartHttpServletRequest multipartRequest,
			String effective, String failure,
			SupplierContract supplierContract, String suplierName,
			String supplierSpId) throws Exception {
		MultipartFile multipartFile = multipartRequest.getFile("contractFile");
		String fileName = multipartFile.getOriginalFilename();
		String filePath = null;
		File file = null;
		FileOutputStream fos = null;
		try {
			if (multipartFile != null) {
				if (!multipartFile.isEmpty()) {
					if (multipartFile.getSize() > 5000000) {
						model.addAttribute("message", "文件大小超过50M!");
					}

					byte[] bytes = multipartFile.getBytes();
					file = new File(multipartFile.getOriginalFilename());
					fos = new FileOutputStream(file);
					fos.write(bytes);

					filePath = request.getSession().getServletContext()
							.getRealPath(File.separator + "upload")
							+ File.separator + file.getName();
					this.moveFile(file, filePath);
					FtpUtils ftpUtils = new FtpUtils();
					ftpUtils.connectServer("117.121.50.55", 9999, "mobilepic",
							"CsWaDCw!#$()D", "/contract");
					FileInputStream fis = new FileInputStream(filePath);
					ftpUtils.uploadContract(
							fis,
							new String(fileName.getBytes("UTF-8"), "iso-8859-1"));
					ftpUtils.closeConnect();
					fos.close();
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("message", "上传文件失败!");
		} finally {
			try {
				// 判断供应商id是否存在
				if (StringUtils.isNotBlank(suplierName)) {
					supplierContract.setAttachment(fileName);// 附近文件名名称
					boolean bool = merchantsService.update_supplierContract(
							query, model, effective, failure, supplierContract,
							suplierName, request);
					if (bool) {
						model.addAttribute("message", "修改成功!");
						model.addAttribute("refreshflag", "1");
						model.addAttribute(
								"methodName",
								"/yitiansystem/merchants/businessorder/to_supplierContractList.sc?supplierSpId="
										+ supplierSpId);
						return "yitiansystem/merchants/merchants_message";
					} else {
						model.addAttribute("message", "修改失败!");
						model.addAttribute("refreshflag", "1");
						model.addAttribute("methodName",
								"/yitiansystem/merchants/businessorder/to_supplierContractList.sc");
						return "yitiansystem/merchants/merchants_message";
					}
				}
			} catch (IOException e) {
				logger.error("添加合同信息失败", e);
				model.addAttribute("message", "添加合同信息失败!");
			}
		}

		return "";
	}

	/**
	 * 
	 * 判断该商家的该种联系人是否已创建信息
	 */
	@ResponseBody
	@RequestMapping("exist_linkman")
	public String queryExistContactOfThisType( SupplierContact contact, String supplierSpId,
			HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		if (StringUtils.isNotBlank(supplierSpId) && null!= contact && null!=contact.getType() ) {
			contact.setSupplyId(supplierSpId);
			boolean isExist = false;
			try{
				isExist = this.merchantServiceNew.queryExistContactOfThisType(contact);
				
				if(isExist){
					jsonObject.put("success","true");
					jsonObject.put("message", "该商家已绑定该种类型联系人，请更换商家或联系人类型再试!");
				}else{
					jsonObject.put("success","false");
				}
			}catch(Exception e){
				jsonObject.put("success","500");
				jsonObject.put("message", "校验商家是否已经绑定该类联系人发生异常!");
				logger.error( "校验商家是否已经绑定该类联系人发生异常！",e );
			}
		}
		return jsonObject.toString();
	}
	/**
	 * 
	 * 添加保存联系人信息
	 * 
	 * @Date 2012-03-06
	 * @author wang.m
	 * @throws Exception
	 */
	@RequestMapping("add_linkmanList")
	public String add_linkmanList(ModelMap modelMap, SupplierContact contact, String supplierSpId, String flag, String updateAndAdd,
			HttpServletRequest request) {
		SystemmgtUser user = GetSessionUtil.getSystemUser(request);
		// 判断供应商name是否存在
		if (StringUtils.isNotBlank(supplierSpId) && null!= contact && null!=contact.getType() ) {
			//boolean bool = merchantsService.add_linkmanList(supplierContactSp, suplierName, user.getUsername());
			contact.setId(UUIDGenerator.getUUID());
			contact.setSupplyId(supplierSpId);
			
			//  校验该商家是否已经绑定过该类型的联系人
			
			boolean isExist = false;
			try{
				isExist = this.merchantServiceNew.queryExistContactOfThisType(contact);
				
				if(isExist){
					modelMap.addAttribute("message", "该商家已绑定该种类型联系人，请更换商家或联系人类型再试!");
					modelMap.addAttribute("refreshflag", "1");
					modelMap.addAttribute("methodName",
							"/yitiansystem/merchants/businessorder/to_linkmanList.sc?supplierSpId="
											+ supplierSpId);
					return "yitiansystem/merchants/merchants_message";
				}
				
				boolean bool = this.merchantServiceNew.addLinkmanList(contact, user.getUsername());
				
				if (bool) {
					modelMap.addAttribute("message", "添加成功!");
					modelMap.addAttribute("refreshflag", "1");
					if (StringUtils.isNotBlank(flag) && "1".equals(flag)) {
						modelMap.addAttribute("methodName",
								"/supply/manage/supplier/toSupplierContact.sc?type=2&supplier="
										+ supplierSpId);
					} else {
						if (StringUtils.isNotBlank(updateAndAdd)
								&& "1".equals(updateAndAdd)) {
							modelMap.addAttribute("methodName",
									"/yitiansystem/merchants/businessorder/to_linkmanList.sc?");
						} else {
							modelMap.addAttribute("methodName",
									"/yitiansystem/merchants/businessorder/to_linkmanList.sc?supplierSpId="
											+ supplierSpId);
						}
					}
			
					return "yitiansystem/merchants/merchants_message";
				
				} else {
					modelMap.addAttribute("message", "添加联系人信息到数据库保存失败!");
					modelMap.addAttribute("refreshflag", "1");
					modelMap.addAttribute("methodName",
							"/yitiansystem/merchants/businessorder/to_linkmanList.sc");
					return "yitiansystem/merchants/merchants_message";
				}
			}catch(Exception e){
				modelMap.addAttribute("message", "操作失败，后台异常！");
				modelMap.addAttribute("refreshflag", "1");
				modelMap.addAttribute("methodName",
						"/yitiansystem/merchants/businessorder/to_linkmanList.sc");
				logger.error("保存商家联系人出现异常：",e);
				
				return "yitiansystem/merchants/merchants_message";
			}
		}
		return "yitiansystem/merchants/supplier_contactsp_list";
	}

	/**
	 * 
	 * 根据联系人ID加载修改页面数据
	 * 
	 * @Date 2012-03-06
	 * @author wang.m
	 * @throws Exception
	 */
	@RequestMapping("initial_linkmanList")
	public String initial_linkmanList(Query query, ModelMap modelMap,
			String flag, String id, String supplierSpId) {
		// 判断联系id是否为空
		if (StringUtils.isNotBlank(id)) {
			SupplierContactSp supplierContactSp = merchantsService
					.initial_linkmanList(query, modelMap, id);
			modelMap.addAttribute("contactSp", supplierContactSp);
		}
		if (StringUtils.isNotBlank(supplierSpId)) {
			modelMap.addAttribute("supplierId", supplierSpId);
		} else {
			modelMap.addAttribute("supplierId", null);
		}
		modelMap.addAttribute("flag", flag);
		return "yitiansystem/merchants/update_supplier_contactsp";
	}

	/**
	 * 
	 * 修改联系人信息
	 * 
	 * @Date 2012-03-06
	 * @author wang.m
	 * @throws Exception
	 */
	@RequestMapping("update_linkmanList")
	public String update_linkmanList(Query query, ModelMap modelMap,
			SupplierContact contact, String supplierSpId, String flag, String updateAndAdd,
			HttpServletRequest request) throws Exception {
		SystemmgtUser user = GetSessionUtil.getSystemUser(request);
		// 判断供应商id是否存在
		if (StringUtils.isNotBlank(supplierSpId)) {
			contact.setSupplyId(supplierSpId);
			boolean bool = this.merchantServiceNew.updateLinkmanList(contact, user.getUsername());
			if (bool) {
				modelMap.addAttribute("message", "修改联系人信息成功!");
				modelMap.addAttribute("refreshflag", "1");
				if (StringUtils.isNotBlank(flag) && "1".equals(flag)) {
					modelMap.addAttribute("methodName",
							"redirect:/supply/manage/supplier/toSupplierContact.sc?type=2&supplier="
									+ supplierSpId);
				} else {
					if (StringUtils.isNotBlank(updateAndAdd)
							&& "1".equals(updateAndAdd)) {
						modelMap.addAttribute("methodName",
								"/yitiansystem/merchants/businessorder/to_linkmanList.sc?");
					} else {
						modelMap.addAttribute("methodName",
								"/yitiansystem/merchants/businessorder/to_linkmanList.sc?supplierSpId="
										+ supplierSpId);
					}

				}
				return "yitiansystem/merchants/merchants_message";
			} else {
				modelMap.addAttribute("message", "修改联系人信息失败!");
				modelMap.addAttribute("refreshflag", "1");
				modelMap.addAttribute("methodName",
						"/yitiansystem/merchants/businessorder/to_linkmanList.sc");
				return "yitiansystem/merchants/merchants_message";
			}
		}
		return "";
	}

	/**
	 * 
	 * 判断联系人表中是否存在该手机号码
	 * 
	 * @param telePhone
	 *            电子邮箱
	 * @Date 2012-03-06
	 * @author wang.m
	 */
	@ResponseBody
	@RequestMapping("existsTelePhone")
	public String existsTelePhone(Query query, ModelMap modelMap, String telePhone) {
		boolean bool = merchantsService.existsTelePhone(telePhone);
		return bool ? "success" : "fail";
	}

	/**
	 * 
	 * 判断联系人表中是否存在该电子邮箱
	 * 
	 * @param email
	 *            电子邮箱
	 * @Date 2012-03-06
	 * @author wang.m
	 */
	@ResponseBody
	@RequestMapping("existsEmail")
	public String existsEmail(Query query, ModelMap modelMap, String email) {
		boolean bool = merchantsService.existsEmail(email);
		return bool ? "success" : "fail";
	}

	/**
	 * 
	 * 跳转到添加合同页面
	 * 
	 * @Date 2012-03-05
	 * @author wang.m
	 * @throws Exception 
	 
	@RequestMapping("to_supplierContractList")
	public String to_supplierContractList(Query query, ModelMap modelMap,
			MerchantsVo merchantsVo, String supplierSpId) throws Exception {
		com.yougou.merchant.api.common.Query _query = new com.yougou.merchant.api.common.Query(query.getPageSize());
		_query.setPage(query.getPage());
		
		ContractVo vo = new ContractVo();
		vo.setSupplier(merchantsVo.getSupplier());
		vo.setSupplierCode(merchantsVo.getSupplierCode());
		vo.setContractNo(merchantsVo.getContractNo());
		vo.setIsValid((merchantsVo.getIsValid() == null || merchantsVo.getIsValid() == 0) ? null : merchantsVo.getIsValid());
		if (StringUtils.isNotBlank(merchantsVo.getEffectiveDate()))
			vo.setEffectiveDate(DateUtil.getdate1(merchantsVo.getEffectiveDate()));
		if (StringUtils.isNotBlank(merchantsVo.getFailureDate()))
			vo.setFailureDate(DateUtil.getdate1(merchantsVo.getFailureDate()));
		vo.setSupplierId(StringUtils.isNotBlank(supplierSpId) ? supplierSpId : null);
		com.yougou.merchant.api.common.PageFinder<ContractVo> pageFinder = contactApi.queryContractListByVo(vo, _query);
		
		modelMap.addAttribute("pageFinder", pageFinder);
		modelMap.addAttribute("supplierSpId", vo.getSupplierId());
		return "yitiansystem/merchants/supplier_contract_list";
	}
	 */
	/**
	 * 
	 * 判断商家登录名称是否已经存在
	 * 
	 * @Date 2012-03-07
	 * @author wang.m
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping("exitsLoginAccount")
	public String exitsLoginAccount(String loginAccount) {
		boolean bool = false;
		try {
			String name = new String(loginAccount.getBytes("ISO-8859-1"), "UTF-8");
			bool = merchantsService.exitsLoginAccount(name);
		} catch (UnsupportedEncodingException e) {
			logger.error(MessageFormat.format("存在此账户{}。", loginAccount), e);
		}
		return bool ? "sucuess" : "faile";
	}

	/**
	 * 加载商家信息
	 * 
	 * @author wang.m
	 * @Date 2012-03-06
	 */
	@RequestMapping("/to_suppliersp")
	public String to_suppliersp(ModelMap model, String supplierCode) {
		List<SupplierSp> suppliersps = merchantsService
				.getSupplies(supplierCode);
		model.addAttribute("suppliersps", suppliersps);
		model.addAttribute("supplierCode", supplierCode);
		return "yitiansystem/merchants/suppliersp_list";
	}

	/**
	 * 添加合同信息
	 * 
	 * @author wang.m
	 * @throws Exception
	 * @Date 2012-03-06
	 */
	@RequestMapping("/add_supplierContract")
	public String add_supplierContract(Query query, ModelMap model,
			HttpServletRequest request,
			DefaultMultipartHttpServletRequest multipartRequest,
			String effective, String failure,
			SupplierContract supplierContract, String suplierName,
			String supplierSpId, InputStream input, String flag)
			throws Exception {
		MultipartFile multipartFile = multipartRequest.getFile("contractFile");
		String fileName = multipartFile.getOriginalFilename();
		String filePath = null;
		File file = null;
		FileOutputStream fos = null;
		try {
			if (multipartFile != null) {
				if (!multipartFile.isEmpty()) {
					if (multipartFile.getSize() > 5000000) {
						model.addAttribute("message", "文件大小超过50M!");
					}
					FTPClientConfig comfig = new FTPClientConfig(
							FTPClientConfig.SYST_NT);
					comfig.setServerLanguageCode("zh");
					byte[] bytes = multipartFile.getBytes();
					file = new File(multipartFile.getOriginalFilename());
					fos = new FileOutputStream(file);
					fos.write(bytes);

					filePath = request.getSession().getServletContext()
							.getRealPath(File.separator + "upload")
							+ File.separator + file.getName();
					this.moveFile(file, filePath);
					FtpUtils ftpUtils = new FtpUtils();
					ftpUtils.connectServer("117.121.50.55", 9999, "mobilepic",
							"CsWaDCw!#$()D", "/contract");
					FileInputStream fis = new FileInputStream(filePath);
					ftpUtils.uploadContract(
							fis,
							new String(fileName.getBytes("UTF-8"), "iso-8859-1"));
					ftpUtils.closeConnect();
					fos.close();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("message", "上传文件失败!");
		} finally {
			try {
				// 判断供应商id是否存在
				if (StringUtils.isNotBlank(suplierName)) {
					supplierContract.setAttachment(fileName);// 附近文件名名称
					boolean bool = merchantsService.add_supplierContract(query,
							model, effective, failure, supplierContract,
							suplierName, request);
					if (bool) {
						model.addAttribute("message", "添加合同信息成功!");
						model.addAttribute("refreshflag", "1");
						if (StringUtils.isNotBlank(flag)) {
							model.addAttribute("methodName",
									"/yitiansystem/merchants/businessorder/to_supplierContractList.sc");
						} else {
							model.addAttribute(
									"methodName",
									"/yitiansystem/merchants/businessorder/to_supplierContractList.sc?supplierSpId="
											+ supplierSpId);
						}
						return "yitiansystem/merchants/merchants_message";
					} else {
						model.addAttribute("message", "添加合同信息失败!");
						model.addAttribute("refreshflag", "1");
						model.addAttribute("methodName",
								"/yitiansystem/merchants/businessorder/to_supplierContractList.sc");
						return "yitiansystem/merchants/merchants_message";
					}
				}
			} catch (IOException e) {
				logger.error("添加合同信息失败", e);
				model.addAttribute("message", "添加合同信息失败!");
			}
		}
		return "yitiansystem/merchants/merchants_message";

	}

	/**
	 * 下载合同附件
	 * 
	 * @param response
	 * @param request
	 */

	@RequestMapping("/downMerchantsContractFile")
	public void downMerchantsContractFile(HttpServletResponse response,
			HttpServletRequest request, String fileName,
			OutputStream outputStream) {
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			FTPClientConfig comfig = new FTPClientConfig(
					FTPClientConfig.SYST_NT);
			comfig.setServerLanguageCode("zh");
			//2015-05-06 li_j1
			FileFtpUtil ftp = new FileFtpUtil(sysconfigProperties.getContractFtpServer(), 
					Integer.valueOf(sysconfigProperties.getContractFtpPort()),
					sysconfigProperties.getContractFtpUsername(), sysconfigProperties.getContractFtpPassword());
			ftp.login();

			fileName = new String(fileName.getBytes("ISO8859_1"), "UTF-8");
			// 这个就就是弹出下载对话框的关键代码
			response.setContentType("APPLICATION/OCTET-STREAM ");
			response.setHeader("Content-disposition", "attachment;filename="
					+ new String(fileName.getBytes("UTF-8"), "ISO8859_1"));
			ftp.downRemoteFile(sysconfigProperties.getContractFtpPath() + "/" + fileName, os);
			os.flush();
			ftp.logout();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/**
	 * 
	 * @param file
	 * @param filePath
	 * @return
	 */
	private boolean moveFile(File file, String filePath) {
		boolean susscess = false;
		FileUtil fileUtil = new FileUtil();
		fileUtil.moveFile(file.getPath(), filePath);
		susscess = true;
		return susscess;
	}

	/**
	 * 跳转到添加品牌权限页面
	 * 
	 * @author wang.m
	 * @Date 2012-03-07
	 */
	@RequestMapping("/to_addBank")
	public String to_addBank(ModelMap model, String brandName, String flag) {
		List<com.yougou.pc.model.brand.Brand> brandList;
		if (StringUtils.isNotBlank(brandName)) {
			brandList = commodityBaseApiService.getBrandListLikeBrandName(
					"%" + brandName, NumberUtils.SHORT_ONE);
		} else {
			brandList = commodityBaseApiService
					.getAllBrands(NumberUtils.SHORT_ONE);
		}

		model.addAttribute("brandList", brandList);
		// 判断跳转到添加还是修改页面 1 添加 2修改
		model.addAttribute("flag", flag);
		model.addAttribute("brandName", brandName);
		return "yitiansystem/merchants/to_addbank_list";
	}

	/**
	 * 跳转到添加分类权限页面
	 * 
	 * @author wang.m
	 * @Date 2012-03-07
	 */
	@RequestMapping("/to_addCat")
	public String to_addCat(ModelMap model, String flag, String brandNos) {
		List<com.yougou.pc.model.brand.Brand> brands = new ArrayList<com.yougou.pc.model.brand.Brand>();
		String[] _brandNos = StringUtils.split(brandNos, ",");
		for (int i = 0; _brandNos != null && i < _brandNos.length; i++) {
			com.yougou.pc.model.brand.Brand brand = commodityBaseApiService
					.getBrandByNo(_brandNos[i]);
			brands.add(brand);
		}

		model.addAttribute("lstBrand", brands);
		// 判断跳转到添加还是修改页面 1 添加 2修改
		model.addAttribute("flag", flag);
		return "yitiansystem/merchants/to_addcat_list";
	}

	@ResponseBody
	@RequestMapping("/queryBrandCat")
	public String queryCatByBrandNo(String brandNo) {
		List<Category> list = commodityMerchantApiService
				.getAllCategoryByBrandNo(brandNo);
		if (list == null || list.isEmpty())
			return null;

		for (Category category : list) {
			category.setParentId(this.subCategory(category.getStructName()));
		}

		JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(new PropertyFilter() {
			@Override
			public boolean apply(Object source, String name, Object value) {
				if (name.equals("id") || name.equals("catName")
						|| name.equals("structName") || name.equals("catLeave")
						|| name.equals("catNo") || name.equals("parentId")) {
					return false;
				}
				return true;
			}
		});
		JSONArray jsonArray = JSONArray.fromObject(list, config);
		return jsonArray.toString();
	}

	/**
	 * 截取分类的父级
	 * 
	 * @param structName
	 *            10-11-12
	 * @return 10-11
	 */
	private String subCategory(String structName) {
		if (StringUtils.isBlank(structName))
			return null;

		int index = structName.lastIndexOf("-");
		if (index == -1) {
			return "0";
		}

		return structName.substring(0, index);
	}

	/**
	 * 获取子分类的json字符串
	 * 
	 * @author wang.m
	 * @Date 2012-03-07
	 */
	@RequestMapping(value = "getChildCat", method = RequestMethod.POST)
	@ResponseBody
	public String getChildCat(@RequestParam("value") String structName) {
		List<Category> catList = commodityBaseApiService.getChildCategoryByStructName(structName);
		JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(new PropertyFilter() {
			@Override
			public boolean apply(Object source, String name, Object value) {
				if (name.equals("id") || name.equals("catName")
						|| name.equals("structName") || name.equals("no")) {
					return false;
				}
				return true;
			}
		});
		JSONArray jsonArray = JSONArray.fromObject(catList, config);
		return jsonArray.toString();
	}

	/**
	 * 跳转到到分配用户角色页面
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/to_addMerchants_Authority")
	public String to_addMerchants_Authority(String id, ModelMap model)
			throws Exception {
		// 查询所有商家角色列表
		List<MerchantsRole> merchantsRoleList = merchantsService
				.getMerchantsRoleList();
		model.addAttribute("merchantId", id);
		// 根据商家Id查询商家已拥有的商家权限
		List<Map<String, Object>> authorityMap = merchantsService
				.getMerchantsRoleDaoImplById(id);
		model.addAttribute("merchantsRoleList", merchantsRoleList);
		model.addAttribute("authorityMap", authorityMap);
		return "yitiansystem/merchants/add_merchants_role";
	}

	/**
	 * 给商家添加权限
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/add_Merchants_Authority")
	public String add_Merchants_Authority(String uid, String authority,
			ModelMap model) throws Exception {
		boolean bool = merchantsService.saveUserAuthority(uid, authority);
		if (bool) {
			model.addAttribute("message", "添加成功!");
			model.addAttribute("refreshflag", "1");
			model.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_merchants_user_list.sc");
			return "yitiansystem/merchants/merchants_message";
		} else {
			model.addAttribute("message", "添加失败!");
			model.addAttribute("refreshflag", "1");
			model.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_merchants_user_list.sc");
			return "yitiansystem/merchants/merchants_message";
		}
	}

	/**
	 * 跳转到到分配商家权限页面
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/assignAuthority")
	public String assignAuthority(ModelMap model, String id,
			String yougouAdminId) throws Exception {
		// 根据供应商Id查询商家超级管理员账户Id
		String userId = null;
		if (StringUtils.isNotBlank(yougouAdminId)) {
			userId = yougouAdminId;
		} else {
			userId = merchantsService.getMerchantUserBySupplierId(id);
		}
		if (StringUtils.isNotBlank(userId)) {
			// 查询所有商家资源列表
			List<MerchantsAuthority> merchantsAuthorityList = merchantsService
					.getMerchantsAuthorityList(userId);
			model.addAttribute("merchantsAuthorityList", merchantsAuthorityList);
			// 保存商家账号ID
			model.addAttribute("userId", userId);
			// 根据商家帐号Id查询商家已拥有的资源列表
			List<Map<String, Object>> authorityMap = merchantsService
					.getMerchantsAuthorityByUserId(userId);
			model.addAttribute("authorityMap", authorityMap);
		}
		return "yitiansystem/merchants/assign_merchants_user_authority";
	}

	/**
	 * 给商家帐号分配权限
	 * 
	 * @throws Exception
	 */
	@RequestMapping("/assign_Merchants_User_Authority")
	public String assign_Merchants_User_Authority(String userId,
			String authority, ModelMap model, HttpServletRequest request)
			throws Exception {
		boolean bool = merchantsService.addUserAuthority(userId, authority,
				GetSessionUtil.getSystemUser(request));
		if (bool) {
			merchantsService.loadAuthResource(userId);
			model.addAttribute("message", "添加成功!");
			model.addAttribute("refreshflag", "1");
			model.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_merchantsList.sc?isCanAssign=true");
			return "yitiansystem/merchants/merchants_message";
		} else {
			model.addAttribute("message", "添加失败!");
			model.addAttribute("refreshflag", "1");
			model.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_merchantsList.sc?isCanAssign=true");
			return "yitiansystem/merchants/merchants_message";
		}
	}

	/**
	 * 删除商家信息
	 * 
	 * @author wang.M
	 * @throws Exception
	 * 
	 */
	@ResponseBody
	@RequestMapping("delete_merchants")
	public String delete_merchants(ModelMap model, String id,
			String supplierCode, HttpServletRequest req) throws Exception {
		Supplier supplier = new Supplier();
		supplier.setId(id);
		supplier.setDeleteFlag(0);
		supplier.setUpdateDate(new Date());
		supplier.setUpdateTimestamp(System.currentTimeMillis());
		Integer result = this.purchaseApiService.updateSupplier(supplier);
		
		SystemmgtUser sysuser = GetSessionUtil.getSystemUser(req);
		
		//添加供应商修改日志
		SupplierUpdateHistory history = new SupplierUpdateHistory();
		history.setId(UUIDGenerator.getUUID());
		history.setOperationTime(new Date());
		history.setOperator(sysuser == null ? null : sysuser.getUsername());
		history.setProcessing("标记删除");
		history.setUpdateField("delete_flag");
		history.setSupplierId(id);
		purchaseApiService.insertSupplierLog(history);
		if (result > 0) {
			//删除商家关联的信息
			this.merchantsService.delete_merchants(id, supplierCode);
			return "success";
		} else {
			return "failure";
		}
	}

	/**
	 * 跳转到商家用户权限管理页面
	 * 
	 * @author wang.M
	 * @Date 2012-03-26
	 */
	@RequestMapping("to_merchants_authority")
	public String to_merchants_authority(ModelMap model, Query query,
			MerchantsAuthority merchantsAuthority) throws Exception {
		PageFinder<Map<String, Object>> pageFinder = merchantsService
				.queryMerchantsAuthorityList(query, merchantsAuthority);
		model.addAttribute("pageFinder", pageFinder);
		model.addAttribute("merchantsAuthority", merchantsAuthority);
		// 查询商家信息集合
		return "yitiansystem/merchants/merchants_authority_list";
	}
	
	@RequestMapping("viewAuthorityOperationLog")
	public String viewAuthorityOperationLog(ModelMap model, String merchantCode,com.yougou.merchant.api.common.Query query) throws Exception {
		// 两套逻辑
		if(StringUtils.isEmpty(merchantCode) ){
			PageFinder<MerchantOperationLog> pageFinder = merchantOperatorApi
					.queryMerchantOperationLog("authorityLog", query);
			model.addAttribute("pageFinder", pageFinder);
		}else{
			com.belle.infrastructure.orm.basedao.Query _query = new com.belle.infrastructure.orm.basedao.Query(query.getPageSize());
			_query.setPage(query.getPage());
			PageFinder<com.belle.yitiansystem.merchant.model.pojo.MerchantOperationLog> pageFinder = 
					merchantOperationLogService.queryMerchantOperationLogByOperationType(merchantCode,com.yougou.merchant.api.supplier.vo.MerchantOperationLog.OperationType.ACCOUNT, _query);
			model.addAttribute("pageFinder", pageFinder);
		}
		return "yitiansystem/merchants/view_auth_operation_log";
	}

	/**
	 * 跳转修改商家资源页面
	 * 
	 * @author wang.M
	 * @Date 2012-03-26
	 */
	@RequestMapping("to_update_merchants_authority")
	public String to_update_merchants_authority(ModelMap model, String id)
			throws Exception {
		MerchantsAuthority authority = merchantsService
				.getMerchantsAuthorityByid(id);
		List<MerchantsAuthority> merchantsAuthority=merchantsService.getMerchantsAuthorityByPid("0");
		model.addAttribute("merchantsAuthority",merchantsAuthority);
		model.addAttribute("authority", authority);
		return "yitiansystem/merchants/update_merchants_authority";
	}

	/**
	 * 修改商家资源页面
	 * 
	 * @author wang.M
	 * @Date 2012-03-26
	 */
	@RequestMapping("update_merchants_authority")
	public String update_merchants_authority(HttpServletRequest request,ModelMap model,
			MerchantsAuthority merchantsAuthority) throws Exception {
		SystemmgtUser user = GetSessionUtil.getSystemUser(request);
		if(merchantsAuthority.getParentId()!=null&&"0".equals(merchantsAuthority.getParentId())){
			merchantsAuthority.setAuthrityURL("#");
		}
		MerchantsAuthority old=merchantsService.getMerchantsAuthorityByid(merchantsAuthority.getId());
		String oldAuthName=old.getAuthrityName();
		String oldUrl=old.getAuthrityURL();
		String oldNo=old.getSortNo()+"";
		String oldAuthModel=old.getParentId();
		MerchantsAuthority preMerchantsAuthority=merchantsService.getMerchantsAuthorityByid(oldAuthModel);
		MerchantsAuthority newMerchantsAuthority=merchantsService.getMerchantsAuthorityByid(merchantsAuthority.getParentId());
		boolean bool = merchantsService
				.updateMerchantsAuthority(merchantsAuthority);
		if (bool) {
			
			com.yougou.merchant.api.supplier.vo.MerchantOperationLog operationLog=new com.yougou.merchant.api.supplier.vo.MerchantOperationLog();
			operationLog.setId(UUIDUtil.getUUID());
			operationLog.setMerchantCode("authorityLog");
			operationLog.setOperated(new Date());
			operationLog.setOperationNotes("原资源:\n【(资源名称)"+oldAuthName+",(所处模块)"+preMerchantsAuthority.getAuthrityName()+",(路径)"+oldUrl+",(排序号)"+oldNo+"】\n修改后资源:\n【(资源名称)"+merchantsAuthority.getAuthrityName()+",(所处模块)"+newMerchantsAuthority.getAuthrityName()+",(路径)"+merchantsAuthority.getAuthrityURL()+"(排序号)"+merchantsAuthority.getSortNo()+"】");
			operationLog.setOperationType(OperationType.AUTH_UPDATE);
			operationLog.setOperator(user.getUsername());
			merchantsApi.saveMerchantOperationLog(operationLog);
			
			model.addAttribute("message", "修改成功!");
			model.addAttribute("refreshflag", "1");
			model.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_merchants_authority.sc");
			return "yitiansystem/merchants/merchants_message";
		} else {
			model.addAttribute("message", "修改失败!");
			model.addAttribute("refreshflag", "1");
			model.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_merchants_authority.sc");
			return "yitiansystem/merchants/merchants_message";
		}
	}

	/**
	 * 跳转添加商家资源页面
	 * 
	 * @author wang.M
	 * @Date 2012-03-26
	 */
	@RequestMapping("to_add_Merchants_authority")
	public String to_add_Merchants_authority(ModelMap model) {
		List<MerchantsAuthority> merchantsAuthority=merchantsService.getMerchantsAuthorityByPid("0");
		model.addAttribute("merchantsAuthority",merchantsAuthority);
		return "yitiansystem/merchants/add_merchants_authority";
	}

	/**
	 * 添加商家资源页面
	 * 
	 * @author wang.M
	 * @throws Exception 
	 * @Date 2012-03-26
	 */
	@RequestMapping("add_merchants_authority")
	public String add_merchants_authority(HttpServletRequest request,ModelMap model,
			MerchantsAuthority merchantsAuthority) throws Exception {
		SystemmgtUser user = GetSessionUtil.getSystemUser(request);
		if(merchantsAuthority.getParentId()!=null&&"0".equals(merchantsAuthority.getParentId())){
			merchantsAuthority.setAuthrityURL("#");
		}
		merchantsAuthority.setAuthrityModule(-1);
		boolean bool = merchantsService.addMerchantsAuthority(merchantsAuthority);
		if (bool) {
			MerchantsAuthority preMerchantsAuthority=merchantsService.getMerchantsAuthorityByid(merchantsAuthority.getParentId());
			com.yougou.merchant.api.supplier.vo.MerchantOperationLog operationLog=new com.yougou.merchant.api.supplier.vo.MerchantOperationLog();
			operationLog.setId(UUIDUtil.getUUID());
			operationLog.setMerchantCode("authorityLog");
			operationLog.setOperated(new Date());
			operationLog.setOperationNotes("资源名称:"+merchantsAuthority.getAuthrityName()+"\n资源路径："+merchantsAuthority.getAuthrityURL()+"\n所处模块:："+preMerchantsAuthority.getAuthrityName()+"\n排序号:"+merchantsAuthority.getSortNo());
			operationLog.setOperationType(OperationType.AUTH_ADD);
			operationLog.setOperator(user.getUsername());
			merchantsApi.saveMerchantOperationLog(operationLog);
			
			model.addAttribute("message", "添加成功!");
			model.addAttribute("refreshflag", "1");
			model.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_merchants_authority.sc");
			return "yitiansystem/merchants/merchants_message";
		} else {
			model.addAttribute("message", "添加失败!");
			model.addAttribute("refreshflag", "1");
			model.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_merchants_authority.sc");
			return "yitiansystem/merchants/merchants_message";
		}
	}

	/**
	 * 跳转到添加用户角色管理页面
	 */
	@RequestMapping("to_merchants_user_list")
	public String to_merchants_user_list(Query query, ModelMap model,
			com.yougou.merchant.api.supplier.vo.MerchantUser merchantUser) {
		com.yougou.merchant.api.common.Query _query = new com.yougou.merchant.api.common.Query(query.getPageSize());
		_query.setPage(query.getPage());
		merchantUser.setIsYougouAdmin(NumberUtils.INTEGER_ZERO);
		com.yougou.merchant.api.common.PageFinder<com.yougou.merchant.api.supplier.vo.MerchantUser> pageFinder = merchantsApi.queryMerchantUserList(merchantUser, _query);
		model.addAttribute("pageFinder", pageFinder);
		model.addAttribute("merchantUser", merchantUser);
		return "yitiansystem/merchants/merchants_user_list";
	}

	/**
	 * 跳转到添加管理员角色管理页面
	 */
	@RequestMapping("to_merchants_admin_list")
	public String to_merchants_admin_list(Query query, ModelMap model,
			com.yougou.merchant.api.supplier.vo.MerchantUser merchantUser) {
		com.yougou.merchant.api.common.Query _query = new com.yougou.merchant.api.common.Query(query.getPageSize());
		_query.setPage(query.getPage());
		merchantUser.setIsYougouAdmin(NumberUtils.INTEGER_ONE);
		//com.yougou.merchant.api.supplier.vo.MerchantUser vo = new com.yougou.merchant.api.supplier.vo.MerchantUser();
		com.yougou.merchant.api.common.PageFinder<com.yougou.merchant.api.supplier.vo.MerchantUser> pageFinder = merchantsApi.queryMerchantUserList(merchantUser, _query);
		model.addAttribute("pageFinder", pageFinder);
		model.addAttribute("merchantUser", merchantUser);
		return "yitiansystem/merchants/merchants_admin_list";
	}
	
	/**
	 * 跳转到添加业务管理员角色管理页面
	 */
	@RequestMapping("to_merchants_businessAdmin_list")
	public String to_merchants_businessAdmin_list(Query query, ModelMap model,
			com.yougou.merchant.api.supplier.vo.MerchantUser merchantUser) {
		com.yougou.merchant.api.common.Query _query = new com.yougou.merchant.api.common.Query(query.getPageSize());
		_query.setPage(query.getPage());
		merchantUser.setIsYougouAdmin(2);
		com.yougou.merchant.api.common.PageFinder<com.yougou.merchant.api.supplier.vo.MerchantUser> pageFinder = merchantsApi.queryMerchantUserList(merchantUser, _query);
		model.addAttribute("pageFinder", pageFinder);
		model.addAttribute("merchantUser", merchantUser);
		return "yitiansystem/merchants/merchants_businessAdmin_list";
	}

	/**
	 * 跳转到用户角色管理页面
	 */
	@RequestMapping("to_merchants_role_list")
	public String to_merchants_role_list(Query query, ModelMap model,
			MerchantsRole merchantsRole,String authorityName) {
		PageFinder<Map<String, Object>> pageFinder = merchantsService.queryMerchantsRole(query, merchantsRole,authorityName);
		model.addAttribute("pageFinder", pageFinder);
		model.addAttribute("merchantsRole", merchantsRole);
		model.addAttribute("authorityName", authorityName);
		return "yitiansystem/merchants/merchants_role_list";
	}

	/**
	 * 跳转到添加用户角色管理页面
	 */
	@RequestMapping("to_add_merchants_role")
	public String to_add_merchants_role(ModelMap model) {
		List<Map<String, Object>> treeModes=merchantsService.queryAllMerchantsAuthorityList();
		
		model.addAttribute("treeModes", treeModes);
		return "yitiansystem/merchants/add_merchants_user_role";
	}

	/**
	 * 添加用户角色管理页面
	 */
	@RequestMapping("add_merchants_role")
	public String add_merchants_role(HttpServletRequest request,MerchantsRole merchantsRole, ModelMap model,String authrityNameHidden) {
		SystemmgtUser user = GetSessionUtil.getSystemUser(request);
		String operator= user == null ? null : user.getUsername();
		merchantsRole.setOperator(operator);
		merchantsRole.setCreateTime(new Date());
		merchantsRole.setStatus("0");
		boolean bool = merchantsService.addMerchantsRole(merchantsRole,authrityNameHidden.split("_"));
		if (bool) {
			model.addAttribute("message", "添加成功!");
			model.addAttribute("refreshflag", "1");
			model.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_merchants_role_list.sc");
			return "yitiansystem/merchants/merchants_message";
		} else {
			model.addAttribute("message", "添加失败!");
			model.addAttribute("refreshflag", "1");
			model.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_merchants_role_list.sc");
			return "yitiansystem/merchants/merchants_message";
		}
	}

	/**
	 * 添加用户角色管理页面
	 */
	@RequestMapping("update_merchants_role")
	public String update_merchants_role(HttpServletRequest request,MerchantsRole merchantsRole,
			ModelMap model,String authrityNameHidden) {
		SystemmgtUser user = GetSessionUtil.getSystemUser(request);
		String operator= user == null ? null : user.getUsername();
		MerchantsRole merchantsRole2 = merchantsService.initialMerchantsRole(merchantsRole.getId());
		merchantsRole2.setRemark(merchantsRole.getRemark());
		merchantsRole2.setRoleName(merchantsRole.getRoleName());
		merchantsRole2.setOperator(operator);
		boolean bool = merchantsService.update_merchants_role(merchantsRole2,authrityNameHidden.split("_"));
		if (bool) {
			boolean b = merchantsService.update_user_auth(merchantsRole2.getId());
			if(b){
				model.addAttribute("message", "修改成功!");
				model.addAttribute("refreshflag", "1");
				model.addAttribute("methodName",
						"/yitiansystem/merchants/businessorder/to_merchants_role_list.sc");
				return "yitiansystem/merchants/merchants_message";
			}else{
				model.addAttribute("message", "修改失败!");
				model.addAttribute("refreshflag", "1");
				model.addAttribute("methodName",
						"/yitiansystem/merchants/businessorder/to_merchants_role_list.sc");
				return "yitiansystem/merchants/merchants_message";
			}
		} else {
			model.addAttribute("message", "修改失败!");
			model.addAttribute("refreshflag", "1");
			model.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_merchants_role_list.sc");
			return "yitiansystem/merchants/merchants_message";
		}
	}

	/**
	 * 跳转到修改角色管理页面
	 */
	@RequestMapping("to_update_merchants_role")
	public String to_update_merchants_role(String rid, ModelMap model) {
		MerchantsRole merchantsRole = merchantsService
				.initialMerchantsRole(rid);
		
		List<Map<String, Object>> treeModes=merchantsService.queryAllMerchantsAuthorityList();
		
		Map<String,String> roleAuthorityMap=new HashMap<String,String>();
		List<RoleAuthority> roleAuthorityList=merchantsService.findRoleAuthoriryList(rid);
		for(RoleAuthority bean:roleAuthorityList){
			roleAuthorityMap.put(bean.getAuthorityId(), bean.getId());
		}
		String pra=null;
		for(Map<String, Object> treeMap:treeModes){
			if(roleAuthorityMap.get(treeMap.get("id"))!=null){
				treeMap.put("isChecked", "1");
				pra=pra+treeMap.get("parent_id")+";";
			}
		}
		
		if(pra!=null&&pra.length()>0){
			pra=pra+"0;";
		}
		
		for(Map<String, Object> treeMap:treeModes){
			if(pra!=null&&pra.indexOf(treeMap.get("id")+";")>-1){
				treeMap.put("isChecked", "1");
			}
		}
		model.addAttribute("treeModes", treeModes);
		model.addAttribute("merchantsRole", merchantsRole);
		return "yitiansystem/merchants/update_merchants_role";
	}

	/**
	 * 删除角色
	 * 
	 * @author wang.m
	 */
	@ResponseBody
	@RequestMapping("delete_role")
	public String delete_role(String id) {
		boolean bool = false;
		try {
			bool = merchantsService.delete_role(id);
			bool = merchantsService.update_user_auth(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除权限组发生错误：{}",e);
		}
		return bool ? "success" : "fail";
	}
	
	/**
	 * 删除角色
	 * 
	 * @author wang.m
	 */
	@ResponseBody
	@RequestMapping("update_roleStatus")
	public String update_roleStatus(String id) {
		boolean bool=false;
		try {
			merchantsService.update_roleStatus(id);
			merchantsService.update_user_auth(id);
			bool=true;
		} catch (Exception e) {
			logger.error("修改状态失败",e);
			bool=false;
		}
		
		return bool ? "success" : "fail";
	}

	/**
	 * 删除资源
	 * 
	 * @author wang.m
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("delete_authority")
	public String delete_authority(HttpServletRequest request,String id) throws Exception {
		MerchantsAuthority old=merchantsService.getMerchantsAuthorityByid(id);
		boolean bool = merchantsService.delete_authority(id);
		SystemmgtUser user = GetSessionUtil.getSystemUser(request);
		com.yougou.merchant.api.supplier.vo.MerchantOperationLog operationLog=new com.yougou.merchant.api.supplier.vo.MerchantOperationLog();
		operationLog.setId(UUIDUtil.getUUID());
		operationLog.setMerchantCode("authorityLog");
		operationLog.setOperated(new Date());
		operationLog.setOperationNotes("删除资源:"+old.getAuthrityName()+"\n结果:"+bool);
		operationLog.setOperationType(OperationType.AUTH_DELETE);
		operationLog.setOperator(user.getUsername());
		merchantsApi.saveMerchantOperationLog(operationLog);
		
		return bool ? "success" : "fail";
	}

	/**
	 * 跳转分配资源页面
	 * 
	 * @throws SQLException
	 */
	@RequestMapping("distributeRole")
	public String distributeRole(ModelMap model, String id,String yougouAdminId,String merchantCode)
			throws SQLException {
		// 根据供应商Id查询商家超级管理员账户Id
		String userId = null;
		if (StringUtils.isNotBlank(yougouAdminId)) {
			userId = yougouAdminId;
		} else {
			userId = merchantsService.getMerchantUserBySupplierId(id);
		}
		if(userId==null||"".equals(userId)){
			model.addAttribute("merchantsRoles", null);
		}else{
			// 查询所有有效权限组
			List<MerchantsRole> roleList = merchantsService
					.queryAllMerchantsRole();
			// 查看该用户权限组
			List<UserRole> roleExistList = merchantsService
					.queryUserRole(userId);
			for(MerchantsRole role:roleList){
				for(UserRole userRole:roleExistList){
					if(role.getId().equals(userRole.getRoleId())){
						role.setStatus("5");
						break;
					}
				}
			}
			model.addAttribute("merchantsRoles", roleList);
			model.addAttribute("userId", userId);
			model.addAttribute("merchantCode", merchantCode);
		}
		return "yitiansystem/merchants/distribute_role_list";
	}
	
	@RequestMapping("add_distributeRole")
	public String add_distributeRole(HttpServletRequest request,ModelMap model,String merchantCode,String userId,String[] role)
			throws SQLException {
		UserRole userRole=new UserRole();
		userRole.setUserId(userId);
		boolean isScuess=false;
		try {
			merchantsService.saveUserRole(userId,role,merchantCode,GetSessionUtil.getSystemUser(request).getUsername());
			logger.warn("给商家：【商家编码{}，用户id{}】，分配权限组【权限组id{}】成功！",
					new Object[]{merchantCode,userId,ToStringBuilder.reflectionToString(role, ToStringStyle.SHORT_PREFIX_STYLE)});
			//保存成功之后再更新缓存
			merchantsService.loadAuthResource(userId);
			isScuess=true;
		} catch (Exception e) {
            logger.error("保存用户权限组异常", e);
            isScuess=false;
		}
		if (isScuess) {
			model.addAttribute("message", "操作成功!");

		} else {
			model.addAttribute("message", "操作失败!");
		}
		model.addAttribute("refreshflag", "1");
		
		MerchantUser user=null;
		try {
			user=merchantsService.getMerchantUserById(userId);
		} catch (SQLException e) {
			logger.error("根据id查询用户异常:",e);
		}
		if(user!=null&&user.getIsYougouAdmin()!=null&&user.getIsYougouAdmin()==2){
			model.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_merchants_businessAdmin_list.sc");
		}else{
			model.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_merchantsList.sc?isCanAssign=true");
		}
		return "yitiansystem/merchants/merchants_message";
	}

	/**
	 * 给角色分配资源
	 * 
	 * @author wang.m
	 */
	@RequestMapping("add_roleAuthority")
	public String add_roleAuthority(ModelMap model, String roleId,
			String authorityId) {
		boolean bool = merchantsService.addRoleAuthority(roleId, authorityId);
		if (bool) {
			model.addAttribute("message", "添加成功!");
			model.addAttribute("refreshflag", "1");
			model.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_merchants_role_list.sc");
			return "yitiansystem/merchants/merchants_message";
		} else {
			model.addAttribute("message", "添加失败!");
			model.addAttribute("refreshflag", "1");
			model.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_merchants_role_list.sc");
			return "yitiansystem/merchants/merchants_message";
		}
	}

	/**
	 * 判断商家名称是否已经存在
	 * 
	 * @author wang.m
	 * @throws UnsupportedEncodingException
	 * @Date 2012-03-29
	 */
	@ResponseBody
	@RequestMapping("existMerchantSupplieName")
	public String existMerchantSupplieName(ModelMap model, String supplieName)
			throws UnsupportedEncodingException {
		try {
			Integer count = merchantsService.existMerchantSupplieName(supplieName);
			if (count == 1) {
				return "success1";
			} else if (count == 2) {
				return "success2";
			} else if (count == 3) {//韩货供应商类型
				return "success3";
			} else {
				return "fail";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "faile";
	}

	/**
	 * 修改商家账号状态
	 * 
	 * @author wang.m
	 * @Date 2012-03-31
	 */
	@RequestMapping("update_merchantState")
	public String update_merchantState(ModelMap model, Integer state,
			String id, HttpServletRequest request) {
		SystemmgtUser user = GetSessionUtil.getSystemUser(request);
		boolean bool = merchantServiceNew.updateMerchantState(id, state, user.getUsername());
		if (bool) {
			model.addAttribute("message", "修改状态成功!");
			model.addAttribute("refreshflag", "1");
			model.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_merchants_user_list.sc");
			return "yitiansystem/merchants/merchants_message";
		} else {
			model.addAttribute("message", "修改状态失败!");
			model.addAttribute("refreshflag", "1");
			model.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_merchants_user_list.sc");
			return "yitiansystem/merchants/merchants_message";
		}
	}

	/**
	 * 跳转到修改商家密码页面
	 * 
	 */
	@RequestMapping("/to_updatePassword")
	public String to_updatePassword(ModelMap map, String supplierCode) {
		if (StringUtils.isNotBlank(supplierCode)) {
			// 根据供应商编号查询商家用户信息
			MerchantUser merchantUser = merchantsService
					.getMerchantsBySuppliceCode(supplierCode);
			if (null != merchantUser) {
				map.addAttribute("merchantUser", merchantUser);
			} else {
				map.addAttribute("merchantUser", null);
			}
		}
		return "yitiansystem/merchants/update_merchants_password";
	}

	/**
	 * 修改商家密码
	 * 
	 */
	@RequestMapping("/updatePassword")
	public String updatePassword(ModelMap model, MerchantUser merchantUser,
			HttpServletRequest request) {
		if (null != merchantUser) {
			boolean bool = merchantsService.updatePassword(merchantUser,
					GetSessionUtil.getSystemUser(request));
			if (bool) {
				model.addAttribute("message", "修改密码成功!");
				model.addAttribute("refreshflag", "1");
				model.addAttribute("methodName",
						"/yitiansystem/merchants/businessorder/to_merchantsList.sc?isCanAssign=true");
				return "yitiansystem/merchants/merchants_message";
			} else {
				model.addAttribute("message", "修改密码失败!");
				model.addAttribute("refreshflag", "1");
				model.addAttribute("methodName",
						"/yitiansystem/merchants/businessorder/to_merchantsList.sc?isCanAssign=true");
				return "yitiansystem/merchants/merchants_message";
			}
		}
		return "";
	}

	/**
	 * 判断合同编号是否存在
	 * 
	 * @param contractNo
	 *            合同编号
	 */
	@ResponseBody
	@RequestMapping("/exits_contractNo")
	public String exits_contractNo(ModelMap map, String contractNo,
			String supplierSpId) {
		boolean bool = false;
		if (StringUtils.isNotBlank(contractNo) && StringUtils.isNotBlank(supplierSpId)) {
			bool = merchantsService.exits_contractNo(contractNo, supplierSpId);
		}
		return bool ? "sucuess" : "faile";
	}

	/**
	 * 
	 * 根据商家Id修改商家品牌和分类权限的页面 （商家状态为启用的情况下）
	 * 
	 * @Date 2012-04-13
	 * @author wang.m
	 * @throws SQLException
	 */
	@Deprecated
	@RequestMapping("update_merchantsBankAndCat")
	public String update_merchantsBankAndCat(ModelMap modelMap, Query query,
			SupplierSp supplierSp, HttpServletRequest req, String bankNoHidden,
			String catNameHidden, String brandList, String catList) {
		SupplierVo supplierVo = new SupplierVo();
		supplierVo.setId( supplierSp.getId() );
		boolean bool = merchantServiceNew.updateMerchantsBankAndCat(supplierVo, bankNoHidden, catNameHidden);
		if (bool) {
			modelMap.addAttribute("message", "修改商家信息成功!");
			modelMap.addAttribute("refreshflag", "1");
			modelMap.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_merchantsList.sc");
			return "yitiansystem/merchants/merchants_message";
		} else {
			modelMap.addAttribute("message", "修改商家信息失败!");
			modelMap.addAttribute("refreshflag", "1");
			modelMap.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_merchantsList.sc");
			return "yitiansystem/merchants/merchants_message";
		}
	}

	/**
	 * 
	 * 根据商家Id修改商家品牌和分类权限的页面和帐号信息 （商家状态为启用的情况下） 历史记录
	 * 
	 * @Date 2012-04-13
	 * @author wang.m
	 * @throws SQLException
	 */
	@RequestMapping("update_historyMerchants")
	public String update_historyMerchants(ModelMap modelMap, Query query,
			SupplierSp supplierSp, HttpServletRequest req, String bankNoHidden,
			String catNameHidden, String brandList, String catList) {
		boolean bool = merchantServiceNew.updateHistoryMerchants(req, supplierSp, bankNoHidden, catNameHidden);
		if (bool) {
			modelMap.addAttribute("message", "修改商家信息成功!");
			modelMap.addAttribute("refreshflag", "1");
			modelMap.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_merchantsList.sc");
			return "yitiansystem/merchants/merchants_message";
		} else {
			modelMap.addAttribute("message", "修改商家信息失败!");
			modelMap.addAttribute("refreshflag", "1");
			modelMap.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_merchantsList.sc");
			return "yitiansystem/merchants/merchants_message";
		}
	}

	/**
	 * 跳转到物流公司数据列表
	 * 
	 * @author wang.m
	 * @Date 2012-04-23
	 */
	@RequestMapping("to_logistics_corporate_list")
	public String to_logistics_corporate_list(ModelMap modelMap, Query query,
			LogisticsCompanyDomain logisticsVo) {
		if (logisticsVo.getIsMerchant() == null) 
			logisticsVo.setIsMerchant(2);
		if (logisticsVo.getStatus() == null) 
			logisticsVo.setStatus(1);
		if (logisticsVo.getStatus() == -1) logisticsVo.setStatus(null);
		com.yougou.wms.wpi.common.pagefinder.PageFinder<LogisticsCompanyDomain> pageFinder = logisticsCompanyService
				.queryPageFinderLogisticsCompany(query.getPage(),
						query.getPageSize(), logisticsVo.getStatus(),
						logisticsVo.getIsMerchant(),
						logisticsVo.getLogisticsCompanyName());
		modelMap.addAttribute("pageFinder", pageFinder);
		modelMap.addAttribute("logisticsCorporate", logisticsVo);
		return "yitiansystem/merchants/logistics_corporate_list";
	}

	/**
	 * 跳转到商家发货地址设置页面
	 * 
	 * @author wang.m
	 * @Date 2012-04-28
	 */
	@RequestMapping("to_merchant_consignmentAdress_list")
	public String to_merchant_consignmentAdress_list(ModelMap modelMap,
			HttpServletRequest req, Query query, SupplierSp supplierSp) {
		PageFinder<SupplierSp> pageFinder = merchantsService.getSupplierSpList(
				supplierSp, query);
		modelMap.addAttribute("pageFinder", pageFinder);
		modelMap.addAttribute("supplierSp", supplierSp);
		return "yitiansystem/merchants/merchant_consignmentAdress_list";
	}

	/**
	 * 跳转到添加商家发货地址设置页面
	 * 
	 * @author wang.m
	 * @Date 2012-05-09
	 */
	@RequestMapping("to_updateMerchantConsignment")
	public String to_updateMerchantConsignment(ModelMap modelMap,
			String supplyId) {
		return "yitiansystem/merchants/merchant_consignmentAdress_list";
	}

	/**
	 * 跳转到修改快点单模块页面
	 * 
	 * @author wang.m
	 * @Date 2012-04-24
	 */
	@RequestMapping("to_update_express_template")
	public String to_update_express_template(ModelMap modelMap, Query query,
			String logisticsId, String images) {
		modelMap.addAttribute("logisticsId", logisticsId);
		MerchantExpressTemplate template = merchantsService
				.getExpressTemplateByLogisticsId(logisticsId);
		modelMap.addAttribute("template", template);
		String backGroundImage = "";
		if (StringUtils.isNotBlank(images)) {
			backGroundImage = images.substring(images.lastIndexOf("/") + 1);
			modelMap.addAttribute("backGroundImage", backGroundImage);
		}

		modelMap.addAttribute("images", images != null ? images + "?"
				+ new Random().nextInt(10) : images);
		if (null != template) {
			modelMap.addAttribute("templateImages", ftp.getDomainName()
					+ "img/" + template.getBackGroundImage() + "?"
					+ new Random().nextInt(10));
			return "yitiansystem/merchants/update_express_template";
		} else {
			modelMap.addAttribute("templateImages", null);
			return "yitiansystem/merchants/add_express_template";
		}

	}

	/**
	 * 上传图片处理类
	 * 
	 * @param realPath
	 * @throws Exception
	 */
	@RequestMapping("uploadImages")
	public String uploadImages(ModelMap model,
			DefaultMultipartHttpServletRequest multipartRequest,
			InputStream fileStream, HttpServletRequest request,
			HttpServletResponse response, String logisticsId) throws Exception {
		MultipartFile multipartFile = multipartRequest.getFile("filePath");
		String fileName = multipartFile.getOriginalFilename();
		try {
			if (multipartFile != null) {
				if (!multipartFile.isEmpty()) {
					if (multipartFile.getSize() > 5000000) {
						model.addAttribute("message", "文件大小超过50M!");
					}
					FtpUtils ftpUtils = ftp.getFtpUtilsIntance("/img");
					ftpUtils.uploadContract(
							multipartFile.getInputStream(),
							new String(fileName.getBytes("UTF-8"), "iso-8859-1"));
					ftpUtils.closeConnect();
					MerchantExpressTemplate template = merchantsService
							.getExpressTemplateByLogisticsId(logisticsId);
					if (template == null) {
						MerchantExpressTemplate merchantExpressTemplate = new MerchantExpressTemplate();
						merchantExpressTemplate.setBackGroundImage(fileName);
						merchantExpressTemplate.setLogisticsId(logisticsId);
						merchantsService.saveExpressTemplate(request,
								merchantExpressTemplate);
					} else {
						template.setBackGroundImage(fileName);
						merchantsService.saveExpressTemplate(request, template);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			model.addAttribute("message", "上传图片失败!");
			return "yitiansystem/merchants/merchants_message";
		}
		model.addAttribute("images", ftp.getDomainName() + "img/" + fileName);
		model.addAttribute("message", "上传图片成功!");
		model.addAttribute("refreshflag", "2");
		model.addAttribute("logisticsId", logisticsId);
		model.addAttribute("methodName",
				"/yitiansystem/merchants/businessorder/to_update_express_template.sc");
		return "yitiansystem/merchants/merchants_message";
	}

	/**
	 * 保存快递单模块信息
	 * 
	 * @author wang.m
	 * @Date 2012-05-02
	 */
	@RequestMapping("saveExpressTemplate")
	public String saveExpressTemplate(ModelMap modelMap,
			HttpServletRequest req,
			DefaultMultipartHttpServletRequest multipartRequest,
			MerchantExpressTemplate merchantExpressTemplate) {
		MultipartFile multipartFile = multipartRequest.getFile("filePath");
		String fileName = multipartFile.getOriginalFilename();
		if (StringUtils.isNotBlank(fileName)) {
			merchantExpressTemplate.setBackGroundImage(fileName);
		}
		boolean bool = merchantsService.saveExpressTemplate(req,
				merchantExpressTemplate);
		if (bool) {
			modelMap.addAttribute("message", "修改快递单模块信息成功!");
			modelMap.addAttribute("refreshflag", "1");
			modelMap.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_logistics_corporate_list.sc");
			return "yitiansystem/merchants/merchants_message";
		} else {
			modelMap.addAttribute("message", "修改快递单模块信息失败!");
			modelMap.addAttribute("refreshflag", "1");
			modelMap.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_logistics_corporate_list.sc");
			return "yitiansystem/merchants/merchants_message";
		}
	}

	/**
	 * 查询商家售后退货地址列表
	 * 
	 * @author wang.m
	 * @date 2012-05-11
	 */
	@RequestMapping("merchant_rejected_address_list")
	public String merchant_rejected_address_list(ModelMap modelMap, String str,String brand,
			Query query, MerchantRejectedAddress merchantRejectedAddress) {
		modelMap.addAttribute("rejectedAddress", merchantRejectedAddress);
		if (StringUtils.isNotBlank(str)) {
			PageFinder<MerchantRejectedAddress> pageFinder = merchantsService.getMerchantRejectedAddressList(query, merchantRejectedAddress, brand);
			modelMap.addAttribute("pageFinder", pageFinder);
		} else {
			modelMap.addAttribute("pageFinder", null);
		}
		modelMap.addAttribute("brand", brand);
		return "yitiansystem/merchants/merchant_rejected_address_list";
	}

//	@RequestMapping("viewMerchantOperationLog")
//	public String viewMerchantOperationLog(ModelMap model, String merchantCode, Query query) throws Exception {
//		PageFinder<MerchantOperationLog> pageFinder = merchantOperationLogService.queryMerchantOperationLogByOperationType(merchantCode, OperationType.AFTER_SERVICE, query);
//		model.addAttribute("pageFinder", pageFinder);
//		return "yitiansystem/merchants/view_auth_operation_log";
//	}
	/**
	 * 跳转到添加商家售后退货地址页面
	 * 
	 * @author wang.m
	 * @date 2012-05-11
	 */
	@RequestMapping("to_save_merchant_reject_address")
	public String to_save_merchant_reject_address(ModelMap modelMap) {
		List<Map<String, Object>> areaList = merchantsService.getAreaList();
		modelMap.addAttribute("areaList", areaList);
		return "yitiansystem/merchants/add_merchant_rejected_address";
	}

	/**
	 * 跳转到修改商家售后退货地址页面
	 * 
	 * @author wang.m
	 * @date 2012-05-11
	 */
	@RequestMapping("to_update_merchant_reject_address")
	public String to_save_merchant_reject_address(ModelMap modelMap, String id) {
		List<Map<String, Object>> areaList = merchantsService.getAreaList();
		modelMap.addAttribute("areaList", areaList);
		MerchantRejectedAddress reject = merchantsService
				.getMerchantRejectedAddressById(id);
		if (null != reject) {
			String pst = reject.getWarehouseArea();
			if (StringUtils.isNotBlank(pst)) {
				String[] area = pst.split("-");
				if (null != area && area.length > 0) {
					String province = area[0];
					modelMap.addAttribute("province", province);
				}
			}
		}
		modelMap.addAttribute("reject", reject);
		return "yitiansystem/merchants/update_merchant_rejected_address";
	}

	/**
	 * 保存商家售后退货地址数据
	 * 
	 * @author wang.m
	 * @date 2012-05-11
	 */
	@RequestMapping("save_merchant_reject_address")
	public String save_merchant_reject_address(ModelMap modelMap,
			HttpServletRequest req,
			MerchantRejectedAddress merchantRejectedAddress) {
		boolean bool = merchantsService.saveMerchantRejectedAddress(req,
				merchantRejectedAddress);
		if (bool) {
			modelMap.addAttribute("message", "保存商家售后退货地址信息成功!");
			modelMap.addAttribute("refreshflag", "1");
			modelMap.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/merchant_rejected_address_list.sc?str=1");
			return "yitiansystem/merchants/merchants_message";
		} else {
			modelMap.addAttribute("message", "保存商家售后退货地址信息失败!");
			modelMap.addAttribute("refreshflag", "1");
			modelMap.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/merchant_rejected_address_list.sc?str=1");
			return "yitiansystem/merchants/merchants_message";
		}
	}

	/**
	 * 根据编号和等级和售后地址Id查询市 区信息
	 * 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/queryChildById")
	public String queryChildById(@RequestParam("no") String no,
			@RequestParam("level") Integer level, @RequestParam("id") String id)
			throws Exception {
		String nameStr = "";
		IJsonTool jsonToole = new JsonTool();
		List<Map<String, Object>> areaMap = merchantsService.getChildAreaByNo(
				no, level);
		// 根据商家售后退货地址id查询对象
		MerchantRejectedAddress reject = merchantsService
				.getMerchantRejectedAddressById(id);
		if (null != reject) {
			String str = reject.getWarehouseArea();
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

		List<Area> areaList = new ArrayList<Area>();
		for (Map<String, Object> map : areaMap) {
			Area area = new Area();
			area.setNo(map.get("no").toString());
			area.setName(map.get("name").toString());
			area.setCode(nameStr);
			areaList.add(area);
		}

		StringBuffer jsonDate = jsonToole.convertObj2jason(areaList);
		return jsonDate.toString();
	}

	/**
	 * 根据编号和等级查询市 区信息
	 * 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/queryChildByLevelAndNo")
	public String queryChildByLevelAndNo(@RequestParam("no") String no,
			@RequestParam("level") Integer level) throws Exception {
		IJsonTool jsonToole = new JsonTool();
		List<Map<String, Object>> areaMap = merchantsService.getChildAreaByNo(
				no, level);
		List<Area> areaList = new ArrayList<Area>();
		if (areaMap != null) {
			for (Map<String, Object> map : areaMap) {
				Area area = new Area();
				area.setNo(map.get("no").toString());
				area.setName(map.get("name").toString());
				areaList.add(area);
			}
		}
		StringBuffer jsonDate = jsonToole.convertObj2jason(areaList);
		return jsonDate.toString();
	}

	/**
	 * 判断商家退货地址是否已经存在
	 * 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/exictRejectedAddressCount")
	public String exictRejectedAddressCount(String supplierCode) throws Exception {
		boolean bool = merchantsService.exictRejectedAddressCount(supplierCode);
		return bool ? "sucess" : "fail";
	}

//	/**
//	 * 跳转到添加招商供应商页面 - new 
//	 * 
//	 * @author luo.q 2015-07-01
//	 * @param model
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("/to_add_supplier")
//	public ModelAndView to_add_supplier(ModelMap model, String supplierId)
//			throws Exception {
//		//成本帐套名称
//		List<CostSetofBooks> costSetofBooksList = merchantServiceNew.getCostSetofBooksList();
//		model.addAttribute("costSetofBooksList", costSetofBooksList);
//		SupplierVo supplier = null;
//		if (StringUtils.isNotBlank(supplierId)) {
//			supplier = this.merchantServiceNew.getSupplierVoById(supplierId);
//		}
//		model.addAttribute("supplier", supplier);
//		// 获取省市区第一级结果集数据
//		List<Map<String, Object>> areaList = merchantsService.getAreaList();
//	
//		model.addAttribute("areaList", areaList);
//
//		//		model.put("supplierContract",null);
//    	
//		model.put("contractNo",initContractNo());
//		
//		String cfgFileSize = sysconfigProperties.getContractFtpMaxFileSize();
//		int defaultMaxFileSize = MerchantConstant.DEFAULT_CONTRACT_ATTACHMENT_FILE_SIZE; //10M
//    	int maxFileSize = StringUtils.isNotBlank(cfgFileSize) ? 
//    			Integer.valueOf(cfgFileSize) : defaultMaxFileSize;
//    	model.addAttribute("maxFileSize",maxFileSize+"");
////		return new ModelAndView("yitiansystem/merchants/supplier/add_supplier", model);
//		return new ModelAndView("yitiansystem/merchants/supplier/add_information", model);
//	}

	/**
	 * 跳转到添加供应商账户页面
	 */
	@RequestMapping("/to_add_supplier_user")
	public ModelAndView to_add_supplier_user(ModelMap model, String supplierId)
			throws Exception {
		SupplierVo supplier = null;
		com.yougou.merchant.api.supplier.vo.MerchantUser user = null;
		if (StringUtils.isNotBlank(supplierId)) {
			supplier = this.merchantServiceNew.getSupplierVoById(supplierId);
			com.yougou.merchant.api.supplier.vo.MerchantUser _user = new com.yougou.merchant.api.supplier.vo.MerchantUser();
			_user.setMerchantCode(supplier.getSupplierCode());
			com.yougou.merchant.api.common.PageFinder<com.yougou.merchant.api.supplier.vo.MerchantUser> pageFinder = this.merchantsApi.queryMerchantUserList(_user, new com.yougou.merchant.api.common.Query());
			user = CollectionUtils.isNotEmpty(pageFinder.getData()) ? pageFinder.getData().get(0) : null;
		}
		
		model.addAttribute("user", user);
		model.addAttribute("supplierId", supplierId);
		return new ModelAndView("yitiansystem/merchants/supplier/add_supplier_2", model);
	}
	
	/**
	 * 跳转到添加供应商品牌分类授权页面
	 */
	@RequestMapping("/to_add_supplier_auth")
	public ModelAndView to_add_supplier_auth(ModelMap model, String supplierId)
			throws Exception {
		model.addAttribute("supplierId", supplierId);
		return new ModelAndView("yitiansystem/merchants/supplier/add_supplier_3", model);
	}
	
/*	*//**
	 * 保存供应商基本信息-旧版本
	 * 
	 * @param modelMap
	 * @param supplierSp
	 * @param req
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping("add_supplier")
	public String add_supplier(ModelMap modelMap, Supplier supplier,SupplierExtend  supplierExtend,String supplierId, HttpServletRequest req) {
		SystemmgtUser user = GetSessionUtil.getSystemUser(req);
		
		Integer result = 0;
		if (StringUtils.isBlank(supplierId)) {
			String uuid = UUIDGenerator.getUUID();
			supplierId = uuid;
			supplier.setId(uuid);
			supplier.setUpdateTimestamp(System.currentTimeMillis());// 时间戳
			supplier.setSupplierCode(CodeGenerate.getSupplierCode());// 商家编号
			supplier.setCreator(user != null ? user.getUsername() : null);// 创建人
			supplier.setUpdateUser(user != null ? user.getUsername() : null);// 修改人
			supplier.setCreatorname(user != null ? user.getUsername() : null);// 创建人
			supplier.setUpdateUsername(user != null ? user.getUsername() : null);// 修改人
			supplier.setUpdateDate(new Date());// 修改时间 添加时默认为创建时间
			supplier.setDeleteFlag(1);// 未删除标志
			supplier.setIsValid(2);// 供应商状态
			this.mergeBank(supplierExtend, supplier);
			
			result = purchaseApiService.insertSupplier(supplier);
			
			//为供应商添加图片空间默认商品目录
			MerchantPictureCatalog pictureCatalog=new MerchantPictureCatalog();
			pictureCatalog.setCatalogName("默认目录");
			pictureCatalog.setId(uuid);
			pictureCatalog.setMerchantCode(supplier.getSupplierCode());
			pictureCatalog.setParentId("0");
			pictureCatalog.setShopId(null);
			try {
				pictureService.insertPicCatalog(pictureCatalog);
			} catch (Exception e) {
				logger.error("新建供应商("+supplier.getSupplierCode()+"),添加默认商品图片文件夹异常.",e);
			}
			//添加供应商修改日志
			SupplierUpdateHistory history = new SupplierUpdateHistory();
			history.setId(UUIDGenerator.getUUID());
			history.setOperationTime(new Date());
			history.setOperator(user == null ? null : user.getUsername());
			history.setProcessing("新建");
			history.setUpdateField("新建");
			history.setSupplierId(supplierId);
			purchaseApiService.insertSupplierLog(history);
		} else {
			supplier.setId(supplierId);
			supplier.setUpdateTimestamp(System.currentTimeMillis());// 时间戳
			supplier.setUpdateUser(user != null ? user.getUsername() : null);// 修改人
			supplier.setUpdateUsername(user != null ? user.getUsername() : null);// 修改人
			supplier.setUpdateDate(new Date());// 修改时间 添加时默认为创建时间
			this.mergeBank(supplierExtend, supplier);
			
			result = this.purchaseApiService.updateSupplier(supplier);
			
			//添加供应商修改日志
			SupplierUpdateHistory history = new SupplierUpdateHistory();
			history.setId(UUIDGenerator.getUUID());
			history.setOperationTime(new Date());
			history.setOperator(user == null ? null : user.getUsername());
			history.setProcessing("修改供应商基本信息");
			history.setUpdateField("修改供应商基本信息");
			history.setSupplierId(supplierId);
			purchaseApiService.insertSupplierLog(history);
		}
		
		//Add by LQ on 20150327
		 查询合同中是否有同名供应商签订的合同，有，则绑定！
		if(result > 0){
			
			try {
				supplierContractService.bindContractIfExited(supplier);
			} catch (Exception e) {
				logger.error("保存新增供应商操作：判定是否需要绑定同名供应商签订的合同时出错！",e);
			}
			
		}
		
		//正常返回供应商Id
		return result > 0 ? supplierId : "false";
	}
	*/
//	/**
//	 * 保存招商供应商-创建-luo.q
//	 * 
//	 * @param modelMap
//	 * @param supplierSp
//	 * @param req
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("add_supplier")
//	public String add_supplier( ModelMap modelMap, Supplier supplier,String supplierId,
//			com.belle.yitiansystem.merchant.model.pojo.SupplierContract supplierContract,MerchantUser merchantUser,
//			MerchantRejectedAddress rejectedAddress,ContactsFormVo contactsFormVo,
//			String[]contract_attachment,String[] trademark,
//			String[] authorizer,String[] type,String[] registeredTrademark,String[]registeredStartDate,String[] registeredEndDate,String[]beAuthorizer,
//			String[]authorizStartdate,String[]authorizEnddate,
//			HttpServletRequest req ) {
//		
//		SystemmgtUser user = GetSessionUtil.getSystemUser(req);
//		String userName = (user != null )? user.getUsername() : "";
//		Integer result = 0;
//		if (StringUtils.isBlank(supplierId)) {
//			
//			// step 1- 商家VO构建
//			String uuid = UUIDGenerator.getUUID();
//			String supplierCode = CodeGenerate.getSupplierCode();
//			supplierId = uuid;
//			supplier.setId(uuid);
//			supplier.setUpdateTimestamp(System.currentTimeMillis());// 时间戳
//			supplier.setSupplierCode( supplierCode );// 商家编号
//			supplier.setCreator(userName);// 创建人
//			supplier.setUpdateUser(userName);// 修改人
//			supplier.setCreatorname(userName);// 创建人
//			supplier.setUpdateUsername(userName);// 修改人
//			supplier.setUpdateDate(DateUtil2.getCurrentDateTime());// 修改时间 添加时默认为创建时间
//			supplier.setDeleteFlag(MerchantConstant.NOT_DELETED);// 未删除标志
//			supplier.setIsValid(MerchantConstant.MERCHANT_STATUS_NEW);// 供应商状态
//			// step 2- rejectedAddress VO 构建
//			rejectedAddress.setCreaterPerson(userName);
//			rejectedAddress.setCreaterTime(DateUtil2.getCurrentDateTimeToStr2());
//			String uuid2 = UUIDGenerator.getUUID();
//			rejectedAddress.setId(uuid2);
//			rejectedAddress.setSupplierCode(supplierCode);
//			// step3 - contactVoList构建 
//			contactsFormVo.setSupplier(supplier.getSupplier());
//			contactsFormVo.setSupplierCode(supplierCode);
//			contactsFormVo.setSupplyId(supplierId);
//			List<SupplierContact> contactVoList = contactsFormVo.generateContactVoList();
//			
//			// step4 - merchantUser
//			merchantUser.setMerchantCode(supplierCode);
//			// step5 - supplierContract
//			supplierContract.setSupplierId(supplierId);
//			supplierContract.setBindStatus( MerchantConstant.CONTRACT_BIND );
//			supplierContract.setSupplierCode(supplierCode);
//			supplierContract.setSupplier(supplier.getSupplier());
//			// step6 - 
//			Map<String,Object> params = new HashMap<String,Object>();
//			params.put("supplier", supplier);
//			params.put("supplierId", supplierId);
//			params.put("supplierContract", supplierContract);
//			params.put("merchantUser", merchantUser);
//			params.put("rejectedAddress", rejectedAddress);
//			params.put("contactVoList", contactVoList);
//			params.put("contract_attachment", contract_attachment);
//			params.put("trademark", trademark);
//			params.put("authorizer", authorizer);
//			params.put("type", type);
//			params.put("registeredTrademark", registeredTrademark);
//			params.put("registeredStartDate", registeredStartDate);
//			params.put("registeredEndDate", registeredEndDate);
//			params.put("beAuthorizer", beAuthorizer);
//			params.put("authorizStartdate", authorizStartdate);
//			params.put("authorizEnddate", authorizEnddate);
//			// 保存
//			try {
//				merchantServiceNew.saveSupplierMerchant(params);
//			
//				//为供应商添加图片空间默认商品目录
//				MerchantPictureCatalog pictureCatalog=new MerchantPictureCatalog();
//				pictureCatalog.setCatalogName("默认目录");
//				pictureCatalog.setId(uuid);
//				pictureCatalog.setMerchantCode(supplier.getSupplierCode());
//				pictureCatalog.setParentId("0");
//				pictureCatalog.setShopId(null);
//				pictureService.insertPicCatalog(pictureCatalog);
//				
//				//添加供应商状态流转日志 ，添加供应商修改日志
//				SupplierUpdateHistory history = new SupplierUpdateHistory();
//				history.setId(UUIDGenerator.getUUID());
//				history.setOperationTime(new Date());
//				history.setOperator(user == null ? null : user.getUsername());
//				history.setProcessing("新建");
//				history.setUpdateField("新建");
//				history.setSupplierId(supplierId);
//				try {
//					purchaseApiService.insertSupplierLog(history);
//				} catch (Exception e) {
//					logger.error("新建供应商("+supplier.getSupplier()+")增加状态流转日志异常.");
//				}
//				
//			} catch (Exception e) {
//				logger.error("新建供应商("+supplier.getSupplier()+")异常.",e);
//			}
//			
//			
//			
//		} else {
//			supplier.setId(supplierId);
//			supplier.setUpdateTimestamp(System.currentTimeMillis());// 时间戳
//			supplier.setUpdateUser(user != null ? user.getUsername() : null);// 修改人
//			supplier.setUpdateUsername(user != null ? user.getUsername() : null);// 修改人
//			supplier.setUpdateDate(new Date());// 修改时间 添加时默认为创建时间
//			
//			result = this.purchaseApiService.updateSupplier(supplier);
//			
//			//添加供应商修改日志
//			SupplierUpdateHistory history = new SupplierUpdateHistory();
//			history.setId(UUIDGenerator.getUUID());
//			history.setOperationTime(new Date());
//			history.setOperator(user == null ? null : user.getUsername());
//			history.setProcessing("修改供应商基本信息");
//			history.setUpdateField("修改供应商基本信息");
//			history.setSupplierId(supplierId);
//			purchaseApiService.insertSupplierLog(history);
//		}
//		
//		//Add by LQ on 20150327
//		/* 查询合同中是否有同名供应商签订的合同，有，则绑定！*/
//		if(result > 0){
//			
//			try {
//				supplierContractService.bindContractIfExited(supplier);
//			} catch (Exception e) {
//				logger.error("保存新增供应商操作：判定是否需要绑定同名供应商签订的合同时出错！",e);
//			}
//			
//		}
//		
//		//正常返回供应商Id
//		return result > 0 ? supplierId : "false";
//	}
	
	private void mergeBank(SupplierExtend supplierVo, Supplier supplier) {
		List<String> bankList = new ArrayList<String>();
		List<String> accountList = new ArrayList<String>();
		bankList.add(supplier.getBank());
		accountList.add(supplier.getAccount());
		if (StringUtils.isNotBlank(supplierVo.getBankSecond())) {
			bankList.add(supplierVo.getBankSecond());
		}
		if (StringUtils.isNotBlank(supplierVo.getBankThird())) {
			bankList.add(supplierVo.getBankThird());
		}
		if (StringUtils.isNotBlank(supplierVo.getAccountSecond())) {
			accountList.add(supplierVo.getAccountSecond());
		}
		if (StringUtils.isNotBlank(supplierVo.getAccountThird())) {
			accountList.add(supplierVo.getAccountThird());
		}
		supplier.setBank(JSONArray.fromObject(bankList).toString());
		supplier.setAccount(JSONArray.fromObject(accountList).toString());
	}
	
	// 商家创建第二步保存-旧版本
	@ResponseBody
	@RequestMapping("add_supplier_user")
	public String add_supplier_user(ModelMap modelMap, String supplierId, com.yougou.merchant.api.supplier.vo.MerchantUser user, HttpServletRequest req) throws Exception {
		SupplierVo supplier = null;
		com.yougou.merchant.api.supplier.vo.MerchantUser temp = null;
		if (StringUtils.isNotBlank(supplierId)) {
			supplier = this.merchantServiceNew.getSupplierVoById(supplierId);
			com.yougou.merchant.api.supplier.vo.MerchantUser _user = new com.yougou.merchant.api.supplier.vo.MerchantUser();
			_user.setMerchantCode(supplier.getSupplierCode());
			com.yougou.merchant.api.common.PageFinder<com.yougou.merchant.api.supplier.vo.MerchantUser> pageFinder = this.merchantsApi.queryMerchantUserList(_user, new com.yougou.merchant.api.common.Query());
			temp = CollectionUtils.isNotEmpty(pageFinder.getData()) ? pageFinder.getData().get(0) : null;
			
			// 对密码进行MD5加密
			String password = Md5Encrypt.md5(user.getPassword());
			user.setPassword(password);
			user.setMerchantCode(supplier.getSupplierCode());
			user.setId(temp == null ? null : temp.getId());
			user.setUserName("");// 商家真实信息
			user.setCreateTime(temp == null ? DateUtil.getDateTime(new Date()) : null);
			user.setStatus(1);// 状态 1表示可用
			user.setIsAdministrator(1); // 1表示管理员
			user.setDeleteFlag(1);// 未删除标志
			user.setIsYougouAdmin(0);
			
			SystemmgtUser sysuser = GetSessionUtil.getSystemUser(req);
			user.setCreater(sysuser == null ? null : sysuser.getUsername());
		}
		
		boolean result = this.merchantsApi.saveMerchantUser(user);
		
		//正常返回供应商Id
		return result ? supplierId : "false";
	}
	
	@RequestMapping("add_supplier_auth")
	public String add_supplier_auth(ModelMap modelMap, String supplierId, String bankNoHidden,
			String catNameHidden, HttpServletRequest req) {
		//保存品类授权
		SupplierVo supplierVo = new SupplierVo();
		supplierVo.setId(supplierId);
		boolean bool = merchantServiceNew.updateMerchantsBankAndCat( supplierVo, bankNoHidden, catNameHidden);
		
		if (bool) {// 招商供应商
			
			// 操作日志
			try {
				SupplierVo supplier = this.merchantServiceNew.getSupplierVoById(supplierId);
				
				if( null!=supplier ){
					SystemmgtUser user = GetSessionUtil.getSystemUser(req);
					MerchantOperationLog log = new MerchantOperationLog();
					log.setType( MerchantConstant.LOG_FOR_MERCHANT );
					log.setOperator(user.getUsername());
					log.setContractNo("");
					log.setOperationNotes("品类授权");
					log.setOperationType( OperationType.CATEGORY_AUTH.getDescription());
					log.setMerchantCode( supplier.getSupplierCode() );
					merchantServiceNew.insertMerchantLog(log);
					
				}else{
					logger.error("给品类授权创建操作日志时，未找到该商家，ID:{}",supplierId);
				}
			} catch (Exception e) {
				logger.error("给品类授权创建操作日志失败，商家ID：{}",supplierId,e);
			}
			
			modelMap.addAttribute("message", "保存成功!");
			modelMap.addAttribute("refreshflag", "1");
			modelMap.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_merchantsList.sc");
			return "yitiansystem/merchants/merchants_message";
		} else {
			modelMap.addAttribute("message", "保存失败!");
			modelMap.addAttribute("refreshflag", "1");
			modelMap.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_merchantsList.sc");
			return "yitiansystem/merchants/merchants_message";
		}
	}
	
	/**
	 * 跳转到品牌分类授权 修改页面
	 */
	@RequestMapping("/to_update_supplier_auth")
	public ModelAndView to_update_supplier_auth(ModelMap model, String supplierId)
			throws Exception {
		SupplierVo supplier = this.merchantServiceNew.getSupplierVoById(supplierId);
		// 查询授权品牌.[brandNo;brandName_brandNo;brandName...]
		String brandStrs = merchantsService.queryAuthorizationBrandBysupplierId(supplierId);
		List<String> brandNos = merchantsService.getAuthorizationBrandNos(supplierId);
		// 品牌分类关系集合
		List<String> brandStructs = merchantsService.queryAuthorizationBrandCatBysupplierId(supplierId);
		List<Category> treeModes = this.getCategoryTreeMode(brandNos);
					
		this.setTreeModesCheckStatus(treeModes, brandStructs);
					
		model.addAttribute("brandStrs", brandStrs);
		model.addAttribute("treeModes", treeModes);
		model.addAttribute("supplierId", supplierId);
		model.addAttribute("supplier", supplier);
		return new ModelAndView("yitiansystem/merchants/supplier/update_supplier_catbrand_auth", model);
	}
	
	// 商家编辑页面-(普通商家)
	@RequestMapping("to_update_supplier")
	public String to_update_supplier(ModelMap modelMap, String id, Integer flag) throws Exception {
		// 根据id查询商家基本信息
		SupplierVo vo = merchantServiceNew.getSupplierVoById(id);
		modelMap.addAttribute("supplierSp", vo);
		MerchantSupplierExpand supplierExtend = merchantServiceNew.getSupplierExpandVoById(id);
		modelMap.addAttribute("supplierExtend", supplierExtend);
		
		// 财务成本套帐
		List<CostSetofBooks> costSetofBooksList = merchantServiceNew.getAllCostSetofBooksList();
		modelMap.addAttribute("costSetofBooksList", costSetofBooksList);
		
		// 是招商供应商才查询
		MerchantUser merchantUser = null;
		if (null != vo) {
			// 根据商家编号查询商家登录信息
			merchantUser = merchantsService.getMerchantsBySuppliceCode(vo.getSupplierCode());
		}
			
		modelMap.addAttribute("merchantUser", merchantUser);
		modelMap.addAttribute("flag", flag);
		
		return "yitiansystem/merchants/supplier/update_supplier";
	}
	
//    // 编辑商家页面-招商商家  Add by luo.q
//	@RequestMapping("to_update_supplier_merchant")
//	public String to_update_supplier_merchant(ModelMap modelMap, String id) throws Exception {
//		// 根据id查询商家基本信息
//		SupplierVo vo = merchantServiceNew.getSupplierVoById(id);
//		modelMap.addAttribute("supplier", vo);
//		
//		// 财务成本套帐
//		List<CostSetofBooks> costSetofBooksList = merchantServiceNew.getCostSetofBooksList();
//		modelMap.addAttribute("costSetofBooksList", costSetofBooksList);
//		
//		// 是招商供应商才查询
//		MerchantUser merchantUser = null;
//		MerchantRejectedAddress rejectedAddress = null;
//		if (null != vo) {
//			// 根据商家编号查询商家登录信息
//			merchantUser = merchantsService.getMerchantsBySuppliceCode(vo.getSupplierCode());
//			
//			rejectedAddress = merchantsService.getMerchantRejectedAddressByCode(vo.getSupplierCode());
//			
//			if (null != rejectedAddress) {
//				String pst = rejectedAddress.getWarehouseArea();
//				if (StringUtils.isNotBlank(pst)) {
//					String[] area = pst.split("-");
//					if (null != area && area.length > 0) {
//						String province = area[0];
//						modelMap.addAttribute("afterSaleProvince", province);
//					}
//				}
//			}
//		}
//			
//		modelMap.addAttribute("merchantUser", merchantUser);
//		
//
//		// 获取省市区第一级结果集数据
//		List<Map<String, Object>> areaList = merchantsService.getAreaList();
//	
//		modelMap.addAttribute("areaList", areaList);
//		com.belle.yitiansystem.merchant.model.pojo.SupplierContract supplierContract = supplierContractService.getSupplierContractBySupplierId(id);
//		modelMap.put("supplierContract",supplierContract);
//				
//		String cfgFileSize = sysconfigProperties.getContractFtpMaxFileSize();
//		int defaultMaxFileSize = MerchantConstant.DEFAULT_CONTRACT_ATTACHMENT_FILE_SIZE; //10M
//    	int maxFileSize = StringUtils.isNotBlank(cfgFileSize) ? 
//    			Integer.valueOf(cfgFileSize) : defaultMaxFileSize;
//    	modelMap.addAttribute("maxFileSize",maxFileSize+"");
//
//		modelMap.addAttribute("rejectedAddress", rejectedAddress);
//		
//		//构建5种联系人组成的FormVo
//		ContactsFormVo contactsFormVo = new ContactsFormVo();
//		
//		// TODO 
//		
//		
//		
//		
//		
//		modelMap.addAttribute("contactsFormVo",contactsFormVo);
//		
//		
//		return "yitiansystem/merchants/supplier/update_information";
//	 }
	
	 public void splitBank(SupplierExtend supplierVo,SupplierVo supplier){
		 List<String> bankList=new ArrayList<String>();
		 List<String> accountList=new ArrayList<String>();
		 if(StringUtils.isNotBlank(supplier.getBank())&&supplier.getBank().startsWith("[")){
			 bankList=JSONArray.toList(JSONArray.fromObject(supplier.getBank()));
		 }
		 if(StringUtils.isNotBlank(supplier.getAccount())&&supplier.getAccount().startsWith("[")){
			 accountList=JSONArray.toList(JSONArray.fromObject(supplier.getAccount()));
		 }
		 if(bankList.size()>0){
			 supplier.setBank(bankList.get(0));
		 }
		 if(bankList.size()>1){
			 supplierVo.setBankSecond(bankList.get(1));
		 }
		 if(bankList.size()>2){
			 supplierVo.setBankThird(bankList.get(2));
		 }
		 if(accountList.size()>0){
			 supplier.setAccount(accountList.get(0));
		 }
		 if(accountList.size()>1){
			 supplierVo.setAccountSecond(accountList.get(1));
		 }
		 if(accountList.size()>2){
			 supplierVo.setAccountThird(accountList.get(2));
		 }
		 }
	
	/**
	 * 商家编辑-更新保存  旧版本（适合普通供应商）
	 * 
	 * @param modelMap
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("update_supplier")
	public String update_supplier(ModelMap modelMap, Supplier supplier,SupplierExtend  supplierExtend, HttpServletRequest req) throws Exception {
		this.mergeBank(supplierExtend, supplier);
		Integer result = purchaseApiService.updateSupplier(supplier);
		
		SystemmgtUser user = GetSessionUtil.getSystemUser(req);
		// 添加日志
		SupplierUpdateHistory history = new SupplierUpdateHistory();
		history.setId(UUIDGenerator.getUUID());
		history.setOperationTime(new Date());
		history.setOperator(user == null ? null : user.getUsername());
		history.setProcessing("修改供应商基本信息");
		history.setUpdateField("修改供应商基本信息");
		history.setSupplierId(supplier.getId());
		purchaseApiService.insertSupplierLog(history);
		
		return result > 0 ? "true" : "false";
	}
	
	/**
	 * 跳转商家更新历史记录页面
	 * 
	 * @author wang.m
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/to_querySupplierLog")
	public ModelAndView to_querySupplierLog(ModelMap model, Query query,
			MerchantsVo merchantsVo, String supplierId, String flag)
			throws Exception {
		PageFinder<Map<String, Object>> pageFinder = merchantsService
				.getSupplierUpdateHistoryList(query, merchantsVo, supplierId);
		model.addAttribute("pageFinder", pageFinder);
		model.addAttribute("supplierId", supplierId);
		model.addAttribute("merchantsVo", merchantsVo);
		model.addAttribute("flag", flag);
		return new ModelAndView("yitiansystem/merchants/select_supplier_log", model);
	}

	/**
	 * 查询商家操作日志
	 * 
	 * @param merchantCode
	 * @param modelMap
	 * @param query
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping("viewMerchantOperationLog")
	public ModelAndView viewMerchantOperationLog(String merchantCode,
			ModelMap modelMap, com.yougou.merchant.api.common.Query query) throws Exception {
		//SupplierVo supplierVo = this.merchantServiceNew.getMerchantVoByCode(merchantCode);
		
		PageFinder<MerchantOperationLog> pageFinder = merchantOperatorApi
				.queryMerchantOperationLog(merchantCode, query);
		modelMap.addAttribute("pageFinder", pageFinder);
		modelMap.addAttribute("merchantCode", merchantCode);
		return new ModelAndView("yitiansystem/merchants/view_operation_log", modelMap);
	}
	
	/**
	 * 查询商家某种操作日志
	 * 
	 * @param merchantCode
	 * @param modelMap
	 * @param query
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping("viewMOperationLog")
	public ModelAndView viewMOperationLog(String merchantCode,String operationType,
			ModelMap modelMap, com.yougou.merchant.api.common.Query query) throws Exception {
		//SupplierVo supplierVo = this.merchantServiceNew.getMerchantVoByCode(merchantCode);
		
		PageFinder<MerchantOperationLog> pageFinder = merchantOperatorApi
				.queryMOperationLog(merchantCode,operationType, query);
		modelMap.addAttribute("pageFinder", pageFinder);
		modelMap.addAttribute("merchantCode", merchantCode);
		modelMap.addAttribute("operationType", operationType);
		return new ModelAndView("yitiansystem/merchants/view_m_operation_log", modelMap);
	}
	
	/**
	 * 查询商家操作日志
	 * 
	 * @param merchantCode
	 * @param modelMap
	 * @param query
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping("/viewAppOperationLog")
	public ModelAndView viewAppOperationLog(String appKey,
			ModelMap modelMap, Query query) throws Exception {
		// 沿用旧的查日志方式
		PageFinder<com.belle.yitiansystem.merchant.model.pojo.MerchantOperationLog> pageFinder = merchantOperationLogService
				.queryMerchantOperationLog(appKey, query);
		modelMap.addAttribute("pageFinder", pageFinder);
		modelMap.addAttribute("appKey", appKey);
		return new ModelAndView("yitiansystem/merchants/view_app_operation_log", modelMap);
	}

	/**
	 * 删除商家帐号信息
	 * 
	 * @author wang.M
	 * @throws Exception
	 * 
	 */
	@ResponseBody
	@RequestMapping("delete_merchant_user")
	public String delete_merchant_user(ModelMap model, String id,
			HttpServletRequest request) throws Exception {
		SystemmgtUser user = GetSessionUtil.getSystemUser(request);
		boolean isDeleteSuccess = merchantServiceNew.deleteMerchantUser(id, user.getLoginName());
		return isDeleteSuccess ? "success" : "fail";
	}

	/**
	 * 添加商家优购管理员帐号
	 * 
	 * @author zhuang.rb
	 * @throws Exception
	 * 
	 */
	@RequestMapping("toAddMerchantYougouAdmin")
	public String toAddMerchantYougouAdmin(ModelMap model,String yg_admin_type) {
		model.put("yg_admin_type", yg_admin_type);
		return "yitiansystem/merchants/merchant_yougou_admin";
	}

	/**
	 * 保存商家优购管理员帐号
	 * 
	 * @author zhuang.rb
	 * @throws Exception
	 * 
	 */
	@RequestMapping("toEditMerchantYougouAdmin")
	public String toEditMerchantYougouAdmin(ModelMap model, String merchantUserId,String yg_admin_type) throws Exception {
		model.put("merchantUser", merchantsService.getMerchantUserById(merchantUserId));
		model.put("isModify", "true");
		model.put("yg_admin_type", yg_admin_type);
		return "yitiansystem/merchants/merchant_yougou_admin";
	}

	/**
	 * 保存商家优购管理员帐号
	 * 
	 * @author zhuang.rb
	 * @throws Exception
	 * 
	 */
	@RequestMapping("saveMerchantYougouAdmin")
	public String saveMerchantYougouAdmin(ModelMap model,
			HttpServletRequest request, MerchantUser merchantUser,String yg_admin_type) {
		SystemmgtUser user = GetSessionUtil.getSystemUser(request);
		boolean isSaveSuccess = true;
		try {
			if ("".equals(merchantUser.getId())) {
				merchantUser.setId(null);
			}
			merchantUser.setPassword(MD5Util.getMD5String(merchantUser
					.getPassword()));
			merchantUser.setCreater(user.getUsername());
			merchantUser.setCreateTime(DateUtil.getDateTime(new Date()));
			if(StringUtils.isNotBlank(yg_admin_type)){
				merchantUser.setIsYougouAdmin(2);
			}
			merchantsService.saveMerchantUser(merchantUser);
		} catch (Exception ex) {
			isSaveSuccess = false;
			ex.printStackTrace();
		}
		if (isSaveSuccess) {
			model.addAttribute("message", "成功保存商家优购管理员!");
		} else {
			model.addAttribute("message", "保存商家优购管理员失败!");
		}
		model.addAttribute("refreshflag", "1");
		model.addAttribute(
				"methodName",
				"/yitiansystem/merchants/businessorder/to_merchants_user_list.sc?isYougouAdmin=1");
		return "yitiansystem/merchants/merchants_message";
	}

	/**
	 * 管理员拥有商家列表
	 * 
	 * @author zhuang.rb
	 * @throws Exception
	 * 
	 */
	@RequestMapping("toHadYougouAdminMerchant")
	public String toHadYougouAdminMerchant(ModelMap modelMap, Query query,
			String merchantUserId, String loginName, String merchantCode,
			String merchantName, Integer isInputYougouWarehouse) {
		modelMap.put("pageFinder", merchantsService
				.queryYougouAdminMerchantList(query, merchantUserId,
						merchantCode, merchantName, isInputYougouWarehouse));
		modelMap.put("merchantUserId", merchantUserId);
		modelMap.put("loginName", loginName);
		modelMap.put("merchantCode", merchantCode);
		modelMap.put("merchantName", merchantName);
		modelMap.put("isInputYougouWarehouse", isInputYougouWarehouse);
		return "yitiansystem/merchants/merchant_yougou_admin_had";
	}

	/**
	 * 设置管理员拥有商家
	 * 
	 * @author zhuang.rb
	 * @throws Exception
	 * 
	 */
	@RequestMapping("toSetYougouAdminMerchant")
	public String toSetYougouAdminMerchant(ModelMap modelMap, Query query,
			String merchantUserId, String loginName, String merchantCode,
			String merchantName, Integer isInputYougouWarehouse) {
		modelMap.put("pageFinder", merchantsService.queryMerchantNotHadList(
				query, merchantUserId, merchantCode, merchantName,
				isInputYougouWarehouse));
		modelMap.put("merchantUserId", merchantUserId);
		modelMap.put("loginName", loginName);
		modelMap.put("merchantCode", merchantCode);
		modelMap.put("merchantName", merchantName);
		modelMap.put("isInputYougouWarehouse", isInputYougouWarehouse);
		return "yitiansystem/merchants/merchant_yougou_admin_list";
	}

	/**
	 * 删除管理员拥有的商家
	 * 
	 * @author zhuang.rb
	 * @throws Exception
	 * 
	 */
	@RequestMapping("toDelYougouAdminsMerchant")
	public String toDelYougouAdminsMerchant(ModelMap modelMap,
			String merchantUserId, String loginName, String id) {
		boolean isDeleteSuccess = merchantsService.delYougouAdminsMerchant(id);
		if (isDeleteSuccess) {
			modelMap.addAttribute("message", "成功保存商家优购管理员!");
		} else {
			modelMap.addAttribute("message", "保存商家优购管理员失败!");
		}
		modelMap.addAttribute("refreshflag", "1");
		MerchantUser user=null;
		try {
			user=merchantsService.getMerchantUserById(merchantUserId);
		} catch (SQLException e) {
			logger.error("根据id查询用户异常:",e);
		}
		if(user!=null&&user.getIsYougouAdmin()!=null&&user.getIsYougouAdmin()==2){
			modelMap.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_merchants_businessAdmin_list.sc");
		}else{
			modelMap.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_merchants_admin_list.sc");
		}
		return "yitiansystem/merchants/merchants_message";
	}

	/**
	 * 增加管理员拥有的商家
	 * 
	 * @author zhuang.rb
	 * @throws Exception
	 * 
	 */
	@RequestMapping("toSaveYougouAdminsMerchant")
	public String toSaveYougouAdminsMerchant(ModelMap modelMap,
			String merchantUserId, String loginName, String[] merchantCode) {
		boolean isSaveSuccess = false;
		try {
			isSaveSuccess = merchantsService.saveYougouAdminMerchant(
					merchantUserId, merchantCode);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (isSaveSuccess) {
			modelMap.addAttribute("message", "成功保存商家优购管理员!");
		} else {
			modelMap.addAttribute("message", "保存商家优购管理员失败!");
		}
		modelMap.addAttribute("refreshflag", "1");
		MerchantUser user=null;
		try {
			user=merchantsService.getMerchantUserById(merchantUserId);
		} catch (SQLException e) {
			logger.error("根据id查询用户异常:",e);
		}
		if(user!=null&&user.getIsYougouAdmin()!=null&&user.getIsYougouAdmin()==2){
			modelMap.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_merchants_businessAdmin_list.sc");
		}else{
			modelMap.addAttribute("methodName",
					"/yitiansystem/merchants/businessorder/to_merchants_admin_list.sc");
		}

		return "yitiansystem/merchants/merchants_message";
	}

	/**
	 * 通过brandNos加载分类Tree
	 * 
	 * @param request
	 * @param brandNos
	 * @return 分类Tree结构 【json】
	 */
	@ResponseBody
	@RequestMapping("/getBrandCatList")
	public String getBrandCatList(HttpServletRequest request, String brandNos) {
		if (StringUtils.isBlank(brandNos))
			return null;

		List<Category> list = this.getCategoryTreeMode(Arrays.asList(brandNos
				.split(";")));

		JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(new PropertyFilter() {
			@Override
			public boolean apply(Object source, String name, Object value) {
				if (name.equals("id") || name.equals("catName")
						|| name.equals("structName") || name.equals("catLeave")
						|| name.equals("catNo") || name.equals("parentId")) {
					return false;
				}
				return true;
			}
		});
		JSONArray jsonArray = JSONArray.fromObject(list, config);
		return jsonArray.toString();
	}

	/**
	 * <p>
	 * 模拟使用category对象来构造一个品类 Tree mode
	 * </p>
	 * 
	 * @param brandNoList
	 *            brandNo之间使用;分隔
	 * @return Tree Node 集合
	 */
	private List<Category> getCategoryTreeMode(List<String> brandNos) {
		List<Category> list = new ArrayList<Category>();

		for (String brandNo : brandNos) {
			if (StringUtils.isBlank(brandNo))
				continue;

			com.yougou.pc.model.brand.Brand brand = null;
			try {
				brand = commodityBaseApiService.getBrandByNo(brandNo);
			} catch (Exception e) {
				logger.error(MessageFormat.format(
						"commodityBaseApiSercie接口获取品牌[{0}]失败", brandNo), e);
			}
			if (null == brand)
				continue;

			// 获取分类
			List<Category> thirdcats = commodityBaseApiService
					.getCategoryListByBrandId(brand.getId(), (short) 1, null);
			Set<String> firstAndSecondCat = new HashSet<String>();
			// 逆推第一级和第二级分类
			for (Category category : thirdcats) {
				if (StringUtils.isBlank(category.getStructName())
						|| category.getStructName().length() != 8)
					continue;

				firstAndSecondCat.add(category.getStructName().substring(0, 2));
				firstAndSecondCat.add(category.getStructName().substring(0, 5));
				category.setParentId(brand.getBrandNo() + ";"
						+ this.subCategory(category.getStructName()));
				category.setStructName(brand.getBrandNo() + ";"
						+ category.getStructName());
				// 0：未选中 1：选中
				category.setIsEnabled((short) 0);
			}
			if (CollectionUtils.isNotEmpty(firstAndSecondCat)) {
				for (String str : firstAndSecondCat) {
					Category _cat = commodityBaseApiService
							.getCategoryByStructName(str);
					_cat.setParentId(brand.getBrandNo() + ";"
							+ this.subCategory(_cat.getStructName()));
					_cat.setStructName(brand.getBrandNo() + ";"
							+ _cat.getStructName());
					_cat.setIsEnabled((short) 0);
					thirdcats.add(_cat);
				}
			}

			Category _brandWapper = new Category();
			_brandWapper.setCatLeave(-1);
			_brandWapper.setCatName(brand.getBrandName());
			_brandWapper.setCatNo(brand.getBrandNo());
			_brandWapper.setParentId("-1");
			_brandWapper.setStructName(brand.getBrandNo() + ";0");
			_brandWapper.setId(brand.getId());
			_brandWapper.setIsEnabled((short) 0);
			thirdcats.add(_brandWapper);

			list.addAll(thirdcats);
		}

		return list;
	}

	/**
	 * 通过品牌分类 brandNo;structName 字段来匹配被选中节点
	 * 
	 * @param treeModes
	 * @param brandStructs
	 */
	private void setTreeModesCheckStatus(List<Category> treeModes,
			List<String> brandStructs) {
		// 存储被选中的父节点
		Set<String> structNames = new HashSet<String>();
		if (CollectionUtils.isNotEmpty(treeModes)) {
			for (Category category : treeModes) {
				if (category.getCatLeave() == 3) {
					if (brandStructs.contains(category.getStructName())) {
						category.setIsEnabled((short) 1);

						String structName = category.getStructName();
						// 设置parent为选中
						structNames.add(structName.split(";")[0] + ";0");
						structNames.add(structName.substring(0,
								structName.indexOf(";") + 3));
						structNames.add(structName.substring(0,
								structName.indexOf(";") + 6));
					}
				}
			}
			for (Category category : treeModes) {
				if (category.getCatLeave() != 3) {
					if (structNames.contains(category.getStructName())) {
						category.setIsEnabled((short) 1);
					}
				}
			}
		}
	}
	
	@RequestMapping("updateEmail")
	public String updateEmail(ModelMap model,com.yougou.merchant.api.supplier.vo.MerchantUser merchantUser,HttpServletRequest request) throws Exception {
		if ( merchantUser==null || StringUtils.isBlank(merchantUser.getId()) || StringUtils.isBlank(merchantUser.getEmail())) {
			return "0"; //修改失败,请输入正确的email地址
		}
		SystemmgtUser user = GetSessionUtil.getSystemUser(request);
		boolean result = merchantServiceNew.updateEmail(merchantUser,user.getLoginName());
		if (result) {
			model.addAttribute("message", "成功替换绑定邮箱!");
		} else {
			model.addAttribute("message", "替换绑定邮箱失败!");
		}
		model.addAttribute("refreshflag", "1");
		model.addAttribute(
				"methodName",
				"/yitiansystem/merchants/businessorder/to_merchants_user_list.sc");
		return "yitiansystem/merchants/merchants_message";
	}
	

	@RequestMapping("/updateMobile")
	public String updateMobile(ModelMap model,com.yougou.merchant.api.supplier.vo.MerchantUser merchantUser, HttpServletRequest request){
		if ( merchantUser==null || StringUtils.isBlank(merchantUser.getId()) || StringUtils.isBlank(merchantUser.getMobileCode() )) {
			return "0"; //修改失败,请输入正确的mobile
		}
		SystemmgtUser user = GetSessionUtil.getSystemUser(request);
		boolean result = merchantServiceNew.updateMobile(merchantUser,user.getLoginName());
		if (result) {
			model.addAttribute("message", "成功替换绑定手机!");
		} else {
			model.addAttribute("message", "替换绑定手机失败!");
		}
		model.addAttribute("refreshflag", "1");
		model.addAttribute(
				"methodName",
				"/yitiansystem/merchants/businessorder/to_merchants_user_list.sc");
		return "yitiansystem/merchants/merchants_message";
	}
	
    /**
	 * 
	 * 查询商家信息列表
	 * @Date 2012-04-25
	 * @author wang.m
	 */
    @RequestMapping("to_addSupplierName_list")
    public String to_addSupplierName_list(Query query, ModelMap modelMap, MerchantsVo merchantsVo) throws Exception {
    	//PageFinder<Map<String,Object>> pageFinder = merchantsService.queryMerchantsList(query,merchantsVo);
    	SupplierQueryVo vo = new SupplierQueryVo();
		vo.setSupplier(merchantsVo.getSupplier());
		vo.setSupplierCode(merchantsVo.getSupplierCode());
		com.yougou.merchant.api.common.Query _query = new com.yougou.merchant.api.common.Query(query.getPageSize());
		_query.setPage(query.getPage());
		com.yougou.merchant.api.common.PageFinder<SupplierVo> pageFinder = supplierService.querySupplierListByPage(vo, _query);
		
    	modelMap.addAttribute("pageFinder", pageFinder);
    	modelMap.addAttribute("merchantsVo", merchantsVo);
  	  return "yitiansystem/merchants/to_addSupplierName_list";
    }
    
    @RequestMapping("getSupplierContractList")
    public String getSupplierContractList(Query query, ModelMap modelMap, MerchantsVo merchantsVo) throws Exception {
    	//PageFinder<Map<String,Object>> pageFinder = merchantsService.queryMerchantsList(query,merchantsVo);
    	SupplierQueryVo vo = new SupplierQueryVo();
		vo.setSupplier(merchantsVo.getSupplier());
		vo.setSupplierCode(merchantsVo.getSupplierCode());
		com.yougou.merchant.api.common.Query _query = new com.yougou.merchant.api.common.Query(query.getPageSize());
		_query.setPage(query.getPage());
		com.yougou.merchant.api.common.PageFinder<SupplierVo> pageFinder = supplierService.querySupplierListByPage(vo, _query);
		
    	modelMap.addAttribute("pageFinder", pageFinder);
    	modelMap.addAttribute("merchantsVo", merchantsVo);
  	  	return "yitiansystem/merchants/supplier_contract_list";
    }
        
    
	
	/**
	 * 获取供应商最新的一个合同ID
	 * @param modelMap ModelMap
	 * @param request HttpServletRequest
	 * @param supplierId 供应商ID
	 * @return JSON格式字符串，格式：{contractId:12345678}
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
    @RequestMapping("getLatestContractId")
   	public String getLatestContractId(ModelMap modelMap,HttpServletRequest request,String supplierId) {
    	JSONObject result = new JSONObject();
    	String contractId = "";
    	List<com.belle.yitiansystem.merchant.model.pojo.SupplierContract> supplierContracts = supplierContractService.selectSupplierContractListBySupplierId(supplierId);
    	if(CollectionUtils.isNotEmpty(supplierContracts)){
    		contractId = supplierContracts.get(0).getId();
    	}
    	result.put("contractId",contractId);
      	return  result.toString();
   	}
	
	@ResponseBody
	@RequestMapping("/refreshCache")
	public String refreshCache(String merchantCode){
		MerchantUser user = merchantsService.getMerchantsBySuppliceCode(merchantCode);
		if(user!=null){
			try {
				merchantsService.loadAuthResource(user.getId());
				return "1";
			} catch (Exception e) {
				logger.error("给商家:{}刷新权限缓存失败:{}",
						new Object[]{
						ToStringBuilder.reflectionToString(user, ToStringStyle.SHORT_PREFIX_STYLE),e});
				return "0";
			}
		}else{
			return "-1";
		}
	}
	
	/**
	 * deleteCache:删除账号的权限缓存，并没有在数据库删除 
	 * @author li.n1 
	 * @param merchantCode
	 * @return 
	 * @since JDK 1.6
	 */
	@ResponseBody
	@RequestMapping("/{merchantCode}/deleteCache")
	public String deleteCache(@PathVariable("merchantCode") String merchantCode){
		MerchantUser user = merchantsService.getMerchantsBySuppliceCode(merchantCode);
		if(user!=null){
			try {
				merchantsService.delAuthResource(user.getId());
				return "1";
			} catch (Exception e) {
				logger.error("给商家:{}删除权限缓存失败:{}",
						new Object[]{
						ToStringBuilder.reflectionToString(user, ToStringStyle.SHORT_PREFIX_STYLE),e});
				return "0";
			}
		}else{
			return "-1";
		}
	}
	
	@ResponseBody
	@RequestMapping("/deleteCacheByKey")
	public String deleteCacheByKey(String key){
		//返回删除的缓存数量
		return String.valueOf(merchantsService.deleteCacheByKey(key));
	}
}
