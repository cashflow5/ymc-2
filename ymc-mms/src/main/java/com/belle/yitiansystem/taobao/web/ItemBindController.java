package com.belle.yitiansystem.taobao.web;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.yitiansystem.taobao.exception.BusinessException;
import com.belle.yitiansystem.taobao.model.TaobaoYougouItemCat;
import com.belle.yitiansystem.taobao.service.ITaobaoYougouItemCatService;
import com.yougou.merchant.api.common.PageFinder;
import com.yougou.merchant.api.common.Query;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.model.category.Category;
import com.yougou.pc.model.prop.PropItem;

/**
 * 优购商家绑定基本信息数据（品牌、类目、类目属性、类目属性值）
 * @author 
 *
 */
@Controller
@RequestMapping("/yitiansystem/taobao")
public class ItemBindController extends BaseController {
	
	private final String taobaoFtlPath = "/yitiansystem/merchants/taobao/";
	
	private static final Logger log = LoggerFactory
			.getLogger(ItemBindController.class);
	
	@Resource
	private ITaobaoYougouItemCatService taobaoYougouItemCatService;
	
	@Resource
	private ICommodityBaseApiService commodityBaseApiService;

	/**
	 * 
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/goYougouTaobaoItemCat")
	public String goYougouTaobaoItemCat(ModelMap modelMap,
			HttpServletRequest request, Query query)
			throws UnsupportedEncodingException {
		modelMap.put("version",System.currentTimeMillis());
		modelMap.put("yougouTree",
				taobaoYougouItemCatService.findNoBindYougouItemCatZTree("", false));
		Map<String,Object>map = this.builderParams(request, false);
		map.put("pId", "0");
		modelMap.put("taobaoTree",
				taobaoYougouItemCatService.findNoBindTaobaoItemCatList(map));
		return taobaoFtlPath + "yougou_taobao_item_cat";
	}
	
	@RequestMapping("/getTaobaoItemBindList")
	public String getTaobaoItemBindList(ModelMap model, Query query, HttpServletRequest request) {
		try{
			PageFinder<TaobaoYougouItemCat> pageFinder = taobaoYougouItemCatService.findTaobaoYougouItemCatList(this.builderParams(request, false), query);
			model.addAttribute("pageFinder", pageFinder);
			return taobaoFtlPath + "yougou_taobao_item_cat_list";
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return taobaoFtlPath + "yougou_taobao_item_cat_list";
	}
	
	@RequestMapping("/goBindCat")
	public String toSaveYougouTaobaoItemCat(ModelMap modelMap,
			HttpServletRequest request) {
		modelMap.put("version",System.currentTimeMillis());
		return taobaoFtlPath+"yougou_taobao_item_cat_bind";
	}
	
	@ResponseBody
	@RequestMapping("/getYougouItemTree")
	public String getYougouItemTree(HttpServletRequest request,String structName,boolean filterBind) {
		JSONObject result = new JSONObject();
		try {
			result.put("yougouTree",
					taobaoYougouItemCatService.findNoBindYougouItemCatZTree(structName, filterBind));
		} catch (Exception e) {
			log.error("获取优购分类异常异常信息:{}", new Object[]{e.getMessage()});
			result.put("resultCode","500");
			result.put("msg", "系统异常!");
		}
		return result.toString();
	}

	@ResponseBody
	@RequestMapping("/getTaobaoItemTree")
	public String getTaobaoItemTree(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			Map<String,Object> params = this.builderParams(request, false);
			if(params.get("id")==null){
				params.put("pId", "0");
			}else{
				params.put("pId",params.get("id"));
			}
			result.put("taobaoTree",
					taobaoYougouItemCatService.findNoBindTaobaoItemCatList(params));
		} catch (Exception e) {
			log.error("获取淘宝分类异常异常信息:{}", new Object[]{e.getMessage()});
			result.put("resultCode","500");
			result.put("msg", "系统异常!");
		}
		return result.toString();
	}
	
	
	@RequestMapping("/bindTaobaoYougouItemCat")
	public String findTaobaoYougouItemCatZTree(HttpServletRequest request,
			ModelMap modelMap,String yougouItemId,String taobaoCid)
			throws Exception {
		modelMap.put("version",System.currentTimeMillis());
		String bindId = "";
		try{
			String operater = GetSessionUtil.getSystemUser(request).getLoginName();
			Map<String,Object> params = this.builderParams(request,false);
			params.put("operater", operater);
			bindId = taobaoYougouItemCatService.bindYougouItemCate(params);
		} catch (Exception e) {
			log.error("淘宝分类绑定异常,异常信息:{}", new Object[]{e.getMessage()});
			throw e;
		}
		modelMap.put("catId", yougouItemId);
		modelMap.put("bindId", bindId);
		
		return "redirect:"+"/yitiansystem/taobao/goYougouTaobaoItemCatProBind.sc";
	}
	@RequestMapping("/goYougouTaobaoItemCatProBind")
	public String goYougouTaobaoItemCatProBind(HttpServletRequest request,
			ModelMap modelMap,String catId,String bindId,String catNo)throws Exception{
		modelMap.put("version",System.currentTimeMillis());
		if(StringUtils.isEmpty(catId)&&StringUtils.isEmpty(catNo)||StringUtils.isEmpty(bindId)){
			throw new Exception("请求参数错误");
		}
		if(StringUtils.isEmpty(catId)&&!StringUtils.isEmpty(catNo)){
			Category category = commodityBaseApiService.getCategoryByNo(catNo);
			if(null==category){
				throw new Exception("分类不存在");
			}
			catId = category.getId();
		}
		TaobaoYougouItemCat  catTemplet = taobaoYougouItemCatService.findTaobaoYougouItemCatById(bindId);
		if(null==catTemplet){
			throw new Exception("分类绑定不存在");
		}
		modelMap.put("catTemplet",catTemplet);
		modelMap.put("catId", catId);
		modelMap.put("taobaoCid", catTemplet.getTaobaoCid().toString());
		return taobaoFtlPath+"yougou_taobao_item_cat_pro_bind";
	}
	
	
	/**
	 * 查询优购分类属性，淘宝分类属性
	 * 
	 * @param catId
	 * @param bindId
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getYougouTaobaoPro")
	public String getYougouProAndTaobaoPro(String catId, String bindId,
			HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			List<PropItem> yougouPropList = taobaoYougouItemCatService
					.getPropMsgByCatIdNew(catId, true);
			result.put("yougouPropList", yougouPropList);
			result.put("taobaoProList", taobaoYougouItemCatService.selectItemProWidthBindYougouItemPro(bindId));
			result.put("resultCode","200");
			// 获取淘宝颜色分类属性值
			/*TaobaoYougouItemCat cat = taobaoBasicService
					.getTaobaoYougouItemCatById(bindId);
			result.put("taobaoColorValues", taobaoBasicService
					.findTaobaoItemProVal4Color(cat.getTaobaoCid()));*/
		} catch (Exception e) {
			result.put("msg", "获取优购、淘宝属性异常");
			result.put("resultCode", "500");
			log.error("获取优购、淘宝属性异常,异常信息:{}", new Object[] {e.getMessage() });
		}
		return result.toString();
	}
	
	/**
	 * 获取淘宝分类属性值
	 * 
	 * @param pid
	 * @param cid
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getTaobaoItemProVal")
	public String getTaobaoItemProVal(Long pid, Long cid, String catBindId,
			HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
			result.put("proVals",
					taobaoYougouItemCatService.selectTaobaoItemProValWidthYouItemProVal(pid,
					cid, catBindId));
			result.put("resultCode","200");
		} catch (Exception e) {
			result.put("resultCode","500");
			result.put("msg", "获取淘宝属性值异常");
			log.error("获取淘宝属性值异常,异常信息:{}", new Object[]{e.getMessage()});
		}
		return result.toString();
	}
	
	@ResponseBody
	@RequestMapping("/bindTaobaoYougouItemProAndVal")
	public String bindTaobaoYougouItemProAndVal(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String operater = GetSessionUtil.getSystemUser(request).getLoginName();
		try {
			taobaoYougouItemCatService.bindTaobaoYougouItemProAndVal(request,operater);
			result.put("resultCode","200");
		} catch (BusinessException e) {
			result.put("resultCode","500");
			result.put("msg", e.getMessage());
			log.error("绑定分类属性属性值异常,异常信息:{}", new Object[]{e.getMessage()});
		}catch (Exception e) {
			result.put("resultCode","500");
			result.put("msg", "绑定分类属性属性值异常");
			log.error("绑定分类属性属性值异常,异常信息:{}", new Object[]{e.getMessage()});
		}
		return result.toString();
	}
	
	@RequestMapping("/bindTaobaoYougouItemProAndValSuccess")
	public String bindTaobaoYougouItemProAndValSuccess(
			HttpServletRequest request) {
		return taobaoFtlPath+"yougou_taobao_item_prop_success";
	}
	 
	@ResponseBody
	@RequestMapping("/deleteYougouTaobaoItemCat")
	public String deleteYougouTaobaoItemCat(HttpServletRequest request,String ids) {
		JSONObject result = new JSONObject();
		try {
			taobaoYougouItemCatService.deleteYougouTaobaoItemCat(ids,request);
			
			
			result.put("resultCode","200");
		} catch (BusinessException e) {
			result.put("resultCode","500");
			result.put("msg",e.getMessage());
			log.error("删除分类异常,异常信息:{}", new Object[]{e.getMessage()});
		}catch (Exception e) {
			result.put("resultCode","500");
			result.put("msg", "删除分类异常");
			log.error("删除分类异常,异常信息:{}", new Object[]{e.getMessage()});
		}
		return result.toString();
	}
}
