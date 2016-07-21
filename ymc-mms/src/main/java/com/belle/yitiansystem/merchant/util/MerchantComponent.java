/*
 * com.belle.yitiansystem.merchant.enums.ContractStatusEnum
 * 
 *  Tue Jun 23 13:25:28 CST 2015
 * 
 * Copyright (C) 2013 YouGou Information Technology Co.,Ltd 
 * All Rights Reserved. 
 * 
 * The software for the YouGou technology development, without the 
 * company's written consent, and any other individuals and 
 * organizations shall not be used, Copying, Modify or distribute 
 * the software.
 * 
 */

package com.belle.yitiansystem.merchant.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.belle.finance.biz.dubbo.ICostSetOfBooksDubboService;
import com.belle.finance.costsettlement.costsetofbooks.model.vo.CostSetofBooks;
import com.belle.yitiansystem.merchant.constant.MerchantConstant;
import com.belle.yitiansystem.merchant.service.IMerchantServiceNew;
import com.belle.yitiansystem.systemmgmt.util.SysconfigProperties;
import com.yougou.pc.api.ICommodityBaseApiService;
import com.yougou.pc.model.category.Category;

/**
 * 供应商工具类
 * @author li.j1
 * @history 2015-06-23 Created
 */
@Component
public class MerchantComponent {
	
	private static Logger logger = Logger.getLogger(MerchantComponent.class);
	
	@Resource
	private ICommodityBaseApiService commodityBaseApiService;
	
	@Resource
	private SysconfigProperties sysconfigProperties;
	
	@Resource
	private ICostSetOfBooksDubboService costSetofBookApi;
	    
    /**
	 * 通过品牌分类 brandNo;structName 字段来匹配被选中节点
	 * 
	 * @param treeModes
	 * @param brandStructs
	 */
	public void setTreeModesCheckStatus(List<Category> treeModes,
			List<String> brandStructs) {
		// 存储被选中的父节点
		Set<String> structNames = new HashSet<String>();
		if (CollectionUtils.isNotEmpty(treeModes)) {
			for (Category category : treeModes) {
				if (category.getCatLeave() == 3) {
					if (brandStructs.contains(category.getStructName())) {
						category.setIsEnabled((short) 1);

						String structName = category.getStructName();
						// 设置parent为选中
						structNames.add(structName.split(";")[0] + ";0");
						structNames.add(structName.substring(0,
								structName.indexOf(";") + 3));
						structNames.add(structName.substring(0,
								structName.indexOf(";") + 6));
					}
				}
			}
			for (Category category : treeModes) {
				if (category.getCatLeave() != 3) {
					if (structNames.contains(category.getStructName())) {
						category.setIsEnabled((short) 1);
					}
				}
			}
		}
	}
    
    /**
	 * <p>
	 * 模拟使用category对象来构造一个品类 Tree mode
	 * </p>
	 * 
	 * @param brandNoList
	 *            brandNo之间使用;分隔
	 * @return Tree Node 集合
	 */
	public List<Category> getCategoryTreeMode(List<String> brandNos) {
		List<Category> list = new ArrayList<Category>();

		for (String brandNo : brandNos) {
			if (StringUtils.isBlank(brandNo))
				continue;

			com.yougou.pc.model.brand.Brand brand = null;
			try {
				brand = commodityBaseApiService.getBrandByNo(brandNo);
			} catch (Exception e) {
				logger.error(MessageFormat.format(
						"commodityBaseApiSercie接口获取品牌[{0}]失败", brandNo), e);
			}
			if (null == brand)
				continue;

			// 获取分类
			List<Category> thirdcats = commodityBaseApiService
					.getCategoryListByBrandId(brand.getId(), (short) 1, null);
			Set<String> firstAndSecondCat = new HashSet<String>();
			// 逆推第一级和第二级分类
			for (Category category : thirdcats) {
				if (StringUtils.isBlank(category.getStructName())
						|| category.getStructName().length() != 8)
					continue;

				firstAndSecondCat.add(category.getStructName().substring(0, 2));
				firstAndSecondCat.add(category.getStructName().substring(0, 5));
				category.setParentId(brand.getBrandNo() + ";"
						+ subCategory(category.getStructName()));
				category.setStructName(brand.getBrandNo() + ";"
						+ category.getStructName());
				// 0：未选中 1：选中
				category.setIsEnabled((short) 0);
			}
			if (CollectionUtils.isNotEmpty(firstAndSecondCat)) {
				for (String str : firstAndSecondCat) {
					Category _cat = commodityBaseApiService
							.getCategoryByStructName(str);
					_cat.setParentId(brand.getBrandNo() + ";"
							+ subCategory(_cat.getStructName()));
					_cat.setStructName(brand.getBrandNo() + ";"
							+ _cat.getStructName());
					_cat.setIsEnabled((short) 0);
					thirdcats.add(_cat);
				}
			}

			Category _brandWapper = new Category();
			_brandWapper.setCatLeave(-1);
			_brandWapper.setCatName(brand.getBrandName());
			_brandWapper.setCatNo(brand.getBrandNo());
			_brandWapper.setParentId("-1");
			_brandWapper.setStructName(brand.getBrandNo() + ";0");
			_brandWapper.setId(brand.getId());
			_brandWapper.setIsEnabled((short) 0);
			thirdcats.add(_brandWapper);

			list.addAll(thirdcats);
		}

		return list;
	}
    
	/**
	 * 截取分类的父级
	 * 
	 * @param structName
	 *            10-11-12
	 * @return 10-11
	 */
	private String subCategory(String structName) {
		if (StringUtils.isBlank(structName))
			return null;

		int index = structName.lastIndexOf("-");
		if (index == -1) {
			return "0";
		}

		return structName.substring(0, index);
	}  
    
	/**
     * 获取上传文件大小的最大值
     * @return
     */
	public int getMaxFileSize() {
		String cfgFileSize = sysconfigProperties.getContractFtpMaxFileSize();
		int defaultMaxFileSize = MerchantConstant.DEFAULT_CONTRACT_ATTACHMENT_FILE_SIZE; // 10M
		int maxFileSize = StringUtils.isNotBlank(cfgFileSize) ? Integer.valueOf(cfgFileSize) : defaultMaxFileSize;
		return maxFileSize;
	}
 
	 public String getNameBySetOfBooksCode(String code){
	    	String setOfBooksName = "";
	    	if( StringUtils.isEmpty(code) ){
	    		return "";
	    	}
			List<CostSetofBooks> costSetofBooksList = null ;
			try {
				costSetofBooksList = costSetofBookApi.queryAllCostSetOfBooks();
				for(CostSetofBooks book :costSetofBooksList){
					String keyCode = book.getSetOfBooksCode();
					if( StringUtils.isNotEmpty(keyCode) && (keyCode.trim()).equalsIgnoreCase(code.trim()) ){
						return book.getSetOfBooksName();
					}
				}
			} catch (Exception e) {
				logger.error("未查到所有的成本帐套名称枚举，请检查接口！",e);
			}
			return setOfBooksName;
	    }
}
