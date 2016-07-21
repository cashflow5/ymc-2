package com.belle.yitiansystem.systemmgmt.service;

import javax.servlet.http.HttpServletRequest;

import com.belle.infrastructure.orm.basedao.PageFinder;
import com.belle.infrastructure.orm.basedao.Query;
import com.belle.yitiansystem.systemmgmt.model.pojo.OperateLog;
import com.belle.yitiansystem.systemmgmt.model.pojo.SystemmgtUserOperateLog;
import com.belle.yitiansystem.systemmgmt.model.vo.OperateLogVo;

/**
 * 系统用户操作日志记录业务Service接口
 * @author zhubin
 * date：2012-2-17 下午3:09:05
 */
public interface ISystemmgtOperaterLogService {
	//查询所有操作日志记录
	public PageFinder<SystemmgtUserOperateLog> pageQueryOperaterLog(SystemmgtUserOperateLog systemmgtUserOperateLog, Query query);
	
	//新增操作日志记录
	void addUserOperateLog(SystemmgtUserOperateLog log,HttpServletRequest request)throws Exception;
	
	public PageFinder<OperateLog> queryOperateLog(Query query,OperateLogVo log,String url);
}
