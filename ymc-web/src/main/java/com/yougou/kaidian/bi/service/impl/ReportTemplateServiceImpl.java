package com.yougou.kaidian.bi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yougou.kaidian.bi.beans.AnalysisIndex;
import com.yougou.kaidian.bi.dao.MMSDBReportTemplateMapper;
import com.yougou.kaidian.bi.service.IReportTemplateService;
import com.yougou.kaidian.framework.util.YmcThreadLocalHolder;

/**
 * 商家中心数据报表-模板操作服务实现类
 * @author zhang.f1
 *
 */
@Service
public class ReportTemplateServiceImpl implements IReportTemplateService {
	@Resource
	private MMSDBReportTemplateMapper mmsdbReportTemplateMapper;
	private Logger logger = LoggerFactory.getLogger(ReportTemplateServiceImpl.class);
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean addReportTemplate(Map<String,Object> params) throws Exception {
		boolean flag = true;
		try{
			mmsdbReportTemplateMapper.addReportTemplate(params);
			mmsdbReportTemplateMapper.addReportTemplateIndexes(params);
		}catch(Exception e){
			logger.error("商家编码：{},新增模板报错：",YmcThreadLocalHolder.getMerchantCode(),e);
			flag = false;
			throw new Exception("新增模板报错：",e);
		}
		return flag;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean updateReportTemplate(Map<String,Object> params) throws Exception{
		boolean flag = true;
		try{
			mmsdbReportTemplateMapper.deleteReportTemplateIndexes((String)params.get("template_id"));
			mmsdbReportTemplateMapper.addReportTemplateIndexes(params);
		}catch(Exception e){
			logger.error("商家编码：{},更新模板报错：",YmcThreadLocalHolder.getMerchantCode(),e);
			flag = false;
			throw new Exception("更新模板报错：",e);
		}
		return flag;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean deleteReportTemplate(String templateId) throws Exception {
		boolean flag = true;
		try{
			mmsdbReportTemplateMapper.deleteReportTemplateIndexes(templateId);
			mmsdbReportTemplateMapper.deleteReportTemplate(templateId);
		}catch(Exception e){
			logger.error("商家编码：{},删除模板报错：",YmcThreadLocalHolder.getMerchantCode(),e);
			flag = false;
			throw new Exception("删除模板报错：",e);
		}
		return 	flag;
	}

	@Override
	public List<Map<String,Object>> queryReportTemplate(String merchant_code, String loginName)
			throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("merchant_code", merchant_code);
		params.put("loginName", loginName);
		return mmsdbReportTemplateMapper.queryReportTemplate(params);

	}

	@Override
	public List<Map<String,Object>> queryDefaultTemplate() {
		List<Map<String,Object>> list = null;
		try{
			list = mmsdbReportTemplateMapper.queryDefaultTemplate();
		}catch(Exception e){
			logger.error("商家编码：{},查询系统默认预置模板发生错误",YmcThreadLocalHolder.getMerchantCode(),e);
		}
		return list;
	}

	@Override
	public List<Map<String,Object>> queryReportIndexes(String templateId) throws Exception {
		// TODO Auto-generated method stub
		return mmsdbReportTemplateMapper.queryReportIndexes(templateId);
	}

	@Override
	public List<Map<String,Object>> queryIndexList() throws Exception {
		return mmsdbReportTemplateMapper.queryIndexList();
	}
	
	@Override
	public List<Map<String, Object>>queryUserDefaultReportTemplate(
			String merchantCode, String loginName) {
		return mmsdbReportTemplateMapper.queryUserDefaultReportTemplate(merchantCode,loginName);
	}
	
	@Override
	public List<AnalysisIndex> loadAnalysisIndex() {
		return mmsdbReportTemplateMapper.loadAnalysisIndex();
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean setDefaultTemplate(String merchantCode, String loginName, String templateId) 
		throws Exception{
		boolean flag = true;
		try{
			mmsdbReportTemplateMapper.updateOtherTemplateNotLastUse(merchantCode,loginName);
			if(!("0".equals(templateId))){
				mmsdbReportTemplateMapper.setDefaultTemplate(templateId);
			}
		}catch(Exception e){
			logger.error("商家编码：{},设置用户默认模板发生错误",YmcThreadLocalHolder.getMerchantCode(),e);
			flag =  false;
			throw new Exception("设置用户默认模板发生错误",e);
		}
		return flag;
	}

	@Override
	public List<AnalysisIndex> loadTemplateIndexByTemplateId(String id) {
		return mmsdbReportTemplateMapper.loadTemplateIndexByTemplateId(id);
	}
	
	@Override
	public List<Map<String, Object>> loadIndexByTemplateId(String id) {
		return mmsdbReportTemplateMapper.loadIndexByTemplateId(id);
	}
}
