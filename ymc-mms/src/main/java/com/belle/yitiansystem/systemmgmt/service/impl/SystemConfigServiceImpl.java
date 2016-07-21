package com.belle.yitiansystem.systemmgmt.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alisoft.xplatform.asf.cache.IMemcachedCache;
import com.belle.infrastructure.constant.Constant;
import com.belle.infrastructure.orm.basedao.CriteriaAdapter;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.infrastructure.util.DateUtil;
import com.belle.infrastructure.util.GetSessionUtil;
import com.belle.memcached.core.Cache;
import com.belle.yitiansystem.systemmgmt.dao.IOperateLogDao;
import com.belle.yitiansystem.systemmgmt.dao.ISystemConfigDao;
import com.belle.yitiansystem.systemmgmt.model.pojo.OperateLog;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemConfig;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.belle.yitiansystem.systemmgmt.model.vo.OperateLogVo;
import com.belle.yitiansystem.systemmgmt.service.ISystemConfigService;

@Service
public class SystemConfigServiceImpl implements ISystemConfigService {

	@Resource
	private ISystemConfigDao systemConfigDao;
	
	@Resource
	private IOperateLogDao operateLogDao;

	/**
	 * 使用本地内存中的Map缓存获取特定系统配置项
	 * 为了获取最高的性能，采用本地ConcurrentMap 来缓存各个仓库的配置。	 * 
	 * 该方法现在
	 * 
	 * 仅仅被WMS用来 获取各个仓库的授权码验证 
	 *  
	 * 当调用 一键更新缓存时该缓存会被清空
	 */
	private static final ConcurrentMap<String, SystemConfig> fastSystemConfigCacheMap = 
			new ConcurrentHashMap<String, SystemConfig>();
	
	@Transactional
	public SystemConfig addSystemConfig(SystemConfig config,HttpServletRequest request) throws Exception{
		SystemConfig db_config = systemConfigDao.findSystemConfigByKey(config.getKey());
		if(db_config == null){
			config.setDeleteFlag(Constant.NO_DELTE_FLAG.toString());
			systemConfigDao.saveObject(config);
			db_config = config;
			this.saveOperateLog(request,db_config.getConfigName(), "增加配置，"+db_config.getConfigName(),111);
		
		}else{
			String desc = "";
			if(!db_config.getValue().equals(config.getValue())) {
				desc = desc + "将"+db_config.getConfigName()+":配置值"+db_config.getValue()+"修改为"+config.getValue();
			}
			if(!db_config.getConfigName().equals(config.getConfigName())) {
				desc = desc + "将"+db_config.getConfigName()+":配置名称"+db_config.getConfigName()+"修改为"+config.getConfigName();
			}
			if(!db_config.getRemark().equals(config.getRemark())) {
				desc = desc + "将"+db_config.getRemark()+":备注"+db_config.getRemark()+"修改为"+config.getRemark();
			}
			db_config.setValue(config.getValue());
			db_config.setConfigName(config.getConfigName());
			db_config.setRemark(config.getRemark());
			db_config.setDeleteFlag(Constant.NO_DELTE_FLAG.toString());
			systemConfigDao.merge(db_config);
			this.saveOperateLog(request,config.getConfigName(),desc,111);
		}
		
		try{
			//更新缓存
			IMemcachedCache cache = Cache.getCommonCacheInstatce();
			cache.put(db_config.getKey(),db_config);
		
		}catch(Exception e){

		}
		return db_config;
	}

	public SystemConfig getSystemConfigById(String id) {
		return systemConfigDao.getById(id);
	}

	/**
	 * 获取所有没有删除的配置
	 */
	public List<SystemConfig> getAllSystemConfig() {
		return systemConfigDao.getAll("deleteFlag",false);
	}

	/**
	 * 获取所有没有删除的配置
	 */
	public List<SystemConfig> getAllUseSystemConfig() {
		return systemConfigDao.findBy("deleteFlag", Constant.NO_DELTE_FLAG.toString(),true);
	}

	/**
	 * 获取所有没有删除的配置
	 */
	public List<SystemConfig> getAllUseSystemConfigNoCache() {
		//获取所有有效系统配置及 一键更新缓存时清空本地内存中的Map缓存
		fastSystemConfigCacheMap.clear();
		return systemConfigDao.findBy("deleteFlag", Constant.NO_DELTE_FLAG.toString(),false);
	}

	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public SystemConfig updateSystemConfig(SystemConfig systemConfig,HttpServletRequest request)throws Exception {
	
		SystemConfig db_config = getSystemConfigById(systemConfig.getId());
		String desc = "";
		if(!db_config.getValue().equals(systemConfig.getValue())) {
			desc = desc + "将"+db_config.getConfigName()+":配置值"+db_config.getValue()+"修改为"+systemConfig.getValue();
		}
		if(!db_config.getConfigName().equals(systemConfig.getConfigName())) {
			desc = desc + "将"+db_config.getConfigName()+":配置名称"+db_config.getConfigName()+"修改为"+systemConfig.getConfigName();
		}
		if(!db_config.getRemark().equals(systemConfig.getRemark())) {
			desc = desc + "将"+db_config.getRemark()+":备注"+db_config.getRemark()+"修改为"+systemConfig.getRemark();
		}
		db_config.setValue(systemConfig.getValue());
		db_config.setKey(systemConfig.getKey());
		db_config.setRemark(systemConfig.getRemark());
		db_config.setConfigName(systemConfig.getConfigName());
		db_config.setDeleteFlag("1");
		systemConfigDao.merge(db_config);

		try{
			//更新缓存
			IMemcachedCache cache = Cache.getCommonCacheInstatce();
			cache.put(db_config.getKey(),db_config);
			this.saveOperateLog(request,db_config.getConfigName(), desc,111);
		}catch(Exception e){

		}
		
		//remove local fast map cache key if existed
		if (db_config != null && fastSystemConfigCacheMap.containsKey(db_config.getKey())) {
			fastSystemConfigCacheMap.remove(db_config.getKey());
		}
		
		return db_config;
	}

	@Transactional
	public String removeSystemConfig(String configId,HttpServletRequest request) throws Exception {
		SystemConfig db_config = getSystemConfigById(configId);
		this.saveOperateLog(request,db_config.getConfigName(), "删除配置"+db_config.getConfigName(),111);
		db_config.setDeleteFlag(Constant.HAS_DELTE_FLAG.toString());
		try{
			//移除缓存
			IMemcachedCache commonOrderDetail = Cache.getCommonCacheInstatce();
			if(commonOrderDetail!=null){
			commonOrderDetail.remove(db_config.getKey());
			}
		}catch(Exception e){

		}
		systemConfigDao.merge(db_config);
		
		//remove local fast map cache key if existed
		if (db_config != null && fastSystemConfigCacheMap.containsKey(db_config.getKey())) {
			fastSystemConfigCacheMap.remove(db_config.getKey());
		}
		
		return configId;
	}
	@Transactional
	public String useConfig(String id,HttpServletRequest request) throws Exception{
		SystemConfig db_config = getSystemConfigById(id);
		this.saveOperateLog(request,db_config.getConfigName(), "启用配置"+db_config.getConfigName(),111);
		db_config.setDeleteFlag(Constant.NO_DELTE_FLAG.toString());
		systemConfigDao.updateSystemConfig(db_config);
		try{
			//更新缓存
			IMemcachedCache commonOrderDetail = Cache.getCommonCacheInstatce();
			if(commonOrderDetail!=null){
			commonOrderDetail.add(db_config.getKey(),db_config);
			}
		}catch(Exception e){

		}
		return Constant.SUCCESS;
	}

	public SystemConfig getSystemConfigByKey(String key) throws Exception{
//		SystemConfig config = systemConfigDao.findUniqueBy("key",key);
		SystemConfig config = getCacheSystemConfigByKey(key);

		return config;
	}

	/**
	 * 从缓存中取配置，如果缓存没有查询到数据，再查询数据库 加入缓存
	 */
	@Override
	public SystemConfig getCacheSystemConfigByKey(String key){
		/*
		try{
			IMemcachedCache commonOrderDetail = Cache.getCommonCacheInstatce();
			if(commonOrderDetail!= null && commonOrderDetail.get(key)!=null){
				SystemConfig config = (SystemConfig)commonOrderDetail.get(key);
				return config;
			}else{
				SystemConfig config = systemConfigDao.findSystemConfigByKey(key);
				if(config!=null){
					if(commonOrderDetail!= null){
						commonOrderDetail.put(key, config);
					}
					return config;
				}
			}
		}catch(Exception e){
			SystemConfig config = systemConfigDao.findSystemConfigByKey(key);
			if(config!=null){
				return config;
			}
		}
		*/
		return null;
	}

	public SystemConfig getConfigValueByKeyWithNoCache(String key){
		return systemConfigDao.findSystemConfigByKey(key);
	}
	
	public void saveConfigValueByKey(String key ,String value){
		SystemConfig config = getConfigValueByKeyWithNoCache(key);
		if(config != null){
			config.setValue(value);
			systemConfigDao.updateSystemConfig(config);
		}
		//如果保存的Key在本地内存Map中存在则删除该Key缓存
		if (fastSystemConfigCacheMap.containsKey(key)) {
			fastSystemConfigCacheMap.remove(key);
		}
	}

	
	/**
	 * 使用本地内存中的Map缓存获取特定系统配置项
	 * 为了获取最高的性能，采用本地ConcurrentMap 来缓存各个仓库的配置。	 * 
	 * 该方法现在   
	 * 
	 * 仅仅被WMS用来 获取各个仓库的授权码验证 
	 *  
	 * 当调用  一键更新缓存时该缓存会被清空
	 * @author hu.jp 2012-08-23
	 * @param key
	 * @return
	 */
	@Override
	public SystemConfig getConfigByLocalCacheMap(String key) {
		if (key != null) {
			if (fastSystemConfigCacheMap.containsKey(key)) {
				return fastSystemConfigCacheMap.get(key);
			} else {
				SystemConfig ret = systemConfigDao.findSystemConfigByKey(key);
				if (ret != null) {
					fastSystemConfigCacheMap.put(key, ret);
				}
				return ret;
			}
		} else {
			return null;
		}
	}
	
	/**
	 * 添加日志信息
	 * @author zhao.my
	 * @param request 
	 * @param role  AuthorityRole 对象，可以传null  修改时不能为空
	 * @param type  操作状态
	 * @throws Exception
	 */
	@Override
	public void saveOperateLog(HttpServletRequest request,String info_id,String operate_desc,Integer type) throws Exception{
		//获取后台用户登录的信息
		SystemmgtUser user = GetSessionUtil.getSystemUser(request);
		//获取IP信息
		String ip = GetSessionUtil.getIpAddr(request);
		
		OperateLog operateLog = new OperateLog();
		operateLog.setCreate_time(new Date());
		operateLog.setMod_name("客服系统");
		operateLog.setOperate_ip(ip);
		operateLog.setPortal_id(5);//5表示客服系统
		operateLog.setUser_id(user.getLoginName());
		operateLog.setUser_name(user.getUsername());
		operateLog.setOperate_type(type);
		operateLog.setInfo_id(info_id);
			operateLog.setOperate_desc(operate_desc);
		if(null != operate_desc && operate_desc.indexOf("一键更新")>=0){
			operateLog.setOperate_ip(request.getRemoteAddr());
		}
		
		//保存日志信息
		this.operateLogDao.save(operateLog);
		this.operateLogDao.flush();
	}

	@Override
	public PageFinder<OperateLog> pageQueryOperaterLog(OperateLogVo vo,
			Query query) {
		CriteriaAdapter criteriaAdapter = operateLogDao.createCriteriaAdapter();
		Criteria criteria = criteriaAdapter.getCriteria();
		criteria.add(Restrictions.eq("operate_type", 111));
		//操作人
		if(StringUtils.isNotEmpty(vo.getUser_name())){
			criteria.add(Restrictions.eq("user_name", vo.getUser_name()));
		}
		//开始结束时间
		if(StringUtils.isNotEmpty(vo.getState_time())){
			criteria.add(Restrictions.gt("create_time", DateUtil.parseDate(vo.getState_time(), "yyyy-MM-dd hh:mm:ss")));
		}
		if(StringUtils.isNotEmpty(vo.getEnd_time())){
			criteria.add(Restrictions.le("create_time", DateUtil.parseDate(vo.getEnd_time(), "yyyy-MM-dd hh:mm:ss")));
		}
		//ip
		if(StringUtils.isNotEmpty(vo.getOperate_ip())){
			criteria.add(Restrictions.eq("operate_ip", vo.getOperate_ip()));
		}
		//info_id
				if(StringUtils.isNotEmpty(vo.getInfo_id())){
					criteria.add(Restrictions.eq("info_id", vo.getInfo_id()));
				}
		//user_id
		if(StringUtils.isNotEmpty(vo.getUser_id())){
			criteria.add(Restrictions.eq("user_id", vo.getUser_id()));
		}
		criteria.addOrder(Order.desc("create_time"));
		
		return operateLogDao.pagedByCriteria(criteria, query.getPage(), query.getPageSize());
	}
}
