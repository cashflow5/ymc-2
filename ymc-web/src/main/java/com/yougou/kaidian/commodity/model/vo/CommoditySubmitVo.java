package com.yougou.kaidian.commodity.model.vo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import com.yougou.kaidian.commodity.beans.BeanPropertyEqualsPredicate;
import com.yougou.kaidian.commodity.constant.CommodityConstant;
import com.yougou.kaidian.commodity.util.CommodityUtil;
import com.yougou.kaidian.common.commodity.util.CommodityPicIndexer;
import com.yougou.kaidian.common.util.CommonUtil;
import com.yougou.kaidian.framework.settings.CommoditySettings;
import com.yougou.pc.model.prop.PropValue;

/**
 * 新增或修改商品 提交时 用的vo
 * @author huang.wq
 * 2012-11-16
 */
public class CommoditySubmitVo {

	private static final Logger log = LoggerFactory.getLogger(CommoditySubmitVo.class);
	
	/**商品发布改版增加的属性 begin**/
	/**三级分类 id*/
	private String catId;
	
	/**三级分类 no*/
	private String catNo;
	
	/**三级分类 名称*/
	private String catName;
	
	/**三级分类 结构*/
	private String catStructName; 
	
	/**按尺码发布商品 null 为否， 1 为是*/
	private String pubBySize;
	 
	public String getPubBySize() {
		return pubBySize;
	}

	public void setPubBySize(String pubBySize) {
		this.pubBySize = pubBySize;
	}

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public String getCatNo() {
		return catNo;
	}

	public void setCatNo(String catNo) {
		this.catNo = catNo;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getCatStructName() {
		return catStructName;
	}

	public void setCatStructName(String catStructName) {
		this.catStructName = catStructName;
	}
	  
	  
	/**
	 * 商品id
	 */
	private String commodityId;
	/**
	 * 商品编号
	 */
	private String commodityNo;
	/**
	 * 一级分类
	 */
	private String rootCattegory;
	/**
	 * 一级分类名称
	 */
	private String rootCatName;
	/**
	 * 二级分类
	 */
	private String secondCategory;
	/**
	 * 二级分类名称
	 */
	private String secondCatName;
	/**
	 * 三级分类
	 */
	private String threeCategory;
	/**
	 * 分类结构名称
	 */
	private String structName;
	/**
	 * 品牌编号
	 */
	private String brandNo;
	/**
	 * 品牌名称
	 */
	private String brandName;
	/**
	 * 品牌Id
	 */
	private String brandId;
	/**
	 * 商品名称
	 */
	private String commodityName;
	/**
	 * 商品款号
	 */
	private String styleNo;
	/**
	 * 商家款色编码
	 */
	private String supplierCode;
	/**
	 * 优购价string
	 */
	private String salePriceStr;
	/**
	 * 优购价
	 */
	private Double salePrice;
	/**
	 * 市场价string
	 */
	private String publicPriceStr;
	/**
	 * 市场价
	 */
	private Double publicPrice;
	/**
	 * 重量string
	 */
	private String[] weightStr;
	/**
	 * 重量
	 */
	private Double[] weight;
	/**
	 * 年份
	 */
	private String years;
	/**
	 * 配送方
	 */
	private String orderDistributionSide;
	/**
	 * 属性编号
	 */
	private String[] propItemNo;
	/**
	 * 属性名称
	 */
	private String[] propItemName;
	/**
	 * 属性值编号
	 */
	private String[] propValueNo;
	/**
	 * 属性值名称
	 */
	private String[] propValueName;
	/**
	 * 属性id信息json 数组
	 */
	private String propIdInfo;
	/**
	 * 属性id list
	 */
	private List<Map<String, String>> propIdList;
	/**
	 * 颜色编号
	 */
	private String specNo;
	/**
	 * 颜色
	 */
	private String specName;
	/**
	 * 尺码数组
	 */
	private String sizeNo[];
	/**
	 * 尺码名称数组
	 */
	private String sizeName[];
	/**
	 * 库存信息
	 */
	private String stock[];
	/**
	 * 库存数量(int)
	 */
	private Integer stockInt[];
	/**
	 * 货品条码
	 */
	private String thirdPartyCode[];
	/**
	 * 货品id信息
	 */
	private String prodIdInfo;
	/**
	 * 货品id信息list
	 */
	private List<Map<String, String>> prodIdList;
	/**
	 * 商品描述
	 */
	private String prodDesc;
	
	//-----------------------------------------------
	
	private String[] imgFileId; 
	
	/**
	 * 上传文件Map
	 */
	private Map<String, MultipartFile> fileMap;
	/**
	 * 图片文件map
	 */
	private Map<String, MultipartFile> imgFileMap;
	/**
	 * 商家名字
	 */
	private String supplier;
	/**
	 * 商家编号
	 */
	private String merchantCode;
	/**
	 * 是否入优购仓
	 */
	private String isInputYougouWarehouse;
	/**
	 * 是否为预览
	 */
	private String isPreview;
	/**
	 * 是否为保存并提交审核
	 */
	private String isSaveSubmit;
	/**
	 * 商品描述图片url数组
	 */
	private String[] descImgUrls;
	/**
	 * 商品描述中需要下载的URL
	 */
	private String[] downloadImgUrls;
	
	/**
	 * 商品描述inputstream数组
	 */
	private InputStream[] descImgInputStreams;
	
	/**
	 * 图片路径
	 */
	private String picPath;
	/**
	 * 商品基础配置
	 */
	private CommoditySettings settings;
	/**
	 * 与当前商家对应的仓库编码
	 */
	private String wareHouseCode;
	
	/**
	 * 商品卖点
	 */
	private String sellingPoint;

	final static String VALIDATE_ADD_PREFIX = "validateAddCommodityForm#-> ";
	final static String VALIDATE_ADD_UPDATE_PREFIX = "validateAddOrUpdateCommodityCommon#-> "; 
	
	
	//added by ln at 2015-4-10
	//按尺码设置价格
	//优购价
	private String[] salePriceBySize;
	//市场价
	private String[] publicPriceBySize;
	
	
	/**
	 * <p>新增 表单验证方法 一次性返回错误结果集</p>
	 * 
	 * @param resultVo
	 * @return
	 * @throws Exception
	 */
	public List<ErrorVo> validateAddCommodityFormNew(List<ErrorVo> errorList, 
			String[] catNames,List<PropValue> propList) throws Exception {
		this.toNotNullFields();
		
		//brandNo is not null
		if (brandNo.length() == 0 || brandName.length() == 0 || brandId.length() == 0) {
			errorList.add(new ErrorVo("brandNo", "请选择品牌"));
		} /*else if (rootCattegory.length() == 0 || secondCategory.length() == 0 
				|| threeCategory.length() == 0 || structName.length() == 0) {
			errorList.add(new ErrorVo("category", "请选择三级分类"));
		}*/
		
		this.validateCommodityCommon(errorList,propList);
		
		//验证上传的图片
		this.validateImageFileForAddNew(errorList, catNames);
		
		//校验描述图
		//this.validateProDesc(errorList);
		
		return errorList;
	}
	
	private void validateCommodityCommon(List<ErrorVo> errorList, 
			List<PropValue> propList) throws IOException {
		if (commodityName.length() == 0) {
			errorList.add(new ErrorVo("commodityName", "商品名称不能为空"));
			log.warn("{} commodityName is not null",VALIDATE_ADD_UPDATE_PREFIX);
		} else if (commodityName.length() > 200) {
			errorList.add(new ErrorVo("commodityName", "商品名称长度不能超过200个字符"));
			log.warn("{} commodityName 长度不能超过200个字符",VALIDATE_ADD_UPDATE_PREFIX);
		}
		
		if (styleNo.length() == 0) {
			errorList.add(new ErrorVo("styleNo", "商品款号不能为空"));
			log.warn("{} styleNo is not null",VALIDATE_ADD_UPDATE_PREFIX);
		} else if (styleNo.length() > 32) {
			errorList.add(new ErrorVo("styleNo", "商品款号长度不能超过32个字符"));
			log.warn("{} styleNo 长度不能超过32个字符",VALIDATE_ADD_UPDATE_PREFIX);
		}
		
		//如果按尺码设置价格，对价格进行验证
		if(propList==null || propList.size()==0){
			if (supplierCode.length() == 0) {
				errorList.add(new ErrorVo("supplierCode", "商品款色编码不能为空"));
				log.warn("{} styleNo is not null",VALIDATE_ADD_UPDATE_PREFIX);
			} else if (supplierCode.length() > 36) {
				errorList.add(new ErrorVo("supplierCode", "商品款色编码长度不能超过36个字符"));
				log.warn("{} styleNo 长度不能超过36个字符",VALIDATE_ADD_UPDATE_PREFIX);
			}
		}
		
		//如果不是按尺码设置价格，就按原方案执行
		//按尺码设置价格跳过此验证
		if(propList==null || propList.size()==0){
			if (salePriceStr.length() == 0) {
				errorList.add(new ErrorVo("salePrice", "请输入优购价"));
				log.warn("{} salePrice is not null",VALIDATE_ADD_UPDATE_PREFIX);
			} else if (salePriceStr.length() > 10) {
				errorList.add(new ErrorVo("salePrice", "优购价长度不能超过10个字符"));
				log.warn("{} salePrice 长度不能超过10个字符",VALIDATE_ADD_UPDATE_PREFIX);
			}
			if (publicPriceStr.length() == 0) {
				errorList.add(new ErrorVo("publicPrice", "请输入市场价"));
				log.warn("{} salePrice is not null",VALIDATE_ADD_UPDATE_PREFIX);
			} else if (publicPriceStr.length() > 10) {
				errorList.add(new ErrorVo("publicPrice", "市场价长度不能超过10个字符"));
				log.warn("{} salePrice is not null",VALIDATE_ADD_UPDATE_PREFIX);
			}
			
			//验证优购价
			try {
				salePrice = Double.parseDouble(salePriceStr);
			} catch (NumberFormatException e) {
				errorList.add(new ErrorVo("salePrice", "优购价格必须为数字"));
				log.warn("{} salePrice is not double",VALIDATE_ADD_UPDATE_PREFIX);
			}
			if (salePrice <= 0.0) {
				errorList.add(new ErrorVo("salePrice", "优购价格必须大于0"));
				log.warn("{} salePrice <= 0.0",VALIDATE_ADD_UPDATE_PREFIX);
			}
			//验证市场价
			try {
				publicPrice = Double.parseDouble(publicPriceStr);
			} catch (NumberFormatException e) {
				errorList.add(new ErrorVo("publicPrice", "市场价格必须为数字"));
				log.warn("{} publicPrice is not double",VALIDATE_ADD_UPDATE_PREFIX);
			}
			if (publicPrice <= 0.0) {
				errorList.add(new ErrorVo("publicPrice", "市场价格必须大于0"));
				log.warn("{} publicPrice <= 0.0",VALIDATE_ADD_UPDATE_PREFIX);
			}
		}
		
		//years 页面有默认选框，这里暂不做处理
		
		if (specName.length() == 0) {
			errorList.add(new ErrorVo("specName", "请输入商品颜色"));
			log.warn("{} specName is not null", VALIDATE_ADD_UPDATE_PREFIX);
		}
		
		if (prodDesc.length() == 0) {
			errorList.add(new ErrorVo("prodDesc", "请输入商品描述"));
			log.warn("{} prodDesc is not null",VALIDATE_ADD_UPDATE_PREFIX);
		} else if (prodDesc.length() > 30000) {
			errorList.add(new ErrorVo("prodDesc", "商品描述长度不能超过30000个字符"));
			log.warn("{} prodDesc 长度不能超过30000个字符",VALIDATE_ADD_UPDATE_PREFIX);
		}
		
		//检测尺码
		if (sizeNo == null ||sizeNo.length == 0 || sizeName == null || 
				sizeNo.length != sizeName.length ) {
			errorList.add(new ErrorVo("sizeNo", "请选择尺码"));
			log.warn("{} sizeNo array is empty",VALIDATE_ADD_UPDATE_PREFIX);
		}
		
		//如果按尺码设置价格，对价格进行验证
		if(propList!=null && propList.size()>0){
			if(salePriceBySize == null || salePriceBySize.length==0
					|| salePriceBySize.length!=sizeNo.length){
				errorList.add(new ErrorVo("salePriceBySize", "请选择优购价"));
				log.warn("{} salePriceBySize array is empty",VALIDATE_ADD_UPDATE_PREFIX);
			}
			
			if(publicPriceBySize ==null || publicPriceBySize.length == 0 
					|| publicPriceBySize.length!=salePriceBySize.length){
				errorList.add(new ErrorVo("salePriceBySize", "请选择市场价"));
				log.warn("{} publicPriceBySize array is empty",VALIDATE_ADD_UPDATE_PREFIX);
			}
		}
		
		StringBuilder sb = new StringBuilder(VALIDATE_ADD_UPDATE_PREFIX);
		sb.append("|commodityId: ").append(commodityId);
		sb.append("|commodityNo: ").append(commodityNo);
		sb.append("|rootCattegory: ").append(rootCattegory);
		sb.append("|rootCatName: ").append(rootCatName);
		sb.append("|secondCategory: ").append(secondCategory);
		sb.append("|secondCatName: ").append(secondCatName);
		sb.append("|threeCategory: ").append(threeCategory);
		sb.append("|structName: ").append(structName);
		sb.append("|brandNo: ").append(brandNo);
		sb.append("|brandName: ").append(brandName);
		sb.append("|brandId: ").append(brandId);
		sb.append("|commodityName: ").append(commodityName);
		sb.append("|styleNo: ").append(styleNo);
		sb.append("|supplierCode: ").append(supplierCode);
		sb.append("|salePrice: ").append(salePriceStr);
		sb.append("|publicPrice: ").append(publicPriceStr);
		sb.append("|years: ").append(years);
		sb.append("|orderDistributionSide: ").append(orderDistributionSide);
		
		if (propItemNo != null && propItemNo.length > 0) {
			if (propItemName == null || propItemName.length == 0 || propValueNo == null 
					|| propValueNo.length == 0 || propValueName == null || propValueName.length == 0 
					|| propItemNo.length != propItemName.length || propItemNo.length != propValueNo.length 
					|| propItemNo.length != propValueName.length) 
			{
				sb.append("|properties length not match");
				propItemNo = null;
				propItemName = null;
				propValueNo = null;
				propValueName = null;
			} else {
				for (int i = 0, len = propItemNo.length; i < len; i++) {
					sb.append("|propItemNo: " + propItemNo[i] +  
							" ,propItemName: " + propItemName[i] +
							" ,propValueNo: " + propValueNo[i] +
							" ,propValueName: " + propValueName[i]);
				}
			}
		}
		
		sb.append("|specNo: ").append(specNo);
		sb.append("|specName: ").append(specName);
		sb.append("|isPreview: ").append(isPreview);
		sb.append("|isSaveSubmit: ").append(isSaveSubmit);
		
		stockInt = new Integer[sizeNo.length];
		weight = new Double[sizeNo.length];
		for (int i = 0, len = sizeNo.length; i < len; i++) {
			String sizeNoTmp = CommonUtil.getTrimString(sizeNo[i]);
			String sizeNameTmp = CommonUtil.getTrimString(sizeName[i]);
			String stockTmp = CommonUtil.getTrimString(stock[i]);			
			String thirdPartyCodeTmp = CommonUtil.getTrimString(thirdPartyCode[i]);
			String weightStrTmp = CommonUtil.getTrimString(weightStr[i]);
			String salePriceBySizeTmp = CommonUtil.getTrimString(salePriceBySize[i]);
			String publicPriceBySizeTmp = CommonUtil.getTrimString(publicPriceBySize[i]);
			sizeNo[i] = sizeNoTmp;
			sizeName[i] = sizeNameTmp;
			stock[i] = stockTmp;
			thirdPartyCode[i] = thirdPartyCodeTmp;
			salePriceBySize[i] = salePriceBySizeTmp;
			publicPriceBySize[i] = publicPriceBySizeTmp;
			sb.append("|sizeNo:" + i + " - " + sizeNoTmp + " - " + sizeNameTmp +
					" - " + stockTmp + " - " + thirdPartyCodeTmp + " - " + weightStrTmp +
					" - " + salePriceBySizeTmp + " - " + publicPriceBySizeTmp);
			
			int stockIntNum = 0;
			//如果不入优购仓
			if ((CommodityConstant.SUPPLIER_IS_INPUT_YOUGOU_WAREHOUSE_NOT_IN + "").equals(isInputYougouWarehouse)) {
				try {
					stockIntNum = Integer.parseInt(stockTmp.length() == 0 ? "0" : stockTmp);
				} catch (NumberFormatException e) {
					errorList.add(new ErrorVo(sizeNoTmp + "_stock", "第 " + (i + 1) + " 行的库存数量请输入整数数字"));
				}
				if (stockIntNum < 0) {
					errorList.add(new ErrorVo(sizeNoTmp + "_stock", "第 " + (i + 1) + " 行的库存数量请输入大于等于0的数字"));
				}
				if (stockIntNum > 100000) {
					errorList.add(new ErrorVo(sizeNoTmp + "_stock", "第 " + (i + 1) + " 行的库存数量超过了10万"));
				}
			}
			stockInt[i] = stockIntNum;
			
			//验证货品条码
			if (thirdPartyCodeTmp.length() == 0) {
				errorList.add(new ErrorVo(sizeNoTmp + "_thirdPartyCode", "请输入第 " + (i + 1) + " 行的货品条码"));
			}
			if (thirdPartyCodeTmp.length() > 32) {
				errorList.add(new ErrorVo(sizeNoTmp + "_thirdPartyCode", "第 " + (i + 1) + " 行的货品条码长度超过了32"));
			}
			
			//验证重量
			Double weightNum = null;
			if (weightStrTmp.length() != 0) {
				try {
					weightNum = Integer.parseInt(weightStrTmp) + 0.0;
				} catch (NumberFormatException e) {
					errorList.add(new ErrorVo(sizeNoTmp + "_weightStr", "第 " + (i + 1) + " 行的重量请输入整数数字"));
				}
				if (weightNum <= 0) {
					errorList.add(new ErrorVo(sizeNoTmp + "_weightStr", "第 " + (i + 1) + " 行的重量请输入大于0的数字"));
				}
			}
			weight[i] = weightNum;
		}
		
		sb.append("|imgFileId: ").append(ArrayUtils.toString(imgFileId));
		sb.append("|prodDesc length: ").append(prodDesc.length());
		log.info("{}",sb.toString());
		log.debug("{} prodDesc: {}" , VALIDATE_ADD_UPDATE_PREFIX, prodDesc);
	}
	
	/**
	 * 验证修改商品表单
	 * @param resultVo 表单提交结果vo
	 * @return 验证通过则返回true
	 * @throws IOException
	 */
	public void validateUpdateCommodityForm(List<ErrorVo> errorList,
			com.yougou.pc.model.commodityinfo.Commodity commodity, 
			String[] catNames,List<PropValue> propList) throws IOException {
		String prefix = "validateUpdateCommodityForm#-> ";
		
		toNotNullFields();
		
		this.validateCommodityCommon(errorList,propList);
		
		log.info("{} propIdInfo: {}", prefix, propIdInfo);
		//把属性id信息转换成list
		propIdList = CommodityUtil.jsonArray2List(propIdInfo, 
				new String[] {"propItemNo", "propItemId"});
		log.info("{} propIdList.size: {}" , prefix, propIdList.size());
		
		log.info("{} prodIdInfo: {}", prefix, prodIdInfo);
		//货品id信息转换成list
		prodIdList = CommodityUtil.jsonArray2List(prodIdInfo, 
				new String[] {"sizeNo", "productNo", "productId"});
		log.info("{} prodIdList.size: {}",prefix, prodIdList.size());
		
		//验证上传的图片
		this.validateImageFileForUpdate(errorList, commodity, catNames);
		
		//校验商品描述图片的有效性
		//this.validateProDesc(errorList);
	}
	
	//-----------------------------------------------------------
	
	/**
	 * 使所有域转换成非null，别且去掉了空格
	 */
	private void toNotNullFields() {
		commodityId = CommonUtil.getTrimString(commodityId);
		commodityNo = CommonUtil.getTrimString(commodityNo);
		rootCattegory = CommonUtil.getTrimString(rootCattegory);
		rootCatName = CommonUtil.getTrimString(rootCatName);
		secondCategory = CommonUtil.getTrimString(secondCategory);
		secondCatName = CommodityUtil.getCatNameByThreeCategory(secondCategory);
		threeCategory = CommonUtil.getTrimString(threeCategory);
		//通过三级分类获取结构名称
		structName = CommonUtil.getTrimString(
				CommodityUtil.getStructNameByCats(rootCattegory, secondCategory, threeCategory));
		brandNo = CommonUtil.getTrimString(brandNo);
		brandName = CommonUtil.getTrimString(brandName);
		brandId = CommonUtil.getTrimString(brandId);
		commodityName = CommonUtil.getTrimString(commodityName);
		styleNo = CommonUtil.getTrimString(styleNo);
		supplierCode = CommonUtil.getTrimString(supplierCode);
		salePriceStr = CommonUtil.getTrimString(salePriceStr);
		publicPriceStr = CommonUtil.getTrimString(publicPriceStr);
		years = CommonUtil.getTrimString(years);
		orderDistributionSide = CommonUtil.getTrimString(orderDistributionSide);
		propIdInfo = CommonUtil.getTrimString(propIdInfo);
		specNo = CommonUtil.getTrimString(specNo);
		specName = CommonUtil.getTrimString(specName);
		prodIdInfo = CommonUtil.getTrimString(prodIdInfo);
		prodDesc = CommonUtil.getTrimString(prodDesc);
		isPreview = CommonUtil.getTrimString(isPreview);
		isSaveSubmit = CommonUtil.getTrimString(isSaveSubmit);
	}
	
	
	public void validateImageFileForAddNew(List<ErrorVo> errorList, String[] catNames) throws IOException {
		List<String> _imgFileId = new ArrayList<String>();
		if (ArrayUtils.isNotEmpty(imgFileId)) {
			int num = 1;
			for (String fileId : imgFileId) {
				if (!"-1".equals(fileId)) _imgFileId.add(fileId);
				if(fileId.length()>500){
					errorList.add(new ErrorVo("commodityImage", "第"+num+"张商品图片异常，请重新上传"));
				}
				num++;
			}
		}
		if (CollectionUtils.isEmpty(_imgFileId)) {
			errorList.add(new ErrorVo("commodityImage", "商品图片不能为空"));
			return;
		}
		
		// 按分类索引商品图片张数(Modifier by: yang.mq)
		int numbers = CommodityPicIndexer.indexNumbers(CommoditySettings.COMMODITY_MAGNIFIER_IMAGE_DEFAULT_NUMBERS,catNames);
		
		// 验证数量
		if (_imgFileId.size() < numbers) {
			errorList.add(new ErrorVo("commodityImage", "请上传" + numbers  + "张商品图片"));
			return;
		}
	}
	
	/**
	 * 验证上传的图片（修改商品）
	 * @param resultVo 表单提交结果vo
	 * @return 验证通过则返回true
	 */
	public void validateImageFileForUpdate(List<ErrorVo> errorList,
			com.yougou.pc.model.commodityinfo.Commodity commodity, String[] catNames) throws IOException {
		List<String> _imgFileId = new ArrayList<String>();
		if (ArrayUtils.isNotEmpty(imgFileId)) {
			int num = 1;
			for (String fileId : imgFileId) {
				if (!"-1".equals(fileId) && !"0".equals(fileId)) _imgFileId.add(fileId);
				if(fileId.length()>500){
					errorList.add(new ErrorVo("commodityImage", "第"+num+"张商品图片异常，请重新上传"));
				}
				num++;
			}
		}
		// 已上传的放大镜图
		int uploadedCount = CollectionUtils.countMatches(commodity.getPictures(), new BeanPropertyEqualsPredicate("picType", "l"));
		// 按分类索引商品图片张数(Modifier by: yang.mq)
		int numbers = CommodityPicIndexer.indexNumbers(CommoditySettings.COMMODITY_MAGNIFIER_IMAGE_DEFAULT_NUMBERS,catNames);
		// 验证数量
		if (uploadedCount + _imgFileId.size() < numbers) {
			errorList.add(new ErrorVo("commodityImage", "请上传" + numbers  + "张商品图片"));
			return;
		}
	}
	
	//------------	Getter and Setter--------------

	public String getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}

	public String getCommodityNo() {
		return commodityNo;
	}

	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}

	public String getRootCattegory() {
		return rootCattegory;
	}

	public void setRootCattegory(String rootCattegory) {
		this.rootCattegory = rootCattegory;
	}

	public String getRootCatName() {
		return rootCatName;
	}

	public void setRootCatName(String rootCatName) {
		this.rootCatName = rootCatName;
	}

	public String getSecondCategory() {
		return secondCategory;
	}

	public void setSecondCategory(String secondCategory) {
		this.secondCategory = secondCategory;
	}

	public String getSecondCatName() {
		return secondCatName;
	}

	public void setSecondCatName(String secondCatName) {
		this.secondCatName = secondCatName;
	}

	public String getThreeCategory() {
		return threeCategory;
	}

	public void setThreeCategory(String threeCategory) {
		this.threeCategory = threeCategory;
	}

	public String getStructName() {
		return structName;
	}

	public void setStructName(String structName) {
		this.structName = structName;
	}

	public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getStyleNo() {
		return styleNo;
	}

	public void setStyleNo(String styleNo) {
		this.styleNo = styleNo;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSalePriceStr() {
		return salePriceStr;
	}

	public void setSalePriceStr(String salePriceStr) {
		this.salePriceStr = salePriceStr;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public String getPublicPriceStr() {
		return publicPriceStr;
	}

	public void setPublicPriceStr(String publicPriceStr) {
		this.publicPriceStr = publicPriceStr;
	}

	public Double getPublicPrice() {
		return publicPrice;
	}

	public void setPublicPrice(Double publicPrice) {
		this.publicPrice = publicPrice;
	}

	public String[] getWeightStr() {
		return weightStr;
	}

	public void setWeightStr(String[] weightStr) {
		this.weightStr = weightStr;
	}

	public Double[] getWeight() {
		return weight;
	}

	public void setWeight(Double[] weight) {
		this.weight = weight;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public String getOrderDistributionSide() {
		return orderDistributionSide;
	}

	public void setOrderDistributionSide(String orderDistributionSide) {
		this.orderDistributionSide = orderDistributionSide;
	}

	public String[] getPropItemNo() {
		return propItemNo;
	}

	public void setPropItemNo(String[] propItemNo) {
		this.propItemNo = propItemNo;
	}

	public String[] getPropItemName() {
		return propItemName;
	}

	public void setPropItemName(String[] propItemName) {
		this.propItemName = propItemName;
	}

	public String[] getPropValueNo() {
		return propValueNo;
	}

	public void setPropValueNo(String[] propValueNo) {
		this.propValueNo = propValueNo;
	}

	public String[] getPropValueName() {
		return propValueName;
	}

	public void setPropValueName(String[] propValueName) {
		this.propValueName = propValueName;
	}

	public String getPropIdInfo() {
		return propIdInfo;
	}

	public void setPropIdInfo(String propIdInfo) {
		this.propIdInfo = propIdInfo;
	}

	public List<Map<String, String>> getPropIdList() {
		return propIdList;
	}

	public void setPropIdList(List<Map<String, String>> propIdList) {
		this.propIdList = propIdList;
	}

	public String getSpecNo() {
		return specNo;
	}

	public void setSpecNo(String specNo) {
		this.specNo = specNo;
	}

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public String[] getSizeNo() {
		return sizeNo;
	}

	public void setSizeNo(String[] sizeNo) {
		this.sizeNo = sizeNo;
	}

	public String[] getSizeName() {
		return sizeName;
	}

	public void setSizeName(String[] sizeName) {
		this.sizeName = sizeName;
	}

	public String[] getStock() {
		return stock;
	}

	public void setStock(String[] stock) {
		this.stock = stock;
	}

	public Integer[] getStockInt() {
		return stockInt;
	}

	public void setStockInt(Integer[] stockInt) {
		this.stockInt = stockInt;
	}

	public String[] getThirdPartyCode() {
		return thirdPartyCode;
	}

	public void setThirdPartyCode(String[] thirdPartyCode) {
		this.thirdPartyCode = thirdPartyCode;
	}

	public String getProdIdInfo() {
		return prodIdInfo;
	}

	public void setProdIdInfo(String prodIdInfo) {
		this.prodIdInfo = prodIdInfo;
	}

	public List<Map<String, String>> getProdIdList() {
		return prodIdList;
	}

	public void setProdIdList(List<Map<String, String>> prodIdList) {
		this.prodIdList = prodIdList;
	}

	public String getProdDesc() {
		return prodDesc;
	}

	public void setProdDesc(String prodDesc) {
		this.prodDesc = prodDesc;
	}

	public Map<String, MultipartFile> getFileMap() {
		return fileMap;
	}

	public void setFileMap(Map<String, MultipartFile> fileMap) {
		this.fileMap = fileMap;
	}

	public Map<String, MultipartFile> getImgFileMap() {
		return imgFileMap;
	}

	public void setImgFileMap(Map<String, MultipartFile> imgFileMap) {
		this.imgFileMap = imgFileMap;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getIsInputYougouWarehouse() {
		return isInputYougouWarehouse;
	}

	public void setIsInputYougouWarehouse(String isInputYougouWarehouse) {
		this.isInputYougouWarehouse = isInputYougouWarehouse;
	}

	public String getIsPreview() {
		return isPreview;
	}

	public void setIsPreview(String isPreview) {
		this.isPreview = isPreview;
	}

	public String getIsSaveSubmit() {
		return isSaveSubmit;
	}

	public void setIsSaveSubmit(String isSaveSubmit) {
		this.isSaveSubmit = isSaveSubmit;
	}

	public InputStream[] getDescImgInputStreams() {
		return descImgInputStreams;
	}

	public void setDescImgInputStreams(InputStream[] descImgInputStreams) {
		this.descImgInputStreams = descImgInputStreams;
	}
	
	public String[] getDescImgUrls() {
		return descImgUrls;
	}

	public void setDescImgUrls(String[] descImgUrls) {
		this.descImgUrls = descImgUrls;
	}
	
	public String[] getDownloadImgUrls() {
		return downloadImgUrls;
	}

	public void setDownloadImgUrls(String[] downloadImgUrls) {
		this.downloadImgUrls = downloadImgUrls;
	}
	
	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public CommoditySettings getSettings() {
		return settings;
	}

	public void setSettings(CommoditySettings settings) {
		this.settings = settings;
	}

	public String getWareHouseCode() {
		return wareHouseCode;
	}

	public void setWareHouseCode(String wareHouseCode) {
		this.wareHouseCode = wareHouseCode;
	}

	public String[] getImgFileId() {
		return imgFileId;
	}

	public void setImgFileId(String[] imgFileId) {
		this.imgFileId = imgFileId;
	}

	public String getSellingPoint() {
		return sellingPoint;
	}

	public void setSellingPoint(String sellingPoint) {
		this.sellingPoint = sellingPoint;
	}

	public String[] getSalePriceBySize() {
		return salePriceBySize;
	}

	public void setSalePriceBySize(String[] salePriceBySize) {
		this.salePriceBySize = salePriceBySize;
	}

	public String[] getPublicPriceBySize() {
		return publicPriceBySize;
	}

	public void setPublicPriceBySize(String[] publicPriceBySize) {
		this.publicPriceBySize = publicPriceBySize;
	}
}
