package com.belle.yitiansystem.systemmgmt.service.impl;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.belle.infrastructure.constant.Constant;
import com.belle.infrastructure.orm.basedao.CritMap;
import com.belle.infrastructure.orm.basedao.CriteriaAdapter;
import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.yitiansystem.systemmgmt.dao.IOperateLogDao;
import com.belle.yitiansystem.systemmgmt.dao.IsystemmgtOperaterLogDao;
import com.belle.yitiansystem.systemmgmt.model.pojo.OperateLog;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUser;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUserOperateLog;
import com.belle.yitiansystem.systemmgmt.model.vo.OperateLogVo;
import com.belle.yitiansystem.systemmgmt.service.ISystemmgtOperaterLogService;

/**
 * 系统操作用户日志记录业务Service实现类
 * @author zhubin
 * date：2012-2-17 下午3:09:39
 */
@Service
public class SystemmgtOperaterLogServiceImpl implements ISystemmgtOperaterLogService {

	
	@Resource
	private IsystemmgtOperaterLogDao operaterLogDao;
	
	@Resource
	private IOperateLogDao operateDao;
	
	public PageFinder<SystemmgtUserOperateLog> pageQueryOperaterLog(SystemmgtUserOperateLog systemmgtUserOperateLog, Query query) {
		CritMap critMap = new CritMap();
		
		//TODO 增加查询条件
		critMap.addEqual("userId", systemmgtUserOperateLog.getUserId().replace(";", ""));
		
		critMap.addDesc("operateDate");

		return operaterLogDao.pagedByCritMap(critMap, query.getPage(), query.getPageSize());
	}

	@Transactional
	public void addUserOperateLog(SystemmgtUserOperateLog log,HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		SystemmgtUser systemUser = (SystemmgtUser)request.getSession().getAttribute(Constant.LOGIN_SYSTEM_USER);
		log.setOperateAccount(systemUser.getLoginName());
		log.setOperateName(systemUser.getUsername());
	    log.setOperateDate(new Date());
		operaterLogDao.save(log);
	}
	
	
	/**
	 * 查询日志（审计）
	 */
	public PageFinder<OperateLog> queryOperateLog(Query query,OperateLogVo log,String url){
		CriteriaAdapter criteriaAdapter = operateDao.createCriteriaAdapter();
		Criteria criteria = criteriaAdapter.getCriteria();
		//操作人
		if(StringUtils.isNotEmpty(log.getUser_name())){
			criteria.add(Restrictions.eq("user_name", log.getUser_name()));
		}
		//开始结束时间
		if(StringUtils.isNotEmpty(log.getState_time()) || StringUtils.isNotEmpty(log.getEnd_time())){
			criteria.add(Restrictions.between("create_time", log.getState_time(), log.getEnd_time()));
		}
		//ip
		if(StringUtils.isNotEmpty(log.getOperate_ip())){
			criteria.add(Restrictions.eq("operate_ip", log.getOperate_ip()));
		}
		//动作
		if(log.getOperate_type() != null){
			criteria.add(Restrictions.eq("operate_type", log.getOperate_type()));
		}else{ 
			if(StringUtils.equals("login", url)){
				criteria.add(Restrictions.or(Restrictions.eq("operate_type", 7), Restrictions.eq("operate_type", 8)));
			}
		}
		//ip
		if(StringUtils.isNotEmpty(log.getUser_id())){
			criteria.add(Restrictions.eq("user_id", log.getUser_id()));
		}
		criteria.addOrder(Order.desc("create_time"));
		
		return operateDao.pagedByCriteria(criteria, query.getPage(), query.getPageSize());
	}
	
}
