package com.yougou.kaidian.bi.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yougou.kaidian.bi.beans.AnalysisIndex;

/**
 * 商家中心数据报表-模板操作映射接口
 * @author zhang.f1
 *
 */
public interface MMSDBReportTemplateMapper {
	/**
	 * 新增模板
	 * @param params
	 * @return
	 */
	public void addReportTemplate(Map<String,Object> params) throws Exception;
	
	/**
	 * 新增模板指标
	 * @param params
	 * @return
	 */
	public void addReportTemplateIndexes(Map<String,Object> params) throws Exception;
	
	/**
	 * 更新模板
	 * @param params
	 * @return
	 */
	public void updateReportTemplate(Map<String,Object> params) throws Exception;
	
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
	public List<Map<String,Object>> queryReportTemplate (Map<String,Object> params) throws Exception;
	
	/**
	 * 查询系统默认预置模板
	 * @return
	 */
	public List<Map<String,Object>> queryDefaultTemplate() throws Exception;
	
	/**
	 * 根据模板ID查询模板所有指标列表
	 * @param templateId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryReportIndexes(String templateId) throws Exception;
	
	/**
	 * 查询指标列表（通用指标，商品指标）
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryIndexList() throws Exception;

	public List<Map<String, Object>> queryUserDefaultReportTemplate(
			@Param("merchantCode") String merchantCode,@Param("loginName") String loginName);

	public List<AnalysisIndex> loadAnalysisIndex();

	public void updateOtherTemplateNotLastUse(Map<String, Object> params);

	public void setDefaultTemplate(String templateId);

	public void updateOtherTemplateNotLastUse(@Param("merchantCode") String merchantCode,
			@Param("loginName") String loginName);

	public List<AnalysisIndex> loadTemplateIndexByTemplateId(String id);

	public List<Map<String, Object>> loadIndexByTemplateId(String id);
	
}
