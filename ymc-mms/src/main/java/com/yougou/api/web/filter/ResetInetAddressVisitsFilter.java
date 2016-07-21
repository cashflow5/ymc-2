package com.yougou.api.web.filter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.yougou.api.mongodb.GenericMongoDao;
import com.yougou.api.web.servlet.AbstractFilter;

/**
 * 重置访问计数器过滤器，业务规则配置值为毫秒
 * 
 * @author 杨梦清
 * 
 */
public class ResetInetAddressVisitsFilter extends AbstractFilter {

	@Resource
	private GenericMongoDao genericMongoDao;
	
//	@Resource
//	private IApiVisitCounterService apiVisitCounterService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String inetAddress = getClientIp(request);
		long timeMillis = (System.currentTimeMillis() - getFilterRuleAsLong());
		DBObject condition = new BasicDBObject();
		condition.put(MONGODB_DBOBJECT_IDENTIFIER, inetAddress);
		condition.put(MONGODB_DBOBJECT_VARIABLE + "." + MONGODB_DBOBJECT_VARIABLE_1, new BasicDBObject("$lt", timeMillis));
		DBCollection dbCollection = genericMongoDao.getCollection(MONGODB_COLLECTION_NAME);
		DBObject dbObject = dbCollection.findOne(condition);
		if (dbObject != null) {
			dbCollection.remove(dbObject);
			LOGGER.info("Reset inet address visits");
		}
			
//		try {
//			apiVisitCounterService.deleteApiVisitCounter(getClientIp(request), (System.currentTimeMillis() - getFilterRuleAsLong()));
//		} catch (Exception ex) {
//			throw new YOPRuntimeException(YOPBusinessCode.ERROR, "Reset visit counter error.");
//		}

		chain.doFilter(request, response);
	}
}
