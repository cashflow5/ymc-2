package com.yougou.kaidian.fss.web;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yougou.fss.api.IFSSWebApiService;
import com.yougou.fss.api.IFSSYmcApiService;
import com.yougou.fss.api.vo.BrandSelectVo;
import com.yougou.fss.api.vo.CategoryVo;
import com.yougou.fss.api.vo.CommodityStyle;
import com.yougou.fss.api.vo.FSSResult;
import com.yougou.fss.api.vo.FssLog;
import com.yougou.fss.api.vo.ModuleBasicInfo;
import com.yougou.fss.api.vo.PageFinder;
import com.yougou.fss.api.vo.ShopVO;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;

@Controller
@RequestMapping("/mctfss")
public class FssController {

	private final String fssFtlPath = "/manage/fss/";

	@Resource
	private IFSSYmcApiService fssYmcApiService;

	@Resource
	private IFSSWebApiService fssWebApiService;

	@RequestMapping("/store/list")
	public String getStoreList(ModelMap modelMap, HttpServletRequest request,
			Query query, ShopVO shopvo)
			throws Exception {
		shopvo.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
		FSSResult fssResult = fssYmcApiService.getShopList(shopvo,
				query.getPage(),
				query.getPageSize(), null, null);
		if(fssResult!=null&&fssResult.getData()!=null){
			PageFinder<ShopVO> pageFinder = (PageFinder<ShopVO>) fssResult.getData();
			modelMap.addAttribute("storeList", pageFinder.getData());
		}
		return fssFtlPath + "store_list";
	}

	@RequestMapping("/store/showDetail")
	public String showDetail(ModelMap modelMap, HttpServletRequest request,
			String id) throws Exception {
		ShopVO shopvo = fssYmcApiService.selectShopById(id);
		modelMap.addAttribute("store", shopvo);
		return "/manage_unless/fss/store_show";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/log/list")
	public String getLogList(ModelMap modelMap, HttpServletRequest request,
			com.yougou.fss.api.vo.Query query, FssLog fsslog, ShopVO shopvo)
			throws Exception {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		fsslog.setMerchantCode(merchantCode);

		shopvo.setMerchantCode( merchantCode );
		FSSResult fssResult = fssYmcApiService.getShopList(shopvo,
				query.getPage(), query.getPageSize(), null, null);
		PageFinder<ShopVO> shopPageFinder = (PageFinder<ShopVO>) fssResult
				.getData();
		modelMap.addAttribute("storeList", shopPageFinder.getData());

		PageFinder<FssLog> pageFinder = fssWebApiService.selectFssLogList(
				fsslog, query);
		modelMap.addAttribute("pageFinder", pageFinder);
		modelMap.addAttribute("fssLog", fsslog);
		return fssFtlPath + "log_list";
	}

	@RequestMapping("/log/showLogDetail")
	public String showLogDetail(ModelMap modelMap, HttpServletRequest request,
			com.yougou.fss.api.vo.Query query, FssLog fsslog) throws Exception {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		fsslog.setMerchantCode(merchantCode);
		PageFinder<FssLog> pageFinder = fssWebApiService.selectFssLogList(
				fsslog, query);
		if(pageFinder!=null&&pageFinder.getData()!=null){
			List<FssLog> list = pageFinder.getData();
			FssLog resultLog = null;
			for (FssLog log : list) {
				if (log.getId().equals(fsslog.getId())) {
					resultLog = log;
					break;
				}
			}
			modelMap.addAttribute("log", resultLog);
		}
		return "/manage_unless/fss/log_show";
	}

	@RequestMapping("/commodity/list")
	public String goCommodityList(ModelMap modelMap,
			HttpServletRequest request, com.yougou.fss.api.vo.Query query,
			CommodityStyle commoditystyle, ShopVO shopvo) throws Exception {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		commoditystyle.setMerchantCode(merchantCode);
		shopvo.setMerchantCode( merchantCode );
		FSSResult fssResult = fssYmcApiService.getShopList(shopvo,
				query.getPage(), query.getPageSize(), null, null);
		if(fssResult!=null&&fssResult.getData()!=null){
			PageFinder<ShopVO> shopPageFinder = (PageFinder<ShopVO>) fssResult
					.getData();
			modelMap.addAttribute("storeSettingList", shopPageFinder.getData());

			List<BrandSelectVo> brandList = fssWebApiService
					.getBrandListByStoreId(shopPageFinder.getData().get(0)
							.getShopId());
			modelMap.addAttribute("brandList", brandList);
		}
		return fssFtlPath + "commodity";
	}

	@RequestMapping("/commodity/getList")
	public String getCommodityList(ModelMap modelMap,
			HttpServletRequest request, com.yougou.fss.api.vo.Query query,
			CommodityStyle commoditystyle, ShopVO shopvo) throws Exception {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		commoditystyle.setMerchantCode(merchantCode);
		PageFinder<CommodityStyle> pageFinder = fssWebApiService
				.selectCommodityList(
				commoditystyle, query);
		modelMap.addAttribute("pageFinder", pageFinder);
		return "/manage_unless/fss/commodity_list";
	}

	@RequestMapping("/moduleBasicInfo/list")
	public String getmoduleBasicInfoList(ModelMap modelMap,
			HttpServletRequest request,
			ModuleBasicInfo modulebasicinfo) throws Exception {
		List<ModuleBasicInfo> list = fssWebApiService
				.listModuleBasieInfo(modulebasicinfo);
		modelMap.addAttribute("dataList", list);
		return fssFtlPath + "moduleBasicInfo_list";
	}

	@ResponseBody
	@RequestMapping("/commodity/selBrands")
	public String selBrands(ModelMap modelMap, HttpServletRequest request,
			String storeId) throws Exception {
		List<BrandSelectVo> list = fssWebApiService
				.getBrandListByStoreId(storeId);
		JSONObject result = new JSONObject();
		result.put("data", list);
		return result.toString();
	}

	@ResponseBody
	@RequestMapping("/commodity/selCate")
	public String selCate(ModelMap modelMap, HttpServletRequest request,
			String storeId, String brandId, String cateLevel, String cateId)
			throws Exception {
		List<CategoryVo> cateList = fssWebApiService.selCategory(storeId,
				brandId, cateLevel,
				cateId);
		JSONObject result = new JSONObject();
		result.put("cateList", cateList);
		return result.toString();
	}

}
