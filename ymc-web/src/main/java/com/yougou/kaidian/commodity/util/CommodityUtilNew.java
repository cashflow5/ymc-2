/**
 * 
 */
package com.yougou.kaidian.commodity.util;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yougou.kaidian.commodity.constant.CommodityConstant;
import com.yougou.kaidian.commodity.model.vo.CommodityQueryVo;
import com.yougou.kaidian.commodity.model.vo.CommoditySubmitNewVo;
import com.yougou.kaidian.commodity.model.vo.CommoditySubmitVo;
import com.yougou.kaidian.commodity.model.vo.ErrorVo;
import com.yougou.kaidian.common.constant.SystemConstant;
import com.yougou.kaidian.common.util.CommonUtil;
import com.yougou.kaidian.framework.util.SessionUtil;
import com.yougou.pc.model.commodityinfo.Commodity;
import com.yougou.pc.model.commodityinfo.CommodityProp;
import com.yougou.pc.model.product.Product;
import com.yougou.pc.model.prop.PropValue;
import com.yougou.pc.vo.commodity.CommodityPropVO;
import com.yougou.pc.vo.commodity.CommodityVO;
import com.yougou.pc.vo.commodity.ProductVO;

/**
 * 商品相关工具类（新）
 * 
 * @author huang.tao
 *
 */
public class CommodityUtilNew {
	
	private static final Logger logger = LoggerFactory.getLogger(CommodityUtilNew.class);
	
	/**
	 * 构造CommodityVO
	 * @param submitVo 按尺码设置价格 新增或修改商品 提交时 用的vo
	 * @param colorCount 该款下有多少颜色
	 * @param styleCount 该款该颜色下有多少尺码
	 * @return 
	 */
	public static List<CommodityVO> buildCommodityVOList(CommoditySubmitNewVo submitVo, 
			String supplierCode, Map<String,Object> map) {
		List<CommodityVO> list = new ArrayList<CommodityVO>();
		CommodityVO commodityVO = null;
		//货品信息
		List<ProductVO> productMsgList = null;
		//扩展属性信息
		List<CommodityPropVO> commodityPropVOList = null;
		for (int i = 0, len = submitVo.getSizeNo().length; i < len; i++) {
			commodityVO = new CommodityVO();
			productMsgList = new ArrayList<ProductVO>();
			commodityPropVOList = new ArrayList<CommodityPropVO>();
			//商品名称(必填)
			commodityVO.setCommodityName(StringUtils.chomp(submitVo.getCommodityName()
					.replace("\"", "")
					.replace("\n", "")
					.replace("\\", "")
					.replaceAll("　", "")//中文空格
					.replaceAll("\\s+", " ")//中间多个英文空格，保留一个
					.trim()));
			//供应商款色编码(必填)
			//款色编码自动生成，单品发布为款号0001等序号
			//同款同色  在该同款同色的最大的款色编码+1
			//同款不同色  在该同款的最大的款色编码（前两位）+1
			//map.get("colorcount")>0 	说明此颜色必存在
			String supplierCodeStr = "";
			String maxCode = (String)map.get("maxcode");
			int colorcount = ((Number)map.get("colorcount")).intValue();
			if(colorcount>0){
				//此颜色必存在//后四位流水号+1
				String serialnumber = maxCode.substring(maxCode.length()-4, maxCode.length());
				supplierCodeStr = String.format("%04d", Integer.parseInt(serialnumber)+1+i);
			}else{
				//颜色不存在
				//前两位作为颜色流水线
				String colorserialnumber = "00";
				if(supplierCode!=null){
					String serialnumber = supplierCode.substring(supplierCode.length()-4, supplierCode.length());
					colorserialnumber = String.format("%02d",Integer.parseInt(serialnumber.substring(0, 2))+1);
				}
				supplierCodeStr = String.format("%s", colorserialnumber+String.format("%02d",i+1));
			}
			commodityVO.setSupplierCode(submitVo.getBrandNo()+submitVo.getStyleNo()+supplierCodeStr);
			//颜色(必填)
			commodityVO.setSpecName(submitVo.getSizeName()[i]+"_"+submitVo.getSpecName());
			//颜色编号(必填)
			commodityVO.setSpecNo(submitVo.getSpecNo());
			//别名 ?_?
			commodityVO.setAlias(null);
			//分类id(必填)
			commodityVO.setCatId(submitVo.getCatId());
			//分类编号(必填)
			commodityVO.setCatNo(submitVo.getCatNo());
			//分类结构名(必填)
			commodityVO.setCatStructname(submitVo.getCatStructName());
			//分类名称(必填)
			commodityVO.setCatName(submitVo.getCatName());
			//品牌编号(必填)
			commodityVO.setBrandNo(submitVo.getBrandNo());
			//品牌SpeelingName 和ftp图片路径有关系 ?_?
			commodityVO.setBrandSpeelingName(null);
			//品牌名称(必填)
			commodityVO.setBrandName(submitVo.getBrandName());
			//市场价(必填) 大于0
			commodityVO.setPublicPrice(Double.parseDouble(submitVo.getPublicPriceBySize()[i]));
			//销售价格(必填) 大于0
			commodityVO.setSalePrice(Double.parseDouble(submitVo.getSalePriceBySize()[i]));
			//成本价(必填) 大于0 ?_?...
			//commodityVO.setCostPrice(submitVo.getSalePrice());
			//成本价2 (必填) ?_?...
			//commodityVO.setCostPrice2(submitVo.getSalePrice());
			//商品年份
			commodityVO.setYear(submitVo.getYears());
			//款号 (必填)
			commodityVO.setStyleNo(submitVo.getStyleNo());
			//更新人 
			commodityVO.setUpdatePerson(submitVo.getSupplier());
			//商家编码 
			commodityVO.setMerchantCode(submitVo.getMerchantCode());
			//配送方 0：优购；1：商家
			//commodityVO.setOrderDistributionSide(submitVo.getOrderDistributionSide());
			// 买点
			commodityVO.setSellingPoint(StringUtils.chomp(submitVo.getSellingPoint()
					.replace("\"", "")
					.replace("\n", "")
					.replace("\\", "")
					.replaceAll("　", "")
					.replaceAll("\\s+", " ")//中间多个英文空格，保留一个
					.trim()));
			
			//货品信息
			ProductVO productVO = new ProductVO();
			//条形码 ?_?
			productVO.setInsideCode(submitVo.getThirdPartyCode()[i]);
			//供应商条码
			productVO.setThirdPartyCode(submitVo.getThirdPartyCode()[i]);
			//尺码
			productVO.setSizeName(submitVo.getSizeName()[i]);
			//尺码编号
			productVO.setSizeNo(submitVo.getSizeNo()[i]);
			// 重量(xls)
			productVO.setWeight(submitVo.getWeight()[i]);
			//货品可编辑状态 ?_?
			productVO.setEditStatus(null);
			//货品排序 ?_?...
			productVO.setSortNo(1);
			//货品销售状态 ?_?
			productVO.setSellStatus(null);
			//抽样状态 ?_?
			productVO.setSampleSet(null);
			//库存信息 ?_?...
			productVO.setStock(submitVo.getStockInt()[i]);
			
			productMsgList.add(productVO);
			
			commodityVO.setProductMsgList(productMsgList);
			
			//扩展属性信息
			if (submitVo.getPropItemNo() != null && submitVo.getPropItemNo().length != 0) {
				CommodityPropVO commodityPropVO = null;
				for (int m = 0, lenm = submitVo.getPropItemNo().length; m < lenm; m++) {
					//兼容属性值多选情况
					String[] propValueNos = submitVo.getPropValueNo()[m].split(";");
					String[] propValueNames = submitVo.getPropValueName()[m].split(";");
					for (int j = 0; j < propValueNos.length; j++) {
						if (StringUtils.isNotBlank(propValueNos[j])) {
							commodityPropVO = new CommodityPropVO();
							commodityPropVO.setPropItemNo(submitVo.getPropItemNo()[m]);
							commodityPropVO.setPropItemName(submitVo.getPropItemName()[m]);
							commodityPropVO.setPropValueNo(propValueNos[j]);
							commodityPropVO.setPropValue(propValueNames[j]);
							commodityPropVOList.add(commodityPropVO);
						}
					}
				}
			}
			
			commodityVO.setCommodityPropVOList(commodityPropVOList);
			
			//尺码表id ?_?
			commodityVO.setSizeChartId(null);
			//宝贝描述
			commodityVO.setProdDesc(submitVo.getProdDesc());
			//商品的销售状态 （上架下架等状态，导入时不需要填）
			commodityVO.setSellStatus(null);
			//是否抽样 0是不需要抽样，1是需要抽样  ?_?
			commodityVO.setSampleFlag(null);
			//是否供应商提供图片 0是不提供，1是提供  sampleFlag和suppliersPicFlag互斥，两个参数必有且只能有一个值为1，具体逻辑背景可以找产品了解  ?_?
			commodityVO.setSuppliersPicFlag(null);
			//是否已经上传图片 ?_?
			commodityVO.setPicFlag(null);
			//商品默认图片地址 ?_?
			commodityVO.setDefaultPic(null);
			
			list.add(commodityVO);
		}
		return list;
	}
	
	/**
	 * 构造CommodityVO
	 * @param submitVo 新增或修改商品 提交时 用的vo
	 * @return 
	 */
	public static CommodityVO buildCommodityVO(CommoditySubmitNewVo submitVo) {
		
		CommodityVO commodityVO = new CommodityVO();
		//商品名称(必填)
		commodityVO.setCommodityName(StringUtils.chomp(submitVo.getCommodityName()
				.replace("\"", "")
				.replace("\n", "")
				.replace("\\", "")
				.replaceAll("　", "")//中文空格
				.replaceAll("\\s+", " ")//中间多个英文空格，保留一个
				.trim()));
		//供应商款色编码(必填)
		commodityVO.setSupplierCode(submitVo.getSupplierCode());
		//颜色(必填)
		commodityVO.setSpecName(submitVo.getSpecName());
		//颜色编号(必填)
		commodityVO.setSpecNo(submitVo.getSpecNo());
		//别名 ?_?
		commodityVO.setAlias(null);
		//分类id(必填)
		commodityVO.setCatId(submitVo.getCatId());
		//分类编号(必填)
		commodityVO.setCatNo(submitVo.getCatNo());
		//分类结构名(必填)
		commodityVO.setCatStructname(submitVo.getCatStructName());
		//分类名称(必填)
		commodityVO.setCatName(submitVo.getCatName());
		//品牌编号(必填)
		commodityVO.setBrandNo(submitVo.getBrandNo());
		//品牌SpeelingName 和ftp图片路径有关系 ?_?
		commodityVO.setBrandSpeelingName(null);
		//品牌名称(必填)
		commodityVO.setBrandName(submitVo.getBrandName());
		//市场价(必填) 大于0
		commodityVO.setPublicPrice(submitVo.getPublicPrice());
		//销售价格(必填) 大于0
		commodityVO.setSalePrice(submitVo.getSalePrice());
		//成本价(必填) 大于0 ?_?...
		//commodityVO.setCostPrice(submitVo.getSalePrice());
		//成本价2 (必填) ?_?...
		//commodityVO.setCostPrice2(submitVo.getSalePrice());
		//商品年份
		commodityVO.setYear(submitVo.getYears());
		//款号 (必填)
		commodityVO.setStyleNo(submitVo.getStyleNo());
		//更新人 
		commodityVO.setUpdatePerson(submitVo.getSupplier());
		//商家编码 
		commodityVO.setMerchantCode(submitVo.getMerchantCode());
		//配送方 0：优购；1：商家
		//commodityVO.setOrderDistributionSide(submitVo.getOrderDistributionSide());
		// 买点
		commodityVO.setSellingPoint(StringUtils.chomp(submitVo.getSellingPoint()
				.replace("\"", "")
				.replace("\n", "")
				.replace("\\", "")
				.replaceAll("　", "")//中文空格
				.replaceAll("\\s+", " ")//中间多个英文空格，保留一个
				.trim()));
		//货品信息
		List<ProductVO> productMsgList = new ArrayList<ProductVO>();
		ProductVO productVO = null;
		for (int i = 0, len = submitVo.getSizeNo().length; i < len; i++) {
			productVO = new ProductVO();
			
			//条形码 ?_?
			productVO.setInsideCode(submitVo.getThirdPartyCode()[i]);
			//供应商条码
			productVO.setThirdPartyCode(submitVo.getThirdPartyCode()[i]);
			//尺码
			productVO.setSizeName(submitVo.getSizeName()[i]);
			//尺码编号
			productVO.setSizeNo(submitVo.getSizeNo()[i]);
			// 重量(xls)
			productVO.setWeight(submitVo.getWeight()[i]);
			//货品可编辑状态 ?_?
			productVO.setEditStatus(null);
			//货品排序 ?_?...
			productVO.setSortNo(1);
			//货品销售状态 ?_?
			productVO.setSellStatus(null);
			//抽样状态 ?_?
			productVO.setSampleSet(null);
			//库存信息 ?_?...
			productVO.setStock(submitVo.getStockInt()[i]);
			
			productMsgList.add(productVO);
		}

		commodityVO.setProductMsgList(productMsgList);
		
		//扩展属性信息
		List<CommodityPropVO> commodityPropVOList = new ArrayList<CommodityPropVO>();
		if (submitVo.getPropItemNo() != null && submitVo.getPropItemNo().length != 0) {
			CommodityPropVO commodityPropVO = null;
			for (int i = 0, len = submitVo.getPropItemNo().length; i < len; i++) {
				//兼容属性值多选情况
				String[] propValueNos = submitVo.getPropValueNo()[i].split(";");
				String[] propValueNames = submitVo.getPropValueName()[i].split(";");
				for (int j = 0; j < propValueNos.length; j++) {
					if (StringUtils.isNotBlank(propValueNos[j])) {
						commodityPropVO = new CommodityPropVO();
						commodityPropVO.setPropItemNo(submitVo.getPropItemNo()[i]);
						commodityPropVO.setPropItemName(submitVo.getPropItemName()[i]);
						commodityPropVO.setPropValueNo(propValueNos[j]);
						commodityPropVO.setPropValue(propValueNames[j]);
						commodityPropVOList.add(commodityPropVO);
					}
				}
			}
		}
		commodityVO.setCommodityPropVOList(commodityPropVOList);
		
		//尺码表id ?_?
		commodityVO.setSizeChartId(null);
		//宝贝描述
		commodityVO.setProdDesc(submitVo.getProdDesc());
		//商品的销售状态 （上架下架等状态，导入时不需要填）
		commodityVO.setSellStatus(null);
		//是否抽样 0是不需要抽样，1是需要抽样  ?_?
		commodityVO.setSampleFlag(null);
		//是否供应商提供图片 0是不提供，1是提供  sampleFlag和suppliersPicFlag互斥，两个参数必有且只能有一个值为1，具体逻辑背景可以找产品了解  ?_?
		commodityVO.setSuppliersPicFlag(null);
		//是否已经上传图片 ?_?
		commodityVO.setPicFlag(null);
		//商品默认图片地址 ?_?
		commodityVO.setDefaultPic(null);
		
		return commodityVO;
	}
	
	/**
	 * 构造commodityUpdateVo
	 * @param submitVo 新增或修改商品 提交vo
	 * @param propList 
	 * @return 返回commodityUpdateVo
	 */
	public static com.yougou.pc.model.commodityinfo.Commodity buildCommodityUpdateVo(CommoditySubmitNewVo submitVo, 
			List<PropValue> propList) {
		com.yougou.pc.model.commodityinfo.Commodity commodity = new com.yougou.pc.model.commodityinfo.Commodity();
		//商品id
		commodity.setId(submitVo.getCommodityId());
		//商品编号
		commodity.setCommodityNo(submitVo.getCommodityNo());
		//款号
		commodity.setStyleNo(submitVo.getStyleNo());
		//款色编码
		commodity.setSupplierCode(submitVo.getSupplierCode());
		//商品名称
		commodity.setCommodityName(StringUtils.chomp(submitVo.getCommodityName()
				.replace("\"", "")
				.replace("\n", "")
				.replace("\\", "")
				.replaceAll("　", "")//中文空格
				.replaceAll("\\s+", " ")//中间多个英文空格，保留一个
				.trim()));
		//商品别名(no need)
		commodity.setAliasName(null);
		 
		//分类编号 
		commodity.setCatNo(submitVo.getCatNo());
		//分类名称 
		commodity.setCatName(submitVo.getCatName());
		//分类结构名称 
		commodity.setCatStructName(submitVo.getCatStructName());
		//分类id
//		commodity.setcatId(getCatIdByThreeCategory(submitVo.getThreeCategory()));
		
		//品牌编号(no need)
		commodity.setBrandNo(submitVo.getBrandNo());
		//品牌名称(no need)
		commodity.setBrandName(submitVo.getBrandName());
		//颜色编号
		commodity.setColorNo(submitVo.getSpecNo());
		if(propList!=null && propList.size()>0){
			//按尺码设置价格
			//颜色
			commodity.setColorName(submitVo.getSizeName()[0]+"_"+submitVo.getSpecName());
		}else{
			//颜色
			commodity.setColorName(submitVo.getSpecName());
		}
		//年份
		commodity.setYears(submitVo.getYears());
		//订单配送方(0.优购、1.商家)
		commodity.setOrderDistributionSide(Integer.parseInt(submitVo.getOrderDistributionSide()));
		//商家编码
		commodity.setMerchantCode(submitVo.getMerchantCode());
		//审核状态(no need)
		commodity.setIsAudit(null);
		//商品状态(no need)
		commodity.setStatus(null);
		//成本价
		//commodity.setCostPrice(submitVo.getSalePrice());
		//成本价2
		//commodity.setCostPrice2(submitVo.getSalePrice());
		if(propList!=null && propList.size()>0){
			//按尺码设置价格
			//市场价
			commodity.setMarkPrice(Double.parseDouble(submitVo.getPublicPriceBySize()[0]));
			//销售价
			commodity.setSellPrice(Double.parseDouble(submitVo.getSalePriceBySize()[0]));
		}else{
			//市场价
			commodity.setMarkPrice(submitVo.getPublicPrice());
			//销售价
			commodity.setSellPrice(submitVo.getSalePrice());
		}
		//默认图片(no need)
		commodity.setDefalutPic(null);
		//商品小图(no need)
		commodity.setPicSmall(null);
		//图片是否上传完整(no need)
		commodity.setIsGenImage(null);
		//图片是否上传完整(no need)
		commodity.setPicFlag(null);
		//宝贝描述
		commodity.setCommodityDesc(submitVo.getProdDesc());
		//尺码表Id(no need)
		commodity.setSizeChartId(null);
		//创建时间(no need)
		commodity.setCreateDate(null);
		//上架时间(no need)
		commodity.setSellDate(null);
		//修改时间(no need)
		commodity.setUpdateDate(null);
		//优购库存(no need)
		commodity.setYgStrock(null);
		//总库存(no need)
		commodity.setAllStrock(null);
		//updateTimestamp(no need)
		commodity.setUpdateTimestamp(null);
		
		commodity.setSellingPoint(StringUtils.chomp(submitVo.getSellingPoint()
				.replace("\"", "")
				.replace("\n", "")
				.replace("\\", "")
				.replaceAll("　", "")//中文空格
				.replaceAll("\\s+", " ")//中间多个英文空格，保留一个
				.trim()));

		//货品信息列表
		List<Product> products = new ArrayList<Product>();
		if (submitVo.getSizeNo() != null && submitVo.getSizeNo().length != 0) {
			Product product = null;
			for (int i = 0, len = submitVo.getSizeNo().length; i < len; i++) {
				String sizeNo = submitVo.getSizeNo()[i];
				
				product = new Product();
				
				String productId = getValueFormMapListByAnotherKey(
						submitVo.getProdIdList(), "productId", "sizeNo", sizeNo);
				if (productId != null && productId.length() != 0) {
					//id
					product.setId(productId);
					//商品id
					product.setCommodityId(submitVo.getCommodityId());
					//货品编号
					product.setProductNo(getValueFormMapListByAnotherKey(
							submitVo.getProdIdList(), "productNo", "sizeNo", sizeNo));
				}
				
				//尺码编号
				product.setSizeNo(sizeNo);
				//尺码名称
				product.setSizeName(submitVo.getSizeName()[i]);
				//第三方条形码
				product.setThirdPartyInsideCode(submitVo.getThirdPartyCode()[i]);
				//货品条码
				product.setInsideCode(submitVo.getThirdPartyCode()[i]);
				//?_?
				product.setQuantity(null);
				//优购预留的库存数量(no need)
				product.setYougouReserved(null);
				//淘宝预留的库存数量(no need)
				product.setTaobaoReserved(null);
				
				long weightLong = submitVo.getWeight()[i] != null ? 
						Long.valueOf(submitVo.getWeight()[i].intValue()) : 0L;
				//重量
				product.setWeight(weightLong);
				//高度(no need)
				product.setHeight(null);
				//宽度(no need)
				product.setWidth(null);
				//长度(no need)
				product.setLength(null);
				//是否抽样
				//product.setSampleSet(null);
				
				products.add(product);
			}
		}
		commodity.setProducts(products);
		
		//商品属性
		List<CommodityProp> commdoityProps = new ArrayList<CommodityProp>();
		if (submitVo.getPropItemNo() != null && submitVo.getPropItemNo().length != 0) {
			CommodityProp commodityProp = null;
			for (int i = 0, len = submitVo.getPropItemNo().length; i < len; i++) {
				String propItemNo = submitVo.getPropItemNo()[i];
				//兼容属性值多选情况
				String[] propValueNos = submitVo.getPropValueNo()[i].split(";");
				String[] propValueNames = submitVo.getPropValueName()[i].split(";");
				for (int j = 0; j < propValueNos.length; j++) {
					if (StringUtils.isNotBlank(propValueNos[j])) {
						commodityProp = new CommodityProp();
						String propItemId = getValueFormMapListByAnotherKey(
								submitVo.getPropIdList(), "propItemId", "propItemNo", propItemNo);
						if(propValueNos.length>1){
							if (StringUtils.isNotBlank(propItemId)) {
								String[] propItemStrArr = propItemId.split(";");
								if(j<=(propItemStrArr.length-1)){
									propItemId = propItemStrArr[j];
								}else{
									propItemId = null;
								}
							}
						}
						if (StringUtils.isNotBlank(propItemId)) {
							propItemId = propItemId.split(";")[0];
							commodityProp.setId(propItemId);
							commodityProp.setCommodityId(submitVo.getCommodityId());
						}
						//属性项编号
						commodityProp.setPropItemNo(propItemNo);
						//属性项名称
						commodityProp.setPropItemName(submitVo.getPropItemName()[i]);
						//属性值编号
						commodityProp.setPropValueNo(propValueNos[j]);
						//属性值名称
						commodityProp.setPropValueName(propValueNames[j]);
						
						commdoityProps.add(commodityProp);
					}
				}
			}
		}
		commodity.setCommdoityProps(commdoityProps);
		
		return commodity;
	}
	
	/**
	 * 通过分类获取结构名称
	 * @param rootCattegory 一级分类
	 * @param secondCategory 二级分类
	 * @param threeCategory 三级分类
	 * @return
	 */
	public static String getStructNameByCats(String rootCattegory, String secondCategory,
			String threeCategory) {
		String structName = "";
		if (StringUtils.isNotBlank(threeCategory)) {
			structName = threeCategory.substring(0, threeCategory.indexOf(";"));
		} else if (StringUtils.isNotBlank(secondCategory)) {
			structName = secondCategory.substring(0, secondCategory.indexOf(";"));
		} else if (StringUtils.isNotBlank(rootCattegory) && !"0".equals(rootCattegory)) {
			structName = rootCattegory;
		}
		return structName;
	}
	
	/**
	 * 通过 第三级分类 获取 分类id (也适用于第二级分类)
	 * @param threeCategory 第三级分类
	 * @return
	 */
	public static String getCatIdByThreeCategory(String threeCategory) {
		String catId = "";
		String[] info = threeCategory.split(";");
		if (info != null && info.length >= 2) {
			catId = info[1];
		}
		return catId.trim();
	}
	
	/**
	 * 根据 第三级分类 获取 分类编号 (也适用于第二级分类)
	 * @param threeCategory 第三级分类
	 * @return
	 */
	public static String getCatNoByThreeCategory(String threeCategory) {
		String catNo = "";
		String[] info = threeCategory.split(";");
		if (info != null && info.length >= 3) {
			catNo = info[2];
		}
		return catNo.trim();
	}
	
	/**
	 * 根据 第三级分类 获取 分类结构名称 (也适用于第二级分类)
	 * @param threeCategory 第三级分类
	 * @return
	 */
	public static String getStructNameByThreeCategory(String threeCategory) {
		String structName = "";
		String[] info = threeCategory.split(";");
		if (info != null && info.length >= 1) {
			structName = info[0];
		}
		return structName.trim();
	}
	
	/**
	 * 根据 第三级分类 获取 分类名称 (也适用于第二级分类)
	 * @param threeCategory 第三级分类
	 * @return
	 */
	public static String getCatNameByThreeCategory(String threeCategory) {
		String catName = "";
		String[] info = threeCategory.split(";");
		if (info != null && info.length >= 4) {
			catName = info[3];
		}
		return catName.trim();
	}
	
	/**
	 * <p>查询分类结构</p>
	 * 
	 * @param queryVo 查询vo
	 * @return 分类结构
	 */
	public static String getCatStructName(CommodityQueryVo queryVo) {
		if (StringUtils.isNotBlank(queryVo.getThreeCategory())) {
			return queryVo.getThreeCategory().substring(0,
					queryVo.getThreeCategory().indexOf(";"));
		} else if (StringUtils.isNotBlank(queryVo.getSecondCategory())) {
			return queryVo.getSecondCategory().substring(0,
					queryVo.getSecondCategory().indexOf(";"));
		} else if (StringUtils.isNotBlank(queryVo.getRootCattegory())
				&& !"0".equals(queryVo.getRootCattegory())) {
			return queryVo.getRootCattegory();
		}

		return null;
	}
	
	/**
	 * 判断商品是否审核通过
	 * @param status
	 * @param audit
	 * @return 是否审核
	 */
	public static boolean isAudit(Integer status, Integer isAudit) {
		if (null == isAudit || isAudit != 2) return false;
		
		if (status == 4 || status == 5) {
			return true;
		}
		return false;
	}
	
	/**
	 * 新增或修改商品信息, 初始化用户信息
	 * @param request HttpServletRequest对象
	 * @param submitVo 新增或修改商品 提交时 用的vo
	 * @return 成功则返回true
	 */
	public static boolean initMerchantUserInfoForAddOrUpdateNew(HttpServletRequest request, 
			CommoditySubmitNewVo submitVo) {
		StringBuilder sb = new StringBuilder("initMerchantUserInfoForAddOrUpdate#-> ");
		
		//商家名字
		String supplier = (String) SessionUtil.getSessionProperty(request, "supplier");
		sb.append("|supplier: ").append(supplier);
		
		//商家编号
		String merchantCode = SessionUtil.getMerchantCodeFromSession(request);
		sb.append("|merchantCode: ").append(merchantCode);
		
		// 是否入优购仓库 1 入优购仓库，优购发货（默认项） 0 不入优购仓库，商家发货 2 不入优购仓库，优购发货
		String isInputYougouWarehouse = SessionUtil.getSessionProperty(request, CommodityConstant.IS_INPUT_YOUGOU_WAREHOUSE_KEY);		
		sb.append("|isInputYougouWarehouse: ").append(isInputYougouWarehouse);
		
		submitVo.setSupplier(supplier);
		submitVo.setMerchantCode(merchantCode);
		submitVo.setIsInputYougouWarehouse(isInputYougouWarehouse);
		// 订单配送方 0.优购、1.商家
		submitVo.setOrderDistributionSide("1".equals(isInputYougouWarehouse) ? "0" : "1");
		
		logger.info("{}",sb.toString());
		return true;
	}
	
	public static String validateImage(InputStream is, long size, int minWidth,
			int maxWidth, int minHeight, int maxHeight) throws IOException {
		// 文件大小
		if (is.available() > size) {// CommodityConstant.ADD_OR_UPDATE_COMMODITY_IMAGE_SIZE
									// * 1024
			return "3";
			//return new StringBuilder().append("商品图片大小超过了 ").append(CommodityConstant.ADD_OR_UPDATE_COMMODITY_IMAGE_SIZE).append(" KB").toString();
		}
		// 文件尺寸
		Image image = ImageIO.read(is);
		int imgWidth = image.getWidth(null);
		int imgHeight = image.getHeight(null);
		if (imgWidth < minWidth || imgWidth > maxWidth || imgHeight < minHeight
				|| imgHeight > maxHeight) {
			return "4";
			//return "商品图片分辨率不符合  800-1000px * 800-1000px的规格";
		}
		return null;
	}
	
	public static String validateDescImage(InputStream is, long size,
			int minWidth, int maxWidth) throws IOException {
		// 文件大小
		if (is.available() > size) {// CommodityConstant.ADD_OR_UPDATE_COMMODITY_IMAGE_SIZE
									// * 1024
			return "3";
			// return new
			// StringBuilder().append("商品图片大小超过了 ").append(CommodityConstant.ADD_OR_UPDATE_COMMODITY_IMAGE_SIZE).append(" KB").toString();
		}
		// 文件尺寸
		Image image = ImageIO.read(is);
		int imgWidth = image.getWidth(null);
		int imgHeight = image.getHeight(null);
		if (imgWidth < minWidth || imgWidth > maxWidth || imgHeight < 10) {
			return "4";
			// return "商品图片分辨率不符合  800-1000px * 800-1000px的规格";
		}
		return null;
	}

	/**
	 * 校验图片是否符合规则
	 * 
	 * @param is 图片文件流
	 * @param imgType L/B
	 * @return 
	 */
	public static String validateImage(InputStream is, String imgType) throws IOException {
		// 文件尺寸
		Image image = ImageIO.read(is);
		if (null == image) return "图片校验异常";
		
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		if (SystemConstant.IMG_TYPE_L.equals(imgType)) {
			// 文件大小
			if (is.available() > CommodityConstant.ADD_OR_UPDATE_COMMODITY_IMAGE_SIZE * 1024) {
				return new StringBuilder().append("商品图片大小超过了 ").append(CommodityConstant.ADD_OR_UPDATE_COMMODITY_IMAGE_SIZE).append(" KB").toString();
			}
			if (width < 800 || width > 1000 || height < 800 || height > 1000) {
				return "商品图片分辨率不符合  800-1000px × 800-1000px 的规格";
			}
		} else if (SystemConstant.IMG_TYPE_B.equals(imgType)) {
			// 文件大小
			if (is.available() > CommodityConstant.ADD_OR_UPDATE_COMMODITY_IMAGE_SIZE * 1024 *2) {
				return new StringBuilder().append("商品图片大小超过了 ").append(CommodityConstant.ADD_OR_UPDATE_COMMODITY_IMAGE_SIZE).append(" KB").toString();
			}
			if (width < 740 || width > 790 || height < 1) {
				return "商品图片分辨率不符合  790px × 1-9999px 的规格";
			}
		} else {
			return "商品图片名称不符合规范";
		}
		
		return null;
	}
	
	/*****************************************************************************************************************************/
	/************************************                     Other Methods                   ************************************/
	/*****************************************************************************************************************************/
	
	/**
	 * 从map list 中通过一个键、值获取 另一个 键的值
	 * @param mapList map list
	 * @param targetKey 目标键
	 * @param anotherKey 另一个键
	 * @param anotherValue 另一个值
	 * @return 获取目标键的值
	 */
	public static String getValueFormMapListByAnotherKey(
			List<Map<String, String>> mapList, String targetKey,
			String anotherKey, String anotherValue) {
		if (mapList == null || mapList.size() == 0 ||
				targetKey == null || targetKey.length() == 0 ||
				anotherKey == null || anotherKey.length() == 0 ||
				anotherValue == null || anotherValue.length() == 0) {
			return "";
		}
		String targetValue = "";
		Map<String, String> mapTmp = null;
		for (int i = 0, len = mapList.size(); i < len; i++) {
			mapTmp = mapList.get(i);
			if (anotherValue.equals(mapTmp.get(anotherKey))) {
				targetValue = mapTmp.get(targetKey);
				break;
			}
		}
		return targetValue;
	}
	
	/**
	 * json数组字符串转换成List
	 * @param jsonArrayStr
	 * @param keys
	 * @return
	 */
	public static List<Map<String, String>> jsonArray2List(String jsonArrayStr,
			String[] keys) {
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		JSONArray jsonArray = JSONArray.fromObject(jsonArrayStr);
		
		JSONObject jsonObj = null;
		Map<String, String> map = null;
		for (int i = 0, len = jsonArray.size(); i < len; i++) {
			jsonObj = jsonArray.getJSONObject(i);
			map = new HashMap<String, String>();
			for (int j = 0, len1 = keys.length; j < len1; j++) {
				if(jsonObj.containsKey(keys[j])){
					map.put(keys[j], CommonUtil.getTrimString(jsonObj.getString(keys[j])));
				}
			}
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 获取弹框并关闭页面脚本
	 * @param msg 待输出的信息
	 * @return
	 */
	public static String getAlertThenCloseScript(String msg) {
		StringBuffer out = new StringBuffer();
		out.append("<html>");
		out.append("<head>");
		out.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />");
		out.append("<script type='text/javascript'>\n");
		out.append("alert('" + msg + "');");
		out.append("window.opener=null;");
		out.append("window.open('','_self');");
		out.append("window.close();");
		out.append("</script>");
		out.append("</head>");
		out.append("</html>");
		return out.toString();
	}
	
	public static String getYgDgAlertThenRedirectScript(String msg, String url){
		StringBuffer out = new StringBuffer();
		out.append("<html>\n");
		out.append("<head>\n");
		out.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />\n");
		out.append("<link rel='stylesheet' type='text/css' href='/yougou/css/base.css?"+System.currentTimeMillis()+"'/>\n");
		out.append("<link rel='stylesheet' type='text/css' href='/yougou/css/mold.css?"+System.currentTimeMillis()+"'/>\n");
		out.append("<script type='text/javascript' src='/yougou/js/bootstrap.js'></script>\n");
		out.append("<script type='text/javascript'>\n");
		out.append("$(document).ready(function(){ygdg.dialog.alert('" + msg + "',function(){location.href='"+url+"';});});\n");
		out.append("</script>\n");
		out.append("</head>\n");
		out.append("<body>\n");
		out.append("</body>\n");
		out.append("</html>");
		return out.toString();
	}
	
	/**
	 * 获取弹框并关闭页面脚本
	 * @param response HttpServletResponse
	 * @param msg 待输出的信息
	 * @return
	 */
	public static void getAlertThenCloseScript(HttpServletResponse response, String msg) throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; chartset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(getAlertThenCloseScript(msg));
	}
	
	/**
	 * getYgDgAlertThenRedirectScript:获取弹框并关闭页面脚本，跳转其他路径 
	 * @author li.n1 
	 * @param response
	 * @param msg 弹窗信息
	 * @param url 关闭弹窗之后跳转路径
	 * @throws IOException 
	 * @since JDK 1.6
	 */
	public static void getYgDgAlertThenRedirectScript(HttpServletResponse response, String msg ,String url) throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; chartset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(getYgDgAlertThenRedirectScript(msg,url));
	}
	
	/**
	 * <p></p>
	 * 
	 * @param _errorList 
	 * @return 招商系统自定义错误列表
	 */
	public static void transformCommodityError(List<String> source, List<ErrorVo> target) {
		if (CollectionUtils.isNotEmpty(source)) {
			for (String error : source) {
				String errorField = error.substring(error.indexOf(ERRORFIELD) + 11, error.indexOf(","));
				String errorInfo = error.substring(error.indexOf(ERRORINFO) + 10);
				if ("catNo".equals(errorField)) {
					errorField = "category";
				} else if ("size".equals(errorField)) {
					errorField = "sizeNo";
				} else if ("commodityName".equals(errorField)) {
					errorField = "commodity_name";
				} else if ("styleNo".equals(errorField)) {
					errorField = "commodity_style";
				} else if ("supplierCode".equals(errorField)) {
					errorField = "commodity_code";
				} else if ("salePrice".equals(errorField)) {
					errorField = "commodity_price";
				} else if ("publicPrice".equals(errorField)) {
					errorField = "commodity_market_value";
				} else if ("year".equals(errorField)) {
					errorField = "goods_years";
				} else if ("specName".equals(errorField)) {
					errorField = "specName";
				} else if(errorField.endsWith("thirdPartyCode")){
					String[] codeArr = errorField.split("_");
					if(codeArr!=null&&codeArr.length>0)
						errorField = "sku_"+codeArr[0];
				} else if(errorField.endsWith("stock")){
					String[] stockArr = errorField.split("_");
					if(stockArr!=null&&stockArr.length>0)
						errorField = "stock_"+stockArr[0];
				} else if(errorField.endsWith("weightStr")){
					String[] weightArr = errorField.split("_");
					if(weightArr!=null&&weightArr.length>0)
						errorField = "weight_"+weightArr[0];
				}
				target.add(new ErrorVo(errorField, errorInfo));
			}
		}
	}
	
	private final static String ERRORFIELD = "errorField";
	
	private final static String ERRORINFO = "errorInfo";
	
	public static void main(String[] args){
		String str = "as dfasdfd\\sfs\n\"　sdfs　df 　　　　　　　　　　";
		System.out.println(StringUtils.chomp(str.replace("\"", "").replace("\n", "").replace("\\", "").replaceAll("　", "").trim()));
	}

	public static CommoditySubmitVo buildOldCommodityVO(
			CommoditySubmitNewVo submitVo) {
		CommoditySubmitVo subVo = new CommoditySubmitVo();
		subVo.setMerchantCode(submitVo.getMerchantCode());
		subVo.setProdDesc(submitVo.getProdDesc());
		subVo.setSupplierCode(submitVo.getSupplierCode());
		subVo.setImgFileId(submitVo.getImgFileId());
		subVo.setStructName(submitVo.getStructName());
		subVo.setCommodityNo(submitVo.getCommodityNo());
		subVo.setYears(submitVo.getYears());
		subVo.setBrandNo(submitVo.getBrandNo());
		subVo.setWareHouseCode(submitVo.getWareHouseCode());
		subVo.setThirdPartyCode(submitVo.getThirdPartyCode());
		subVo.setStockInt(submitVo.getStockInt());
		subVo.setProdIdList(submitVo.getProdIdList());
		subVo.setCatId(submitVo.getCatId());
		return subVo;
	}

	public static CommoditySubmitNewVo buildCommoditySubmitNewVo(
			CommodityVO commodityVO) {
		CommoditySubmitNewVo newVo = new CommoditySubmitNewVo();
		newVo.setCommodityName(commodityVO.getCommodityName());
		newVo.setSellingPoint(commodityVO.getSellingPoint());
		newVo.setProdDesc(commodityVO.getProdDesc());
		newVo.setCommodityNo(commodityVO.getCommodityNo());
		newVo.setStyleNo(commodityVO.getStyleNo());
		newVo.setSupplierCode(commodityVO.getSupplierCode());
		return newVo;
	}

	/**
	 * buildCommoditySubmitNewVo:构建商品提交审核VO，用于提交审核
	 * @author li.n1 
	 * @param commodity
	 * @param isSubmit 是否提交审核 true是  false否
	 * @return 
	 * @since JDK 1.6 
	 * @date 2015-9-18 下午5:12:36
	 */
	public static CommoditySubmitNewVo buildCommoditySubmitNewVo(
			Commodity commodity,boolean isSubmit) {
		CommoditySubmitNewVo newVo = new CommoditySubmitNewVo();
		newVo.setCommodityId(commodity.getId());
		newVo.setCommodityName(commodity.getCommodityName());
		newVo.setSellingPoint(commodity.getSellingPoint());
		newVo.setProdDesc(commodity.getCommodityDesc());
		newVo.setCommodityNo(commodity.getCommodityNo());
		newVo.setStyleNo(commodity.getStyleNo());
		newVo.setSupplierCode(commodity.getSupplierCode());
		//设置为商品提交审核标志
		if(isSubmit){
			newVo.setIsSaveSubmit(CommodityConstant.SUBMIT_COMMODITY_IS_SAVE_SUBMIT_TRUE);
		}
		return newVo;
	}
}
