package com.yougou.kaidian.commodity.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yougou.kaidian.commodity.model.vo.LayoutTemplate;

/**
 * 商家中心版式设置持久层映射接口
 * @author zhang.f1
 *
 */
public interface LayoutSettingMapper {
	
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
	 * 删除版式设置的商品关联关系
	 * @param templateId
	 */
	public void deleteLayoutSettingCommodity (String templateId);
	
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
	 * 批量删除指定商品的版式设置
	 * @param commodityNos
	 */
	public void batchDeleteLayoutSettingByCommodityNo(List<String> commodityNos);
	
	/**
	 * 批量新增商品版式设置（保存页面版式设置）
	 * @param commodityNos
	 */
	public void batchAddLayoutSetting(List<Map<String,Object>> settingList);
	
	/**
	 * 除去指定模板 都改为 不是绑定所有商品
	 * @param template
	 */
	public void updateLayoutTempalteIsAllNo(LayoutTemplate template);
	
	/**
	 * 指定模板改为绑定所有商品
	 * @param template
	 */
	public void updateLayoutTempalteIsAllYes(LayoutTemplate template);
	
	/**
	 * 根据商品编码获取该商品版式设置html
	 * @param commodityNo
	 * @return
	 */
	public LayoutTemplate querySettingHtmlByCommodityNo(String commodityNo);
	
	/**
	 * 根据商家编码获取该商家指向所有商品的版式设置html
	 * @param merchantCode
	 * @return
	 */
	public LayoutTemplate querySettingHtmlByMerchantCode(String merchantCode);
	
	/**
	 * 根据商品编码获取商品所属商家编码
	 * @param commmodityNo
	 * @return
	 */
	public String queryMerchantCodeByCommodityNo(String commodityNo);
	
	/**
	 * 根据模板ID 查询此模板下的商品编码 
	 * @param templateId
	 * @return
	 */
	List<String> queryCommodityNoByTemplateId(String templateId);
	
	/**
	 * 根据商家编码查询所有的绑定模板的商品编码
	 * @param merchantCode
	 * @return
	 */
	List<String> queryLayoutSetCommodityNosByMerchantCode(@Param("merchantCode") String merchantCode);
	
	/**
	 * 根据商品编码获取该商品绑定的版式html 静态页地址
	 * @param commodityNo
	 * @return
	 */
	LayoutTemplate querylayoutHtmlFileByCommodityNo(@Param("commodityNo") String commodityNo);
	
	/**
	 * 根据商家编码获取商绑定所有商品的版式html 静态页地址
	 * @param commodityNo
	 * @return
	 */
	LayoutTemplate querylayoutHtmlFileByMerchantCode(@Param("merchantCode") String merchantCode);
	
	/**
	 * 查询所有绑定了商品或指向所有商品的固定模板和自定义模板
	 * @return
	 */
	List<LayoutTemplate> queryAllUsingLayoutTemplate();
	
	/**
	 * 更新模板静态页地址
	 * @param template
	 */
	void updateHtmlFilePath(LayoutTemplate template);
	
	/**
	 * 根据商家编码查询绑定所有商品的模板
	 * @param merchantCode
	 * @return
	 */
	LayoutTemplate queryTemplateForAllByMerchantCode(String merchantCode);
}
