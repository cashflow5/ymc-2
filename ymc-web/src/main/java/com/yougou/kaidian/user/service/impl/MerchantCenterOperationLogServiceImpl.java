package com.yougou.kaidian.user.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.yougou.kaidian.common.base.PageFinder;
import com.yougou.kaidian.common.base.Query;
import com.yougou.kaidian.order.util.GetAddressByIpUtil;
import com.yougou.kaidian.user.dao.MerchantCenterOperationLogMapper;
import com.yougou.kaidian.user.model.pojo.MerchantCenterOperationLog;
import com.yougou.kaidian.user.service.IMerchantCenterOperationLogService;

@Service
public class MerchantCenterOperationLogServiceImpl implements
		IMerchantCenterOperationLogService {
	
	private Logger logger = LoggerFactory.getLogger(MerchantCenterOperationLogServiceImpl.class);
	@Resource
	private MerchantCenterOperationLogMapper logMapper;
	
	@Override
	public boolean insertOperationLog(MerchantCenterOperationLog log) {
		boolean flag = false;
		try{
			logMapper.insertSelective(log);
			flag = true;
		}catch(Exception e){
			logger.error("插入日志信息到数据库报错：",e);
		}
		return flag;
	}

	@Override
	public PageFinder<MerchantCenterOperationLog> selectByLoginName(Map<String,Object> params) {
		Query query = (Query) params.get("query");
		//登录日志列表查询
		List<MerchantCenterOperationLog> logList = logMapper.selectByLoginName(params);
		int rowCount = 0;
		// 查询总记录
		rowCount = logMapper.selectCountByLoginName(params);
		// 构造返回对象
		PageFinder<MerchantCenterOperationLog> pageFinder = new PageFinder<MerchantCenterOperationLog>(query.getPage(), query.getPageSize(), rowCount);
		pageFinder.setData(logList);
		return pageFinder;
	}

	@Override
	public boolean cleanLoginLog(int pageSize) throws Exception {
		boolean flag = false;
		try{
			updateAddr(pageSize);
			flag = true;
		}catch(Exception e){
			logger.error("清理归属地发生错误！",e);
			flag = false;
			throw new Exception("清理归属地发生错误！",e);
		}
		return flag;
	}
	
	/**
	 * updateAddr:批量更新归属地
	 * @author li.n1 
	 * @param size 每批批量更新的个数
	 * @since JDK 1.6 
	 * @date 2015-10-15 上午11:09:36
	 */
	
	private void updateAddr(int pageSize){
		try{
			int count = logMapper.findAllLoginIPErrorLogCount();
			int pageTotal = count%pageSize==0?count/pageSize:count/pageSize+1;
			RowBounds rowBounds = new RowBounds(0, pageSize);
			for(int i=0;i<pageTotal;i++){
				List<String> ipList = logMapper.findAllLoginIPErrorLog(rowBounds);
				if(ipList!=null && ipList.size()>0){
					updateAddrByBatch(ipList);
					logger.warn("===========第{}次提交了{}条清理IPError数据记录！============",i+1,pageSize);
				}else{
					logger.warn("===========第{}次提交，ipList为空！=================",i+1);
				}
			}
		}catch(Exception e){
			logger.error("清理归属地发生错误！",e);
		}
	}

	
	private void updateAddrByBatch(List<String> ipList) throws Exception {
		for (String ip : ipList) {
			try{
				logMapper.updateAddrByNewAddr(ip,
						GetAddressByIpUtil.GetAddressByIpWithSina(ip, "json"));
			}catch(Exception e){
				logger.error("更新ip{}的归属地发生错误：",new Object[]{ip,e});
			}
		}
	}
	
}
