package com.yougou.kaidian.taobao.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yougou.fss.api.IFSSYmcApiService;
import com.yougou.kaidian.commodity.component.CommodityComponent;
import com.yougou.kaidian.commodity.constant.CommodityConstant;
import com.yougou.kaidian.commodity.service.ICommodityExtendService;
import com.yougou.kaidian.commodity.service.ICommodityService;
import com.yougou.kaidian.commodity.service.IMerchantCommodityService;
import com.yougou.kaidian.commodity.util.CommodityUtil;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.common.commodity.pojo.Brand;
import com.yougou.kaidian.common.commodity.pojo.CommodityExtend;
import com.yougou.kaidian.common.commodity.util.CommodityPicIndexer;
import com.yougou.kaidian.common.constant.CacheConstant;
import com.yougou.kaidian.common.util.StringUtil;
import com.yougou.kaidian.common.util.UUIDGenerator;
import com.yougou.kaidian.common.vo.CommodityImage;
import com.yougou.kaidian.common.vo.Image4SingleCommodityMessage;
import com.yougou.kaidian.framework.settings.CommoditySettings;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;
import com.yougou.kaidian.taobao.common.TaobaoImportUtils;
import com.yougou.kaidian.taobao.constant.TaobaoImportConstants;
import com.yougou.kaidian.taobao.dao.TaobaoBrandMapper;
import com.yougou.kaidian.taobao.dao.TaobaoItemCatMapper;
import com.yougou.kaidian.taobao.dao.TaobaoItemExtendMapper;
import com.yougou.kaidian.taobao.dao.TaobaoItemExtendPropMapper;
import com.yougou.kaidian.taobao.dao.TaobaoItemImgMapper;
import com.yougou.kaidian.taobao.dao.TaobaoItemMapper;
import com.yougou.kaidian.taobao.dao.TaobaoItemPropImgMapper;
import com.yougou.kaidian.taobao.dao.TaobaoItemPropMapper;
import com.yougou.kaidian.taobao.dao.TaobaoItemPropValueMapper;
import com.yougou.kaidian.taobao.dao.TaobaoItemSkuMapper;
import com.yougou.kaidian.taobao.dao.TaobaoYougouItemPropMapper;
import com.yougou.kaidian.taobao.enums.IsImportYougou;
import com.yougou.kaidian.taobao.enums.PropTypeValue;
import com.yougou.kaidian.taobao.exception.BusinessException;
import com.yougou.kaidian.taobao.model.TaobaoCommodityImportInfo;
import com.yougou.kaidian.taobao.model.TaobaoItem;
import com.yougou.kaidian.taobao.model.TaobaoItemExtend;
import com.yougou.kaidian.taobao.model.TaobaoItemExtendDto;
import com.yougou.kaidian.taobao.model.TaobaoItemExtendYougouPropValue;
import com.yougou.kaidian.taobao.model.TaobaoItemExtendYougouPropValueSize;
import com.yougou.kaidian.taobao.model.TaobaoItemImg;
import com.yougou.kaidian.taobao.model.TaobaoItemProp;
import com.yougou.kaidian.taobao.model.TaobaoItemPropValue;
import com.yougou.kaidian.taobao.model.TaobaoItemSku;
import com.yougou.kaidian.taobao.model.TaobaoYougouItemCat;
import com.yougou.kaidian.taobao.model.TaobaoYougouItemPropValue;
import com.yougou.kaidian.taobao.service.ITaobaoItemService;
import com.yougou.kaidian.taobao.vo.ErrorVo;
import com.yougou.merchant.api.supplier.service.IMerchantImageService;
import com.yougou.merchant.api.supplier.vo.ImageJmsVo;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.api.ICommodityMerchantApiService;
import com.yougou.pc.model.category.Category;
import com.yougou.pc.model.commodityinfo.Commodity;
import com.yougou.pc.model.commodityinfo.CommodityProp;
import com.yougou.pc.model.merchant.MerchantImportInfo;
import com.yougou.pc.model.product.Product;
import com.yougou.pc.model.prop.PropItem;
import com.yougou.pc.model.prop.PropValue;
import com.yougou.pc.model.sensitive.SensitiveCheckLog;
import com.yougou.pc.vo.commodity.CommodityPropVO;
import com.yougou.pc.vo.commodity.CommodityVO;
import com.yougou.pc.vo.commodity.ProductVO;
import com.yougou.tools.common.utils.DateUtil;

@Service
public class TaobaoItemServiceImpl implements ITaobaoItemService {

	private static final Logger logger = LoggerFactory.getLogger(TaobaoItemServiceImpl.class);
	@Resource
	private TaobaoBrandMapper taobaoBrandMapper;

	@Resource
	private TaobaoItemCatMapper taobaoItemCatMapper;

	@Resource
	private TaobaoItemPropMapper taobaoItemPropMapper;

	@Resource
	private TaobaoItemPropValueMapper taobaoItemPropValueMapper;

	@Resource
	private TaobaoItemImgMapper taobaoItemImgMapper;

	@Resource
	private TaobaoItemMapper taobaoItemMapper;

	@Resource
	private ICommodityBaseApiService commodityBaseApiService;

	@Resource
	private ICommodityService commodityService;

	@Resource
	private TaobaoYougouItemPropMapper taobaoYougouItemPropMapper;

	@Resource
	private TaobaoItemExtendPropMapper taobaoItemExtendPropMapper;
	@Resource
	private CommodityComponent commodityComponent;
	@Resource
	private IMerchantCommodityService merchantCommodityService;
	@Resource
	private ICommodityMerchantApiService commodityApi;

	@Resource
	private CommoditySettings settings;

	@Resource
	private TaobaoItemSkuMapper taobaoItemSkuMapper;

	@Resource
	private TaobaoItemExtendMapper taobaoItemExtendMapper;

	@Resource
	private TaobaoItemPropImgMapper taobaoItemPropImgMapper;

	@Resource
	private IMerchantImageService merchantImageService;

	@Resource
	@Qualifier(value = "jmsTemplate")
	private AmqpTemplate amqpTemplate;

	@Resource
	private IFSSYmcApiService fssYmcApiService;

	private Pattern imgPattern = Pattern.compile("([^(/)]*).jpg");

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Resource
	private ICommodityExtendService commodityExtendService;

	/** 工作表名称 **/
	public static final String WRITE_SHEET_NAME = "淘宝商品";

	public void delTaoBaoItemByCommodityNo(String merchantCode, String commodityNo) throws BusinessException {
		String extendId = taobaoItemExtendMapper.getTaobaoItemExtendIdByCommodtyNo(merchantCode, commodityNo);
		if (StringUtils.isNotBlank(extendId)) {
			this.deleteTaobaoItemForever(new String[] { extendId }, merchantCode);
		}
	}

	/**
	 * 将淘宝商品数据导入优购商品系统
	 */
	public List<TaobaoCommodityImportInfo> importTaobaoItemDataToYougou(String merchantCode, String extend_idArry[],
			String loginName, String isAudit) {
		List<TaobaoCommodityImportInfo> list = new ArrayList<TaobaoCommodityImportInfo>();
		for (String extendID : extend_idArry) {
			list.add(toAssemblyCommodityData(merchantCode, extendID, loginName, isAudit));
		}
		return list;
	}

	/**
	 * 检查导入优购是否含有敏感词
	 * 
	 * @param extend_idArry
	 * @return
	 */
	public Map<String, String> checkTaobaoImportSensitiveWords(String extend_idArry[]) {
		Map<String, String> map = new HashMap<String, String>();
		for (String extendID : extend_idArry) {
			TaobaoItemExtend taobaoItemExtend = taobaoItemExtendMapper.getTaobaoItemExtendByExtendId(extendID);
			String result = commodityComponent.checkSensitiveWord(null,
					taobaoItemExtend.getTitle() + taobaoItemExtend.getYougouDescription());
			if (StringUtils.isNotBlank(result)) {
				if (!map.containsKey(result)) {
					map.put(result, taobaoItemExtend.getYougouSupplierCode());
				} else {
					map.put(result, map.get(result) + "," + taobaoItemExtend.getYougouSupplierCode());
				}
			}
		}
		return map;
	}

	public Map<String, String> getImportYougouCommodity(String merchantCode, long numIid) throws Exception {
		Map<String, String> mapCommodityBasicData = new HashMap<String, String>();
		TaobaoItem taobaoItem = taobaoItemMapper.getTaobaoItemByNumIid(numIid);
		long cid = taobaoItem.getCid();
		long nickId = taobaoItem.getNickId();
		// 根据merchantCode和淘宝cid、nickId校验是否和优购的类目做了绑定
		TaobaoYougouItemCat taobaoYougouItemCat = taobaoItemCatMapper.getTaobaoYougouItemCatByCid(merchantCode, nickId,
				cid);
		String yougouCatNo = null;
		if (taobaoYougouItemCat != null) {
			yougouCatNo = taobaoYougouItemCat.getYougouCatNo();
		} else {
			logger.error("[淘宝导入]商家编码:{}-没有从淘宝抓取到对应的类目数据。cid:{}", merchantCode, cid);
			throw new Exception("没有从淘宝抓取到对应的类目数据!");
		}
		if (yougouCatNo == null || "".equals(yougouCatNo)) {
			logger.error("[淘宝导入]商家编码:{}-还没绑定到对应的优购类目，请先做绑定!。cid:{}", merchantCode, cid);
			throw new Exception("还没绑定到对应的优购类目，请先做绑定!");
		}

		mapCommodityBasicData.put("yougouCatNo", yougouCatNo);

		String props = taobaoItem.getProps();

		// 装载商品属性数据到hashmap里边
		Map<Long, Set<Long>> mapProps = getTaobaoItemPropsMap(props);
		// 属性数据中获取品牌
		long pid = TaobaoImportConstants.BRAND_P_ID;
		long vid = 0;
		Set<Long> set = mapProps.get(pid);
		Iterator<Long> it = set.iterator();
		while (it.hasNext()) {
			vid = it.next();
		}
		String yougouBrandNo = taobaoBrandMapper
				.getYougouBrandNoByTaobaoNickIdAndPidVid(merchantCode, nickId, pid, vid);
		if (yougouBrandNo == null || "".equals(yougouBrandNo)) {
			logger.error("[淘宝导入]商家编码:{}-还没绑定到对应的优购品牌，请先做绑定!。cid:{}", merchantCode, cid);
			throw new Exception("还没绑定到对应的优购品牌，请先做绑定!");
		}
		mapCommodityBasicData.put("yougouCatNo", yougouCatNo);
		return mapCommodityBasicData;
	}

	/**
	 * 将淘宝商品数据组装并导入优购
	 * 
	 * @param merchantCode
	 * @param extendID
	 * @param loginName
	 * @param isAudit
	 * @return
	 */
	private TaobaoCommodityImportInfo toAssemblyCommodityData(String merchantCode, String extendID, String loginName,
			String isAudit) {
		TaobaoCommodityImportInfo taobaoCommodityImportInfo = new TaobaoCommodityImportInfo();
		List<String> errorList = new ArrayList<String>();
		taobaoCommodityImportInfo.setErrorList(errorList);
		taobaoCommodityImportInfo.setId(extendID);
		try {

			TaobaoItemExtend taobaoItemExtend = taobaoItemExtendMapper.getTaobaoItemExtendByExtendId(extendID);
			if (taobaoItemExtend != null && "0".equals(taobaoItemExtend.getIsImportYougou())) {
				// add by lsm 如果商品状态是未通过校验，直接返回
				if (!TaobaoImportConstants.STATUS_ALLOW.equals(taobaoItemExtend.getCheckStatus())) {
					logger.error("[淘宝导入]商家编码:{}-校验未通过的商品不允许申请审核 ,  extendId:{}", merchantCode, extendID);
					throw new Exception("商品校验未通过");
				}

				TaobaoItem taobaoItem = taobaoItemMapper.getTaobaoItemByExtendId(extendID);
				if (taobaoItem == null) {
					logger.error("[淘宝导入]商家编码:{}-未找到淘宝商品数据 ,  extendId:{}", merchantCode, extendID);
					throw new Exception("商品不存在");
				}
				long cid = taobaoItem.getCid();
				long nickId = taobaoItem.getNickId();
				// 根据merchantCode和淘宝cid、nickId校验是否和优购的类目做了绑定
				TaobaoYougouItemCat taobaoYougouItemCat = taobaoItemCatMapper.getTaobaoYougouItemCatByCid(merchantCode,
						nickId, cid);
				String yougouCatNo = null;
				if (taobaoYougouItemCat != null) {
					yougouCatNo = taobaoYougouItemCat.getYougouCatNo();
				} else {
					logger.error("[淘宝导入]商家编码:{}-没有从淘宝抓取到对应的类目数据! ,  extendId:{}", merchantCode, extendID);
					throw new Exception("没有从淘宝抓取到对应的类目数据!");
				}
				if (StringUtils.isEmpty(yougouCatNo)) {
					logger.error("[淘宝导入]商家编码:{}-还没绑定到对应的优购类目，请先做绑定! ,  extendId:{}", merchantCode, extendID);
					throw new Exception("还没绑定到对应的优购类目，请先做绑定!");
				}
				String props = taobaoItem.getProps();

				// 装载商品属性数据到hashmap里边
				Map<Long, Set<Long>> mapProps = getTaobaoItemPropsMap(props);
				// 属性数据中获取品牌
				long pid = TaobaoImportConstants.BRAND_P_ID;
				long vid = 0;
				Set<Long> set = mapProps.get(pid);
				if (CollectionUtils.isEmpty(set)) {
					logger.error("[淘宝导入]商家编码:{}-商品属性数据为空！ ,  extendId:{}", merchantCode, extendID);
					throw new Exception("商品属性数据为空！");
				}
				Iterator<Long> it = set.iterator();
				while (it.hasNext()) {
					vid = it.next();
				}
				String yougouBrandNo = taobaoBrandMapper.getYougouBrandNoByTaobaoNickIdAndPidVid(merchantCode, nickId,
						pid, vid);
				if (StringUtils.isEmpty(yougouBrandNo)) {
					logger.error("[淘宝导入]商家编码:{}-还没绑定到对应的优购品牌，请先做绑定! ,  extendId:{}", merchantCode, extendID);
					throw new Exception("还没绑定到对应的优购品牌，请先做绑定!");
				}

				CommodityVO commodityVO = new CommodityVO();
				// 商品名称(必填)
				commodityVO.setCommodityName(taobaoItemExtend.getTitle());
				// 供应商款色编码(必填)
				commodityVO.setSupplierCode(taobaoItemExtend.getYougouSupplierCode());
				// 颜色(必填)
				commodityVO.setSpecName(taobaoItemExtend.getYougouSpecName());
				// 颜色编号(必填)
				commodityVO.setSpecNo("");
				// 别名 ?_?
				commodityVO.setAlias(null);
				// 分类id(必填)
				commodityVO.setCatId(CommodityUtil.getCatIdByThreeCategory(taobaoItem.getYougouCateNo()));
				// 分类编号(必填)
				commodityVO.setCatNo(CommodityUtil.getCatNoByThreeCategory(taobaoItem.getYougouCateNo()));
				// 分类名称(必填)
				commodityVO.setCatName(CommodityUtil.getCatNameByThreeCategory(taobaoItem.getYougouCateNo()));
				// 分类结构名(必填)
				String catStructname = CommodityUtil.getStructNameByThreeCategory(taobaoItem.getYougouCateNo());
				commodityVO.setCatStructname(catStructname);
				// 品牌编号(必填)
				commodityVO.setBrandNo(taobaoItem.getYougouBrandNo());
				// 品牌名称(必填)
				commodityVO.setBrandName(taobaoItem.getYougouBrandName());
				// 市场价(必填) 大于0
				commodityVO.setPublicPrice(Double.parseDouble(taobaoItem.getPrice()));
				// 销售价格(必填) 大于0
				commodityVO.setSalePrice(taobaoItemExtend.getYougouPrice());
				// 商品年份
				commodityVO.setYear(taobaoItem.getYougouYears());
				// 款号 (必填)
				commodityVO.setStyleNo(taobaoItemExtend.getYougouStyleNo());
				// 更新人
				commodityVO.setUpdatePerson(loginName);
				// 商家编码
				commodityVO.setMerchantCode(merchantCode);
				// 宝贝描述
				commodityVO.setProdDesc(taobaoItemExtend.getYougouDescription());
				// 货品信息
				List<ProductVO> productMsgList = new ArrayList<ProductVO>();
				ProductVO productVO = null;
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("extendId", taobaoItemExtend.getExtendId());
				List<TaobaoItemExtendYougouPropValueSize> extendYougouPropValueSizes = taobaoItemExtendPropMapper
						.selectTaobaoItemYougouPropValueSize(params);
				if (CollectionUtils.isEmpty(extendYougouPropValueSizes)) {
					logger.error("[淘宝导入]商家编码:{}-未找到货品信息! ,  extendId:{}", merchantCode, extendID);
					throw new Exception("未找到货品信息!");
				} else {
					for (TaobaoItemExtendYougouPropValueSize extendYougouPropValueSize : extendYougouPropValueSizes) {
						productVO = new ProductVO();

						// 条形码 ?_?
						productVO.setInsideCode(extendYougouPropValueSize.getBarcode());
						// 供应商条码
						productVO.setThirdPartyCode(extendYougouPropValueSize.getBarcode());
						// 尺码
						productVO.setSizeName(extendYougouPropValueSize.getYougouPropValueName());
						// 尺码编号
						productVO.setSizeNo(extendYougouPropValueSize.getYougouPropValueNo());
						// 重量(xls)
						productVO.setWeight(extendYougouPropValueSize.getWeight() == null ? null : Double
								.valueOf(extendYougouPropValueSize.getWeight()));
						// 货品可编辑状态 ?_?
						productVO.setEditStatus(null);
						// 货品排序 ?_?...
						productVO.setSortNo(1);
						// 货品销售状态 ?_?
						productVO.setSellStatus(null);
						// 抽样状态 ?_?
						productVO.setSampleSet(null);
						// 库存信息 ?_?...
						productVO.setStock(extendYougouPropValueSize.getStock());

						productMsgList.add(productVO);
					}
				}
				commodityVO.setProductMsgList(productMsgList);

				// 扩展属性信息
				List<CommodityPropVO> commodityPropVOList = new ArrayList<CommodityPropVO>();
				CommodityPropVO commodityPropVO = null;
				List<TaobaoItemExtendYougouPropValue> extendYougouPropValues = taobaoItemExtendPropMapper
						.selectTaobaoItemYougouPropValueByExtendId(taobaoItemExtend.getExtendId());
				if (CollectionUtils.isEmpty(extendYougouPropValueSizes)) {
					logger.error("[淘宝导入]商家编码:{}-未找到扩展属性信息! ,  extendId:{}", merchantCode, extendID);
					throw new Exception("未找到扩展属性信息!");
				} else {
					for (TaobaoItemExtendYougouPropValue extendYougouPropValue : extendYougouPropValues) {
						// 兼容属性值多选情况
						commodityPropVO = new CommodityPropVO();
						commodityPropVO.setPropItemNo(extendYougouPropValue.getYougouPropItemNo());
						commodityPropVO.setPropItemName(extendYougouPropValue.getYougouPropItemName());
						commodityPropVO.setPropValueNo(extendYougouPropValue.getYougouPropValueNo());
						commodityPropVO.setPropValue(extendYougouPropValue.getYougouPropValueName());
						commodityPropVOList.add(commodityPropVO);
					}
				}
				commodityVO.setCommodityPropVOList(commodityPropVOList);

				// 尺码表id ?_?
				commodityVO.setSizeChartId(null);
				// 商品的销售状态 （上架下架等状态，导入时不需要填）
				commodityVO.setSellStatus(null);
				// 是否抽样 0是不需要抽样，1是需要抽样 ?_?
				commodityVO.setSampleFlag(null);
				// 是否供应商提供图片 0是不提供，1是提供
				// sampleFlag和suppliersPicFlag互斥，两个参数必有且只能有一个值为1，具体逻辑背景可以找产品了解
				// ?_?
				commodityVO.setSuppliersPicFlag(null);
				// 是否已经上传图片 ?_?
				commodityVO.setPicFlag(null);
				// 商品默认图片地址 ?_?
				commodityVO.setDefaultPic(null);
				// 品牌SpeelingName 和ftp图片路径有关系 ?_?
				commodityVO.setBrandSpeelingName(null);

				// 填充商品默认扩展属性
				commodityComponent.fillCommodityVo(commodityVO);

				MerchantImportInfo returnVo = commodityApi.insertCommodityMerchant(commodityVO);

				if (returnVo != null && returnVo.getCommodityNo() != null) {
					// 添加商品到商品系统成功，更新状态和商品编号
					TaobaoItemExtend updateTaobaoItemExtend = new TaobaoItemExtend();
					updateTaobaoItemExtend.setExtendId(extendID);
					updateTaobaoItemExtend.setImportYougouTime(DateUtil.getDateTime(new Date()));
					updateTaobaoItemExtend.setIsImportYougou("1");
					updateTaobaoItemExtend.setYougouCommodityNo(returnVo.getCommodityNo());
					updateTaobaoItemExtend.setMerchantCode(merchantCode);
					taobaoItemExtendMapper.updateByPrimaryKeySelective(updateTaobaoItemExtend);
					// 发送消息切图
					// 获取角度图
					List<TaobaoItemImg> imageList = taobaoItemImgMapper.queryTaobaoItemImgByExtendId(extendID);
					if (CollectionUtils.isNotEmpty(imageList)) {
						String[] picURLArry = new String[imageList.size()];
						for (int i = 0; i < picURLArry.length; i++) {
							picURLArry[i] = imageList.get(i).getYougouUrl();
						}
						this.sendJmsForMaster(merchantCode, returnVo.getCommodityNo(), this.getCommodityUrlFragment(
								taobaoItem.getYougouBrandNo(), taobaoItem.getYougouYears(), returnVo.getCommodityNo()),
								taobaoItemExtend.getYougouDescription(), picURLArry, catStructname);
					} else {
						logger.error("[淘宝导入]商家编码:{}-未找到角度图信息! ,  extendId:{}", merchantCode, extendID);
					}

					// 如果是保存并提交审核
					if (CommodityConstant.SUBMIT_COMMODITY_IS_SAVE_SUBMIT_TRUE.equals(isAudit)) {
						boolean checkResult = false;
						try {
							checkResult = merchantCommodityService.checkCommodityPicsIntegrality(
									returnVo.getCommodityNo(), commodityVO.getSupplierCode(), merchantCode,
									commodityVO.getBrandNo());
						} catch (Exception e) {
							taobaoCommodityImportInfo.setResult("ERROR");
							errorList.add("商品编码:" + returnVo.getCommodityNo() + "-提交审核失败!" + e.getMessage());
							taobaoCommodityImportInfo.setErrorList(errorList);
							logger.error("[淘宝导入]商家编码:{}-淘宝商品导入优购产生异常 ,提交审核失败, extendId:{} 商品编码:{}  ", 
									merchantCode, extendID,returnVo.getCommodityNo(),e);
							return taobaoCommodityImportInfo;
						}
						// 校验图片完整性
						if (!checkResult) {
							taobaoCommodityImportInfo.setResult("ERROR");
							errorList.add("商品编码:" + returnVo.getCommodityNo() + "-提交审核失败,图片完整性校验不通过!");
							taobaoCommodityImportInfo.setErrorList(errorList);						
							logger.error("[淘宝导入]商家编码:{}-图片完整性校验不通过! , extendId:{} 商品编码:{}", 
									merchantCode, extendID,returnVo.getCommodityNo());
							return taobaoCommodityImportInfo;
						}

						// 提交审核
						logger.info("[淘宝导入]商家编码:{} 商品编码:{} 提交审核开始", merchantCode, returnVo.getCommodityNo());
						boolean audit = commodityApi.auditMerchant(returnVo.getCommodityNo(), merchantCode);
						logger.info("[淘宝导入]商家编码:{} 商品编码:{} 提交审核结果:{}",merchantCode, returnVo.getCommodityNo(),
								audit);
						if (!audit) {
							taobaoCommodityImportInfo.setResult("ERROR");
							errorList.add("商品编码:" + returnVo.getCommodityNo() + "-提交审核失败!");
							taobaoCommodityImportInfo.setErrorList(errorList);
							logger.error("[淘宝导入]商家编码:{}-调用接口commodityApi.auditMerchant失败,extendID:{} 商品编码:{}",merchantCode, extendID, returnVo.getCommodityNo());
							return taobaoCommodityImportInfo;
						}

					}
					try {
						// 插入敏感词日志
						String sensitiveWords = insertSensitiveWordsLog(loginName, isAudit, taobaoItemExtend,
								returnVo.getCommodityNo());
						// 增加商品扩展表数据
						CommodityExtend commodityExtend = new CommodityExtend();
						commodityExtend.setId(UUIDGenerator.getUUID());
						commodityExtend.setCommodityNo(returnVo.getCommodityNo());
						commodityExtend.setSensitiveWord(sensitiveWords);
						commodityExtendService.insertCommodityExtend(commodityExtend);
					} catch (Exception e) {
						logger.error("[淘宝导入]商家编码:{}-记录敏感词log或增加商品扩展表敏感词记录异常， 商品编码:{}",merchantCode, returnVo.getCommodityNo(), e);
					}

					// 给旗舰店插入数据
					try {
						fssYmcApiService.updateStoreIdWhenPublishCommodity(merchantCode, commodityVO.getBrandNo(),
								commodityVO.getCatStructname());
					} catch (Exception e) {
						logger.error("[淘宝导入]商家编码:{}-给旗舰店添加商品运行时异常， 商品编码:{}",merchantCode, returnVo.getCommodityNo(), e);
					}
					taobaoCommodityImportInfo.setResult(returnVo.getResult());
					if (CollectionUtils.isNotEmpty(returnVo.getErrorList())) {
						taobaoCommodityImportInfo.setErrorList(returnVo.getErrorList());
					}
				} else {
					taobaoCommodityImportInfo.setResult("ERROR");
					errorList.add("商家中心:添加商品到商品系统失败!");
					if (returnVo != null && CollectionUtils.isNotEmpty(returnVo.getErrorList())) {
						errorList.addAll(returnVo.getErrorList());
					}
					taobaoCommodityImportInfo.setErrorList(errorList);
				}
				return taobaoCommodityImportInfo;
			} else {
				taobaoCommodityImportInfo.setResult("ERROR");
				errorList.add("商家中心:该商品已导入,请勿重复导入!");
				taobaoCommodityImportInfo.setErrorList(errorList);
				return taobaoCommodityImportInfo;
			}
		} catch (Exception e) {
			logger.error("[淘宝导入]商家编码:{}-淘宝商品导入优购产生异常:extendID:{} 异常原因:{}", merchantCode, extendID, e );
			taobaoCommodityImportInfo.setResult("ERROR");
			errorList.add("商家中心:该商品导入系统产生异常,请联系管理员!" + e.getMessage());
			taobaoCommodityImportInfo.setErrorList(errorList);
		}
		return taobaoCommodityImportInfo;
	}

	/**
	 * 插入敏感词日志
	 * 
	 * @param loginName
	 * @param isAudit
	 * @param taobaoItemExtend
	 * @param commodityNo
	 */
	private String insertSensitiveWordsLog(String loginName, String isAudit, TaobaoItemExtend taobaoItemExtend,
			String commodityNo) {
		String sensitiveWords = commodityComponent.checkSensitiveWord(null, taobaoItemExtend.getTitle()
				+ taobaoItemExtend.getYougouDescription());
		// 插入敏感词日志
		SensitiveCheckLog sensitiveLog = new SensitiveCheckLog();
		sensitiveLog.setCommodityNo(commodityNo);
		sensitiveLog.setContent("标题：" + taobaoItemExtend.getTitle() + "描述：" + taobaoItemExtend.getYougouDescription());
		// 如果是保存并提交审核
		if (CommodityConstant.SUBMIT_COMMODITY_IS_SAVE_SUBMIT_TRUE.equals(isAudit)) {
			sensitiveLog.setComment("淘宝导入-导入审核");
			sensitiveLog.setFollowOperate((short) 1);
		} else {
			sensitiveLog.setComment("淘宝导入-导入优购");
			sensitiveLog.setFollowOperate((short) 0);
		}
		sensitiveLog.setOperatorPerson(loginName);
		sensitiveLog.setSensitive(StringUtils.isNotBlank(sensitiveWords) ? true : false);
		sensitiveLog.setSensitiveWord(sensitiveWords);
		sensitiveLog.setStyleNo(taobaoItemExtend.getYougouStyleNo());
		sensitiveLog.setSupplierCode(taobaoItemExtend.getYougouSupplierCode());
		sensitiveLog.setOperateType(SensitiveCheckLog.OperateType.ADD.getValue());

		boolean result = commodityComponent.insertSensitiveWordCheckLogByOne(sensitiveLog);
		if (!result) {
			logger.error("[淘宝导入]商家编码:{}-插入敏感词日志失败:commodityNo:{} 敏感词:{}",
					YmcThreadLocalHolder.getMerchantCode(),commodityNo, sensitiveLog);
		}
		return sensitiveWords;
	}

	/**
	 * 发送JMS消息切图
	 * 
	 * @param merchantCode
	 * @param commodityNo
	 * @param urlFragment
	 * @param proDesc
	 * @param picURLArry
	 * @param catStructName
	 */
	private void sendJmsForMaster(String merchantCode, String commodityNo, String urlFragment, String proDesc,
			String[] picURLArry, String catStructName) {
		// 定位放大镜图索引
		int sheetIndex = 1;
		try {
			sheetIndex = CommodityPicIndexer.indexSheets(merchantCommodityService
					.getCommodityCatNamesByCatStructName(catStructName));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		Image4SingleCommodityMessage imgMessage = new Image4SingleCommodityMessage();
		CommodityImage[] commodityImageArray = new CommodityImage[picURLArry.length];
		for (int i = 0; i < picURLArry.length; i++) {
			commodityImageArray[i] = new CommodityImage();
			commodityImageArray[i].setIndex(i + 1);
			if (picURLArry[i].length() > 3) {
				Matcher matcher = imgPattern.matcher(picURLArry[i]);
				if (matcher.find()) {
					commodityImageArray[i].setPicName(matcher.group(0));
				} else {
					commodityImageArray[i].setPicName("0");
				}
			} else {
				commodityImageArray[i].setPicName("0");
			}
			commodityImageArray[i].setPicUrl(picURLArry[i]);
		}

		imgMessage.setCommodityImages(commodityImageArray);
		imgMessage.setCommodityNo(commodityNo);
		imgMessage.setCreateTime(new Date());
		imgMessage.setFtpRelativePath(MessageFormat.format(settings.imageFtpPreTempSpace, merchantCode));
		imgMessage.setId(UUIDGenerator.getUUID());
		imgMessage.setMerchantCode(merchantCode);
		imgMessage.setProDesc(proDesc);
		imgMessage.setSeqNo(sheetIndex);
		imgMessage.setStatus(0);
		imgMessage.setUrlFragment(urlFragment);
		// 消息持久化到cache和DB
		this.redisTemplate.opsForHash().put(CacheConstant.C_IMAGE_MASTER_JMS_KEY, commodityNo, imgMessage);
		// 消息持久化到DB
		ImageJmsVo jmsVo = new ImageJmsVo();
		jmsVo.setCommodityNo(imgMessage.getCommodityNo());
		jmsVo.setMerchantCode(imgMessage.getMerchantCode());
		jmsVo.setCreateTime(new Date());
		// l 单品 批量--p(l图) m(b图)
		jmsVo.setPicType("l");
		jmsVo.setProDesc(imgMessage.getProDesc());
		jmsVo.setSeqNo(imgMessage.getSeqNo());
		jmsVo.setUrlFragment(imgMessage.getUrlFragment());
		jmsVo.setStatus(0);
		jmsVo.setId(imgMessage.getId());
		jmsVo.setImageId(ArrayUtils.isEmpty(picURLArry) ? "" : StringUtil.join(picURLArry, ","));
		merchantImageService.addImageJms(jmsVo);
		try {
			amqpTemplate.convertAndSend("ymc.handleimage.queue", imgMessage);
		} catch (AmqpException e) {
			logger.error("[淘宝导入]商家编码:{}-发送切图(单品)产生异常:commodityNo:{}",merchantCode, commodityNo , e);
		}
	}

	/**
	 * 查询品牌信息，拼接图片url路径
	 * 
	 * @param brandNo
	 * @param years
	 * @param commodityNo
	 * @return
	 * @throws Exception
	 */
	private String getCommodityUrlFragment(String brandNo, String years, String commodityNo) throws Exception {
		com.yougou.pc.model.brand.Brand brand = commodityBaseApiService.getBrandByNo(brandNo);
		if (null == brand){
			logger.error("[淘宝导入]商家编码:{}-调用商品接口获取品牌异常:brandNo:{} commodityNo:{}", 
					YmcThreadLocalHolder.getMerchantCode(),brandNo, commodityNo);
			throw new Exception("调用商品接口获取品牌异常brandNo:" + brandNo + " commodityNo:" + commodityNo);
		}
		return new StringBuilder("/").append(brand.getSpeelingName()).append("/").append(years).append("/")
				.append(commodityNo).append("/").toString();
	}

	/**
	 * 根据淘宝商品属性字符串转换为属性和属性值
	 * 
	 * @param props
	 * @return
	 */
	private Map<Long, Set<Long>> getTaobaoItemPropsMap(String props) {
		// 装载商品属性数据到hashmap里边
		Map<Long, Set<Long>> mapProps = new HashMap<Long, Set<Long>>();
		if (props != null) {
			String[] arrProps = props.split(";");
			if (arrProps != null) {
				for (String sProp : arrProps) {
					String[] sTemp = sProp.split(":");
					if (sTemp != null) {
						if (sTemp[0] != null && !"".equals(sTemp[0]) && sTemp[1] != null && !"".equals(sTemp[1])) {
							long propId = Long.valueOf(sTemp[0]);
							if (mapProps.containsKey(propId)) {
								long propValId = Long.valueOf(sTemp[1]);
								Set<Long> setPropValTemp = mapProps.get(propId);
								setPropValTemp.add(propValId);
								mapProps.put(propId, setPropValTemp);
							} else {
								long propValId = Long.valueOf(sTemp[1]);
								Set<Long> setPropValTemp = new HashSet<Long>();
								setPropValTemp.add(propValId);
								mapProps.put(propId, setPropValTemp);
							}
						}
					}
				}
			}
		}
		return mapProps;
	}

	@Override
	public PageFinder<TaobaoItemExtendDto> getTaobaoItem(Map<String, Object> params, Query query) {
		RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
		if (!org.springframework.util.StringUtils.isEmpty(params.get("catId"))) {
			Category category = commodityBaseApiService.getCategoryById(String.valueOf(params.get("catId")));
			if (null != category) {
				params.put("yougouCatNo", category.getCatNo());
			}

		}
		List<TaobaoItemExtendDto> extendList = taobaoItemExtendMapper.selectTaobaoItemExtend(params, rowBounds);
		int count = taobaoItemExtendMapper.selectTaobaoItemExtendCount(params);
		PageFinder<TaobaoItemExtendDto> pageFinder = new PageFinder<TaobaoItemExtendDto>(query.getPage(),
				query.getPageSize(), count, extendList);
		return pageFinder;
	}

	@Override
	public TaobaoItem getTaobaoItemByNumIid(long num_iid) {
		return taobaoItemMapper.getTaobaoItemByNumIid(num_iid);
	}

	@Override
	public void updateByPrimaryKeySelective(TaobaoItem taobaoItem) {
		taobaoItemMapper.updateByPrimaryKeySelective(taobaoItem);
	}

	/**
	 * 删除淘宝商品
	 */
	@Override
	@Transactional
	public void deleteTaobaoItemExtend(String idStr, String merchantCode) throws BusinessException {

		if (isEmpty4String(idStr)) {
			throw new BusinessException("请求参数错误");
		}
		String[] ids = idStr.split(",");
		TaobaoItemExtend exntend = null;
		for (String id : ids) {
			exntend = new TaobaoItemExtend();
			exntend.setExtendId(id);
			exntend.setIsImportYougou(IsImportYougou.STATUS_DELETED.getStatus());
			exntend.setMerchantCode(merchantCode);
			taobaoItemExtendMapper.updateByPrimaryKeySelective(exntend);
		}
	}

	/**
	 * updateTaobaoItemExtend:删除还是还原商品
	 * 
	 * @author li.n1
	 * @param idStr
	 * @param merchantCode
	 * @param deleteOrReduct -1删除 0未导入优购（还原）
	 * @throws BusinessException
	 * @since JDK 1.6
	 */
	@Override
	@Transactional
	public void updateTaobaoItemExtend(String idStr, String merchantCode, String deleteOrReduct)
			throws BusinessException {
		if (isEmpty4String(idStr)) {
			throw new BusinessException("请求参数错误");
		}
		String[] ids = idStr.split(",");
		TaobaoItemExtend exntend = null;
		for (String id : ids) {
			exntend = new TaobaoItemExtend();
			exntend.setExtendId(id);
			exntend.setIsImportYougou(deleteOrReduct);
			exntend.setMerchantCode(merchantCode);
			taobaoItemExtendMapper.updateByPrimaryKeySelective(exntend);
		}

	}

	/**
	 * 彻底删除淘宝商品 物理删除
	 * 
	 * @param idStr
	 * @param merchantCode
	 */
	@Transactional
	public void deleteTaobaoItemForever(String[] idStr, String merchantCode) throws BusinessException {
		for (String id : idStr) {
			// 查询商品扩展信息
			TaobaoItemExtend extend = taobaoItemExtendMapper.getTaobaoItemExtendByExtendId(id);
			if (null == extend) {
				logger.error("[淘宝导入]商家编码:{}-删除的记录没有找到:ExtendId:{}", merchantCode, id);
				throw new BusinessException("删除的记录没有找到");
			}
			// 删除扩展信息
			taobaoItemExtendMapper.deleteByExtendId(id, merchantCode);

			// 删除尺码信息tbl_merchant_taobao_item_extend_yougou_prop_value_size
			taobaoItemExtendPropMapper.deleteTaobaoItemYougouPropValueSizeByExtendId(id, merchantCode);

			// 删除图片信息tbl_merchant_taobao_item_img
			taobaoItemImgMapper.deleteTaobaoItemImgByExtendId(id);

			// 是否是最后一条记录
			int count = taobaoItemExtendMapper.selectCountByNumIid(extend.getNumIid());

			/*
			 * 是最后一条记录，删除 商品 主表信息（tbl_merchant_taobao_item） 商品扩展属性信息
			 * tbl_merchant_taobao_item_extend_yougou_prop_value
			 * 属性图片信息（tbl_merchant_taobao_item_prop_img）
			 * SKU信息（tbl_merchant_taobao_item_sku）
			 */
			if (count == 0) {
				// 删除 商品 主表信息（tbl_merchant_taobao_item）
				taobaoItemMapper.deleteByPrimaryKey(extend.getNumIid(), merchantCode);

				// 商品扩展属性信息 tbl_merchant_taobao_item_extend_yougou_prop_value
				taobaoItemExtendPropMapper.deleteTaobaoItemYougouPropValueByNumIid(extend.getNumIid(), merchantCode);

				// 属性图片信息（tbl_merchant_taobao_item_prop_img）
				taobaoItemPropImgMapper.deleteTaobaoItemPropImgByNumIid(extend.getNumIid());

				// 删除SKU信息
				taobaoItemSkuMapper.deleteTaobaoItemSkuByNumIid(extend.getNumIid());
			}
		}
	}

	/**
	 * 检查商品绑定信息
	 */
	public void checkBindInfo(long numIid, String merchantCode) throws BusinessException {
		TaobaoItem taobaoItem = taobaoItemMapper.getTaobaoItemByNumIid(numIid);
		if (null == taobaoItem) {
			logger.error("[淘宝导入]商家编码:{}-未找到淘宝商品信息:numIid:{}", merchantCode, numIid);
			throw new BusinessException("未找到淘宝商品信息。");
		}
		long cid = taobaoItem.getCid();
		long nickId = taobaoItem.getNickId();

		String yougou_brand_no = this.getBandYougouBrandNo(taobaoItem.getProps(), merchantCode, nickId);
		if (isEmpty4String(yougou_brand_no)) {
			logger.error("[淘宝导入]商家编码:{}-商品对应的品牌没有绑定，请先绑定品牌:numIid:{}", merchantCode, numIid);
			throw new BusinessException("商品对应的品牌没有绑定，请先绑定品牌");
		}

		// 根据merchantCode和淘宝cid、nickId校验是否和优购的类目做了绑定
		String yougouCatNo = getYougouCatNo(merchantCode, nickId, cid);

		List<Brand> lstBrand = commodityService.queryBrandList(merchantCode);
		if (null == lstBrand) {
			logger.error("[淘宝导入]商家编码:{}-未找到商品授权的品牌信息:numIid:{}", merchantCode, numIid);
			throw new BusinessException("未找到商品授权的品牌信息。");
		}
		String brandId = "";
		for (Brand brand : lstBrand) {
			if (yougou_brand_no.equals(brand.getBrandNo())) {
				brandId = brand.getId();
			}
		}
		if (StringUtils.isBlank(brandId)) {
			logger.error("[淘宝导入]商家编码:{}-未找到品牌授权信息:numIid:{}", merchantCode, numIid);
			throw new BusinessException("未找到品牌授权信息。");
		}
		List<Category> cateList = commodityService.getAllCategoryByBrandId(merchantCode, brandId);
		if (null == cateList) {
			logger.error("[淘宝导入]商家编码:{}-未找到品牌关联的分类信息:numIid:{}", merchantCode, numIid);
			throw new BusinessException("未找到品牌关联的分类信息。");
		}
		boolean isOk = false;
		for (Category category : cateList) {
			if (yougouCatNo.equals(category.getCatNo())) {
				isOk = true;
				break;
			}
		}

		if (!isOk) {
			logger.error("[淘宝导入]商家编码:{}-商品对应的分类绑定，不在绑定品牌下，请重新设置分类，或品牌!:numIid:{}", merchantCode, numIid);
			throw new BusinessException("商品对应的分类绑定，不在绑定品牌下，请重新设置分类，或品牌!");
		}
		// 判断分类对应是否是否属匹配品牌对应

		/* 判断优购必填属性是否对应 */
		// 查新分类对应的所有优购属性
		/*
		 * Category category = commodityBaseApiService
		 * .getCategoryByNo(yougouCatNo); List<PropItem> yougouPropList =
		 * merchantCommodityService .getPropMsgByCatIdNew(category .getId());
		 * //int mustCount = 0; // 优购必填属性数量 for (PropItem prop : yougouPropList)
		 * { if (null != prop.getIsShowMall() && prop.getIsShowMall().intValue()
		 * == 0) { mustCount++; } } // 淘宝优购属性绑定必填属性数量 Map<String, Object> params
		 * = new HashMap<String, Object>(); params.put("taobaoCid", cid);
		 * params.put("yougouCatNo", yougouCatNo); params.put("nickId", nickId);
		 * params.put("merchantCode", merchantCode); params.put("isYougouMust",
		 * 0); List<TaobaoYougouItemProp> propList = taobaoYougouItemPropMapper
		 * .selectTaobaoYougouItemPropList(params); if (propList == null ||
		 * propList.size() < mustCount) { throw new
		 * BusinessException("商品对应的分类必填属性没有绑定，请先绑定!"); }
		 */
	}

	private String getBandYougouBrandNo(String props, String merchantCode, long nickId) throws BusinessException {
		String[] propsArray = props.split(";");
		String pid = "";
		String vid = "";
		for (String prop : propsArray) {
			String[] prop_vals = prop.split("\\:");
			if (PropTypeValue.PROPTYPE20000.getValue().equals(prop_vals[0])) {
				pid = prop_vals[0];
				vid = prop_vals[1];
				break;
			}
		}
		if (isEmpty4String(pid)) {
			logger.error("[淘宝导入]商家编码:{}-商品对应的品牌为空不能修改:props:{}", merchantCode, props);
			throw new BusinessException("商品对应的品牌为空不能修改");
		}

		String yougou_brand_no = taobaoBrandMapper.getYougouBrandNoByTaobaoNickIdAndPidVid(merchantCode, nickId,
				Long.valueOf(pid), Long.valueOf(vid));
		return yougou_brand_no;
	}

	private String getYougouCatNo(String merchantCode, long nickId, long cid) throws BusinessException {
		String yougouCatNo = null;
		TaobaoYougouItemCat taobaoYougouItemCat = taobaoItemCatMapper.getTaobaoYougouItemCatByCid(merchantCode, nickId,
				cid);
		if (taobaoYougouItemCat != null) {
			yougouCatNo = taobaoYougouItemCat.getYougouCatNo();
		} else {
			logger.error("[淘宝导入]商家编码:{}-商品对应的分类为空不能修改!:cid:{}", merchantCode, cid);
			throw new BusinessException("商品对应的分类为空不能修改!");
		}
		if (yougouCatNo == null || "".equals(yougouCatNo)) {
			logger.error("[淘宝导入]商家编码:{}-商品对应的分类没有绑定，请先绑定分类!,cid:{}", merchantCode, cid);
			throw new BusinessException("商品对应的分类没有绑定，请先绑定分类!");
		}
		return yougouCatNo;
	}

	// 设置颜色
	private void setYougouSpecNameNew(TaobaoItemExtend taobaoItem, List<TaobaoItemExtendYougouPropValue> propList) {
		final String yougouPropItemNo = "color";// 优购颜色分类编码
		if (null != propList && !propList.isEmpty()) {
			for (TaobaoItemExtendYougouPropValue v : propList) {
				if (yougouPropItemNo.equals(v.getYougouPropItemNo())) {
					taobaoItem.setYougouSpecName(v.getYougouPropValueNo());
				}
			}
		}
	}

	public Commodity getExtendFormatCommodity(String extendId, long numIid) throws Exception {
		TaobaoItemExtend taobaoItemExtend = taobaoItemExtendMapper.getTaobaoItemExtendByExtendId(extendId);
		String merchantCode = YmcThreadLocalHolder.getMerchantCode();

		// 保存已经对应的属性，属性值
		Map<String, Object> params = new HashMap<String, Object>();
		if (numIid != 0) {
			params.put("numIid", numIid);
		}
		params.put("extendId", extendId);
		params.put("merchantCode", merchantCode);
		List<TaobaoItemExtendYougouPropValue> propList = taobaoItemExtendPropMapper
				.selectTaobaoItemYougouPropValue(params);

		Commodity commodity = new Commodity();

		// 用extendId 代替commodityNo
		commodity.setCommodityNo(extendId);

		// 对应的优购的分类
		String yougouCatNo = this.getYougouCatNo(merchantCode, taobaoItemExtend.getNickId(), taobaoItemExtend.getCid());

		Category category = commodityBaseApiService.getCategoryByNo(yougouCatNo);
		taobaoItemExtend.setYougouCateNo(category.getStructName() + ";" + category.getId() + ";" + category.getCatNo()
				+ ";" + category.getCatName());
		TaobaoYougouItemCat taobaoYougouItemCat = taobaoItemCatMapper.getTaobaoYougouItemCatByCid(merchantCode,
				taobaoItemExtend.getNickId(), taobaoItemExtend.getCid());
		String yougoucatNo = taobaoItemExtend.getYougouCateNo();
		// 分类信息
		commodity.setCatName(taobaoYougouItemCat.getYougouCatFullName());
		commodity.setCatNo(taobaoYougouItemCat.getYougouCatNo());
		commodity.setCatStructName(yougoucatNo);

		// 品牌信息
		commodity.setBrandNo(taobaoItemExtend.getYougouBrandNo());
		if (StringUtils.isEmpty(taobaoItemExtend.getYougouBrandName())) {
			com.yougou.pc.model.brand.Brand brand = commodityBaseApiService.getBrandByNo(taobaoItemExtend
					.getYougouBrandNo());
			commodity.setBrandName(brand.getBrandName());
		} else {
			commodity.setBrandName(taobaoItemExtend.getYougouBrandName());
		}

		commodity.setSupplierCode(taobaoItemExtend.getYougouSupplierCode());
		//设置款号
		setYougouStyleNo(taobaoItemExtend);
		commodity.setStyleNo(taobaoItemExtend.getYougouStyleNo());

		commodity.setCommodityDesc(taobaoItemExtend.getYougouDescription());
		// 设置颜色
		setYougouSpecNameNew(taobaoItemExtend, propList);

		commodity.setColorName(taobaoItemExtend.getYougouSpecName());

		// 商品名称(必填)
		commodity.setCommodityName(taobaoItemExtend.getTitle());
		// 分类编号(必填)
		commodity.setCatNo(CommodityUtil.getCatNoByThreeCategory(taobaoItemExtend.getYougouCateNo()));
		// 分类名称(必填)
		commodity.setCatName(CommodityUtil.getCatNameByThreeCategory(taobaoItemExtend.getYougouCateNo()));
		// 款号 (必填)
		commodity.setStyleNo(taobaoItemExtend.getYougouStyleNo());
		// 更新人
		commodity.setUpdatePerson(YmcThreadLocalHolder.getOperater());
		// 商家编码
		commodity.setMerchantCode(merchantCode);
		// 宝贝描述
		// commodity.setProdDesc(taobaoItemExtend.getYougouDescription());
		// 货品信息
		List<Product> productMsgList = new ArrayList<Product>();
		Product productVO = null;
		params = new HashMap<String, Object>();
		params.put("extendId", taobaoItemExtend.getExtendId());
		List<TaobaoItemExtendYougouPropValueSize> extendYougouPropValueSizes = taobaoItemExtendPropMapper
				.selectTaobaoItemYougouPropValueSize(params);
		for (TaobaoItemExtendYougouPropValueSize extendYougouPropValueSize : extendYougouPropValueSizes) {
			productVO = new Product();

			// 条形码 ?_?
			productVO.setInsideCode(extendYougouPropValueSize.getBarcode());
			// 供应商条码
			productVO.setThirdPartyInsideCode(extendYougouPropValueSize.getBarcode());
			// 尺码
			productVO.setSizeName(extendYougouPropValueSize.getYougouPropValueName());
			// 尺码编号
			productVO.setSizeNo(extendYougouPropValueSize.getYougouPropValueNo());
			// 重量(xls)
			productVO.setWeight(extendYougouPropValueSize.getWeight() == null ? null : extendYougouPropValueSize
					.getWeight().longValue());
			// 货品可编辑状态 ?_?
			productVO.setEditStatus(null);
			// 货品销售状态 ?_?
			productVO.setSellStatus(null);
			productMsgList.add(productVO);
		}
		commodity.setProducts(productMsgList);
		List<TaobaoItemExtendYougouPropValue> extendYougouPropValues = taobaoItemExtendPropMapper
				.selectTaobaoItemYougouPropValueByExtendId(taobaoItemExtend.getExtendId());
		// 扩展属性信息
		List<CommodityProp> commodityPropList = new ArrayList<CommodityProp>();
		CommodityProp commodityProp = null;
		for (TaobaoItemExtendYougouPropValue extendYougouPropValue : extendYougouPropValues) {
			// 兼容属性值多选情况
			commodityProp = new CommodityProp();
			commodityProp.setPropItemNo(extendYougouPropValue.getYougouPropItemNo());
			commodityProp.setPropItemName(extendYougouPropValue.getYougouPropItemName());
			commodityProp.setPropValueNo(extendYougouPropValue.getYougouPropValueNo());
			commodityProp.setPropValueName(extendYougouPropValue.getYougouPropValueName());
			commodityPropList.add(commodityProp);
		}
		commodity.setCommdoityProps(commodityPropList);
		// 尺码表id ?_?
		commodity.setSizeChartId(null);
		// 商品的销售状态 （上架下架等状态，导入时不需要填）
		// commodity.setSellStatus(null);
		// 是否抽样 0是不需要抽样，1是需要抽样 ?_?
		commodity.setSampleFlag(null);
		// 是否供应商提供图片 0是不提供，1是提供
		// sampleFlag和suppliersPicFlag互斥，两个参数必有且只能有一个值为1，具体逻辑背景可以找产品了解 ?_?
		commodity.setSuppliersPicFlag(null);
		// 是否已经上传图片 ?_?
		commodity.setPicFlag(null);
		return commodity;
	}

	/**
	 * 获取淘宝商品信息-跳转到修改商品页面
	 */
	@Transactional
	@Override
	public Map<String, Object> getTaobaoItem4Update(String merchantCode, long numIid, String extendId)
			throws BusinessException {
		if (org.springframework.util.StringUtils.isEmpty(numIid) || StringUtils.isEmpty(extendId)) {
			throw new BusinessException("参数错误!numIid为空，extendId为空");
		}
		Map<String, Object> result = new HashMap<String, Object>();
		setYear(result);
		TaobaoItemExtend taobaoItemExtend = taobaoItemExtendMapper.getTaobaoItemExtendByExtendId(extendId);
		// 优购所有品牌
		List<Brand> lstBrand = commodityService.queryBrandList(merchantCode);
		// 保存已经对应的属性，属性值
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("numIid", numIid);
		params.put("extendId", extendId);
		params.put("merchantCode", merchantCode);
		List<TaobaoItemExtendYougouPropValue> propList = taobaoItemExtendPropMapper
				.selectTaobaoItemYougouPropValue(params);
		// 尺码明细
		List<TaobaoItemExtendYougouPropValueSize> sizeInfo = taobaoItemExtendPropMapper
				.selectTaobaoItemYougouPropValueSize(params);
		if (isEmpty4String(taobaoItemExtend.getYougouBrandNo()) || isEmpty4String(taobaoItemExtend.getYougouCateNo())) {
			// 第一次，更新淘宝品牌，分类编码
			// 对应的优购的品牌
			String yougouBrandNo = this.getBandYougouBrandNo(taobaoItemExtend.getProps(), merchantCode,
					taobaoItemExtend.getNickId());
			// 对应的优购的分类
			String yougouCatNo = this.getYougouCatNo(merchantCode, taobaoItemExtend.getNickId(),
					taobaoItemExtend.getCid());

			Category category = commodityBaseApiService.getCategoryByNo(yougouCatNo);
			taobaoItemExtend.setYougouCateNo(category.getStructName() + ";" + category.getId() + ";"
					+ category.getCatNo() + ";" + category.getCatName());

			taobaoItemExtend.setYougouBrandNo(yougouBrandNo);
			// 设置款号
			setYougouStyleNo(taobaoItemExtend);
			// 设置颜色
			setYougouSpecName(taobaoItemExtend, propList);

			taobaoItemMapper.updateByPrimaryKeySelective(taobaoItemExtend);

			// 保存尺码明细
			if (CollectionUtils.isEmpty(sizeInfo)) {
				sizeInfo = saveSizeInfo(taobaoItemExtend, merchantCode, category.getId());
			}
		}
		if (CollectionUtils.isEmpty(propList)) {
			propList = saveYougouPropAndValue(taobaoItemExtend);
		}
		// 从cateNo中解析出真正的cateNo;
		taobaoItemExtend.setYougouCateNo(taobaoItemExtend.getYougouCateNo().split(";")[2]);

		// 获取角度图
		List<TaobaoItemImg> imageList = taobaoItemImgMapper.queryTaobaoItemImgByExtendId(extendId);
		JSONObject imageJson = new JSONObject();
		imageJson.put("images", imageList);
		result.put("images", imageJson.toString());

		JSONObject propListJson = new JSONObject();
		propListJson.put("props", propList);
		result.put("props", propListJson.toString());
		propListJson = new JSONObject();
		propListJson.put("sizeInfo", sizeInfo);
		result.put("sizeInfo", propListJson.toString());

		result.put("lstBrand", lstBrand);
		result.put("taobaoItem", taobaoItemExtend);
		result.put("YOUGOU_VALID_IMAGE_REGEX", settings.yougouValidImageRegex);
		return result;
	}

	// 保存尺码信息
	private List<TaobaoItemExtendYougouPropValueSize> saveSizeInfo(TaobaoItemExtend taobaoItem, String merchantCode,
			String catId) {
		// 查询sku信息
		List<TaobaoItemSku> skuList = taobaoItemSkuMapper.queryTaobaoItemSkuByNumIid(taobaoItem.getNumIid());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taobaoCid", taobaoItem.getCid());
		params.put("nickId", taobaoItem.getNickId());
		params.put("merchantCode", taobaoItem.getMerchantCode());
		// 查询绑定商品对应的绑定分类属性值
		List<TaobaoYougouItemPropValue> propValueList = taobaoYougouItemPropMapper
				.selectTaobaoYougouItemPropValueList(params);

		// 查询分类对应的尺码信息

		List<PropValue> sizeList = commodityApi.getSizeByCatId(catId);

		Map<String, SkuProp> map = new HashMap<String, SkuProp>();
		for (TaobaoItemSku sku : skuList) {
			String properties = sku.getPropertiesName();
			if (!StringUtils.isEmpty(properties)) {
				String[] propertyArray = properties.split(";");
				if (propertyArray.length > 1) {
					String[] sizeProperArray = propertyArray[1].split("\\:");
					if (sizeProperArray.length > 3) {
						if (map.get(sizeProperArray[3]) == null) {
							SkuProp value = new SkuProp();
							value.setPid(sizeProperArray[0]);
							value.setVid(sizeProperArray[1]);
							value.setVname(sizeProperArray[3]);
							value.setBarcode(sku.getOuterId());
							map.put(sizeProperArray[3], value);
						}

					}
				}
			}
		}
		List<TaobaoItemExtendYougouPropValueSize> extendSizeList = new ArrayList<TaobaoItemExtendYougouPropValueSize>();
		// 匹配属性
		Set<String> set = map.keySet();
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			SkuProp skuv = map.get(iterator.next());
			boolean setProp = false;
			TaobaoItemExtendYougouPropValueSize valueSize = null;
			for (TaobaoYougouItemPropValue pValue : propValueList) {
				if (skuv.getPid().equals(String.valueOf(pValue.getTaobaoPid()))
						&& skuv.getVid().equals(pValue.getTaobaoVid())) {
					valueSize = new TaobaoItemExtendYougouPropValueSize();
					valueSize.setBarcode(skuv.getBarcode());
					valueSize.setId(UUIDGenerator.getUUID());
					valueSize.setMerchantCode(merchantCode);
					valueSize.setNumIid(taobaoItem.getNumIid());
					valueSize.setYougouPropValueNo(pValue.getYougouPropValueNo());
					valueSize.setYougouPropValueName(skuv.getVname());
					valueSize.setExtendId(taobaoItem.getExtendId());
					extendSizeList.add(valueSize);
					setProp = true;
				}
			}
			// 如果没有设置属性对应关系，那么根据颜色属性名称匹配
			if (!setProp) {
				for (PropValue sizeP : sizeList) {
					if (sizeP.getPropValueName().equals(skuv.getVname())) {
						valueSize = new TaobaoItemExtendYougouPropValueSize();
						valueSize.setBarcode(skuv.getBarcode());
						valueSize.setId(UUIDGenerator.getUUID());
						valueSize.setMerchantCode(merchantCode);
						valueSize.setNumIid(taobaoItem.getNumIid());
						valueSize.setYougouPropValueNo(sizeP.getPropValueNo());
						valueSize.setYougouPropValueName(skuv.getVname());
						valueSize.setExtendId(taobaoItem.getExtendId());
						extendSizeList.add(valueSize);
						break;
					}
				}
			}
		}
		if (!extendSizeList.isEmpty()) {
			taobaoItemExtendPropMapper.insertTaobaoItemYougouPropValueSizeBatch(extendSizeList);
		}

		return extendSizeList;
	}

	private class SkuProp {
		private String pid;
		private String vid;
		private String vname;
		private String barcode;

		public String getPid() {
			return pid;
		}

		public void setPid(String pid) {
			this.pid = pid;
		}

		public String getVid() {
			return vid;
		}

		public void setVid(String vid) {
			this.vid = vid;
		}

		public String getVname() {
			return vname;
		}

		public void setVname(String vname) {
			this.vname = vname;
		}

		public String getBarcode() {
			return barcode;
		}

		public void setBarcode(String barcode) {
			this.barcode = barcode;
		}
	}

	/**
	 * 设置颜色
	 * 
	 * @param taobaoItem
	 * @param propList
	 */
	private void setYougouSpecName(TaobaoItem taobaoItem, List<TaobaoItemExtendYougouPropValue> propList) {
		final String yougouPropItemNo = "color";// 优购颜色分类编码
		if (null != propList && !propList.isEmpty()) {
			for (TaobaoItemExtendYougouPropValue v : propList) {
				if (yougouPropItemNo.equals(v.getYougouPropItemNo())) {
					// taobaoItem.setYougouSpecName(v.getYougouPropValueNo());
				}
			}
		}
	}

	/**
	 * 设置款号
	 * 
	 * @param taobaoItem
	 */
	private void setYougouStyleNo(TaobaoItem taobaoItem) {
		String inputPids = taobaoItem.getInputPids();
		String inputStr = taobaoItem.getInputStr();
		if (StringUtils.isNotEmpty(inputPids) && StringUtils.isNotEmpty(inputStr)) {
			String[] inputPidArray = inputPids.split(";");
			String[] inputStrArray = inputStr.split(";");
			for (int i = 0, len = inputPidArray.length; i < len; i++) {
				if (TaobaoImportConstants.TAOBAO_STYLE_NO.equals(inputPidArray[i])) {
					taobaoItem.setYougouStyleNo(inputStrArray[i] == null ? "" : inputStrArray[i]);
					break;
				}
			}
		}
	}

	private List<TaobaoItemExtendYougouPropValue> saveYougouPropAndValue(TaobaoItemExtend taobaoItem) {
		String[] propsArray = taobaoItem.getProps().split(";");
		String pid = "";
		String vid = "";
		Map<String, List<String>> itmePropMap = new HashMap<String, List<String>>();
		for (String prop : propsArray) {
			String[] prop_vals = prop.split("\\:");
			if (!PropTypeValue.PROPTYPE20000.getValue().equals(prop_vals[0])) {
				pid = prop_vals[0];
				vid = prop_vals[1];
				if (null == itmePropMap.get(pid)) {
					List<String> values = new ArrayList<String>();
					values.add(vid);
					itmePropMap.put(pid, values);
				} else {
					itmePropMap.get(pid).add(vid);
				}
			}
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taobaoCid", taobaoItem.getCid());
		params.put("nickId", taobaoItem.getNickId());
		params.put("merchantCode", taobaoItem.getMerchantCode());
		// 查询绑定商品对应的绑定分类属性值
		List<TaobaoYougouItemPropValue> propValueList = taobaoYougouItemPropMapper
				.selectTaobaoYougouItemPropValueList(params);

		List<TaobaoItemExtendYougouPropValue> yougouPropValueList = new ArrayList<TaobaoItemExtendYougouPropValue>();
		String taobaoPid = "";
		String taobaoVid = "";
		long numIid = taobaoItem.getNumIid();
		String merchantCode = taobaoItem.getMerchantCode();

		for (TaobaoYougouItemPropValue value : propValueList) {
			taobaoPid = String.valueOf(value.getTaobaoPid());
			taobaoVid = String.valueOf(value.getTaobaoVid());
			List<String> valueList = itmePropMap.get(taobaoPid);
			if (null != valueList) {
				for (String vid_ : valueList) {
					if (vid_.equals(taobaoVid)) {
						System.out.println("taobaoPId" + taobaoPid);
						TaobaoItemExtendYougouPropValue yougouPropValue = new TaobaoItemExtendYougouPropValue();
						yougouPropValue.setId(UUIDGenerator.getUUID());
						yougouPropValue.setNumIid(numIid);
						yougouPropValue.setMerchantCode(merchantCode);
						yougouPropValue.setYougouPropItemNo(value.getYougouPropItemNo());
						yougouPropValue.setYougouPropValueNo(value.getYougouPropValueNo());
						yougouPropValue.setExtendId(taobaoItem.getExtendId());
						yougouPropValueList.add(yougouPropValue);
					}
				}
			}
		}

		if (yougouPropValueList.size() > 0) {
			taobaoItemExtendPropMapper.insertTaobaoItemYougouPropValueBatch(yougouPropValueList);
		}

		return yougouPropValueList;
	}

	private void setYear(Map<String, Object> result) {
		int curYear = Calendar.getInstance().get(Calendar.YEAR);
		int startYear = curYear - CommodityConstant.ADD_OR_UPDATE_COMMODITY_YEAR_GROUND;
		int endYear = startYear + CommodityConstant.ADD_OR_UPDATE_COMMODITY_YEAR_LENGTH - 1;
		int[] yearArr = new int[CommodityConstant.ADD_OR_UPDATE_COMMODITY_YEAR_LENGTH];
		for (int i = startYear, j = 0; i <= endYear; i++, j++) {
			yearArr[j] = i;
		}
		result.put("curYear", curYear);
		result.put("yearArr", yearArr);
	}

	/**
	 * 淘宝导入-修改商品-保存
	 */
	@Transactional
	@Override
	public List<ErrorVo> updateTaobaoItem(Map<String, Object> params, String merchantCode, HttpServletRequest request)
			throws BusinessException {

		List<ErrorVo> errorList = new ArrayList<ErrorVo>();
		// 判断基本信息是否完整
		String numIidStr = String.valueOf(params.get("numIid"));
		String extendId = String.valueOf(params.get("extendId"));
		logger.info("[淘宝导入]商家编码:{}-修改商品!,numIid:{} extendId:{}", merchantCode, numIidStr, extendId);
		if (isEmpty4String(numIidStr) || isEmpty4String(extendId)) {
			throw new BusinessException("参数错误!");
		}
		TaobaoItemExtend taobaoItemExtend = taobaoItemExtendMapper.getTaobaoItemExtendByExtendId(extendId);
		if (null == taobaoItemExtend) {
			throw new BusinessException("参数错误!");
		}
		long numIid = Long.valueOf(numIidStr);
		// 品牌
		String brandNo = String.valueOf(params.get("brandNo"));
		if (isEmpty4String(brandNo)) {
			throw new BusinessException("请选择品牌!");
		}
		String brandName = String.valueOf(params.get("brandName"));
		if (isEmpty4String(brandName)) {
			throw new BusinessException("请选择品牌!");
		}
		// 分类
		String catNoFull = String.valueOf(params.get("threeCategory"));
		if (isEmpty4String(catNoFull)) {
			throw new BusinessException("请选择分类!");
		}
		String cateNo = catNoFull.split(";")[2];
		// 商品名称
		String title = String.valueOf(params.get("title"));
		if (isEmpty4String(title)) {
			ErrorVo errorVo = new ErrorVo("title", "请输入商品名称");
			errorList.add(errorVo);
		} else {
			title = StringUtils.chomp(title.replace("\"", "").replace("\n", "").replace("\\", "").replaceAll("　", "")
					.trim());
		}
		// 商品款号
		String yougouStyleNo = String.valueOf(params.get("yougouStyleNo"));
		if (isEmpty4String(yougouStyleNo)) {
			ErrorVo errorVo = new ErrorVo("yougouStyleNo", "请输入商品款号");
			errorList.add(errorVo);
		}

		// 商家款色编码
		String yougouSupplierCode = String.valueOf(params.get("yougouSupplierCode"));
		if (isEmpty4String(yougouSupplierCode)) {
			ErrorVo errorVo = new ErrorVo("yougouSupplierCode", "请输入商家款色编码");
			errorList.add(errorVo);
		}

		// 优购价格
		String yougouPrice = String.valueOf(params.get("yougouPrice"));
		if (isEmpty4String(yougouStyleNo)) {
			ErrorVo errorVo = new ErrorVo("yougouPrice", "请输入优购价格");
			errorList.add(errorVo);
		}

		// 淘宝价
		String price = String.valueOf(params.get("price"));
		// 年份
		String yougouYears = String.valueOf(params.get("yougouYears"));
		if (isEmpty4String(yougouStyleNo)) {
			ErrorVo errorVo = new ErrorVo("yougouYears", "请输入年份");
			errorList.add(errorVo);
		}
		// 颜色
		String yougouSpecName = String.valueOf(params.get("yougouSpecName"));
		if (isEmpty4String(yougouSpecName)) {
			yougouSpecName = String.valueOf(params.get("specName"));
		}

		if (isEmpty4String(yougouSpecName)) {
			errorList.add(new ErrorVo("specName", "请输入颜色"));
		}
		// 描述图
		String prodDesc = String.valueOf(params.get("prodDesc"));

		// 查询分类下的所有属性
		Category category = commodityBaseApiService.getCategoryByNo(cateNo);
		if (null == category) {
			throw new BusinessException("商品分类不存在!");
		}
		List<PropItem> yougouPropList = merchantCommodityService.getPropMsgByCatIdNew(category.getId(), false);

		List<TaobaoItemExtendYougouPropValue> propValuesList = new ArrayList<TaobaoItemExtendYougouPropValue>();
		Integer isShowMall = null;
		for (PropItem item : yougouPropList) {
			isShowMall = item.getIsShowMall();
			// 单选
			String selectValue = String.valueOf(params.get(TaobaoImportConstants.GOODS_PROP_SELECT
					+ item.getPropItemNo()));

			// 多选
			String[] checkBoxValue = request.getParameterValues(TaobaoImportConstants.GOODS_PROP_CHECKBOX
					+ item.getPropItemNo());
			if (null != isShowMall && isShowMall.intValue() == 0) {// 必须填的属性
				if (isEmpty4String(selectValue) && null == checkBoxValue) {
					errorList.add(new ErrorVo(item.getPropItemNo(), "请选择" + item.getPropItemName()));
				}
			}

			if (!isEmpty4String(selectValue)) {
				TaobaoItemExtendYougouPropValue value = new TaobaoItemExtendYougouPropValue();
				value.setId(UUIDGenerator.getUUID());
				value.setNumIid(numIid);
				value.setExtendId(extendId);
				value.setMerchantCode(merchantCode);
				value.setYougouPropItemNo(item.getPropItemNo());
				value.setYougouPropItemName(item.getPropItemName());
				value.setYougouPropValueNo(selectValue.split(";")[0]);
				value.setYougouPropValueName(selectValue.split(";")[1]);
				propValuesList.add(value);
			}

			if (null != checkBoxValue) {
				for (String propValue : checkBoxValue) {
					TaobaoItemExtendYougouPropValue value = new TaobaoItemExtendYougouPropValue();
					value.setId(UUIDGenerator.getUUID());
					value.setExtendId(extendId);
					value.setNumIid(numIid);
					value.setMerchantCode(merchantCode);
					value.setYougouPropItemNo(item.getPropItemNo());
					value.setYougouPropItemName(item.getPropItemName());
					value.setYougouPropValueNo(propValue.split(";")[0]);// 多选value值存的是编码加名称，取编码
					value.setYougouPropValueName(propValue.split(";")[1]);//
					propValuesList.add(value);
				}
			}
		}

		List<TaobaoItemExtendYougouPropValueSize> propSizeList = new ArrayList<TaobaoItemExtendYougouPropValueSize>();

		// 插入尺码信息
		List<PropValue> sizeList = commodityApi.getSizeByCatId(category.getId());

		String sizeNo = null;
		List<String> sizes = new ArrayList<String>();
		int lineCount = 0;
		if (CollectionUtils.isNotEmpty(sizeList)) {
			for (PropValue val : sizeList) {
				sizeNo = val.getPropValueNo();
				String size = String.valueOf(params.get(TaobaoImportConstants.SIZE + sizeNo));
				String stock = String.valueOf(params.get(TaobaoImportConstants.GOODS_STOCK + sizeNo));
				String barcode = String.valueOf(params.get(TaobaoImportConstants.GOODS_THIRDPARTYCODE + sizeNo));
				if (!isEmpty4String(size)) {
					sizes.add(size);
					lineCount++;
					if (isEmpty4String(barcode)) {
						errorList.add(new ErrorVo(size, "请输入第" + lineCount + "行的商品条码"));
					} else {
						TaobaoItemExtendYougouPropValueSize valueSize = new TaobaoItemExtendYougouPropValueSize();
						valueSize.setId(UUIDGenerator.getUUID());
						valueSize.setNumIid(numIid);
						valueSize.setMerchantCode(merchantCode);
						valueSize.setBarcode(barcode);
						valueSize.setYougouPropValueNo(sizeNo);
						valueSize.setYougouPropValueName(val.getPropValueName());
						valueSize.setStock(isEmpty4String(stock) ? 0 : Integer.parseInt(stock));
						valueSize.setExtendId(extendId);
						propSizeList.add(valueSize);
					}
				}
			}
		}
		if (sizes.isEmpty()) {
			errorList.add(new ErrorVo("sizeNo", "请选择尺码"));
		}

		// 角度图
		String defaultPic = "";
		List<TaobaoItemImg> imageList = new ArrayList<TaobaoItemImg>();
		String preImageStr = request.getParameter("preImage");
		if (StringUtils.isNotEmpty(preImageStr)) {

			String[] imageArry = preImageStr.split(",");
			for (int i = 0, len = imageArry.length; i < len; i++) {
				String thumbnail = imageArry[i].replaceAll("(?i)\\.jpg", ".png");
				if (i == 0) {
					defaultPic = thumbnail;
				}
				TaobaoItemImg img = new TaobaoItemImg();
				img.setImgId(UUIDGenerator.getUUID());
				img.setNumIid(numIid);
				img.setId(0l);
				img.setUrl("");
				img.setPosition(Long.valueOf(i));
				img.setYougouUrl(imageArry[i]);
				img.setYougouUrlThumbnail(thumbnail);
				img.setExtendId(extendId);
				imageList.add(img);
			}

		}
		if (!errorList.isEmpty()) {
			return errorList;
		}
		// 更新淘宝商品
		taobaoItemExtend.setYougouBrandNo(brandNo);
		taobaoItemExtend.setYougouBrandName(brandName);
		taobaoItemExtend.setYougouCateNo(catNoFull);
		taobaoItemExtend.setTitle(title);
		taobaoItemExtend.setYougouStyleNo(yougouStyleNo);
		taobaoItemExtend.setYougouSupplierCode(yougouSupplierCode);
		taobaoItemExtend.setYougouPrice(Double.valueOf(yougouPrice));
		taobaoItemExtend.setPrice(price);
		taobaoItemExtend.setYougouSpecName(yougouSpecName);
		taobaoItemExtend.setYougouYears(yougouYears);
		taobaoItemExtend.setCheckStatus(String.valueOf(params.get("checkStatus")));
		taobaoItemExtend.setYougouDescription(prodDesc);
		taobaoItemExtend.setDefaultPic(defaultPic);
		taobaoItemMapper.updateByPrimaryKeySelective(taobaoItemExtend);
		taobaoItemExtendMapper.updateByPrimaryKeySelective(taobaoItemExtend);
		// 删除淘宝属性值
		taobaoItemExtendPropMapper.deleteTaobaoItemYougouPropValueByExtendId(numIid, extendId, merchantCode);
		// 插入淘宝属性
		if (!propValuesList.isEmpty()) {
			taobaoItemExtendPropMapper.insertTaobaoItemYougouPropValueBatch(propValuesList);
		}
		// 删除尺码
		taobaoItemExtendPropMapper.deleteTaobaoItemYougouPropValueSizeByExtendId(extendId, merchantCode);

		// 插入尺寸
		if (!propSizeList.isEmpty()) {
			taobaoItemExtendPropMapper.insertTaobaoItemYougouPropValueSizeBatch(propSizeList);
		}

		// 保存角度图
		if (!imageList.isEmpty()) {
			// 删除角度图
			taobaoItemImgMapper.deleteTaobaoItemImgByExtendId(extendId);
			taobaoItemImgMapper.insertTaobaoItemImgList(imageList);
		}

		return errorList;
	}

	/**
	 * 如果str为null，空字符串，null，返回true，否则返回false
	 * 
	 * @param str
	 * @return
	 */
	private boolean isEmpty4String(String str) {
		if (null == str) {
			return true;
		}
		if ("".equals(str.trim())) {
			return true;
		}
		if ("null".equals(str.trim())) {
			return true;
		}
		return false;
	}

	public XSSFWorkbook getItemBook(String itemIds, String merchantCode) throws BusinessException {
		if (StringUtils.isEmpty(itemIds) || StringUtils.isEmpty(merchantCode)) {
			throw new BusinessException("参数错误!");
		}
		String[] itemIdsArray = itemIds.split(",");

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet(WRITE_SHEET_NAME);
		sheet.setColumnWidth(0, 3000);
		sheet.setColumnWidth(1, 6000);
		sheet.setColumnWidth(2, 4500);
		sheet.setColumnWidth(3, 4500);
		sheet.setColumnWidth(4, 3000);
		sheet.setColumnWidth(5, 3000);
		sheet.setColumnWidth(6, 3000);
		int rowInd = 0, colInd = 0;
		XSSFRow row = sheet.createRow(rowInd++);
		row.createCell(colInd++).setCellValue("商品Id");
		row.createCell(colInd++).setCellValue("商品名称");
		row.createCell(colInd++).setCellValue("款号");
		row.createCell(colInd++).setCellValue("款色编码");
		row.createCell(colInd++).setCellValue("颜色");
		row.createCell(colInd++).setCellValue("淘宝价");
		row.createCell(colInd++).setCellValue("优购价");
		// row.createCell(colInd++).setCellValue("货品条码");
		// row.createCell(colInd++).setCellValue("尺码");
		// 生成一个样式
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		int count = itemIdsArray.length;
		int size = 500;
		int loopNum = count / size;
		List<String> ids = Arrays.asList(itemIdsArray);
		int start = 0;
		for (int i = 0; i < loopNum; i++) {
			List<String> resultIds = ids.subList(start, start + size);
			start = start + size;
			List<TaobaoItemExtendDto> list = this.taobaoItemExtendMapper.getTaobaoItemExtendByIds(merchantCode,
					resultIds);
			rowInd = write2Row(sheet, list, rowInd, style);
		}
		if (count % size != 0) {
			List<String> resultIds = ids.subList(start, ids.size());
			List<TaobaoItemExtendDto> list = this.taobaoItemExtendMapper.getTaobaoItemExtendByIds(merchantCode,
					resultIds);
			rowInd = write2Row(sheet, list, rowInd, style);
		}
		return workbook;
	}

	private int write2Row(XSSFSheet sheet, List<TaobaoItemExtendDto> list, int rowInd, XSSFCellStyle style) {
		Map<String, Object> sizePrams = new HashMap<String, Object>();
		for (TaobaoItemExtendDto dto : list) {
			int colInd = 0;
			XSSFRow row = sheet.createRow(rowInd++);
			Cell cellExtendId = row.createCell(colInd++);
			cellExtendId.setCellValue(ObjectUtils.toString(dto.getExtendId()));
			cellExtendId.setCellStyle(style);
			Cell cellTitle = row.createCell(colInd++);
			cellTitle.setCellValue(ObjectUtils.toString(dto.getTitle()));
			cellTitle.setCellStyle(style);
			Cell cellYougouStyleNo = row.createCell(colInd++);
			cellYougouStyleNo.setCellValue(ObjectUtils.toString(dto.getYougouStyleNo()));
			cellYougouStyleNo.setCellStyle(style);
			row.createCell(colInd++).setCellValue(ObjectUtils.toString(dto.getYougouSupplierCode()));
			Cell cellYougouSpecName = row.createCell(colInd++);
			cellYougouSpecName.setCellValue(ObjectUtils.toString(dto.getYougouSpecName()));
			cellYougouSpecName.setCellStyle(style);
			Cell cellPrice = row.createCell(colInd++);
			cellPrice.setCellValue(ObjectUtils.toString(dto.getPrice()));
			cellPrice.setCellStyle(style);
			// row.createCell(colInd++).setCellValue(ObjectUtils.toString(dto.getTitle()));
			// row.createCell(colInd++).setCellValue(ObjectUtils.toString(dto.getYougouStyleNo()));
			// row.createCell(colInd++).setCellValue(ObjectUtils.toString(dto.getYougouSpecName()));
			// row.createCell(colInd++).setCellValue(ObjectUtils.toString(dto.getPrice()));
			row.createCell(colInd++).setCellValue(ObjectUtils.toString(dto.getYougouPrice()));
			// 查询商品尺码信息
			sizePrams.put("extendId", dto.getExtendId());
			sizePrams.put("merchantCode", dto.getMerchantCode());
		}
		return rowInd;
	}

	/**
	 * 导入修改淘宝商品，只能修改商家款色编码和优购价
	 */
	public Map<String, Object> exportExcel(File file, String merchantCode) throws BusinessException {
		Map<String, Object> result = new HashMap<String, Object>();
		InputStream in = null;
		XSSFWorkbook workbook = null;
		int successCount = 0;
		int errorCount = 0;
		try {
			in = new FileInputStream(file);
			workbook = new XSSFWorkbook(in);
		} catch (Exception e) {
			logger.error("[淘宝导入]商家编码:{}-获取导入文件出现异常", merchantCode, e);
			throw new BusinessException("获取导入文件出现异常，请重试。");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("输入流关闭异常", e);
				}
			}
		}
		// 创建错误样式
		XSSFCellStyle wrongCellStyle = workbook.createCellStyle();
		wrongCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
		wrongCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		// 默认样式
		XSSFCellStyle defaultCellStyle = workbook.createCellStyle();
		XSSFSheet writeSheet = workbook.getSheet(WRITE_SHEET_NAME);
		if (writeSheet == null) {
			logger.info("[淘宝导入]商家编码:{}-导入淘宝商品资料错误：文件不存在, 工作表名称:{}", merchantCode, WRITE_SHEET_NAME);
			throw new BusinessException("导入淘宝商品资料错误：文件不存在" + WRITE_SHEET_NAME);
		}
		boolean cellOk = true;
		for (int i = 1; i <= writeSheet.getLastRowNum(); i++) {
			StringBuilder erroMsg = new StringBuilder();
			cellOk = true;
			XSSFRow row = writeSheet.getRow(i);
			TaobaoItemExtendDto dto = new TaobaoItemExtendDto();
			Cell extendCell = row.getCell(0);
			if (extendCell == null) {
				logger.error("extendCell 遍历数据为空，writeSheet.getLastRowNum() 数组下标为i=" + i);
				continue;
			}
			Cell yougouSupplierCodeCell = row.getCell(3);
			Cell yougouPriceCell = row.getCell(6);
			extendCell.setCellStyle(defaultCellStyle);
			extendCell.removeCellComment();
			String extendId = String.valueOf(GetValueTypeForXLSX(extendCell));
			String yougouPrice = String.valueOf(GetValueTypeForXLSX(yougouPriceCell));
			String yougouSupplierCode = "";

			if (null != yougouSupplierCodeCell) {
				yougouSupplierCodeCell.setCellStyle(defaultCellStyle);
				yougouSupplierCodeCell.removeCellComment();
				if (yougouSupplierCodeCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					yougouSupplierCode = String.valueOf(String.valueOf(new DecimalFormat("#")
							.format(yougouSupplierCodeCell.getNumericCellValue())));
				} else if (yougouSupplierCodeCell.getCellType() == Cell.CELL_TYPE_STRING) {
					yougouSupplierCode = yougouSupplierCodeCell.getStringCellValue();
				}

				Comment commentSupplierCode = yougouSupplierCodeCell.getCellComment();
				if (StringUtils.isEmpty(yougouSupplierCode)) {
					cellOk = false;
					yougouSupplierCodeCell.setCellStyle(wrongCellStyle);
					if (null == commentSupplierCode) {
						yougouSupplierCodeCell.setCellComment(this.createComment(writeSheet.createDrawingPatriarch(),
								yougouSupplierCodeCell.getColumnIndex(), yougouSupplierCodeCell.getRowIndex(),
								yougouSupplierCodeCell.getColumnIndex() + 3, yougouSupplierCodeCell.getRowIndex() + 3,
								"款色编码不能为空"));
					} else {
						commentSupplierCode
								.setString(writeSheet.createDrawingPatriarch() instanceof XSSFDrawing ? new XSSFRichTextString(
										"款色编码不能为空") : new HSSFRichTextString("款色编码不能为空"));
					}
				} else if (!TaobaoImportUtils.checkYougouSupplierCode(yougouSupplierCode)) {
					cellOk = false;
					yougouSupplierCodeCell.setCellStyle(wrongCellStyle);
					if (null == commentSupplierCode) {
						yougouSupplierCodeCell.setCellComment(this.createComment(writeSheet.createDrawingPatriarch(),
								yougouSupplierCodeCell.getColumnIndex(), yougouSupplierCodeCell.getRowIndex(),
								yougouSupplierCodeCell.getColumnIndex() + 3, yougouSupplierCodeCell.getRowIndex() + 3,
								"款色编码只能是数字、字母、横线(-)、下划线"));
					} else {
						commentSupplierCode
								.setString(writeSheet.createDrawingPatriarch() instanceof XSSFDrawing ? new XSSFRichTextString(
										"款色编码只能是数字、字母、横线(-)、下划线") : new HSSFRichTextString("款色编码只能是数字、字母、横线(-)、下划线"));
					}
				}
			} else {
				erroMsg.append("款色编码不能为空");
			}
			if (null != yougouPriceCell) {
				yougouPriceCell.setCellStyle(defaultCellStyle);
				yougouPriceCell.removeCellComment();
			} else {
				erroMsg.append(",优购价不能为空");
			}
			Comment comment = extendCell.getCellComment();
			if (!StringUtils.isEmpty(erroMsg.toString())) {
				cellOk = false;
				extendCell.setCellStyle(wrongCellStyle);
				if (comment == null) {
					extendCell.setCellComment(this.createComment(writeSheet.createDrawingPatriarch(),
							extendCell.getColumnIndex(), extendCell.getRowIndex(), extendCell.getColumnIndex() + 3,
							extendCell.getRowIndex() + 3, erroMsg.toString()));
				} else {
					comment.setString(writeSheet.createDrawingPatriarch() instanceof XSSFDrawing ? new XSSFRichTextString(
							erroMsg.toString()) : new HSSFRichTextString(erroMsg.toString()));
				}
			}

			if (StringUtils.isEmpty(extendId)) {
				cellOk = false;
				extendCell.setCellStyle(wrongCellStyle);
			}

			if (null != yougouPriceCell && StringUtils.isEmpty(yougouPrice)) {
				cellOk = false;
				yougouPriceCell.setCellStyle(wrongCellStyle);
				yougouPriceCell.setCellComment(this.createComment(writeSheet.createDrawingPatriarch(),
						yougouPriceCell.getColumnIndex(), yougouPriceCell.getRowIndex(),
						yougouPriceCell.getColumnIndex() + 3, yougouPriceCell.getRowIndex() + 3, "优购价格不能为空"));

			} else if (null != yougouSupplierCodeCell && !TaobaoImportUtils.checkYougouPrice(yougouPrice)
					|| Float.valueOf(yougouPrice) < 0) {
				cellOk = false;
				yougouPriceCell.setCellStyle(wrongCellStyle);
				yougouPriceCell.setCellComment(this.createComment(writeSheet.createDrawingPatriarch(),
						yougouPriceCell.getColumnIndex(), yougouPriceCell.getRowIndex(),
						yougouPriceCell.getColumnIndex() + 3, yougouPriceCell.getRowIndex() + 3, "优购价格必须是数字，并且大于0"));
			}
			if (cellOk) {
				dto.setMerchantCode(merchantCode);
				dto.setExtendId(extendId);
				dto.setYougouSupplierCode(yougouSupplierCode);
				dto.setYougouPrice(Double.valueOf(yougouPrice));
				int count = this.taobaoItemExtendMapper.updateByPrimaryKeySelective(dto);
				if (count > 0) {
					successCount++;
					// 删除正确的行
					writeSheet.removeRow(writeSheet.getRow(i));
					if (i < writeSheet.getLastRowNum()) {
						writeSheet.shiftRows(i + 1, writeSheet.getLastRowNum(), -1);
						i = i - 1;
					}
				} else {
					errorCount++;
				}
			} else {
				errorCount++;
			}

		}
		OutputStream out = null;
		try {
			out = new FileOutputStream(file);
			workbook.write(out);
		} catch (IOException e) {
			logger.error("[淘宝导入]商家编码:{}-输出异常文件出现异常", merchantCode, e);
			throw new BusinessException("输出异常文件出现异常，请重试。");
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				logger.error("关闭输出流出现异常", e);
			}
		}

		result.put("successCount", successCount);
		result.put("errorCount", errorCount);
		return result;
	}

	class ExcelObj {
		private String value;
		private Comment comment;
		private CellStyle style;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public Comment getComment() {
			return comment;
		}

		public void setComment(Comment comment) {
			this.comment = comment;
		}

		public CellStyle getStyle() {
			return style;
		}

		public void setStyle(CellStyle style) {
			this.style = style;
		}
	}

	

	private Comment createComment(Drawing drawing, int col1, int row1, int col2, int row2, String text) {
		ClientAnchor clientAnchor = drawing.createAnchor(0, 0, 0, 0, col1, row1, col2, row2);
		Comment comment = drawing.createCellComment(clientAnchor);
		comment.setString(drawing instanceof XSSFDrawing ? new XSSFRichTextString(text) : new HSSFRichTextString(text));
		comment.setAuthor("Yougou");
		return comment;
	}

	private static Object GetValueTypeForXLSX(Cell cell) {
		if (cell == null)
			return null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK: // BLANK:
			return null;
		case Cell.CELL_TYPE_BOOLEAN: // BOOLEAN:
			return cell.getBooleanCellValue();
		case Cell.CELL_TYPE_NUMERIC: // NUMERIC:
			return cell.getNumericCellValue();
		case Cell.CELL_TYPE_STRING: // STRING:
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_ERROR: // ERROR:
			return cell.getErrorCellValue();
		case Cell.CELL_TYPE_FORMULA: // FORMULA:
		default:
			return cell.getCellFormula();
		}
	}

	@Override
	public synchronized TaobaoItemProp getTaobaoItemProp(long pid) throws BusinessException {
		Object prop = redisTemplate.opsForHash().get("merchant.taobao.prop", String.valueOf(pid));
		if (prop == null) {
			TaobaoItemProp taobaoItemProp = taobaoItemPropMapper.getTaobaoItemProp(pid);
			redisTemplate.opsForHash().put("merchant.taobao.prop", String.valueOf(pid), taobaoItemProp);
			return taobaoItemProp;
		} else {
			return (TaobaoItemProp) prop;
		}
	}

	@Override
	public synchronized TaobaoItemPropValue getTaobaoItemPropValue(long vid) throws BusinessException {
		Object propValue = redisTemplate.opsForHash().get("merchant.taobao.propValue", String.valueOf(vid));
		if (propValue == null) {
			TaobaoItemPropValue taobaoItemPropValue = taobaoItemPropValueMapper.getTaobaoItemPropValueByVid(vid);
			redisTemplate.opsForHash().put("merchant.taobao.propValue", String.valueOf(vid), taobaoItemPropValue);
			return taobaoItemPropValue;
		} else {
			return (TaobaoItemPropValue) propValue;
		}
	}
}
