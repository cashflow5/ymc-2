package com.belle.yitiansystem.merchant.web.controller;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.belle.infrastructure.constant.Constant;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.infrastructure.util.DateUtil;
import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.other.model.vo.ResultMsg;
import com.belle.yitiansystem.merchant.model.pojo.MerchantOperationLog;
import com.belle.yitiansystem.merchant.model.pojo.MerchantOperationLog.OperationType;
import com.belle.yitiansystem.merchant.service.IMerchantOperationLogService;
import com.belle.yitiansystem.merchant.service.IMerchantServiceNew;
import com.belle.yitiansystem.merchant.service.IMerchantsService;
import com.belle.yitiansystem.merchant.util.ExportHelper;
import com.belle.yitiansystem.merchant.util.StatusUtil;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.yougou.fss.api.IFSSYmcApiService;
import com.yougou.fss.api.vo.FSSResult;
import com.yougou.fss.api.vo.PageFinder;
import com.yougou.fss.api.vo.ShopRuleVO;
import com.yougou.fss.api.vo.ShopVO;
import com.yougou.merchant.api.supplier.vo.SupplierVo;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.model.brand.Brand;
import com.yougou.pc.model.category.Category;

/**
 * 店铺装修
 * 
 * @author mei.jf
 * 
 */
@Controller
@RequestMapping("/merchant/shop/decorate")
public class ShopDecorateController {

    private final String BASE_PATH = "yitiansystem/merchant/shop/";

    private static final Logger logger = Logger.getLogger(ShopDecorateController.class);
    @Resource
    private IFSSYmcApiService iFSSYmcApiService;

	@Resource
	private IMerchantServiceNew merchantServiceNew;
	
	@Resource
	private IMerchantsService merchantsService;

	@Resource
	private ICommodityBaseApiService commodityBaseApiService;
	
	@Resource
	private IMerchantOperationLogService merchantOperationLogService;
    /**
     * 店铺列表查询
     * 
     * @param modelMap
     * @param vo
     * @param query
     * @return
     */
    @RequestMapping("/shoplist")
    public String shopList(ModelMap model, ShopVO vo, com.yougou.ordercenter.common.Query query, HttpServletRequest request) {
        String createDateTimeBegin = "";
        String createDateTimeEnd = "";
        String publishDateTimeBegin = "";
        String publishDateTimeEnd = "";
        if (StringUtils.isNotEmpty(vo.getCreateDateTimeBegin())) {
            createDateTimeBegin = vo.getCreateDateTimeBegin();
            vo.setCreateDateTimeBegin(createDateTimeBegin + " 00:00:00");
        }
        if (StringUtils.isNotEmpty(vo.getCreateDateTimeEnd())) {
            createDateTimeEnd = vo.getCreateDateTimeEnd();
            vo.setCreateDateTimeEnd(createDateTimeEnd + " 23:59:59");
        }
        if(StringUtils.isNotEmpty(vo.getPublishDateTimeBegin())){
        	publishDateTimeBegin = vo.getPublishDateTimeBegin();
        	vo.setPublishDateTimeBegin(publishDateTimeBegin+" 00:00:00");
        }
        if(StringUtils.isNotEmpty(vo.getPublishDateTimeEnd())){
        	publishDateTimeEnd = vo.getPublishDateTimeEnd();
        	vo.setPublishDateTimeEnd(publishDateTimeEnd+" 23:59:59");
        }
        FSSResult result = iFSSYmcApiService.getShopList(vo, query.getPage(), query.getPageSize(), null, null);
        if (!"".equals(createDateTimeBegin)) {
            vo.setCreateDateTimeBegin(createDateTimeBegin);
        }
        if (!"".equals(createDateTimeEnd)) {
            vo.setCreateDateTimeEnd(createDateTimeEnd);
        }
        if (!"".equals(vo.getPublishDateTimeBegin())) {
            vo.setPublishDateTimeBegin(publishDateTimeBegin);
        }
        if (!"".equals(vo.getPublishDateTimeEnd())) {
            vo.setPublishDateTimeEnd(publishDateTimeEnd);
        }
        PageFinder pageFinder = (PageFinder) result.getData();
        if(pageFinder != null) {
            for (ShopVO shopVO : (List<ShopVO>) pageFinder.getData()) {
                if (StringUtils.isNotEmpty(shopVO.getBrandIds())) {
                	String[] brandIds = shopVO.getBrandIds().split(",");
                	String brandNames = "";
                	for(String brandId : brandIds) {
                		Brand brand = commodityBaseApiService.getBrandById(brandId);
                		if(brand != null) {
                			brandNames += brand.getBrandName() + "<br/>";
                		}
                	}
        			shopVO.setBrandNames(brandNames);
                }
            }
        }
        model.addAttribute("vo", vo);
        model.addAttribute("pageFinder", pageFinder);
        return BASE_PATH + "shop_list2";
    }
    
    @RequestMapping("/exportShoplist")
    public void exportShoplist( ModelMap model, ShopVO vo,HttpServletRequest request,HttpServletResponse response) throws Exception {
    	 String createDateTimeBegin = "";
         String createDateTimeEnd = "";
         String publishDateTimeBegin = "";
         String publishDateTimeEnd = "";
         if (StringUtils.isNotEmpty(vo.getCreateDateTimeBegin())) {
             createDateTimeBegin = vo.getCreateDateTimeBegin();
             vo.setCreateDateTimeBegin(createDateTimeBegin + " 00:00:00");
         }
         if (StringUtils.isNotEmpty(vo.getCreateDateTimeEnd())) {
             createDateTimeEnd = vo.getCreateDateTimeEnd();
             vo.setCreateDateTimeEnd(createDateTimeEnd + " 23:59:59");
         }
         if(StringUtils.isNotEmpty(vo.getPublishDateTimeBegin())){
         	publishDateTimeBegin = vo.getPublishDateTimeBegin();
         	vo.setPublishDateTimeBegin(publishDateTimeBegin+" 00:00:00");
         }
         if(StringUtils.isNotEmpty(vo.getPublishDateTimeEnd())){
         	publishDateTimeEnd = vo.getPublishDateTimeEnd();
         	vo.setPublishDateTimeEnd(publishDateTimeEnd+" 23:59:59");
         }
         FSSResult result = iFSSYmcApiService.getShopList(vo,1,1000, null, null);
         List<Object[]> responseList = new ArrayList<Object[]>();
         Object[] _obj = null;
         PageFinder pageFinder = (PageFinder) result.getData();
         if(pageFinder != null) {
        	 SimpleDateFormat dfNew = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
             for (ShopVO shopVO : (List<ShopVO>) pageFinder.getData()) {
            	 
            	 // 加工品牌名称
                 if (StringUtils.isNotEmpty(shopVO.getBrandIds())) {
                 	String[] brandIds = shopVO.getBrandIds().split(",");
                 	String brandNames = "";
                 	for(String brandId : brandIds) {
                 		Brand brand = commodityBaseApiService.getBrandById(brandId);
                 		if(brand != null) {
                 			brandNames += brand.getBrandName() + " ";
                 		}
                 	}
         			shopVO.setBrandNames(brandNames);
                 }
                 _obj = new Object[8];
                 // 封装导出List：responseList
                 _obj[0] = shopVO.getMerchantName();
                 _obj[1] = shopVO.getShopName();
                 _obj[2] = shopVO.getBrandNames();
                 _obj[3] = (null==shopVO.getCreateDateTime())?"":dfNew.format( shopVO.getCreateDateTime() );
                 _obj[4] = (null==shopVO.getPublishDateTime())?"":dfNew.format(  shopVO.getPublishDateTime() );
                 String access = shopVO.getAccess();
                 int status = shopVO.getShopStatus();
                 _obj[5] = StatusUtil.getStatusName(access, status);
                 _obj[6] = shopVO.getLastUpdateUserId();
                 _obj[7] =  StatusUtil.getAuditName( shopVO.getAuditFlag() );
                 
                 responseList.add(_obj);
             }
        }
        ExportHelper exportHelper = new ExportHelper();
         
        String sheetName = DateUtil.formatDateByFormat(new Date(),"yyyy-MM-dd");
        String fileRealPath = request.getSession().getServletContext().getRealPath( Constant.TEMPLATE_PATH );
        String templatePath = fileRealPath +"/"+ Constant.SHOP_XLS;
        int[] params = {1,8};
        int[] amountCol = null;
        Map<Integer,Integer> indexMap = new HashMap<Integer,Integer>();
        indexMap.put(0, 0);
		indexMap.put(1, 1);
		indexMap.put(2, 2);
		indexMap.put(3, 3);
		indexMap.put(4, 4);
		indexMap.put(5, 5);
		indexMap.put(6, 6);
		indexMap.put(7, 7);
		exportHelper.doExport(responseList, templatePath, Constant.SHOP_XLS,
 									sheetName, null, params, null, amountCol, indexMap, false, response);
        
    	
    }

    /**
     * 跳转创建品牌旗舰店页面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/toCreateFssShop")
    public String toCreateFssShop(ModelMap model, HttpServletRequest request) {
    	String supplierId = request.getParameter("supplierId");
    	String supplierCode = request.getParameter("supplierCode");
    	String supplierName = request.getParameter("supplierName");
		try {
			if(supplierId != null && (supplierId = supplierId.trim()).length() > 0){
	    		model.put("supplierId", supplierId);
				SupplierVo supplier = this.merchantServiceNew.getSupplierVoById(supplierId);
				supplierName = supplier.getSupplier();
				// 查询授权品牌.[brandNo;brandName_brandNo;brandName...]
//				String brandStrs;
//				brandStrs = merchantsService.queryAuthorizationBrandBysupplierId(supplierId);
				List<String> brandNos = merchantsService.getAuthorizationBrandNos(supplierId);
				//List<Category> treeModes = this.getCategoryTreeMode(brandNos);
				//新需求修改，只显示到第一级分类节点
				List<Category> treeModes = this.getFirstCategoryTreeMode(brandNos);
				// 品牌分类关系集合
//				List<String> brandStructs = merchantsService.queryAuthorizationBrandCatBysupplierId(supplierId);
//				this.setTreeModesCheckStatus(treeModes, brandStructs);
//				model.addAttribute("brandStrs", brandStrs);
				model.addAttribute("treeModes", treeModes);
				model.addAttribute("supplierId", supplierId);
				model.addAttribute("supplier", supplier);
    		}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
    	if(supplierCode != null && (supplierCode = supplierCode.trim()).length() > 0) {
    		model.put("supplierCode", supplierCode);
    	}
    	if(supplierName != null && (supplierName = supplierName.trim()).length() > 0) {
    		model.put("supplierName", supplierName);
    	}
        return BASE_PATH + "shop_create";
    }
    
    /** 
	 * getFirstCategoryTreeMode:获取品牌与第一级分类节点 
	 * @author li.n1 
	 * @param brandNos
	 * @return 
	 * @since JDK 1.6 
	 */  
	private List<Category> getFirstCategoryTreeMode(List<String> brandNos) {
		List<Category> list = new ArrayList<Category>();

		for (String brandNo : brandNos) {
			if (StringUtils.isBlank(brandNo))
				continue;

			com.yougou.pc.model.brand.Brand brand = null;
			try {
				brand = commodityBaseApiService.getBrandByNo(brandNo);
			} catch (Exception e) {
				logger.error(MessageFormat.format("commodityBaseApiSercie接口获取品牌[{0}]失败", brandNo), e);
			}
			if (null == brand)
				continue;
			// 获取分类
			List<Category> thirdcats = commodityBaseApiService.getCategoryListByBrandId(brand.getId(), (short) 1, null);
			Set<String> firstAndSecondCat = new HashSet<String>();
			// 逆推第一级和第二级分类
			for (Category category : thirdcats) {
				if (StringUtils.isBlank(category.getStructName()) || category.getStructName().length() != 8)
					continue;

				firstAndSecondCat.add(category.getStructName().substring(0, 2));
				//firstAndSecondCat.add(category.getStructName().substring(0, 5));
				//category.setParentId(brand.getBrandNo() + ";" + this.subCategory(category.getStructName()));
				//category.setStructName(brand.getBrandNo() + ";" + category.getStructName());
				// 0：未选中 1：选中
				//category.setIsEnabled((short) 0);
			}
			thirdcats.clear();
			if (CollectionUtils.isNotEmpty(firstAndSecondCat)) {
				for (String str : firstAndSecondCat) {
					Category _cat = commodityBaseApiService.getCategoryByStructName(str);
					_cat.setParentId(brand.getBrandNo() + ";" + this.subCategory(_cat.getStructName()));
					_cat.setStructName(brand.getBrandNo() + ";" + _cat.getStructName());
					_cat.setIsEnabled((short) 0);
					thirdcats.add(_cat);
				}
			}

			Category _brandWapper = new Category();
			_brandWapper.setCatLeave(-1);
			_brandWapper.setCatName(brand.getBrandName());
			_brandWapper.setCatNo(brand.getBrandNo());
			_brandWapper.setParentId("-1");
			_brandWapper.setStructName(brand.getBrandNo() + ";");
			_brandWapper.setId(brand.getId());
			_brandWapper.setIsEnabled((short) 0);
			thirdcats.add(_brandWapper);
			
			list.addAll(thirdcats);
		}
		return list;
	}

	/**
     * 跳转编辑修改品牌旗舰店页面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/toModifyFssShop")
    public String toModifyFssShop(ModelMap model, HttpServletRequest request,@RequestParam String shopId) {
    	String merchantCode = null;
    	//Map<String, ShopRuleVO> mapShopRule = null;
    	if(shopId != null && (shopId = shopId.trim()).length() > 0) {
			ShopVO shopVo = iFSSYmcApiService.selectShopById(shopId);
    		model.put("shopVo", shopVo);
    		merchantCode = shopVo.getMerchantCode();
    		//List<ShopRuleVO> lstShopRule = shopVo.getShopRuleList();
    		//mapShopRule = new HashMap<String, ShopRuleVO>(lstShopRule.size());
//    		String brandNo = null;
    		//for(ShopRuleVO shopRule : lstShopRule) {
//    			//绑定品牌在经营类目中勾选
//    			if(brandNo == null || (brandNo != null && !brandNo.equals(shopRule.getBrandNo()))) {
//    				brandNo = shopRule.getBrandNo();
//    				mapShopRule.put(brandNo+";0", null);
//    			}
    			//mapShopRule.put(shopRule.getBrandNo()+";"+shopRule.getStructName(), shopRule);
    		//}
    	}
			if(merchantCode != null && (merchantCode = merchantCode.trim()).length() > 0){
				SupplierVo supplier = this.merchantServiceNew.getMerchantVoByCode(merchantCode);
				String supplierId = supplier.getId(); 
				// 查询授权品牌.[brandNo;brandName_brandNo;brandName...]
//				String brandStrs;
//				brandStrs = merchantsService.queryAuthorizationBrandBysupplierId(supplierId);
				/*List<String> brandNos = merchantsService.getAuthorizationBrandNos(supplierId);
				List<Category> treeModes = this.getCategoryTreeMode(brandNos);
				for(Category cate : treeModes) {
					if(mapShopRule != null && mapShopRule.containsKey(cate.getStructName())){
						cate.setIsEnabled((short) 1);
					} else {
						cate.setIsEnabled((short) 0);
					}
				}
				// 品牌分类关系集合
//				List<String> brandStructs = merchantsService.queryAuthorizationBrandCatBysupplierId(supplierId);
//				this.setTreeModesCheckStatus(treeModes, brandStructs);
//				model.addAttribute("brandStrs", brandStrs);
				model.addAttribute("treeModes", treeModes);*/
				model.addAttribute("supplierId", supplierId);
				model.addAttribute("supplier", supplier);
    		}
        return BASE_PATH + "shop_edit";
    }
    
    /**
     * 跳转编辑修改品牌旗舰店页面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/toModifyFssShopRule")
    public String toModifyFssShopRule(ModelMap model,@RequestParam String shopId) {
    	//String shopId = request.getParameter("shopId");
    	String merchantCode = null;
    	Map<String, ShopRuleVO> mapShopRule = null;
    	if(shopId != null && (shopId = shopId.trim()).length() > 0) {
			ShopVO shopVo = iFSSYmcApiService.selectShopById(shopId);
    		model.put("shopVo", shopVo);
    		merchantCode = shopVo.getMerchantCode();
    		List<ShopRuleVO> lstShopRule = shopVo.getShopRuleList();
    		mapShopRule = new HashMap<String, ShopRuleVO>(lstShopRule.size());
    		String brandNo = null;
    		for(ShopRuleVO shopRule : lstShopRule) {
    			//绑定品牌在经营类目中勾选
    			if(brandNo == null || (brandNo != null && !brandNo.equals(shopRule.getBrandNo()))) {
    				brandNo = shopRule.getBrandNo();
    				mapShopRule.put(brandNo+";", null);
    			}
    			if(null==shopRule.getStructName()||"0".equals(shopRule.getStructName())){
    				shopRule.setStructName("");
    			}
    			mapShopRule.put(shopRule.getBrandNo()+";"+shopRule.getStructName(), shopRule);
    		}
    	}
		try {
			if(merchantCode != null && (merchantCode = merchantCode.trim()).length() > 0){
				SupplierVo supplier = this.merchantServiceNew.getMerchantVoByCode(merchantCode);
				String supplierId = supplier.getId(); 
				// 查询授权品牌.[brandNo;brandName_brandNo;brandName...]
//				String brandStrs;
//				brandStrs = merchantsService.queryAuthorizationBrandBysupplierId(supplierId);
				List<String> brandNos = merchantsService.getAuthorizationBrandNos(supplierId);
				List<Category> treeModes = this.getCheckedFirstCategoryTreeMode(brandNos,mapShopRule);
				//System.out.println("原有的shopRule="+mapShopRule.keySet());
/*				for(Category cate : treeModes) {
						if(mapShopRule != null && mapShopRule.containsKey(cate.getStructName())){
							cate.setIsEnabled((short) 1);
						}else {
							cate.setIsEnabled((short) 0);
						}
						//System.out.println("分类结构名称="+cate.getStructName()+"\t分类名称="+cate.getCatName()+"\t是否选中="+cate.getIsEnabled());
				}
*/				
				// 品牌分类关系集合
//				List<String> brandStructs = merchantsService.queryAuthorizationBrandCatBysupplierId(supplierId);
//				this.setTreeModesCheckStatus(treeModes, brandStructs);
//				model.addAttribute("brandStrs", brandStrs);
				model.addAttribute("treeModes", treeModes);
				model.addAttribute("supplierId", supplierId);
				model.addAttribute("supplier", supplier);
    		}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
        return BASE_PATH + "shop_editShopRule";
    }
    

    /** 
	 * getCheckedFirstCategoryTreeMode:修改前获取有勾选的品牌与一级分类 
	 * @author li.n1 
	 * @param brandNos
	 * @param mapShopRule
	 * @return 
	 * @since JDK 1.6 
	 */  
	private List<Category> getCheckedFirstCategoryTreeMode(
			List<String> brandNos, Map<String, ShopRuleVO> mapShopRule) {
		List<Category> list = new ArrayList<Category>();

		for (String brandNo : brandNos) {
			if (StringUtils.isBlank(brandNo))
				continue;

			com.yougou.pc.model.brand.Brand brand = null;
			try {
				brand = commodityBaseApiService.getBrandByNo(brandNo);
			} catch (Exception e) {
				logger.error(MessageFormat.format("commodityBaseApiSercie接口获取品牌[{0}]失败", brandNo), e);
			}
			if (null == brand)
				continue;
			// 获取分类
			List<Category> thirdcats = commodityBaseApiService.getCategoryListByBrandId(brand.getId(), (short) 1, null);
			Set<String> firstAndSecondCat = new HashSet<String>();
			// 逆推第一级和第二级分类
			for (Category category : thirdcats) {
				if (StringUtils.isBlank(category.getStructName()) || category.getStructName().length() != 8)
					continue;

				firstAndSecondCat.add(category.getStructName().substring(0, 2));
				//firstAndSecondCat.add(category.getStructName().substring(0, 5));
				//category.setParentId(brand.getBrandNo() + ";" + this.subCategory(category.getStructName()));
				//category.setStructName(brand.getBrandNo() + ";" + category.getStructName());
				// 0：未选中 1：选中
				//category.setIsEnabled((short) 0);
			}
			thirdcats.clear();
			
			Category _brandWapper = new Category();
			_brandWapper.setCatLeave(-1);
			_brandWapper.setCatName(brand.getBrandName());
			_brandWapper.setCatNo(brand.getBrandNo());
			_brandWapper.setParentId("-1");
			_brandWapper.setStructName(brand.getBrandNo() + ";");
			_brandWapper.setId(brand.getId());
			if(mapShopRule.containsKey(_brandWapper.getStructName())){
				_brandWapper.setIsEnabled((short) 1);
			}else{
				_brandWapper.setIsEnabled((short) 0);
			}
			if (CollectionUtils.isNotEmpty(firstAndSecondCat)) {
				for (String str : firstAndSecondCat) {
					Category _cat = commodityBaseApiService.getCategoryByStructName(str);
					_cat.setParentId(brand.getBrandNo() + ";" + this.subCategory(_cat.getStructName()));
					_cat.setStructName(brand.getBrandNo() + ";" + _cat.getStructName());
					if(mapShopRule.containsKey(_cat.getStructName())||
							(_brandWapper.getIsEnabled()==(short)1&&
							checkUnique(mapShopRule.keySet(),brand.getBrandNo()))){
						_cat.setIsEnabled((short) 1);
					}else{
						_cat.setIsEnabled((short) 0);
					}
					thirdcats.add(_cat);
				}
			}
			thirdcats.add(_brandWapper);
			list.addAll(thirdcats);
		}
		return list;
	}
	
	private boolean checkUnique(Set<String> shopRuleSet,String brandNo){
		int count = 0;
		for(String rule : shopRuleSet){
			if(rule.startsWith(brandNo)){
				count++;
				if(count>1)
					return false;
			}
		}
		return true;
	}

	/**
     * 保存店铺信息
     * @param model
     * @param vo
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/saveFssShop")
    public String saveFssShop(ModelMap model, ShopVO vo, HttpServletRequest request) throws Exception {
        String operator = GetSessionUtil.getSystemUser(request).getUsername();
        ResultMsg resultMsg = new ResultMsg();
        String structs = vo.getBrandNames();
        if (StringUtils.isNotBlank(structs)) {
            structs = StringUtils.substring(structs, 0);
        }
        for (String struct : structs.split(",")) {
            ShopRuleVO shopRuleVO = new ShopRuleVO();
            shopRuleVO.setBrandNo(struct.split(";")[0]);
            shopRuleVO.setStructName(struct.split(";")[1]);
            vo.getShopRuleList().add(shopRuleVO);
        }
        vo.setBrandNames(null);
        try {
            vo.setAuditStatus(-1);
            vo.setLastUpdateUserId(operator);
            vo.setCreateDateTime(new Date());
            FSSResult result = iFSSYmcApiService.addOrUpdateShop(vo);
            if(result != null) {
            	String code = result.getCode();
            	if("0".equals(code)){
                    resultMsg.setReCode(code);
                    resultMsg.setSuccess(true);
            	} else {
                    resultMsg.setSuccess(false);
                    resultMsg.setMsg(result.getMessage());
                    resultMsg.setReCode(code);
                    logger.error(result.getMessage());
            	}
            } else {
                resultMsg.setSuccess(false);
                logger.error("保存店铺时返回结果==null");
            }
        } catch (Exception e) {
            resultMsg.setSuccess(false);
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return JSONObject.fromObject(resultMsg).toString();
    }
    
    
    @ResponseBody
    @RequestMapping("/saveFssShopRule")
    public String modifyFssShopRule(ModelMap model, ShopVO vo, HttpServletRequest request,@RequestParam String shopId) throws Exception {
        String operator = GetSessionUtil.getSystemUser(request).getUsername();
        ResultMsg resultMsg = new ResultMsg();
        String structs = vo.getBrandNames();
        if (StringUtils.isNotBlank(structs)) {
            structs = StringUtils.substring(structs, 0);
        }
        ShopVO shopVo = null;
        //只保留修改前勾选的品牌与一级分类（用作日志）
        String str1,str2 = "";
        List<String> cateStr = new ArrayList<String>();
        Category tempCate = null;
		Brand tempBrand = null;
		if(shopId != null && (shopId = shopId.trim()).length() > 0) {
			shopVo = iFSSYmcApiService.selectShopById(shopId);
			List<ShopRuleVO> lstShopRule = shopVo.getShopRuleList();
			for(ShopRuleVO shopRule : lstShopRule) {
				if((null!=shopRule.getBrandNo())&&(null==shopRule.getStructName())){	//品牌
					tempBrand = commodityBaseApiService.getBrandByNo(shopRule.getBrandNo());
					cateStr.add(tempBrand.getBrandName());
				}else if((null!=shopRule.getBrandNo())&&(null!=shopRule.getStructName())){	//分类
					tempCate = commodityBaseApiService.getCategoryByStructName(shopRule.getStructName());
					tempBrand = commodityBaseApiService.getBrandByNo(shopRule.getBrandNo());
					cateStr.add("("+tempBrand.getBrandName()+"->"+tempCate.getCatName()+")");
				}
			}
		}
		Collections.sort(cateStr);
		str1 = StringUtils.join(cateStr, "，");
        ShopRuleVO shopRuleVO = null;
        String[] structArr = null;
        for (String struct : structs.split(",")) {
            shopRuleVO = new ShopRuleVO();
            structArr = struct.split(";");
            shopRuleVO.setBrandNo(structArr[0]);
            if(structArr.length>1){
            	shopRuleVO.setStructName(structArr[1]);
            }
            vo.getShopRuleList().add(shopRuleVO);
        }
        vo.setBrandNames(null);
        try {
            vo.setLastUpdateUserId(operator);
            FSSResult result = iFSSYmcApiService.updateCommodityRule(vo);
            if(result != null) {
            	String code = result.getCode();
            	if("0".equals(code)){
                    resultMsg.setReCode(code);
                    resultMsg.setSuccess(true);
                    /** 添加店铺日志 **/
                    SystemmgtUser user = GetSessionUtil.getSystemUser(request);
                    String loginUser = "";
            		if (user != null) {
            			loginUser = user.getUsername();
            		}
            		//修改后勾选的品牌与一级分类（用作日志比较）
            		cateStr.clear();
            		tempCate = null;
            		tempBrand = null;
            		shopVo = iFSSYmcApiService.selectShopById(shopId);
            		List<ShopRuleVO> lstShopRule = shopVo.getShopRuleList();
            		for(ShopRuleVO shopRule : lstShopRule) {
            			if((null!=shopRule.getBrandNo())&&(null==shopRule.getStructName())){	//品牌
            				tempBrand = commodityBaseApiService.getBrandByNo(shopRule.getBrandNo());
            				cateStr.add(tempBrand.getBrandName());
            			}else if((null!=shopRule.getBrandNo())&&(null!=shopRule.getStructName())){	//分类
            				tempCate = commodityBaseApiService.getCategoryByStructName(shopRule.getStructName());
            				tempBrand = commodityBaseApiService.getBrandByNo(shopRule.getBrandNo());
            				cateStr.add("("+tempBrand.getBrandName()+"->"+tempCate.getCatName()+")");
            			}
            		}
            		Collections.sort(cateStr);
            		str2 = StringUtils.join(cateStr, "，");
            		MerchantOperationLog operationLog = new MerchantOperationLog();
            		operationLog.setMerchantCode(shopVo.getShopId());
            		operationLog.setOperator(loginUser);
            		operationLog.setOperated(new Date());
            		operationLog.setOperationType(OperationType.SHOP);
            		operationLog.setOperationNotes(merchantOperationLogService.buildMerchantShopRuleOperationNotes(str1,str2));
            		merchantOperationLogService.saveMerchantOperationLog(operationLog);
            	} else {
                    resultMsg.setSuccess(false);
                    resultMsg.setMsg(result.getMessage());
                    resultMsg.setReCode(code);
                    logger.error(result.getMessage());
            	}
            } else {
                resultMsg.setSuccess(false);
                logger.error("保存店铺规则时返回结果==null");
            }
        } catch (Exception e) {
            resultMsg.setSuccess(false);
            e.printStackTrace();
        logger.error(e.getMessage());
        }
        return JSONObject.fromObject(resultMsg).toString();
    }
    
    /**
     * 保存店铺信息
     * @param model
     * @param vo
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/saveFssShopInfo")
    public String modifyFssShopInfo(ModelMap model, ShopVO vo, HttpServletRequest request,@RequestParam String shopId) throws Exception {
    	String operator = GetSessionUtil.getSystemUser(request).getUsername();
        ResultMsg resultMsg = new ResultMsg();
        String structs = vo.getBrandNames();
        ShopVO shopVo = null;
		if(shopId != null && (shopId = shopId.trim()).length() > 0) {
			shopVo = iFSSYmcApiService.selectShopById(shopId);
		}
        if (StringUtils.isNotBlank(structs)) {
            structs = StringUtils.substring(structs, 0);
        }
        vo.setBrandNames(null);
        try {
            vo.setAuditStatus(-1);
            vo.setLastUpdateUserId(operator);
            vo.setCreateDateTime(new Date());
            FSSResult result = iFSSYmcApiService.updateShopBaseInfo(vo);
            if(result != null) {
            	String code = result.getCode();
            	if("0".equals(code)){
                    resultMsg.setReCode(code);
                    resultMsg.setSuccess(true);
                    /** 添加店铺日志 **/
                    SystemmgtUser user = GetSessionUtil.getSystemUser(request);
                    String loginUser = "";
            		if (user != null) {
            			loginUser = user.getUsername();
            		}
            		MerchantOperationLog operationLog = new MerchantOperationLog(); 
            		operationLog.setMerchantCode(shopVo.getShopId());
            		operationLog.setOperator(loginUser);
            		operationLog.setOperated(new Date());
            		operationLog.setOperationType(OperationType.SHOP);
            		operationLog.setOperationNotes(merchantOperationLogService.buildMerchantShopOperationNotes(shopVo, vo));
            		merchantOperationLogService.saveMerchantOperationLog(operationLog);
            	} else {
                    resultMsg.setSuccess(false);
                    resultMsg.setMsg(result.getMessage());
                    resultMsg.setReCode(code);
                    logger.error(result.getMessage());
            	}
            } else {
                resultMsg.setSuccess(false);
                logger.error("保存店铺时返回结果==null");
            }
        } catch (Exception e) {
            resultMsg.setSuccess(false);
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return JSONObject.fromObject(resultMsg).toString();
    }
    
    /**
     * saveFssShopAndRule:保存店铺信息 
     * @author li.n1 
     * @param model
     * @param vo
     * @param request
     * @return
     * @throws Exception 
     * @since JDK 1.6
     */
    @ResponseBody
    @RequestMapping("/saveFssShopAndRule")
    public String saveFssShopAndRule(ModelMap model, ShopVO vo, HttpServletRequest request) throws Exception {
    	SystemmgtUser user = GetSessionUtil.getSystemUser(request);
    	String operator = user.getUsername();
        ResultMsg resultMsg = new ResultMsg();
        String structs = vo.getBrandNames();
        if (StringUtils.isNotBlank(structs)) {
            structs = StringUtils.substring(structs, 0);
        }
        String[] strutsArr = null;
        for (String struct : structs.split(",")) {
            ShopRuleVO shopRuleVO = new ShopRuleVO();
            strutsArr = struct.split(";");
            shopRuleVO.setBrandNo(strutsArr[0]);
            if(strutsArr.length>1){
            	shopRuleVO.setStructName(strutsArr[1]);
            }
            vo.getShopRuleList().add(shopRuleVO);
        }
        vo.setBrandNames(null);
        try {
            vo.setAuditStatus(-1);
            vo.setLastUpdateUserId(operator);
            vo.setCreateDateTime(new Date());
            FSSResult result = iFSSYmcApiService.addNewShop(vo);
            if(result != null) {
            	String code = result.getCode();
            	if("0".equals(code)){
                    resultMsg.setReCode(code);
                    resultMsg.setSuccess(true);
                    ShopVO tempVo = (ShopVO)result.getData();
                    /** 添加店铺日志 **/
                    String loginUser = "";
            		if (user != null) {
            			loginUser = user.getUsername();
            		}
            		MerchantOperationLog operationLog = new MerchantOperationLog();
            		operationLog.setMerchantCode(tempVo.getShopId());
            		operationLog.setOperator(loginUser);
            		operationLog.setOperated(new Date());
            		operationLog.setOperationType(OperationType.SHOP);
            		operationLog.setOperationNotes(merchantOperationLogService.buildMerchantShopOperationNotes(null, vo));
            		merchantOperationLogService.saveMerchantOperationLog(operationLog);
            	} else {
                    resultMsg.setSuccess(false);
                    resultMsg.setMsg(result.getMessage());
                    resultMsg.setReCode(code);
                    logger.error(result.getMessage());
            	}
            } else {
                resultMsg.setSuccess(false);
                logger.error("删除店铺时返回结果==null");
            }
        } catch (Exception e) {
            resultMsg.setSuccess(false);
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return JSONObject.fromObject(resultMsg).toString();
    }

    /**
     * 删除店铺信息
     * @param model
     * @param vo
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/delete_shop")
    public String deleteShop(ModelMap model, ShopVO vo, HttpServletRequest request,@RequestParam String shopId) throws Exception {
        String operator = GetSessionUtil.getSystemUser(request).getUsername();
        ResultMsg resultMsg = new ResultMsg();
        ShopVO shopVo = null;
		if(shopId != null && (shopId = shopId.trim()).length() > 0) {
			shopVo = iFSSYmcApiService.selectShopById(shopId);
		}
        try {
            vo.setLastUpdateUserId(operator);
            vo.setAuditDatetime(new Date());
            FSSResult result = iFSSYmcApiService.deleteShop(vo.getShopId(), null);
            if(result != null) {
            	String code = result.getCode();
            	if("0".equals(code)){
            		SystemmgtUser user = GetSessionUtil.getSystemUser(request);
                    resultMsg.setReCode(code);
                    resultMsg.setSuccess(true);
                    /** 添加店铺日志 **/
                    String loginUser = "";
            		if (user != null) {
            			loginUser = user.getUsername();
            		}
            		MerchantOperationLog operationLog = new MerchantOperationLog();
            		operationLog.setMerchantCode(shopVo.getShopId());
            		operationLog.setOperator(loginUser);
            		operationLog.setOperated(new Date());
            		operationLog.setOperationType(OperationType.SHOP);
            		operationLog.setOperationNotes(merchantOperationLogService.buildMerchantShopOperationNotes(shopVo, null));
            		merchantOperationLogService.saveMerchantOperationLog(operationLog);
            	} else {
                    resultMsg.setSuccess(false);
                    resultMsg.setMsg(result.getMessage());
                    resultMsg.setReCode(code);
                    logger.error(result.getMessage());
            	}
            } else {
                resultMsg.setSuccess(false);
                logger.error("删除店铺时返回结果==null");
            }
        } catch (Exception e) {
            resultMsg.setSuccess(false);
            logger.error(e.getMessage());
        }
        return JSONObject.fromObject(resultMsg).toString();
    }
    
    /**
     * 设置装修后是否需要审核店铺
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/goUpdateAuditShopFlag")
    public String goUpdateAuditShopFlag(ModelMap model,ShopVO vo, HttpServletRequest request) throws Exception {
    	SystemmgtUser user = GetSessionUtil.getSystemUser(request);
        ResultMsg resultMsg = new ResultMsg();
        try {
        	ShopVO tempVo = iFSSYmcApiService.selectShopById(vo.getShopId());
        	ShopVO shopVo = iFSSYmcApiService.selectShopById(vo.getShopId());
        	if(shopVo != null) {
        		if(vo.getAuditFlag() != null && vo.getAuditFlag().length() > 0){
        			shopVo.setLastUpdateUserId(user.getUsername());
        			shopVo.setAuditFlag(vo.getAuditFlag());
                    FSSResult result = iFSSYmcApiService.updateAuditShopFlag(shopVo);
                    if(result != null) {
                    	String code = result.getCode();
                    	if("0".equals(code)){
                    		vo = iFSSYmcApiService.selectShopById(vo.getShopId());
                            resultMsg.setReCode(code);
                            resultMsg.setSuccess(true);
                            MerchantOperationLog operationLog = new MerchantOperationLog();
                    		operationLog.setMerchantCode(vo.getShopId());
                    		operationLog.setOperator(user.getUsername());
                    		operationLog.setOperated(new Date());
                    		operationLog.setOperationType(OperationType.SHOP);
                    		operationLog.setOperationNotes(merchantOperationLogService.buildMerchantShopOperationNotes(tempVo, vo));
                    		merchantOperationLogService.saveMerchantOperationLog(operationLog);
                    	} else {
                            resultMsg.setSuccess(false);
                            resultMsg.setMsg(result.getMessage());
                            resultMsg.setReCode(code);
                            logger.error(result.getMessage());
                    	}
                    } else {
                        resultMsg.setMsg("是否需要审核标记为空，请联系技术支持人员?");
                        logger.error("是否需要审核标记为空");
                    }
        		} else {
                    resultMsg.setMsg("审核标记为空，请联系技术支持人员?");
                    logger.error("审核标记为空");
        		}
        	} else {
                resultMsg.setSuccess(false);
                resultMsg.setMsg("找不到对应的店铺,是否已经删除?");
                logger.error("找不到对应的店铺");
        	}
        } catch (Exception e) {
            resultMsg.setSuccess(false);
            logger.error(e.getMessage());
        }
        return JSONObject.fromObject(resultMsg).toString();
    }
    
    /**
     * 店铺审核列表查询
     * 
     * @param modelMap
     * @param vo
     * @param query
     * @return
     */
    @RequestMapping("/shop_audit")
    public String shopAudit(ModelMap model, ShopVO vo, com.yougou.ordercenter.common.Query query, HttpServletRequest request) {
        String auditDatetimeBegin = "";
        String auditDatetimeEnd = "";
        if (StringUtils.isNotEmpty(vo.getAuditDatetimeBegin())) {
            auditDatetimeBegin = vo.getAuditDatetimeBegin();
            vo.setAuditDatetimeBegin(auditDatetimeBegin + " 00:00:00");
        }
        if (StringUtils.isNotEmpty(vo.getAuditDatetimeEnd())) {
            auditDatetimeEnd = vo.getAuditDatetimeEnd();
            vo.setAuditDatetimeEnd(auditDatetimeEnd + " 23:59:59");
        }
        FSSResult result = iFSSYmcApiService.getShopList(vo, query.getPage(), query.getPageSize(), null, null);
        if (!"".equals(auditDatetimeBegin)) {
            vo.setAuditDatetimeBegin(auditDatetimeBegin);
        }
        if (!"".equals(auditDatetimeEnd)) {
            vo.setAuditDatetimeEnd(auditDatetimeEnd);
        }
        PageFinder pageFinder = (PageFinder) result.getData();
        if(pageFinder != null) {
            for (ShopVO shopVO : (List<ShopVO>) pageFinder.getData()) {
                if (StringUtils.isNotEmpty(shopVO.getBrandIds())) {
                	String[] brandIds = shopVO.getBrandIds().split(",");
                	String[] recommendBrandIds = shopVO.getRecommendBrandIds() != null ? shopVO.getRecommendBrandIds().split(",") : null;
                	List<String> lstRecommendBrand = recommendBrandIds != null ? Arrays.asList(recommendBrandIds) : null;
                	String brandNames = "";
                	
                	for(String brandId : brandIds) {
                		Brand brand = commodityBaseApiService.getBrandById(brandId);
            			if(brand != null) {
    						if (lstRecommendBrand != null && lstRecommendBrand.contains(brandId)) {
    							brandNames += brandId +"," + brand.getBrandName()+",1;";
    						} else {
    							brandNames += brandId +"," + brand.getBrandName()+",0;";
    						}
            			}
                	}
                	int length = 0;
                	if(brandNames != null && (length = brandNames.length()) > 0) {
                		shopVO.setBrandNames(brandNames.substring(0, length -1));
                	}
                }
            }
        }
        model.addAttribute("vo", vo);
        model.addAttribute("pageFinder", pageFinder);
        return BASE_PATH + "shop_audit_list";
    }

    /**
     * 新建店铺通过按钮，使YMC可以看到这个店铺并进行操作
     * @param model
     * @param vo
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/update_shop_status")
    public String updateShopStatus(ModelMap model, ShopVO vo, HttpServletRequest request) throws Exception {
        SystemmgtUser user = GetSessionUtil.getSystemUser(request);
        ResultMsg resultMsg = new ResultMsg();
        try {
        	ShopVO tempVo = iFSSYmcApiService.selectShopById(vo.getShopId());
            vo.setLastUpdateUserId(user.getUsername());
            vo.setAuditDatetime(new Date());
            FSSResult result = iFSSYmcApiService.releaseStoreToYMC(vo);
            if(result != null) {
            	String code = result.getCode();
            	if("0".equals(code)){
                    resultMsg.setReCode(code);
                    resultMsg.setSuccess(true);
                    /** 添加店铺日志 **/
                    String loginUser = "";
            		if (user != null) {
            			loginUser = user.getUsername();
            		}
            		vo = iFSSYmcApiService.selectShopById(vo.getShopId());
            		MerchantOperationLog operationLog = new MerchantOperationLog();
            		operationLog.setMerchantCode(vo.getShopId());
            		operationLog.setOperator(loginUser);
            		operationLog.setOperated(new Date());
            		operationLog.setOperationType(OperationType.SHOP);
            		operationLog.setOperationNotes(merchantOperationLogService.buildMerchantShopOperationNotes(tempVo, vo));
            		merchantOperationLogService.saveMerchantOperationLog(operationLog);
            	} else {
                    resultMsg.setSuccess(false);
                    resultMsg.setMsg(result.getMessage());
                    resultMsg.setReCode(code);
                    logger.error(result.getMessage());
            	}
            } else {
                resultMsg.setMsg("更新店铺状态为空，请联系技术支持人员?");
                logger.error("更新店铺状态为空");
            }
        } catch (Exception e) {
            resultMsg.setSuccess(false);
            logger.error(e.getMessage());
        }
        return JSONObject.fromObject(resultMsg).toString();
    }

    /**
     * 更新店铺开放或关闭（Access）状态
     * @param model
     * @param vo
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/update_shop_access_status")
    public String updateShopAccessStatus(ModelMap model, ShopVO vo, HttpServletRequest request) throws Exception {
        SystemmgtUser user = GetSessionUtil.getSystemUser(request);
        ResultMsg resultMsg = new ResultMsg();
        try {
            vo.setLastUpdateUserId(user.getUsername());
            vo.setAuditDatetime(new Date());
            ShopVO tempVo = iFSSYmcApiService.selectShopById(vo.getShopId());
            FSSResult result = iFSSYmcApiService.updateShopAccess(vo);
            if(result != null) {
            	String code = result.getCode();
            	if("0".equals(code)){
                    resultMsg.setReCode(code);
                    resultMsg.setSuccess(true);
                    /** 添加店铺日志 **/
                    String loginUser = "";
            		if (user != null) {
            			loginUser = user.getUsername();
            		}
            		vo = iFSSYmcApiService.selectShopById(vo.getShopId());
            		MerchantOperationLog operationLog = new MerchantOperationLog();
            		operationLog.setMerchantCode(vo.getShopId());
            		operationLog.setOperator(loginUser);
            		operationLog.setOperated(new Date());
            		operationLog.setOperationType(OperationType.SHOP);
            		operationLog.setOperationNotes(merchantOperationLogService.buildMerchantShopOperationNotes(tempVo, vo));
            		merchantOperationLogService.saveMerchantOperationLog(operationLog);
            	} else {
                    resultMsg.setSuccess(false);
                    resultMsg.setMsg(result.getMessage());
                    resultMsg.setReCode(code);
                    logger.error(result.getMessage());
            	}
            } else {
                resultMsg.setMsg("更新店铺状态为空，请联系技术支持人员?");
                logger.error("更新店铺状态为空");
            }
        } catch (Exception e) {
            resultMsg.setSuccess(false);
            logger.error(e.getMessage());
        }
        return JSONObject.fromObject(resultMsg).toString();
    }

    
    /**
     * 审核店铺状态
     * @param model
     * @param vo
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/audit_shop")
    public String auditShop(ModelMap model, ShopVO vo, HttpServletRequest request) throws Exception {
        String operator = GetSessionUtil.getSystemUser(request).getUsername();
        ResultMsg resultMsg = new ResultMsg();

        try {
            vo.setLastUpdateUserId(operator);
            vo.setAuditDatetime(new Date());
            FSSResult result = iFSSYmcApiService.auditShop(vo);
            if(result != null) {
            	String code = result.getCode();
            	if("0".equals(code)){
                    resultMsg.setReCode(code);
                    resultMsg.setSuccess(true);
            	} else {
                    resultMsg.setSuccess(false);
                    resultMsg.setMsg(result.getMessage());
                    resultMsg.setReCode(code);
                    logger.error(result.getMessage());
            	}
            } else {
                resultMsg.setSuccess(false);
                resultMsg.setMsg("审核店铺状态为空，请联系技术支持人员?");
                logger.error("审核店铺状态为空");
            }
        } catch (Exception e) {
            resultMsg.setSuccess(false);
            logger.error(e.getMessage());
        }
        return JSONObject.fromObject(resultMsg).toString();
    }

    /**
     * 设置品牌推荐
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/goRecommendTheBrand")
    public String goRecommendTheBrand(ModelMap model, HttpServletRequest request) throws Exception {
        String operator = GetSessionUtil.getSystemUser(request).getUsername();
        String shopId = request.getParameter("shopId");
        String recommendBrandId = request.getParameter("recommendBrandId");
        ResultMsg resultMsg = new ResultMsg();
        try {
            FSSResult result = iFSSYmcApiService.updateRecommendBrand(shopId, recommendBrandId, operator);
            if(result != null) {
            	String code = result.getCode();
            	if("0".equals(code)){
                    resultMsg.setReCode(code);
                    resultMsg.setSuccess(true);
            	} else {
                    resultMsg.setSuccess(false);
                    resultMsg.setMsg(result.getMessage());
                    resultMsg.setReCode(code);
                    logger.error(result.getMessage());
            	}
            } else {
                resultMsg.setSuccess(false);
                logger.error("品牌推荐返回结果==null");
            }
        } catch (Exception e) {
            resultMsg.setSuccess(false);
            logger.error(e.getMessage());
        }
        return JSONObject.fromObject(resultMsg).toString();
    }
    
    /**
     * 取消品牌推荐
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/goCancelRecommendBrand")
    public String goCancelRecommendBrand(ModelMap model, HttpServletRequest request) throws Exception {
        String operator = GetSessionUtil.getSystemUser(request).getUsername();
        String shopId = request.getParameter("shopId");
        String recommendBrandId = request.getParameter("recommendBrandId");
        ResultMsg resultMsg = new ResultMsg();
        try {
            FSSResult result = iFSSYmcApiService.cancelRecommendBrand(shopId, recommendBrandId, operator);
            if(result != null) {
            	String code = result.getCode();
            	if("0".equals(code)){
                    resultMsg.setReCode(code);
                    resultMsg.setSuccess(true);
            	} else {
                    resultMsg.setSuccess(false);
                    resultMsg.setMsg(result.getMessage());
                    resultMsg.setReCode(code);
                    logger.error(result.getMessage());
            	}
            } else {
                resultMsg.setSuccess(false);
                logger.error("取消品牌推荐返回结果==null");
            }
        } catch (Exception e) {
            resultMsg.setSuccess(false);
            logger.error(e.getMessage());
        }
        return JSONObject.fromObject(resultMsg).toString();
    }
    
    /**
     * 覆盖其他店铺品牌推荐
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/goOverideRecommendBrand")
    public String goOverideRecommendBrand(ModelMap model, HttpServletRequest request) throws Exception {
        String operator = GetSessionUtil.getSystemUser(request).getUsername();
        String shopId = request.getParameter("shopId");
        String recommendBrandId = request.getParameter("recommendBrandId");
        ResultMsg resultMsg = new ResultMsg();
        try {
            FSSResult result = iFSSYmcApiService.overideRecommendBrand(shopId, recommendBrandId, operator);
            if(result != null) {
            	String code = result.getCode();
            	if("0".equals(code)){
                    resultMsg.setReCode(code);
                    resultMsg.setSuccess(true);
            	} else {
                    resultMsg.setSuccess(false);
                    resultMsg.setMsg(result.getMessage());
                    resultMsg.setReCode(code);
                    logger.error(result.getMessage());
            	}
            } else {
                resultMsg.setSuccess(false);
                logger.error("覆盖其他店铺品牌推荐返回结果==null");
            }
        } catch (Exception e) {
            resultMsg.setSuccess(false);
            logger.error(e.getMessage());
        }
        return JSONObject.fromObject(resultMsg).toString();
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
				logger.error(MessageFormat.format("commodityBaseApiSercie接口获取品牌[{0}]失败", brandNo), e);
			}
			if (null == brand)
				continue;

			// 获取分类
			List<Category> thirdcats = commodityBaseApiService.getCategoryListByBrandId(brand.getId(), (short) 1, null);
			Set<String> firstAndSecondCat = new HashSet<String>();
			// 逆推第一级和第二级分类
			for (Category category : thirdcats) {
				if (StringUtils.isBlank(category.getStructName()) || category.getStructName().length() != 8)
					continue;

				firstAndSecondCat.add(category.getStructName().substring(0, 2));
				firstAndSecondCat.add(category.getStructName().substring(0, 5));
				category.setParentId(brand.getBrandNo() + ";" + this.subCategory(category.getStructName()));
				category.setStructName(brand.getBrandNo() + ";" + category.getStructName());
				// 0：未选中 1：选中
				category.setIsEnabled((short) 0);
			}
			if (CollectionUtils.isNotEmpty(firstAndSecondCat)) {
				for (String str : firstAndSecondCat) {
					Category _cat = commodityBaseApiService.getCategoryByStructName(str);
					_cat.setParentId(brand.getBrandNo() + ";" + this.subCategory(_cat.getStructName()));
					_cat.setStructName(brand.getBrandNo() + ";" + _cat.getStructName());
					_cat.setIsEnabled((short) 0);
					thirdcats.add(_cat);
				}
			}

			Category _brandWapper = new Category();
			_brandWapper.setCatLeave(-1);
			_brandWapper.setCatName(brand.getBrandName());
			_brandWapper.setCatNo(brand.getBrandNo());
			_brandWapper.setParentId("-1");
			_brandWapper.setStructName(brand.getBrandNo() + ";");
			_brandWapper.setId(brand.getId());
			_brandWapper.setIsEnabled((short) 0);
			thirdcats.add(_brandWapper);

			list.addAll(thirdcats);
		}

		return list;
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
			return "";
		}

		return structName.substring(0, index);
	}
	
	/**
	 * 检查商家开店的店名是否重复
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/checkShopName")
	public String checkShopName(ModelMap model, HttpServletRequest request) throws Exception {
		String shopName = request.getParameter("shopName");
		ResultMsg resultMsg = new ResultMsg();
		ShopVO vo = new ShopVO();
		vo.setShopName(shopName);
		resultMsg.setSuccess(iFSSYmcApiService.isShopNameExist(vo));
		return JSONObject.fromObject(resultMsg).toString();
	}

	/**
	 * 检查商家开店的域名url是否重复
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/checkShopUrl")
	public String checkShopUrl(ModelMap model, HttpServletRequest request) throws Exception {
		String shopUrl = request.getParameter("shopUrl");
		ResultMsg resultMsg = new ResultMsg();
		ShopVO vo = new ShopVO();
		vo.setShopURL(shopUrl);
		resultMsg.setSuccess(iFSSYmcApiService.isShopUrlExist(vo));
		return JSONObject.fromObject(resultMsg).toString();
	}
	
	@RequestMapping("/viewShopOperationLog")
	public ModelAndView viewShopOperationLog(ModelMap model,@RequestParam String merchantCode, HttpServletRequest request,Query query) 
			throws Exception{
		com.belle.infrastructure.orm.basedao.PageFinder<MerchantOperationLog> pageFinder = 
				merchantOperationLogService.queryMerchantOperationLogByOperationType(merchantCode,OperationType.SHOP, query);
		model.addAttribute("pageFinder", pageFinder);
		model.addAttribute("shopId", merchantCode);
		return new ModelAndView(BASE_PATH + "shop_log",model);
	}
	
}
