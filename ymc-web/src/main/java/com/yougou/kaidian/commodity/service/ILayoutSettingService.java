package com.yougou.kaidian.commodity.service;

import java.util.List;
import com.yougou.kaidian.commodity.model.vo.LayoutTemplate;

/**
 * 商家中心版式设置服务类接口
 * @author zhang.f1
 *
 */
public interface ILayoutSettingService {

	/**
	 * 新增版式
	 * @param template
	 */
	public void addLayoutTemplate(LayoutTemplate template);
	
	/**
	 * 修改版式
	 * @param template
	 */
	public void updateLayoutTemplate(LayoutTemplate template);
	
	/**
	 * 删除版式
	 * @param templateId
	 */
	public void deleteLayoutTemplate(String templateId);
	
	/**
	 * 查询自定义版式
	 * @param merchantCode
	 */
	public List<LayoutTemplate> queryLayoutTemplate (String merchantCode);
	
	/**
	 * 根据版式模板ID 查询模板
	 * @param id
	 * @return
	 */
	public LayoutTemplate queryLayoutTemplateById (String id);
	
	/**
	 * 删除指定商家所有版式设置
	 * @param merchantCode
	 */
	public void deleteLayoutSettingByMerchantCode(String merchantCode);
	
	/**
	 * 删除指定商品的版式设置
	 * @param commodityNo
	 */
	public void deleteLayoutSettingByCommodityNo(String commodityNo);
	
	/**
	 * 保存版式设置,部分商品
	 * @param template
	 * @param settingList
	 */
	public void layoutSettingForSomeCommoditys(LayoutTemplate template,List<String> commodityNos);
	
	/**
	 * 保存版式设置，所有商品
	 * @param template
	 */
	public void layoutSettingForAllCommoditys(LayoutTemplate template,String merchantCode);
	
	
	/**
	 * 根据模板ID 查询此模板下的商品编码 
	 * @param templateId
	 * @return
	 */
	List<String> queryCommodityNoByTemplateId(String templateId);
	
	/**
	 * 初始化所有绑定了商品或指向所有商品的固定模板和自定义模板，生成静态页
	 * @return
	 */
	void initAllUsingLayoutTemplate();
	
}
