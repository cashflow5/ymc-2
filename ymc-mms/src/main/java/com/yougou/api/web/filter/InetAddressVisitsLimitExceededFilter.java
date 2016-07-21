package com.yougou.api.web.filter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.yougou.api.constant.YOPBusinessCode;
import com.yougou.api.exception.YOPRuntimeException;
import com.yougou.api.mongodb.GenericMongoDao;
import com.yougou.api.web.servlet.AbstractFilter;


/**
 * 限制同IP地址在重置访问记数器时间段中允许的最大访问次数
 * 
 * @author 杨梦清
 *
 */
public class InetAddressVisitsLimitExceededFilter extends AbstractFilter {

	@Resource
	private GenericMongoDao genericMongoDao;
	
//	@Resource
//	private IApiVisitCounterService apiVisitCounterService;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		int maxAccessCount = getFilterRuleAsInt();
		String inetAddress = getClientIp(request);
		DBObject condition = new BasicDBObject();
		condition.put(MONGODB_DBOBJECT_IDENTIFIER, inetAddress);
		condition.put(MONGODB_DBOBJECT_VARIABLE + "." + MONGODB_DBOBJECT_VARIABLE_2, new BasicDBObject("$gt", maxAccessCount));
		DBObject dbObject = genericMongoDao.uniqueDBObject(MONGODB_COLLECTION_NAME, condition);
		if (dbObject != null) {
			throw new YOPRuntimeException(YOPBusinessCode.FORBIDDEN, "Visits limit exceeded");
		}
			
//		try {
//			if (apiVisitCounterService.isVisitsLimitExceeded(getClientIp(request), getFilterRuleAsInt())) {
//				throw new YOPRuntimeException(YOPBusinessCode.FORBIDDEN, "Visits limit exceeded");
//			}
//		} catch (Exception ex) {
//			throw new YOPRuntimeException(YOPBusinessCode.ERROR, "Check Visit limit error.");
//		}
		
		chain.doFilter(request, response);
	}
}
