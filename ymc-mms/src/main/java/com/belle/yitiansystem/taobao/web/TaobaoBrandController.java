package com.belle.yitiansystem.taobao.web;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.yitiansystem.merchant.model.pojo.MerchantOperationLog;
import com.belle.yitiansystem.merchant.model.pojo.MerchantOperationLog.OperationType;
import com.belle.yitiansystem.taobao.exception.BusinessException;
import com.belle.yitiansystem.taobao.model.TaobaoBrand;
import com.belle.yitiansystem.taobao.service.ITaobaoBrandService;
import com.yougou.merchant.api.common.PageFinder;
import com.yougou.merchant.api.common.Query;

/**
 * 淘宝品牌管理
 * @author 
 *
 */
@Controller
@RequestMapping("/yitiansystem/taobao")
public class TaobaoBrandController extends BaseController {
	
	private final String taobaoFtlPath = "/yitiansystem/merchants/taobao/";
	
	private static final Logger log = LoggerFactory
			.getLogger(TaobaoBrandController.class);
	
	@Resource
	private ITaobaoBrandService taobaoBrandService;
	

	
	
	/**
	 * 根据session中的merchantCode获取未绑定的类目 跳转优购淘宝类目绑定信息页面
	 * 
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/goBrand")
	public String goYougouTaobaoItemCat(ModelMap modelMap,
			HttpServletRequest request, Query query)
			throws UnsupportedEncodingException {
		return taobaoFtlPath + "taobaobrand4local";
	}
	
	@RequestMapping("/getTaobaoBindList")
	public String getYougouTaobaoBindList(ModelMap model, Query query, HttpServletRequest request) {
		try{
			PageFinder<TaobaoBrand> pageFinder = taobaoBrandService.findTaoaoBrandList(this.builderParams(request, false), query);
			model.addAttribute("pageFinder", pageFinder);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return taobaoFtlPath + "taobaobrandlist4local";
	}
	
	@ResponseBody
	@RequestMapping("/addBrand")
	public String bindBrand(ModelMap model,TaobaoBrand brand,HttpServletRequest request){
		JSONObject result = new JSONObject();
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("vid", brand.getVid());
			List<TaobaoBrand> list = this.taobaoBrandService.findTaobaoBrandList(map);
			if(list.isEmpty()){
				result.put("resultCode", "200");
				this.taobaoBrandService.addBrand(brand,GetSessionUtil.getSystemUser(request).getLoginName(),request);
			}else{
				result.put("resultCode", "500");
				result.put("msg","VID："+brand.getVid()+" 已经存在！品牌名称为："+list.get(0).getName());
			}
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
	@RequestMapping("/delBrand")
	public String delBrand(ModelMap model,String  ids,HttpServletRequest request){
		JSONObject result = new JSONObject();
		try{
			result.put("resultCode", "200");
			this.taobaoBrandService.deleteTaobaoBrand(ids,request);
		}catch(BusinessException e){
			log.error(e.getMessage());
			result.put("resultCode", "500");
			result.put("msg",e.getMessage());
		}catch(Exception e){
			log.error(e.getMessage(),e);
			result.put("msg","系统异常，删除失败");
			result.put("resultCode", "500");
		}
		return result.toString();
	}
}
