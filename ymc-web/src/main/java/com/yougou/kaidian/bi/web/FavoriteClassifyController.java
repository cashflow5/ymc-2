package com.yougou.kaidian.bi.web;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.poi.hssf.util.CellRangeAddress;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.yougou.kaidian.commodity.component.CommodityComponent;
import com.yougou.kaidian.commodity.component.CommodityStatus;
import com.yougou.kaidian.commodity.service.IImageService;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.constant.CacheConstant;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.yougou.kaidian.bi.service.IReportFavoriteService;
import com.yougou.kaidian.common.util.DateUtil2;
import com.yougou.kaidian.common.util.FileFtpUtil;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.kaidian.framework.settings.CommoditySettings;
import com.yougou.kaidian.framework.util.SessionUtil;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.stock.service.IWarehouseService;
import com.yougou.pc.model.category.Category;
import freemarker.ext.beans.BeansWrapper;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
/**
 * 收藏夹功能controller 
 * 
 * @author zhang.wj
 *
 */
@Controller
public class FavoriteClassifyController {
	
	private Logger logger = LoggerFactory.getLogger(FavoriteClassifyController.class);
	
	@Resource
	private IReportFavoriteService iReportFavoriteService;
	
	@Resource
	private CommodityComponent commodityComponent;
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	 @Resource
	private CommoditySettings commoditySettings;
	 
	@Resource
	private IImageService imageService;
	 
	
	 @Resource
	 private IWarehouseService warehouseService;
	
	/**
	 * 初始化收藏夹页面
	 * 
	 * @param summaryOfOperationsVo
	 * @param modelMap
	 * @param request
	 * @return ModelAndView
	 */
	@RequestMapping("favoriteClassifyController/queryFavoriteClassifyInfo")
	public ModelAndView queryFavoriteClassifyInfo(ModelMap modelMap, HttpServletRequest request,Query query) throws Exception {
		//用于查询参数map
		Map<String,Object>  favoriteInfo=new HashMap<String,Object>();
		// 获得当前登录的商家
		Map<String, Object> unionUser = SessionUtil.getUnionUser(request);
		
		//获取用户登录信息
		String loginName =   MapUtils.getString(unionUser, "login_name");
		
		String merchantCode = MapUtils.getString(unionUser, "supplier_code");
		String warehouseCode = warehouseService.queryWarehouseCodeByMerchantCode(merchantCode);
		
		favoriteInfo.put("warehouseCode", warehouseCode);
		favoriteInfo.put("login_name", loginName);
		 
		favoriteInfo.put("merchant_code", merchantCode);
		//把查询值存入map
		toMap(request,favoriteInfo,modelMap);
		
		
		PageFinder<Map<String, Object>> pageFinder=iReportFavoriteService.queryFavoriteClassifyInfo(query, favoriteInfo);
		
		if(!isEmptyForPageFinder(pageFinder)){
			modelMap.put("pageFinder", pageFinder);
		}
		initCommonProperty(modelMap, request);
		List<Map> favoriteClassifyList=iReportFavoriteService.queryFavoriteClassify(favoriteInfo);
		//如果收藏夹为空，初始化收藏夹
		if(favoriteClassifyList==null || favoriteClassifyList.size()<=0){
			initSaveFavoriteClassifyInfo(request);
			favoriteClassifyList=iReportFavoriteService.queryFavoriteClassify(favoriteInfo);
		}
		modelMap.put("favoriteClassifyList",favoriteClassifyList);
		
		return new ModelAndView("manage/report/favoriteClassifyList", modelMap);
	}
	/**
	 * 收藏入口查询
	 * 
	 * @param summaryOfOperationsVo
	 * @param modelMap
	 * @param request
	 * @return ModelAndView
	 */
	@ResponseBody
	@RequestMapping("favoriteClassifyController/queryFavoriteInfo")
	public String queryFavoriteInfo(String  commodity_no,ModelMap modelMap, HttpServletRequest request,Query query) throws Exception {
		//用于查询参数p
		Map<String,Object>  favoriteInfo=new HashMap<String,Object>();
		// 获得当前登录的商家
		Map<String, Object> unionUser = SessionUtil.getUnionUser(request);
		
		//获取用户登录信息
		String loginName =   MapUtils.getString(unionUser, "login_name");
		
		String merchantCode = MapUtils.getString(unionUser, "supplier_code");
		String warehouseCode = warehouseService.queryWarehouseCodeByMerchantCode(merchantCode);
		
		favoriteInfo.put("warehouseCode", warehouseCode);
		favoriteInfo.put("login_name", loginName);
		 
		favoriteInfo.put("merchant_code", merchantCode);
		
		favoriteInfo.put("commodity_no", commodity_no);
		
		
		//把查询值存入map
		toMap(request,favoriteInfo,modelMap);
		List<Map> favoriteClassifyList=iReportFavoriteService.queryFavoriteClassify(favoriteInfo);
		//如果收藏夹为空，初始化收藏夹
		if(favoriteClassifyList==null || favoriteClassifyList.size()<=0){
			initSaveFavoriteClassifyInfo(request);
		}
		
		List<Map<String, Object>>  listMap=iReportFavoriteService.queryFavoriteInfo(favoriteInfo);
		
		Map  hashMap=new HashMap();
		hashMap.put("listMap", listMap);
		return JSONObject.fromObject(hashMap).toString();
	}
	
	/**
	 * 收藏夹删除
	 * @param commodityNo
	 * @param supplierCode
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("favoriteClassifyController/delete")
	public String deleteFavoriteClassify(String favoriteId, HttpServletRequest request) {
		boolean result = false;
		try {
			if(StringUtils.isEmpty(favoriteId)){
				return Boolean.toString(result);
			}
			iReportFavoriteService.deleteFavoriteClassify(favoriteId);
		} catch (Exception ex) {
			logger.error("商家编码：{},收藏夹删除异常：",YmcThreadLocalHolder.getMerchantCode(), ex);
		}
		return Boolean.toString(result);
	}
	/**
	 * 新增收藏夹 
	 * @param favoriteName
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("favoriteClassifyController/save")
	public String saveFavoriteClassify(String favoriteId,String favoriteName, HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean result = false; 
		String id=null;
		try {
				Map<String,String>  mapFavoriteClassify=new HashMap<String,String>();
				
				id= UUIDGenerator.getUUID();
				
				mapFavoriteClassify.put("classify_name", favoriteName);
				// 获得当前登录的商家
				Map<String, Object> unionUser = SessionUtil.getUnionUser(request);
				//获取用户登录信息
				String loginName =   MapUtils.getString(unionUser, "login_name");
				
				String merchantCode = MapUtils.getString(unionUser, "supplier_code");
				
				
				mapFavoriteClassify.put("id",id);
				//获取用户登录信息
				mapFavoriteClassify.put("merchant_code", merchantCode);
				
				//获取用户登录信息
				mapFavoriteClassify.put("login_name", loginName);
				List<Map> favoriteClassifyList=iReportFavoriteService.queryFavoriteClassify(mapFavoriteClassify);
				//判断收藏夹不能超过10个
				if(favoriteClassifyList!=null && favoriteClassifyList.size()>=10 && StringUtils.isBlank(favoriteId)){
					return "-1";
				}
				//通过favoriteId判断新增还是修改
				if(StringUtils.isNotBlank(favoriteId)){
					mapFavoriteClassify.put("id", favoriteId);
					iReportFavoriteService.updateFavoriteClassify(mapFavoriteClassify);
				}else{
					iReportFavoriteService.addFavoriteClassify(mapFavoriteClassify);
				}
				result=true;
	      } catch (Exception e) {
		  }
		
		return id;
	}
	/**
	 * <p>判断数据结果集是否为空</p>
	 * 
	 * @param page
	 * @return 空 :true  | 非空:false
	 */
	private boolean isEmptyForPageFinder(PageFinder<?> page) {
		if (page == null) 
			return true;
		
		if (page.getData() == null) 
			return true;
		
		if (page.getData().size() == 0) 
			return true;

		return false;
	}
	/**
	 * <p>初始化查询条件</p>
	 * <p>包括  [品牌、分类]</p>
	 * 
	 * @param modelMap
	 * @param queryVo
	 */
	private void initCommonProperty(ModelMap modelMap, HttpServletRequest request) {
		modelMap.addAttribute("statics", BeansWrapper.getDefaultInstance().getStaticModels());
		modelMap.addAttribute("commodityUtil", commodityComponent);
		
		commodityComponent.initCommonProperty(modelMap);
	}
	/**
	 * 转换map
	 * @param request
	 * @param favoriteInfo
	 */
	private void toMap(HttpServletRequest request,Map<String,Object>  favoriteInfo,ModelMap modelMap){
		//添加查询参数
		favoriteInfo.put("classifyName", request.getParameter("classifyId"));
		favoriteInfo.put("startTime", request.getParameter("starttime-1"));
		favoriteInfo.put("endTime", request.getParameter("endtime-1"));
		favoriteInfo.put("favorite_classify_id", request.getParameter("favorite_classify_id"));
		if(StringUtils.isNotBlank(request.getParameter("statusName"))){
			favoriteInfo.put("statusName", CommodityStatus.getStatusCode(request.getParameter("statusName"))+"");
		}else{
			favoriteInfo.put("statusName", "");
		}
		favoriteInfo.put("brandNo", request.getParameter("brandNo"));
		favoriteInfo.put("rootCattegory", (request.getParameter("rootCattegory")));
		favoriteInfo.put("secondCategory", request.getParameter("secondCategory"));
		favoriteInfo.put("threeCategory", request.getParameter("threeCategory"));
		if("请输入商品名称或商品编码".equals(request.getParameter("commodityCode"))){
			favoriteInfo.put("commodityCode","");
		}else{
			favoriteInfo.put("commodityCode", request.getParameter("commodityCode"));
		}
		//返回参数
		modelMap.put("classifyName", request.getParameter("classifyId"));
		modelMap.put("starttime", request.getParameter("starttime-1"));
		modelMap.put("endtime", request.getParameter("endtime-1"));
		modelMap.put("statusName", request.getParameter("statusName"));
		modelMap.put("brandNo", request.getParameter("brandNo"));
		modelMap.put("rootCattegory", request.getParameter("rootCattegory"));
		modelMap.put("secondCategory", request.getParameter("secondCategory"));
		modelMap.put("threeCategory", request.getParameter("threeCategory"));
		if(StringUtils.isNotBlank(request.getParameter("favorite_classify_id"))){
			modelMap.put("favorite_classify_id", request.getParameter("favorite_classify_id"));
		}else{
			modelMap.put("favorite_classify_id","");
		}
		
		modelMap.put("commodityCode", request.getParameter("commodityCode"));
	}
	
	@ResponseBody
	@RequestMapping(value = "favoriteClassifyController/exportFavoriteExcel",method = RequestMethod.POST)
	public String exportFavoriteExcel(HttpServletRequest request,
			 HttpServletResponse response,ModelMap modelMap){
		final String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		redisTemplate.delete(CacheConstant.C_EXPORT_FAVORITE_CLASSIFY_KEY+":"+merchantCode);
		redisTemplate.delete(CacheConstant.C_USER_ENTER_METHOD+":exportFavoriteExcel:"+merchantCode);
		redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_FAVORITE_CLASSIFY_KEY+":"+merchantCode, -1.0d);
		//查询参数MAP
		final Map<String,Object>  queryFavorite=new HashMap<String,Object>();
		//把查询参数放入map
		toMap(request, queryFavorite, modelMap);
		// 获得当前登录的商家
		Map<String, Object> unionUser = SessionUtil.getUnionUser(request);
		
		//获取用户登录信息
		String loginName =   MapUtils.getString(unionUser, "login_name");
		
		
		
		queryFavorite.put("login_name", loginName);
		 
		queryFavorite.put("merchant_code", merchantCode);
		
		final HttpServletRequest rq=request;
		final HttpServletResponse rp=response;
		final double progress = 5.0d;

		ExecutorService threadPool = null;
		try{
			// updated by zhangfeng 2016.5.22 直接异步线程处理，不用每次创建单个线程池处理	
			/*threadPool = Executors.newFixedThreadPool(1);// 线程池
			threadPool.execute(new Runnable() {
				Map<String,Object> hashMap = null;
				//进度值默认起始值
				@Override
				public void run() {
					try {
						
					    hashMap = createFavoriteExcel(rq,rp,progress,merchantCode,queryFavorite);
						redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_FAVORITE_CLASSIFY_KEY+":"+merchantCode, 100.0d);
					} catch (Exception e) {
						hashMap = null;
						logger.error("商家编码：{},收藏夹导出商品出现错误：",YmcThreadLocalHolder.getMerchantCode(),e);
						//删除缓存，不再进行处理
						redisTemplate.delete(CacheConstant.C_EXPORT_FAVORITE_CLASSIFY_KEY+":"+merchantCode);
			    		redisTemplate.delete(CacheConstant.C_USER_ENTER_METHOD+":exportFavoriteExcel:"+merchantCode);
						redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_FAVORITE_CLASSIFY_KEY+":"+merchantCode, -1.0d);
					}finally{
						redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_FAVORITE_CLASSIFY_PRODANALYSE+":"+merchantCode, hashMap);
					}
				}
			});*/
			Runnable run = new Runnable() {
				Map<String,Object> hashMap = null;
				//进度值默认起始值
				@Override
				public void run() {
					try {
						logger.warn("线程{}，开始处理数据报表收藏导出....",Thread.currentThread().getName());
					    hashMap = createFavoriteExcel(rq,rp,progress,merchantCode,queryFavorite);
						redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_FAVORITE_CLASSIFY_KEY+":"+merchantCode, 100.0d);
						logger.warn("线程{}，结束处理数据报表收藏导出....",Thread.currentThread().getName());
					} catch (Exception e) {
						hashMap = null;
						logger.error("商家编码：{},收藏夹导出商品出现错误：",YmcThreadLocalHolder.getMerchantCode(),e);
						//删除缓存，不再进行处理
						redisTemplate.delete(CacheConstant.C_EXPORT_FAVORITE_CLASSIFY_KEY+":"+merchantCode);
			    		redisTemplate.delete(CacheConstant.C_USER_ENTER_METHOD+":exportFavoriteExcel:"+merchantCode);
						redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_FAVORITE_CLASSIFY_KEY+":"+merchantCode, -1.0d);
					}finally{
						redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_FAVORITE_CLASSIFY_PRODANALYSE+":"+merchantCode, hashMap);
					}
				}
			};
			Thread thread = new Thread(run);
			thread.setName("exportFavoriteExcel 数据报表收藏列表导出");
			thread.start();
			//缓存进度值
			redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_FAVORITE_CLASSIFY_KEY+":"+merchantCode, progress);
			//表示已进入该方法，设置过安全库存
		    redisTemplate.opsForValue().set(CacheConstant.C_USER_ENTER_METHOD+":exportFavoriteExcel:"+merchantCode,"enter");
		    redisTemplate.expire(CacheConstant.C_USER_ENTER_METHOD+":exportFavoriteExcel:"+merchantCode, 1, TimeUnit.HOURS);
		    //60分钟之内没有下载完，就失效
		    redisTemplate.expire(CacheConstant.C_EXPORT_FAVORITE_CLASSIFY_PRODANALYSE+":"+merchantCode, 1, TimeUnit.HOURS);
		    redisTemplate.expire(CacheConstant.C_EXPORT_FAVORITE_CLASSIFY_KEY+":"+merchantCode, 1, TimeUnit.HOURS);
		}catch(Exception e){
			logger.error("商家编码：{},收藏夹导出商品信息发生错误！",YmcThreadLocalHolder.getMerchantCode(),e);
		}finally{
			if (threadPool != null) {
				threadPool.shutdown();
				threadPool = null;
			}
		}
		return "success";
	}
	
	
	//按商品导出
	private Map<String, Object> createFavoriteExcel(
				HttpServletRequest request, HttpServletResponse response,double progress,String merchantCode,Map<String,Object> queryMap) throws Exception {
			//加入当前商家的信息
			
			Query query = new Query(500);
			progress = progress +5.0d;
			redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_FAVORITE_CLASSIFY_KEY+":"+merchantCode, progress);
			
			List<Map<String, Object>>  favoriteInfoList=iReportFavoriteService.queryExportFavoriteInfo(queryMap);
			
			progress = progress +5.0d;
			redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_FAVORITE_CLASSIFY_KEY+":"+merchantCode, progress);
			
			//查询可售库存
			List<String> commodityNoList = new ArrayList<String>(0);
			
			
		
			Map<String, Integer> qtyMap = new HashMap<String, Integer>();
	        
	        double tempProgress = 0;
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("收藏夹商品列表");
			int rowInd = 0, colInd = 0;
			HSSFCellStyle style = workbook.createCellStyle(); // 样式对象  
			HSSFRow  row =null;
			colInd = 0;
			HSSFPatriarch patr = sheet.createDrawingPatriarch();
			row = sheet.createRow(rowInd++);
			style = workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.CORAL.index);
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			row.createCell(colInd).setCellValue("商品名称");
			row.getCell(colInd).setCellStyle(style);
			HSSFComment comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
			//comment.setString(new HSSFRichTextString("一级分类不可修改或删除"));
			row.getCell(colInd).setCellComment(comment);
			
			colInd++;
			row.createCell(colInd).setCellValue("商品款色编码");
			row.getCell(colInd).setCellStyle(style);
			//comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
			//comment.setString(new HSSFRichTextString("品牌不可修改或删除"));
			row.getCell(colInd).setCellComment(comment);
			
			colInd++;
			row.createCell(colInd).setCellValue("商品编号");
			row.getCell(colInd).setCellStyle(style);
			//comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
			//comment.setString(new HSSFRichTextString("品牌不可修改或删除"));
			row.getCell(colInd).setCellComment(comment);
			
			colInd++;
			row.createCell(colInd).setCellValue("优购价格（元）");
			row.getCell(colInd).setCellStyle(style);
			//comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
			//comment.setString(new HSSFRichTextString("品牌不可修改或删除"));
			row.getCell(colInd).setCellComment(comment);
			colInd++;
			row.createCell(colInd).setCellValue("收藏时间");
			row.getCell(colInd).setCellStyle(style);
			//comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
			//comment.setString(new HSSFRichTextString("商品编码不可修改或删除"));
			row.getCell(colInd).setCellComment(comment);
			colInd++;
			row.createCell(colInd).setCellValue("所属归类");
			row.getCell(colInd).setCellStyle(style);
			//comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
			//comment.setString(new HSSFRichTextString("商品名称可修改，修改后替换原有商品名称"));
			row.getCell(colInd).setCellComment(comment);
			colInd++;
			row.createCell(colInd).setCellValue("商品状态");
			row.getCell(colInd).setCellStyle(style);
		//	comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
			//comment.setString(new HSSFRichTextString("商品款号可修改，修改后替换原有商品款号"));
			row.getCell(colInd).setCellComment(comment);
			colInd++;
			row.createCell(colInd).setCellValue("可售库存");
			row.getCell(colInd).setCellStyle(style);
			//comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
			//comment.setString(new HSSFRichTextString("商品款号可修改，修改后替换原有商品款号"));
			row.getCell(colInd).setCellComment(comment);
			colInd++;
			String catName="";
			Category cat=null;
			for (Map<String, Object> map : favoriteInfoList) {
				tempProgress = new BigDecimal((rowInd*65.0/favoriteInfoList.size()))
				.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				colInd = 0;
				row = sheet.createRow(rowInd++);
				
				/*select t.id,t.commodity_no,t.create_time, 
				s.commodity_name ,s.pic_small,s.sale_price ,s.commodity_status,*/
				
				row.createCell(colInd++).setCellValue(ObjectUtils.toString(map.get("commodity_name")));
				row.createCell(colInd++).setCellValue(ObjectUtils.toString(map.get("supplier_code")));
				row.createCell(colInd++).setCellValue(ObjectUtils.toString(map.get("no")));
				row.createCell(colInd++).setCellValue(ObjectUtils.toString(map.get("sale_price")));
				row.createCell(colInd++).setCellValue(ObjectUtils.toString(map.get("create_time")));
				row.createCell(colInd++).setCellValue(ObjectUtils.toString(map.get("first_classify_name")));
				String	status=CommodityStatus.getStatusName((Integer)map.get("commodity_status"));
				row.createCell(colInd++).setCellValue(ObjectUtils.toString(status));
				row.createCell(colInd++).setCellValue(ObjectUtils.toString(""));
				redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_FAVORITE_CLASSIFY_KEY+":"+merchantCode, 
						new BigDecimal(progress+tempProgress).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
			}
			Map<String,Object> hashMap = new HashMap<String,Object>();
			
			String dateStr = DateUtil2.getCurrentDateTimeToStr();
			File file = new File(this.getAbsoluteFilepath(merchantCode));
			if(!file.exists()){
				file.mkdirs();
			}
			FileOutputStream outputStream = null;
			try{
				outputStream = new FileOutputStream(this.getAbsoluteFilepath(merchantCode)+
						"favoriteClassify_"+dateStr+".xls");
				workbook.write(outputStream);
				outputStream.flush();
				progress = progress+10.0d;
				redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_FAVORITE_CLASSIFY_KEY+":"+merchantCode, progress);
			}catch(Exception e){
				logger.error("商家编码：{},收藏夹IO异常，导出失败！",YmcThreadLocalHolder.getMerchantCode(),e);
			}finally{
				if(outputStream!=null){
					try {
						outputStream.close();
					}catch(Exception e){
						logger.error("商家编码：{},收藏夹IO异常，导出失败！",YmcThreadLocalHolder.getMerchantCode(),e);
					}
				}
			}
			File excelFile = new File(this.getAbsoluteFilepath(merchantCode)+"favoriteClassify_"+dateStr+".xls");
			boolean result = imageService.ftpUpload(excelFile,"/merchantpics/exceltemp/"+merchantCode);
	    	hashMap.put("result", result);
	    	hashMap.put("url",dateStr+".xls");
	    	progress = progress+5.0;
	    	redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_FAVORITE_CLASSIFY_KEY+":"+merchantCode, progress);
	    	return hashMap;
			
		}
		/** 
		 * getAbsoluteFilepath:得到绝对路径
		 * @author li.n1 
		 * @param merchantCode
		 * @return 
		 * @since JDK 1.6 
		 */  
		private String getAbsoluteFilepath(String merchantCode) {
			return new StringBuffer(commoditySettings.picDir)
			.append(File.separator)
			.append("exceltemp")
			.append(File.separator)
			.append(merchantCode)
			.append(File.separator)
			.toString();
		}
		@ResponseBody
		@RequestMapping("favoriteClassifyController/getExportResult")
		public String getExportResult(){
			String merchantCode = YmcThreadLocalHolder.getMerchantCode();
			Map<String,Object> hashMap = (Map<String,Object>)redisTemplate.opsForValue().get(CacheConstant.C_EXPORT_FAVORITE_CLASSIFY_PRODANALYSE+":"+merchantCode);
			Double progress = (Double)redisTemplate.opsForValue().get(CacheConstant.C_EXPORT_FAVORITE_CLASSIFY_KEY+":"+merchantCode);
			if(hashMap!=null){
				//已经生成excel，可供下载
				hashMap.put("result", "true");
				//删除缓存

				redisTemplate.delete(CacheConstant.C_EXPORT_FAVORITE_CLASSIFY_PRODANALYSE+":"+merchantCode);
	    		redisTemplate.delete(CacheConstant.C_USER_ENTER_METHOD+":exportFavoriteExcel:"+merchantCode);
	    		redisTemplate.delete(CacheConstant.C_EXPORT_FAVORITE_CLASSIFY_KEY+":"+merchantCode);
			}else{
				hashMap = new HashMap<String,Object>();
				hashMap.put("result", "false");
			}
			hashMap.put("progress", progress);
			return JSONObject.fromObject(hashMap).toString();
		}
		@RequestMapping("favoriteClassifyController/favoriteDownload")
		public void favoriteDownload(@RequestParam String name,HttpServletRequest request, HttpServletResponse response){
			String merchantCode = YmcThreadLocalHolder.getMerchantCode();
			OutputStream outputStream = null;
			try{
				response.reset();
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", MessageFormat.format("attachment; filename={0}", encodeDownloadAttachmentFilename(request, "收藏商品_"+name)));
				outputStream = response.getOutputStream();
				FileFtpUtil ftpUtil = new FileFtpUtil(commoditySettings.imageFtpServer,commoditySettings.imageFtpPort, 
						commoditySettings.imageFtpUsername, commoditySettings.imageFtpPassword);
				ftpUtil.login();
				ftpUtil.downRemoteFile("/merchantpics/exceltemp/"+merchantCode+"/favoriteClassify_"+name,outputStream);
				outputStream.flush();
				ftpUtil.logout();
			}catch(Exception e){
				logger.error("商家编码：{},收藏夹下载失败！",YmcThreadLocalHolder.getMerchantCode(),e);
			}finally{
				if(outputStream!=null){
					try {
						outputStream.close();
					} catch (IOException e) {
						logger.error("商家编码：{},收藏夹IO流关闭异常！",YmcThreadLocalHolder.getMerchantCode(),e);
					}
				}
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
		/**
		 * 如用户没有收藏夹默认初始化三个收藏夹 
		 * @param request
		 * @param response
		 * @throws Exception
		 */
		public void initSaveFavoriteClassifyInfo(HttpServletRequest request) throws Exception {
			
			try {
					Map<String,String>  mapFavoriteClassify=new HashMap<String,String>();
					
					
					// 获得当前登录的商家
					Map<String, Object> unionUser = SessionUtil.getUnionUser(request);
					//获取用户登录信息
					String loginName =   MapUtils.getString(unionUser, "login_name");
					
					String merchantCode = MapUtils.getString(unionUser, "supplier_code");
					//获取用户登录信息
					mapFavoriteClassify.put("merchant_code", merchantCode);
					//获取用户登录信息
					mapFavoriteClassify.put("login_name", loginName);
				
					mapFavoriteClassify.put("classify_name", "商品归类1");
					mapFavoriteClassify.put("id",UUIDGenerator.getUUID());
					iReportFavoriteService.addFavoriteClassify(mapFavoriteClassify);
					
					mapFavoriteClassify.put("classify_name", "商品归类2");
					mapFavoriteClassify.put("id",UUIDGenerator.getUUID());
					iReportFavoriteService.addFavoriteClassify(mapFavoriteClassify);
					mapFavoriteClassify.put("classify_name", "商品归类3");
					mapFavoriteClassify.put("id",UUIDGenerator.getUUID());
					iReportFavoriteService.addFavoriteClassify(mapFavoriteClassify);
					//通过favoriteId判断新增还是修改
					
		      } catch (Exception e) {
			  }
			
		}
		/**
		 * 取消归档
		 * @param favoriteId
		 * @param commodity_id
		 * @param request
		 * @param response
		 * @return
		 */
		@ResponseBody
		@RequestMapping("favoriteClassifyController/cancelMerchantClassification")
		public String cancelMerchantClassification(String favoriteId,String commodity_id, HttpServletRequest request, HttpServletResponse response)  {
			Map cancelMap=new HashMap();
			List<String> fvrList = new ArrayList<String>();
			try {
				if(StringUtils.isNotBlank(commodity_id)){
					String  [] favoriteIdList=commodity_id.split(",");
					for (int i = 0; i < favoriteIdList.length; i++) {
						fvrList.add(favoriteIdList[i]);
					}
				}
			
				cancelMap.put("fvr_commodity_ids", fvrList);
				cancelMap.put("classify_id", favoriteId);
				iReportFavoriteService.batchDeleteFavoriteCommodityClassify(cancelMap);
			} catch (Exception e) {
				logger.error("商家编码：{},收藏夹取消归类cancelMerchantClassification发生异常.",YmcThreadLocalHolder.getMerchantCode(), e);
				return "false";
			}
			return "true";
	  }
		/**
		 * 保存收藏商品信息
		 * @param favoriteId
		 * @param commodity_id
		 * @param request
		 * @param response
		 * @return
		 */
		@ResponseBody
		@RequestMapping("favoriteClassifyController/saveFavoriteCommodity")
		public String saveFavoriteCommodity(String favoriteIds,String commodity_id, HttpServletRequest request)  {
			try {
				Map<String, String> favoriteCommodityIofo=new HashMap<String, String>();
				// 获得当前登录的商家
				Map<String, Object> unionUser = SessionUtil.getUnionUser(request);
				//获取用户登录信息
				String loginName =   MapUtils.getString(unionUser, "login_name");
				
				String merchantCode = MapUtils.getString(unionUser, "supplier_code");
				//获取用户登录信息
				favoriteCommodityIofo.put("merchant_code", merchantCode);
				//获取用户登录信息
				favoriteCommodityIofo.put("login_name", loginName);
				
				favoriteCommodityIofo.put("commodity_id",commodity_id);
				favoriteCommodityIofo.put("favoriteIds",favoriteIds);
				
				List<Map<String, Object>> favoriteList;
				favoriteList = iReportFavoriteService.queryFavoriteCommodityInfo(favoriteCommodityIofo);
				if(favoriteList!=null && favoriteList.size()>0){
					//先删除关联表信息
					Map<String, Object>   map=favoriteList.get(0);
					iReportFavoriteService.deleteFavoriteCommodityClassify((String)map.get("id"));
					
					//批量新增收藏商品信息
					if(StringUtils.isNotBlank(favoriteIds)){
						List<String> fvrList = new ArrayList<String>();
						List<Map<String, String>>   favoriteCommodityList=new ArrayList<Map<String, String>>();
						String  [] favoriteIdList=favoriteIds.split(",");
						if(favoriteIdList!=null){
							for (int i = 0; i < favoriteIdList.length; i++) {
								Map<String, String>   favoriteCommodityMao=new HashMap<String, String>();
								String id= UUIDGenerator.getUUID();
								favoriteCommodityMao.put("id", id);
								favoriteCommodityMao.put("classify_id", favoriteIdList[i]);
								favoriteCommodityMao.put("fvr_commodity_id", (String)map.get("id")); 
								favoriteCommodityList.add(favoriteCommodityMao);
								fvrList.add(favoriteIdList[i]);
							}
						}
						Map  hashMap=new HashMap();
						hashMap.put("fvr_commodity_ids", fvrList);
						hashMap.put("comClaList", favoriteCommodityList);
						iReportFavoriteService.batchAddFavoriteCommodityClassify(hashMap);
					}
				}else{
					
					//新增商品管理表信息
					String commodityId= UUIDGenerator.getUUID();
					Map<String,String> commodityMap=new HashMap<String, String>();
					commodityMap.put("id",commodityId);
					commodityMap.put("commodity_no", commodity_id);
					commodityMap.put("login_name", loginName) ;
					commodityMap.put("merchant_code", merchantCode);
					iReportFavoriteService.addFavoriteCommodity(commodityMap);
					
					
					//批量新增收藏商品信息
					if(StringUtils.isNotBlank(favoriteIds)){
						List<Map<String, String>>   favoriteCommodityList=new ArrayList<Map<String, String>>();
						String  [] favoriteIdList=favoriteIds.split(",");
						List<String> fvrList = new ArrayList<String>();
						if(favoriteIdList!=null){
							for (int i = 0; i < favoriteIdList.length; i++) {
								Map<String, String>   favoriteCommodityMao=new HashMap<String, String>();
								String id= UUIDGenerator.getUUID();
								favoriteCommodityMao.put("id", id);
								favoriteCommodityMao.put("classify_id", favoriteIdList[i]);
								favoriteCommodityMao.put("fvr_commodity_id", commodityId); 
								favoriteCommodityList.add(favoriteCommodityMao);
								fvrList.add(favoriteIdList[i]);
							}
						}
						Map  map=new HashMap();
						map.put("fvr_commodity_ids", fvrList);
						map.put("comClaList", favoriteCommodityList);
						iReportFavoriteService.batchAddFavoriteCommodityClassify(map);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		    return "";	
	   }
     	
}
