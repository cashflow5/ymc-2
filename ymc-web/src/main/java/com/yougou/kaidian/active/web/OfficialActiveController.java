package com.yougou.kaidian.active.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yougou.active.api.IOfficialActiveApiService;
import com.yougou.active.vo.ActiveRuleVo;
import com.yougou.active.vo.BrandNoAndFirstCategoryNo;
import com.yougou.active.vo.OfficialActiveSearcher;
import com.yougou.active.vo.OfficialActiveVo;
import com.yougou.active.vo.OfficicalActiveBrandAndCategory;
import com.yougou.imago.client.IYouGouConfiguration;
import com.yougou.kaidian.active.service.IOfficialActiveService;
import com.yougou.kaidian.active.vo.MerchantActiveCommodity;
import com.yougou.kaidian.active.vo.MerchantActiveCommodityQuery;
import com.yougou.kaidian.active.vo.MerchantActiveSignup;
import com.yougou.kaidian.commodity.convert.ExcelToDataModelConverter;
import com.yougou.kaidian.commodity.model.vo.ExcelErrorVo;
import com.yougou.kaidian.commodity.service.ICommodityService;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.commodity.pojo.Brand;
import com.yougou.kaidian.common.commodity.pojo.Cat;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.kaidian.framework.base.BaseController;
import com.yougou.kaidian.framework.constant.Constant;
import com.yougou.kaidian.framework.constant.SystemConfig;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.taobao.enums.ResultCode;
import com.yougou.pc.api.ICommodityMerchantApiService;
import com.yougou.pc.model.category.Category;
import com.yougou.pc.model.commodityinfo.Commodity;
import com.yougou.tools.common.utils.DateUtil;

/**
 * 商家中心 > 营销中心 > 官方活动
 * @author li.j1
 */

@Controller
public class OfficialActiveController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(OfficialActiveController.class);
	
	@Resource
	private IOfficialActiveApiService officialActiveApiService;
	
	@Resource
	private ICommodityService commodityService;
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Resource
	private ICommodityMerchantApiService commodityApi;
	
	@Resource
	private IOfficialActiveService officialActiveService;
	
	@Resource
	private SystemConfig systemConfig;
	
	@Resource
	private IYouGouConfiguration yougouConfiguration;
	/**
	 * 可参与报名活动列表
	 * @param modelMap
	 * @param query
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("active/officialActiveList")
	public String queryOfficialActiveList(ModelMap modelMap, Query query, HttpServletRequest request) throws Exception {
		
		Map<String, Object> params = builderParams(request,false);
		modelMap.put("params", params);
		
		//获取商家编码，品牌，品类
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		List<Brand> lstBrand = commodityService.queryBrandList(merchantCode);
		BrandNoAndFirstCategoryNo brandNoAndFirstCategoryNo = null;
		List<BrandNoAndFirstCategoryNo> BrandNoAndFirstCategoryNoLst = new ArrayList<BrandNoAndFirstCategoryNo>();
		for(Brand brand : lstBrand){
			List<Cat> lstCat = commodityService.querySupplierFirstCatListByBrandNo(merchantCode, brand.getBrandNo());
			for(Cat cat : lstCat){
				brandNoAndFirstCategoryNo = new BrandNoAndFirstCategoryNo();
				brandNoAndFirstCategoryNo.setBrandNo(brand.getBrandNo());
				brandNoAndFirstCategoryNo.setFirstCategoryNo(cat.getNo());
				BrandNoAndFirstCategoryNoLst.add(brandNoAndFirstCategoryNo);
			}
		}
		modelMap.put("activeType", initActiveType());
		OfficialActiveSearcher searcher = new OfficialActiveSearcher();
//		searcher.setStart((query.getPage()-1)*query.getPageSize());
//		searcher.setLength(query.getPageSize());
		//品牌,一级分类
		if(BrandNoAndFirstCategoryNoLst.size()>0){
			searcher.setBrandNoAndFirstCategoryNoList(BrandNoAndFirstCategoryNoLst);
		}
		String activeName = (String)params.get("activeName");
		if(StringUtils.isNotBlank(activeName)){
			searcher.setActiveName(activeName);
		}
		String activeType = (String)params.get("activeType");
		if(StringUtils.isNotBlank(activeType)){
			searcher.setActiveType(Short.valueOf(activeType));
		}
		String signupBeginTime = (String)params.get("signupBeginTime");
		if(StringUtils.isBlank(signupBeginTime)){
			signupBeginTime = "10";
			params.put("signupBeginTime", signupBeginTime);
		}
		searcher.setActiveState((short)10);
		Date endTime = DateUtil.addDate(new Date(System.currentTimeMillis()), Integer.valueOf(signupBeginTime));
		searcher.setSignUpStartTime(new Date(System.currentTimeMillis()));
		searcher.setSignUpEndTime(endTime);
		searcher.setLength(100);
//		int count = officialActiveApiService.getOfficialActiveVoCount(searcher);
		List<OfficialActiveVo> tempOfficialActiveList = new ArrayList<OfficialActiveVo>();
	    List<OfficialActiveVo> officialActiveList =  officialActiveApiService.getOfficialActiveVoList(searcher);
	    for(OfficialActiveVo officialActive : officialActiveList){
	    	MerchantActiveSignup merchantActiveSignup =  officialActiveService.getMerchantActiveSignup(officialActive.getId(),YmcThreadLocalHolder.getMerchantCode()); 
			if(null == merchantActiveSignup){
				tempOfficialActiveList.add(officialActive);
			}
	    }
	    int count = tempOfficialActiveList.size();
	    int start = (query.getPage()-1)*query.getPageSize();
	    int end = start+query.getPageSize();
	    end = end > tempOfficialActiveList.size() ? tempOfficialActiveList.size() : end;
	    tempOfficialActiveList =  tempOfficialActiveList.subList(start, end);
	    PageFinder<OfficialActiveVo> pageFinder = new PageFinder<OfficialActiveVo>(query.getPage(), query.getPageSize(),count,tempOfficialActiveList);
		modelMap.put("pageFinder", pageFinder);
	    return "/manage/active/official_active_list";
	}

	/**
	 * 初始化活动类型
	 * @return
	 */
	private Map<String, String> initActiveType() {
		Map activeTypeMap = officialActiveApiService.getOfficialActiveTypeMap();
		Map<String,String> activeTypes = new HashMap<String,String>(activeTypeMap.size());
		for(Object obj : activeTypeMap.keySet()){
			activeTypes.put(String.valueOf(obj), String.valueOf(activeTypeMap.get(obj)));
		}
		return activeTypes;
	}
	
	/**
	 * 我的报名活动列表
	 * @param modelMap
	 * @param query
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("active/myOfficialActiveList")
	public String queryMyOfficialActiveList(ModelMap modelMap, Query query,MerchantActiveSignup signup, HttpServletRequest request) throws Exception {
		Map<String, Object> params = builderParams(request,false);
		modelMap.put("params", params);
		modelMap.put("activeType", initActiveType());
		signup.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
		int count = officialActiveService.selectMerchantActiveSignupCount(signup);
	    List<MerchantActiveSignup> officialActiveSignupList =  officialActiveService.selectMerchantActiveSignupList(signup,query);
		
	    PageFinder<MerchantActiveSignup> pageFinder = new PageFinder<MerchantActiveSignup>(query.getPage(), query.getPageSize(),count,officialActiveSignupList);
		modelMap.put("pageFinder", pageFinder);
		return "/manage/active/my_official_active_list";
	}
	
	/**
	 * 活动介绍
	 * @param modelMap
	 * @param query
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("active/to_signupOfficialActive")
	public String toSignupOfficialActive(ModelMap modelMap, String id, HttpServletRequest request) throws Exception {
		OfficialActiveVo officialActiveVo = officialActiveApiService.getOfficialActiveVoById(id);
		modelMap.put("officialActive", officialActiveVo);
		List<OfficicalActiveBrandAndCategory> officicalActiveBrandAndCategoryList = officialActiveVo.getBrandAndCategories();
		Set<String> brands = new HashSet<String>();
		Set<String> cats = new HashSet<String>();
		for(OfficicalActiveBrandAndCategory officicalActiveBrandAndCategory : officicalActiveBrandAndCategoryList){
			brands.add(officicalActiveBrandAndCategory.getBrandName());
			cats.add(officicalActiveBrandAndCategory.getFirstCategoryName());
		}
		String brandNames = StringUtils.join(brands,",");
		modelMap.put("brandNames", StringUtils.isBlank(brandNames) ? "不限" : brandNames);
		String catNames = StringUtils.join(cats,",");
		modelMap.put("catNames", StringUtils.isBlank(catNames) ? "不限" : catNames);
		modelMap.put("activeType", initActiveType());
		//查询是否已经报名
		MerchantActiveSignup merchantActiveSignup =  officialActiveService.getMerchantActiveSignup(officialActiveVo.getId(),YmcThreadLocalHolder.getMerchantCode()); 
		if(null != merchantActiveSignup){
			modelMap.put("merchantActiveSignup", merchantActiveSignup);
		}
		
		return "/manage/active/signup_official_active";
	}
	
	/**
	 * 活动报名
	 * @param modelMap
	 * @param query
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("active/signupOfficialActive")
	public String signupOfficialActive(ModelMap modelMap, String id, HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		OfficialActiveVo officialActiveVo = officialActiveApiService.getOfficialActiveVoById(id);
		//校验是否重复提交
		MerchantActiveSignup merchantActiveSignup =  officialActiveService.getMerchantActiveSignup(id,YmcThreadLocalHolder.getMerchantCode()); 
		if(null != merchantActiveSignup){
			result.put("code", "300");
			return result.toString();
		}
		
		MerchantActiveSignup activeSignup = new MerchantActiveSignup();
		activeSignup.setActiveId(officialActiveVo.getId());
		activeSignup.setActiveName(officialActiveVo.getActiveName());
		activeSignup.setActiveType(officialActiveVo.getActiveType());
		activeSignup.setCreateTime(new Date());
		activeSignup.setCreator(YmcThreadLocalHolder.getOperater());
		activeSignup.setId(UUIDGenerator.getUUID());
		activeSignup.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
		activeSignup.setMerchantName(YmcThreadLocalHolder.getMerchantName());
		activeSignup.setStatus(Constant.OFFICIAL_ACTIVE_STATUS_NEW);
		activeSignup.setUpdateTime(new Date());
		
		int count = officialActiveService.signupOfficialActive(activeSignup);
		if(count > 0){
			result.put("code", ResultCode.SUCCESS.getCode());
		}else{
			result.put("code", ResultCode.ERROR.getCode());
		}
		return result.toString();
	}
	
	/**
	 * 活动报名
	 * @param modelMap
	 * @param query
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("active/mySignupOfficialActive")
	public String mySignupOfficialActive(ModelMap modelMap, String id, HttpServletRequest request) throws Exception {
		OfficialActiveVo officialActiveVo = officialActiveApiService.getOfficialActiveVoById(id);
		modelMap.put("officialActive", officialActiveVo);
		List<OfficicalActiveBrandAndCategory> officicalActiveBrandAndCategoryList = officialActiveVo.getBrandAndCategories();
		Set<String> brands = new HashSet<String>();
		Set<String> cats = new HashSet<String>();
		for(OfficicalActiveBrandAndCategory officicalActiveBrandAndCategory : officicalActiveBrandAndCategoryList){
			brands.add(officicalActiveBrandAndCategory.getBrandName());
			cats.add(officicalActiveBrandAndCategory.getFirstCategoryName());
		}
		String brandNames = StringUtils.join(brands,",");
		modelMap.put("brandNames", StringUtils.isBlank(brandNames) ? "不限" : brandNames);
		String catNames = StringUtils.join(cats,",");
		modelMap.put("catNames", StringUtils.isBlank(catNames) ? "不限" : catNames);
		modelMap.put("activeType", initActiveType());
		
		//查询是否已经报名
		MerchantActiveSignup merchantActiveSignup =  officialActiveService.getMerchantActiveSignup(officialActiveVo.getId(),YmcThreadLocalHolder.getMerchantCode()); 
		if(null != merchantActiveSignup){
			modelMap.put("merchantActiveSignup", merchantActiveSignup);
		}
		String uniqueCoupon = yougouConfiguration.getString(Constant.YMC_COUPON_UNIQUE_VALUE, "0");
		int couponHighest = yougouConfiguration.getInt(Constant.YMC_COUPON_HIGHEST_VALUE, 200);
		int couponDefault = yougouConfiguration.getInt(Constant.YMC_COUPON_DEFAULT_VALUE, 30);
		modelMap.put("uniqueCoupon", uniqueCoupon);
		modelMap.put("couponDefault", couponDefault);
		modelMap.put("couponHighest", couponHighest);
		return "/manage/active/my_signup_official_active";
	}
	
	
	/**
	 * 活动报名
	 * @param modelMap
	 * @param query
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("active/getOfficialActiveCommodityList")
	public String getActiveCommodityList(ModelMap modelMap, MerchantActiveCommodity activeCommodity, Query query, HttpServletRequest request) throws Exception {
		activeCommodity.setComodityNo(StringUtils.trim(activeCommodity.getComodityNo()));
		activeCommodity.setComodityName(StringUtils.trim(activeCommodity.getComodityName()));
		List<MerchantActiveCommodity> merchantActiveCommodityList = officialActiveService.selectMerchantCommodityList(activeCommodity,query);
		//加入单品页链接
		if(merchantActiveCommodityList!=null &&  merchantActiveCommodityList.size()>0   ){
			for (MerchantActiveCommodity merchantActiveCommodity : merchantActiveCommodityList) { 
				merchantActiveCommodity.setProdUrl("/commodity/"+merchantActiveCommodity.getComodityNo()+"/generate.sc");
			}
		
			
		}
		
		int count = officialActiveService.selectMerchantCommodityCount(activeCommodity);
		PageFinder<MerchantActiveCommodity> pageFinder = new PageFinder<MerchantActiveCommodity>(query.getPage(), query.getPageSize(),count,merchantActiveCommodityList);
		//查询总数，不要其他过滤条件
		MerchantActiveCommodity activeCommodityForAll = new MerchantActiveCommodity();
		activeCommodityForAll.setSignupId(activeCommodity.getSignupId());
		int totalCount = officialActiveService.selectMerchantCommodityCount(activeCommodityForAll);
		modelMap.put("pageFinder", pageFinder);
		modelMap.put("tatalCommodity", totalCount);
		modelMap.put("activeCommodity", activeCommodity);
		String uniqueCoupon = yougouConfiguration.getString(Constant.YMC_COUPON_UNIQUE_VALUE, "0");
		int couponHighest = yougouConfiguration.getInt(Constant.YMC_COUPON_HIGHEST_VALUE, 200);
		int couponDefault = yougouConfiguration.getInt(Constant.YMC_COUPON_DEFAULT_VALUE, 30);
		modelMap.put("uniqueCoupon", uniqueCoupon);
		modelMap.put("couponDefault", couponDefault);
		modelMap.put("couponHighest", couponHighest);
		return "manage/active/official_active_commodity_list";
	}
	
	/**
	 * 删除报名商品
	 * @param commodityIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "active/deleteOfficialActiveCommodity")
	public String deleteOfficialActiveCommodity(String commodityIds){
		JSONObject jsonObject = new JSONObject();
		int count = officialActiveService.deleteMerchantActiveCommodity(commodityIds);
		if(count > 0){
			jsonObject.put("msg", "Success");
		}else{
			jsonObject.put("msg", "Failure");
		}
		return jsonObject.toString(); 
	}
	
	/**
	 * 提交、取消报名
	 * @param signupId
	 * @param type 1提交 2取消
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "active/updateOfficialActiveSignup")
	public String updateOfficialActiveSignup(String signupId,String type){
		JSONObject jsonObject = new JSONObject();
		MerchantActiveSignup merchantActiveSignup =  officialActiveService.getMerchantActiveSignupById(signupId);
		OfficialActiveVo officialActiveVo = officialActiveApiService.getOfficialActiveVoById(merchantActiveSignup.getActiveId());
		if(officialActiveVo.getActiveState() != (short)6){
			jsonObject.put("msg", "当前官方活动不是报名中或审核中状态，不能操作提交审核或撤销报名了");
			return jsonObject.toString(); 
		}
		//如果是统一的最高支持优惠券金额，校验所有商品的金额是否一致
		String uniqueCoupon = yougouConfiguration.getString(Constant.YMC_COUPON_UNIQUE_VALUE, "0");
		if(Constant.OFFICIAL_ACTIVE_COUPON_UNIQUE.equals(uniqueCoupon)){
			Query query = new Query();
			query.setPage(1);
			query.setPageSize(Integer.MAX_VALUE);
			MerchantActiveCommodity commodity = new MerchantActiveCommodity();
			commodity.setSignupId(signupId);
			List<MerchantActiveCommodity> commodityList = officialActiveService.selectMerchantCommodityList(commodity, query);
			if(CollectionUtils.isNotEmpty(commodityList)){
				int couponAmount = commodityList.get(0).getCouponAmount();
				for(MerchantActiveCommodity merchantActiveCommodity : commodityList){
					if(couponAmount != merchantActiveCommodity.getCouponAmount()){
						jsonObject.put("msg", "所有商品的最高承担卡券金额须一致");
						return jsonObject.toString(); 
					}
				}
			}
		}
		
		MerchantActiveSignup updateSignup = new MerchantActiveSignup();
		updateSignup.setId(signupId);
		short status = merchantActiveSignup.getStatus();
		if("1".equals(type)){
			if(status != Constant.OFFICIAL_ACTIVE_STATUS_NEW && status != Constant.OFFICIAL_ACTIVE_STATUS_REFUSED){
				jsonObject.put("msg", "只有新建或审批不通过状态才可以提交报名");
				return jsonObject.toString(); 
			}else{
				updateSignup.setStatus(Constant.OFFICIAL_ACTIVE_STATUS_AUDITING);
				//重新提交，删除审批备注
				updateSignup.setAuditRemark("");
			}
		}
		if("2".equals(type)){
			if(status != Constant.OFFICIAL_ACTIVE_STATUS_AUDITING){
				jsonObject.put("msg", "只有审批中状态才可以撤消报名");
				return jsonObject.toString();
			}else{
				updateSignup.setStatus(Constant.OFFICIAL_ACTIVE_STATUS_NEW);
			}
		}
		updateSignup.setUpdateTime(new Date());
		int count = officialActiveService.updateOfficialActiveSignup(updateSignup);
		if(count > 0){
			jsonObject.put("msg", "Success");
		}else{
			jsonObject.put("msg", "Failure");
		}
		return jsonObject.toString(); 
		
	}
	
	/**
	 * 更新活动商品
	 * @param commodityId
	 * @param price
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "active/updateOfficialActiveCommodity")
	public String updateOfficialActiveCommodity(String commodityId,int price,String type){
		JSONObject jsonObject = new JSONObject();
		MerchantActiveCommodity activeCommodity = officialActiveService.getActiveCommodityById(commodityId);
		if("1".equals(type)){
			activeCommodity.setActivePrice(price);
		}else{
			activeCommodity.setCouponAmount(price);
		}
		int count = officialActiveService.updateActiveCommodity(activeCommodity);
		if(count > 0){
			jsonObject.put("msg", "Success");
		}else{
			jsonObject.put("msg", "Failure");
		}
		return jsonObject.toString();
	}
	
	/**
	 * 导入商品
	 * @param request
	 * @param signupId
	 * @param activeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "active/importActiveCommodity", method = RequestMethod.POST)
	public String importActiveCommodity(MultipartHttpServletRequest request, String signupId, String activeId, int highestCoupon){
		JSONObject jsonObject = new JSONObject();
		OfficialActiveVo officialActiveVo = officialActiveApiService.getOfficialActiveVoById(activeId);
//		MerchantActiveSignup merchantActiveSignup = officialActiveService.getMerchantActiveSignupById(signupId);
		MultipartFile mutipartfile = request.getFile("file");
		if(!(mutipartfile.isEmpty())&&(mutipartfile.getSize()>=0)){
			try {
				return readXls(mutipartfile.getInputStream(),officialActiveVo,signupId,highestCoupon);
			} catch (Exception e) {
				jsonObject.put("resultCode", ResultCode.ERROR.getCode());
				jsonObject.put("msg","文件信息有误，请保持模板格式！");
				logger.error("官方活动报名导入文件出现异常", e);
			}
		}else{
			jsonObject.put("resultCode", ResultCode.ERROR.getCode());
			jsonObject.put("msg","导入excel异常，文件为空！");
		}
		return jsonObject.toString();
	}
	
	/** 
	 * readXls:解析excel
	 * @author li.n1 
	 * @param inputStream 
	 * @since JDK 1.6 
	 */  
	private String readXls(InputStream inputStream,OfficialActiveVo officialActiveVo, String signupId, int highestCoupon) throws Exception{
		int errorCount = 0;
		JSONObject jsonObject = new JSONObject();
		List<ExcelErrorVo> errorList = new ArrayList<ExcelErrorVo>();
		
		//内存中解析excel文件		
		Commodity commodity = null;
		List<Integer> successIndexList = new ArrayList<Integer>();
		
		//解析xls
		XSSFWorkbook work = new XSSFWorkbook(inputStream);
		IOUtils.closeQuietly(inputStream);
		XSSFSheet sheet = work.getSheetAt(0);
		int rowNum = sheet.getLastRowNum();
		XSSFCellStyle errorStyle = work.createCellStyle();
		errorStyle.setFillForegroundColor(HSSFColor.CORAL.index);
		errorStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		XSSFCellStyle defaultCellStyle = work.createCellStyle();
		
		//是否需要活动价
		boolean isNeedActivePrice = false;
		if(officialActiveVo.getActiveType() == (short)2){
			isNeedActivePrice = true;
		}
		//是否支持优惠券
		boolean isSupportCoupon = false;
		if(officialActiveVo.getIsSupportCoupons() == (short)1){
			isSupportCoupon = true;
		}
		
		XSSFRow row = null;
		if(!(checkExcelFile(sheet,errorList,defaultCellStyle,isNeedActivePrice,isSupportCoupon))){	//文件有严重错误
			//列名是否修改没有作判断，只判断是否添加列或删除列
			//标示异常
			//创建错误批注
			createErrorCellComment(sheet, errorStyle, errorList);
			// 设置默认选中第一条错误数据行
			ExcelToDataModelConverter.setErrorCellAsActive(sheet);
			//将错误文件缓存到redis
			storeErrorFileInRedis(jsonObject, work);
			jsonObject.put("resultCode", ResultCode.ERROR.getCode());
			jsonObject.put("msg", "导入文件格式填写错误，请修改后重新上传");
			return jsonObject.toString();
		}else{	//文件无严重错误
			int rowIndex = 0;
			List<ExcelErrorVo> errorList2 = null;
			
			//最高的均价-组合优惠
			float highestAverageAmount = 0;
			if(officialActiveVo.getActiveType() == (short)11){
				//组合优惠 商品单价不能低于最大均价
				 List<ActiveRuleVo> activeRules = officialActiveVo.getActiveRuleList();
				 for(ActiveRuleVo activeRule : activeRules){
					 float minRuleAmount = activeRule.getMinRuleAmount();
					 Float decreaseAmount = activeRule.getDecreaseAmount();
					 if(decreaseAmount != null && minRuleAmount != 0){
						 highestAverageAmount = highestAverageAmount > (decreaseAmount /minRuleAmount) 
								 ? highestAverageAmount : (decreaseAmount /minRuleAmount);
					 }
				 }
			}
			//品牌和品类
			Set<String> brandNos = new HashSet<String>();
			Set<String> catNos = new HashSet<String>();
			for(OfficicalActiveBrandAndCategory officicalActiveBrandAndCategory : officialActiveVo.getBrandAndCategories()){
				brandNos.add(officicalActiveBrandAndCategory.getBrandNo());
				catNos.add(officicalActiveBrandAndCategory.getFirstCategoryNo());
			}
			
			for(int i=1;i<=rowNum;i++){
				row = sheet.getRow(i);
				if(row == null){
					continue;
				}
				errorList2 = new ArrayList<ExcelErrorVo>();
				if(!checkCommodityRow(isNeedActivePrice,isSupportCoupon, row, errorList2)){
					//创建错误批注
					createErrorCellComment(sheet, errorStyle, errorList2);
					errorCount++;
					continue;
				}else{
					String commodityNo = ExcelToDataModelConverter.getCellValue(row,0);
					String activePrice = "";
					String couponAmount = "";
					
					String uniqueCoupon = yougouConfiguration.getString(Constant.YMC_COUPON_UNIQUE_VALUE, "0");
					if(Constant.OFFICIAL_ACTIVE_COUPON_UNIQUE.equals(uniqueCoupon)){
						isSupportCoupon = false;
					}
					if(isNeedActivePrice && isSupportCoupon){
						activePrice = ExcelToDataModelConverter.getCellValue(row,1);
						couponAmount = ExcelToDataModelConverter.getCellValue(row,2);
					}else if(isNeedActivePrice){
						activePrice = ExcelToDataModelConverter.getCellValue(row,1);
					}else if(isSupportCoupon){
						couponAmount = ExcelToDataModelConverter.getCellValue(row,1);
					}
					
					commodity = commodityApi.getCommodityByNo(commodityNo);
					if(commodity != null){
						rowIndex = i;
					}else{
						//创建错误批注
						List<ExcelErrorVo> errors = new ArrayList<ExcelErrorVo>();
						errors.add(new ExcelErrorVo("商品编码","未找到商品数据，请确认商品编号是否正确",row.getRowNum(),0));
						createErrorCellComment(sheet, errorStyle, errors);
						errorCount++;
						continue;
					}
					//校验商品
					String result = validateCommodiyForImport(commodity,officialActiveVo,
							highestAverageAmount,activePrice, brandNos, catNos);
					if(StringUtils.isBlank(result)){
						MerchantActiveCommodity activeCommodity = null;
						//检查是否已经导入，已导入则修改
						MerchantActiveCommodityQuery query = new MerchantActiveCommodityQuery();
						MerchantActiveCommodityQuery.Criteria criteria = query.createCriteria();
						criteria.andSignupIdEqualTo(signupId);
						criteria.andComodityNoEqualTo(commodityNo);
						List<MerchantActiveCommodity> commodityList = officialActiveService.getMerchantCommodityList(query);
						if(CollectionUtils.isNotEmpty(commodityList)){
							activeCommodity = commodityList.get(0);
							activeCommodity.setCreator(YmcThreadLocalHolder.getOperater());
							activeCommodity.setCreateTime(new Date());
						}else{
							activeCommodity = new MerchantActiveCommodity();
							activeCommodity.setId(UUIDGenerator.getUUID());
							activeCommodity.setSignupId(signupId);
							activeCommodity.setCreator(YmcThreadLocalHolder.getOperater());
							activeCommodity.setCreateTime(new Date());
							activeCommodity.setComodityNo(commodityNo);
						}				
						
						if(isNeedActivePrice){
							activeCommodity.setActivePrice(Integer.valueOf(activePrice));
						}
						int couponDefault = yougouConfiguration.getInt(Constant.YMC_COUPON_DEFAULT_VALUE, 30);
						if(Constant.OFFICIAL_ACTIVE_COUPON_UNIQUE.equals(uniqueCoupon)){
							activeCommodity.setCouponAmount(highestCoupon);
						}else if(isSupportCoupon){
							activeCommodity.setCouponAmount(StringUtils.isBlank(couponAmount)? couponDefault:Integer.valueOf(couponAmount));
						}
						int count = 0;
						if(CollectionUtils.isNotEmpty(commodityList)){
							count = officialActiveService.updateActiveCommodity(activeCommodity);
						}else{
							count = officialActiveService.saveActiveCommodity(activeCommodity);
						}
						if(count <= 0){
							result = "更新数据库发生错误";
						}
					}
					//插入数据库发生错误，记录
					if(!result.equalsIgnoreCase("")){
						//创建错误批注
						List<ExcelErrorVo> errors = new ArrayList<ExcelErrorVo>();
						errors.add(new ExcelErrorVo("商品编码",result,row.getRowNum(),0));
						createErrorCellComment(sheet, errorStyle, errors);
						errorCount++;
					}else{
						//插入成功，记录rowIndex，方便删除成功的行
						successIndexList.add(rowIndex);
					}
					
				}
			}
			//插入数据库发生错误，记录并生成excel
			if(errorCount>0){
				//删除导入成功的行
//				for(int i=0,k=0;i<successIndexList.size();i++,k++){
//					ExcelToDataModelConverter.removeRow(sheet,successIndexList.get(i)-k, defaultCellStyle, errorStyle);
//				}
				//将错误文件缓存到redis
				storeErrorFileInRedis(jsonObject, work);
			}
		}
		
		
		//记录日志
		jsonObject.put("resultCode", ResultCode.SUCCESS.getCode());
		jsonObject.put("errorCount", errorCount);
		jsonObject.put("allCount", rowNum);
		return jsonObject.toString();
	}
	
	/**
	 * 校验导入的商品是否符合要求
	 * @param commodity
	 * @param officialActiveVo
	 * @param highestAverageAmount
	 * @param activePrice
	 * @return
	 */
	private String validateCommodiyForImport(Commodity commodity, OfficialActiveVo officialActiveVo,
			float highestAverageAmount, String activePrice, Set<String> brandNos, Set<String> catNos){
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		boolean isNeedActivePrice = false;
		if(officialActiveVo.getActiveType() == (short)2){
			isNeedActivePrice = true;
		}
		
		//保存活动商品信息  需校验
		//商家、品牌、分类、是否在售、活动价
		String result = "";
		if(!merchantCode.equals(commodity.getMerchantCode())){
			result = "只能导入商家自己的商品";
			return result;
		}
		if(!brandNos.contains(commodity.getBrandNo())){
			result = "该商品的品牌不符合活动要求";
			return result;
		}
		
		List<Category> categoryList = commodityApi.getCategoryTreeByStruct(StringUtils.substring(commodity.getCatStructName(), 0, 2));
		if(CollectionUtils.isNotEmpty(categoryList)){
			if(!catNos.contains(categoryList.get(0).getCatNo())){
				result = "该商品的品类不符合活动要求";
				return result;
			}
		}else{
			result = "该商品的品类不符合活动要求";
			return result;
		}
		
		if(commodity.getStatus() != 2){
			result = "该商品不是在售商品";
			return result;
		}
		if(officialActiveVo.getActiveType() == (short)11){
			//组合优惠 商品单价不能低于最大均价
			if(highestAverageAmount > commodity.getSellPrice().floatValue()){
				result = "商品优购价不应该低于组合优惠最高价";
				return result;
			}
		}
		if(isNeedActivePrice && commodity.getSellPrice().intValue() < Integer.valueOf(activePrice)){
			result = "活动价不能大于优购价";
			return result;
		}
		return result;
	}
	
 
	/**
	 * 创建错误批注
	 * @param sheet
	 * @param errorStyle
	 * @param errorVo
	 */
	private void createErrorCellComment(XSSFSheet sheet,
			XSSFCellStyle errorStyle, List<ExcelErrorVo> errorList) {
		XSSFDrawing drawing = sheet.createDrawingPatriarch();
		for (ExcelErrorVo errorVo : errorList) {
			Cell _cell = ExcelToDataModelConverter.getCell(sheet, errorVo.getRow(), errorVo.getColumn());
			_cell.setCellStyle(errorStyle);
			_cell.removeCellComment();
			_cell.setCellComment(ExcelToDataModelConverter.createComment(drawing, 3, 3, 6, 6, errorVo.getErrMsg()));
		}
	}

	/**
	 * 将错误文件缓存到redis
	 * @param jsonObject
	 * @param work
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void storeErrorFileInRedis(JSONObject jsonObject, XSSFWorkbook work)
			throws FileNotFoundException, IOException {
		// 将错误数据行写入Excel文件
		String uuid = UUID.randomUUID().toString();
		OutputStream os = null;
		try {
			os = new FileOutputStream(getErrorXlsPathname(uuid,"xls"));
			work.write(os);
		} finally {
			IOUtils.closeQuietly(os);
			jsonObject.accumulate("uuid", uuid);
		}
		//将文件缓存到redis
		InputStream is = new FileInputStream(getErrorXlsPathname(uuid,"xls"));
		this.redisTemplate.opsForHash().put(CacheConstant.C_COMMODITY_IMPORT_ERROR_KEY, uuid, IOUtils.toByteArray(is));
	}
	
	/** 
	 * 校验excel数据格式
	 * （属于文件严重问题，不可跳过）
	 * @author li.n1 
	 * @param sheet
	 * @return 
	 * @since JDK 1.6 
	 */  
	private boolean checkExcelFile(Sheet sheet,List<ExcelErrorVo> errorList,CellStyle cellStyle,boolean isNeedActivePrice, boolean isSupportCoupon) {
		String uniqueCoupon = yougouConfiguration.getString(Constant.YMC_COUPON_UNIQUE_VALUE, "0");
		if(Constant.OFFICIAL_ACTIVE_COUPON_UNIQUE.equals(uniqueCoupon)){
			isSupportCoupon = false;
		}
		String[] titleName = null;
		if(isNeedActivePrice && isSupportCoupon){
			titleName = new String[]{"商品编码","活动价","最高可承担卡券金额"};				
		}else if(isNeedActivePrice){
			titleName = new String[]{"商品编码","活动价"};
		}else if(isSupportCoupon){
			titleName = new String[]{"商品编码","最高可承担卡券金额"};
		}else{
			titleName = new String[]{"商品编码"};
		}
		Row row = null;
		row = sheet.getRow(0);
		if(row!=null){
			int rowNumIndex = row.getLastCellNum();
			if(rowNumIndex != titleName.length){
				errorList.add(new ExcelErrorVo("错误列","请保持Excel列为"+Arrays.toString(titleName),0,0));
			}else{
				for(int c=0;c<row.getLastCellNum();c++){
					if(!titleName[c].equals(ExcelToDataModelConverter.getCellValue(row,c))){
						errorList.add(new ExcelErrorVo("错误列",titleName[c]+"列不允许被修改或变换顺序",row.getRowNum(),c));
					}
				}
			}
			
			if(errorList.size()>0){	//格式有错误
				return false;
			}
			return true;
		}else{
			errorList.add(new ExcelErrorVo("错误列","请保持Excel列为"+Arrays.toString(titleName),0,0));
			return false;
		}
		
	}
	
	/**
	 * 获取导入错误商品数据行文件名
	 * 
	 * @param uuid
	 * @return String
	 */
	private String getErrorXlsPathname(String uuid,String fileType) {
		return new StringBuilder().append(System.getProperty("java.io.tmpdir")).append(File.separator).append(uuid).append(".").append(fileType).toString();
	}
	
	/** 
	 * 校验excel每行内容
	 * （属于小问题，行有错误可跳过执行下一行）
	 * @author li.n1 
	 * @param sheet
	 * @return true 检验行没问题，false 有问题
	 * @since JDK 1.6 
	 */  
	private boolean checkCommodityRow(boolean isNeedActivePrice,boolean isSupportCoupon,Row row,List<ExcelErrorVo> errorList) {
		int couponHighest = yougouConfiguration.getInt(Constant.YMC_COUPON_HIGHEST_VALUE, 200);
		int couponDefault = yougouConfiguration.getInt(Constant.YMC_COUPON_DEFAULT_VALUE, 30);
		String uniqueCoupon = yougouConfiguration.getString(Constant.YMC_COUPON_UNIQUE_VALUE, "0");
		if(Constant.OFFICIAL_ACTIVE_COUPON_UNIQUE.equals(uniqueCoupon)){
			isSupportCoupon = false;
		}
		//商品编码不能为空！
		//活动价,最高可承担卡券金额只能为数字 		
		String cellValue = ExcelToDataModelConverter.getCellValue(row,0);
		if(StringUtils.isBlank(StringUtils.trim(cellValue))){
			errorList.add(new ExcelErrorVo("商品编码","商品编码不能为空",row.getRowNum(),0));
		}
		if(isNeedActivePrice && isSupportCoupon){
			cellValue = ExcelToDataModelConverter.getCellValue(row,1);
			if((!(Pattern.compile("^[1-9][0-9]*$").matcher(cellValue).find())||
					StringUtils.isBlank(cellValue))){		
				errorList.add(new ExcelErrorVo("活动价","活动价不能为空，且只能为大于0的整数",row.getRowNum(),1));
			}
			cellValue = ExcelToDataModelConverter.getCellValue(row,2);
			if(StringUtils.isNotBlank(cellValue)){
				if(!(Pattern.compile("^[0-9]*[05]$").matcher(cellValue).find())){		
					errorList.add(new ExcelErrorVo("最高可承担卡券金额","最高可承担卡券金额只能为5的整数倍",row.getRowNum(),2));
				}else if(Integer.valueOf(cellValue) < couponDefault
						|| Integer.valueOf(cellValue) > couponHighest){
					errorList.add(new ExcelErrorVo("最高可承担卡券金额","最高可承担卡券金额应该为["+
							couponDefault+","+
							couponHighest+"]之间的数，且为5的整数倍",row.getRowNum(),2));
				}
			}
		}else if(isNeedActivePrice){
			cellValue = ExcelToDataModelConverter.getCellValue(row,1);
			if((!(Pattern.compile("^[1-9][0-9]*$").matcher(cellValue).find())||
					StringUtils.isBlank(cellValue))){		
				errorList.add(new ExcelErrorVo("活动价","活动价不能为空，且只能为大于0的整数",row.getRowNum(),1));
			}
		}else if(isSupportCoupon){
			cellValue = ExcelToDataModelConverter.getCellValue(row,1);
			if(StringUtils.isNotBlank(cellValue)){
				if(!(Pattern.compile("^[0-9]*[05]$").matcher(cellValue).find())){		
					errorList.add(new ExcelErrorVo("最高可承担卡券金额","最高可承担卡券金额只能为5的整数倍",row.getRowNum(),2));
				}else if(Integer.valueOf(cellValue) < couponDefault
						|| Integer.valueOf(cellValue) > couponHighest){
					errorList.add(new ExcelErrorVo("最高可承担卡券金额","最高可承担卡券金额应该为["+
							couponDefault+","+
							couponHighest+"]之间的数，且为5的整数倍",row.getRowNum(),2));
				}
			}
		}		
			
		if(errorList.size()>0){	//格式有错误
			return false;
		}
		return true;
		
	}
	
	/**
	 * 下载模板文件
	 * @param request
	 * @param response
	 * @param needActivePrice
	 * @param needCoupon
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("active/downTemplateFile")
	public String downTemplateFile(HttpServletRequest request, HttpServletResponse response,
			String needActivePrice, String needCoupon) throws Exception {
		try {
			String fileName = "";
			String fileNameZH = "";
			
			if("1".equals(needActivePrice) && "1".equals(needCoupon)){
				fileName = "activeCommodity.xls";
				fileNameZH = "导入商品模板_活动价_最高可承担卡券金额.xls";
			}else if("1".equals(needActivePrice)){
				fileName = "activeCommodity_1_0.xls";
				fileNameZH = "导入商品模板_活动价.xls";
			}else if("1".equals(needCoupon)){
				fileName = "activeCommodity_0_1.xls";
				fileNameZH = "导入商品模板_最高可承担卡券金额.xls";
			}else{
				fileName = "activeCommodity_0_0.xls";
				fileNameZH = "导入商品模板.xls";
			}
			// 导出EXCEL
			String templatePath = request.getSession().getServletContext()
					.getRealPath("/WEB-INF/template/"+fileName);
			// path是指欲下载的文件的路径。
			File file = new File(templatePath);

			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(
					templatePath));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			String browser = request.getHeader("user-agent");
			if(browser.indexOf("Firefox") > 0){
				response.addHeader("Content-Disposition",
						"attachment;filename="+new String(fileNameZH.getBytes("GBK"),"ISO-8859-1"));
			}else{
				response.addHeader("Content-Disposition",
						"attachment;filename="+java.net.URLEncoder.encode(fileNameZH, "UTF-8"));
			}
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
