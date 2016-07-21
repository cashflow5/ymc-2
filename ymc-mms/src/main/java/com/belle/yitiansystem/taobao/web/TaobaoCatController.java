package com.belle.yitiansystem.taobao.web;  

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.yitiansystem.merchant.model.pojo.MerchantOperationLog;
import com.belle.yitiansystem.merchant.model.pojo.MerchantOperationLog.OperationType;
import com.belle.yitiansystem.merchant.service.IMerchantOperationLogService;
import com.belle.yitiansystem.systemmgmt.util.SysconfigProperties;
import com.belle.yitiansystem.taobao.common.ExportExcelTool;
import com.belle.yitiansystem.taobao.exception.BusinessException;
import com.belle.yitiansystem.taobao.model.TaobaoItemCatPropValue;
import com.belle.yitiansystem.taobao.model.TaobaoItemPropValue;
import com.belle.yitiansystem.taobao.model.vo.TaobaoItemCatParentVo;
import com.belle.yitiansystem.taobao.model.vo.TaobaoItemCatPropVo;
import com.belle.yitiansystem.taobao.model.vo.TaobaoItemCatValueVo;
import com.belle.yitiansystem.taobao.model.vo.TaobaoItemCatVo;
import com.belle.yitiansystem.taobao.service.ITaobaoCatService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.yougou.merchant.api.common.Query;

@Controller
@RequestMapping("/yitiansystem/taobao")
public class TaobaoCatController {
	
	private final String BASE_PATH = "yitiansystem/merchants/taobao/";
	private static final Logger logger = LoggerFactory.getLogger(TaobaoCatController.class);
	
	@Autowired
	private ITaobaoCatService taobaoCatService;
	
	@Resource(name="sysconfigProperties")
    private SysconfigProperties settings;
	
	
	/**
	 * add by lsm 
	 * 日志查看
	 * @return
	 */
	@RequestMapping("/log")
	public String getTaobaoLogList(ModelMap model, HttpServletRequest request,
			String type ,
			com.belle.infrastructure.orm.basedao.Query query) {
		com.belle.infrastructure.orm.basedao.PageFinder<MerchantOperationLog> pageFinder=null;
		String merchantCode ="";
		try {
			pageFinder =merchantOperationLogService.queryMerchantOperationLogByOperationType(merchantCode, OperationType.valueOf(type), query);
		} catch (Exception e) {
			 
		}	
			model.addAttribute("pageFinder", pageFinder);
			model.addAttribute("type", type);
		return BASE_PATH + "taobao_log_list"; 
	}
	/**
	 * goTaobaoCat:淘宝分类列表 
	 * @author li.n1 
	 * @return 
	 * @since JDK 1.6
	 */
	@RequestMapping("/goTaobaoCat")
	public String goTaobaoCat(ModelMap model,TaobaoItemCatVo vo, Query query){
		PageFinder<Map<String, Object>> pageFinder = taobaoCatService.findTaobaoCat(vo,query);
		model.addAttribute("pageFinder", pageFinder);
		model.addAttribute("vo",vo);
		return BASE_PATH+"taobaoCat_list";
	}
	
	@ResponseBody
	@RequestMapping(value="/getTaobaoItem")
	public String getTaobaoItem(@RequestParam long cid){
		Gson gson = new Gson();
		List<Map<String, Object>> firstCats = null;
		JsonObject result = new JsonObject();
		try {
			firstCats = taobaoCatService.findChildrenById(cid);
			logger.info("taobao Item category json :"+gson.toJson(firstCats));
			return gson.toJson(firstCats);
		} catch (Exception e) {
			logger.error("获取淘宝分类异常,异常信息:",e);
			result.addProperty("resultCode", "500");
			result.addProperty("msg", "系统异常!");
		}
		return result.toString();
	}
	
	@RequestMapping("/viewCatDetail")
	public String viewCatDetail(ModelMap model,TaobaoItemCatVo vo){
		if(vo.getCid()!=null&&!"".equals(vo.getCid())){
			Map<TaobaoItemCatPropVo,List<TaobaoItemCatValueVo>>  propValMap = 
					taobaoCatService.findCatPropDetail(Long.parseLong(vo.getCid()));
			Map<String,List<TaobaoItemCatValueVo>> pMap = null;
			Map<String,Boolean> mMap = null;
			pMap = new HashMap<String,List<TaobaoItemCatValueVo>>();
			mMap = new HashMap<String, Boolean>();
			for(Map.Entry<TaobaoItemCatPropVo,List<TaobaoItemCatValueVo>> entry : propValMap.entrySet()){
				pMap.put(entry.getKey().getPname(),entry.getValue());
				mMap.put(entry.getKey().getPname(), entry.getKey().getMust());
			}
			model.addAttribute("pVList", pMap);
			model.addAttribute("mmList", mMap);
			model.addAttribute("vo",vo);
		}else{
			try {
				throw new Exception("分类编码为空！");
			} catch (Exception e) {
				logger.error("分类编码为空：",e);
			}
		}
		return BASE_PATH+"taobaoCat_prop";
	}
	
	@RequestMapping("/updateCatDetail")
	public String updateCatDetail(ModelMap model,TaobaoItemCatVo vo){
		if(vo.getCid()!=null&&!"".equals(vo.getCid())){
			Map<TaobaoItemCatPropVo,List<TaobaoItemCatValueVo>>  propValMap = 
					taobaoCatService.findCatPropDetail(Long.parseLong(vo.getCid()));
			Map<String,List<TaobaoItemCatValueVo>> pMap = null;
			Map<String,Boolean> mMap = null;
			Map<String,String> pidMap = null;
			pMap = new HashMap<String,List<TaobaoItemCatValueVo>>();
			mMap = new HashMap<String, Boolean>();
			pidMap = new HashMap<String,String>();
			for(Map.Entry<TaobaoItemCatPropVo,List<TaobaoItemCatValueVo>> entry : propValMap.entrySet()){
				pMap.put(entry.getKey().getPname(),entry.getValue());
				mMap.put(entry.getKey().getPname(), entry.getKey().getMust());
				pidMap.put(entry.getKey().getPname(),entry.getKey().getPid()+"");
			}
			model.addAttribute("pVList", pMap);
			model.addAttribute("mmList", mMap);
			model.addAttribute("pidMap", pidMap);
			model.addAttribute("vo",vo);
		}else{
			try {
				throw new Exception("分类编码为空！");
			} catch (Exception e) {
				logger.error("分类编码为空：",e);
			}
		}
		return BASE_PATH+"taobaoCat_prop_update";
	}
	
	@ResponseBody
	@RequestMapping(value="/saveTaobaoPropValue",method=RequestMethod.POST)
	public String saveTaobaoPropValue(HttpServletRequest request,String cid){
		JSONObject result = new JSONObject();
		try{
			List<TaobaoItemCatPropValue> catPropValueList =  taobaoCatService.saveTaobaoPropValue(request, cid);
			result.put("catPropValueList", catPropValueList);
			result.put("resultCode","200");
		}catch(BusinessException e){
			result.put("resultCode","500");
			result.put("msg",e.getMessage());
			logger.error(e.getMessage());
			return result.toString();
		}catch(Exception e){
			result.put("resultCode","500");
			result.put("msg","系统异常");
			logger.error(e.getMessage(),e);
			return result.toString();
		}
		return result.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/delPropValue",method=RequestMethod.POST)
	public String delPropValue(Long cid,Long pid,Long vid,HttpServletRequest request){
		JSONObject result = new JSONObject();
		try{
			taobaoCatService.delTaobaoProVal(cid, pid, vid,request);
			result.put("resultCode","200");
		}catch(BusinessException e){
			result.put("resultCode","500");
			result.put("msg",e.getMessage());
			logger.error(e.getMessage());
			return result.toString();
		}catch(Exception e){
			result.put("resultCode","500");
			result.put("msg","系统异常");
			logger.error(e.getMessage(),e);
			return result.toString();
		}
		return result.toString();
	}
	
	@RequestMapping("/exportCatDetail")
	public void exportCatDetail(TaobaoItemCatVo vo,HttpServletRequest request, HttpServletResponse response){
		OutputStream oStream = null;
		try {
			if(vo.getCid()!=null&&!"".equals(vo.getCid())){
				Map<TaobaoItemCatPropVo,List<TaobaoItemCatValueVo>>  propValMap = 
						taobaoCatService.findCatPropDetail(Long.parseLong(vo.getCid()));
				String fileName= vo.getName();
				//去除空格与>
				fileName = fileName.replaceAll("/", "")
						.replaceAll("\\s*","").replaceAll(">", "_");
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename="+
						encodeDownloadAttachmentFilename(request, fileName)+ ".xls");
				if(propValMap!=null&&propValMap.keySet().size()>0){
					String[] headers = new String[propValMap.keySet().size()];
					int i =0;
					for(TaobaoItemCatPropVo pvo : propValMap.keySet()){
						headers[i] = pvo.getPname();
						i++;
					}
					String sheetName = "淘宝分类属性明细";
					oStream = response.getOutputStream();
					ExportExcelTool.exportExcel(sheetName, headers ,oStream, propValMap, "yyyy-MM-dd");
				}
			}else{
				try {
					throw new Exception("分类编码为空！");
				} catch (Exception e) {
					logger.error("分类编码为空：",e);
				}
			}
		} catch (Exception e) {
			logger.error("导出淘宝分类属性明细异常！", e);
		}finally {
			if(oStream != null) {
				try {
					oStream.close();
				} catch (IOException e) {
					logger.error("IO流关闭异常！", e);
				}
			}
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/checkTaobaoCat",method=RequestMethod.POST)
	public String checkTaobaoCat(HttpServletRequest request,@RequestParam String ids){
		List<TaobaoItemCatParentVo> result = 
				taobaoCatService.checkTaobaoCat(ids.split(","));
		List<TaobaoItemCatParentVo> error = new ArrayList<TaobaoItemCatParentVo>();	//不能删除的，有子节点
		Gson gson = new Gson();
		for(TaobaoItemCatParentVo entry : result){
			if(entry.getIsParent()){
				error.add(entry);
			}
		}
		if(error.size()<=0){	//可批量删除
			try{
				taobaoCatService.deleteTaobaoCat(ids.split(","),request);
				return "{'result':'1'}";
			}catch(Exception e){
				logger.error("批量删除发生淘宝分类失败！", e);
				return "{'result':'-1'}";
			}
		}else{					//分类存在子节点，不可删除
			String json = gson.toJson(error);
			return "{'result':'0','reason':"+json+"}";
		}
	}
	 @Resource
	 private IMerchantOperationLogService merchantOperationLogService;
	@ResponseBody
	@RequestMapping("/addTaobaoCat")
	public String addTaobaoCat(HttpServletRequest request,@RequestParam String cids){
		JSONObject result = new JSONObject();
		try {
			String loginName = GetSessionUtil.getSystemUser(request).getLoginName();
			HttpClient client = new DefaultHttpClient();
			HttpGet method = new HttpGet("http://"+settings.getKaidianIp()+":"+settings.getKaidianPort()
					+"/taobao/toTaobaoGetCatPropData.sc?cids="+cids+"&operater="+loginName);
			HttpResponse response = client.execute(method);
			HttpEntity entity = response.getEntity();
			int status = 0;
			if(response != null && response.getStatusLine() != null){
				status = response.getStatusLine().getStatusCode();
				result.put("resultCode", status);
				if(status==200){
					merchantOperationLogService.addMerchantOperationLog("TAOBAO_CAT_ADD",  OperationType.TAOBAO_CAT, "新增淘宝分类 cids:"+cids + " 成功" , request);
					return EntityUtils.toString(entity);
				}else if(status==500){
					result.put("msg", "参数异常，请找优购系统支持人员！");
				}else if(status==404){
					result.put("msg", "页面没找到！");
				}else if(status==403){
					result.put("msg", "您没权限访问！");
				}
			}
			return result.toString();
		}catch(Exception ex){
			result.put("resultCode", "500");
			result.put("msg", "参数异常，请找优购系统支持人员！");
			logger.error("参数异常！",ex);
			return result.toString();
		}
	}
	
	/**
	 * 编码中文文件名称(解决下载文件弹出窗体中文乱码问题)
	 * 
	 * @param request
	 * @param filename
	 * @return String
	 * @throws Exception
	 */
	private String encodeDownloadAttachmentFilename(HttpServletRequest request, String filename) throws Exception {
		return StringUtils.indexOf(request.getHeader("User-Agent"), "MSIE") != -1 ? URLEncoder.encode(filename, "UTF-8") : new String(filename.getBytes("UTF-8"), "ISO-8859-1");
	}

}
