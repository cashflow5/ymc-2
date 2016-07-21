package com.yougou.kaidian.bi.service;

import java.util.List;
import java.util.Map;

import com.yougou.kaidian.bi.beans.AnalysisIndex;

/**
 * 商家中心数据报表-模板管理服务接口
 * @author zhang.f1
 *
 */
public interface IReportTemplateService {
	
	/**
	 * 新增模板
	 * @param params
	 * @return
	 */
	public boolean addReportTemplate(Map<String,Object> params) throws Exception;
	
	/**
	 * 更新模板
	 * @param params
	 *  params:{template_id:aaa,indexList:[指标名称1，指标名称2...]}
	 * @return
	 */
	public boolean updateReportTemplate(Map<String,Object> params) throws Exception;
	
	/**
	 * 删除模板
	 * @param templateId
	 * @return
	 */
	public boolean deleteReportTemplate(String templateId) throws Exception;
	
	/**
	 * 根据商家编码，登陆账号 查询商家账号所有的模板列表
	 * @param merchant_code
	 * @param loginName
	 * @return
	 */
	public List<Map<String,Object>> queryReportTemplate (String merchant_code,String loginName) throws Exception;
	
	/**
	 * 查询系统默认预置模板的指标，最终返回的是指标信息
	 * @return
	 */
	public List<Map<String,Object>> queryDefaultTemplate();
	
	/**
	 * 根据模板ID查询模板所有指标列表
	 * @param templateId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryReportIndexes(String templateId) throws Exception;
	
	
	/**
	 * 查询指标列表
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryIndexList() throws Exception;
	/**
	 * queryUserDefaultReportTemplate:查询用户设置的预置模板的指标，最终返回的是指标信息 
	 * @author li.n1 
	 * @param merchant_code
	 * @param loginName
	 * @return 
	 * @since JDK 1.6 
	 * @date 2015-8-31 上午11:01:57
	 */
	public List<Map<String, Object>> queryUserDefaultReportTemplate(
			String merchant_code, String loginName);
	/**
	 * loadAnalysisIndex:查询商品分析的指标
	 * @author li.n1 
	 * @return 
	 * @since JDK 1.6 
	 * @date 2015-9-2 上午10:55:37
	 */
	public List<AnalysisIndex> loadAnalysisIndex();
	/**
	 * setDefaultTemplate: 设置用户的默认模板
	 * @author li.n1 
	 * @param merchantCode
	 * @param loginName
	 * @param templateId 模板id
	 * @return 
	 * @since JDK 1.6 
	 * @date 2015-8-31 下午5:53:18
	 */
	public boolean setDefaultTemplate(String merchantCode, String loginName,String templateId) throws Exception;
	/**
	 * loadTemplateIndexByTemplateId:根据模板id查询改模板下的指标
	 * @author li.n1 
	 * @param id
	 * @return List<AnalysisIndex>
	 * @since JDK 1.6 
	 * @date 2015-8-31 下午5:53:14
	 */
	public List<AnalysisIndex> loadTemplateIndexByTemplateId(String id);
	/**
	 * loadTemplateIndexByTemplateId:根据模板id查询改模板下的指标
	 * @author li.n1 
	 * @param id
	 * @return List<Map>
	 * @since JDK 1.6 
	 * @date 2015-8-31 下午5:53:14
	 */
	public List<Map<String, Object>> loadIndexByTemplateId(String id);
	
}