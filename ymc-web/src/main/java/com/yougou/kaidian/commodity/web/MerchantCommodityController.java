package com.yougou.kaidian.commodity.web;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.net.util.Base64;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.yougou.fss.api.IFSSYmcApiService;
import com.yougou.kaidian.commodity.beans.BeanPropertyEqualsPredicate;
import com.yougou.kaidian.commodity.beans.CommodityWrapper;
import com.yougou.kaidian.commodity.component.CommodityComponent;
import com.yougou.kaidian.commodity.constant.CommodityConstant;
import com.yougou.kaidian.commodity.convert.DataModelToExcelConverter;
import com.yougou.kaidian.commodity.convert.ExcelToDataModelConverter;
import com.yougou.kaidian.common.commodity.pojo.Cat;
import com.yougou.kaidian.commodity.model.vo.CmsCommodityProp;
import com.yougou.kaidian.commodity.model.vo.CommoditySubmitNewVo;
import com.yougou.kaidian.commodity.model.vo.CommoditySubmitVo;
import com.yougou.kaidian.commodity.model.vo.ErrorVo;
import com.yougou.kaidian.commodity.model.vo.ExcelErrorVo;
import com.yougou.kaidian.commodity.service.ICommodityExtendService;
import com.yougou.kaidian.commodity.service.ICommodityService;
import com.yougou.kaidian.commodity.service.IMerchantCommodityService;
import com.yougou.kaidian.commodity.util.CommodityUtil;
import com.yougou.kaidian.commodity.util.CommodityUtilNew;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.framework.beans.DubboProvider;
import com.yougou.kaidian.framework.exception.DubboServiceException;
import com.yougou.kaidian.framework.settings.CommoditySettings;
import com.yougou.kaidian.common.util.HttpUtil;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.stock.service.IWarehouseService;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.api.ICommodityMerchantApiService;
import com.yougou.pc.model.category.Category;
import com.yougou.pc.model.commodityinfo.Commodity;
import com.yougou.pc.model.commodityinfo.CommodityDto;
import com.yougou.pc.model.merchant.MerchantImportInfo;
import com.yougou.pc.model.merchant.MerchantImportModel;
import com.yougou.pc.model.picture.Picture;
import com.yougou.pc.model.product.Product;
import com.yougou.pc.vo.commodity.CommodityPropVO;
import com.yougou.pc.vo.commodity.CommodityVO;
import com.yougou.pc.vo.commodity.ProductVO;
import com.yougou.wms.wpi.common.exception.WPIBussinessException;
import com.yougou.wms.wpi.inventory.service.IInventoryDomainService;

/**
 * 商品图片空间控制器(批量)
 * 
 * @author yang.mq
 *
 */
@Controller
@RequestMapping("/commodity")
public class MerchantCommodityController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantCommodityController.class);
	
	@Resource
	private IMerchantCommodityService merchantCommodityService;
	@Resource
	private ICommodityMerchantApiService commodityMerchantApiService;
	@Resource
	private ICommodityBaseApiService commodityBaseApiService;
	@Resource
	private IWarehouseService warehouseService;
	@Resource
	private IInventoryDomainService inventoryDomainService;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private CommoditySettings settings;
	@Resource
	private ICommodityMerchantApiService commodityApi;
	@Resource
	private ICommodityService commodityService;
	@Resource
	private IFSSYmcApiService fssYmcApiService;
	@Resource
	private CommodityComponent commodityComponent;
	@Resource
	private ICommodityExtendService commodityExtendService;
	

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	
	/**
	 * 获取分类信息
	 * @param structName
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/import/struct")
	public String getChildrenStructsWithLoggedMerchant(String structName, HttpServletRequest request) {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		//String merchantId = SessionUtil.getSessionProperty(request, "supplierId");
		//List<Category> categories = commodityMerchantApiService.getCategoryByMerchant(merchantId, structName, null);
		List<Cat> categories = commodityService.queryCatList(merchantCode, structName);
		LOGGER.info("Qeruy category {} with '{}'.", StringUtils.defaultIfEmpty(structName, "null"), merchantCode );
		return JSONArray.fromObject(ObjectUtils.defaultIfNull(categories, Collections.EMPTY_LIST)).toString();
	}
	
	/**
	 * 跳转到批量导入商品页面
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping("/import/ready")
	public ModelAndView readyBatchImportCommodity(ModelMap modelMap, HttpServletRequest request) {
		modelMap.addAttribute("tagTab", "goods");
		modelMap.addAttribute("commodityPreviewUrl", settings.commodityPreviewPersistentUrl);
		return new ModelAndView("/manage/commodity/batch_import_commodity", modelMap);
	}
	
	/**
	 * 获取批量导入商品列表
	 * @param modelMap
	 * @param query
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/import/list")
	public ModelAndView listBatchImportCommodity(ModelMap modelMap, Query query) 
			throws Exception {
		final String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		// 处理批量导入描述图片后、而未处理的商品描述字符串
		merchantCommodityService.commodityDescRedisOpt(merchantCode);
		
		//Modify by meijunfeng 20130912 原接口废弃换新接口。
		//CommodityDto commodityDto = commodityMerchantApiService.getCommodityByMerchant(merchantCode, COMMODITY_STATUS_NEW, query.getPageSize(), query.getPage());
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("merchantCode", merchantCode);
		paramMap.put("commodityStatusList", Arrays.<Integer>asList(11));	//11为新建状态
		paramMap.put("deleteFlag", (short)1);
		paramMap.put("recyclebinFlag", (short)0);
		paramMap.put("sortFields", "update_date");
		CommodityDto commodityDto = commodityBaseApiService.getCommodityListByParams(paramMap, query.getPage(), query.getPageSize(), false, true, false);

		LOGGER.info("Qeruy commodity limit {},{} with '{}'", new Object[]{query.getPage() , query.getPageSize(), merchantCode } );

		if (commodityDto != null) { // 包装商品对象注入商品最低要求图片总张数
			CollectionUtils.transform(commodityDto.getCommodityList(),
					new Transformer() {

						@Override
						public Object transform(Object bean) {
							Commodity commodity = Commodity.class.cast(bean);
							try {
								int number = merchantCommodityService
										.getCommodityImageLeastNumbersByCommodityNo(commodity
												.getCommodityNo());
								int uploadStatus = merchantCommodityService
										.getCommodityImageUploadStatus(commodity);
								return new CommodityWrapper(commodity, number,
										uploadStatus);
							} catch (Exception ex) {
								throw new IllegalStateException("商家["+merchantCode+"],商品款色编码：["
										+ commodity.getSupplierCode()
										+ "]的分类信息异常,请联系优购系统管理员.", ex);
							}
						}
					});
			modelMap.put(
					"pageFinder",
					new PageFinder<Commodity>(query.getPage(), query
							.getPageSize(), commodityDto.getCount(),
							commodityDto.getCommodityList()));
		}

		/*
		 * List<Commodity> commodityList = commodityDto.getCommodityList();
		 * List<CommodityWrapper> CommodityWrapperList = new
		 * ArrayList<CommodityWrapper>(); for (Commodity commodity :
		 * commodityList) { new CommodityWrapper(commodity, numbers);
		 * if(commodity.getPictures()==null||commodity.getPictures().size()==0){
		 * int number=merchantCommodityService
		 * .getCommodityImageLeastNumbersByCommodityNo
		 * (commodity.getCommodityNo()) } int latestPicNumbers =
		 * merchantCommodityService .getLatestPicNumbers(merchantCode,
		 * commodity.getSupplierCode());
		 * 
		 * }
		 */
		modelMap.addAttribute("commodityPreviewUrl", settings.commodityPreviewPersistentUrl);
		return new ModelAndView("/manage_unless/commodity/batch_import_commodity_list", modelMap);
	}

	/**
	 * 批量提交审核
	 * @param commodityNo
	 * @param supplierCode
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/import/audit")
	public String auditImportCommodity(String[] commodityNo, String[] supplierCode,Short followOperate,
			HttpServletRequest request) {
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		JSONArray jsonArray = new JSONArray();
		if (ArrayUtils.isNotEmpty(commodityNo)) {
			Commodity commodity = null;
			CommoditySubmitNewVo newVo = null;
			for (int i = 0; i < commodityNo.length; i++) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.accumulate("commodityNo", commodityNo[i]);
				try {
					commodity = commodityApi.getCommodityByNo(commodityNo[i]);
					merchantCommodityService.checkCommodityPicsIntegrality(commodityNo[i], supplierCode[i], merchantCode,commodity.getBrandNo());
					jsonObject.accumulate("auditMessage", commodityMerchantApiService.auditMerchant(commodityNo[i], merchantCode));
					LOGGER.info("Audit commodity {} with merchant '{}', server return data {}" ,
							new Object[]{commodityNo[i], merchantCode, jsonObject.get("auditMessage")});
					newVo = CommodityUtilNew.buildCommoditySubmitNewVo(commodity,true);
					newVo.setFollowOperate(followOperate);
					if(!commodityExtendService.insertCommodityExtendAndLog(newVo,
							YmcThreadLocalHolder.getOperater(),"批量发布")){
						LOGGER.error("商家{}插入商品{}扩展表数据与日志报错！",new Object[]{commodity.getMerchantCode(),commodityNo[i]});
					}
				} catch (Exception ex) {
					LOGGER.error("发生错误！！",ex);
					jsonObject.accumulate("auditMessage", StringUtils.defaultIfEmpty(ex.getMessage(), "null"));
				}
				jsonArray.add(jsonObject);
			}
		}
		return jsonArray.toString();
	}
	
	/**
	 * 预览
	 * @param commodityVO
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/imported/preview")
	public void previewImportedCommodity(CommodityVO commodityVO, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute(CommodityConstant.COMMODITY_PREVIEW_COMMODITYVO_KEY, commodityVO);
		request.setAttribute(CommodityConstant.COMMODITY_PREVIEW_COMMODITY_SUBMIT_VO_KEY, new CommoditySubmitVo());
		previewImportingCommodity(modelMap, request, response);
	}
	
	/**
	 * 预览
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/import/preview")
	public void previewImportingCommodity(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			CommodityVO commodityVo = (CommodityVO) request.getAttribute(CommodityConstant.COMMODITY_PREVIEW_COMMODITYVO_KEY);
			CommoditySubmitVo commoditySubmitVo = (CommoditySubmitVo) request.getAttribute(CommodityConstant.COMMODITY_PREVIEW_COMMODITY_SUBMIT_VO_KEY);
			if (commodityVo == null) {
				throw new NullPointerException("preview commodity error, commodityVo is null.");
			}
			if (commoditySubmitVo == null) {
				throw new NullPointerException("preview commodity error, commoditySubmitVo is null.");
			}
			
			HttpGet httpGet = null;
			HttpPost httpPost = null;
			if (StringUtils.isNotBlank(commodityVo.getCommodityNo()) && commodityMerchantApiService.getCommodityByNo(commodityVo.getCommodityNo()) != null) {
				httpGet = new HttpGet(MessageFormat.format("{0}?commodityNo={1}", settings.commodityPreviewPersistentUrl, commodityVo.getCommodityNo()));
			} else {
				CommoditySubmitVo submitVo = (CommoditySubmitVo) request.getAttribute(CommodityConstant.COMMODITY_PREVIEW_COMMODITY_SUBMIT_VO_KEY);
				Map<String, Object> priviewVoMap = new HashMap<String, Object>();
				
				// 基础属性
				priviewVoMap.put("brandName", commodityVo.getBrandName());
				priviewVoMap.put("brandEnglishName", commodityVo.getBrandSpeelingName());
				priviewVoMap.put("styleNo", commodityVo.getStyleNo());
				priviewVoMap.put("specValueName", commodityVo.getSpecName());
				priviewVoMap.put("prodName", commodityVo.getCommodityName());
				priviewVoMap.put("breadcrumb", new String[] { commoditySubmitVo.getRootCatName(), commoditySubmitVo.getSecondCatName(), commodityVo.getCatName() });
				priviewVoMap.put("publicPrice", commodityVo.getPublicPrice());
				priviewVoMap.put("salePrice", commodityVo.getSalePrice());
				priviewVoMap.put("prodNo", "12345678");
				priviewVoMap.put("prodId", UUID.randomUUID().toString().replace("-", ""));
				priviewVoMap.put("orderDistributionSide", 1);
				priviewVoMap.put("prodPropDesc", commodityVo.getProdDesc());
				
				// 颜色
				List<Map<String,String>> proColorlist = new ArrayList<Map<String,String>>();
				Map<String,String> colorMap = new HashMap<String,String>();
				colorMap.put("src", "");
				colorMap.put("midpic", "");
				colorMap.put("data_name", commodityVo.getSpecName());
				colorMap.put("data_value", commodityVo.getSpecNo());
				proColorlist.add(colorMap);
				priviewVoMap.put("proColor", proColorlist);
				
				// 尺码
				Set<String> proSizeSet = new HashSet<String>();
				for (ProductVO productVO : commodityVo.getProductMsgList()) {
					proSizeSet.add(productVO.getSizeName());
				}
				priviewVoMap.put("proSizeList", proSizeSet);
				
				// 商品的图片
				// 1、picBigUrl  中尺寸图  
				// 2、picLargeUrl  大尺寸图
				// 3、picSmallUrl  小图
				List<Map<String, Object>> proPicsList = new ArrayList<Map<String,Object>>();
				String[] imgFiles = submitVo.getImgFileId();
				for (int i = 0; i < imgFiles.length; i++) {
					Map<String, Object> proPicsMap = new HashMap<String, Object>();
					StringBuilder sb = new StringBuilder("");
					if (!"-1".equals(imgFiles[i])) 
						sb.append(settings.getCommodityPreviewDomain())
								.append(MessageFormat.format(settings.imageFtpPreTempSpace, commodityVo.getMerchantCode()))
								.append(imgFiles[i]).append(".jpg");
					proPicsMap.put("picBigUrl", sb.toString());
					proPicsMap.put("picLargeUrl", sb.toString());
					proPicsMap.put("picSmallUrl", sb.toString());
					proPicsList.add(proPicsMap);
				}
				priviewVoMap.put("defaultGroupPic", proPicsList);
				priviewVoMap.put("showSecoundImageCat", "no");
				
				// 商品扩展属性
				List<CmsCommodityProp> commodityPropList = new ArrayList<CmsCommodityProp>();
				CmsCommodityProp prop = null;
				for (int i = 0, j = commodityVo.getCommodityPropVOList().size(); i < j;) {
					CommodityPropVO commodityPropVO = commodityVo.getCommodityPropVOList().get(i++);
					prop = new CmsCommodityProp();
					prop.setPropItemName(commodityPropVO.getPropItemName());
					prop.setPropValueName(commodityPropVO.getPropValue());
					commodityPropList.add(prop);
				}
				priviewVoMap.put("commdoityPropList", commodityPropList);
				
				String json = JSONObject.fromObject(priviewVoMap).toString();
				httpPost = new HttpPost(settings.commodityPreviewMemoryUrl);
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("commodityVo", Base64.encodeBase64URLSafeString(json.getBytes())));
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
				httpPost.setEntity(entity);
			}
			
			InputStream is = HttpUtil.executeMethod(httpGet == null ? httpPost : httpGet);
			response.getOutputStream().write(IOUtils.toByteArray(is));
			response.getOutputStream().flush();
		} catch (Exception ex) {
			IOUtils.write("预览商品失败,请联系优购系统管理员!", response.getOutputStream());
			response.getOutputStream().flush();
		} finally {
			IOUtils.closeQuietly(response.getOutputStream());
		}
	}
	
	/**
	 * 删除批量导入商品(单个)
	 * @param commodityNo
	 * @param supplierCode
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/import/delete")
	public String deleteImportCommodity(String commodityNo, String supplierCode, HttpServletRequest request) {
		boolean result = false;
		try {
			final Integer STATUS11= 11 ,STATUS13 =  13 ,STATUS1 =  1 ;
			if(StringUtils.isEmpty(commodityNo)){
				return Boolean.toString(result);
			}
			Commodity commodity = commodityMerchantApiService.getCommodityByNo(commodityNo);
			if(commodity==null||(STATUS11.intValue()!=commodity.getStatus()&&STATUS13.intValue()!=commodity.getStatus()&&STATUS1.intValue()!=commodity.getStatus())){
				return Boolean.toString(result);
			}
			String merchantCode = YmcThreadLocalHolder.getMerchantCode();
			result = commodityApi.deleteMerchantCommodity2RecycleByCommodityNo(merchantCode, commodityNo);
			LOGGER.warn("商家：{}账号：{}删除商品：{}到回收站！",new Object[]{merchantCode,YmcThreadLocalHolder.getOperater(),commodityNo});
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		return Boolean.toString(result);
	}
	
	/**
	 * 删除批量导入商品(批量)
	 * @param commodityNos
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/import/deletebatch")
	public String deleteImportCommodityBatch(String commodityNos, HttpServletRequest request) {
		boolean result = false;
		StringBuilder sb=new StringBuilder();
		final Integer STATUS11= 11 ,STATUS13 =  13 ,STATUS1 =  1 ;
		try {
			if(StringUtils.isEmpty(commodityNos)){
				return Boolean.toString(result);
			}
			String merchantCode = YmcThreadLocalHolder.getMerchantCode();
			String[] commodityNoArry=commodityNos.split(",");
			for(String commodityNo:commodityNoArry){
				commodityNo=StringUtils.substringBefore(commodityNo, "_");
				Commodity commodity = commodityMerchantApiService.getCommodityByNo(commodityNo);
				if(commodity==null||(STATUS11.intValue()!=commodity.getStatus()&&STATUS13.intValue()!=commodity.getStatus()&&STATUS1.intValue()!=commodity.getStatus())){
					result = false;
					sb.append("{"+commodityNo+":"+(result?"ok":"&otimes;")+"}<br/>");
					continue;
				}
				result = commodityApi.deleteMerchantCommodity2RecycleByCommodityNo(merchantCode, commodityNo);
				sb.append("{"+commodityNo+":"+(result?"ok":"&otimes;")+"}<br/>");
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		return sb.toString();
	}

	/**
	 * 下载excel导入模板
	 * @param category
	 * @param catNames
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/import/template/download")
	public void startDownloadImportCommodityTemplate(Category category, String catNames, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputStream os = null;
		try {
			String merchantCode = YmcThreadLocalHolder.getMerchantCode();
			MerchantImportModel importModel = commodityMerchantApiService.getImportModelByCategory(category.getId(), merchantCode);
			LOGGER.info("Query commodity import model {} with '{}'", new Object[]{ category.getId(), merchantCode});
			XSSFWorkbook excel = new DataModelToExcelConverter(category).convert(importModel);
			// 下载生成的模板
			response.reset();
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", MessageFormat.format("attachment; filename={0}_{1}.xlsx", encodeDownloadAttachmentFilename(request, catNames), DateFormatUtils.format(new Date(), "yyyyMMdd")));
			os = response.getOutputStream();
			excel.write(os);
		} finally {
			IOUtils.closeQuietly(os);
		}
	}
	
	/**
	 * 下载导入商品失败excel文件
	 * @param uuid
	 * @param isImport是否为批量导入待售商品1：是
	 * @param type 导出excel的后缀eg：xls、xlsx
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/import/error/download")
	public void startDownloadImportCommodityError(String uuid, String type, 
			String isImport,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		OutputStream os = null;
		try {
			if(type==null){
				type="xlsx";
			}
			String filename = "批量发布商品失败信息";
			if("1".equals(isImport)){
				filename = "批量导入商品失败信息";
			}
			// 下载生成的模板
			response.reset();
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", MessageFormat.format("attachment; filename={0}."+type, encodeDownloadAttachmentFilename(request, filename)));
			byte[] _bytes = (byte[]) this.redisTemplate.opsForHash().get(CacheConstant.C_COMMODITY_IMPORT_ERROR_KEY, uuid);
			os = response.getOutputStream();
			os.write(_bytes);
		} finally {
			IOUtils.closeQuietly(os);
		}
	}
	
	/**
	 * 上传excel-导入商品
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/import")
	public String startBatchImportCommodity(DefaultMultipartHttpServletRequest request, HttpServletResponse response) {
		try {
			String merchantName = request.getParameter("merchantName");
			String merchantCode = request.getParameter("merchantCode");
			String operator = request.getParameter("operator");
			int succeed = 0, fail = 0;
			Set<String> productNoSet=new HashSet<String>();
			
			// 获取商家虚拟仓库
			String warehouseCode = warehouseService.queryWarehouseCodeByMerchantCode(merchantCode);

			// POI 处理上传 Excel
			MultipartFile multipartFile = request.getFile("Filedata");
			XSSFWorkbook excel = new XSSFWorkbook(multipartFile.getInputStream());
			IOUtils.closeQuietly(multipartFile.getInputStream());
			
			// Excel 转换为导入商品数据模型
			List<CommodityVO> commodityVOList = new ExcelToDataModelConverter().convert(excel);
			
			//批量发布商品导入分类判断
			Iterator<CommodityVO> it = commodityVOList.iterator();
			int matchCount = 0;
			while(it.hasNext()){
				CommodityVO commodityVO = it.next();
				List<com.yougou.pc.model.brand.Brand> brandList = new ArrayList<com.yougou.pc.model.brand.Brand>();
				String categoryId = commodityBaseApiService.getCategoryByNo(commodityVO.getCatNo()).getId();
				brandList = commodityBaseApiService.getBrandListByCategoryId(categoryId,(short) 1);
				MerchantImportModel importModel = commodityMerchantApiService.getImportModelByCategory(categoryId, merchantCode);
				for(com.yougou.pc.model.brand.Brand MerchantBrand:importModel.getBrandList()){
					com.yougou.pc.model.brand.Brand hasBrand = (com.yougou.pc.model.brand.Brand)CollectionUtils.find(brandList,  new BeanPropertyEqualsPredicate("brandNo", MerchantBrand.getBrandNo()));
					if(hasBrand!=null){
						matchCount++;
					}
				}
				if(matchCount == 0){
					throw new IllegalStateException("您所上传的商品分类文件未授权，请重新上传。");
				}
				
			}
			
			// 按数据模型项依次导入商品
			// 创建 XLS 错误样式
			XSSFCellStyle errorStyle = excel.createCellStyle();
			errorStyle.setFillForegroundColor(new XSSFColor(new Color(255, 130, 105)));
			errorStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			// 默认样式
			XSSFCellStyle defaultCellStyle = excel.createCellStyle();
			
			XSSFSheet writeSheet = excel.getSheet(ExcelToDataModelConverter.WRITE_SHEET_NAME);
			XSSFDrawing drawing = writeSheet.createDrawingPatriarch();
			
			String brandNo="";
			String catStructname="";
			String sensitiveWord = null;
			List<Map<String,String>> sensitiveList = new ArrayList<Map<String,String>>();
			for (int i = 1, j = writeSheet.getLastRowNum(), k, n = i; n <= j; i = k) {
				boolean removing = false; 
				XSSFRow row = writeSheet.getRow(i);
				XSSFCell cell = row.getCell(ExcelToDataModelConverter.ERROR_COMMENT_COLUMN_INDEX);
				String supplierCode = ExcelToDataModelConverter.getCellValue(row, ExcelToDataModelConverter.IDENTITY_COLUMN_INDEX);
				// 定位标识列结束行索引
				for (k = i + 1, n++; k <= writeSheet.getLastRowNum(); k++, n++) {
					String another = ExcelToDataModelConverter.getCellValue(writeSheet, k, ExcelToDataModelConverter.IDENTITY_COLUMN_INDEX);
					if (StringUtils.isNotBlank(another) && !another.equals(supplierCode)) {
						break;
					}
				}
				
				try {
					// 导入商品
					CommodityVO commodityVO = (CommodityVO) CollectionUtils.find(commodityVOList, new BeanPropertyEqualsPredicate("supplierCode", supplierCode));
					// 没有查到款色编码对应的商品
					if (null == commodityVO) { fail++; continue; }
					
					commodityVO.setUpdatePerson(merchantName);
					commodityVO.setMerchantCode(merchantCode);
					// commodityVO.setOrderDistributionSide(orderDistributionSide);
					// 返回 JSON 数据格工为：{"success|failure":{"商家货品条码1":"优购货品编码1","商家货品条码N":"优购货品编码N"}}
					List<ProductVO> productVOList=commodityVO.getProductMsgList();
					boolean repeat=false;
					String thirdPartyCode="";
					for(ProductVO vo:productVOList){
						thirdPartyCode=vo.getInsideCode();
						repeat=productNoSet.contains(thirdPartyCode);
						if(!repeat){
							productNoSet.add(thirdPartyCode);
						}else{
							break;
						}
					}
					MerchantImportInfo returnVo=null;
					if(repeat){
						returnVo=new MerchantImportInfo();
						returnVo.setResult("ERROR");
						List<String> errorList=new ArrayList<String>();
						errorList.add("errorField:thirdPartyCode,errorInfo:与已经存在货品条码："+thirdPartyCode+"重复");
						returnVo.setErrorList(errorList);
					}else{
						commodityVO.setCommodityName(StringUtils.chomp(commodityVO.getCommodityName().replace("\"", "")
								.replace("\n", "")
								.replace("\\", "")
								.replaceAll("　", "")//中文空格
								.replaceAll("\\s+", " ")//中间多个英文空格，保留一个
								.trim()));
						returnVo = commodityMerchantApiService.insertCommodityMerchant(commodityVO);
					}

					brandNo=commodityVO.getBrandNo();
					catStructname=commodityVO.getCatStructname();
					// String returnValue = commodityMerchantApiService.importCommodityMerchant(commodityVO);
					LOGGER.info("Imported commodity:{} with merchant: {}, server return data : {} .", new Object[]{supplierCode, merchantCode, returnVo.getResult()});
					if (returnVo.getResult().toUpperCase().indexOf("SUCCESS") == -1) {
						LOGGER.info("Imported commodity : {} | errordata : {}.", new Object[]{supplierCode, returnVo.getErrorList()});
						List<ExcelErrorVo> errorList = this.transformCommodityError(returnVo.getErrorList(), i);
						if (CollectionUtils.isNotEmpty(errorList)) {
							for (ExcelErrorVo errorVo : errorList) {
								Cell _cell = row.getCell(errorVo.getColumn());
								_cell.setCellStyle(errorStyle);
								_cell.removeCellComment();
								_cell.setCellComment(ExcelToDataModelConverter.createComment(writeSheet.createDrawingPatriarch(), 3, 3, 6, 6, errorVo.getErrMsg()));
								LOGGER.error("批量发布商品在第{}行，在第{}列发生错误信息{}（行列从0开始，是上传的excel中的行列信息）.",new Object[]{errorVo.getRow(),errorVo.getColumn(),errorVo.getErrMsg()});
							}
							fail++;
							continue;
						} else {
							throw new DubboServiceException(DubboProvider.COMMODITY, "导入错误：" + returnVo.getErrorList());								
						}
					}
					
					//TODO
					//资料提交成功之后记录敏感词的日志
					sensitiveWord = commodityComponent.checkSensitiveWord(null,commodityVO.getCommodityName()+";"+
															commodityVO.getSellingPoint()+";"+commodityVO.getProdDesc());
					if(StringUtils.isNotBlank(sensitiveWord)){
						Map<String,String> sensitiveMap = new HashMap<String,String>();
						sensitiveMap.put("supplierCode", commodityVO.getSupplierCode());
						sensitiveMap.put("sensiveWord", sensitiveWord);
						sensitiveList.add(sensitiveMap);
					}
					commodityVO.setCommodityNo(returnVo.getCommodityNo());
					if(!commodityExtendService.insertCommodityExtendAndLog(
							CommodityUtilNew.buildCommoditySubmitNewVo(commodityVO),
							operator,"批量发布",sensitiveWord,true)){
						LOGGER.error("商家{}插入商品{}扩展表数据与日志报错！",new Object[]{merchantCode,returnVo.getCommodityNo()});
					}
					
					try {
						//redis缓存[通过款色编码存储分类]
						this.redisTemplate.opsForHash().put(CacheConstant.C_COMMODITY_CATEGORY_KEY, commodityVO.getMerchantCode() + "_" + commodityVO.getSupplierCode(), commodityVO.getCatStructname());
						this.redisTemplate.expire(CacheConstant.C_COMMODITY_CATEGORY_KEY, 30, TimeUnit.MINUTES);// 设置属性时，重新设置超时时间续命
					} catch (Exception e) {
						LOGGER.error("设置分类缓存异常.", e);
					}
					LOGGER.info("Imported commodity : {} | data : {}. ", new Object[]{supplierCode, returnVo.getProductCodes()});
					// 更新成功导入的商品库存数量
					Map<String, String> productCodes =  returnVo.getProductCodes();
					for (int x = 0, y = commodityVO.getProductMsgList().size(); x < y; x++) {
						ProductVO productVO = commodityVO.getProductMsgList().get(x);
						//meijunfeng Modify at 20130909 #8634 【商家中心】商品模板去掉“供应商条码”productVO.getThirdPartyCode()  =>  productVO.getInsideCode()
						String productNo = productCodes.get(productVO.getInsideCode().trim());
						
						// 人工干预记录导入商品日志信息
						if (x == 0) {
							Commodity commodity = commodityMerchantApiService.getCommodityByNo(returnVo.getCommodityNo());
							if (commodity == null) {
								LOGGER.warn("Get imported commodity:{} with merchant :{} data delay, server return data is null", new Object[]{returnVo.getCommodityNo(), merchantCode});
							} else {
								StringBuilder logBuilder = new StringBuilder();
								logBuilder.append("Get imported commodity " + returnVo.getCommodityNo() + " with merchant '" + merchantCode + "' server return data is [ ");
								logBuilder.append("commodityNo:").append(returnVo.getCommodityNo());
								for (Product product : commodity.getProducts()) {
									logBuilder.append(" --> productNo:").append(product.getProductNo());
								}
								LOGGER.info("{}",logBuilder.append(" ]").toString());
							}
						}
							
						//库存数量默认为0
						Map<String, ?> temporaryMap = inventoryDomainService.updateInventoryForMerchant(productNo, warehouseCode, (productVO.getStock() == null ? 0 : productVO.getStock()), NumberUtils.INTEGER_ZERO);
						LOGGER.info("Updated inventory quantity {} with merchant '{}' at {}" ,
								new Object[]{ (productVO.getStock() == null ? 0 : productVO.getStock()),merchantCode,temporaryMap });
					}
					// 删除成功导入的商品行
					removing = true;
					for (int startIndex = i, endIndex = k; startIndex < endIndex; startIndex++, k--) {
						ExcelToDataModelConverter.removeRow(writeSheet, i, defaultCellStyle, errorStyle);
					}
					// 累计成功导入的商品数
					succeed++;
					continue;
				} catch (Throwable ex) {
					LOGGER.error(ExceptionUtils.getStackTrace(ex));
					String errorMessage;
					if (WPIBussinessException.class.isInstance(ex)) {
						errorMessage = "系统异常：商品库存导入失败，" + ((WPIBussinessException) ex).getReturnMessage();
					} else if (JSONException.class.isInstance(ex)) {
						errorMessage = "系统异常：商品库存导入错误";
					} else if (DubboServiceException.class.isInstance(ex)) {
						errorMessage = ex.getMessage();
					} else {
						errorMessage = "系统异常：商品资料导入错误";
					}
					cell.setCellStyle(errorStyle);
					cell.removeCellComment();
					cell.setCellComment(ExcelToDataModelConverter.createComment(drawing, i, 0, i + 4, 6, errorMessage));
					fail++;
				} finally {
					// 调整发生异常情况下索引数值
					if (removing && k > i) {
						k = i;
					}
				}
				fail++;
			}
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("succeed", succeed);
			jsonObject.accumulate("fail", fail);
			if(succeed>0){
				//给旗舰店插入数据
				try {
					fssYmcApiService.updateStoreIdWhenPublishCommodity(merchantCode,brandNo,catStructname);
				} catch (Exception e) {
					LOGGER.error("给旗舰店添加商品运行时异常(批量):",e);
				}
			}
			
			if (fail > 0) {
				// 设置默认选中第一条错误数据行
				ExcelToDataModelConverter.setErrorCellAsActive(writeSheet);
				// 将错误数据行写入Excel文件
				String uuid = UUID.randomUUID().toString();
				OutputStream os = null;
				try {
					os = new FileOutputStream(getErrorXlsPathname(uuid));
					excel.write(os);
				} finally {
					IOUtils.closeQuietly(os);
					jsonObject.accumulate("uuid", uuid);
				}
				//将文件缓存到redis
				InputStream is = new FileInputStream(getErrorXlsPathname(uuid));
				this.redisTemplate.opsForHash().put(CacheConstant.C_COMMODITY_IMPORT_ERROR_KEY, uuid, IOUtils.toByteArray(is));
			}
			jsonObject.accumulate("sensitiveResult", sensitiveList);
			return jsonObject.toString();
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			return MessageFormat.format("'{' error: \"{0}\" '}'", StringUtils.defaultIfEmpty(ex.getMessage(), "系统异常：商品资料导入异常"));
		}
	}
	
	/**
	 * 加载单个商品图片上传 页面
	 * @param replacement
	 * @param editFilename
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/pics/upload/singleReady")
	public ModelAndView readyUploadSingleCommodityPic(String commodityNo,
			HttpServletRequest request, ModelMap modelMap, ModelMap model)
			throws Exception {
		modelMap.addAttribute("commodityNo", commodityNo);
		Commodity commodity = commodityApi.getCommodityByNo(commodityNo);
		Map<String,String> lpicMap = new HashMap<String, String>();
		Map<String,String> bpicMap = new HashMap<String, String>();
		Map<String,String> picIdMap = new HashMap<String, String>();
		
		List<Picture> picList = commodity.getPictures();
		Collections.sort(picList, new Comparator<Picture>(){
			public int compare(Picture o1, Picture o2) {
				return o1.getPicName().compareTo(o2.getPicName());
			}	
		});
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		if(StringUtils.isBlank(merchantCode)){
			throw new IllegalStateException("商家编号不能为空！");
		}
		
		for(Picture pic:picList){
			String picUrl = settings.getCommodityPreviewDomain() + pic.getPicPath() + pic.getPicName();
			if(StringUtils.equals(pic.getPicType(), "ms")){
				lpicMap.put(pic.getPicName(), picUrl);
			}
			if(StringUtils.equals(pic.getPicType(), "b")){
				bpicMap.put(pic.getPicName(), picUrl);
				picIdMap.put(pic.getPicName(), String.valueOf(pic.getId()));
			}
		}
		int latestPicNumbers = merchantCommodityService.getLatestPicNumbers(
				merchantCode, commodity.getSupplierCode(),commodity.getBrandNo());
		modelMap.addAttribute("lpicMap", lpicMap);
		modelMap.addAttribute("bpicMap", bpicMap);
		modelMap.addAttribute("picIdMap", picIdMap);
		modelMap.addAttribute("curDate", new Date());
		modelMap.addAttribute("commodity", commodity);
		modelMap.addAttribute("numbers", latestPicNumbers);
		commodityComponent.goAddOrUpdateCommodityCommon(null, model, request);
		// modelMap.addAttribute("catNames", getCateName(model, request));
		model.addAttribute("commodityPreviewDomain",
				settings.getCommodityPreviewDomain());
		return new ModelAndView("/manage_unless/commodity/commodity_pics_upload", modelMap);
	}
	
	
	/**
	 * 删除商品图片
	 * 
	 * @param ids
	 * @return String
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/pics/delete")
	public String deleteCommodityPicTile(String ids) throws Exception {
		if(merchantCommodityService.deleteMerchantCommodityPicsWithinImage(StringUtils.split(ids, ","))){
			return "success";
		}else{
			return "fail";
		}
	}
	
	/**
	 * 删除商品图片,以外部图片ID为条件删除商家商品图片信息(不删除FTP图片)
	 * 
	 * @param ids
	 * @return String
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/pics/singleDelete")
	public String deleteMerchantCommodityPicsByOuterPicId(Long picOutId, String innerPicName,String commodityNo, HttpServletRequest request) throws Exception {
//		Map<String, Object> unionUser = SessionUtil.getUnionUser(request);
//		if (unionUser == null) {
//			return StringUtils.defaultIfEmpty("请先登录！", "null");
//		}
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();
		if(StringUtils.isBlank(merchantCode)){
			return StringUtils.defaultIfEmpty("请先登录！商家编号不能为空！", "null");
		}
		merchantCommodityService.deleteMerchantCommodityPicsAndCommodityPics(picOutId, innerPicName, commodityNo, merchantCode);
		return "success";
	}
	
	/**
	 * 保存单品图片(批量导入商品页的入口)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/saveCommodityPicture")
	public String saveCommodityPicture(String commodityNo, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LOGGER.info("save commodity picture : {}", commodityNo);
		
		Commodity commodity = commodityApi.getCommodityByNo(commodityNo);
		List<ErrorVo> errorList = new ArrayList<ErrorVo>();
		CommoditySubmitVo vo = new CommoditySubmitVo();
		vo.setSettings(settings);
		vo.setCommodityNo(commodityNo);
		//新增或修改商品信息，初始化用户信息
		CommodityUtil.initMerchantUserInfoForAddOrUpdate(request, vo);
		// 校验图片
		vo.validateImageFileForAddNew(errorList, merchantCommodityService.getCommodityCatNamesByCatStructName(commodity.getCatStructName()));
		
		
		return null;
	}
	
	/**
	 * 获取导入错误商品数据行文件名
	 * 
	 * @param uuid
	 * @return String
	 */
	private String getErrorXlsPathname(String uuid) {
		return new StringBuilder().append(System.getProperty("java.io.tmpdir")).append(File.separator).append(uuid).append(".xlsx").toString();
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
	 * 解析导入商品错误列转换
	 * 
	 * @param errors
	 * @return List<ExcelErrorVo>
	 */
	private List<ExcelErrorVo> transformCommodityError(List<String> errors, int row) {
		List<ExcelErrorVo> errorList = new ArrayList<ExcelErrorVo>();
		if (CollectionUtils.isNotEmpty(errors)) {
			for (String error : errors) {
				String errorField = error.substring(error.indexOf(ERRORFIELD) + 11, error.indexOf(","));
				String errorInfo = error.substring(error.indexOf(ERRORINFO) + 10);
				if ("commodityName".equals(errorField)) {
					errorList.add(new ExcelErrorVo(errorField, errorInfo, row, ExcelToDataModelConverter.COMMODITYNAME_COLUMN_INDEX));
				} else if ("styleNo".equals(errorField)) {
					errorList.add(new ExcelErrorVo(errorField, errorInfo, row, ExcelToDataModelConverter.STYLENO_COLUMN_INDEX));
				} else if ("specName".equals(errorField)) {
					errorList.add(new ExcelErrorVo(errorField, errorInfo, row, ExcelToDataModelConverter.SPECNAME_COLUMN_INDEX));
				} else if ("supplierCode".equals(errorField)) {
					errorList.add(new ExcelErrorVo(errorField, errorInfo, row, ExcelToDataModelConverter.IDENTITY_COLUMN_INDEX));
				} else if ("salePrice".equals(errorField)) {
					errorList.add(new ExcelErrorVo(errorField, errorInfo, row, ExcelToDataModelConverter.SALEPRICE_COLUMN_INDEX));
				} else if ("publicPrice".equals(errorField)) {
					errorList.add(new ExcelErrorVo(errorField, errorInfo, row, ExcelToDataModelConverter.PUBLICPRICE_COLUMN_INDEX));
				} else if ("brandNo".equals(errorField)) {
					errorList.add(new ExcelErrorVo(errorField, errorInfo, row, ExcelToDataModelConverter.BRANDNAME_COLUMN_INDEX));
				} else if ("catNo".equals(errorField) || "size".equals(errorField)) {
					errorList.add(new ExcelErrorVo(errorField, errorInfo, row, 0));
				} else {
					errorList.add(new ExcelErrorVo(errorField, errorInfo, row, 0));
				}
			}
		}
		return errorList;
	}
	
	final static String ERRORFIELD = "errorField";
	
	final static String ERRORINFO = "errorInfo";
}
