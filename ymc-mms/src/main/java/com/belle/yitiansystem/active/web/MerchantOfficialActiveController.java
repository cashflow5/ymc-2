package com.belle.yitiansystem.active.web;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.web.controller.BaseController;
import com.belle.yitiansystem.active.service.MerchantOfficialActivityService;
import com.belle.yitiansystem.mctnotice.web.controller.NoticeController;
import com.yougou.active.api.IOfficialActiveApiService;
import com.yougou.active.vo.OfficialActiveSearcher;
import com.yougou.active.vo.OfficialActiveVo;
import com.yougou.active.vo.OfficicalActiveBrandAndCategory;
import com.yougou.coupon.api.ICouponApiService;
import com.yougou.kaidian.common.base.Query;
import com.yougou.member.user.api.IMemberLevelApi;
import com.yougou.member.user.vo.MemberLevelVo;

/**
 * 商家报名官方Controller
 * @author zhang.wj
 *
 */
@Controller
@RequestMapping("/active/web/merchantOfficialActiveController")
public class MerchantOfficialActiveController extends BaseController{
	private Logger logger = Logger.getLogger(MerchantOfficialActiveController.class);
	@Resource 
	public MerchantOfficialActivityService merchantOfficialActivityService;
	
	@Resource 
	public IOfficialActiveApiService officialActiveApiService;
	
	
	@Resource
	public ICouponApiService  couponApiService;
	@Resource
	public IMemberLevelApi memberLevelApi;
	
	
	/**
	 * 查询官方活动报名数据
	 * @param modelMap
	 * @param request
	 * @param query
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/qeuryMerchantOfficialActive")
	public String qeuryMerchantOfficialActive(ModelMap modelMap, HttpServletRequest request,Query query) throws Exception {
		
		Map<String, Object> params =new HashMap<String,Object>();
		//query.setPageSize(10);
		OfficialActiveSearcher  officialActiveSearcher=new OfficialActiveSearcher();
		if(query != null){
			officialActiveSearcher.setStart((query.getPage() -1) * query.getPageSize());
			officialActiveSearcher.setLength(query.getPageSize());
		}
		this.toMapMerchant(params, request,officialActiveSearcher);
		
		 //官方活动查询 数量
		 int count =officialActiveApiService.getOfficialActiveVoCount(officialActiveSearcher);
		 //从促销查询官方活动列表
		 List<OfficialActiveVo> 	officialActiveList=officialActiveApiService.getOfficialActiveVoList(officialActiveSearcher);
		 List<Map<String,Object>> activeList=officialActiveToMap(officialActiveList);
		 //如果为空直接返回
		 PageFinder<Map<String, Object>> pageFinder=null;
		 if (!CollectionUtils.isEmpty(activeList)) {
			 pageFinder=new PageFinder<Map<String, Object>>(query.getPage(), query.getPageSize(),count);
			 pageFinder.setData(activeList);
			 modelMap.addAttribute("pageFinder", pageFinder);
		 }
		 modelMap.put("params", params);
		 modelMap.addAttribute("activeType", initActiveType());
		 
		return "active/merchantOfficialActiveList";
	}
	public void toMapMerchant(Map<String, Object> params, HttpServletRequest request,OfficialActiveSearcher  officialActiveSearcher) throws ParseException{
		
		String activeName = request.getParameter("activeName");
		params.put("activeName", activeName);
		if(StringUtils.isNotBlank(activeName)){
			officialActiveSearcher.setActiveName(activeName);
		}
		String activeType = request.getParameter("activeType");
		params.put("activeType", activeType);
		if(StringUtils.isNotBlank(activeType)){
			officialActiveSearcher.setActiveType(Short.valueOf(activeType));
		}
		String status = request.getParameter("status");
		if(StringUtils.isNotBlank(status)){
			params.put("status", status);
			officialActiveSearcher.setActiveState(Short.valueOf(status));
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 报名时间
		String signUpStartTime = request.getParameter("createTimeStart_1");
		params.put("createTimeStart_1", signUpStartTime);
		if(StringUtils.isNotBlank(signUpStartTime)){
			Date date = sdf.parse(signUpStartTime);
			officialActiveSearcher.setSignUpStartTime(date);
		}
		
		String signUpEndTime =request.getParameter("createTimeEnd_1");
		params.put("createTimeEnd_1", signUpEndTime);
		if(StringUtils.isNotBlank(signUpEndTime)){
			Date date = sdf.parse(signUpEndTime);
			officialActiveSearcher.setSignUpEndTime(date);
		}
		//审核时间
		String auditMerchantStartTime = request.getParameter("createTimeStart_2");
		params.put("createTimeStart_2", auditMerchantStartTime);
		if(StringUtils.isNotBlank(auditMerchantStartTime)){
			Date date = sdf.parse(auditMerchantStartTime);
			officialActiveSearcher.setAuditMerchantStartTime(date);
		}
		String auditMerchantEndTime = request.getParameter("createTimeEnd_2");
		params.put("createTimeEnd_2", auditMerchantEndTime);
		if(StringUtils.isNotBlank(auditMerchantEndTime)){
			Date date = sdf.parse(auditMerchantEndTime);
			officialActiveSearcher.setAuditMerchantEndTime(date);
		}
		//活动时间
		String activeStartTime = request.getParameter("createTimeStart_3");
		params.put("createTimeStart_3", activeStartTime);
		if(StringUtils.isNotBlank(activeStartTime)){
			Date date = sdf.parse(activeStartTime);
			officialActiveSearcher.setActiveStartTime(date);
		}
		String activeEndTime = request.getParameter("createTimeEnd_3");
		params.put("createTimeEnd_3", activeEndTime);
		if(StringUtils.isNotBlank(activeEndTime)){
			Date date = sdf.parse(activeEndTime);
			officialActiveSearcher.setActiveEndTime(date);
		}
	}
	
	/**
	 * 活动规则
	 * @param modelMap
	 * @param request
	 * @param activeId
	 * @return
	 */
	@RequestMapping("/queryActiveRule")
	public String queryActiveRule(ModelMap modelMap, HttpServletRequest request,String activeId){
		//活动规则
		 OfficialActiveVo 	officialActive=officialActiveApiService.getOfficialActiveVoById(activeId);
		 modelMap.addAttribute("officialActive", officialActive);
		//活动id
		modelMap.addAttribute("activeId", activeId);
		return "active/activeRuleList";
	}
	/**
	 * 活动介绍
	 * @param modelMap
	 * @param request
	 * @param activeId
	 * @return
	 */
	@RequestMapping("/queryActiveIntroduce")
	public String queryActiveIntroduce(ModelMap modelMap, HttpServletRequest request,String activeId){
		
		//活动介绍
		 OfficialActiveVo 	officialActive=officialActiveApiService.getOfficialActiveVoById(activeId);
		 
		 List<OfficicalActiveBrandAndCategory> officicalActiveBrandAndCategoryList = officialActive.getBrandAndCategories();
		 Set<String> brands = new HashSet<String>();
		 Set<String> cats = new HashSet<String>();
		 //上传商品范围
		 for(OfficicalActiveBrandAndCategory officicalActiveBrandAndCategory : officicalActiveBrandAndCategoryList){
			brands.add(officicalActiveBrandAndCategory.getBrandName());
			cats.add(officicalActiveBrandAndCategory.getFirstCategoryName());
		 }
		 //获取活动参与对象
		List<MemberLevelVo>  memberLevelVoList=memberLevelApi.queryLevel();
		if(officialActive!=null && "all".equals(officialActive.getMemberType())){
			 modelMap.addAttribute("memberLevelVoList", memberLevelVoList);
		}else{
			String memberType=officialActive.getMemberType();
			if(StringUtils.isNotBlank(memberType)){
				List<MemberLevelVo>  memberLevelList=new  ArrayList<MemberLevelVo>();
				String [] levelIds=memberType.split(",");
				if(levelIds!=null){
					for (int i = 0; i < levelIds.length; i++) {
						for (int j = 0; j < memberLevelVoList.size(); j++) {
							MemberLevelVo vo=memberLevelVoList.get(j);
							String id=vo.getId().trim();
							if(id!=null  && id.equals(levelIds[i].trim())){
								memberLevelList.add(vo);
							}
						}
					}
				}
				modelMap.addAttribute("memberLevelVoList", memberLevelList);
			}
		}
		 
		 modelMap.addAttribute("officialActive", officialActive);
		 modelMap.addAttribute("brands", brands);
		 modelMap.addAttribute("cats", cats);
		//活动id
		modelMap.addAttribute("activeId", activeId);
		return "active/activeIntroduceList";
	}
	
	/**
	 * 查看商家
	 * @param modelMap
	 * @param request
	 * @param query
	 * @return
	 */
	@RequestMapping("/queryMerchant")
	public String queryMerchant(ModelMap modelMap, HttpServletRequest request,Query query){
		//查询条件
		Map<String,Object> map=new HashMap<String, Object>();
		toMap(map, request, query);
		try {
			PageFinder<Map<String, Object>>  pageFinder=merchantOfficialActivityService.queryMerchant(map, query);
			if (!CollectionUtils.isEmpty(pageFinder.getData())) {
				modelMap.addAttribute("pageFinder",pageFinder);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		modelMap.addAttribute("params", map);
		return "active/offActivitMerchantList";
	}
	/**
	 * 查询商品信息
	 * @param modelMap
	 * @param request
	 * @param query
	 * @return
	 */
	@RequestMapping("/queryCommodity")
	public String queryCommodity(ModelMap modelMap, HttpServletRequest request,Query query){
		//查询条件
		Map<String,Object> map=new HashMap<String, Object>();
		toMap(map, request, query);
		try {
			PageFinder<Map<String, Object>>  pageFinder=merchantOfficialActivityService.queryCommodity(map, query);
			if (!CollectionUtils.isEmpty(pageFinder.getData())) {
				//取出活动名称和商品编码
				List<Map<String, Object>> commodityList=pageFinder.getData();
				initCommoditySupportCoupons(commodityList);
				pageFinder.setData(commodityList);
				Map<String, Object>   commodityMap=commodityList.get(0);
				map.put("activeName",commodityMap.get("active_name") );
				map.put("merchantName",commodityMap.get("merchant_name"));
				modelMap.addAttribute("pageFinder",pageFinder);
				
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		modelMap.addAttribute("params", map);
		return "active/offActivitCommodityList";
	}
	/**
	 * 初始化用卷限制
	 * 
	 * @param commodityList
	 */
	public void initCommoditySupportCoupons(List<Map<String, Object>> commodityList){
		List<String>  commodityNoList=new ArrayList<String>();
		for (int i = 0; i < commodityList.size(); i++) {
			Map<String, Object>  map=commodityList.get(i);
			commodityNoList.add((String)map.get("no"));
		}
		Map<String, Boolean> coupons=couponApiService.checkCommoditySupportCoupons(commodityNoList);
		for (int i = 0; i < commodityList.size(); i++) {
			Map<String, Object>  map=commodityList.get(i);
			String strNo=(String)map.get("no");
			Iterator it = coupons.keySet().iterator();
			while(it.hasNext()){
				String key = (String) it.next();
				if(strNo!=null && strNo.equals(key)){
					map.put("coupons", coupons.get(key));
				}
			}
			commodityList.set(i, map);
		}
		
		

	}
	
	/**
	 * 拼查询参数
	 */
	private void toMap(Map<String,Object> map ,HttpServletRequest request,Query query){
		try {
 			//request.setCharacterEncoding("UTF-8");
			String status=request.getParameter("status"); 
			map.put("status", (status==null)?"":status);
			map.put("activeId",request.getParameter("activeId"));
			String merchantName=request.getParameter("merchantName");
			map.put("merchantName",merchantName);
			map.put("merchantCode", request.getParameter("merchantCode"));
			map.put("commodityNo", request.getParameter("commodityNo"));
			
			if(query != null){
				map.put("start", (query.getPage() -1) * query.getPageSize());
				map.put("pageSize", query.getPageSize());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
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
	 * 把OfficialActiveVo转换成map
	 * @param officialActiveList
	 * @return
	 * @throws Exception 
	 */
	private List<Map<String,Object>>  officialActiveToMap(List<OfficialActiveVo> 	officialActiveList) throws Exception{
		List<Map<String,Object>>   activeList=new ArrayList<Map<String,Object>>();
		if(officialActiveList!=null && officialActiveList.size()>0){
			for (int i = 0; i < officialActiveList.size(); i++) {
				Map<String,Object>  officialActiveMap=new HashMap<String, Object>();
				OfficialActiveVo   vo=officialActiveList.get(i);
				officialActiveMap.put("activeName", vo.getActiveName());
				officialActiveMap.put("activeType", vo.getActiveType());
				officialActiveMap.put("signUpStartTime", vo.getSignUpStartTime());
				officialActiveMap.put("signUpEndTime", vo.getSignUpEndTime());
				officialActiveMap.put("merchantAuditStartTime", vo.getMerchantAuditStartTime());
				officialActiveMap.put("merchantAuditEndTime", vo.getMerchantAuditEndTime());
				officialActiveMap.put("startTime", vo.getStartTime());
				officialActiveMap.put("endTime", vo.getEndTime());
				officialActiveMap.put("activeState", vo.getActiveState());
				officialActiveMap.put("id", vo.getId());
				int  count=merchantOfficialActivityService.queryMerchantCount(vo.getId());
				officialActiveMap.put("merchantCount",  count );
				activeList.add(officialActiveMap);
			}
		}
		return activeList;
	}
	
}



