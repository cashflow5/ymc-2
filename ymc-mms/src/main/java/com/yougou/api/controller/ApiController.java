package com.yougou.api.controller;

import java.io.OutputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.belle.infrastructure.constant.Constant;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.mongodb.DBObject;
import com.yougou.api.constant.BitWeight;
import com.yougou.api.model.pojo.Api;
import com.yougou.api.model.pojo.ApiLog;
import com.yougou.api.service.IApiCategoryService;
import com.yougou.api.service.IApiImplementorService;
import com.yougou.api.service.IApiService;
import com.yougou.api.service.IApiVersionService;
import com.yougou.api.web.servlet.AbstractFilter;

/**
 * API控制器
 * 
 * @author yang.mq
 *
 */
@Controller
@RequestMapping("/openapimgt/api")
public class ApiController {

	@Resource
	private IApiService apiService;
	
	@Resource
	private IApiVersionService apiVersionService;
	
	@Resource
	private IApiCategoryService apiCategoryService;

	@Resource
	private IApiImplementorService apiImplementorService;

	@Resource(name = "ApplicationProgramInterfaceDispatcher")
	private AbstractFilter abstractFilter;
	
	@RequestMapping("/updatecache")
	public void updateApiCache(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			SystemmgtUser systemmgtUser = (SystemmgtUser) request.getSession().getAttribute(Constant.LOGIN_SYSTEM_USER);
			if (systemmgtUser != null) {
				abstractFilter.onInitFilterSet();
				response.getWriter().print(true);
			} else {
				response.getWriter().print(false);
			}
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}
	
	@RequestMapping("/delete")
	public void deleteApi(String id, HttpServletResponse response) throws Exception {
		try {
			apiService.deleteApiById(id);
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}
	
	@RequestMapping("/saveorupdate")
	public void saveOrUpdateApi(Api api, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			SystemmgtUser systemmgtUser = (SystemmgtUser) request.getSession().getAttribute(Constant.LOGIN_SYSTEM_USER);
			if (StringUtils.isBlank(api.getId())) {
				api.setId(null);
				api.setCreator(systemmgtUser.getLoginName());
				api.setCreated(new Date());
				apiService.saveApi(api);
			} else {
				api.setModifier(systemmgtUser.getLoginName());
				api.setModified(new Date());
				apiService.updateApi(api);
			}
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}
	
	@RequestMapping("/view/inputparam")
	public ModelAndView viewApiInputParam(String id) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("api", apiService.getApiById(id));
		return new ModelAndView("yitiansystem/merchant/apimgt/view_api_input_param", resultMap);
	}

	@RequestMapping("/view/outputparam")
	public ModelAndView viewApiOutputParam(String id) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("api", apiService.getApiById(id));
		return new ModelAndView("yitiansystem/merchant/apimgt/view_api_output_param", resultMap);
	}
	
	@RequestMapping("/view/validator")
	public ModelAndView viewApiValidator(String id) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("api", apiService.getApiById(id));
		return new ModelAndView("yitiansystem/merchant/apimgt/view_api_validator", resultMap);
	}
	
	@RequestMapping("/view/interceptor")
	public ModelAndView viewApiInterceptor(String id) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("api", apiService.getApiById(id));
		return new ModelAndView("yitiansystem/merchant/apimgt/view_api_interceptor", resultMap);
	}
	
	@RequestMapping("/view/example")
	public ModelAndView viewApiExample(String id) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("api", apiService.getApiById(id));
		return new ModelAndView("yitiansystem/merchant/apimgt/view_api_example", resultMap);
	}
	
	@RequestMapping("/view/error")
	public ModelAndView viewApiError(String id) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("api", apiService.getApiById(id));
		return new ModelAndView("yitiansystem/merchant/apimgt/view_api_error", resultMap);
	}
	
	@RequestMapping("/view")
	public ModelAndView viewApi(String id) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Api api = null;
		if (StringUtils.isNotBlank(id)) {
			api = apiService.getApiById(id);
		}
		resultMap.put("api", api);
		resultMap.put("bitWeights", BitWeight.values());
		resultMap.put("apiVersions", apiVersionService.queryAllApiVersion());
		resultMap.put("apiCategorys", apiCategoryService.queryAllApiCategory());
		resultMap.put("apiImplementors", apiImplementorService.queryAllApiImplementor());
		return new ModelAndView("yitiansystem/merchant/apimgt/view_api", resultMap);
	}
	
	@RequestMapping("/prelist")
	public ModelAndView preListApi() throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("bitWeights", BitWeight.values());
		resultMap.put("apiVersions", apiVersionService.queryAllApiVersion());
		resultMap.put("apiCategorys", apiCategoryService.queryAllApiCategory());
		resultMap.put("apiImplementors", apiImplementorService.queryAllApiImplementor());
		return new ModelAndView("yitiansystem/merchant/apimgt/list_api", resultMap);
	}
	
	@RequestMapping("/list")
	public ModelAndView listApi(Api api, Query query) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		DetachedCriteria criteria = DetachedCriteria.forClass(Api.class);
		criteria.addOrder(Order.desc("created"));
		if (StringUtils.isNotBlank(api.getApiCode())) {
			criteria.add(Restrictions.eq("apiCode", api.getApiCode()));
		}
		if (StringUtils.isNotBlank(api.getApiMethod())) {
			criteria.add(Restrictions.like("apiMethod", api.getApiMethod(), MatchMode.ANYWHERE));
		}
		if (api.getApiWeight() != null) {
			criteria.add(Restrictions.sqlRestriction("api_weight & ? = ?", new Object[] { api.getApiWeight(), api.getApiWeight() }, new Type[] { Hibernate.LONG, Hibernate.LONG }));
		}
		if (api.getApiCategory() != null && StringUtils.isNotBlank(api.getApiCategory().getId())) {
			criteria.add(Restrictions.eq("apiCategory.id", api.getApiCategory().getId()));
		}
		if (api.getApiVersion() != null && StringUtils.isNotBlank(api.getApiVersion().getId())) {
			criteria.add(Restrictions.eq("apiVersion.id", api.getApiVersion().getId()));
		}
		if (api.getApiImplementor() != null && StringUtils.isNotBlank(api.getApiImplementor().getId())) {
			criteria.add(Restrictions.eq("apiImplementor.id", api.getApiImplementor().getId()));
		}
		
		PageFinder<Api> pageFinder = apiService.queryApi(criteria, query);
		resultMap.put("pageFinder", pageFinder);
		resultMap.put("api", api);
		resultMap.put("bitWeights", BitWeight.values());
		resultMap.put("apiVersions", apiVersionService.queryAllApiVersion());
		resultMap.put("apiCategorys", apiCategoryService.queryAllApiCategory());
		resultMap.put("apiImplementors", apiImplementorService.queryAllApiImplementor());
		return new ModelAndView("yitiansystem/merchant/apimgt/list_api", resultMap);
	}
	
	@RequestMapping("/log/prelist")
	public ModelAndView preListApiLog() throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -7);
		//cal.set(Calendar.HOUR, 0);
		//cal.set(Calendar.MINUTE, 0);
		//cal.set(Calendar.SECOND, 0);

		// 分组 API
		Map<String, List<Api>> apiListMap = new HashMap<String, List<Api>>();
		List<Api> apis = apiService.getAllApi();
		for (Api api : apis) {
			String key = api.getApiCategory().getCategoryName();
			List<Api> list = apiListMap.get(key);
			if (list == null) {
				list = new ArrayList<Api>();
				apiListMap.put(key, list);
			}
			list.add(api);
		}
		
		resultMap.put("apiListMap", apiListMap);
		resultMap.put("fromOperated", cal.getTime());
		return new ModelAndView("yitiansystem/merchant/apimgt/list_api_log", resultMap);
	}
	
	@RequestMapping("/log/list")
	public ModelAndView listApiLog(ApiLog apiLog, Date fromOperated, Date toOperated, String apiInputParam, String apiInputParamValue, Query query) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		// 分组 API
		Map<String, List<Api>> apiListMap = new HashMap<String, List<Api>>();
		List<Api> apis = apiService.getAllApi();
		for (Api api : apis) {
			String key = api.getApiCategory().getCategoryName();
			List<Api> list = apiListMap.get(key);
			if (list == null) {
				list = new ArrayList<Api>();
				apiListMap.put(key, list);
			}
			list.add(api);
		}
		
		PageFinder<DBObject> pageFinder = apiService.queryApiLogFromMongoDB(apiLog, fromOperated, toOperated, apiInputParam, apiInputParamValue, query);
		resultMap.put("pageFinder", pageFinder);
		resultMap.put("apiListMap", apiListMap);
		resultMap.put("fromOperated", fromOperated);
		resultMap.put("toOperated", toOperated);
		resultMap.put("apiInputParam", apiInputParam);
		resultMap.put("apiInputParamValue", apiInputParamValue);
		
		if (CollectionUtils.isNotEmpty(pageFinder.getData())) {
			for (DBObject o : pageFinder.getData()) {
				if (o.containsField("operationParameters")) {
					o.put("operationParametersStr", o.get("operationParameters").toString());
				}
			}
		}
		
		return new ModelAndView("yitiansystem/merchant/apimgt/list_api_log", resultMap);
	}
	
	@RequestMapping("/status")
	public void updateApiStatus(String id, String isEnable, HttpServletResponse response) throws Exception {
		try {
			apiService.updateApiStatus(id, isEnable);
			response.getWriter().print(true);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().print(ex.getMessage());
		}
	}
	
	@RequestMapping("/log/export")
	public void exportApiLog(ApiLog apiLog, Date fromOperated, Date toOperated, String apiInputParam, String apiInputParamValue, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = listApiLog(apiLog, fromOperated, toOperated, apiInputParam, apiInputParamValue, new Query(Short.MAX_VALUE));
		PageFinder<DBObject> pageFinder = PageFinder.class.cast(modelAndView.getModel().get("pageFinder"));
		if (pageFinder != null && CollectionUtils.isNotEmpty(pageFinder.getData())) {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("API日志");
			int rowInd = 0, colInd = 0;
			HSSFRow row = sheet.createRow(rowInd++);
			row.createCell(colInd++).setCellValue("AppKey");
			row.createCell(colInd++).setCellValue("请求IP");
			row.createCell(colInd++).setCellValue("请求时间戳");
			row.createCell(colInd++).setCellValue("API版本");
			row.createCell(colInd++).setCellValue("API");
			row.createCell(colInd++).setCellValue("Format");
			row.createCell(colInd++).setCellValue("记录时间");
			row.createCell(colInd++).setCellValue("请求参数");
			row.createCell(colInd++).setCellValue("响应结果");
			for (DBObject dbObject : pageFinder.getData()) {
				colInd = 0;
				row = sheet.createRow(rowInd++);
				DBObject operationParameters = DBObject.class.cast(dbObject.get("operationParameters"));
				row.createCell(colInd++).setCellValue(ObjectUtils.toString(operationParameters.get("app_key")));
				row.createCell(colInd++).setCellValue(ObjectUtils.toString(dbObject.get("clientIp")));
				row.createCell(colInd++).setCellValue(ObjectUtils.toString(operationParameters.get("timestamp")));
				row.createCell(colInd++).setCellValue(ObjectUtils.defaultIfNull(operationParameters.get("app_version"), "1.0").toString());
				row.createCell(colInd++).setCellValue(ObjectUtils.toString(operationParameters.get("method")));
				row.createCell(colInd++).setCellValue(ObjectUtils.defaultIfNull(operationParameters.get("format"), "xml").toString());
				row.createCell(colInd++).setCellValue(ObjectUtils.toString(dbObject.get("operated")));
				apiInputParam = ObjectUtils.toString(dbObject.get("operationParametersStr"));
				row.createCell(colInd++).setCellValue(apiInputParam.substring(0, Math.min(apiInputParam.length(), Byte.MAX_VALUE)) + "...");
				apiInputParam = ObjectUtils.toString(dbObject.get("operationResult"));
				row.createCell(colInd++).setCellValue(apiInputParam.substring(0, Math.min(apiInputParam.length(), Byte.MAX_VALUE)) + "...");
			}
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", MessageFormat.format("attachment; filename={0}.xls", System.currentTimeMillis()));
			OutputStream os = null;
			try {
				os = response.getOutputStream();
				workbook.write(os);
			} finally {
				os.flush();
				os.close();
			}
		}
	}
	
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}
}
