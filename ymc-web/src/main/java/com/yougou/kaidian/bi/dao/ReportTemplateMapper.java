package com.yougou.kaidian.bi.dao;

import java.util.List;
import java.util.Map;

/**
 * 商家中心数据报表-模板操作映射接口
 * @author zhang.f1
 *
 */
public interface ReportTemplateMapper {
	/**
	 * 新增模板
	 * @param params
	 * @return
	 */
	public void addReportTemplate(Map params) throws Exception;
	
	/**
	 * 新增模板指标
	 * @param params
	 * @return
	 */
	public void addReportTemplateIndexes(Map params) throws Exception;
	
	/**
	 * 更新模板
	 * @param params
	 * @return
	 */
	public void updateReportTemplate(Map params) throws Exception;
	
	/**
	 * 删除模板
	 * @param templateId
	 * @return
	 */
	public void deleteReportTemplate(String templateId) throws Exception;
	
	/**
	 * 删除模板指标关系
	 * @param templateId
	 * @return
	 */
	public void deleteReportTemplateIndexes(String templateId) throws Exception;
	
	/**
	 * 根据商家编码，登陆账号 查询所属模板列表
	 * @param merchant_code
	 * @param loginName
	 * @return
	 */
	public List<Map> queryReportTemplate (Map params) throws Exception;
	
	/**
	 * 查询系统默认预置模板
	 * @return
	 */
	public Map queryDefaultTemplate() throws Exception;
	
	/**
	 * 根据模板ID查询模板所有指标列表
	 * @param templateId
	 * @return
	 * @throws Exception
	 */
	public List<Map> queryReportIndexes(String templateId) throws Exception;
	
	/**
	 * 查询指标列表（通用指标，商品指标）
	 * @return
	 * @throws Exception
	 */
	public List<Map> queryIndexList() throws Exception;
	
}
