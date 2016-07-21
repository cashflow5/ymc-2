package com.yougou.kaidian.commodity.web;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.yougou.fss.api.IFSSYmcApiService;
import com.yougou.kaidian.commodity.component.CommodityComponent;
import com.yougou.kaidian.commodity.constant.CommodityConstant;
import com.yougou.kaidian.commodity.convert.ExcelToDataModelConverter;
import com.yougou.kaidian.commodity.model.vo.CommodityAndProductExportVo;
import com.yougou.kaidian.commodity.model.vo.CommodityQueryVo;
import com.yougou.kaidian.commodity.model.vo.CommoditySubmitNewVo;
import com.yougou.kaidian.commodity.model.vo.CommoditySubmitResultVo;
import com.yougou.kaidian.commodity.model.vo.CommoditySubmitVo;
import com.yougou.kaidian.commodity.model.vo.ErrorVo;
import com.yougou.kaidian.commodity.model.vo.ExcelErrorVo;
import com.yougou.kaidian.commodity.model.vo.ProductExportVo;
import com.yougou.kaidian.commodity.service.ICommodityExtendService;
import com.yougou.kaidian.commodity.service.ICommodityService;
import com.yougou.kaidian.commodity.service.IImageService;
import com.yougou.kaidian.commodity.service.IMerchantCommodityService;
import com.yougou.kaidian.commodity.util.CommodityUtil;
import com.yougou.kaidian.commodity.util.CommodityUtilNew;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.commodity.pojo.Commodity;
import com.yougou.kaidian.common.commodity.pojo.CommodityExtend;
import com.yougou.kaidian.common.commodity.util.CommodityPicIndexer;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.common.util.CommonUtil;
import com.yougou.kaidian.common.util.DateUtil2;
import com.yougou.kaidian.common.util.FileFtpUtil;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.kaidian.framework.constant.Constant;
import com.yougou.kaidian.framework.settings.CommoditySettings;
import com.yougou.kaidian.framework.util.SessionUtil;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.order.enums.ResultEnums;
import com.yougou.kaidian.stock.service.IStockService;
import com.yougou.kaidian.stock.service.IWarehouseService;
import com.yougou.kaidian.taobao.enums.ResultCode;
import com.yougou.kaidian.taobao.exception.BusinessException;
import com.yougou.kaidian.taobao.service.ITaobaoItemService;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.api.ICommodityMerchantApiService;
import com.yougou.pc.model.category.Category;
import com.yougou.pc.model.commoditylog.CommodityUpdateLog;
import com.yougou.pc.model.merchant.MerchantImportInfo;
import com.yougou.pc.model.product.Product;
import com.yougou.pc.model.prop.PropItem;
import com.yougou.pc.model.prop.PropValue;
import com.yougou.pc.model.sensitive.SensitiveCheckLog;
import com.yougou.pc.vo.commodity.CommodityVO;
import com.yougou.wms.wpi.common.exception.WPIBussinessException;
import com.yougou.wms.wpi.inventory.domain.vo.InventoryAssistVo;
import com.yougou.wms.wpi.inventory.service.IInventoryDomainService;
import com.yougou.wms.wpi.inventory.service.IInventoryForMerchantService;

import freemarker.ext.beans.BeansWrapper;

/**
 * 商品Controller类-主要包括在售和待售两个菜单的页面功能
 * @author li.m1
 *
 */
@Controller
@RequestMapping("/commodity")
public class CommodityController {
	
	private static final Logger log = LoggerFactory.getLogger(CommodityController.class);
	
	@Resource
	private ICommodityService commodityService;
	@Resource
	private IMerchantCommodityService merchantCommodityService;
	@Resource
	private ICommodityMerchantApiService commodityApi;
	@Resource
	private CommoditySettings settings;
	@Resource
	private IInventoryDomainService inventoryDomainService;
	@Resource
	private ICommodityBaseApiService commodityBaseApiService;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private CommodityComponent commodityComponent;
	@Resource
	private IInventoryForMerchantService inventoryForMerchantService;
	@Resource
	private IFSSYmcApiService fssYmcApiService;
	@Resource
	private IImageService imageService;
    @Resource
	private CommoditySettings commoditySettings;
    @Resource
    private IStockService stockService;
	@Resource
	private ICommodityMerchantApiService commodityMerchantApiService;
	@Resource
	private ITaobaoItemService taobaoItemService;
	
	@Resource
	private IWarehouseService warehouseService;
	
	@Resource
	private ICommodityExtendService commodityExtendService;
	
	private static final String RESULT_SUCCESS = "SUCCESS";
	/**
	 * 按商品导入
	 */
	private static final short IMPORTBYCOMMCOLUMN = 9;
	/**
	 * 按尺寸导入
	 */
	private static final short IMPORTBYSIZECOLUMN = 12;
	
	//判断是否按尺码修改价格
	//深圳测试4s932
	//正式线上4y644
	@Value("#{configProperties['priceBySizeProp']}")
	private String priceBySizePropNo = "4y644";
	
	/**
	 * 查询出售中的商品
	 * @param modelMap
	 * @param request
	 * @param query
	 * @param commodityQueryVo
	 * @return
	 */
	@RequestMapping("goQueryOnSaleCommodity")
	public String goQueryOnSaleCommodity(ModelMap modelMap, HttpServletRequest request, Query query, CommodityQueryVo commodityQueryVo) {
		//加入当前商家的信息
		commodityQueryVo.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
		commodityQueryVo.setWarehouseCode(SessionUtil.getWarehouseCodeFromSession(request));
		commodityComponent.initCommonProperty(modelMap);
		commodityQueryVo.setStructName(CommodityUtil.getCatStructName(commodityQueryVo));
		commodityQueryVo.setCommodityStatus("2");
		//查询主数据
		query.setPageSize(20);
		PageFinder<Commodity> pageFinder = commodityService.queryCommodityList(query, commodityQueryVo, modelMap);
		
		if(!isEmptyForPageFinder(pageFinder)){
			modelMap.put("pageFinder", pageFinder);
		}
		modelMap.addAttribute("tagTab", "goods");
		modelMap.put("commodityQueryVo", commodityQueryVo);
		return "/manage/commodity/on_sale_commodity";
	}
	
	@ResponseBody
	@RequestMapping("/createOnSaleCommodity")
	public String createOnSaleCommodity(HttpServletRequest request,
			HttpServletResponse response, 
			CommodityQueryVo commodityQueryVo) throws Exception{
		Map<String,Object> hashMap = exportOnSaleCommodity(request,response,commodityQueryVo);
		return JSONObject.fromObject(hashMap).toString();
	}
	
	@RequestMapping("/onSaleDownload")
	public void onSaleDownload(@RequestParam String name,HttpServletRequest request, HttpServletResponse response){
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		OutputStream outputStream = null;
		try{
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", MessageFormat.format("attachment; filename={0}", encodeDownloadAttachmentFilename(request, "在售商品_"+name)));
			outputStream = response.getOutputStream();
			FileFtpUtil ftpUtil = new FileFtpUtil(commoditySettings.imageFtpServer,commoditySettings.imageFtpPort, 
					commoditySettings.imageFtpUsername, commoditySettings.imageFtpPassword);
			ftpUtil.login();
			ftpUtil.downRemoteFile("/merchantpics/exceltemp/"+merchantCode+"/onSale_"+name,outputStream);
			outputStream.flush();
			ftpUtil.logout();
		}catch(Exception e){
			log.error("下载失败！",e);
		}finally{
			if(outputStream!=null){
				try {
					outputStream.close();
				} catch (IOException e) {
					log.error("IO流关闭异常！",e);
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
	 * 导出在售商品
	 * @param request
	 * @param response
	 * @param commodityQueryVo
	 * @throws Exception
	 */
	private Map<String,Object> exportOnSaleCommodity(HttpServletRequest request, HttpServletResponse response, CommodityQueryVo commodityQueryVo) throws Exception {
		//加入当前商家的信息
		commodityQueryVo.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
		commodityQueryVo.setWarehouseCode(SessionUtil.getWarehouseCodeFromSession(request));
		commodityQueryVo.setStructName(CommodityUtil.getCatStructName(commodityQueryVo));
		
		commodityQueryVo.setCommodityStatus("2");
		commodityQueryVo.setOnSaleQuantiry(null);
		//查询主数据
		List<Commodity> commodityList = commodityService.queryExportCommodityList(commodityQueryVo,500);
		List<ProductExportVo> productList = commodityService.queryExportProductList(commodityQueryVo,500);
		
		//查询销售数据
		List<String> commodityNoList = new ArrayList<String>(0);
		for(Commodity c : commodityList) {
			commodityNoList.add(c.getCommodityNo());
		}
		Map<String, Integer> mapCommoditySale = commodityComponent.querySaleNumForBIByCommodityNos(commodityNoList, commodityQueryVo.getMerchantCode());
		//查询可售库存
		Map<String, Integer> qtyMap = new HashMap<String, Integer>();
        try {
            List<InventoryAssistVo> qtys = null;
            if (StringUtils.isNotBlank(commodityQueryVo.getWarehouseCode())) {
                qtys = inventoryForMerchantService.queryCommodityInventory(commodityNoList, commodityQueryVo.getWarehouseCode());
                if (CollectionUtils.isNotEmpty(qtys)) {
                    for (InventoryAssistVo qtyVo : qtys) {
                        qtyMap.put(qtyVo.getCommodityNo(), qtyVo.getCanSalesInventoryNum());
                    }
                }
            } else {
                qtys = inventoryForMerchantService.queryInvenotryByCommodity(commodityNoList);
                if (CollectionUtils.isNotEmpty(qtys)) {
                    for (InventoryAssistVo qtyVo : qtys) {
                        qtyMap.put(qtyVo.getCommodityNo(), qtyVo.getInventoryQuantity());
                    }
                }
            }
        } catch (WPIBussinessException e) {
			log.error("查询queryCommodityInventory发生异常.", e);
		}
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("商品列表");
		HSSFSheet sheet1 = workbook.createSheet("货品条码");
		int rowInd = 0, colInd = 0;
		HSSFRow row = sheet.createRow(rowInd++);
		row.createCell(colInd++).setCellValue("商品名称");
		row.createCell(colInd++).setCellValue("商品编码");
		row.createCell(colInd++).setCellValue("商品款号");
		row.createCell(colInd++).setCellValue("颜色");
		row.createCell(colInd++).setCellValue("款色编码");
		row.createCell(colInd++).setCellValue("一级分类");
		row.createCell(colInd++).setCellValue("二级级分类");
		row.createCell(colInd++).setCellValue("三级分类");
		row.createCell(colInd++).setCellValue("品牌");
		row.createCell(colInd++).setCellValue("在售库存");
		row.createCell(colInd++).setCellValue("市场价");
		row.createCell(colInd++).setCellValue("销售价");
		row.createCell(colInd++).setCellValue("商品销量");
		String StructCatName[]=new String[3];
		Category cat=null;
		for (Commodity commodity : commodityList) {
			colInd = 0;
			row = sheet.createRow(rowInd++);
			cat=commodityBaseApiService.getCategoryByStructName(commodity.getCatStructName());
			if(cat!=null){
				StructCatName=cat.getStructCatName().split("-");
			}
			row.createCell(colInd++).setCellValue(commodity.getCommodityName());
			row.createCell(colInd++).setCellValue(commodity.getCommodityNo());
			row.createCell(colInd++).setCellValue(commodity.getStyleNo());
			row.createCell(colInd++).setCellValue(commodity.getSpecName());
			row.createCell(colInd++).setCellValue(commodity.getSupplierCode());
			row.createCell(colInd++).setCellValue(StructCatName[0]);
			row.createCell(colInd++).setCellValue(StructCatName[1]);
			row.createCell(colInd++).setCellValue(StructCatName[2]);
			row.createCell(colInd++).setCellValue(commodity.getBrandName());
			//row.createCell(colInd++).setCellValue(ObjectUtils.toString(commodity.getOnSaleQuantity()));
			row.createCell(colInd++).setCellValue(qtyMap.containsKey(commodity.getCommodityNo()) ? qtyMap.get(commodity.getCommodityNo()) : 0);
			row.createCell(colInd++).setCellValue(commodity.getPublicPrice());
			row.createCell(colInd++).setCellValue(commodity.getSalePrice());
			row.createCell(colInd++).setCellValue(MapUtils.getInteger(mapCommoditySale, commodity.getCommodityNo(), NumberUtils.INTEGER_ZERO));
		}
		//货品条码sheet
		rowInd = 0; colInd = 0;
		row = sheet1.createRow(rowInd++);
		row.createCell(colInd++).setCellValue("款色编码");
		row.createCell(colInd++).setCellValue("尺码");
		row.createCell(colInd++).setCellValue("货品条码");
		for (ProductExportVo product : productList) {
			colInd = 0;
			row = sheet1.createRow(rowInd++);
			row.createCell(colInd++).setCellValue(product.getSupplierCode());
			row.createCell(colInd++).setCellValue(product.getSizeName());
			row.createCell(colInd++).setCellValue(product.getThirdPartyCode());
		}
		Map<String,Object> hashMap = new HashMap<String,Object>();
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		String dateStr = DateUtil2.getCurrentDateTimeToStr();
		File file = new File(this.getAbsoluteFilepath(merchantCode));
		if(!file.exists()){
			file.mkdirs();
		}
		FileOutputStream outputStream = null;
		try{
			outputStream = new FileOutputStream(this.getAbsoluteFilepath(merchantCode)+
					"onSale_"+dateStr+".xls");
			workbook.write(outputStream);
			outputStream.flush();
		}catch(Exception e){
			log.error("IO异常，导出失败！",e);
		}finally{
			if(outputStream!=null){
				try {
					outputStream.close();
				}catch(Exception e){
					log.error("IO异常，导出失败！",e);
				}
			}
		}
		File excelFile = new File(this.getAbsoluteFilepath(merchantCode)+"onSale_"+dateStr+".xls");
		boolean result = imageService.ftpUpload(excelFile,"/merchantpics/exceltemp/"+merchantCode);
    	hashMap.put("result", result);
    	hashMap.put("url",dateStr+".xls");
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
		/*return new StringBuffer("D:")
		.append(File.separator)
		.append("exceltemp")
		.append(File.separator)
		.append(merchantCode)
		.append(File.separator)
		.toString();*/
	}

	/**
	 * 通过分类id，获取商品属性、颜色、尺码
	 * @param catId 商品分类id
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getCommodityPropertitiesByCatId")
	public String getCommodityPropertitiesByCatId(
			String catId, ModelMap model, HttpServletRequest request) {
		com.alibaba.fastjson.JSONObject jsonObj = new com.alibaba.fastjson.JSONObject();
		jsonObj.put("isSuccess", "false");
		catId = CommonUtil.getTrimString(catId);
		if (catId.length() == 0) {
			jsonObj.put("errorMsg", "请选择分类");
			return jsonObj.toString();
		}
		//获取商品属性
		List<PropItem> propItemList = merchantCommodityService.getPropMsgByCatIdNew(catId, false);
		if (propItemList != null && !propItemList.isEmpty()) {
			for(PropItem propItem : propItemList) {
				if(propItem != null && propItem.getIsShowMall() == null) {
					propItem.setIsShowMall(1);
				}
			}
			jsonObj.put("propList", propItemList);
		}
		//判断是否按尺码修改价格
		//深圳测试4s932
		//正式线上4y644
		List<PropValue> propList = commodityBaseApiService.getPropValueListByCategoryIdAndItemNo(catId,priceBySizePropNo);
		//有按尺码修改价格属性
		if(propList!=null&&propList.size()>0){
			jsonObj.put("isSizePrice", 1);
		}
		//获取颜色
		List<PropValue> colorList = commodityApi.getColorsByCatId(catId);
		if (colorList != null) {
			jsonObj.put("colorList", colorList);
		}
		
		//获取尺码
		List<PropValue> sizeList = commodityApi.getSizeByCatId(catId);
		if (sizeList != null) {
			jsonObj.put("sizeList", sizeList);
		}
		
		//获取分类对象
		String merchantCode=YmcThreadLocalHolder.getMerchantCode();
		int sheetIndex = 1;
		try {
			sheetIndex = CommodityPicIndexer.indexSheets(merchantCommodityService.getCommodityCatNamesByCatID(catId));
		} catch (Exception e) {
			log.error("商家：{}通过分类获取sheetIndex异常.",merchantCode,e);
		}
		jsonObj.put("sheetIndex",sheetIndex);
		jsonObj.put("isSuccess", "true");
		return jsonObj.toString();
	}
	
	/**
	 * 跳转到发布单品的页面
	 * @param fromTabId 来自于哪个列表tab点击进来的
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/goAddCommodity")
	public String goAddCommodity(String fromTabId, ModelMap model, HttpServletRequest request) {
		String ftlUrl = "/manage/commodity/add_commodity";
		//商家编号
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		model.put("merchantCode", merchantCode);
		//新增或修改商品 公共方法
		commodityComponent.goAddOrUpdateCommodityCommon(fromTabId, model, request);
		model.put("pageSourceId", 0);
		model.put("auditStatus", "");
		model.put("tagTab", "goods");
		return ftlUrl;
	}
	
	
	/**
	 * 跳转到 修改商品的页面
	 * @param commodityNo 商品编号
	 * @param fromTabId 来自于哪个列表tab点击进来的
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/goUpdateCommodity")
	public String goUpdateCommodity(String commodityNo, String fromTabId, String commodityStatus,
			ModelMap model, HttpServletRequest request) {
		String ftlUrl = "/manage/commodity/update_commodity";
		String prefix = "goUpdateCommodity#-> ";
		
		//新增或修改商品 公共方法
		commodityComponent.goAddOrUpdateCommodityCommon(fromTabId, model, request);
		//商品编号
		commodityNo = CommonUtil.getTrimString(commodityNo);
		model.addAttribute("commodityNo", commodityNo);
		log.info("{} commodityNo: {}", prefix, commodityNo);
		
		//保存修改前的审核状态
		model.put("auditStatus", "13".equals(commodityStatus) ? "2" : "11");
		
		//图片域名
		model.addAttribute("commodityPreviewDomain", settings.getCommodityPreviewDomain());
		log.info("{} commodityPreviewDomain: {} ", prefix, settings.getCommodityPreviewDomain());
		model.put("pageSourceId", 1);
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		model.put("merchantCode", merchantCode);
		return ftlUrl;
	}
	
	@RequestMapping("/goUpdateCommodityNew")
	public String goUpdateCommodityNew(String commodityNo, String fromTabId, String commodityStatus,
			ModelMap model, HttpServletRequest request) {
		String ftlUrl = "/manage/commodity/update_commodity_new";
		String prefix = "goUpdateCommodity#-> ";
		
		//新增或修改商品 公共方法
		commodityComponent.goAddOrUpdateCommodityCommon(fromTabId, model, request);
		//商品编号
		commodityNo = CommonUtil.getTrimString(commodityNo);
		model.addAttribute("commodityNo", commodityNo);
		log.info("{} commodityNo: {}",prefix,commodityNo);
		//保存修改前的审核状态
		model.put("auditStatus", "13".equals(commodityStatus) ? "2" : "11");
		//图片域名
		model.addAttribute("commodityPreviewDomain", settings.getCommodityPreviewDomain());
		log.info("{} commodityPreviewDomain: {}" ,prefix, settings.getCommodityPreviewDomain());
		model.put("pageSourceId", 1);
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		model.put("merchantCode", merchantCode);
		return ftlUrl;
	}
	
	final static String COMMODITY_ADD_LOG_PREFIX = "addCommodity#-> ";
	
	/**
	 * 添加商品
	 * @param submitVo 新增或修改商品 提交时 用的vo
	 * @param multiRequest 
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addCommodity")
	public String addCommodity(
			CommoditySubmitVo submitVo,
			DefaultMultipartHttpServletRequest multiRequest,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String merchantCode=YmcThreadLocalHolder.getMerchantCode();
		CommoditySubmitResultVo resultVo = new CommoditySubmitResultVo();
		String addCommodity_key="merchant.addCommodity:"+merchantCode+":"+submitVo.getStyleNo();
		//防止瞬时提交,2秒内
		Object addTime=redisTemplate.opsForValue().get(addCommodity_key);
		if(addTime!=null){
			Date existTime=(Date)addTime;
			Date now=new Date();
			if((now.getTime()-existTime.getTime())<2000){
				resultVo.setErrorMsg("请勿频繁提交!");
				return JSONObject.fromObject(resultVo).toString();
			}
		}else{
			redisTemplate.opsForValue().set(addCommodity_key, new Date());
			redisTemplate.expire(addCommodity_key, 4, TimeUnit.SECONDS);
		}
		
		//0000
		//判断是否按尺码修改价格
		//深圳测试4s932
		//正式线上4y644
		List<PropValue> propList = commodityBaseApiService.getPropValueListByCategoryIdAndItemNo(
				CommodityUtil.getCatIdByThreeCategory(submitVo.getThreeCategory()),priceBySizePropNo);
		
		List<ErrorVo> errorList = new ArrayList<ErrorVo>();
		//新增或修改商品信息，初始化用户信息
		CommodityUtil.initMerchantUserInfoForAddOrUpdate(request, submitVo);
		//设置商品基础配置
		submitVo.setSettings(settings);
		
		submitVo.validateAddCommodityFormNew(errorList, merchantCommodityService.getCommodityCatNamesByCatStructName(CommodityUtil.getStructNameByThreeCategory(submitVo.getThreeCategory())),propList);
		//外链处理
		errorList=imageService.verifyCommodityProdDesc(submitVo,errorList);
		if (CollectionUtils.isNotEmpty(errorList)) {
			//如果操作是预览
			if (CommodityConstant.SUBMIT_COMMODITY_IS_PREVIEW_TRUE.equals(
					submitVo.getIsPreview())) {
				CommodityUtil.getAlertThenCloseScript(response, errorList.get(0).getErrMsg());
				return null;
			} else {
				resultVo.setErrorList(errorList);
				return JSONObject.fromObject(resultVo).toString();				
			}
		}
		List<CommodityVO> voList = null;
		//有按尺码修改价格属性
		if(propList!=null&&propList.size()>0){
			String supplierCode = commodityService.getSupplierCodeByStyleNoAndMerchantCode(submitVo.getBrandNo(),
					submitVo.getStyleNo(),merchantCode);
			Map<String,Object> map = commodityService.getCountByStyleNoAndMerchantCodeAndColor(
					submitVo.getBrandNo(),
					submitVo.getStyleNo(), merchantCode, submitVo.getSpecName());
			voList = CommodityUtil.buildCommodityVOList(submitVo,supplierCode,map);
			for(CommodityVO vo : voList){
				commodityComponent.fillCommodityVo(vo);
			}
			log.warn("=======进入按尺码设置价格======商品属性：{}",ToStringBuilder.reflectionToString(voList,ToStringStyle.MULTI_LINE_STYLE));
		}else{
			//木有按尺码修改价格属性，按原来方案流程走
			//构造CommodityVO
			voList = new ArrayList<CommodityVO>();
			CommodityVO commodityVO = CommodityUtil.buildCommodityVO(submitVo);
			commodityComponent.fillCommodityVo(commodityVO);
			voList.add(commodityVO);
		}
		
		//如果为预览
		//按尺码设置价格暂先屏蔽预览功能，等货品组完成静态页生成再考虑开放
		if(voList!=null && voList.size()==1){
			if (CommodityConstant.SUBMIT_COMMODITY_IS_PREVIEW_TRUE.equals(
					submitVo.getIsPreview())) {
				request.setAttribute(CommodityConstant.COMMODITY_PREVIEW_COMMODITYVO_KEY, voList.get(0));
				request.setAttribute(CommodityConstant.COMMODITY_PREVIEW_COMMODITY_SUBMIT_VO_KEY, submitVo);
				request.getRequestDispatcher("/commodity/import/preview.sc").forward(request, response);
				return null;
			}
		}
		
		try {			
			//提交商品 
			if(voList!=null&&voList.size()>0){
				if(propList!=null&&propList.size()>0){
					//按尺码设置价格保存
					//保存成功数与失败数量
					//失败跳过继续保存下一个
					return saveBySizeCommodity(voList,errorList,resultVo,submitVo);
				}else{
					for(CommodityVO commodityVO : voList){
						MerchantImportInfo returnVo = commodityApi.insertCommodityMerchant(commodityVO);
						if (returnVo.getResult().toUpperCase().indexOf(RESULT_SUCCESS) == -1) {
							log.info("{} result: {}" , COMMODITY_ADD_LOG_PREFIX , returnVo.getErrorList());
							CommodityUtil.transformCommodityError(returnVo.getErrorList(), errorList);
							resultVo.setErrorList(errorList);
							return JSONObject.fromObject(resultVo).toString();
						} else {
							//标记商品资料提交成功
							resultVo.setIsCommoditySubmitSuccess(true);
							submitVo.setCommodityNo(returnVo.getCommodityNo());
						}
						try {
							//缓存[通过款色编码存储分类]
							this.redisTemplate.opsForHash().put(CacheConstant.C_COMMODITY_CATEGORY_KEY, commodityVO.getMerchantCode() + "_" + commodityVO.getSupplierCode(), commodityVO.getCatStructname());
							this.redisTemplate.expire(CacheConstant.C_COMMODITY_CATEGORY_KEY, 30, TimeUnit.MINUTES);// 设置属性时，重新设置超时时间续命
						} catch (Exception e) {
							log.error("设置分类缓存异常.", e);
						}
						
						//发送JMS消息通知Image处理图片
						commodityService.sendJmsForMaster(submitVo, 0);
						
						//统一抛出错误异常
						if (CollectionUtils.isNotEmpty(errorList)) {
							resultVo.setErrorList(errorList);
							return JSONObject.fromObject(resultVo).toString();
						}
						
						//库存
						if ((CommodityConstant.SUPPLIER_IS_INPUT_YOUGOU_WAREHOUSE_NOT_IN + "")
								.equals(submitVo.getIsInputYougouWarehouse())) {
							String updateStockErrorMsg = commodityComponent.updateStockForAdd(submitVo, resultVo, returnVo.getProductCodes());
							if (StringUtils.isNotBlank(updateStockErrorMsg)) {
								resultVo.setErrorMsg(updateStockErrorMsg);
								return JSONObject.fromObject(resultVo).toString();
							}
						}
						
						//如果是保存并提交审核
						if (CommodityConstant.SUBMIT_COMMODITY_IS_SAVE_SUBMIT_TRUE
								.equals(submitVo.getIsSaveSubmit())) {
							//校验图片完整性
							if (!merchantCommodityService.checkCommodityPicsIntegrality(
									submitVo.getCommodityNo(), submitVo.getSupplierCode(), submitVo.getMerchantCode(),submitVo.getBrandNo())) {
								resultVo.setErrorMsg("图片完整性校验异常");
								return JSONObject.fromObject(resultVo).toString();
							}
							
							//提交审核
							if (!commodityApi.auditMerchant(submitVo.getCommodityNo(), submitVo.getMerchantCode())) {
								resultVo.setErrorMsg("提交审核失败");
								return JSONObject.fromObject(resultVo).toString();
							}
							//设置为保存并提交审核
							resultVo.setIsAddCommoditySaveSubmit(true);
						}
						
						//给旗舰店插入数据
						try {
							fssYmcApiService.updateStoreIdWhenPublishCommodity(submitVo.getMerchantCode(),commodityVO.getBrandNo(),commodityVO.getCatStructname());
						} catch (Exception e) {
							log.error("给旗舰店添加商品运行时异常({}):{}",returnVo.getCommodityNo());
						}
					}
				}
			}
		} catch (RuntimeException e) {
			log.error("添加商品运行时异常:",e);
			if (e.getClass().getName().equals("java.lang.RuntimeException")) {
				resultVo.setErrorMsg(e.getMessage());
			} else {
				resultVo.setErrorMsg("网络超时,请刷新后重新提交");
			}
			return JSONObject.fromObject(resultVo).toString();
		} catch (Exception e) {
			log.error("添加商品产生异常:",e);
			resultVo.setErrorMsg("网络超时,请刷新后重新提交");
			return JSONObject.fromObject(resultVo).toString();
		}
		resultVo.setIsSuccess(true);
		return JSONObject.fromObject(resultVo).toString();
	}
	
	
	private String saveBySizeCommodity(List<CommodityVO> voList,
			List<ErrorVo> errorList,CommoditySubmitResultVo resultVo,
			CommoditySubmitVo submitVo) throws Exception  {
		int error = 0;
		for(CommodityVO commodityVO : voList){
			MerchantImportInfo returnVo = commodityApi.insertCommodityMerchant(commodityVO);
			if (returnVo.getResult().toUpperCase().indexOf(RESULT_SUCCESS) == -1) {
				log.info("{} result: {}",COMMODITY_ADD_LOG_PREFIX, returnVo.getErrorList());
				CommodityUtil.transformCommodityError(returnVo.getErrorList(), errorList);
				resultVo.setErrorList(errorList);
				//记录错误的个数
				error++;
				//return JSONObject.fromObject(resultVo).toString();
			}else{
				submitVo.setCommodityNo(returnVo.getCommodityNo());
				try {
					//缓存[通过款色编码存储分类]
					this.redisTemplate.opsForHash().put(CacheConstant.C_COMMODITY_CATEGORY_KEY, commodityVO.getMerchantCode() + "_" + commodityVO.getSupplierCode(), commodityVO.getCatStructname());
					this.redisTemplate.expire(CacheConstant.C_COMMODITY_CATEGORY_KEY, 30, TimeUnit.MINUTES);// 设置属性时，重新设置超时时间续命
				} catch (Exception e) {
					log.error("设置分类缓存异常.", e);
				}
				
				//发送JMS消息通知Image处理图片
				commodityService.sendJmsForMaster(submitVo, 0);
				
				//库存
				if ((CommodityConstant.SUPPLIER_IS_INPUT_YOUGOU_WAREHOUSE_NOT_IN + "")
						.equals(submitVo.getIsInputYougouWarehouse())) {
					submitVo.setSupplierCode(commodityVO.getSupplierCode());
					String updateStockErrorMsg = commodityComponent.updateStockForAdd(submitVo, resultVo, returnVo.getProductCodes());
					if (StringUtils.isNotBlank(updateStockErrorMsg)) {
						resultVo.setErrorMsg(updateStockErrorMsg);
						return JSONObject.fromObject(resultVo).toString();
					}
				}
				
				//如果是保存并提交审核
				if (CommodityConstant.SUBMIT_COMMODITY_IS_SAVE_SUBMIT_TRUE
						.equals(submitVo.getIsSaveSubmit())) {
					//校验图片完整性
					if (!merchantCommodityService.checkCommodityPicsIntegrality(
							submitVo.getCommodityNo(), submitVo.getSupplierCode(), submitVo.getMerchantCode(),submitVo.getBrandNo())) {
						resultVo.setErrorMsg("图片完整性校验异常");
						return JSONObject.fromObject(resultVo).toString();
					}
					
					//提交审核
					if (!commodityApi.auditMerchant(submitVo.getCommodityNo(), submitVo.getMerchantCode())) {
						resultVo.setErrorMsg("提交审核失败");
						return JSONObject.fromObject(resultVo).toString();
					}
					//设置为保存并提交审核
					resultVo.setIsAddCommoditySaveSubmit(true);
				}
				
				//给旗舰店插入数据
				try {
					fssYmcApiService.updateStoreIdWhenPublishCommodity(submitVo.getMerchantCode(),commodityVO.getBrandNo(),commodityVO.getCatStructname());
				} catch (Exception e) {
					log.error("给旗舰店添加商品运行时异常({})",returnVo.getCommodityNo(),e);
				}
			}
		}
		
		if(error==0){
			resultVo.setIsSuccess(true);
			resultVo.setIsCommoditySubmitSuccess(true);
		}
		//统一抛出错误异常
		if (CollectionUtils.isNotEmpty(errorList)) {
			resultVo.setErrorList(errorList);
			resultVo.setIsSuccess(false);
			resultVo.setIsCommoditySubmitSuccess(false);
		}
		resultVo.setErrorSize(error);
		resultVo.setSuccessSize(voList.size()-error);
		return JSONObject.fromObject(resultVo).toString();
	}

	final static String COMMODITY_UPDATE_LOG_PREFIX = "updateCommodity#-> ";
	
	/**
	 * 修改商品
	 * @param submitVo 新增或修改商品 提交时 用的vo
	 * @param multiRequest 
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateCommodity")
	public String updateCommodity(
			CommoditySubmitVo submitVo,
			DefaultMultipartHttpServletRequest multiRequest,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String merchantCode=YmcThreadLocalHolder.getMerchantCode();
		CommoditySubmitResultVo resultVo = new CommoditySubmitResultVo();
		String addCommodity_key="merchant.updateCommodity:"+merchantCode+":"+submitVo.getStyleNo();
		//防止瞬时提交,2秒内
		Object addTime=redisTemplate.opsForValue().get(addCommodity_key);
		if(addTime!=null){
			Date existTime=(Date)addTime;
			Date now=new Date();
			if((now.getTime()-existTime.getTime())<2000){
				resultVo.setErrorMsg("请勿频繁提交!");
				return JSONObject.fromObject(resultVo).toString();
			}
		}else{
			redisTemplate.opsForValue().set(addCommodity_key, new Date());
			redisTemplate.expire(addCommodity_key, 4, TimeUnit.SECONDS);
		}
		//0000
		//判断是否按尺码修改价格
		//深圳测试4s932
		//正式线上4y644
		List<PropValue> propList = commodityBaseApiService.getPropValueListByCategoryIdAndItemNo(
				CommodityUtil.getCatIdByThreeCategory(submitVo.getThreeCategory()),priceBySizePropNo);
		List<ErrorVo> errorList = new ArrayList<ErrorVo>();
		if(StringUtils.isEmpty(submitVo.getCommodityNo())){
			resultVo.setErrorMsg("商品编号不能为空");
			return JSONObject.fromObject(resultVo).toString();
		}
		com.yougou.pc.model.commodityinfo.Commodity commodity = commodityApi.getCommodityByNo(submitVo.getCommodityNo());
		if (commodity == null) {
			resultVo.setErrorMsg("该商品不存在");
			return JSONObject.fromObject(resultVo).toString();
		}
		
		//新增或修改商品信息，初始化用户信息
		CommodityUtil.initMerchantUserInfoForAddOrUpdate(request, submitVo);
		 
		//设置商品基础配置
		submitVo.setSettings(settings);
		
		submitVo.validateUpdateCommodityForm(errorList, commodity, 
				merchantCommodityService.getCommodityCatNamesByCatStructName(
						CommodityUtil.getStructNameByThreeCategory(submitVo.getThreeCategory())),propList);
		//外链处理
		errorList=imageService.verifyCommodityProdDesc(submitVo,errorList);
		if (CollectionUtils.isNotEmpty(errorList)) {
			//如果为预览
			if (CommodityConstant.SUBMIT_COMMODITY_IS_PREVIEW_TRUE.equals(submitVo.getIsPreview())) {
				CommodityUtil.getAlertThenCloseScript(response, errorList.get(0).getErrMsg());
				return null;
			} else {
				resultVo.setErrorList(errorList);
				return JSONObject.fromObject(resultVo).toString();				
			}
		}
		//如果为预览
		if (CommodityConstant.SUBMIT_COMMODITY_IS_PREVIEW_TRUE.equals(
				submitVo.getIsPreview())) {
			CommodityVO commodityVO = new CommodityVO();
			commodityVO.setCommodityNo(submitVo.getCommodityNo());
			request.setAttribute(CommodityConstant.COMMODITY_PREVIEW_COMMODITYVO_KEY, commodityVO);
			request.setAttribute(CommodityConstant.COMMODITY_PREVIEW_COMMODITY_SUBMIT_VO_KEY, submitVo);
			request.getRequestDispatcher("/commodity/import/preview.sc").forward(request, response);
			return null;
		}
		
		//构造commodityUpdateVo
		com.yougou.pc.model.commodityinfo.Commodity commodityUpdateVo = CommodityUtil.buildCommodityUpdateVo(submitVo,propList);
		//判断修改的信息是不是原来的同款同色同年份
		String resultMsg = checkIsUnique(submitVo,commodityUpdateVo,commodity,propList,resultVo);
		if(resultMsg != null && resultMsg.length() > 0){
			return resultMsg;
		}
		commodityComponent.fillCommodityUpdateVo(commodityUpdateVo, commodity);
		try {
			// 如果商品还没有下架，先下架 added by liang.yx at 2015-3-6
			if(commodity.getStatus() != null && commodity.getStatus().intValue() == Constant.COMMODITY_STATUS_AVIL) {
				String _result = commodityApi.updateCommodityStatusForMerchant(commodity.getCommodityNo(), merchantCode, Constant.COMMODITY_STATUS_DOWN);
				log.warn("{}:修改前先下架，result:{}",commodity.getCommodityNo(), _result);
			}
			 
			//修改商品(先保存再上传商品描述图片，如先上传商品描述图片再保存宝贝描述会被当前商品对象再次改写)
			String result = commodityApi.saveAllCommodityMsgForMerchant(commodityUpdateVo);
			log.info("{} result: {}", COMMODITY_UPDATE_LOG_PREFIX ,  result);
			if (!RESULT_SUCCESS.equals(result)) {
				resultVo.setErrorMsg(result);
				return JSONObject.fromObject(resultVo).toString();
			}
			
			// 判断停售和审核通过的商品提交修改
			if (commodity.getStatus()==1||commodity.getStatus()==13||commodity.getStatus()==5) {
				commodityApi.updateCommodityStatusForMerchant(commodity.getCommodityNo(), submitVo.getMerchantCode(), 11);
			}
			
			//发送JMS消息通知Image处理图片
			commodityService.sendJmsForMaster(submitVo, commodity.getCommodityDesc() == null ? 0 : commodity.getCommodityDesc().length());
			
			//更新库存
			if ((CommodityConstant.SUPPLIER_IS_INPUT_YOUGOU_WAREHOUSE_NOT_IN + "")
					.equals(submitVo.getIsInputYougouWarehouse())) {
				
				String updateStockErrorMsg = commodityComponent.updateStockForAdd(submitVo, resultVo, null);
				if (updateStockErrorMsg != null && updateStockErrorMsg.length() != 0) {
					resultVo.setErrorMsg(updateStockErrorMsg);
					return JSONObject.fromObject(resultVo).toString();
				}
			}
			
			//清除被删除货品的库存
			if (!commodityComponent.clearDeletedProdInventory(submitVo, resultVo, commodity)) {
				return JSONObject.fromObject(resultVo).toString();
			}
			//如果是保存并提交审核
			if (CommodityConstant.SUBMIT_COMMODITY_IS_SAVE_SUBMIT_TRUE
					.equals(submitVo.getIsSaveSubmit())) {
				//校验图片完整性
				if (!merchantCommodityService.checkCommodityPicsIntegrality(
						submitVo.getCommodityNo(), submitVo.getSupplierCode(), submitVo.getMerchantCode(),submitVo.getBrandNo())) {
					resultVo.setErrorMsg("图片完整性校验异常");
					return JSONObject.fromObject(resultVo).toString();
				}
				//提交审核
				if (!commodityApi.auditMerchant(submitVo.getCommodityNo(), 
						submitVo.getMerchantCode())) {
					resultVo.setErrorMsg("提交审核失败");
					return JSONObject.fromObject(resultVo).toString();
				}
				//设置为保存并提交审核
				resultVo.setIsAddCommoditySaveSubmit(true);
			}
		} catch (RuntimeException e) {
			log.error("修改商品运行时异常:",e);
			if (e.getClass().getName().equals("java.lang.RuntimeException")) {
				resultVo.setErrorMsg(e.getMessage());
			} else {
				resultVo.setErrorMsg("网络超时,请刷新后重新提交");
			}
			return JSONObject.fromObject(resultVo).toString();
		} catch (Exception e) {
			log.error("修改商品产生异常:",e);
			resultVo.setErrorMsg("网络超时,请刷新后重新提交");
			return JSONObject.fromObject(resultVo).toString();
		} 
		resultVo.setIsSuccess(true);
		return JSONObject.fromObject(resultVo).toString();
	}
	
	/**
	 * checkIsUnique:
	 * 按尺码设置价格分类修改：根据款号，颜色，年份，尺码判断是否已存在货品
	 * 非按尺码设置价格类修改：根据款号，颜色，年份判断是否已存在商品
	 * @author li.n1 
	 * @param submitVo
	 * @param commodityUpdateVo
	 * @param commodity
	 * @param propList
	 * @param resultVo
	 * @return 
	 * @since JDK 1.6
	 */
	private String checkIsUnique(CommoditySubmitVo submitVo,
			com.yougou.pc.model.commodityinfo.Commodity commodityUpdateVo,
			com.yougou.pc.model.commodityinfo.Commodity commodity, 
			List<PropValue> propList, 
			CommoditySubmitResultVo resultVo) {
		if(propList!=null&&propList.size()>0){
			if(!(commodityUpdateVo.getStyleNo().equals(commodity.getStyleNo()))
				|| !(commodityUpdateVo.getColorName().equals(commodity.getColorName()))
				|| !(commodityUpdateVo.getYears().equals(commodity.getYears()))
				|| !(submitVo.getSizeNo()[0].equals(commodity.getProducts().get(0).getSizeNo()))){
				//修改了其中一个
				//按尺码设置价格的分类还要判断尺码是否使用过
				if(!(commodityService.check(submitVo.getMerchantCode(),commodityUpdateVo.getStyleNo(),
						commodityUpdateVo.getColorName(),
						commodityUpdateVo.getYears(),submitVo.getSizeNo(),propList))){
					//不可修改
					StringBuffer msg = new StringBuffer("已存在同款："+
							commodityUpdateVo.getStyleNo()+"，同色："+submitVo.getSpecName()+
							"，同年份："+commodityUpdateVo.getYears()+"，且同尺码："+submitVo.getSizeName()[0]);
					resultVo.setErrorMsg(msg.append("的商品！").toString());
					return JSONObject.fromObject(resultVo).toString();
				}
			}
		}else{
			if(!(commodityUpdateVo.getStyleNo().equals(commodity.getStyleNo()))
					|| !(commodityUpdateVo.getColorName().equals(commodity.getColorName()))
					|| !(commodityUpdateVo.getYears().equals(commodity.getYears()))){
				//修改了其中一个
				//判断该款同色同年份下是否存在商品编码，存在则提示不允许修改
				//按尺码设置价格的分类还要判断尺码是否使用过
				if(!(commodityService.check(submitVo.getMerchantCode(),commodityUpdateVo.getStyleNo(),
						commodityUpdateVo.getColorName(),
						commodityUpdateVo.getYears(),null,null))){
					//不可修改
					StringBuffer msg = new StringBuffer("已存在同款："+
							commodityUpdateVo.getStyleNo()+"，同色："+submitVo.getSpecName()+
							"，同年份："+commodityUpdateVo.getYears());
					resultVo.setErrorMsg(msg.append("的商品！").toString());
					return JSONObject.fromObject(resultVo).toString();
				}
			}
		}
		return null;
	}

	/**
	 * 提交商品图片(批量)
	 * @param imgFiles
	 * @param descImages
	 * @param commodityNo
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/uploadImage4Commodity")
	public String uploadImage4Commodity(String imgFiles, String descImages,
			String commodityNo, ModelMap model, HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		JSONObject jsonObj = new JSONObject();
		try {
			if(StringUtils.isEmpty(imgFiles)){
				jsonObj.put("result", ResultEnums.FAIL.getResultMsg());
				jsonObj.put("msg", "焦点图不能为空");
				return jsonObj.toString();
			}

			if (StringUtils.isEmpty(descImages)) {
				jsonObj.put("result", ResultEnums.FAIL.getResultMsg());
				jsonObj.put("msg", "描述图不能为空");
				return jsonObj.toString();
			}

			if (StringUtils.isEmpty(commodityNo)) {
				jsonObj.put("result", ResultEnums.FAIL.getResultMsg());
				jsonObj.put("msg", "商品编码不能为空");
				return jsonObj.toString();
			}

			com.yougou.pc.model.commodityinfo.Commodity commodity = commodityApi
					.getCommodityByNo(commodityNo);
			if (commodity == null) {
				jsonObj.put("result", ResultEnums.FAIL.getResultMsg());
				jsonObj.put("msg", "该件商品不存在");
				return jsonObj.toString();
			}
			String[] imgFileArray = {"0", "0", "0", "0", "0", "0", "0"};
			
			String[] tempImgs = imgFiles.split(",");
			for (int i = 0, length = tempImgs.length; i < length; i++) {
				imgFileArray[i] = tempImgs[i];
			}
			CommoditySubmitVo submitVo = new CommoditySubmitVo();
			submitVo.setStructName(commodity.getCatStructName());
			submitVo.setImgFileId(imgFileArray);
			submitVo.setCommodityNo(commodityNo);
			submitVo.setMerchantCode(commodity.getMerchantCode());
			submitVo.setBrandNo(commodity.getBrandNo());
			submitVo.setYears(commodity.getYears());
			String[] descImageArray = descImages.split(",");
			StringBuilder prodDesc = new StringBuilder();
			for (int i = 0, length = descImageArray.length; i < length; i++) {
				prodDesc.append("<img src=\"" + descImageArray[i] + "\">");
			}
			submitVo.setProdDesc(prodDesc.toString());
			commodityService.sendJms4SingleCommodityImg(submitVo);
		} catch (Exception e) {
			log.error("图片上传异常:", e);
			jsonObj.put("result", ResultEnums.FAIL.getResultMsg());
			jsonObj.put("msg", e.getMessage());
			return jsonObj.toString();
		}
		jsonObj.put("result", ResultEnums.SUCCESS.getResultMsg());
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		log.warn("统计:批量导入商品-图片上传.商家:{} 商品编码:{}", new Object[]{merchantCode,commodityNo});
		return jsonObj.toString();
	}

	/**
	 * 通过 商品编号 获取
	 * @param commodityNo 商品编号
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/getCommodityByNo")
	public String getCommodityByNo(String commodityNo, 
			HttpServletRequest request, ModelMap model) throws Exception {
		final String IS_SUCCESS_KEY = "isSuccess";
		final String ERROR_MSG_KEY = "errorMsg";
		final String COMMODITY_KEY = "commodity";
		final String IMAGE_CACHE = "imageCache";
		
		com.alibaba.fastjson.JSONObject jsonObj = new com.alibaba.fastjson.JSONObject();
		jsonObj.put(IS_SUCCESS_KEY, "false");
		jsonObj.put(ERROR_MSG_KEY, "该件商品不存在");
		
		commodityNo = CommonUtil.getTrimString(commodityNo);
		if (commodityNo.length() == 0) return jsonObj.toString();
		
		com.yougou.pc.model.commodityinfo.Commodity commodity = commodityApi.getCommodityByNo(commodityNo);
		if (commodity == null) jsonObj.toString();
		
		//图片缓存
		Object message = this.redisTemplate.opsForHash().get(CacheConstant.C_IMAGE_MASTER_JMS_KEY, commodityNo);
		if (message != null) jsonObj.put(IMAGE_CACHE, message);
		
		//获取货品的销售状态
		
		List<Product> products=commodity.getProducts();
		int count=0;
		if(products!=null&&products.size()>0){
			for(Product product:products){
				count=commodityService.querySaleCountByroductNo(product.getProductNo());
				if(count>0){
					product.setSellStatus(2);
				}
			}
		}
		//判断是否按尺码修改价格
		//深圳测试4s932
		//正式线上4y644
		Category category = commodityBaseApiService.getCategoryByNo(commodity.getCatNo());
		List<PropValue> propList = commodityBaseApiService.getPropValueListByCategoryIdAndItemNo(
				category.getId(),priceBySizePropNo);
		//有按尺码修改价格属性
		if(propList!=null&&propList.size()>0){
			jsonObj.put("isSizePrice", 1);
		}
		jsonObj.put(IS_SUCCESS_KEY, "true");
		jsonObj.put(COMMODITY_KEY, commodity);
		return jsonObj.toString();
	}
	
	/**
	 * 通过 货品编号串 获取 库存
	 * @param prodNoStr 货品编号串，用英文逗号分隔
	 * @param request 
	 * @param model
	 * @return 
	 */
	@Deprecated
	@ResponseBody
	@RequestMapping("/getInventoryByProdNos")
	public String getInventoryByProdNos(String prodNoStr, HttpServletRequest request,
			ModelMap model) {
		String prefix = "getInventoryByProdNos#-> ";
		JSONObject jsonObj = new JSONObject();
		prodNoStr = CommonUtil.getTrimString(prodNoStr);
		if (prodNoStr.length() == 0) return jsonObj.toString();
		String[] prodNos = prodNoStr.split(",");
		if (prodNos == null || prodNos.length == 0) return jsonObj.toString();
		
		//商家编号
		//String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		//仓库编码
		String wareHouseCode = SessionUtil.getWarehouseCodeFromSession(request);
		if (StringUtils.isBlank(wareHouseCode)) {
			return jsonObj.toString();
		}
		//货品编号 / 库存 map
		Map<String, Integer> prodInventoryMap = new HashMap<String, Integer>();
		Map<Integer, Date> inventoryMap = null;
		for (int i = 0, len = prodNos.length; i < len; i++) {
			inventoryMap = inventoryDomainService.querySalesInventroyForMerchant(
					prodNos[i], wareHouseCode);
			if (inventoryMap == null || inventoryMap.size() == 0) {
				continue;
			}
			Integer stock = null;
			for (Integer stockTmp : inventoryMap.keySet()) {
				stock = stockTmp;
				break;
			}
			if (stock == null) {
				continue;
			}
			prodInventoryMap.put(prodNos[i], stock);
		}
		jsonObj = JSONObject.fromObject(prodInventoryMap);
		String result = jsonObj.toString();
		log.info("{} result: {}", prefix, result);
		return result;
	}
	
	
	/**
	 * 通过 货品编号串 获取 库存
	 * @param prodNoStr 货品编号串，用英文逗号分隔
	 * @param request 
	 * @param model
	 * @return 
	 */
	@ResponseBody
	@RequestMapping("/getInventoryByProdNo")
	public String getInventoryByProdNo(String prodNoStr, HttpServletRequest request,
			ModelMap model) {
		String prefix = "getInventoryByProdNos#-> ";
		JSONObject jsonObj = new JSONObject();
		prodNoStr = CommonUtil.getTrimString(prodNoStr);
		if (prodNoStr.length() == 0) return jsonObj.toString();
		String[] prodNos = prodNoStr.split(",");
		if (prodNos == null || prodNos.length == 0) return jsonObj.toString();
		
		//商家编号
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		//仓库编码
		String wareHouseCode = SessionUtil.getWarehouseCodeFromSession(request);
		if (StringUtils.isBlank(wareHouseCode)) {
			return jsonObj.toString();
		}
		 Map<String, InventoryAssistVo> qtyMap = new HashMap<String, InventoryAssistVo>();
		 List<String> productNoList = new ArrayList<String>();
        for (String _obj : prodNos) {
            productNoList.add(_obj);
        }
        if (CollectionUtils.isNotEmpty(productNoList)) {
            try {
                List<InventoryAssistVo> qtys = null;
                if (StringUtils.isNotBlank(wareHouseCode))
                    qtys = inventoryForMerchantService.queryProductInventory(productNoList, wareHouseCode,0);
                else
                    qtys = inventoryForMerchantService.queryInvenotryByProduct(productNoList); // 非商家仓库暂时不统计残品库存
                if (CollectionUtils.isNotEmpty(qtys)) {
                    for (InventoryAssistVo qtyVo : qtys) {
                        qtyMap.put(qtyVo.getProductNo(), qtyVo);
                    }
                }
            } catch (WPIBussinessException e) {
            	log.error("查询queryProductInventory发生异常.", e);
            }
        }
		//货品编号 / 库存 map
		Map<String, Integer> prodInventoryMap = new HashMap<String, Integer>();
		Integer safeStockQuantity = null;
		InventoryAssistVo qty=null;
		for (int i = 0, len = prodNos.length; i < len; i++) {
			safeStockQuantity = stockService.getSafeStockQuantityByProductNo(merchantCode, prodNos[i]);
			if(safeStockQuantity==null){
				safeStockQuantity = 0;
			}
			qty = (InventoryAssistVo) MapUtils.getObject(qtyMap, prodNos[i]);
			prodInventoryMap.put(prodNos[i], null == qty ? 0 : qty.getInventoryQuantity() + safeStockQuantity);
		}
		jsonObj = JSONObject.fromObject(prodInventoryMap);
		String result = jsonObj.toString();
		log.info("{} result: {}",prefix, result);
		return result;
	}
	
	/**
	 * 商品提交审批
	 * @param commodityNo 商品编号
	 * @param supplierCode 商家款色编码
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/submitAudit")
	public String submitAudit(String commodityNo, HttpServletRequest request, 
			ModelMap model) {
		JSONObject jsonObj = new JSONObject();
		final String SUCCESS_KEY = "success";
		final String MSG_KEY = "msg";
		jsonObj.put(SUCCESS_KEY, "false");
		jsonObj.put(MSG_KEY, "该商品提交审核失败");
		
		commodityNo = CommonUtil.getTrimString(commodityNo);
		if (commodityNo.length() == 0) {
			jsonObj.put(MSG_KEY, "商品编号不能为空,请检查!");
			return jsonObj.toString();
		}
		
		//商家编号
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		
		try{
			com.yougou.pc.model.commodityinfo.Commodity commodity = commodityApi.getCommodityByNo(commodityNo);
			final String desc = commodity.getCommodityDesc();
			if(desc == null || desc.indexOf("<img") < 1) {
				jsonObj.put(MSG_KEY, "商品描述中的没有对应的图片信息");
				return jsonObj.toString();
			}
			//校验图片完整性
			if (!merchantCommodityService.checkCommodityPicsIntegrality(
					commodityNo, commodity.getSupplierCode(), merchantCode,commodity.getBrandNo())) {
				jsonObj.put(MSG_KEY, "图片完整性校验异常");
				return jsonObj.toString();
			}
			
			//提交审核
			if (!commodityApi.auditMerchant(commodityNo, merchantCode)) {
				jsonObj.put(MSG_KEY, "提交审核失败");
				return jsonObj.toString();
			}
			//插入敏感词日志
			insertSensitiveWordLog(false,commodity);
			
		} catch (RuntimeException e) {
			log.error("提交审批运行时异常,commodityNo:{}",commodityNo,e);
			if (e.getClass().getName().equals("java.lang.RuntimeException")) {
				jsonObj.put(MSG_KEY, e.getMessage());
			} else {
				jsonObj.put(MSG_KEY, "网络超时,请刷新后重新提交");
			}
			return jsonObj.toString();
		} catch (Exception e) {
			log.error("提交审批产生异常:",e);
			jsonObj.put(MSG_KEY, "网络超时,请刷新后重新提交");
			return jsonObj.toString();
		} 
		jsonObj.put(SUCCESS_KEY, "true");
		return jsonObj.toString();
	}

	/**
	 * 插入敏感词日志
	 * @param commodity
	 */
	private Map<String,String> insertSensitiveWordLog(boolean isNeedCheck,com.yougou.pc.model.commodityinfo.Commodity... commoditys) {
		Map<String,String> sensitiveResult = new HashMap<String,String>();
		List<SensitiveCheckLog> sensitiveCheckLogs = new ArrayList<SensitiveCheckLog>();
		try{		
			for(com.yougou.pc.model.commodityinfo.Commodity commodity : commoditys){
				CommodityExtend commodityExtend = commodityExtendService.getCommodityExtendByCommodityNo(commodity.getCommodityNo());
				String sensitiveWords = (commodityExtend == null ? "" : commodityExtend.getSensitiveWord());
				if(isNeedCheck){
					sensitiveWords = commodityComponent.checkSensitiveWord(null, commodity.getCommodityName()+commodity.getSellingPoint()+commodity.getCommodityDesc());
					if(commodityExtend == null){
						if(StringUtils.isNotBlank(sensitiveWords)){					
							//增加商品扩展表数据
							commodityExtend = new CommodityExtend();
							commodityExtend.setId(UUIDGenerator.getUUID());
							commodityExtend.setCommodityNo(commodity.getCommodityNo());
							commodityExtend.setSensitiveWord(sensitiveWords);
							commodityExtendService.insertCommodityExtend(commodityExtend);
						}
					}else{
						commodityExtend.setSensitiveWord(sensitiveWords);
						commodityExtendService.updateCommodityExtend(commodityExtend);
					}
				}
				//插入敏感词日志
				SensitiveCheckLog sensitiveLog = new SensitiveCheckLog();
				sensitiveLog.setCommodityNo(commodity.getCommodityNo());
				sensitiveLog.setContent("标题："+commodity.getCommodityName()+"卖点："+commodity.getSellingPoint()+"描述："+commodity.getCommodityDesc());
				if(isNeedCheck){
					sensitiveLog.setComment("导入修改商品");
					sensitiveLog.setFollowOperate((short)0);
				}else{
					sensitiveLog.setComment("待售商品提交审核");
					sensitiveLog.setFollowOperate(StringUtils.isNotBlank(sensitiveWords) ? (short)1 : (short)0);
				}
				sensitiveLog.setOperatorPerson(YmcThreadLocalHolder.getOperater());
				sensitiveLog.setSensitive(StringUtils.isNotBlank(sensitiveWords) ? true : false);
				sensitiveLog.setSensitiveWord(sensitiveWords);
				sensitiveLog.setStyleNo(commodity.getStyleNo());
				sensitiveLog.setSupplierCode(commodity.getSupplierCode());
				sensitiveLog.setOperateTime(new Date());
				sensitiveLog.setOperateType(SensitiveCheckLog.OperateType.UPDATE.getValue());
				sensitiveLog.setSource(SensitiveCheckLog.Source.MERCHANT.getValue());
				sensitiveCheckLogs.add(sensitiveLog);
				if(StringUtils.isNotBlank(sensitiveWords)){
					if(!sensitiveResult.containsKey(sensitiveWords)){					
						sensitiveResult.put(sensitiveWords, commodity.getSupplierCode());
					}else{
						sensitiveResult.put(sensitiveWords, sensitiveResult.get(sensitiveWords) + ","+ commodity.getSupplierCode());
					}
				}
			}
			boolean result = commodityComponent.insertSensitiveWordCheckLogByBatch(sensitiveCheckLogs);
			if(!result){
				log.error("待售商品插入敏感词：{}，日志失败",JSONArray.fromObject(sensitiveCheckLogs).toString());
			}
		}catch(Exception e){
			log.error("待售商品插入敏感词日志{}出现异常",JSONArray.fromObject(sensitiveCheckLogs).toString(),e);
		}
		return sensitiveResult;
	}
	
	/**
	 * 商品提交审批
	 * @param commodityNo 商品编号
	 * @param supplierCode 商家款色编码
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/submitAuditBatch")
	public String submitAuditBatch(String commodityNos, HttpServletRequest request, 
			ModelMap model) {
		//String supplierCode="";
		StringBuilder sb=new StringBuilder();
		//System.out.println(commodityNos);
		String[] commodityNoArry=commodityNos.split(",");
		String jsonStr="";
		JSONObject jsonObj = new JSONObject();
		for(String commodityNo:commodityNoArry){
			//supplierCode=StringUtils.substringAfter(commodityNo, "_");
			commodityNo=StringUtils.substringBefore(commodityNo, "_");
			jsonStr=this.submitAudit(commodityNo,request,model);
			sb.append("{"+commodityNo+":"+("true".equalsIgnoreCase(JSONObject.fromObject(jsonStr).getString("success"))?"ok":"&otimes;")+"}<br/>");
		}
		jsonObj.put("back", sb.toString());
		return jsonObj.toString();
	}
	
	/**
	 * 预览商品
	 * @param commodityNo 商品编号
	 * @param request
	 * @param model
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@ResponseBody
	@RequestMapping("/previewDetail")
	public void previewDetail(String commodityNo, HttpServletRequest request, ModelMap model,
			HttpServletResponse response) throws ServletException, IOException  {
		commodityNo = CommonUtil.getTrimString(commodityNo);
		CommodityVO commodityVO = new CommodityVO();
		CommoditySubmitVo submitVo = new CommoditySubmitVo();
		commodityVO.setCommodityNo(commodityNo);
		request.setAttribute(CommodityConstant.COMMODITY_PREVIEW_COMMODITYVO_KEY, commodityVO);
		request.setAttribute(CommodityConstant.COMMODITY_PREVIEW_COMMODITY_SUBMIT_VO_KEY, submitVo);
		request.getRequestDispatcher("/commodity/import/preview.sc").forward(request, response);
	}
	
	
	/**
	 * <p>待售商品查询入口</p>
	 * 
	 * @param modelMap
	 * @param request
	 * @param query
	 * @param queryVo
	 * @param firstFlag  标记是否第一次进入页面
	 * @return 
	 * @throws Exception 
	 */
	@RequestMapping("goWaitSaleCommodity")
	public String goForSaleCommodity(ModelMap modelMap,
			HttpServletRequest request, Query query,
			CommodityQueryVo queryVo) throws Exception {
		modelMap.put("tagTab", "goods");
		//加入当前商家的信息
		queryVo.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
		queryVo.setWarehouseCode(SessionUtil.getWarehouseCodeFromSession(request));
		queryVo.setStructName(CommodityUtil.getCatStructName(queryVo));
		queryVo.setSensitive(false);
		
		// 处理批量导入描述图片后、而未处理的商品描述字符串
		merchantCommodityService.commodityDescRedisOpt(queryVo.getMerchantCode());
		query.setPageSize(20);
		PageFinder<Map<String, Object>> pageFinder = commodityService.queryWaitSaleCommodityList(query,	queryVo, modelMap);
		
		initCommonProperty(modelMap);
		
		if (!isEmptyForPageFinder(pageFinder)) {
			modelMap.put("pageFinder", pageFinder);
		}
		
		modelMap.put("commodityQueryVo", queryVo);
		return "/manage/commodity/wait_sale_commodity";
	}
	
	/**
	 * goWaitSaleNoSensitiveCommodity:查询敏感词列表
	 * @author li.n1 
	 * @param modelMap
	 * @param request
	 * @param query
	 * @param queryVo
	 * @return
	 * @throws Exception 
	 * @since JDK 1.6 
	 * @date 2015-9-17 下午5:14:51
	 */
	@RequestMapping("goWaitSaleSensitiveCommodity")
	public String goWaitSaleNoSensitiveCommodity(ModelMap modelMap,
			HttpServletRequest request, Query query,
			CommodityQueryVo queryVo) throws Exception {
		modelMap.put("tagTab", "goods");
		//加入当前商家的信息
		queryVo.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
		queryVo.setWarehouseCode(SessionUtil.getWarehouseCodeFromSession(request));
		queryVo.setStructName(CommodityUtil.getCatStructName(queryVo));
		queryVo.setSensitive(true);
		
		// 处理批量导入描述图片后、而未处理的商品描述字符串
		merchantCommodityService.commodityDescRedisOpt(queryVo.getMerchantCode());
		query.setPageSize(20);
		PageFinder<Map<String, Object>> pageFinder = commodityService.queryWaitSaleCommodityList(query,	queryVo, modelMap);
		
		initCommonProperty(modelMap);
		
		if (!isEmptyForPageFinder(pageFinder)) {
			modelMap.put("pageFinder", pageFinder);
		}
		
		modelMap.put("commodityQueryVo", queryVo);
		return "/manage/commodity/wait_sale_sensitive_commodity";
	}
	
	
	@ResponseBody
	@RequestMapping("/createWaitSaleCommodity")
	public String createWaitSaleCommodity(HttpServletRequest request, HttpServletResponse response, 
			CommodityQueryVo commodityQueryVo){
		Map<String,Object> map = null;
		try{
			map = exportWaitSaleCommodity(request,response,commodityQueryVo);
		}catch(Exception e){
			log.error("服务器发生异常，导出失败！",e);
		}
		return JSONObject.fromObject(map).toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/createWaitSaleCommodityByCommRunnable",method = RequestMethod.POST)
	public String createWaitSaleCommodityByCommRunnable(final HttpServletRequest request,
			final HttpServletResponse response,final CommodityQueryVo vo){
		vo.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
		final String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		final HttpServletRequest rq=request;
		final HttpServletResponse rp=response;
//		redisTemplate.delete(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_KEY+":"+merchantCode);
//		redisTemplate.delete(CacheConstant.C_USER_ENTER_METHOD+":createWaitSaleCommodityByCommRunnable:"+merchantCode);
//		redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_KEY+":"+merchantCode, -1.0d);
		vo.setWarehouseCode(SessionUtil.getWarehouseCodeFromSession(request));
		//commodityQueryVo.setWarehouseCode(SessionUtil.getWarehouseCodeFromSession(request));
		vo.setStructName(CommodityUtil.getCatStructName(vo));
		final double progress = 5.0d;
		Map<String,Object> hashMap = (Map<String,Object>)redisTemplate.opsForValue().get(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_PRODANALYSE+":"+merchantCode);
    	String isExportData = (String)redisTemplate.opsForValue().get(CacheConstant.C_USER_ENTER_METHOD+":createWaitSaleCommodityByCommRunnable:"+merchantCode);
		if(isExportData!=null){
    		//执行过导出excel
    		if(hashMap==null){
    			//未执行完
    			return "loading";
    		}
		}
		vo.setMerchantCode(merchantCode);
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
						hashMap = exportWaitSaleCommodityByComm(rq,rp,vo,progress);
						redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_KEY+":"+merchantCode, 100.0d);
					} catch (Exception e) {
						hashMap = null;
						log.error("导出商品出现错误：",e);
						//删除缓存，不再进行处理
						redisTemplate.delete(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_KEY+":"+merchantCode);
			    		redisTemplate.delete(CacheConstant.C_USER_ENTER_METHOD+":createWaitSaleCommodityByCommRunnable:"+merchantCode);
						redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_KEY+":"+merchantCode, -1.0d);
					}finally{
						redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_PRODANALYSE+":"+merchantCode, hashMap);
					}
				}
			});*/
			Runnable run = new Runnable() {
				Map<String,Object> hashMap = null;
				//进度值默认起始值
				@Override
				public void run() {
					try {
						log.warn("线程{},开始处理代售商品按商品导出...", Thread.currentThread().getName());
						hashMap = exportWaitSaleCommodityByComm(rq,rp,vo,progress);
						redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_KEY+":"+merchantCode, 100.0d);
						log.warn("线程{},结束处理代售商品按商品导出...", Thread.currentThread().getName());
					} catch (Exception e) {
						hashMap = null;
						log.error("导出商品出现错误：",e);
						//删除缓存，不再进行处理
						redisTemplate.delete(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_KEY+":"+merchantCode);
			    		redisTemplate.delete(CacheConstant.C_USER_ENTER_METHOD+":createWaitSaleCommodityByCommRunnable:"+merchantCode);
						redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_KEY+":"+merchantCode, -1.0d);
					}finally{
						redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_PRODANALYSE+":"+merchantCode, hashMap);
					}
				}
			};
			Thread thread = new Thread(run);
			thread.setName("createWaitSaleCommodityByCommRunnable 代售商品按商品导出");
			thread.start();
			
			//缓存进度值
			redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_KEY+":"+merchantCode, progress);
			//表示已进入该方法，设置过安全库存
		    redisTemplate.opsForValue().set(CacheConstant.C_USER_ENTER_METHOD+":createWaitSaleCommodityByCommRunnable:"+merchantCode,"enter");
		    redisTemplate.expire(CacheConstant.C_USER_ENTER_METHOD+":createWaitSaleCommodityByCommRunnable:"+merchantCode, 1, TimeUnit.HOURS);
		    //60分钟之内没有下载完，就失效
		    redisTemplate.expire(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_PRODANALYSE+":"+merchantCode, 1, TimeUnit.HOURS);
		    redisTemplate.expire(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_KEY+":"+merchantCode, 1, TimeUnit.HOURS);
		}catch(Exception e){
			log.error("导出商品信息发生错误！",e);
		}finally{
			if (threadPool != null) {
				threadPool.shutdown();
				threadPool = null;
			}
		}
		return "success";
	}
	@ResponseBody
	@RequestMapping("/getExportResultSize")
	public String getExportResultSize(){
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		Map<String,Object> hashMap = (Map<String,Object>)redisTemplate.opsForValue().get(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_SIZE_PRODANALYSE+":"+merchantCode);
		Double progress = (Double)redisTemplate.opsForValue().get(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_SIZE_KEY+":"+merchantCode);
		if(hashMap!=null){
			//已经生成excel，可供下载
			hashMap.put("result", "true");
			//删除缓存

			redisTemplate.delete(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_SIZE_PRODANALYSE+":"+merchantCode);
    		redisTemplate.delete(CacheConstant.C_USER_ENTER_METHOD+":createWaitSaleCommodityBySizeRunnable:"+merchantCode);
    		redisTemplate.delete(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_SIZE_KEY+":"+merchantCode);
		}else{
			hashMap = new HashMap<String,Object>();
			hashMap.put("result", "false");
		}
		hashMap.put("progress", progress);
		return JSONObject.fromObject(hashMap).toString();
	}
	@ResponseBody
	@RequestMapping("/getExportResultComm")
	public String getExportResultComm(){
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		Map<String,Object> hashMap = (Map<String,Object>)redisTemplate.opsForValue().get(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_PRODANALYSE+":"+merchantCode);
		Double progress = (Double)redisTemplate.opsForValue().get(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_KEY+":"+merchantCode);
		if(hashMap!=null){
			//已经生成excel，可供下载
			hashMap.put("result", "true");
			//删除缓存
			redisTemplate.delete(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_PRODANALYSE+":"+merchantCode);
    		redisTemplate.delete(CacheConstant.C_USER_ENTER_METHOD+":createWaitSaleCommodityByCommRunnable:"+merchantCode);
    		redisTemplate.delete(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_KEY+":"+merchantCode);
		}else{
			hashMap = new HashMap<String,Object>();
			hashMap.put("result", "false");
		}
		hashMap.put("progress", progress);
		return JSONObject.fromObject(hashMap).toString();
	}
	@ResponseBody
	@RequestMapping("/createWaitSaleCommodityByComm")
	public String createWaitSaleCommodityByComm(HttpServletRequest request, HttpServletResponse response, 
			CommodityQueryVo commodityQueryVo){
		double progress = 0;
		Map<String,Object> map = null;
		try{
			map = exportWaitSaleCommodityByComm(request,response,commodityQueryVo,progress);
		}catch(Exception e){
			log.error("服务器发生异常，导出失败！",e);
		}
		return JSONObject.fromObject(map).toString();
	}
	//按商品导出
	private Map<String, Object> exportWaitSaleCommodityByComm(
			HttpServletRequest request, HttpServletResponse response,
			CommodityQueryVo commodityQueryVo,double progress) throws Exception {
		//加入当前商家的信息
		
		Query query = new Query(500);
		progress = progress +5.0d;
		redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_KEY+":"+commodityQueryVo.getMerchantCode(), progress);
		//查询主数据
		List<Commodity> commodityList = commodityService.exportWaitSaleCommodityList(commodityQueryVo,query);
		
		progress = progress +5.0d;
		redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_KEY+":"+commodityQueryVo.getMerchantCode(), progress);
		
		//查询可售库存
		List<String> commodityNoList = new ArrayList<String>(0);
		for(Commodity c : commodityList) {
			commodityNoList.add(c.getCommodityNo());
		}
		Map<String, Integer> qtyMap = new HashMap<String, Integer>();
        try {
            List<InventoryAssistVo> qtys = null;
            if (StringUtils.isNotBlank(commodityQueryVo.getWarehouseCode())&&commodityNoList.size()>0) {
                qtys = inventoryForMerchantService.queryCommodityInventory(commodityNoList, commodityQueryVo.getWarehouseCode());
                if (CollectionUtils.isNotEmpty(qtys)) {
                    for (InventoryAssistVo qtyVo : qtys) {
                        qtyMap.put(qtyVo.getCommodityNo(), qtyVo.getCanSalesInventoryNum());
                    }
                }
            } else {
                qtys = inventoryForMerchantService.queryInvenotryByCommodity(commodityNoList);
                if (CollectionUtils.isNotEmpty(qtys)) {
                    for (InventoryAssistVo qtyVo : qtys) {
                        qtyMap.put(qtyVo.getCommodityNo(), qtyVo.getInventoryQuantity());
                    }
                }
            }
        } catch (WPIBussinessException e) {
			log.error("查询queryCommodityInventory发生异常.", e);
		}
        double tempProgress = 0;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("待售商品列表");
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,8));
		int rowInd = 0, colInd = 0;
		HSSFCellStyle style = workbook.createCellStyle(); // 样式对象  
		HSSFRow row = sheet.createRow(rowInd++);
		HSSFFont font=workbook.createFont();
        font.setColor(HSSFColor.RED.index); //字体颜色红色
        style.setFont(font);
		row.createCell(colInd).setCellValue("注意：如需修改商品资料，红色底的字段不可修改，灰色底字段可修改。");
		row.getCell(colInd).setCellStyle(style);
		colInd = 0;
		HSSFPatriarch patr = sheet.createDrawingPatriarch();
		row = sheet.createRow(rowInd++);
		style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.CORAL.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		row.createCell(colInd).setCellValue("一级分类");
		row.getCell(colInd).setCellStyle(style);
		HSSFComment comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
		comment.setString(new HSSFRichTextString("一级分类不可修改或删除"));
		row.getCell(colInd).setCellComment(comment);
		colInd++;
		row.createCell(colInd).setCellValue("品牌");
		row.getCell(colInd).setCellStyle(style);
		comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
		comment.setString(new HSSFRichTextString("品牌不可修改或删除"));
		row.getCell(colInd).setCellComment(comment);
		colInd++;
		row.createCell(colInd).setCellValue("商品编码");
		row.getCell(colInd).setCellStyle(style);
		comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
		comment.setString(new HSSFRichTextString("商品编码不可修改或删除"));
		row.getCell(colInd).setCellComment(comment);
		colInd++;
		style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		row.createCell(colInd).setCellValue("颜色");
		row.getCell(colInd).setCellStyle(style);
		comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
		comment.setString(new HSSFRichTextString("颜色可修改，修改后替换原有颜色"));
		row.getCell(colInd).setCellComment(comment);
		colInd++;
		row.createCell(colInd).setCellValue("商品名称");
		row.getCell(colInd).setCellStyle(style);
		comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
		comment.setString(new HSSFRichTextString("商品名称可修改，修改后替换原有商品名称"));
		row.getCell(colInd).setCellComment(comment);
		colInd++;
		row.createCell(colInd).setCellValue("商品款号");
		row.getCell(colInd).setCellStyle(style);
		comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
		comment.setString(new HSSFRichTextString("商品款号可修改，修改后替换原有商品款号"));
		row.getCell(colInd).setCellComment(comment);
		colInd++;
		row.createCell(colInd).setCellValue("款色编码");
		row.getCell(colInd).setCellStyle(style);
		comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
		comment.setString(new HSSFRichTextString("款色编码可修改，修改后替换原有款色编码"));
		row.getCell(colInd).setCellComment(comment);
		colInd++;
		row.createCell(colInd).setCellValue("市场价");
		row.getCell(colInd).setCellStyle(style);
		comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
		comment.setString(new HSSFRichTextString("市场价可修改，修改后替换原有市场价"));
		row.getCell(colInd).setCellComment(comment);
		colInd++;
		row.createCell(colInd).setCellValue("优购价");
		row.getCell(colInd).setCellStyle(style);
		comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
		comment.setString(new HSSFRichTextString("优购价可修改，修改后替换原有优购价"));
		row.getCell(colInd).setCellComment(comment);
		colInd++;
		String catName="";
		Category cat=null;
		for (Commodity commodity : commodityList) {
			tempProgress = new BigDecimal((rowInd*65.0/commodityList.size()))
			.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			colInd = 0;
			row = sheet.createRow(rowInd++);
			cat=commodityBaseApiService.getCategoryByStructName(StringUtils.substringBefore(commodity.getCatStructName(), "-"));
			catName=cat.getCatName();
			row.createCell(colInd++).setCellValue(ObjectUtils.toString(catName));
			row.createCell(colInd++).setCellValue(ObjectUtils.toString(commodity.getBrandName()));
			row.createCell(colInd++).setCellValue(ObjectUtils.toString(commodity.getCommodityNo()));
			row.createCell(colInd++).setCellValue(ObjectUtils.toString(commodity.getSpecName()));
			row.createCell(colInd++).setCellValue(ObjectUtils.toString(commodity.getCommodityName()));
			row.createCell(colInd++).setCellValue(ObjectUtils.toString(commodity.getStyleNo()));
			row.createCell(colInd++).setCellValue(ObjectUtils.toString(commodity.getSupplierCode()));
			row.createCell(colInd++).setCellValue(ObjectUtils.toString(commodity.getPublicPrice()));
			row.createCell(colInd++).setCellValue(ObjectUtils.toString(commodity.getSalePrice()));
			redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_KEY+":"+commodityQueryVo.getMerchantCode(), 
					new BigDecimal(progress+tempProgress).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		}
		Map<String,Object> hashMap = new HashMap<String,Object>();
		String merchantCode = commodityQueryVo.getMerchantCode();
		String dateStr = DateUtil2.getCurrentDateTimeToStr();
		File file = new File(this.getAbsoluteFilepath(merchantCode));
		if(!file.exists()){
			file.mkdirs();
		}
		FileOutputStream outputStream = null;
		try{
			outputStream = new FileOutputStream(this.getAbsoluteFilepath(merchantCode)+
					"waitSale_"+dateStr+".xls");
			workbook.write(outputStream);
			outputStream.flush();
			progress = progress+10.0d;
			redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_KEY+":"+commodityQueryVo.getMerchantCode(), progress);
		}catch(Exception e){
			log.error("IO异常，导出失败！",e);
		}finally{
			if(outputStream!=null){
				try {
					outputStream.close();
				}catch(Exception e){
					log.error("IO异常，导出失败！",e);
				}
			}
		}
		File excelFile = new File(this.getAbsoluteFilepath(merchantCode)+"waitSale_"+dateStr+".xls");
		boolean result = imageService.ftpUpload(excelFile,"/merchantpics/exceltemp/"+merchantCode);
    	hashMap.put("result", result);
    	hashMap.put("url",dateStr+".xls");
    	progress = progress+5.0;
    	redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_KEY+":"+commodityQueryVo.getMerchantCode(), progress);
    	return hashMap;
	}

	@ResponseBody
	@RequestMapping("/createWaitSaleCommodityBySize")
	public String createWaitSaleCommodityBySize(HttpServletRequest request, HttpServletResponse response, 
			CommodityQueryVo commodityQueryVo){
		Map<String,Object> map = null;
		double progress = 0;
		try{
			map = exportWaitSaleCommodityBySize(request,response,commodityQueryVo,progress);
		}catch(Exception e){
			log.error("服务器发生异常，导出失败！",e);
		}
		return JSONObject.fromObject(map).toString();
	}
	@ResponseBody
	@RequestMapping(value = "/createWaitSaleCommodityBySizeRunnable",method = RequestMethod.POST)
	public String createWaitSaleCommodityBySizeRunnable(final HttpServletRequest request,
			final HttpServletResponse response,final CommodityQueryVo vo){
		vo.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
		final String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		final HttpServletRequest rq=request;
		final HttpServletResponse rp=response;
//		redisTemplate.delete(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_SIZE_KEY+":"+merchantCode);
//		redisTemplate.delete(CacheConstant.C_USER_ENTER_METHOD+":createWaitSaleCommodityBySizeRunnable:"+merchantCode);
//		redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_SIZE_KEY+":"+merchantCode, -1.0d);
		vo.setWarehouseCode(SessionUtil.getWarehouseCodeFromSession(request));
		//commodityQueryVo.setWarehouseCode(SessionUtil.getWarehouseCodeFromSession(request));
		vo.setStructName(CommodityUtil.getCatStructName(vo));
		final double progress = 5.0d;
		Map<String,Object> hashMap = (Map<String,Object>)redisTemplate.opsForValue().get(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_SIZE_PRODANALYSE+":"+merchantCode);
    	String isExportData = (String)redisTemplate.opsForValue().get(CacheConstant.C_USER_ENTER_METHOD+":createWaitSaleCommodityBySizeRunnable:"+merchantCode);
		if(isExportData!=null){
    		//执行过导出excel
    		if(hashMap==null){
    			//未执行完
    			return "loading";
    		}
		}
		vo.setMerchantCode(merchantCode);
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
						hashMap = exportWaitSaleCommodityBySize(rq,rp,vo,progress);
						
						
						redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_SIZE_KEY+":"+merchantCode, 100.0d);
					} catch (Exception e) {
						hashMap = null;
						log.error("导出商品出现错误：",e);
						//删除缓存，不再进行处理
						redisTemplate.delete(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_SIZE_KEY+":"+merchantCode);
			    		redisTemplate.delete(CacheConstant.C_USER_ENTER_METHOD+":createWaitSaleCommodityBySizeRunnable:"+merchantCode);
						redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_SIZE_KEY+":"+merchantCode, -1.0d);
					}finally{
						redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_SIZE_PRODANALYSE+":"+merchantCode, hashMap);
					}
				}
			});*/
			Runnable run = new Runnable() {
				Map<String,Object> hashMap = null;
				//进度值默认起始值
				@Override
				public void run() {
					try {
						log.warn("线程{}，开始处理代售商品导出按照尺码导出...", Thread.currentThread().getName());
						hashMap = exportWaitSaleCommodityBySize(rq,rp,vo,progress);										
						redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_SIZE_KEY+":"+merchantCode, 100.0d);
						log.warn("线程{}，结束处理代售商品导出按照尺码导出...", Thread.currentThread().getName());
					} catch (Exception e) {
						hashMap = null;
						log.error("导出商品出现错误：",e);
						//删除缓存，不再进行处理
						redisTemplate.delete(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_SIZE_KEY+":"+merchantCode);
			    		redisTemplate.delete(CacheConstant.C_USER_ENTER_METHOD+":createWaitSaleCommodityBySizeRunnable:"+merchantCode);
						redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_SIZE_KEY+":"+merchantCode, -1.0d);
					}finally{
						redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_SIZE_PRODANALYSE+":"+merchantCode, hashMap);
					}
				}
			};
			Thread thread = new Thread(run);
			thread.setName("createWaitSaleCommodityBySizeRunnable 代售商品按尺码导出");
			thread.start();
			//缓存进度值
			redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_SIZE_KEY+":"+merchantCode, progress);
			//表示已进入该方法，设置过安全库存
		    redisTemplate.opsForValue().set(CacheConstant.C_USER_ENTER_METHOD+":createWaitSaleCommodityBySizeRunnable:"+merchantCode,"enter");
		    redisTemplate.expire(CacheConstant.C_USER_ENTER_METHOD+":createWaitSaleCommodityBySizeRunnable:"+merchantCode, 1, TimeUnit.HOURS);
		    //60分钟之内没有下载完，就失效
		    redisTemplate.expire(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_SIZE_PRODANALYSE+":"+merchantCode, 1, TimeUnit.HOURS);
		    redisTemplate.expire(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_SIZE_KEY+":"+merchantCode, 1, TimeUnit.HOURS);
		}catch(Exception e){
			log.error("导出商品信息发生错误！",e);
		}finally{
			if (threadPool != null) {
				threadPool.shutdown();
				threadPool = null;
			}
		}
		return "success";
	}
	//按商品尺寸导出
	private Map<String, Object> exportWaitSaleCommodityBySize(
			HttpServletRequest request, HttpServletResponse response,
			CommodityQueryVo commodityQueryVo,double progress) throws Exception {
		
		
		
		//加入当前商家的信息
		String merchantCode = commodityQueryVo.getMerchantCode();
		//commodityQueryVo.setMerchantCode(merchantCode);
		String wareHouseCode = commodityQueryVo.getWarehouseCode();
		//commodityQueryVo.setWarehouseCode(wareHouseCode);
		commodityQueryVo.setStructName(CommodityUtil.getCatStructName(commodityQueryVo));
		Query query = new Query(500);
		progress = progress +5.0d;
		redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_SIZE_KEY+":"+commodityQueryVo.getMerchantCode(), progress);
		
		List<CommodityAndProductExportVo> commodityProductList = commodityService.exportWaitSaleCommodityAndProductList(commodityQueryVo,query);
		
		progress = progress +5.0d;
		redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_KEY+":"+commodityQueryVo.getMerchantCode(), progress);
		 double tempProgress = 0;
		//货品条码sheet
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet1 = workbook.createSheet("待售商品列表");
		sheet1.addMergedRegion(new CellRangeAddress(0,0,0,11));
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFPatriarch patr = sheet1.createDrawingPatriarch();
		HSSFFont font=workbook.createFont();
        font.setColor(HSSFColor.RED.index); //字体颜色红色
        style.setFont(font);
		int rowInd=0,colInd = 0;
		HSSFRow row = sheet1.createRow(rowInd++);
		row.createCell(colInd).setCellValue("注意：如需修改商品资料，红色底的字段不可修改，灰色底字段可修改。");
		row.getCell(colInd).setCellStyle(style);
		colInd=0;
		style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.CORAL.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		row = sheet1.createRow(rowInd++);
		row.createCell(colInd).setCellValue("一级分类");
		row.getCell(colInd).setCellStyle(style);
		HSSFComment comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
		comment.setString(new HSSFRichTextString("一级分类不可修改或删除"));
		row.getCell(colInd).setCellComment(comment);
		colInd++;
		row.createCell(colInd).setCellValue("品牌");
		row.getCell(colInd).setCellStyle(style);
		comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
		comment.setString(new HSSFRichTextString("品牌不可修改或删除"));
		row.getCell(colInd).setCellComment(comment);
		colInd++;
		row.createCell(colInd).setCellValue("商品编码");
		row.getCell(colInd).setCellStyle(style);
		comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
		comment.setString(new HSSFRichTextString("商品编码不可修改或删除"));
		row.getCell(colInd).setCellComment(comment);
		colInd++;
		HSSFCellStyle style1 = workbook.createCellStyle();
		style1.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
		style1.setFillPattern(CellStyle.SOLID_FOREGROUND);
		row.createCell(colInd).setCellValue("颜色");
		row.getCell(colInd).setCellStyle(style1);
		comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
		comment.setString(new HSSFRichTextString("颜色可修改，修改后替换原有颜色"));
		row.getCell(colInd).setCellComment(comment);
		colInd++;
		row.createCell(colInd).setCellValue("商品名称");
		row.getCell(colInd).setCellStyle(style1);
		comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
		comment.setString(new HSSFRichTextString("商品名称可修改，修改后替换原有商品名称"));
		row.getCell(colInd).setCellComment(comment);
		colInd++;
		row.createCell(colInd).setCellValue("商品款号");
		row.getCell(colInd).setCellStyle(style1);
		comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
		comment.setString(new HSSFRichTextString("商品款号可修改，修改后替换原有商品款号"));
		row.getCell(colInd).setCellComment(comment);
		colInd++;
		row.createCell(colInd).setCellValue("款色编码");
		row.getCell(colInd).setCellStyle(style1);
		comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
		comment.setString(new HSSFRichTextString("款色编码可修改，修改后替换原有款色编码"));
		row.getCell(colInd).setCellComment(comment);
		colInd++;
		row.createCell(colInd).setCellValue("市场价");
		row.getCell(colInd).setCellStyle(style1);
		comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
		comment.setString(new HSSFRichTextString("市场价可修改，修改后替换原有市场价"));
		row.getCell(colInd).setCellComment(comment);
		colInd++;
		row.createCell(colInd).setCellValue("优购价");
		row.getCell(colInd).setCellStyle(style1);
		comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
		comment.setString(new HSSFRichTextString("优购价可修改，修改后替换原有优购价"));
		row.getCell(colInd).setCellComment(comment);
		colInd++;
		row.createCell(colInd).setCellValue("尺码");
		row.getCell(colInd).setCellStyle(style);
		comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
		comment.setString(new HSSFRichTextString("尺码不可修改或删除"));
		row.getCell(colInd).setCellComment(comment);
		colInd++;
		row.createCell(colInd).setCellValue("货品条码");
		row.getCell(colInd).setCellStyle(style1);
		comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
		comment.setString(new HSSFRichTextString("货品条码可修改，修改后替换原有货品条码"));
		row.getCell(colInd).setCellComment(comment);
		colInd++;
		row.createCell(colInd).setCellValue("库存");
		row.getCell(colInd).setCellStyle(style1);
		comment = patr.createCellComment(new HSSFClientAnchor(0,0,0,0,(short)colInd,row.getRowNum(),(short)(colInd+3),row.getRowNum()+3));
		comment.setString(new HSSFRichTextString("库存可修改，修改后替换原有库存"));
		row.getCell(colInd).setCellComment(comment);
		colInd++;
		String catName="";
		Category cat=null;
		CommodityAndProductExportVo commodityProduct = null;
		Product product = null;
		Integer safeStockQuantity = null;
		InventoryAssistVo qty=null;
		for (int i=0;i<commodityProductList.size();i++) {
			commodityProduct = commodityProductList.get(i);
			colInd = 0;
			tempProgress = new BigDecimal((rowInd*65.0/commodityProductList.size()))
			.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			row = sheet1.createRow(rowInd++);
			if(i>0&&(commodityProductList.get(i-1).getCommodityNo().equals(commodityProductList.get(i).getCommodityNo()))){
				colInd = colInd+9;
			}else{
				cat=commodityBaseApiService.getCategoryByStructName(StringUtils.substringBefore(commodityProduct.getCatStructName(), "-"));
				catName=cat.getCatName();
				row.createCell(colInd++).setCellValue(ObjectUtils.toString(catName));
				row.createCell(colInd++).setCellValue(ObjectUtils.toString(commodityProduct.getBrandName()));
				row.createCell(colInd++).setCellValue(ObjectUtils.toString(commodityProduct.getCommodityNo()));
				row.createCell(colInd++).setCellValue(ObjectUtils.toString(commodityProduct.getSpecName()));
				row.createCell(colInd++).setCellValue(ObjectUtils.toString(commodityProduct.getCommodityName()));
				row.createCell(colInd++).setCellValue(ObjectUtils.toString(commodityProduct.getStyleNo()));
				row.createCell(colInd++).setCellValue(ObjectUtils.toString(commodityProduct.getSupplierCode()));
				row.createCell(colInd++).setCellValue(ObjectUtils.toString(commodityProduct.getPublicPrice()));
				row.createCell(colInd++).setCellValue(ObjectUtils.toString(commodityProduct.getSalePrice()));
			}
			product = commodityApi.getProductByThirdPartyCodeAndMerchantCode(commodityProduct.getThirdPartyCode(), commodityQueryVo.getMerchantCode());
			redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_SIZE_KEY+":"+commodityQueryVo.getMerchantCode(), 
					new BigDecimal(progress+tempProgress).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
			/*inventoryMap = inventoryDomainService.querySalesInventroyForMerchant(
					product.getProductNo(), commodityQueryVo.getWarehouseCode());
			Integer stock = null;
			if(inventoryMap!=null){
				for (Integer stockTmp : inventoryMap.keySet()) {
					stock = stockTmp;
					break;
				}
			}
			if (stock == null) {
				stock=0;
			}*/
			if(product!=null){
	            try {
	            		List<InventoryAssistVo> qtys = inventoryForMerchantService.queryProductInventory(
	                    		Arrays.asList(product.getProductNo()), wareHouseCode,0);
	                    if(qtys!=null&&qtys.size()>0){
	                    	qty = qtys.get(0);
	                    }
	            } catch (WPIBussinessException e) {
	            	log.error("查询queryProductInventory发生异常.", e);
	            }
	            safeStockQuantity = stockService.getSafeStockQuantityByProductNo(merchantCode, product.getProductNo());
				if(safeStockQuantity==null){
					safeStockQuantity = 0;
				}
				row.createCell(colInd++).setCellValue(ObjectUtils.toString(commodityProduct.getSizeName()));
				row.createCell(colInd++).setCellValue(ObjectUtils.toString(commodityProduct.getThirdPartyCode()));
				row.createCell(colInd++).setCellValue(null == qty ? 0 : qty.getInventoryQuantity() + safeStockQuantity);
			}
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
					"waitSale_"+dateStr+".xls");
			workbook.write(outputStream);
			outputStream.flush();
			progress = progress+10.0d;
			redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_SIZE_KEY+":"+commodityQueryVo.getMerchantCode(), progress);
		}catch(Exception e){
			log.error("IO异常，导出失败！",e);
		}finally{
			if(outputStream!=null){
				try {
					outputStream.close();
				}catch(Exception e){
					log.error("IO异常，导出失败！",e);
				}
			}
		}
		File excelFile = new File(this.getAbsoluteFilepath(merchantCode)+"waitSale_"+dateStr+".xls");
		boolean result = imageService.ftpUpload(excelFile,"/merchantpics/exceltemp/"+merchantCode);
    	hashMap.put("result", result);
    	hashMap.put("url",dateStr+".xls");
    	progress = progress+5.0;
    	redisTemplate.opsForValue().set(CacheConstant.C_EXPORT_WAIT_SALE_COMMODIT_SIZE_KEY+":"+commodityQueryVo.getMerchantCode(), progress);
    	return hashMap;
	}

	@RequestMapping("/waitSaleDownload")
	public void waitSaleDownload(@RequestParam String name,HttpServletRequest request, HttpServletResponse response){
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		OutputStream outputStream = null;
		try{
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", MessageFormat.format("attachment; filename={0}", encodeDownloadAttachmentFilename(request, "待售商品_"+name)));
			outputStream = response.getOutputStream();
			FileFtpUtil ftpUtil = new FileFtpUtil(commoditySettings.imageFtpServer,commoditySettings.imageFtpPort, 
					commoditySettings.imageFtpUsername, commoditySettings.imageFtpPassword);
			ftpUtil.login();
			ftpUtil.downRemoteFile("/merchantpics/exceltemp/"+merchantCode+"/waitSale_"+name,outputStream);
			outputStream.flush();
			ftpUtil.logout();
		}catch(Exception e){
			log.error("下载失败！",e);
		}finally{
			if(outputStream!=null){
				try {
					outputStream.close();
				} catch (IOException e) {
					log.error("IO流关闭异常！",e);
				}
			}
		}
		
	}
	
	
	/**
	 * 导出待售商品
	 * @param request
	 * @param response
	 * @param commodityQueryVo
	 * @throws Exception
	 */
	//@ResponseBody
	//@RequestMapping("exportWaitSaleCommodity")
	private Map<String,Object> exportWaitSaleCommodity(HttpServletRequest request, HttpServletResponse response, CommodityQueryVo commodityQueryVo) throws Exception {
		//加入当前商家的信息
		commodityQueryVo.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
		commodityQueryVo.setWarehouseCode(SessionUtil.getWarehouseCodeFromSession(request));
		commodityQueryVo.setStructName(CommodityUtil.getCatStructName(commodityQueryVo));
		Query query = new Query(500);
		//查询主数据
		List<Commodity> commodityList = commodityService.exportWaitSaleCommodityList(commodityQueryVo,query);
		
		//查询可售库存
		List<String> commodityNoList = new ArrayList<String>(0);
		for(Commodity c : commodityList) {
			commodityNoList.add(c.getCommodityNo());
		}
		Map<String, Integer> qtyMap = new HashMap<String, Integer>();
        try {
            List<InventoryAssistVo> qtys = null;
            if (StringUtils.isNotBlank(commodityQueryVo.getWarehouseCode())) {
                qtys = inventoryForMerchantService.queryCommodityInventory(commodityNoList, commodityQueryVo.getWarehouseCode());
                if (CollectionUtils.isNotEmpty(qtys)) {
                    for (InventoryAssistVo qtyVo : qtys) {
                        qtyMap.put(qtyVo.getCommodityNo(), qtyVo.getCanSalesInventoryNum());
                    }
                }
            } else {
                qtys = inventoryForMerchantService.queryInvenotryByCommodity(commodityNoList);
                if (CollectionUtils.isNotEmpty(qtys)) {
                    for (InventoryAssistVo qtyVo : qtys) {
                        qtyMap.put(qtyVo.getCommodityNo(), qtyVo.getInventoryQuantity());
                    }
                }
            }
        } catch (WPIBussinessException e) {
			log.error("查询queryCommodityInventory发生异常.", e);
		}
		
        query = new Query(500);
		List<ProductExportVo> productList = commodityService.exportWaitSaleProductList(commodityQueryVo,query);
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("商品列表");
		HSSFSheet sheet1 = workbook.createSheet("货品条码");
		int rowInd = 0, colInd = 0;
		HSSFRow row = sheet.createRow(rowInd++);
		row.createCell(colInd++).setCellValue("商品名称");
		row.createCell(colInd++).setCellValue("商品编码");
		row.createCell(colInd++).setCellValue("商品款号");
		row.createCell(colInd++).setCellValue("颜色");
		row.createCell(colInd++).setCellValue("款色编码");
		row.createCell(colInd++).setCellValue("一级分类");
		row.createCell(colInd++).setCellValue("品牌");
		row.createCell(colInd++).setCellValue("可售库存");
		row.createCell(colInd++).setCellValue("市场价");
		row.createCell(colInd++).setCellValue("销售价");
		String catName="";
		Category cat=null;
		for (Commodity commodity : commodityList) {
			colInd = 0;
			row = sheet.createRow(rowInd++);
			cat=commodityBaseApiService.getCategoryByStructName(StringUtils.substringBefore(commodity.getCatStructName(), "-"));
			catName=cat.getCatName();
			row.createCell(colInd++).setCellValue(ObjectUtils.toString(commodity.getCommodityName()));
			row.createCell(colInd++).setCellValue(ObjectUtils.toString(commodity.getCommodityNo()));
			row.createCell(colInd++).setCellValue(ObjectUtils.toString(commodity.getStyleNo()));
			row.createCell(colInd++).setCellValue(ObjectUtils.toString(commodity.getSpecName()));
			row.createCell(colInd++).setCellValue(ObjectUtils.toString(commodity.getSupplierCode()));
			row.createCell(colInd++).setCellValue(ObjectUtils.toString(catName));
			row.createCell(colInd++).setCellValue(ObjectUtils.toString(commodity.getBrandName()));
			//row.createCell(colInd++).setCellValue(ObjectUtils.toString(commodity.getOnSaleQuantity()));
			row.createCell(colInd++).setCellValue(ObjectUtils.toString(qtyMap.containsKey(commodity.getCommodityNo()) ? qtyMap.get(commodity.getCommodityNo()) : 0));
			row.createCell(colInd++).setCellValue(ObjectUtils.toString(commodity.getPublicPrice()));
			row.createCell(colInd++).setCellValue(ObjectUtils.toString(commodity.getSalePrice()));
		}
		//货品条码sheet
		rowInd = 0; colInd = 0;
		row = sheet1.createRow(rowInd++);
		row.createCell(colInd++).setCellValue("款色编码");
		row.createCell(colInd++).setCellValue("尺码");
		row.createCell(colInd++).setCellValue("货品条码");
		for (ProductExportVo product : productList) {
			colInd = 0;
			row = sheet1.createRow(rowInd++);
			row.createCell(colInd++).setCellValue(ObjectUtils.toString(product.getSupplierCode()));
			row.createCell(colInd++).setCellValue(ObjectUtils.toString(product.getSizeName()));
			row.createCell(colInd++).setCellValue(ObjectUtils.toString(product.getThirdPartyCode()));
		}
		Map<String,Object> hashMap = new HashMap<String,Object>();
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		String dateStr = DateUtil2.getCurrentDateTimeToStr();
		File file = new File(this.getAbsoluteFilepath(merchantCode));
		if(!file.exists()){
			file.mkdirs();
		}
		FileOutputStream outputStream = null;
		try{
			outputStream = new FileOutputStream(this.getAbsoluteFilepath(merchantCode)+
					"waitSale_"+dateStr+".xls");
			workbook.write(outputStream);
			outputStream.flush();
		}catch(Exception e){
			log.error("IO异常，导出失败！",e);
		}finally{
			if(outputStream!=null){
				try {
					outputStream.close();
				}catch(Exception e){
					log.error("IO异常，导出失败！",e);
				}
			}
		}
		File excelFile = new File(this.getAbsoluteFilepath(merchantCode)+"waitSale_"+dateStr+".xls");
		boolean result = imageService.ftpUpload(excelFile,"/merchantpics/exceltemp/"+merchantCode);
    	hashMap.put("result", result);
    	hashMap.put("url",dateStr+".xls");
    	return hashMap;
		/*
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", MessageFormat.format("attachment; filename={0}.xls", DateUtil.getCurrentDateTimeToStr()));
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			workbook.write(os);
		} finally {
			//os.flush();
			os.close();
		}
		*/
		
	}
	
	/**
	 * <p>初始化查询条件</p>
	 * <p>包括  [品牌、分类]</p>
	 * 
	 * @param modelMap
	 * @param queryVo
	 */
	private void initCommonProperty(ModelMap modelMap) {
		modelMap.addAttribute("statics", BeansWrapper.getDefaultInstance().getStaticModels());
		commodityComponent.initCommonProperty(modelMap);
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
	
	@ResponseBody
	@RequestMapping("/queryBrandCat")
	public String queryBrandCat(ModelMap modelMap, HttpServletRequest request, String brandId) {
		List<Category> list = commodityService.getAllCategoryByBrandId(YmcThreadLocalHolder.getMerchantCode(), brandId);
		
		if (list == null || list.isEmpty())
			return null;
		
		JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(new PropertyFilter() {
			@Override
			public boolean apply(Object source, String name, Object value) {
				if (name.equals("id") || name.equals("catName") 
						|| name.equals("structName") || name.equals("catLeave") 
						|| name.equals("catNo") || name.equals("parentId")) {
					return false;
				}
				return true;
			}
		});
		JSONArray jsonArray = JSONArray.fromObject(list, config);
		return jsonArray.toString();
	}
	
	/**
	 * <p>下架</p>
	 * 
	 * @return 
	 */
	@ResponseBody
	@RequestMapping("/downGoods")
	public String downGoods(String commodityNo, HttpServletRequest request) {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		try {
			String _result = commodityApi.updateCommodityStatusForMerchant(commodityNo, merchantCode, 1);
			Pattern pattern = Pattern.compile("\\d+$");
			Matcher matcher = pattern.matcher(_result);
			if(matcher.find()){
				String commodityStatus = matcher.group();
				_result = _result.replace(commodityStatus, 
						MapUtils.getString(CommodityConstant.COMMODITY_STATUS_MAP, commodityStatus, commodityStatus));
			}
			return _result;
		} catch (Exception e) {
			log.error("商家{}下架商品{}失败", new Object[]{merchantCode, commodityNo, e});
			return "系统内部异常.";
		}
	}
	
	/**
	 * <p>下架</p>
	 * 
	 * @return 
	 */
	@ResponseBody
	@RequestMapping("/downGoodsBatch")
	public String downGoodsBatch(String commodityNos, HttpServletRequest request) {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		try {
			String[] commodityNoArry=commodityNos.split(",");
			String _result="";
			StringBuilder sb=new StringBuilder();
			boolean isSuccess=true;
			for(String commodityNo:commodityNoArry){
				_result = commodityApi.updateCommodityStatusForMerchant(commodityNo, merchantCode, 1);
				if(!"SUCCESS".equalsIgnoreCase(_result)){
					isSuccess=false;
				}
				sb.append("{"+commodityNo+":"+("SUCCESS".equalsIgnoreCase(_result)?"ok":"&otimes;")+"}<br/>");
			}
			return isSuccess?"SUCCESS":sb.toString();
		} catch (Exception e) {
			log.error("商家{}下架商品{}失败", new Object[]{merchantCode, commodityNos , e});
			return "系统内部异常.";
		}
	}
	
	final static String SHOW_COMMODITY_LOG_URL = "/manage_unless/commodity/show_commodity_update_log";
	
	@RequestMapping("/showCommodityLog")
	public ModelAndView showCommodityLog(ModelMap modelMap, String commodityNo) {
		log.warn("查看商品日志commodityNo=={}",commodityNo);
		List<CommodityUpdateLog> commodityUpdateLogs = commodityBaseApiService.getCommodityUpdateLogsByCommodityNo(commodityNo);
		
		modelMap.put("commodityUpdateLogs", commodityUpdateLogs);
		return new ModelAndView(SHOW_COMMODITY_LOG_URL, modelMap);
	}
	
	@ResponseBody
	@RequestMapping("/doShopCommodityTips")
	public String toShopCommodityTips(HttpServletRequest request) {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		if (StringUtils.isBlank(merchantCode)) return "";
		
		Map<String, Integer> tips = commodityService.queryShopCommodityTips(merchantCode);
		StringBuilder sb = new StringBuilder();
		if (MapUtils.isNotEmpty(tips)) {
			sb.append(MapUtils.getInteger(tips, "onSaleCount", 0)).append(",");
			sb.append(MapUtils.getInteger(tips, "approveCount", 0)).append(",");
			sb.append(MapUtils.getInteger(tips, "newCount", 0)).append(",");
			sb.append(MapUtils.getInteger(tips, "waitAuditCount", 0)).append(",");
			sb.append(MapUtils.getInteger(tips, "refusedCount", 0)).append(",");
			sb.append(MapUtils.getInteger(tips, "dialogCount", 0));
			return sb.toString();
		}

		return sb.append("0").append(",").append("0").append(",").append("0")
				.append(",").append("0").append(",").append("0").append(",").append("0").toString();
	}
	
	@ResponseBody
	@RequestMapping("/updateCommodityName")
	public String updateCommodityName(String commodityName, String commodityNo,Short followOperate) {
		JSONObject result = new JSONObject();
		//过滤特殊字符
		//中间多个空格，保留一个空格
		commodityName = StringUtils.chomp(commodityName.replace("\"", "")
				.replace("\n", "")
				.replace("\\", "")
				.replaceAll("　", "")
				.replaceAll("\\s+", " ")//中间多个英文空格，保留一个
				.trim());
		if (StringUtils.isEmpty(commodityName)) {
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "商品名称不能为空");
			return result.toString();
		}
		if (commodityName.length()>200) {
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "商品名称长度不能为超过200");
			return result.toString();
		}
		if (StringUtils.isEmpty(commodityNo)) {
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "商品编码不能为空");
			return result.toString();
		}
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		try{
			commodityApi.updateCommodityName4OnSale(commodityName, commodityNo, merchantCode);
			//记录敏感词校验的日志
			com.yougou.pc.model.commodityinfo.Commodity commodity = commodityApi.getCommodityByNo(commodityNo);
			commodity.setCommodityName(commodityName);
			commodity.setSellingPoint(null);
			commodity.setCommodityDesc(null);
			CommoditySubmitNewVo newVo = CommodityUtilNew.buildCommoditySubmitNewVo(commodity,false);
			newVo.setFollowOperate(followOperate);
			if(!commodityExtendService.insertCommodityExtendAndLog(newVo,
					YmcThreadLocalHolder.getOperater(),"在售商品修改商品名称")){
				log.error("商家{}插入商品{}扩展表数据与日志报错！",new Object[]{commodity.getMerchantCode(),commodityNo});
			}
		}catch(Exception e){
			log.error("修改在线商品失败，商品编码：{},商家编码：{}",new Object[]{commodityNo,merchantCode, e});
			result.put("resultCode", ResultCode.ERROR.getCode());
			result.put("msg", "系统异常，请联系管理员");
			return result.toString();
		}
		result.put("resultCode", ResultCode.SUCCESS.getCode());
		return result.toString();
	}
	
	@ResponseBody
	@RequestMapping("/checkCommodityStatus")
	public String checkCommodityStatus(@RequestParam String commodityNo){
		String result = "{'result':true}";
		if(commodityNo!=null&&!"".equals(commodityNo)){
			com.yougou.pc.model.commodityinfo.Commodity commodity = commodityApi.getCommodityByNo(commodityNo);
			if(null!=commodity.getStatus()&&2==commodity.getStatus()){	//已上架
				result = "{'result':false}";
			}
		}
		return JSONObject.fromObject(result).toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/importXlsToDb",method=RequestMethod.POST)
	public String importWaitCommodityToDb(MultipartHttpServletRequest request, HttpServletResponse response){
		JSONObject jsonObject = new JSONObject();
		String wareHouseCode = SessionUtil.getWarehouseCodeFromSession(request);
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		MultipartFile mutipartfile = request.getFile("file");
		if(!(mutipartfile.isEmpty())&&(mutipartfile.getSize()>=0)){
			try {
				String fileType = mutipartfile.getOriginalFilename().
						substring(mutipartfile.getOriginalFilename().
								lastIndexOf(".") + 1);
				return readXls(fileType,mutipartfile.getInputStream(),jsonObject,wareHouseCode,merchantCode);
			} catch (IOException e) {
				jsonObject.put("resultCode", ResultCode.ERROR.getCode());
				jsonObject.put("msg","文件信息有误！");
				//e.printStackTrace();
				log.error("服务器发生异常！", e);
			} catch (Exception e) {
				jsonObject.put("resultCode", ResultCode.ERROR.getCode());
				jsonObject.put("msg","文件信息有误！");
				//e.printStackTrace();
				log.error("服务器发生异常！", e);
			}
		}else{
			jsonObject.put("resultCode", ResultCode.ERROR.getCode());
			jsonObject.put("msg","导入excel文件为空！");
		}
		return jsonObject.toString();
	}

	/** 
	 * readXls:解析excel
	 * @author li.n1 
	 * @param inputStream 
	 * @since JDK 1.6 
	 */  
	private String readXls(String fileType,InputStream inputStream,
			JSONObject jsonObject,String wareHouseCode,String merchantCode) throws Exception{
		int errorCount = 0;
		int rowNum = 0;
		List<ExcelErrorVo> errorList = new ArrayList<ExcelErrorVo>();
		
		//内存中解析excel文件
		//商品导出  9列
		//尺寸导出  12列
		com.yougou.pc.model.commodityinfo.Commodity commodity = null;
		com.yougou.pc.model.commodityinfo.Commodity[] commoditys = new com.yougou.pc.model.commodityinfo.Commodity[]{};
		List<Integer> successIndexList = new ArrayList<Integer>();
		if("xls".equalsIgnoreCase(fileType)){
			//解析xls
			HSSFWorkbook work = new HSSFWorkbook(inputStream);
			IOUtils.closeQuietly(inputStream);
			HSSFSheet sheet = work.getSheetAt(0);
			rowNum = sheet.getLastRowNum();
			HSSFCellStyle errorStyle = work.createCellStyle();
			errorStyle.setFillForegroundColor(HSSFColor.CORAL.index);
			errorStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			HSSFCellStyle defaultCellStyle = work.createCellStyle();
			HSSFPatriarch drawing = sheet.createDrawingPatriarch();
			HSSFRow row = null;
			if(!(checkCommodity(sheet,errorList,defaultCellStyle))){	//文件有严重错误
				//列名是否修改没有作判断，只判断是否添加列或删除列
				//标示异常
				for (ExcelErrorVo errorVo : errorList) {
					Cell _cell = ExcelToDataModelConverter.getCell(sheet, errorVo.getRow(), errorVo.getColumn());
					_cell.setCellStyle(errorStyle);
					_cell.removeCellComment();
					_cell.setCellComment(ExcelToDataModelConverter.createComment(drawing, 3, 3, 6, 6, errorVo.getErrMsg()));
				}
				// 设置默认选中第一条错误数据行
				ExcelToDataModelConverter.setErrorCellAsActive(sheet);
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
				jsonObject.put("resultCode", ResultCode.ERROR.getCode());
				jsonObject.put("msg", "导入文件格式填写错误，请修改后重新上传");
				jsonObject.put("type", "xls");
				return jsonObject.toString();
			}else{	//文件无严重错误
				//真正商品内容从2开始，（以0为基）
				com.yougou.pc.model.commodityinfo.Commodity tempCommodity = null;
				Map<String,Integer> prodMap = null;
				boolean anotherCommflag = false;
				int commCount = 0;
				int rowIndex = 0;
				List<ExcelErrorVo> errorList2 = null;
				String commodityName = null;
				for(int i=2;i<=rowNum;i++){
					row = sheet.getRow(i);
					errorList2 = new ArrayList<ExcelErrorVo>();
					if(StringUtils.isNotBlank(ExcelToDataModelConverter.getCellValue(row,0))){
						commCount++;
					}
					if(!checkCommodityRow(row.getLastCellNum(), row, errorList2, defaultCellStyle)){
						for (ExcelErrorVo errorVo : errorList2) {
							Cell _cell = ExcelToDataModelConverter.getCell(sheet, errorVo.getRow(), errorVo.getColumn());
							_cell.setCellStyle(errorStyle);
							_cell.removeCellComment();
							_cell.setCellComment(ExcelToDataModelConverter.createComment(drawing, 3, 3, 6, 6, errorVo.getErrMsg()));
						}
						errorCount++;
						continue;
					}else{
						if(StringUtils.isNotBlank(ExcelToDataModelConverter.getCellValue(row,2))){
							tempCommodity = commodityApi.getCommodityByNo(StringUtils.trim(ExcelToDataModelConverter.getCellValue(row,2)));
							//判断商品是否属于该商家，否则不能修改
							if(tempCommodity!=null && merchantCode.equals(tempCommodity.getMerchantCode())){
								anotherCommflag = true;
								rowIndex = i;
								commodity = tempCommodity;
								//从第三列颜色开始
								commodity.setColorName(ExcelToDataModelConverter.getCellValue(row,3));
								//商品名称
								//商品名称过回车、转义符、英文双引号、中间多个英文空格保留一个，中文空格，只保留前200个字
								commodityName = ExcelToDataModelConverter.getCellValue(row,4);
								commodityName = StringUtils.chomp(commodityName
										.replace("\"", "")
										.replace("\n", "")
										.replace("\\", "")
										.replaceAll("　", "")//中文空格
										.replaceAll("\\s+", " ")//中间多个英文空格，保留一个
										.trim());
								commodity.setCommodityName(commodityName);
								//商品款号
								commodity.setStyleNo(ExcelToDataModelConverter.getCellValue(row,5));
								//款色编码
								commodity.setSupplierCode(ExcelToDataModelConverter.getCellValue(row,6));
								//市场价
								commodity.setMarkPrice(Double.parseDouble(ExcelToDataModelConverter.getCellValue2(row.getCell(7),true)));
								//优购价
								commodity.setSellPrice(Double.parseDouble(ExcelToDataModelConverter.getCellValue2(row.getCell(8),true)));
							}else{
								log.warn("商家编码为{}导入修改资料，商品编码为{}的商品【该商品的商家编码为{}】不存在或该商品不属于该商家！",
										new Object[]{merchantCode,StringUtils.trim(ExcelToDataModelConverter.getCellValue(row,2)),
										tempCommodity!=null?tempCommodity.getMerchantCode():null});
								errorCount++;
								Cell _cell = ExcelToDataModelConverter.getCell(sheet, i, 0);
								_cell.setCellStyle(errorStyle);
								_cell.removeCellComment();
								_cell.setCellComment(ExcelToDataModelConverter.createComment(drawing, 3, 3, 6, 6, "商品不存在或该商品不属于该商家！"));
								continue;
							}
						}else{
							commodity = tempCommodity;
							anotherCommflag = false;
						}
						if(commodity==null){
							continue;
						}
						//修改时间
						commodity.setUpdateDate(new Date());
						if(row.getLastCellNum()>=12){	//按尺码导入
							if(anotherCommflag){
								prodMap = new HashMap<String,Integer>();
							}
							//货品条码
							for(Product product : commodity.getProducts()){
								if(product.getSizeName().equals(ExcelToDataModelConverter.getCellValue2(row.getCell(9),true))){
									product.setInsideCode(ExcelToDataModelConverter.getCellValue(row,10));
									product.setThirdPartyInsideCode(ExcelToDataModelConverter.getCellValue(row,10));
									//库存
										//inventoryDomainService.updateInventoryForMerchant(product.getProductNo(), 
											//wareHouseCode, Integer.parseInt(ExcelToDataModelConverter.getCellValue(row,11)), NumberUtils.INTEGER_ZERO);
									prodMap.put(product.getProductNo(), Integer.parseInt(ExcelToDataModelConverter.getCellValue(row,11)));
									break;
								}
							}
							if((i+1)<=rowNum){
								//判断下一行是否是新的商品，还是同一个商品
								row = sheet.getRow(i+1);
								//下一行第2列商品编码不为空，说明是新的商品，那前一个商品赶紧保存
								if(StringUtils.isNotBlank(ExcelToDataModelConverter.getCellValue(row,2))){
									//修改库存
									Map<String, Object> map = null;
									for(Map.Entry<String, Integer> entry : prodMap.entrySet()){
										//map =  inventoryDomainService.updateInventoryForMerchant(entry.getKey(), 
										//	wareHouseCode, entry.getValue(), NumberUtils.INTEGER_ZERO);
										map = stockService.updateInventoryForMerchant(merchantCode, 
												wareHouseCode, entry.getKey(), entry.getValue());
										if (map.containsKey(null)) {
											log.error("待售商品导入修改资料时保存库存失败，货品条码：{}，欲修改成的库存值：{}，失败原因：{}",new Object[]{entry.getKey(),entry.getValue(),map.get(null)});
										}else{
											log.info("待售商品导入修改资料时保存库存成功，货品条码：{}，欲修改成的库存值：{}，修改时间：{}",new Object[]{entry.getKey(),entry.getValue(),map.get(entry.getKey())});
										}
									}
									String result = commodityApi.saveAllCommodityMsgForMerchant(commodity);
									commoditys = ArrayUtils.add(commoditys, commodity);
									
									//插入数据库发生错误，记录
									if(!(result.equalsIgnoreCase(RESULT_SUCCESS))){
										Cell _cell = ExcelToDataModelConverter.getCell(sheet, rowIndex, 0);
										_cell.setCellStyle(errorStyle);
										_cell.removeCellComment();
										_cell.setCellComment(ExcelToDataModelConverter.createComment(drawing, 3, 3, 6, 6, "该商品"+result));
										errorCount++;
									}else{
										//插入成功，记录rowIndex，方便删除成功的行
										for(int m=rowIndex;m<=i;m++){
											successIndexList.add(m);
										}
									}
								}
							}else{//最后一行
								//修改库存
								Map<String, Object> map = null;
								for(Map.Entry<String, Integer> entry : prodMap.entrySet()){
									//map =  inventoryDomainService.updateInventoryForMerchant(entry.getKey(), 
									//	wareHouseCode, entry.getValue(), NumberUtils.INTEGER_ZERO);
									map = stockService.updateInventoryForMerchant(merchantCode, 
											wareHouseCode, entry.getKey(), entry.getValue());
									if (map.containsKey(null)) {
										log.error("待售商品导入修改资料时保存库存失败，货品条码：{}，欲修改成的库存值：{}，失败原因：{}",new Object[]{entry.getKey(),entry.getValue(),map.get(null)});
									}else{
										log.info("待售商品导入修改资料时保存库存成功，货品条码：{}，欲修改成的库存值：{}，修改时间：{}",new Object[]{entry.getKey(),entry.getValue(),map.get(entry.getKey())});
									}
								}
								String result = commodityApi.saveAllCommodityMsgForMerchant(commodity);
								commoditys = ArrayUtils.add(commoditys, commodity);
								//插入数据库发生错误，记录
								if(!(result.equalsIgnoreCase(RESULT_SUCCESS))){
									Cell _cell = ExcelToDataModelConverter.getCell(sheet, rowIndex, 0);
									_cell.setCellStyle(errorStyle);
									_cell.removeCellComment();
									_cell.setCellComment(ExcelToDataModelConverter.createComment(drawing, 3, 3, 6, 6, "该商品"+result));
									errorCount++;
								}else{
									//插入成功，记录rowIndex，方便删除成功的行
									for(int m=rowIndex;m<=i;m++){
										successIndexList.add(m);
									}
								}
							}
						}else{		//按商品导入
							String result = commodityApi.saveAllCommodityMsgForMerchant(commodity);
							commoditys = ArrayUtils.add(commoditys, commodity);
							//插入数据库发生错误，记录
							if(!(result.equalsIgnoreCase(RESULT_SUCCESS))){
								Cell _cell = ExcelToDataModelConverter.getCell(sheet, row.getRowNum(), 0);
								_cell.setCellStyle(errorStyle);
								_cell.removeCellComment();
								_cell.setCellComment(ExcelToDataModelConverter.createComment(drawing, 3, 3, 6, 6, result));
								errorCount++;
							}else{
								//插入成功，记录rowIndex，方便删除成功的行
								successIndexList.add(rowIndex);
							}
						}
					}
				}
				//插入数据库发生错误，记录并生成excel
				if(errorCount>0){
					//删除导入成功的行
					for(int i=0,k=0;i<successIndexList.size();i++,k++){
						ExcelToDataModelConverter.removeRow(sheet,successIndexList.get(i)-k, defaultCellStyle, errorStyle);
					}
					//记录备注信息（方便查看）
					//返回正确的插入失败的商品数量
					errorCount = ExcelToDataModelConverter.logCellComment(sheet, 2);
					// 设置默认选中第一条错误数据行
					ExcelToDataModelConverter.setErrorCellAsActive(sheet);
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
				rowNum = commCount;
				jsonObject.put("type", "xls");
			}
		}else if("xlsx".equalsIgnoreCase(fileType)){
			//解析xlsx
			XSSFWorkbook work = new XSSFWorkbook(inputStream);
			IOUtils.closeQuietly(inputStream);
			XSSFSheet sheet = work.getSheetAt(0);
			XSSFCellStyle errorStyle = work.createCellStyle();
			errorStyle.setFillForegroundColor(new XSSFColor(new Color(255, 130, 105)));
			errorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			XSSFCellStyle defaultCellStyle = work.createCellStyle();
			XSSFDrawing drawing = sheet.createDrawingPatriarch();
			rowNum = sheet.getLastRowNum();
			XSSFRow row = null;
			if(!(checkCommodity(sheet,errorList,defaultCellStyle))){	//文件有严重错误
				//列名是否修改没有作判断，只判断是否添加列或删除列
				//标示异常
				for (ExcelErrorVo errorVo : errorList) {
					Cell _cell = ExcelToDataModelConverter.getCell(sheet, errorVo.getRow(), errorVo.getColumn());
					_cell.setCellStyle(errorStyle);
					_cell.removeCellComment();
					_cell.setCellComment(ExcelToDataModelConverter.createComment(drawing, 3, 3, 6, 6, errorVo.getErrMsg()));
				}
				// 设置默认选中第一条错误数据行
				ExcelToDataModelConverter.setErrorCellAsActive(sheet);
				// 将错误数据行写入Excel文件
				String uuid = UUID.randomUUID().toString();
				OutputStream os = null;
				try {
					os = new FileOutputStream(getErrorXlsPathname(uuid,"xlsx"));
					work.write(os);
				} finally {
					IOUtils.closeQuietly(os);
					jsonObject.accumulate("uuid", uuid);
				}
				//将文件缓存到redis
				InputStream is = new FileInputStream(getErrorXlsPathname(uuid,"xlsx"));
				this.redisTemplate.opsForHash().put(CacheConstant.C_COMMODITY_IMPORT_ERROR_KEY, uuid, IOUtils.toByteArray(is));
				jsonObject.put("resultCode", ResultCode.ERROR.getCode());
				jsonObject.put("msg", "导入文件格式填写错误，请修改后重新上传");
				jsonObject.put("type", "xlsx");
				return jsonObject.toString();
			}else{//文件没有严重的问题
				//真正商品内容从2开始，（以0为基）
				com.yougou.pc.model.commodityinfo.Commodity tempCommodity = null;
				Map<String,Integer> prodMap = null;
				boolean anotherCommflag = false;
				int commCount = 0;
				int rowIndex = 0;
				List<ExcelErrorVo> errorList2 = null;
				String commodityName = null;
				for(int i=2;i<=rowNum;i++){
					row = sheet.getRow(i);
					errorList2 = new ArrayList<ExcelErrorVo>();
					if(StringUtils.isNotBlank(ExcelToDataModelConverter.getCellValue(row,0))){
						commCount++;
					}
					if(!checkCommodityRow(row.getLastCellNum(), row, errorList2, defaultCellStyle)){
						for (ExcelErrorVo errorVo : errorList2) {
							Cell _cell = ExcelToDataModelConverter.getCell(sheet, errorVo.getRow(), errorVo.getColumn());
							_cell.setCellStyle(errorStyle);
							_cell.removeCellComment();
							_cell.setCellComment(ExcelToDataModelConverter.createComment(drawing, 3, 3, 6, 6, errorVo.getErrMsg()));
						}
						errorCount++;
						continue;
					}else{
						if(StringUtils.isNotBlank(ExcelToDataModelConverter.getCellValue(row,2))){
							tempCommodity = commodityApi.getCommodityByNo(StringUtils.trim(ExcelToDataModelConverter.getCellValue(row,2)));
							if(tempCommodity!=null){
								anotherCommflag = true;
								rowIndex = i;
								commodity = tempCommodity;
								//从第三列颜色开始
								commodity.setColorName(ExcelToDataModelConverter.getCellValue(row,3));
								//商品名称
								//商品名称过回车、转义符、英文双引号、中间多个英文空格保留一个，中文空格，只保留前200个字
								commodityName = ExcelToDataModelConverter.getCellValue(row,4);
								commodityName = StringUtils.chomp(commodityName
										.replace("\"", "")
										.replace("\n", "")
										.replace("\\", "")
										.replaceAll("　", "")//中文空格
										.replaceAll("\\s+", " ")//中间多个英文空格，保留一个
										.trim());
								commodity.setCommodityName(commodityName);
								//商品款号
								commodity.setStyleNo(ExcelToDataModelConverter.getCellValue(row,5));
								//款色编码
								commodity.setSupplierCode(ExcelToDataModelConverter.getCellValue(row,6));
								//市场价
								commodity.setMarkPrice(Double.parseDouble(ExcelToDataModelConverter.getCellValue2(row.getCell(7),true)));
								//优购价
								commodity.setSellPrice(Double.parseDouble(ExcelToDataModelConverter.getCellValue2(row.getCell(8),true)));
							}else{
								continue;
							}
						}else{
							commodity = tempCommodity;
							anotherCommflag = false;
						}
						//修改时间
						commodity.setUpdateDate(new Date());
						if(row.getLastCellNum()>=12){	//按尺码导入
							if(anotherCommflag){
								prodMap = new HashMap<String,Integer>();
							}
							//货品条码
							for(Product product : commodity.getProducts()){
								if(product.getSizeName().equals(ExcelToDataModelConverter.getCellValue2(row.getCell(9),true))){
									product.setInsideCode(ExcelToDataModelConverter.getCellValue(row,10));
									product.setThirdPartyInsideCode(ExcelToDataModelConverter.getCellValue(row,10));
									//库存
										//inventoryDomainService.updateInventoryForMerchant(product.getProductNo(), 
											//wareHouseCode, Integer.parseInt(ExcelToDataModelConverter.getCellValue(row,11)), NumberUtils.INTEGER_ZERO);
									prodMap.put(product.getProductNo(), Integer.parseInt(ExcelToDataModelConverter.getCellValue(row,11)));
									break;
								}
							}
							if((i+1)<=rowNum){
								row = sheet.getRow(i+1);
								if(StringUtils.isNotBlank(ExcelToDataModelConverter.getCellValue(row,2))){
									//修改库存
									Map<String, Date> map = null;
									for(Map.Entry<String, Integer> entry : prodMap.entrySet()){
										map =  inventoryDomainService.updateInventoryForMerchant(entry.getKey(), 
											wareHouseCode, entry.getValue(), NumberUtils.INTEGER_ZERO);
										log.warn("修改库存信息：{}",map);
									}
									String result = commodityApi.saveAllCommodityMsgForMerchant(commodity);
									commoditys = ArrayUtils.add(commoditys, commodity);
									//插入数据库发生错误，记录
									if(!(result.equalsIgnoreCase(RESULT_SUCCESS))){
										Cell _cell = ExcelToDataModelConverter.getCell(sheet, rowIndex, 0);
										_cell.setCellStyle(errorStyle);
										_cell.removeCellComment();
										_cell.setCellComment(ExcelToDataModelConverter.createComment(drawing, 3, 3, 6, 6, "该商品"+result));
										errorCount++;
									}else{
										//插入成功，记录rowIndex，方便删除成功的行
										for(int m=rowIndex;m<=i;m++){
											successIndexList.add(m);
										}
									}
								}
							}else{
								//修改库存
								Map<String, Date> map = null;
								for(Map.Entry<String, Integer> entry : prodMap.entrySet()){
									map =  inventoryDomainService.updateInventoryForMerchant(entry.getKey(), 
										wareHouseCode, entry.getValue(), NumberUtils.INTEGER_ZERO);
									log.warn("修改库存信息：{}",map);
								}
								String result = commodityApi.saveAllCommodityMsgForMerchant(commodity);
								commoditys = ArrayUtils.add(commoditys, commodity);
								//插入数据库发生错误，记录
								if(!(result.equalsIgnoreCase(RESULT_SUCCESS))){
									Cell _cell = ExcelToDataModelConverter.getCell(sheet, rowIndex, 0);
									_cell.setCellStyle(errorStyle);
									_cell.removeCellComment();
									_cell.setCellComment(ExcelToDataModelConverter.createComment(drawing, 3, 3, 6, 6, "该商品"+result));
									errorCount++;
								}else{
									//插入成功，记录rowIndex，方便删除成功的行
									for(int m=rowIndex;m<=i;m++){
										successIndexList.add(m);
									}
								}
							}
						}else{		//按商品导入
							String result = commodityApi.saveAllCommodityMsgForMerchant(commodity);
							commoditys = ArrayUtils.add(commoditys, commodity);
							//插入数据库发生错误，记录
							if(!(result.equalsIgnoreCase(RESULT_SUCCESS))){
								Cell _cell = ExcelToDataModelConverter.getCell(sheet, row.getRowNum(), 0);
								_cell.setCellStyle(errorStyle);
								_cell.removeCellComment();
								_cell.setCellComment(ExcelToDataModelConverter.createComment(drawing, 3, 3, 6, 6, result));
								errorCount++;
							}else{
								//插入成功，记录rowIndex，方便删除成功的行
								successIndexList.add(rowIndex);
							}
						}
					}
				}
				//插入数据库发生错误，记录并生成excel
				if(errorCount>0){
					//删除导入成功的行
					for(int i=0,k=0;i<successIndexList.size();i++,k++){
						//ExcelToDataModelConverter.removeSuccessRow(sheet, successIndexList.get(i)-k, defaultCellStyle, errorStyle);
						ExcelToDataModelConverter.removeRow(sheet, successIndexList.get(i)-k, defaultCellStyle, errorStyle);
					}
					//查看备注信息
					//返回正确的插入失败的商品数量
					errorCount = ExcelToDataModelConverter.logCellComment(sheet, 2);
					// 设置默认选中第一条错误数据行
					ExcelToDataModelConverter.setErrorCellAsActive(sheet);
					// 将错误数据行写入Excel文件
					String uuid = UUID.randomUUID().toString();
					OutputStream os = null;
					try {
						os = new FileOutputStream(getErrorXlsPathname(uuid,"xlsx"));
						work.write(os);
					} finally {
						IOUtils.closeQuietly(os);
						jsonObject.accumulate("uuid", uuid);
					}
					//将文件缓存到redis
					InputStream is = new FileInputStream(getErrorXlsPathname(uuid,"xlsx"));
					this.redisTemplate.opsForHash().put(CacheConstant.C_COMMODITY_IMPORT_ERROR_KEY, uuid, IOUtils.toByteArray(is));
				}
				rowNum = commCount;
				jsonObject.put("type", "xlsx");
			}
		}
		
		//记录日志
		Map<String,String> infoMap = insertSensitiveWordLog(true,commoditys);
		String warnInfo = "";
		if(infoMap.size() > 0){
			for(String sensitiveWord : infoMap.keySet()){
				warnInfo += "款色编码："+infoMap.get(sensitiveWord)+" 检测到敏感词：<span style=\"color:red\">"+sensitiveWord+"</span><br/>";
			}
		}
		jsonObject.put("warnInfo", warnInfo);
		jsonObject.put("resultCode", ResultCode.SUCCESS.getCode());
		jsonObject.put("errorCount", errorCount);
		jsonObject.put("allCount", rowNum);
		return jsonObject.toString();
	}

	/** 
	 * checkCommodity:校验 03/07的excel数据格式
	 * （属于文件严重问题，不可跳过）
	 * @author li.n1 
	 * @param sheet
	 * @return 
	 * @since JDK 1.6 
	 */  
	private boolean checkCommodity(Sheet sheet,List<ExcelErrorVo> errorList,CellStyle cellStyle) {
		String[] titleName = new String[]{"一级分类","品牌","商品编码","颜色","商品名称","商品款号","款色编码","市场价","优购价","尺码","货品条码","库存"};
		Row row = null;
		row = sheet.getRow(1);
		if(row!=null){
			int rowNumIndex = row.getLastCellNum();
			if(rowNumIndex==IMPORTBYCOMMCOLUMN||rowNumIndex==IMPORTBYSIZECOLUMN){
				for(int c=0;c<row.getLastCellNum();c++){
					if(!titleName[c].equals(ExcelToDataModelConverter.getCellValue(row,c))){
						errorList.add(new ExcelErrorVo("错误列",titleName[c]+"列不允许被修改或变换顺序",row.getRowNum(),c));
					}
				}
				if(errorList.size()>0){	//格式有错误
					return false;
				}
				return true;
			}else{
				for(int c=0;c<row.getLastCellNum();c++){
					if(!titleName[c].equals(ExcelToDataModelConverter.getCellValue(row,c))){
						errorList.add(new ExcelErrorVo("错误列",titleName[c]+"列不允许被删除或修改或变换顺序",row.getRowNum(),c));
					}
				}
				return false;
			}
		}
		return false;
	}
	
	/** 
	 * checkCommodity:校验 03/07的excel每行内容
	 * （属于小问题，行有错误可跳过执行下一行）
	 * @author li.n1 
	 * @param sheet
	 * @return true 检验行没问题，false 有问题
	 * @since JDK 1.6 
	 */  
	private boolean checkCommodityRow(int cellNumIndex,Row row,List<ExcelErrorVo> errorList,CellStyle cellStyle) {
		//均不能为空！
		//市场价、优购价只能为数字 7/8
		//款色编码不能包含中文  6 (创建不能为中文，修改不做判断【对已有为中文不做判断】)
		//款号不能包含中文 5
		//颜色 3
		//货品条码不能重复  10
		//货品条码不能为中文 10
		//库存为自然数 11
		int r = row.getRowNum();
		if(row!=null){
			if(cellNumIndex==IMPORTBYCOMMCOLUMN||cellNumIndex==IMPORTBYSIZECOLUMN){
				switch(cellNumIndex){
					case IMPORTBYCOMMCOLUMN:
						if(StringUtils.isBlank(ExcelToDataModelConverter.getCellValue(row,2))){		//按商品导出
							errorList.add(new ExcelErrorVo("商品编码","商品编码不能为空",row.getRowNum(),2));
						}else{
							row.getCell(2).removeCellComment();
							row.getCell(2).setCellStyle(cellStyle);
						}
						//针对按尺码设置价格，商品的颜色为“尺码_颜色”
						//此处验证有问题，不好验证，此处验证省略
						/*if(!(Pattern.compile("^[\u4E00-\u9FA5]+(/[\u4E00-\u9FA5]+)*$").matcher(ExcelToDataModelConverter.getCellValue(row,3)).matches())||
								StringUtils.isBlank(ExcelToDataModelConverter.getCellValue(row,3))){		
							errorList.add(new ExcelErrorVo("颜色","商品颜色必须为中文汉字或/,格式参考\"蓝色\",\"蓝色/黄色\"",row.getRowNum(),3));
						}else{
							row.getCell(3).removeCellComment();
							row.getCell(3).setCellStyle(cellStyle);
						}*/
						
						if(StringUtils.isBlank(ExcelToDataModelConverter.getCellValue(row,4))){		//按商品导出
							errorList.add(new ExcelErrorVo("商品名称","商品名称不能为空",row.getRowNum(),4));
						}else{
							row.getCell(4).removeCellComment();
							row.getCell(4).setCellStyle(cellStyle);
						}
						
						if(Pattern.compile("[\u4e00-\u9fa5]").matcher(ExcelToDataModelConverter.getCellValue(row,5)).find()||
								StringUtils.isBlank(ExcelToDataModelConverter.getCellValue(row,5))){		//按商品导出
							errorList.add(new ExcelErrorVo("商品款号","商品款号不能包含中文或为空",row.getRowNum(),5));
						}else{
							row.getCell(5).removeCellComment();
							row.getCell(5).setCellStyle(cellStyle);
						}
						//if(Pattern.compile("[\u4e00-\u9fa5]").matcher(ExcelToDataModelConverter.getCellValue(row,6)).find()||
						
						if(StringUtils.isBlank(ExcelToDataModelConverter.getCellValue(row,6))){		//按商品导出
							errorList.add(new ExcelErrorVo("款色编码","款色编码不能为空",row.getRowNum(),6));
						}else{
							row.getCell(6).removeCellComment();
							row.getCell(6).setCellStyle(cellStyle);
						}
						
						if(!(Pattern.compile("^[0-9]+\\.{0,1}[0-9]{0,9}$").matcher(ExcelToDataModelConverter.getCellValue(row,7)).find())||
								StringUtils.isBlank(ExcelToDataModelConverter.getCellValue(row,7))
								){		//按商品导出
							errorList.add(new ExcelErrorVo("市场价","市场价只能为整数或小数",row.getRowNum(),7));
						}else{
							row.getCell(7).removeCellComment();
							row.getCell(7).setCellStyle(cellStyle);
						}
						
						if(!(Pattern.compile("^[0-9]+\\.{0,1}[0-9]{0,9}$").matcher(ExcelToDataModelConverter.getCellValue(row,8)).find())||
								StringUtils.isBlank(ExcelToDataModelConverter.getCellValue(row,8))
								&&row.getLastCellNum()==IMPORTBYCOMMCOLUMN){		//按商品导出
							errorList.add(new ExcelErrorVo("优购价","优购价只能为整数或小数",row.getRowNum(),8));
						}else{
							row.getCell(8).removeCellComment();
							row.getCell(8).setCellStyle(cellStyle);
						}
						break;
					case IMPORTBYSIZECOLUMN:
						if((StringUtils.isBlank(ExcelToDataModelConverter.getCellValue(row,2))		//按尺码导出
								&&(r==2))){
							errorList.add(new ExcelErrorVo("商品编码","商品编码不能为空",row.getRowNum(),2));
						}else{
							row.getCell(2).removeCellComment();
							row.getCell(2).setCellStyle(cellStyle);
						}
						//针对按尺码设置价格，商品的颜色为“尺码_颜色”
						//此处验证有问题，不好验证，此处验证省略
						/*if((StringUtils.isNotBlank(ExcelToDataModelConverter.getCellValue(row,3)) && 
								!(Pattern.compile("^[\u4E00-\u9FA5]+(/[\u4E00-\u9FA5]+)*$")
										.matcher(ExcelToDataModelConverter.getCellValue(row,3)).matches()))||
								(StringUtils.isBlank(ExcelToDataModelConverter.getCellValue(row,3))&&(r==2))){		//按尺码导出
							errorList.add(new ExcelErrorVo("颜色","商品颜色必须为中文汉字或/,格式参考\"蓝色\",\"蓝色/黄色\"",row.getRowNum(),3));
						}else{
							row.getCell(3).removeCellComment();
							row.getCell(3).setCellStyle(cellStyle);
						}*/
						if((StringUtils.isBlank(ExcelToDataModelConverter.getCellValue(row,4)))&&(r==2)){		//按尺码导出
							errorList.add(new ExcelErrorVo("商品名称","商品名称不能为空",row.getRowNum(),4));
						}else{
							row.getCell(4).removeCellComment();
							row.getCell(4).setCellStyle(cellStyle);
						}
						if((Pattern.compile("[\u4e00-\u9fa5]").matcher(ExcelToDataModelConverter.getCellValue(row,5)).find()||
								(StringUtils.isBlank(ExcelToDataModelConverter.getCellValue(row,5))))&&(r==2)){		//按尺码导出
							errorList.add(new ExcelErrorVo("商品款号","商品款号不能包含中文或为空",row.getRowNum(),5));
						}else if(StringUtils.isNotBlank(ExcelToDataModelConverter.getCellValue(row,5))&&
								Pattern.compile("[\u4e00-\u9fa5]")
								.matcher(ExcelToDataModelConverter.getCellValue(row,5)).find()){
							errorList.add(new ExcelErrorVo("商品款号","商品款号不能包含中文或为空",row.getRowNum(),5));
						}else{
							row.getCell(5).removeCellComment();
							row.getCell(5).setCellStyle(cellStyle);
						}
						if((Pattern.compile("[\u4e00-\u9fa5]").matcher(ExcelToDataModelConverter.getCellValue(row,6)).find()||
								(StringUtils.isBlank(ExcelToDataModelConverter.getCellValue(row,6))))&&(r==2)){		//按尺码导出
							errorList.add(new ExcelErrorVo("款色编码","款色编码不能包含中文或为空",row.getRowNum(),6));
						}else if(StringUtils.isNotBlank(ExcelToDataModelConverter.getCellValue(row,6))&&
								Pattern.compile("[\u4e00-\u9fa5]").matcher(ExcelToDataModelConverter.getCellValue(row,6)).find()){
							errorList.add(new ExcelErrorVo("款色编码","款色编码不能包含中文或为空",row.getRowNum(),6));
						}else{
							row.getCell(6).removeCellComment();
							row.getCell(6).setCellStyle(cellStyle);
						}
						if((!(Pattern.compile("^[0-9]+\\.{0,1}[0-9]{0,9}$").matcher(ExcelToDataModelConverter.getCellValue(row,7)).find())||
								StringUtils.isBlank(ExcelToDataModelConverter.getCellValue(row,7)))&&(r==2)){		//按尺码导出
							errorList.add(new ExcelErrorVo("市场价","市场价只能为整数或小数",row.getRowNum(),7));
						}else if(StringUtils.isNotBlank(ExcelToDataModelConverter.getCellValue(row,7))&&
								!(Pattern.compile("^[0-9]+\\.{0,1}[0-9]{0,9}$")
										.matcher(ExcelToDataModelConverter.getCellValue(row,7)).find())){
							errorList.add(new ExcelErrorVo("市场价","市场价只能为整数或小数",row.getRowNum(),7));
						}else{
							row.getCell(7).removeCellComment();
							row.getCell(7).setCellStyle(cellStyle);
						}
						if((!(Pattern.compile("^[0-9]+\\.{0,1}[0-9]{0,9}$").matcher(ExcelToDataModelConverter.getCellValue(row,8)).find())||
								StringUtils.isBlank(ExcelToDataModelConverter.getCellValue(row,8)))&&(r==2)){		//按尺码导出
							errorList.add(new ExcelErrorVo("优购价","优购价只能为整数或小数",row.getRowNum(),8));
						}else if(StringUtils.isNotBlank(ExcelToDataModelConverter.getCellValue(row,8))&&
								!(Pattern.compile("^[0-9]+\\.{0,1}[0-9]{0,9}$")
										.matcher(ExcelToDataModelConverter.getCellValue(row,8)).find())){
							errorList.add(new ExcelErrorVo("优购价","优购价只能为整数或小数",row.getRowNum(),8));
						}else{
							row.getCell(8).removeCellComment();
							row.getCell(8).setCellStyle(cellStyle);
						}
						if(StringUtils.isBlank(ExcelToDataModelConverter.getCellValue(row,9))){		//按尺码导出
							errorList.add(new ExcelErrorVo("尺码","尺码不能为空",row.getRowNum(),9));
						}else{
							row.getCell(9).removeCellComment();
							row.getCell(9).setCellStyle(cellStyle);
						}
						if(Pattern.compile("[\u4e00-\u9fa5]").matcher(ExcelToDataModelConverter.getCellValue(row,10)).find()||
								StringUtils.isBlank(ExcelToDataModelConverter.getCellValue(row,10))){		//按尺码导出
							errorList.add(new ExcelErrorVo("货品条码","货品条码不能包含中文或为空",row.getRowNum(),10));
						}else{
							row.getCell(10).removeCellComment();
							row.getCell(10).setCellStyle(cellStyle);
						}
						if(!(Pattern.compile("^([1-9]\\d*|0)$").matcher(ExcelToDataModelConverter.getCellValue(row,11)).find())||
								StringUtils.isBlank(ExcelToDataModelConverter.getCellValue(row,11))){		//按尺码导出
							errorList.add(new ExcelErrorVo("库存","库存只能为正整数或0",row.getRowNum(),11));
						}else{
							row.getCell(11).removeCellComment();
							row.getCell(11).setCellStyle(cellStyle);
						}
						break;
					}
				if(errorList.size()>0){	//格式有错误
					/*for(ExcelErrorVo vo : errorList){
						System.out.println("行="+vo.getRow()+" 列="+vo.getColumn()+" 错误信息="+vo.getErrMsg());
					}*/
					return false;
				}
				return true;
			}else{
				errorList.add(new ExcelErrorVo("错误","该行最后一列的后面单元格内容改动过！",row.getRowNum(),0));
			}
		}
		return false;
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
	
	/****
	 * 待售商品回收站
	 * @param modelMap
	 * @param request
	 * @param query
	 * @param queryVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("go4SaleCommodityRecycle")
	public String go4SaleCommodityRecycle(ModelMap modelMap, HttpServletRequest request, Query query, CommodityQueryVo commodityQueryVo) {
		//加入当前商家的信息
		commodityQueryVo.setMerchantCode(YmcThreadLocalHolder.getMerchantCode());
		commodityQueryVo.setWarehouseCode(SessionUtil.getWarehouseCodeFromSession(request));
		commodityQueryVo.setRecyclebinFlag("1");
		//查询主数据
		query.setPageSize(20);
		PageFinder<Commodity> pageFinder = commodityService.queryCommodityList(query, commodityQueryVo, modelMap);
		
		if(!isEmptyForPageFinder(pageFinder)){
			modelMap.put("pageFinder", pageFinder);
		}
		modelMap.addAttribute("tagTab", "goods");
		modelMap.put("commodityQueryVo", commodityQueryVo);
		return "/manage/commodity/recycle_commodity";
	}
	
	/**
	 * 彻底删除
	 * @param request
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delForever")
	public String delForever(HttpServletRequest request,String ids){
		JSONObject jsonObject = new JSONObject();
		boolean result = false;
		try{
			if(StringUtils.isEmpty(ids)){
				jsonObject.put("resultCode",ResultCode.ERROR.getCode());
				jsonObject.put("msg","参数错误");
			}
			String[] commodityNoArry=ids.split(",");
			com.yougou.pc.model.commodityinfo.Commodity commodity=null;
			String supplierCode = null;
			String merchantCode = YmcThreadLocalHolder.getMerchantCode();
			int successCount = 0;
			String failMsg = "";
			for(String commodityNo:commodityNoArry){
				commodity = commodityMerchantApiService.getCommodityByNo(commodityNo);
				if(commodity==null){
					log.info("商品：{}不存在",commodityNo);
					continue;
				}
				// 删除商品系统商品资料
				String delResult = commodityMerchantApiService.deleteMerchantByCommodityNo(merchantCode, commodityNo);
				if("SUCCESS".equals(delResult)){
					result = true;
					successCount++;
				}else{
					failMsg +=commodityNo+":"+delResult+"<br/>";
				}
				supplierCode = commodity.getSupplierCode();
				log.info("Delete commodity {} with '{}', server return date {}",new Object[]{commodityNo,merchantCode,delResult});
				
				// 删除招商系统商品图片
				result = result && merchantCommodityService.deleteMerchantCommodityAllPics(commodity.getSupplierCode(), merchantCode);
				log.info("Delete commodity {} all pics with '{}:{}', return date {}", new Object[]{commodityNo,supplierCode,merchantCode,result});
				
				// 清零被删除的商品库存
				if (result) {
					try {
						taobaoItemService.delTaoBaoItemByCommodityNo(merchantCode, commodityNo);
					} catch (BusinessException e) {
						log.error("{},彻底删除商品:{}关联的淘宝资料异常",new Object[]{merchantCode,commodityNo,e});
					}
					// 获取商家虚拟仓库
					String warehouseCode = warehouseService.queryWarehouseCodeByMerchantCode(merchantCode);
					for (Product product : commodity.getProducts()) {
						Map<String, Date> temporaryMap = inventoryDomainService.updateInventoryForMerchant(product.getProductNo(), warehouseCode, NumberUtils.INTEGER_ZERO, NumberUtils.INTEGER_ZERO);
						log.info("Updated inventory quantity 0 with merchant '{}' at {}", merchantCode, temporaryMap);
					}
				}
			}
			if(successCount > 0){	
				String msg = "成功删除"+successCount+"个商品";
				if(commodityNoArry.length-successCount > 0){
					msg += ",失败"+(commodityNoArry.length-successCount)+"个,原因<br/>"+failMsg;
				}
				jsonObject.put("resultCode",ResultCode.SUCCESS.getCode());
				jsonObject.put("msg",msg);
			}else{
				jsonObject.put("resultCode",ResultCode.ERROR.getCode());
				jsonObject.put("msg","删除"+commodityNoArry.length+"个商品失败,原因<br/>"+failMsg);
			}
		}catch(Exception e){
			jsonObject.put("resultCode",ResultCode.ERROR.getCode());
			jsonObject.put("msg","系统异常");
			log.error(e.getMessage(),e);
		}
		return jsonObject.toString();
	}
	
	@ResponseBody
	@RequestMapping("/recoveryCommodity")
	public String recoveryCommodity(HttpServletRequest request,String ids){
		JSONObject jsonObject = new JSONObject();
		try{
			if(StringUtils.isEmpty(ids)){
				jsonObject.put("resultCode",ResultCode.ERROR.getCode());
				jsonObject.put("msg","参数错误");
			}
			String[] commodityNoArry=ids.split(",");
			com.yougou.pc.model.commodityinfo.Commodity commodity=null;
			String merchantCode = YmcThreadLocalHolder.getMerchantCode();
			for(String commodityNo:commodityNoArry){
				commodity = commodityMerchantApiService.getCommodityByNo(commodityNo);
				if(commodity==null){
					log.info("商品：{}不存在", commodityNo );
					continue;
				}
				commodityApi.recoveryMerchantCommodity2WaitSaleByCommodityNo(merchantCode, commodityNo);
			}
			jsonObject.put("resultCode",ResultCode.SUCCESS.getCode());
		}catch(Exception e){
			jsonObject.put("resultCode",ResultCode.ERROR.getCode());
			jsonObject.put("msg","系统异常");
			log.error(e.getMessage(),e);
		}
		return jsonObject.toString();
	}
	

	@ResponseBody
	@RequestMapping("/updatePrice4OnSale")
	public String updatePrice4OnSale(HttpServletRequest request,String commodityNo,String price,String type){
		JSONObject jsonObject = new JSONObject();
		try{
			String merchantCode = YmcThreadLocalHolder.getMerchantCode();
			commodityService.updateOnsalePrice(commodityNo, merchantCode, price, type);
			jsonObject.put("resultCode",ResultCode.SUCCESS.getCode());
		}catch(BusinessException e){
			jsonObject.put("resultCode",ResultCode.ERROR.getCode());
			jsonObject.put("msg",e.getMessage());
			log.error(e.getMessage());
		}catch(Exception e){
			jsonObject.put("resultCode",ResultCode.ERROR.getCode());
			jsonObject.put("msg",e.getMessage());
			log.error(e.getMessage(),e);
		}
		return jsonObject.toString();
	}

	public String getPriceBySizePropNo() {
		return priceBySizePropNo;
	}

	public void setPriceBySizePropNo(String priceBySizePropNo) {
		this.priceBySizePropNo = priceBySizePropNo;
	}
	
}
