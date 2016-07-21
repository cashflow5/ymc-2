package com.belle.yitiansystem.taobao.web;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.yitiansystem.taobao.exception.BusinessException;
import com.belle.yitiansystem.taobao.model.TaobaoBrand;
import com.belle.yitiansystem.taobao.model.TaobaoYougouBrand;
import com.belle.yitiansystem.taobao.service.ITaobaoYougouBrandService;
import com.yougou.merchant.api.common.PageFinder;
import com.yougou.merchant.api.common.Query;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.model.brand.Brand;

/**
 * 优购商家绑定基本信息数据（品牌、类目、类目属性、类目属性值）
 * @author 
 *
 */
@Controller
@RequestMapping("/yitiansystem/taobao")
public class BrandBindController extends BaseController {
	
	private final String taobaoFtlPath = "/yitiansystem/merchants/taobao/";
	
	private static final Logger log = LoggerFactory
			.getLogger(BrandBindController.class);
	
	@Resource
	private ITaobaoYougouBrandService taobaoYougouBrandService;
	
	@Resource
	private ICommodityBaseApiService commodityBaseApiService;

	/**
	 * 根据session中的merchantCode获取未绑定的类目 跳转优购淘宝类目绑定信息页面
	 * 
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/goYougouTaobaoBrand")
	public String goYougouTaobaoItemCat(ModelMap modelMap,
			HttpServletRequest request, Query query)
			throws UnsupportedEncodingException {
		modelMap.put("version",System.currentTimeMillis());
		return taobaoFtlPath + "yougou_taobao_brand";
	}
	
	@RequestMapping("/getYougouTaobaoBindList")
	public String getYougouTaobaoBindList(ModelMap model, Query query, HttpServletRequest request) {
		try{
			PageFinder<TaobaoYougouBrand> pageFinder = taobaoYougouBrandService.findTaoaoYougouBrandList(this.builderParams(request, false), query);
			model.addAttribute("pageFinder", pageFinder);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return taobaoFtlPath + "yougou_taobao_brand_list";
	}
	
	@RequestMapping("/goYougouTaobaoBrandBind")
	public String goYougouTaobaoBrandBind(ModelMap model, Query query, HttpServletRequest request) {
		model.put("version",System.currentTimeMillis());
		return taobaoFtlPath + "yougou_taobao_brandbind";
	}
	
	
	@RequestMapping("/goYougouBrand")
	public String goYougouBrand(ModelMap model, Query query, HttpServletRequest request) {
		model.put("version",System.currentTimeMillis());
		return taobaoFtlPath + "yougou_brand";
	}
	
	@RequestMapping("/getYougouBrandList")
	public String getYougouBrandList(ModelMap model,String yougouBrandName, HttpServletRequest request) {
		try{
			List<Brand> brands = commodityBaseApiService.getBrandListLikeBrandName("%" + yougouBrandName, (short) 1);
			if(brands!=null&&!brands.isEmpty()){
				Iterator<Brand> iterator = brands.iterator();
				while(iterator.hasNext()){
					Brand brand = iterator.next();
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("yougouBrandNo", brand.getBrandNo());
					List<TaobaoYougouBrand> list = taobaoYougouBrandService.findTaoaoYougouBrandList(map);
					if(list!=null&&!list.isEmpty()){
						iterator.remove();
					}
				}
			}
			model.addAttribute("brands", brands);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return taobaoFtlPath + "yougou_brand_list";
	}
	
	@RequestMapping("/goTaobaoBrand")
	public String goTaobaoBrand(ModelMap model, Query query, HttpServletRequest request) {
		model.put("version",System.currentTimeMillis());
		return taobaoFtlPath + "taobao_brand";
	}
	
	@RequestMapping("/getTaobaoBrandList")
	public String getTaobaoBrandList(ModelMap model,String taobaoBrandName, HttpServletRequest request) {
		try{
			List<TaobaoBrand> list = taobaoYougouBrandService.findTaobaoBrandList(taobaoBrandName);
			model.addAttribute("brands", list);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return taobaoFtlPath + "taobao_brand_list";
	}
	
	@ResponseBody
	@RequestMapping("/bindBrand")
	public String bindBrand(ModelMap model,String[] yougouBrandNo,String[] yougouBrandName,String[] taobaoBrandNo,String[] taobaoBrandName,HttpServletRequest request){
		JSONObject result = new JSONObject();
		try{
			result.put("resultCode", "200");
			this.taobaoYougouBrandService.saveTaobaoYougouBrandBatch(yougouBrandNo, yougouBrandName, taobaoBrandNo, taobaoBrandName, GetSessionUtil.getSystemUser(request).getLoginName());
		}catch(BusinessException e){
			log.error(e.getMessage());
			result.put("resultCode", "500");
			result.put("msg",e.getMessage());
		}catch(Exception e){
			log.error(e.getMessage(),e);
			result.put("msg","系统异常，绑定失败");
			result.put("resultCode", "500");
		}
		return result.toString();
	}
	
	@ResponseBody
	@RequestMapping("/deleteYougouTaobaoBrand")
	public String deleteYougouTaobaoItemCat(HttpServletRequest request,String ids) {
		JSONObject result = new JSONObject();
		try {
			taobaoYougouBrandService.deleteBatch(ids,request);
			result.put("resultCode","200"); 
		} catch (BusinessException e) {
			result.put("resultCode","500");
			result.put("msg",e.getMessage());
			log.error("删除品牌,异常信息:{}", new Object[]{e.getMessage()});
		}catch (Exception e) {
			result.put("resultCode","500");
			result.put("msg", "删除品牌异常");
			log.error("删除品牌异常,异常信息:{}", new Object[]{e.getMessage()});
		}
		return result.toString();
	}
}
