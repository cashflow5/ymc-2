/**
 * 
 */
package com.yougou.kaidian.stock.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.yougou.kaidian.commodity.service.ICommodityService;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.commodity.pojo.Brand;
import com.yougou.kaidian.common.commodity.pojo.Cat;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.common.util.DateUtil2;
import com.yougou.kaidian.common.util.StringUtil;
import com.yougou.kaidian.framework.util.SessionUtil;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.stock.common.ExportHelper;
import com.yougou.kaidian.stock.common.util.ExcelUtils;
import com.yougou.kaidian.stock.model.vo.InventoryHDQueryVO;
import com.yougou.kaidian.stock.model.vo.InventoryVo;
import com.yougou.kaidian.stock.model.vo.KeyValueVo;
import com.yougou.kaidian.stock.model.vo.ResultMsg;
import com.yougou.kaidian.stock.service.IStockService;
import com.yougou.kaidian.stock.service.IWarehouseService;

/**
 * 商家中心 - 库存模块
 * 
 * @author huang.tao
 *
 */
@Controller
@RequestMapping("/wms/supplyStockInput")
public class StockController {
	
	private final static Logger logger = LoggerFactory.getLogger(StockController.class);
	
	@Resource
	private IWarehouseService warehouseService;
	@Resource
	private IStockService stockService;
	@Resource
	private ICommodityService commodityService;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	private static final String STOCK_TEMPLATE_NAME = "supplyStock.xls";
	
	/**
	 * 供应商库存查询
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/querySupplyGenStock")
	public ModelAndView querySupplyGenStock(InventoryHDQueryVO queryVO, Query query, ModelMap modelMap, HttpServletRequest request) throws Exception {
		List<KeyValueVo> years = null;
		List<Brand> lstBrand = null;
		List<Cat> lstCat = null;
		PageFinder<InventoryVo> pageFinder = null;
		String merchantCode = null;
        try {
            merchantCode = YmcThreadLocalHolder.getMerchantCode();
            String warehouseCode = SessionUtil.getWarehouseCodeFromSession(request);
            queryVO.setMerchantCode(merchantCode);
            queryVO.setWarehouseCode(warehouseCode);
            years = stockService.getYearsList();// 获取年份
            lstBrand = commodityService.queryBrandList(merchantCode);// 获取品牌
            lstCat = commodityService.queryCatList(merchantCode, "");// 获取分类

            pageFinder = stockService.queryInventoryNew(queryVO, query);
            //获取安全库存，只有设定了安全库存，页面才有设置项
            if (StringUtils.isNotEmpty(warehouseCode)) {
                modelMap.put("safeStockQuantity", stockService.getSafeStockQuantity(merchantCode));
            }
        } catch (Exception e) {
			logger.error("查询供应商库存异常.", e);
			pageFinder = new PageFinder<InventoryVo>(query.getPage(), query.getPageSize(), 0, null);
		}

		modelMap.put("pageFinder", pageFinder);
		modelMap.put("years", years);
		modelMap.put("lstBrand", lstBrand);
		modelMap.put("lstCat", lstCat);
		modelMap.put("queryVO", queryVO);
		
		Map<String, Object> mapMerchantUser = (Map<String, Object>) request.getSession().getAttribute("merchantUsers");
		logger.info("Merchant user :{} querySupplyGenStock.", MapUtils.getString(mapMerchantUser, "login_name"));
		//假如是优购管理员,将选出的商家权限选出
		if(mapMerchantUser.get("is_yougou_admin")!=null&&"1".equals(mapMerchantUser.get("is_yougou_admin").toString())){
			modelMap.put("isYougouAdmin", 1);
		}
    	Boolean result = (Boolean)redisTemplate.opsForValue().get(CacheConstant.C_MERCHANT_SET_SAFE_STOCK+":"+merchantCode);
    	String isSetSafeStock = (String)redisTemplate.opsForValue().get(CacheConstant.C_USER_ENTER_METHOD+":updateSafeStockQuantity:"+merchantCode);
    	//redisTemplate.delete(CacheConstant.C_USER_ENTER_METHOD+":updateSafeStockQuantity:"+merchantCode);
    	if(isSetSafeStock!=null){
    		//执行过设置安全库存
    		if(result!=null){
    			//运行完设置安全库存
    			if(result){
            		//更新成功
        			modelMap.put("setSafeStockResult", "true");
            	}else{
            		//设置安全库存成功，但是批量更新库存失败，请联系优购支持人员
            		modelMap.put("setSafeStockResult", "false");
            	}
    			//缓存清除
        		redisTemplate.delete(CacheConstant.C_MERCHANT_SET_SAFE_STOCK+":"+merchantCode);
        		redisTemplate.delete(CacheConstant.C_USER_ENTER_METHOD+":updateSafeStockQuantity:"+merchantCode);
        		modelMap.put("isSetSafeStock", "loaded");
    		}else{
    			//正在执行设置安全库存
    			modelMap.put("isSetSafeStock", "loading");
    		}
    	}else{
    		//未执行设置安全库存
			modelMap.put("isSetSafeStock", "noenter");
    	}
		return new ModelAndView("manage/wms/supplyStockInput/querySupplyStock", modelMap);
	}
	
	/**
	 * 选择导出库存
	 * 
	 * @param queryVO
	 * @param query
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/doGenStockExport")
	public void doGenStockExport(InventoryHDQueryVO queryVO, Query query, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			long start = System.currentTimeMillis();
			String merchantCode = YmcThreadLocalHolder.getMerchantCode();
			queryVO.setMerchantCode(merchantCode);
			queryVO.setWarehouseCode(SessionUtil.getWarehouseCodeFromSession(request));
			List<InventoryVo> inventoryList = stockService.exportInventoryNew(queryVO);
			List<Object[]> list = new ArrayList<Object[]>(inventoryList.size());
			Object[] _obj = null;
			for (InventoryVo inventory : inventoryList) {
				_obj = new Object[13];
				_obj[0] = inventory.getGoodsName();
				_obj[1] = inventory.getProductNo();
				_obj[2] = inventory.getThirdPartyCode();
				_obj[3] = inventory.getSupplierCode();
				_obj[4] = inventory.getSpecification();
				_obj[5] = inventory.getBrandName();
				_obj[6] = inventory.getCatStructName();
				_obj[7] = inventory.getUnit();
				_obj[8] = inventory.getQuantity();
				_obj[9] = inventory.getPreQuantity();
				_obj[10] = inventory.getSaleQuantity();
				_obj[11] = inventory.getCostPrice();
				_obj[12] = inventory.getQtyCost();
				list.add(_obj);
			}
			
			ExportHelper exportHelper = new ExportHelper();
			String sheetName = DateUtil2.formatDateByFormat(new Date(), "yyyy-MM-dd");

			String fileRealPath = request.getSession().getServletContext().getRealPath("manage/wms/download/");
			// 模板路径
			String templatePath = fileRealPath + "/inventory.xls";
			// {(开始行),(总列数)}
			int[] paras = { 1, 13 };
			// 数值列 {0,1,2,3,....}
			int[] numCol = { 9, 10,11,12 };
			// "合计"标题 {(开始列),(结束列)}
			int[] amountCol = null;
			// 指定索引值(0,3)....
			Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
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

			exportHelper.doExport(list, templatePath, "inventory.xls", sheetName, null, paras, numCol, amountCol, indexMap, false, response);
			long end = System.currentTimeMillis();
			logger.warn("库存导出耗时{}毫秒，导出数据量:{}",new Object[]{end-start,inventoryList.size()});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 下载库存模板
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/exportTemplate")
	public void exportTemplate(HttpServletRequest request, HttpServletResponse response) {
		String realFile = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/") + "/manage/wms/download/" + STOCK_TEMPLATE_NAME;
		File excelName = new File(realFile);
		try {
			String fileName = URLEncoder.encode(STOCK_TEMPLATE_NAME, "UTF-8");
			response.setHeader("Content-disposition", "attachment; filename=" + fileName);
			response.setContentLength((int) excelName.length());
			response.setContentType("application/x-msdownload");
			byte[] buffer = new byte[4096];
			int i = 0;

			FileInputStream fis = new FileInputStream(excelName);
			while ((i = fis.read(buffer, 0, 4096)) > 0) {
				response.getOutputStream().write(buffer, 0, i);
			}
			response.flushBuffer();
			fis.close();
		} catch (Exception e) {
			logger.error("导出库存模板异常.", e);
		}
	}
	
	/**
	 * 下载库存模板(在售商品模板)
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/loacdExport")
	public void doYgStockExport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//商家编码
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		try {
			List<Object[]> list = stockService.queryAllProductStocks(merchantCode);
			
			logger.info("商家：{} | 下载在售商品库存模板, 总记录数：{}.", new Object[] { merchantCode, list.size() });
			ExportHelper exportHelper = new ExportHelper();
			String sheetName = DateUtil2.formatDateByFormat(new Date(), "yyyy-MM-dd");
			
			// 模板路径
			String templatePath = request.getSession().getServletContext().getRealPath("manage/wms/download/supplyStock.xls");
			// {(开始行),(总列数)}
			int[] paras = { 1, 3 };
			// 数值列 {0,1,2,3,....}
			int[] numCol = {};
			// "合计"标题 {(开始列),(结束列)}
			int[] amountCol = null;
			// 指定索引值(0,3)....
			Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
			indexMap.put(0, 0);
			indexMap.put(1, 1);
			indexMap.put(2, 2);
			
			exportHelper.doExport(list, templatePath, "supplyStock.xls", sheetName, null, paras, numCol, amountCol, indexMap, false, response);
		} catch (Exception e) {
			logger.error("商家：{} | 下载在售商品库存模板异常.", merchantCode);
			e.printStackTrace();
		}
	}
	
	/**
	 * 批量导入商品库存
	 * 
	 * @param request
	 * @param multipartRequest
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/excelSupplyStockInput")
	public void excelSupplyStockInput(HttpServletRequest request, DefaultMultipartHttpServletRequest multipartRequest, HttpServletResponse response){
		//执行进度
		int progress = 0;
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		String wareHouseCode = SessionUtil.getWarehouseCodeFromSession(request);
		this.redisTemplate.opsForHash().put(CacheConstant.C_STOCK_PROGRESS_BAR_KEY, merchantCode, progress);
		response.setContentType("text/html;charset=utf-8");
		ResultMsg resultMsg = new ResultMsg();
		resultMsg.setSuccess(true);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			MultipartFile multipartFile = multipartRequest.getFile("excelFile");

			List<Object[]> paramList = new ArrayList<Object[]>();

			if (multipartFile != null && !multipartFile.isEmpty() && multipartFile.getSize() > 2000000) {
				logger.error("商家：{} | 导入库存失败(文件不能超过2M).", merchantCode);
				resultMsg.setMsg("上传失败：文件大小不能超过2M");
				out.write(JSONObject.fromObject(resultMsg).toString());
			}

			ExcelUtils excelData = new ExcelUtils(multipartFile.getInputStream());
			paramList = excelData.getNewDatasInSheet(0, 0, 4);
			// 删除第一行 (标题栏)
			paramList.remove(0);
			logger.info("商家: {} | 成功导入库存文件：{}, 总数：{}.", new Object[] { merchantCode, multipartFile.getOriginalFilename(), paramList.size() });
			// 执行进度
			progress = 20;
			this.redisTemplate.opsForHash().put(CacheConstant.C_STOCK_PROGRESS_BAR_KEY, merchantCode, progress);

			List<Object> errorList = null;
			errorList = stockService.batchUpdateStock(merchantCode, wareHouseCode, paramList, progress);
			if (CollectionUtils.isNotEmpty(errorList)) {
				resultMsg.setSuccess(false);
				if (paramList.size() == errorList.size())
					resultMsg.setMsg("导入库存失败, 请按提示修复数据后重新导入！");
				else if (paramList.size() == errorList.size())
					resultMsg.setMsg("导入库存部分失败, 请按提示修复数据后重新导入！");
				// 自动下载错误文件
				inputExcelDate(errorList, request, response);

			}
			if (progress <= 100) {
				this.redisTemplate.opsForHash().put(CacheConstant.C_STOCK_PROGRESS_BAR_KEY, merchantCode, 100);
			}

			out.write(JSONObject.fromObject(resultMsg).toString());
		} catch (IOException e) {
			resultMsg.setMsg("导入库存失败, 请重新导入！");
			 logger.error( MessageFormat.format("商家：{0} | 导入库存时发生异常。", merchantCode),e);
		}catch (Exception e) {
			resultMsg.setMsg("导入库存失败, 请重新导入！");
			
			 logger.error( MessageFormat.format("商家：{0} | 下载导入库存错误数据异常。", merchantCode),e);
		}finally{
			out.close();
		}
	}
	
	/**
	 * 后台获取库存导入进度
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("queryStockImportProgressBar")
	public String getProgressBar(String isflag, HttpServletRequest request) {
		JSONObject jsonObj = new JSONObject();
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		int progress = 0;
		try {
			if ("true".equals(isflag)) {
				this.redisTemplate.opsForHash().put(CacheConstant.C_STOCK_PROGRESS_BAR_KEY, merchantCode, progress);
			} else {
				progress = (Integer) this.redisTemplate.opsForHash().get(CacheConstant.C_STOCK_PROGRESS_BAR_KEY, merchantCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		jsonObj.put("progress", progress);
		jsonObj.put("result", "true");
		
		return jsonObj.toString();
	}
	
	/**
	 * 导出错误数据
	 * 
	 * @param objList
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ModelAndView inputExcelDate(List<Object> objList, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			ExportHelper exportHelper = new ExportHelper();
			String sheetName = DateUtil2.formatDateByFormat(new Date(), "yyyy-MM-dd");
			String fileRealPath = request.getSession().getServletContext().getRealPath("manage/wms/download/");
			// 模板路径
			String templatePath = fileRealPath + "/errSupplyStock.xls";

			// {(开始行),(总列数)}
			int[] paras = { 1, 4 };
			// 数值列 {0,1,2,3,....}
			int[] numCol = {};
			// "合计"标题 {(开始列),(结束列)}
			int[] amountCol = null;
			// 指定索引值(0,3)....
			Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
			for (int i = 0; i < 4; i++) {
				indexMap.put(i, i);
			}
			exportHelper.doExport(objList, templatePath, "库存导入出错记录.xls", sheetName, null, paras, numCol, amountCol, indexMap, false, response);
		} catch (Exception e) {
			logger.error( MessageFormat.format("商家：{0} | 导入错误数据异常。", YmcThreadLocalHolder.getMerchantCode()),e);
			e.printStackTrace();
		}
		return null;
	}
	
	/***
	 * 转到导入页
	 */
	@RequestMapping("inputSupplyStockExcel")
	public ModelAndView inputSupplyStockExcel() throws Exception {
		ModelMap modelMap = new ModelMap();
		modelMap.put("tagTab", "stock");// 标识
		return new ModelAndView("manage/wms/supplyStockInput/inputSupplyStockExcel", modelMap);
	}

	@RequestMapping("/preExactUpdateInventory")
	public ModelAndView preExactUpdateInventory() throws Exception {
		ModelMap modelMap = new ModelMap();
		modelMap.put("tagTab", "stock");// 标识
		return new ModelAndView("manage/wms/exact_update_inventory", modelMap);
	}
	
	@RequestMapping("/queryWaitingUpdateInventory")
	public void queryWaitingUpdateInventory(String thirdPartyCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		if (StringUtils.isNotBlank(thirdPartyCode) && StringUtils.isNotBlank(merchantCode)) {
			String warehouseCode = warehouseService.queryWarehouseCodeByMerchantCode(merchantCode);
			Map<String, Object> resultMap = null;
			if (StringUtils.isBlank(warehouseCode))
				throw new IllegalArgumentException("未设置仓库编码");
			resultMap = stockService.queryMerchantInventoryByThirdPartyCode(merchantCode, thirdPartyCode, warehouseCode);
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().print(JSONObject.fromObject(resultMap).toString());
			response.getWriter().flush();
		}
	}
	
	@RequestMapping("/exactUpdateInventory")
	public void exactUpdateInventory(String commodityCode, Integer quantity, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			response.setContentType("text/html; charset=utf-8");
			String merchantCode = YmcThreadLocalHolder.getMerchantCode();
			//获取该货品的实际安全库存数，更新给wms的库存要减去该数目
            //Integer safeStockQuantity = stockService.updateSafeStockQuantity(merchantCode, SessionUtil.getWarehouseCodeFromSession(request), commodityCode, quantity);
            //Map<String, Date> result = inventoryDomainService.updateInventoryForMerchant(commodityCode, warehouseService.queryWarehouseCodeByMerchantCode(merchantCode), quantity
                    //- safeStockQuantity, NumberUtils.INTEGER_ZERO);
            String warehouseCode = SessionUtil.getWarehouseCodeFromSession(request);
            warehouseCode = warehouseService.queryWarehouseCodeByMerchantCode(merchantCode);
            Map<String, Object> result =  stockService.updateInventoryForMerchant(merchantCode,warehouseCode,commodityCode,quantity);
			if(result!=null && !(result.containsKey(null))){
				logger.info("商家：{}| 货品编码：{}, 修改库存: {} 。 操作结果：{}. ", new Object[]{merchantCode, commodityCode, quantity, null != MapUtils.getObject(result, commodityCode) ? "success" : "failure"});
				Map<String, Object> resultMap = new HashMap<String, Object>();
				resultMap.put("result", Boolean.TRUE);
				resultMap.put("safeStockQuantity", result.get("safeStockQuantity"));
				response.getWriter().print(JSONObject.fromObject(resultMap).toString());
				response.getWriter().flush();
			}else{
				logger.error("商家：{}| 货品编码：{}, 修改库存: {} 。 操作结果：{}. ", new Object[]{merchantCode, commodityCode, quantity, null != MapUtils.getObject(result, commodityCode) ? "success" : "failure"});
				logger.error("更新库存失败：{}",new Object[]{String.valueOf(result.get(null))});
				response.getWriter().print("更新库存失败，货品编码（"+commodityCode+"）"+String.valueOf(result.get(null)));
				response.getWriter().flush();
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			response.getWriter().print("更新库存失败!");
			response.getWriter().flush();
		}
	}
	
	@ResponseBody
	@RequestMapping("/getChileCat")
	public String getChileCat(ModelMap modelMap, HttpServletRequest request, String structName) {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		
		List<Cat> lstCat = commodityService.queryCatList(merchantCode, structName);
		JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(new PropertyFilter() {
			@Override
			public boolean apply(Object source, String name, Object value) {
				if (name.equals("id") || name.equals("catName") || name.equals("structName") || name.equals("no")) {
					return false;
				}
				return true;
			}
		});
		JSONArray jsonArray = JSONArray.fromObject(lstCat, config);
		return jsonArray.toString();
	}
	
	@RequestMapping("/queryOrderTipsStockUnder")
	public ModelAndView queryOrderTipsStockUnder(Query query, HttpServletRequest request) throws Exception {
		InventoryHDQueryVO queryVo = new InventoryHDQueryVO();
		
		// 获取商家编码
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		String wareHouseCode = SessionUtil.getWarehouseCodeFromSession(request);
		Set<String> stockTips = null;
		try {
			stockTips=stockService.getStockTips(merchantCode,wareHouseCode);
		} catch (Exception e) {
			logger.error("query订单提醒相关信息异常.", e);
		}
		if (CollectionUtils.isNotEmpty(stockTips)) {
			queryVo.setProductList(StringUtil.setToString(stockTips, ","));
		}else{
			queryVo.setProductList("no");
		}
		
		return this.querySupplyGenStock(queryVo, query, new ModelMap(), request);
	}
	
	@ResponseBody
    @RequestMapping("/updateSafeStockQuantity")
    public String updateSafeStockQuantity(final Integer quantity, HttpServletRequest request, HttpServletResponse response) throws Exception {
		final String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		//判断是否上一次执行设置安全库存正在执行
		Boolean result = (Boolean)redisTemplate.opsForValue().get(CacheConstant.C_MERCHANT_SET_SAFE_STOCK+":"+merchantCode);
    	String isSetSafeStock = (String)redisTemplate.opsForValue().get(CacheConstant.C_USER_ENTER_METHOD+":updateSafeStockQuantity:"+merchantCode);
		if(isSetSafeStock!=null){
    		//执行过设置安全库存
    		if(result==null){
    			//未执行完
    			return "loading";
    		}
		}
    	final String warehouseCode = SessionUtil.getWarehouseCodeFromSession(request);
    	ExecutorService threadPool = null;
    	try{
    		// updated by zhangfeng 2016.5.22 直接异步线程处理，不用每次创建单个线程池处理
    		/*threadPool = Executors.newFixedThreadPool(1);// 线程池
    	    threadPool.execute(new Runnable() {
    	    	Boolean result = null;
    			@Override
    			public void run() {
    				try{
    					result = stockService.updateSafeStockQuantityForSet(merchantCode, warehouseCode, quantity);
    				}catch(Exception e){
    					result = false;
    					logger.error("设置安全库存发生错误：",e);
    				}finally{
    					redisTemplate.opsForValue().set(CacheConstant.C_MERCHANT_SET_SAFE_STOCK+":"+merchantCode, result);
    				}
    			}
    		});*/
    		Runnable run = new Runnable() {
    	    	Boolean result = null;
    			@Override
    			public void run() {
    				try{
    					logger.warn("线程{},开始修改安全库存...", Thread.currentThread().getName());
    					result = stockService.updateSafeStockQuantityForSet(merchantCode, warehouseCode, quantity);
    					logger.warn("线程{},结束修改安全库存...", Thread.currentThread().getName());
    				}catch(Exception e){
    					result = false;
    					logger.error("设置安全库存发生错误：",e);
    				}finally{
    					redisTemplate.opsForValue().set(CacheConstant.C_MERCHANT_SET_SAFE_STOCK+":"+merchantCode, result);
    				}
    			}
    		};
    		Thread thread = new Thread(run);
    		thread.setName("updateSafeStockQuantity 修改安全库存");
    		thread.start();
    	}catch(Exception e){
    		logger.error("设置安全库存发生错误:",e);
    	}finally{
    		if (threadPool != null) {
				threadPool.shutdown();
				threadPool = null;
			}
    	}
	    //表示已进入该方法，设置过安全库存
	    redisTemplate.opsForValue().set(CacheConstant.C_USER_ENTER_METHOD+":updateSafeStockQuantity:"+merchantCode,"enter");
	    redisTemplate.expire(CacheConstant.C_USER_ENTER_METHOD+":updateSafeStockQuantity:"+merchantCode, 24, TimeUnit.HOURS);
	    //20分钟之内没有设置完，就失效
	    redisTemplate.expire(CacheConstant.C_MERCHANT_SET_SAFE_STOCK+":"+merchantCode, 20, TimeUnit.MINUTES);
        return "success";
    }
    
	@ResponseBody
	@RequestMapping("/getSetSafeStockResult")
	public String getSetSafeStockResult(){
		String isSuccss = "false";
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		Boolean result = (Boolean)redisTemplate.opsForValue().get(CacheConstant.C_MERCHANT_SET_SAFE_STOCK+":"+merchantCode);
		if(result!=null){
			//运行完设置安全库存
			isSuccss = "true";
		}else{
			isSuccss = "false";
		}
		return isSuccss;
	}
	
	@ResponseBody
	@RequestMapping("/stockTips")
	public String stockTips(HttpServletRequest request) {
        JSONObject json=new JSONObject();
        String merchantCode = YmcThreadLocalHolder.getMerchantCode();
        String warehouseCode = SessionUtil.getWarehouseCodeFromSession(request);
        int stockTips=0;
        try {
        	stockTips = stockService.getStockTips(merchantCode, warehouseCode).size();
		} catch (Exception e) {
			
			 logger.error( MessageFormat.format("查询库存小于5提示发生异常.merchantCode:{0},异常信息:", merchantCode),e);
		}
        json.put("stockTips",stockTips);
        return json.toString();
	}
	
	@RequestMapping("/getSafeStock")
	public String getSafeStock(String productNo,
			String safeStockQuantityGe, 
			String safeStockQuantityLe, 
			ModelMap model,
			Query query, HttpServletRequest request){
		if(StringUtils.isBlank(safeStockQuantityGe)){
			safeStockQuantityGe = "0";
		}
		Map<String, Object> mapMerchantUser = (Map<String, Object>) request.getSession().getAttribute("merchantUsers");
		logger.info("Merchant user :{} getSafeStock.", MapUtils.getString(mapMerchantUser, "login_name"));
		if(mapMerchantUser.get("is_yougou_admin")!=null&&
				"1".equals(mapMerchantUser.get("is_yougou_admin").toString())){
			String merchantCode = MapUtils.getString(mapMerchantUser, "merchantCode");
			PageFinder<Map<String,Object>> pageFinder =  stockService.getSafeStock(merchantCode,productNo,
					safeStockQuantityGe,
					safeStockQuantityLe,
					query);
			model.put("productNo", productNo);
			model.put("safeStockQuantityGe", safeStockQuantityGe);
			model.put("safeStockQuantityLe", safeStockQuantityLe);
			Integer quantity = stockService.getSafeStockQuantity(merchantCode);
			model.put("setSafeStockQuantity", null==quantity?0:quantity);
			model.put("pageFinder", pageFinder);
			return "manage/wms/supplyStockInput/querySafeStock";
		}
		return "redirect:/wms/supplyStockInput/querySupplyGenStock.sc";
	}
	
	@ResponseBody
	@RequestMapping("/modifySafeStock")
	public String modifySafeStock(@RequestParam("id") String id,Integer safeStockQuantity,HttpServletRequest request ){
		Map<String, Object> mapMerchantUser = (Map<String, Object>) request.getSession().getAttribute("merchantUsers");
		logger.info("Merchant user :{} modifySafeStock.", MapUtils.getString(mapMerchantUser, "login_name"));
		JSONObject object = new JSONObject();
		if(mapMerchantUser.get("is_yougou_admin")!=null&&
				"1".equals(mapMerchantUser.get("is_yougou_admin").toString())){
			String merchantCode = MapUtils.getString(mapMerchantUser, "merchantCode");
			boolean flag = stockService.updateSafeStock(id,merchantCode,safeStockQuantity);
			object.put("result", flag);
		}else{
			object.put("result", false);
		}
		return object.toString();
	}
	
	@ResponseBody
	@RequestMapping("/delSafeStock")
	public String delSafeStock(@RequestParam("id") String[] id,HttpServletRequest request){
		Map<String, Object> mapMerchantUser = (Map<String, Object>) request.getSession().getAttribute("merchantUsers");
		JSONObject object = new JSONObject();
		logger.info("Merchant user :{} delSafeStock.", MapUtils.getString(mapMerchantUser, "login_name"));
		if(mapMerchantUser.get("is_yougou_admin")!=null&&
				"1".equals(mapMerchantUser.get("is_yougou_admin").toString())){
			boolean flag = stockService.delSafeStock(id);
			object.put("result", flag);
		}else{
			object.put("result", false);
		}
		return object.toString();
	}
}
